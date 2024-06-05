package com.thomas.verdant.block.custom;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.util.ModTags;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VerdantTendrilPlantBlock extends GrowingPlantBodyBlock {

	protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public VerdantTendrilPlantBlock(Properties properties) {
		super(properties, Direction.DOWN, SHAPE, true);
	}

	@Override
	protected GrowingPlantHeadBlock getHeadBlock() {
		// TODO Auto-generated method stub
		return (GrowingPlantHeadBlock) ModBlocks.VERDANT_TENDRIL.get();
	}

	@Override
	protected boolean canAttachTo(BlockState state) {
		return true;
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
	
	@SuppressWarnings("deprecation")
	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		super.randomTick(state, level, pos, rand);
		this.tick(state, level, pos, rand);
		
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(WATERLOGGED);
	}
}
