package com.thomas.verdant.block.custom;

import com.thomas.verdant.damage.ModDamageSources;
import com.thomas.verdant.overgrowth.EntityOvergrowthEffects;
import com.thomas.verdant.util.ModTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ThornBushBlock extends BushBlock {

	private final float damage;

	public ThornBushBlock(Properties properties, float damage) {
		super(properties);
		this.damage = damage;
	}

	// Mostly from SweetBerryBushBlock, with a few tweaks.
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity livingEntity && (livingEntity.getMobType() != MobType.ARTHROPOD
				&& livingEntity.getType() != EntityType.RABBIT && !EntityOvergrowthEffects.isFriend(livingEntity))) {
			double slowdownFactor = 0.2d;
			if (livingEntity.getItemBySlot(EquipmentSlot.FEET).is(ModTags.Items.VERDANT_FRIENDLY_ARMORS)) {
				slowdownFactor -= 0.1d;
			}
			if (livingEntity.getItemBySlot(EquipmentSlot.LEGS).is(ModTags.Items.VERDANT_FRIENDLY_ARMORS)) {
				slowdownFactor -= 0.1d;
			}

			slowdownFactor = 1 - slowdownFactor;

			if (slowdownFactor < 0.999) {
				entity.makeStuckInBlock(state, new Vec3(slowdownFactor, slowdownFactor, slowdownFactor));
			}
			if (!level.isClientSide
					&& (entity.xOld != entity.getX() || entity.yOld != entity.getY() || entity.zOld != entity.getZ())) {
				double zMovement = Math.abs(entity.getX() - entity.xOld);
				double yMovement = Math.abs(entity.getY() - entity.yOld);
				double xMovement = Math.abs(entity.getZ() - entity.zOld);
				int cumulativeDamage = 0;
				if (yMovement >= (double) 0.003F) {
					cumulativeDamage += this.damage;
				}
				if (zMovement >= (double) 0.003F || xMovement >= (double) 0.003F) {
					cumulativeDamage += this.damage / 2;
				}
				if (cumulativeDamage >= 0) {
					DamageSource source = ModDamageSources.get(level, ModDamageSources.THORN_BUSH);
					entity.hurt(source, cumulativeDamage);
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
