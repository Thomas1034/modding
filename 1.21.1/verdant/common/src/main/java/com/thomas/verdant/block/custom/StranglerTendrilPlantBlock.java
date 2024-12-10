package com.thomas.verdant.block.custom;

import com.mojang.serialization.MapCodec;
import com.thomas.verdant.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StranglerTendrilPlantBlock extends GrowingPlantBodyBlock {
    protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    public static final MapCodec<StranglerTendrilPlantBlock> CODEC = simpleCodec(StranglerTendrilPlantBlock::new);

    public StranglerTendrilPlantBlock(Properties properties) {
        this(properties, SHAPE);
    }

    public StranglerTendrilPlantBlock(Properties properties, VoxelShape shape) {
        super(properties, Direction.DOWN, shape, true);
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) BlockRegistry.STRANGLER_TENDRIL.get();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(pos);
        return super.getShape(state, level, pos, context).move(offset);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        super.randomTick(state, level, pos, rand);
        this.tick(state, level, pos, rand);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
    }

    @Override
    protected MapCodec<? extends GrowingPlantBodyBlock> codec() {
        return CODEC;
    }
}
