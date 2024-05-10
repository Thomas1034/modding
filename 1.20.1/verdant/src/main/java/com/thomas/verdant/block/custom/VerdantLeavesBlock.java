package com.thomas.verdant.block.custom;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.growth.VerdantGrower;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class VerdantLeavesBlock extends LeavesBlock implements VerdantGrower {

	public VerdantLeavesBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		super.randomTick(state, level, pos, rand);
		//System.out.println("Verdant leaves are ticking at " + pos + ".");
		this.grow(state, level, pos);
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return true;
	}

	@Override
	public void grow(BlockState state, Level level, BlockPos pos) {
		//System.out.println("Verdant leaves are attempting to grow at " + pos + ".");
		// See if it can grow into leafy vines.
		if (VerdantVineBlock.canGrowToAnyFace(level, pos)) {
			System.out.println("Converting to leafy vines.");
			// Grow to leafy vines.
			// Place the vine block there.
			BlockState placed = ModBlocks.LEAFY_VERDANT_VINE.get().defaultBlockState();
			// Store the previous block there.
			BlockState replaced = level.getBlockState(pos);

			// Find every direction it can grow there.
			for (Direction d : Direction.values()) {
				if (VerdantVineBlock.canGrowToFace(level, pos, d)) {
					placed = placed.setValue(VerdantVineBlock.SIDES.get(d), 1);
				}
			}
			// Waterlog if possible
			if (replaced.hasProperty(BlockStateProperties.WATERLOGGED)) {
				if (replaced.getValue(BlockStateProperties.WATERLOGGED)) {
					placed = placed.setValue(WATERLOGGED, true);
				}
			}
			// Update the block.
			level.destroyBlock(pos, true);
			level.setBlockAndUpdate(pos, placed);
		}

		// Try to convert nearby leaves.
		// Find a place to grow within three tries. Can use further tries to spread even
		// more.
		for (int tries = 0; tries < 3; tries++) {
			// The range to check is constant.
			BlockPos posToTry = VerdantGrower.withinDist(pos, 3, level.random);
			// If it converted successfully, break.
			if (VerdantGrower.convertLeaves(level, posToTry)) {
				// System.out.println("Successfully grew.");
				// break;
			}
		}
	}

}
