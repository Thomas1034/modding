package com.thomas.quantumcircuit.block.custom;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public abstract class QuantumCircuitElement extends Block {

	public static final DirectionProperty Q_OUTPUT = DirectionProperty.create("output", Direction.Plane.HORIZONTAL);
	public static final DirectionProperty Q_INPUT = DirectionProperty.create("input", Direction.Plane.HORIZONTAL);

	public QuantumCircuitElement(Properties properties) {
		super(properties);
	}

	public static Direction getInputDirection(BlockState state) {
		return getInputDirection(state, null);
	}

	public static Direction getOutputDirection(BlockState state) {
		return getOutputDirection(state, null);
	}

	public static Direction getInputDirection(BlockState state, @Nullable Axis axis) {
		return state.getValue(Q_INPUT);
	}

	public static Direction getOutputDirection(BlockState state, @Nullable Axis axis) {
		return state.getValue(Q_OUTPUT);
	}

	public static BlockPos getInputPos(BlockPos pos, BlockState state, @Nullable Axis axis) {
		return pos.relative(getInputDirection(state, axis));
	}

	public static BlockPos getOutputPos(BlockPos pos, BlockState state, @Nullable Axis axis) {
		return pos.relative(getOutputDirection(state, axis));
	}

	// Checks if this is receiving valid input from the block behind it.
	// Axis is the axis that the input is being requested from.
	public static boolean canGetInput(Level level, BlockPos pos, BlockState state, @Nullable Axis axis) {

		BlockPos inputPos = getInputPos(pos, state, axis);
		BlockState inputState = level.getBlockState(pos);
		if (inputState.hasProperty(Q_OUTPUT)) {
			BlockPos inputOutputPos = getOutputPos(inputPos, inputState, axis);
			return inputOutputPos.equals(pos);
		} else {
			return false;
		}
	}

	public static boolean canGetInput(Level level, BlockPos pos, BlockState state) {
		return canGetInput(level, pos, state, null);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState otherState, boolean bool) {

		if (level instanceof ServerLevel sl) {
			// TODO
			// Update the circuit here.
		}

		super.onRemove(state, level, pos, otherState, bool);
	}

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
		stateBuilder.add(Q_OUTPUT).add(Q_INPUT);
	}

}
