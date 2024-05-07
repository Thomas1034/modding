package com.thomas.verdant.util;

import com.thomas.verdant.Verdant;

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

		private static TagKey<Block> tag(String name) {
			return BlockTags.create(new ResourceLocation(Verdant.MOD_ID, name));
		}
	}

	public static class Structures {

		private static TagKey<Structure> tag(String name) {
			TagKey<Structure> tag = TagKey.create(Registries.STRUCTURE, new ResourceLocation(Verdant.MOD_ID, name));
			// System.out.println("\n\n\nDEBUGGER TK: "+tag.toString()+" \n\n\n");
			return tag;
		}
	}

	public static class Items {

		private static TagKey<Item> tag(String name) {
			return ItemTags.create(new ResourceLocation(Verdant.MOD_ID, name));
		}
	}

	public static class EntityTypes {

		private static TagKey<EntityType<?>> tag(String name) {
			return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(Verdant.MOD_ID, name));
		}
	}

	public static class Biomes {

		private static TagKey<Biome> tag(String name) {
			return TagKey.create(Registries.BIOME, new ResourceLocation(Verdant.MOD_ID, name));
		}
	}
}