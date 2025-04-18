/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant.registry;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.VerdantIFF;
import com.startraveler.verdant.block.custom.InfestedRotatedPillarBlock;
import com.startraveler.verdant.block.custom.*;
import com.startraveler.verdant.block.custom.extensible.ExtensibleCakeBlock;
import com.startraveler.verdant.block.custom.extensible.ExtensibleCandleCakeBlock;
import com.startraveler.verdant.block.loot.LootLocations;
import com.startraveler.verdant.registration.RegistrationProvider;
import com.startraveler.verdant.registration.RegistryObject;
import com.startraveler.verdant.registry.properties.BlockProperties;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;


public class BlockRegistry {
    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(
            Registries.BLOCK,
            Constants.MOD_ID
    );

    public static final RegistryObject<Block, Block> VERDANT_ROOTED_DIRT;
    public static final RegistryObject<Block, Block> VERDANT_GRASS_DIRT;
    public static final RegistryObject<Block, Block> VERDANT_ROOTED_MUD;
    public static final RegistryObject<Block, Block> VERDANT_GRASS_MUD;
    public static final RegistryObject<Block, Block> VERDANT_ROOTED_CLAY;
    public static final RegistryObject<Block, Block> VERDANT_GRASS_CLAY;
    public static final RegistryObject<Block, Block> VERDANT_ROOTED_GRUS;
    public static final RegistryObject<Block, Block> VERDANT_GRASS_GRUS;
    public static final RegistryObject<Block, Block> PACKED_GRAVEL;
    public static final RegistryObject<Block, Block> FUSED_GRAVEL;
    public static final RegistryObject<Block, Block> DIRT_COAL_ORE;
    public static final RegistryObject<Block, Block> DIRT_COPPER_ORE;
    public static final RegistryObject<Block, Block> DIRT_IRON_ORE;
    public static final RegistryObject<Block, Block> DIRT_GOLD_ORE;
    public static final RegistryObject<Block, Block> DIRT_LAPIS_ORE;
    public static final RegistryObject<Block, Block> DIRT_REDSTONE_ORE;
    public static final RegistryObject<Block, Block> DIRT_EMERALD_ORE;
    public static final RegistryObject<Block, Block> DIRT_DIAMOND_ORE;
    public static final RegistryObject<Block, Block> GRUS_COAL_ORE;
    public static final RegistryObject<Block, Block> GRUS_COPPER_ORE;
    public static final RegistryObject<Block, Block> GRUS_IRON_ORE;
    public static final RegistryObject<Block, Block> GRUS_GOLD_ORE;
    public static final RegistryObject<Block, Block> GRUS_LAPIS_ORE;
    public static final RegistryObject<Block, Block> GRUS_REDSTONE_ORE;
    public static final RegistryObject<Block, Block> GRUS_EMERALD_ORE;
    public static final RegistryObject<Block, Block> GRUS_DIAMOND_ORE;
    public static final RegistryObject<Block, Block> STRANGLER_VINE;
    public static final RegistryObject<Block, Block> LEAFY_STRANGLER_VINE;
    public static final RegistryObject<Block, Block> STRANGLER_LEAVES;
    public static final RegistryObject<Block, Block> POISON_STRANGLER_LEAVES;
    public static final RegistryObject<Block, Block> THORNY_STRANGLER_LEAVES;
    public static final RegistryObject<Block, Block> WILTED_STRANGLER_LEAVES;
    public static final RegistryObject<Block, Block> ROTTEN_WOOD;
    public static final RegistryObject<Block, Block> STRANGLER_TENDRIL;
    public static final RegistryObject<Block, Block> STRANGLER_TENDRIL_PLANT;
    public static final RegistryObject<Block, Block> POISON_IVY;
    public static final RegistryObject<Block, Block> POISON_IVY_PLANT;
    public static final RegistryObject<Block, Block> FISH_TRAP;
    public static final RegistryObject<Block, Block> ANTIGORITE;
    public static final RegistryObject<Block, Block> ROPE;
    public static final RegistryObject<Block, Block> ROPE_HOOK;
    public static final RegistryObject<Block, Block> STINKING_BLOSSOM;
    public static final RegistryObject<Block, Block> BUSH;
    public static final RegistryObject<Block, Block> POTTED_BUSH;
    public static final RegistryObject<Block, Block> THORN_BUSH;
    public static final RegistryObject<Block, Block> POTTED_THORN_BUSH;
    public static final RegistryObject<Block, Block> WILD_COFFEE;
    public static final RegistryObject<Block, Block> POTTED_COFFEE_CROP;
    public static final RegistryObject<Block, Block> POTTED_WILD_COFFEE;
    public static final RegistryObject<Block, Block> COFFEE_CROP;
    public static final RegistryObject<Block, Block> BLEEDING_HEART;
    public static final RegistryObject<Block, Block> POTTED_BLEEDING_HEART;
    public static final RegistryObject<Block, Block> TIGER_LILY;
    public static final RegistryObject<Block, Block> POTTED_TIGER_LILY;
    public static final RegistryObject<Block, Block> DROWNED_HEMLOCK;
    public static final RegistryObject<Block, Block> DROWNED_HEMLOCK_PLANT;
    public static final RegistryObject<Block, Block> WOODEN_SPIKES;
    public static final RegistryObject<Block, Block> IRON_SPIKES;
    public static final RegistryObject<Block, Block> WOODEN_TRAP;
    public static final RegistryObject<Block, Block> IRON_TRAP;
    public static final RegistryObject<Block, Block> SNAPLEAF;
    public static final RegistryObject<Block, Block> FRAME_BLOCK;
    public static final RegistryObject<Block, Block> CHARRED_FRAME_BLOCK;
    public static final RegistryObject<Block, Block> VERDANT_CONDUIT;
    public static final RegistryObject<Block, Block> IMBUED_HEARTWOOD_LOG;
    public static final RegistryObject<Block, Block> CASSAVA_ROOTED_DIRT;
    public static final RegistryObject<Block, Block> BITTER_CASSAVA_ROOTED_DIRT;
    public static final RegistryObject<Block, Block> CASSAVA_CROP;
    public static final RegistryObject<Block, Block> BITTER_CASSAVA_CROP;
    public static final RegistryObject<Block, Block> WILD_CASSAVA;
    public static final RegistryObject<Block, Block> POTTED_WILD_CASSAVA;
    public static final RegistryObject<Block, Block> UBE_CAKE;
    public static final RegistryObject<Block, Block> CANDLE_UBE_CAKE;
    public static final RegistryObject<Block, Block> WHITE_CANDLE_UBE_CAKE;
    public static final RegistryObject<Block, Block> ORANGE_CANDLE_UBE_CAKE;
    public static final RegistryObject<Block, Block> MAGENTA_CANDLE_UBE_CAKE;
    public static final RegistryObject<Block, Block> LIGHT_BLUE_CANDLE_UBE_CAKE;
    public static final RegistryObject<Block, Block> YELLOW_CANDLE_UBE_CAKE;
    public static final RegistryObject<Block, Block> LIME_CANDLE_UBE_CAKE;
    public static final RegistryObject<Block, Block> PINK_CANDLE_UBE_CAKE;
    public static final RegistryObject<Block, Block> GRAY_CANDLE_UBE_CAKE;
    public static final RegistryObject<Block, Block> LIGHT_GRAY_CANDLE_UBE_CAKE;
    public static final RegistryObject<Block, Block> CYAN_CANDLE_UBE_CAKE;
    public static final RegistryObject<Block, Block> PURPLE_CANDLE_UBE_CAKE;
    public static final RegistryObject<Block, Block> BLUE_CANDLE_UBE_CAKE;
    public static final RegistryObject<Block, Block> BROWN_CANDLE_UBE_CAKE;
    public static final RegistryObject<Block, Block> GREEN_CANDLE_UBE_CAKE;
    public static final RegistryObject<Block, Block> RED_CANDLE_UBE_CAKE;
    public static final RegistryObject<Block, Block> BLACK_CANDLE_UBE_CAKE;
    public static final RegistryObject<Block, Block> UBE_CROP;
    public static final RegistryObject<Block, Block> WILD_UBE;
    public static final RegistryObject<Block, Block> POTTED_WILD_UBE;
    public static final RegistryObject<Block, Block> TOXIC_DIRT;
    public static final RegistryObject<Block, Block> DEAD_GRASS;
    public static final RegistryObject<Block, Block> POISON_IVY_BLOCK;
    public static final RegistryObject<Block, Block> TOXIC_ASH_BLOCK;
    public static final RegistryObject<Block, Block> PUTRID_FERTILIZER;
    public static final RegistryObject<Block, Block> RUE;
    public static final RegistryObject<Block, Block> POTTED_RUE;
    public static final RegistryObject<Block, Block> PAPER_FRAME;
    public static final RegistryObject<Block, Block> ROPE_LADDER;
    public static final RegistryObject<Block, Block> SMALL_ALOE;
    public static final RegistryObject<Block, Block> LARGE_ALOE;
    public static final RegistryObject<Block, Block> HUGE_ALOE;
    public static final RegistryObject<Block, Block> SCREE;
    public static final RegistryObject<Block, Block> PACKED_SCREE;
    public static final RegistryObject<Block, Block> FUSED_SCREE;
    public static final RegistryObject<Block, Block> GRUS;
    public static final RegistryObject<Block, Block> STONY_GRUS;
    public static final RegistryObject<Block, Block> BLASTING_BLOSSOM;
    public static final RegistryObject<Block, Block> BLASTING_BUNCH;
    public static final RegistryObject<Block, Block> EARTH_BRICKS;
    public static final RegistryObject<Block, Block> EARTH_BRICK_STAIRS;
    public static final RegistryObject<Block, Block> EARTH_BRICK_SLAB;
    public static final RegistryObject<Block, Block> EARTH_BRICK_WALL;
    public static final RegistryObject<Block, Block> TOXIC_GRUS;
    public static final RegistryObject<Block, Block> BLUEWEED;
    public static final RegistryObject<Block, Block> POTTED_BLUEWEED;
    public static final RegistryObject<Block, Block> TALL_BUSH;
    public static final RegistryObject<Block, Block> TALL_THORN_BUSH;
    // public static final RegistryObject<Block, Block> ROPE_LADDER;

