package com.thomas.verdant.data;

import com.thomas.verdant.Constants;
import com.thomas.verdant.client.item.VerdantItemProperties;
import com.thomas.verdant.item.component.RopeCoilData;
import com.thomas.verdant.registration.RegistryObject;
import com.thomas.verdant.registry.BlockRegistry;
import com.thomas.verdant.registry.ItemRegistry;
import com.thomas.verdant.registry.WoodSets;
import com.thomas.verdant.woodset.WoodSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import oshi.util.tuples.Triplet;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

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
        evenSimplerBlockItem(BlockRegistry.STINKING_BLOSSOM);
        simpleBlockItemBlockTexture(BlockRegistry.BUSH);
        simpleBlockItemBlockTexture(BlockRegistry.THORN_BUSH);
    }

    private ItemModelBuilder simpleBlockItemBlockTexture(RegistryObject<Block, Block> item) {
        return withExistingParent(
                item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")
        ).texture("layer0", ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + item.getId().getPath()));
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

        PropertyDispatch.QuadFunction<Boolean, Boolean, Integer, RopeCoilData.LanternOptions, ResourceLocation> namer = (isShort, hasHook, light, lantern) -> {

            StringBuilder fileExtension = new StringBuilder();

            if (isShort) {
                fileExtension.append("_short");
            }
            if (!hasHook) {
                fileExtension.append("_no");
            }
            fileExtension.append("_hook");
            fileExtension.append("_light");
            fileExtension.append(light);
            if (lantern != RopeCoilData.LanternOptions.NONE) {
                fileExtension.append("_");
                fileExtension.append(lantern.typeName);
            }
            return name.withSuffix(fileExtension.toString());
        };

        // Stores a consumer that creates the predicate and the name of the corresponding texture.
        List<Triplet<Consumer<ItemModelBuilder.OverrideBuilder>, String, Boolean>> lengthOptions = List.of(
                new Triplet<>(
                        lambdaBuilder -> lambdaBuilder.predicate(VerdantItemProperties.ROPE_LENGTH, 0),
                        "short_rope_coil",
                        true
                ), new Triplet<>(
                        // 0.4999f for floating point uncertainties.
                        lambdaBuilder -> lambdaBuilder.predicate(VerdantItemProperties.ROPE_LENGTH, 0.499f),
                        "rope_coil",
                        false
                )
        );

        List<Triplet<Consumer<ItemModelBuilder.OverrideBuilder>, String, Boolean>> hookOptions = List.of(
                new Triplet<>(
                        lambdaBuilder -> lambdaBuilder.predicate(VerdantItemProperties.HAS_HOOK, 0),
                        "empty_overlay",
                        false
                ), new Triplet<>(
                        lambdaBuilder -> lambdaBuilder.predicate(VerdantItemProperties.HAS_HOOK, 1),
                        "hook_overlay",
                        true
                )
        );

        List<Triplet<Consumer<ItemModelBuilder.OverrideBuilder>, String, Integer>> lightOptions = List.of(
                new Triplet<>(
                        lambdaBuilder -> lambdaBuilder.predicate(VerdantItemProperties.ROPE_GLOW, 0),
                        "empty_overlay",
                        0
                ), new Triplet<>(
                        lambdaBuilder -> lambdaBuilder.predicate(VerdantItemProperties.ROPE_GLOW, 0.2499f),
                        "light_1_overlay",
                        1
                ), new Triplet<>(
                        lambdaBuilder -> lambdaBuilder.predicate(VerdantItemProperties.ROPE_GLOW, 0.4999f),
                        "light_2_overlay",
                        2
                ), new Triplet<>(
                        lambdaBuilder -> lambdaBuilder.predicate(VerdantItemProperties.ROPE_GLOW, 0.7499f),
                        "light_3_overlay",
                        3
                ), new Triplet<>(
                        lambdaBuilder -> lambdaBuilder.predicate(VerdantItemProperties.ROPE_GLOW, 0.9999f),
                        "light_4_overlay",
                        4
                )
        );

        List<Triplet<Consumer<ItemModelBuilder.OverrideBuilder>, String, RopeCoilData.LanternOptions>> lanternOptions = Stream.of(
                        RopeCoilData.LanternOptions.values())
                .map(option -> new Triplet<Consumer<ItemModelBuilder.OverrideBuilder>, String, RopeCoilData.LanternOptions>(
                        lambdaBuilder -> lambdaBuilder.predicate(VerdantItemProperties.LANTERN_OPTION, option.cutoff),
                        option.overlay,
                        option
                ))
                .toList();

        for (Triplet<Consumer<ItemModelBuilder.OverrideBuilder>, String, Boolean> lengthOption : lengthOptions) {
            for (Triplet<Consumer<ItemModelBuilder.OverrideBuilder>, String, Boolean> hookOption : hookOptions) {
                for (Triplet<Consumer<ItemModelBuilder.OverrideBuilder>, String, Integer> lightOption : lightOptions) {
                    for (Triplet<Consumer<ItemModelBuilder.OverrideBuilder>, String, RopeCoilData.LanternOptions> lanternOption : lanternOptions) {
                        // Get the override builder.
                        ItemModelBuilder.OverrideBuilder overrideBuilder = builder.override()
                                .model(createBasicItemModel(
                                        namer.apply(
                                                lengthOption.getC(),
                                                hookOption.getC(),
                                                lightOption.getC(),
                                                lanternOption.getC()
                                        ), Stream.of(
                                                        lengthOption.getB(),
                                                        hookOption.getB(),
                                                        lightOption.getB(),
                                                        lanternOption.getB()
                                                )
                                                .map(str -> str == null ? null : ResourceLocation.fromNamespaceAndPath(
                                                        Constants.MOD_ID,
                                                        "item/" + str
                                                ))
                                                .toArray(ResourceLocation[]::new)
                                ));

                        // Apply all the predicates.
                        lengthOption.getA().accept(overrideBuilder);
                        hookOption.getA().accept(overrideBuilder);
                        lightOption.getA().accept(overrideBuilder);
                        lanternOption.getA().accept(overrideBuilder);

                    }
                }
            }
        }
    }

    protected ItemModelBuilder createBasicItemModel(ResourceLocation location, ResourceLocation... layers) {
        ItemModelBuilder builder = getBuilder(location.toString()).parent(new ModelFile.UncheckedModelFile(
                "item/generated"));
        int i = 0;
        for (ResourceLocation layer : layers) {
            if (layer != null) {
                builder.texture("layer" + i, layer);
                i++;
            }
        }
        return builder;
    }

}