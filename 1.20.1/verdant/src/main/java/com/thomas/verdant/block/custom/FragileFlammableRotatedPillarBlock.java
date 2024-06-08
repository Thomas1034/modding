package com.thomas.verdant.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FragileFlammableRotatedPillarBlock extends ModFlammableRotatedPillarBlock {

	private final float fallDistanceForBreak;

	public FragileFlammableRotatedPillarBlock(Properties properties, float fallDistanceForBreak) {
		super(properties);
		this.fallDistanceForBreak = fallDistanceForBreak;
	}

	public FragileFlammableRotatedPillarBlock(Properties properties, int flammability, int fireSpreadSpeed,
			float fallDistanceForBreak) {
		super(properties);
		this.fallDistanceForBreak = fallDistanceForBreak;
	}

	@Override
	public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float f) {
		// Break the block if the fall distance has been exceeded.
		if (entity.fallDistance > fallDistanceForBreak && !level.isClientSide) {
			level.addDestroyBlockEffect(pos, level.getBlockState(pos));
			level.destroyBlock(pos, false);
		}
		// Caused reduced fall damage.
		entity.causeFallDamage(f, 0.5F, entity.damageSources().fall());
	}

}
