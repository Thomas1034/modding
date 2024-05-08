package com.thomas.verdant.block.custom;

import java.util.OptionalInt;

import com.thomas.verdant.growth.VerdantGrassGrower;
import com.thomas.verdant.growth.VerdantGrower;
import com.thomas.verdant.growth.VerdantHydratable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluids;

public class VerdantRootedDirtBlock extends Block implements VerdantGrower, VerdantHydratable {

	public VerdantRootedDirtBlock(Properties properties) {
		super(properties);
		
		this.registerDefaultState(this.stateDefinition.any().setValue(WATER_DISTANCE, MAX_DISTANCE));
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		super.randomTick(state, level, pos, rand);
		// System.out.println("Ticking at " + pos + " with " + state);
		if (state.getValue(WATER_DISTANCE) < MAX_DISTANCE) {
			state = VerdantHydratable.getHydrated(state);
			// System.out.println("Hydrating to " + state);
			level.setBlockAndUpdate(pos, state);
		} else {
			// System.out.println("Dehydrating to " + state);
			state = VerdantHydratable.getDehydrated(state);
			level.setBlockAndUpdate(pos, state);
		}

		// Check if grass can survive.
		if (!VerdantGrower.canBeGrass(state, level, pos)) {
			// System.out.println("Killing grass at " + pos);
			state = VerdantGrassGrower.getDegrass(state);
			level.setBlockAndUpdate(pos, state);
		} else {
			// System.out.println("Growing grass at " + pos);
			state = VerdantGrassGrower.getGrass(state);
			level.setBlockAndUpdate(pos, state);
		}

		// Grow.
		if (rand.nextFloat() < this.growthChance()) {
			// System.out.println("Trying to spread.");
			this.grow(state, level, pos);
		}
	}

	@Override
	public float growthChance() {
		return 1.0f;
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
		if (state.getFluidState().is(Fluids.WATER)) {
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
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54447_) {
		p_54447_.add(WATER_DISTANCE);
	}

	@Override
	public void grow(BlockState state, Level level, BlockPos pos) {
		// System.out.println("Verdant Roots are calling the grow function.");

		// Find a place to grow within three tries. Can use further tries to spread even more.
		for (int tries = 0; tries < 3; tries++) {
			// The range to check is constant.
			BlockPos posToTry = VerdantGrower.withinDist(pos, 3, level.random);
			// If it converted successfully, break.
			if (VerdantGrower.convert(level, posToTry, state.getValue(WATER_DISTANCE) < MAX_DISTANCE)) {
				// System.out.println("Successfully grew.");
				//break;
			} else {
				// Otherwise, try to erode it.
				// System.out.println("Failed to grow; eroding.");
				this.erode(level, posToTry, state.getValue(WATER_DISTANCE) < MAX_DISTANCE);
			}
		}
	}

}
