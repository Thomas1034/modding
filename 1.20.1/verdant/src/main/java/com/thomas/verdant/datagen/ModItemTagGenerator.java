package com.thomas.verdant.datagen;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import com.thomas.verdant.Verdant;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagGenerator extends ItemTagsProvider {
	public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
			CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
		super(p_275343_, p_275729_, p_275322_, Verdant.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider pProvider) {

		// Adds in the copper armor as trimmable.
		//this.tag(ItemTags.TRIMMABLE_ARMOR).add();
		// Adds in copper tools and weapons.
		//this.tag(ItemTags.PICKAXES).add();
		//this.tag(ItemTags.HOES).add();
		//this.tag(ItemTags.AXES).add();
		//this.tag(ItemTags.SHOVELS).add();
		//this.tag(ItemTags.SWORDS).add();

		

		// Adds in palm wood items into all their tags, for safety.
		//this.tag(ItemTags.WOODEN_BUTTONS).add();
		//this.tag(ItemTags.BUTTONS).add();
		//this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add();
		//this.tag(ItemTags.LOGS).add();
		//this.tag(ItemTags.LOGS_THAT_BURN).add();
		//this.tag(ItemTags.PLANKS).add();
		//this.tag(ItemTags.SLABS).add();
		//this.tag(ItemTags.WOODEN_SLABS).add();
		//this.tag(ItemTags.COMPLETES_FIND_TREE_TUTORIAL).add();
		//this.tag(ItemTags.DOORS).add();
		//this.tag(ItemTags.WOODEN_DOORS).add();
		//this.tag(ItemTags.TRAPDOORS).add();
		///this.tag(ItemTags.WOODEN_TRAPDOORS).add();
		//this.tag(ItemTags.FENCES).add();
		//this.tag(ItemTags.WOODEN_FENCES).add();
		//this.tag(ItemTags.FENCE_GATES).add();
		//this.tag(ItemTags.STAIRS).add();
		//this.tag(ItemTags.WOODEN_STAIRS).add();

		// Tag flowers
		//this.tag(ItemTags.SMALL_FLOWERS).add();

		// Forge tags
		
		// Armor sets
		//this.tag(Tags.Items.ARMORS).add();
		//this.tag(Tags.Items.ARMORS_HELMETS).add();
		//this.tag(Tags.Items.ARMORS_CHESTPLATES).add();
		//this.tag(Tags.Items.ARMORS_LEGGINGS).add();
		//this.tag(Tags.Items.ARMORS_BOOTS).add();
		// Adds in copper tools and weapons.
		//this.tag(Tags.Items.TOOLS).add();

		
		// Blueberry crop
		//this.tag(Tags.Items.CROPS).add();

		//this.tag(Tags.Items.DUSTS).add();

		// All the blocks.
		//this.tag(Tags.Items.FENCE_GATES).add();
		//this.tag(Tags.Items.FENCE_GATES_WOODEN).add();
		
	}
}
