package com.thomas.turbulent.datagen;

import java.util.List;
import java.util.Set;

import com.thomas.turbulent.block.ModBlocks;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLootSubProvider {
	public ModBlockLootTables() {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags());
	}

	@Override
	protected void generate() {
	}

	protected LootTable.Builder createChanceDrops(Block block, Item item, float chance) {
		return createShearsDispatchTable(block,
				this.applyExplosionDecay(block,
						LootItem.lootTableItem(item).when(LootItemRandomChanceCondition.randomChance(chance))
								.apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2))));
	}

	protected LootTable.Builder createOreDrops(Block block, ItemLike item, List<Integer> range) {
		return createSilkTouchDispatchTable(block,
				this.applyExplosionDecay(block, LootItem.lootTableItem(item)
						.apply(SetItemCountFunction.setCount(UniformGenerator.between(range.get(0), range.get(1))))
						.apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
	}

	protected LootTable.Builder createSilkTouchDrop(Block pBlock, Item item) {
		return createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)));
	}

	protected LootTable.Builder createSilkTouchOrShearsOreDrops(Block block, Item item, List<Integer> range) {
		return createSilkTouchOrShearsDispatchTable(block,
				this.applyExplosionDecay(block, LootItem.lootTableItem(item)
						.apply(SetItemCountFunction.setCount(UniformGenerator.between(range.get(0), range.get(1))))
						.apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
	}

	protected LootTable.Builder createSilkTouchOrShearsDrop(Block pBlock, Item item) {
		return createSilkTouchOrShearsDispatchTable(pBlock,
				this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)));
	}

	protected LootTable.Builder createSilkTouchOrShearsDrop(Block pBlock, Item item, List<Integer> range) {
		return createSilkTouchOrShearsOreDrops(pBlock, item, range);
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
	}

	protected void requireSilkTouch(Block base, ItemLike withoutSilk) {
		this.add(base, block -> createSilkTouchDrop(base, withoutSilk.asItem()));
	}

	protected void requireSilkTouchOrShears(Block base, ItemLike withoutSilk) {
		this.add(base, block -> createSilkTouchOrShearsDrop(base, withoutSilk.asItem()));
	}

	protected void requireSilkTouchOrShears(Block base, ItemLike withoutSilk, List<Integer> range) {
		this.add(base, block -> createSilkTouchOrShearsDrop(base, withoutSilk.asItem(), range));
	}

	protected void requireSilkTouch(Block base, ItemLike withoutSilk, List<Integer> range) {
		this.add(base, block -> createOreDrops(base, withoutSilk.asItem(), range));
	}

	protected void oreDrop(Block base, ItemLike drop, List<Integer> range) {
		this.add(base, block -> createOreDrops(base, drop, range));
	}

	protected void fromOtherTable(Block base, ResourceLocation source) {
		LootTable.Builder builder = LootTable.lootTable().withPool(LootPool.lootPool()
				.setRolls(ConstantValue.exactly(1.0F)).add(LootTableReference.lootTableReference(source)));
		this.add(base, builder);
	}

}
