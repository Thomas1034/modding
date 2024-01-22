package com.thomas.zirconmod.worldgen.custom;

import java.util.stream.Stream;

import com.mojang.serialization.Codec;
import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.block.custom.FrondBlock;
import com.thomas.zirconmod.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.phys.AABB;

public class OasisFeature extends Feature<NoneFeatureConfiguration> {
	private static final BlockStatePredicate IS_SAND = BlockStatePredicate.forBlock(Blocks.SAND);
	private final static BlockState SAND = Blocks.SAND.defaultBlockState();
	private final static BlockState SAND_SLAB = Blocks.SANDSTONE_SLAB.defaultBlockState();
	private final static BlockState SANDSTONE = Blocks.SANDSTONE.defaultBlockState();
	private final static BlockState WATER = Blocks.WATER.defaultBlockState();
	private final static LakeFeature.Configuration LAKE_CONFIG = new LakeFeature.Configuration(
			BlockStateProvider.simple(WATER), BlockStateProvider.simple(SANDSTONE));
	private final static LakeFeature LAKE_GENERATOR = new LakeFeature(LAKE_CONFIG.CODEC);

	public OasisFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	// Places an oasis.
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		ChunkGenerator chunks = context.chunkGenerator();
		System.out.println("placing oasis at: " + pos);
		int type = level.getRandom().nextInt(0, 10);

		if (type < 0) {

			placeDeepOasis(level, chunks, pos.below());

		} else {

			placeSimpleOasis(level, chunks, pos.below());
		}

