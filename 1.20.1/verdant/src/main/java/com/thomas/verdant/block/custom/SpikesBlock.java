package com.thomas.verdant.block.custom;

import com.thomas.verdant.damage.ModDamageSources;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SpikesBlock extends AmethystClusterBlock {

	private final float damage;

	public SpikesBlock(Properties properties, float damage) {
		super(5, 3, properties);
		this.damage = damage;
	}

	// Mostly from SweetBerryBushBlock, with a few tweaks.
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity livingEntity && (livingEntity.getMobType() != MobType.ARTHROPOD
				&& livingEntity.getType() != EntityType.RABBIT)) {
			entity.makeStuckInBlock(state, new Vec3((double) 0.8F, 0.75D, (double) 0.8F));
			if (!level.isClientSide && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
				double zMovement = Math.abs(entity.getX() - entity.xOld);
				double yMovement = Math.abs(entity.getY() - entity.yOld);
				double xMovement = Math.abs(entity.getZ() - entity.zOld);
				if (zMovement >= (double) 0.003F || xMovement >= (double) 0.003F || yMovement >= (double) 0.003F) {
					DamageSource source = ModDamageSources.get(level, ModDamageSources.THORN_BUSH);
					entity.hurt(source, damage);
				}
			}
		}
	}

	// Fire!
	@Override
	public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return !state.getValue(WATERLOGGED);
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return 20;
	}

}
