package com.thomas.verdant.block.custom;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class PoisonVerdantLeavesBlock extends VerdantLeavesBlock {

	protected static final Supplier<MobEffectInstance> POISON = () -> new MobEffectInstance(MobEffects.POISON, 200, 0);

	public PoisonVerdantLeavesBlock(Properties properties) {
		super(properties);
	}

	// Poison players who step on it.
	// Copied from PoisonVerdantTendrilBlock.
	@Override
	public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
		if (entity instanceof LivingEntity livingEntity && livingEntity.getType() != EntityType.FOX
				&& livingEntity.getMobType() != MobType.ARTHROPOD) {
			//livingEntity.makeStuckInBlock(state, new Vec3((double) 0.9F, 0.95D, (double) 0.9F));
			if (!level.isClientSide) {
				livingEntity.addEffect(POISON.get());
			}
		}
	}

}
