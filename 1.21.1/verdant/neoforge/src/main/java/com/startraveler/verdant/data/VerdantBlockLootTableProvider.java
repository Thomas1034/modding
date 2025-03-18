package com.startraveler.verdant.data;

import com.startraveler.verdant.block.custom.BombPileBlock;
import com.startraveler.verdant.block.custom.CassavaCropBlock;
import com.startraveler.verdant.block.custom.SpreadingCropBlock;
import com.startraveler.verdant.block.custom.StranglerVineBlock;
import com.startraveler.verdant.registration.RegistryObject;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.registry.ItemRegistry;
import com.startraveler.verdant.registry.WoodSets;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class VerdantBlockLootTableProvider extends BlockLootSubProvider {

    protected final Set<Block> knownBlocks;

    public VerdantBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
        this.knownBlocks = new HashSet<>();
    }

    @Override
    protected void generate() {

        this.knownBlocks.addAll(BlockRegistry.BLOCKS.getEntries()
                .stream()
                .map(RegistryObject::get)
                .collect(Collectors.toSet()));

        // BlockRegistry.VERDANT_HEARTWOOD.addLootTables(this);
        // BlockRegistry.VERDANT.addLootTables(this);

        this.add(BlockRegistry.BLASTING_BLOSSOM.get(), noDrop());
        this.dropOther(BlockRegistry.SMALL_ALOE.get(), ItemRegistry.ALOE_LEAF.get());
        this.dropOther(BlockRegistry.LARGE_ALOE.get(), ItemRegistry.ALOE_PUP.get());
        this.dropOther(BlockRegistry.HUGE_ALOE.get(), ItemRegistry.ALOE_PUP.get());
        this.dropOther(BlockRegistry.ROPE_HOOK.get(), Blocks.TRIPWIRE_HOOK);
        this.dropSelf(BlockRegistry.GRUS.get());
        this.dropSelf(BlockRegistry.STONY_GRUS.get());
        this.dropSelf(BlockRegistry.SCREE.get());
        this.dropSelf(BlockRegistry.FUSED_SCREE.get());
        this.dropSelf(BlockRegistry.FUSED_GRAVEL.get());
        this.dropSelf(BlockRegistry.ROPE_LADDER.get());
        this.dropSelf(BlockRegistry.FISH_TRAP.get());
        this.dropSelf(BlockRegistry.ANTIGORITE.get());
        this.add(BlockRegistry.STRANGLER_VINE.get(), noDrop());
        this.add(BlockRegistry.LEAFY_STRANGLER_VINE.get(), noDrop());
        requireSilkTouch(BlockRegistry.STRANGLER_LEAVES.get(), Items.VINE, List.of(0, 1));
        requireSilkTouch(BlockRegistry.WILTED_STRANGLER_LEAVES.get(), Items.STICK, List.of(0, 1));
        requireSilkTouch(BlockRegistry.POISON_STRANGLER_LEAVES.get(), BlockRegistry.POISON_IVY.get(), List.of(1, 3));
        requireSilkTouch(BlockRegistry.THORNY_STRANGLER_LEAVES.get(), ItemRegistry.THORN.get(), List.of(1, 3));
        this.dropOther(BlockRegistry.STRANGLER_TENDRIL_PLANT.get(), BlockRegistry.STRANGLER_TENDRIL.get());
        this.dropSelf(BlockRegistry.STRANGLER_TENDRIL.get());
        this.dropOther(BlockRegistry.POISON_IVY_PLANT.get(), BlockRegistry.POISON_IVY.get());
        this.dropSelf(BlockRegistry.POISON_IVY.get());

        // Proudly written by ChatGPT 4o
        BiFunction<Block, Item, LootTable.Builder> stranglerVineLoot = (stranglerVine, stranglerItem) -> LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(stranglerItem)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stranglerVine)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(StranglerVineBlock.DOWN, StranglerVineBlock.MAX_AGE)))))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(stranglerItem)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stranglerVine)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(StranglerVineBlock.UP, StranglerVineBlock.MAX_AGE)))))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(stranglerItem)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stranglerVine)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(StranglerVineBlock.NORTH, StranglerVineBlock.MAX_AGE)))))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(stranglerItem)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stranglerVine)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(StranglerVineBlock.EAST, StranglerVineBlock.MAX_AGE)))))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(stranglerItem)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stranglerVine)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(StranglerVineBlock.SOUTH, StranglerVineBlock.MAX_AGE)))))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(stranglerItem)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(stranglerVine)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(StranglerVineBlock.WEST, StranglerVineBlock.MAX_AGE)))));
        this.add(
                BlockRegistry.STRANGLER_VINE.get(),
                stranglerVineLoot.apply(BlockRegistry.STRANGLER_VINE.get(), BlockRegistry.STRANGLER_VINE.get().asItem())
        );
        this.add(
                BlockRegistry.LEAFY_STRANGLER_VINE.get(),
                stranglerVineLoot.apply(
                        BlockRegistry.LEAFY_STRANGLER_VINE.get(),
                        BlockRegistry.LEAFY_STRANGLER_VINE.get().asItem()
                )
        );

        // Proudly written by ChatGPT 4o
        Block rottenWood = BlockRegistry.ROTTEN_WOOD.get();
        LootTable.Builder rottenWoodLoot = LootTable.lootTable().withPool(LootPool.lootPool()
                // Add conditions for silk touch mining
                .when(this.hasSilkTouch()).add(LootItem.lootTableItem(rottenWood))).withPool(LootPool.lootPool()
                // No silk touch: Drops sticks and mushrooms
                .when(this.hasSilkTouch().invert())
                .add(LootItem.lootTableItem(Items.STICK)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                        .setWeight(6))
                .add(LootItem.lootTableItem(Items.BROWN_MUSHROOM)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))
                        .setWeight(3))
                .add(LootItem.lootTableItem(Items.RED_MUSHROOM)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))
                        .setWeight(1))
                .setRolls(UniformGenerator.between(1, 3)));
        this.add(rottenWood, rottenWoodLoot);

        this.dropSelf(BlockRegistry.STINKING_BLOSSOM.get());

        requireSilkTouch(BlockRegistry.THORN_BUSH.get(), ItemRegistry.THORN.get(), List.of(1, 3));
        this.add(BlockRegistry.POTTED_THORN_BUSH.get(), createPotFlowerItemTable(BlockRegistry.THORN_BUSH.get()));
        requireSilkTouch(BlockRegistry.BUSH.get(), Items.STICK, List.of(2, 4));
        this.add(BlockRegistry.POTTED_BUSH.get(), createPotFlowerItemTable(BlockRegistry.BUSH.get()));
        dropSelf(BlockRegistry.WILD_COFFEE.get());
        this.add(BlockRegistry.POTTED_WILD_COFFEE.get(), createPotFlowerItemTable(BlockRegistry.WILD_COFFEE.get()));
        dropOther(BlockRegistry.COFFEE_CROP.get(), ItemRegistry.COFFEE_BERRIES.get());
        this.add(BlockRegistry.POTTED_COFFEE_CROP.get(), createPotFlowerItemTable(BlockRegistry.COFFEE_CROP.get()));
        dropSelf(BlockRegistry.BLEEDING_HEART.get());
        this.add(
                BlockRegistry.POTTED_BLEEDING_HEART.get(),
                createPotFlowerItemTable(BlockRegistry.BLEEDING_HEART.get())
        );
        dropSelf(BlockRegistry.TIGER_LILY.get());
        this.add(BlockRegistry.POTTED_TIGER_LILY.get(), createPotFlowerItemTable(BlockRegistry.TIGER_LILY.get()));

        dropSelf(BlockRegistry.RUE.get());
        this.add(BlockRegistry.POTTED_RUE.get(), createPotFlowerItemTable(BlockRegistry.RUE.get()));

        this.requireSilkTouch(BlockRegistry.CHARRED_FRAME_BLOCK.get(), Items.CHARCOAL, List.of(0, 1));
        this.dropSelf(BlockRegistry.FRAME_BLOCK.get());
        this.dropSelf(BlockRegistry.PAPER_FRAME.get());

        this.dropSelf(BlockRegistry.WOODEN_SPIKES.get());
        this.dropSelf(BlockRegistry.IRON_SPIKES.get());
        this.dropSelf(BlockRegistry.WOODEN_TRAP.get());
        this.dropSelf(BlockRegistry.IRON_TRAP.get());

        this.add(BlockRegistry.UBE_CAKE.get(), noDrop());
        this.add(BlockRegistry.CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.CANDLE));
        this.add(BlockRegistry.WHITE_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.WHITE_CANDLE));
        this.add(BlockRegistry.ORANGE_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.ORANGE_CANDLE));
        this.add(BlockRegistry.MAGENTA_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.MAGENTA_CANDLE));
        this.add(BlockRegistry.LIGHT_BLUE_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.LIGHT_BLUE_CANDLE));
        this.add(BlockRegistry.YELLOW_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.YELLOW_CANDLE));
        this.add(BlockRegistry.LIME_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.LIME_CANDLE));
        this.add(BlockRegistry.PINK_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.PINK_CANDLE));
        this.add(BlockRegistry.GRAY_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.GRAY_CANDLE));
        this.add(BlockRegistry.LIGHT_GRAY_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.LIGHT_GRAY_CANDLE));
        this.add(BlockRegistry.CYAN_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.CYAN_CANDLE));
        this.add(BlockRegistry.PURPLE_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.PURPLE_CANDLE));
        this.add(BlockRegistry.BLUE_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.BLUE_CANDLE));
        this.add(BlockRegistry.BROWN_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.BROWN_CANDLE));
        this.add(BlockRegistry.GREEN_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.GREEN_CANDLE));
        this.add(BlockRegistry.RED_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.RED_CANDLE));
        this.add(BlockRegistry.BLACK_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.BLACK_CANDLE));

        requireSilkTouch(BlockRegistry.PACKED_GRAVEL.get(), Blocks.GRAVEL, List.of(3, 4));
        requireSilkTouch(BlockRegistry.PACKED_SCREE.get(), BlockRegistry.SCREE.get(), List.of(3, 4));

        this.dropSelf(BlockRegistry.DROWNED_HEMLOCK.get());
        this.dropOther(BlockRegistry.DROWNED_HEMLOCK_PLANT.get(), BlockRegistry.DROWNED_HEMLOCK.get());

        this.add(
                BlockRegistry.WILD_CASSAVA.get(),
                this.createChanceDrops(
                        BlockRegistry.WILD_CASSAVA.get(),
                        ItemRegistry.BITTER_CASSAVA_CUTTINGS.get(),
                        0.25f
                )
        );
        this.add(
                BlockRegistry.WILD_UBE.get(),
                this.createChanceDrops(BlockRegistry.WILD_UBE.get(), ItemRegistry.UBE.get(), 0.2f)
        );

        this.dropSelf(BlockRegistry.ROPE.get());
        this.dropSelf(BlockRegistry.VERDANT_CONDUIT.get());

        oreDrop(BlockRegistry.DIRT_COAL_ORE.get(), Items.COAL, List.of(1, 2));
        this.add(BlockRegistry.DIRT_COPPER_ORE.get(), this.createCopperOreDrops(BlockRegistry.DIRT_COPPER_ORE.get()));
        oreDrop(BlockRegistry.DIRT_IRON_ORE.get(), Items.RAW_IRON, List.of(1, 2));
        oreDrop(BlockRegistry.DIRT_GOLD_ORE.get(), Items.RAW_GOLD, List.of(1, 2));
        this.add(BlockRegistry.DIRT_LAPIS_ORE.get(), this.createLapisOreDrops(BlockRegistry.DIRT_LAPIS_ORE.get()));
        this.add(
                BlockRegistry.DIRT_REDSTONE_ORE.get(),
                this.createRedstoneOreDrops(BlockRegistry.DIRT_REDSTONE_ORE.get())
        );
        oreDrop(BlockRegistry.DIRT_EMERALD_ORE.get(), Items.EMERALD, List.of(1, 3));
        oreDrop(BlockRegistry.DIRT_DIAMOND_ORE.get(), Items.DIAMOND, List.of(1, 1));
        oreDrop(BlockRegistry.GRUS_COAL_ORE.get(), Items.COAL, List.of(1, 2));
        this.add(BlockRegistry.GRUS_COPPER_ORE.get(), this.createCopperOreDrops(BlockRegistry.DIRT_COPPER_ORE.get()));
        oreDrop(BlockRegistry.GRUS_IRON_ORE.get(), Items.RAW_IRON, List.of(1, 2));
        oreDrop(BlockRegistry.GRUS_GOLD_ORE.get(), Items.RAW_GOLD, List.of(1, 2));
        this.add(BlockRegistry.GRUS_LAPIS_ORE.get(), this.createLapisOreDrops(BlockRegistry.DIRT_LAPIS_ORE.get()));
        this.add(
                BlockRegistry.GRUS_REDSTONE_ORE.get(),
                this.createRedstoneOreDrops(BlockRegistry.DIRT_REDSTONE_ORE.get())
        );
        oreDrop(BlockRegistry.GRUS_EMERALD_ORE.get(), Items.EMERALD, List.of(1, 3));
        oreDrop(BlockRegistry.GRUS_DIAMOND_ORE.get(), Items.DIAMOND, List.of(1, 1));

        requireSilkTouchDropsOther(BlockRegistry.VERDANT_GRASS_DIRT.get(), Blocks.DIRT);
        requireSilkTouchDropsOther(BlockRegistry.VERDANT_ROOTED_DIRT.get(), Blocks.DIRT);
        requireSilkTouchDropsOther(BlockRegistry.VERDANT_GRASS_MUD.get(), Blocks.MUD);
        requireSilkTouchDropsOther(BlockRegistry.VERDANT_ROOTED_MUD.get(), Blocks.MUD);
        requireSilkTouchDropsOther(BlockRegistry.VERDANT_GRASS_CLAY.get(), Blocks.CLAY);
        requireSilkTouchDropsOther(BlockRegistry.VERDANT_ROOTED_CLAY.get(), Blocks.CLAY);
        requireSilkTouchDropsOther(BlockRegistry.VERDANT_GRASS_GRUS.get(), BlockRegistry.GRUS.get());
        requireSilkTouchDropsOther(BlockRegistry.VERDANT_ROOTED_GRUS.get(), BlockRegistry.GRUS.get());
        this.dropSelf(BlockRegistry.SNAPLEAF.get());

        this.add(BlockRegistry.POTTED_WILD_CASSAVA.get(), createPotFlowerItemTable(BlockRegistry.WILD_CASSAVA.get()));

        LootItemCondition.Builder cassavaCropMaxAgeBuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(
                        BlockRegistry.CASSAVA_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(CassavaCropBlock.AGE, CassavaCropBlock.MAX_AGE));

        LootTable.Builder cassavaLoot = LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ItemRegistry.CASSAVA_CUTTINGS.get())
                                .when(cassavaCropMaxAgeBuilder.invert())))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ItemRegistry.CASSAVA_CUTTINGS.get())
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT)
                                                .getOrThrow(Enchantments.FORTUNE), 0.5714286F, 2
                                ))
                                .when(cassavaCropMaxAgeBuilder)
                                .setWeight(31))
                        .add(LootItem.lootTableItem(ItemRegistry.BITTER_CASSAVA_CUTTINGS.get())
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT)
                                                .getOrThrow(Enchantments.FORTUNE), 0.5714286F, 4
                                ))
                                .when(cassavaCropMaxAgeBuilder)
                                .setWeight(1)));

        this.add(BlockRegistry.CASSAVA_CROP.get(), cassavaLoot);

        LootItemCondition.Builder bitterCassavaCropMaxAgeBuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(
                        BlockRegistry.BITTER_CASSAVA_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(CassavaCropBlock.AGE, CassavaCropBlock.MAX_AGE));

        LootTable.Builder bitterCassavaLoot = LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ItemRegistry.BITTER_CASSAVA_CUTTINGS.get())
                                .when(bitterCassavaCropMaxAgeBuilder.invert())))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ItemRegistry.BITTER_CASSAVA_CUTTINGS.get())
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT)
                                                .getOrThrow(Enchantments.FORTUNE), 0.5714286F, 4
                                ))
                                .when(bitterCassavaCropMaxAgeBuilder)
                                .setWeight(15))
                        .add(LootItem.lootTableItem(ItemRegistry.CASSAVA_CUTTINGS.get())
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT)
                                                .getOrThrow(Enchantments.FORTUNE), 0.5714286F, 1
                                ))
                                .when(bitterCassavaCropMaxAgeBuilder)
                                .setWeight(1)));

        this.add(BlockRegistry.BITTER_CASSAVA_CROP.get(), bitterCassavaLoot);

        this.add(BlockRegistry.POTTED_WILD_UBE.get(), createPotFlowerItemTable(BlockRegistry.WILD_UBE.get()));

        LootItemCondition.Builder ubeCropMaxAgeBuilder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(
                        BlockRegistry.UBE_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(CassavaCropBlock.AGE, SpreadingCropBlock.MAX_AGE));
        LootTable.Builder ubeLoot = LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(ItemRegistry.UBE.get())))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ItemRegistry.UBE.get())
                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(
                                        registries.lookupOrThrow(Registries.ENCHANTMENT)
                                                .getOrThrow(Enchantments.FORTUNE), 0.5714286F, 1
                                )))
                        .setRolls(UniformGenerator.between(1, 2))
                        .when(ubeCropMaxAgeBuilder));

        this.add(BlockRegistry.UBE_CROP.get(), ubeLoot);

        this.requireSilkTouch(BlockRegistry.IMBUED_HEARTWOOD_LOG.get(), WoodSets.HEARTWOOD.getLog().get());
        this.dropSelf(BlockRegistry.CASSAVA_ROOTED_DIRT.get());
        this.dropSelf(BlockRegistry.BITTER_CASSAVA_ROOTED_DIRT.get());
        this.dropSelf(BlockRegistry.TOXIC_DIRT.get());
        this.requireSilkTouchOrShears(BlockRegistry.DEAD_GRASS.get(), Items.WHEAT_SEEDS, List.of(0, 1));
        this.dropSelf(BlockRegistry.POISON_IVY_BLOCK.get());
        this.dropSelf(BlockRegistry.TOXIC_ASH_BLOCK.get());
        this.dropSelf(BlockRegistry.PUTRID_FERTILIZER.get());

        // Proudly generated by ChatGPT
        Block blastingBunch = BlockRegistry.BLASTING_BUNCH.get();
        ItemLike blastingBunchLoot = ItemRegistry.STABLE_BLASTING_BLOOM.get();

        LootTable.Builder blastingBunchTable = LootTable.lootTable().withPool(this.applyExplosionCondition(
                blastingBunchLoot,
                LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(blastingBunch)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(TntBlock.UNSTABLE, false)))
                        .setRolls(ConstantValue.exactly(1.0F))

                        .add(LootItem.lootTableItem(blastingBunchLoot)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(blastingBunch)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(BombPileBlock.BOMBS, 1)))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))

                        .add(LootItem.lootTableItem(blastingBunchLoot)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(blastingBunch)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(BombPileBlock.BOMBS, 2)))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))))

                        .add(LootItem.lootTableItem(blastingBunchLoot)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(blastingBunch)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(BombPileBlock.BOMBS, 3)))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3))))

                        .add(LootItem.lootTableItem(blastingBunchLoot)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(blastingBunch)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(BombPileBlock.BOMBS, 4)))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))

                        .add(LootItem.lootTableItem(blastingBunchLoot)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(blastingBunch)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(BombPileBlock.BOMBS, 5)))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(5))))

                        .add(LootItem.lootTableItem(blastingBunchLoot)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(blastingBunch)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(BombPileBlock.BOMBS, 6)))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(6))))

                        .add(LootItem.lootTableItem(blastingBunchLoot)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(blastingBunch)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(BombPileBlock.BOMBS, 7)))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(7))))

                        .add(LootItem.lootTableItem(blastingBunchLoot)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(blastingBunch)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(BombPileBlock.BOMBS, 8)))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(8))))

        ));
        this.add(blastingBunch, blastingBunchTable);


    }

    @Override
    @NotNull
    protected Iterable<Block> getKnownBlocks() {
        return this.knownBlocks;
    }


    protected LootTable.Builder createChanceDrops(Block block, Item item, float chance) {
        return createShearsDispatchTable(
                block, this.applyExplosionDecay(
                        block,
                        LootItem.lootTableItem(item)
                                .when(LootItemRandomChanceCondition.randomChance(chance))
                                .apply(ApplyBonusCount.addUniformBonusCount(
                                        registries.lookup(Registries.ENCHANTMENT)
                                                .orElseThrow()
                                                .getOrThrow(Enchantments.FORTUNE), 2
                                ))
                )
        );
    }

    protected LootTable.Builder createOreDrops(Block block, ItemLike item, List<Integer> range) {
        return createSilkTouchDispatchTable(
                block, this.applyExplosionDecay(
                        block,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(
                                        range.get(0),
                                        range.get(1)
                                )))
                                .apply(ApplyBonusCount.addOreBonusCount(registries.lookup(Registries.ENCHANTMENT)
                                        .orElseThrow()
                                        .getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    protected LootTable.Builder createSilkTouchDrop(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)));
    }

    protected LootTable.Builder createSilkTouchOrShearsOreDrops(Block block, Item item, List<Integer> range) {
        return createSilkTouchOrShearsDispatchTable(
                block, this.applyExplosionDecay(
                        block,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(
                                        range.get(0),
                                        range.get(1)
                                )))
                                .apply(ApplyBonusCount.addOreBonusCount(registries.lookup(Registries.ENCHANTMENT)
                                        .orElseThrow()
                                        .getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    protected LootTable.Builder createSilkTouchOrShearsDrop(Block pBlock, Item item) {
        return createSilkTouchOrShearsDispatchTable(
                pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item))
        );
    }

    protected LootTable.Builder createSilkTouchOrShearsDrop(Block pBlock, Item item, List<Integer> range) {
        return createSilkTouchOrShearsOreDrops(pBlock, item, range);
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

    protected void requireSilkTouchDropsOther(Block base, Block source) {
        ResourceLocation sourceLoc = BuiltInRegistries.BLOCK.getKey(source).withPrefix("blocks/");
        this.add(base, block -> this.createSilkTouchOrOtherDrop(block, sourceLoc));
    }

    protected void oreDrop(Block base, ItemLike drop, List<Integer> range) {
        this.add(base, block -> createOreDrops(base, drop, range));
    }

    protected LootTable.Builder createSilkTouchOrOtherDrop(Block block, ResourceLocation source) {
        return createSilkTouchDispatchTable(
                block,
                this.applyExplosionDecay(
                        block,
                        NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, source))
                )
        );
    }

}

