package com.thomas.verdant.block.custom;

import com.thomas.verdant.Constants;
import com.thomas.verdant.block.Hoeable;
import com.thomas.verdant.block.VerdantGrower;
import com.thomas.verdant.registry.BlockTransformerRegistry;
import com.thomas.verdant.util.blocktransformer.BlockTransformer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalInt;
import java.util.function.Supplier;

public class SpreadingRootsBlock extends Block implements VerdantGrower, Hoeable {

    // The maximum distance the block can be from water.
    public static final int MAX_DISTANCE = 15;
    // The minimum distance the block can be from water.
    public static final int MIN_DISTANCE = 1;

    // A property storing how far the block is from the nearest water source.
    public static final IntegerProperty WATER_DISTANCE = IntegerProperty.create("water_distance", MIN_DISTANCE,
            MAX_DISTANCE);

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    private final boolean isGrassy;
    private final Supplier<Supplier<Block>> alternateGrassy;

    public SpreadingRootsBlock(BlockBehaviour.Properties properties, boolean isGrassy, Supplier<Supplier<Block>> alternateGrassy) {
        super(properties);
        // Set the default state to be non-hydrated.
        this.registerDefaultState(this.stateDefinition.any().setValue(WATER_DISTANCE, MAX_DISTANCE));
        this.isGrassy = isGrassy;
        this.alternateGrassy = alternateGrassy;
    }

    // Returns the distance to the nearest available water, as stored in the given state.
    protected static int getDistanceAt(LevelAccessor level, BlockPos pos, BlockState state) {
        return getOptionalDistanceAt(state).orElse(MAX_DISTANCE);
    }

    private static OptionalInt getOptionalDistanceAt(BlockState state) {
        if (state.getFluidState().is(FluidTags.WATER) || state.is(Blocks.WATER)) {
            return OptionalInt.of(0);
        } else {
            return state.hasProperty(WATER_DISTANCE) ? OptionalInt.of(state.getValue(WATER_DISTANCE))
                    : OptionalInt.empty();
        }
    }

    // Updates the block whenever there is a change next to it.
    @Override
    @NotNull
    public BlockState updateShape(BlockState state, Direction dir, BlockState otherState, LevelAccessor level,
                                  BlockPos pos, BlockPos otherPos) {
        // First, get the block transformer. As usual, this is a moderately involved process.
        Registry<BlockTransformer> blockTransformers = level.registryAccess().registryOrThrow(BlockTransformer.KEY);
        BlockTransformer erode = blockTransformers.get(BlockTransformerRegistry.EROSION);
        BlockTransformer erodeWet = blockTransformers.get(BlockTransformerRegistry.EROSION_WET);
        BlockTransformer roots = blockTransformers.get(BlockTransformerRegistry.VERDANT_ROOTS);
        // If the block transformers were not gotten successfully, print an error and take no action.
        if (erode == null || erodeWet == null || roots == null) {
            Constants.LOG.error("Unable to get Block Transformers in SpreadingRootsBlock. Please report this error to the developer, along with a list of the mods and data packs you are using.");
            Constants.LOG.error("Level class is {}", level.getClass());
            Constants.LOG.error("This block is {}", this);
            Constants.LOG.error("Erode is {}", erode);
            Constants.LOG.error("Erode Wet is {}", erodeWet);
            Constants.LOG.error("Roots is {}", roots);

            return state;
        }

        // Now, update its activity and wetness.
        // But first, set up some variables that will be needed.
        boolean isActive = state.getValue(ACTIVE);
        int distance = MAX_DISTANCE;
        BlockPos.MutableBlockPos neighborPos = new BlockPos.MutableBlockPos();
        BlockState neighbor;
        boolean canBeActive = false;
        ServerLevelAccessor levelAccessor = null;
        if (level instanceof ServerLevelAccessor sla) {
            levelAccessor = sla;
        }

        // Checking every neighbor:
        for (Direction direction : Direction.values()) {
            // Get the neighboring block state in that direction.
            if (direction != dir) {
                // If we don't know what's in that direction, check it.
                neighborPos.setWithOffset(pos, direction);
                neighbor = level.getBlockState(neighborPos);
            } else {
                // If the direction is the one we already have the state for, use that instead.
                neighborPos.set(otherPos);
                neighbor = otherState;
            }

            // First, handle wetness.
            // Update the distance to be the minimum of the current lowest and the distance gotten from the neighbor
            // (plus one).
            distance = Math.min(distance, getDistanceAt(level, neighborPos, neighbor) + 1);
            // If the distance is the minimum possible, then we can break out of the loop.
            // It can't get better than it is now.
            if (distance == MIN_DISTANCE) {
                break;
            }

            // Second, check whether the neighbor can either be eroded or rooted.
            // Silently skip this part if it isn't a server level; it's probably being placed in a structure or worldgen or something.
            // It'll figure itself out sooner or later, when it starts eroding something once it's fully loaded into the world.
            boolean canBeErodedOrRooted = true;
            if (levelAccessor != null) {
                canBeErodedOrRooted = erode.isValidInput(levelAccessor, neighbor) || erodeWet.isValidInput(levelAccessor, neighbor) || roots.isValidInput(levelAccessor, neighbor);

            }
            boolean isFullBlock = neighbor.isCollisionShapeFullBlock(level, neighborPos);

            // If the block can be rooted, be eroded, or is not a full block, it should not prevent the roots from growing.
            canBeActive |= canBeErodedOrRooted || isFullBlock;
        }

        return state.setValue(WATER_DISTANCE, distance).setValue(ACTIVE, canBeActive);
    }

