package com.thomas.verdant.datagen;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.datagen.lootmodifiers.AddTableModifier;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
	public ModGlobalLootModifiersProvider(PackOutput output) {
		super(output, Verdant.MOD_ID);
	}

	@Override
	protected void start() {

		this.add("fish_trap_add_treasure", new AddTableModifier(
				new LootItemCondition[] { new LootTableIdCondition.Builder(BuiltInLootTables.FISHING).build(),
						LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.FISH_TRAP_BLOCK.get())
								.build(),
						LootItemRandomChanceCondition.randomChance(0.01f).build() },
				BuiltInLootTables.FISHING_TREASURE));

	}
}