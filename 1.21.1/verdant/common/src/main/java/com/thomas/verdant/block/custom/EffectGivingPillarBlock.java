package com.thomas.verdant.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class EffectGivingPillarBlock extends RotatedPillarBlock {
    protected final Supplier<MobEffectInstance> effect;

    public EffectGivingPillarBlock(Properties properties, Supplier<MobEffectInstance> effect) {
        super(properties);
        this.effect = effect;
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            if (!level.isClientSide) {
                livingEntity.addEffect(this.effect.get());
            }
        }
    }
}
