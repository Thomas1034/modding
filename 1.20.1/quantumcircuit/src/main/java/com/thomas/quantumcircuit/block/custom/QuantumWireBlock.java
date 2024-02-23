package com.thomas.quantumcircuit.block.custom;

import javax.annotation.Nullable;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

public class QuantumWireBlock extends QuantumCircuitElement {

	public QuantumWireBlock(Properties properties) {
		super(properties);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		if (context == null) {
			return this.defaultBlockState();
		}
		// Sets the wire to face in the direction the player is placing it in.
		return this.defaultBlockState().setValue(Q_INPUT, context.getHorizontalDirection().getOpposite()).setValue(Q_OUTPUT, context.getHorizontalDirection());
	}
}
