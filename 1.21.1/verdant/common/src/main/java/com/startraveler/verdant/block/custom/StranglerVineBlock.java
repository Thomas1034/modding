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
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.registry.WoodSets;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
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

// TODO handle logic when placed by the player.
public class StranglerVineBlock extends Block implements SimpleWaterloggedBlock, BonemealableBlock {

    public static final int MIN_AGE = 0;
    public static final int MAX_AGE = 3;
    public static final IntegerProperty EAST = IntegerProperty.create("east", MIN_AGE, MAX_AGE);
    public static final IntegerProperty WEST = IntegerProperty.create("west", MIN_AGE, MAX_AGE);
    public static final IntegerProperty UP = IntegerProperty.create("up", MIN_AGE, MAX_AGE);
    public static final IntegerProperty DOWN = IntegerProperty.create("down", MIN_AGE, MAX_AGE);
    public static final IntegerProperty SOUTH = IntegerProperty.create("south", MIN_AGE, MAX_AGE);
    public static final IntegerProperty NORTH = IntegerProperty.create("north", MIN_AGE, MAX_AGE);
    public static final IntegerProperty[] FACES = new IntegerProperty[]{EAST, WEST, UP, DOWN, SOUTH, NORTH};
    public static final Map<Direction, IntegerProperty> PROPERTY_FOR_FACE = Map.of(
            Direction.EAST,
            EAST,
            Direction.WEST,
            WEST,
            Direction.UP,
            UP,
            Direction.DOWN,
            DOWN,
            Direction.SOUTH,
            SOUTH,
            Direction.NORTH,
            NORTH
    );
    public static final List<VoxelShape> UP_SHAPE = List.of(
            Shapes.empty(),
            Block.box(0.0f, 15.0f, 0.0f, 16.0f, 16.0f, 16.0f),
            Block.box(0.0f, 12.0f, 0.0f, 16.0f, 16.0f, 16.0f),
            Block.box(0.0f, 8.0f, 0.0f, 16.0f, 16.0f, 16.0f)
    );
    public static final List<VoxelShape> DOWN_SHAPE = List.of(
            Shapes.empty(),
            Block.box(0.0f, 0.0f, 0.0f, 16.0f, 1.0f, 16.0f),
            Block.box(0.0f, 0.0f, 0.0f, 16.0f, 4.0f, 16.0f),
            Block.box(0.0f, 0.0f, 0.0f, 16.0f, 8.0f, 16.0f)
    );
    public static final List<VoxelShape> NORTH_SHAPE = List.of(
            Shapes.empty(),
            Block.box(0.0f, 0.0f, 0.0f, 16.0f, 16.0f, 1.0f),
            Block.box(0.0f, 0.0f, 0.0f, 16.0f, 16.0f, 4.0f),
            Block.box(0.0f, 0.0f, 0.0f, 16.0f, 16.0f, 8.0f)
    );
    public static final List<VoxelShape> SOUTH_SHAPE = List.of(
            Shapes.empty(),
            Block.box(0.0f, 0.0f, 15.0f, 16.0f, 16.0f, 16.0f),
            Block.box(0.0f, 0.0f, 12.0f, 16.0f, 16.0f, 16.0f),
            Block.box(0.0f, 0.0f, 8.0f, 16.0f, 16.0f, 16.0f)
    );
    public static final List<VoxelShape> WEST_SHAPE = List.of(
            Shapes.empty(),
            Block.box(0.0f, 0.0f, 0.0f, 1.0f, 16.0f, 16.0f),
            Block.box(0.0f, 0.0f, 0.0f, 4.0f, 16.0f, 16.0f),
            Block.box(0.0f, 0.0f, 0.0f, 8.0f, 16.0f, 16.0f)
    );
    public static final List<VoxelShape> EAST_SHAPE = List.of(
            Shapes.empty(),
            Block.box(15.0f, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f),
            Block.box(12.0f, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f),
            Block.box(8.0f, 0.0f, 0.0f, 16.0f, 16.0f, 16.0f)
    );
    public static final int IMBUED_HEARTWOOD_CHANCE = 128;
    private static final Map<BlockState, VoxelShape> CACHED_SHAPES = new HashMap<>();
    protected final double leafGrowthRadius = 2.9;
    protected final boolean[][][] leafPattern;
    private final Function<RandomSource, Block> log = (rand) -> WoodSets.STRANGLER.getLog().get();

