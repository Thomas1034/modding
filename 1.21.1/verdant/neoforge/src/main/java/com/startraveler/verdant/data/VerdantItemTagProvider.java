package com.startraveler.verdant.data;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.registry.ItemRegistry;
import com.startraveler.verdant.registry.WoodSets;
import com.startraveler.verdant.util.VerdantTags;
import com.startraveler.verdant.woodset.WoodSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class VerdantItemTagProvider extends ItemTagsProvider {
    public VerdantItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags, Constants.MOD_ID);
    }


    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            generateFor(woodSet);
        }
        addDirtOres();


        // Verdant ground
        this.tag(VerdantTags.Items.VERDANT_GROUND).add(
                BlockRegistry.VERDANT_GRASS_DIRT.get().asItem(),
                BlockRegistry.VERDANT_ROOTED_DIRT.get().asItem(),
                BlockRegistry.VERDANT_ROOTED_MUD.get().asItem(),
                BlockRegistry.VERDANT_GRASS_MUD.get().asItem(),
                BlockRegistry.VERDANT_ROOTED_CLAY.get().asItem(),
                BlockRegistry.VERDANT_GRASS_CLAY.get().asItem()
        );

        this.tag(VerdantTags.Items.STRANGLER_VINES)
                .add(BlockRegistry.STRANGLER_VINE.get().asItem(), BlockRegistry.LEAFY_STRANGLER_VINE.get().asItem());
        tag(Tags.Items.FERTILIZERS).add(ItemRegistry.ROTTEN_COMPOST.get(), ItemRegistry.RANCID_SLIME.get());

        this.tag(ItemTags.SMALL_FLOWERS).add(
                BlockRegistry.BLEEDING_HEART.get().asItem(),
                BlockRegistry.WILD_COFFEE.get().asItem(),
                BlockRegistry.RUE.get().asItem(),
                BlockRegistry.TIGER_LILY.get().asItem()
        );


        this.tag(ItemTags.COW_FOOD).add(ItemRegistry.STARCH.get(), ItemRegistry.BITTER_STARCH.get());
        this.tag(ItemTags.HORSE_FOOD).add(ItemRegistry.STARCH.get());
        this.tag(ItemTags.PARROT_FOOD).add(ItemRegistry.STARCH.get());
        this.tag(ItemTags.PARROT_POISONOUS_FOOD).add(ItemRegistry.BITTER_STARCH.get());
        this.tag(ItemTags.SHEEP_FOOD).add(ItemRegistry.STARCH.get(), ItemRegistry.BITTER_STARCH.get());
        this.tag(ItemTags.PIG_FOOD)
                .add(ItemRegistry.CASSAVA.get(), ItemRegistry.BITTER_CASSAVA.get(), ItemRegistry.UBE.get());
        this.tag(ItemTags.CHICKEN_FOOD).add(
                ItemRegistry.STARCH.get(),
                ItemRegistry.BITTER_STARCH.get(),
                ItemRegistry.CASSAVA_CUTTINGS.get(),
                ItemRegistry.BITTER_CASSAVA_CUTTINGS.get()
        );

        this.tag(Tags.Items.CROPS).add(
                ItemRegistry.COFFEE_BERRIES.get(),
                ItemRegistry.BITTER_CASSAVA_CUTTINGS.get(),
                ItemRegistry.CASSAVA_CUTTINGS.get(),
                ItemRegistry.UBE.get()
        );
        this.tag(Tags.Items.FOODS_BERRY).add(ItemRegistry.COFFEE_BERRIES.get());
        this.tag(Tags.Items.FOODS_FOOD_POISONING)
                .add(
                        ItemRegistry.ROTTEN_COMPOST.get(),
                        ItemRegistry.RANCID_SLIME.get(),
                        ItemRegistry.BITTER_BREAD.get()
                );
        this.tag(Tags.Items.FOODS_BREAD).add(ItemRegistry.BITTER_BREAD.get(), ItemRegistry.GOLDEN_BREAD.get());
        this.tag(Tags.Items.FOODS_VEGETABLE)
                .add(ItemRegistry.COOKED_CASSAVA.get(), ItemRegistry.COOKED_GOLDEN_CASSAVA.get());
        this.tag(Tags.Items.FOODS_GOLDEN)
                .add(ItemRegistry.GOLDEN_BREAD.get(), ItemRegistry.COOKED_GOLDEN_CASSAVA.get());

        this.tag(Tags.Items.FOODS).add(
                ItemRegistry.ROASTED_COFFEE.get(),
                ItemRegistry.BITTER_BREAD.get(),
                ItemRegistry.COOKED_CASSAVA.get(),
                ItemRegistry.GOLDEN_BREAD.get(),
                ItemRegistry.COOKED_GOLDEN_CASSAVA.get(),
                ItemRegistry.UBE.get(),
                ItemRegistry.BAKED_UBE.get(),
                ItemRegistry.UBE_COOKIE.get()
        );
        this.tag(Tags.Items.FOODS_EDIBLE_WHEN_PLACED).add(ItemRegistry.UBE_CAKE.get());


        this.tag(ItemTags.ARROWS).add(ItemRegistry.POISON_ARROW.get());
        this.tag(VerdantTags.Items.CRAFTS_TO_ROPES)
                .add(Items.VINE, Items.STRING, BlockRegistry.STRANGLER_TENDRIL.get().asItem());

        this.tag(ItemTags.VILLAGER_PLANTABLE_SEEDS).add(
                ItemRegistry.CASSAVA_CUTTINGS.get(),
                ItemRegistry.BITTER_CASSAVA_CUTTINGS.get(),
                ItemRegistry.UBE.get()
        );
        this.tag(ItemTags.VILLAGER_PICKS_UP).add(
                ItemRegistry.CASSAVA_CUTTINGS.get(),
                ItemRegistry.BITTER_CASSAVA_CUTTINGS.get(),
                ItemRegistry.UBE.get(),
                ItemRegistry.BITTER_CASSAVA.get(),
                ItemRegistry.CASSAVA.get(),
                ItemRegistry.BITTER_BREAD.get()
        );
        this.tag(VerdantTags.Items.STARCHES)
                .add(ItemRegistry.STARCH.get(), ItemRegistry.BITTER_STARCH.get(), ItemRegistry.SPARKLING_STARCH.get());

        addHeartwoodSet();
        addImbuedHeartwoodSet();
    }

    private void addImbuedHeartwoodSet() {
        this.tag(VerdantTags.Items.REPAIRS_IMBUED_HEARTWOOD_ARMOR).add(ItemRegistry.HEART_FRAGMENT.get());
        this.tag(VerdantTags.Items.IMBUED_HEARTWOOD_TOOL_MATERIALS).add(ItemRegistry.HEART_FRAGMENT.get());

        this.tag(ItemTags.TRIMMABLE_ARMOR).add(
                ItemRegistry.IMBUED_HEARTWOOD_HELMET.get(),
                ItemRegistry.IMBUED_HEARTWOOD_CHESTPLATE.get(),
                ItemRegistry.IMBUED_HEARTWOOD_LEGGINGS.get(),
                ItemRegistry.IMBUED_HEARTWOOD_BOOTS.get()
        );
        this.tag(ItemTags.ARMOR_ENCHANTABLE).add(
                ItemRegistry.IMBUED_HEARTWOOD_HELMET.get(),
                ItemRegistry.IMBUED_HEARTWOOD_CHESTPLATE.get(),
                ItemRegistry.IMBUED_HEARTWOOD_LEGGINGS.get(),
                ItemRegistry.IMBUED_HEARTWOOD_BOOTS.get()
        );
        this.tag(ItemTags.HEAD_ARMOR).add(ItemRegistry.IMBUED_HEARTWOOD_HELMET.get());
        this.tag(ItemTags.HEAD_ARMOR_ENCHANTABLE).add(ItemRegistry.IMBUED_HEARTWOOD_HELMET.get());
        this.tag(ItemTags.CHEST_ARMOR).add(ItemRegistry.IMBUED_HEARTWOOD_CHESTPLATE.get());
        this.tag(ItemTags.CHEST_ARMOR_ENCHANTABLE).add(ItemRegistry.IMBUED_HEARTWOOD_CHESTPLATE.get());
        this.tag(ItemTags.LEG_ARMOR).add(ItemRegistry.IMBUED_HEARTWOOD_LEGGINGS.get());
        this.tag(ItemTags.LEG_ARMOR_ENCHANTABLE).add(ItemRegistry.IMBUED_HEARTWOOD_LEGGINGS.get());
        this.tag(ItemTags.FOOT_ARMOR).add(ItemRegistry.IMBUED_HEARTWOOD_BOOTS.get());
        this.tag(ItemTags.FOOT_ARMOR_ENCHANTABLE).add(ItemRegistry.IMBUED_HEARTWOOD_BOOTS.get());

        this.tag(ItemTags.AXES).add(ItemRegistry.IMBUED_HEARTWOOD_AXE.get());
        this.tag(ItemTags.HOES).add(ItemRegistry.IMBUED_HEARTWOOD_HOE.get());
        this.tag(ItemTags.PICKAXES).add(ItemRegistry.IMBUED_HEARTWOOD_PICKAXE.get());
        this.tag(ItemTags.SHOVELS).add(ItemRegistry.IMBUED_HEARTWOOD_SHOVEL.get());
        this.tag(ItemTags.SWORDS).add(ItemRegistry.IMBUED_HEARTWOOD_SWORD.get());
        this.tag(ItemTags.SWORD_ENCHANTABLE).add(ItemRegistry.IMBUED_HEARTWOOD_SWORD.get());
    }

    private void addHeartwoodSet() {
        this.tag(VerdantTags.Items.REPAIRS_HEARTWOOD_ARMOR).addTag(WoodSets.HEARTWOOD.getLogItems());
        this.tag(VerdantTags.Items.HEARTWOOD_TOOL_MATERIALS).addTag(WoodSets.HEARTWOOD.getLogItems());

        this.tag(ItemTags.TRIMMABLE_ARMOR).add(
                ItemRegistry.HEARTWOOD_HELMET.get(),
                ItemRegistry.HEARTWOOD_CHESTPLATE.get(),
                ItemRegistry.HEARTWOOD_LEGGINGS.get(),
                ItemRegistry.HEARTWOOD_BOOTS.get()
        );
        this.tag(ItemTags.ARMOR_ENCHANTABLE).add(
                ItemRegistry.HEARTWOOD_HELMET.get(),
                ItemRegistry.HEARTWOOD_CHESTPLATE.get(),
                ItemRegistry.HEARTWOOD_LEGGINGS.get(),
                ItemRegistry.HEARTWOOD_BOOTS.get()
        );
        this.tag(ItemTags.HEAD_ARMOR).add(ItemRegistry.HEARTWOOD_HELMET.get());
        this.tag(ItemTags.HEAD_ARMOR_ENCHANTABLE).add(ItemRegistry.HEARTWOOD_HELMET.get());
        this.tag(ItemTags.CHEST_ARMOR).add(ItemRegistry.HEARTWOOD_CHESTPLATE.get());
        this.tag(ItemTags.CHEST_ARMOR_ENCHANTABLE).add(ItemRegistry.HEARTWOOD_CHESTPLATE.get());
        this.tag(ItemTags.LEG_ARMOR).add(ItemRegistry.HEARTWOOD_LEGGINGS.get());
        this.tag(ItemTags.LEG_ARMOR_ENCHANTABLE).add(ItemRegistry.HEARTWOOD_LEGGINGS.get());
        this.tag(ItemTags.FOOT_ARMOR).add(ItemRegistry.HEARTWOOD_BOOTS.get());
        this.tag(ItemTags.FOOT_ARMOR_ENCHANTABLE).add(ItemRegistry.HEARTWOOD_BOOTS.get());

        this.tag(ItemTags.AXES).add(ItemRegistry.HEARTWOOD_AXE.get());
        this.tag(ItemTags.HOES).add(ItemRegistry.HEARTWOOD_HOE.get());
        this.tag(ItemTags.PICKAXES).add(ItemRegistry.HEARTWOOD_PICKAXE.get());
        this.tag(ItemTags.SHOVELS).add(ItemRegistry.HEARTWOOD_SHOVEL.get());
        this.tag(ItemTags.SWORDS).add(ItemRegistry.HEARTWOOD_SWORD.get());
        this.tag(ItemTags.SWORD_ENCHANTABLE).add(ItemRegistry.HEARTWOOD_SWORD.get());

        this.tag(VerdantTags.Items.DARTS).add(ItemRegistry.DART.get(), ItemRegistry.TIPPED_DART.get());
    }

    private void addDirtOres() {
        this.tag(Tags.Items.ORES).add(
                BlockRegistry.DIRT_COAL_ORE.get().asItem(),
                BlockRegistry.DIRT_COPPER_ORE.get().asItem(),
                BlockRegistry.DIRT_DIAMOND_ORE.get().asItem(),
                BlockRegistry.DIRT_EMERALD_ORE.get().asItem(),
                BlockRegistry.DIRT_GOLD_ORE.get().asItem(),
                BlockRegistry.DIRT_IRON_ORE.get().asItem(),
                BlockRegistry.DIRT_LAPIS_ORE.get().asItem(),
                BlockRegistry.DIRT_REDSTONE_ORE.get().asItem()
        );
        this.tag(Tags.Items.ORES_COAL).add(BlockRegistry.DIRT_COAL_ORE.get().asItem());
        this.tag(Tags.Items.ORES_COPPER).add(BlockRegistry.DIRT_COPPER_ORE.get().asItem());
        this.tag(Tags.Items.ORES_DIAMOND).add(BlockRegistry.DIRT_DIAMOND_ORE.get().asItem());
        this.tag(Tags.Items.ORES_EMERALD).add(BlockRegistry.DIRT_EMERALD_ORE.get().asItem());
        this.tag(Tags.Items.ORES_GOLD).add(BlockRegistry.DIRT_GOLD_ORE.get().asItem());
        this.tag(Tags.Items.ORES_IRON).add(BlockRegistry.DIRT_IRON_ORE.get().asItem());
        this.tag(Tags.Items.ORES_LAPIS).add(BlockRegistry.DIRT_LAPIS_ORE.get().asItem());
        this.tag(Tags.Items.ORES_REDSTONE).add(BlockRegistry.DIRT_REDSTONE_ORE.get().asItem());
    }


    public void generateFor(WoodSet woodSet) {
        this.tag(woodSet.getLogItems()).add(
                woodSet.getLog().get().asItem(),
                woodSet.getWood().get().asItem(),
                woodSet.getStrippedLog().get().asItem(),
                woodSet.getStrippedWood().get().asItem()
        );
        this.tag(ItemTags.WOODEN_SLABS).add(woodSet.getSlab().get().asItem());
        this.tag(ItemTags.WOODEN_STAIRS).add(woodSet.getStairs().get().asItem());
        this.tag(ItemTags.WOODEN_BUTTONS).add(woodSet.getButton().get().asItem());
        this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add(woodSet.getPressurePlate().get().asItem());
        this.tag(ItemTags.WOODEN_FENCES).add(woodSet.getFence().get().asItem());
        this.tag(ItemTags.PLANKS).add(woodSet.getPlanks().get().asItem());
        this.tag(ItemTags.LOGS).add(
                woodSet.getLog().get().asItem(),
                woodSet.getWood().get().asItem(),
                woodSet.getStrippedLog().get().asItem(),
                woodSet.getStrippedWood().get().asItem()
        );
        if (woodSet.isFlammable()) {
            this.tag(ItemTags.LOGS_THAT_BURN).add(
                    woodSet.getLog().get().asItem(),
                    woodSet.getWood().get().asItem(),
                    woodSet.getStrippedLog().get().asItem(),
                    woodSet.getStrippedWood().get().asItem()
            );
        }
        this.tag(ItemTags.SIGNS).add(woodSet.getSignItem().get());
        this.tag(ItemTags.HANGING_SIGNS).add(woodSet.getHangingSignItem().get());
        this.tag(ItemTags.WOODEN_DOORS).add(woodSet.getDoor().get().asItem());
        this.tag(ItemTags.WOODEN_TRAPDOORS).add(woodSet.getTrapdoor().get().asItem());
        this.tag(Tags.Items.FENCE_GATES_WOODEN).add(woodSet.getFenceGate().get().asItem());
        this.tag(Tags.Items.FENCES_WOODEN).add(woodSet.getFence().get().asItem());
    }
}
