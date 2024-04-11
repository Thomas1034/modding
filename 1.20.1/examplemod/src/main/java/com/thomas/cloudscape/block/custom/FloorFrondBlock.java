package com.thomas.cloudscape.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FloorFrondBlock extends Block {
	protected static final int AABB_STANDING_OFFSET = 2;
	protected static final VoxelShape AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);

	public FloorFrondBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return AABB;
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState state, Direction dir, BlockState state2, LevelAccessor level, BlockPos pos,
			BlockPos pos2) {
		return dir == Direction.DOWN && !this.canSurvive(state, level, pos) ? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, dir, state2, level, pos, pos2);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return canSupportCenter(level, pos.below(), Direction.UP);
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

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {

		if (random.nextInt(100) < 50) {
			return;
		}

		double d0 = (double) pos.getX() + 0.5D + random.nextDouble() * 4D - 2D;
		double d1 = (double) pos.getY() + 0.7D + random.nextDouble() * 4D - 2D;
		double d2 = (double) pos.getZ() + 0.5D + random.nextDouble() * 4D - 2D;

		ParticleOptions particle = ParticleTypes.SPORE_BLOSSOM_AIR;
		level.addParticle(particle, d0, d1, d2, random.nextDouble() * 0.2D - 0.1D, random.nextDouble() * 0.2D - 0.1D,
				random.nextDouble() * 0.2D - 0.1D);
	}

}
