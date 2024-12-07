package com.thomas.verdant.block.custom;

import com.thomas.verdant.Constants;
import com.thomas.verdant.util.OptionalDirection;
import com.thomas.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class StranglerLeavesBlock extends GradientLeavesBlock {
    private static final double GROWING_RADIUS = 4.4;
    private static final VoxelShape SUPPORT_SHAPE = Shapes.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);

    public StranglerLeavesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(BlockStateProperties.WATERLOGGED, false).setValue(BlockStateProperties.PERSISTENT, false).setValue(GradientLeavesBlock.GRADIENT, OptionalDirection.EMPTY).setValue(GradientLeavesBlock.DISTANCE, GradientLeavesBlock.MAX_DISTANCE));
    }

    // Returns the number of blocks to move in that direction to find an air block.
    // Negative max values are ignored.
    public static int getDistanceTillAir(Level level, BlockPos initial, Direction direction, int max) {
        Predicate<BlockState> checker = BlockStateBase::isAir;
        return getDistanceTill(level, initial, direction, checker, max);
    }

    // Returns the number of blocks to move in that direction to find a non-leaf
    // block.
    // Negative max values are ignored.
    public static int getDistanceTillNonLeaf(Level level, BlockPos initial, Direction direction, int max) {
        Predicate<BlockState> checker = (state) -> !state.is(BlockTags.LEAVES);
        return getDistanceTill(level, initial, direction, checker, max);
    }

    public static int getDistanceTill(Level level, BlockPos initial, Direction direction, Predicate<BlockState> checker, int max) {

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos().set(initial);
        int distance = 0;
        while ((!checker.test(level.getBlockState(pos))) && (max < 0 || distance <= max)) {

            pos = pos.move(direction);
            distance++;
        }
        return distance;
    }


    // Check if the leaf is part of a valid shelf.

    // Returns the number of blocks to move in that direction to find a non-air
    // block.
    // Negative max values are ignored.
    public static int getDistanceTillBlock(Level level, BlockPos initial, Direction direction, int max) {
        Predicate<BlockState> checker = (state) -> !state.isAir();
        return getDistanceTill(level, initial, direction, checker, max);
    }

    private static boolean hasAirAbove(Level level, BlockPos pos, int distanceToCheck) {
        // Check for nearby blocks

        Predicate<BlockState> checker = (stateToCheck) -> !stateToCheck.isAir();
        int distance = getDistanceTill(level, pos, Direction.UP, checker, distanceToCheck + 1);

        return distance > distanceToCheck;

    }

    private boolean hasTransparentOrPlantSpaceBeneath(Level level, BlockPos pos, int distanceToCheck) {
        // Check for nearby blocks

        Predicate<BlockState> checkerForSolid = (stateToCheck) -> !(stateToCheck.isAir() || stateToCheck.propagatesSkylightDown() || stateToCheck.is(BlockTags.LEAVES) || stateToCheck.is(BlockTags.LOGS) || stateToCheck.is(VerdantTags.Blocks.STRANGLER_LOGS) || stateToCheck.is(VerdantTags.Blocks.STRANGLER_VINES) || stateToCheck.is(VerdantTags.Blocks.STRANGLER_LEAVES));
        int distance = getDistanceTill(level, pos, Direction.DOWN, checkerForSolid, distanceToCheck + 1);

        return distance > distanceToCheck;

    }

    @Override
    public @NotNull VoxelShape getBlockSupportShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return SUPPORT_SHAPE;
    }

    protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        // If it's decaying, do nothing.
        if (this.decaying(state)) {
            dropResources(state, level, pos);
            level.removeBlock(pos, false);
            return;
        }

        Constants.LOG.warn("Ticking at {}", pos);

        this.spreadLeaves(level, pos);

    }

    public void spreadLeaves(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        // System.out.println("spreadLeaves is being called by " + state + " at " + pos
        // + ".");

        // Check if within support distance.
        int distance = state.hasProperty(DISTANCE) ? state.getValue(DISTANCE) : MAX_DISTANCE;
        if (distance >= MAX_DISTANCE - 1) {
            return;
        }

        // Find the log supporting this leaf block.
        BlockPos supportingLog = this.gradientDescent(pos, level, state);
        // Find the distance to that log.
        // Vertical scale is more important.
        double distanceToLog = Vec3.atCenterOf(supportingLog).subtract(Vec3.atCenterOf(pos)).multiply(1, 2, 1).length();

        // Ensure that this block is within the growing radius.
        if ((distanceToLog < GROWING_RADIUS)) {
            //this.basicSpread(level, pos);
            tryToGrowShelf(level, pos, 2, 3);
            // tryToSpreadBasedOnDistanceFromLog(level, pos, 3, 5, distanceToLog);

        }
    }

    private void basicSpread(Level level, BlockPos pos) {
        for (Direction d : Direction.values()) {
            trySpreadLeafBlock(level, pos.relative(d));
        }
    }

    private void tryToGrowShelf(Level level, BlockPos pos, int maxShelfThickness, int minAirGap) {
        Constants.LOG.warn("Trying to grow shelf");
        int numberOfBlocksAbove = getDistanceTillAir(level, pos, Direction.UP, maxShelfThickness + 2);
        // Check the number of blocks till there's air, to compare to the shelf
        // thickness.
        if (numberOfBlocksAbove > maxShelfThickness) {
            // System.out.println("That's too many.");
            Constants.LOG.warn("Failing as there are {} blocks above (more than {})", numberOfBlocksAbove, maxShelfThickness);
            return;
        }

        int numberOfBlocksBelow = getDistanceTillAir(level, pos, Direction.DOWN, maxShelfThickness + 2);
        // Check the number of blocks till there's air, to compare to the shelf
        // thickness.
        if (numberOfBlocksBelow > maxShelfThickness) {
            // System.out.println("That's too many.");
            Constants.LOG.warn("Failing as there are {} blocks below (more than {})", numberOfBlocksBelow, maxShelfThickness);
            return;
        }

        int numberOfAirBlocksAboveThat = getDistanceTillBlock(level, pos.above(numberOfBlocksAbove), Direction.UP, minAirGap + 2);
        // Check the number of blocks till there's air, to compare to the shelf
        // thickness.
        if (numberOfAirBlocksAboveThat < minAirGap) {
            // System.out.println("That's too few.");
            Constants.LOG.warn("Failing as the air gap above is {} blocks (less than {})", numberOfAirBlocksAboveThat, minAirGap);
            return;
        }

        int numberOfAirBlocksBelowThat = getDistanceTillBlock(level, pos.below(numberOfBlocksBelow), Direction.DOWN, minAirGap + 2);
        // Check the number of blocks till there's air, to compare to the shelf
        // thickness.
        // System.out.println("There are " + numberOfAirBlocksBelowThat + " air blocks
        // above this block.");
        if (numberOfAirBlocksBelowThat < minAirGap) {
            // System.out.println("That's too few.");
            Constants.LOG.warn("Failing as the air gap below is {} blocks (less than {})", numberOfAirBlocksBelowThat, minAirGap);
            return;
        }

        boolean successfullyPlaced = false;
        for (Direction d : Direction.values()) {
            BlockPos neighbor = pos.relative(d);
            successfullyPlaced = successfullyPlaced || trySpreadLeafBlock(level, neighbor);
        }
        successfullyPlaced = successfullyPlaced || trySpreadLeafBlock(level, pos.above());
    }

    private void tryToSpreadBasedOnDistanceFromLog(Level level, BlockPos pos, int maxDistFromLog, int minDistFromGround, double calculatedDistanceToLog) {

        if (calculatedDistanceToLog <= maxDistFromLog) {

            // Ensure that this leaf has no more than two leaf blocks below it.
            int distanceTillNonLeaf = getDistanceTillNonLeaf(level, pos, Direction.DOWN, 4);
            if (!(distanceTillNonLeaf <= 2)) {
                return;
            }
            if (!hasTransparentOrPlantSpaceBeneath(level, pos.below(distanceTillNonLeaf), minDistFromGround)) {
                return;
            }
            if (hasAirAbove(level, pos.above(), 4)) {
                trySpreadLeafBlock(level, pos.above());
            }

            trySpreadLeafBlock(level, pos.north());
            trySpreadLeafBlock(level, pos.south());

            trySpreadLeafBlock(level, pos.east());
            trySpreadLeafBlock(level, pos.west());
        }
    }


    // Returns the number of blocks to move in a direction till the given condition
    // is satisfied. Negative max values are ignored.

    // Attempts to place a verdant leaf block there.
    // Returns true if it changed anything.
    public boolean trySpreadLeafBlock(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);

        // If it's already leaves, return early.
        if (state.is(VerdantTags.Blocks.STRANGLER_LEAVES)) {
            return false;
        }

        // If it's too close to the ground or to a non-leaf block, return early.
        int distanceToCheck = 2;
        for (int i = -distanceToCheck; i <= distanceToCheck; i++) {
            for (int j = -distanceToCheck; j <= distanceToCheck; j++) {
                for (int k = -distanceToCheck; k <= distanceToCheck; k++) {

                    // Don't check if the position is on the edge of the checked cube.
                    int numberOfSidesThisPositionIsOn = 0;

                    if (i == -distanceToCheck || i == distanceToCheck) {
                        numberOfSidesThisPositionIsOn++;
                    }

                    if (j == -distanceToCheck || j == distanceToCheck) {
                        numberOfSidesThisPositionIsOn++;
                    }

                    if (k == -distanceToCheck || k == distanceToCheck) {
                        numberOfSidesThisPositionIsOn++;
                    }

                    if (numberOfSidesThisPositionIsOn >= 2) {
                        continue;
                    }

                    BlockState stateToCheck = level.getBlockState(pos);
                    if (!stateToCheck.isAir() && !stateToCheck.is(BlockTags.REPLACEABLE) && !stateToCheck.is(VerdantTags.Blocks.STRANGLER_LOGS) && !stateToCheck.is(VerdantTags.Blocks.HEARTWOOD_LOGS) && !stateToCheck.is(VerdantTags.Blocks.STRANGLER_LEAVES) && !stateToCheck.is(BlockTags.LEAVES) && !stateToCheck.is(BlockTags.LOGS)) {
                        return false;
                    }
                }
            }
        }

        if (state.is(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES)) {
            BlockState placed = this.updateDistance(this.defaultBlockState(), level, pos);
            level.setBlockAndUpdate(pos, placed);
            return true;
        }

        return false;
    }


}
