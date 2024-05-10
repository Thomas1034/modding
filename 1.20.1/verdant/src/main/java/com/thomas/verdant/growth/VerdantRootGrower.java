package com.thomas.verdant.growth;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.thomas.verdant.block.ModBlocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class VerdantRootGrower {

	public static final Map<Block, Block> ROOTED = new HashMap<>();
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static BlockState getRooted(BlockState oldState) {

		if (ROOTED.containsKey(oldState.getBlock())) {
			BlockState newState = ROOTED.get(oldState.getBlock()).defaultBlockState();
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
	
	public static boolean isRootable(BlockState state) {
		return isRootable(state.getBlock());
	}
	
	public static boolean isRootable(Block block) {
		return ROOTED.containsKey(block);
	}
	
	public static boolean isRoots(BlockState state) {
		return isRoots(state.getBlock());
	}

	private static boolean isRoots(Block block) {
		return ROOTED.containsValue(block);
	}

	public static void register(Block unrooted, Block rooted) {
		ROOTED.put(unrooted, rooted);
	}

	public static void registerRoots() {

		register(Blocks.PODZOL, ModBlocks.VERDANT_ROOTED_DIRT.get());
		register(Blocks.MYCELIUM, ModBlocks.VERDANT_ROOTED_DIRT.get());
		register(Blocks.GRASS_BLOCK, ModBlocks.VERDANT_ROOTED_DIRT.get());
		register(Blocks.DIRT, ModBlocks.VERDANT_ROOTED_DIRT.get());
		register(Blocks.MUD, ModBlocks.VERDANT_ROOTED_MUD.get());
		register(Blocks.CLAY, ModBlocks.VERDANT_ROOTED_CLAY.get());
		
		System.out.println("Registered rooted blocks:");
		for (Entry<Block, Block> entry : ROOTED.entrySet()) {
			System.out.println(entry.getKey().getName().getString() + " -> " + entry.getValue().getName().getString());
		}
	}

}
