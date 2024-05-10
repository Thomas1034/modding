package com.thomas.verdant.growth;

import java.util.Map.Entry;

import com.google.common.collect.HashBiMap;
import com.thomas.verdant.block.ModBlocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class VerdantGrassGrower {

	public static final HashBiMap<Block, Block> GRASSY = HashBiMap.create();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static BlockState getGrass(BlockState oldState) {

		if (GRASSY.containsKey(oldState.getBlock())) {
			BlockState newState = GRASSY.get(oldState.getBlock()).defaultBlockState();
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BlockState getDegrass(BlockState oldState) {

		if (GRASSY.inverse().containsKey(oldState.getBlock())) {
			BlockState newState = GRASSY.inverse().get(oldState.getBlock()).defaultBlockState();
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

	public static boolean isGrassable(BlockState state) {
		return isGrassable(state.getBlock());
	}

	public static boolean isGrassable(Block block) {
		return GRASSY.containsKey(block);
	}
	
	public static boolean isGrass(BlockState state) {
		return isGrass(state.getBlock());
	}

	private static boolean isGrass(Block block) {
		return GRASSY.containsValue(block);
	}

	public static void register(Block degrass, Block grass) {
		GRASSY.put(degrass, grass);
	}

	public static void registerGrasses() {
		register(ModBlocks.VERDANT_ROOTED_DIRT.get(), ModBlocks.VERDANT_GRASS_BLOCK.get());
		register(ModBlocks.VERDANT_ROOTED_MUD.get(), ModBlocks.VERDANT_MUD_GRASS_BLOCK.get());
		register(ModBlocks.VERDANT_ROOTED_CLAY.get(), ModBlocks.VERDANT_CLAY_GRASS_BLOCK.get());
		
		System.out.println("Registered grassy blocks:");
		for (Entry<Block, Block> entry : GRASSY.entrySet()) {
			System.out.println(entry.getKey().getName().getString() + " -> " + entry.getValue().getName().getString());
		}
	}

}
