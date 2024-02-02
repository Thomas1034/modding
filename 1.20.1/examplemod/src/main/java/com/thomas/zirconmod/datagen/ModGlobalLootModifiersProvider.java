package com.thomas.zirconmod.datagen;

import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.item.ModItems;
import com.thomas.zirconmod.loot.AddSingleItemModifier;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, ZirconMod.MOD_ID);
    }

    @Override
    protected void start() {
        add("pine_cone_from_spruce_leaves", new AddSingleItemModifier(new LootItemCondition[] {
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.SPRUCE_LEAVES).build(),
                LootItemRandomChanceCondition.randomChance(0.50f).build()}, ModItems.PINE_CONE.get()));

        add("citrine_from_jungle_temples", new AddSingleItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/jungle_temple")).build(),
                LootItemRandomChanceCondition.randomChance(0.25f).build() }, ModItems.CITRINE_SHARD.get()));

    }
}