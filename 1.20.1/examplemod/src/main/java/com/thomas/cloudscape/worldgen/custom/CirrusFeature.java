package com.thomas.cloudscape.worldgen.custom;

import java.util.HashMap;

import java.util.Map;

import com.mojang.serialization.Codec;
import com.thomas.cloudscape.util.OpenSimplexNoise;
import com.thomas.cloudscape.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CirrusFeature extends Feature<NoneFeatureConfiguration> {

	private final ResourceKey<Biome> filter;
	private final BlockState state;
	private final BlockState alternate;
	private static final Map<Long, OpenSimplexNoise> NOISES = new HashMap<>();

	public CirrusFeature(Codec<NoneFeatureConfiguration> codec, ResourceKey<Biome> filter, BlockState state,
			BlockState alternate) {
		super(codec);
		this.filter = filter;
		this.state = state;
		this.alternate = alternate;
	}

	// Places a cloud layer.
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		// ChunkGenerator chunks = context.chunkGenerator();

		if (!NOISES.containsKey(level.getSeed())) {
			NOISES.put(level.getSeed(), new OpenSimplexNoise(level.getSeed()));
		}

		placeSimplexNoiseLayer(level.getSeed(), level, pos, 3, this.state, this.alternate, this.filter,
				NOISES.get(level.getSeed()));

		return true;

	}

	// Places a simplex-noise-based layer of cloud, if the biome at that position
	// matches the given key.
	@SuppressWarnings("deprecation")
	public static void placeSimplexNoiseLayer(long seed, LevelAccessor level, BlockPos anchor, int thickness,
			BlockState state, BlockState alternate, ResourceKey<Biome> biome, OpenSimplexNoise noise) {

		// If there is no chunk here, skip it.
		if (!level.hasChunkAt(anchor)) {
			System.out.println("Skipping " + anchor.getX() + " " + anchor.getY() + " " + anchor.getZ()
					+ " since it has not been generated yet.");
			return;
		}

		ChunkPos thisChunk = level.getChunk(anchor).getPos();

		// Iterate over the chunk, checking the biome and placing each time.
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {

				BlockPos here = thisChunk.getBlockAt(i, anchor.getY(), j);

				// Check for the correct biome.
				boolean isCorrectBiome = level.getBiome(here).is(biome);
				if (!isCorrectBiome) {
					continue;
				}
				// The coordinates for terrain generation.
				double x = here.getX() * 0.025;
				double y = here.getY() * 0.025;
				double z = here.getZ() * 0.025;

				// For above the middle point
				int aboveRange = (int) (thickness * (noise.eval(x, y, z) + 1) / 2);// + 1;

				// For below the middle point.
				int belowRange = aboveRange / 2;

				// For the middle point itself.
				// Swaps z and y. This rotates it into yet
				// another plane (vertical diagonal). Even harder to find.
				int center = (int) (thickness * 2
						* (noise.eval(0.5 * x + 0.5 * z + 1024, 0.5 * x - 0.5 * z - 1024, y) + 1));

				// The rotation of the cirrus formation.
				double rotationScale = 0.05;// 0.03125;
				double decreaseRotationByDistance = 1e2 / Math.sqrt(Math.sqrt(x * x + z * z));
				double rotation = decreaseRotationByDistance * (Math.PI / 64)
						* (noise.eval(x * rotationScale - 512, y * rotationScale, z * rotationScale - 512) + 1) / 2;
				
				// Iterate over the vertical range.
				for (int k = -belowRange; k < aboveRange; k++) {
					// Offset downwards by the centering value.
					BlockPos verticalPos = here.above(k - center);
					// Check if it matches the cirrus template.
					if (isValidCirrusPlacement(level, verticalPos, rotation)) {
						Utilities.setSafe(level, verticalPos, state);
						Utilities.setIfAir(level, verticalPos.below(), alternate);
					}
				}
			}
		}
	}

	public static boolean isValidCirrusPlacement(LevelAccessor level, BlockPos here, double rotation) {

		// The separation of the cirrus bands.
		double separation = 24;
		// The degree of skew in the y direction.
		double skewY = 0.5;

		// Split the coordinates.
		double x = here.getX();
		double y = here.getY();
		double z = here.getZ();

		// Rotate the x and z;
		double[] rotated = rotatePoint(x, z, rotation);
		x = rotated[0];
		z = rotated[1];
		// System.out.println("new: " + x + " " + y);

		// Subtract from the x coordinate based on the y.
		x -= skewY * y;

		// Mod by the separation distance.
		x = x % separation;

		// Add separation if negative.
		x = x < 0 ? x + separation : x;

		// If x is less than the separation / 4, it's good.
		// System.out.println("x is now " + x);
		return x < (separation / 12);// * (1 + Math.sin(2 * Math.PI * x * period) / 2);
	}

	public static double[] rotatePoint(double x, double z, double radians) {

		// Perform rotation
		double newX = x * Math.cos(radians) - z * Math.sin(radians);
		double newZ = x * Math.sin(radians) + z * Math.cos(radians);

		return new double[] { newX, newZ };
	}

}
