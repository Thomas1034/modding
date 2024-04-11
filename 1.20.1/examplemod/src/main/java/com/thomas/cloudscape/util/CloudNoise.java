package com.thomas.cloudscape.util;

import net.minecraft.core.BlockPos;

public class CloudNoise {
	
	private static final int NUM_OSCILLATORS = 16;
	
	public static int at(long seed, BlockPos pos, int range) {
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		
		
		// Split the seed to get a part for each x, y, and z, plus a leftover.
		// This depends on a long being at least 4 times the size of an integer.
		// The split seeds will be used as the offset for the oscillator.
		int xSeed = (int) ((seed) & Integer.MAX_VALUE);
		int ySeed = (int) ((seed >> Integer.SIZE) & Integer.MAX_VALUE);
		int zSeed = (int) ((seed >> (Integer.SIZE * 2)) & Integer.MAX_VALUE);
		int tSeed = (int) ((seed >> (Integer.SIZE * 3)) & Integer.MAX_VALUE);
		
		// Add up all the oscillators
		double accumulator = 0;
		double maxVal = 0;
		for (int i = 0; i < NUM_OSCILLATORS; i++) {
			int period = 1 << (i + 4);
			double amplitude = i;
			accumulator += oscillator(x, xSeed + tSeed * 0.5, period, amplitude);
			accumulator += oscillator(y, ySeed + tSeed * 0.5, period, amplitude);
			accumulator += oscillator(z, zSeed + tSeed * 0.5, period, amplitude);
			maxVal += 3 * i;
		}
		
		// Map and return.
		return (int) Utilities.map(Utilities.unmap(accumulator, -maxVal, maxVal), 1, range);
	}
	
	private static double oscillator(double x, double offset, double period, double amplitude) {
		return Math.sin((x + offset) * 2 * Math.PI / period) * amplitude;
	}
	
	
	
}
