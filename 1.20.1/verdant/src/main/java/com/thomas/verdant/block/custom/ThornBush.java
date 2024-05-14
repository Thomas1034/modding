package com.thomas.verdant.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ThornBush extends Block {

	public ThornBush(Properties properties) {
		super(properties);

	}

	// Mostly from SweetBerryBushBlock
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE
				&& entity.getType() != EntityType.SPIDER && entity.getType() != EntityType.CAVE_SPIDER) {
			entity.makeStuckInBlock(state, new Vec3((double) 0.8F, 0.75D, (double) 0.8F));
			if (!level.isClientSide && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
				double zMovement = Math.abs(entity.getX() - entity.xOld);
				double xMovement = Math.abs(entity.getZ() - entity.zOld);
				if (zMovement >= (double) 0.003F || xMovement >= (double) 0.003F) {
					entity.hurt(level.damageSources().sweetBerryBush(), 1.0F);
				}
			}

		}
	}

}
