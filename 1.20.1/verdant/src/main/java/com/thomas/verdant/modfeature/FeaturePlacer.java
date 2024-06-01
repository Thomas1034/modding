package com.thomas.verdant.modfeature;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.block.custom.StinkingBlossomBlock;
import com.thomas.verdant.block.custom.VerdantVineBlock;
import com.thomas.verdant.util.ModTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.MultifaceBlock;

public class FeaturePlacer {

	private static final FeatureSet FEATURES = new FeatureSet();

	// Surface features will only check for placement if the block above their
	// position is air.
	private static final FeatureType SURFACE_FEATURES = create(FeaturePlacer::surfacePlacement, "surface_features");
	// Surface replaceable features will only check for placement if the block above
	// their position is replaceable.
	private static final FeatureType VERDANT_VINE_FEATURES = create(FeaturePlacer::verdantVinePlacement,
			"verdant_vine_features");
	// Hanging features will only check for placement if the block below their
	// position is air.
	private static final FeatureType HANGING_FEATURES = create(FeaturePlacer::hangingPlacement, "hanging_features");
	// Water features will only check for placement if the block above their
	// position is water.
	private static final FeatureType WATER_FEATURES = create(FeaturePlacer::waterPlacement, "water_features");
	// Generic features always check for placement
	private static final FeatureType GENERIC_FEATURES = create(FeaturePlacer::always, "generic_features");

	// Creates a new class of features.
	public static FeatureType create(BiPredicate<Level, BlockPos> condition, String name) {
		return FEATURES.create(condition, name);
	}

	public static void register(FeatureType type, WeightedFeature weightedFeature) {
		type.addFeature(weightedFeature);
		System.out.println("Registered " + weightedFeature + " to " + type.getName());
	}

	public static void register(FeatureType type, Feature feature, int weight, String name) {
		WeightedFeature weightedFeature = new WeightedFeature(feature, weight, name);
		register(type, weightedFeature);
	}

	// Registers a feature to the list of generic features, with a given weight and
	// name.
	private static void registerGenericFeature(Feature feature, int weight, String name) {
		register(GENERIC_FEATURES, feature, weight, name);
	}

	// Registers a feature to the list of surface features, with a given weight and
	// name.
	private static void registerSurfaceFeature(Feature feature, int weight, String name) {
		register(SURFACE_FEATURES, feature, weight, name);
	}

	// Registers a feature to the list of verdant vine features, with a given
	// weight and name.
	private static void registerVerdantVineFeature(Feature feature, int weight, String name) {
		register(VERDANT_VINE_FEATURES, feature, weight, name);
	}

	// Registers a feature to the list of hanging features, with a given weight and
	// name.
	private static void registerHangingFeature(Feature feature, int weight, String name) {
		register(HANGING_FEATURES, feature, weight, name);
	}

	// Registers a feature to the list of water features, with a given weight and
	// name.
	private static void registerWaterFeature(Feature feature, int weight, String name) {
		register(WATER_FEATURES, feature, weight, name);
	}

	// Registers all surface features.
	private static void registerSurfaceFeatures() {
		registerSurfaceFeature(Feature.smallPlant(Blocks.FERN), Rarity.VERY_COMMON, "small_fern");
		registerSurfaceFeature(Feature.smallPlant(Blocks.GRASS), Rarity.COMMON, "small_grass");
		registerSurfaceFeature(Feature.tallPlant((DoublePlantBlock) Blocks.LARGE_FERN), Rarity.UNCOMMON, "tall_fern");
		registerSurfaceFeature(Feature.tallPlant((DoublePlantBlock) Blocks.TALL_GRASS), Rarity.UNCOMMON, "tall_grass");
		registerSurfaceFeature(Feature.smallPlant(ModBlocks.THORN_BUSH.get()), Rarity.VERY_UNCOMMON, "thorn_bush");
		registerSurfaceFeature(Feature.smallPlant(ModBlocks.BLEEDING_HEART.get()), Rarity.EXTREMELY_UNCOMMON,
				"bleeding_heart_flower");
		registerSurfaceFeature(
				Feature.smallPlant(Blocks.GLOW_LICHEN.defaultBlockState()
						.setValue(MultifaceBlock.getFaceProperty(Direction.DOWN), true)),
				Rarity.RARE, "floor_glow_lichen");
		registerSurfaceFeature(Feature.smallPlant(Blocks.BLUE_ORCHID), Rarity.VERY_RARE, "blue_orchid");
		registerSurfaceFeature(Feature.smallPlant(ModBlocks.WILD_COFFEE.get()), Rarity.VERY_RARE, "wild_coffee");
		registerSurfaceFeature(Feature.smallPlant(Blocks.BROWN_MUSHROOM), Rarity.VERY_RARE, "brown_mushroom");
		registerSurfaceFeature(Feature.smallPlant(Blocks.RED_MUSHROOM), Rarity.EXTREMELY_RARE, "red_mushroom");
		registerSurfaceFeature(Feature.smallPlant(ModBlocks.STINKING_BLOSSOM.get()), Rarity.EXTREMELY_RARE,
				"floor_stinking_blossom");
		System.out.println("Registered " + SURFACE_FEATURES);
	}

	private static void registerVerdantVineFeatures() {
		registerVerdantVineFeature(Feature.verdantVine((VerdantVineBlock) ModBlocks.VERDANT_VINE.get()), Rarity.RARE,
				"verdant_vine");
		registerVerdantVineFeature(Feature.verdantVine((VerdantVineBlock) ModBlocks.LEAFY_VERDANT_VINE.get()),
				Rarity.VERY_RARE, "leafy_verdant_vine");

		System.out.println("Registered " + VERDANT_VINE_FEATURES);
	}

