package com.thomas.zirconmod.block.custom;

import java.util.List;
import java.util.function.BiFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
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

	private BiFunction<Level, BlockPos, Boolean> isSolid;

	public DirectionalPassageBlock(Properties properties, BiFunction<Level, BlockPos, Boolean> isSolid) {
		super(properties);
		this.isSolid = isSolid;
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType pathType) {
		return false;
	}

	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		if (context instanceof EntityCollisionContext entityCollisionContext) {
			Entity entity = entityCollisionContext.getEntity();

			if (entity != null) {

				Direction facing = state.getValue(FACING);

				Axis facingAxis = facing.getAxis();

				Vec3 entityPos = entity.position();

				Vec3 blockCenter = Vec3.atCenterOf(pos);

				Vec3 distFromCenter = blockCenter.subtract(entityPos);

				boolean canPass = !state.getValue(SOLID);

				if (facingAxis == Axis.X) {
					if (distFromCenter.x < 0 && facing.getAxisDirection() == AxisDirection.NEGATIVE
							|| distFromCenter.x > 0 && facing.getAxisDirection() == AxisDirection.POSITIVE) {
						canPass = true;
					}
				} else if (facingAxis == Axis.Y) {
					if (distFromCenter.y < 0 && facing.getAxisDirection() == AxisDirection.NEGATIVE
							|| distFromCenter.y > 0 && facing.getAxisDirection() == AxisDirection.POSITIVE) {
						canPass = true;
					}
				} else if (facingAxis == Axis.Z) {
					if (distFromCenter.z < 0 && facing.getAxisDirection() == AxisDirection.NEGATIVE
							|| distFromCenter.z > 0 && facing.getAxisDirection() == AxisDirection.POSITIVE) {
						canPass = true;
					}
				} else {
					throw new IllegalStateException("Unrecognized axis: " + facingAxis);
				}

				if (canPass) {
					return Shapes.empty();
				}

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
