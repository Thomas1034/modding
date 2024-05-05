package com.thomas.cloudscape.worldgen;

import com.thomas.cloudscape.Cloudscape;
import com.thomas.cloudscape.entity.ModEntityType;
import com.thomas.cloudscape.util.ModTags;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomeModifiers {
	public static final ResourceKey<BiomeModifier> ADD_ZIRCON_ORE = registerKey("add_zircon_ore");
	public static final ResourceKey<BiomeModifier> ADD_CITRINE_GEODE = registerKey("add_citrine_geode");

	public static final ResourceKey<BiomeModifier> ADD_TREE_PALM = registerKey("add_tree_palm");

	public static final ResourceKey<BiomeModifier> ADD_TREE_PALM_JUNGLE = registerKey("add_tree_palm_jungle");

	public static final ResourceKey<BiomeModifier> ADD_OASIS = registerKey("add_oasis");

	public static final ResourceKey<BiomeModifier> ADD_SCULK_JAW = registerKey("add_sculk_jaw");

	public static final ResourceKey<BiomeModifier> ADD_MOLE = registerKey("add_mole");

	public static void bootstrap(BootstapContext<BiomeModifier> context) {
		var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		var biomes = context.lookup(Registries.BIOME);

		context.register(ADD_ZIRCON_ORE,
				new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
						HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.ZIRCON_ORE_PLACED_KEY)),
						GenerationStep.Decoration.UNDERGROUND_ORES));

		context.register(ADD_CITRINE_GEODE,
				new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_JUNGLE),
						HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.CITRINE_GEODE_PLACED_KEY)),
						GenerationStep.Decoration.UNDERGROUND_ORES));
		
		context.register(ADD_TREE_PALM,
				new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(ModTags.Biomes.HAS_BEACH_PALMS),
						HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PALM_PLACED_KEY)),
						GenerationStep.Decoration.VEGETAL_DECORATION));

		context.register(ADD_TREE_PALM_JUNGLE,
				new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.IS_JUNGLE),
						HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.PALM_PLACED_JUNGLE_KEY)),
						GenerationStep.Decoration.VEGETAL_DECORATION));

		context.register(ADD_OASIS,
				new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.HAS_DESERT_PYRAMID),
						HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.OASIS_PLACED_KEY)),
						GenerationStep.Decoration.LAKES));

		context.register(ADD_SCULK_JAW,
				new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomes.getOrThrow(BiomeTags.HAS_ANCIENT_CITY),
						HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.SCULK_HOSTILES_PLACED_KEY)),
						GenerationStep.Decoration.UNDERGROUND_DECORATION));

		context.register(ADD_MOLE,
				ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(biomes.getOrThrow(BiomeTags.HAS_ANCIENT_CITY),
						new MobSpawnSettings.SpawnerData(ModEntityType.MOLE_ENTITY.get(), 1, 3, 4)));
	}

	private static ResourceKey<BiomeModifier> registerKey(String name) {
		
		return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Cloudscape.MOD_ID, name));
	}
}
