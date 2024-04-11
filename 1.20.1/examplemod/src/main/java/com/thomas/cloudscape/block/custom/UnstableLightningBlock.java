package com.thomas.cloudscape.block.custom;

import com.thomas.cloudscape.block.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class UnstableLightningBlock extends LightningBlock {

	public UnstableLightningBlock(Properties properties) {
		super(properties);
	}

	// Randomly dissipates.
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
	}

	public static void place(Level level, BlockPos pos) {
		if (level.getBlockState(pos).isAir()) {
			level.setBlockAndUpdate(pos, ModBlocks.UNSTABLE_LIGHTNING_BLOCK.get().defaultBlockState());
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

				UnstableLightningBlock.place(level, offsetPos);
			}
		}
	}
}
