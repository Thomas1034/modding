package com.thomas.zirconmod.worldgen.dimension.noise;

import java.util.List;

import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.block.ModBlocks;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

// Mostly pulled from the Aether Mod's respective file, will be edited significantly though.
public class ModNoiseBuilders {
	private static final SurfaceRules.RuleSource CLOUD = SurfaceRules.state(ModBlocks.CLOUD.get().defaultBlockState());

	private static final SurfaceRules.RuleSource AIR = SurfaceRules.state(Blocks.AIR.defaultBlockState());

	
	private static final ResourceKey<DensityFunction> BASE_3D_NOISE_SKY = ResourceKey
			.create(Registries.DENSITY_FUNCTION, new ResourceLocation(ZirconMod.MOD_ID, "base_3d_noise_sky"));

	public static NoiseGeneratorSettings skyNoiseSettings(HolderGetter<DensityFunction> densityFunctions,
			HolderGetter<NormalNoise.NoiseParameters> noise) {
		BlockState cloud = ModBlocks.CLOUD.get().defaultBlockState();
		//BlockState air = Blocks.AIR.defaultBlockState();
		return new NoiseGeneratorSettings(new NoiseSettings(0, 32, 2, 1), // noiseSettings, the first two parameters are
																			// the vertical bound
				cloud, // defaultBlock
				Blocks.WATER.defaultBlockState(), // defaultFluid
				makeNoiseRouter(densityFunctions, noise), // noiseRouter
				cloudSurfaceRules(), // surfaceRule
				List.of(), // spawnTarget
				-64, // seaLevel
				false, // disableMobGeneration
				false, // aquifersEnabled
				false, // oreVeinsEnabled
				false // useLegacyRandomSource
		);
	}

	public static void bootstrap(BootstapContext<DensityFunction> context) {
		context.register(BASE_3D_NOISE_SKY, DensityFunctions.constant(0));
	}

	public static SurfaceRules.RuleSource cloudSurfaceRules() {
		SurfaceRules.RuleSource surface = SurfaceRules
				.sequence(SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(-1, 0), CLOUD), CLOUD);
		return SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, surface),
				SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, CLOUD));
	}

	private static NoiseRouter makeNoiseRouter(HolderGetter<DensityFunction> densityFunctions,
			HolderGetter<NormalNoise.NoiseParameters> noise) {
		return createNoiseRouter(densityFunctions, noise, buildFinalDensity(densityFunctions));
	}

	private static DensityFunction buildFinalDensity(HolderGetter<DensityFunction> densityFunctions) {
		DensityFunction density = getFunction(densityFunctions, BASE_3D_NOISE_SKY);
		density = DensityFunctions.add(density, DensityFunctions.constant(-0.13));
		density = slide(density, 0, 128, 72, 0, -0.2, 8, 40, -0.1);
		density = DensityFunctions.add(density, DensityFunctions.constant(-0.05));
		density = DensityFunctions.blendDensity(density);
		density = DensityFunctions.interpolated(density);
		density = density.squeeze();
		return density;
	}

	/**
	 * [CODE COPY] -
	 * {@link NoiseRouterData#slide(DensityFunction, int, int, int, int, double, int, int, double)}.
	 */
	private static DensityFunction slide(DensityFunction density, int minY, int maxY, int fromYTop, int toYTop,
			double offset1, int fromYBottom, int toYBottom, double offset2) {
		DensityFunction topSlide = DensityFunctions.yClampedGradient(minY + maxY - fromYTop, minY + maxY - toYTop, 1,
				0);
		density = DensityFunctions.lerp(topSlide, offset1, density);
		DensityFunction bottomSlide = DensityFunctions.yClampedGradient(minY + fromYBottom, minY + toYBottom, 0, 1);
		return DensityFunctions.lerp(bottomSlide, offset2, density);
	}

	/**
	 * [CODE COPY] -
	 * {@link NoiseRouterData#noNewCaves(HolderGetter, HolderGetter, DensityFunction)}.<br>
	 * <br>
	 * Logic that called {@link NoiseRouterData#postProcess(DensityFunction)} has
	 * been moved to {@link ModNoiseBuilders#buildFinalDensity(HolderGetter)}.
	 */
	private static NoiseRouter createNoiseRouter(HolderGetter<DensityFunction> densityFunctions,
			HolderGetter<NormalNoise.NoiseParameters> noise, DensityFunction finalDensity) {
		DensityFunction shiftX = getFunction(densityFunctions,
				ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("shift_x")));
		DensityFunction shiftZ = getFunction(densityFunctions,
				ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation("shift_z")));
		DensityFunction temperature = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25,
				noise.getOrThrow(ModNoises.TEMPERATURE));
		DensityFunction vegetation = DensityFunctions.shiftedNoise2d(shiftX, shiftZ, 0.25,
				noise.getOrThrow(ModNoises.VEGETATION));
		return new NoiseRouter(DensityFunctions.zero(), // barrier noise
				DensityFunctions.zero(), // fluid level floodedness noise
				DensityFunctions.zero(), // fluid level spread noise
				DensityFunctions.zero(), // lava noise
				temperature, // temperature
				vegetation, // vegetation
				DensityFunctions.zero(), // continentalness noise
				DensityFunctions.zero(), // erosion noise
				DensityFunctions.zero(), // depth
				DensityFunctions.zero(), // ridges
				DensityFunctions.zero(), // initial density without jaggedness, not sure if this is needed. Some vanilla
											// dimensions use this while others don't.
				finalDensity, // finaldensity
				DensityFunctions.zero(), // veinToggle
				DensityFunctions.zero(), // veinRidged
				DensityFunctions.zero()); // veinGap
	}

	private static DensityFunction getFunction(HolderGetter<DensityFunction> densityFunctions,
			ResourceKey<DensityFunction> key) {
		return new DensityFunctions.HolderHolder(densityFunctions.getOrThrow(key));
	}
}
