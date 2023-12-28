package com.thomas.zirconmod.block.custom;

import javax.annotation.Nullable;

import com.thomas.zirconmod.block.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;

public class ModFlammableRotatedPillarBlock extends RotatedPillarBlock {

	public ModFlammableRotatedPillarBlock(Properties properties) {
		super(properties);
	}
	
	
	@Override
	public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
	{
		return true;
	}
	
	@Override
	public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
	{
		return 7;
	}
	
	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
	{
		return 7;
	}
	
	
	@Override
	public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate)
	{
		if (context.getItemInHand().getItem() instanceof AxeItem)
		{
			if (state.is(ModBlocks.PALM_LOG.get()))
			{
				return ModBlocks.STRIPPED_PALM_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
			}
			else if (state.is(ModBlocks.PALM_WOOD.get()))
			{
				return ModBlocks.STRIPPED_PALM_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
			}
		}
		
		return super.getToolModifiedState(state, context, toolAction, simulate);
		
	}
}