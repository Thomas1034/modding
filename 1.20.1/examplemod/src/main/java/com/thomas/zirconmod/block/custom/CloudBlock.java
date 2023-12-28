package com.thomas.zirconmod.block.custom;

import java.util.OptionalInt;

import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.effect.ModEffects;
import com.thomas.zirconmod.util.ModTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CloudBlock extends Block {

	public static final int MAX_DISTANCE = 15;
	public static final int MIN_DISTANCE = 0;

	public static final IntegerProperty SOLIDIFIER_DISTANCE = IntegerProperty.create("distance", MIN_DISTANCE,
			MAX_DISTANCE);

	// private static final VoxelShape FALLING_COLLISION_SHAPE = Shapes.box(0.0D,
	// 0.0D, 0.0D, 1.0D, (double) 0.9F, 1.0D);

	// private static final int TICK_DELAY = 1;

	public CloudBlock(Properties properties) {
		super(properties);
	}

	public boolean skipRendering(BlockState p_154268_, BlockState p_154269_, Direction p_154270_) {
		return false;
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType pathType) {
		/*
		 * boolean isSolid = (state.getValue(SOLIDIFIER_DISTANCE) < MAX_DISTANCE);
		 * switch (pathType) { case LAND: return !isSolid; case WATER: return !isSolid;
		 * case AIR: return !isSolid; default: return false; }
		 */
		return false;
	}

	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		if (context instanceof EntityCollisionContext entitycollisioncontext) {
			Entity entity = entitycollisioncontext.getEntity();
			// if (entity.fallDistance > 2.5F) {
			// return FALLING_COLLISION_SHAPE;
			// }

			boolean isSolid = state.getValue(SOLIDIFIER_DISTANCE) < MAX_DISTANCE;

			if (isSolid || canEntityWalkOnCloud(entity)) {
				return Shapes.block();
			}
		}

		return Shapes.empty();
	}

	public static boolean canEntityWalkOnCloud(Entity entity) {
		if (entity == null) {
			return false;
		}

		if (entity.getType().is(ModTags.EntityTypes.CLOUD_WALKABLE_MOBS)) {
			return true;

		} else {
			return entity instanceof LivingEntity
					? ((LivingEntity) entity).getItemBySlot(EquipmentSlot.FEET).is(ModTags.Items.CLOUD_WALKABLE_ITEMS)
							|| ((LivingEntity) entity).hasEffect(ModEffects.CITRINE_GLOW.get())
					: false;
		}
	}

	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		updateDistance(state, level, pos);
	}

	public void tick(BlockState p_221369_, ServerLevel p_221370_, BlockPos p_221371_, RandomSource p_221372_) {
		p_221370_.setBlock(p_221371_, updateDistance(p_221369_, p_221370_, p_221371_), 3);
	}

	public BlockState updateShape(BlockState state, Direction dir, BlockState otherState, LevelAccessor level,
			BlockPos pos, BlockPos otherPos) {
		int i = getDistanceAt(otherState) + 1;
		if (i != 1 || state.getValue(SOLIDIFIER_DISTANCE) != i) {
			level.scheduleTick(pos, this, 1);
		}

		return state;
	}

	public int getLightBlock(BlockState p_54460_, BlockGetter p_54461_, BlockPos p_54462_) {
		return 1;
	}

	private static BlockState updateDistance(BlockState state, LevelAccessor level, BlockPos pos) {
		int i = MAX_DISTANCE;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		for (Direction direction : Direction.values()) {
			blockpos$mutableblockpos.setWithOffset(pos, direction);
			i = Math.min(i, getDistanceAt(level.getBlockState(blockpos$mutableblockpos)) + 1);
			if (i == 1) {
				break;
			}
		}

		return state.setValue(SOLIDIFIER_DISTANCE, Integer.valueOf(i));
	}

	private static int getDistanceAt(BlockState state) {
		return getOptionalDistanceAt(state).orElse(MAX_DISTANCE);
	}

	public static OptionalInt getOptionalDistanceAt(BlockState state) {
		if (state.is(ModTags.Blocks.CLOUD_SOLIDIFYING_BLOCKS)) {
			int s = Math.max(MAX_DISTANCE - getSolidifyingStrength(state), 0);
			return OptionalInt.of(s);
		} else {
			// System.out.println("Not a solidifier!" + state.getBlock());
			return state.hasProperty(SOLIDIFIER_DISTANCE) ? OptionalInt.of(state.getValue(SOLIDIFIER_DISTANCE))
					: OptionalInt.empty();
		}
	}

	// Gets the projection distance that a block will solidify clouds for.
	// Checks the tags for solidifying strength.
	// If the block is not recognized, returns the maximum amount.
	public static int getSolidifyingStrength(BlockState state) {
		if (state.is(ModTags.Blocks.STRONG_CLOUD_SOLIDIFYING_BLOCKS)) {
			return MAX_DISTANCE;
		} else if (state.is(ModTags.Blocks.MEDIUM_CLOUD_SOLIDIFYING_BLOCKS)) {
			return (MAX_DISTANCE + 1) / 2;
		} else if (state.is(ModTags.Blocks.WEAK_CLOUD_SOLIDIFYING_BLOCKS)) {
			return (MAX_DISTANCE + 1) / 4;
		}
		return MAX_DISTANCE;
	}

	public BlockState getStateForPlacement(BlockPlaceContext p_54424_) {
		BlockState blockstate = this.defaultBlockState().setValue(SOLIDIFIER_DISTANCE, MAX_DISTANCE);
		return updateDistance(blockstate, p_54424_.getLevel(), p_54424_.getClickedPos());
	}

	public static void placeCloud(Level level, BlockPos pos) {
		BlockState blockstate = ModBlocks.CLOUD.get().defaultBlockState().setValue(SOLIDIFIER_DISTANCE, MAX_DISTANCE);
		blockstate = updateDistance(blockstate, level, pos);
		level.setBlockAndUpdate(pos, blockstate);
	}

	// Very important!
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54447_) {
		p_54447_.add(SOLIDIFIER_DISTANCE);
	}

}
