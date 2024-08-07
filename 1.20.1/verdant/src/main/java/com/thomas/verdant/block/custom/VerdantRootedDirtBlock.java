package com.thomas.verdant.block.custom;

import java.util.ArrayList;
import java.util.OptionalInt;

import javax.annotation.Nullable;

import com.thomas.verdant.growth.VerdantBlockTransformer;
import com.thomas.verdant.growth.VerdantGrower;
import com.thomas.verdant.modfeature.FeaturePlacer;
import com.thomas.verdant.util.FallingBlockHelper;
import com.thomas.verdant.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
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

	public static final int MAX_DISTANCE = 15;
	public static final int MIN_DISTANCE = 0;

	public static final IntegerProperty WATER_DISTANCE = IntegerProperty.create("water_distance", MIN_DISTANCE,
			MAX_DISTANCE);

	public VerdantRootedDirtBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(WATER_DISTANCE, MAX_DISTANCE));
	}

	@Nullable
	private static BlockPos findSettleLocation(ServerLevel level, BlockPos pos) {

		ArrayList<BlockPos> locations = new ArrayList<>();
		for (int i = -1; i <= 1; i++) {
			for (int k = -1; k <= 1; k++) {
				if (level.getBlockState(pos.offset(i, -1, k)).is(Blocks.AIR)
						&& level.getBlockState(pos.offset(i, 0, k)).is(Blocks.AIR)) {
					locations.add(pos.offset(i, -1, k));
				}
			}
		}

		if (locations.size() > 0) {
			return locations.get(level.random.nextInt(locations.size()));
		}
		return null;
	}

	private static boolean canSettle(ServerLevel level, BlockPos pos) {

		// If it has no block above it and no block below it, it should certainly
		// settle.
		BlockState above = level.getBlockState(pos.above());
		BlockState below = level.getBlockState(pos.below());
		if (above.is(Blocks.AIR) && below.is(BlockTags.REPLACEABLE)) {
			return true;
		}

		// If it has a block above it and a block below it, don't settle.
		if (!above.is(BlockTags.REPLACEABLE) && !below.is(Blocks.AIR)) {
			return false;
		}

		// Check if it's supported from its sides.
		// Keep track of how many sides can support.
		int solidSides = 0;
		// Loop over the horizontal directions.
		for (Direction d : Utilities.HORIZONTAL_DIRECTIONS) {
			// Check if the block in that direction has a sturdy face.
			// If so, don't settle.
			// Also don't settle if the block in that direction has a liquid.
			if (level.getBlockState(pos.relative(d)).isFaceSturdy(level, pos, d.getOpposite())
					|| !level.getFluidState(pos.relative(d)).isEmpty()) {
				// Found a solid side.
				solidSides++;
				// If it's supported by at least two sides, it's good.
				if (solidSides >= 2) {
					return false;
				}
			}
		}

		return true;
	}

	// Removes the block if it is not supported from the sides.
	private boolean settle(ServerLevel level, BlockPos pos) {
		// Check if the dirt should settle.
		boolean shouldSettle = canSettle(level, pos);

		if (shouldSettle) {
			// If it has a block beneath it, try to find a place to move this block to.
			if (!level.getBlockState(pos.below()).is(Blocks.AIR)) {
				// Find the location.
				BlockPos settleLocation = findSettleLocation(level, pos);
				// Move the block if one was found.
				if (settleLocation != null) {
					level.setBlockAndUpdate(settleLocation, level.getBlockState(pos));
					// If it's not supported underneath, fall.
					if (level.getBlockState(settleLocation.below()).isFaceSturdy(level, pos, Direction.UP)) {
						FallingBlockHelper.fallNoDrops(level, settleLocation);
					}
				}
				// Destroy it.
				level.destroyBlock(pos, false);
			}
			// Otherwise fall.
			else {
				FallingBlockHelper.fallNoDrops(level, pos);
			}
		}

		return shouldSettle;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		// super.randomTick(state, level, pos, rand);
		this.tick(state, level, pos, rand);
		// System.out.println("Ticking at " + pos + " with " + state);
		boolean isLowDistance = state.getValue(WATER_DISTANCE) < MAX_DISTANCE;
		if (isLowDistance && VerdantBlockTransformer.HYDRATE.get().hasInput(state.getBlock())) {
			state = VerdantBlockTransformer.HYDRATE.get().next(state);
			// System.out.println("Hydrating to " + state);
			level.setBlockAndUpdate(pos, state);
		} else if (!isLowDistance && VerdantBlockTransformer.DEHYDRATE.get().hasInput(state.getBlock())) {
			// System.out.println("Dehydrating to " + state);
			state = VerdantBlockTransformer.DEHYDRATE.get().next(state);
			level.setBlockAndUpdate(pos, state);
		}

		// Check if grass can survive.
		boolean canBeGrass = VerdantGrower.canBeGrass(state, level, pos);
		if (!canBeGrass && VerdantBlockTransformer.REMOVE_GRASSES.get().hasInput(state.getBlock())) {
			// System.out.println("Killing grass at " + pos);
			// System.out.println("The input light blockedness level is " +
			// LightEngine.getLightBlockInto(level, state, pos,
			// level.getBlockState(pos.above()), pos.above(), Direction.UP,
			// level.getBlockState(pos.above()).getLightBlock(level, pos.above())));
			// System.out.println("Because the block above is " +
			// level.getBlockState(pos.above()));
			state = VerdantBlockTransformer.REMOVE_GRASSES.get().next(state);
			level.setBlockAndUpdate(pos, state);
		} else if (canBeGrass && VerdantBlockTransformer.GROW_GRASSES.get().hasInput(state.getBlock())) {
			// System.out.println("Growing grass at " + pos);
			// System.out.println("The input light blockedness level is " +
			// LightEngine.getLightBlockInto(level, state, pos,
			// level.getBlockState(pos.above()), pos.above(), Direction.UP,
			// level.getBlockState(pos.above()).getLightBlock(level, pos.above())));
			// System.out.println("Because the block above is " +
			// level.getBlockState(pos.above()));
			state = VerdantBlockTransformer.GROW_GRASSES.get().next(state);
			level.setBlockAndUpdate(pos, state);
		}

		// Settle
		if (!this.settle(level, pos)) {
			// Grow.
			float growthChance = this.growthChance(level);
			float randomChance = rand.nextFloat();
			while (randomChance < growthChance) {
				// System.out.println("Trying to spread.");
				this.grow(state, level, pos);
				growthChance--;
			}
		}

	}

	@Override
	public float growthChance(Level level) {
		return 1.0f * VerdantGrower.super.growthChance(level);
	}

	// Handles hydration
	@Override
	public void tick(BlockState p_221369_, ServerLevel p_221370_, BlockPos p_221371_, RandomSource p_221372_) {
		p_221370_.setBlock(p_221371_, updateDistance(p_221369_, p_221370_, p_221371_), 3);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction dir, BlockState otherState, LevelAccessor level,
			BlockPos pos, BlockPos otherPos) {
		int dist = getDistanceAt(level, otherPos, otherState) + 1;
		if (dist != 1 || state.getValue(WATER_DISTANCE) != dist) {
			level.scheduleTick(pos, this, 1);
		}

		return state;
	}

	public static BlockState updateDistance(BlockState state, LevelAccessor level, BlockPos pos) {
		int dist = MAX_DISTANCE;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

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

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState blockstate = this.defaultBlockState().setValue(WATER_DISTANCE, MAX_DISTANCE);
		return updateDistance(blockstate, context.getLevel(), context.getClickedPos());
	}

	// Very important!
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATER_DISTANCE);
	}

	@Override
	public void grow(BlockState state, Level level, BlockPos pos) {
		// System.out.println("Verdant Roots are calling the grow function.");

		// Find a place to grow within three tries. Can use further tries to spread even
		// more.
		// Check for surrounding roots.
		boolean canGrow = VerdantGrower.checkForRoots(level, pos);
		for (int tries = 0; tries < 3; tries++) {
			// The range to check is constant.
			BlockPos posToTry = VerdantGrower.withinDist(pos, 3, level.random);
			// Ensure that this state is still hydratable.
			if (!state.hasProperty(WATER_DISTANCE)) {
				continue;
			}

			// Try to convert the nearby block.
			boolean canBeGrass = VerdantGrower.canBeGrass(state, level, posToTry);
			if ((canGrow || canBeGrass) && VerdantGrower.convertGround(level, posToTry)) {
				// System.out.println("Successfully grew.");
				// break;
			}
			// If that fails, try to erode that block.
			else {
				// Otherwise, try to erode it.
				// System.out.println("Failed to grow; eroding.");
				this.erode(level, posToTry, state.getValue(WATER_DISTANCE) < MAX_DISTANCE);
			}
		}

		// Try to grow vegetation.
		FeaturePlacer.place(level, pos);
	}

	@Nullable
	public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction,
			boolean simulate) {
		ItemStack itemStack = context.getItemInHand();
		if (!itemStack.canPerformAction(toolAction))
			return null;

		if (ToolActions.HOE_TILL == toolAction) {
			// Logic modified super call.
			Block block = state.getBlock();
			if (VerdantBlockTransformer.HOEING.get().hasInput(block)) {
				return VerdantBlockTransformer.HOEING.get().next(state);
			}
		}

		return super.getToolModifiedState(state, context, toolAction, simulate);
	}

}
