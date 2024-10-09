package com.thomas.verdant.growth;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.util.ModTags;
import com.thomas.verdant.util.blocktransformers.BlockTransformer;
import com.thomas.verdant.util.data.DataAccessor;
import com.thomas.verdant.util.data.DataRegistries;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

public class VerdantBlockTransformer {

	// Handles all Verdant block conversion processes.
	public static final DataAccessor<BlockTransformer> EROSION = DataRegistries.BLOCK_TRANSFORMERS
			.register(Verdant.MOD_ID, "erosion");
	public static final DataAccessor<BlockTransformer> EROSION_WET = DataRegistries.BLOCK_TRANSFORMERS
			.register(Verdant.MOD_ID, "erosion_wet");
	public static final DataAccessor<BlockTransformer> GROW_GRASSES = DataRegistries.BLOCK_TRANSFORMERS
			.register(Verdant.MOD_ID, "grow_grasses");
	public static final DataAccessor<BlockTransformer> REMOVE_GRASSES = DataRegistries.BLOCK_TRANSFORMERS
			.register(Verdant.MOD_ID, "remove_grasses");
	public static final DataAccessor<BlockTransformer> HOEING = DataRegistries.BLOCK_TRANSFORMERS
			.register(Verdant.MOD_ID, "hoeing");
	public static final DataAccessor<BlockTransformer> ROOTS = DataRegistries.BLOCK_TRANSFORMERS
			.register(Verdant.MOD_ID, "roots");
	public static final DataAccessor<BlockTransformer> HYDRATE = DataRegistries.BLOCK_TRANSFORMERS
			.register(Verdant.MOD_ID, "hydrate");
	public static final DataAccessor<BlockTransformer> DEHYDRATE = DataRegistries.BLOCK_TRANSFORMERS
			.register(Verdant.MOD_ID, "dehydrate");
	public static final DataAccessor<BlockTransformer> STRIPPING = DataRegistries.BLOCK_TRANSFORMERS
			.register(Verdant.MOD_ID, "stripping");
	public static final DataAccessor<BlockTransformer> TOXIC_ASH = DataRegistries.BLOCK_TRANSFORMERS
			.register(Verdant.MOD_ID, "toxic_ash");

	public static void registerHydration(BlockTransformer transformer) {
		transformer.register(ModBlocks.VERDANT_ROOTED_DIRT.get(), ModBlocks.VERDANT_ROOTED_MUD.get());
		transformer.register(ModBlocks.VERDANT_GRASS_BLOCK.get(), ModBlocks.VERDANT_MUD_GRASS_BLOCK.get());
	}

	public static void registerToxicAsh(BlockTransformer transformer) {
		transformer.register(Blocks.WITHER_ROSE, Blocks.WITHER_ROSE);
		transformer.register(BlockTags.LEAVES, Blocks.AIR);
		transformer.register(BlockTags.FLOWERS, Blocks.AIR);
		transformer.register(BlockTags.BAMBOO_BLOCKS, Blocks.AIR);
		transformer.register(BlockTags.CAVE_VINES, Blocks.AIR);
		transformer.register(BlockTags.CROPS, Blocks.AIR);
		transformer.register(BlockTags.SAPLINGS, Blocks.DEAD_BUSH);
		transformer.register(BlockTags.NYLIUM, Blocks.NETHERRACK);
		transformer.register(ModTags.Blocks.VERDANT_VINES, Blocks.AIR);
		transformer.register(Blocks.GRASS, Blocks.AIR);
		transformer.register(Blocks.TALL_GRASS, Blocks.AIR);
		transformer.register(Blocks.FERN, Blocks.AIR);
		transformer.register(Blocks.LARGE_FERN, Blocks.AIR);
		transformer.register(Blocks.SEAGRASS, Blocks.AIR);
		transformer.register(Blocks.TALL_SEAGRASS, Blocks.AIR);
		transformer.register(Blocks.GRASS_BLOCK, Blocks.COARSE_DIRT);
		transformer.register(Blocks.ROOTED_DIRT, Blocks.COARSE_DIRT);
		transformer.register(Blocks.PODZOL, Blocks.COARSE_DIRT);
		transformer.register(Blocks.MYCELIUM, Blocks.COARSE_DIRT);
		transformer.register(ModBlocks.VERDANT_GRASS_BLOCK.get(), Blocks.COARSE_DIRT);
		transformer.register(ModBlocks.VERDANT_ROOTED_DIRT.get(), Blocks.COARSE_DIRT);
		transformer.register(ModBlocks.VERDANT_MUD_GRASS_BLOCK.get(), Blocks.MUD);
		transformer.register(ModBlocks.VERDANT_ROOTED_MUD.get(), Blocks.MUD);
		transformer.register(ModBlocks.VERDANT_CLAY_GRASS_BLOCK.get(), Blocks.CLAY);
		transformer.register(ModBlocks.VERDANT_ROOTED_CLAY.get(), Blocks.CLAY);
		transformer.register(ModBlocks.THORN_BUSH.get(), Blocks.DEAD_BUSH);
		transformer.register(ModBlocks.VERDANT_LEAVES.get(), ModBlocks.WILTED_VERDANT_LEAVES.get());
		transformer.register(ModBlocks.THORNY_VERDANT_LEAVES.get(), ModBlocks.WILTED_VERDANT_LEAVES.get());
		transformer.register(ModBlocks.POISON_IVY_VERDANT_LEAVES.get(), ModBlocks.WILTED_VERDANT_LEAVES.get());
		transformer.register(Blocks.GLOW_LICHEN, Blocks.AIR);
		transformer.register(Blocks.VINE, Blocks.AIR);
		transformer.register(Blocks.DIRT, Blocks.COARSE_DIRT);
		transformer.register(Blocks.SUGAR_CANE, Blocks.AIR);
	}

