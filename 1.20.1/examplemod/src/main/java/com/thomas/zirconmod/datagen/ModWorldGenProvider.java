package com.thomas.zirconmod.datagen;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.worldgen.ModBiomeModifiers;
import com.thomas.zirconmod.worldgen.ModConfiguredFeatures;
import com.thomas.zirconmod.worldgen.ModPlacedFeatures;
import com.thomas.zirconmod.worldgen.biome.ModBiomes;
import com.thomas.zirconmod.worldgen.dimension.ModDimensions;
import com.thomas.zirconmod.worldgen.dimension.noise.ModNoiseBuilders;
import com.thomas.zirconmod.worldgen.dimension.noise.ModNoiseSettings;
import com.thomas.zirconmod.worldgen.dimension.noise.ModNoises;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;


public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
    		.add(Registries.DIMENSION_TYPE, ModDimensions::bootstrapType)
    		.add(Registries.LEVEL_STEM, ModDimensions::bootstrapStem)
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)
            .add(Registries.BIOME, ModBiomes::boostrap)
    		.add(Registries.NOISE_SETTINGS, ModNoiseSettings::bootstrap)
    		.add(Registries.NOISE, ModNoises::bootstrap)
    		.add(Registries.DENSITY_FUNCTION, ModNoiseBuilders::bootstrap);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(ZirconMod.MOD_ID));
    }
}