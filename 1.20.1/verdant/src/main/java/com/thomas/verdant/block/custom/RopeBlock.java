package com.thomas.verdant.block.custom;

import com.thomas.verdant.entity.custom.ThrownRopeEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RopeBlock extends Block {

	private static final VoxelShape SHAPE = Block.box(7, 0, 7, 9, 16, 9);
	private static final VoxelShape LARGE_SHAPE = Block.box(4, 0, 4, 12, 16, 12);

	public RopeBlock(Properties properties) {
		super(properties);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	// Thrown ropes can hit this.
	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		if (context instanceof EntityCollisionContext entitycollisioncontext) {
			Entity entity = entitycollisioncontext.getEntity();

			if (entity instanceof ThrownRopeEntity thrownRope) {
				return LARGE_SHAPE;
			} else {
				return super.getCollisionShape(state, level, pos, entitycollisioncontext);
			}

		} else {
			return super.getCollisionShape(state, level, pos, context);
		}
	}

	protected boolean canAttachTo(BlockState state, LevelReader level, BlockPos pos) {
		return state.isFaceSturdy(level, pos, Direction.DOWN, SupportType.CENTER) || state.is(this);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos growingFrom = pos.relative(Direction.UP);
		BlockState growingOn = level.getBlockState(growingFrom);
		return this.canAttachTo(growingOn, level, growingFrom);
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {

		if (!this.canSurvive(state, level, pos)) {
			level.destroyBlock(pos, true);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState otherState, LevelAccessor level,
			BlockPos pos, BlockPos otherPos) {
		if (direction == Direction.UP && !state.canSurvive(level, pos)) {
			level.scheduleTick(pos, this, 1);
		}
		return super.updateShape(state, direction, otherState, level, pos, otherPos);
	}

	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult hitResult) {

		// Check if the user is holding this item.
		// If not, return.
		if (!player.getItemInHand(hand).is(this.asItem())) {
			return super.use(state, level, pos, player, hand, hitResult);
		}

		// Start scanning for blocks, if it is server side.
		boolean hasFound = false;
		BlockPos.MutableBlockPos scanPos = new BlockPos.MutableBlockPos().set(pos);
		while (level.getBlockState(scanPos).is(this)) {
			hasFound = true;
			scanPos.move(Direction.DOWN);
		}
		if (hasFound && level.getBlockState(scanPos).is(BlockTags.REPLACEABLE)) {
			if (level instanceof ServerLevel serverLevel) {
				serverLevel.setBlockAndUpdate(scanPos, this.defaultBlockState());
				serverLevel.addDestroyBlockEffect(scanPos, this.defaultBlockState());
				if (!player.getAbilities().instabuild) {
					if (hand == InteractionHand.MAIN_HAND) {
						player.getInventory().removeFromSelected(false);
					}
					else {
						player.getInventory().removeItem(Inventory.SLOT_OFFHAND, 1);
					}
				}
			}
			// On success
			return InteractionResult.sidedSuccess(level.isClientSide);
		}

		// On failure
		return super.use(state, level, pos, player, hand, hitResult);
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState placedOn, boolean simulate) {
		// if (!level.isClientSide) {
		// level.scheduleTick(pos, this, 1);
		// }
	}

}