	// Registers all hanging features.
	private static void registerHangingFeatures() {
		registerHangingFeature(Feature.hanging(Blocks.HANGING_ROOTS, 0), Rarity.COMMON, "hanging_roots");
		registerHangingFeature(Feature.hanging(ModBlocks.VERDANT_TENDRIL.get(), 1), Rarity.UNCOMMON, "tendril");
		registerHangingFeature(
				Feature.hanging(Blocks.GLOW_LICHEN.defaultBlockState()
						.setValue(MultifaceBlock.getFaceProperty(Direction.UP), true), 0),
				Rarity.UNCOMMON, "ceiling_glow_lichen");
		registerHangingFeature(Feature.hanging(ModBlocks.POISON_IVY.get(), 1), Rarity.VERY_UNCOMMON, "poison_ivy");
		registerHangingFeature(Feature.hanging(Blocks.CAVE_VINES, 1), Rarity.EXTREMELY_UNCOMMON, "cave_vines");
		registerHangingFeature(
				Feature.hanging(ModBlocks.STINKING_BLOSSOM.get().defaultBlockState()
						.setValue(StinkingBlossomBlock.VERTICAL_DIRECTION, Direction.DOWN), 1),
				Rarity.RARE, "ceiling_stinking_blossom");
		registerHangingFeature(Feature.hanging(Blocks.SPORE_BLOSSOM, 1), Rarity.EXTREMELY_RARE, "spore_blossom");
		System.out.println("Registered " + HANGING_FEATURES);
	}

	// Registers all water features.
	private static void registerWaterFeatures() {
		registerWaterFeature(Feature.smallUnderwaterPlant(Blocks.SEAGRASS), Rarity.COMMON, "seagrass");
		registerWaterFeature(Feature.tallUnderwaterPlant((DoublePlantBlock) Blocks.TALL_SEAGRASS), Rarity.UNCOMMON,
				"tall_seagrass");
		registerWaterFeature(Feature.tallUnderwaterPlant((DoublePlantBlock) Blocks.SMALL_DRIPLEAF), Rarity.UNCOMMON,
				"small_dripleaf");
		System.out.println("Registered " + WATER_FEATURES);
	}

	// Registers all generic features.
	private static void registerGenericFeatures() {

		System.out.println("Registered " + GENERIC_FEATURES);
	}

	// Registers all the features for this class.
	public static void registerFeatures() {
		// Surface features
		registerSurfaceFeatures();
		// Surface replaceable features
		registerVerdantVineFeatures();
		// Hanging features
		registerHangingFeatures();
		// Water features
		registerWaterFeatures();
		// Generic features
		registerGenericFeatures();
	}

	// Places a random feature at the given position.
	public static void place(Level level, BlockPos pos) {

		List<FeatureType> features = new ArrayList<>();

		for (FeatureType type : FEATURES.iterate()) {
			if (type.checkForPlacement(level, pos)) {
				// System.out.println("Adding " + type);
				features.add(type);
			}
		}

		// Get a random feature holder.
		WeightedFeature selectedHolder = null;

		// Get the total weight.
		int totalWeight = features.stream().mapToInt((type) -> type.weight()).reduce((a, b) -> a + b).orElse(0);
		if (totalWeight == 0) {
			// No features were registered.
			return;
		}
		// Get a random number to correspond to the feature holder to choose.
		int randomWeight = level.getRandom().nextInt(totalWeight);

		// Iterate until all the weight has been consumed.
		for (FeatureType type : features) {

			for (WeightedFeature featureHolder : type) {
				int featureWeight = featureHolder.weight();
				if (randomWeight <= featureWeight) {
					selectedHolder = featureHolder;
					// System.out.println("Found feature in " + type);
					// System.out.println("Current weight: " + randomWeight);
					// System.out.println("Feature weight: " + featureWeight);
					// System.out.println(selectedHolder);
					break;
				}
				randomWeight -= featureWeight;
			}
		}

		// If the holder is still null, there has been an error.
		// Print out a warning message.
		if (selectedHolder == null) {
			System.out.println("Warning! Failed to place a feature in " + FeaturePlacer.class);
			System.out.println("There was " + randomWeight + " weight remaining out of " + totalWeight + ".");
			return;
		}

		// Otherwise, place the feature.
		selectedHolder.feature().place(level, pos);

	}

	public static boolean surfacePlacement(Level level, BlockPos pos) {
		return level.getBlockState(pos.above()).isAir()
				&& level.getBlockState(pos).isFaceSturdy(level, pos, Direction.UP);
	}

	public static boolean verdantVinePlacement(Level level, BlockPos pos) {
		return VerdantVineBlock.canGrowToAnyFace(level, pos.above())
				&& level.getBlockState(pos.above()).is(ModTags.Blocks.VERDANT_VINE_REPLACABLES);
	}

	public static boolean hangingPlacement(Level level, BlockPos pos) {
		return level.getBlockState(pos.below()).isAir()
				&& level.getBlockState(pos).isFaceSturdy(level, pos, Direction.DOWN);
	}

	public static boolean waterPlacement(Level level, BlockPos pos) {
		return level.getBlockState(pos.above()).is(Blocks.WATER)
				&& level.getBlockState(pos).isFaceSturdy(level, pos, Direction.UP);
	}

	public static boolean always(Level level, BlockPos pos) {
		return true;
	}
}
