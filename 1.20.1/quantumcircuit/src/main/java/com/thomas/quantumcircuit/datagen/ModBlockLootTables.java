package com.thomas.quantumcircuit.datagen;

import java.util.List;
import java.util.Set;

import com.thomas.quantumcircuit.block.ModBlocks;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLootSubProvider {
	public ModBlockLootTables() {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags());
	}

	@Override
	protected void generate() {

		this.dropSelf(ModBlocks.ZIRCON_BLOCK.get());
		
	}

	protected LootTable.Builder createOreDrops(Block block, Item item, List<Integer> range) {
		return createSilkTouchDispatchTable(block,
				this.applyExplosionDecay(block, LootItem.lootTableItem(item)
						.apply(SetItemCountFunction.setCount(UniformGenerator.between(range.get(0), range.get(1))))
						.apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
	}

	protected LootTable.Builder createSilkTouchDrop(Block pBlock, Item item) {
		return createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)));
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
	}
	
	protected void requireSilkTouch(Block base, ItemLike withoutSilk) {
		this.add(base,
				block -> createSilkTouchDrop(base, withoutSilk.asItem()));
	}
	
	protected void requireSilkTouch(Block base, ItemLike withoutSilk, List<Integer> range) {
		this.add(base,
				block -> createOreDrops(base, withoutSilk.asItem(), range));
	}
	
}
