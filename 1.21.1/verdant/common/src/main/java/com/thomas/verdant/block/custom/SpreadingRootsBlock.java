package com.thomas.verdant.block.custom;

import com.thomas.verdant.Constants;
import com.thomas.verdant.block.Hoeable;
import com.thomas.verdant.block.VerdantGrower;
import com.thomas.verdant.registry.BlockTransformerRegistry;
import com.thomas.verdant.registry.FeatureSetRegistry;
import com.thomas.verdant.util.blocktransformer.BlockTransformer;
import com.thomas.verdant.util.featureset.FeatureSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class SpreadingRootsBlock extends Block implements VerdantGrower, Hoeable, BonemealableBlock {

    // The maximum distance the block can be from water.
    public static final int MAX_DISTANCE = 7;
    // The minimum distance the block can be from water.
    public static final int MIN_DISTANCE = 1;

    // A property storing how far the block is from the nearest water source.
    public static final IntegerProperty WATER_DISTANCE = IntegerProperty.create(
            "water_distance",
            MIN_DISTANCE,
            MAX_DISTANCE
    );

    // Properties that aren't reflected in what the user sees.
    // These are used for caching surrounding blocks, to optimize spreading mechanics.
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public static final EnumProperty<NeighborType> ABOVE = EnumProperty.create("above", NeighborType.class);
    public static final EnumProperty<NeighborType> BELOW = EnumProperty.create("below", NeighborType.class);

    // These store properties of the block internally; this allows me to reuse this class
    // for six blocks (at the moment) and possibly more in the future.
    // Whether this block can swap to a "wet" version; for instance, dirt to mud.
    private final boolean hasAlternateWetness;
    // Whether this block is a wet version.
    private final boolean isWet;
    // Using multiple indirection, gets the dry version if the block is wet and the wet version if the block is dry.
    // Multiple indirection is necessary due to registration problems.
    private final Supplier<Supplier<Block>> alternateWet;
    // Whether this block is grassy.
    private final boolean isGrassy;
    // Gets the grassy version if the block is not grassy and the non-grassy version if the block is grassy.
    private final Supplier<Supplier<Block>> alternateGrassy;

    // Sets all the properties.
    // The other constructors below are preferred.
    public SpreadingRootsBlock(BlockBehaviour.Properties properties, boolean isGrassy, Supplier<Supplier<Block>> alternateGrassy, boolean isWet, Supplier<Supplier<Block>> alternateWet, boolean hasAlternateWetness) {
        super(properties);
        // Set the default state to be non-hydrated.
        this.registerDefaultState(this.stateDefinition.any().setValue(WATER_DISTANCE, MAX_DISTANCE));
        this.isGrassy = isGrassy;
        this.alternateGrassy = alternateGrassy;
        this.isWet = isWet;
        this.alternateWet = alternateWet;
        this.hasAlternateWetness = hasAlternateWetness;
    }

    // Specialized versions of the constructor.
    // This one is for blocks with a wet/dry state.
    public SpreadingRootsBlock(BlockBehaviour.Properties properties, boolean isGrassy, Supplier<Supplier<Block>> alternateGrassy, boolean isWet, Supplier<Supplier<Block>> alternateWet) {
        this(properties, isGrassy, alternateGrassy, isWet, alternateWet, true);
    }

    // This one is for blocks without a wet/dry state.
    public SpreadingRootsBlock(BlockBehaviour.Properties properties, boolean isGrassy, Supplier<Supplier<Block>> alternateGrassy) {
        this(properties, isGrassy, alternateGrassy, false, null, false);
    }

    // Returns the distance to the nearest available water, as stored in the given state.
    // Very similar to how leaf blocks work; just changed to check for water instead of logs.
    // Also doesn't bother redirecting through an optional, to save on computational resources.
    // There's no need to make that many extra objects.
    protected static int getDistanceAt(BlockState state) {
        if (state.getFluidState().is(FluidTags.WATER) || state.is(Blocks.WATER)) {
            return 0;
        } else {
            return state.hasProperty(WATER_DISTANCE) ? state.getValue(WATER_DISTANCE) : MAX_DISTANCE;
        }
    }

    // Generates a random position within the given distance of the block.
    // dist is the maximum Chebyshev distance, bounded between 0 and 128.
    // To avoid expensive random calls, this uses bit shifting to
    // extract the data from only one integer.
    // I probably don't need to do this, but I'm keeping it around. It can't hurt.
    public static BlockPos withinDist(BlockPos pos, int dist, RandomSource rand) {
        // Throw an error if it's outside the bounds.
        if (dist < 0 || dist > 127) {
            throw new IllegalArgumentException("Chebyshev must be in range 0 to 127.");
        }

        int range = 2 * dist + 1;
        // Generate a single random 32-bit number
        int num = rand.nextInt();
        // Extract offsets from different parts of the number
        int offsetX = (num & 0x7F) % range - dist;       // Lowest 7 bits
        int offsetY = ((num >> 7) & 0x7F) % range - dist; // Next 7 bits
        int offsetZ = ((num >> 14) & 0x7F) % range - dist; // Following 7 bits

        return pos.offset(offsetX, offsetY, offsetZ);
    }

    // This handles erosion, growth, and (eventually) placing features like
    // grass, bushes, vines, and monsters.
    public void grow(BlockState state, ServerLevel level, BlockPos pos) {
        // First, check if the state is wet.
        // This is important for erosion; some things can only be
        // eroded with access to water.
        boolean isWet = state.getValue(WATER_DISTANCE) < MAX_DISTANCE;
        for (int tries = 0; tries < 3; tries++) {
            // The range to check is constant.
            BlockPos posToTry = withinDist(pos, tries + 1, level.random);
            // Try to convert the nearby block.
            if (!this.erodeOrGrow(level, posToTry, isWet)) {
                this.placeFeature(state, level, pos);
                break;
            }
        }
    }

    public void placeFeature(BlockState state, ServerLevel level, BlockPos pos) {
        Registry<FeatureSet> features = level.registryAccess().lookupOrThrow(FeatureSet.KEY);

        if (state.getValue(ABOVE) == NeighborType.AIR) {
            FeatureSet set = features.get(FeatureSetRegistry.ABOVE_GROUND).orElseThrow().value();
            set.place(level, pos.above());
        } else if (state.getValue(BELOW) == NeighborType.AIR) {
            FeatureSet set = features.get(FeatureSetRegistry.HANGING).orElseThrow().value();
            set.place(level, pos.below());
        } else if (state.getValue(ABOVE) == NeighborType.WATER) {
            FeatureSet set = features.get(FeatureSetRegistry.WATER).orElseThrow().value();
            set.place(level, pos.above());
        } else if (state.getValue(ABOVE) == NeighborType.LOG) {
            FeatureSet set = features.get(FeatureSetRegistry.BELOW_LOG).orElseThrow().value();
            set.place(level, pos.above());
        } else {
            FeatureSet set = features.get(FeatureSetRegistry.ALWAYS).orElseThrow().value();
            set.place(level, pos.above());
        }


    }

    // Returns true if the block at the given position can be grassy.
    // For now, just checks if it has a non-liquid, non-full block above it.
    protected boolean canBeGrassy(BlockState state, LevelAccessor level, BlockPos pos) {
        // Take the shortcut!
        if (state.getValue(ABOVE) == NeighborType.AIR) {
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
            return !aboveState.isCollisionShapeFullBlock(level, abovePos);
        }
    }

    // Returns true if the block at the given position can be switched to a wet state.
    // I.E., if it is anywhere near water.
    protected boolean canBeWet(BlockState state, @SuppressWarnings("unused") LevelAccessor level, @SuppressWarnings("unused") BlockPos pos) {
        return state.getValue(WATER_DISTANCE) < MAX_DISTANCE;
    }

    // Handles bone mealing. Fairly straightforward.
    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return state.getValue(ACTIVE);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return this.isValidBonemealTarget(level, pos, state);
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        this.grow(state, level, pos);
    }

    @Override
    public Type getType() {
        return Type.NEIGHBOR_SPREADER;
    }

    protected BlockState updateDistance(BlockState state, Level level, BlockPos pos) {
        // First, get the block transformer. As usual, this is a moderately involved process.
        // These registers are synced; therefore, this works on the client and server equally well.
        // Unfortunately I haven't been able to test that in a multiplayer server, but I'll
        // cross that bridge when I come to it.
        Registry<BlockTransformer> blockTransformers = level.registryAccess().lookupOrThrow(BlockTransformer.KEY);
        BlockTransformer erode = blockTransformers.get(BlockTransformerRegistry.EROSION).orElseThrow().value();
        BlockTransformer erodeWet = blockTransformers.get(BlockTransformerRegistry.EROSION_WET).orElseThrow().value();
        BlockTransformer roots = blockTransformers.get(BlockTransformerRegistry.VERDANT_ROOTS).orElseThrow().value();

        // Now, update the state's activity and wetness.
        // But first, set up some variables that will be needed.
        // The current least distance to water. Will be decreased as it goes.
        int distance = MAX_DISTANCE;
        // To hold the position of the neighbor, to avoid having to
        // create new BlockPos objects unnecessarily.
        BlockPos.MutableBlockPos neighborPos = new BlockPos.MutableBlockPos();
        // To store the position of the neighbor.
        BlockState neighbor;
        // Calculates whether this block should be able to be active.
        // If it's grassy, or should be able to switch its wetness, it can stay active until that is done.
        boolean canBeActive = this.isGrassy || (this.hasAlternateWetness && (this.isWet != this.canBeWet(
                state,
                level,
                pos
        )));

        // Checking every neighbor:
        Direction[] directions = Direction.values();
        int numDirections = directions.length;
        Direction direction;
        for (int i = 0; i < numDirections; i++) {
            direction = directions[i];

            // If we don't know what's in that direction, check it.
            neighborPos.setWithOffset(pos, direction);
            neighbor = level.getBlockState(neighborPos);

            // Special cases.
            // These check for air above and below.
            RegistryAccess access = level.registryAccess();
            boolean overrideTicking = false;
            NeighborType adjacent = NeighborType.OTHER;
            if (direction == Direction.UP) {
                adjacent = NeighborType.get(access, neighbor);
                state = state.setValue(ABOVE, adjacent);
            } else if (direction == Direction.DOWN) {
                adjacent = NeighborType.get(access, neighbor);
                state = state.setValue(BELOW, adjacent);
            }
            overrideTicking = adjacent != NeighborType.OTHER;

            canBeActive |= overrideTicking;
            // If the block has not been marked as able to be active, check its neighbor
            // for the criteria.
            if (!canBeActive) {
                // First, check whether the neighbor is a full block.
                boolean isFullBlock = neighbor.isCollisionShapeFullBlock(level, neighborPos);
                if (isFullBlock) {
                    // If the block can be rooted or eroded, it should not prevent the roots from
                    // growing.
                    boolean canBeErodedOrRooted = erode.isValidInput(
                            level.registryAccess(),
                            neighbor
                    ) || erodeWet.isValidInput(
                            level.registryAccess(),
                            neighbor
                    ) || roots.isValidInput(level.registryAccess(), neighbor);
                    canBeActive = canBeErodedOrRooted;
                } else {
                    canBeActive = true;
                }
            }

            // Second, handle updating wetness.
            if (distance > MIN_DISTANCE) {
                // Update the distance to be the minimum of the current lowest and the distance gotten from the
                // neighbor (plus one).
                distance = Math.min(distance, getDistanceAt(neighbor) + 1);
                // DO NOT break out of the loop since the rest still needs to run.
                // (Note to self: did this originally; it didn't go well. Recheck legacy code before copying it!)
            }
        }
        // Update and return the state.
        // The air values were already set above.
        return state.setValue(WATER_DISTANCE, distance).setValue(ACTIVE, canBeActive);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource random) {
        // Logic is too complicated to duplicate, most likely.
        tickAccess.scheduleTick(currentPos, this, 1);

        return state;
    }

    // Applies custom hoeing logic; I feel like doing this should be much easier.
    @Override
    protected @NotNull InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (level instanceof ServerLevel serverLevel) {
            if (stack.is(ItemTags.HOES)) {
                BlockState hoedTo = this.hoe(state, serverLevel, pos, stack);
                serverLevel.setBlockAndUpdate(pos, hoedTo);
                return InteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    // Handles spreading and updating wetness/grassiness.
    // This is anticipated to cause the most lag.
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        // If it is ticking while inactive, there is a problem. Log an error and report back.
        if (!state.getValue(ACTIVE)) {
            Constants.LOG.warn("SpreadingRootsBlock is ticking while inactive ({}).", state);
            Constants.LOG.warn("Please report this warning to the developer of the mod.");
            return;
        }
        // If it is grassy and should not be, or it is not grassy but should be, swap.
        // Not-equal is being used as XOR here, to report a mismatch.
        if (this.isGrassy != this.canBeGrassy(state, level, pos)) {
            // Update the state, copying all applicable properties.
            state = BlockTransformer.copyProperties(state, this.alternateGrassy.get().get());
        }

        // If it is wet and should not be, or it is not wet but should be, swap.
        // Not-equal is being used as XOR here, to report a mismatch.
        else if (this.hasAlternateWetness && (this.isWet != this.canBeWet(state, level, pos))) {
            // Update the state, copying all applicable properties.
            state = BlockTransformer.copyProperties(state, this.alternateWet.get().get());
        }
        // Set the state in the world.
        level.setBlockAndUpdate(pos, state);
        // Erode or spread, and grow.
        this.grow(state, level, pos);
    }

    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.setBlockAndUpdate(pos, this.updateDistance(state, level, pos));
    }

    // Checks if it can random tick. This significantly decreases lag!
    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(ACTIVE);
    }

    // Handles getting the placement state. This unfortunately copies a lot of logic from the
    // updateShape function, which I'll try to simplify now that I've got that one working.
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.updateDistance(this.defaultBlockState(), context.getLevel(), context.getClickedPos());
    }

    // Very important!
    // Defines the properties for the block.
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATER_DISTANCE, ACTIVE, ABOVE, BELOW);
    }

    public enum NeighborType implements StringRepresentable {

        OTHER("other", null),
        AIR("air", (access, state) -> state.isAir()),
        LOG("log", (access, state) -> state.is(BlockTags.LOGS)),
        WATER("water", (access, state) -> state.is(Blocks.WATER) && state.getFluidState().isSourceOfType(Fluids.WATER));

        private final String representation;
        private final BiPredicate<RegistryAccess, BlockState> identifier;


        NeighborType(String representation, BiPredicate<RegistryAccess, BlockState> identifier) {
            this.representation = representation;
            this.identifier = identifier;
        }

        public static NeighborType get(RegistryAccess access, BlockState state) {
            for (NeighborType type : NeighborType.values()) {
                if (type.identifier != null && type.identifier.test(access, state)) {
                    return type;
                }
            }
            return OTHER;
        }

        @Override
        public String getSerializedName() {
            return this.representation;
        }
    }

}
