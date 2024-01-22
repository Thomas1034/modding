package com.thomas.zirconmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class LightningBlock extends Block {

	public LightningBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (level instanceof ServerLevel) {
			level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			// Summon lightning bolt if can see sky.
			if (level.canSeeSky(pos.above())) {
				LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(level);
				if (lightningbolt != null) {
					lightningbolt.moveTo(Vec3.atBottomCenterOf(pos));
					level.addFreshEntity(lightningbolt);
					level.playSound(null, pos, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.BLOCKS, 1.0f, 1.0f);
				}
				entity.hurt(entity.damageSources().lightningBolt(), 10.0f);
			}
			else {
				entity.hurt(entity.damageSources().lightningBolt(), 7.0f);
			}
		}
	}

	public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
		return true;
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState otherState, boolean bool) {

		if (level instanceof ServerLevel && level.canSeeSky(pos.above())) {
			level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(level);
			if (lightningbolt != null) {
				lightningbolt.moveTo(Vec3.atBottomCenterOf(pos));
				level.addFreshEntity(lightningbolt);
				level.playSound(null, pos, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.BLOCKS, 1.0f, 1.0f);
			}
		}

		super.onRemove(state, level, pos, otherState, bool);

	}

}
