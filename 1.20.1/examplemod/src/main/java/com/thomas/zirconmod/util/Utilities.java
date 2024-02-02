package com.thomas.zirconmod.util;

import java.awt.geom.CubicCurve2D;
import java.util.ArrayList;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Utilities {

	// By ChatGPT
	public static int pickNumberWithProbability(RandomSource random, int[] probabilities) {
		int totalWeight = 0;

		for (int weight : probabilities) {
			totalWeight += weight;
		}

		int randomNumber = random.nextInt(totalWeight);
		int cumulativeWeight = 0;

		for (int i = 0; i < probabilities.length; i++) {
			cumulativeWeight += probabilities[i];
			if (randomNumber < cumulativeWeight) {
				return i;
			}
		}

		// This should not happen, but just in case
		return probabilities.length - 1;
	}

	public static boolean setSafe(LevelAccessor level, BlockPos pos, BlockState state) {
		if (pos == null) {
			return false;
		}
		if (Utilities.canReplaceBlockAt(level, pos)) {
			level.setBlock(pos, state, 3);
			return true;
		}
		return false;
	}

	public static boolean setSafeNoFluid(LevelAccessor level, BlockPos pos, BlockState state) {
		if (pos == null) {
			return false;
		}
		if (Utilities.canReplaceBlockNoFluidAt(level, pos)) {
			level.setBlock(pos, state, 3);
			return true;
		}
		return false;
	}

	public static boolean setSculkSafe(LevelAccessor level, BlockPos pos, BlockState state) {
		if (pos == null) {
			return false;
		}
		if (Utilities.sculkReplacableAt(level, pos)) {
			level.setBlock(pos, state, 3);
			return true;
		}
		return false;
	}

	public static boolean setSculk(LevelAccessor level, BlockPos pos) {
		return setSculkSafe(level, pos, Blocks.SCULK.defaultBlockState());
	}

	// Function to get enclosed grid points
	public static ArrayList<BlockPos> getEnclosedGridPoints(BlockPos p1, BlockPos p2, BlockPos p3) {
		// Create a cubic spline interpolation (closed curve)
		int x1 = p1.getX();
		int x2 = p2.getX();
		int x3 = p3.getX();
		int z1 = p1.getZ();
		int z2 = p2.getZ();
		int z3 = p3.getZ();
		CubicCurve2D.Double cubicCurve = new CubicCurve2D.Double(x1, z1, x2, z2, x3, z3, x1, z1);

		// Define the bounding box for the grid
		int minX = Math.min(x1, Math.min(x2, x3));
		int minZ = Math.min(z1, Math.min(z2, z3));
		int maxX = Math.max(x1, Math.max(x2, x3));
		int maxZ = Math.max(z1, Math.max(z2, z3));

		// Get the enclosed grid points
		ArrayList<BlockPos> enclosedPoints = new ArrayList<>();

		for (int x = minX; x <= maxX; x += 1.0) {
			for (int z = minZ; z <= maxZ; z += 1.0) {
				if (cubicCurve.contains(x, z)) {
					enclosedPoints.add(new BlockPos(x, 0, z));
				}
			}
		}

		return enclosedPoints;
	}

	public static BlockPos findSurface(LevelAccessor level, BlockPos pos) {

		if (level.getBlockState(pos).isAir()) {
			return sink(level, pos);
		} else {
			return rise(level, pos);
		}

	}

	public static BlockPos findSurface(LevelAccessor level, BlockPos pos, int range) throws SurfaceNotFoundException {

		if (level.getBlockState(pos).isAir()) {
			return sink(level, pos, range);
		} else {
			return rise(level, pos, range);
		}

	}

	public static ArrayList<int[]> getCoordinatesInRadius(int radius) {
		ArrayList<int[]> coordinatesList = new ArrayList<>();

		for (int x = -radius; x <= radius; x++) {
			for (int y = -radius; y <= radius; y++) {
				if (isWithinRadius(x, y, radius)) {
					int[] coordinates = { x, y };
					coordinatesList.add(coordinates);
				}
			}
		}

		return coordinatesList;
	}

	private static boolean isWithinRadius(int x, int y, int radius) {
		return (x * x) + (y * y) <= (radius * radius);
	}

	// Converts rgb to hex
	public static int toHexColor(int r, int g, int b) {
		r = r & 255;
		g = g & 255;
		b = b & 255;

		return (r << 16) | (g << 8) | (b);

	}

	// Returns a random horizontal direction.
	public static Direction randomHorizontalDirection(RandomSource rand) {

		Direction[] dirs = { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST };
		return dirs[rand.nextInt(4)];
	}

	// Returns a block position within the given radius at a random position.
	// The block position will have y=0 and x^2 + z^2 <= radius^2.
	public static BlockPos withinCircle(RandomSource rand, double radius) {
		// Ensure radius is positive.
		radius = Math.abs(radius);

		double placementRadius = radius * rand.nextDouble();
		double angle = 2 * Math.PI * rand.nextDouble();

		return new BlockPos((int) Math.round(Math.cos(angle) * placementRadius), 0,
				(int) Math.round(Math.sin(angle) * placementRadius));
	}

	public static BlockPos withinCircle(RandomSource rand, double minRadius, double maxRadius) {
		// Ensure radius is positive.
		minRadius = Math.abs(minRadius);
		maxRadius = Math.abs(maxRadius);

		double placementRadius = Math.abs(maxRadius - minRadius) * rand.nextDouble() + Math.min(minRadius, maxRadius);
		double angle = 2 * Math.PI * rand.nextDouble();

		return new BlockPos((int) Math.round(Math.cos(angle) * placementRadius), 0,
				(int) Math.round(Math.sin(angle) * placementRadius));
	}

	// Checks if a block position is a valid respawn point.
	private static boolean isValidRespawn(Level level, BlockPos pos) throws NullPointerException {

		BlockState above = level.getBlockState(pos.above());
		BlockState at = level.getBlockState(pos);
		BlockState below = level.getBlockState(pos.below());
		// System.out.println("Column is: " + below.getBlock() + " " + at.getBlock() + "
		// " + above.getBlock());

		boolean isBottomSolid = below.isFaceSturdy(level, pos, Direction.UP);
		boolean isEmpty = at.canBeReplaced() && above.canBeReplaced();

		// System.out.println("Is the bottom solid? " + isBottomSolid);
		// System.out.println("Is it empty? " + isEmpty);
		return isBottomSolid && isEmpty;
	}

	// Gets a nearby respawn point that will be valid.
	// If there are no valid respawn points, returns null.
	// Searches from the inside out.
	public static BlockPos getNearbyRespawn(Level level, BlockPos start, int maxDistance) {

		BlockPos npos = null;
		for (int i = 0; i < maxDistance; i++) {
			npos = iterateCubeSurfaceForRespawn(i, start, level);
			if (npos != null) {
				return start.offset(npos.getX(), npos.getY(), npos.getZ());
			}
		}

		return null;
	}

	public static BlockPos iterateCubeSurfaceForRespawn(int n, BlockPos start, Level level) {

		for (int i = -n; i <= n; i++) {
			for (int j = -n; j <= n; j++) {

				if (isValidRespawn(level, start, i, -n, j)) {
					return new BlockPos(i, j, -n);
				} else if (isValidRespawn(level, start, i, n, j)) {
					return new BlockPos(i, n, j);
				} else if (isValidRespawn(level, start, -n, i, j)) {
					return new BlockPos(-n, i, j);
				} else if (isValidRespawn(level, start, n, i, j)) {
					return new BlockPos(n, i, j);
				} else if (isValidRespawn(level, start, i, j, -n)) {
					return new BlockPos(i, j, -n);
				} else if (isValidRespawn(level, start, i, j, n)) {
					return new BlockPos(i, j, n);
				}
			}
		}

		return null;

	}

	private static boolean isValidRespawn(Level level, BlockPos start, int i, int j, int k) {
		return isValidRespawn(level, new BlockPos(start.getX() + i, start.getY() + j, start.getZ() + k));
	}

	public static void addParticlesAroundEntity(Entity entity, ParticleOptions particle) {
		addParticlesAroundEntity(entity, particle, 1.0);
	}

	public static void addParticlesAroundEntity(Entity entity, ParticleOptions particle, double boxSize) {
		Level level = entity.level();
		addParticlesAroundPosition(level, entity.getEyePosition(), particle, boxSize);
	}

	public static void addParticlesAroundEntity(ServerLevel level, Entity entity, ParticleOptions particle,
			double boxSize) {
		addParticlesAroundPosition(level, entity.getEyePosition(), particle, boxSize);
	}

	public static void addParticlesAroundPosition(Level level, Vec3 position, ParticleOptions particle,
			double boxSize) {
		for (int i = 0; i < 5; ++i) {
			double dx = level.random.nextGaussian() * 0.02D;
			double dy = level.random.nextGaussian() * 0.02D;
			double dz = level.random.nextGaussian() * 0.02D;
			double x = (2.0D * level.random.nextDouble() - 1.0D) * boxSize + position.x;
			double y = (2.0D * level.random.nextDouble() - 1.0D) * boxSize + position.y;
			double z = (2.0D * level.random.nextDouble() - 1.0D) * boxSize + position.z;
			level.addParticle(particle, x, y, z, dx, dy, dz);
		}
	}

	// Rotates the given x, y, z position so that z-vector is aligned with the
	// facing vector.
	public static Vec3 localCoordinates(Vec3 at, Vec3 facing, Vec3 offset) {

		// Calculate the end position of z.
		facing = facing.normalize();

		// Calculate the rotation matrix
		double[][] rotationMatrix = Matrix.calculateRotationMatrix(facing, new Vec3(0, 0, 1));

		// Calculate the final position.
		Vec3 endPos = Matrix.matToVec(Matrix.multiplyMatrix(rotationMatrix, Matrix.vecToMat(offset)));

		return at.add(endPos);
	}

	// Returns a vector representing a player's motion
	public static Vec3 deltaMotion(Player player) {
		return new Vec3(player.getX() - player.xOld, player.getY() - player.yOld, player.getZ() - player.zOld);
	}

	// Written by ChatGPT
	public static class Matrix {

		public static double[][] calculateRotationMatrix(Vec3 originalVector, Vec3 targetVector) {
			// Step 1: Calculate the Axis of Rotation
			Vec3 axis = originalVector.cross(targetVector).normalize();

			// Step 2: Calculate the Angle of Rotation
			double angle = Math
					.acos(originalVector.dot(targetVector) / (originalVector.length() * targetVector.length()));

			// Step 3: Construct the Rotation Matrix
			return calculateRotationMatrix(axis, angle);
		}

		public static double[][] calculateRotationMatrix(Vec3 axis, double angle) {
			double cosTheta = Math.cos(angle);
			double sinTheta = Math.sin(angle);

			return new double[][] {
					{ cosTheta + axis.x * axis.x * (1 - cosTheta), axis.x * axis.y * (1 - cosTheta) - axis.z * sinTheta,
							axis.x * axis.z * (1 - cosTheta) + axis.y * sinTheta },
					{ axis.y * axis.x * (1 - cosTheta) + axis.z * sinTheta, cosTheta + axis.x * axis.y * (1 - cosTheta),
							axis.y * axis.z * (1 - cosTheta) - axis.x * sinTheta },
					{ axis.z * axis.x * (1 - cosTheta) - axis.y * sinTheta,
							axis.z * axis.y * (1 - cosTheta) + axis.x * sinTheta,
							cosTheta + axis.z * axis.z * (1 - cosTheta) } };
		}

		public static Vec3 matToVec(double[][] arr) {
			return new Vec3(arr[0][0], arr[1][0], arr[2][0]);
		}

		public static double[][] vecToMat(Vec3 vec) {
			return new double[][] { new double[] { vec.x }, new double[] { vec.y }, new double[] { vec.z } };
		}

		// Written by ChatGPT
		public static double[][] multiplyMatrix(double[][] matrixA, double[][] matrixB) {
			int rowsA = matrixA.length;
			int colsA = matrixA[0].length;
			int colsB = matrixB[0].length;

			if (matrixB.length != colsA) {
				throw new IllegalArgumentException("Incompatible matrix dimensions for multiplication");
			}

			double[][] result = new double[rowsA][colsB];

			for (int i = 0; i < rowsA; i++) {
				for (int j = 0; j < colsB; j++) {
					for (int k = 0; k < colsA; k++) {
						result[i][j] += matrixA[i][k] * matrixB[k][j];
					}
				}
			}

			return result;

		}

	}

	public static byte max(byte a, byte b) {
		return a > b ? a : b;
	}

	public static byte min(byte a, byte b) {
		return a < b ? a : b;
	}

	public static int max(int a, int b) {
		return a > b ? a : b;
	}

	public static int min(int a, int b) {
		return a < b ? a : b;
	}

	public static float max(float a, float b) {
		return a > b ? a : b;
	}

	public static float min(float a, float b) {
		return a < b ? a : b;
	}

	public static double max(double a, double b) {
		return a > b ? a : b;
	}

	public static double min(double a, double b) {
		return a < b ? a : b;
	}

	public static boolean checkEqualStates(BlockState blockstate, BlockState target) {
		boolean isCorrectBlock = target.is(blockstate.getBlock());
		boolean doAllMatch = blockstate.getProperties().stream().map((property) -> target.hasProperty(property)
				&& target.getValue(property).equals(blockstate.getValue(property))).allMatch((bool) -> bool);
		return isCorrectBlock && doAllMatch;
	}

	public static boolean canReplaceBlock(BlockState state) {
		return !state.is(BlockTags.FEATURES_CANNOT_REPLACE);
	}

	public static boolean canReplaceBlockNoFluid(BlockState state) {
		return !state.is(BlockTags.FEATURES_CANNOT_REPLACE) && state.getFluidState().isEmpty();
	}

	public static boolean sculkReplacable(BlockState state) {
		return state.is(BlockTags.SCULK_REPLACEABLE_WORLD_GEN);
	}

	public static boolean sculkReplacableAt(LevelAccessor level, BlockPos pos) {
		return sculkReplacable(level.getBlockState(pos));
	}

	public static boolean canReplaceBlockAt(LevelAccessor level, BlockPos pos) {
		return canReplaceBlock(level.getBlockState(pos));
	}

	public static boolean canReplaceBlockNoFluidAt(LevelAccessor level, BlockPos pos) {
		return canReplaceBlockNoFluid(level.getBlockState(pos));
	}

	// Moves the given position downward to above a solid block.
	public static BlockPos sink(LevelAccessor level, BlockPos pos) {

		BlockState state = level.getBlockState(pos);

		while (!state.isSolid()) {
			pos = pos.below();
			state = level.getBlockState(pos);
		}

		return pos.above();
	}

	// Moves the given position downward to above a solid block.
	public static BlockPos sink(LevelAccessor level, BlockPos pos, int range) throws SurfaceNotFoundException {

		BlockState state = level.getBlockState(pos);
		int tries = 0;
		while (!state.isSolid() && tries < range) {
			pos = pos.below();
			state = level.getBlockState(pos);
			tries++;
		}
		if (tries == range) {
			throw new SurfaceNotFoundException("Failed to find surface within " + range + " blocks.");
		}

		return pos.above();
	}

	// Iterates upward to find an air block, and returns that position.
	public static BlockPos rise(LevelAccessor level, BlockPos pos) {

		BlockState state = level.getBlockState(pos);

		while (state.isSolid()) {
			pos = pos.above();
			state = level.getBlockState(pos);

		}

		return pos;
	}

	// Iterates upward to find an air block, and returns that position.
	public static BlockPos rise(LevelAccessor level, BlockPos pos, int range) throws SurfaceNotFoundException {

		BlockState state = level.getBlockState(pos);
		int tries = 0;
		while (state.isSolid() && tries < range) {
			pos = pos.above();
			state = level.getBlockState(pos);
			tries++;

		}
		if (tries == range) {
			throw new SurfaceNotFoundException("Failed to find surface within " + range + " blocks.");
		}

		return pos;
	}
}
