package com.thomas.verdant.block.custom;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

// ADD CONNECTED TEXTURES when able to hang from sides!

public class HangingLadderBlock extends LadderBlock {

	public static final BooleanProperty UP = BooleanProperty.create("up");
	public static final BooleanProperty LEFT = BooleanProperty.create("left");
	public static final BooleanProperty RIGHT = BooleanProperty.create("right");

	public HangingLadderBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockState updateShape(BlockState thisState, Direction towardThat, BlockState thatState, LevelAccessor level,
			BlockPos thisPosition, BlockPos thatPosition) {
		if (!this.canSurvive(thisState, level, thisPosition)) {
			return Blocks.AIR.defaultBlockState();
		} else {
			if (thisState.getValue(WATERLOGGED)) {
				level.scheduleTick(thisPosition, Fluids.WATER, Fluids.WATER.getTickDelay(level));
			}

			// Update the directional stuff.
			Direction facing = thisState.getValue(LadderBlock.FACING);

			BlockState above = level.getBlockState(thisPosition.above());
			BlockState left = level.getBlockState(thisPosition.relative(facing.getClockWise()));
			BlockState right = level.getBlockState(thisPosition.relative(facing.getCounterClockWise()));

			if (above.hasProperty(LadderBlock.FACING) && facing == above.getValue(LadderBlock.FACING)) {
				thisState = thisState.setValue(HangingLadderBlock.UP, true);
			} else {
				thisState = thisState.setValue(HangingLadderBlock.UP, false);
			}
			if (left.hasProperty(LadderBlock.FACING) && facing == left.getValue(LadderBlock.FACING)) {
				thisState = thisState.setValue(HangingLadderBlock.LEFT, true);
			} else {
				thisState = thisState.setValue(HangingLadderBlock.LEFT, false);
			}
			if (right.hasProperty(LadderBlock.FACING) && facing == right.getValue(LadderBlock.FACING)) {
				thisState = thisState.setValue(HangingLadderBlock.RIGHT, true);
			} else {
				thisState = thisState.setValue(HangingLadderBlock.RIGHT, false);
			}

			return thisState;
		}
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		boolean sup = super.canSurvive(state, level, pos);
		if (sup) {
			return true;
		} else {
			Direction facing = state.getValue(FACING);
			BlockState above = level.getBlockState(pos.above());
			BlockState aside = level.getBlockState(pos.relative(facing.getClockWise()));
			BlockState bside = level.getBlockState(pos.relative(facing.getCounterClockWise()));

			Optional<Direction> dir = above.getOptionalValue(FACING);
			Optional<Direction> adir = aside.getOptionalValue(FACING);
			Optional<Direction> bdir = bside.getOptionalValue(FACING);

			return (dir.isPresent() && (dir.get() == state.getValue(FACING)))
					|| ((adir.isPresent() && (adir.get() == state.getValue(FACING)))
							&& (bdir.isPresent() && (bdir.get() == state.getValue(FACING))));
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult hitResult) {

		// Check if the user is holding this item.
		// If not, return.
		ItemStack stack = player.getItemInHand(hand);

		if (!stack.is(this.asItem())) {
			return super.use(state, level, pos, player, hand, hitResult);
		}

		// Check if the used on block is this.
		BlockState usedOn = level.getBlockState(pos);
		if (usedOn.is(this)) {

			Direction usedOnFacing = usedOn.getValue(FACING);

			// Start scanning for blocks.
			boolean hasFound = false;
			BlockPos.MutableBlockPos scanPos = new BlockPos.MutableBlockPos().set(pos);
			BlockState investigating;
			while ((investigating = level.getBlockState(scanPos)).is(this)
					&& investigating.getValue(FACING) == usedOnFacing) {
				hasFound = true;
				scanPos.move(Direction.DOWN);
			}

			BlockState toPlace = this.defaultBlockState().setValue(FACING, usedOnFacing);

			// If the bottom of the ladder has been found, check if a ladder can be placed
			// there.
			BlockState replaceState = level.getBlockState(scanPos);
			if (hasFound && replaceState.is(BlockTags.REPLACEABLE) && this.canSurvive(toPlace, level, scanPos)) {
				// If it's a server level, place the block and take the item.
				if (level instanceof ServerLevel serverLevel) {
					if (replaceState.hasProperty(BlockStateProperties.WATERLOGGED)) {
						toPlace = toPlace.setValue(LadderBlock.WATERLOGGED,
								replaceState.getValue(BlockStateProperties.WATERLOGGED));
					} else if (replaceState.is(Blocks.WATER)) {
						toPlace = toPlace.setValue(LadderBlock.WATERLOGGED, true);
					}
					serverLevel.setBlockAndUpdate(scanPos, toPlace);
					serverLevel.addDestroyBlockEffect(scanPos, toPlace);
					if (!player.getAbilities().instabuild) {
						if (hand == InteractionHand.MAIN_HAND) {
							player.getInventory().removeFromSelected(false);
						} else {
							player.getInventory().removeItem(Inventory.SLOT_OFFHAND, 1);
						}
					}
				}
				// On success
				return InteractionResult.sidedSuccess(level.isClientSide);
			}
		}

		// On failure
		return super.use(state, level, pos, player, hand, hitResult);

	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(UP, RIGHT, LEFT);
	}
}
