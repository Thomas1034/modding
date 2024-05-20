package com.thomas.verdant.datagen;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.item.ModItems;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagGenerator extends ItemTagsProvider {
	public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
			CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
		super(p_275343_, p_275729_, p_275322_, Verdant.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider pProvider) {

		// Adds in armor as trimmable.
		this.tag(ItemTags.TRIMMABLE_ARMOR).add(ModItems.VERDANT_HEARTWOOD_HELMET.get(),
				ModItems.VERDANT_HEARTWOOD_CHESTPLATE.get(), ModItems.VERDANT_HEARTWOOD_LEGGINGS.get(),
				ModItems.VERDANT_HEARTWOOD_BOOTS.get());
		// Adds in copper tools and weapons.
		this.tag(ItemTags.PICKAXES).add(ModItems.VERDANT_HEARTWOOD_PICKAXE.get());
		this.tag(ItemTags.HOES).add(ModItems.VERDANT_HEARTWOOD_HOE.get());
		this.tag(ItemTags.AXES).add(ModItems.VERDANT_HEARTWOOD_AXE.get());
		this.tag(ItemTags.SHOVELS).add(ModItems.VERDANT_HEARTWOOD_SHOVEL.get());
		this.tag(ItemTags.SWORDS).add(ModItems.VERDANT_HEARTWOOD_SWORD.get());

		// Tag flowers
		this.tag(ItemTags.SMALL_FLOWERS).add(ModBlocks.BLEEDING_HEART.get().asItem());
		this.tag(ItemTags.SMALL_FLOWERS).add(ModBlocks.WILD_COFFEE.get().asItem());

		// Tag arrows
		this.tag(ItemTags.ARROWS).add(ModItems.POISON_ARROW.get());

		// Forge tags

		// Armor sets
		this.tag(Tags.Items.ARMORS).add(ModItems.VERDANT_HEARTWOOD_HELMET.get(),
				ModItems.VERDANT_HEARTWOOD_CHESTPLATE.get(), ModItems.VERDANT_HEARTWOOD_LEGGINGS.get(),
				ModItems.VERDANT_HEARTWOOD_BOOTS.get());
		this.tag(Tags.Items.ARMORS_HELMETS).add(ModItems.VERDANT_HEARTWOOD_HELMET.get());
		this.tag(Tags.Items.ARMORS_CHESTPLATES).add(ModItems.VERDANT_HEARTWOOD_CHESTPLATE.get());
		this.tag(Tags.Items.ARMORS_LEGGINGS).add(ModItems.VERDANT_HEARTWOOD_LEGGINGS.get());
		this.tag(Tags.Items.ARMORS_BOOTS).add(ModItems.VERDANT_HEARTWOOD_BOOTS.get());
		// Adds in copper tools and weapons.
		this.tag(Tags.Items.TOOLS).add(ModItems.VERDANT_HEARTWOOD_AXE.get(), ModItems.VERDANT_HEARTWOOD_SHOVEL.get(),
				ModItems.VERDANT_HEARTWOOD_HOE.get(), ModItems.VERDANT_HEARTWOOD_PICKAXE.get());

		// Crop
		// this.tag(Tags.Items.CROPS).add();

		// Dusts (for salt?)
		// this.tag(Tags.Items.DUSTS).add();

		// All the blocks.
		this.tag(ItemTags.WOODEN_TRAPDOORS).add(ModBlocks.VERDANT_TRAPDOOR.get().asItem(),
				ModBlocks.VERDANT_HEARTWOOD_TRAPDOOR.get().asItem());
		this.tag(ItemTags.TRAPDOORS).add(ModBlocks.VERDANT_TRAPDOOR.get().asItem(),
				ModBlocks.VERDANT_HEARTWOOD_TRAPDOOR.get().asItem());
		this.tag(ItemTags.WOODEN_DOORS).add(ModBlocks.VERDANT_DOOR.get().asItem(),
				ModBlocks.VERDANT_HEARTWOOD_DOOR.get().asItem());
		this.tag(ItemTags.DOORS).add(ModBlocks.VERDANT_DOOR.get().asItem(),
				ModBlocks.VERDANT_HEARTWOOD_DOOR.get().asItem());
		this.tag(ItemTags.WOODEN_SLABS).add(ModBlocks.VERDANT_SLAB.get().asItem(),
				ModBlocks.VERDANT_HEARTWOOD_SLAB.get().asItem());
		this.tag(ItemTags.SLABS).add(ModBlocks.VERDANT_SLAB.get().asItem(),
				ModBlocks.VERDANT_HEARTWOOD_SLAB.get().asItem());
		this.tag(ItemTags.WOODEN_STAIRS).add(ModBlocks.VERDANT_STAIRS.get().asItem(),
				ModBlocks.VERDANT_HEARTWOOD_STAIRS.get().asItem());
		this.tag(ItemTags.STAIRS).add(ModBlocks.VERDANT_STAIRS.get().asItem(),
				ModBlocks.VERDANT_HEARTWOOD_STAIRS.get().asItem());
		this.tag(ItemTags.WOODEN_BUTTONS).add(ModBlocks.VERDANT_BUTTON.get().asItem(),
				ModBlocks.VERDANT_HEARTWOOD_BUTTON.get().asItem());
		this.tag(ItemTags.BUTTONS).add(ModBlocks.VERDANT_BUTTON.get().asItem(),
				ModBlocks.VERDANT_HEARTWOOD_BUTTON.get().asItem());
		this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.VERDANT_PRESSURE_PLATE.get().asItem(),
				ModBlocks.VERDANT_HEARTWOOD_PRESSURE_PLATE.get().asItem());
		this.tag(ItemTags.WOODEN_FENCES).add(ModBlocks.VERDANT_FENCE.get().asItem(),
				ModBlocks.VERDANT_HEARTWOOD_FENCE.get().asItem());
		this.tag(ItemTags.FENCES).add(ModBlocks.VERDANT_FENCE.get().asItem(),
				ModBlocks.VERDANT_HEARTWOOD_FENCE.get().asItem());
		this.tag(ItemTags.FENCE_GATES).add(ModBlocks.VERDANT_FENCE_GATE.get().asItem(),
				ModBlocks.VERDANT_HEARTWOOD_FENCE_GATE.get().asItem());
		this.tag(ItemTags.PLANKS).add(ModBlocks.VERDANT_PLANKS.get().asItem(),
				ModBlocks.VERDANT_HEARTWOOD_PLANKS.get().asItem());
		this.tag(ItemTags.LOGS).add(ModBlocks.VERDANT_LOG.get().asItem(), ModBlocks.STRIPPED_VERDANT_LOG.get().asItem(),
				ModBlocks.VERDANT_WOOD.get().asItem(), ModBlocks.STRIPPED_VERDANT_WOOD.get().asItem(),
				ModBlocks.VERDANT_HEARTWOOD_LOG.get().asItem(), ModBlocks.STRIPPED_VERDANT_HEARTWOOD_LOG.get().asItem(),
				ModBlocks.VERDANT_HEARTWOOD_WOOD.get().asItem(),
				ModBlocks.STRIPPED_VERDANT_HEARTWOOD_WOOD.get().asItem());
		this.tag(ItemTags.LOGS_THAT_BURN).add(ModBlocks.VERDANT_LOG.get().asItem(),
				ModBlocks.STRIPPED_VERDANT_LOG.get().asItem(), ModBlocks.VERDANT_WOOD.get().asItem(),
				ModBlocks.STRIPPED_VERDANT_WOOD.get().asItem(), ModBlocks.VERDANT_HEARTWOOD_LOG.get().asItem(),
				ModBlocks.STRIPPED_VERDANT_HEARTWOOD_LOG.get().asItem(),
				ModBlocks.VERDANT_HEARTWOOD_WOOD.get().asItem(),
				ModBlocks.STRIPPED_VERDANT_HEARTWOOD_WOOD.get().asItem());
		this.tag(ItemTags.LEAVES).add(ModBlocks.VERDANT_LEAVES.get().asItem(),
				ModBlocks.THORNY_VERDANT_LEAVES.get().asItem(), ModBlocks.LEAFY_VERDANT_VINE.get().asItem());
		this.tag(Tags.Items.FENCE_GATES).add(ModBlocks.VERDANT_FENCE_GATE.get().asItem());
		this.tag(Tags.Items.FENCE_GATES_WOODEN).add(ModBlocks.VERDANT_FENCE_GATE.get().asItem());
	}
}
