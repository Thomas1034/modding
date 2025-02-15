package com.startraveler.verdant.block.custom;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FrameBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {

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
    private static final double ENTITY_AREA_CUTOFF = 0.25 * 0.75 * 0.75;
    private static final double LENIENT_ENTITY_AREA_CUTOFF = 1.5 * 1.5 * ENTITY_AREA_CUTOFF;
    private static final double SHORT_ENTITY_HEIGHT_CUTOFF = Math.sqrt(2);

    public FrameBlock(Properties properties) {
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

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        boolean isSmallEntity = context instanceof EntityCollisionContext entityContext && entityContext.getEntity() instanceof Entity entity && entity.getBoundingBox() instanceof AABB box && ((box.getXsize() * box.getZsize() < ((entity instanceof LivingEntity livingEntity && livingEntity.isBaby()) || (entity instanceof Projectile) ? LENIENT_ENTITY_AREA_CUTOFF : ENTITY_AREA_CUTOFF)) || (box.getYsize() < SHORT_ENTITY_HEIGHT_CUTOFF));
        return this.hasCollision && !isSmallEntity ? state.getShape(level, pos) : Shapes.empty();
    }

}