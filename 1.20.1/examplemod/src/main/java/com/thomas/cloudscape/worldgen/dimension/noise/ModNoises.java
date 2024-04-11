package com.thomas.cloudscape.worldgen.dimension.noise;

import com.thomas.cloudscape.ZirconMod;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;

// Also more or less copied with changed values from Aether Mod.
public class ModNoises {
	public static final ResourceKey<NoiseParameters> TEMPERATURE = createKey("temperature");
	public static final ResourceKey<NoiseParameters> VEGETATION = createKey("vegetation");
	
	private static ResourceKey<NoiseParameters> createKey(String name) {
        return ResourceKey.create(Registries.NOISE, new ResourceLocation(ZirconMod.MOD_ID, name));
    }
	
	public static void bootstrap(BootstapContext<NoiseParameters> context) {
		register(context, TEMPERATURE, -8, 1.5, 0.0, 1.0, 0.0, 0.0, 0.0); // change these values later?
		register(context, VEGETATION, -7, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0); // change these values later?
    }
	
	public static void register(BootstapContext<NoiseParameters> context, ResourceKey<NormalNoise.NoiseParameters> key, int firstOctave, double firstAmplitude, double... amplitudes) {
		context.register(key, new NormalNoise.NoiseParameters(firstOctave, firstAmplitude, amplitudes));
	}
}
