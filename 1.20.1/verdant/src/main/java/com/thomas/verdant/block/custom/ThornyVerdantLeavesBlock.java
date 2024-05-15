package com.thomas.verdant.block.custom;

import com.thomas.verdant.damage.ModDamageSources;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ThornyVerdantLeavesBlock extends VerdantLeavesBlock {

	public ThornyVerdantLeavesBlock(Properties properties) {
		super(properties);
	}

	// Harm players who step on it.
	// Copied from ThornBushBlock.
	@Override
	public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
		if (entity instanceof LivingEntity le && entity.getType() != EntityType.FOX
				&& entity.getType() != EntityType.BEE && le.getMobType() != MobType.ARTHROPOD) {
			entity.makeStuckInBlock(state, new Vec3((double) 0.9F, 1.0D, (double) 0.9F));
			if (!level.isClientSide) {
				DamageSource source = ModDamageSources.get(level, ModDamageSources.THORN_BUSH);
				entity.hurt(source, 2.0F);
			}
		}
	}

}
