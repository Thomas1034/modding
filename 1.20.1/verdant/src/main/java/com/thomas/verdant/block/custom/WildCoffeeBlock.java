package com.thomas.verdant.block.custom;

import java.util.function.Supplier;

import com.thomas.verdant.item.ModItems;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WildCoffeeBlock extends FlowerBlock implements BonemealableBlock {

	public WildCoffeeBlock(Supplier<MobEffect> effectSupplier, int duration, Properties properties) {
		super(effectSupplier, duration, properties);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader p_256559_, BlockPos p_50898_, BlockState p_50899_,
			boolean p_50900_) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level p_220878_, RandomSource p_220879_, BlockPos p_220880_,
			BlockState p_220881_) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
		popResource(level, pos, new ItemStack(ModItems.COFFEE_BERRIES.get()));
	}

}
