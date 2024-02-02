package com.thomas.zirconmod.block.custom;

import java.util.List;

import com.thomas.zirconmod.entity.custom.MoleEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SculkRootBlock extends Block {

	protected static final VoxelShape[] SHAPE = { Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D),
			Block.box(1.0D, 0.0D, 1.0D, 15.0D, 5.0D, 15.0D), Block.box(1.0D, 0.0D, 1.0D, 15.0D, 10.0D, 15.0D),
			Block.box(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D) };
	protected static final AABB[] TOUCH_SHAPE = { new AABB(0.0625D, 0.0D, 0.0625D, 0.9375D, 1.0D, 0.9375D),
			new AABB(0.0625D, 0.0D, 0.0625D, 0.9375D, 5.0D, 0.9375D),
			new AABB(0.0625D, 0.0D, 0.0625D, 0.9375D, 10.0D, 0.9375D),
			new AABB(0.0625D, 0.0D, 0.0625D, 0.9375D, 15.0D, 0.9375D) };
	public static final int MIN_STAGE = 0;
	public static final int MAX_STAGE = 3;
	public static final IntegerProperty STAGE = IntegerProperty.create("stage", MIN_STAGE, MAX_STAGE);
	public static final BooleanProperty READY = BooleanProperty.create("ready");

	public SculkRootBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState p_56620_, BlockGetter p_56621_, BlockPos p_56622_,
			CollisionContext p_56623_) {
		return SHAPE[p_56620_.getValue(STAGE)];
	}

	@Override
	public BlockState updateShape(BlockState p_49329_, Direction p_49330_, BlockState p_49331_, LevelAccessor p_49332_,
			BlockPos p_49333_, BlockPos p_49334_) {
		return p_49330_ == Direction.DOWN && !p_49329_.canSurvive(p_49332_, p_49333_) ? Blocks.AIR.defaultBlockState()
				: super.updateShape(p_49329_, p_49330_, p_49331_, p_49332_, p_49333_, p_49334_);
	}

	@Override
	public boolean canSurvive(BlockState p_49325_, LevelReader p_49326_, BlockPos p_49327_) {
		BlockPos blockpos = p_49327_.below();
		return canSupportRigidBlock(p_49326_, blockpos) || canSupportCenter(p_49326_, blockpos, Direction.UP);
	}

	protected int getCooldownTime() {
		return 5;
	}

	@Override
	public boolean isPossibleToRespawnInThis(BlockState p_279155_) {
		return false;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		level.scheduleTick(new BlockPos(pos), this, this.getCooldownTime());
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		// this.checkPressed((Entity) null, p_220769_, p_220770_, p_220768_, i);
		// Check if there are any entities in the block. If so, grow the roots.
		// If not, shrink the roots.

		// First, get the stage of the roots.
		int stage = state.getValue(STAGE);
		// System.out.println("Ticking with value " + stage);

		// Get the collision shape.
		AABB shape = TOUCH_SHAPE[stage].move(pos);

		// Get all the entities inside the collision shape.
		List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, shape);
		// System.out.println("Entities: " + entities);

		// See if there are any entities there at all.
		if (entities == null || entities.size() == 0) {
			// If there are no entities, shrink.
			state = state.setValue(STAGE, Math.max(stage - 1, MIN_STAGE));
			// Also schedule another tick if it's not at minimum growth.
			if (stage > MIN_STAGE) {
				level.scheduleTick(new BlockPos(pos), this, this.getCooldownTime());
			}
		} else {
			// Otherwise, grow.
			state = state.setValue(STAGE, Math.min(stage + 1, MAX_STAGE));

			// Now damage all the entities.
			for (Entity entity : entities) {
				if (!(entity instanceof MoleEntity) && !(entity instanceof Warden))
					entity.hurt(entity.damageSources().magic(), stage);
			}
		}

		// Set the block to be ready to grow.
		level.setBlock(pos, state.setValue(READY, true), 3);

	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {

		// If there is an entity inside, schedule a tick.
		if (!level.isClientSide) {
			// Only schedule a tick if the block is ready to grow.
			if (state.getValue(READY)) {
				// Set the block to be not ready to grow.
				level.setBlock(pos, state.setValue(READY, false), 3);
				level.scheduleTick(new BlockPos(pos), this, this.getCooldownTime());
			}
		}

	}

	@Override
	public void onRemove(BlockState p_49319_, Level p_49320_, BlockPos p_49321_, BlockState p_49322_,
			boolean p_49323_) {
		if (!p_49323_ && !p_49319_.is(p_49322_.getBlock())) {
			this.updateNeighbours(p_49320_, p_49321_);
			super.onRemove(p_49319_, p_49320_, p_49321_, p_49322_, p_49323_);
		}
	}

	protected void updateNeighbours(Level p_49292_, BlockPos p_49293_) {
		p_49292_.updateNeighborsAt(p_49293_, this);
		p_49292_.updateNeighborsAt(p_49293_.below(), this);
	}

	protected static int getEntityCount(Level p_289656_, AABB p_289647_, Class<? extends Entity> p_289686_) {
		return p_289656_.getEntitiesOfClass(p_289686_, p_289647_, EntitySelector.NO_SPECTATORS.and((p_289691_) -> {
			return !p_289691_.isIgnoringBlockTriggers();
		})).size();
	}

	// Very important!
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54447_) {
		p_54447_.add(STAGE).add(READY);
	}
}
