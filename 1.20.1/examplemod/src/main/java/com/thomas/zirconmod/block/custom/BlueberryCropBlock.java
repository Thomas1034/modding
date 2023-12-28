package com.thomas.zirconmod.block.custom;

import com.thomas.zirconmod.item.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlueberryCropBlock extends CropBlock {

	public static final int MAX_AGE = 5;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_5;
	private static final VoxelShape SAPLING_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);
	private static final VoxelShape MID_GROWTH_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

	public BlueberryCropBlock(Properties p_52247_) {
		super(p_52247_);
	}

	@Override
	protected ItemLike getBaseSeedId() {
		return ModItems.BLUEBERRY_SEEDS.get();
	}

	@Override
	public IntegerProperty getAgeProperty() {
		return AGE;
	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter p_57256_, BlockPos p_57257_, BlockState p_57258_) {
		return new ItemStack(ModItems.BLUEBERRY_SEEDS.get());
	}

	public VoxelShape getShape(BlockState p_57291_, BlockGetter p_57292_, BlockPos p_57293_,
			CollisionContext p_57294_) {
		if (p_57291_.getValue(AGE) == 0) {
			return SAPLING_SHAPE;
		} else {
			return p_57291_.getValue(AGE) < MAX_AGE ? MID_GROWTH_SHAPE
					: super.getShape(p_57291_, p_57292_, p_57293_, p_57294_);
		}
	}

	public boolean isRandomlyTicking(BlockState p_57284_) {
		return p_57284_.getValue(AGE) < MAX_AGE;
	}

	@SuppressWarnings("deprecation")
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult blockHitResult) {
		int i = state.getValue(AGE);
		boolean flag = i == MAX_AGE;
		if (!flag && player.getItemInHand(hand).is(Items.BONE_MEAL)) {
			return InteractionResult.PASS;
		} else if (flag) {
			int j = 1 + level.random.nextInt(4);
			popResource(level, pos, new ItemStack(ModItems.BLUEBERRY.get(), j + (flag ? 1 : 0)));
			level.playSound((Player) null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS,
					1.0F, 0.8F + level.random.nextFloat() * 0.4F);
			BlockState blockstate = state.setValue(AGE, Integer.valueOf(1));
			level.setBlock(pos, blockstate, 2);
			level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockstate));
			return InteractionResult.sidedSuccess(level.isClientSide);
		} else {
			return super.use(state, level, pos, player, hand, blockHitResult);
		}
	}

	public boolean isValidBonemealTarget(LevelReader p_256056_, BlockPos p_57261_, BlockState p_57262_,
			boolean p_57263_) {
		return p_57262_.getValue(AGE) < MAX_AGE;
	}

	public boolean isBonemealSuccess(Level p_222558_, RandomSource p_222559_, BlockPos p_222560_,
			BlockState p_222561_) {
		return true;
	}

	public void performBonemeal(ServerLevel p_222553_, RandomSource p_222554_, BlockPos p_222555_,
			BlockState p_222556_) {
		int i = Math.min(MAX_AGE, p_222556_.getValue(AGE) + 1);
		p_222553_.setBlock(p_222555_, p_222556_.setValue(AGE, Integer.valueOf(i)), 2);
	}

	@Override
	public int getMaxAge() {
		return MAX_AGE;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
}
