package com.thomas.verdant.growth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.block.custom.StinkingBlossomBlock;
import com.thomas.verdant.block.custom.VerdantVineBlock;
import com.thomas.verdant.util.TriFunction;
import com.thomas.verdant.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class VerdantFeaturePlacer {

	// Surface features will only check for placement if the block above their
	// position is air.
	private static final HashSet<WeightedFeatureHolder> SURFACE_FEATURES = new HashSet<>();
	// Hanging features will only check for placement if the block below their
	// position is air.
	private static final HashSet<WeightedFeatureHolder> HANGING_FEATURES = new HashSet<>();
	// Water features will only check for placement if the block above their
	// position is water.
	private static final HashSet<WeightedFeatureHolder> WATER_FEATURES = new HashSet<>();
	// Generic features always check for placement
	private static final HashSet<WeightedFeatureHolder> GENERIC_FEATURES = new HashSet<>();

	// Registers a feature to the list of generic features, with a given weight.
	public static void register(Feature feature, int weight) {
		GENERIC_FEATURES.add(new WeightedFeatureHolder(feature, weight));
	}

	// Registers a feature to the list of surface features, with a given weight.
	public static void registerSurfaceFeature(Feature feature, int weight) {
		SURFACE_FEATURES.add(new WeightedFeatureHolder(feature, weight));
	}

	// Registers a feature to the list of hanging features, with a given weight.
	public static void registerHangingFeature(Feature feature, int weight) {
		HANGING_FEATURES.add(new WeightedFeatureHolder(feature, weight));
	}

	// Registers a feature to the list of water features, with a given weight.
	public static void registerWaterFeature(Feature feature, int weight) {
		WATER_FEATURES.add(new WeightedFeatureHolder(feature, weight));
	}

	// Registers all surface features.
	private static void registerSurfaceFeatures() {
		registerSurfaceFeature(Feature.smallPlant(Blocks.FERN), Rarity.VERY_COMMON);
		registerSurfaceFeature(Feature.smallPlant(Blocks.GRASS), Rarity.COMMON);
		registerSurfaceFeature(Feature.tallPlant((DoublePlantBlock) Blocks.LARGE_FERN), Rarity.UNCOMMON);
		registerSurfaceFeature(Feature.tallPlant((DoublePlantBlock) Blocks.TALL_GRASS), Rarity.UNCOMMON);
		registerSurfaceFeature(Feature.smallPlant(ModBlocks.THORN_BUSH.get()), Rarity.VERY_UNCOMMON);
		registerSurfaceFeature(Feature.smallPlant(ModBlocks.BLEEDING_HEART.get()), Rarity.EXTREMELY_UNCOMMON);
		registerSurfaceFeature(
				Feature.simple(
						(level, pos) -> level.setBlockAndUpdate(pos,
								Blocks.GLOW_LICHEN.defaultBlockState()
										.setValue(MultifaceBlock.getFaceProperty(Direction.DOWN), true))),
				Rarity.EXTREMELY_UNCOMMON);
		registerSurfaceFeature(Feature.smallPlant(Blocks.BLUE_ORCHID), Rarity.VERY_RARE);
		registerSurfaceFeature(Feature.smallPlant(Blocks.BROWN_MUSHROOM), Rarity.VERY_RARE);
		registerSurfaceFeature(Feature.smallPlant(Blocks.RED_MUSHROOM), Rarity.EXTREMELY_RARE);
		registerSurfaceFeature(Feature.smallPlant(ModBlocks.STINKING_BLOSSOM.get()), Rarity.EXTREMELY_RARE);
	}

	// Registers all hanging features.
	private static void registerHangingFeatures() {
		registerHangingFeature(Feature.hanging(Blocks.HANGING_ROOTS, 0), Rarity.COMMON);
		registerHangingFeature(Feature.hanging(ModBlocks.VERDANT_TENDRIL.get(), 1), Rarity.UNCOMMON);
		registerHangingFeature(Feature.hanging(ModBlocks.POISON_IVY.get(), 1), Rarity.VERY_UNCOMMON);
		registerHangingFeature(Feature.hanging(Blocks.CAVE_VINES, 1), Rarity.EXTREMELY_UNCOMMON);
		registerHangingFeature(Feature.hanging(ModBlocks.STINKING_BLOSSOM.get().defaultBlockState()
				.setValue(StinkingBlossomBlock.VERTICAL_DIRECTION, Direction.DOWN), 1), Rarity.RARE);
		registerHangingFeature(Feature.hanging(Blocks.SPORE_BLOSSOM, 1), Rarity.EXTREMELY_RARE);
		System.out.println("Registered " + HANGING_FEATURES.size() + " hanging features.");
	}

	// Registers all water features.
	private static void registerWaterFeatures() {
		registerWaterFeature(Feature.smallUnderwaterPlant(Blocks.SEAGRASS), Rarity.COMMON);
		registerWaterFeature(Feature.tallUnderwaterPlant((DoublePlantBlock) Blocks.TALL_SEAGRASS), Rarity.UNCOMMON);
		registerWaterFeature(Feature.tallUnderwaterPlant((DoublePlantBlock) Blocks.SMALL_DRIPLEAF), Rarity.UNCOMMON);
	}

	// Registers all generic features.
	private static void registerGenericFeatures() {
		register(Feature.verdantVine((VerdantVineBlock) ModBlocks.VERDANT_VINE.get()), Rarity.SPECIAL_CASE_COMMON);
		register(Feature.verdantVine((VerdantVineBlock) ModBlocks.LEAFY_VERDANT_VINE.get()),
				Rarity.SPECIAL_CASE_UNCOMMON);
	}

	// Registers all the features for this class.
	public static void registerFeatures() {
		// Surface features
		registerSurfaceFeatures();
		// Hanging features
		registerHangingFeatures();
		// Water features
		registerWaterFeatures();
		// Generic features
		registerGenericFeatures();
	}

	// Places a random feature at the given position.
	public static void place(Level level, BlockPos pos) {

		List<HashSet<WeightedFeatureHolder>> options = new ArrayList<>();
		options.add(GENERIC_FEATURES);

		// First, check the conditions for the different types of features.
		if (level.isStateAtPosition(pos.above(), BlockState::isAir)) {
			options.add(SURFACE_FEATURES);
		}
		if (level.isStateAtPosition(pos.above(), (state) -> state.getFluidState().is(Fluids.WATER))) {
			options.add(WATER_FEATURES);
		}
		if (level.isStateAtPosition(pos.below(), BlockState::isAir)) {
			options.add(HANGING_FEATURES);
		}

		// Get a random effect holder.
		WeightedFeatureHolder holder = null;
		int totalWeight = options.stream().mapToInt(
				(set) -> set.stream().mapToInt((feat) -> feat.weight()).reduce((a, b) -> a + b).orElseGet(() -> 1))
				.reduce((a, b) -> a + b).orElseGet(() -> 1);
		int r = level.getRandom().nextInt(totalWeight);
		for (HashSet<WeightedFeatureHolder> set : options) {

			for (WeightedFeatureHolder el : set) {
				if (r <= el.weight()) {
					holder = el;
					break;
				}
				r -= el.weight();
			}
		}

		// If the holder is still null, there has been an error.
		// Print out a warning message.
		if (holder == null) {
			System.out.println("Warning! Failed to inflict an effect in applyEffectTick for FoodPoisoningEffect.");
			System.out.println("There was " + r + " weight remaining. ");
			return;
		}

		// Otherwise, place the plant.
		holder.feature().place(level, pos);

	}

	public static class Rarity {

		public static final int ALWAYS = Integer.MAX_VALUE;
		public static final int EXTREMELY_COMMON = 200;
		public static final int VERY_COMMON = 150;
		public static final int COMMON = 100;
		public static final int UNCOMMON = 50;
		public static final int VERY_UNCOMMON = 25;
		public static final int EXTREMELY_UNCOMMON = 10;
		public static final int RARE = 5;
		public static final int VERY_RARE = 2;
		public static final int EXTREMELY_RARE = 1;
		public static final int NEVER = 0;

		public static final int SPECIAL_CASE_COMMON = 3000;
		public static final int SPECIAL_CASE_UNCOMMON = 1000;
	}

	public static class WeightedFeatureHolder {
		private final Feature feature;
		private final int weight;

		public WeightedFeatureHolder(Feature feature, int weight) {
			this.feature = feature;
			this.weight = weight;
		}

		public Feature feature() {
			return this.feature;
		}

		public int weight() {
			return this.weight;
		}
	}

	public static class Feature {

		// Places the plant, effecting a change in the level at the given position.
		private BiConsumer<Level, BlockPos> place;
		// Takes in the level and the position of the block that it's being placed on.
		// Returns a valid position to place the block.
		// Returns null if it failed to find one.
		private TriFunction<Feature, Level, BlockPos, BlockPos> findPlacementLocation;
		// Takes in the level and the attempted placement position, returns true if the
		// plant can grow at that location.
		private BiPredicate<Level, BlockPos> canPlace;

		private Feature(BiConsumer<Level, BlockPos> place) {
			this.place = place;
			// Set placement to a simple placement
			this.findPlacementLocation = Feature::simplePlacement;
			// Use a simple checker.
			this.canPlace = Feature::simpleAirChecker;
		}

		private Feature(BiConsumer<Level, BlockPos> place, BiPredicate<Level, BlockPos> canPlace) {
			this.place = place;
			// Set placement to a simple placement
			this.findPlacementLocation = Feature::simplePlacement;
			this.canPlace = canPlace;
		}

		private Feature(BiConsumer<Level, BlockPos> place,
				TriFunction<Feature, Level, BlockPos, BlockPos> findPlacementLocation) {
			this.place = place;
			this.findPlacementLocation = findPlacementLocation;
			this.canPlace = Feature::simpleAirChecker;
		}

		private Feature(BiConsumer<Level, BlockPos> place,
				TriFunction<Feature, Level, BlockPos, BlockPos> findPlacementLocation,
				BiPredicate<Level, BlockPos> canPlace) {
			this.place = place;
			this.findPlacementLocation = findPlacementLocation;
			this.canPlace = canPlace;
		}

		public void place(Level level, BlockPos pos) {
			BlockPos placementPos = this.findPlacementLocation.apply(this, level, pos);
			if (placementPos == null) {
				return;
			}
			this.place.accept(level, placementPos);
		}

		public static Feature simple(BiConsumer<Level, BlockPos> place) {
			return new Feature(place);
		}

		public static Feature doubleHeight(BiConsumer<Level, BlockPos> place) {
			return new Feature(place, Feature::simpleDoubleAirChecker);
		}

		public static Feature doubleHeightWater(BiConsumer<Level, BlockPos> place) {
			return new Feature(place, Feature::simpleDoubleWaterChecker);
		}

		public static Feature smallPlant(Block block) {
			return Feature.simple((level, pos) -> level.setBlockAndUpdate(pos, block.defaultBlockState()));
		}

		public static Feature smallUnderwaterPlant(Block block) {
			return new Feature((level, pos) -> level.setBlockAndUpdate(pos, block.defaultBlockState()),
					Feature::simpleWaterChecker);
		}

		// TODO
		public static Feature vine(VineBlock block) {
			return Feature.simple((level, pos) -> level.setBlockAndUpdate(pos, block.defaultBlockState()));
		}

		public static Feature tallPlant(DoublePlantBlock block) {
			return Feature
					.doubleHeight((level, pos) -> DoublePlantBlock.placeAt(level, block.defaultBlockState(), pos, 2));
		}

		public static Feature tallUnderwaterPlant(DoublePlantBlock block) {
			return Feature.doubleHeightWater(
					(level, pos) -> DoublePlantBlock.placeAt(level, block.defaultBlockState(), pos, 2));
		}

		public static Feature verdantVine(VerdantVineBlock block) {
			return Feature.simple(VerdantVineBlock::spread);

		}

		public static Feature hanging(Block block, int requireAirRadius) {
			return new Feature((level, pos) -> level.setBlockAndUpdate(pos, block.defaultBlockState()),
					Feature::belowPlacement, Feature.withinHorizontalRangeBelowBlockCheckerProvider(
							(state) -> !state.isAir(), requireAirRadius));
		}

		public static Feature hanging(BlockState state, int requireAirRadius) {
			return new Feature((level, pos) -> level.setBlockAndUpdate(pos, state), Feature::belowPlacement,
					Feature.withinHorizontalRangeBelowBlockCheckerProvider((stateToCheck) -> !stateToCheck.isAir(),
							requireAirRadius));
		}

		// Returns true if the given position is air.
		public static boolean simpleAirChecker(Level level, BlockPos pos) {
			return level.getBlockState(pos).isAir();
		}

		// Returns true if the given position is water.
		public static boolean simpleWaterChecker(Level level, BlockPos pos) {
			return level.getBlockState(pos).getFluidState().is(Fluids.WATER);
		}

		// Returns true if the given position and the one above it is air.
		public static boolean simpleDoubleAirChecker(Level level, BlockPos pos) {
			return level.getBlockState(pos).isAir() && level.getBlockState(pos.above()).isAir();
		}

		// Returns true if the given position and the one above it is water.
		public static boolean simpleDoubleWaterChecker(Level level, BlockPos pos) {
			return level.getBlockState(pos).getFluidState().is(Fluids.WATER)
					&& level.getBlockState(pos.above()).getFluidState().is(Fluids.WATER);
		}

		// Returns true if the given position and the one below it is air.
		public static boolean belowDoubleAirChecker(Level level, BlockPos pos) {
			return level.getBlockState(pos).isAir() && level.getBlockState(pos.below()).isAir();
		}

		public static BiPredicate<Level, BlockPos> withinRangeAirCheckerProvider(int radius) {
			return withinRangeBlockCheckerProvider((state) -> !state.isAir(), radius);
		}

		// Returns a lambda expression that returns true if no blocks within a given
		// square layer radius (centered on the block below the given block) match the
		// given predicate.
		public static BiPredicate<Level, BlockPos> withinHorizontalRangeBelowBlockCheckerProvider(
				Predicate<BlockState> isInvalid, int radius) {
			return (level, pos) -> {
				// Center on the block below the given block.
				pos = pos.below();

				// Check that position, guaranteed.
				if (isInvalid.test(level.getBlockState(pos))) {
					return false;
				}

				// Iterate over the given area.
				for (int i = -radius; i <= radius; i++) {
					for (int k = -radius; k <= radius; k++) {
						BlockPos offset = pos.offset(i, 0, k);
						// Return false if the block is invalid.
						if (isInvalid.test(level.getBlockState(offset))) {
							return false;
						}
					}
				}

				// Return true if the checks succeeded.
				return true;
			};
		}

		// Returns a lambda expression that returns true if no blocks within the given
		// cube radius (centered on the block above the given block) match the given
		// predicate.
		public static BiPredicate<Level, BlockPos> withinRangeBlockCheckerProvider(Predicate<BlockState> isInvalid,
				int radius) {
			return (level, pos) -> {
				// Center on the block above the given block.
				pos = pos.above();

				// Iterate over the given area.
				for (int i = -radius; i <= radius; i++) {
					for (int j = -radius; j <= radius; j++) {
						for (int k = -radius; k <= radius; k++) {
							BlockPos offset = pos.offset(i, j, k);
							// Return false if the block is invalid.
							if (isInvalid.test(level.getBlockState(offset))) {
								return false;
							}
						}
					}
				}

				// Return true if the checks succeeded.
				return true;
			};
		}

		// Returns the position above the block if it is valid, or else null;
		public static BlockPos simplePlacement(Feature plant, Level level, BlockPos pos) {
			pos = pos.above();
			return plant.canPlace.test(level, pos) ? pos : null;
		}

		// Returns the position below the block if it is valid, or else null;
		public static BlockPos belowPlacement(Feature plant, Level level, BlockPos pos) {
			pos = pos.below();
			return plant.canPlace.test(level, pos) ? pos : null;
		}

		// Checks each of the sides of the block.
		// Returns a random valid side, or null if it failed to find one.
		public static BlockPos onSidePlacement(Feature plant, Level level, BlockPos pos) {

			ArrayList<BlockPos> validSides = new ArrayList<>();
			for (Direction d : Utilities.HORIZONTAL_DIRECTIONS) {
				BlockPos side = pos.relative(d);
				if (plant.canPlace.test(level, side)) {
					validSides.add(side);
				}
			}

			// Check if the list has entries.
			// Return null if it does not.
			if (validSides.size() == 0) {
				return null;
			}
			// Return a random entry in the list.
			return validSides.get(level.random.nextInt(validSides.size()));
		}

		// Returns a lambda that will check every block within the given cube radius,
		// centered on the block above the given position, keep track of all the valid
		// placement locations, and return one at random.
		public static TriFunction<Feature, Level, BlockPos, BlockPos> withinRangePlacementProvider(Feature plantParam,
				Level levelParam, BlockPos posParam, int radius) {
			return (plant, level, pos) -> {
				// Center on the block above the given block.
				pos = pos.above();

				// Keep a list of valid positions.
				ArrayList<BlockPos> validLocations = new ArrayList<>();

				// Iterate over the given area.
				for (int i = -radius; i <= radius; i++) {
					for (int j = -radius; j <= radius; j++) {
						for (int k = -radius; k <= radius; k++) {
							BlockPos offset = pos.offset(i, j, k);
							if (plant.canPlace.test(level, offset)) {
								validLocations.add(offset);
							}
						}
					}
				}

				// Check if the list has entries.
				// Return null if it does not.
				if (validLocations.size() == 0) {
					return null;
				}
				// Return a random entry in the list.
				return validLocations.get(level.random.nextInt(validLocations.size()));
			};
		}
	}
}
