package com.thomas.verdant.growth;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.thomas.verdant.block.ModBlocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

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
		ifWet(Blocks.RED_TERRACOTTA, Blocks.CLAY);
		ifWet(Blocks.ORANGE_TERRACOTTA, Blocks.CLAY);
		ifWet(Blocks.YELLOW_TERRACOTTA, Blocks.CLAY);
		ifWet(Blocks.LIME_TERRACOTTA, Blocks.CLAY);
		ifWet(Blocks.GREEN_TERRACOTTA, Blocks.CLAY);
		ifWet(Blocks.CYAN_TERRACOTTA, Blocks.CLAY);
		ifWet(Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.CLAY);
		ifWet(Blocks.BLUE_TERRACOTTA, Blocks.CLAY);
		ifWet(Blocks.PURPLE_TERRACOTTA, Blocks.CLAY);
		ifWet(Blocks.MAGENTA_TERRACOTTA, Blocks.CLAY);
		ifWet(Blocks.BROWN_TERRACOTTA, Blocks.CLAY);
		ifWet(Blocks.BLACK_TERRACOTTA, Blocks.CLAY);
		ifWet(Blocks.GRAY_TERRACOTTA, Blocks.CLAY);
		ifWet(Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CLAY);
		ifWet(Blocks.WHITE_TERRACOTTA, Blocks.CLAY);
		ifWet(Blocks.PINK_TERRACOTTA, Blocks.CLAY);
		// Deepslate ores to stone ores.
		register(Blocks.DEEPSLATE_COAL_ORE, Blocks.COAL_ORE);
		register(Blocks.DEEPSLATE_COPPER_ORE, Blocks.COPPER_ORE);
		register(Blocks.DEEPSLATE_IRON_ORE, Blocks.IRON_ORE);
		register(Blocks.DEEPSLATE_GOLD_ORE, Blocks.GOLD_ORE);
		register(Blocks.DEEPSLATE_REDSTONE_ORE, Blocks.REDSTONE_ORE);
		register(Blocks.DEEPSLATE_LAPIS_ORE, Blocks.LAPIS_ORE);
		register(Blocks.DEEPSLATE_EMERALD_ORE, Blocks.EMERALD_ORE);
		register(Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DIAMOND_ORE);
		// Stone ores to dirt ores
		register(Blocks.COAL_ORE, ModBlocks.DIRT_COAL_ORE.get());
		register(Blocks.COPPER_ORE, ModBlocks.DIRT_COPPER_ORE.get());
		register(Blocks.IRON_ORE, ModBlocks.DIRT_IRON_ORE.get());
		register(Blocks.GOLD_ORE, ModBlocks.DIRT_GOLD_ORE.get());
		register(Blocks.REDSTONE_ORE, ModBlocks.DIRT_REDSTONE_ORE.get());
		register(Blocks.LAPIS_ORE, ModBlocks.DIRT_LAPIS_ORE.get());
		register(Blocks.EMERALD_ORE, ModBlocks.DIRT_EMERALD_ORE.get());
		register(Blocks.DIAMOND_ORE, ModBlocks.DIRT_DIAMOND_ORE.get());
		System.out.println("Registered erosions:");
		for (Entry<Block, Block> entry : NEXT.entrySet()) {
			System.out.println(entry.getKey().getName().getString() + " -> " + entry.getValue().getName().getString());
		}
	}
}
