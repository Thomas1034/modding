package com.thomas.verdant.datagen;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.util.ModTags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagGenerator extends BlockTagsProvider {
	public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
			@Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, Verdant.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {

		ModBlocks.VERDANT_HEARTWOOD.addBlockTags(this);
		ModBlocks.VERDANT.addBlockTags(this);

		// Mineables

		this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.LEAFY_VERDANT_VINE.get(), ModBlocks.VERDANT_VINE.get());
		this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.VERDANT_GRASS_BLOCK.get(),
				ModBlocks.VERDANT_ROOTED_DIRT.get(), ModBlocks.VERDANT_ROOTED_MUD.get(),
				ModBlocks.VERDANT_MUD_GRASS_BLOCK.get(), ModBlocks.VERDANT_ROOTED_CLAY.get(),
				ModBlocks.VERDANT_CLAY_GRASS_BLOCK.get(), ModBlocks.DIRT_COAL_ORE.get(),
				ModBlocks.DIRT_COPPER_ORE.get(), ModBlocks.DIRT_DIAMOND_ORE.get(), ModBlocks.DIRT_EMERALD_ORE.get(),
				ModBlocks.DIRT_GOLD_ORE.get(), ModBlocks.DIRT_IRON_ORE.get(), ModBlocks.DIRT_LAPIS_ORE.get(),
				ModBlocks.DIRT_REDSTONE_ORE.get(), ModBlocks.CASSAVA_ROOTED_DIRT.get(),
				ModBlocks.BITTER_CASSAVA_ROOTED_DIRT.get(), ModBlocks.VERDANT_CONDUIT.get());
		this.tag(BlockTags.MINEABLE_WITH_HOE).add(ModBlocks.WILTED_VERDANT_LEAVES.get(), ModBlocks.VERDANT_LEAVES.get(),
				ModBlocks.THORNY_VERDANT_LEAVES.get(), ModBlocks.POISON_IVY_VERDANT_LEAVES.get(),
				ModBlocks.THORN_BUSH.get(), ModBlocks.VERDANT_TENDRIL.get(), ModBlocks.VERDANT_TENDRIL_PLANT.get(),
				ModBlocks.POISON_IVY.get(), ModBlocks.POISON_IVY_PLANT.get());
		this.tag(BlockTags.SWORD_EFFICIENT).add(ModBlocks.VERDANT_LEAVES.get(), ModBlocks.THORNY_VERDANT_LEAVES.get(),
				ModBlocks.POISON_IVY_VERDANT_LEAVES.get(), ModBlocks.THORN_BUSH.get(), ModBlocks.VERDANT_TENDRIL.get(),
				ModBlocks.VERDANT_TENDRIL_PLANT.get(), ModBlocks.POISON_IVY.get(), ModBlocks.POISON_IVY_PLANT.get());

		this.tag(BlockTags.LEAVES).add(ModBlocks.VERDANT_LEAVES.get(), ModBlocks.THORNY_VERDANT_LEAVES.get(),
				ModBlocks.LEAFY_VERDANT_VINE.get());
		// this.tag(BlockTags.FLOWER_POTS).add();
		// this.tag(BlockTags.SMALL_FLOWERS).add();

		// Mechanical.
		this.tag(BlockTags.CROPS).add(ModBlocks.CASSAVA_CROP.get(), ModBlocks.BITTER_CASSAVA_CROP.get(),
				ModBlocks.COFFEE_CROP.get());
		this.tag(BlockTags.DIRT).add(ModBlocks.VERDANT_ROOTED_DIRT.get(), ModBlocks.VERDANT_GRASS_BLOCK.get(),
				ModBlocks.VERDANT_MUD_GRASS_BLOCK.get(), ModBlocks.VERDANT_ROOTED_MUD.get(),
				ModBlocks.VERDANT_CLAY_GRASS_BLOCK.get(), ModBlocks.VERDANT_ROOTED_CLAY.get(),
				ModBlocks.CASSAVA_ROOTED_DIRT.get(), ModBlocks.BITTER_CASSAVA_ROOTED_DIRT.get());
		this.tag(BlockTags.MUSHROOM_GROW_BLOCK).add(ModBlocks.VERDANT_ROOTED_DIRT.get(),
				ModBlocks.VERDANT_GRASS_BLOCK.get(), ModBlocks.VERDANT_MUD_GRASS_BLOCK.get(),
				ModBlocks.VERDANT_ROOTED_MUD.get(), ModBlocks.VERDANT_CLAY_GRASS_BLOCK.get(),
				ModBlocks.VERDANT_ROOTED_CLAY.get());

		this.tag(BlockTags.CLIMBABLE).add(ModBlocks.VERDANT_VINE.get(), ModBlocks.VERDANT_TENDRIL_PLANT.get(),
				ModBlocks.POISON_IVY.get(), ModBlocks.POISON_IVY_PLANT.get(), ModBlocks.ROPE.get());
		this.tag(BlockTags.REPLACEABLE).add(ModBlocks.POISON_IVY.get(), ModBlocks.POISON_IVY_PLANT.get());
		this.tag(BlockTags.REPLACEABLE_BY_TREES).add(ModBlocks.POISON_IVY.get(), ModBlocks.POISON_IVY_PLANT.get());

		this.tag(BlockTags.BIG_DRIPLEAF_PLACEABLE).add(ModBlocks.VERDANT_ROOTED_MUD.get(),
				ModBlocks.VERDANT_MUD_GRASS_BLOCK.get(), ModBlocks.VERDANT_ROOTED_CLAY.get(),
				ModBlocks.VERDANT_CLAY_GRASS_BLOCK.get());
		this.tag(BlockTags.SMALL_DRIPLEAF_PLACEABLE).add(ModBlocks.VERDANT_ROOTED_MUD.get(),
				ModBlocks.VERDANT_MUD_GRASS_BLOCK.get(), ModBlocks.VERDANT_ROOTED_CLAY.get(),
				ModBlocks.VERDANT_CLAY_GRASS_BLOCK.get());

		// Anvil
		// this.tag(BlockTags.ANVIL).add();

		// Forge
		// this.tag(Tags.Blocks.ORE_RATES_DENSE).add(ModBlocks.ZIRCON_ORE.get());
		// this.tag(Tags.Blocks.ORE_RATES_DENSE).add(ModBlocks.DEEPSLATE_ZIRCON_ORE.get());
		// this.tag(Tags.Blocks.STORAGE_BLOCKS).add(ModBlocks.ZIRCON_BLOCK.get());
		// this.tag(Tags.Blocks.STORAGE_BLOCKS).add(ModBlocks.ZIRCONIUM_BLOCK.get());
		// this.tag(Tags.Blocks.STORAGE_BLOCKS).add(ModBlocks.RAW_ZIRCONIUM_BLOCK.get());

		// TODO
		this.tag(ModTags.Blocks.MATURE_VERDANT_LOGS).add(ModBlocks.VERDANT_HEARTWOOD_LOG.get(),
				ModBlocks.STRIPPED_VERDANT_HEARTWOOD_LOG.get(), ModBlocks.VERDANT_HEARTWOOD_WOOD.get(),
				ModBlocks.STRIPPED_VERDANT_HEARTWOOD_WOOD.get());

		// All vine replacable blocks.
		this.tag(ModTags.Blocks.VERDANT_VINE_REPLACABLES).addTag(BlockTags.REPLACEABLE);
		this.tag(ModTags.Blocks.VERDANT_VINE_REPLACABLES).addTag(BlockTags.REPLACEABLE_BY_TREES);
		this.tag(ModTags.Blocks.VERDANT_VINE_REPLACABLES).addTag(BlockTags.FLOWERS);
		this.tag(ModTags.Blocks.VERDANT_VINE_REPLACABLES).addTag(BlockTags.SNOW);
		this.tag(ModTags.Blocks.VERDANT_VINE_REPLACABLES).addTag(BlockTags.SIGNS);
		this.tag(ModTags.Blocks.VERDANT_VINE_REPLACABLES).addTag(BlockTags.WOOL_CARPETS);
		this.tag(ModTags.Blocks.VERDANT_VINE_REPLACABLES).addTag(BlockTags.STONE_BUTTONS);
		this.tag(ModTags.Blocks.VERDANT_VINE_REPLACABLES).addTag(BlockTags.WOODEN_BUTTONS);
		this.tag(ModTags.Blocks.VERDANT_VINE_REPLACABLES).addTag(BlockTags.STONE_PRESSURE_PLATES);
		this.tag(ModTags.Blocks.VERDANT_VINE_REPLACABLES).addTag(BlockTags.WOODEN_PRESSURE_PLATES);
		this.tag(ModTags.Blocks.VERDANT_VINE_REPLACABLES).add(Blocks.VINE);
		this.tag(ModTags.Blocks.VERDANT_VINE_REPLACABLES).add(Blocks.CAVE_VINES);
		this.tag(ModTags.Blocks.VERDANT_VINE_REPLACABLES).add(Blocks.CAVE_VINES_PLANT);
		this.tag(ModTags.Blocks.VERDANT_VINE_REPLACABLES).add(Blocks.AIR);
		this.tag(ModTags.Blocks.VERDANT_VINE_REPLACABLES).add(Blocks.CAVE_AIR);
		this.tag(ModTags.Blocks.VERDANT_VINE_REPLACABLES).add(Blocks.VOID_AIR);
		this.tag(ModTags.Blocks.VERDANT_VINE_REPLACABLES).add(Blocks.WATER);

		// Leafy blocks
		this.tag(ModTags.Blocks.VERDANT_LEAFY_BLOCKS).add(ModBlocks.WILTED_VERDANT_LEAVES.get(),
				ModBlocks.VERDANT_LEAVES.get(), ModBlocks.THORNY_VERDANT_LEAVES.get(),
				ModBlocks.POISON_IVY_VERDANT_LEAVES.get(), ModBlocks.LEAFY_VERDANT_VINE.get());
		// Vines
		this.tag(ModTags.Blocks.VERDANT_VINES).add(ModBlocks.VERDANT_VINE.get(), ModBlocks.LEAFY_VERDANT_VINE.get());
		// Logs
		this.tag(ModTags.Blocks.VERDANT_LOGS).add(ModBlocks.VERDANT_LOG.get(), ModBlocks.STRIPPED_VERDANT_LOG.get(),
				ModBlocks.VERDANT_WOOD.get(), ModBlocks.STRIPPED_VERDANT_WOOD.get());
		this.tag(ModTags.Blocks.VERDANT_LOGS).addTag(ModTags.Blocks.MATURE_VERDANT_LOGS);

		// Verdant ground
		this.tag(ModTags.Blocks.VERDANT_GROUND).add(ModBlocks.VERDANT_GRASS_BLOCK.get(),
				ModBlocks.VERDANT_ROOTED_DIRT.get(), ModBlocks.VERDANT_ROOTED_MUD.get(),
				ModBlocks.VERDANT_MUD_GRASS_BLOCK.get(), ModBlocks.VERDANT_ROOTED_CLAY.get(),
				ModBlocks.VERDANT_CLAY_GRASS_BLOCK.get());

		// Flowers
		this.tag(BlockTags.SMALL_FLOWERS).add(ModBlocks.BLEEDING_HEART.get());
		this.tag(BlockTags.SMALL_FLOWERS).add(ModBlocks.WILD_COFFEE.get());
		this.tag(BlockTags.FLOWER_POTS).add(ModBlocks.POTTED_BLEEDING_HEART.get());
		this.tag(BlockTags.FLOWER_POTS).add(ModBlocks.POTTED_WILD_COFFEE.get());
		this.tag(BlockTags.FLOWER_POTS).add(ModBlocks.POTTED_THORN_BUSH.get());

		// Add in ores.
		this.tag(Tags.Blocks.ORES).add(ModBlocks.DIRT_COAL_ORE.get(), ModBlocks.DIRT_COPPER_ORE.get(),
				ModBlocks.DIRT_DIAMOND_ORE.get(), ModBlocks.DIRT_EMERALD_ORE.get(), ModBlocks.DIRT_GOLD_ORE.get(),
				ModBlocks.DIRT_IRON_ORE.get(), ModBlocks.DIRT_LAPIS_ORE.get(), ModBlocks.DIRT_REDSTONE_ORE.get());
		this.tag(Tags.Blocks.ORES_COAL).add(ModBlocks.DIRT_COAL_ORE.get());
		this.tag(Tags.Blocks.ORES_COPPER).add(ModBlocks.DIRT_COPPER_ORE.get());
		this.tag(Tags.Blocks.ORES_DIAMOND).add(ModBlocks.DIRT_DIAMOND_ORE.get());
		this.tag(Tags.Blocks.ORES_EMERALD).add(ModBlocks.DIRT_EMERALD_ORE.get());
		this.tag(Tags.Blocks.ORES_GOLD).add(ModBlocks.DIRT_GOLD_ORE.get());
		this.tag(Tags.Blocks.ORES_IRON).add(ModBlocks.DIRT_IRON_ORE.get());
		this.tag(Tags.Blocks.ORES_LAPIS).add(ModBlocks.DIRT_LAPIS_ORE.get());
		this.tag(Tags.Blocks.ORES_REDSTONE).add(ModBlocks.DIRT_REDSTONE_ORE.get());

		// REMOVE vines from tree replaceables.
		this.tag(BlockTags.REPLACEABLE_BY_TREES).remove(ModTags.Blocks.VERDANT_VINES);
	}
}
