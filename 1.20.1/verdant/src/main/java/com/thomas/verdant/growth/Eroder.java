package com.thomas.verdant.growth;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class Eroder {

	public static final Map<Block, Block> NEXT = new HashMap<>();

	public static BlockState getNext(BlockState oldState) {

		if (NEXT.containsKey(oldState.getBlock())) {
			BlockState newState = NEXT.get(oldState.getBlock()).defaultBlockState();
			for (Property prop : oldState.getProperties()) {
				newState = newState.trySetValue(prop, oldState.getValue(prop));
			}
			return newState;
		} else {
			return oldState;
		}
	}

	public static void register(Block start, Block finish) {
		NEXT.put(start, finish);
	}

	public static void registerErosions() {
		register(Blocks.STONE, Blocks.GRAVEL);
		register(Blocks.DEEPSLATE, Blocks.COBBLED_DEEPSLATE);
		register(Blocks.COBBLED_DEEPSLATE, Blocks.GRAVEL);
		register(Blocks.GRAVEL, Blocks.COARSE_DIRT);
		register(Blocks.COARSE_DIRT, Blocks.DIRT);
		register(Blocks.MOSS_BLOCK, Blocks.DIRT);
		register(Blocks.MOSS_CARPET, Blocks.AIR);
		// Replace with verdant cobblestone when implemented.
		register(Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE);
		register(Blocks.COBBLESTONE_STAIRS, Blocks.MOSSY_COBBLESTONE_STAIRS);
		register(Blocks.COBBLESTONE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB);
		register(Blocks.COBBLESTONE_WALL, Blocks.MOSSY_COBBLESTONE_WALL);
		register(Blocks.INFESTED_COBBLESTONE, Blocks.MOSSY_COBBLESTONE);
		// Stone bricks
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
		
	}

}
