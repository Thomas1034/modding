package com.thomas.verdant.block.custom;

import java.util.ArrayList;
import java.util.OptionalInt;

import javax.annotation.Nullable;

import com.thomas.verdant.growth.VerdantBlockTransformer;
import com.thomas.verdant.growth.VerdantGrower;
import com.thomas.verdant.modfeature.FeaturePlacer;
import com.thomas.verdant.util.blocktransformers.BlockTransformer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class VerdantRootedDirtBlock extends Block implements VerdantGrower {

	// The maximum distance the block can be from water.
	public static final int MAX_DISTANCE = 15;
	// The minimum distance the block can be from water.
	public static final int MIN_DISTANCE = 0;

	// A property storing how far the block is from the nearest water source.
	public static final IntegerProperty WATER_DISTANCE = IntegerProperty.create("water_distance", MIN_DISTANCE,
			MAX_DISTANCE);

	public VerdantRootedDirtBlock(Properties properties) {
		super(properties);
		// Registers the default state to be far away from water.
		this.registerDefaultState(this.stateDefinition.any().setValue(WATER_DISTANCE, MAX_DISTANCE));
	}

	// If the block is going to settle, finds a location for it to settle to.
	@Nullable
	private static BlockPos findSettleLocation(ServerLevel level, BlockPos pos) {

		// Keep a list of all valid locations.
		ArrayList<BlockPos> locations = new ArrayList<>();
		// Iterate over the 3x3 grid around it.
		for (int i = -1; i <= 1; i++) {
			for (int k = -1; k <= 1; k++) {
				// At each position, check if the block has a clear path to fall to the y-level
				// beneath it.
				BlockPos offsetBelow = pos.offset(i, -1, k);
				if (level.getBlockState(offsetBelow).is(Blocks.AIR)
						&& level.getBlockState(offsetBelow.above()).is(Blocks.AIR)) {
					locations.add(offsetBelow);
				}
			}
		}

		// If there is a place to fall to (or more than one) return one at random.
		if (locations.size() > 0) {
			return locations.get(level.random.nextInt(locations.size()));
		}

		// Return null if no place to fall to was found.
		return null;
	}

	// The heart and soul of the lag. Block! I meant block.
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {

		// Check if grass can survive.
		boolean canBeGrass = VerdantGrower.canBeGrass(level, pos);
		if (!canBeGrass && VerdantBlockTransformer.REMOVE_GRASSES.get().hasInput(state.getBlock())) {
			// If it cannot be grass, and grass can be removed, set the state and update the
			// block.
			state = VerdantBlockTransformer.REMOVE_GRASSES.get().next(state);
		} else if (canBeGrass && VerdantBlockTransformer.GROW_GRASSES.get().hasInput(state.getBlock())) {
			// If it can be grass and can be turned into grass, set the state and update the
			// block.
			state = VerdantBlockTransformer.GROW_GRASSES.get().next(state);
		}
		level.setBlockAndUpdate(pos, state);

		float growthChance = this.growthChance(level);
		float randomChance = rand.nextFloat();
		// If the growth chance is really high, it might grow repeatedly.
		// Unlikely, though, unless the user sets it really high.
		while (randomChance < growthChance) {
			// System.out.println("Trying to spread.");
			this.grow(state, level, pos);
			growthChance--;
		}

	}

	// This just gets the base growth chance from the game rule.
	@Override
	public float growthChance(Level level) {
		return 1.0f * VerdantGrower.super.growthChance(level);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction dir, BlockState otherState, LevelAccessor level,
			BlockPos pos, BlockPos otherPos) {
		// Unfortunately I can't use any fancy tricks.
		state = updateDistance(state, level, pos);
		return updateHydrationState(state, state.getValue(WATER_DISTANCE));
	}

	private static BlockState updateHydrationState(BlockState state, int distanceToWater) {
		BlockTransformer hydrationTransformer = VerdantBlockTransformer.HYDRATE.get();
		BlockTransformer dehydrationTransformer = VerdantBlockTransformer.DEHYDRATE.get();
		Block block = state.getBlock();
		boolean isLowDistance = distanceToWater < MAX_DISTANCE;
		if (isLowDistance && hydrationTransformer.hasInput(block)) {
			// If it is hydrated, and the data-driven map says it should turn into something
			// when hydrated,
			// set the state and update the block.
			return hydrationTransformer.next(state);
		} else if (!isLowDistance && dehydrationTransformer.hasInput(block)) {
			// If it is not hydrated, and the data-driven map says it should turn into
			// something when dehydrated,
			// set the state and update the block.
			return dehydrationTransformer.next(state);
		}
		return state;
	}

	// Returns the updated state's water distance, given its position, by checking
	// its neighbors.
	// Mostly just copied from LeafBlock
	public static BlockState updateDistance(BlockState state, LevelAccessor level, BlockPos pos) {
		int dist = MAX_DISTANCE;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		// Iterates over each directly adjacent block, checking its distance from water.
		// Should be less laggy, but doesn't count corners.
		for (Direction direction : Direction.values()) {
			blockpos$mutableblockpos.setWithOffset(pos, direction);
			dist = Math.min(dist,
					getDistanceAt(level, blockpos$mutableblockpos, level.getBlockState(blockpos$mutableblockpos)) + 1);
			if (dist == 1) {
				break;
			}
		}

		return state.setValue(WATER_DISTANCE, Integer.valueOf(dist));
	}

	protected static int getDistanceAt(LevelAccessor level, BlockPos pos, BlockState state) {
		return getOptionalDistanceAt(state).orElse(MAX_DISTANCE);
	}

	private static OptionalInt getOptionalDistanceAt(BlockState state) {
		if (state.getFluidState().is(FluidTags.WATER)) {
			return OptionalInt.of(0);
		} else {
			return state.hasProperty(WATER_DISTANCE) ? OptionalInt.of(state.getValue(WATER_DISTANCE))
					: OptionalInt.empty();
		}
	}

	// Boring. Just sets the default state for placement to be the correct one for
	// the given position.
	// Does not contribute significantly to lag.
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState blockstate = this.defaultBlockState();
		blockstate = updateDistance(blockstate, context.getLevel(), context.getClickedPos());
		return updateHydrationState(blockstate, blockstate.getValue(WATER_DISTANCE));
	}

	// Very important!
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATER_DISTANCE);
	}

	// This function causes the most lag.
	@Override
	public void grow(BlockState state, Level level, BlockPos pos) {
		// System.out.println("Verdant Roots are calling the grow function.");

		boolean isWet = state.getValue(WATER_DISTANCE) < MAX_DISTANCE;
		for (int tries = 0; tries < 3; tries++) {
			// The range to check is constant.
			BlockPos posToTry = VerdantGrower.withinDist(pos, 2, level.random);
			// Ensure that this state is still hydratable.
			if (!state.hasProperty(WATER_DISTANCE)) {
				break;
			}
			// Try to convert the nearby block.
			if (!VerdantGrower.convertOrErodeGround(level, posToTry, isWet)) {
				// Try to grow vegetation.
				// This is a big source of lag, which I will cover separately.
				FeaturePlacer.place(level, pos);
				break;
			}
		}
	}

	@Nullable
	public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction,
			boolean simulate) {
		// Basic sanity-check from super call.
		ItemStack itemStack = context.getItemInHand();
		if (!itemStack.canPerformAction(toolAction))
			return null;

		// Use custom logic for hoeing.
		if (ToolActions.HOE_TILL == toolAction) {
			// Logic modified super call.
			Block block = state.getBlock();
			// This is my fancy shmancy deferred-registry style data handling.
			// This won't cause too much lag (hopefully)! since it's just checking if a map
			// has a key
			if (VerdantBlockTransformer.HOEING.get().hasInput(block)) {
				// Again, using the fancy data driven stuff to transfer over as many block state
				// properties as possible.
				return VerdantBlockTransformer.HOEING.get().next(state);
			}
		}

		return super.getToolModifiedState(state, context, toolAction, simulate);
	}
}
