package com.thomas.zirconmod.util;

import com.thomas.zirconmod.ZirconMod;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public class ModTags {
	public static class Blocks {

		public static final TagKey<Block> CLOUD_SOLIDIFYING_BLOCKS = tag("cloud_solidifying_blocks");
		public static final TagKey<Block> WEAK_CLOUD_SOLIDIFYING_BLOCKS = tag("weak_cloud_solidifying_blocks");
		public static final TagKey<Block> MEDIUM_CLOUD_SOLIDIFYING_BLOCKS = tag("medium_cloud_solidifying_blocks");
		public static final TagKey<Block> STRONG_CLOUD_SOLIDIFYING_BLOCKS = tag("strong_cloud_solidifying_blocks");
		public static final TagKey<Block> NEEDS_COPPER_TOOL = tag("needs_copper_tool");
		public static final TagKey<Block> NEEDS_ZIRCONIUM_TOOL = tag("needs_zirconium_tool");
		public static final TagKey<Block> PALM_LOGS = tag("palm_logs");
		public static final TagKey<Block> CLOUD_PLANTS = tag("cloud_plants");
		public static final TagKey<Block> SPEAR_EFFICIENT = tag("spear_efficient");


		private static TagKey<Block> tag(String name) {
			return BlockTags.create(new ResourceLocation(ZirconMod.MOD_ID, name));
		}
	}

	public static class Structures {
		public static final TagKey<Structure> VIGIL_EYE_LOCATED = tag("vigil_eye_located");
		public static final TagKey<Structure> QUEST_EYE_LOCATED = tag("quest_eye_located");

		private static TagKey<Structure> tag(String name) {
			TagKey<Structure> tag = TagKey.create(Registries.STRUCTURE, new ResourceLocation(ZirconMod.MOD_ID, name));
			// System.out.println("\n\n\nDEBUGGER TK: "+tag.toString()+" \n\n\n");
			return tag;
		}
	}

	public static class Items {
		public static final TagKey<Item> SCULK_AWAKENING_ITEMS = tag("sculk_awakening_items");
		public static final TagKey<Item> CLOUD_WALKABLE_ITEMS = tag("cloud_walkable_items");
		public static final TagKey<Item> CLOUD_HARVEST_ITEMS = tag("cloud_harvest_items");
		public static final TagKey<Item> BERRIES = tag("berries");

		private static TagKey<Item> tag(String name) {
			return ItemTags.create(new ResourceLocation(ZirconMod.MOD_ID, name));
		}
	}

	public static class EntityTypes {
		public static final TagKey<EntityType<?>> CLOUD_WALKABLE_MOBS = tag("cloud_walkable_mobs");
		public static final TagKey<EntityType<?>> CLOUD_SPAWNABLE_MOBS = tag("cloud_spawnable_mobs");
		
		private static TagKey<EntityType<?>> tag(String name) {
			return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(ZirconMod.MOD_ID, name));
		}
	}
	
	public static class Biomes {
		public static final TagKey<Biome> HAS_BEACH_PALMS = tag("has_beach_palms");
		
		private static TagKey<Biome> tag(String name) {
			return TagKey.create(Registries.BIOME, new ResourceLocation(ZirconMod.MOD_ID, name));
		}
	}
}