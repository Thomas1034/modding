package com.thomas.verdant.block.custom;

import com.mojang.serialization.MapCodec;
import com.thomas.verdant.VerdantIFF;
import com.thomas.verdant.registry.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class PoisonIvyPlantBlock extends StranglerTendrilPlantBlock {
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    public static final MapCodec<PoisonIvyPlantBlock> CODEC = simpleCodec(PoisonIvyPlantBlock::new);
    protected static final Supplier<MobEffectInstance> POISON = () -> new MobEffectInstance(MobEffects.POISON, 100, 0);

    public PoisonIvyPlantBlock(Properties properties) {
        super(properties, SHAPE);
    }

    // Inflicts poison on anything inside.
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && !VerdantIFF.isFriend(livingEntity)) {
            if (!level.isClientSide) {
                livingEntity.addEffect(POISON.get());
            }
        }
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) BlockRegistry.POISON_IVY.get();
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