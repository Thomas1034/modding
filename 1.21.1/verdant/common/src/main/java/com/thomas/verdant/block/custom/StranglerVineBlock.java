package com.thomas.verdant.block.custom;

import com.thomas.verdant.Constants;
import com.thomas.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class StranglerVineBlock extends Block implements SimpleWaterloggedBlock {

    public static final int MIN_AGE = 0;
    public static final int MAX_AGE = 3;
    public static final IntegerProperty EAST = IntegerProperty.create("east", MIN_AGE, MAX_AGE);
    public static final IntegerProperty WEST = IntegerProperty.create("west", MIN_AGE, MAX_AGE);
    public static final IntegerProperty UP = IntegerProperty.create("up", MIN_AGE, MAX_AGE);
    public static final IntegerProperty DOWN = IntegerProperty.create("down", MIN_AGE, MAX_AGE);
    public static final IntegerProperty SOUTH = IntegerProperty.create("south", MIN_AGE, MAX_AGE);
    public static final IntegerProperty NORTH = IntegerProperty.create("north", MIN_AGE, MAX_AGE);
    public static final IntegerProperty[] FACES = new IntegerProperty[]{EAST, WEST, UP, DOWN, SOUTH, NORTH};
    public static final Map<Direction, IntegerProperty> PROPERTY_FOR_FACE = Map.of(Direction.EAST, EAST, Direction.WEST, WEST, Direction.UP, UP, Direction.DOWN, DOWN, Direction.SOUTH, SOUTH, Direction.NORTH, NORTH);

    public static final List<VoxelShape> UP_SHAPE = List.of(Shapes.empty(), Block.box(0.0f, 15.0f, 0.0f, 16.0f, 16.0f, 16.0f), Block.box(0.0f, 12.0f, 0.0f, 16.0f, 16.0f, 16.0f), Block.box(0.0f, 8.0f, 0.0f, 16.0f, 16.0f, 16.0f));

    public static final List<VoxelShape> DOWN_SHAPE = List.of(Shapes.empty(), Block.box(0.0f, 0.0f, 0.0f, 16.0f, 1.0f, 16.0f), Block.box(0.0f, 0.0f, 0.0f, 16.0f, 4.0f, 16.0f), Block.box(0.0f, 0.0f, 0.0f, 16.0f, 8.0f, 16.0f));

    public static final List<VoxelShape> NORTH_SHAPE = List.of(Shapes.empty(), Block.box(0.0f, 0.0f, 0.0f, 16.0f, 16.0f, 1.0f), Block.box(0.0f, 0.0f, 0.0f, 16.0f, 16.0f, 4.0f), Block.box(0.0f, 0.0f, 0.0f, 16.0f, 16.0f, 8.0f));

    public static final List<VoxelShape> SOUTH_SHAPE = List.of(Shapes.empty(), Block.box(0.0f, 0.0f, 15.0f, 16.0f, 16.0f, 16.0f), Block.box(0.0f, 0.0f, 12.0f, 16.0f, 16.0f, 16.0f), Block.box(0.0f, 0.0f, 8.0f, 16.0f, 16.0f, 16.0f));

    public static final List<VoxelShape> WEST_SHAPE = List.of(Shapes.empty(), Block.box(0.0f, 0.0f, 0.0f, 1.0f, 16.0f, 16.0f), Block.box(0.0f, 0.0f, 0.0f, 4.0f, 16.0f, 16.0f), Block.box(0.0f, 0.0f, 0.0f, 8.0f, 16.0f, 16.0f));

    public static final List<VoxelShape> EAST_SHAPE = List.of(Shapes.empty(), Block.box(15.0f, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f), Block.box(12.0f, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f), Block.box(8.0f, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f));

    private static final Map<BlockState, VoxelShape> CACHED_SHAPES = new HashMap<>();

    private final Function<RandomSource, BlockState> log = (rand) -> Blocks.DIAMOND_BLOCK.defaultBlockState();

    private final Supplier<BlockState> leafyVine = this::defaultBlockState;

    private final Function<RandomSource, Block> rottenWood = (rand) -> Blocks.COAL_BLOCK;

    private final Function<RandomSource, Block> heartwood = (rand) -> Blocks.DEEPSLATE_EMERALD_ORE;

    public StranglerVineBlock(Properties properties) {
        super(properties);
    }

    // Spreads the vine to a nearby block.
    public void spread(Level level, BlockPos pos) {

        ArrayList<BlockPos> validSites = getGrowthSites(level, pos, 1);

        // Check if any valid sites were found.
        if (!validSites.isEmpty()) {

            // Pick a random location from the list.
            BlockPos site = validSites.get(level.random.nextInt(validSites.size()));

            placeVine(level, site);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        this.grow(state, level, pos);
    }

    // Finds all valid growth sites in a 2n+1 cube.
    public ArrayList<BlockPos> getGrowthSites(Level level, BlockPos pos, int n) {
        // System.out.println("Finding growth sites.");
        // Check every location in a 2n+1 cube. If it can spread there, add it to a
        // list.
        ArrayList<BlockPos> validSites = new ArrayList<>();
        for (int i = -n; i <= n; i++) {
            for (int j = -n; j <= n; j++) {
                for (int k = -n; k <= n; k++) {
                    // Get the proper position to check.
                    BlockPos here = pos.offset(i, j, k);
                    // Get the block state at that position.
                    BlockState hereBlockState = level.getBlockState(here);
                    // Check if it is replaceable.
                    if (!hereBlockState.is(VerdantTags.Blocks.VERDANT_VINE_REPLACEABLES)) {
                        continue;
                    }
                    // Get the fluid state at that position.
                    FluidState hereFluidState = level.getFluidState(here);
                    if (!hereFluidState.is(Fluids.WATER) && !hereFluidState.isEmpty()) {
                        continue;
                    }

                    for (Direction d : Direction.values()) {
                        if (canGrowToFace(level, here, d)) {
                            validSites.add(here);
                        }
                    }
                }
            }
        }

        return validSites;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        // Try to get the cached shape.
        VoxelShape shape = CACHED_SHAPES.get(state);
        if (shape == null) {
            // Empty shape.
            shape = Shapes.empty();
            // Merge in the shapes based on the state.
            shape = Shapes.or(shape, UP_SHAPE.get(state.getValue(UP)));
            shape = Shapes.or(shape, DOWN_SHAPE.get(state.getValue(DOWN)));
            shape = Shapes.or(shape, NORTH_SHAPE.get(state.getValue(NORTH)));
            shape = Shapes.or(shape, SOUTH_SHAPE.get(state.getValue(SOUTH)));
            shape = Shapes.or(shape, WEST_SHAPE.get(state.getValue(WEST)));
            shape = Shapes.or(shape, EAST_SHAPE.get(state.getValue(EAST)));
            // shape = shape.optimize();
            CACHED_SHAPES.put(state, shape);
        }
        return shape;
    }

    public boolean canGrowToFace(Level level, BlockPos pos, Direction direction) {
        BlockState state = level.getBlockState(pos.relative(direction));
        if (!state.is(BlockTags.LOGS)) {
            return false;
        }
        return state.isFaceSturdy(level, pos, direction.getOpposite());
    }

    // Updates the block whenever there is a change next to it.
    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource random) {

        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            tickAccess.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        IntegerProperty propertyForDirection = PROPERTY_FOR_FACE.get(facing);

        if (state.getValue(propertyForDirection) > MIN_AGE && !state.is(VerdantTags.Blocks.SUPPORTS_STRANGLER_VINES)) {
            state = state.setValue(propertyForDirection, MIN_AGE);
        }

        boolean hasAnyFace = false;
        for (int i = 0; i < FACES.length; i++) {
            if (state.getValue(FACES[i]) > MIN_AGE) {
                hasAnyFace = true;
                break;
            }
        }
        if (!hasAnyFace) {
            // TODO
            // Don't hardcode air as the default?
            state = Blocks.AIR.defaultBlockState();
        }

        return state;
    }

    // Returns true if a block has mature verdant log neighbors.
    private boolean hasMatureVerdantLogNeighbors(Level level, BlockPos pos) {

        // If it has neighbors both above and below, then return false. It can still
        // grow to connect the two.
        BlockState above = level.getBlockState(pos.above());
        BlockState below = level.getBlockState(pos.below());
        if (above.is(VerdantTags.Blocks.MATURE_STRANGLER_LOGS) && below.is(VerdantTags.Blocks.MATURE_STRANGLER_LOGS)) {
            // System.out.println("There was heartwood both above and below the block.");
            return false;
        }

        for (Direction d : Direction.values()) {
            // System.out.println("Checking the " + d + " side for verdant heartwood.");
            BlockPos neighborPos = pos.relative(d);
            BlockState neighbor = level.getBlockState(neighborPos);

            if (neighbor.is(VerdantTags.Blocks.MATURE_STRANGLER_LOGS)) {
                // System.out.println("Found verdant heartwood on the " + d + " side.");
                return true;
            }
        }
        // System.out.println("Did not find verdant heartwood.");
        return false;
    }

    // Returns true if a block has any log neighbors.
    private boolean hasLogNeighbors(Level level, BlockPos pos) {

        for (Direction d : Direction.values()) {
            BlockState state = level.getBlockState(pos.relative(d));
            if (state.is(BlockTags.LOGS) || state.is(VerdantTags.Blocks.MATURE_STRANGLER_LOGS) || state.is(VerdantTags.Blocks.STRANGLER_LOGS) || state.is(this.rottenWood.apply(level.random))) {
                return true;
            }
        }
        // System.out.println("Did not find any logs.");
        return false;
    }


    // Tries to consume the neighboring log.
    // Returns true if it succeeds.
    private boolean tryConsumeLog(Level level, BlockPos pos) {

        Constants.LOG.warn("Trying to consume a log.");

        BlockState host = level.getBlockState(pos);
        boolean shouldDecayToAir = false;

        // First, check if the host is a log.
        if (!host.is(BlockTags.LOGS_THAT_BURN)) {
            Constants.LOG.warn("Host is non-inflammable.");
            return false;
        }

        // Then, check if this log is a mature verdant log.
        if (host.is(VerdantTags.Blocks.MATURE_STRANGLER_LOGS)) {
            Constants.LOG.warn("Host is a mature strangler log.");
            return false;
        }

        // Then, check if this log is a verdant log and has a mature neighbor.
        // If so, return early.
        if (host.is(VerdantTags.Blocks.STRANGLER_LOGS) && this.hasMatureVerdantLogNeighbors(level, pos)) {
            Constants.LOG.warn("Host is a strangler log with mature neighbors.");
            return false;
        }

        // Check if this log has neighboring logs or decayed wood.
        if (!host.is(VerdantTags.Blocks.STRANGLER_LOGS) && !this.hasLogNeighbors(level, pos)) {
            Constants.LOG.warn("Host is not a strangler log and does not have log neighbors; decaying to air.");
            shouldDecayToAir = true;
        }

        boolean canConsume = true;
        // Check each of the sides. Keep track of the ones that need to be grown.
        ArrayList<BlockPos> positionsToGrow = new ArrayList<>();
        for (Direction d : Direction.values()) {

            BlockPos neighborPos = pos.relative(d);
            BlockState neighbor = level.getBlockState(neighborPos);
            // If the side is a fully grown vine, it's good to proceed after growing it to a
            // log.
            if (neighbor.is(VerdantTags.Blocks.STRANGLER_VINES)) {

                // If the vines are mature, keep checking.
                if (neighbor.getValue(PROPERTY_FOR_FACE.get(d.getOpposite())) == MAX_AGE) {
                    positionsToGrow.add(neighborPos);
                }
                // If not, do not proceed.
                else {
                    Constants.LOG.warn("Host has an immature strangler vine to its {}", d);
                    canConsume = false;
                    break;
                }
            } else if (neighbor.is(VerdantTags.Blocks.VERDANT_VINE_REPLACEABLES) || neighbor.is(BlockTags.LEAVES)) {
                Constants.LOG.warn("Host has {} strangler vine to its {}, which is either leaves or replaceable.", neighbor, d);
                canConsume = false;
                break;
            }
        }
        // If it can consume the log, does so.
        if (canConsume && !shouldDecayToAir) {

            Constants.LOG.warn("Consuming the host.");
            for (BlockPos toGrow : positionsToGrow) {
                Constants.LOG.warn("Growing vines into logs at {}", toGrow);
                level.setBlockAndUpdate(toGrow, this.log.apply(level.random));
            }

            // Add particle and sound effect.
            level.addDestroyBlockEffect(pos, host);
            // Save the host state.
            // If the host is verdant, mature it.
            if (host.is(VerdantTags.Blocks.STRANGLER_LOGS)) {
                Constants.LOG.warn("Maturing the host.");
                level.setBlockAndUpdate(pos, this.heartwood.apply(level.random).defaultBlockState());
            }
            // Otherwise rot it.
            else {
                Constants.LOG.warn("Rotting the host.");
                level.setBlockAndUpdate(pos, this.rottenWood.apply(level.random).defaultBlockState());
            }
        } else if (shouldDecayToAir) {
            Constants.LOG.warn("Decaying the host to air.");
            level.destroyBlock(pos, false);

            // Add particle and sound effect.
            level.destroyBlock(pos, false);
        }

        return canConsume;
    }

    public void grow(BlockState state, Level level, BlockPos pos) {

        // Spread to nearby blocks.
        spread(level, pos);

        // Attempt to grow.
        boolean isMature = this.growInPlace(level, pos);

        // If it is mature, try to consume the log.
        boolean hasConsumed = false;
        if (isMature) {
            // Consume the log in every adjacent direction.
            for (Direction d : Direction.values()) {
                if (state.getValue(PROPERTY_FOR_FACE.get(d)) == MAX_AGE) {
                    hasConsumed |= this.tryConsumeLog(level, pos.relative(d));
                }
            }
        }
    }

    // Grows the current block if possible.
    // Returns true if it has grown to the maximum for the current environment.
    private boolean growInPlace(Level level, BlockPos pos) {

        boolean grownIntoLog = false;

        BlockState state = level.getBlockState(pos);
        // Check if the state is indeed a vine.
        if (!(state.getBlock() instanceof StranglerVineBlock)) {
            return false;
        }

        boolean isMature = true;
        for (Direction d : Direction.values()) {
            // Save the growth level in this direction.
            int maturity = state.getValue(PROPERTY_FOR_FACE.get(d));
            int oppositeMaturity = state.getValue(PROPERTY_FOR_FACE.get(d.getOpposite()));
            // Check if it can grow
            if (maturity == MIN_AGE && canGrowToFace(level, pos, d)) {
                isMature = false;
                state = state.setValue(PROPERTY_FOR_FACE.get(d), MIN_AGE + 1);
            } else if (maturity > MIN_AGE && maturity < MAX_AGE) {
                isMature = false;
                state = state.setValue(PROPERTY_FOR_FACE.get(d), maturity + 1);

            } else if (maturity == MAX_AGE && oppositeMaturity == MAX_AGE) {
                // If it's fully grown on both sides, grow to a log.
                state = this.log.apply(level.random).trySetValue(RotatedPillarBlock.AXIS, d.getAxis());
                grownIntoLog = true;
                break;
            }
        }
        if (!isMature || grownIntoLog) {
            level.addDestroyBlockEffect(pos, state);
            level.setBlockAndUpdate(pos, state);
        }

        return isMature;
    }

    // Places a Verdant Vine at that block.
    public void placeVine(Level level, BlockPos pos) {
        // Store the previous block there.
        BlockState replaced = level.getBlockState(pos);
        // Place the vine block there. Leafy if it is replacing leaves.
        BlockState placed = replaced.is(BlockTags.LEAVES) ? this.leafyVine.get() : this.defaultBlockState();

        // Find every direction it can grow there.
        for (Direction d : Direction.allShuffled(level.random)) {
            // Place it there.
            if (canGrowToFace(level, pos, d)) {
                placed = placed.setValue(PROPERTY_FOR_FACE.get(d), 1);
            }
        }
        // Water-log if possible
        placed = placed.setValue(BlockStateProperties.WATERLOGGED, replaced.getOptionalValue(BlockStateProperties.WATERLOGGED).orElse(false));

        // Update the block.
        level.setBlockAndUpdate(pos, placed);
    }

    // Very important!
    // Defines the properties for the block.
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(EAST, WEST, UP, DOWN, SOUTH, NORTH, BlockStateProperties.WATERLOGGED);
    }
}