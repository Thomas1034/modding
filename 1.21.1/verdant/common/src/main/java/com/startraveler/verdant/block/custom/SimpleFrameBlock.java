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


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SimpleFrameBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape ZN = Block.box(0, 0, 0, 16, 16, 1);
    private static final VoxelShape ZP = Block.box(0, 0, 15, 16, 16, 16);
    private static final VoxelShape YN = Block.box(0, 0, 0, 16, 1, 16);
    private static final VoxelShape YP = Block.box(0, 15, 0, 16, 16, 16);
    private static final VoxelShape XP = Block.box(15, 0, 0, 16, 16, 16);
    private static final VoxelShape XN = Block.box(0, 0, 0, 1, 16, 16);
    private static final VoxelShape ZN_THICK = Block.box(0, 0, 0, 16, 16, 4);
    private static final VoxelShape ZP_THICK = Block.box(0, 0, 12, 16, 16, 16);
    private static final VoxelShape YN_THICK = Block.box(0, 0, 0, 16, 4, 16);
    private static final VoxelShape YP_THICK = Block.box(0, 12, 0, 16, 16, 16);
    private static final VoxelShape XP_THICK = Block.box(12, 0, 0, 16, 16, 16);
    private static final VoxelShape XN_THICK = Block.box(0, 0, 0, 4, 16, 16);
    private static final VoxelShape X_SHAPE = Shapes.or(YP, YN, ZP, ZN);
    private static final VoxelShape Y_SHAPE = Shapes.or(XP, XN, ZP, ZN);
    private static final VoxelShape Z_SHAPE = Shapes.or(XP, XN, YP, YN);
    private static final VoxelShape X_SHAPE_THICK = Shapes.or(YP_THICK, YN_THICK, ZP_THICK, ZN_THICK);
    private static final VoxelShape Y_SHAPE_THICK = Shapes.or(XP_THICK, XN_THICK, ZP_THICK, ZN_THICK);
    private static final VoxelShape Z_SHAPE_THICK = Shapes.or(XP_THICK, XN_THICK, YP_THICK, YN_THICK);

    public SimpleFrameBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return super.getStateForPlacement(context).setValue(WATERLOGGED, flag);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }


    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    protected VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return switch (state.getValue(AXIS)) {
            case Z -> Z_SHAPE_THICK;
            case Y -> Y_SHAPE_THICK;
            default -> X_SHAPE_THICK;
        };
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {

        return switch (state.getValue(AXIS)) {
            case Z -> Z_SHAPE;
            case Y -> Y_SHAPE;
            default -> X_SHAPE;
        };
    }

}
