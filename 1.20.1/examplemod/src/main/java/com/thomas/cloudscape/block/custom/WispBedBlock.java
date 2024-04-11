package com.thomas.cloudscape.block.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class WispBedBlock extends Block {

	public static final BooleanProperty IS_UNOCCUPIED = BooleanProperty.create("is_unoccupied");

	public WispBedBlock(Properties properties) {
		super(properties);
	}

	// Very important!
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(IS_UNOCCUPIED);
	}

}