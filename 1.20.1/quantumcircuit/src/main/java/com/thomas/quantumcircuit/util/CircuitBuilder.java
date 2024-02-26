package com.thomas.quantumcircuit.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class CircuitBuilder {

	// Parses blocks into a quantum circuit.
	public static Stack<BlockPos> findBeginnings(Level level, BlockPos pos) throws CycleDetectedException {

		Set<BlockPos> visited = new HashSet<>();
		Stack<BlockPos> traces = new Stack<>();
		ArrayList<BlockPos> ends = new ArrayList<>();

		traces.push(pos);

		BlockPos currPos;

		while (!traces.isEmpty()) {
			currPos = traces.pop();
			// Check for cycle. If detected, skip.
			if (visited.contains(currPos)) {
				continue;
			}
			// Add to the visited set, for the future...
			visited.add(currPos);

			// Check if it is the end of the line.
			if (isBeginning(level, currPos)) {
				// Add it to the ends array.
				ends.add(currPos);
				continue;
			}

			// Add all the branches.
			ArrayList<BlockPos> children = getAllBranches(level, currPos);
			for (BlockPos child : children) {
				traces.push(child);
			}
		}
		return traces;
	}

	private static boolean isBeginning(Level level, BlockPos currPos) {
		return false;
	}

	private static ArrayList<BlockPos> getAllBranches(Level level, BlockPos pos) {
		ArrayList<BlockPos> branches = getControlBranches(level, pos);
		branches.addAll(getWireBranches(level, pos));
		return branches;
	}

	// TODO
	private static ArrayList<BlockPos> getControlBranches(Level level, BlockPos pos) {
		return null;
	}

	// TODO
	private static ArrayList<BlockPos> getWireBranches(Level level, BlockPos pos) {
		return null;
	}

	// TODO
	private static boolean isControlledGate(Level level, BlockPos currPos) {
		return false;
	}

	// TODO
	private static boolean isControllerGate(Level level, BlockPos currPos) {
		return false;
	}

	// TODO
	@SuppressWarnings("serial")
	static class CycleDetectedException extends Exception {
		public CycleDetectedException() {
	        super("A cycle was detected in the circuit!");
	    }
	}
}
