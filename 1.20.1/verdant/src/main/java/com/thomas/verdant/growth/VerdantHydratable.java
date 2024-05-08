package com.thomas.verdant.growth;

import java.util.Map.Entry;

import com.google.common.collect.HashBiMap;
import com.thomas.verdant.block.ModBlocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public interface VerdantHydratable {
	
	public static final int MAX_DISTANCE = 15;
	public static final int MIN_DISTANCE = 0;
	
	public static final IntegerProperty WATER_DISTANCE = IntegerProperty.create("water_distance", MIN_DISTANCE,
			MAX_DISTANCE);

	public static final HashBiMap<Block, Block> HYDRATION = HashBiMap.create();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BlockState getHydrated(BlockState oldState) {

		if (HYDRATION.containsKey(oldState.getBlock())) {
			BlockState newState = HYDRATION.get(oldState.getBlock()).defaultBlockState();
			for (Property prop : oldState.getProperties()) {
				if (newState.hasProperty(prop)) {
					newState = newState.trySetValue(prop, oldState.getValue(prop));
				}
			}
			return newState;
		} else {
			return oldState;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static BlockState getDehydrated(BlockState oldState) {

		if (HYDRATION.inverse().containsKey(oldState.getBlock())) {
			BlockState newState = HYDRATION.inverse().get(oldState.getBlock()).defaultBlockState();
			for (Property prop : oldState.getProperties()) {
				if (newState.hasProperty(prop)) {
					newState = newState.trySetValue(prop, oldState.getValue(prop));
				}
			}
			return newState;
		} else {
			return oldState;
		}
	}
	
	public static boolean isHydrated(BlockState state) {
		return isHydrated(state.getBlock());
	}

	private static boolean isHydrated(Block block) {
		return HYDRATION.containsValue(block);
	}

	public static void register(Block dry, Block wet) {
		HYDRATION.put(dry, wet);
	}

	public static void registerHydratables() {
		register(ModBlocks.VERDANT_ROOTED_DIRT.get(), ModBlocks.VERDANT_ROOTED_MUD.get());
		register(ModBlocks.VERDANT_GRASS_BLOCK.get(), ModBlocks.VERDANT_MUD_GRASS_BLOCK.get());
		
		System.out.println("Registered hydratable blocks:");
		for (Entry<Block, Block> entry : HYDRATION.entrySet()) {
			System.out.println(entry.getKey().getName().getString() + " -> " + entry.getValue().getName().getString());
		}
	}
}
