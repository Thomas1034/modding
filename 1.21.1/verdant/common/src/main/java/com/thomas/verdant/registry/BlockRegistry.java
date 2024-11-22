package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.block.custom.SpreadingRootsBlock;
import com.thomas.verdant.registration.RegistrationProvider;
import com.thomas.verdant.registration.RegistryObject;
import com.thomas.verdant.registry.properties.BlockProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class BlockRegistry {
    public static void init() {}

    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, Constants.MOD_ID);

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

    static {
        VERDANT_ROOTED_DIRT = registerBlockWithItem("verdant_rooted_dirt", () -> new SpreadingRootsBlock(BlockProperties.VERDANT_ROOTS, false, () -> BlockRegistry.VERDANT_GRASS_DIRT, false, () -> BlockRegistry.VERDANT_ROOTED_MUD));
        VERDANT_GRASS_DIRT = registerBlockWithItem("verdant_grass_dirt", () -> new SpreadingRootsBlock(BlockProperties.VERDANT_GRASS, true, () -> BlockRegistry.VERDANT_ROOTED_DIRT, false, () -> BlockRegistry.VERDANT_GRASS_MUD));
        VERDANT_ROOTED_MUD = registerBlockWithItem("verdant_rooted_mud", () -> new SpreadingRootsBlock(BlockProperties.VERDANT_ROOTS, false, () -> BlockRegistry.VERDANT_GRASS_MUD, true, () -> BlockRegistry.VERDANT_ROOTED_DIRT));
        VERDANT_GRASS_MUD = registerBlockWithItem("verdant_grass_mud", () -> new SpreadingRootsBlock(BlockProperties.VERDANT_GRASS, true, () -> BlockRegistry.VERDANT_ROOTED_MUD, true, () -> BlockRegistry.VERDANT_GRASS_MUD));
        VERDANT_ROOTED_CLAY = registerBlockWithItem("verdant_rooted_clay", () -> new SpreadingRootsBlock(BlockProperties.VERDANT_ROOTS, false, () -> BlockRegistry.VERDANT_GRASS_CLAY));
        VERDANT_GRASS_CLAY = registerBlockWithItem("verdant_grass_clay", () -> new SpreadingRootsBlock(BlockProperties.VERDANT_GRASS, true, () -> BlockRegistry.VERDANT_ROOTED_CLAY));
        PACKED_GRAVEL = registerBlockWithItem("packed_gravel", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL).pushReaction(PushReaction.DESTROY)));
        DIRT_COAL_ORE = registerBlockWithItem("dirt_coal_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.ofFullCopy(Blocks.COARSE_DIRT)));
        DIRT_COPPER_ORE = registerBlockWithItem("dirt_copper_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.ofFullCopy(Blocks.COARSE_DIRT)));
        DIRT_IRON_ORE = registerBlockWithItem("dirt_iron_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.ofFullCopy(Blocks.COARSE_DIRT)));
        DIRT_GOLD_ORE = registerBlockWithItem("dirt_gold_ore", () -> new DropExperienceBlock(ConstantInt.of(0), BlockBehaviour.Properties.ofFullCopy(Blocks.COARSE_DIRT)));
        DIRT_LAPIS_ORE = registerBlockWithItem("dirt_lapis_ore", () -> new DropExperienceBlock(UniformInt.of(2, 5), BlockBehaviour.Properties.ofFullCopy(Blocks.COARSE_DIRT)));
        DIRT_REDSTONE_ORE = registerBlockWithItem("dirt_redstone_ore", () -> new RedStoneOreBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COARSE_DIRT).lightLevel(litBlockEmission(9))));
        DIRT_EMERALD_ORE = registerBlockWithItem("dirt_emerald_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.ofFullCopy(Blocks.COARSE_DIRT)));
        DIRT_DIAMOND_ORE = registerBlockWithItem("dirt_diamond_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.ofFullCopy(Blocks.COARSE_DIRT)));
    }

    public static <T extends Block> RegistryObject<Block, T> registerBlockWithItem(String name, Supplier<T> block) {
        return registerBlockWithItem(name, block, b -> () -> new BlockItem(b.get(), ItemRegistry.getItemProperties()));
    }

    protected static <T extends Block> RegistryObject<Block, T> registerBlockWithItem(String name, Supplier<T> block, Function<RegistryObject<Block, T>, Supplier<? extends BlockItem>> item) {
        var reg = BLOCKS.register(name, block);
        ItemRegistry.ITEMS.register(name, () -> item.apply(reg).get());
        return reg;
    }

    private static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }
}
