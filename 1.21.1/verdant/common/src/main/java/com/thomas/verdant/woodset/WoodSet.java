package com.thomas.verdant.woodset;


import com.thomas.verdant.Constants;
import com.thomas.verdant.registration.RegistrationProvider;
import com.thomas.verdant.registration.RegistryObject;
import com.thomas.verdant.registry.ItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

// WIP TODO data gen
public class WoodSet {

    // The blocks created
    protected RegistryObject<Block, Block> log;
    protected RegistryObject<Block, Block> wood;
    protected RegistryObject<Block, Block> strippedLog;
    protected RegistryObject<Block, Block> strippedWood;
    protected RegistryObject<Block, Block> planks;
    protected RegistryObject<Block, Block> slab;
    protected RegistryObject<Block, Block> stairs;
    protected RegistryObject<Block, Block> fence;
    protected RegistryObject<Block, Block> fenceGate;
    protected RegistryObject<Block, Block> sign;
    protected RegistryObject<Block, Block> wallSign;
    protected RegistryObject<Block, Block> hangingSign;
    protected RegistryObject<Block, Block> wallHangingSign;
    protected RegistryObject<Block, Block> button;
    protected RegistryObject<Block, Block> pressurePlate;
    protected RegistryObject<Block, Block> door;
    protected RegistryObject<Block, Block> trapdoor;

    // The items created
    protected RegistryObject<Item, Item> signItem;
    protected RegistryObject<Item, Item> hangingSignItem;
    protected RegistryObject<Item, Item> boatItem;
    protected RegistryObject<Item, Item> chestBoatItem;

    // The mod id
    protected final String modid;
    // The set name
    protected final String setName;
    // The base block properties
    protected final Supplier<BlockBehaviour.Properties> base;

    // Registration helpers
    protected final RegistrationProvider<Block> blocks;
    protected final RegistrationProvider<Item> items;

    public WoodSet(String modid, String setName, Supplier<BlockBehaviour.Properties> baseProperties) {
        this.modid = modid;
        this.setName = setName;
        this.base = baseProperties;
        this.blocks = RegistrationProvider.get(Registries.BLOCK, modid);
        this.items = RegistrationProvider.get(Registries.ITEM, modid);
    }

    protected void registerBlocks() {
        this.log = registerBlockWithItem(typeName("_log"), () -> new RotatedPillarBlock(this.logProperties(typeName("_log"))));
        this.wood = registerBlockWithItem(typeName("_wood"), () -> new RotatedPillarBlock(this.logProperties(typeName("_wood"))));
        this.strippedLog = registerBlockWithItem(typeName("_stripped_log"), () -> new RotatedPillarBlock(this.logProperties(typeName("_log"))));
        this.strippedWood = registerBlockWithItem(typeName("_stripped_wood"), () -> new RotatedPillarBlock(this.logProperties(typeName("_wood"))));
        this.planks = registerBlockWithItem(typeName("_planks"), () -> new Block(this.planksProperties(typeName("_planks"))));
        this.slab = registerBlockWithItem(typeName("_slab"), () -> new SlabBlock(this.slabProperties(typeName("_slab"))));
        this.stairs = registerBlockWithItem(typeName("_stairs"), () -> new StairBlock(this.planks.get().defaultBlockState(), this.stairsProperties(typeName("_slab"))));



    }

    // Use StrippableBlockRegistry for Fabric, not sure for NeoForge.
    protected void registerStrippableBlocks(BiConsumer<Block, Block> registrar) {
        registrar.accept(this.log.get(), this.strippedLog.get());
        registrar.accept(this.wood.get(), this.strippedWood.get());
    }

    protected String typeName(String suffix) {
        return this.setName + suffix;
    }


    protected <T extends Block> RegistryObject<Block, T> registerBlockWithItem(String name, Supplier<T> block) {
        return registerBlockWithItem(name, block, b -> () -> new BlockItem(b.get(), ItemRegistry.properties(name)));
    }

    protected <T extends Block> RegistryObject<Block, T> registerBlockWithItem(String name, Supplier<T> block, Function<RegistryObject<Block, T>, Supplier<? extends BlockItem>> item) {
        var reg = this.blocks.register(name, block);
        this.register(name, () -> item.apply(reg).get());
        return reg;
    }

    protected RegistryObject<Item, Item> register(String name, Supplier<Item> supplier) {
        return this.items.register(name, supplier);
    }

    protected Item.Properties itemProperties(String name) {
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(this.modid, name)));
    }

    private BlockBehaviour.Properties blockProperties(String name) {
        return this.base.get().setId(id(name));
    }

    private BlockBehaviour.Properties blockProperties(Block block, String name) {
        return BlockBehaviour.Properties.ofFullCopy(block).setId(id(name));
    }

    protected BlockBehaviour.Properties planksProperties(String name) {
        return this.blockProperties(name);
    }

    protected BlockBehaviour.Properties logProperties(String name) {
        return this.blockProperties(name);
    }

    protected BlockBehaviour.Properties slabProperties(String name) {
        return this.blockProperties(name);
    }

    protected BlockBehaviour.Properties stairsProperties(String name) {
        return this.blockProperties(name);
    }

    protected BlockBehaviour.Properties fenceProperties(String name) {
        return this.blockProperties(name).forceSolidOn();
    }

    protected BlockBehaviour.Properties fenceGateProperties(String name) {
        return this.blockProperties(name).forceSolidOn();
    }

    protected BlockBehaviour.Properties signProperties(String name) {
        return this.blockProperties(name).forceSolidOn().noCollission().strength(1.0F);
    }

    protected BlockBehaviour.Properties wallSignProperties(String name) {
        return this.blockProperties(name).forceSolidOn().noCollission().strength(1.0F);
    }

    protected BlockBehaviour.Properties hangingSignProperties(String name) {
        return this.blockProperties(name).forceSolidOn().noCollission().strength(1.0F);
    }

    protected BlockBehaviour.Properties wallHangingSignProperties(String name) {
        return this.blockProperties(name).forceSolidOn().noCollission().strength(1.0F);
    }

    protected BlockBehaviour.Properties buttonProperties(String name) {
        return this.blockProperties(name).noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
    }

    protected BlockBehaviour.Properties pressurePlateProperties(String name) {
        return this.blockProperties(name).forceSolidOn().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
    }

    protected BlockBehaviour.Properties trapdoorProperties(String name) {
        return this.blockProperties(name).noOcclusion();
    }

    protected BlockBehaviour.Properties doorProperties(String name) {
        return this.blockProperties(name).noOcclusion();
    }

    private ResourceKey<Block> id(String name) {
        return ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name));
    }

}
