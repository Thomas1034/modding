package com.thomas.verdant.data;

import com.thomas.verdant.Constants;
import com.thomas.verdant.registry.BlockRegistry;
import com.thomas.verdant.registry.ItemRegistry;
import com.thomas.verdant.registry.WoodSets;
import com.thomas.verdant.util.VerdantTags;
import com.thomas.verdant.woodset.WoodSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
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

        this.tag(VerdantTags.Items.STRANGLER_VINES)
                .add(BlockRegistry.STRANGLER_VINE.get().asItem(), BlockRegistry.LEAFY_STRANGLER_VINE.get().asItem());
        tag(Tags.Items.FERTILIZERS).add(ItemRegistry.ROTTEN_COMPOST.get());

        this.tag(ItemTags.SMALL_FLOWERS)
                .add(BlockRegistry.BLEEDING_HEART.get().asItem(), BlockRegistry.WILD_COFFEE.get().asItem(), BlockRegistry.TIGER_LILY.get()
                        .asItem());

        this.tag(Tags.Items.CROPS).add(ItemRegistry.COFFEE_BERRIES.get());
        this.tag(Tags.Items.FOODS_BERRY).add(ItemRegistry.COFFEE_BERRIES.get());
        this.tag(Tags.Items.FOODS_FOOD_POISONING).add(ItemRegistry.ROTTEN_COMPOST.get());

        this.tag(Tags.Items.FOODS).add(ItemRegistry.ROASTED_COFFEE.get());
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
