package com.thomas.verdant.block.custom;

import javax.annotation.Nullable;

import com.thomas.verdant.growth.VerdantBlockTransformer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class ModFlammableRotatedPillarBlock extends RotatedPillarBlock {

	private int flammability = 7;
	private int fireSpreadSpeed = 7;

	public ModFlammableRotatedPillarBlock(Properties properties) {
		super(properties);
	}

	public ModFlammableRotatedPillarBlock(Properties properties, int flammability, int fireSpreadSpeed) {
		super(properties);
		this.flammability = flammability;
		this.fireSpreadSpeed = fireSpreadSpeed;
	}

	@Override
	public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return this.flammability > 0;
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return this.flammability;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return this.fireSpreadSpeed;
	}

	@Override
	public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction,
			boolean simulate) {
		if (toolAction == ToolActions.AXE_STRIP) {
			if (VerdantBlockTransformer.STRIPPING.get().hasInput(state.getBlock())) {
				return VerdantBlockTransformer.STRIPPING.get().next(state);
			}
		}

		return super.getToolModifiedState(state, context, toolAction, simulate);

	}

	public record ToolModificationContext(BlockState state, UseOnContext context, ToolAction toolAction,
			boolean simulate) {

	}

}