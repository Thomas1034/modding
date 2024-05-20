package com.thomas.verdant.block.custom;

import javax.annotation.Nullable;

import com.thomas.verdant.block.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;

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
	public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
	{
		return this.flammability > 0;
	}
	
	@Override
	public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
	{
		return this.flammability;
	}
	
	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction)
	{
		return this.fireSpreadSpeed;
	}
	
	
	@Override
	public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate)
	{
		if (context.getItemInHand().getItem() instanceof AxeItem)
		{
			if (state.is(ModBlocks.VERDANT_LOG.get()))
			{
				return ModBlocks.STRIPPED_VERDANT_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
			}
			else if (state.is(ModBlocks.VERDANT_WOOD.get()))
			{
				return ModBlocks.STRIPPED_VERDANT_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
			}
			else if (state.is(ModBlocks.VERDANT_HEARTWOOD_LOG.get()))
			{
				return ModBlocks.STRIPPED_VERDANT_HEARTWOOD_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
			}
			else if (state.is(ModBlocks.VERDANT_HEARTWOOD_WOOD.get()))
			{
				return ModBlocks.STRIPPED_VERDANT_HEARTWOOD_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
			}
		}
		
		return super.getToolModifiedState(state, context, toolAction, simulate);
		
	}
	
	public record ToolModificationContext(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
		
	}
	
}