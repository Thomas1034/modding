package com.startraveler.verdant.data;

import com.google.common.collect.Streams;
import com.startraveler.verdant.Constants;
import com.startraveler.verdant.block.custom.*;
import com.startraveler.verdant.data.definitions.VerdantBlockFamilies;
import com.startraveler.verdant.data.definitions.VerdantModelTemplates;
import com.startraveler.verdant.data.definitions.VerdantTextureMapping;
import com.startraveler.verdant.data.definitions.VerdantTexturedModel;
import com.startraveler.verdant.registry.ArmorMaterialRegistry;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.registry.ItemRegistry;
import com.startraveler.verdant.registry.WoodSets;
import com.startraveler.verdant.util.Util;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import org.apache.commons.lang3.function.TriFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class VerdantModelProvider extends ModelProvider {

    public static final String[] VERDANT_OVERLAYS = Arrays.stream(new String[]{"default",
            "thick",
            "thin",
            "thin2",
            "very_thin",
            "very_thin_mixed",
            "wilted",
            "wilted_thin"}).map(str -> "overlay_" + str).toArray(String[]::new);

    private BlockModelGenerators blockModels;
    private ItemModelGenerators itemModels;

    public VerdantModelProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    public static MultiVariantGenerator createTumbledBlock(Block block, ResourceLocation model) {
        Variant[] variants = new Variant[4 * 4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                variants[i + 4 * j] = Variant.variant()
                        .with(VariantProperties.MODEL, model)
                        .with(VariantProperties.X_ROT, VariantProperties.Rotation.values()[i])
                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.values()[j]);
            }
        }
        return MultiVariantGenerator.multiVariant(block, variants);
    }

    public static MultiPartGenerator createFishTrapBlock(Block block, ResourceLocation model) {

        return MultiPartGenerator.multiPart(block)
                .with(
                        Condition.condition().term(FishTrapBlock.FACING, Direction.NORTH),
                        Variant.variant().with(VariantProperties.MODEL, model)
                )
                .with(
                        Condition.condition().term(FishTrapBlock.FACING, Direction.EAST),
                        Variant.variant()
                                .with(VariantProperties.MODEL, model)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                )
                .with(
                        Condition.condition().term(FishTrapBlock.FACING, Direction.SOUTH),
                        Variant.variant()
                                .with(VariantProperties.MODEL, model)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                )
                .with(
                        Condition.condition().term(FishTrapBlock.FACING, Direction.WEST),
                        Variant.variant()
                                .with(VariantProperties.MODEL, model)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                );
    }

    public static MultiPartGenerator createDoubleSidedLogBlock(Block block, ResourceLocation model) {

        return MultiPartGenerator.multiPart(block)
                .with(
                        Condition.condition().term(RotatedPillarBlock.AXIS, Direction.Axis.Y),
                        Variant.variant().with(VariantProperties.MODEL, model)
                )
                .with(
                        Condition.condition().term(RotatedPillarBlock.AXIS, Direction.Axis.X),
                        Variant.variant()
                                .with(VariantProperties.MODEL, model)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                )
                .with(
                        Condition.condition().term(RotatedPillarBlock.AXIS, Direction.Axis.Z),
                        Variant.variant()
                                .with(VariantProperties.MODEL, model)
                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                );
    }

    public static MultiPartGenerator createSpikesBlock(Block block, ResourceLocation model) {
        return MultiPartGenerator.multiPart(block).with(
                Condition.condition().term(SpikesBlock.FACING, Direction.UP),
                Variant.variant()
                        .with(VariantProperties.MODEL, model)
                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
        ).with(
                Condition.condition().term(SpikesBlock.FACING, Direction.DOWN),
                Variant.variant()
                        .with(VariantProperties.MODEL, model)
                        .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
        ).with(
                Condition.condition().term(SpikesBlock.FACING, Direction.EAST),
                Variant.variant()
                        .with(VariantProperties.MODEL, model)
                        .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
        ).with(
                Condition.condition().term(SpikesBlock.FACING, Direction.SOUTH),
                Variant.variant()
                        .with(VariantProperties.MODEL, model)
                        .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
        ).with(
                Condition.condition().term(SpikesBlock.FACING, Direction.WEST),
                Variant.variant()
                        .with(VariantProperties.MODEL, model)
                        .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
        );
    }

    public static MultiPartGenerator createBlastingBlossom(Block block, Function<Integer, ResourceLocation> modelFunction) {
        MultiPartGenerator generator = MultiPartGenerator.multiPart(block);
        for (int i : BombFlowerCropBlock.AGE.getPossibleValues()) {
            ResourceLocation model = modelFunction.apply(i);
            generator = generator.with(
                    Condition.condition()
                            .term(BombFlowerCropBlock.FACING, Direction.UP)
                            .term(BombFlowerCropBlock.AGE, i),
                    Variant.variant()
                            .with(VariantProperties.MODEL, model)
                            .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
            ).with(
                    Condition.condition()
                            .term(BombFlowerCropBlock.FACING, Direction.DOWN)
                            .term(BombFlowerCropBlock.AGE, i),
                    Variant.variant()
                            .with(VariantProperties.MODEL, model)
                            .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
            ).with(
                    Condition.condition()
                            .term(BombFlowerCropBlock.FACING, Direction.EAST)
                            .term(BombFlowerCropBlock.AGE, i),
                    Variant.variant()
                            .with(VariantProperties.MODEL, model)
                            .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                            .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
            ).with(
                    Condition.condition()
                            .term(BombFlowerCropBlock.FACING, Direction.SOUTH)
                            .term(BombFlowerCropBlock.AGE, i),
                    Variant.variant()
                            .with(VariantProperties.MODEL, model)
                            .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                            .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
            ).with(
                    Condition.condition()
                            .term(BombFlowerCropBlock.FACING, Direction.NORTH)
                            .term(BombFlowerCropBlock.AGE, i),
                    Variant.variant()
                            .with(VariantProperties.MODEL, model)
                            .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
            ).with(
                    Condition.condition()
                            .term(BombFlowerCropBlock.FACING, Direction.WEST)
                            .term(BombFlowerCropBlock.AGE, i),
                    Variant.variant()
                            .with(VariantProperties.MODEL, model)
                            .with(VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                            .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
            );
        }

        return generator;
    }

    public static MultiPartGenerator createBlastingBunch(Block block, Function<Integer, ResourceLocation> modelFunction) {
        MultiPartGenerator generator = MultiPartGenerator.multiPart(block);
        for (int i : BombPileBlock.BOMBS.getPossibleValues()) {
            ResourceLocation model = modelFunction.apply(i);
            generator = generator.with(
                    Condition.condition().term(BombPileBlock.FACING, Direction.EAST).term(BombPileBlock.BOMBS, i),
                    Variant.variant()
                            .with(VariantProperties.MODEL, model)
                            .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
            ).with(
                    Condition.condition().term(BombPileBlock.FACING, Direction.SOUTH).term(BombPileBlock.BOMBS, i),
                    Variant.variant()
                            .with(VariantProperties.MODEL, model)
                            .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
            ).with(
                    Condition.condition().term(BombPileBlock.FACING, Direction.WEST).term(BombPileBlock.BOMBS, i),
                    Variant.variant()
                            .with(VariantProperties.MODEL, model)
                            .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
            ).with(
                    Condition.condition().term(BombPileBlock.FACING, Direction.NORTH).term(BombPileBlock.BOMBS, i),
                    Variant.variant().with(VariantProperties.MODEL, model)
            );
        }

        return generator;
    }


    public static BlockStateGenerator createMirroredColumnGenerator(Block columnBlock, BiConsumer<ResourceLocation, ModelInstance> modelOutput, TextureMapping[] mappings, String[] suffixes) {
        Stream<ResourceLocation> mirrored = Util.zip(
                Arrays.stream(mappings),
                Arrays.stream(suffixes),
                (mapping, suffix) -> ModelTemplates.CUBE_COLUMN_MIRRORED.createWithSuffix(
                        columnBlock,
                        suffix,
                        mapping,
                        modelOutput
                )
        );
        Stream<ResourceLocation> normal = Util.zip(
                Arrays.stream(mappings),
                Arrays.stream(suffixes),
                (mapping, suffix) -> ModelTemplates.CUBE_COLUMN.createWithSuffix(
                        columnBlock,
                        suffix,
                        mapping,
                        modelOutput
                )
        );
        Stream<ResourceLocation> merged = Streams.concat(mirrored, normal);
        return createRotatedVariant(
                columnBlock,
                merged.toArray(ResourceLocation[]::new)
        ).with(BlockModelGenerators.createRotatedPillar());
    }

    public static MultiVariantGenerator createRotatedVariant(Block block, ResourceLocation... models) {
        return MultiVariantGenerator.multiVariant(
                block, Stream.concat(
                        Arrays.stream(models).map(model -> Variant.variant().with(VariantProperties.MODEL, model)),
                        Arrays.stream(models)
                                .map(model -> Variant.variant()
                                        .with(VariantProperties.MODEL, model)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                ).toArray(Variant[]::new)

        );
    }

    public MultiPartGenerator createHugeAloeBlock(Block block, BiFunction<Integer, Integer, TexturedModel.Provider> model) {

        MultiPartGenerator generator = MultiPartGenerator.multiPart(block);

        for (int i = 0; i <= HugeAloeCropBlock.MAX_AGE; i++) {

            for (int j = HugeAloeCropBlock.MIN_COORD; j <= HugeAloeCropBlock.MAX_COORD; j++) {
                ResourceLocation location = model.apply(i, j).createWithSuffix(
                        block,
                        (j == 0 ? "_base" : j == 1 ? "_middle" : "_top") + "_stage" + i,
                        blockModels.modelOutput
                );


                generator = generator.with(
                        Condition.condition()
                                .term(HugeAloeCropBlock.Y_PROPERTY, j)
                                .term(HugeAloeCropBlock.Z_PROPERTY, HugeAloeCropBlock.CENTER_COORD)
                                .term(HugeAloeCropBlock.X_PROPERTY, HugeAloeCropBlock.CENTER_COORD)
                                .term(HugeAloeCropBlock.AGE, i),
                        Variant.variant().with(VariantProperties.MODEL, location)
                );
            }
        }

        return generator;
    }

    public MultiPartGenerator createTrapBlock(Block block, BiFunction<Integer, Boolean, TexturedModel.Provider> model) {

        MultiPartGenerator generator = MultiPartGenerator.multiPart(block);

        for (int i = 0; i < 4; i++) {
            for (Boolean b : new Boolean[]{true, false}) {
                ResourceLocation location = model.apply(i, b)
                        .createWithSuffix(block, (b ? "_hidden" : "") + "_stage" + i, blockModels.modelOutput);


                generator = generator.with(
                        Condition.condition()
                                .term(TrapBlock.FACING, Direction.NORTH)
                                .term(TrapBlock.STAGE, i)
                                .term(TrapBlock.HIDDEN, b),
                        Variant.variant()
                                .with(VariantProperties.MODEL, location)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0)
                );
                generator = generator.with(
                        Condition.condition()
                                .term(TrapBlock.FACING, Direction.EAST)
                                .term(TrapBlock.STAGE, i)
                                .term(TrapBlock.HIDDEN, b),
                        Variant.variant()
                                .with(VariantProperties.MODEL, location)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                );
                generator = generator.with(
                        Condition.condition()
                                .term(TrapBlock.FACING, Direction.SOUTH)
                                .term(TrapBlock.STAGE, i)
                                .term(TrapBlock.HIDDEN, b),
                        Variant.variant()
                                .with(VariantProperties.MODEL, location)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                );
                generator = generator.with(
                        Condition.condition()
                                .term(TrapBlock.FACING, Direction.WEST)
                                .term(TrapBlock.STAGE, i)
                                .term(TrapBlock.HIDDEN, b),
                        Variant.variant()
                                .with(VariantProperties.MODEL, location)
                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                );
            }
        }

        return generator;
    }

    protected BlockStateGenerator createRotatedTopOverlaidBlock(Block block, Function<String, TexturedModel.Provider> model, String[] overlays) {
        Variant[] variants = new Variant[4 * overlays.length];
        for (int o = 0; o < overlays.length; o++) {
            String overlay = overlays[o];
            ResourceLocation modelLocation = model.apply(overlay).createWithSuffix(
                    block,
                    (overlays.length == 1 || overlay.equals("default") || overlay.equals("overlay_default") ? "" : "_" + overlay),
                    blockModels.modelOutput
            );
            for (int j = 0; j < 4; j++) {
                variants[o * 4 + j] = Variant.variant()
                        .with(VariantProperties.MODEL, modelLocation)
                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.values()[j]);
            }
        }
        return MultiVariantGenerator.multiVariant(block, variants);
    }

    protected BlockStateGenerator createOverlaidBlock(Block block, Function<String, TexturedModel.Provider> model, String[] overlays) {
        Variant[] variants = new Variant[overlays.length];
        for (int o = 0; o < overlays.length; o++) {
            String overlay = overlays[o];
            String trimmedOverlay = overlay.substring(overlay.lastIndexOf('/') + 1);
            ResourceLocation modelLocation = model.apply(overlay).createWithSuffix(
                    block,
                    (overlays.length == 1 || trimmedOverlay.equals("default") || trimmedOverlay.equals("overlay_default")) ? "" : "_" + trimmedOverlay,
                    blockModels.modelOutput
            );
            variants[o] = Variant.variant().with(VariantProperties.MODEL, modelLocation);

        }
        return MultiVariantGenerator.multiVariant(block, variants);
    }

    protected BlockStateGenerator createTumbledOverlaidBlock(Block block, Function<String, TexturedModel.Provider> model, String[] overlays) {
        Variant[] variants = new Variant[4 * 4 * overlays.length];
        for (int o = 0; o < overlays.length; o++) {
            String overlay = overlays[o];
            String trimmedOverlay = overlay.substring(overlay.lastIndexOf('/') + 1);
            ResourceLocation modelLocation = model.apply(overlay).createWithSuffix(
                    block,
                    (overlays.length == 1 || trimmedOverlay.equals("default") || trimmedOverlay.equals("overlay_default")) ? "" : "_" + trimmedOverlay,
                    blockModels.modelOutput
            );
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    variants[o * 16 + i * 4 + j] = Variant.variant()
                            .with(VariantProperties.MODEL, modelLocation)
                            .with(VariantProperties.X_ROT, VariantProperties.Rotation.values()[i])
                            .with(VariantProperties.Y_ROT, VariantProperties.Rotation.values()[j]);
                }
            }
        }
        return MultiVariantGenerator.multiVariant(block, variants);
    }

    public void candleCake(Block candleBlock, Block cakeBlock, Block candleCakeBlock) {
        this.blockModels.registerSimpleFlatItemModel(candleBlock.asItem());
        ResourceLocation candleCakeBase = ModelTemplates.CANDLE_CAKE.create(
                candleCakeBlock,
                VerdantTextureMapping.candleCake(cakeBlock, candleBlock, false),
                this.blockModels.modelOutput
        );
        ResourceLocation candleCakeLit = ModelTemplates.CANDLE_CAKE.createWithSuffix(
                candleCakeBlock,
                "_lit",
                VerdantTextureMapping.candleCake(cakeBlock, candleBlock, true),
                this.blockModels.modelOutput
        );
        this.blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(candleCakeBlock)
                .with(BlockModelGenerators.createBooleanModelDispatch(
                        BlockStateProperties.LIT,
                        candleCakeLit,
                        candleCakeBase
                )));
    }

    public void cakeBlock(Block cake, Item cakeItem) {
        this.blockModels.registerSimpleFlatItemModel(cakeItem);
        this.blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(cake)
                .with(PropertyDispatch.property(BlockStateProperties.BITES)
                        .select(
                                0,
                                Variant.variant()
                                        .with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(cake))
                        )
                        .select(
                                1,
                                Variant.variant()
                                        .with(
                                                VariantProperties.MODEL,
                                                ModelLocationUtils.getModelLocation(cake, "_slice1")
                                        )
                        )
                        .select(
                                2,
                                Variant.variant()
                                        .with(
                                                VariantProperties.MODEL,
                                                ModelLocationUtils.getModelLocation(cake, "_slice2")
                                        )
                        )
                        .select(
                                3,
                                Variant.variant()
                                        .with(
                                                VariantProperties.MODEL,
                                                ModelLocationUtils.getModelLocation(cake, "_slice3")
                                        )
                        )
                        .select(
                                4,
                                Variant.variant()
                                        .with(
                                                VariantProperties.MODEL,
                                                ModelLocationUtils.getModelLocation(cake, "_slice4")
                                        )
                        )
                        .select(
                                5,
                                Variant.variant()
                                        .with(
                                                VariantProperties.MODEL,
                                                ModelLocationUtils.getModelLocation(cake, "_slice5")
                                        )
                        )
                        .select(
                                6,
                                Variant.variant()
                                        .with(
                                                VariantProperties.MODEL,
                                                ModelLocationUtils.getModelLocation(cake, "_slice6")
                                        )
                        )));
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        this.blockModels = blockModels;
        this.itemModels = itemModels;

        fishTrapWithItem(BlockRegistry.FISH_TRAP.get());
        tumbledBlockWithItem(BlockRegistry.ANTIGORITE.get());
        tumbledBlockWithItem(BlockRegistry.ROTTEN_WOOD.get());
        tumbledOverlaidBlockWithItem(BlockRegistry.VERDANT_ROOTED_DIRT.get(), Blocks.DIRT, VERDANT_OVERLAYS);
        rotatedTopOverlaidBlockWithItem(
                BlockRegistry.VERDANT_GRASS_DIRT.get(),
                Blocks.DIRT,
                "verdant_grass",
                VERDANT_OVERLAYS
        );
        tumbledOverlaidBlockWithItem(BlockRegistry.VERDANT_ROOTED_MUD.get(), Blocks.MUD, VERDANT_OVERLAYS);
        rotatedTopOverlaidBlockWithItem(
                BlockRegistry.VERDANT_GRASS_MUD.get(),
                Blocks.MUD,
                "verdant_grass",
                VERDANT_OVERLAYS
        );
        tumbledOverlaidBlockWithItem(BlockRegistry.VERDANT_ROOTED_CLAY.get(), Blocks.CLAY, VERDANT_OVERLAYS);
        rotatedTopOverlaidBlockWithItem(
                BlockRegistry.VERDANT_GRASS_CLAY.get(),
                Blocks.CLAY,
                "verdant_grass",
                VERDANT_OVERLAYS
        );
        tumbledOverlaidBlockWithItem(
                BlockRegistry.VERDANT_ROOTED_GRUS.get(),
                BlockRegistry.GRUS.get(),
                VERDANT_OVERLAYS
        );
        rotatedTopOverlaidBlockWithItem(
                BlockRegistry.VERDANT_GRASS_GRUS.get(),
                BlockRegistry.GRUS.get(),
                "verdant_grass",
                VERDANT_OVERLAYS
        );
        tumbledBlockWithItem(BlockRegistry.PACKED_GRAVEL.get());
        tumbledBlockWithItem(BlockRegistry.FUSED_GRAVEL.get());
        tumbledBlockWithItem(BlockRegistry.WILTED_STRANGLER_LEAVES.get());
        tumbledBlockWithItem(BlockRegistry.STRANGLER_LEAVES.get());
        tumbledBlockWithItem(BlockRegistry.THORNY_STRANGLER_LEAVES.get());
        tumbledBlockWithItem(BlockRegistry.POISON_STRANGLER_LEAVES.get());
        overlaidBlockWithItem(BlockRegistry.DIRT_COAL_ORE.get(), Blocks.DIRT, "coal_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_COPPER_ORE.get(), Blocks.DIRT, "copper_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_IRON_ORE.get(), Blocks.DIRT, "iron_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_GOLD_ORE.get(), Blocks.DIRT, "gold_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_LAPIS_ORE.get(), Blocks.DIRT, "lapis_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_REDSTONE_ORE.get(), Blocks.DIRT, "redstone_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_EMERALD_ORE.get(), Blocks.DIRT, "emerald_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_DIAMOND_ORE.get(), Blocks.DIRT, "diamond_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.GRUS_COAL_ORE.get(), BlockRegistry.GRUS.get(), "coal_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.GRUS_COPPER_ORE.get(), BlockRegistry.GRUS.get(), "copper_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.GRUS_IRON_ORE.get(), BlockRegistry.GRUS.get(), "iron_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.GRUS_GOLD_ORE.get(), BlockRegistry.GRUS.get(), "gold_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.GRUS_LAPIS_ORE.get(), BlockRegistry.GRUS.get(), "lapis_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.GRUS_REDSTONE_ORE.get(), BlockRegistry.GRUS.get(), "redstone_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.GRUS_EMERALD_ORE.get(), BlockRegistry.GRUS.get(), "emerald_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.GRUS_DIAMOND_ORE.get(), BlockRegistry.GRUS.get(), "diamond_ore_overlay");

        createCrossBlock(BlockRegistry.POISON_IVY.get(), BlockModelGenerators.PlantType.NOT_TINTED, "cutout");
        createCrossBlock(BlockRegistry.POISON_IVY_PLANT.get(), BlockModelGenerators.PlantType.NOT_TINTED, "cutout");
        createCrossBlock(BlockRegistry.STRANGLER_TENDRIL.get(), BlockModelGenerators.PlantType.NOT_TINTED, "cutout");
        createCrossBlock(
                BlockRegistry.STRANGLER_TENDRIL_PLANT.get(),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout"
        );
        createCrossBlock(BlockRegistry.DROWNED_HEMLOCK.get(), BlockModelGenerators.PlantType.NOT_TINTED, "cutout");
        createCrossBlock(
                BlockRegistry.DROWNED_HEMLOCK_PLANT.get(),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout"
        );

        createCrossBlock(
                BlockRegistry.COFFEE_CROP.get(),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout",
                CoffeeCropBlock.AGE,
                IntStream.range(0, CoffeeCropBlock.MAX_AGE + 1).toArray()
        );
        createPottedOnly(
                BlockRegistry.COFFEE_CROP.get(),
                BlockRegistry.POTTED_COFFEE_CROP.get(),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout"
        );
        createPlantWithDefaultItem(
                BlockRegistry.BUSH.get(),
                BlockRegistry.POTTED_BUSH.get(),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout"
        );
        createPlantWithDefaultItem(
                BlockRegistry.WILD_COFFEE.get(),
                BlockRegistry.POTTED_WILD_COFFEE.get(),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout"
        );
        createPlantWithDefaultItem(
                BlockRegistry.THORN_BUSH.get(),
                BlockRegistry.POTTED_THORN_BUSH.get(),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout"
        );
        createPlantWithDefaultItem(
                BlockRegistry.BLEEDING_HEART.get(),
                BlockRegistry.POTTED_BLEEDING_HEART.get(),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout"
        );
        createPlantWithDefaultItem(
                BlockRegistry.BLUEWEED.get(),
                BlockRegistry.POTTED_BLUEWEED.get(),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout"
        );
        createPlantWithDefaultItem(
                BlockRegistry.TIGER_LILY.get(),
                BlockRegistry.POTTED_TIGER_LILY.get(),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout"
        );
        createCrossBlock(
                BlockRegistry.CASSAVA_CROP.get(),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout",
                CassavaCropBlock.AGE,
                IntStream.range(0, CassavaCropBlock.MAX_AGE + 1).toArray()
        );
        createCrossBlock(
                BlockRegistry.BITTER_CASSAVA_CROP.get(),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout",
                CassavaCropBlock.AGE,
                IntStream.range(0, CassavaCropBlock.MAX_AGE + 1).toArray()
        );
        createPlantWithDefaultItem(
                BlockRegistry.WILD_CASSAVA.get(),
                BlockRegistry.POTTED_WILD_CASSAVA.get(),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout"
        );
        createPlantWithDefaultItem(
                BlockRegistry.WILD_UBE.get(),
                BlockRegistry.POTTED_WILD_UBE.get(),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout"
        );
        createCrossBlock(
                BlockRegistry.UBE_CROP.get(),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout",
                SpreadingCropBlock.AGE,
                IntStream.range(0, SpreadingCropBlock.MAX_AGE + 1).toArray()
        );
        trapBlock(BlockRegistry.WOODEN_TRAP.get());
        trapBlock(BlockRegistry.IRON_TRAP.get());
        trapBlock(BlockRegistry.SNAPLEAF.get());
        spikesBlockWithItem(BlockRegistry.WOODEN_SPIKES.get());
        spikesBlockWithItem(BlockRegistry.IRON_SPIKES.get());
        doubleSidedLogBlockWithItem(BlockRegistry.CHARRED_FRAME_BLOCK.get());
        doubleSidedLogBlockWithItem(BlockRegistry.FRAME_BLOCK.get());
        blockModels.createAxisAlignedPillarBlock(BlockRegistry.IMBUED_HEARTWOOD_LOG.get(), TexturedModel.COLUMN);
        tumbledBlockWithItem(BlockRegistry.CASSAVA_ROOTED_DIRT.get());
        tumbledBlockWithItem(BlockRegistry.BITTER_CASSAVA_ROOTED_DIRT.get());
        cakeBlock(BlockRegistry.UBE_CAKE.get(), ItemRegistry.UBE_CAKE.get());
        candleCake(Blocks.WHITE_CANDLE, BlockRegistry.UBE_CAKE.get(), BlockRegistry.WHITE_CANDLE_UBE_CAKE.get());
        candleCake(Blocks.ORANGE_CANDLE, BlockRegistry.UBE_CAKE.get(), BlockRegistry.ORANGE_CANDLE_UBE_CAKE.get());
        candleCake(Blocks.MAGENTA_CANDLE, BlockRegistry.UBE_CAKE.get(), BlockRegistry.MAGENTA_CANDLE_UBE_CAKE.get());
        candleCake(
                Blocks.LIGHT_BLUE_CANDLE,
                BlockRegistry.UBE_CAKE.get(),
                BlockRegistry.LIGHT_BLUE_CANDLE_UBE_CAKE.get()
        );
        candleCake(Blocks.YELLOW_CANDLE, BlockRegistry.UBE_CAKE.get(), BlockRegistry.YELLOW_CANDLE_UBE_CAKE.get());
        candleCake(Blocks.LIME_CANDLE, BlockRegistry.UBE_CAKE.get(), BlockRegistry.LIME_CANDLE_UBE_CAKE.get());
        candleCake(Blocks.PINK_CANDLE, BlockRegistry.UBE_CAKE.get(), BlockRegistry.PINK_CANDLE_UBE_CAKE.get());
        candleCake(Blocks.GRAY_CANDLE, BlockRegistry.UBE_CAKE.get(), BlockRegistry.GRAY_CANDLE_UBE_CAKE.get());
        candleCake(
                Blocks.LIGHT_GRAY_CANDLE,
                BlockRegistry.UBE_CAKE.get(),
                BlockRegistry.LIGHT_GRAY_CANDLE_UBE_CAKE.get()
        );
        candleCake(Blocks.CYAN_CANDLE, BlockRegistry.UBE_CAKE.get(), BlockRegistry.CYAN_CANDLE_UBE_CAKE.get());
        candleCake(Blocks.PURPLE_CANDLE, BlockRegistry.UBE_CAKE.get(), BlockRegistry.PURPLE_CANDLE_UBE_CAKE.get());
        candleCake(Blocks.BLUE_CANDLE, BlockRegistry.UBE_CAKE.get(), BlockRegistry.BLUE_CANDLE_UBE_CAKE.get());
        candleCake(Blocks.BROWN_CANDLE, BlockRegistry.UBE_CAKE.get(), BlockRegistry.BROWN_CANDLE_UBE_CAKE.get());
        candleCake(Blocks.GREEN_CANDLE, BlockRegistry.UBE_CAKE.get(), BlockRegistry.GREEN_CANDLE_UBE_CAKE.get());
        candleCake(Blocks.RED_CANDLE, BlockRegistry.UBE_CAKE.get(), BlockRegistry.RED_CANDLE_UBE_CAKE.get());
        candleCake(Blocks.BLACK_CANDLE, BlockRegistry.UBE_CAKE.get(), BlockRegistry.BLACK_CANDLE_UBE_CAKE.get());
        candleCake(Blocks.CANDLE, BlockRegistry.UBE_CAKE.get(), BlockRegistry.CANDLE_UBE_CAKE.get());
        tumbledBlockWithItem(BlockRegistry.TOXIC_DIRT.get());
        createCrossBlock(BlockRegistry.DEAD_GRASS.get(), BlockModelGenerators.PlantType.NOT_TINTED, "cutout");

        blockModels.createAxisAlignedPillarBlock(BlockRegistry.POISON_IVY_BLOCK.get(), TexturedModel.COLUMN);
        blockModels.createAxisAlignedPillarBlock(BlockRegistry.TOXIC_ASH_BLOCK.get(), TexturedModel.COLUMN);

        createPlantWithDefaultItemWithCustomPottedTexture(
                BlockRegistry.RUE.get(),
                BlockRegistry.POTTED_RUE.get(),
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/rue_potted"),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout"
        );

        blockModels.createAxisAlignedPillarBlock(BlockRegistry.PUTRID_FERTILIZER.get(), TexturedModel.COLUMN);

        blockModels.createAxisAlignedPillarBlock(BlockRegistry.PAPER_FRAME.get(), TexturedModel.COLUMN);

        createCrossBlockWithoutItem(
                BlockRegistry.SMALL_ALOE.get(),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout",
                ((AloeCropBlock) BlockRegistry.SMALL_ALOE.get()).getAgeProperty(),
                IntStream.range(0, ((AloeCropBlock) BlockRegistry.SMALL_ALOE.get()).getMaxAge() + 1).toArray()
        );

        createAsteriskBlockWithoutItem(
                BlockRegistry.LARGE_ALOE.get(),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout",
                ((AloeCropBlock) BlockRegistry.LARGE_ALOE.get()).getAgeProperty(),
                IntStream.range(0, ((AloeCropBlock) BlockRegistry.LARGE_ALOE.get()).getMaxAge() + 1).toArray()
        );

        hugeAloeBlock(BlockRegistry.HUGE_ALOE.get());
        mirroredColumnBlock(BlockRegistry.SCREE.get());
        mirroredColumnBlock(BlockRegistry.PACKED_SCREE.get());
        mirroredColumnBlock(BlockRegistry.FUSED_SCREE.get());

        tumbledBlockWithItem(BlockRegistry.GRUS.get());
        tumbledBlockWithItem(BlockRegistry.STONY_GRUS.get());

        blastingBlossom(BlockRegistry.BLASTING_BLOSSOM.get());

        blastingBunch(BlockRegistry.BLASTING_BUNCH.get());

        blockModels.family(BlockRegistry.EARTH_BRICKS.get()).generateFor(VerdantBlockFamilies.EARTH_BRICKS);
        tumbledBlockWithItem(BlockRegistry.TOXIC_GRUS.get());

        blockModels.createDoublePlantWithDefaultItem(
                BlockRegistry.TALL_BUSH.get(),
                BlockModelGenerators.PlantType.NOT_TINTED
        );
        blockModels.createDoublePlantWithDefaultItem(
                BlockRegistry.TALL_THORN_BUSH.get(),
                BlockModelGenerators.PlantType.NOT_TINTED
        );

        basicItem(ItemRegistry.ALOE_PUP.get());

        basicItem(ItemRegistry.ROASTED_COFFEE.get());
        basicItem(ItemRegistry.THORN.get());
        basicItem(BlockRegistry.STRANGLER_VINE.get().asItem());
        basicItem(BlockRegistry.LEAFY_STRANGLER_VINE.get().asItem());
        basicItem(BlockRegistry.STRANGLER_TENDRIL.get().asItem());
        basicItem(BlockRegistry.POISON_IVY.get().asItem());
        basicItem(BlockRegistry.DROWNED_HEMLOCK.get().asItem());
        basicItem(ItemRegistry.ROPE.get());
        basicItem(BlockRegistry.WOODEN_SPIKES.get().asItem());
        basicItem(BlockRegistry.IRON_SPIKES.get().asItem());
        basicItem(BlockRegistry.SNAPLEAF.get().asItem());
        basicItem(BlockRegistry.WOODEN_TRAP.get().asItem());
        basicItem(BlockRegistry.IRON_TRAP.get().asItem());
        basicItem(ItemRegistry.ROTTEN_COMPOST.get());
        basicItem(ItemRegistry.HEART_OF_THE_FOREST.get());
        basicItem(ItemRegistry.HEART_FRAGMENT.get());
        basicItem(ItemRegistry.POISON_ARROW.get());
        basicItem(ItemRegistry.BITTER_CASSAVA.get());
        basicItem(ItemRegistry.CASSAVA.get());
        basicItem(ItemRegistry.COOKED_CASSAVA.get());
        basicItem(ItemRegistry.BITTER_STARCH.get());
        basicItem(ItemRegistry.STARCH.get());
        basicItem(ItemRegistry.SPARKLING_STARCH.get());
        basicItem(ItemRegistry.BITTER_BREAD.get());
        basicItem(ItemRegistry.GOLDEN_BREAD.get());
        basicItem(ItemRegistry.GOLDEN_CASSAVA.get());
        basicItem(ItemRegistry.COOKED_GOLDEN_CASSAVA.get());
        basicItem(ItemRegistry.BAKED_UBE.get());
        basicItem(ItemRegistry.UBE_COOKIE.get());

        basicItem(ItemRegistry.HEARTWOOD_HORSE_ARMOR.get());

        itemModels.generateTrimmableItem(
                ItemRegistry.HEARTWOOD_HELMET.get(),
                ArmorMaterialRegistry.HEARTWOOD_ASSET,
                "helmet",
                false
        );
        itemModels.generateTrimmableItem(
                ItemRegistry.HEARTWOOD_CHESTPLATE.get(),
                ArmorMaterialRegistry.HEARTWOOD_ASSET,
                "chestplate",
                false
        );
        itemModels.generateTrimmableItem(
                ItemRegistry.HEARTWOOD_LEGGINGS.get(),
                ArmorMaterialRegistry.HEARTWOOD_ASSET,
                "leggings",
                false
        );
        itemModels.generateTrimmableItem(
                ItemRegistry.HEARTWOOD_BOOTS.get(),
                ArmorMaterialRegistry.HEARTWOOD_ASSET,
                "boots",
                false
        );
        handheldItem(ItemRegistry.HEARTWOOD_AXE.get());
        handheldItem(ItemRegistry.HEARTWOOD_HOE.get());
        handheldItem(ItemRegistry.HEARTWOOD_SHOVEL.get());
        handheldItem(ItemRegistry.HEARTWOOD_PICKAXE.get());
        handheldItem(ItemRegistry.HEARTWOOD_SWORD.get());


        basicItem(ItemRegistry.IMBUEMENT_UPGRADE_SMITHING_TEMPLATE.get());
        basicItem(ItemRegistry.IMBUED_HEARTWOOD_HORSE_ARMOR.get());

        itemModels.generateTrimmableItem(
                ItemRegistry.IMBUED_HEARTWOOD_HELMET.get(),
                ArmorMaterialRegistry.IMBUED_HEARTWOOD_ASSET,
                "helmet",
                false
        );
        itemModels.generateTrimmableItem(
                ItemRegistry.IMBUED_HEARTWOOD_CHESTPLATE.get(),
                ArmorMaterialRegistry.IMBUED_HEARTWOOD_ASSET,
                "chestplate",
                false
        );
        itemModels.generateTrimmableItem(
                ItemRegistry.IMBUED_HEARTWOOD_LEGGINGS.get(),
                ArmorMaterialRegistry.IMBUED_HEARTWOOD_ASSET,
                "leggings",
                false
        );
        itemModels.generateTrimmableItem(
                ItemRegistry.IMBUED_HEARTWOOD_BOOTS.get(),
                ArmorMaterialRegistry.IMBUED_HEARTWOOD_ASSET,
                "boots",
                false
        );
        handheldItem(ItemRegistry.IMBUED_HEARTWOOD_AXE.get());
        handheldItem(ItemRegistry.IMBUED_HEARTWOOD_HOE.get());
        handheldItem(ItemRegistry.IMBUED_HEARTWOOD_SHOVEL.get());
        handheldItem(ItemRegistry.IMBUED_HEARTWOOD_PICKAXE.get());
        handheldItem(ItemRegistry.IMBUED_HEARTWOOD_SWORD.get());

        basicItem(BlockRegistry.ROPE_LADDER.get().asItem());
        basicItem(ItemRegistry.TOXIC_ASH.get());
        basicItem(ItemRegistry.BUCKET_OF_TOXIC_ASH.get());
        basicItem(ItemRegistry.BUCKET_OF_TOXIC_SOLUTION.get());
        basicItem(BlockRegistry.DEAD_GRASS.get().asItem());

        basicItem(ItemRegistry.RANCID_SLIME.get());

        basicItem(ItemRegistry.DART.get());
        tippedArrow(ItemRegistry.TIPPED_DART.get());
        // handheldItem(ItemRegistry.HUNTING_SPEAR.get());

        basicItem(ItemRegistry.ALOE_LEAF.get());
        basicItem(ItemRegistry.YOUNG_ALOE_LEAF.get());
        basicItem(ItemRegistry.OLD_ALOE_LEAF.get());

        basicItem(ItemRegistry.STABLE_BLASTING_BLOOM.get());
        basicItem(ItemRegistry.BLASTING_BLOOM.get());
        basicItem(ItemRegistry.BLASTING_BLOSSOM_SPROUT.get());

        basicItem(ItemRegistry.FRAGILE_FLASK.get());

        basicItem(ItemRegistry.THORNS_UPGRADE_SMITHING_TEMPLATE.get());

        basicItem(ItemRegistry.THORNY_HEARTWOOD_HORSE_ARMOR.get());

        itemModels.generateTrimmableItem(
                ItemRegistry.THORNY_HEARTWOOD_HELMET.get(),
                ArmorMaterialRegistry.THORNY_HEARTWOOD_ASSET,
                "helmet",
                false
        );
        itemModels.generateTrimmableItem(
                ItemRegistry.THORNY_HEARTWOOD_CHESTPLATE.get(),
                ArmorMaterialRegistry.THORNY_HEARTWOOD_ASSET,
                "chestplate",
                false
        );
        itemModels.generateTrimmableItem(
                ItemRegistry.THORNY_HEARTWOOD_LEGGINGS.get(),
                ArmorMaterialRegistry.THORNY_HEARTWOOD_ASSET,
                "leggings",
                false
        );
        itemModels.generateTrimmableItem(
                ItemRegistry.THORNY_HEARTWOOD_BOOTS.get(),
                ArmorMaterialRegistry.THORNY_HEARTWOOD_ASSET,
                "boots",
                false
        );
        handheldItem(ItemRegistry.THORNY_HEARTWOOD_AXE.get());
        handheldItem(ItemRegistry.THORNY_HEARTWOOD_HOE.get());
        handheldItem(ItemRegistry.THORNY_HEARTWOOD_SHOVEL.get());
        handheldItem(ItemRegistry.THORNY_HEARTWOOD_PICKAXE.get());
        handheldItem(ItemRegistry.THORNY_HEARTWOOD_SWORD.get());


        itemModels.generateSpawnEgg(ItemRegistry.ROOTED_SPAWN_EGG.get(), 0x223d23, 0x1ff227);
        itemModels.generateSpawnEgg(ItemRegistry.TIMBERMITE_SPAWN_EGG.get(), 0x402c14, 0x21f103);
        itemModels.generateSpawnEgg(ItemRegistry.POISONER_SPAWN_EGG.get(), 0x0e3001, 0xa9a197);
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        List<Block> excluded = new ArrayList<>();

        excluded.add(BlockRegistry.STRANGLER_VINE.get());
        excluded.add(BlockRegistry.LEAFY_STRANGLER_VINE.get());
        excluded.add(BlockRegistry.ROPE.get());
        excluded.add(BlockRegistry.ROPE_LADDER.get());
        excluded.add(BlockRegistry.ROPE_HOOK.get());
        excluded.add(BlockRegistry.STINKING_BLOSSOM.get());
        excluded.add(BlockRegistry.VERDANT_CONDUIT.get());
        WoodSets.WOOD_SETS.forEach(woodSet -> {
            woodSet.getBlockProvider().getEntries().forEach(registryObject -> excluded.add(registryObject.get()));
        });

        return super.getKnownBlocks().filter(entry -> !excluded.contains(entry.value()));
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        List<Item> excluded = new ArrayList<>();

        excluded.add(ItemRegistry.BLOWGUN.get());
        excluded.add(ItemRegistry.ROPE_COIL.get());
        excluded.add(BlockRegistry.VERDANT_CONDUIT.get().asItem());

        WoodSets.WOOD_SETS.forEach(woodSet -> {
            woodSet.getItemProvider().getEntries().forEach(registryObject -> excluded.add(registryObject.get()));
        });


        return super.getKnownItems().filter(entry -> !excluded.contains(entry.value()));
    }

    private String name(Block block) {
        return this.key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private void basicItem(Item item) {
        itemModels.generateFlatItem(item, item, ModelTemplates.FLAT_ITEM);
    }

    private void handheldItem(Item item) {
        itemModels.generateFlatItem(item, item, ModelTemplates.FLAT_HANDHELD_ITEM);
    }

    public void tippedArrow(Item arrowItem) {
        ResourceLocation resourcelocation = itemModels.generateLayeredItem(
                arrowItem,
                ModelLocationUtils.getModelLocation(arrowItem, "_head"),
                ModelLocationUtils.getModelLocation(arrowItem, "_base")
        );
        itemModels.addPotionTint(arrowItem, resourcelocation);
    }

    protected void simpleBlockWithItem(Block block) {
        blockModels.createTrivialBlock(block, TexturedModel.CUBE);
    }

    protected void simpleBlockWithItem(Block block, String renderType) {
        blockModels.createTrivialBlock(
                block,
                TexturedModel.CUBE.updateTemplate(template -> template.extend().renderType(renderType).build())
        );
    }

    protected void fishTrapWithItem(Block block) {
        blockModels.blockStateOutput.accept(createFishTrapBlock(
                block,
                VerdantTexturedModel.FISH_TRAP.create(block, blockModels.modelOutput)
        ));
    }

    protected void tumbledBlockWithItem(Block block) {
        tumbledBlockWithItem(block, null);
    }

    protected void mirroredColumnBlock(Block block) {
        TextureMapping mapping = TextureMapping.column(block);
        TextureMapping altMapping = VerdantTextureMapping.columnAlt(block);


        TextureMapping[] mappings = new TextureMapping[]{mapping, altMapping};
        String[] suffixes = new String[]{"", "_alt"};

        blockModels.blockStateOutput.accept(createMirroredColumnGenerator(
                block,
                blockModels.modelOutput,
                mappings,
                suffixes
        ));
    }


    protected void tumbledBlockWithItem(Block block, String renderType) {
        TexturedModel.Provider model = TexturedModel.CUBE;

        if (renderType != null) {
            model = model.updateTemplate(template -> template.extend().renderType(renderType).build());
        }

        blockModels.blockStateOutput.accept(createTumbledBlock(block, model.create(block, blockModels.modelOutput)));
    }

    protected void tumbledOverlaidBlockWithItem(Block block, Block base, String... overlays) {
        BiFunction<String, Block, TexturedModel.Provider> baseModel = VerdantTexturedModel.OVERLAID_CUBE;

        Function<String, TexturedModel.Provider> model = (lambdaOverlay) -> baseModel.apply(lambdaOverlay, base)
                .updateTemplate(template -> template.extend().renderType("cutout").build());

        blockModels.blockStateOutput.accept(createTumbledOverlaidBlock(block, model, overlays));
    }

    protected void overlaidBlockWithItem(Block block, Block base, String... overlays) {
        BiFunction<String, Block, TexturedModel.Provider> baseModel = VerdantTexturedModel.OVERLAID_CUBE;

        Function<String, TexturedModel.Provider> model = (lambdaOverlay) -> baseModel.apply(lambdaOverlay, base)
                .updateTemplate(template -> template.extend().renderType("cutout").build());

        blockModels.blockStateOutput.accept(createOverlaidBlock(block, model, overlays));
    }

    protected void trapBlock(Block block) {
        BiFunction<Integer, Boolean, TexturedModel.Provider> baseModel = VerdantTexturedModel.TRAP;

        BiFunction<Integer, Boolean, TexturedModel.Provider> model = (stage, isHidden) -> baseModel.apply(
                stage,
                isHidden
        ).updateTemplate(template -> template.extend().renderType("cutout").build());

        blockModels.blockStateOutput.accept(createTrapBlock(block, model));
    }

    protected void hugeAloeBlock(Block block) {
        BiFunction<Integer, Integer, TexturedModel.Provider> baseModel = VerdantTexturedModel.HUGE_ASTERISK_FOR_ALOE;

        BiFunction<Integer, Integer, TexturedModel.Provider> model = (age, height) -> baseModel.apply(age, height)
                .updateTemplate(template -> template.extend().renderType("cutout").build());

        blockModels.blockStateOutput.accept(createHugeAloeBlock(block, model));
    }

    protected void blastingBlossom(Block block) {
        blockModels.blockStateOutput.accept(createBlastingBlossom(
                block,
                (i) -> VerdantTexturedModel.BLASTING_BLOSSOM.apply(i)
                        .get(block)
                        .updateTemplate(template -> template.extend().renderType("cutout").build())
                        .createWithSuffix(block, "_stage" + i, blockModels.modelOutput)
        ));
    }

    protected void blastingBunch(Block block) {
        blockModels.blockStateOutput.accept(createBlastingBunch(
                block,
                (i) -> VerdantTexturedModel.BLASTING_BUNCH.apply(i)
                        .get(block)
                        .updateTemplate(template -> template.extend().renderType("cutout").build())
                        .createWithSuffix(block, "_stack" + i, blockModels.modelOutput)
        ));
    }


    protected void spikesBlockWithItem(Block block) {
        blockModels.blockStateOutput.accept(createSpikesBlock(
                block,
                VerdantTexturedModel.SPIKES.updateTemplate(template -> template.extend().renderType("cutout").build())
                        .create(block, blockModels.modelOutput)
        ));
    }

    protected void doubleSidedLogBlockWithItem(Block block) {
        blockModels.blockStateOutput.accept(createDoubleSidedLogBlock(
                block,
                VerdantTexturedModel.DOUBLE_SIDED_LOG.create(block, blockModels.modelOutput)
        ));
    }

    protected void rotatedTopOverlaidBlockWithItem(Block block, Block base, String topOverlay, String[] overlays) {
        TriFunction<String, String, Block, TexturedModel.Provider> baseModel = VerdantTexturedModel.TOP_OVERLAID_CUBE;

        Function<String, TexturedModel.Provider> model = (lambdaOverlay) -> baseModel.apply(
                        lambdaOverlay,
                        topOverlay,
                        base
                )
                .updateTemplate(template -> template.extend().renderType("cutout").build());

        blockModels.blockStateOutput.accept(createRotatedTopOverlaidBlock(block, model, overlays));
    }


    public void createPlantWithDefaultItem(Block block, Block pottedBlock, BlockModelGenerators.PlantType plantType, String renderType) {
        blockModels.registerSimpleItemModel(block.asItem(), plantType.createItemModel(blockModels, block));
        createPlant(block, pottedBlock, plantType, renderType);
    }

    public void createPlantWithDefaultItemWithCustomPottedTexture(Block block, Block pottedBlock, ResourceLocation customTexture, BlockModelGenerators.PlantType plantType, String renderType) {
        blockModels.registerSimpleItemModel(block.asItem(), plantType.createItemModel(blockModels, block));
        createPlantWithCustomPottedTexture(block, pottedBlock, customTexture, plantType, renderType);
    }

    public void createPlant(Block block, Block pottedBlock, BlockModelGenerators.PlantType plantType, String renderType) {
        createCrossBlock(block, plantType, renderType);
        TextureMapping texturemapping = plantType.getPlantTextureMapping(block);
        ResourceLocation resourcelocation = plantType.getCrossPot()
                .extend()
                .renderType(renderType)
                .build()
                .create(pottedBlock, texturemapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pottedBlock, resourcelocation));
    }

    public void createPlantWithCustomPottedTexture(Block block, Block pottedBlock, ResourceLocation customPottedTexture, BlockModelGenerators.PlantType plantType, String renderType) {
        createCrossBlock(block, plantType, renderType);
        TextureMapping texturemapping = TextureMapping.singleSlot(TextureSlot.PLANT, customPottedTexture);

        ResourceLocation resourcelocation = plantType.getCrossPot()
                .extend()
                .renderType(renderType)
                .build()
                .create(pottedBlock, texturemapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pottedBlock, resourcelocation));
    }

    public void createPottedOnly(Block block, Block pottedBlock, BlockModelGenerators.PlantType plantType, String renderType) {
        TextureMapping texturemapping = plantType.getPlantTextureMapping(block);
        ResourceLocation resourcelocation = plantType.getCrossPot()
                .extend()
                .renderType(renderType)
                .build()
                .create(pottedBlock, texturemapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(pottedBlock, resourcelocation));
    }

    public void createCrossBlock(Block block, BlockModelGenerators.PlantType plantType, String renderType) {
        TextureMapping texturemapping = plantType.getTextureMapping(block);
        createCrossBlock(block, plantType, texturemapping, renderType);
    }

    public void createCrossBlock(Block block, BlockModelGenerators.PlantType plantType, TextureMapping textureMapping, String renderType) {
        ResourceLocation resourcelocation = plantType.getCross()
                .extend()
                .renderType(renderType)
                .build()
                .create(block, textureMapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, resourcelocation));
    }

    public void createCrossBlockWithoutItem(Block block, BlockModelGenerators.PlantType plantType, String renderType, Property<Integer> ageProperty, int... possibleValues) {
        if (ageProperty.getPossibleValues().size() != possibleValues.length) {
            throw new IllegalArgumentException("missing values for property: " + ageProperty);
        } else {
            PropertyDispatch propertydispatch = PropertyDispatch.property(ageProperty).generate(p_388685_ -> {
                String s = "_stage" + possibleValues[p_388685_];
                TextureMapping texturemapping = TextureMapping.cross(TextureMapping.getBlockTexture(block, s));
                ResourceLocation resourcelocation = plantType.getCross()
                        .extend()
                        .renderType(renderType)
                        .build()
                        .createWithSuffix(block, s, texturemapping, blockModels.modelOutput);
                return Variant.variant().with(VariantProperties.MODEL, resourcelocation);
            });
            blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(propertydispatch));
        }
    }

    public void createCrossBlock(Block block, BlockModelGenerators.PlantType plantType, String renderType, Property<Integer> ageProperty, int... possibleValues) {
        createCrossBlockWithoutItem(block, plantType, renderType, ageProperty, possibleValues);
        blockModels.registerSimpleFlatItemModel(block.asItem());

    }

    public void createAsteriskBlockWithoutItem(Block block, BlockModelGenerators.PlantType plantType, String renderType, Property<Integer> ageProperty, int... possibleValues) {
        if (ageProperty.getPossibleValues().size() != possibleValues.length) {
            throw new IllegalArgumentException("missing values for property: " + ageProperty);
        } else {
            PropertyDispatch propertydispatch = PropertyDispatch.property(ageProperty).generate(index -> {
                String s = "_stage" + possibleValues[index];
                TextureMapping texture = VerdantTextureMapping.asterisk(
                        TextureMapping.getBlockTexture(block),
                        TextureMapping.getBlockTexture(block, s)
                );

                ResourceLocation resourcelocation = VerdantModelTemplates.ASTERISK.extend()
                        .renderType(renderType)
                        .build()
                        .createWithSuffix(block, s, texture, blockModels.modelOutput);
                return Variant.variant().with(VariantProperties.MODEL, resourcelocation);
            });
            blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(propertydispatch));
        }
    }

    public void createAsteriskBlock(Block block, BlockModelGenerators.PlantType plantType, String renderType, Property<Integer> ageProperty, int... possibleValues) {
        createAsteriskBlockWithoutItem(block, plantType, renderType, ageProperty, possibleValues);
        blockModels.registerSimpleFlatItemModel(block.asItem());

    }

    public void generateSimpleSpecialItemModel(Block block, SpecialModelRenderer.Unbaked specialModel) {
        Item item = block.asItem();
        ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(item);
        itemModels.itemModelOutput.accept(item, ItemModelUtils.specialModel(resourcelocation, specialModel));
    }
}