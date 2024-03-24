package com.thomas.zirconmod.block.custom;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DirectionalPassageBlock extends DirectionalBlock {

	public static final BooleanProperty SOLID = BooleanProperty.create("solid");

	private static final VoxelShape UP_AABB = Block.box(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	private static final VoxelShape DOWN_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
	private static final VoxelShape WEST_AABB = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
	private static final VoxelShape EAST_AABB = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	private static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
	private static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
	private static final Map<Direction, VoxelShape> SHAPES = Map.of(Direction.UP, UP_AABB, Direction.DOWN, DOWN_AABB,
			Direction.WEST, WEST_AABB, Direction.EAST, EAST_AABB, Direction.NORTH, NORTH_AABB, Direction.SOUTH,
			SOUTH_AABB);

	private BiFunction<Level, BlockPos, Boolean> isSolid;

	public DirectionalPassageBlock(Properties properties, BiFunction<Level, BlockPos, Boolean> isSolid) {
		super(properties);
		this.isSolid = isSolid;
	}

	@Override
	public VoxelShape getVisualShape(BlockState p_48735_, BlockGetter p_48736_, BlockPos p_48737_,
			CollisionContext p_48738_) {
		return Shapes.empty();
	}

	@Override
	public float getShadeBrightness(BlockState p_48731_, BlockGetter p_48732_, BlockPos p_48733_) {
		return 1.0F;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState p_48740_, BlockGetter p_48741_, BlockPos p_48742_) {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean skipRendering(BlockState p_53972_, BlockState p_53973_, Direction p_53974_) {
		return p_53973_.is(this) ? true : super.skipRendering(p_53972_, p_53973_, p_53974_);
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType pathType) {
		return false;
	}

	@Override
	public VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
		return Shapes.empty();
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		if (context instanceof EntityCollisionContext entityCollisionContext) {
			Entity entity = entityCollisionContext.getEntity();

			if (!state.getValue(SOLID)) {
				return Shapes.empty();
			}

			if (entity != null) {

				Direction facing = state.getValue(FACING);
				Axis axis = facing.getAxis();

				Vec3 entityPos = entity.position();

				Vec3 blockCenter = Vec3.atCenterOf(pos);
				Vec3 distFromCenter = blockCenter.subtract(entityPos);
				double radiusSum = 0.5 + entity.getBbWidth() / 2;
				// Check if the entity is inside the block. If so, return true.
				if (axis.isHorizontal()) {
					if (Math.abs(distFromCenter.x) < radiusSum && Math.abs(distFromCenter.z) < radiusSum) {
						return Shapes.empty();
					} else {
						return SHAPES.get(facing);
					}
				} else if (facing == Direction.UP) {
					if (Math.abs(distFromCenter.x) < radiusSum && (distFromCenter.y > -0.5)
							&& Math.abs(distFromCenter.z) < radiusSum) {
						return Shapes.empty();
					} else {
						return SHAPES.get(facing);
					}
				} else if (facing == Direction.DOWN) {
					if (Math.abs(distFromCenter.x) < radiusSum && (distFromCenter.y < (0.5 + entity.getBbHeight()))
							&& Math.abs(distFromCenter.z) < radiusSum) {
						return Shapes.empty();
					} else {
						return SHAPES.get(facing);
					}
				}

				/*
				 * if (facingAxis == Axis.X) { if (distFromCenter.x < 1 + EPSILON &&
				 * facing.getAxisDirection() == AxisDirection.NEGATIVE || distFromCenter.x > -(1
				 * + EPSILON) && facing.getAxisDirection() == AxisDirection.POSITIVE) { canPass
				 * = true; } } else if (facingAxis == Axis.Y) { if (distFromCenter.y < 1 +
				 * EPSILON && facing.getAxisDirection() == AxisDirection.NEGATIVE ||
				 * distFromCenter.y > -(1 + EPSILON) && facing.getAxisDirection() ==
				 * AxisDirection.POSITIVE) { canPass = true; } } else if (facingAxis == Axis.Z)
				 * { if (distFromCenter.z < 1 + EPSILON && facing.getAxisDirection() ==
				 * AxisDirection.NEGATIVE || distFromCenter.z > -(1 + EPSILON) &&
				 * facing.getAxisDirection() == AxisDirection.POSITIVE) { canPass = true; } }
				 * else { throw new IllegalStateException("Unrecognized axis: " + facingAxis); }
				 */
			}

		}
		return Shapes.block();
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		level.setBlockAndUpdate(pos, updateSolidity(level, pos));
	}

	@Override
	public BlockState updateShape(BlockState state, Direction dir, BlockState otherState, LevelAccessor level,
			BlockPos pos, BlockPos otherPos) {
		level.scheduleTick(pos, this, 1);
		return updateSolidity(level, pos);
	}

	private BlockState updateSolidity(LevelAccessor level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		try {
			List<? extends Player> players = level.players();
			if (players.size() > 0) {
				return state.setValue(SOLID, isSolid.apply(players.get(0).level(), pos));
			} else {
				return state;
			}
		} catch (Exception e) {
			System.out.println("ZirconMod experienced an error in DirectionalPassageBlock.java");
			e.printStackTrace();
		}
		return state;

	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
		stateBuilder.add(FACING, SOLID);
	}

	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState blockstate = this.defaultBlockState()
				.setValue(FACING, context.getNearestLookingDirection().getOpposite())
				.setValue(SOLID, isSolid.apply(context.getLevel(), context.getClickedPos()));
		return blockstate;
	}

}
