package com.thomas.cloudscape.block.custom;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CloudConverterBlock extends CloudBlock {

	public CloudConverterBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Shapes.block();
	}

	@Override
	public BlockState updateShape(BlockState state, Direction dir, BlockState otherState, LevelAccessor level,
			BlockPos pos, BlockPos otherPos) {
		int i = getDistanceAt(level, otherPos, otherState) + 1;
		if (i != 1 || state.getValue(SOLIDIFIER_DISTANCE) != i) {
			level.scheduleTick(pos, this, 1);
		}
		return state;
	}
	
	@Override
	public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction)
    {
        return true;
    }

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_54424_) {
		BlockState blockstate = this.defaultBlockState().setValue(SOLIDIFIER_DISTANCE, MAX_DISTANCE);
		return updateDistance(blockstate, p_54424_.getLevel(), p_54424_.getClickedPos());
	}

	@Override
	public void tick(BlockState p_221369_, ServerLevel p_221370_, BlockPos p_221371_, RandomSource p_221372_) {
		p_221370_.setBlock(p_221371_, updateDistance(p_221369_, p_221370_, p_221371_), 3);
	}

	// Sets the distance based on the block's redstone power.
	protected static int getDistanceAt(LevelAccessor level, BlockPos pos, BlockState state) {
		return 15 - level.getBestNeighborSignal(pos);
	}
	
	public static BlockState updateDistance(BlockState state, LevelAccessor level, BlockPos pos) {
		int dist = 15 - level.getBestNeighborSignal(pos);
		return state.setValue(SOLIDIFIER_DISTANCE, Integer.valueOf(dist));
	}

}
