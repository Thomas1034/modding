package com.thomas.verdant.datagen;

import java.util.List;
import java.util.Set;

import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.block.custom.CassavaCropBlock;
import com.thomas.verdant.item.ModItems;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
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
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
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

		// For the wood set
		ModBlocks.VERDANT_HEARTWOOD.addLootTables(this);
		ModBlocks.VERDANT.addLootTables(this);

		this.add(ModBlocks.WATER_HEMLOCK.get(), createDoublePlantShearsDrop(ModBlocks.WATER_HEMLOCK.get()));

		this.add(ModBlocks.WILD_CASSAVA.get(),
				this.createChanceDrops(ModBlocks.WILD_CASSAVA.get(), ModItems.BITTER_CASSAVA_CUTTINGS.get(), 0.25f));

		this.dropSelf(ModBlocks.FISH_TRAP_BLOCK.get());
		this.dropSelf(ModBlocks.ROPE_LADDER.get());
		this.dropSelf(ModBlocks.BITTER_CASSAVA_ROOTED_DIRT.get());
		this.dropSelf(ModBlocks.CASSAVA_ROOTED_DIRT.get());
		this.dropSelf(ModBlocks.FRAME_BLOCK.get());
		this.dropSelf(ModBlocks.THORN_TRAP.get());
		this.dropSelf(ModBlocks.IRON_TRAP.get());
		this.dropSelf(ModBlocks.THORN_SPIKES.get());
		this.dropSelf(ModBlocks.IRON_SPIKES.get());
		this.dropSelf(ModBlocks.ROPE.get());
		this.dropSelf(ModBlocks.POISON_IVY_BLOCK.get());
		this.dropSelf(ModBlocks.TOXIC_ASH_BLOCK.get());
		requireSilkTouch(ModBlocks.VERDANT_CONDUIT.get(), Blocks.AIR);

		oreDrop(ModBlocks.DIRT_COAL_ORE.get(), Items.COAL, List.of(1, 1));
		this.add(ModBlocks.DIRT_COPPER_ORE.get(), this.createCopperOreDrops(ModBlocks.DIRT_COPPER_ORE.get()));
		oreDrop(ModBlocks.DIRT_IRON_ORE.get(), Items.RAW_IRON, List.of(1, 1));
		oreDrop(ModBlocks.DIRT_GOLD_ORE.get(), Items.RAW_GOLD, List.of(1, 1));
		this.add(ModBlocks.DIRT_LAPIS_ORE.get(), this.createLapisOreDrops(ModBlocks.DIRT_LAPIS_ORE.get()));
		this.add(ModBlocks.DIRT_REDSTONE_ORE.get(), this.createRedstoneOreDrops(ModBlocks.DIRT_REDSTONE_ORE.get()));
		oreDrop(ModBlocks.DIRT_EMERALD_ORE.get(), Items.EMERALD, List.of(1, 1));
		oreDrop(ModBlocks.DIRT_DIAMOND_ORE.get(), Items.DIAMOND, List.of(1, 1));

		requireSilkTouch(ModBlocks.THORN_BUSH.get(), ModItems.THORN.get(), List.of(0, 1));
		this.add(ModBlocks.POTTED_THORN_BUSH.get(), createPotFlowerItemTable(ModBlocks.THORN_BUSH.get()));
		requireSilkTouch(ModBlocks.BUSH.get(), Items.STICK, List.of(0, 1));
		this.add(ModBlocks.POTTED_BUSH.get(), createPotFlowerItemTable(ModBlocks.BUSH.get()));

		requireSilkTouchOrShears(ModBlocks.WILTED_VERDANT_LEAVES.get(), Items.STICK, List.of(0, 1));
		requireSilkTouchOrShears(ModBlocks.VERDANT_LEAVES.get(), Items.STICK, List.of(0, 1));
		requireSilkTouchOrShears(ModBlocks.THORNY_VERDANT_LEAVES.get(), ModItems.THORN.get(), List.of(0, 2));
		requireSilkTouchOrShears(ModBlocks.POISON_IVY_VERDANT_LEAVES.get(), ModBlocks.POISON_IVY.get(), List.of(0, 2));

		requireSilkTouch(ModBlocks.ROTTEN_WOOD.get(), Blocks.AIR);
		requireSilkTouch(ModBlocks.CHARRED_FRAME_BLOCK.get(), Blocks.AIR);

		// TODO
		this.add(ModBlocks.VERDANT_VINE.get(),
				this.createMultifaceBlockDrops(ModBlocks.VERDANT_VINE.get(), HAS_SILK_TOUCH));
		this.add(ModBlocks.LEAFY_VERDANT_VINE.get(),
				this.createMultifaceBlockDrops(ModBlocks.LEAFY_VERDANT_VINE.get(), HAS_SILK_TOUCH));

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

		this.dropSelf(ModBlocks.BLEEDING_HEART.get());
		this.add(ModBlocks.POTTED_BLEEDING_HEART.get(), createPotFlowerItemTable(ModBlocks.BLEEDING_HEART.get()));
		this.dropSelf(ModBlocks.STINKING_BLOSSOM.get());
		this.dropSelf(ModBlocks.WILD_COFFEE.get());
		this.add(ModBlocks.POTTED_WILD_COFFEE.get(), createPotFlowerItemTable(ModBlocks.WILD_COFFEE.get()));
		this.dropOther(ModBlocks.COFFEE_CROP.get(), ModItems.COFFEE_BERRIES.get());

		LootItemCondition.Builder cassavaCropMaxAgeBuilder = LootItemBlockStatePropertyCondition
				.hasBlockStateProperties(ModBlocks.CASSAVA_CROP.get()).setProperties(StatePropertiesPredicate.Builder
						.properties().hasProperty(CassavaCropBlock.AGE, CassavaCropBlock.MAX_AGE));

		LootTable.Builder cassavaLoot = LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.add(LootItem.lootTableItem(ModItems.CASSAVA_CUTTINGS.get())
								.when(cassavaCropMaxAgeBuilder.invert())))
				.withPool(LootPool.lootPool()
						.add(LootItem.lootTableItem(ModItems.CASSAVA_CUTTINGS.get())
								.apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE,
										0.5714286F, 2))
								.when(cassavaCropMaxAgeBuilder).setWeight(31))
						.add(LootItem
								.lootTableItem(ModItems.BITTER_CASSAVA_CUTTINGS.get()).apply(ApplyBonusCount
										.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 4))
								.when(cassavaCropMaxAgeBuilder).setWeight(1)));

		this.add(ModBlocks.CASSAVA_CROP.get(), cassavaLoot);

		LootItemCondition.Builder bitterCassavaCropMaxAgeBuilder = LootItemBlockStatePropertyCondition
				.hasBlockStateProperties(ModBlocks.BITTER_CASSAVA_CROP.get())
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CassavaCropBlock.AGE,
						CassavaCropBlock.MAX_AGE));

		LootTable.Builder bitterCassavaLoot = LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.add(LootItem.lootTableItem(ModItems.BITTER_CASSAVA_CUTTINGS.get())
								.when(bitterCassavaCropMaxAgeBuilder.invert())))
				.withPool(LootPool.lootPool()
						.add(LootItem.lootTableItem(ModItems.BITTER_CASSAVA_CUTTINGS.get())
								.apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE,
										0.5714286F, 4))
								.when(bitterCassavaCropMaxAgeBuilder).setWeight(15))
						.add(LootItem
								.lootTableItem(ModItems.CASSAVA_CUTTINGS.get()).apply(ApplyBonusCount
										.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 1))
								.when(bitterCassavaCropMaxAgeBuilder).setWeight(1)));

		this.add(ModBlocks.BITTER_CASSAVA_CROP.get(), bitterCassavaLoot);

		LootTable.Builder imbuedLogLoot = LootTable.lootTable()
				.withPool(LootPool.lootPool().add(LootItem.lootTableItem(ModItems.HEART_FRAGMENT.get())))
				.withPool(LootPool.lootPool().add(LootItem.lootTableItem(ModBlocks.VERDANT_HEARTWOOD_LOG.get())));

		this.add(ModBlocks.IMBUED_VERDANT_HEARTWOOD_LOG.get(), imbuedLogLoot);
	}

//	// Stop requiring all blocks to have loot tables.
//	// The equivalent of banging on the blinking warning light to get it to stop.
//	@Override
//	public void generate(BiConsumer<ResourceLocation, LootTable.Builder> p_249322_) {
//		this.generate();
//		Set<ResourceLocation> set = new HashSet<>();
//
//		for (Block block : getKnownBlocks()) {
//			if (block.isEnabled(this.enabledFeatures)) {
//				ResourceLocation resourcelocation = block.getLootTable();
//				if (resourcelocation != BuiltInLootTables.EMPTY && set.add(resourcelocation)) {
//					LootTable.Builder builder = this.map.remove(resourcelocation);
//					// if (builder == null) {
//					// throw new IllegalStateException(String.format(Locale.ROOT, "Missing loottable
//					// '%s' for '%s'", resourcelocation, BuiltInRegistries.BLOCK.getKey(block)));
//					// }
//					if (builder != null) {
//						p_249322_.accept(resourcelocation, builder);
//					}
//				}
//			}
//		}
//	}

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
