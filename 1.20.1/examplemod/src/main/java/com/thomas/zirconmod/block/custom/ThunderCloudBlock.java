package com.thomas.zirconmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

public class ThunderCloudBlock extends CloudBlock {

	public ThunderCloudBlock(Properties properties) {
		super(properties);
	}

	// Damage all entities inside the block if it is thundering.
	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		entity.hurt(entity.damageSources().lightningBolt(), 3f);
	}

	// Damage all entities that step on the block
	public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
		if (!entity.isSteppingCarefully() && entity instanceof LivingEntity) {
			entity.hurt(entity.damageSources().lightningBolt(), 1.0F);
		}
		super.stepOn(level, pos, state, entity);
	}

	// Display lighting particles.
	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource source) {
		if (level.isThundering() && (long) source.nextInt(200) <= level.getGameTime() % 200L
				&& pos.getY() == level.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ()) - 1) {
			ParticleUtils.spawnParticlesAlongAxis(Axis.Y, level, pos, 0.625D, ParticleTypes.ELECTRIC_SPARK,
					UniformInt.of(1, 3));
		}
	}

}
