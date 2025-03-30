package com.startraveler.verdant.data;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.recipe.BlowdartTippingRecipe;
import com.startraveler.verdant.recipe.RopeCoilUpgradeRecipe;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.registry.ItemRegistry;
import com.startraveler.verdant.registry.WoodSets;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class VerdantRecipeProvider extends RecipeProvider {
    protected final String modid;

    public VerdantRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
        this.modid = Constants.MOD_ID;
    }


    // TODO marking the place since IntelliJ sorts this file (partially and arbitrarily)
    @Override
    protected void buildRecipes() {

        suspiciousStew(
                BlockRegistry.WILD_COFFEE.get().asItem(),
                Objects.requireNonNull(SuspiciousEffectHolder.tryGet(BlockRegistry.WILD_COFFEE.get()))
        );
        suspiciousStew(
                BlockRegistry.BLEEDING_HEART.get().asItem(),
                Objects.requireNonNull(SuspiciousEffectHolder.tryGet(BlockRegistry.BLEEDING_HEART.get()))
        );
        suspiciousStew(
                BlockRegistry.BLUEWEED.get().asItem(),
                Objects.requireNonNull(SuspiciousEffectHolder.tryGet(BlockRegistry.BLUEWEED.get()))
        );
        suspiciousStew(
                BlockRegistry.TIGER_LILY.get().asItem(),
                Objects.requireNonNull(SuspiciousEffectHolder.tryGet(BlockRegistry.TIGER_LILY.get()))
        );
        suspiciousStew(
                BlockRegistry.RUE.get().asItem(),
                Objects.requireNonNull(SuspiciousEffectHolder.tryGet(BlockRegistry.RUE.get()))
        );
        suspiciousStew(
                BlockRegistry.DROWNED_HEMLOCK.get().asItem(),
                Objects.requireNonNull(SuspiciousEffectHolder.tryGet(BlockRegistry.DROWNED_HEMLOCK.get()))
        );

        shapeless(List.of(BlockRegistry.TIGER_LILY.get()), List.of(1), RecipeCategory.MISC, Items.ORANGE_DYE, 1);
        shapeless(List.of(BlockRegistry.BLEEDING_HEART.get()), List.of(1), RecipeCategory.MISC, Items.RED_DYE, 1);
        shapeless(List.of(BlockRegistry.BLUEWEED.get()), List.of(1), RecipeCategory.MISC, Items.BLUE_DYE, 1);
        shapeless(List.of(BlockRegistry.WILD_COFFEE.get()), List.of(1), RecipeCategory.MISC, Items.LIGHT_GRAY_DYE, 1);
        shapeless(List.of(ItemRegistry.ROASTED_COFFEE.get()), List.of(1), RecipeCategory.MISC, Items.BROWN_DYE, 1);

        shaped(
                List.of(" f ", "fdf", " f "),
                List.of('f', 'd'),
                List.of(ItemRegistry.HEART_FRAGMENT.get(), VerdantTags.Items.VERDANT_GROUND),
                RecipeCategory.MISC,
                ItemRegistry.HEART_OF_THE_FOREST.get(),
                1
        );

        shaped(
                List.of("fff", "fdf"),
                List.of('f', 'd'),
                List.of(VerdantTags.Items.ALOES, ItemTags.DIRT),
                RecipeCategory.MISC,
                ItemRegistry.ALOE_PUP.get(),
                1
        );

        shapeless(
                List.of(Items.ROTTEN_FLESH, BlockRegistry.POISON_IVY.get(), Tags.Items.SEEDS),
                List.of(1, 1, 1),
                RecipeCategory.TOOLS,
                ItemRegistry.ROTTEN_COMPOST.get(),
                2
        );

        shapeless(
                List.of(Tags.Items.EGGS, Tags.Items.SLIME_BALLS, VerdantTags.Items.STARCHES),
                List.of(1, 1, 1),
                RecipeCategory.TOOLS,
                ItemRegistry.RANCID_SLIME.get(),
                2
        );

        shaped(
                List.of("rbs", "bfb", "smr"), List.of('r', 'b', 's', 'f', 'm'), List.of(
                        ItemRegistry.ROTTEN_COMPOST.get(),
                        Items.BONE_MEAL,
                        ItemRegistry.RANCID_SLIME.get(),
                        BlockRegistry.PAPER_FRAME.get(),
                        Blocks.MUD
                ), RecipeCategory.BUILDING_BLOCKS, BlockRegistry.PUTRID_FERTILIZER.get(), 1
        );

        shaped(
                List.of("vv", "vv"),
                List.of('v'),
                List.of(VerdantTags.Items.STRANGLER_VINES),
                RecipeCategory.BUILDING_BLOCKS,
                WoodSets.STRANGLER.getLog().get(),
                1
        );

        shapeless(
                List.of(BlockRegistry.STRANGLER_VINE.get().asItem()),
                List.of(1),
                RecipeCategory.MISC,
                BlockRegistry.LEAFY_STRANGLER_VINE.get(),
                1
        );
        shapeless(
                List.of(BlockRegistry.LEAFY_STRANGLER_VINE.get().asItem()),
                List.of(1),
                RecipeCategory.MISC,
                BlockRegistry.STRANGLER_VINE.get(),
                1
        );
        shaped(
                List.of("v", "v"),
                List.of('v'),
                List.of(BlockRegistry.STRANGLER_TENDRIL.get().asItem()),
                RecipeCategory.MISC,
                ItemRegistry.ROPE.get(),
                2
        );
        shaped(
                List.of("v", "v", "w"),
                List.of('v', 'w'),
                List.of(ItemRegistry.ROPE.get(), VerdantTags.Items.CRAFTS_TO_ROPES),
                RecipeCategory.MISC,
                ItemRegistry.ROPE.get(),
                3
        );
        shaped(
                List.of(" r ", "r r", " r "),
                List.of('r'),
                List.of(ItemRegistry.ROPE.get()),
                RecipeCategory.MISC,
                ItemRegistry.ROPE_COIL.get(),
                1
        );

        shapeless(List.of(Items.WATER_BUCKET, Items.DIRT), List.of(1, 8), RecipeCategory.BUILDING_BLOCKS, Items.MUD, 8);

        // Register rope upgrading.
        new RopeCoilUpgradeRecipe.Builder().category(CraftingBookCategory.EQUIPMENT).save(output);
        // Register dart tipping.
        new BlowdartTippingRecipe.Builder().category(CraftingBookCategory.EQUIPMENT).save(output);

        // Cooking coffee
        foodCooking(
                List.of(ItemRegistry.COFFEE_BERRIES.get()),
                RecipeCategory.FOOD,
                ItemRegistry.ROASTED_COFFEE.get(),
                0.1f,
                400
        );

        // Arrow from thorn
        shaped(
                List.of("  P", " S ", "V  "),
                List.of('P', 'S', 'V'),
                List.of(ItemRegistry.THORN.get(), Items.STICK, Items.VINE),
                RecipeCategory.COMBAT,
                Items.ARROW,
                1
        );

        // Poison arrows
        shapeless(
                List.of(Items.ARROW, BlockRegistry.POISON_IVY.get().asItem()),
                List.of(1, 1),
                RecipeCategory.COMBAT,
                ItemRegistry.POISON_ARROW.get(),
                1
        );

        // Packed mud from strangler tendrils
        shapeless(
                List.of(BlockRegistry.STRANGLER_TENDRIL.get(), Blocks.MUD),
                List.of(2, 1),
                RecipeCategory.TOOLS,
                Blocks.PACKED_MUD,
                1
        );
        shapeless(
                List.of(ItemRegistry.ROPE.get(), Blocks.MUD),
                List.of(2, 1),
                RecipeCategory.TOOLS,
                Blocks.PACKED_MUD,
                1
        );

        shaped(
                List.of("gg", "gg"),
                List.of('g'),
                List.of(Blocks.GRAVEL),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.PACKED_GRAVEL.get(),
                1
        );

        shaped(
                List.of("gg", "gg"),
                List.of('g'),
                List.of(BlockRegistry.SCREE.get()),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.PACKED_SCREE.get(),
                1
        );

        shaped(
                List.of("gx", "xg"),
                List.of('g', 'x'),
                List.of(BlockRegistry.GRUS.get(), Blocks.MOSS_BLOCK),
                RecipeCategory.BUILDING_BLOCKS,
                Blocks.DIRT,
                4
        );

        shaped(
                List.of("gx", "xg"),
                List.of('g', 'x'),
                List.of(BlockRegistry.GRUS.get(), Blocks.PALE_MOSS_BLOCK),
                RecipeCategory.BUILDING_BLOCKS,
                Blocks.DIRT,
                4
        );

        shaped(
                List.of("gx", "xg"),
                List.of('g', 'x'),
                List.of(BlockRegistry.GRUS.get(), BlockRegistry.SCREE.get()),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.STONY_GRUS.get(),
                4
        );

        smeltingResultFromBase(BlockRegistry.FUSED_GRAVEL.get(), BlockRegistry.PACKED_GRAVEL.get());

        smeltingResultFromBase(BlockRegistry.FUSED_SCREE.get(), BlockRegistry.PACKED_SCREE.get());

        // Thorn spikes item
        shaped(
                List.of("TTT", "TTT", "KRK"),
                List.of('T', 'K', 'R'),
                List.of(ItemRegistry.THORN.get(), Items.STICK, Items.STRING),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.WOODEN_SPIKES.get(),
                1
        );
        shaped(
                List.of("TTT", "TTT", "KRK"),
                List.of('T', 'K', 'R'),
                List.of(ItemRegistry.THORN.get(), Items.STICK, BlockRegistry.ROPE.get()),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.WOODEN_SPIKES.get(),
                1
        );
        // Iron spikes item
        shaped(
                List.of("NNN", "SSS", "III"),
                List.of('N', 'S', 'I'),
                List.of(Items.IRON_NUGGET, BlockRegistry.WOODEN_SPIKES.get(), Items.IRON_BARS),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.IRON_SPIKES.get(),
                3
        );

        // Frame block
        shaped(
                List.of("HTH", "T T", "HTH"),
                List.of('T', 'H'),
                List.of(Items.STICK, ItemRegistry.THORN.get()),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.FRAME_BLOCK.get(),
                1
        );
        smeltingResultFromBase(Items.GREEN_DYE, BlockRegistry.SNAPLEAF.get());
        smeltingResultFromBase(BlockRegistry.CHARRED_FRAME_BLOCK.get(), BlockRegistry.FRAME_BLOCK.get());
        shaped(
                List.of(" P ", "PFP", " P "),
                List.of('P', 'F'),
                List.of(Items.PAPER, BlockRegistry.FRAME_BLOCK.get()),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.PAPER_FRAME.get(),
                1
        );

        // Traps
        shaped(
                List.of("S S", "SCS", "TPT"), List.of('S', 'C', 'T', 'P'), List.of(
                        BlockRegistry.WOODEN_SPIKES.get(),
                        Items.COPPER_INGOT,
                        Items.STICK,
                        BlockTags.WOODEN_PRESSURE_PLATES
                ), RecipeCategory.MISC, BlockRegistry.WOODEN_TRAP.get(), 3
        );
        shaped(
                List.of("S S", "SCS", "TPT"),
                List.of('S', 'C', 'T', 'P'),
                List.of(BlockRegistry.IRON_SPIKES.get(), Items.IRON_INGOT, Items.STICK, Items.STONE_PRESSURE_PLATE),
                RecipeCategory.MISC,
                BlockRegistry.IRON_TRAP.get(),
                3
        );
        shaped(
                List.of("FPF", "LLL", "FPF"),
                List.of('F', 'P', 'L'),
                List.of(BlockRegistry.FRAME_BLOCK.get(), ItemTags.PLANKS, BlockRegistry.ROPE.get()),
                RecipeCategory.MISC,
                BlockRegistry.FISH_TRAP.get(),
                2
        );

        // Roasting bitter cassava
        foodCooking(
                List.of(ItemRegistry.BITTER_CASSAVA.get()),
                RecipeCategory.FOOD,
                ItemRegistry.CASSAVA.get(),
                0.1f,
                200
        );

        // Golden Cassava
        shaped(
                List.of("GGG", "GCG", "GGG"),
                List.of('G', 'C'),
                List.of(Items.GOLD_INGOT, ItemRegistry.CASSAVA.get()),
                RecipeCategory.FOOD,
                ItemRegistry.GOLDEN_CASSAVA.get(),
                1
        );

        // Roasting golden cassava
        foodCooking(
                List.of(ItemRegistry.GOLDEN_CASSAVA.get()),
                RecipeCategory.FOOD,
                ItemRegistry.COOKED_GOLDEN_CASSAVA.get(),
                0.1f,
                200
        );

        // Sparkling Starch
        shapeless(
                List.of(ItemRegistry.GOLDEN_CASSAVA.get()),
                List.of(1),
                RecipeCategory.FOOD,
                ItemRegistry.SPARKLING_STARCH.get(),
                4
        );

        // Starch
        shapeless(List.of(ItemRegistry.CASSAVA.get()), List.of(1), RecipeCategory.FOOD, ItemRegistry.STARCH.get(), 4);

        // Bitter Starch
        shapeless(
                List.of(ItemRegistry.BITTER_CASSAVA.get()),
                List.of(1),
                RecipeCategory.FOOD,
                ItemRegistry.BITTER_STARCH.get(),
                4
        );

        // Bread
        shaped(List.of("BBB"), List.of('B'), List.of(ItemRegistry.STARCH.get()), RecipeCategory.FOOD, Items.BREAD, 1);

        // Bitter Bread
        shaped(
                List.of("BBB"),
                List.of('B'),
                List.of(ItemRegistry.BITTER_STARCH.get()),
                RecipeCategory.FOOD,
                ItemRegistry.BITTER_BREAD.get(),
                1
        );

        // Golden Bread
        shaped(
                List.of("BBB"),
                List.of('B'),
                List.of(ItemRegistry.SPARKLING_STARCH.get()),
                RecipeCategory.FOOD,
                ItemRegistry.GOLDEN_BREAD.get(),
                1
        );

        // Roasting cassava
        foodCooking(
                List.of(ItemRegistry.CASSAVA.get()),
                RecipeCategory.FOOD,
                ItemRegistry.COOKED_CASSAVA.get(),
                0.1f,
                200
        );


        // Roasting ube
        foodCooking(List.of(ItemRegistry.UBE.get()), RecipeCategory.FOOD, ItemRegistry.BAKED_UBE.get(), 0.1f, 200);

        // Purple dye from ube
        shapeless(List.of(ItemRegistry.BAKED_UBE.get()), List.of(1), RecipeCategory.DECORATIONS, Items.PURPLE_DYE, 1);

        // Purple dye from ube with mordant
        shapeless(
                List.of(ItemRegistry.BAKED_UBE.get(), Items.RAW_COPPER, Items.WATER_BUCKET),
                List.of(4, 1, 1),
                RecipeCategory.DECORATIONS,
                Items.PURPLE_DYE,
                12
        );

        // Purple cake
        shaped(
                List.of("UBU", "GEG", "SSS"), List.of('U', 'B', 'S', 'E', 'G'), List.of(
                        ItemRegistry.BAKED_UBE.get(),
                        Items.MILK_BUCKET,
                        ItemRegistry.STARCH.get(),
                        Items.EGG,
                        Items.SUGAR
                ), RecipeCategory.FOOD, ItemRegistry.UBE_CAKE.get(), 1
        );
        shaped(
                List.of("UBU", "GEG", "SSS"),
                List.of('U', 'B', 'S', 'E', 'G'),
                List.of(ItemRegistry.BAKED_UBE.get(), Items.MILK_BUCKET, Items.WHEAT, Items.EGG, Items.SUGAR),
                RecipeCategory.FOOD,
                ItemRegistry.UBE_CAKE.get(),
                1
        );

        // Purple cookies
        shaped(
                List.of("SUS"),
                List.of('U', 'S'),
                List.of(ItemRegistry.BAKED_UBE.get(), ItemRegistry.STARCH.get()),
                RecipeCategory.FOOD,
                ItemRegistry.UBE_COOKIE.get(),
                8
        );

        // Purple cookies
        shaped(
                List.of("SUS"),
                List.of('U', 'S'),
                List.of(ItemRegistry.BAKED_UBE.get(), Items.WHEAT),
                RecipeCategory.FOOD,
                ItemRegistry.UBE_COOKIE.get(),
                8
        );

        // Slime from Aloe
        shapeless(List.of(VerdantTags.Items.ALOES), List.of(3), RecipeCategory.TOOLS, Items.SLIME_BALL, 1);


        // Heartwood armor
        shaped(
                List.of("PPP", "P P"),
                List.of('P'),
                List.of(WoodSets.HEARTWOOD.getLogItems()),
                RecipeCategory.COMBAT,
                ItemRegistry.HEARTWOOD_HELMET.get(),
                1
        );
        shaped(
                List.of("P P", "PPP", "PPP"),
                List.of('P'),
                List.of(WoodSets.HEARTWOOD.getLogItems()),
                RecipeCategory.COMBAT,
                ItemRegistry.HEARTWOOD_CHESTPLATE.get(),
                1
        );
        shaped(
                List.of("PPP", "P P", "P P"),
                List.of('P'),
                List.of(WoodSets.HEARTWOOD.getLogItems()),
                RecipeCategory.COMBAT,
                ItemRegistry.HEARTWOOD_LEGGINGS.get(),
                1
        );
        shaped(
                List.of("P P", "P P"),
                List.of('P'),
                List.of(WoodSets.HEARTWOOD.getLogItems()),
                RecipeCategory.COMBAT,
                ItemRegistry.HEARTWOOD_BOOTS.get(),
                1
        );

        // Heartwood tools
        shaped(
                List.of("P", "P", "S"),
                List.of('P', 'S'),
                List.of(WoodSets.HEARTWOOD.getLogItems(), Items.STICK),
                RecipeCategory.COMBAT,
                ItemRegistry.HEARTWOOD_SWORD.get(),
                1
        );
        shaped(
                List.of("PPP", " S ", " S "),
                List.of('P', 'S'),
                List.of(WoodSets.HEARTWOOD.getLogItems(), Items.STICK),
                RecipeCategory.TOOLS,
                ItemRegistry.HEARTWOOD_PICKAXE.get(),
                1
        );
        shaped(
                List.of("PP", "PS", " S"),
                List.of('P', 'S'),
                List.of(WoodSets.HEARTWOOD.getLogItems(), Items.STICK),
                RecipeCategory.TOOLS,
                ItemRegistry.HEARTWOOD_AXE.get(),
                1
        );
        shaped(
                List.of("PP", " S", " S"),
                List.of('P', 'S'),
                List.of(WoodSets.HEARTWOOD.getLogItems(), Items.STICK),
                RecipeCategory.TOOLS,
                ItemRegistry.HEARTWOOD_HOE.get(),
                1
        );
        shaped(
                List.of("P", "S", "S"),
                List.of('P', 'S'),
                List.of(WoodSets.HEARTWOOD.getLogItems(), Items.STICK),
                RecipeCategory.TOOLS,
                ItemRegistry.HEARTWOOD_SHOVEL.get(),
                1
        );


        // Imbuing
        imbuementSmithing(
                ItemRegistry.HEARTWOOD_AXE.get(),
                RecipeCategory.TOOLS,
                ItemRegistry.IMBUED_HEARTWOOD_AXE.get()
        );
        imbuementSmithing(
                ItemRegistry.HEARTWOOD_HOE.get(),
                RecipeCategory.TOOLS,
                ItemRegistry.IMBUED_HEARTWOOD_HOE.get()
        );
        imbuementSmithing(
                ItemRegistry.HEARTWOOD_PICKAXE.get(),
                RecipeCategory.TOOLS,
                ItemRegistry.IMBUED_HEARTWOOD_PICKAXE.get()
        );
        imbuementSmithing(
                ItemRegistry.HEARTWOOD_SHOVEL.get(),
                RecipeCategory.TOOLS,
                ItemRegistry.IMBUED_HEARTWOOD_SHOVEL.get()
        );
        imbuementSmithing(
                ItemRegistry.HEARTWOOD_SWORD.get(),
                RecipeCategory.COMBAT,
                ItemRegistry.IMBUED_HEARTWOOD_SWORD.get()
        );
        imbuementSmithing(
                ItemRegistry.HEARTWOOD_HELMET.get(),
                RecipeCategory.COMBAT,
                ItemRegistry.IMBUED_HEARTWOOD_HELMET.get()
        );
        imbuementSmithing(
                ItemRegistry.HEARTWOOD_CHESTPLATE.get(),
                RecipeCategory.COMBAT,
                ItemRegistry.IMBUED_HEARTWOOD_CHESTPLATE.get()
        );
        imbuementSmithing(
                ItemRegistry.HEARTWOOD_LEGGINGS.get(),
                RecipeCategory.COMBAT,
                ItemRegistry.IMBUED_HEARTWOOD_LEGGINGS.get()
        );
        imbuementSmithing(
                ItemRegistry.HEARTWOOD_BOOTS.get(),
                RecipeCategory.COMBAT,
                ItemRegistry.IMBUED_HEARTWOOD_BOOTS.get()
        );

        // Imbuement smithing template.
        shaped(
                List.of("#S#", "#L#", "###"),
                List.of('#', 'S', 'L'),
                List.of(WoodSets.HEARTWOOD.getLogItems(), Items.EMERALD, WoodSets.STRANGLER.getLogItems()),
                RecipeCategory.COMBAT,
                ItemRegistry.IMBUEMENT_UPGRADE_SMITHING_TEMPLATE.get(),
                1
        );

        // Poison Ivy block.
        shaped(
                List.of("III", "IFI", "III"),
                List.of('I', 'F'),
                List.of(BlockRegistry.POISON_IVY.get(), BlockRegistry.FRAME_BLOCK.get()),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.POISON_IVY_BLOCK.get(),
                1
        );

        // Toxic Ash block
        smelting(
                List.of(BlockRegistry.POISON_IVY_BLOCK.get()),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.TOXIC_ASH_BLOCK.get(),
                0.2f,
                200,
                "toxic_ash_block"
        );


        // Toxic Ash
        shapeless(
                List.of(BlockRegistry.TOXIC_ASH_BLOCK.get()),
                List.of(1),
                RecipeCategory.TOOLS,
                ItemRegistry.TOXIC_ASH.get(),
                8
        );

        // Toxic Ash
        shapeless(
                List.of(ItemRegistry.TOXIC_ASH.get(), Items.BUCKET),
                List.of(8, 1),
                RecipeCategory.TOOLS,
                ItemRegistry.BUCKET_OF_TOXIC_ASH.get(),
                1
        );

        // Toxic Ash
        shapeless(
                List.of(ItemRegistry.BUCKET_OF_TOXIC_ASH.get(), Items.WATER_BUCKET),
                List.of(1, 1),
                RecipeCategory.TOOLS,
                ItemRegistry.BUCKET_OF_TOXIC_SOLUTION.get(),
                1
        );

        smelting(
                List.of(BlockRegistry.TOXIC_DIRT.get()),
                RecipeCategory.BUILDING_BLOCKS,
                Blocks.COARSE_DIRT,
                0.2f,
                200,
                "detoxification"
        );


        // Rope Ladder block.
        shaped(
                List.of("R R", "RSR", "R R"),
                List.of('R', 'S'),
                List.of(BlockRegistry.ROPE.get(), Items.STICK),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.ROPE_LADDER.get(),
                3
        );

        shapeless(
                List.of(Items.SUGAR, ItemRegistry.BLASTING_BLOOM.get(), VerdantTags.Items.STARCHES),
                List.of(1, 2, 1),
                RecipeCategory.TOOLS,
                ItemRegistry.STABLE_BLASTING_BLOOM.get(),
                2
        );
        shapeless(
                List.of(ItemTags.BREWING_FUEL, ItemRegistry.STABLE_BLASTING_BLOOM.get()),
                List.of(1, 8),
                RecipeCategory.COMBAT,
                ItemRegistry.BLASTING_BLOOM.get(),
                8
        );

        shapeless(
                List.of(Items.CHARCOAL, ItemRegistry.BLASTING_BLOOM.get()),
                List.of(1, 1),
                RecipeCategory.MISC,
                Items.GUNPOWDER,
                1
        );


        shaped(
                List.of("BCB", "BGB", "BFB"), List.of('B', 'C', 'G', 'F'), List.of(
                        ItemRegistry.BLASTING_BLOOM.get(),
                        Items.MOSS_CARPET,
                        BlockRegistry.GRUS.get(),
                        BlockRegistry.PUTRID_FERTILIZER.get()
                ), RecipeCategory.TOOLS, ItemRegistry.BLASTING_BLOSSOM_SPROUT.get(), 2
        );

        shaped(
                List.of("BCB", "BGB", "BFB"), List.of('B', 'C', 'G', 'F'), List.of(
                        ItemRegistry.BLASTING_BLOOM.get(),
                        Items.PALE_MOSS_CARPET,
                        BlockRegistry.GRUS.get(),
                        BlockRegistry.PUTRID_FERTILIZER.get()
                ), RecipeCategory.TOOLS, ItemRegistry.BLASTING_BLOSSOM_SPROUT.get(), 2
        );

        shaped(
                List.of("DDD", "DAD", "DDD"),
                List.of('A', 'D'),
                List.of(ItemRegistry.TOXIC_ASH.get(), BlockTags.DIRT),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.EARTH_BRICKS.get(),
                4
        );

        shaped(
                List.of("BD", "DB"),
                List.of('B', 'D'),
                List.of(BlockRegistry.TOXIC_DIRT.get(), BlockTags.DIRT),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.EARTH_BRICKS.get(),
                2
        );

        shaped(
                List.of("BD", "DB"),
                List.of('B', 'D'),
                List.of(BlockTags.DIRT, BlockRegistry.TOXIC_GRUS.get()),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.EARTH_BRICKS.get(),
                2
        );

        shaped(
                List.of("E  ", "EE ", "EEE"),
                List.of('E'),
                List.of(BlockRegistry.EARTH_BRICKS.get()),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.EARTH_BRICK_STAIRS.get(),
                4
        );

        shaped(
                List.of("EEE"),
                List.of('E'),
                List.of(BlockRegistry.EARTH_BRICKS.get()),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.EARTH_BRICK_SLAB.get(),
                6
        );

        shaped(
                List.of("EEE", "EEE"),
                List.of('E'),
                List.of(BlockRegistry.EARTH_BRICKS.get()),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.EARTH_BRICK_WALL.get(),
                6
        );

        stonecutting(
                BlockRegistry.EARTH_BRICKS.get(),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.EARTH_BRICK_SLAB.get(),
                2
        );
        stonecutting(
                BlockRegistry.EARTH_BRICKS.get(),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.EARTH_BRICK_WALL.get(),
                1
        );
        stonecutting(
                BlockRegistry.EARTH_BRICKS.get(),
                RecipeCategory.BUILDING_BLOCKS,
                BlockRegistry.EARTH_BRICK_STAIRS.get(),
                1
        );

        shaped(
                List.of("wb ", " wi", "  w"),
                List.of('b', 'w', 'i'),
                List.of(Items.BAMBOO, Tags.Items.RODS_WOODEN, Tags.Items.NUGGETS_IRON),
                RecipeCategory.COMBAT,
                ItemRegistry.BLOWGUN.get(),
                1
        );

        shaped(
                List.of("t ", " s"),
                List.of('s', 't'),
                List.of(Tags.Items.RODS_WOODEN, ItemRegistry.THORN.get()),
                RecipeCategory.COMBAT,
                ItemRegistry.DART.get(),
                1
        );


        shapeless(RecipeCategory.MISC, BlockRegistry.TALL_THORN_BUSH.get(), 4, ItemRegistry.THORN.get(), 8);
        shapeless(RecipeCategory.MISC, BlockRegistry.TALL_BUSH.get(), 4, WoodSets.STRANGLER.getPlanks().get(), 1);

        shapeless(RecipeCategory.MISC, BlockRegistry.THORN_BUSH.get(), 2, BlockRegistry.TALL_THORN_BUSH.get(), 1);
        shapeless(RecipeCategory.MISC, BlockRegistry.TALL_THORN_BUSH.get(), 1, BlockRegistry.THORN_BUSH.get(), 2);

        shapeless(RecipeCategory.MISC, BlockRegistry.TALL_BUSH.get(), 1, BlockRegistry.BUSH.get(), 2);
        shapeless(RecipeCategory.MISC, BlockRegistry.BUSH.get(), 2, BlockRegistry.TALL_BUSH.get(), 1);


        // Thornification
        thornsSmithing(ItemRegistry.HEARTWOOD_AXE.get(), RecipeCategory.TOOLS, ItemRegistry.THORNY_HEARTWOOD_AXE.get());
        thornsSmithing(ItemRegistry.HEARTWOOD_HOE.get(), RecipeCategory.TOOLS, ItemRegistry.THORNY_HEARTWOOD_HOE.get());
        thornsSmithing(
                ItemRegistry.HEARTWOOD_PICKAXE.get(),
                RecipeCategory.TOOLS,
                ItemRegistry.THORNY_HEARTWOOD_PICKAXE.get()
        );
        thornsSmithing(
                ItemRegistry.HEARTWOOD_SHOVEL.get(),
                RecipeCategory.TOOLS,
                ItemRegistry.THORNY_HEARTWOOD_SHOVEL.get()
        );
        thornsSmithing(
                ItemRegistry.HEARTWOOD_SWORD.get(),
                RecipeCategory.COMBAT,
                ItemRegistry.THORNY_HEARTWOOD_SWORD.get()
        );
        thornsSmithing(
                ItemRegistry.HEARTWOOD_HELMET.get(),
                RecipeCategory.COMBAT,
                ItemRegistry.THORNY_HEARTWOOD_HELMET.get()
        );
        thornsSmithing(
                ItemRegistry.HEARTWOOD_CHESTPLATE.get(),
                RecipeCategory.COMBAT,
                ItemRegistry.THORNY_HEARTWOOD_CHESTPLATE.get()
        );
        thornsSmithing(
                ItemRegistry.HEARTWOOD_LEGGINGS.get(),
                RecipeCategory.COMBAT,
                ItemRegistry.THORNY_HEARTWOOD_LEGGINGS.get()
        );
        thornsSmithing(
                ItemRegistry.HEARTWOOD_BOOTS.get(),
                RecipeCategory.COMBAT,
                ItemRegistry.THORNY_HEARTWOOD_BOOTS.get()
        );

        // Thorns smithing template.
        shaped(
                List.of("#S#", "#L#", "###"),
                List.of('#', 'S', 'L'),
                List.of(WoodSets.HEARTWOOD.getLogItems(), Items.GOLD_INGOT, WoodSets.STRANGLER.getLogItems()),
                RecipeCategory.COMBAT,
                ItemRegistry.THORNS_UPGRADE_SMITHING_TEMPLATE.get(),
                1
        );

        // TODO End of recipe definitions
    }

    protected void thornsSmithing(Item ingredientItem, RecipeCategory category, Item resultItem) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ItemRegistry.THORNS_UPGRADE_SMITHING_TEMPLATE.get()),
                        Ingredient.of(ingredientItem),
                        this.tag(VerdantTags.Items.THORNY_HEARTWOOD_TOOL_MATERIALS),
                        category,
                        resultItem
                )
                .unlocks("has_imbuement_item", this.has(VerdantTags.Items.IMBUED_HEARTWOOD_TOOL_MATERIALS))
                .save(this.output, getItemName(resultItem) + "_smithing");
    }

    protected void imbuementSmithing(Item ingredientItem, RecipeCategory category, Item resultItem) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ItemRegistry.IMBUEMENT_UPGRADE_SMITHING_TEMPLATE.get()),
                        Ingredient.of(ingredientItem),
                        this.tag(VerdantTags.Items.IMBUED_HEARTWOOD_TOOL_MATERIALS),
                        category,
                        resultItem
                )
                .unlocks("has_imbuement_item", this.has(VerdantTags.Items.IMBUED_HEARTWOOD_TOOL_MATERIALS))
                .save(this.output, getItemName(resultItem) + "_smithing");
    }

    protected void shaped(List<String> pattern, List<Character> tokens, List<Object> ingredients, RecipeCategory recipeCategory, ItemLike result, int count) {
        shaped(pattern, tokens, ingredients, recipeCategory, result, count, null);
    }

    @SuppressWarnings({"unchecked"})
    protected void shaped(List<String> pattern, List<Character> tokens, List<Object> ingredients, RecipeCategory recipeCategory, ItemLike result, int count, String group) {

        ShapedRecipeBuilder recipe = shaped(recipeCategory, result, count);
        StringBuilder recipeName = new StringBuilder(this.modid + ":" + getItemName(result) + "_from");

        // Adds in the pattern.
        for (String row : pattern) {
            recipe = recipe.pattern(row);
        }

        // Check if the ingredients match the tokens.
        if (tokens.size() != ingredients.size()) {
            throw new IllegalArgumentException("Token count does not match ingredient count.");
        }

        // Defines the tokens.
        for (int i = 0; i < tokens.size(); i++) {
            if (ingredients.get(i) instanceof ItemLike) {
                recipeName.append("_").append(getItemName((ItemLike) ingredients.get(i)));
                recipe = recipe.define(tokens.get(i), (ItemLike) ingredients.get(i));
            } else if (ingredients.get(i) instanceof TagKey<?> tag) {
                Ingredient ingredient = Ingredient.of(this.registries.lookupOrThrow(Registries.ITEM)
                        .getOrThrow((TagKey<Item>) tag));
                recipeName.append("_tag_").append(tag.location().toDebugFileName());
                recipe = recipe.define(tokens.get(i), ingredient);
            } else {
                throw new IllegalArgumentException("Unrecognized item or tag type: " + ingredients.get(i));
            }
        }

        // Adds in the unlock trigger for the ingredients.
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i) instanceof ItemLike) {
                String name = getHasName((ItemLike) ingredients.get(i));
                recipe = recipe.unlockedBy(name, has((ItemLike) ingredients.get(i)));
            } else if (ingredients.get(i) instanceof TagKey) {
                String name = "has_" + ((TagKey<Item>) ingredients.get(i)).registry().registry().toDebugFileName();
                recipe = recipe.unlockedBy(name, has((TagKey<Item>) ingredients.get(i)));
            } else {
                throw new IllegalArgumentException("Unrecognized item or tag type: " + ingredients.get(i));
            }
        }
        // Adds in the unlock trigger for the result.
        recipe = recipe.unlockedBy(getHasName(result), has(result));

        recipe.group(group == null ? group(result) : group);

        // Saves the recipe.
        recipe.save(this.output, recipeName.toString());
    }

    @SuppressWarnings({"unchecked"})
    protected void stonecutting(Object ingredient, RecipeCategory recipeCategory, ItemLike result, int count) {

        // The name of the recipe.
        String recipeName = this.modid + ":" + getItemName(result) + "_from_stonecutting_";

        Ingredient toAdd = null;

        // Determines what type of ingredient is there.
        if (ingredient instanceof ItemLike item) {
            toAdd = Ingredient.of(item);
            recipeName += getItemName(item);

        } else if (ingredient instanceof TagKey<?> tag) {
            toAdd = Ingredient.of(this.registries.lookupOrThrow(Registries.ITEM).getOrThrow((TagKey<Item>) tag));
            recipeName += tag.location().toDebugFileName();

        } else {
            throw new IllegalArgumentException("Cannot create a stonecutting recipe with a non-Item, non-Tag object.");
        }

        // Creates the recipe.
        SingleItemRecipeBuilder recipe = SingleItemRecipeBuilder.stonecutting(toAdd, recipeCategory, result, count);

        // Adds in the unlock trigger for the ingredient.
        if (ingredient instanceof ItemLike) {
            recipe = recipe.unlockedBy(getHasName((ItemLike) ingredient), has((ItemLike) ingredient));
        } else if (ingredient instanceof TagKey) {
            String name = "has" + ((TagKey<Item>) ingredient).registry().registry().toDebugFileName();
            recipe = recipe.unlockedBy(name, has((TagKey<Item>) ingredient));
        } else {
            throw new IllegalArgumentException("Unrecognized item or tag type: " + ingredient);
        }

        // Adds in the unlock trigger for the result.
        recipe = recipe.unlockedBy(getHasName(result), has(result));

        recipe.group(group(result));

        // Prints out the result.
        recipe.save(this.output, recipeName);
    }

    protected void shapeless(List<Object> ingredients, List<Integer> counts, RecipeCategory recipeCategory, ItemLike result, int count) {
        shapeless(ingredients, counts, recipeCategory, result, count, null);
    }

    // Shapeless recipe. Item at n must correspond to item count at n.
    @SuppressWarnings({"unchecked"})
    protected void shapeless(List<Object> ingredients, List<Integer> counts, RecipeCategory recipeCategory, ItemLike result, int count, String group) {

        ShapelessRecipeBuilder recipe = shapeless(recipeCategory, result, count);
        StringBuilder recipeName = new StringBuilder(group(result) + "_from");

        // Check if the ingredients match the count length.
        if (counts.size() != ingredients.size()) {
            throw new IllegalArgumentException("Token count does not match ingredient count.");
        }

        // Adds in the ingredients.
        for (int i = 0; i < counts.size(); i++) {
            if (ingredients.get(i) instanceof ItemLike) {
                recipeName.append("_").append(getItemName((ItemLike) ingredients.get(i)));
                recipe = recipe.requires((ItemLike) ingredients.get(i), counts.get(i));
            } else if (ingredients.get(i) instanceof TagKey<?> tag) {
                recipeName.append("_tag_").append(tag.location().toDebugFileName());
                Ingredient ingredient = Ingredient.of(this.registries.lookupOrThrow(Registries.ITEM)
                        .getOrThrow((TagKey<Item>) tag));
                recipe = recipe.requires(ingredient, counts.get(i));
            } else {
                throw new IllegalArgumentException("Unrecognized item or tag type: " + ingredients.get(i));
            }
        }

        // Adds in the unlock triggers for the ingredients.
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i) instanceof ItemLike) {
                recipe = recipe.unlockedBy(
                        getHasName((ItemLike) ingredients.get(i)),
                        has((ItemLike) ingredients.get(i))
                );
            } else if (ingredients.get(i) instanceof TagKey) {
                String name = "has" + ((TagKey<Item>) ingredients.get(i)).registry().registry().toDebugFileName();
                recipe = recipe.unlockedBy(name, has((TagKey<Item>) ingredients.get(i)));
            } else {
                throw new IllegalArgumentException("Unrecognized item or tag type: " + ingredients.get(i));
            }
        }
        // Adds in the unlock trigger for the result.
        recipe = recipe.unlockedBy(getHasName(result), has(result));

        recipe.group(group == null ? group(result) : group);

        // Saves the recipe.
        recipe.save(this.output, recipeName.toString());

    }

    protected void foodCooking(List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime) {
        String group = (namespace(result) + ":" + getItemName(result));
        campfire(ingredients, category, result, experience, 2 * cookingTime, group);
        smelting(ingredients, category, result, experience, cookingTime, group);
        smoking(ingredients, category, result, experience, cookingTime / 2, group);
    }

    protected String hasName(ItemLike item) {
        return "has_" + name(item);
    }

    protected void shapeless(RecipeCategory recipeCategory, ItemLike input, int inCount, ItemLike output, int count) {
        shapeless(recipeCategory, input, inCount, output, count, null);
    }

    protected void shapeless(RecipeCategory recipeCategory, ItemLike input, int inCount, ItemLike output, int count, String group) {
        shapeless(List.of(input), List.of(inCount), recipeCategory, output, count, group);
    }

    protected String group(ItemLike item) {
        return identifier(item).toString();
    }

    protected String name(ItemLike item) {
        return identifier(item).getPath();
    }

    protected String namespace(ItemLike item) {
        return identifier(item).getNamespace();
    }

    @SuppressWarnings("deprecation")
    protected ResourceLocation identifier(ItemLike item) {
        return item.asItem().builtInRegistryHolder().key().location();
    }

    protected void campfire(List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        cooking(
                RecipeSerializer.CAMPFIRE_COOKING_RECIPE,
                CampfireCookingRecipe::new,
                ingredients,
                category,
                result,
                experience,
                cookingTime * 3,
                group,
                "_from_campfire"
        );
    }

    protected void smoking(List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        cooking(
                RecipeSerializer.SMOKING_RECIPE,
                SmokingRecipe::new,
                ingredients,
                category,
                result,
                experience,
                cookingTime / 2,
                group,
                "_from_smoking"
        );
    }

    protected void smelting(List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        cooking(
                RecipeSerializer.SMELTING_RECIPE,
                SmeltingRecipe::new,
                ingredients,
                category,
                result,
                experience,
                cookingTime,
                group,
                "_from_smelting"
        );
    }

    protected void blasting(List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        cooking(
                RecipeSerializer.BLASTING_RECIPE,
                BlastingRecipe::new,
                ingredients,
                category,
                result,
                experience,
                cookingTime / 2,
                group,
                "_from_blasting"
        );
    }

    protected <T extends AbstractCookingRecipe> void cooking(RecipeSerializer<T> cookingSerializer, AbstractCookingRecipe.Factory<T> factory, List<ItemLike> ingredients, RecipeCategory category, ItemLike pResult, float experience, int cookingTime, String group, String recipeName) {
        for (ItemLike itemlike : ingredients) {
            SimpleCookingRecipeBuilder.generic(
                            Ingredient.of(itemlike),
                            category,
                            pResult,
                            experience,
                            cookingTime,
                            cookingSerializer,
                            factory
                    )
                    .group(group)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(
                            this.output,
                            this.modid + ":" + getItemName(pResult) + recipeName + "_" + getItemName(itemlike)
                    );
        }
    }

    // The runner to add to the data generator
    public static class Runner extends RecipeProvider.Runner {
        // Get the parameters from GatherDataEvent.
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new VerdantRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "Verdant Recipe Provider";
        }
    }

}