		return true;

	}

	// Places a simple oasis.
	// First, generates a random radius.
	// Then, places puddles of water around that radius.
	// Finally, places a ring of palm trees around the water.
	public static void placeSimpleOasis(WorldGenLevel level, ChunkGenerator chunks, BlockPos pos) {

		RandomSource rand = level.getRandom();

		// The radius to place in.
		int radius = rand.nextInt(3, 7);
		placePuddles(level, pos, 0, radius + 1);
		placePalmTrees(level, pos, radius - 2, 2 * radius - 2);
	}

	// Places a simple oasis.
	// First, places a lake.
	// Then, places a ring of palm trees around the water.
	public static void placeDeepOasis(WorldGenLevel level, ChunkGenerator chunks, BlockPos pos) {

		RandomSource rand = level.getRandom();

		// The radius to place in.
		int radius = rand.nextInt(5, 8);
		LAKE_GENERATOR.place(LAKE_CONFIG, level, chunks, rand, pos.offset(-8, 0, -8));
		placePalmTrees(level, pos, radius - 2, 2 * radius - 2);
		placePalmTrees(level, pos, radius - 2, 2 * radius - 2);
	}

	// Places palm trees in a ring around the position.
	// minRadius is the radius of the inside of the ring.
	// maxRadius is the radius of the outside of the ring.
	// pos should be a level below the base of the tree trunks.
	public static void placePalmTrees(LevelAccessor level, BlockPos pos, int minRadius, int maxRadius) {

		RandomSource rand = level.getRandom();

		int count = maxRadius + minRadius;

		// Now place trees.
		for (int i = 0; i < count; i++) {
			// Get the offset position.
			BlockPos offset = Utilities.withinCircle(rand, minRadius, maxRadius);
			BlockPos treePos = pos.offset(offset);

			placePalmTree(level, Utilities.findSurface(level, treePos));
		}
	}

	// Places single-deep water puddles in a ring around the position.
	// minRadius is the radius of the inside of the ring.
	// maxRadius is the radius of the outside of the ring.
	// pos should be at the desired water level.
	public static void placePuddles(LevelAccessor level, BlockPos pos, int minRadius, int maxRadius) {

		RandomSource rand = level.getRandom();

		// The maximum density of the puddles.
		double density = rand.nextDouble() * 0.5 + 0.5;
		// Get how many puddles to place, based on the density and radius.
		int count = (int) Math.round(density * Math.PI * (maxRadius * maxRadius - minRadius * minRadius));

		// Now place puddles.
		for (int i = 0; i < count; i++) {
			// Get the offset position.
			BlockPos offset = Utilities.withinCircle(rand, minRadius, maxRadius);
			BlockPos puddlePos = pos.offset(offset);

			placePuddle(level, Utilities.findSurface(level, puddlePos));
		}
	}

	private static void placeSusSand(LevelAccessor level, BlockPos pos) {
		level.setBlock(pos, Blocks.SUSPICIOUS_SAND.defaultBlockState(), 3);
		level.getBlockEntity(pos, BlockEntityType.BRUSHABLE_BLOCK).ifPresent((entity) -> {
			entity.setLootTable(BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY, pos.asLong());
		});
	}

	// Places a puddle below the specified position, if it is valid.
	// Has a chance of placing suspicious sand under it.
	// It is valid if it has sides and a bottom, and nothing above it.
	public static void placePuddle(LevelAccessor level, BlockPos pos) {
		pos = pos.below();
		boolean above = level.getBlockState(pos.above()).isAir();
		boolean north = !level.getBlockState(pos.north()).isAir();
		boolean east = !level.getBlockState(pos.east()).isAir();
		boolean south = !level.getBlockState(pos.south()).isAir();
		boolean west = !level.getBlockState(pos.west()).isAir();
		boolean below = !level.getBlockState(pos.below()).isAir();

		if (above && north && east && south && west && below && Utilities.canReplaceBlockAt(level, pos)) {
			level.setBlock(pos, WATER, 3);

			if (level.getRandom().nextInt(0, 5) == 0) {
				placeSusSand(level, pos.below());
			}
		}
	}

	// Places a palm tree at the specified position, if it is valid.
	// The specified position will be the bottom of the trunk.
	public static void placePalmTree(LevelAccessor level, BlockPos pos) {

		BlockState stateBelow = level.getBlockState(pos.below());

		// First, check if the tree can survive. If not, return.
		if (!(stateBelow.is(BlockTags.DIRT) || stateBelow.is(Blocks.FARMLAND) || stateBelow.is(BlockTags.SAND))) {
			System.out.println("returning early since no good dirt, " + stateBelow + " at " + pos);
			return;
		}

		BlockState trunk = ModBlocks.PALM_TRUNK.get().defaultBlockState();
		BlockState floorFrond = ModBlocks.PALM_FLOOR_FROND.get().defaultBlockState();
		BlockState frond = ModBlocks.PALM_FROND.get().defaultBlockState();

		RandomSource rand = level.getRandom();

		// Gets the maximum height at that position for a radius-1 box.
		int maxHeight = getMaxHeight(1, 15, level, pos);

		// If the maximum height is less than 3, return.
		if (maxHeight < 3) {
			System.out.println("returning early due to too short.");
			return;
		}

		// Set the max height to a hard cap of 15, or whichever is lower.
		maxHeight = Math.min(maxHeight, 15);

		// Set the min height to 3 or the max height - 7, whichever is higher.
		int minHeight = Math.max(3, maxHeight - 7);

		// Now pick a random number between the two for the height.
		int height = rand.nextIntBetweenInclusive(minHeight, maxHeight);

		// Place the tree.

		// Places the trunk.
		for (int i = 0; i <= height - 2; i++) {
			level.setBlock(pos.above(i), trunk, 3);
		}

		// Resets pos to the top of the tree.
		pos = pos.above(height - 2);

		// Places the fronds.
		level.setBlock(pos.above(1), floorFrond, 3);
		// Place each row of fronds.
		int numRows = 1 + ((rand.nextBoolean() && height > 3) ? 1 : 0);
		for (int i = 0; i < numRows; i++) {
			// Place fronds on each direction.
			for (Direction dir : Direction.values()) {
				// Skip the vertical axis.
				if (dir.getAxis() == Axis.Y)
					continue;
				// Get the location of the frond.
				BlockPos at = pos.relative(dir).below(i);
				// Get the state of the frond.
				BlockState placedFrond = frond.setValue(FrondBlock.getFacingProperty(), dir)
						.setValue(FrondBlock.getCountProperty(), 3);
				// Place the frond.
				level.setBlock(at, placedFrond, 3);
			}
		}

		System.out.println("succeeded placing tree at " + pos);

	}

	// Gets the maximum height that a (2n+1)x(2n+1) box can have centered at the
	// given
	// position without running into another solid block.
	private static int getMaxHeight(int n, int cap, LevelAccessor level, BlockPos pos) {
		AABB boundingBox = new AABB(pos.offset(-n, 0, -n), pos.offset(n, 1, n));
		Stream<BlockState> blocks = level.getBlockStates(boundingBox);
		int height = 0;
		while (blocks.allMatch((state) -> state.isAir()) && height < cap) {
			boundingBox = new AABB(pos.offset(-n, height, -n), pos.offset(n, height + 1, n));
			blocks = level.getBlockStates(boundingBox);
			height++;
		}
		return height;
	}

}
