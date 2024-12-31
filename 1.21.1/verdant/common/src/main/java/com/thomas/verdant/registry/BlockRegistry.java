package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.block.custom.*;
import com.thomas.verdant.registration.RegistrationProvider;
import com.thomas.verdant.registration.RegistryObject;
import com.thomas.verdant.registry.properties.BlockProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;


// NEXT UP:
// Rework block and item registration.
// Registration helper classes:
// Supply


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
    public static final RegistryObject<Block, Block> PACKED_GRAVEL;
    public static final RegistryObject<Block, Block> DIRT_COAL_ORE;
    public static final RegistryObject<Block, Block> DIRT_COPPER_ORE;
    public static final RegistryObject<Block, Block> DIRT_IRON_ORE;
    public static final RegistryObject<Block, Block> DIRT_GOLD_ORE;
    public static final RegistryObject<Block, Block> DIRT_LAPIS_ORE;
    public static final RegistryObject<Block, Block> DIRT_REDSTONE_ORE;
    public static final RegistryObject<Block, Block> DIRT_EMERALD_ORE;
    public static final RegistryObject<Block, Block> DIRT_DIAMOND_ORE;
    public static final RegistryObject<Block, Block> TEST_BLOCK;
    public static final RegistryObject<Block, Block> TEST_LOG;
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
    public static final RegistryObject<Block, Block> FISH_TRAP_BLOCK;
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
        PACKED_GRAVEL = registerBlockWithItem(
                "packed_gravel",
                () -> new Block(properties("packed_gravel").pushReaction(PushReaction.DESTROY)
                        .pushReaction(PushReaction.DESTROY))
        );
        DIRT_COAL_ORE = registerBlockWithItem(
                "dirt_coal_ore",
                () -> new DropExperienceBlock(UniformInt.of(0, 2), properties(Blocks.COARSE_DIRT, "dirt_coal_ore"))
        );
        DIRT_COPPER_ORE = registerBlockWithItem(
                "dirt_copper_ore",
                () -> new DropExperienceBlock(ConstantInt.of(0), properties(Blocks.COARSE_DIRT, "dirt_copper_ore"))
        );
        DIRT_IRON_ORE = registerBlockWithItem(
                "dirt_iron_ore",
                () -> new DropExperienceBlock(ConstantInt.of(0), properties(Blocks.COARSE_DIRT, "dirt_iron_ore"))
        );
        DIRT_GOLD_ORE = registerBlockWithItem(
                "dirt_gold_ore",
                () -> new DropExperienceBlock(ConstantInt.of(0), properties(Blocks.COARSE_DIRT, "dirt_gold_ore"))
        );
        DIRT_LAPIS_ORE = registerBlockWithItem(
                "dirt_lapis_ore",
                () -> new DropExperienceBlock(UniformInt.of(2, 5), properties(Blocks.COARSE_DIRT, "dirt_lapis_ore"))
        );
        DIRT_REDSTONE_ORE = registerBlockWithItem(
                "dirt_redstone_ore",
                () -> new RedStoneOreBlock(properties(Blocks.COARSE_DIRT, "dirt_redstone_ore").lightLevel(
                        litBlockEmission(9)))
        );
        DIRT_EMERALD_ORE = registerBlockWithItem(
                "dirt_emerald_ore",
                () -> new DropExperienceBlock(UniformInt.of(3, 7), properties(Blocks.COARSE_DIRT, "dirt_emerald_ore"))
        );
        DIRT_DIAMOND_ORE = registerBlockWithItem(
                "dirt_diamond_ore",
                () -> new DropExperienceBlock(UniformInt.of(3, 7), properties(Blocks.COARSE_DIRT, "dirt_diamond_ore"))
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
                ).noOcclusion().randomTicks())
        );
        STRANGLER_LEAVES = registerBlockWithItem(
                "strangler_leaves",
                () -> new StranglerLeavesBlock(properties(Blocks.ACACIA_LEAVES, "strangler_leaves").noOcclusion()
                        .randomTicks())
        );
        WILTED_STRANGLER_LEAVES = registerBlockWithItem(
                "wilted_strangler_leaves",
                () -> new LeavesBlock(properties(Blocks.ACACIA_LEAVES, "wilted_strangler_leaves").noOcclusion()
                        .randomTicks())
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
        FISH_TRAP_BLOCK = registerBlockWithItem(
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

        TEST_LOG = registerBlockWithItem(
                "test_log",
                () -> new RotatedPillarBlock(properties(Blocks.GLASS, "test_log"))
        );
        TEST_BLOCK = registerBlockWithItem(
                "test_block",
                () -> new Block(properties(Blocks.GLASS, "test_block").randomTicks())
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
        var reg = BLOCKS.register(name, block);
        return reg;
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
