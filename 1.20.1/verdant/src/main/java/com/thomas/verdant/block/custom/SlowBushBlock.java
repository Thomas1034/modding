package com.thomas.verdant.block.custom;

import com.thomas.verdant.overgrowth.EntityOvergrowthEffects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SlowBushBlock extends BushBlock {

	private final float x;
	private final float y;
	private final float z;

	public SlowBushBlock(Properties properties, float x, float y, float z) {
		super(properties);
		this.x = x;
		this.y = y;
		this.z = z;
	}

	// Mostly from SweetBerryBushBlock, with a few tweaks.
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity livingEntity && (livingEntity.getMobType() != MobType.ARTHROPOD
				&& livingEntity.getType() != EntityType.RABBIT && !EntityOvergrowthEffects.isFriend(livingEntity))) {
			entity.makeStuckInBlock(state, new Vec3(x, y, z));
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
