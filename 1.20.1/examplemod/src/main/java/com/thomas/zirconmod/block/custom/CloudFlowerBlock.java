package com.thomas.zirconmod.block.custom;

import java.util.function.Supplier;

import com.thomas.zirconmod.block.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CloudFlowerBlock extends FlowerBlock {

	@SuppressWarnings("deprecation")
	public CloudFlowerBlock(MobEffect p_53512_, int p_53513_, Properties p_53514_) {
		super(p_53512_, p_53513_, p_53514_);
	}

	public CloudFlowerBlock(Supplier<MobEffect> effectSupplier, int p_53513_, Properties p_53514_) {
		super(effectSupplier, p_53513_, p_53514_);
		this.canSurvive(null, null, null);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.is(ModBlocks.CLOUD.get());
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return level.getBlockState(pos.below()).is(ModBlocks.CLOUD.get()) || super.canSurvive(state, level, pos);
	}

}