    static {
        VERDANT_ROOTED_DIRT = registerBlockWithItem(
                "verdant_rooted_dirt", () -> new SpreadingRootsBlock(
                        BlockProperties.VERDANT_ROOTS.setId(id("verdant_rooted_dirt")),
                        false,
                        () -> BlockRegistry.VERDANT_GRASS_DIRT,
                        false,
                        () -> BlockRegistry.VERDANT_ROOTED_MUD
                )
        );
        VERDANT_GRASS_DIRT = registerBlockWithItem(
                "verdant_grass_dirt", () -> new SpreadingRootsBlock(
                        BlockProperties.VERDANT_GRASS.setId(id("verdant_grass_dirt")),
                        true,
                        () -> BlockRegistry.VERDANT_ROOTED_DIRT,
                        false,
                        () -> BlockRegistry.VERDANT_GRASS_MUD
                )
        );
        VERDANT_ROOTED_MUD = registerBlockWithItem(
                "verdant_rooted_mud", () -> new SpreadingRootsBlock(
                        BlockProperties.VERDANT_ROOTS.setId(id("verdant_rooted_mud")),
                        false,
                        () -> BlockRegistry.VERDANT_GRASS_MUD,
                        true,
                        () -> BlockRegistry.VERDANT_ROOTED_DIRT
                )
        );
        VERDANT_GRASS_MUD = registerBlockWithItem(
                "verdant_grass_mud", () -> new SpreadingRootsBlock(
                        BlockProperties.VERDANT_GRASS.setId(id("verdant_grass_mud")),
                        true,
                        () -> BlockRegistry.VERDANT_ROOTED_MUD,
                        true,
                        () -> BlockRegistry.VERDANT_GRASS_MUD
                )
        );
        VERDANT_ROOTED_CLAY = registerBlockWithItem(
                "verdant_rooted_clay",
                () -> new SpreadingRootsBlock(
                        BlockProperties.VERDANT_ROOTS.setId(id("verdant_rooted_clay")),
                        false,
                        () -> BlockRegistry.VERDANT_GRASS_CLAY
                )
        );

        VERDANT_GRASS_CLAY = registerBlockWithItem(
                "verdant_grass_clay",
                () -> new SpreadingRootsBlock(
                        BlockProperties.VERDANT_GRASS.setId(id("verdant_grass_clay")),
                        true,
                        () -> BlockRegistry.VERDANT_ROOTED_CLAY
                )
        );
        VERDANT_ROOTED_GRUS = registerBlockWithItem(
                "verdant_rooted_grus", () -> new SpreadingRootsBlock(
                        BlockProperties.VERDANT_ROOTS.setId(id("verdant_rooted_grus")),
                        false,
                        () -> BlockRegistry.VERDANT_GRASS_GRUS,
                        false,
                        () -> BlockRegistry.VERDANT_ROOTED_MUD
                )
        );
        VERDANT_GRASS_GRUS = registerBlockWithItem(
                "verdant_grass_grus", () -> new SpreadingRootsBlock(
                        BlockProperties.VERDANT_GRASS.setId(id("verdant_grass_grus")),
                        true,
                        () -> BlockRegistry.VERDANT_ROOTED_GRUS,
                        false,
                        () -> BlockRegistry.VERDANT_GRASS_MUD
                )
        );
        PACKED_GRAVEL = registerBlockWithItem(
                "packed_gravel",
                () -> new Block(properties(Blocks.GRAVEL, "packed_gravel").strength(1.0F)
                        .pushReaction(PushReaction.DESTROY))
        );
        FUSED_GRAVEL = registerBlockWithItem(
                "fused_gravel",
                () -> new Block(properties(Blocks.GRAVEL, "fused_gravel").strength(1.0F))
        );
        DIRT_COAL_ORE = registerBlockWithItem(
                "dirt_coal_ore", () -> new HoeRemovableItemBlock(
                        properties(Blocks.COARSE_DIRT, "dirt_coal_ore"),
                        ResourceKey.create(Registries.LOOT_TABLE, LootLocations.DIRT_COAL_ORE_POP),
                        (context) -> Blocks.DIRT.defaultBlockState(),
                        (r) -> r.nextIntBetweenInclusive(1, 2)
                )
        );
        DIRT_COPPER_ORE = registerBlockWithItem(
                "dirt_copper_ore", () -> new HoeRemovableItemBlock(
                        properties(Blocks.COARSE_DIRT, "dirt_copper_ore"),
                        ResourceKey.create(Registries.LOOT_TABLE, LootLocations.DIRT_COPPER_ORE_POP),
                        (context) -> Blocks.DIRT.defaultBlockState(),
                        (r) -> r.nextIntBetweenInclusive(1, 2)
                )
        );
        DIRT_IRON_ORE = registerBlockWithItem(
                "dirt_iron_ore", () -> new HoeRemovableItemBlock(
                        properties(Blocks.COARSE_DIRT, "dirt_iron_ore"),
                        ResourceKey.create(Registries.LOOT_TABLE, LootLocations.DIRT_IRON_ORE_POP),
                        (context) -> Blocks.DIRT.defaultBlockState(),
                        (r) -> r.nextIntBetweenInclusive(1, 2)
                )
        );
        DIRT_GOLD_ORE = registerBlockWithItem(
                "dirt_gold_ore", () -> new HoeRemovableItemBlock(
                        properties(Blocks.COARSE_DIRT, "dirt_gold_ore"),
                        ResourceKey.create(Registries.LOOT_TABLE, LootLocations.DIRT_GOLD_ORE_POP),
                        (context) -> Blocks.DIRT.defaultBlockState(),
                        (r) -> r.nextIntBetweenInclusive(1, 2)
                )
        );
        DIRT_LAPIS_ORE = registerBlockWithItem(
                "dirt_lapis_ore", () -> new HoeRemovableItemBlock(
                        properties(Blocks.COARSE_DIRT, "dirt_lapis_ore"),
                        ResourceKey.create(Registries.LOOT_TABLE, LootLocations.DIRT_LAPIS_ORE_POP),
                        (context) -> Blocks.DIRT.defaultBlockState(),
                        (r) -> r.nextIntBetweenInclusive(1, 2)
                )
        );
        DIRT_REDSTONE_ORE = registerBlockWithItem(
                "dirt_redstone_ore", () -> new HoeRemovableItemBlock(
                        properties(Blocks.COARSE_DIRT, "dirt_redstone_ore").lightLevel((state) -> 2),
                        ResourceKey.create(Registries.LOOT_TABLE, LootLocations.DIRT_REDSTONE_ORE_POP),
                        (context) -> Blocks.DIRT.defaultBlockState(),
                        (r) -> r.nextIntBetweenInclusive(1, 2)
                )
        );
        DIRT_EMERALD_ORE = registerBlockWithItem(
                "dirt_emerald_ore", () -> new HoeRemovableItemBlock(
                        properties(Blocks.COARSE_DIRT, "dirt_emerald_ore"),
                        ResourceKey.create(Registries.LOOT_TABLE, LootLocations.DIRT_EMERALD_ORE_POP),
                        (context) -> Blocks.DIRT.defaultBlockState(),
                        (r) -> r.nextIntBetweenInclusive(1, 2)
                )
        );
        DIRT_DIAMOND_ORE = registerBlockWithItem(
                "dirt_diamond_ore", () -> new HoeRemovableItemBlock(
                        properties(Blocks.COARSE_DIRT, "dirt_diamond_ore"),
                        ResourceKey.create(Registries.LOOT_TABLE, LootLocations.DIRT_DIAMOND_ORE_POP),
                        (context) -> Blocks.DIRT.defaultBlockState(),
                        (r) -> r.nextIntBetweenInclusive(1, 2)
                )
        );
        GRUS_COAL_ORE = registerBlockWithItem(
                "grus_coal_ore", () -> new HoeRemovableItemBlock(
                        properties(Blocks.COARSE_DIRT, "grus_coal_ore"),
                        ResourceKey.create(Registries.LOOT_TABLE, LootLocations.DIRT_COAL_ORE_POP),
                        (context) -> BlockRegistry.GRUS.get().defaultBlockState(),
                        (r) -> r.nextIntBetweenInclusive(1, 2)
                )
        );
        GRUS_COPPER_ORE = registerBlockWithItem(
                "grus_copper_ore", () -> new HoeRemovableItemBlock(
                        properties(Blocks.COARSE_DIRT, "grus_copper_ore"),
                        ResourceKey.create(Registries.LOOT_TABLE, LootLocations.DIRT_COPPER_ORE_POP),
                        (context) -> BlockRegistry.GRUS.get().defaultBlockState(),
                        (r) -> r.nextIntBetweenInclusive(1, 2)
                )
        );
        GRUS_IRON_ORE = registerBlockWithItem(
                "grus_iron_ore", () -> new HoeRemovableItemBlock(
                        properties(Blocks.COARSE_DIRT, "grus_iron_ore"),
                        ResourceKey.create(Registries.LOOT_TABLE, LootLocations.DIRT_IRON_ORE_POP),
                        (context) -> BlockRegistry.GRUS.get().defaultBlockState(),
                        (r) -> r.nextIntBetweenInclusive(1, 2)
                )
        );
        GRUS_GOLD_ORE = registerBlockWithItem(
                "grus_gold_ore", () -> new HoeRemovableItemBlock(
                        properties(Blocks.COARSE_DIRT, "grus_gold_ore"),
                        ResourceKey.create(Registries.LOOT_TABLE, LootLocations.DIRT_GOLD_ORE_POP),
                        (context) -> BlockRegistry.GRUS.get().defaultBlockState(),
                        (r) -> r.nextIntBetweenInclusive(1, 2)
                )
        );
        GRUS_LAPIS_ORE = registerBlockWithItem(
                "grus_lapis_ore", () -> new HoeRemovableItemBlock(
                        properties(Blocks.COARSE_DIRT, "grus_lapis_ore"),
                        ResourceKey.create(Registries.LOOT_TABLE, LootLocations.DIRT_LAPIS_ORE_POP),
                        (context) -> BlockRegistry.GRUS.get().defaultBlockState(),
                        (r) -> r.nextIntBetweenInclusive(1, 2)
                )
        );
        GRUS_REDSTONE_ORE = registerBlockWithItem(
                "grus_redstone_ore", () -> new HoeRemovableItemBlock(
                        properties(Blocks.COARSE_DIRT, "grus_redstone_ore").lightLevel((state) -> 2),
                        ResourceKey.create(Registries.LOOT_TABLE, LootLocations.DIRT_REDSTONE_ORE_POP),
                        (context) -> BlockRegistry.GRUS.get().defaultBlockState(),
                        (r) -> r.nextIntBetweenInclusive(1, 2)
                )
        );
        GRUS_EMERALD_ORE = registerBlockWithItem(
                "grus_emerald_ore", () -> new HoeRemovableItemBlock(
                        properties(Blocks.COARSE_DIRT, "grus_emerald_ore"),
                        ResourceKey.create(Registries.LOOT_TABLE, LootLocations.DIRT_EMERALD_ORE_POP),
                        (context) -> BlockRegistry.GRUS.get().defaultBlockState(),
                        (r) -> r.nextIntBetweenInclusive(1, 2)
                )
        );
        GRUS_DIAMOND_ORE = registerBlockWithItem(
                "grus_diamond_ore", () -> new HoeRemovableItemBlock(
                        properties(Blocks.COARSE_DIRT, "grus_diamond_ore"),
                        ResourceKey.create(Registries.LOOT_TABLE, LootLocations.DIRT_DIAMOND_ORE_POP),
                        (context) -> BlockRegistry.GRUS.get().defaultBlockState(),
                        (r) -> r.nextIntBetweenInclusive(1, 2)
                )
        );
        STRANGLER_VINE = registerBlockWithItem(
                "strangler_vine",
                () -> new StranglerVineBlock(properties(Blocks.BIRCH_PLANKS, "strangler_vine").strength(1.0f, 1.5f)
                        .noOcclusion()
                        .randomTicks())
        );
        LEAFY_STRANGLER_VINE = registerBlockWithItem(
                "leafy_strangler_vine",
                () -> new LeafyStranglerVineBlock(properties(Blocks.BIRCH_PLANKS, "leafy_strangler_vine").strength(
                        1.0f,
                        1.5f
                ).randomTicks())
        );
        STRANGLER_LEAVES = registerBlockWithItem(
                "strangler_leaves",
                () -> new StranglerLeavesBlock(properties(Blocks.ACACIA_LEAVES, "strangler_leaves").randomTicks())
        );
        WILTED_STRANGLER_LEAVES = registerBlockWithItem(
                "wilted_strangler_leaves",
                () -> new LeavesBlock(properties(Blocks.ACACIA_LEAVES, "wilted_strangler_leaves").randomTicks())
        );
        POISON_STRANGLER_LEAVES = registerBlockWithItem(
                "poison_strangler_leaves",
                () -> new PoisonStranglerLeavesBlock(properties(
                        Blocks.ACACIA_LEAVES,
                        "poison_strangler_leaves"
                ).noOcclusion().randomTicks())
        );
        THORNY_STRANGLER_LEAVES = registerBlockWithItem(
                "thorny_strangler_leaves",
                () -> new ThornyStranglerLeavesBlock(properties(
                        Blocks.ACACIA_LEAVES,
                        "thorny_strangler_leaves"
                ).noOcclusion().randomTicks())
        );
        ROTTEN_WOOD = registerBlockWithItem(
                "rotten_wood",
                () -> new FragileBlock(properties(Blocks.DARK_OAK_PLANKS, "rotten_wood").instabreak()
                        .ignitedByLava()
                        .noOcclusion()
                        .isRedstoneConductor((s, l, p) -> false)
                        .mapColor(MapColor.TERRACOTTA_BROWN)
                        .strength(0, 0)
                        .randomTicks())
        );
        STRANGLER_TENDRIL = registerBlockWithItem(
                "strangler_tendril",
                () -> new StranglerTendrilBlock(properties(Blocks.WEEPING_VINES, "strangler_tendril").offsetType(
                        BlockBehaviour.OffsetType.XZ).noOcclusion().randomTicks())
        );
        STRANGLER_TENDRIL_PLANT = registerBlockWithoutItem(
                "strangler_tendril_plant",
                () -> new StranglerTendrilPlantBlock(properties(
                        Blocks.WEEPING_VINES_PLANT,
                        "strangler_tendril_plant"
                ).offsetType(BlockBehaviour.OffsetType.XZ)
                        .noOcclusion()
                        .randomTicks())
        );
        POISON_IVY = registerBlockWithItem(
                "poison_ivy",
                () -> new PoisonIvyBlock(properties(
                        Blocks.WEEPING_VINES,
                        "poison_ivy"
                ).offsetType(BlockBehaviour.OffsetType.XZ)
                        .noOcclusion()
                        .randomTicks())
        );
        POISON_IVY_PLANT = registerBlockWithoutItem(
                "poison_ivy_plant",
                () -> new PoisonIvyPlantBlock(properties(Blocks.WEEPING_VINES_PLANT, "poison_ivy_plant").offsetType(
                        BlockBehaviour.OffsetType.XZ).noOcclusion().randomTicks())
        );
        FISH_TRAP = registerBlockWithItem(
                "fish_trap",
                () -> new FishTrapBlock(properties(Blocks.OAK_PLANKS, "fish_trap").noOcclusion()
                        .isViewBlocking((s, l, p) -> false))
        );
        ANTIGORITE = registerBlockWithItem("antigorite", () -> new Block(properties(Blocks.STONE, "antigorite")));
        ROPE = registerBlockWithoutItem(
                "rope", () -> new RopeBlock(properties(Blocks.KELP, "rope").sound(SoundType.WOOL)

                        .lightLevel((state) -> 2 * state.getValue(RopeBlock.GLOW_LEVEL)))
        );
        ROPE_HOOK = registerBlockWithoutItem(
                "rope_hook",
                () -> new RopeHookBlock(properties(Blocks.TRIPWIRE_HOOK, "rope_hook"))
        );
        BUSH = registerBlockWithItem(
                "bush",
                () -> new ThornBushBlock(properties(Blocks.SWEET_BERRY_BUSH, "bush").noOcclusion().strength(0.5F), 0.0f)
        );
        POTTED_BUSH = registerBlockWithoutItem(
                "potted_bush",
                () -> new FlowerPotBlock(
                        BlockRegistry.BUSH.get(),
                        properties(Blocks.POTTED_BLUE_ORCHID, "potted_bush").noOcclusion()
                )
        );
        THORN_BUSH = registerBlockWithItem(
                "thorn_bush",
                () -> new ThornBushBlock(
                        properties(Blocks.SWEET_BERRY_BUSH, "thorn_bush").noOcclusion()
                                .strength(0.75F), 2.0f
                )
        );
        POTTED_THORN_BUSH = registerBlockWithoutItem(
                "potted_thorn_bush", () -> new FlowerPotBlock(
                        BlockRegistry.THORN_BUSH.get(),
                        properties(Blocks.POTTED_BLUE_ORCHID, "potted_thorn_bush").noOcclusion()
                )
        );
        STINKING_BLOSSOM = registerBlockWithItem(
                "stinking_blossom",
                () -> new StinkingBlossomBlock(properties(Blocks.SPORE_BLOSSOM, "stinking_blossom"))
        );
        WILD_COFFEE = registerBlockWithItem(
                "wild_coffee", () -> new WildCoffeeBlock(
                        MobEffectRegistry.CAFFEINATED.asHolder(),
                        25,
                        properties(Blocks.BLUE_ORCHID, "wild_coffee").noOcclusion().noCollission()
                )
        );
        POTTED_WILD_COFFEE = registerBlockWithoutItem(
                "potted_wild_coffee", () -> new FlowerPotBlock(
                        BlockRegistry.WILD_COFFEE.get(),
                        properties(Blocks.POTTED_BLUE_ORCHID, "potted_wild_coffee").noOcclusion()
                )
        );
        COFFEE_CROP = registerBlockWithoutItem(
                "coffee_crop",
                () -> new CoffeeCropBlock(properties(Blocks.SWEET_BERRY_BUSH, "coffee_crop"))
        );
        POTTED_COFFEE_CROP = registerBlockWithoutItem(
                "potted_coffee_crop", () -> new FlowerPotBlock(
                        BlockRegistry.COFFEE_CROP.get(),
                        properties(Blocks.POTTED_BLUE_ORCHID, "potted_coffee_crop").noOcclusion()
                )
        );
        BLEEDING_HEART = registerBlockWithItem(
                "bleeding_heart", () -> new FlowerBlock(
                        MobEffectRegistry.FOOD_POISONING.asHolder(),
                        40,
                        properties(Blocks.BLUE_ORCHID, "bleeding_heart")
                )
        );
        POTTED_BLEEDING_HEART = registerBlockWithoutItem(
                "potted_bleeding_heart", () -> new FlowerPotBlock(
                        BlockRegistry.BLEEDING_HEART.get(),
                        properties(Blocks.POTTED_BLUE_ORCHID, "potted_bleeding_heart").noOcclusion()
                )
        );
        TIGER_LILY = registerBlockWithItem(
                "tiger_lily",
                () -> new FlowerBlock(MobEffects.DAMAGE_BOOST, 3.0f, properties(Blocks.BLUE_ORCHID, "tiger_lily"))
        );
        POTTED_TIGER_LILY = registerBlockWithoutItem(
                "potted_tiger_lily", () -> new FlowerPotBlock(
                        BlockRegistry.TIGER_LILY.get(),
                        properties(Blocks.POTTED_BLUE_ORCHID, "potted_tiger_lily").noOcclusion()
                )
        );
        DROWNED_HEMLOCK = registerBlockWithItem(
                "drowned_hemlock",
                () -> new HemlockBlock(properties(Blocks.KELP, "drowned_hemlock"))
        );
        DROWNED_HEMLOCK_PLANT = registerBlockWithoutItem(
                "drowned_hemlock_plant",
                () -> new HemlockPlantBlock(properties(Blocks.KELP_PLANT, "drowned_hemlock_plant"))
        );
        WOODEN_SPIKES = registerBlockWithItem(
                "wooden_spikes",
                () -> new SpikesBlock(properties(Blocks.OAK_BUTTON, "wooden_spikes"), 3)
        );
        IRON_SPIKES = registerBlockWithItem(
                "iron_spikes",
                () -> new SpikesBlock(properties(Blocks.IRON_BARS, "iron_spikes"), 6)
        );

        WOODEN_TRAP = registerBlockWithItem(
                "wooden_trap",
                () -> new TrapBlock(properties(Blocks.OAK_BUTTON, "wooden_trap"), 20, 5, 4)
        );

        IRON_TRAP = registerBlockWithItem(
                "iron_trap",
                () -> new TrapBlock(properties(Blocks.IRON_BARS, "iron_trap").noCollission().noOcclusion(), 10, 2, 8)
        );

        SNAPLEAF = registerBlockWithItem(
                "snapleaf", () -> new TrapBlock(
                        properties(Blocks.OAK_LEAVES, "snapleaf").noCollission().noOcclusion(),
                        15,
                        3,
                        4,
                        VerdantIFF::isEnemy,
                        false,
                        false,
                        true
                )
        );

        FRAME_BLOCK = registerBlockWithItem(
                "frame_block",
                () -> new FrameBlock(properties(
                        Blocks.OAK_FENCE,
                        "frame_block"
                ).isViewBlocking((state, level, pos) -> false)
                        .noOcclusion()
                        .instabreak())
        );

        CHARRED_FRAME_BLOCK = registerBlockWithItem(
                "charred_frame_block",
                () -> new FragileFrameBlock(properties(
                        Blocks.OAK_FENCE,
                        "charred_frame_block"
                ).isViewBlocking((state, level, pos) -> false)
                        .noOcclusion()
                        .instabreak())
        );

        VERDANT_CONDUIT = registerBlockWithItem(
                "verdant_conduit",
                () -> new VerdantConduitBlock(properties(Blocks.CONDUIT, "verdant_conduit"))
        );

        IMBUED_HEARTWOOD_LOG = registerBlockWithItem(
                "imbued_heartwood_log", () -> new InfestedRotatedPillarBlock(
                        properties(Blocks.OAK_LOG, "imbued_heartwood_log").strength(4.0f).lightLevel(state -> 2),
                        EntityTypeRegistry.TIMBERMITE::get
                )
        );

        CASSAVA_ROOTED_DIRT = registerBlockWithItem(
                "cassava_rooted_dirt", () -> new HoeRemovableItemBlock(
                        properties(Blocks.ROOTED_DIRT, "cassava_rooted_dirt"),
                        ResourceKey.create(Registries.LOOT_TABLE, LootLocations.CASSAVA_ROOTED_DIRT_POP),
                        (context) -> Blocks.DIRT.defaultBlockState()
                )
        );

        BITTER_CASSAVA_ROOTED_DIRT = registerBlockWithItem(
                "bitter_cassava_rooted_dirt", () -> new HoeRemovableItemBlock(
                        properties(Blocks.ROOTED_DIRT, "bitter_cassava_rooted_dirt"),
                        ResourceKey.create(Registries.LOOT_TABLE, LootLocations.BITTER_CASSAVA_ROOTED_DIRT_POP),
                        (context) -> Blocks.DIRT.defaultBlockState()
                )
        );

        CASSAVA_CROP = registerBlockWithoutItem(
                "cassava_crop", () -> new CassavaCropBlock(
                        properties(Blocks.POTATOES, "cassava_crop"),
                        (state) -> (state.is(BlockRegistry.CASSAVA_ROOTED_DIRT.get()) || state.is(BlockRegistry.BITTER_CASSAVA_ROOTED_DIRT.get())),
                        () -> BlockRegistry.CASSAVA_ROOTED_DIRT.get().defaultBlockState(),
                        ItemRegistry.CASSAVA_CUTTINGS
                )
        );

        BITTER_CASSAVA_CROP = registerBlockWithoutItem(
                "bitter_cassava_crop", () -> new CassavaCropBlock(
                        properties(Blocks.POTATOES, "bitter_cassava_crop"),
                        (state) -> (state.is(BlockRegistry.CASSAVA_ROOTED_DIRT.get()) || state.is(BlockRegistry.BITTER_CASSAVA_ROOTED_DIRT.get())),
                        () -> BlockRegistry.BITTER_CASSAVA_ROOTED_DIRT.get().defaultBlockState(),
                        ItemRegistry.BITTER_CASSAVA_CUTTINGS
                )
        );

        WILD_CASSAVA = registerBlockWithItem(
                "wild_cassava", () -> new FlowerBlock(
                        MobEffectRegistry.CASSAVA_POISONING.asHolder(),
                        40,
                        properties(Blocks.BLUE_ORCHID, "wild_cassava")
                )
        );

        POTTED_WILD_CASSAVA = registerBlockWithoutItem(
                "potted_wild_cassava", () -> new FlowerPotBlock(
                        BlockRegistry.WILD_CASSAVA.get(),
                        properties(Blocks.POTTED_BLUE_ORCHID, "potted_wild_cassava").noOcclusion()
                )
        );

        UBE_CAKE = registerBlockWithoutItem(
                "ube_cake",
                () -> new ExtensibleCakeBlock(properties(Blocks.CAKE, "ube_cake"))
        );


        CANDLE_UBE_CAKE = registerBlockWithoutItem(
                "candle_ube_cake",
                () -> new ExtensibleCandleCakeBlock(
                        Blocks.CANDLE,
                        BlockRegistry.UBE_CAKE,
                        properties(Blocks.CANDLE_CAKE, "candle_ube_cake")
                )
        );

        WHITE_CANDLE_UBE_CAKE = registerBlockWithoutItem(
                "white_candle_ube_cake", () -> new ExtensibleCandleCakeBlock(
                        Blocks.WHITE_CANDLE,
                        BlockRegistry.UBE_CAKE,
                        properties(Blocks.CANDLE_CAKE, "white_candle_ube_cake")
                )
        );

        ORANGE_CANDLE_UBE_CAKE = registerBlockWithoutItem(
                "orange_candle_ube_cake", () -> new ExtensibleCandleCakeBlock(
                        Blocks.ORANGE_CANDLE,
                        BlockRegistry.UBE_CAKE,
                        properties(Blocks.CANDLE_CAKE, "orange_candle_ube_cake")
                )
        );

        MAGENTA_CANDLE_UBE_CAKE = registerBlockWithoutItem(
                "magenta_candle_ube_cake", () -> new ExtensibleCandleCakeBlock(
                        Blocks.MAGENTA_CANDLE,
                        BlockRegistry.UBE_CAKE,
                        properties(Blocks.CANDLE_CAKE, "magenta_candle_ube_cake")
                )
        );

        LIGHT_BLUE_CANDLE_UBE_CAKE = registerBlockWithoutItem(
                "light_blue_candle_ube_cake", () -> new ExtensibleCandleCakeBlock(
                        Blocks.LIGHT_BLUE_CANDLE,
                        BlockRegistry.UBE_CAKE,
                        properties(Blocks.CANDLE_CAKE, "light_blue_candle_ube_cake")
                )
        );

        YELLOW_CANDLE_UBE_CAKE = registerBlockWithoutItem(
                "yellow_candle_ube_cake", () -> new ExtensibleCandleCakeBlock(
                        Blocks.YELLOW_CANDLE,
                        BlockRegistry.UBE_CAKE,
                        properties(Blocks.CANDLE_CAKE, "yellow_candle_ube_cake")
                )
        );

        LIME_CANDLE_UBE_CAKE = registerBlockWithoutItem(
                "lime_candle_ube_cake", () -> new ExtensibleCandleCakeBlock(
                        Blocks.LIME_CANDLE,
                        BlockRegistry.UBE_CAKE,
                        properties(Blocks.CANDLE_CAKE, "lime_candle_ube_cake")
                )
        );

        PINK_CANDLE_UBE_CAKE = registerBlockWithoutItem(
                "pink_candle_ube_cake", () -> new ExtensibleCandleCakeBlock(
                        Blocks.PINK_CANDLE,
                        BlockRegistry.UBE_CAKE,
                        properties(Blocks.CANDLE_CAKE, "pink_candle_ube_cake")
                )
        );

        GRAY_CANDLE_UBE_CAKE = registerBlockWithoutItem(
                "gray_candle_ube_cake", () -> new ExtensibleCandleCakeBlock(
                        Blocks.GRAY_CANDLE,
                        BlockRegistry.UBE_CAKE,
                        properties(Blocks.CANDLE_CAKE, "gray_candle_ube_cake")
                )
        );

        LIGHT_GRAY_CANDLE_UBE_CAKE = registerBlockWithoutItem(
                "light_gray_candle_ube_cake", () -> new ExtensibleCandleCakeBlock(
                        Blocks.LIGHT_GRAY_CANDLE,
                        BlockRegistry.UBE_CAKE,
                        properties(Blocks.CANDLE_CAKE, "light_gray_candle_ube_cake")
                )
        );

        CYAN_CANDLE_UBE_CAKE = registerBlockWithoutItem(
                "cyan_candle_ube_cake", () -> new ExtensibleCandleCakeBlock(
                        Blocks.CYAN_CANDLE,
                        BlockRegistry.UBE_CAKE,
                        properties(Blocks.CANDLE_CAKE, "cyan_candle_ube_cake")
                )
        );

        PURPLE_CANDLE_UBE_CAKE = registerBlockWithoutItem(
                "purple_candle_ube_cake", () -> new ExtensibleCandleCakeBlock(
                        Blocks.PURPLE_CANDLE,
                        BlockRegistry.UBE_CAKE,
                        properties(Blocks.CANDLE_CAKE, "purple_candle_ube_cake")
                )
        );

        BLUE_CANDLE_UBE_CAKE = registerBlockWithoutItem(
                "blue_candle_ube_cake", () -> new ExtensibleCandleCakeBlock(
                        Blocks.BLUE_CANDLE,
                        BlockRegistry.UBE_CAKE,
                        properties(Blocks.CANDLE_CAKE, "blue_candle_ube_cake")
                )
        );

        BROWN_CANDLE_UBE_CAKE = registerBlockWithoutItem(
                "brown_candle_ube_cake", () -> new ExtensibleCandleCakeBlock(
                        Blocks.BROWN_CANDLE,
                        BlockRegistry.UBE_CAKE,
                        properties(Blocks.CANDLE_CAKE, "brown_candle_ube_cake")
                )
        );

        GREEN_CANDLE_UBE_CAKE = registerBlockWithoutItem(
                "green_candle_ube_cake", () -> new ExtensibleCandleCakeBlock(
                        Blocks.GREEN_CANDLE,
                        BlockRegistry.UBE_CAKE,
                        properties(Blocks.CANDLE_CAKE, "green_candle_ube_cake")
                )
        );

        RED_CANDLE_UBE_CAKE = registerBlockWithoutItem(
                "red_candle_ube_cake", () -> new ExtensibleCandleCakeBlock(
                        Blocks.RED_CANDLE,
                        BlockRegistry.UBE_CAKE,
                        properties(Blocks.CANDLE_CAKE, "red_candle_ube_cake")
                )
        );

        BLACK_CANDLE_UBE_CAKE = registerBlockWithoutItem(
                "black_candle_ube_cake", () -> new ExtensibleCandleCakeBlock(
                        Blocks.BLACK_CANDLE,
                        BlockRegistry.UBE_CAKE,
                        properties(Blocks.CANDLE_CAKE, "black_candle_ube_cake")
                )
        );

        UBE_CROP = registerBlockWithoutItem(
                "ube_crop",
                () -> new SpreadingCropBlock(
                        properties(Blocks.POTATOES, "ube_crop"),
                        ItemRegistry.BITTER_CASSAVA_CUTTINGS
                )
        );

        WILD_UBE = registerBlockWithItem(
                "wild_ube",
                () -> new FlowerBlock(MobEffects.CONFUSION, 40, properties(Blocks.BLUE_ORCHID, "wild_ube"))
        );

        POTTED_WILD_UBE = registerBlockWithoutItem(
                "potted_wild_ube", () -> new FlowerPotBlock(
                        BlockRegistry.WILD_UBE.get(),
                        properties(Blocks.POTTED_BLUE_ORCHID, "potted_wild_ube").noOcclusion()
                )
        );

        TOXIC_DIRT = registerBlockWithItem(
                "toxic_dirt",
                () -> new ToxicDirtBlock(properties(Blocks.DIRT, "toxic_dirt"))
        );

        DEAD_GRASS = registerBlockWithItem(
                "dead_grass",
                () -> new DeadTallGrassBlock(properties(Blocks.SHORT_GRASS, "dead_grass"))
        );

        POISON_IVY_BLOCK = registerBlockWithItem(
                "poison_ivy_block",
                () -> new EffectGivingPillarBlock(
                        properties(Blocks.HAY_BLOCK, "poison_ivy_block").mapColor(MapColor.COLOR_LIGHT_GREEN),
                        () -> new MobEffectInstance(MobEffects.POISON, 100, 1)
                )
        );

        TOXIC_ASH_BLOCK = registerBlockWithItem(
                "toxic_ash_block",
                () -> new EffectGivingPillarBlock(
                        properties(Blocks.HAY_BLOCK, "toxic_ash_block").instabreak(),
                        () -> new MobEffectInstance(MobEffects.WITHER, 100, 0)
                )
        );

        RUE = registerBlockWithItem(
                "rue",
                () -> new FlowerBlock(MobEffectRegistry.BLURRING.asHolder(), 40, properties(Blocks.BLUE_ORCHID, "rue"))
        );

        POTTED_RUE = registerBlockWithoutItem(
                "potted_rue",
                () -> new FlowerPotBlock(
                        BlockRegistry.RUE.get(),
                        properties(Blocks.POTTED_BLUE_ORCHID, "potted_rue").noOcclusion()
                )
        );

        PUTRID_FERTILIZER = registerBlockWithItem(
                "putrid_fertilizer",
                () -> new FertilizerRotatedPillarBlock(properties(Blocks.HAY_BLOCK, "putrid_fertilizer").mapColor(
                        MapColor.COLOR_BROWN).randomTicks())
        );

        PAPER_FRAME = registerBlockWithItem(
                "paper_frame",
                () -> new RotatedPillarBlock(properties(
                        Blocks.OAK_FENCE,
                        "paper_frame"
                ).isViewBlocking((state, level, pos) -> false)
                        .noOcclusion()
                        .instabreak()
                        .mapColor(MapColor.TERRACOTTA_WHITE))
        );

        ROPE_LADDER = registerBlockWithItem(
                "rope_ladder",
                () -> new HangingLadderBlock(properties(Blocks.LADDER, "rope_ladder"))
        );

        SMALL_ALOE = registerBlockWithoutItem(
                "small_aloe", () -> new AloeCropBlock(
                        (state, level, pos) -> level.setBlockAndUpdate(
                                pos,
                                BlockRegistry.LARGE_ALOE.get().defaultBlockState()
                        ),
                        (rand) -> new ItemStack(ItemRegistry.YOUNG_ALOE_LEAF.get(), rand.nextInt(0, 2)),
                        () -> ItemRegistry.ALOE_PUP.get(),
                        i -> 0f,
                        properties(Blocks.SWEET_BERRY_BUSH, "small_aloe")
                )
        );

        LARGE_ALOE = registerBlockWithoutItem(
                "large_aloe", () -> new AloeCropBlock(
                        (state, level, pos) -> {
                            if (false) {
                                if (((HugeAloeCropBlock) BlockRegistry.HUGE_ALOE.get()).canPlace(level, pos, true)) {
                                    ((HugeAloeCropBlock) BlockRegistry.HUGE_ALOE.get()).placeFullBush(level, pos, 0);
                                }
                            }


                        },
                        (rand) -> new ItemStack(ItemRegistry.ALOE_LEAF.get(), rand.nextInt(0, 2)),
                        () -> ItemRegistry.ALOE_PUP.get(),
                        (i -> i / 10f),
                        properties(Blocks.SWEET_BERRY_BUSH, "large_aloe")
                ) {

                    @Override
                    public IntegerProperty getAgeProperty() {
                        return BlockStateProperties.AGE_5;
                    }

                    @Override
                    public int getMaxAge() {
                        return 5;
                    }

                }
        );

        HUGE_ALOE = registerBlockWithoutItem(
                "huge_aloe", () -> new HugeAloeCropBlock(
                        properties(Blocks.SWEET_BERRY_BUSH, "huge_aloe"),
                        (rand) -> new ItemStack(ItemRegistry.OLD_ALOE_LEAF.get(), rand.nextInt(2, 5)),
                        () -> ItemRegistry.ALOE_PUP.get()
                )
        );

        SCREE = registerBlockWithItem(
                "scree", () -> new ColoredFallingPillarBlock(
                        new ColorRGBA(0x2A2A2F),
                        properties("scree").mapColor(MapColor.DEEPSLATE)
                                .instrument(NoteBlockInstrument.SNARE)
                                .strength(1.5F)
                                .requiresCorrectToolForDrops()
                                .sound(SoundType.GRAVEL)
                )
        );

        PACKED_SCREE = registerBlockWithItem(
                "packed_scree",
                () -> new RotatedPillarBlock(properties("packed_scree").mapColor(MapColor.DEEPSLATE)
                        .instrument(NoteBlockInstrument.SNARE)
                        .strength(1.8F)
                        .requiresCorrectToolForDrops()
                        .sound(SoundType.GRAVEL))
        );

        FUSED_SCREE = registerBlockWithItem(
                "fused_scree",
                () -> new RotatedPillarBlock(properties("fused_scree").mapColor(MapColor.DEEPSLATE)
                        .instrument(NoteBlockInstrument.SNARE)
                        .strength(2.0F)
                        .requiresCorrectToolForDrops()
                        .sound(SoundType.GRAVEL))
        );

        GRUS = registerBlockWithItem(
                "grus",
                () -> new Block(properties("grus").mapColor(MapColor.COLOR_GRAY).strength(0.8F).sound(SoundType.GRAVEL))
        );

        STONY_GRUS = registerBlockWithItem(
                "stony_grus",
                () -> new Block(properties("stony_grus").mapColor(MapColor.COLOR_GRAY)
                        .strength(1.0F)
                        .sound(SoundType.GRAVEL))
        );

        BLASTING_BLOSSOM = registerBlockWithoutItem(
                "blasting_blossom",
                () -> new BombFlowerCropBlock(
                        properties(Blocks.MELON, "blasting_blossom").randomTicks(),
                        rand -> new ItemStack(ItemRegistry.BLASTING_BLOOM.get())
                )
        );

        BLASTING_BUNCH = registerBlockWithoutItem(
                "blasting_bunch", () -> new BombPileBlock(
                        properties(Blocks.MELON, "blasting_bunch").randomTicks(),
                        (stack) -> stack.is(VerdantTags.Items.BLASTING_BLOSSOM_BOMBS)
                )
        );

        EARTH_BRICKS = registerBlockWithItem(
                "earth_bricks",
                () -> new Block(properties("earth_bricks").mapColor(MapColor.DIRT)
                        .strength(2.5F)
                        .sound(SoundType.GRAVEL))
        );

        EARTH_BRICK_STAIRS = registerBlockWithItem(
                "earth_brick_stairs", () -> new StairBlock(
                        EARTH_BRICKS.get().defaultBlockState(),
                        properties("earth_brick_stairs").mapColor(MapColor.DIRT).strength(2.5F).sound(SoundType.GRAVEL)
                )
        );

        EARTH_BRICK_SLAB = registerBlockWithItem(
                "earth_brick_slab",
                () -> new SlabBlock(properties("earth_brick_slab").mapColor(MapColor.DIRT)
                        .strength(2.5F)
                        .sound(SoundType.GRAVEL))
        );

        EARTH_BRICK_WALL = registerBlockWithItem(
                "earth_brick_wall",
                () -> new WallBlock(properties("earth_brick_wall").mapColor(MapColor.DIRT)
                        .strength(2.5F)
                        .sound(SoundType.GRAVEL))
        );


        TOXIC_GRUS = registerBlockWithItem(
                "toxic_grus",
                () -> new ToxicDirtBlock(properties("toxic_grus").mapColor(MapColor.COLOR_GRAY)
                        .strength(0.9F)
                        .sound(SoundType.GRAVEL))
        );


        BLUEWEED = registerBlockWithItem(
                "blueweed", () -> new FlowerBlock(
                        new SuspiciousStewEffects(List.of(
                                new SuspiciousStewEffects.Entry(MobEffects.HUNGER, 280),
                                new SuspiciousStewEffects.Entry(MobEffectRegistry.PHOTOSENSITIVITY.asHolder(), 140)
                        )), properties(Blocks.BLUE_ORCHID, "blueweed")
                ) {
                    private static final Supplier<MobEffectInstance> PHOTOSENSITIVITY = () -> new MobEffectInstance(MobEffectRegistry.PHOTOSENSITIVITY.asHolder(),
                            300,
                            0
                    );

                    @Override
                    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
                        if (entity instanceof LivingEntity livingEntity && VerdantIFF.isEnemy(livingEntity)) {
                            if (!level.isClientSide) {
                                if (livingEntity instanceof ServerPlayer player) {
                                    TriggerRegistry.VERDANT_PLANT_ATTACK_TRIGGER.get().trigger(player);
                                }
                                livingEntity.addEffect(PHOTOSENSITIVITY.get());
                            }
                        }
                    }
                }
        );
        POTTED_BLUEWEED = registerBlockWithoutItem(
                "potted_blueweed", () -> new FlowerPotBlock(
                        BlockRegistry.BLUEWEED.get(),
                        properties(Blocks.POTTED_BLUE_ORCHID, "potted_blueweed").noOcclusion()
                )
        );

