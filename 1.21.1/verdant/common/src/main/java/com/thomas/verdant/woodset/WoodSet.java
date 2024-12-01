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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.PushReaction;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

// WIP TODO data gen
public class WoodSet {

    // The mod id
    protected final String modid;
    // The wood type name
    protected final String setName;
    // The wood type
    protected final WoodType woodType;
    // The block set type
    protected final BlockSetType setType;
    // The base block properties
    protected final Supplier<BlockBehaviour.Properties> base;
    // Registration helpers
    protected final RegistrationProvider<Block> blocks;
    protected final RegistrationProvider<Item> items;
    protected final RegistrationProvider<BlockEntityType<?>> blockEntities;
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
    protected RegistryObject<Block, Block> button;
    protected RegistryObject<Block, Block> pressurePlate;
    protected RegistryObject<Block, Block> door;
    protected RegistryObject<Block, Block> trapdoor;
    protected RegistryObject<Block, Block> sign;
    protected RegistryObject<Block, Block> wallSign;
    protected RegistryObject<Block, Block> hangingSign;
    protected RegistryObject<Block, Block> wallHangingSign;
    // The items created
    protected RegistryObject<Item, Item> signItem;
    protected RegistryObject<Item, Item> hangingSignItem;
    protected RegistryObject<Item, Item> boatItem;
    protected RegistryObject<Item, Item> chestBoatItem;

    public WoodSet(String modid, String setName, Supplier<BlockBehaviour.Properties> baseProperties) {
        this.modid = modid;
        this.setName = setName;
        this.base = baseProperties;
        this.blocks = RegistrationProvider.get(Registries.BLOCK, this.modid);
        this.items = RegistrationProvider.get(Registries.ITEM, this.modid);
        this.blockEntities = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, this.modid);
        this.setType = new BlockSetType(this.setName);
        this.woodType = new WoodType(this.setName, this.setType);

