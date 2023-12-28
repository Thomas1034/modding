package com.thomas.zirconmod.datagen;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.item.ModItems;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagGenerator extends ItemTagsProvider {
	public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
			CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
		super(p_275343_, p_275729_, p_275322_, ZirconMod.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider pProvider) {
		
		// Adds in the copper armor as trimmable.
		this.tag(ItemTags.TRIMMABLE_ARMOR)
        .add(ModItems.COPPER_HELMET.get(),
                ModItems.COPPER_CHESTPLATE.get(),
                ModItems.COPPER_LEGGINGS.get(),
                ModItems.COPPER_BOOTS.get());
		// Adds in copper tools and weapons.
		this.tag(ItemTags.PICKAXES).add(ModItems.COPPER_PICKAXE.get());
		this.tag(ItemTags.HOES).add(ModItems.COPPER_HOE.get());
		this.tag(ItemTags.AXES).add(ModItems.COPPER_AXE.get());
		this.tag(ItemTags.SHOVELS).add(ModItems.COPPER_SHOVEL.get());
		this.tag(ItemTags.SWORDS).add(ModItems.COPPER_SWORD.get());

		// Adds in the zirconium armor as trimmable.
		this.tag(ItemTags.TRIMMABLE_ARMOR)
        .add(ModItems.ZIRCONIUM_HELMET.get(),
                ModItems.ZIRCONIUM_CHESTPLATE.get(),
                ModItems.ZIRCONIUM_LEGGINGS.get(),
                ModItems.ZIRCONIUM_BOOTS.get());
		// Adds in zirconium tools and weapons.
		this.tag(ItemTags.PICKAXES).add(ModItems.ZIRCONIUM_PICKAXE.get());
		this.tag(ItemTags.HOES).add(ModItems.ZIRCONIUM_HOE.get());
		this.tag(ItemTags.AXES).add(ModItems.ZIRCONIUM_AXE.get());
		this.tag(ItemTags.SHOVELS).add(ModItems.ZIRCONIUM_SHOVEL.get());
		this.tag(ItemTags.SWORDS).add(ModItems.ZIRCONIUM_SWORD.get());
		
		// Adds in the amethyst armor as trimmable.
		this.tag(ItemTags.TRIMMABLE_ARMOR)
        .add(ModItems.CITRINE_HELMET.get(),
                ModItems.CITRINE_CHESTPLATE.get(),
                ModItems.CITRINE_LEGGINGS.get(),
                ModItems.CITRINE_BOOTS.get());
		
		// Marks the flaming arrow as an arrow.
		this.tag(ItemTags.ARROWS).add(ModItems.FLAMING_ARROW.get());
		
		// Adds in palm wood items into all their tags, for safety.
		this.tag(ItemTags.WOODEN_BUTTONS).add(ModBlocks.PALM_BUTTON.get().asItem());
		this.tag(ItemTags.BUTTONS).add(ModBlocks.PALM_BUTTON.get().asItem());
		this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.PALM_PRESSURE_PLATE.get().asItem());
		this.tag(ItemTags.LOGS).add(ModBlocks.PALM_LOG.get().asItem(), ModBlocks.STRIPPED_PALM_LOG.get().asItem(), ModBlocks.PALM_WOOD.get().asItem(), ModBlocks.STRIPPED_PALM_WOOD.get().asItem());
		this.tag(ItemTags.LOGS_THAT_BURN).add(ModBlocks.PALM_LOG.get().asItem(), ModBlocks.STRIPPED_PALM_LOG.get().asItem(), ModBlocks.PALM_WOOD.get().asItem(), ModBlocks.STRIPPED_PALM_WOOD.get().asItem());
		this.tag(ItemTags.PLANKS).add(ModBlocks.PALM_PLANKS.get().asItem());
		this.tag(ItemTags.SLABS).add(ModBlocks.PALM_SLAB.get().asItem());
		this.tag(ItemTags.WOODEN_SLABS).add(ModBlocks.PALM_SLAB.get().asItem());
		this.tag(ItemTags.COMPLETES_FIND_TREE_TUTORIAL).add(ModBlocks.PALM_LOG.get().asItem(), ModBlocks.STRIPPED_PALM_LOG.get().asItem(), ModBlocks.PALM_WOOD.get().asItem(), ModBlocks.STRIPPED_PALM_WOOD.get().asItem());
		this.tag(ItemTags.DOORS).add(ModBlocks.PALM_DOOR.get().asItem());
		this.tag(ItemTags.WOODEN_DOORS).add(ModBlocks.PALM_DOOR.get().asItem());
		this.tag(ItemTags.TRAPDOORS).add(ModBlocks.PALM_TRAPDOOR.get().asItem());
		this.tag(ItemTags.WOODEN_TRAPDOORS).add(ModBlocks.PALM_TRAPDOOR.get().asItem());
		this.tag(ItemTags.FENCES).add(ModBlocks.PALM_FENCE.get().asItem());
		this.tag(ItemTags.WOODEN_FENCES).add(ModBlocks.PALM_FENCE.get().asItem());
		this.tag(ItemTags.FENCE_GATES).add(ModBlocks.PALM_FENCE_GATE.get().asItem());
		this.tag(ItemTags.STAIRS).add(ModBlocks.PALM_STAIRS.get().asItem());
		this.tag(ItemTags.WOODEN_STAIRS).add(ModBlocks.PALM_STAIRS.get().asItem());
	}
}
