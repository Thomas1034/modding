package com.thomas.verdant.datagen;

import java.util.List;
import java.util.Set;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.item.ModItems;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLootSubProvider {
	public ModBlockLootTables() {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags());
	}

	@Override
	protected void generate() {

		this.dropSelf(ModBlocks.FRAME_BLOCK.get());
		this.dropSelf(ModBlocks.THORN_SPIKES.get());
		this.dropSelf(ModBlocks.ROPE.get());
		this.dropSelf(ModBlocks.POISON_IVY_BLOCK.get());
		this.dropSelf(ModBlocks.TOXIC_ASH_BLOCK.get());

		requireSilkTouch(ModBlocks.VERDANT_CONDUIT.get(), Blocks.AIR);

		oreDrop(ModBlocks.DIRT_COAL_ORE.get(), Items.COAL, List.of(1, 1));
		oreDrop(ModBlocks.DIRT_COPPER_ORE.get(), Items.RAW_COPPER, List.of(2, 5));
		oreDrop(ModBlocks.DIRT_IRON_ORE.get(), Items.RAW_IRON, List.of(1, 1));
		oreDrop(ModBlocks.DIRT_GOLD_ORE.get(), Items.RAW_GOLD, List.of(1, 1));
		oreDrop(ModBlocks.DIRT_LAPIS_ORE.get(), Items.LAPIS_LAZULI, List.of(4, 9));
		oreDrop(ModBlocks.DIRT_REDSTONE_ORE.get(), Items.REDSTONE, List.of(4, 5));
		oreDrop(ModBlocks.DIRT_EMERALD_ORE.get(), Items.EMERALD, List.of(1, 1));
		oreDrop(ModBlocks.DIRT_DIAMOND_ORE.get(), Items.DIAMOND, List.of(1, 1));

		requireSilkTouch(ModBlocks.THORN_BUSH.get(), ModItems.THORN.get(), List.of(0, 1));
		this.add(ModBlocks.POTTED_THORN_BUSH.get(), createPotFlowerItemTable(ModBlocks.THORN_BUSH.get()));
		
		requireSilkTouchOrShears(ModBlocks.WILTED_VERDANT_LEAVES.get(), Items.STICK, List.of(0, 1));
		requireSilkTouchOrShears(ModBlocks.VERDANT_LEAVES.get(), Items.STICK, List.of(0, 1));
		requireSilkTouchOrShears(ModBlocks.THORNY_VERDANT_LEAVES.get(), ModItems.THORN.get(), List.of(0, 2));
		requireSilkTouchOrShears(ModBlocks.POISON_IVY_VERDANT_LEAVES.get(), ModBlocks.POISON_IVY.get(), List.of(0, 2));

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
		this.dropOther(ModBlocks.POISON_IVY_PLANT.get(), ModBlocks.POISON_IVY.get());
		this.dropSelf(ModBlocks.POISON_IVY.get());
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
		this.add(ModBlocks.VERDANT_HEARTWOOD_SLAB.get(),
				block -> createSlabItemTable(ModBlocks.VERDANT_HEARTWOOD_SLAB.get()));
		this.add(ModBlocks.VERDANT_HEARTWOOD_DOOR.get(),
				block -> createDoorTable(ModBlocks.VERDANT_HEARTWOOD_DOOR.get()));
		this.add(ModBlocks.VERDANT_HEARTWOOD_SIGN.get(),
				block -> createSingleItemTable(ModItems.VERDANT_HEARTWOOD_SIGN.get()));
		this.add(ModBlocks.VERDANT_HEARTWOOD_WALL_SIGN.get(),
				block -> createSingleItemTable(ModItems.VERDANT_HEARTWOOD_SIGN.get()));
		this.add(ModBlocks.VERDANT_HEARTWOOD_HANGING_SIGN.get(),
				block -> createSingleItemTable(ModItems.VERDANT_HEARTWOOD_HANGING_SIGN.get()));
		this.add(ModBlocks.VERDANT_HEARTWOOD_WALL_HANGING_SIGN.get(),
				block -> createSingleItemTable(ModItems.VERDANT_HEARTWOOD_HANGING_SIGN.get()));
		this.dropSelf(ModBlocks.BLEEDING_HEART.get());
		this.add(ModBlocks.POTTED_BLEEDING_HEART.get(), createPotFlowerItemTable(ModBlocks.BLEEDING_HEART.get()));
		this.dropSelf(ModBlocks.STINKING_BLOSSOM.get());
		this.dropSelf(ModBlocks.WILD_COFFEE.get());
		this.add(ModBlocks.POTTED_WILD_COFFEE.get(), createPotFlowerItemTable(ModBlocks.WILD_COFFEE.get()));
		this.dropOther(ModBlocks.COFFEE_CROP.get(), ModItems.COFFEE_BERRIES.get());
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