        registerBlocks();
        registerBlockEntities();
    }

    public String getType() {
        return this.setName;
    }

    protected void registerBlockEntities() {

        this.blockEntities.register(typeName("_sign"), () -> new BlockEntityType<>(SignBlockEntity::new, Set.of(this.sign.get(), this.wallSign.get())));
        this.blockEntities.register(typeName("_hanging_sign"), () -> new BlockEntityType<>(HangingSignBlockEntity::new, Set.of(this.hangingSign.get(), this.wallHangingSign.get())));

    }

    protected void registerBlocks() {
        this.log = registerBlockWithItem(typeName("_log"), () -> new RotatedPillarBlock(this.logProperties(typeName("_log"))));
        this.wood = registerBlockWithItem(typeName("_wood"), () -> new RotatedPillarBlock(this.logProperties(typeName("_wood"))));
        this.strippedLog = registerBlockWithItem(splitName("stripped_", "_log"), () -> new RotatedPillarBlock(this.logProperties(typeName("_log"))));
        this.strippedWood = registerBlockWithItem(splitName("stripped_", "_wood"), () -> new RotatedPillarBlock(this.logProperties(typeName("_wood"))));
        this.planks = registerBlockWithItem(typeName("_planks"), () -> new Block(this.planksProperties(typeName("_planks"))));
        this.slab = registerBlockWithItem(typeName("_slab"), () -> new SlabBlock(this.slabProperties(typeName("_slab"))));
        this.stairs = registerBlockWithItem(typeName("_stairs"), () -> new StairBlock(this.planks.get().defaultBlockState(), this.stairsProperties(typeName("_stairs"))));
        this.fence = registerBlockWithItem(typeName("_fence"), () -> new FenceBlock(this.fenceProperties(typeName("_fence"))));
        this.fenceGate = registerBlockWithItem(typeName("_fence_gate"), () -> new FenceGateBlock(this.woodType, this.fenceGateProperties(typeName("_fence_gate"))));
        this.button = registerBlockWithItem(typeName("_button"), () -> new ButtonBlock(this.setType, 30, this.buttonProperties(typeName("_button"))));
        this.pressurePlate = registerBlockWithItem(typeName("_pressure_plate"), () -> new PressurePlateBlock(this.setType, this.pressurePlateProperties(typeName("_pressure_plate"))));
        this.sign = registerBlockWithoutItem(typeName("_sign"), () -> new StandingSignBlock(this.woodType, this.signProperties(typeName("_sign"))) {
            // TODO Override block entity type here?
        });
        this.wallSign = registerBlockWithoutItem(typeName("_wall_sign"), () -> new WallSignBlock(this.woodType, this.wallSignProperties(typeName("_wall_sign"))));
        this.hangingSign = registerBlockWithoutItem(typeName("_hanging_sign"), () -> new CeilingHangingSignBlock(this.woodType, this.signProperties(typeName("_hanging_sign"))));
        this.wallHangingSign = registerBlockWithoutItem(typeName("_wall_hanging_sign"), () -> new WallHangingSignBlock(this.woodType, this.wallHangingSignProperties(typeName("_wall_hanging_sign"))));

    }

    // Use StrippableBlockRegistry for Fabric, not sure for NeoForge.
    protected void registerStrippableBlocks(BiConsumer<Block, Block> registrar) {
        registrar.accept(this.log.get(), this.strippedLog.get());
        registrar.accept(this.wood.get(), this.strippedWood.get());
    }

    protected String typeName(String suffix) {
        return this.setName + suffix;
    }

    protected String splitName(String prefix, String suffix) {
        return prefix + this.setName + suffix;
    }

    protected <T extends Block> RegistryObject<Block, T> registerBlockWithItem(String name, Supplier<T> block) {
        return registerBlockWithItem(name, block, b -> () -> new BlockItem(b.get(), ItemRegistry.properties(name)));
    }

    protected <T extends Block> RegistryObject<Block, T> registerBlockWithItem(String name, Supplier<T> block, Function<RegistryObject<Block, T>, Supplier<? extends BlockItem>> item) {
        var reg = this.blocks.register(name, block);
        this.register(name, () -> item.apply(reg).get());
        return reg;
    }

    protected <T extends Block> RegistryObject<Block, T> registerBlockWithoutItem(String name, Supplier<T> block) {
        return this.blocks.register(name, block);
    }

    protected RegistryObject<Item, Item> register(String name, Supplier<Item> supplier) {
        return this.items.register(name, supplier);
    }

    protected Item.Properties itemProperties(String name) {
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(this.modid, name)));
    }


    public RegistryObject<Block, Block> getLog() {
        return log;
    }

    public RegistryObject<Block, Block> getWood() {
        return wood;
    }

    public RegistryObject<Block, Block> getStrippedLog() {
        return strippedLog;
    }

    public RegistryObject<Block, Block> getStrippedWood() {
        return strippedWood;
    }

    public RegistryObject<Block, Block> getPlanks() {
        return planks;
    }

    public RegistryObject<Block, Block> getSlab() {
        return slab;
    }

    public RegistryObject<Block, Block> getStairs() {
        return stairs;
    }

    public RegistryObject<Block, Block> getFence() {
        return fence;
    }

    public RegistryObject<Block, Block> getFenceGate() {
        return fenceGate;
    }

    public RegistryObject<Block, Block> getSign() {
        return sign;
    }

    public RegistryObject<Block, Block> getWallSign() {
        return wallSign;
    }

    public RegistryObject<Item, Item> getSignItem() {
        return signItem;
    }

    public RegistryObject<Block, Block> getHangingSign() {
        return hangingSign;
    }

    public RegistryObject<Block, Block> getWallHangingSign() {
        return wallHangingSign;
    }

    public RegistryObject<Item, Item> getHangingSignItem() {
        return hangingSignItem;
    }

    public RegistryObject<Block, Block> getButton() {
        return button;
    }

    public RegistryObject<Block, Block> getPressurePlate() {
        return pressurePlate;
    }

    public RegistryObject<Block, Block> getDoor() {
        return door;
    }

    public RegistryObject<Block, Block> getTrapdoor() {
        return trapdoor;
    }

    public RegistryObject<Item, Item> getBoatItem() {
        return boatItem;
    }

    public RegistryObject<Item, Item> getChestBoatItem() {
        return chestBoatItem;
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