	public static void registerDehydration(BlockTransformer transformer) {
		transformer.register(ModBlocks.VERDANT_ROOTED_MUD.get(), ModBlocks.VERDANT_ROOTED_DIRT.get());
		transformer.register(ModBlocks.VERDANT_MUD_GRASS_BLOCK.get(), ModBlocks.VERDANT_GRASS_BLOCK.get());
	}

	public static void registerErosion(BlockTransformer transformer) {
		transformer.register(Blocks.STONE, Blocks.COBBLESTONE);
		transformer.register(Blocks.STONE_SLAB, ModBlocks.DENSE_GRAVEL.get());
		transformer.register(Blocks.STONE_STAIRS, Blocks.COBBLESTONE_STAIRS);
		transformer.register(Blocks.INFESTED_STONE, Blocks.STONE);
		transformer.register(Blocks.COBBLESTONE, ModBlocks.DENSE_GRAVEL.get());
		transformer.register(Blocks.DEEPSLATE, Blocks.COBBLED_DEEPSLATE);
		transformer.register(Blocks.COBBLED_DEEPSLATE, Blocks.COBBLESTONE);
		transformer.register(ModBlocks.DENSE_GRAVEL.get(), Blocks.COARSE_DIRT);
		transformer.register(Blocks.GRAVEL, Blocks.COARSE_DIRT);
		transformer.register(Blocks.DIRT_PATH, Blocks.DIRT);
		transformer.register(Blocks.COARSE_DIRT, Blocks.DIRT);
		transformer.register(Blocks.MOSS_BLOCK, Blocks.DIRT);
		transformer.register(Blocks.MOSS_CARPET, Blocks.AIR);
		// Cobblestone
		transformer.register(Blocks.COBBLESTONE_STAIRS, Blocks.GRAVEL);
		transformer.register(Blocks.COBBLESTONE_SLAB, Blocks.GRAVEL);
		transformer.register(Blocks.COBBLESTONE_WALL, Blocks.GRAVEL);
		transformer.register(Blocks.INFESTED_COBBLESTONE, Blocks.COBBLESTONE);
		transformer.register(Blocks.MOSSY_COBBLESTONE, ModBlocks.DENSE_GRAVEL.get());
		transformer.register(Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.GRAVEL);
		transformer.register(Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.GRAVEL);
		transformer.register(Blocks.MOSSY_COBBLESTONE_WALL, Blocks.GRAVEL);
		// Stone bricks
		transformer.register(Blocks.SMOOTH_STONE, Blocks.COBBLESTONE);
		transformer.register(Blocks.SMOOTH_STONE_SLAB, Blocks.COBBLESTONE_SLAB);
		transformer.register(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
		transformer.register(Blocks.STONE_BRICK_STAIRS, Blocks.COBBLESTONE_STAIRS);
		transformer.register(Blocks.STONE_BRICK_SLAB, Blocks.COBBLESTONE_SLAB);
		transformer.register(Blocks.STONE_BRICK_WALL, Blocks.COBBLESTONE_WALL);
		transformer.register(Blocks.CHISELED_STONE_BRICKS, Blocks.COBBLESTONE);
		transformer.register(Blocks.CRACKED_STONE_BRICKS, Blocks.COBBLESTONE);
		transformer.register(Blocks.MOSSY_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
		transformer.register(Blocks.MOSSY_STONE_BRICK_STAIRS, Blocks.COBBLESTONE_STAIRS);
		transformer.register(Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.COBBLESTONE_SLAB);
		transformer.register(Blocks.MOSSY_STONE_BRICK_WALL, Blocks.COBBLESTONE_WALL);
		// Sandstones.
		transformer.register(Blocks.SMOOTH_SANDSTONE, Blocks.SANDSTONE);
		transformer.register(Blocks.SMOOTH_SANDSTONE_STAIRS, Blocks.SANDSTONE_STAIRS);
		transformer.register(Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SANDSTONE_SLAB);
		transformer.register(Blocks.CHISELED_SANDSTONE, Blocks.SANDSTONE);
		transformer.register(Blocks.CUT_SANDSTONE, Blocks.SANDSTONE);
		transformer.register(Blocks.CUT_SANDSTONE_SLAB, Blocks.SANDSTONE_SLAB);
		transformer.register(Blocks.SANDSTONE, Blocks.SAND);
		transformer.register(Blocks.SANDSTONE_STAIRS, Blocks.SAND);
		transformer.register(Blocks.SANDSTONE_SLAB, Blocks.SAND);
		transformer.register(Blocks.SAND, Blocks.COARSE_DIRT);
		// Mud
		transformer.register(Blocks.PACKED_MUD, Blocks.COARSE_DIRT);
		transformer.register(Blocks.MUD_BRICKS, Blocks.PACKED_MUD);
		transformer.register(Blocks.MUD_BRICK_SLAB, Blocks.COARSE_DIRT);
		transformer.register(Blocks.MUD_BRICK_STAIRS, Blocks.COARSE_DIRT);
		transformer.register(Blocks.MUD_BRICK_WALL, Blocks.COARSE_DIRT);
		// Deepslate ores to stone ores.
		transformer.register(Blocks.DEEPSLATE_COAL_ORE, Blocks.COAL_ORE);
		transformer.register(Blocks.DEEPSLATE_COPPER_ORE, Blocks.COPPER_ORE);
		transformer.register(Blocks.DEEPSLATE_IRON_ORE, Blocks.IRON_ORE);
		transformer.register(Blocks.DEEPSLATE_GOLD_ORE, Blocks.GOLD_ORE);
		transformer.register(Blocks.DEEPSLATE_REDSTONE_ORE, Blocks.REDSTONE_ORE);
		transformer.register(Blocks.DEEPSLATE_LAPIS_ORE, Blocks.LAPIS_ORE);
		transformer.register(Blocks.DEEPSLATE_EMERALD_ORE, Blocks.EMERALD_ORE);
		transformer.register(Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DIAMOND_ORE);
		// Stone ores to dirt ores
		transformer.register(Blocks.COAL_ORE, ModBlocks.DIRT_COAL_ORE.get());
		transformer.register(Blocks.COPPER_ORE, ModBlocks.DIRT_COPPER_ORE.get());
		transformer.register(Blocks.IRON_ORE, ModBlocks.DIRT_IRON_ORE.get());
		transformer.register(Blocks.GOLD_ORE, ModBlocks.DIRT_GOLD_ORE.get());
		transformer.register(Blocks.REDSTONE_ORE, ModBlocks.DIRT_REDSTONE_ORE.get());
		transformer.register(Blocks.LAPIS_ORE, ModBlocks.DIRT_LAPIS_ORE.get());
		transformer.register(Blocks.EMERALD_ORE, ModBlocks.DIRT_EMERALD_ORE.get());
		transformer.register(Blocks.DIAMOND_ORE, ModBlocks.DIRT_DIAMOND_ORE.get());
	}

	public static void registerErosionWet(BlockTransformer transformer) {
		transformer.register(Blocks.DIRT, Blocks.MUD);
		transformer.register(Blocks.TERRACOTTA, Blocks.CLAY);
		transformer.register(Blocks.RED_TERRACOTTA, Blocks.CLAY);
		transformer.register(Blocks.ORANGE_TERRACOTTA, Blocks.CLAY);
		transformer.register(Blocks.YELLOW_TERRACOTTA, Blocks.CLAY);
		transformer.register(Blocks.LIME_TERRACOTTA, Blocks.CLAY);
		transformer.register(Blocks.GREEN_TERRACOTTA, Blocks.CLAY);
		transformer.register(Blocks.CYAN_TERRACOTTA, Blocks.CLAY);
		transformer.register(Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.CLAY);
		transformer.register(Blocks.BLUE_TERRACOTTA, Blocks.CLAY);
		transformer.register(Blocks.PURPLE_TERRACOTTA, Blocks.CLAY);
		transformer.register(Blocks.MAGENTA_TERRACOTTA, Blocks.CLAY);
		transformer.register(Blocks.BROWN_TERRACOTTA, Blocks.CLAY);
		transformer.register(Blocks.BLACK_TERRACOTTA, Blocks.CLAY);
		transformer.register(Blocks.GRAY_TERRACOTTA, Blocks.CLAY);
		transformer.register(Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CLAY);
		transformer.register(Blocks.WHITE_TERRACOTTA, Blocks.CLAY);
		transformer.register(Blocks.PINK_TERRACOTTA, Blocks.CLAY);
	}

	public static void registerGrowGrasses(BlockTransformer transformer) {
		transformer.register(ModBlocks.VERDANT_ROOTED_DIRT.get(), ModBlocks.VERDANT_GRASS_BLOCK.get());
		transformer.register(ModBlocks.VERDANT_ROOTED_MUD.get(), ModBlocks.VERDANT_MUD_GRASS_BLOCK.get());
		transformer.register(ModBlocks.VERDANT_ROOTED_CLAY.get(), ModBlocks.VERDANT_CLAY_GRASS_BLOCK.get());
	}

	public static void registerRemoveGrasses(BlockTransformer transformer) {
		transformer.register(ModBlocks.VERDANT_GRASS_BLOCK.get(), ModBlocks.VERDANT_ROOTED_DIRT.get());
		transformer.register(ModBlocks.VERDANT_MUD_GRASS_BLOCK.get(), ModBlocks.VERDANT_ROOTED_MUD.get());
		transformer.register(ModBlocks.VERDANT_CLAY_GRASS_BLOCK.get(), ModBlocks.VERDANT_ROOTED_CLAY.get());
	}

	public static void registerRoots(BlockTransformer transformer) {
		transformer.register(Blocks.PODZOL, ModBlocks.VERDANT_ROOTED_DIRT.get());
		transformer.register(Blocks.MYCELIUM, ModBlocks.VERDANT_ROOTED_DIRT.get());
		transformer.register(Blocks.GRASS_BLOCK, ModBlocks.VERDANT_ROOTED_DIRT.get());
		transformer.register(Blocks.DIRT, ModBlocks.VERDANT_ROOTED_DIRT.get());
		transformer.register(Blocks.GRASS_BLOCK, ModBlocks.VERDANT_ROOTED_DIRT.get());
		transformer.register(Blocks.MUD, ModBlocks.VERDANT_ROOTED_MUD.get());
		transformer.register(Blocks.CLAY, ModBlocks.VERDANT_ROOTED_CLAY.get());
	}

	public static void registerHoeing(BlockTransformer transformer) {
		transformer.register(ModBlocks.VERDANT_ROOTED_DIRT.get(), Blocks.DIRT);
		transformer.register(ModBlocks.VERDANT_GRASS_BLOCK.get(), ModBlocks.VERDANT_ROOTED_DIRT.get());
		transformer.register(ModBlocks.VERDANT_ROOTED_MUD.get(), Blocks.MUD);
		transformer.register(ModBlocks.VERDANT_MUD_GRASS_BLOCK.get(), ModBlocks.VERDANT_ROOTED_MUD.get());
		transformer.register(ModBlocks.VERDANT_ROOTED_CLAY.get(), Blocks.CLAY);
		transformer.register(ModBlocks.VERDANT_CLAY_GRASS_BLOCK.get(), ModBlocks.VERDANT_ROOTED_CLAY.get());
	}
}
