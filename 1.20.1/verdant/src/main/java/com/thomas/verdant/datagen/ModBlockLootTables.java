package com.thomas.verdant.datagen;

import java.util.List;
import java.util.Set;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.item.ModItems;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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

		requireSilkTouch(ModBlocks.VERDANT_LEAVES.get(), Items.STICK, List.of(0, 2));
		requireSilkTouch(ModBlocks.ROTTEN_WOOD.get(), Blocks.AIR);
		requireSilkTouch(ModBlocks.VERDANT_VINE.get(), Items.STICK, List.of(1, 3));
		requireSilkTouch(ModBlocks.LEAFY_VERDANT_VINE.get(), Items.STICK, List.of(1, 3));
		requireSilkTouch(ModBlocks.VERDANT_GRASS_BLOCK.get(), Blocks.DIRT);
		requireSilkTouch(ModBlocks.VERDANT_ROOTED_DIRT.get(), Blocks.DIRT);
		requireSilkTouch(ModBlocks.VERDANT_MUD_GRASS_BLOCK.get(), Blocks.MUD);
		requireSilkTouch(ModBlocks.VERDANT_ROOTED_MUD.get(), Blocks.MUD);
		requireSilkTouch(ModBlocks.VERDANT_CLAY_GRASS_BLOCK.get(), Blocks.CLAY);
		requireSilkTouch(ModBlocks.VERDANT_ROOTED_CLAY.get(), Blocks.CLAY);
		this.dropOther(ModBlocks.VERDANT_TENDRIL_PLANT.get(), ModBlocks.VERDANT_TENDRIL.get());
		this.dropSelf(ModBlocks.VERDANT_TENDRIL.get());
		this.dropSelf(ModBlocks.VERDANT_BUTTON.get());
		this.dropSelf(ModBlocks.VERDANT_FENCE.get());
		this.dropSelf(ModBlocks.VERDANT_FENCE_GATE.get());
		this.dropSelf(ModBlocks.VERDANT_PLANKS.get());
		this.dropSelf(ModBlocks.VERDANT_LOG.get());
		this.dropSelf(ModBlocks.VERDANT_WOOD.get());
		this.dropSelf(ModBlocks.STRIPPED_VERDANT_LOG.get());
		this.dropSelf(ModBlocks.STRIPPED_VERDANT_WOOD.get());
		this.dropSelf(ModBlocks.VERDANT_PRESSURE_PLATE.get());
		this.dropSelf(ModBlocks.VERDANT_STAIRS.get());
		this.dropSelf(ModBlocks.VERDANT_TRAPDOOR.get());
		this.add(ModBlocks.VERDANT_SLAB.get(), block -> createSlabItemTable(ModBlocks.VERDANT_SLAB.get()));
		this.add(ModBlocks.VERDANT_DOOR.get(), block -> createDoorTable(ModBlocks.VERDANT_DOOR.get()));
		this.add(ModBlocks.VERDANT_SIGN.get(), block -> createSingleItemTable(ModItems.VERDANT_SIGN.get()));
		this.add(ModBlocks.VERDANT_WALL_SIGN.get(), block -> createSingleItemTable(ModItems.VERDANT_SIGN.get()));
		this.add(ModBlocks.VERDANT_HANGING_SIGN.get(),
				block -> createSingleItemTable(ModItems.VERDANT_HANGING_SIGN.get()));
		this.add(ModBlocks.VERDANT_WALL_HANGING_SIGN.get(),
				block -> createSingleItemTable(ModItems.VERDANT_HANGING_SIGN.get()));
		this.dropSelf(ModBlocks.VERDANT_HEARTWOOD_BUTTON.get());
		this.dropSelf(ModBlocks.VERDANT_HEARTWOOD_FENCE.get());
		this.dropSelf(ModBlocks.VERDANT_HEARTWOOD_FENCE_GATE.get());
		this.dropSelf(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get());
		this.dropSelf(ModBlocks.VERDANT_HEARTWOOD_LOG.get());
		this.dropSelf(ModBlocks.VERDANT_HEARTWOOD_WOOD.get());
		this.dropSelf(ModBlocks.STRIPPED_VERDANT_HEARTWOOD_LOG.get());
		this.dropSelf(ModBlocks.STRIPPED_VERDANT_HEARTWOOD_WOOD.get());
		this.dropSelf(ModBlocks.VERDANT_HEARTWOOD_PRESSURE_PLATE.get());
		this.dropSelf(ModBlocks.VERDANT_HEARTWOOD_STAIRS.get());
		this.dropSelf(ModBlocks.VERDANT_HEARTWOOD_TRAPDOOR.get());
		this.add(ModBlocks.VERDANT_HEARTWOOD_SLAB.get(), block -> createSlabItemTable(ModBlocks.VERDANT_HEARTWOOD_SLAB.get()));
		this.add(ModBlocks.VERDANT_HEARTWOOD_DOOR.get(), block -> createDoorTable(ModBlocks.VERDANT_HEARTWOOD_DOOR.get()));
		this.add(ModBlocks.VERDANT_HEARTWOOD_SIGN.get(), block -> createSingleItemTable(ModItems.VERDANT_HEARTWOOD_SIGN.get()));
		this.add(ModBlocks.VERDANT_HEARTWOOD_WALL_SIGN.get(), block -> createSingleItemTable(ModItems.VERDANT_HEARTWOOD_SIGN.get()));
		this.add(ModBlocks.VERDANT_HEARTWOOD_HANGING_SIGN.get(),
				block -> createSingleItemTable(ModItems.VERDANT_HEARTWOOD_HANGING_SIGN.get()));
		this.add(ModBlocks.VERDANT_HEARTWOOD_WALL_HANGING_SIGN.get(),
				block -> createSingleItemTable(ModItems.VERDANT_HEARTWOOD_HANGING_SIGN.get()));
		this.dropSelf(ModBlocks.BLEEDING_HEART.get());
		this.add(ModBlocks.POTTED_BLEEDING_HEART.get(), createPotFlowerItemTable(ModBlocks.BLEEDING_HEART.get()));

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
		this.add(base, block -> createSilkTouchDrop(base, withoutSilk.asItem()));
	}

	protected void requireSilkTouch(Block base, ItemLike withoutSilk, List<Integer> range) {
		this.add(base, block -> createOreDrops(base, withoutSilk.asItem(), range));
	}

}
