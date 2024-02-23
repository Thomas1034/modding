package com.thomas.quantumcircuit.block.custom;

import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

public class QuantumCornerBlock extends QuantumCircuitElement {

	public QuantumCornerBlock(Properties properties) {
		super(properties);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		if (context == null) {
			return this.defaultBlockState();
		}
		// Sets the wire to face in the direction the player is placing it in.
		// Turns it based on whether the player is crouching.
		boolean isSneaking = context.getPlayer().isShiftKeyDown();
		Direction output = isSneaking ? context.getHorizontalDirection().getClockWise() : context.getHorizontalDirection().getCounterClockWise();
		return this.defaultBlockState().setValue(Q_INPUT, context.getHorizontalDirection().getOpposite()).setValue(Q_OUTPUT, output);
	}
}
