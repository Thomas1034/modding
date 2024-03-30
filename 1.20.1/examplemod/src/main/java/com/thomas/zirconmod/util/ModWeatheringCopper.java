package com.thomas.zirconmod.util;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.thomas.zirconmod.block.ModBlocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

// Reimplements the WeatheringCopper interface, to use mod blocks.
// Because of course they couldn't make it use overridable methods for subclasses.
public interface ModWeatheringCopper extends WeatheringCopper {

	@SuppressWarnings("rawtypes")
	List<Property> IGNORED_PROPERTIES = List.of(ButtonBlock.POWERED);

	Supplier<BiMap<Block, Block>> NEXT_BY_BLOCK = Suppliers.memoize(() -> {
		return ImmutableBiMap.<Block, Block>builder()
				.put(ModBlocks.COPPER_BUTTON.get(), ModBlocks.EXPOSED_COPPER_BUTTON.get())
				.put(ModBlocks.EXPOSED_COPPER_BUTTON.get(), ModBlocks.WEATHERED_COPPER_BUTTON.get())
				.put(ModBlocks.WEATHERED_COPPER_BUTTON.get(), ModBlocks.OXIDIZED_COPPER_BUTTON.get()).build();
	});
	Supplier<BiMap<Block, Block>> PREVIOUS_BY_BLOCK = Suppliers.memoize(() -> {
		return NEXT_BY_BLOCK.get().inverse();
	});

	static Optional<Block> getPrevious(Block p_154891_) {
		return Optional.ofNullable(PREVIOUS_BY_BLOCK.get().get(p_154891_));
	}

	static Block getFirst(Block p_154898_) {
		Block block = p_154898_;

		for (Block block1 = PREVIOUS_BY_BLOCK.get().get(p_154898_); block1 != null; block1 = PREVIOUS_BY_BLOCK.get()
				.get(block1)) {
			block = block1;
		}

		return block;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	static Optional<BlockState> getPrevious(BlockState inputState) {
		return getPrevious(inputState.getBlock()).map((rawState) -> {
			BlockState toReturn = rawState.withPropertiesOf(inputState);
			BlockState defaultState = inputState.getBlock().defaultBlockState();
			for (Property p : IGNORED_PROPERTIES) {
				if (toReturn.hasProperty(p)) {
					toReturn = toReturn.setValue(p, defaultState.getValue(p));

				}
			}

			return toReturn;
		});
	}

	static Optional<Block> getNext(Block p_154905_) {
		return Optional.ofNullable(NEXT_BY_BLOCK.get().get(p_154905_));
	}

	static BlockState getFirst(BlockState p_154907_) {
		return getFirst(p_154907_.getBlock()).withPropertiesOf(p_154907_);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	default Optional<BlockState> getNext(BlockState inputState) {
		return getNext(inputState.getBlock()).map((rawState) -> {
			BlockState toReturn = rawState.withPropertiesOf(inputState);
			BlockState defaultState = inputState.getBlock().defaultBlockState();
			for (Property p : IGNORED_PROPERTIES) {
				if (toReturn.hasProperty(p)) {
					toReturn = toReturn.setValue(p, defaultState.getValue(p));
				}
			}
			return toReturn;
		});
	}

	default float getChanceModifier() {
		return this.getAge() == WeatheringCopper.WeatherState.UNAFFECTED ? 0.75F : 1.0F;
	}

}
