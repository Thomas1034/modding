package com.thomas.quantumcircuit.util;

import com.thomas.quantumcircuit.block.custom.QuantumCircuitElement;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public abstract class QuantumCircuitHelper {
	
	public static BlockPos findCircuitStart(Level level, BlockPos pos) {
		
		BlockPos currPos = new BlockPos(pos);
		BlockState currState = level.getBlockState(currPos);
		BlockPos lastPos = new BlockPos(pos);
		while (currState.hasProperty(QuantumCircuitElement.Q_INPUT) && QuantumCircuitElement.canGetInput(level, currPos, currState)) {
			lastPos = currPos;
			currPos = QuantumCircuitElement.getInputPos(currPos, currState, currState.getValue(QuantumCircuitElement.Q_INPUT).getAxis());
			currState = level.getBlockState(currPos);
		}
		
		return lastPos;
	}
	
	public static void demo() {
		
		
	}
	
}
