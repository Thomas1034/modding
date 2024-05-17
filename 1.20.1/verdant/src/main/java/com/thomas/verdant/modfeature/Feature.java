package com.thomas.verdant.modfeature;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import com.thomas.verdant.block.custom.VerdantVineBlock;
import com.thomas.verdant.util.TriFunction;
import com.thomas.verdant.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class Feature {

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

	public static Feature doubleHeightPlant(BiConsumer<Level, BlockPos> place) {
		return new Feature(place, ((BiPredicate<Level, BlockPos>) Feature::simpleDoubleAirChecker)
				.and((BiPredicate<Level, BlockPos>) Feature::simpleDirtBelowChecker));
	}

	public static Feature doubleHeightWater(BiConsumer<Level, BlockPos> place) {
		return new Feature(place, ((BiPredicate<Level, BlockPos>) Feature::simpleDoubleWaterChecker)
				.and((BiPredicate<Level, BlockPos>) Feature::simpleDirtBelowChecker));
	}

	public static Feature smallPlant(Block block) {
		return smallPlant(block.defaultBlockState());
	}

	public static Feature smallPlant(BlockState state) {
		return new Feature((level, pos) -> level.setBlockAndUpdate(pos, state),
				((BiPredicate<Level, BlockPos>) Feature::simpleAirChecker)
						.and((BiPredicate<Level, BlockPos>) Feature::simpleDirtBelowChecker));
	}

	public static Feature smallUnderwaterPlant(Block block) {
		return smallUnderwaterPlant(block.defaultBlockState());
	}

	public static Feature smallUnderwaterPlant(BlockState state) {
		return new Feature((level, pos) -> level.setBlockAndUpdate(pos, state),
				((BiPredicate<Level, BlockPos>) Feature::simpleWaterChecker)
						.and((BiPredicate<Level, BlockPos>) Feature::simpleDirtBelowChecker));
	}

	// TODO
	public static Feature vine(VineBlock block) {
		return Feature.simple((level, pos) -> level.setBlockAndUpdate(pos, block.defaultBlockState()));
	}

	public static Feature tallPlant(DoublePlantBlock block) {
		return new Feature((level, pos) -> DoublePlantBlock.placeAt(level, block.defaultBlockState(), pos, 2),
				((BiPredicate<Level, BlockPos>) Feature::simpleDoubleAirChecker)
						.and((BiPredicate<Level, BlockPos>) Feature::simpleDirtBelowChecker));
	}

	public static Feature tallUnderwaterPlant(DoublePlantBlock block) {
		return new Feature((level, pos) -> DoublePlantBlock.placeAt(level, block.defaultBlockState(), pos, 2),
				((BiPredicate<Level, BlockPos>) Feature::simpleDoubleWaterChecker)
						.and((BiPredicate<Level, BlockPos>) Feature::simpleDirtBelowChecker));
	}

	public static Feature verdantVine(VerdantVineBlock block) {
		return new Feature(VerdantVineBlock::placeVine, (feature, level, pos) -> pos.above(),
				VerdantVineBlock::canGrowToAnyFace);

	}

	public static Feature hanging(Block block, int requireAirRadius) {
		return new Feature((level, pos) -> level.setBlockAndUpdate(pos, block.defaultBlockState()),
				Feature::belowPlacement,
				Feature.withinHorizontalRangeBelowBlockCheckerProvider((state) -> !state.isAir(), requireAirRadius));
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

	// Returns true if the given position has air above it.
	public static boolean simpleAirAboveChecker(Level level, BlockPos pos) {
		return simpleAirChecker(level, pos.above());
	}

	// Returns true if the given position is dirt.
	public static boolean simpleDirtChecker(Level level, BlockPos pos) {
		return level.getBlockState(pos).is(BlockTags.DIRT);
	}

	// Returns true if the given position has dirt below it.
	public static boolean simpleDirtBelowChecker(Level level, BlockPos pos) {
		return simpleDirtChecker(level, pos.below());
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