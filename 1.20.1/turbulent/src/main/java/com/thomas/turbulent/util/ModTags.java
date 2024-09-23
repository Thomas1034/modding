package com.thomas.turbulent.util;

import com.thomas.turbulent.Turbulent;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public class ModTags {

	public static class Blocks {
		// Blocks that solidify clouds in any way.
		public static final TagKey<Block> CLOUD_SOLIDIFYING_BLOCKS = tag("cloud_solidifying_blocks");
		// Blocks that solidify clouds in a small radius.
		public static final TagKey<Block> WEAK_CLOUD_SOLIDIFYING_BLOCKS = tag("weak_cloud_solidifying_blocks");
		// Blocks that solidify clouds in a medium radius.
		public static final TagKey<Block> MEDIUM_CLOUD_SOLIDIFYING_BLOCKS = tag("medium_cloud_solidifying_blocks");
		// Blocks that solifify clouds in a large radius.
		public static final TagKey<Block> STRONG_CLOUD_SOLIDIFYING_BLOCKS = tag("strong_cloud_solidifying_blocks");

		private static TagKey<Block> tag(String name) {

			return BlockTags.create(new ResourceLocation(Turbulent.MOD_ID, name));
		}
	}

	public static class Structures {

		private static TagKey<Structure> tag(String name) {
			TagKey<Structure> tag = TagKey.create(Registries.STRUCTURE, new ResourceLocation(Turbulent.MOD_ID, name));
			// System.out.println("\n\n\nDEBUGGER TK: "+tag.toString()+" \n\n\n");
			return tag;
		}
	}

	public static class Items {

		// Items that, when worn as boots, allow an entity to walk on clouds
		public static final TagKey<Item> CLOUD_WALKABLE_BOOTS = tag("cloud_walkable_boots");
		// Items that can harvest cloud blocks.
		public static final TagKey<Item> CLOUD_HARVEST_ITEMS = tag("cloud_harvest_items");

		private static TagKey<Item> tag(String name) {
			return ItemTags.create(new ResourceLocation(Turbulent.MOD_ID, name));
		}
	}

	public static class EntityTypes {

		// Entities that can always walk on clouds.
		public static final TagKey<EntityType<?>> CLOUD_WALKABLE_MOBS = tag("cloud_walkable_mobs");

		private static TagKey<EntityType<?>> tag(String name) {
			return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(Turbulent.MOD_ID, name));
		}
	}

	public static class Effects {

		// Effects that allow entities to walk on clouds.
		public static final TagKey<MobEffect> CLOUD_WALKABLE_MOB_EFFECT = tag("cloud_walkable_mob_effect");

		private static TagKey<MobEffect> tag(String name) {

			return TagKey.create(Registries.MOB_EFFECT, new ResourceLocation(Turbulent.MOD_ID, name));
		}
	}

	public static class Biomes {

		private static TagKey<Biome> tag(String name) {
			return TagKey.create(Registries.BIOME, new ResourceLocation(Turbulent.MOD_ID, name));
		}
	}
}