package com.thomas.cloudscape.datagen;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import com.thomas.cloudscape.Cloudscape;
import com.thomas.cloudscape.block.ModBlocks;
import com.thomas.cloudscape.item.ModItems;
import com.thomas.cloudscape.util.ModTags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagGenerator extends ItemTagsProvider {
	public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
			CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
		super(p_275343_, p_275729_, p_275322_, Cloudscape.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider pProvider) {

		// Adds in the copper armor as trimmable.
		this.tag(ItemTags.TRIMMABLE_ARMOR).add(ModItems.COPPER_HELMET.get(), ModItems.COPPER_CHESTPLATE.get(),
				ModItems.COPPER_LEGGINGS.get(), ModItems.COPPER_BOOTS.get());
		// Adds in copper tools and weapons.
		this.tag(ItemTags.PICKAXES).add(ModItems.COPPER_PICKAXE.get());
		this.tag(ItemTags.HOES).add(ModItems.COPPER_HOE.get());
		this.tag(ItemTags.AXES).add(ModItems.COPPER_AXE.get());
		this.tag(ItemTags.SHOVELS).add(ModItems.COPPER_SHOVEL.get());
		this.tag(ItemTags.SWORDS).add(ModItems.COPPER_SWORD.get());

		// Adds in the zirconium armor as trimmable.
		this.tag(ItemTags.TRIMMABLE_ARMOR).add(ModItems.ZIRCONIUM_HELMET.get(), ModItems.ZIRCONIUM_CHESTPLATE.get(),
				ModItems.ZIRCONIUM_LEGGINGS.get(), ModItems.ZIRCONIUM_BOOTS.get());
		// Adds in zirconium tools and weapons.
		this.tag(ItemTags.PICKAXES).add(ModItems.ZIRCONIUM_PICKAXE.get());
		this.tag(ItemTags.HOES).add(ModItems.ZIRCONIUM_HOE.get());
		this.tag(ItemTags.AXES).add(ModItems.ZIRCONIUM_AXE.get());
		this.tag(ItemTags.SHOVELS).add(ModItems.ZIRCONIUM_SHOVEL.get());
		this.tag(ItemTags.SWORDS).add(ModItems.ZIRCONIUM_SWORD.get());

		// Adds in the citrine armor as trimmable.
		this.tag(ItemTags.TRIMMABLE_ARMOR).add(ModItems.CITRINE_HELMET.get(), ModItems.CITRINE_CHESTPLATE.get(),
				ModItems.CITRINE_LEGGINGS.get(), ModItems.CITRINE_BOOTS.get());
		// Adds in citrine tools and weapons.
		this.tag(ItemTags.PICKAXES).add(ModItems.CITRINE_PICKAXE.get());
		this.tag(ItemTags.HOES).add(ModItems.CITRINE_HOE.get());
		this.tag(ItemTags.AXES).add(ModItems.CITRINE_AXE.get());
		this.tag(ItemTags.SHOVELS).add(ModItems.CITRINE_SHOVEL.get());
		this.tag(ItemTags.SWORDS).add(ModItems.CITRINE_SWORD.get());
		this.tag(ModTags.Items.CLOUD_HARVEST_ITEMS).add(ModItems.CITRINE_PICKAXE.get());
		this.tag(ModTags.Items.CLOUD_HARVEST_ITEMS).add(ModItems.CITRINE_HOE.get());
		this.tag(ModTags.Items.CLOUD_HARVEST_ITEMS).add(ModItems.CITRINE_AXE.get());
		this.tag(ModTags.Items.CLOUD_HARVEST_ITEMS).add(ModItems.CITRINE_SHOVEL.get());
		this.tag(ModTags.Items.CLOUD_HARVEST_ITEMS).add(ModItems.CITRINE_SWORD.get());
		this.tag(ModTags.Items.CLOUD_HARVEST_ITEMS).add(ModItems.CITRINE_SPEAR.get());

		// Marks the flaming arrow as an arrow.
		this.tag(ItemTags.ARROWS).add(ModItems.FLAMING_ARROW.get());

		// Adds in palm wood items into all their tags, for safety.
		this.tag(ItemTags.WOODEN_BUTTONS).add(ModBlocks.PALM_BUTTON.get().asItem());
		this.tag(ItemTags.BUTTONS).add(ModBlocks.PALM_BUTTON.get().asItem());
		this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.PALM_PRESSURE_PLATE.get().asItem());
		this.tag(ItemTags.LOGS).add(ModBlocks.PALM_LOG.get().asItem(), ModBlocks.STRIPPED_PALM_LOG.get().asItem(),
				ModBlocks.PALM_WOOD.get().asItem(), ModBlocks.STRIPPED_PALM_WOOD.get().asItem());
		this.tag(ItemTags.LOGS_THAT_BURN).add(ModBlocks.PALM_LOG.get().asItem(),
				ModBlocks.STRIPPED_PALM_LOG.get().asItem(), ModBlocks.PALM_WOOD.get().asItem(),
				ModBlocks.STRIPPED_PALM_WOOD.get().asItem());
		this.tag(ItemTags.PLANKS).add(ModBlocks.PALM_PLANKS.get().asItem());
		this.tag(ItemTags.SLABS).add(ModBlocks.PALM_SLAB.get().asItem());
		this.tag(ItemTags.WOODEN_SLABS).add(ModBlocks.PALM_SLAB.get().asItem());
		this.tag(ItemTags.COMPLETES_FIND_TREE_TUTORIAL).add(ModBlocks.PALM_LOG.get().asItem(),
				ModBlocks.STRIPPED_PALM_LOG.get().asItem(), ModBlocks.PALM_WOOD.get().asItem(),
				ModBlocks.STRIPPED_PALM_WOOD.get().asItem());
		this.tag(ItemTags.DOORS).add(ModBlocks.PALM_DOOR.get().asItem());
		this.tag(ItemTags.WOODEN_DOORS).add(ModBlocks.PALM_DOOR.get().asItem());
		this.tag(ItemTags.TRAPDOORS).add(ModBlocks.PALM_TRAPDOOR.get().asItem());
		this.tag(ItemTags.WOODEN_TRAPDOORS).add(ModBlocks.PALM_TRAPDOOR.get().asItem());
		this.tag(ItemTags.FENCES).add(ModBlocks.PALM_FENCE.get().asItem());
		this.tag(ItemTags.WOODEN_FENCES).add(ModBlocks.PALM_FENCE.get().asItem());
		this.tag(ItemTags.FENCE_GATES).add(ModBlocks.PALM_FENCE_GATE.get().asItem());
		this.tag(ItemTags.STAIRS).add(ModBlocks.PALM_STAIRS.get().asItem());
		this.tag(ItemTags.WOODEN_STAIRS).add(ModBlocks.PALM_STAIRS.get().asItem());

		// Tag flowers
		this.tag(ItemTags.SMALL_FLOWERS).add(ModBlocks.ILLUMINATED_TORCHFLOWER.get().asItem(),
				ModBlocks.WHITE_ORCHID.get().asItem());

		// Marks Echo Powder as a sculk awakening item.
		this.tag(ModTags.Items.SCULK_AWAKENING_ITEMS).add(ModItems.ECHO_POWDER.get());

		// Forge tags
		// Adds in the copper armor.
		this.tag(Tags.Items.ARMORS).add(ModItems.COPPER_HELMET.get(), ModItems.COPPER_CHESTPLATE.get(),
				ModItems.COPPER_LEGGINGS.get(), ModItems.COPPER_BOOTS.get());
		this.tag(Tags.Items.ARMORS_HELMETS).add(ModItems.COPPER_HELMET.get());
		this.tag(Tags.Items.ARMORS_CHESTPLATES).add(ModItems.COPPER_CHESTPLATE.get());
		this.tag(Tags.Items.ARMORS_LEGGINGS).add(ModItems.COPPER_LEGGINGS.get());
		this.tag(Tags.Items.ARMORS_BOOTS).add(ModItems.COPPER_BOOTS.get());
		// Adds in copper tools and weapons.
		this.tag(Tags.Items.TOOLS).add(ModItems.COPPER_PICKAXE.get());
		this.tag(Tags.Items.TOOLS).add(ModItems.COPPER_HOE.get());
		this.tag(Tags.Items.TOOLS).add(ModItems.COPPER_AXE.get());
		this.tag(Tags.Items.TOOLS).add(ModItems.COPPER_SHOVEL.get());
		this.tag(Tags.Items.TOOLS).add(ModItems.COPPER_SWORD.get());

		// Adds in the zirconium armor.
		this.tag(Tags.Items.ARMORS).add(ModItems.ZIRCONIUM_HELMET.get(), ModItems.ZIRCONIUM_CHESTPLATE.get(),
				ModItems.ZIRCONIUM_LEGGINGS.get(), ModItems.ZIRCONIUM_BOOTS.get());
		this.tag(Tags.Items.ARMORS_HELMETS).add(ModItems.ZIRCONIUM_HELMET.get());
		this.tag(Tags.Items.ARMORS_CHESTPLATES).add(ModItems.ZIRCONIUM_CHESTPLATE.get());
		this.tag(Tags.Items.ARMORS_LEGGINGS).add(ModItems.ZIRCONIUM_LEGGINGS.get());
		this.tag(Tags.Items.ARMORS_BOOTS).add(ModItems.ZIRCONIUM_BOOTS.get());
		// Adds in zirconium tools and weapons.
		this.tag(Tags.Items.TOOLS).add(ModItems.ZIRCONIUM_PICKAXE.get());
		this.tag(Tags.Items.TOOLS).add(ModItems.ZIRCONIUM_HOE.get());
		this.tag(Tags.Items.TOOLS).add(ModItems.ZIRCONIUM_AXE.get());
		this.tag(Tags.Items.TOOLS).add(ModItems.ZIRCONIUM_SHOVEL.get());
		this.tag(Tags.Items.TOOLS).add(ModItems.ZIRCONIUM_SWORD.get());

		// Adds in the citrine armor as trimmable.
		this.tag(Tags.Items.ARMORS).add(ModItems.CITRINE_HELMET.get(), ModItems.CITRINE_CHESTPLATE.get(),
				ModItems.CITRINE_LEGGINGS.get(), ModItems.CITRINE_BOOTS.get());
		this.tag(Tags.Items.ARMORS_HELMETS).add(ModItems.CITRINE_HELMET.get());
		this.tag(Tags.Items.ARMORS_CHESTPLATES).add(ModItems.CITRINE_CHESTPLATE.get());
		this.tag(Tags.Items.ARMORS_LEGGINGS).add(ModItems.CITRINE_LEGGINGS.get());
		this.tag(Tags.Items.ARMORS_BOOTS).add(ModItems.CITRINE_BOOTS.get());

		// Adds in citrine tools and weapons.
		this.tag(Tags.Items.TOOLS).add(ModItems.CITRINE_PICKAXE.get());
		this.tag(Tags.Items.TOOLS).add(ModItems.CITRINE_HOE.get());
		this.tag(Tags.Items.TOOLS).add(ModItems.CITRINE_AXE.get());
		this.tag(Tags.Items.TOOLS).add(ModItems.CITRINE_SHOVEL.get());
		this.tag(Tags.Items.TOOLS).add(ModItems.CITRINE_SWORD.get());

		// Blueberry crop
		this.tag(Tags.Items.CROPS).add(ModItems.BLUEBERRY_SEEDS.get());

		this.tag(Tags.Items.DUSTS).add(ModItems.ECHO_POWDER.get());
		this.tag(Tags.Items.GEMS).add(ModItems.ZIRCON.get());
		this.tag(Tags.Items.GEMS).add(ModItems.ZIRCON_SHARD.get());
		this.tag(Tags.Items.INGOTS).add(ModItems.ZIRCONIUM_INGOT.get());
		this.tag(Tags.Items.RAW_MATERIALS).add(ModItems.RAW_ZIRCONIUM.get());

		// All the blocks.
		this.tag(Tags.Items.FENCE_GATES).add(ModBlocks.PALM_FENCE_GATE.get().asItem());
		this.tag(Tags.Items.FENCE_GATES_WOODEN).add(ModBlocks.PALM_FENCE_GATE.get().asItem());
		this.tag(Tags.Items.ORE_RATES_DENSE).add(ModBlocks.ZIRCON_ORE.get().asItem());
		this.tag(Tags.Items.ORE_RATES_DENSE).add(ModBlocks.DEEPSLATE_ZIRCON_ORE.get().asItem());
		this.tag(Tags.Items.STORAGE_BLOCKS).add(ModBlocks.ZIRCON_BLOCK.get().asItem());
		this.tag(Tags.Items.STORAGE_BLOCKS).add(ModBlocks.ZIRCONIUM_BLOCK.get().asItem());
		this.tag(Tags.Items.STORAGE_BLOCKS).add(ModBlocks.RAW_ZIRCONIUM_BLOCK.get().asItem());
		this.tag(Tags.Items.ORES).add(ModBlocks.ZIRCON_ORE.get().asItem());
		this.tag(Tags.Items.ORES).add(ModBlocks.DEEPSLATE_ZIRCON_ORE.get().asItem());
		this.tag(Tags.Items.ORES_IN_GROUND_DEEPSLATE).add(ModBlocks.ZIRCON_ORE.get().asItem());
		this.tag(Tags.Items.ORES_IN_GROUND_STONE).add(ModBlocks.DEEPSLATE_ZIRCON_ORE.get().asItem());

		// Tag all the berries
		this.tag(ModTags.Items.BERRIES).add(ModItems.BLUEBERRY.get());
		this.tag(ModTags.Items.BERRIES).add(ModItems.BUBBLEFRUIT.get());
		this.tag(ModTags.Items.BERRIES).add(Items.GLOW_BERRIES);
		this.tag(ModTags.Items.BERRIES).add(Items.SWEET_BERRIES);

	}
}
