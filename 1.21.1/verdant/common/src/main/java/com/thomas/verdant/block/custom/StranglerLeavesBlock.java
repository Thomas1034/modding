package com.thomas.verdant.block.custom;

import com.thomas.verdant.registry.BlockRegistry;
import com.thomas.verdant.util.OptionalDirection;
import com.thomas.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Predicate;

public class StranglerLeavesBlock extends GradientLeavesBlock {
    private static final double GROWING_RADIUS = 4.4;
    private static final VoxelShape SUPPORT_SHAPE = Shapes.block();
    public static int MIN_AGE = 0;
    public static int MAX_AGE = 2;
    public static IntegerProperty AGE = IntegerProperty.create("age", MIN_AGE, MAX_AGE);
    private final Function<RandomSource, StranglerLeavesBlock> leaves = (random) -> {
        float chance = random.nextFloat();
        if (chance < 0.1) {
            return (StranglerLeavesBlock) BlockRegistry.THORNY_STRANGLER_LEAVES.get();
        } else if (chance < 0.2) {
            return (StranglerLeavesBlock) BlockRegistry.POISON_STRANGLER_LEAVES.get();
        } else {
            return (StranglerLeavesBlock) BlockRegistry.STRANGLER_LEAVES.get();
        }
    };

    public StranglerLeavesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(BlockStateProperties.WATERLOGGED, false).setValue(BlockStateProperties.PERSISTENT, false).setValue(GradientLeavesBlock.GRADIENT, OptionalDirection.EMPTY).setValue(GradientLeavesBlock.DISTANCE, GradientLeavesBlock.MAX_DISTANCE));
    }

    // Returns the number of blocks to move in that direction to find an air block.
    // Negative max values are ignored.
    public static int getDistanceTillAir(Level level, BlockPos initial, Direction direction, int max) {
        Predicate<BlockState> checker = ((state) -> state.isAir() || state.is(VerdantTags.Blocks.STRANGLER_VINES));
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
        LeafyStranglerVineBlock leafyVine = (LeafyStranglerVineBlock) BlockRegistry.LEAFY_STRANGLER_VINE.get();
        // Find every direction it can grow there.
        BlockState newVine = leafyVine.defaultBlockState();
        boolean canPlace = false;
        for (Direction d : Direction.allShuffled(level.random)) {
            // Place it there.
            if (leafyVine.canGrowToFace(level, pos, d)) {
                canPlace = true;
                newVine = newVine.setValue(LeafyStranglerVineBlock.PROPERTY_FOR_FACE.get(d), 1);
            }
        }
        if (canPlace) {
            state = newVine;
            level.setBlockAndUpdate(pos, state);
        }

        // Find the log supporting this leaf block.
        BlockPos supportingLog = this.gradientDescent(pos, level, state);
        // Find the distance to that log.
        // Vertical scale is more important.
        double distanceToLog = supportingLog != null ? Vec3.atCenterOf(supportingLog).subtract(Vec3.atCenterOf(pos)).multiply(1, 2, 1).length() : Double.MAX_VALUE;

        // Ensure that this block is within the growing radius.
        boolean grewShelf = false;
        if ((distanceToLog < GROWING_RADIUS)) {
            //this.basicSpread(level, pos);
            grewShelf = tryToGrowShelf(level, pos, 2, 3);
            // tryToSpreadBasedOnDistanceFromLog(level, pos, 3, 5, distanceToLog);
        }
        if (!grewShelf) {

            int age = state.getValueOrElse(AGE, MIN_AGE);
            if (age == MAX_AGE) {
                if (distanceToLog > 2.5) {
                    this.tryToGrowVine(level, pos.below());
                }
            } else if (state.hasProperty(AGE)) {
                state = state.setValue(AGE, age + 1);
                level.setBlockAndUpdate(pos, state);
            }
        }
    }

    private void tryToGrowVine(Level level, BlockPos below) {
        BlockState state = level.getBlockState(below);
        if (!state.is(BlockTags.REPLACEABLE) || state.is(VerdantTags.Blocks.TENDRILS)) {
            return;
        }
        // Now set the block. There's a chance it places a tendril instead.
        float chance = level.random.nextFloat();
        BlockState newState = Blocks.VINE.defaultBlockState().setValue(VineBlock.UP, true);
        if (chance < 0.1) {
            newState = BlockRegistry.STRANGLER_TENDRIL.get().defaultBlockState();
        } else if (chance < 0.2) {
            newState = BlockRegistry.POISON_IVY.get().defaultBlockState();
        }
        level.setBlockAndUpdate(below, newState);
    }

    private boolean tryToGrowShelf(Level level, BlockPos pos, int maxShelfThickness, int minAirGap) {

        int numberOfBlocksAbove = getDistanceTillAir(level, pos, Direction.UP, maxShelfThickness + 2);
        // Check the number of blocks till there's air, to compare to the shelf
        // thickness.
        if (numberOfBlocksAbove > maxShelfThickness) {
            // System.out.println("That's too many.");
            return false;
        }

        int numberOfBlocksBelow = getDistanceTillAir(level, pos, Direction.DOWN, maxShelfThickness + 2);
        // Check the number of blocks till there's air, to compare to the shelf
        // thickness.
        if (numberOfBlocksBelow > maxShelfThickness) {
            // System.out.println("That's too many.");
            return false;
        }

        int numberOfAirBlocksAboveThat = getDistanceTillBlock(level, pos.above(numberOfBlocksAbove), Direction.UP, minAirGap + 2);
        // Check the number of blocks till there's air, to compare to the shelf
        // thickness.
        if (numberOfAirBlocksAboveThat < minAirGap) {
            // System.out.println("That's too few.");
            return false;
        }

        int numberOfAirBlocksBelowThat = getDistanceTillBlock(level, pos.below(numberOfBlocksBelow), Direction.DOWN, minAirGap + 2);
        // Check the number of blocks till there's air, to compare to the shelf
        // thickness.
        // System.out.println("There are " + numberOfAirBlocksBelowThat + " air blocks
        // above this block.");
        if (numberOfAirBlocksBelowThat < minAirGap) {
            // System.out.println("That's too few.");
            return false;
        }

        boolean successfullyPlaced = false;
        for (Direction d : Direction.values()) {
            BlockPos neighbor = pos.relative(d);
            successfullyPlaced = successfullyPlaced || trySpreadLeafBlock(level, neighbor);
        }
        successfullyPlaced = successfullyPlaced || trySpreadLeafBlock(level, pos.above());
        return successfullyPlaced;
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
                    if (!stateToCheck.isAir() && !stateToCheck.is(BlockTags.REPLACEABLE) && !stateToCheck.is(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES) && !stateToCheck.is(VerdantTags.Blocks.STRANGLER_LOGS) && !stateToCheck.is(VerdantTags.Blocks.HEARTWOOD_LOGS) && !stateToCheck.is(VerdantTags.Blocks.STRANGLER_LEAVES) && !stateToCheck.is(BlockTags.LEAVES) && !stateToCheck.is(BlockTags.LOGS)) {
                        return false;
                    }
                }
            }
        }

        if (state.is(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES)) {
            BlockState placed = this.updateDistance(this.leaves.apply(level.random).defaultBlockState(), level, pos);
            level.setBlockAndUpdate(pos, placed);
            return true;
        }

        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AGE);
    }
}
