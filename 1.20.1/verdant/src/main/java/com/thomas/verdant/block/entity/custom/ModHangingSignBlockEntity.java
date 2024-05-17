package com.thomas.verdant.block.entity.custom;

import com.thomas.verdant.block.entity.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ModHangingSignBlockEntity extends SignBlockEntity {

	private static final int MAX_TEXT_LINE_WIDTH = 60;
	private static final int TEXT_LINE_HEIGHT = 9;

	public ModHangingSignBlockEntity(BlockPos pPos, BlockState pBlockState) {
		super(ModBlockEntities.MOD_HANGING_SIGN.get(), pPos, pBlockState);
	}

	@Override
	public BlockEntityType<?> getType() {
		return ModBlockEntities.MOD_HANGING_SIGN.get();
	}

	@Override
	public int getMaxTextLineWidth() {
		return MAX_TEXT_LINE_WIDTH;
	}

	@Override
	public int getTextLineHeight() {
		return TEXT_LINE_HEIGHT;
	}
}
