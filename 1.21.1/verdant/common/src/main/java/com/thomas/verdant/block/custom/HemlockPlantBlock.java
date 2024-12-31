package com.thomas.verdant.block.custom;

import com.mojang.serialization.MapCodec;
import com.thomas.verdant.VerdantIFF;
import com.thomas.verdant.registry.BlockRegistry;
import com.thomas.verdant.registry.MobEffectRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.KelpPlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import java.util.function.Supplier;

public class HemlockPlantBlock extends KelpPlantBlock {
    public static final MapCodec<HemlockPlantBlock> CODEC = simpleCodec(HemlockPlantBlock::new);
    protected static final Supplier<MobEffectInstance> ASPHYXIATION = () -> new MobEffectInstance(
            MobEffectRegistry.ASPHYXIATING.asHolder(),
            100,
            0
    );

    public HemlockPlantBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        super.randomTick(state, level, pos, rand);
        this.tick(state, level, pos, rand);
    }

    // Inflicts poison on anything inside.
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && !VerdantIFF.isFriend(livingEntity)) {
            if (!level.isClientSide) {
                livingEntity.addEffect(ASPHYXIATION.get());
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) BlockRegistry.DROWNED_HEMLOCK.get();
    }
}