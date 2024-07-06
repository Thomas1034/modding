package com.thomas.verdant.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ModFlammableRotatedPillarBlock extends RotatedPillarBlock {

	private int flammability = 7;
	private int fireSpreadSpeed = 7;

	public ModFlammableRotatedPillarBlock(Properties properties) {
		super(properties);
	}

	public ModFlammableRotatedPillarBlock(Properties properties, int flammability, int fireSpreadSpeed) {
		super(properties);
		this.flammability = flammability;
		this.fireSpreadSpeed = fireSpreadSpeed;
	}

	@Override
	public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return this.flammability > 0;
	}

	@Override
	public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return this.flammability;
	}

	@Override
	public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
		return this.fireSpreadSpeed;
	}
}