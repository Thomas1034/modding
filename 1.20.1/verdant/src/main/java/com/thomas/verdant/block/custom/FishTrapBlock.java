package com.thomas.verdant.block.custom;

import javax.annotation.Nullable;

import com.thomas.verdant.block.entity.ModBlockEntities;
import com.thomas.verdant.block.entity.custom.FishTrapBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

public class FishTrapBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty ENABLED = BlockStateProperties.ENABLED;
	private static final VoxelShape BLOCK_SUPPORT_SHAPE = Shapes.or(Block.box(0, 0, 0, 16, 1, 16),
			Block.box(0, 15, 0, 16, 16, 16));

	public FishTrapBlock(Properties properties) {
		super(properties);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		
		return new FishTrapBlockEntity(pos, state);
	}

	@Override
	public VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
		return BLOCK_SUPPORT_SHAPE;
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false)
				: super.getFluidState(state);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
		if (pState.getBlock() != pNewState.getBlock()) {
			BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
			if (blockEntity instanceof FishTrapBlockEntity) {
				((FishTrapBlockEntity) blockEntity).drops();
			}
		}

		super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult hit) {
		if (!level.isClientSide()) {
			BlockEntity entity = level.getBlockEntity(pos);
			if (entity instanceof FishTrapBlockEntity) {
				NetworkHooks.openScreen(((ServerPlayer) player), (FishTrapBlockEntity) entity, pos);
			} else {
				throw new IllegalStateException("Our Container provider is missing!");
			}
		}

		return InteractionResult.sidedSuccess(level.isClientSide());
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> blockEntityType) {
		if (level.isClientSide()) {
			return null;
		}

		return createTickerHelper(blockEntityType, ModBlockEntities.FISH_TRAP_BLOCK_ENTITY.get(),
				(lambdaLevel, lamdaPos, lambdaState, lambdaBlockEntity) -> lambdaBlockEntity.tick(lambdaLevel, lamdaPos,
						lambdaState));
	}

	public BlockState setEnabled(LevelAccessor level, BlockState original, BlockPos pos) {
		BlockState result = original;
		if (!result.getValue(BlockStateProperties.WATERLOGGED)) {
			return result.setValue(ENABLED, false);
		}
		BlockState inFront = level.getBlockState(pos.relative(result.getValue(FACING)));
		if (inFront.is(Blocks.WATER)) {
			result = result.setValue(ENABLED, true);
		} else {
			result = result.setValue(ENABLED, false);
		}
		return result;
	}

	// From DoublePlantBlock
	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState blockstate = super.getStateForPlacement(context);
		if (blockstate == null) {
			return null;
		}
		Direction facing = context.getHorizontalDirection().getOpposite();
		BlockState afterWaterlogged = copyWaterloggedFrom(context.getLevel(), context.getClickedPos(),
				blockstate.setValue(FACING, facing));
		return setEnabled(context.getLevel(), afterWaterlogged, context.getClickedPos());
	}

	@Override
	public BlockState updateShape(BlockState thisState, Direction towardThat, BlockState thatState, LevelAccessor level,
			BlockPos thisPosition, BlockPos thatPosition) {

		if (thisState.getValue(BlockStateProperties.WATERLOGGED)) {
			level.scheduleTick(thisPosition, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}

		return this.setEnabled(level, thisState, thisPosition);
	}

	@Override
	public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluid) {
		boolean placed = SimpleWaterloggedBlock.super.placeLiquid(level, pos, state, fluid);

		if (placed && !level.isClientSide()) {
			level.setBlock(pos, this.setEnabled(level, level.getBlockState(pos), pos), 3);
		}

		return placed;
	}

	@Override
	public ItemStack pickupBlock(LevelAccessor level, BlockPos pos, BlockState state) {
		ItemStack stack = SimpleWaterloggedBlock.super.pickupBlock(level, pos, state);

		if (!stack.isEmpty() && !level.isClientSide()) {
			level.setBlock(pos, this.setEnabled(level, level.getBlockState(pos), pos), 3);
		}

		return stack;
	}

	// From DoublePlantBlock
	public static BlockState copyWaterloggedFrom(LevelReader level, BlockPos pos, BlockState state) {
		return state.hasProperty(BlockStateProperties.WATERLOGGED)
				? state.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(level.isWaterAt(pos)))
				: state;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, BlockStateProperties.WATERLOGGED, BlockStateProperties.ENABLED);
	}

}
