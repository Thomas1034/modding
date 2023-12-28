package com.thomas.zirconmod.block.custom;

import com.thomas.zirconmod.block.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class BuddingCitrineBlock extends BuddingAmethystBlock {
	public static final int GROWTH_CHANCE = 10;
	private static final Direction[] DIRECTIONS = Direction.values();

	public BuddingCitrineBlock(Properties properties) {
		super(properties);
	}

	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		if (rand.nextInt(GROWTH_CHANCE) == 0) {
			Direction direction = DIRECTIONS[rand.nextInt(DIRECTIONS.length)];
			BlockPos blockpos = pos.relative(direction);
			BlockState blockstate = level.getBlockState(blockpos);
			Block block = null;
			if (canClusterGrowAtState(blockstate)) {
				block = ModBlocks.SMALL_CITRINE_BUD.get();
			} else if (blockstate.is(ModBlocks.SMALL_CITRINE_BUD.get())
					&& blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
				block = ModBlocks.MEDIUM_CITRINE_BUD.get();
			} else if (blockstate.is(ModBlocks.MEDIUM_CITRINE_BUD.get())
					&& blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
				block = ModBlocks.LARGE_CITRINE_BUD.get();
			} else if (blockstate.is(ModBlocks.LARGE_CITRINE_BUD.get())
					&& blockstate.getValue(AmethystClusterBlock.FACING) == direction) {
				block = ModBlocks.CITRINE_CLUSTER.get();
			}

			if (block != null) {
				BlockState blockstate1 = block.defaultBlockState().setValue(AmethystClusterBlock.FACING, direction)
						.setValue(AmethystClusterBlock.WATERLOGGED,
								Boolean.valueOf(blockstate.getFluidState().getType() == Fluids.WATER));
				level.setBlockAndUpdate(blockpos, blockstate1);
			}

		}
	}

}
