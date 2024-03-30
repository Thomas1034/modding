package com.thomas.zirconmod.block.custom;

import com.thomas.zirconmod.util.ModWeatheringCopper;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class WeatheringCopperButtonBlock extends ButtonBlock implements ModWeatheringCopper {
	private final WeatheringCopper.WeatherState weatherState;

	public WeatheringCopperButtonBlock(WeatheringCopper.WeatherState weatherState, int ticks,
			BlockBehaviour.Properties properties) {
		super(properties, BlockSetType.IRON, ticks, false);
		this.weatherState = weatherState;
	}

	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
		// System.out.println("Ticking at " + pos);
		this.onRandomTick(state, level, pos, rand);
	}

	public boolean isRandomlyTicking(BlockState p_154935_) {
		// System.out.println(
		// " In isRandomlyTicking, returning " +
		// ModWeatheringCopper.getNext(p_154935_.getBlock()).isPresent());
		return ModWeatheringCopper.getNext(p_154935_.getBlock()).isPresent();
	}

	public WeatheringCopper.WeatherState getAge() {
		// System.out.println(" In getAge, returning " + this.weatherState);
		return this.weatherState;
	}
}
