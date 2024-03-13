package com.thomas.zirconmod.block.custom;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.entity.ModEntityType;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NimbulaPolypBlock extends Block {

	public static final int MAX_AGE = 3;
	public static final int MIN_AGE = 0;
	private static final Map<Integer, VoxelShape> SHAPES = ImmutableMap.of(0,
			Block.box(5.0D, 3.0D, 5.0D, 11.0D, 7.0D, 11.0D), 1,
			Shapes.or(Block.box(4.0D, 0.0D, 4.0D, 12.0D, 3.0D, 4.0D), Block.box(5.0D, 3.0D, 5.0D, 11.0D, 5.0D, 11.0D),
					Block.box(6.0D, 5.0D, 6.0D, 10.0D, 6.0D, 10.0D)),
			2,
			Shapes.or(Block.box(2.0D, 0.0D, 2.0D, 14.0D, 1.0D, 14.0D), Block.box(4.0D, 2.0D, 4.0D, 12.0D, 8.0D, 12.0D)),
			3, Shapes.or(Block.box(2.0D, 0.0D, 2.0D, 14.0D, 1.0D, 14.0D),
					Block.box(3.0D, 1.0D, 3.0D, 13.0D, 3.0D, 13.0D), Block.box(4.0D, 2.0D, 4.0D, 12.0D, 16.0D, 12.0D)));

	public static final IntegerProperty AGE = IntegerProperty.create("age", MIN_AGE, MAX_AGE);

	public NimbulaPolypBlock(Properties properties) {
		super(properties);

	}

	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPES.get(state.getValue(AGE));
	}

	// Matures the polyp.
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		int age = state.getValue(AGE);
		if (age < MAX_AGE) {
			// Will only mature if there is a solid block or air or cloud beneath it.
			BlockState below_state = level.getBlockState(pos);
			if (below_state.isAir() || this.canSurvive(state, level, pos))
			{
				// Produces a small clump of clouds as well.
				addCloudClump(level, pos.below(), age);
				level.setBlockAndUpdate(pos, state.setValue(AGE, age + 1));
			}
		}
		
		// If the age is maxed out, then produce children.
		else if (age == MAX_AGE) {
			int count = level.random.nextInt(3) + 2;
			for (int i = 0; i < count; i++) {
				AgeableMob ageablemob = ModEntityType.NIMBULA_ENTITY.get().create(level);
				ageablemob.setBaby(true);
				ageablemob.moveTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 0.0F,
						(float) (level.random.nextFloat() * 6.28));
				level.addFreshEntityWithPassengers(ageablemob);
				level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
			}
		}
	}

	private void addCloudClump(ServerLevel level, BlockPos pos, int size) {

		// Adds a cloud at that exact position.
		addCloudSafe(level, pos);

		// Adds clouds around that position.
		for (int i = -size; i <= size; i++) {
			for (int j = -size; j <= 0; j++) {
				for (int k = -size; k <= size; k++) {
					int chance = Math.abs(i) + Math.abs(j) + Math.abs(k) + 1;
					if (level.random.nextInt(chance) < 2)
						addCloudSafe(level, pos.offset(i, j, k));
				}
			}
		}
	}

	private void addCloudSafe(ServerLevel level, BlockPos pos) {
		if (level.getBlockState(pos).isAir())
			CloudBlock.placeCloud(level, pos);
	}

	// Checks if the polyp can survive.
	@SuppressWarnings("deprecation")
	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return super.canSurvive(state, level, pos)
				&& (state.getValue(AGE) == 0 || (level.getBlockState(pos.below()).isFaceSturdy(level, pos, Direction.UP)
						|| level.getBlockState(pos.below()).is(ModBlocks.CLOUD.get())));
	}

	// Kills the polyp if it cannot survive.
	@SuppressWarnings("deprecation")
	public BlockState updateShape(BlockState state, Direction dir, BlockState state2, LevelAccessor level, BlockPos pos,
			BlockPos pos2) {
		return dir == Direction.DOWN && !this.canSurvive(state, level, pos) ? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, dir, state2, level, pos, pos2);
	}

	public BlockState getStateForPlacement(BlockPlaceContext p_54424_) {
		return this.defaultBlockState().setValue(AGE, MIN_AGE);
	}

	public IntegerProperty getAgeProperty() {
		return AGE;
	}

	// Very important!
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54447_) {
		p_54447_.add(AGE);
	}

}
