package com.thomas.verdant.block.custom;

import java.util.function.Function;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class HoeRemovableItemBlock extends Block {

	private final Function<UseOnContext, ItemStack> itemProvider;
	private final Function<UseOnContext, BlockState> stateProvider;

	// For cassava roots.
	// They'll grow under the plant, if it's in dirt.
	// You'll need to collect the plant and hoe the ground underneath it again
	// to collect the roots.
	public HoeRemovableItemBlock(Properties properties, Function<UseOnContext, ItemStack> itemProvider,
			Function<UseOnContext, BlockState> stateProvider) {
		super(properties);
		this.itemProvider = itemProvider;
		this.stateProvider = stateProvider;
	}

	@Nullable
	public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction,
			boolean simulate) {

		if (toolAction == ToolActions.HOE_TILL) {
			Block.popResourceFromFace(context.getLevel(), context.getClickedPos(), context.getClickedFace(),
					this.itemProvider.apply(context));
			return this.stateProvider.apply(context);
		}

		return super.getToolModifiedState(state, context, toolAction, simulate);
	}

}
