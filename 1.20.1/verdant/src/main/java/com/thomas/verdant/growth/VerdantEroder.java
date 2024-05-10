package com.thomas.verdant.growth;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.RegistryObject;

public class VerdantEroder {

	public static final Map<Block, Block> NEXT = new HashMap<>();
	public static final Map<Block, Block> IF_WET = new HashMap<>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BlockState getNext(BlockState oldState) {

		if (NEXT.containsKey(oldState.getBlock())) {
			BlockState newState = NEXT.get(oldState.getBlock()).defaultBlockState();
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
	public static BlockState getNextIfWet(BlockState oldState) {

		if (IF_WET.containsKey(oldState.getBlock())) {
			BlockState newState = IF_WET.get(oldState.getBlock()).defaultBlockState();
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

	public static void register(Block start, Block finish) {
		NEXT.put(start, finish);
	}
	
	public static void ifWet(Block start, Block finish) {
		IF_WET.put(start, finish);
	}

	public static void registerErosions() {
		register(Blocks.STONE, Blocks.COBBLESTONE);
		register(Blocks.INFESTED_STONE, Blocks.STONE);
		register(Blocks.COBBLESTONE, Blocks.GRAVEL);
		register(Blocks.DEEPSLATE, Blocks.COBBLED_DEEPSLATE);
		register(Blocks.COBBLED_DEEPSLATE, Blocks.COBBLESTONE);
		register(Blocks.GRAVEL, Blocks.COARSE_DIRT);
		register(Blocks.DIRT_PATH, Blocks.DIRT);
		register(Blocks.COARSE_DIRT, Blocks.DIRT);
		register(Blocks.MOSS_BLOCK, Blocks.DIRT);
		register(Blocks.MOSS_CARPET, Blocks.AIR);
		// Cobblestone
		register(Blocks.COBBLESTONE_STAIRS, Blocks.GRAVEL);
		register(Blocks.COBBLESTONE_SLAB, Blocks.GRAVEL);
		register(Blocks.COBBLESTONE_WALL, Blocks.GRAVEL);
		register(Blocks.INFESTED_COBBLESTONE, Blocks.COBBLESTONE);
		register(Blocks.MOSSY_COBBLESTONE, Blocks.GRAVEL);
		register(Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.GRAVEL);
		register(Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.GRAVEL);
		register(Blocks.MOSSY_COBBLESTONE_WALL, Blocks.GRAVEL);
		// Stone bricks
		register(Blocks.SMOOTH_STONE, Blocks.COBBLESTONE);
		register(Blocks.SMOOTH_STONE_SLAB, Blocks.COBBLESTONE_SLAB);
		register(Blocks.STONE_BRICKS, Blocks.COBBLESTONE);
		register(Blocks.STONE_BRICK_STAIRS, Blocks.COBBLESTONE_STAIRS);
		register(Blocks.STONE_BRICK_SLAB, Blocks.COBBLESTONE_SLAB);
		register(Blocks.STONE_BRICK_WALL, Blocks.COBBLESTONE_WALL);
		register(Blocks.CHISELED_STONE_BRICKS, Blocks.COBBLESTONE);
		register(Blocks.MOSSY_STONE_BRICKS, Blocks.COBBLESTONE);
		register(Blocks.MOSSY_STONE_BRICK_STAIRS, Blocks.COBBLESTONE_STAIRS);
		register(Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.COBBLESTONE_SLAB);
		register(Blocks.MOSSY_STONE_BRICK_WALL, Blocks.COBBLESTONE_WALL);
		// Sandstones.
		register(Blocks.SMOOTH_SANDSTONE, Blocks.SANDSTONE);
		register(Blocks.SMOOTH_SANDSTONE_STAIRS, Blocks.SANDSTONE_STAIRS);
		register(Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SANDSTONE_SLAB);
		register(Blocks.CHISELED_SANDSTONE, Blocks.SANDSTONE);
		register(Blocks.CUT_SANDSTONE, Blocks.SANDSTONE);
		register(Blocks.CUT_SANDSTONE_SLAB, Blocks.SANDSTONE_SLAB);
		register(Blocks.SANDSTONE, Blocks.SAND);
		register(Blocks.SANDSTONE_STAIRS, Blocks.SAND);
		register(Blocks.SANDSTONE_SLAB, Blocks.SAND);
		register(Blocks.SAND, Blocks.COARSE_DIRT);
		// Mud
		ifWet(Blocks.DIRT, Blocks.MUD);
		register(Blocks.PACKED_MUD, Blocks.DIRT);
		register(Blocks.MUD_BRICKS, Blocks.PACKED_MUD);
		register(Blocks.MUD_BRICK_SLAB, Blocks.COARSE_DIRT);
		register(Blocks.MUD_BRICK_STAIRS, Blocks.COARSE_DIRT);
		register(Blocks.MUD_BRICK_WALL, Blocks.COARSE_DIRT);
		// Terracotta, if wet.
		ifWet(Blocks.TERRACOTTA, Blocks.CLAY);
		
		System.out.println("Registered erosions:");
		for (Entry<Block, Block> entry : NEXT.entrySet()) {
			System.out.println(entry.getKey().getName().getString() + " -> " + entry.getValue().getName().getString());
		}
	}

}
