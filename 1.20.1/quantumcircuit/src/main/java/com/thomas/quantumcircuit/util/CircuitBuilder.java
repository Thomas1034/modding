package com.thomas.quantumcircuit.util;

import com.thomas.quantumcircuit.block.custom.QuantumCircuitElement;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.Level;

public class CircuitBuilder {

	// Parses blocks into a quantum circuit.

	// Finds the beginning of a circuit with one end at pos
	public static BlockPos findDirectBeginPos(Level level, BlockPos pos) {

		Axis traveling = null;
		while (true) {
			traveling = QuantumCircuitElement.getInputDirection(level.getBlockState(pos), traveling).getAxis();
			BlockPos next = QuantumCircuitElement.getInputPos(pos, level.getBlockState(pos), traveling);
			if (!level.getBlockState(next).hasProperty(QuantumCircuitElement.Q_INPUT)) {
				break;
			}
			pos = next;
		}
		return pos;
	}

	// Follows the given quantum wire starting at pos to its end.
	// Assume that the first block only has one output.
	public static BlockPos findDirectEndPos(Level level, BlockPos pos) {
		Axis traveling = null;
		while (true) {
			traveling = QuantumCircuitElement.getOutputDirection(level.getBlockState(pos), traveling).getAxis();
			BlockPos next = QuantumCircuitElement.getOutputPos(pos, level.getBlockState(pos), traveling);
			if (!level.getBlockState(next).hasProperty(QuantumCircuitElement.Q_OUTPUT)) {
				break;
			}
			pos = next;
		}
		return pos;
	}
	
	public static BlockPos findBranchingBeginPos(Level level, BlockPos pos) {

		Axis traveling = null;
		while (true) {
			traveling = QuantumCircuitElement.getInputDirection(level.getBlockState(pos), traveling).getAxis();
			BlockPos next = QuantumCircuitElement.getInputPos(pos, level.getBlockState(pos), traveling);
			if (!level.getBlockState(next).hasProperty(QuantumCircuitElement.Q_INPUT)) {
				break;
			}
			pos = next;
		}
		return pos;
	}

}