    // Checks if it can random tick. This significantly decreases lag!
    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(ACTIVE);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        // If it is ticking while inactive, there is a problem. Log an error and report back.
        if (!state.getValue(ACTIVE)) {
            Constants.LOG.error("SpreadingRootsBlock ticking while inactive. Please report this error to the developer of the mod.");
            return;
        }
        // If it is grassy and should not be, or it is not grassy but should be, swap.
        // Not-equal is being used as XOR here, to report a mismatch.
        if (this.isGrassy != this.canBeGrassy(state, level, pos)) {
            // Update the state, copying all applicable properties.
            state = BlockTransformer.copyProperties(state, this.alternateGrassy.get().get());
        }
        // Set the state
        level.setBlockAndUpdate(pos, state);
        // Erode or spread, and grow.
        this.grow(state, level, pos);
    }

    public void grow(BlockState state, ServerLevel level, BlockPos pos) {
        // System.out.println("Verdant Roots are calling the "grow" function.");

        boolean isWet = state.getValue(WATER_DISTANCE) < MAX_DISTANCE;
        for (int tries = 0; tries < 3; tries++) {
            // The range to check is constant.
            BlockPos posToTry = withinDist(pos, 2, level.random);
            // Try to convert the nearby block.
            if (this.erodeOrGrow(level, posToTry, isWet)) {
                // Try to grow vegetation.
                // This is a big source of lag, which I will cover separately.
                // FeaturePlacer.place(level, pos);
                break;
            }
        }
    }

    // Returns an offset block position. Works for distances less than 2^7. Behavior is undefined above that.
    public static BlockPos withinDist(BlockPos pos, int dist, RandomSource rand) {
        int num = rand.nextInt();
        int range = 2 * dist + 1;
        int int1 = num & 0xFF; // Use the lowest 7 bits
        int int2 = (num >> 7) & 0xFF; // Use the next 7 bits
        int int3 = (num >> 14) & 0xFF; // Use the next 7 bits after that

        int offset1 = (int1 * range) / 0xFF - dist;
        int offset2 = (int2 * range) / 0xFF - dist;
        int offset3 = (int3 * range) / 0xFF - dist;

        return pos.offset(offset1, offset2, offset3);
    }

    // Applies custom hoeing logic; I feel like this should be easier.
    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (level instanceof ServerLevel serverLevel) {
            if (stack.is(ItemTags.HOES)) {
                BlockState hoedTo = this.hoe(state, serverLevel, pos, stack);
                serverLevel.setBlockAndUpdate(pos, hoedTo);
                return ItemInteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    // Returns true if the block at the given position can be grassy.
    // For now, just checks if it has a non-liquid, non-full block above it.
    protected boolean canBeGrassy(BlockState state, Level level, BlockPos pos) {
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

    // Very important!
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATER_DISTANCE, ACTIVE);
    }
}
