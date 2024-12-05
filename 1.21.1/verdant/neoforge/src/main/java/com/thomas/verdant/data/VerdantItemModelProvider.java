package com.thomas.verdant.data;

import com.thomas.verdant.Constants;
import com.thomas.verdant.registration.RegistryObject;
import com.thomas.verdant.registry.ItemRegistry;
import com.thomas.verdant.registry.properties.WoodSets;
import com.thomas.verdant.woodset.WoodSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class VerdantItemModelProvider extends ItemModelProvider {
    public VerdantItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        generateFor(WoodSets.STRANGLER);
        basicItem(ItemRegistry.ROASTED_COFFEE.get());
    }

    protected void generateFor(WoodSet woodSet) {
        fenceItem(woodSet.getFence(), woodSet.getPlanks());
        evenSimplerBlockItem(woodSet.getStairs());
        evenSimplerBlockItem(woodSet.getSlab());
        evenSimplerBlockItem(woodSet.getFenceGate());
        buttonItem(woodSet.getButton(), woodSet.getPlanks());
        evenSimplerBlockItem(woodSet.getPressurePlate());
        basicItem(woodSet.getSignItem().get());
        basicItem(woodSet.getHangingSignItem().get());
        basicItem(woodSet.getDoor().get().asItem());
        trapdoorItem(woodSet.getTrapdoor());
    }

    public void trapdoorItem(RegistryObject<Block, Block> block) {
        this.withExistingParent(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath() + "_bottom"));
    }

    public void fenceItem(RegistryObject<Block, Block> block, RegistryObject<Block, Block> baseBlock) {
        this.withExistingParent(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), mcLoc("block/fence_inventory")).texture("texture", ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block, Block> block, RegistryObject<Block, Block> baseBlock) {
        this.withExistingParent(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), mcLoc("block/button_inventory")).texture("texture", ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block, Block> block, Block baseBlock) {
        this.withExistingParent(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), mcLoc("block/button_inventory")).texture("texture", ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(baseBlock).getPath()));
    }

    public void evenSimplerBlockItem(RegistryObject<Block, Block> block) {
        this.withExistingParent(Constants.MOD_ID + ":" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath(),
                modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath()));
    }

}