package com.thomas.cloudscape.block.custom;

import java.util.Optional;

import com.thomas.cloudscape.block.ModBlocks;
import com.thomas.cloudscape.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class LightningBlock extends Block {

	public LightningBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (level instanceof ServerLevel sl) {
			level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			// Summon lightning bolt if can see sky.
			LightningBlock.addLightningBoltAtChecked(sl, pos);
			entity.hurt(entity.damageSources().lightningBolt(), 7.0f);
		}
	}

	// Adds a lightning bolt to the specified server level.
	public static void addLightningBoltAt(ServerLevel level, BlockPos pos) {
		level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(level);
		if (lightningbolt != null) {
			
			lightningbolt.moveTo(Vec3.atBottomCenterOf(pos));
			level.addFreshEntity(lightningbolt);
			level.playSound(null, pos, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.BLOCKS, 1.0f, 1.0f);
		}
	}

	// Only adds lightning bolt if there is sky access.
	// Will also deflect to nearby lightning rod.
	public static void addLightningBoltAtChecked(ServerLevel level, BlockPos pos) {

		// First, check for a lightning rod nearby
		Optional<BlockPos> lightningRod = Utilities.findLightningRod(level, pos);
		
		if (lightningRod.isEmpty()) {
			if (level.canSeeSky(pos.above())) {
				addLightningBoltAt(level, pos);
			}
		}
		else {
			pos = lightningRod.get();
			BlockPos rodPos = pos.below();
			BlockState rodState = level.getBlockState(rodPos);
			// Trigger the lightning rod.
			((LightningRodBlock) Blocks.LIGHTNING_ROD).onLightningStrike(rodState, level, rodPos);
			// Add lightning
			addLightningBoltAt(level, pos);
			
		}
	}

	@Override
	public boolean propagatesSkylightDown(BlockState p_48740_, BlockGetter p_48741_, BlockPos p_48742_) {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean skipRendering(BlockState p_53972_, BlockState p_53973_, Direction p_53974_) {
		return p_53973_.is(this) ? true : super.skipRendering(p_53972_, p_53973_, p_53974_);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState otherState, boolean bool) {

		if (level instanceof ServerLevel sl && level.canSeeSky(pos.above())) {
			level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			LightningBlock.addLightningBoltAtChecked(sl, pos);
		}

		super.onRemove(state, level, pos, otherState, bool);
	}

	public static void place(Level level, BlockPos pos) {
		if (level.getBlockState(pos).isAir()) {
			level.setBlockAndUpdate(pos, ModBlocks.LIGHTNING_BLOCK.get().defaultBlockState());
		}
	}

	public static void placeCluster(Level level, BlockPos center, int size, RandomSource random) {
		if (!level.isClientSide) {
			// Creates lightning blocks
			// Place lightning blocks at and around that position.
			UnstableLightningBlock.place(level, center);
			int numToPlace = (int) (0.5 * size * size * size);
			for (int i = 0; i < numToPlace; i++) {
				int x = random.nextIntBetweenInclusive(-size, size);
				int y = random.nextIntBetweenInclusive(-size, size);
				int z = random.nextIntBetweenInclusive(-size, size);
				BlockPos offsetPos = center.offset(x, y, z);

				LightningBlock.place(level, offsetPos);
			}
		}
	}

}
