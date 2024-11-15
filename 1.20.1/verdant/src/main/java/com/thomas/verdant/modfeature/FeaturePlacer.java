package com.thomas.verdant.modfeature;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.block.custom.StinkingBlossomBlock;
import com.thomas.verdant.block.custom.VerdantVineBlock;
import com.thomas.verdant.entity.ModEntityTypes;
import com.thomas.verdant.entity.custom.OvergrownZombieEntity;
import com.thomas.verdant.util.ModTags;
import com.thomas.verdant.util.function.PentaPredicate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class FeaturePlacer {

	private static final FeatureSet FEATURES = new FeatureSet();

	// Surface features will only check for placement if the block above their
	// position is air.
	public static final FeatureType SURFACE_FEATURES = create(FeaturePlacer::surfacePlacement,
			FeaturePlacer::surfacePlacement, "surface_features");
	// Surface replaceable features will only check for placement if the block above
	// their position is replaceable.
	public static final FeatureType VERDANT_VINE_FEATURES = create(FeaturePlacer::verdantVinePlacement,
			FeaturePlacer::verdantVinePlacement, "verdant_vine_features");
	// Hanging features will only check for placement if the block below their
	// position is air.
	public static final FeatureType HANGING_FEATURES = create(FeaturePlacer::hangingPlacement,
			FeaturePlacer::hangingPlacement, "hanging_features");
	// Water features will only check for placement if the block above their
	// position is water.
	public static final FeatureType WATER_FEATURES = create(FeaturePlacer::waterPlacement,
			FeaturePlacer::waterPlacement, "water_features");
	// Entity features check for passable blocks above.
	public static final FeatureType ENTITY_FEATURES = create(Feature::simpleAboveDoublePassableChecker,
			Feature::simpleAboveDoublePassableChecker, "entity_features");
	// Generic features always check for placement
	public static final FeatureType GENERIC_FEATURES = create(FeaturePlacer::always, "generic_features");

	// Creates a new class of features.
	public static FeatureType create(BiPredicate<Level, BlockPos> condition, String name) {
		return FEATURES.create(condition, name);
	}

	public static FeatureType create(BiPredicate<Level, BlockPos> condition,
			PentaPredicate<Level, BlockPos, BlockState, BlockState, BlockState> extendedCondition, String name) {
		return FEATURES.create(condition, extendedCondition, name);
	}

	public static void register(FeatureType type, WeightedFeature weightedFeature) {
		type.addFeature(weightedFeature);
		// System.out.println("Registered " + weightedFeature + " to " +
		// type.getName());
	}

	public static void register(FeatureType type, Feature feature, int weight, String name) {
		WeightedFeature weightedFeature = new WeightedFeature(feature, weight, name);
		register(type, weightedFeature);
	}

	// Registers a feature to the list of generic features, with a given weight and
	// name.
	@SuppressWarnings("unused")
	private static void registerGenericFeature(Feature feature, int weight, String name) {
		register(GENERIC_FEATURES, feature, weight, name);
	}

	// Registers a feature to the list of surface features, with a given weight and
	// name.
	private static void registerSurfaceFeature(Feature feature, int weight, String name) {
		register(SURFACE_FEATURES, feature, weight, name);
	}

	// Registers a feature to the list of entity features, with a given weight and
	// name.
	private static void registerEntityFeature(Feature feature, int weight, String name) {
		register(ENTITY_FEATURES, feature, weight, name);
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

	// Registers all entity features.
	private static void registerEntityFeatures() {
		registerEntityFeature(Feature.monster(ModEntityTypes.OVERGROWN_SKELETON.get(), new ItemStack(Items.BOW)),
				Rarity.EXTREMELY_RARE, "overgrown_skeleton");
		registerEntityFeature(Feature.monster(ModEntityTypes.OVERGROWN_ZOMBIE.get(), new ItemStack(Items.AIR), (m) -> {
			OvergrownZombieEntity z = ((OvergrownZombieEntity) m);
			z.setBaby(Zombie.getSpawnAsBabyOdds(z.getRandom()));
			return z;
		}), Rarity.VERY_RARE, "overgrown_zombie");
	}

	// Registers all surface features.
	private static void registerSurfaceFeatures() {
		registerSurfaceFeature(Feature.smallPlant(Blocks.FERN), Rarity.VERY_COMMON, "small_fern");
		registerSurfaceFeature(Feature.smallPlant(Blocks.GRASS), Rarity.COMMON, "small_grass");
		registerSurfaceFeature(Feature.smallPlant(ModBlocks.BUSH.get()), Rarity.UNCOMMON, "bush");
		registerSurfaceFeature(Feature.tallPlant((DoublePlantBlock) Blocks.LARGE_FERN), Rarity.UNCOMMON, "tall_fern");
		registerSurfaceFeature(Feature.tallPlant((DoublePlantBlock) Blocks.TALL_GRASS), Rarity.UNCOMMON, "tall_grass");
		registerSurfaceFeature(Feature.smallPlant(ModBlocks.THORN_BUSH.get()), Rarity.VERY_UNCOMMON, "thorn_bush");
		registerSurfaceFeature(Feature.smallPlant(ModBlocks.BLEEDING_HEART.get()), Rarity.EXTREMELY_UNCOMMON,
				"bleeding_heart_flower");
		registerSurfaceFeature(
				Feature.smallPlant(Blocks.GLOW_LICHEN.defaultBlockState()
						.setValue(MultifaceBlock.getFaceProperty(Direction.DOWN), true)),
				Rarity.RARE, "floor_glow_lichen");
		registerSurfaceFeature(Feature.smallPlant(ModBlocks.WILD_CASSAVA.get()), Rarity.RARE, "wild_cassava");
		registerSurfaceFeature(Feature.smallPlant(ModBlocks.WILD_UBE.get()), Rarity.RARE, "wild_ube");
		registerSurfaceFeature(Feature.smallPlant(Blocks.BLUE_ORCHID), Rarity.RARE, "blue_orchid");
		registerSurfaceFeature(Feature.smallPlant(Blocks.BROWN_MUSHROOM), Rarity.RARE, "brown_mushroom");
		registerSurfaceFeature(Feature.smallPlant(ModBlocks.STINKING_BLOSSOM.get()), Rarity.VERY_RARE,
				"floor_stinking_blossom");
		registerSurfaceFeature(Feature.smallPlant(ModBlocks.WILD_COFFEE.get()), Rarity.VERY_RARE, "wild_coffee");
		registerSurfaceFeature(Feature.smallPlant(Blocks.RED_MUSHROOM), Rarity.VERY_RARE, "red_mushroom");
		// System.out.println("Registered " + SURFACE_FEATURES);
	}

	private static void registerVerdantVineFeatures() {
		registerVerdantVineFeature(Feature.verdantVine((VerdantVineBlock) ModBlocks.VERDANT_VINE.get()), Rarity.RARE,
				"verdant_vine");
		registerVerdantVineFeature(Feature.verdantVine((VerdantVineBlock) ModBlocks.LEAFY_VERDANT_VINE.get()),
				Rarity.VERY_RARE, "leafy_verdant_vine");

		// System.out.println("Registered " + VERDANT_VINE_FEATURES);
	}

	// Registers all hanging features.
	private static void registerHangingFeatures() {
		registerHangingFeature(Feature.hanging(Blocks.HANGING_ROOTS, 0), Rarity.COMMON, "hanging_roots");
		registerHangingFeature(Feature.hanging(
				Blocks.VINE.defaultBlockState().setValue(MultifaceBlock.getFaceProperty(Direction.UP), true), 0),
				Rarity.COMMON, "ceiling_vine");
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
		// System.out.println("Registered " + HANGING_FEATURES);
	}

	// Registers all water features.
	private static void registerWaterFeatures() {
		registerWaterFeature(Feature.smallUnderwaterPlant(Blocks.SEAGRASS), Rarity.COMMON, "seagrass");
		registerWaterFeature(Feature.tallUnderwaterPlant((DoublePlantBlock) Blocks.TALL_SEAGRASS), Rarity.UNCOMMON,
				"tall_seagrass");
		registerWaterFeature(Feature.tallUnderwaterPlant((DoublePlantBlock) Blocks.SMALL_DRIPLEAF), Rarity.UNCOMMON,
				"small_dripleaf");
		registerWaterFeature(Feature.tallUnderwaterPlant((DoublePlantBlock) ModBlocks.WATER_HEMLOCK.get()),
				Rarity.EXTREMELY_UNCOMMON, "water_hemlock");
		registerWaterFeature(Feature.aboveShallowWater(Blocks.LILY_PAD.defaultBlockState(), 4, 2), Rarity.VERY_UNCOMMON,
				"lily_pad");

		// System.out.println("Registered " + WATER_FEATURES);
	}

	// Registers all generic features.
	private static void registerGenericFeatures() {

		// System.out.println("Registered " + GENERIC_FEATURES);
	}

	// Registers all the features for this class.
	public static void registerFeatures() {
		// Entity features
		registerEntityFeatures();
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
		BlockState above = level.getBlockState(pos.above());
		BlockState at = level.getBlockState(pos);
		BlockState below = level.getBlockState(pos.below());
		place(level, pos, above, at, below);
	}

	// Places a random feature at the given position.
	public static void place(Level level, BlockPos pos, BlockState above, BlockState at, BlockState below) {

		List<FeatureType> features = new ArrayList<>();

		int totalWeight = 0;
		for (FeatureType type : FEATURES.iterate()) {
			if (type.checkForPlacement(level, pos, above, at, below)) {
				// System.out.println("Adding " + type);
				features.add(type);
				totalWeight += type.weight();
			}
		}

		// First, get a random type to place.
		// Get the total weight.
		if (0 == totalWeight) {
			// No features can be placed.
			return;
		}
		// Get a random number to correspond to the feature type to choose.
		int randomWeight = level.getRandom().nextInt(totalWeight);
		FeatureType chosenType = null;
		// Iterate until all the weight has been consumed.
		for (FeatureType type : features) {

			int weight = type.weight();
			if (randomWeight <= weight) {
				chosenType = type;
				break;
			}
			randomWeight -= weight;
		}

		// If the type is still null, there has been an error.
		// Print out a warning message.
		if (chosenType == null) {
			System.err.println("Warning! Failed to select a feature type in " + FeaturePlacer.class);
			System.err.println("There was " + randomWeight + " weight remaining out of " + totalWeight + ".");
			return;
		}

		// Then, get a random feature holder.
		WeightedFeature selectedHolder = null;
		// Get a random number to correspond to the feature type to choose.
		totalWeight = chosenType.weight();
		randomWeight = level.getRandom().nextInt(totalWeight);
		// Iterate until all the weight has been consumed.
		for (WeightedFeature feature : chosenType) {

			int weight = feature.weight();
			if (randomWeight <= weight) {
				selectedHolder = feature;
				break;
			}
			randomWeight -= weight;
		}

		// If the holder is still null, there has been an error.
		// Print out a warning message.
		if (selectedHolder == null) {
			System.err.println("Warning! Failed to select a feature in " + FeaturePlacer.class);
			System.err.println("There was " + randomWeight + " weight remaining out of " + totalWeight + ".");
			return;
		}

		// Otherwise, place the feature.
		selectedHolder.feature().place(level, pos);

	}

	public static boolean surfacePlacement(Level level, BlockPos pos) {
		return level.getBlockState(pos.above()).isAir()
				&& level.getBlockState(pos).isCollisionShapeFullBlock(level, pos);
	}

	public static boolean surfacePlacement(Level level, BlockPos pos, BlockState above, BlockState at,
			BlockState below) {
		return above.isAir() && at.isCollisionShapeFullBlock(level, pos);
	}

	public static boolean verdantVinePlacement(Level level, BlockPos pos) {
		BlockState above = level.getBlockState(pos.above());
		return above.is(ModTags.Blocks.VERDANT_VINE_REPLACABLES)
				&& VerdantVineBlock.canGrowToAnyFace(level, pos.above());
	}

	public static boolean verdantVinePlacement(Level level, BlockPos pos, BlockState above, BlockState at,
			BlockState below) {
		return above.is(ModTags.Blocks.VERDANT_VINE_REPLACABLES)
				&& VerdantVineBlock.canGrowToAnyFace(level, pos.above());
	}

	public static boolean hangingPlacement(Level level, BlockPos pos) {
		return level.getBlockState(pos.below()).isAir()
				&& level.getBlockState(pos).isFaceSturdy(level, pos, Direction.DOWN);
	}

	public static boolean hangingPlacement(Level level, BlockPos pos, BlockState above, BlockState at,
			BlockState below) {
		return below.isAir() && at.isFaceSturdy(level, pos, Direction.DOWN);
	}

	public static boolean waterPlacement(Level level, BlockPos pos) {
		BlockState above = level.getBlockState(pos.above());
		return above.is(Blocks.WATER) && above.getFluidState().isSourceOfType(Fluids.WATER)
				&& level.getBlockState(pos).isFaceSturdy(level, pos, Direction.UP);
	}

	public static boolean waterPlacement(Level level, BlockPos pos, BlockState above, BlockState at, BlockState below) {
		return above.is(Blocks.WATER) && above.getFluidState().isSourceOfType(Fluids.WATER)
				&& at.isFaceSturdy(level, pos, Direction.UP);
	}

	public static boolean always(Level level, BlockPos pos) {
		return true;
	}
}
