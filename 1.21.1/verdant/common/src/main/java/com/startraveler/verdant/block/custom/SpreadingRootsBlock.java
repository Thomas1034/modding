/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant.block.custom;

import com.startraveler.rootbound.blocktransformer.BlockTransformer;
import com.startraveler.rootbound.featureset.FeatureSet;
import com.startraveler.verdant.block.Hoeable;
import com.startraveler.verdant.block.VerdantGrower;
import com.startraveler.verdant.registry.BlockTransformerRegistry;
import com.startraveler.verdant.registry.FeatureSetRegistry;
import net.minecraft.core.*;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class SpreadingRootsBlock extends Block implements VerdantGrower, Hoeable, BonemealableBlock {
    public static final float ACTIVE_SPREAD_RATE = 0.25f;
    public static final float INACTIVE_SPREAD_RATE_FACTOR = 1f / 16f;
    // The maximum distance the block can be from water.
    public static final int MAX_DISTANCE = 7;
    // The minimum distance the block can be from water.
    public static final int MIN_DISTANCE = 1;
    // The distance to spread to.
    public static final int SPREAD_DISTANCE = 4;
    // A property storing how far the block is from the nearest water source.
    public static final IntegerProperty WATER_DISTANCE = IntegerProperty.create(
            "water_distance",
            MIN_DISTANCE,
            MAX_DISTANCE
    );

    // Properties that aren't reflected in what the user sees.
    // These are used for caching surrounding blocks, to optimize spreading mechanics.
    public static final BooleanProperty SUCCESSFULLY_SPREAD = BooleanProperty.create("successfully_spread");
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final BooleanProperty SNOWY = BlockStateProperties.SNOWY;
    public static final EnumProperty<@NotNull ContextType> CONTEXT = EnumProperty.create("context", ContextType.class);
    // The list of offsets to spread to.
    protected static final List<int[]> OFFSETS_TO_SPREAD_TO = generateOffsetsInRadius(SPREAD_DISTANCE);
    // These store properties of the block internally; this allows me to reuse this class
    // for six blocks (at the moment) and possibly more in the future.
    // Whether this block can swap to a "wet" version; for instance, dirt to mud.
    private final boolean hasAlternateWetness;
    // Whether this block is a wet version.
    private final boolean isWet;
    // Using multiple indirection, gets the dry version if the block is wet and the wet version if the block is dry.
    // Multiple indirection is necessary due to registration problems.
    private final Supplier<Supplier<? extends Block>> alternateWet;
    // Whether this block is grassy.
    private final boolean isGrassy;
    // Gets the grassy version if the block is not grassy and the non-grassy version if the block is grassy.
    private final Supplier<Supplier<? extends Block>> alternateGrassy;

    // Sets all the properties.
    // The other constructors below are preferred.
    public SpreadingRootsBlock(Properties properties, boolean isGrassy, Supplier<Supplier<? extends Block>> alternateGrassy, boolean isWet, Supplier<Supplier<? extends Block>> alternateWet, boolean hasAlternateWetness) {
        super(properties);
        // Set the default state to be non-hydrated.
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(WATER_DISTANCE, MAX_DISTANCE)
                .setValue(SNOWY, false)
                .setValue(ACTIVE, true)
                .setValue(SUCCESSFULLY_SPREAD, true)
                .setValue(CONTEXT, ContextType.DEFAULT));
        this.isGrassy = isGrassy;
        this.alternateGrassy = alternateGrassy;
        this.isWet = isWet;
        this.alternateWet = alternateWet;
        this.hasAlternateWetness = hasAlternateWetness;
    }

    // Specialized versions of the constructor.
    // This one is for blocks with a wet/dry state.
    public SpreadingRootsBlock(Properties properties, boolean isGrassy, Supplier<Supplier<? extends Block>> alternateGrassy, boolean isWet, Supplier<Supplier<? extends Block>> alternateWet) {
        this(properties, isGrassy, alternateGrassy, isWet, alternateWet, true);
    }

    // This one is for blocks without a wet/dry state.
    public SpreadingRootsBlock(Properties properties, boolean isGrassy, Supplier<Supplier<? extends Block>> alternateGrassy) {
        this(properties, isGrassy, alternateGrassy, false, null, false);
    }

    public static @NotNull List<int[]> generateOffsetsInRadius(int radius) {
        List<int[]> points = new ArrayList<>();
        int r2 = radius * radius;
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                for (int k = -radius; k <= radius; k++) {
                    if (i * i + j * j + k * k < r2) {
                        points.add(new int[]{i, j, k});
                    }
                }
            }
        }

        return points;
    }


    // Returns the distance to the nearest available water, as stored in the given state.
    // Very similar to how leaf blocks work; just changed to check for water instead of logs.
    // Also doesn't bother redirecting through an optional, to save on computational resources.
    // There's no need to make that many extra objects.
    protected static int getDistanceAt(@NotNull BlockState state) {
        if (state.is(Blocks.WATER) || state.getFluidState().is(FluidTags.WATER)) {
            return 0;
        } else {
            return state.getValueOrElse(WATER_DISTANCE, MAX_DISTANCE);
        }
    }

    protected static boolean isSnowySetting(@NotNull BlockState state) {
        return state.is(BlockTags.SNOW);
    }

    // This handles erosion, growth, and (eventually) placing features like
    // grass, bushes, vines, etc.
    // Returns whether any erosion or growth succeeded.
    public boolean grow(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos) {

        boolean anySucceeded = false;
        // First, check if the state is wet.
        // This is important for erosion; some things can only be
        // eroded with access to water.
        boolean isWet = state.getValue(WATER_DISTANCE) < MAX_DISTANCE;
        // Spread everywhere in the radius.
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int posX = pos.getX();
        int posY = pos.getY();
        int posZ = pos.getZ();
        for (int[] offset : OFFSETS_TO_SPREAD_TO) {
            mutableBlockPos.set(posX + offset[0], posY + offset[1], posZ + offset[2]);
            anySucceeded |= this.erodeOrGrow(level, mutableBlockPos, isWet);
        }
        anySucceeded |= this.placeFeature(state, level, pos);
        return anySucceeded;
    }

    public boolean placeFeature(@NotNull BlockState state, @NotNull ServerLevel level, BlockPos pos) {
        Registry<FeatureSet> featureSetRegistry = level.registryAccess().lookupOrThrow(FeatureSet.KEY);
        ContextType context = state.getValueOrElse(CONTEXT, ContextType.DEFAULT);
        FeatureSet featureSet = featureSetRegistry.get(context.featureSet()).orElseThrow().value();
        return featureSet.place(level, pos.offset(context.offset()));
    }

    // Returns true if the block at the given position can be grassy.
    // For now, just checks if it has a non-liquid, non-full block above it.
    protected boolean canBeGrassy(@NotNull BlockState state, LevelAccessor level, BlockPos pos) {
        // Take the shortcut!
        if (state.getValue(CONTEXT) == ContextType.ABOVE_GROUND) {
            return true;
        }
        // Get the position above the block.
        BlockPos abovePos = pos.above();
        // Get the state above the block.
        BlockState aboveState = level.getBlockState(abovePos);
        // If the state above the block has fluid, then this cannot be grass.
        if (aboveState.getFluidState().getAmount() > 0) {
            return false;
        } else {
            // If it does not have fluid, then it can only be grass if the block above is not a full block.
            return !aboveState.isCollisionShapeFullBlock(level, abovePos) || isSnowySetting(aboveState);
        }
    }

    // Returns true if the block at the given position can be switched to a wet state.
    // I.E., if it is anywhere near water.
    protected boolean canBeWet(@NotNull BlockState state) {
        return state.getValue(WATER_DISTANCE) < MAX_DISTANCE;
    }

    // Handles bone mealing. Fairly straightforward.
    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return state.getValue(ACTIVE);
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return this.isValidBonemealTarget(level, pos, state);
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        this.grow(state, level, pos);
    }

    @Override
    public @NotNull Type getType() {
        return Type.NEIGHBOR_SPREADER;
    }

    protected BlockState updateState(BlockState state, @NotNull LevelReader level, BlockPos pos) {

        RegistryAccess access = level.registryAccess();

        BlockTransformer erode = BlockTransformer.SAFE_CACHE.get(access, BlockTransformerRegistry.EROSION);

        BlockTransformer erodeWet = BlockTransformer.SAFE_CACHE.get(access, BlockTransformerRegistry.EROSION_WET);

        BlockTransformer roots = BlockTransformer.SAFE_CACHE.get(access, BlockTransformerRegistry.VERDANT_ROOTS);

        // Cache all neighboring states immediately.
        // This ensures we don't have to access the world repeatedly.
        BlockState[] neighbors = new BlockState[6];

        BlockPos.MutableBlockPos neighborPos = new BlockPos.MutableBlockPos();

        for (Direction direction : Direction.values()) {

            neighborPos.setWithOffset(pos, direction);

            neighbors[direction.ordinal()] = level.getBlockState(neighborPos);

        }

        BlockState aboveState = neighbors[Direction.UP.ordinal()];

        BlockState belowState = neighbors[Direction.DOWN.ordinal()];

        // Context classification.
        ContextType context = ContextType.get(aboveState, belowState);

        boolean snowy = isSnowySetting(aboveState);

        // Initial activation state.
        boolean canBeActive = this.isGrassy || (this.hasAlternateWetness && (this.isWet != this.canBeWet(state)));

        // Water propagation.
        int distance = MAX_DISTANCE;

        for (Direction direction : Direction.values()) {

            BlockState neighbor = neighbors[direction.ordinal()];

            // Water distance propagation.
            if (distance > MIN_DISTANCE) {

                distance = Math.min(distance, getDistanceAt(neighbor) + 1);

            }

            // Activity checks.
            if (!canBeActive) {

                // Fast-path:
                // if neighboring terrain is transformable,
                // roots should remain active.
                canBeActive = erode.isValidInput(access, neighbor) || erodeWet.isValidInput(
                        access,
                        neighbor
                ) || roots.isValidInput(access, neighbor);

                // Fallback:
                // exposed/non-solid neighbors also permit activity.
                if (!canBeActive) {

                    neighborPos.setWithOffset(pos, direction);

                    canBeActive = !neighbor.isCollisionShapeFullBlock(level, neighborPos);

                }
            }

            // Early exit once fully hydrated and active.
            if (distance == MIN_DISTANCE && canBeActive) {
                break;
            }
        }

        return state.setValue(CONTEXT, context)
                .setValue(SNOWY, snowy)
                .setValue(WATER_DISTANCE, distance)
                .setValue(ACTIVE, canBeActive);
    }

    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull LevelReader level, @NotNull ScheduledTickAccess tickAccess, @NotNull BlockPos currentPos, @NotNull Direction facing, @NotNull BlockPos facingPos, @NotNull BlockState facingState, @NotNull RandomSource random) {

        // Logic is too complicated to duplicate, most likely.
        state = this.updateState(state, level, currentPos);
        return facing == Direction.UP ? state.setValue(SNOWY, isSnowySetting(facingState)) : super.updateShape(
                state,
                level,
                tickAccess,
                currentPos,
                facing,
                facingPos,
                facingState,
                random
        );
    }

    // Applies custom hoeing logic; I feel like doing this should be much easier.
    @Override
    protected @NotNull InteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {

        if (level instanceof ServerLevel serverLevel) {
            if (stack.is(ItemTags.HOES)) {
                BlockState hoedTo = this.hoe(state, serverLevel, pos, stack);
                serverLevel.setBlockAndUpdate(pos, hoedTo);
                return InteractionResult.SUCCESS_SERVER;
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    // Handles spreading and updating wetness/grassiness.
    // This is anticipated to cause the most lag.
    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource rand) {
        // If it is ticking while inactive, there is a problem.
        if (!state.getValue(ACTIVE)) {
            return;
        }
        BlockState originalState = state;
        // If it is grassy and should not be, or it is not grassy but should be, swap.
        // Not-equal is being used as XOR here, to report a mismatch.
        if (this.isGrassy != this.canBeGrassy(state, level, pos)) {
            // Update the state, copying all applicable properties.
            state = BlockTransformer.copyProperties(state, this.alternateGrassy.get().get());
        }
        // If it is wet and should not be, or it is not wet but should be, swap.
        // Not-equal is being used as XOR here, to report a mismatch.
        else if (this.hasAlternateWetness && (this.isWet != this.canBeWet(state))) {
            // Update the state, copying all applicable properties.
            state = BlockTransformer.copyProperties(state, this.alternateWet.get().get());
        }
        // Erode or spread, and grow.
        if (rand.nextFloat() < this.chanceToSpread(state)) {
            boolean wasPreviouslyUnsuccessful = state.getValue(SUCCESSFULLY_SPREAD);
            boolean successfullySpread = this.grow(state, level, pos);
            if (wasPreviouslyUnsuccessful && !successfullySpread) {
                state = state.setValue(ACTIVE, false);
            } else {
                state = state.setValue(SUCCESSFULLY_SPREAD, successfullySpread);
            }
        }
        // Set the state in the world.
        if (state != originalState) {
            BlockState updatedOriginalStateAfterFeaturePlacement = level.getBlockState(pos);
            if (updatedOriginalStateAfterFeaturePlacement.is(this)) {
                level.setBlockAndUpdate(pos, state);
            }
        }
    }

    // Checks if it can random tick. This significantly decreases lag!
    @Override
    public boolean isRandomlyTicking(@NotNull BlockState state) {
        return state.getValue(ACTIVE);
    }

    // Handles getting the placement state. This unfortunately copies a lot of logic from the
    // updateShape function, which I'll try to simplify now that I've got that one working.
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {

        BlockState above = context.getLevel().getBlockState(context.getClickedPos().above());
        Level level = context.getLevel();
        return (level instanceof ServerLevel serverLevel ? this.updateState(
                this.defaultBlockState(),
                serverLevel,
                context.getClickedPos()
        ) : this.defaultBlockState()).setValue(SNOWY, isSnowySetting(above));
    }

    // Very important!
    // Defines the properties for the block.
    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, @NotNull BlockState> builder) {
        builder.add(WATER_DISTANCE, ACTIVE, CONTEXT, SUCCESSFULLY_SPREAD, SNOWY);
    }

    protected float chanceToSpread(@NotNull BlockState state) {
        return ACTIVE_SPREAD_RATE * (state.getValue(SUCCESSFULLY_SPREAD) ? 1 : INACTIVE_SPREAD_RATE_FACTOR);
    }

    public enum ContextType implements StringRepresentable {
        HANGING(
                "hanging",
                (_, belowState) -> belowState.isAir(),
                Direction.DOWN.getUnitVec3i(),
                () -> FeatureSetRegistry.HANGING
        ),

        BELOW_LOG(
                "below_log",
                (aboveState, _) -> aboveState.is(BlockTags.LOGS),
                Direction.UP.getUnitVec3i(),
                () -> FeatureSetRegistry.BELOW_LOG
        ),

        WATER(
                "water",
                (aboveState, _) -> aboveState.getFluidState().is(FluidTags.WATER),
                Direction.UP.getUnitVec3i(),
                () -> FeatureSetRegistry.WATER
        ),

        ABOVE_GROUND(
                "above_ground",
                (aboveState, _) -> aboveState.isAir() || isSnowySetting(aboveState),
                Direction.UP.getUnitVec3i(),
                () -> FeatureSetRegistry.ABOVE_GROUND
        ),

        DEFAULT("default", (_, _) -> true, Vec3i.ZERO, () -> FeatureSetRegistry.ALWAYS);

        private static final ContextType[] VALUES = values();

        private final String serializedName;
        private final ContextPredicate predicate;
        private final Vec3i offset;
        private final Supplier<Identifier> featureSet;

        ContextType(String serializedName, ContextPredicate predicate, Vec3i offset, Supplier<Identifier> featureSet) {
            this.serializedName = serializedName;
            this.predicate = predicate;
            this.offset = offset;
            this.featureSet = featureSet;
        }

        public static @NotNull ContextType get(@NotNull BlockState aboveState, @NotNull BlockState belowState) {
            // More specific contexts must appear first!
            for (ContextType type : VALUES) {
                if (type.predicate.test(aboveState, belowState)) {
                    return type;
                }
            }

            return DEFAULT;
        }

        public @NotNull Vec3i offset() {
            return this.offset;
        }

        public @NotNull Identifier featureSet() {
            return this.featureSet.get();
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.serializedName;
        }

        @FunctionalInterface
        public interface ContextPredicate extends BiPredicate<BlockState, BlockState> {
            @Override
            boolean test(BlockState aboveState, BlockState belowState);
        }
    }
}

