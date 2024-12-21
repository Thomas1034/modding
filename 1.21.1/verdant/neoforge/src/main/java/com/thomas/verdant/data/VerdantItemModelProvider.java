package com.thomas.verdant.data;

import com.thomas.verdant.Constants;
import com.thomas.verdant.client.item.VerdantItemProperties;
import com.thomas.verdant.registration.RegistryObject;
import com.thomas.verdant.registry.BlockRegistry;
import com.thomas.verdant.registry.ItemRegistry;
import com.thomas.verdant.registry.WoodSets;
import com.thomas.verdant.woodset.WoodSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class VerdantItemModelProvider extends ItemModelProvider {
    public VerdantItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            generateFor(woodSet);
        }

        evenSimplerBlockItem(BlockRegistry.FISH_TRAP_BLOCK);
        basicItem(ItemRegistry.ROASTED_COFFEE.get());
        basicItem(ItemRegistry.THORN.get());
        basicItem(BlockRegistry.STRANGLER_VINE.get().asItem());
        basicItem(BlockRegistry.LEAFY_STRANGLER_VINE.get().asItem());
        basicItem(BlockRegistry.STRANGLER_TENDRIL.get().asItem());
        basicItem(BlockRegistry.POISON_IVY.get().asItem());
        basicItem(ItemRegistry.ROPE.get());
        ropeCoilItem(ItemRegistry.ROPE_COIL);
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
        basicItem(woodSet.getBoatItem().get());
        basicItem(woodSet.getChestBoatItem().get());
        trapdoorItem(woodSet.getTrapdoor());
    }

    public void trapdoorItem(RegistryObject<Block, Block> block) {
        this.withExistingParent(
                BuiltInRegistries.BLOCK.getKey(block.get()).getPath(),
                modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath() + "_bottom")
        );
    }

    public void fenceItem(RegistryObject<Block, Block> block, RegistryObject<Block, Block> baseBlock) {
        this.withExistingParent(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture(
                        "texture",
                        ResourceLocation.fromNamespaceAndPath(
                                Constants.MOD_ID,
                                "block/" + BuiltInRegistries.BLOCK.getKey(baseBlock.get()).getPath()
                        )
                );
    }

    public void buttonItem(RegistryObject<Block, Block> block, RegistryObject<Block, Block> baseBlock) {
        this.withExistingParent(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture(
                        "texture",
                        ResourceLocation.fromNamespaceAndPath(
                                Constants.MOD_ID,
                                "block/" + BuiltInRegistries.BLOCK.getKey(baseBlock.get()).getPath()
                        )
                );
    }

    public void buttonItem(RegistryObject<Block, Block> block, Block baseBlock) {
        this.withExistingParent(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture(
                        "texture",
                        ResourceLocation.fromNamespaceAndPath(
                                Constants.MOD_ID,
                                "block/" + BuiltInRegistries.BLOCK.getKey(baseBlock).getPath()
                        )
                );
    }

    public void evenSimplerBlockItem(RegistryObject<Block, Block> block) {
        this.withExistingParent(
                Constants.MOD_ID + ":" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath(),
                modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath())
        );
    }

    public void ropeCoilItem(RegistryObject<Item, Item> item) {

        ResourceLocation name = item.getId();

        ItemModelBuilder builder = basicItem(item.get());

        builder.override().model(createBasicItemModel(
                name.withSuffix("_short_no_hook"),
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "item/short_rope_coil")
        )).predicate(VerdantItemProperties.ROPE_LENGTH, 0).predicate(VerdantItemProperties.HAS_HOOK, 0);

        builder.override().model(createBasicItemModel(
                name.withSuffix("_short_hook"),
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "item/short_rope_coil"),
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "item/hook_overlay")
        )).predicate(VerdantItemProperties.ROPE_LENGTH, 0).predicate(VerdantItemProperties.HAS_HOOK, 1);

        builder.override()
                .model(createBasicItemModel(
                        name.withSuffix("_no_hook"),
                        ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "item/rope_coil")
                ))
                .predicate(VerdantItemProperties.ROPE_LENGTH, 0.49f)
                .predicate(VerdantItemProperties.HAS_HOOK, 0);

        builder.override().model(createBasicItemModel(
                name.withSuffix("_hook"),
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "item/rope_coil"),
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "item/hook_overlay")
        )).predicate(VerdantItemProperties.ROPE_LENGTH, 0.49f).predicate(VerdantItemProperties.HAS_HOOK, 1);

    }

    protected ItemModelBuilder createBasicItemModel(ResourceLocation location, ResourceLocation... layers) {
        ItemModelBuilder builder = getBuilder(location.toString()).parent(new ModelFile.UncheckedModelFile(
                "item/generated"));
        int i = 0;
        for (ResourceLocation layer : layers) {
            builder.texture("layer" + i, layer);
            i++;
        }
        return builder;
    }

}