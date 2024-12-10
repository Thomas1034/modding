package com.thomas.verdant.block.custom;

import com.thomas.verdant.VerdantIFF;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class PoisonStranglerLeavesBlock extends StranglerLeavesBlock {

    protected static final Supplier<MobEffectInstance> POISON = () -> new MobEffectInstance(MobEffects.POISON, 100, 0);

    public PoisonStranglerLeavesBlock(Properties properties) {
        super(properties);
    }

    // Poison players who step on it.
    // Copied from PoisonVerdantTendrilBlock.
    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && !VerdantIFF.isFriend(livingEntity)) {
            if (!level.isClientSide) {
                livingEntity.addEffect(POISON.get());
            }
        }
    }
}
