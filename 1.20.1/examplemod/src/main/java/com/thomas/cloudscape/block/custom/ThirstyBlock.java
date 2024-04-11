package com.thomas.cloudscape.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SpongeBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class ThirstyBlock extends SpongeBlock {

	public static final int MAX_DEPTH = 6;
	public static final int MAX_COUNT = 64;
	private static final Direction[] ALL_DIRECTIONS = Direction.values();

	protected Block toBecome = Blocks.AIR;

	// A specialized block that turns into a different block
	// after absorbing water like a sponge does.
	// Used for custom structures where water must be absorbed on generation.
	public ThirstyBlock(BlockBehaviour.Properties properties, Block block) {
		super(properties);
		this.toBecome = block;
	}

	// This function was copied from the SpongeBlock class, with the one change that
	// the block now becomes the specified custom block instead of a wet sponge
	protected void tryAbsorbWater(Level level, BlockPos pos) {
		if (this.removeWaterBreadthFirstSearch(level, pos)) {
			level.setBlock(pos, this.toBecome.defaultBlockState(), 2);
			level.levelEvent(2001, pos, Block.getId(Blocks.WATER.defaultBlockState()));
		}

	}

	// This was directly copied from the SpongeBlock class, with no changes.
	// Originally it was private, now it is protected.
	protected boolean removeWaterBreadthFirstSearch(Level p_56808_, BlockPos p_56809_) {
		BlockState spongeState = p_56808_.getBlockState(p_56809_);
		return BlockPos.breadthFirstTraversal(p_56809_, 6, 65, (p_277519_, p_277492_) -> {
			for (Direction direction : ALL_DIRECTIONS) {
				p_277492_.accept(p_277519_.relative(direction));
			}

		}, (p_279054_) -> {
			if (p_279054_.equals(p_56809_)) {
				return true;
			} else {
				BlockState blockstate = p_56808_.getBlockState(p_279054_);
				FluidState fluidstate = p_56808_.getFluidState(p_279054_);
				if (!spongeState.canBeHydrated(p_56808_, p_56809_, fluidstate, p_279054_)) {
					return false;
				} else {
					Block block = blockstate.getBlock();
					if (block instanceof BucketPickup) {
						BucketPickup bucketpickup = (BucketPickup) block;
						if (!bucketpickup.pickupBlock(p_56808_, p_279054_, blockstate).isEmpty()) {
							return true;
						}
					}

					if (blockstate.getBlock() instanceof LiquidBlock) {
						p_56808_.setBlock(p_279054_, Blocks.AIR.defaultBlockState(), 3);
					} else {
						if (!blockstate.is(Blocks.KELP) && !blockstate.is(Blocks.KELP_PLANT)
								&& !blockstate.is(Blocks.SEAGRASS) && !blockstate.is(Blocks.TALL_SEAGRASS)) {
							return false;
						}

						BlockEntity blockentity = blockstate.hasBlockEntity() ? p_56808_.getBlockEntity(p_279054_)
								: null;
						dropResources(blockstate, p_56808_, p_279054_, blockentity);
						p_56808_.setBlock(p_279054_, Blocks.AIR.defaultBlockState(), 3);
					}

					return true;
				}
			}
		}) > 1;
	}

}