    private final Function<RandomSource, Block> rottenWood = (rand) -> BlockRegistry.ROTTEN_WOOD.get();

    private final Function<RandomSource, Block> heartwood = (rand) -> rand.nextInt(IMBUED_HEARTWOOD_CHANCE) == 0 ? BlockRegistry.IMBUED_HEARTWOOD_LOG.get() : WoodSets.HEARTWOOD.getLog()
            .get();

    private final Function<RandomSource, Block> leaves = (random) -> {
        float chance = random.nextFloat();
        if (chance < 0.1) {
            return BlockRegistry.THORNY_STRANGLER_LEAVES.get();
        } else if (chance < 0.2) {
            return BlockRegistry.POISON_STRANGLER_LEAVES.get();
        } else {
            return BlockRegistry.STRANGLER_LEAVES.get();
        }
    };

    public StranglerVineBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(BlockStateProperties.WATERLOGGED, false));

        int center = (int) (Math.ceil(this.leafGrowthRadius));
        int arraySize = (2 * center) + 1;
        this.leafPattern = new boolean[arraySize][arraySize][arraySize];

        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                for (int k = 0; k < arraySize; k++) {
                    int distanceSquared = (i - center) * (i - center) + 3 * (j - center) * (j - center) + (k - center) * (k - center);
                    if (j >= (center) && distanceSquared < (this.leafGrowthRadius * this.leafGrowthRadius)) {
                        this.leafPattern[i][j][k] = true;
                    }
                }
            }
        }

        ((FireBlock) Blocks.FIRE).setFlammable(this, 60, 20);
    }

    public static boolean canGrowToFace(Level level, BlockPos pos, Direction direction) {
        BlockState state = level.getBlockState(pos.relative(direction));
        if (!canSupportStranglerVine(state)) {
            return false;
        }
        return state.isFaceSturdy(level, pos, direction.getOpposite());
    }

    protected static boolean canSupportStranglerVine(BlockState state) {
        return (!state.is(VerdantTags.Blocks.DOES_NOT_SUPPORT_STRANGLER_VINES)) && (state.is(VerdantTags.Blocks.SUPPORTS_STRANGLER_VINES));
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
                    if (!hereBlockState.is(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES)) {
                        continue;
                    }
                    // Ensure it is not already a vine.
                    if (hereBlockState.is(VerdantTags.Blocks.STRANGLER_VINES)) {
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

    // Updates the block whenever there is a change next to it.
    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos currentPos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource random) {

        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            tickAccess.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        IntegerProperty propertyForDirection = PROPERTY_FOR_FACE.get(facing);

        if (state.getValue(propertyForDirection) > MIN_AGE && !canSupportStranglerVine(state)) {
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
            state = state.getValue(BlockStateProperties.WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
        }

        return state;
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

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        this.grow(state, level, pos);
    }

    // Returns true if a block has mature verdant log neighbors.
    private boolean hasMatureVerdantLogNeighbors(Level level, BlockPos pos) {

        // If it has neighbors both above and below, then return false. It can still
        // grow to connect the two.
        BlockState above = level.getBlockState(pos.above());
        BlockState below = level.getBlockState(pos.below());
        if (above.is(VerdantTags.Blocks.HEARTWOOD_LOGS) && below.is(VerdantTags.Blocks.HEARTWOOD_LOGS)) {
            // System.out.println("There was heartwood both above and below the block.");
            return false;
        }

        for (Direction d : Direction.values()) {
            // System.out.println("Checking the " + d + " side for verdant heartwood.");
            BlockPos neighborPos = pos.relative(d);
            BlockState neighbor = level.getBlockState(neighborPos);

            if (neighbor.is(VerdantTags.Blocks.HEARTWOOD_LOGS)) {
                // System.out.println("Found verdant heartwood on the " + d + " side.");
                return true;
            }
        }
        // System.out.println("Did not find verdant heartwood.");
        return false;
    }

    // Returns true if a block has any log neighbors.
    private boolean hasLogOrVineNeighbors(Level level, BlockPos pos) {

        for (Direction d : Direction.values()) {
            int dValue = d.getAxis().getNegative().get3DDataValue();
            BlockState state = level.getBlockState(pos.relative(d));
            if (canSupportStranglerVine(state) || (state.is(VerdantTags.Blocks.STRANGLER_VINES) && (state.getValue(
                    PROPERTY_FOR_FACE.get(Direction.from3DDataValue(dValue + 2))) > MIN_AGE || state.getValue(
                    PROPERTY_FOR_FACE.get(Direction.from3DDataValue(dValue + 3))) > MIN_AGE || state.getValue(
                    PROPERTY_FOR_FACE.get(Direction.from3DDataValue(dValue + 4))) > MIN_AGE || state.getValue(
                    PROPERTY_FOR_FACE.get(Direction.from3DDataValue(dValue + 5))) > MIN_AGE)) || state.is(VerdantTags.Blocks.ROTTEN_WOOD)) {
                return true;
            }
        }
        // System.out.println("Did not find any logs.");
        return false;
    }

    // Tries to consume the neighboring log.
    // Returns true if it succeeds.
    private boolean tryConsumeLog(Level level, BlockPos pos) {


        BlockState host = level.getBlockState(pos);
        boolean shouldDecayToAir = false;

        // First, check if the host is a log.
        if (!canSupportStranglerVine(host)) {
            return false;
        }

        // Then, check if this log is a mature verdant log.
        if (host.is(VerdantTags.Blocks.HEARTWOOD_LOGS)) {
            return false;
        }

        // Then, check if this log is a verdant log and has a mature neighbor.
        // If so, return early.
        if (host.is(VerdantTags.Blocks.STRANGLER_LOGS) && this.hasMatureVerdantLogNeighbors(level, pos)) {
            return false;
        }

        // Check if this log has neighboring logs or decayed wood.
        if (!host.is(VerdantTags.Blocks.STRANGLER_LOGS) && !this.hasLogOrVineNeighbors(level, pos)) {
            shouldDecayToAir = true;
        }

        boolean canConsume = true;
        int allowedOccupiedDistantNeighbors = 2;
        // Check each of the sides. Keep track of the ones that need to be grown.
        ArrayList<BlockPos> positionsToGrow = new ArrayList<>();
        for (Direction d : Direction.values()) {

            BlockPos neighborPos = pos.relative(d);
            BlockState neighbor = level.getBlockState(neighborPos);
            BlockState distantNeighbor = level.getBlockState(neighborPos.relative(d));
            // If the side is a fully grown vine, it's good to proceed after growing it to a
            // log.
            if (neighbor.is(VerdantTags.Blocks.STRANGLER_VINES) && !distantNeighbor.is(VerdantTags.Blocks.STRANGLER_VINES)) {

                // If the vines are mature, keep checking.
                if (neighbor.getValue(PROPERTY_FOR_FACE.get(d.getOpposite())) == MAX_AGE) {
                    if (!(distantNeighbor.is(BlockTags.REPLACEABLE_BY_TREES) || d.getAxis() == Direction.Axis.Y)) {
                        allowedOccupiedDistantNeighbors--;
                    }
                    if (allowedOccupiedDistantNeighbors <= 0) {
                        canConsume = false;
                        break;
                    }
                    positionsToGrow.add(neighborPos);
                }
                // If not, do not proceed.
                else {
                    canConsume = false;
                    break;
                }
            } else if (neighbor.is(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES)) {
                canConsume = false;
                break;
            }
        }
        // If it can consume the log, does so.
        if (canConsume && !shouldDecayToAir) {

            for (BlockPos toGrow : positionsToGrow) {
                level.setBlockAndUpdate(toGrow, this.log.apply(level.random).defaultBlockState());
            }

            // Add particle and sound effect.
            level.addDestroyBlockEffect(pos, host);
            // Save the host state.
            // If the host is verdant, mature it.
            if (host.is(VerdantTags.Blocks.STRANGLER_LOGS)) {
                level.setBlockAndUpdate(pos, this.heartwood.apply(level.random).defaultBlockState());
            }
            // Otherwise rot it.
            else {
                level.setBlockAndUpdate(pos, this.rottenWood.apply(level.random).defaultBlockState());
            }
        } else if (shouldDecayToAir) {
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
                state = this.log.apply(level.random)
                        .defaultBlockState()
                        .trySetValue(RotatedPillarBlock.AXIS, d.getAxis());
                grownIntoLog = true;
                break;
            }
        }
        if (!isMature || grownIntoLog) {
            level.setBlockAndUpdate(pos, state);
        } else if (isMature && !grownIntoLog && state.getValue(DOWN) == MAX_AGE) {
            if (!(state.getBlock() instanceof LeafyStranglerVineBlock)) {
                level.setBlockAndUpdate(
                        pos,
                        BlockTransformer.copyProperties(state, BlockRegistry.LEAFY_STRANGLER_VINE.get())
                );
                // TODO Temporary I hope hope hope
                // Pending leaf rework.
                boolean clearAbove = true;
                for (int i = 1; i < 4; i++) {
                    if (!level.getBlockState(pos.above()).isAir()) {
                        clearAbove = false;
                        break;
                    }
                }
                if (clearAbove) {
                    this.growLeafCluster(level, pos);
                }
            }
        }

        return isMature;
    }

    private void growLeafCluster(Level level, BlockPos pos) {

        // Iterate over the leaf pattern and place them.
        int center = (int) (Math.ceil(this.leafGrowthRadius));
        int arraySize = (2 * center) + 1;

        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                for (int k = 0; k < arraySize; k++) {
                    if (this.leafPattern[i][j][k]) {

                        BlockPos localPos = pos.offset(i - center, j - center, k - center);

                        BlockState localState = level.getBlockState(localPos);

                        /*
                        if (state.getBlock() instanceof StranglerVineBlock && !this.canGrowToFace(level, localPos, Direction.DOWN)) {
                            level.setBlockAndUpdate(localPos, BlockTransformer.copyProperties(state, BlockRegistry.LEAFY_STRANGLER_VINE.get()));
                        } else
                            */
                        if ((localState.is(BlockTags.REPLACEABLE) || localState.is(VerdantTags.Blocks.STRANGLER_VINE_REPLACEABLES)) && !localState.is(
                                VerdantTags.Blocks.STRANGLER_VINES)) {
                            Block leaves = this.leaves.apply(level.random);
                            level.setBlockAndUpdate(localPos, leaves.defaultBlockState());
                            level.scheduleTick(localPos, leaves, 1);
                        }
                    }
                }
            }
        }
    }

    // Places a Verdant Vine at that block.
    public void placeVine(Level level, BlockPos pos) {
        level.setBlockAndUpdate(pos, this.getStateForPlacement(level, pos));

    }

    // Places a Verdant Vine at that block.
    public BlockState getStateForPlacement(Level level, BlockPos pos) {
        // Store the previous block there.
        BlockState replaced = level.getBlockState(pos);
        // Place the vine block there. Leafy if it is replacing leaves.
        BlockState placed = BlockRegistry.STRANGLER_VINE.get().defaultBlockState();

        // Find every direction it can grow there.
        boolean canGrowToAnyFace = false;
        for (Direction d : Direction.values()) {
            // Place it there.
            if (canGrowToFace(level, pos, d)) {
                placed = placed.setValue(PROPERTY_FOR_FACE.get(d), 1);
                canGrowToAnyFace = true;
            }
        }
        // Water-log if possible
        placed = placed.setValue(
                BlockStateProperties.WATERLOGGED,
                replaced.getOptionalValue(BlockStateProperties.WATERLOGGED).orElse(false)
        );

        // Update the block.
        return canGrowToAnyFace ? placed : null;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.getStateForPlacement(context.getLevel(), context.getClickedPos());
    }

    // Very important!
    // Defines the properties for the block.
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(EAST, WEST, UP, DOWN, SOUTH, NORTH, BlockStateProperties.WATERLOGGED);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        this.grow(state, level, pos);
    }

}

