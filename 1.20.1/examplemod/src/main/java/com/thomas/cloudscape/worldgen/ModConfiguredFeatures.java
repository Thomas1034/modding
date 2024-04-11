package com.thomas.cloudscape.worldgen;

import java.util.List;

import com.thomas.cloudscape.ZirconMod;
import com.thomas.cloudscape.block.ModBlocks;
import com.thomas.cloudscape.block.custom.BubblefruitCropBlock;
import com.thomas.cloudscape.worldgen.custom.CloudFloorFeature;
import com.thomas.cloudscape.worldgen.custom.OasisFeature;
import com.thomas.cloudscape.worldgen.custom.PatchFeature;
import com.thomas.cloudscape.worldgen.custom.PetrifiedTreeFeature;
import com.thomas.cloudscape.worldgen.custom.SculkHostilesFeature;
import com.thomas.cloudscape.worldgen.custom.SimplexCloudFeature;
import com.thomas.cloudscape.worldgen.tree.custom.PalmFoliagePlacer;
import com.thomas.cloudscape.worldgen.tree.custom.PalmTrunkPlacer;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class ModConfiguredFeatures {
	public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_ZIRCON_ORE_KEY = registerKey("zircon_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_CITRINE_GEODE_KEY = registerKey("citrine_geode");

	public static final ResourceKey<ConfiguredFeature<?, ?>> PALM_KEY = registerKey("palm");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PALM_JUNGLE_KEY = registerKey("palm_jungle");

	public static final ResourceKey<ConfiguredFeature<?, ?>> OASIS_KEY = registerKey("oasis");

	public static final ResourceKey<ConfiguredFeature<?, ?>> PETRIFIED_TREE_KEY = registerKey("petrified_tree");

	public static final ResourceKey<ConfiguredFeature<?, ?>> STONE_PATCH_KEY = registerKey("stone_patch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> COARSE_DIRT_PATCH_KEY = registerKey("coarse_dirt_patch");

	public static final ResourceKey<ConfiguredFeature<?, ?>> SCULK_HOSTILES_KEY = registerKey("sculk_hostiles");

	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_CLOUD_KEY = registerKey("large_cloud");

	public static final ResourceKey<ConfiguredFeature<?, ?>> THICK_CLOUD_CEILING_FOR_CLOUDY_SKY_KEY = registerKey(
			"thick_cloud_ceiling");

	public static final ResourceKey<ConfiguredFeature<?, ?>> BUBBLEFRUIT_PATCH_KEY = registerKey("bubblefruit");

	public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_ORCHID_PATCH_KEY = registerKey("white_orchid");

	public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
		RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
		RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

		List<OreConfiguration.TargetBlockState> overworldZirconOres = List.of(
				OreConfiguration.target(stoneReplaceable, ModBlocks.ZIRCON_ORE.get().defaultBlockState()),
				OreConfiguration.target(deepslateReplaceables,
						ModBlocks.DEEPSLATE_ZIRCON_ORE.get().defaultBlockState()));

		register(context, OVERWORLD_ZIRCON_ORE_KEY, Feature.ORE, new OreConfiguration(overworldZirconOres, 12));
		register(context, OVERWORLD_CITRINE_GEODE_KEY, Feature.GEODE, new GeodeConfiguration(
				new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR),
						BlockStateProvider.simple(ModBlocks.CITRINE_BLOCK.get()),
						BlockStateProvider.simple(ModBlocks.BUDDING_CITRINE.get()),
						BlockStateProvider.simple(Blocks.CALCITE), BlockStateProvider.simple(Blocks.SMOOTH_BASALT),
						List.of(ModBlocks.SMALL_CITRINE_BUD.get().defaultBlockState(),
								ModBlocks.MEDIUM_CITRINE_BUD.get().defaultBlockState(),
								ModBlocks.LARGE_CITRINE_BUD.get().defaultBlockState(),
								ModBlocks.CITRINE_CLUSTER.get().defaultBlockState()),
						BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS),
				new GeodeLayerSettings(1.7D, 2.2D, 3.2D, 4.2D), new GeodeCrackSettings(0.95D, 2.0D, 2), 0.35D, 0.083D,
				true, UniformInt.of(4, 6), UniformInt.of(3, 4), UniformInt.of(1, 2), -16, 16, 0.05D, 1));

		register(context, PALM_KEY, Feature.TREE,
				new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.PALM_TRUNK.get()),
						new PalmTrunkPlacer(3, 1, 1), BlockStateProvider.simple(ModBlocks.PALM_FROND.get()),
						new PalmFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 3),
						new TwoLayersFeatureSize(1, 0, 2)).build());

		register(context, PALM_JUNGLE_KEY, Feature.TREE,
				new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.PALM_TRUNK.get()),
						new PalmTrunkPlacer(5, 1, 1), BlockStateProvider.simple(ModBlocks.PALM_FROND.get()),
						new PalmFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 3),
						new TwoLayersFeatureSize(1, 0, 2)).build());

		register(context, OASIS_KEY, (OasisFeature) ModFeature.OASIS.get(), new NoneFeatureConfiguration());
		register(context, PETRIFIED_TREE_KEY, (PetrifiedTreeFeature) ModFeature.PETRIFIED_TREE.get(),
				new NoneFeatureConfiguration());
		register(context, STONE_PATCH_KEY, (PatchFeature) ModFeature.STONE_PATCH.get(), new NoneFeatureConfiguration());
		register(context, COARSE_DIRT_PATCH_KEY, (PatchFeature) ModFeature.COARSE_DIRT_PATCH.get(),
				new NoneFeatureConfiguration());

		register(context, SCULK_HOSTILES_KEY, (SculkHostilesFeature) ModFeature.SCULK_DECORATION.get(),
				new NoneFeatureConfiguration());

		register(context, LARGE_CLOUD_KEY, (SimplexCloudFeature) ModFeature.LARGE_CLOUD.get(),
				new NoneFeatureConfiguration());

		register(context, THICK_CLOUD_CEILING_FOR_CLOUDY_SKY_KEY,
				(CloudFloorFeature) ModFeature.THICK_CLOUD_CEILING_FOR_CLOUDY_SKY.get(),
				new NoneFeatureConfiguration());

		register(context, BUBBLEFRUIT_PATCH_KEY, Feature.RANDOM_PATCH,
				FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
						new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.BUBBLEFRUIT_CROP.get()
								.defaultBlockState().setValue(BubblefruitCropBlock.AGE, Integer.valueOf(3)))),
						List.of(ModBlocks.CLOUD.get())));

		register(context, WHITE_ORCHID_PATCH_KEY, Feature.RANDOM_PATCH,
				FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
						new SimpleBlockConfiguration(
								BlockStateProvider.simple(ModBlocks.WHITE_ORCHID.get().defaultBlockState())),
						List.of(ModBlocks.CLOUD.get())));
	}

	public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(ZirconMod.MOD_ID, name));
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
			BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature,
			FC configuration) {
		context.register(key, new ConfiguredFeature<>(feature, configuration));
	}
}