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
	protected void addTags(HolderLookup.Provider pProvider) {
		// this.tag(ModTags.Blocks.METAL_DETECTOR_VALUABLES).add(ModBlocks.SAPPHIRE_ORE.get()).addTag(Tags.Blocks.ORES);

		// Mineables
		// this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add();

		this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.VERDANT_LOG.get(), ModBlocks.VERDANT_WOOD.get(),
				ModBlocks.STRIPPED_VERDANT_LOG.get(), ModBlocks.STRIPPED_VERDANT_WOOD.get(),
				ModBlocks.VERDANT_BUTTON.get(), ModBlocks.VERDANT_DOOR.get(), ModBlocks.VERDANT_FENCE.get(),
				ModBlocks.VERDANT_FENCE_GATE.get(), ModBlocks.VERDANT_PLANKS.get(),
				ModBlocks.VERDANT_PRESSURE_PLATE.get(), ModBlocks.VERDANT_SLAB.get(), ModBlocks.VERDANT_TRAPDOOR.get());
		this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.VERDANT_GRASS_BLOCK.get(),
				ModBlocks.VERDANT_ROOTED_DIRT.get());

		// Verdant furniture
		this.tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.VERDANT_TRAPDOOR.get());
		this.tag(BlockTags.TRAPDOORS).add(ModBlocks.VERDANT_TRAPDOOR.get());
		this.tag(BlockTags.WOODEN_DOORS).add(ModBlocks.VERDANT_DOOR.get());
		this.tag(BlockTags.DOORS).add(ModBlocks.VERDANT_DOOR.get());
		this.tag(BlockTags.WOODEN_SLABS).add(ModBlocks.VERDANT_SLAB.get());
		this.tag(BlockTags.SLABS).add(ModBlocks.VERDANT_SLAB.get());
		this.tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.VERDANT_STAIRS.get());
		this.tag(BlockTags.STAIRS).add(ModBlocks.VERDANT_STAIRS.get());
		this.tag(BlockTags.WOODEN_BUTTONS).add(ModBlocks.VERDANT_BUTTON.get());
		this.tag(BlockTags.BUTTONS).add(ModBlocks.VERDANT_BUTTON.get());
		this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.VERDANT_PRESSURE_PLATE.get());
		this.tag(BlockTags.PRESSURE_PLATES).add(ModBlocks.VERDANT_PRESSURE_PLATE.get());
		this.tag(BlockTags.WOODEN_FENCES).add(ModBlocks.VERDANT_FENCE.get());
		this.tag(BlockTags.FENCES).add(ModBlocks.VERDANT_FENCE.get());
		this.tag(BlockTags.FENCE_GATES).add(ModBlocks.VERDANT_FENCE_GATE.get());
		this.tag(BlockTags.PLANKS).add(ModBlocks.VERDANT_PLANKS.get());
		this.tag(ModTags.Blocks.VERDANT_LOGS).add(ModBlocks.VERDANT_LOG.get(), ModBlocks.STRIPPED_VERDANT_LOG.get(),
				ModBlocks.VERDANT_WOOD.get(), ModBlocks.STRIPPED_VERDANT_WOOD.get());
		this.tag(BlockTags.LOGS).add(ModBlocks.VERDANT_LOG.get(), ModBlocks.STRIPPED_VERDANT_LOG.get(),
				ModBlocks.VERDANT_WOOD.get(), ModBlocks.STRIPPED_VERDANT_WOOD.get());
		// this.tag(BlockTags.FLOWER_POTS).add();
		// this.tag(BlockTags.SMALL_FLOWERS).add();

		this.tag(BlockTags.DIRT).add(ModBlocks.VERDANT_ROOTED_DIRT.get(), ModBlocks.VERDANT_GRASS_BLOCK.get(),
				ModBlocks.VERDANT_MUD_GRASS_BLOCK.get(), ModBlocks.VERDANT_ROOTED_MUD.get());

		// Anvil
		// this.tag(BlockTags.ANVIL).add();

		// Forge
		this.tag(Tags.Blocks.FENCE_GATES).add(ModBlocks.VERDANT_FENCE_GATE.get());
		this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(ModBlocks.VERDANT_FENCE_GATE.get());
		// this.tag(Tags.Blocks.ORE_RATES_DENSE).add(ModBlocks.ZIRCON_ORE.get());
		// this.tag(Tags.Blocks.ORE_RATES_DENSE).add(ModBlocks.DEEPSLATE_ZIRCON_ORE.get());
		// this.tag(Tags.Blocks.STORAGE_BLOCKS).add(ModBlocks.ZIRCON_BLOCK.get());
		// this.tag(Tags.Blocks.STORAGE_BLOCKS).add(ModBlocks.ZIRCONIUM_BLOCK.get());
		// this.tag(Tags.Blocks.STORAGE_BLOCKS).add(ModBlocks.RAW_ZIRCONIUM_BLOCK.get());

	}
}
