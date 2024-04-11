package com.thomas.cloudscape.util;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.thomas.cloudscape.block.ModBlocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public interface Waxable {

	@SuppressWarnings("rawtypes")
	List<Property> IGNORED_PROPERTIES = List.of(ButtonBlock.POWERED);

	static final Supplier<BiMap<Block, Block>> WAXABLES = Suppliers.memoize(() -> {
		return ImmutableBiMap.<Block, Block>builder()
				.put(ModBlocks.COPPER_BUTTON.get(), ModBlocks.WAXED_COPPER_BUTTON.get())
				.put(ModBlocks.EXPOSED_COPPER_BUTTON.get(), ModBlocks.WAXED_EXPOSED_COPPER_BUTTON.get())
				.put(ModBlocks.WEATHERED_COPPER_BUTTON.get(), ModBlocks.WAXED_WEATHERED_COPPER_BUTTON.get())
				.put(ModBlocks.OXIDIZED_COPPER_BUTTON.get(), ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get()).build();
	});
	static final Supplier<BiMap<Block, Block>> WAX_OFF_BY_BLOCK = Suppliers.memoize(() -> {
		return WAXABLES.get().inverse();
	});

	static Optional<Block> wax(Block input) {
		return Optional.ofNullable(WAXABLES.get().getOrDefault(input, null));
	}

	static Optional<Block> unwax(Block input) {
		return Optional.ofNullable(WAX_OFF_BY_BLOCK.get().getOrDefault(input, null));
	}

	static boolean canApplyWax(BlockState state) {
		return wax(state.getBlock()).isPresent();
	}

	static boolean canRemoveWax(BlockState state) {
		return unwax(state.getBlock()).isPresent();
	}

	@SuppressWarnings("unchecked")
	static BlockState applyWax(BlockState state) {
		if (!canApplyWax(state)) {
			return state;
		}

		BlockState toReturn = wax(state.getBlock()).get().withPropertiesOf(state);
		BlockState defaultState = state.getBlock().defaultBlockState();
		for (@SuppressWarnings("rawtypes") Property p : IGNORED_PROPERTIES) {
			if (toReturn.hasProperty(p)) {
				toReturn = toReturn.setValue(p, defaultState.getValue(p));
			}
		}
		return toReturn;
	}

	@SuppressWarnings("unchecked")
	static BlockState removeWax(BlockState state) {
		if (!canRemoveWax(state)) {
			return state;
		}
		
		BlockState toReturn = unwax(state.getBlock()).get().withPropertiesOf(state);
		BlockState defaultState = state.getBlock().defaultBlockState();
		for (@SuppressWarnings("rawtypes") Property p : IGNORED_PROPERTIES) {
			if (toReturn.hasProperty(p)) {
				toReturn = toReturn.setValue(p, defaultState.getValue(p));
			}
		}
		return toReturn;
	}

}
