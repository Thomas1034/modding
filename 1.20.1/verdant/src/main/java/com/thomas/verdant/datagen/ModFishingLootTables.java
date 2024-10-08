package com.thomas.verdant.datagen;

import java.util.function.BiConsumer;

import com.thomas.verdant.Verdant;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class ModFishingLootTables implements LootTableSubProvider {

	public static final ResourceLocation FISH_TRAP_FISHING = new ResourceLocation(Verdant.MOD_ID,
			"gameplay/fish_trap_fishing");

	@Override
	public void generate(BiConsumer<ResourceLocation, Builder> builder) {

//		builder.accept(FISH_TRAP_FISHING,
//				LootTable.lootTable()
//						.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
//								.add(LootTableReference.lootTableReference(BuiltInLootTables.FISHING).setWeight(99)
//										.setQuality(-1))
//								.add(LootTableReference.lootTableReference(BuiltInLootTables.FISHING_TREASURE)
//										.setWeight(1).setQuality(2))));

	}

}
