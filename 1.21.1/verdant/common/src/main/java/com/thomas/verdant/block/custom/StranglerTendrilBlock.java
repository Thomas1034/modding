package com.thomas.verdant.block.custom;

import com.mojang.serialization.MapCodec;
import com.thomas.verdant.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StranglerTendrilBlock extends GrowingPlantHeadBlock {
    public static final Integer CUSTOM_MAX_AGE = 12;
    protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    public static final MapCodec<StranglerTendrilBlock> CODEC = simpleCodec(StranglerTendrilBlock::new);
    protected int maxAge;

    public StranglerTendrilBlock(Properties properties) {
        this(properties, SHAPE);
    }

    public StranglerTendrilBlock(Properties properties, VoxelShape shape) {
        super(properties, Direction.DOWN, SHAPE, false, 1.0f);
        maxAge = 12;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(pos);
        return super.getShape(state, level, pos, context).move(offset);
    }

    @Override
    protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < this.maxAge;
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource rand) {
        return rand.nextInt(3) + 1;
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return state.getFluidState().isEmpty()
                && (state.isAir() || state.is(BlockTags.REPLACEABLE));
    }
    
    @Override
    protected Block getBodyBlock() {
        return BlockRegistry.STRANGLER_TENDRIL_PLANT.get();
    }
}
