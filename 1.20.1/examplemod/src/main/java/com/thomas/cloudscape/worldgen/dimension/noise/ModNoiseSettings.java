package com.thomas.cloudscape.worldgen.dimension.noise;

import com.thomas.cloudscape.Cloudscape;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class ModNoiseSettings {
	 public static final ResourceKey<NoiseGeneratorSettings> SKY = createKey("sky");

	    private static ResourceKey<NoiseGeneratorSettings> createKey(String name) {
	        return ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(Cloudscape.MOD_ID, name));
	    }

	    public static void bootstrap(BootstapContext<NoiseGeneratorSettings> context) {
	        HolderGetter<DensityFunction> densityFunctions = context.lookup(Registries.DENSITY_FUNCTION);
	        HolderGetter<NormalNoise.NoiseParameters> noise = context.lookup(Registries.NOISE);
	        context.register(SKY, ModNoiseBuilders.skyNoiseSettings(densityFunctions, noise));
	    }
}
