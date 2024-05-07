package com.thomas.verdant.datagen;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.thomas.verdant.Verdant;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
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

		// this.tag(BlockTags.MINEABLE_WITH_AXE).add();

		// Tool tiers
		// this.tag(BlockTags.NEEDS_IRON_TOOL).add();

		// this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add();

		// this.tag(BlockTags.NEEDS_STONE_TOOL).add();

		// this.tag(BlockTags.STONE_ORE_REPLACEABLES).add();

		// this.tag(Tags.Blocks.NEEDS_NETHERITE_TOOL).add(ModBlocks.END_STONE_SAPPHIRE_ORE.get());

		// this.tag(ModTags.Blocks.NEEDS_SAPPHIRE_TOOL).add(ModBlocks.SOUND_BLOCK.get());

		// Other block tags

		// this.tag(BlockTags.BUTTONS).add();

		// Palm furniture
		// this.tag(BlockTags.WOODEN_TRAPDOORS).add();
		// this.tag(BlockTags.TRAPDOORS).add();
		// this.tag(BlockTags.WOODEN_DOORS).add();
		// this.tag(BlockTags.DOORS).add();
		// this.tag(BlockTags.WOODEN_SLABS).add();
		// this.tag(BlockTags.SLABS).add();
		// this.tag(BlockTags.WOODEN_STAIRS).add();
		// this.tag(BlockTags.STAIRS).add();
		// this.tag(BlockTags.WOODEN_BUTTONS).add();
		// this.tag(BlockTags.BUTTONS).add();
		// this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add();
		// this.tag(BlockTags.PRESSURE_PLATES).add();
		// this.tag(BlockTags.WOODEN_FENCES).add();
		// this.tag(BlockTags.FENCES).add();
		// this.tag(BlockTags.FENCE_GATES).add();
		// this.tag(BlockTags.PLANKS).add();
		// this.tag(ModTags.Blocks.VERDANT_LOGS).add();
		// this.tag(BlockTags.LOGS).add();
		// this.tag(BlockTags.FLOWER_POTS).add();
		// this.tag(BlockTags.SMALL_FLOWERS).add();

		// Anvil
		// this.tag(BlockTags.ANVIL).add();

		// Forge
		// this.tag(Tags.Blocks.FENCE_GATES).add(ModBlocks.PALM_FENCE_GATE.get());
		// this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(ModBlocks.PALM_FENCE_GATE.get());
		// this.tag(Tags.Blocks.ORE_RATES_DENSE).add(ModBlocks.ZIRCON_ORE.get());
		// this.tag(Tags.Blocks.ORE_RATES_DENSE).add(ModBlocks.DEEPSLATE_ZIRCON_ORE.get());
		// this.tag(Tags.Blocks.STORAGE_BLOCKS).add(ModBlocks.ZIRCON_BLOCK.get());
		// this.tag(Tags.Blocks.STORAGE_BLOCKS).add(ModBlocks.ZIRCONIUM_BLOCK.get());
		// this.tag(Tags.Blocks.STORAGE_BLOCKS).add(ModBlocks.RAW_ZIRCONIUM_BLOCK.get());
	}
}