        TALL_BUSH = registerBlockWithItem(
                "tall_bush",
                () -> new TallThornBushBlock(
                        properties(Blocks.SWEET_BERRY_BUSH, "tall_bush").noOcclusion()
                                .strength(0.5F), 0.0f
                )
        );

        TALL_THORN_BUSH = registerBlockWithItem(
                "tall_thorn_bush",
                () -> new TallThornBushBlock(
                        properties(Blocks.SWEET_BERRY_BUSH, "tall_thorn_bush").noOcclusion()
                                .strength(0.75F), 3.0f
                )
        );

    }

    public static void init() {
    }

    public static <T extends Block> RegistryObject<Block, T> registerBlockWithItem(String name, Supplier<T> block) {
        return registerBlockWithItem(
                name,
                block,
                b -> () -> new BlockItem(b.get(), ItemRegistry.properties(name).useBlockDescriptionPrefix())
        );
    }

    protected static <T extends Block> RegistryObject<Block, T> registerBlockWithItem(String name, Supplier<T> block, Function<RegistryObject<Block, T>, Supplier<? extends BlockItem>> item) {
        var reg = BLOCKS.register(name, block);
        ItemRegistry.ITEMS.register(name, () -> item.apply(reg).get());
        return reg;
    }

    protected static <T extends Block> RegistryObject<Block, T> registerBlockWithoutItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }

    private static BlockBehaviour.Properties properties(String name) {
        return BlockBehaviour.Properties.of().setId(id(name));
    }

    private static BlockBehaviour.Properties properties(Block block, String name) {
        return BlockBehaviour.Properties.ofFullCopy(block).setId(id(name));
    }

    private static ResourceKey<Block> id(String name) {
        return ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
    }
}

