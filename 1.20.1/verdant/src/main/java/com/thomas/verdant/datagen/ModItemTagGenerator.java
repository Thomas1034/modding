package com.thomas.verdant.datagen;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.item.ModItems;
import com.thomas.verdant.util.ModTags;

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

		ModBlocks.VERDANT_HEARTWOOD.addItemTags(this);
		ModBlocks.VERDANT.addItemTags(this);

		// Friendly armors
		this.tag(ModTags.Items.VERDANT_FRIENDLY_ARMORS).add(ModItems.VERDANT_HEARTWOOD_HELMET.get(),
				ModItems.VERDANT_HEARTWOOD_CHESTPLATE.get(), ModItems.VERDANT_HEARTWOOD_LEGGINGS.get(),
				ModItems.VERDANT_HEARTWOOD_BOOTS.get(), ModItems.IMBUED_VERDANT_HEARTWOOD_HELMET.get(),
				ModItems.IMBUED_VERDANT_HEARTWOOD_CHESTPLATE.get(), ModItems.IMBUED_VERDANT_HEARTWOOD_LEGGINGS.get(),
				ModItems.IMBUED_VERDANT_HEARTWOOD_BOOTS.get());

		// Adds in armor as trimmable.
		this.tag(ItemTags.TRIMMABLE_ARMOR).add(ModItems.VERDANT_HEARTWOOD_HELMET.get(),
				ModItems.VERDANT_HEARTWOOD_CHESTPLATE.get(), ModItems.VERDANT_HEARTWOOD_LEGGINGS.get(),
				ModItems.VERDANT_HEARTWOOD_BOOTS.get(), ModItems.IMBUED_VERDANT_HEARTWOOD_HELMET.get(),
				ModItems.IMBUED_VERDANT_HEARTWOOD_CHESTPLATE.get(), ModItems.IMBUED_VERDANT_HEARTWOOD_LEGGINGS.get(),
				ModItems.IMBUED_VERDANT_HEARTWOOD_BOOTS.get());
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
				ModItems.VERDANT_HEARTWOOD_BOOTS.get(), ModItems.IMBUED_VERDANT_HEARTWOOD_HELMET.get(),
				ModItems.IMBUED_VERDANT_HEARTWOOD_CHESTPLATE.get(), ModItems.IMBUED_VERDANT_HEARTWOOD_LEGGINGS.get(),
				ModItems.IMBUED_VERDANT_HEARTWOOD_BOOTS.get());
		this.tag(Tags.Items.ARMORS_HELMETS).add(ModItems.VERDANT_HEARTWOOD_HELMET.get(),
				ModItems.IMBUED_VERDANT_HEARTWOOD_HELMET.get());
		this.tag(Tags.Items.ARMORS_CHESTPLATES).add(ModItems.VERDANT_HEARTWOOD_CHESTPLATE.get(),
				ModItems.IMBUED_VERDANT_HEARTWOOD_CHESTPLATE.get());
		this.tag(Tags.Items.ARMORS_LEGGINGS).add(ModItems.VERDANT_HEARTWOOD_LEGGINGS.get(),
				ModItems.IMBUED_VERDANT_HEARTWOOD_LEGGINGS.get());
		this.tag(Tags.Items.ARMORS_BOOTS).add(ModItems.VERDANT_HEARTWOOD_BOOTS.get(),
				ModItems.IMBUED_VERDANT_HEARTWOOD_BOOTS.get());
		// Adds in copper tools and weapons.
		this.tag(Tags.Items.TOOLS).add(ModItems.VERDANT_HEARTWOOD_AXE.get(), ModItems.VERDANT_HEARTWOOD_SHOVEL.get(),
				ModItems.VERDANT_HEARTWOOD_HOE.get(), ModItems.VERDANT_HEARTWOOD_PICKAXE.get());

		// Crop
		// this.tag(Tags.Items.CROPS).add();

		// Dusts (for salt?)
		// this.tag(Tags.Items.DUSTS).add();

		// All the blocks.

		this.tag(ItemTags.LEAVES).add(ModBlocks.VERDANT_LEAVES.get().asItem(),
				ModBlocks.THORNY_VERDANT_LEAVES.get().asItem(), ModBlocks.LEAFY_VERDANT_VINE.get().asItem());
	}
}
