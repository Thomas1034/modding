package com.thomas.verdant.data;

import com.thomas.verdant.block.custom.StranglerVineBlock;
import com.thomas.verdant.registration.RegistryObject;
import com.thomas.verdant.registry.BlockRegistry;
import com.thomas.verdant.registry.ItemRegistry;
import com.thomas.verdant.registry.WoodSets;
import com.thomas.verdant.woodset.WoodSet;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
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
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
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

        // For the wood set
        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            generateFor(woodSet);
        }
        // ModBlocks.VERDANT_HEARTWOOD.addLootTables(this);
        // ModBlocks.VERDANT.addLootTables(this);

        this.dropOther(BlockRegistry.ROPE_HOOK.get(), Blocks.TRIPWIRE_HOOK);
        this.dropSelf(BlockRegistry.FISH_TRAP_BLOCK.get());
        this.dropSelf(BlockRegistry.ANTIGORITE.get());
        this.add(BlockRegistry.TEST_BLOCK.get(), noDrop());
        this.add(BlockRegistry.TEST_LOG.get(), noDrop());
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


        // this.add(ModBlocks.UBE_CAKE.get(), noDrop());
        // this.add(ModBlocks.CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.CANDLE));
        // this.add(ModBlocks.WHITE_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.WHITE_CANDLE));
        // this.add(ModBlocks.ORANGE_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.ORANGE_CANDLE));
        // this.add(ModBlocks.MAGENTA_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.MAGENTA_CANDLE));
        // this.add(ModBlocks.LIGHT_BLUE_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.LIGHT_BLUE_CANDLE));
        // this.add(ModBlocks.YELLOW_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.YELLOW_CANDLE));
        // this.add(ModBlocks.LIME_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.LIME_CANDLE));
        // this.add(ModBlocks.PINK_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.PINK_CANDLE));
        // this.add(ModBlocks.GRAY_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.GRAY_CANDLE));
        // this.add(ModBlocks.LIGHT_GRAY_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.LIGHT_GRAY_CANDLE));
        // this.add(ModBlocks.CYAN_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.CYAN_CANDLE));
        // this.add(ModBlocks.PURPLE_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.PURPLE_CANDLE));
        // this.add(ModBlocks.BLUE_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.BLUE_CANDLE));
        // this.add(ModBlocks.BROWN_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.BROWN_CANDLE));
        // this.add(ModBlocks.GREEN_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.GREEN_CANDLE));
        // this.add(ModBlocks.RED_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.RED_CANDLE));
        // this.add(ModBlocks.BLACK_CANDLE_UBE_CAKE.get(), createCandleCakeDrops(Blocks.BLACK_CANDLE));

        requireSilkTouch(BlockRegistry.PACKED_GRAVEL.get(), Blocks.GRAVEL, List.of(1, 3));

        // this.add(ModBlocks.WATER_HEMLOCK.get(), createDoublePlantShearsDrop(ModBlocks.WATER_HEMLOCK.get()));

        // this.add(ModBlocks.WILD_CASSAVA.get(),
        //         this.createChanceDrops(ModBlocks.WILD_CASSAVA.get(), ModItems.BITTER_CASSAVA_CUTTINGS.get(), 0.25f));
        // this.add(ModBlocks.WILD_UBE.get(),
        //         this.createChanceDrops(ModBlocks.WILD_UBE.get(), ModItems.UBE.get(), 0.2f));

        //  this.dropSelf(ModBlocks.FISH_TRAP_BLOCK.get());
        // this.dropSelf(ModBlocks.ROPE_LADDER.get());
        // this.dropSelf(ModBlocks.BITTER_CASSAVA_ROOTED_DIRT.get());
        // this.dropSelf(ModBlocks.CASSAVA_ROOTED_DIRT.get());
        // this.dropSelf(ModBlocks.FRAME_BLOCK.get());
        //  this.dropSelf(ModBlocks.THORN_TRAP.get());
        // this.dropSelf(ModBlocks.IRON_TRAP.get());
        // this.dropSelf(ModBlocks.THORN_SPIKES.get());
        // this.dropSelf(ModBlocks.IRON_SPIKES.get());
        this.dropSelf(BlockRegistry.ROPE.get());
        // this.dropSelf(ModBlocks.POISON_IVY_BLOCK.get());
        // this.dropSelf(ModBlocks.TOXIC_ASH_BLOCK.get());
        // requireSilkTouch(ModBlocks.VERDANT_CONDUIT.get(), Blocks.AIR);

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

        // requireSilkTouch(ModBlocks.THORN_BUSH.get(), ModItems.THORN.get(), List.of(0, 1));
        // this.add(ModBlocks.POTTED_THORN_BUSH.get(), createPotFlowerItemTable(ModBlocks.THORN_BUSH.get()));
        // requireSilkTouch(ModBlocks.BUSH.get(), Items.STICK, List.of(0, 1));
        // this.add(ModBlocks.POTTED_BUSH.get(), createPotFlowerItemTable(ModBlocks.BUSH.get()));

        // requireSilkTouchOrShears(ModBlocks.WILTED_VERDANT_LEAVES.get(), Items.STICK, List.of(0, 1));
        // requireSilkTouchOrShears(ModBlocks.VERDANT_LEAVES.get(), Items.STICK, List.of(0, 1));
        // requireSilkTouchOrShears(ModBlocks.THORNY_VERDANT_LEAVES.get(), ModItems.THORN.get(), List.of(0, 2));
        // requireSilkTouchOrShears(ModBlocks.POISON_IVY_VERDANT_LEAVES.get(), ModBlocks.POISON_IVY.get(), List.of(0, 2));

        // requireSilkTouch(ModBlocks.ROTTEN_WOOD.get(), Blocks.AIR);
        // requireSilkTouch(ModBlocks.CHARRED_FRAME_BLOCK.get(), Blocks.AIR);

        // TODO
        // this.add(ModBlocks.VERDANT_VINE.get(),
        //         this.createMultifaceBlockDrops(ModBlocks.VERDANT_VINE.get(), HAS_SILK_TOUCH));
        // this.add(ModBlocks.LEAFY_VERDANT_VINE.get(),
        //         this.createMultifaceBlockDrops(ModBlocks.LEAFY_VERDANT_VINE.get(), HAS_SILK_TOUCH));

        requireSilkTouchDropsOther(BlockRegistry.VERDANT_GRASS_DIRT.get(), Blocks.DIRT);
        requireSilkTouchDropsOther(BlockRegistry.VERDANT_ROOTED_DIRT.get(), Blocks.DIRT);
        requireSilkTouchDropsOther(BlockRegistry.VERDANT_GRASS_MUD.get(), Blocks.MUD);
        requireSilkTouchDropsOther(BlockRegistry.VERDANT_ROOTED_MUD.get(), Blocks.MUD);
        requireSilkTouchDropsOther(BlockRegistry.VERDANT_GRASS_CLAY.get(), Blocks.CLAY);
        requireSilkTouchDropsOther(BlockRegistry.VERDANT_ROOTED_CLAY.get(), Blocks.CLAY);

        // this.dropOther(ModBlocks.VERDANT_TENDRIL_PLANT.get(), ModBlocks.VERDANT_TENDRIL.get());
        // this.dropSelf(ModBlocks.VERDANT_TENDRIL.get());
        // this.dropOther(ModBlocks.POISON_IVY_PLANT.get(), ModBlocks.POISON_IVY.get());
        // this.dropSelf(ModBlocks.POISON_IVY.get());

        // this.dropSelf(ModBlocks.BLEEDING_HEART.get());
        // this.add(ModBlocks.POTTED_BLEEDING_HEART.get(), createPotFlowerItemTable(ModBlocks.BLEEDING_HEART.get()));
        // this.dropSelf(ModBlocks.STINKING_BLOSSOM.get());
        // this.dropSelf(ModBlocks.WILD_COFFEE.get());
        // this.add(ModBlocks.POTTED_WILD_COFFEE.get(), createPotFlowerItemTable(ModBlocks.WILD_COFFEE.get()));
        // this.dropOther(ModBlocks.COFFEE_CROP.get(), ModItems.COFFEE_BERRIES.get());
        // this.add(ModBlocks.POTTED_WILD_CASSAVA.get(), createPotFlowerItemTable(ModBlocks.WILD_CASSAVA.get()));
        // this.add(ModBlocks.POTTED_WILD_UBE.get(), createPotFlowerItemTable(ModBlocks.WILD_UBE.get()));

        // LootItemCondition.Builder cassavaCropMaxAgeBuilder = LootItemBlockStatePropertyCondition
        //         .hasBlockStateProperties(ModBlocks.CASSAVA_CROP.get()).setProperties(StatePropertiesPredicate.Builder
        //                 .properties().hasProperty(CassavaCropBlock.AGE, CassavaCropBlock.MAX_AGE));

        // LootTable.Builder cassavaLoot = LootTable.lootTable()
        //         .withPool(LootPool.lootPool()
        //                 .add(LootItem.lootTableItem(ModItems.CASSAVA_CUTTINGS.get())
        //                         .when(cassavaCropMaxAgeBuilder.invert())))
        //         .withPool(LootPool.lootPool()
        //                 .add(LootItem.lootTableItem(ModItems.CASSAVA_CUTTINGS.get())
        //                         .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE,
        //                                0.5714286F, 2))
        //                         .when(cassavaCropMaxAgeBuilder).setWeight(31))
        //                 .add(LootItem
        //                         .lootTableItem(ModItems.BITTER_CASSAVA_CUTTINGS.get()).apply(ApplyBonusCount
        //                                 .addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 4))
        //                         .when(cassavaCropMaxAgeBuilder).setWeight(1)));

        // this.add(ModBlocks.CASSAVA_CROP.get(), cassavaLoot);

        // LootItemCondition.Builder bitterCassavaCropMaxAgeBuilder = LootItemBlockStatePropertyCondition
        //         .hasBlockStateProperties(ModBlocks.BITTER_CASSAVA_CROP.get())
        //         .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CassavaCropBlock.AGE,
        //                 CassavaCropBlock.MAX_AGE));

        // LootTable.Builder bitterCassavaLoot = LootTable.lootTable()
        //         .withPool(LootPool.lootPool()
        //                 .add(LootItem.lootTableItem(ModItems.BITTER_CASSAVA_CUTTINGS.get())
        //                         .when(bitterCassavaCropMaxAgeBuilder.invert())))
        //         .withPool(LootPool.lootPool()
        //                 .add(LootItem.lootTableItem(ModItems.BITTER_CASSAVA_CUTTINGS.get())
        //                         .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE,
        //                                0.5714286F, 4))
        //                         .when(bitterCassavaCropMaxAgeBuilder).setWeight(15))
        //                 .add(LootItem
        //                         .lootTableItem(ModItems.CASSAVA_CUTTINGS.get()).apply(ApplyBonusCount
        //                                 .addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286F, 1))
        //                         .when(bitterCassavaCropMaxAgeBuilder).setWeight(1)));

        // this.add(ModBlocks.BITTER_CASSAVA_CROP.get(), bitterCassavaLoot);

        //        LootItemCondition.Builder yamCropMaxAgeBuilder = LootItemBlockStatePropertyCondition
        //                .hasBlockStateProperties(ModBlocks.UBE_CROP.get()).setProperties(StatePropertiesPredicate.Builder
        //                        .properties().hasProperty(CassavaCropBlock.AGE, SpreadingCropBlock.MAX_AGE));
        //        LootTable.Builder yamLoot = LootTable.lootTable()
        //                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(ModItems.UBE.get())))
        //                .withPool(LootPool.lootPool()
        //                        .add(LootItem.lootTableItem(ModItems.UBE.get())
        //                                .apply(ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE,
        //                                        0.5714286F, 1)))
        //                        .setRolls(UniformGenerator.between(1, 2)).when(yamCropMaxAgeBuilder));
        //        this.add(ModBlocks.UBE_CROP.get(), yamLoot);

        //        LootTable.Builder imbuedLogLoot = LootTable.lootTable()
        //                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(ModItems.HEART_FRAGMENT.get())))
        //                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(ModBlocks.VERDANT_HEARTWOOD_LOG.get())));

        // this.add(ModBlocks.IMBUED_VERDANT_HEARTWOOD_LOG.get(), imbuedLogLoot);
    }


    protected void generateFor(WoodSet woodSet) {

        this.knownBlocks.addAll(woodSet.getBlockProvider()
                .getEntries()
                .stream()
                .map(RegistryObject::get)
                .collect(Collectors.toSet()));

        this.dropSelf(woodSet.getPlanks().get());
        this.dropSelf(woodSet.getLog().get());
        this.dropSelf(woodSet.getWood().get());
        this.dropSelf(woodSet.getStrippedLog().get());
        this.dropSelf(woodSet.getStrippedWood().get());
        this.dropSelf(woodSet.getFence().get());
        this.dropSelf(woodSet.getFenceGate().get());
        this.dropSelf(woodSet.getStairs().get());
        this.dropSelf(woodSet.getButton().get());
        this.dropSelf(woodSet.getPressurePlate().get());
        this.dropSelf(woodSet.getTrapdoor().get());
        this.dropOther(woodSet.getWallSign().get(), woodSet.getSignItem().get());
        this.dropOther(woodSet.getSign().get(), woodSet.getSignItem().get());
        this.dropOther(woodSet.getWallHangingSign().get(), woodSet.getHangingSignItem().get());
        this.dropOther(woodSet.getHangingSign().get(), woodSet.getHangingSignItem().get());
        this.add(woodSet.getSlab().get(), this.createSlabItemTable(woodSet.getSlab().get()));
        this.add(woodSet.getDoor().get(), this.createDoorTable(woodSet.getDoor().get()));
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

    @Override
    @NotNull
    protected Iterable<Block> getKnownBlocks() {
        return this.knownBlocks;
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
        ResourceLocation sourceLoc = source.builtInRegistryHolder().key().location().withPrefix("blocks/");
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

