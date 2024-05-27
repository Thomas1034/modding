package com.thomas.verdant.block.custom;

import com.thomas.verdant.damage.ModDamageSources;
import com.thomas.verdant.infection.EntityInfectionEffects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ThornBushBlock extends BushBlock {

	public ThornBushBlock(Properties properties) {
		super(properties);
	}

	// Mostly from SweetBerryBushBlock, with a few tweaks.
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity livingEntity && (livingEntity.getMobType() != MobType.ARTHROPOD
				&& livingEntity.getType() != EntityType.RABBIT && !EntityInfectionEffects.isFriend(livingEntity))) {
			entity.makeStuckInBlock(state, new Vec3((double) 0.8F, 0.75D, (double) 0.8F));
			if (!level.isClientSide && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
				double zMovement = Math.abs(entity.getX() - entity.xOld);
				double xMovement = Math.abs(entity.getZ() - entity.zOld);
				if (zMovement >= (double) 0.003F || xMovement >= (double) 0.003F) {
					DamageSource source = ModDamageSources.get(level, ModDamageSources.THORN_BUSH);
					entity.hurt(source, 2.0F);
				}
			}
		}
	}

	// Fire!
	@Override
	public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return true;
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return 20;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return 40;
	}

}
