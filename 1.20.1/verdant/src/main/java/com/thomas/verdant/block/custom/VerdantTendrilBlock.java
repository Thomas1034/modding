package com.thomas.verdant.block.custom;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.block.VerdantGrower;
import com.thomas.verdant.util.ModTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VerdantTendrilBlock extends GrowingPlantHeadBlock implements VerdantGrower {

	protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public VerdantTendrilBlock(Properties properties) {
		super(properties, Direction.DOWN, SHAPE, true, 1.0f);

	}

	@Override
	protected int getBlocksToGrowWhenBonemealed(RandomSource rand) {
		// TODO Auto-generated method stub
		return rand.nextInt(2) + 1;
	}

	@Override
	protected boolean canGrowInto(BlockState state) {
		return state.getFluidState().isEmpty() && (state.isAir() || state.is(ModBlocks.VERDANT_LEAVES.get()) || state.is(BlockTags.REPLACEABLE));
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		super.randomTick(state, level, pos, rand);
		float growthChance =  this.growthChance();
		float randomChance = rand.nextFloat();
		while (randomChance < growthChance) {
			// System.out.println("Trying to spread.");
			this.grow(state, level, pos);
			growthChance--;
		}
	}

	@Override
	protected Block getBodyBlock() {
		return ModBlocks.VERDANT_TENDRIL_PLANT.get();
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos growingFrom = pos.relative(this.growthDirection.getOpposite());
		BlockState growingOn = level.getBlockState(growingFrom);
		if (!this.canAttachTo(growingOn)) {
			return false;
		} else {
			return growingOn.is(this.getHeadBlock()) || growingOn.is(this.getBodyBlock())
					|| growingOn.isFaceSturdy(level, growingFrom, this.growthDirection)
					|| growingOn.is(ModTags.Blocks.VERDANT_LEAFY_BLOCKS);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(WATERLOGGED);
	}

	@Override
	public float growthChance() {
		return 0.5f;
	}

	@Override
	public void grow(BlockState state, Level level, BlockPos pos) {
		// Try to convert the ground.
		if (VerdantGrower.convertGround(level, pos.below(), state.getValue(WATERLOGGED))) {
		} else {
			// Otherwise, try to erode it.
			this.erode(level, pos.below(), state.getValue(WATERLOGGED));
		}
	}
}
