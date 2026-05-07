package com.startraveler.verdant.data;

import com.google.common.collect.Streams;
import com.mojang.math.Quadrant;
import com.startraveler.verdant.Constants;
import com.startraveler.verdant.block.custom.*;
import com.startraveler.verdant.data.definitions.*;
import com.startraveler.verdant.registry.ArmorMaterialRegistry;
import com.startraveler.verdant.registry.BlockRegistry;
import com.startraveler.verdant.registry.ItemRegistry;
import com.startraveler.verdant.registry.WoodSets;
import com.startraveler.verdant.util.Util;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.color.item.CustomModelDataSource;
import net.minecraft.client.color.item.GrassColorSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.apache.commons.lang3.function.TriFunction;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    public static MultiVariantGenerator createTumbledBlock(Block block, Identifier model) {
        Variant[] variants = new Variant[4 * 4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                variants[i + 4 * j] = VariantMutator.X_ROT.withValue(Quadrant.values()[i])
                        .then(VariantMutator.Y_ROT.withValue(Quadrant.values()[i]))
                        .apply(new Variant(model));
            }
        }
        return MultiVariantGenerator.dispatch(block, BlockModelGenerators.variants(variants));
    }

    public static MultiPartGenerator createFishTrapBlock(Block block, Identifier model) {

        return MultiPartGenerator.multiPart(block)
                .with(
                        new ConditionBuilder().term(FishTrapBlock.FACING, Direction.NORTH).build(),
                        BlockModelGenerators.variants(new Variant(model))
                )
                .with(
                        new ConditionBuilder().term(FishTrapBlock.FACING, Direction.EAST).build(),
                        BlockModelGenerators.variants(new Variant(model).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                )
                .with(
                        new ConditionBuilder().term(FishTrapBlock.FACING, Direction.SOUTH).build(),
                        BlockModelGenerators.variants(new Variant(model).with(VariantMutator.Y_ROT.withValue(Quadrant.R180)))
                )
                .with(
                        new ConditionBuilder().term(FishTrapBlock.FACING, Direction.WEST).build(),
                        BlockModelGenerators.variants(new Variant(model).with(VariantMutator.Y_ROT.withValue(Quadrant.R270)))
                );
    }

    public static MultiPartGenerator createDoubleSidedLogBlock(Block block, Identifier model) {

        return MultiPartGenerator.multiPart(block)
                .with(
                        new ConditionBuilder().term(RotatedPillarBlock.AXIS, Direction.Axis.Y),
                        BlockModelGenerators.variants(new Variant(model))
                )
                .with(
                        new ConditionBuilder().term(RotatedPillarBlock.AXIS, Direction.Axis.X),
                        BlockModelGenerators.variants(new Variant(model))
                                .with(VariantMutator.X_ROT.withValue(Quadrant.R90))
                                .with(VariantMutator.Y_ROT.withValue(Quadrant.R90))
                )
                .with(
                        new ConditionBuilder().term(RotatedPillarBlock.AXIS, Direction.Axis.Z),
                        BlockModelGenerators.variants(new Variant(model))
                                .with(VariantMutator.X_ROT.withValue(Quadrant.R90))
                );
    }

    public static MultiPartGenerator createSpikesBlock(Block block, Identifier model) {
        return MultiPartGenerator.multiPart(block).with(
                new ConditionBuilder().term(SpikesBlock.FACING, Direction.UP),
                BlockModelGenerators.variants(new Variant(model)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90))
        ).with(
                new ConditionBuilder().term(SpikesBlock.FACING, Direction.DOWN),
                BlockModelGenerators.variants(new Variant(model))
                        .with(VariantMutator.X_ROT.withValue(Quadrant.R180))
                        .with(VariantMutator.Y_ROT.withValue(Quadrant.R90))
        ).with(
                new ConditionBuilder().term(SpikesBlock.FACING, Direction.EAST),
                BlockModelGenerators.variants(new Variant(model))
                        .with(VariantMutator.X_ROT.withValue(Quadrant.R90))
                        .with(VariantMutator.Y_ROT.withValue(Quadrant.R90))
        ).with(
                new ConditionBuilder().term(SpikesBlock.FACING, Direction.SOUTH),
                BlockModelGenerators.variants(new Variant(model))
                        .with(VariantMutator.X_ROT.withValue(Quadrant.R90))
                        .with(VariantMutator.Y_ROT.withValue(Quadrant.R180))
        ).with(
                new ConditionBuilder().term(SpikesBlock.FACING, Direction.WEST),
                BlockModelGenerators.variants(new Variant(model))
                        .with(VariantMutator.X_ROT.withValue(Quadrant.R90))
                        .with(VariantMutator.Y_ROT.withValue(Quadrant.R270))
        );
    }

    public static MultiPartGenerator createBlastingBlossom(Block block, Function<Integer, Identifier> modelFunction) {
        MultiPartGenerator generator = MultiPartGenerator.multiPart(block);
        for (int i : BombFlowerCropBlock.AGE.getPossibleValues()) {
            Identifier model = modelFunction.apply(i);
            generator = generator.with(
                    new ConditionBuilder().term(BombFlowerCropBlock.FACING, Direction.UP)
                            .term(BombFlowerCropBlock.AGE, i),
                    BlockModelGenerators.variants(new Variant(model)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90))
            ).with(
                    new ConditionBuilder().term(BombFlowerCropBlock.FACING, Direction.DOWN)
                            .term(BombFlowerCropBlock.AGE, i),
                    BlockModelGenerators.variants(new Variant(model))
                            .with(VariantMutator.Y_ROT.withValue(Quadrant.R270))
                            .with(VariantMutator.X_ROT.withValue(Quadrant.R180))
            ).with(
                    new ConditionBuilder().term(BombFlowerCropBlock.FACING, Direction.EAST)
                            .term(BombFlowerCropBlock.AGE, i),
                    BlockModelGenerators.variants(new Variant(model))
                            .with(VariantMutator.X_ROT.withValue(Quadrant.R90))
                            .with(VariantMutator.Y_ROT.withValue(Quadrant.R90))
            ).with(
                    new ConditionBuilder().term(BombFlowerCropBlock.FACING, Direction.SOUTH)
                            .term(BombFlowerCropBlock.AGE, i),
                    BlockModelGenerators.variants(new Variant(model))
                            .with(VariantMutator.X_ROT.withValue(Quadrant.R90))
                            .with(VariantMutator.Y_ROT.withValue(Quadrant.R180))
            ).with(
                    new ConditionBuilder().term(BombFlowerCropBlock.FACING, Direction.NORTH)
                            .term(BombFlowerCropBlock.AGE, i),
                    BlockModelGenerators.variants(new Variant(model)).with(VariantMutator.X_ROT.withValue(Quadrant.R90))
            ).with(
                    new ConditionBuilder().term(BombFlowerCropBlock.FACING, Direction.WEST)
                            .term(BombFlowerCropBlock.AGE, i),
                    BlockModelGenerators.variants(new Variant(model))
                            .with(VariantMutator.X_ROT.withValue(Quadrant.R90))
                            .with(VariantMutator.Y_ROT.withValue(Quadrant.R270))
            );
        }

        return generator;
    }

    @SuppressWarnings("unused")
    public static MultiPartGenerator createOozeFissure(Block block, Identifier model) {
        MultiPartGenerator generator = MultiPartGenerator.multiPart(block);
        generator = generator.with(
                new ConditionBuilder().term(BombFlowerCropBlock.FACING, Direction.UP),
                BlockModelGenerators.variants(new Variant(model)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90))
        ).with(
                new ConditionBuilder().term(BombFlowerCropBlock.FACING, Direction.DOWN),
                BlockModelGenerators.variants(new Variant(model))
                        .with(VariantMutator.Y_ROT.withValue(Quadrant.R270))
                        .with(VariantMutator.X_ROT.withValue(Quadrant.R180))
        ).with(
                new ConditionBuilder().term(BombFlowerCropBlock.FACING, Direction.EAST),
                BlockModelGenerators.variants(new Variant(model))
                        .with(VariantMutator.X_ROT.withValue(Quadrant.R90))
                        .with(VariantMutator.Y_ROT.withValue(Quadrant.R90))
        ).with(
                new ConditionBuilder().term(BombFlowerCropBlock.FACING, Direction.SOUTH),
                BlockModelGenerators.variants(new Variant(model))
                        .with(VariantMutator.X_ROT.withValue(Quadrant.R90))
                        .with(VariantMutator.Y_ROT.withValue(Quadrant.R180))
        ).with(
                new ConditionBuilder().term(BombFlowerCropBlock.FACING, Direction.NORTH),
                BlockModelGenerators.variants(new Variant(model)).with(VariantMutator.X_ROT.withValue(Quadrant.R90))
        ).with(
                new ConditionBuilder().term(BombFlowerCropBlock.FACING, Direction.WEST),
                BlockModelGenerators.variants(new Variant(model))
                        .with(VariantMutator.X_ROT.withValue(Quadrant.R90))
                        .with(VariantMutator.Y_ROT.withValue(Quadrant.R270))
        );

        return generator;
    }

    public static MultiPartGenerator createBlastingBunch(Block block, Function<Integer, Identifier> modelFunction) {
        MultiPartGenerator generator = MultiPartGenerator.multiPart(block);
        for (int i : BombPileBlock.BOMBS.getPossibleValues()) {
            Identifier model = modelFunction.apply(i);
            generator = generator.with(
                    new ConditionBuilder().term(BombPileBlock.FACING, Direction.EAST).term(BombPileBlock.BOMBS, i),
                    BlockModelGenerators.variants(new Variant(model)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90))
            ).with(
                    new ConditionBuilder().term(BombPileBlock.FACING, Direction.SOUTH).term(BombPileBlock.BOMBS, i),
                    BlockModelGenerators.variants(new Variant(model))
                            .with(VariantMutator.Y_ROT.withValue(Quadrant.R180))
            ).with(
                    new ConditionBuilder().term(BombPileBlock.FACING, Direction.WEST).term(BombPileBlock.BOMBS, i),
                    BlockModelGenerators.variants(new Variant(model))
                            .with(VariantMutator.Y_ROT.withValue(Quadrant.R270))
            ).with(
                    new ConditionBuilder().term(BombPileBlock.FACING, Direction.NORTH).term(BombPileBlock.BOMBS, i),
                    BlockModelGenerators.variants(new Variant(model))
            );
        }

        return generator;
    }


    public static MultiVariantGenerator createMirroredColumnGenerator(Block columnBlock, BiConsumer<Identifier, ModelInstance> modelOutput, TextureMapping[] mappings, String[] suffixes) {
        Stream<Identifier> mirrored = Util.zip(
                Arrays.stream(mappings),
                Arrays.stream(suffixes),
                (mapping, suffix) -> ModelTemplates.CUBE_COLUMN_MIRRORED.createWithSuffix(
                        columnBlock,
                        suffix,
                        mapping,
                        modelOutput
                )
        );
        Stream<Identifier> normal = Util.zip(
                Arrays.stream(mappings),
                Arrays.stream(suffixes),
                (mapping, suffix) -> ModelTemplates.CUBE_COLUMN.createWithSuffix(
                        columnBlock,
                        suffix,
                        mapping,
                        modelOutput
                )
        );
        Stream<Identifier> merged = Streams.concat(mirrored, normal);
        return createRotatedVariant(
                columnBlock,
                merged.toArray(Identifier[]::new)
        ).with(BlockModelGenerators.createRotatedPillar());
    }

    public static MultiVariantGenerator createRotatedVariant(Block block, Identifier... models) {
        return MultiVariantGenerator.dispatch(
                block, BlockModelGenerators.variants(Stream.concat(
                        Arrays.stream(models).map(Variant::new),
                        Arrays.stream(models)
                                .map(model -> new Variant(model).with(VariantMutator.Y_ROT.withValue(Quadrant.R180)))


                ).toArray(Variant[]::new))
        );
    }

    public static Identifier createFlatItemModelWithBlockTextureAndOverlay(BlockModelGenerators generators, Item item, Block block, String baseSuffix, String overlaySuffix) {
        Identifier identifier = TextureMapping.getBlockTexture(block, baseSuffix);
        Identifier identifier1 = TextureMapping.getBlockTexture(block, overlaySuffix);
        return ModelTemplates.TWO_LAYERED_ITEM.create(
                ModelLocationUtils.getModelLocation(item),
                TextureMapping.layered(identifier, identifier1),
                generators.modelOutput
        );
    }

    public MultiPartGenerator createHugeAloeBlock(Block block, BiFunction<Integer, Integer, TexturedModel.Provider> model) {

        MultiPartGenerator generator = MultiPartGenerator.multiPart(block);

        for (int i = 0; i <= HugeAloeCropBlock.MAX_AGE; i++) {

            for (int j = HugeAloeCropBlock.MIN_COORD; j <= HugeAloeCropBlock.MAX_COORD; j++) {
                Identifier location = model.apply(i, j).createWithSuffix(
                        block,
                        (j == 0 ? "_base" : j == 1 ? "_middle" : "_top") + "_stage" + i,
                        blockModels.modelOutput
                );


                generator = generator.with(
                        new ConditionBuilder().term(HugeAloeCropBlock.Y_PROPERTY, j)
                                .term(HugeAloeCropBlock.Z_PROPERTY, HugeAloeCropBlock.CENTER_COORD)
                                .term(HugeAloeCropBlock.X_PROPERTY, HugeAloeCropBlock.CENTER_COORD)
                                .term(HugeAloeCropBlock.AGE, i), BlockModelGenerators.variants(new Variant(location))
                );
            }
        }

        return generator;
    }

    public MultiPartGenerator createSkullBlock(Block block, Function<Integer, TexturedModel.Provider> model) {

        MultiPartGenerator generator = MultiPartGenerator.multiPart(block);

        Identifier location0 = model.apply(0).createWithSuffix(block, "_rot0", blockModels.modelOutput);
        Identifier location1 = model.apply(1).createWithSuffix(block, "_rot1", blockModels.modelOutput);
        Identifier location2 = model.apply(2).createWithSuffix(block, "_rot2", blockModels.modelOutput);
        Identifier location3 = model.apply(3).createWithSuffix(block, "_rot3", blockModels.modelOutput);
        generator = generator.with(
                new ConditionBuilder().term(SimpleSkullBlock.ROTATION, 0),
                BlockModelGenerators.variants(new Variant(location0).with(VariantMutator.Y_ROT.withValue(Quadrant.R0)))
        );
        generator = generator.with(
                new ConditionBuilder().term(SimpleSkullBlock.ROTATION, 1),
                BlockModelGenerators.variants(new Variant(location1).with(VariantMutator.Y_ROT.withValue(Quadrant.R0)))
        );
        generator = generator.with(
                new ConditionBuilder().term(SimpleSkullBlock.ROTATION, 2),
                BlockModelGenerators.variants(new Variant(location2).with(VariantMutator.Y_ROT.withValue(Quadrant.R0)))
        );
        generator = generator.with(
                new ConditionBuilder().term(SimpleSkullBlock.ROTATION, 3),
                BlockModelGenerators.variants(new Variant(location3).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
        );

        generator = generator.with(
                new ConditionBuilder().term(SimpleSkullBlock.ROTATION, 4),
                BlockModelGenerators.variants(new Variant(location0).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
        );
        generator = generator.with(
                new ConditionBuilder().term(SimpleSkullBlock.ROTATION, 5),
                BlockModelGenerators.variants(new Variant(location1).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
        );
        generator = generator.with(
                new ConditionBuilder().term(SimpleSkullBlock.ROTATION, 6),
                BlockModelGenerators.variants(new Variant(location2).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
        );
        generator = generator.with(
                new ConditionBuilder().term(SimpleSkullBlock.ROTATION, 7),
                BlockModelGenerators.variants(new Variant(location3).with(VariantMutator.Y_ROT.withValue(Quadrant.R180)))
        );

        generator = generator.with(
                new ConditionBuilder().term(SimpleSkullBlock.ROTATION, 8),
                BlockModelGenerators.variants(new Variant(location0).with(VariantMutator.Y_ROT.withValue(Quadrant.R180)))
        );
        generator = generator.with(
                new ConditionBuilder().term(SimpleSkullBlock.ROTATION, 9),
                BlockModelGenerators.variants(new Variant(location1).with(VariantMutator.Y_ROT.withValue(Quadrant.R180)))
        );
        generator = generator.with(
                new ConditionBuilder().term(SimpleSkullBlock.ROTATION, 10),
                BlockModelGenerators.variants(new Variant(location2).with(VariantMutator.Y_ROT.withValue(Quadrant.R180)))
        );
        generator = generator.with(
                new ConditionBuilder().term(SimpleSkullBlock.ROTATION, 11),
                BlockModelGenerators.variants(new Variant(location3).with(VariantMutator.Y_ROT.withValue(Quadrant.R270)))
        );

        generator = generator.with(
                new ConditionBuilder().term(SimpleSkullBlock.ROTATION, 12),
                BlockModelGenerators.variants(new Variant(location0).with(VariantMutator.Y_ROT.withValue(Quadrant.R270)))
        );
        generator = generator.with(
                new ConditionBuilder().term(SimpleSkullBlock.ROTATION, 13),
                BlockModelGenerators.variants(new Variant(location1).with(VariantMutator.Y_ROT.withValue(Quadrant.R270)))
        );
        generator = generator.with(
                new ConditionBuilder().term(SimpleSkullBlock.ROTATION, 14),
                BlockModelGenerators.variants(new Variant(location2).with(VariantMutator.Y_ROT.withValue(Quadrant.R270)))
        );
        generator = generator.with(
                new ConditionBuilder().term(SimpleSkullBlock.ROTATION, 15),
                BlockModelGenerators.variants(new Variant(location3).with(VariantMutator.Y_ROT.withValue(Quadrant.R0)))
        );

        return generator;
    }

    public MultiPartGenerator createWallSkullBlock(Block block, Block skull, TexturedModel.Provider model) {

        MultiPartGenerator generator = MultiPartGenerator.multiPart(block);
        Identifier location = model.create(skull, blockModels.modelOutput);
        generator = generator.with(
                new ConditionBuilder().term(SimpleWallSkullBlock.FACING, Direction.NORTH),
                BlockModelGenerators.variants(new Variant(location).with(VariantMutator.Y_ROT.withValue(Quadrant.R0)))
        );
        generator = generator.with(
                new ConditionBuilder().term(SimpleWallSkullBlock.FACING, Direction.EAST),
                BlockModelGenerators.variants(new Variant(location).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
        );
        generator = generator.with(
                new ConditionBuilder().term(SimpleWallSkullBlock.FACING, Direction.SOUTH),
                BlockModelGenerators.variants(new Variant(location).with(VariantMutator.Y_ROT.withValue(Quadrant.R180)))
        );
        generator = generator.with(
                new ConditionBuilder().term(SimpleWallSkullBlock.FACING, Direction.WEST),
                BlockModelGenerators.variants(new Variant(location).with(VariantMutator.Y_ROT.withValue(Quadrant.R270)))
        );

        return generator;
    }

    public MultiPartGenerator createTrapBlock(Block block, BiFunction<Integer, Boolean, TexturedModel.Provider> model) {

        MultiPartGenerator generator = MultiPartGenerator.multiPart(block);

        for (int i = 0; i < 4; i++) {
            for (Boolean b : new Boolean[]{true, false}) {
                Identifier location = model.apply(i, b)
                        .createWithSuffix(block, (b ? "_hidden" : "") + "_stage" + i, blockModels.modelOutput);


                generator = generator.with(
                        new ConditionBuilder().term(TrapBlock.FACING, Direction.NORTH)
                                .term(TrapBlock.STAGE, i)
                                .term(TrapBlock.HIDDEN, b),
                        BlockModelGenerators.variants(new Variant(location).with(VariantMutator.Y_ROT.withValue(Quadrant.R0)))
                );
                generator = generator.with(
                        new ConditionBuilder().term(TrapBlock.FACING, Direction.EAST)
                                .term(TrapBlock.STAGE, i)
                                .term(TrapBlock.HIDDEN, b),
                        BlockModelGenerators.variants(new Variant(location).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                );
                generator = generator.with(
                        new ConditionBuilder().term(TrapBlock.FACING, Direction.SOUTH)
                                .term(TrapBlock.STAGE, i)
                                .term(TrapBlock.HIDDEN, b),
                        BlockModelGenerators.variants(new Variant(location).with(VariantMutator.Y_ROT.withValue(Quadrant.R180)))
                );
                generator = generator.with(
                        new ConditionBuilder().term(TrapBlock.FACING, Direction.WEST)
                                .term(TrapBlock.STAGE, i)
                                .term(TrapBlock.HIDDEN, b),
                        BlockModelGenerators.variants(new Variant(location).with(VariantMutator.Y_ROT.withValue(Quadrant.R270)))
                );
            }
        }

        return generator;
    }

    protected MultiVariant createRotatedTopOverlaidBlock(Block block, Function<String, TexturedModel.Provider> model, String[] overlays, String modelSuffix) {
        Variant[] variants = new Variant[4 * overlays.length];
        for (int o = 0; o < overlays.length; o++) {
            String overlay = overlays[o];
            Identifier modelLocation = model.apply(overlay).createWithSuffix(
                    block,
                    (overlays.length == 1 || overlay.equals("default") || overlay.equals("overlay_default") ? "" : "_" + overlay) + modelSuffix,
                    blockModels.modelOutput
            );
            for (int j = 0; j < 4; j++) {
                variants[o * 4 + j] = new Variant(modelLocation).with(VariantMutator.Y_ROT.withValue(Quadrant.values()[j]));
            }
        }
        return BlockModelGenerators.variants(variants);
    }

    protected MultiVariantGenerator createOverlaidBlock(Block block, Function<String, TexturedModel.Provider> model, String[] overlays) {
        Variant[] variants = new Variant[overlays.length];
        for (int o = 0; o < overlays.length; o++) {
            String overlay = overlays[o];
            String trimmedOverlay = overlay.substring(overlay.lastIndexOf('/') + 1);
            Identifier modelLocation = model.apply(overlay).createWithSuffix(
                    block,
                    (overlays.length == 1 || trimmedOverlay.equals("default") || trimmedOverlay.equals("overlay_default")) ? "" : "_" + trimmedOverlay,
                    blockModels.modelOutput
            );
            variants[o] = new Variant(modelLocation);

        }
        return MultiVariantGenerator.dispatch(block, BlockModelGenerators.variants(variants));
    }

    protected MultiVariantGenerator createTumbledOverlaidBlock(Block block, Function<String, TexturedModel.Provider> model, String[] overlays) {
        Variant[] variants = new Variant[4 * 4 * overlays.length];
        for (int o = 0; o < overlays.length; o++) {
            String overlay = overlays[o];
            String trimmedOverlay = overlay.substring(overlay.lastIndexOf('/') + 1);
            Identifier modelLocation = model.apply(overlay).createWithSuffix(
                    block,
                    (overlays.length == 1 || trimmedOverlay.equals("default") || trimmedOverlay.equals("overlay_default")) ? "" : "_" + trimmedOverlay,
                    blockModels.modelOutput
            );
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    variants[o * 16 + i * 4 + j] = new Variant(modelLocation).with(VariantMutator.X_ROT.withValue(
                            Quadrant.values()[i])).with(VariantMutator.Y_ROT.withValue(Quadrant.values()[j]));
                }
            }
        }
        return MultiVariantGenerator.dispatch(block, BlockModelGenerators.variants(variants));
    }

    public void candleCake(Block candleBlock, Block cakeBlock, Block candleCakeBlock) {
        this.blockModels.registerSimpleFlatItemModel(candleBlock.asItem());
        Identifier candleCakeBase = ModelTemplates.CANDLE_CAKE.create(
                candleCakeBlock,
                VerdantTextureMapping.candleCake(cakeBlock, candleBlock, false),
                this.blockModels.modelOutput
        );
        Identifier candleCakeLit = ModelTemplates.CANDLE_CAKE.createWithSuffix(
                candleCakeBlock,
                "_lit",
                VerdantTextureMapping.candleCake(cakeBlock, candleBlock, true),
                this.blockModels.modelOutput
        );
        this.blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(candleCakeBlock)
                .with(BlockModelGenerators.createBooleanModelDispatch(
                        BlockStateProperties.LIT,
                        BlockModelGenerators.variant(new Variant(candleCakeLit)),
                        BlockModelGenerators.variant(new Variant(candleCakeBase))
                )));
    }

    public void cakeBlock(Block cake, Item cakeItem) {
        this.blockModels.registerSimpleFlatItemModel(cakeItem);
        this.blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(cake)
                .with(PropertyDispatch.initial(BlockStateProperties.BITES)
                        .generate(n -> BlockModelGenerators.variant(new Variant(ModelLocationUtils.getModelLocation(
                                cake,
                                "_slice" + n
                        ))))));
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        this.blockModels = blockModels;
        this.itemModels = itemModels;

        simpleBlockWithItem(BlockRegistry.OVERGROWN_SPAWNER.get(), "cutout");

        fishTrapWithItem(BlockRegistry.FISH_TRAP.get());
        tumbledBlockWithItem(BlockRegistry.ANTIGORITE.get());
        tumbledBlockWithItem(BlockRegistry.ROTTEN_WOOD.get());
        tumbledOverlaidBlockWithItem(BlockRegistry.VERDANT_ROOTED_DIRT.get(), Blocks.DIRT, -1, VERDANT_OVERLAYS);
        rotatedTopOverlaidBlockWithItem(
                BlockRegistry.VERDANT_GRASS_DIRT.get(),
                Blocks.DIRT,
                "verdant_grass",
                -12012264,
                VERDANT_OVERLAYS
        );
        tumbledOverlaidBlockWithItem(BlockRegistry.VERDANT_ROOTED_MUD.get(), Blocks.MUD, -1, VERDANT_OVERLAYS);
        rotatedTopOverlaidBlockWithItem(
                BlockRegistry.VERDANT_GRASS_MUD.get(),
                Blocks.MUD,
                "verdant_grass",
                -12012264,
                VERDANT_OVERLAYS
        );
        tumbledOverlaidBlockWithItem(BlockRegistry.VERDANT_ROOTED_CLAY.get(), Blocks.CLAY, -1, VERDANT_OVERLAYS);
        rotatedTopOverlaidBlockWithItem(
                BlockRegistry.VERDANT_GRASS_CLAY.get(),
                Blocks.CLAY,
                "verdant_grass",
                -12012264,
                VERDANT_OVERLAYS
        );
        tumbledOverlaidBlockWithItem(
                BlockRegistry.VERDANT_ROOTED_GRUS.get(),
                BlockRegistry.GRUS.get(),
                -1,
                VERDANT_OVERLAYS
        );
        rotatedTopOverlaidBlockWithItem(
                BlockRegistry.VERDANT_GRASS_GRUS.get(),
                BlockRegistry.GRUS.get(),
                "verdant_grass",
                -12012264,
                VERDANT_OVERLAYS
        );
        tumbledBlockWithItem(BlockRegistry.PACKED_GRAVEL.get());
        tumbledBlockWithItem(BlockRegistry.FUSED_GRAVEL.get());
        blockModels.createTintedLeaves(BlockRegistry.WILTED_STRANGLER_LEAVES.get(), TexturedModel.LEAVES, -12012264);
        blockModels.createTintedLeaves(BlockRegistry.STRANGLER_LEAVES.get(), TexturedModel.LEAVES, -12012264);
        createTintedOverlaidLeaves(
                BlockRegistry.THORNY_STRANGLER_LEAVES.get(),
                BlockRegistry.STRANGLER_LEAVES.get(),
                -12012264,
                "thorns_overlay"
        );
        createTintedOverlaidLeaves(
                BlockRegistry.POISON_STRANGLER_LEAVES.get(),
                BlockRegistry.STRANGLER_LEAVES.get(),
                -12012264,
                "poison_ivy_overlay"
        );
        overlaidBlockWithItem(BlockRegistry.DIRT_COAL_ORE.get(), Blocks.DIRT, -1, "coal_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_COPPER_ORE.get(), Blocks.DIRT, -1, "copper_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_IRON_ORE.get(), Blocks.DIRT, -1, "iron_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_GOLD_ORE.get(), Blocks.DIRT, -1, "gold_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_LAPIS_ORE.get(), Blocks.DIRT, -1, "lapis_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_REDSTONE_ORE.get(), Blocks.DIRT, -1, "redstone_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_EMERALD_ORE.get(), Blocks.DIRT, -1, "emerald_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_DIAMOND_ORE.get(), Blocks.DIRT, -1, "diamond_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.GRUS_COAL_ORE.get(), BlockRegistry.GRUS.get(), -1, "coal_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.GRUS_COPPER_ORE.get(), BlockRegistry.GRUS.get(), -1, "copper_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.GRUS_IRON_ORE.get(), BlockRegistry.GRUS.get(), -1, "iron_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.GRUS_GOLD_ORE.get(), BlockRegistry.GRUS.get(), -1, "gold_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.GRUS_LAPIS_ORE.get(), BlockRegistry.GRUS.get(), -1, "lapis_ore_overlay");
        overlaidBlockWithItem(
                BlockRegistry.GRUS_REDSTONE_ORE.get(),
                BlockRegistry.GRUS.get(),
                -1,
                "redstone_ore_overlay"
        );
        overlaidBlockWithItem(
                BlockRegistry.GRUS_EMERALD_ORE.get(),
                BlockRegistry.GRUS.get(),
                -1,
                "emerald_ore_overlay"
        );
        overlaidBlockWithItem(
                BlockRegistry.GRUS_DIAMOND_ORE.get(),
                BlockRegistry.GRUS.get(),
                -1,
                "diamond_ore_overlay"
        );

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
                BlockModelGenerators.PlantType.TINTED,
                "cutout"
        );
        createPlantWithDefaultItem(
                BlockRegistry.WILD_COFFEE.get(),
                BlockRegistry.POTTED_WILD_COFFEE.get(),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout"
        );

        createOverlaidPlantWithDefaultItem(
                BlockRegistry.THORN_BUSH.get(),
                BlockRegistry.POTTED_THORN_BUSH.get(),
                VerdantPlantType.OVERLAID_TINTED,
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
        createCropBlock(
                BlockRegistry.CASSAVA_CROP.get(),
                "cutout",
                CassavaCropBlock.AGE,
                IntStream.range(0, CassavaCropBlock.MAX_AGE + 1).toArray()
        );
        createCropBlock(
                BlockRegistry.BITTER_CASSAVA_CROP.get(),
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

        createCropBlock(
                BlockRegistry.UBE_CROP.get(),
                "cutout",
                SpreadingCropBlock.AGE,
                IntStream.range(0, SpreadingCropBlock.MAX_AGE + 1).toArray()
        );

        tintedTrapBlock(BlockRegistry.SNAPLEAF.get());
        trapBlock(BlockRegistry.WOODEN_TRAP.get());
        trapBlock(BlockRegistry.COPPER_TRAP.get());
        trapBlock(BlockRegistry.IRON_TRAP.get());
        trapBlock(BlockRegistry.GOLDEN_TRAP.get());
        spikesBlockWithItem(BlockRegistry.WOODEN_SPIKES.get());
        spikesBlockWithItem(BlockRegistry.COPPER_SPIKES.get());
        spikesBlockWithItem(BlockRegistry.IRON_SPIKES.get());
        spikesBlockWithItem(BlockRegistry.GOLDEN_SPIKES.get());
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
                Constants.id("block/rue_potted"),
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

        mirroredColumnBlock(BlockRegistry.SCREE.get());
        mirroredColumnBlock(BlockRegistry.PACKED_SCREE.get());
        mirroredColumnBlock(BlockRegistry.FUSED_SCREE.get());

        tumbledBlockWithItem(BlockRegistry.GRUS.get());
        tumbledBlockWithItem(BlockRegistry.STONY_GRUS.get());

        blastingBlossom(BlockRegistry.BLASTING_BLOSSOM.get());

        blastingBunch(BlockRegistry.BLASTING_BUNCH.get());
        blastingBunch(BlockRegistry.METAL_BOMB_PILE.get());
        blastingBunch(BlockRegistry.TERRACOTTA_BOMB_PILE.get());

        blockModels.family(BlockRegistry.EARTH_BRICKS.get()).generateFor(VerdantBlockFamilies.EARTH_BRICKS);
        blockModels.family(BlockRegistry.VERDANT_RESIN_BRICKS.get())
                .generateFor(VerdantBlockFamilies.VERDANT_RESIN_BRICKS);

        nonrotatablePillarBlock(BlockRegistry.CHISELED_EARTH_BRICKS.get());

        blockModels.createNonTemplateModelBlock(BlockRegistry.SAP_BLOCK.get());
        simpleBlockWithItem(BlockRegistry.VERDANT_RESIN_BLOCK.get());
        blockModels.createNonTemplateModelBlock(BlockRegistry.BRAMBLE_FRAME.get());


        tumbledBlockWithItem(BlockRegistry.TOXIC_GRUS.get());

        blockModels.createTintedDoublePlant(BlockRegistry.TALL_BUSH.get());
        // TODO make this not tint the thorns.
        createOverlaidDoublePlantWithItem(BlockRegistry.TALL_THORN_BUSH.get(), VerdantPlantType.OVERLAID_TINTED);

        createFruitingTintedLeaves(
                BlockRegistry.MANGO_LEAVES.get(),
                VerdantTexturedModel.FRUITING_LEAVES,
                -12012264,
                FruitingTintedParticleLeavesBlock.STAGES
        );

        blockModels.createPlantWithDefaultItem(
                BlockRegistry.MANGO_SAPLING.get(),
                BlockRegistry.POTTED_MANGO_SAPLING.get(),
                BlockModelGenerators.PlantType.NOT_TINTED
        );

        wallSkullBlock(BlockRegistry.BRAMBLE_WALL_HEAD.get(), BlockRegistry.BRAMBLE_HEAD.get());
        skullBlock(BlockRegistry.BRAMBLE_HEAD.get());

        blockModels.createCreakingHeart(BlockRegistry.OOZE_FISSURE_BLOCK.get());

        blockModels.createNormalTorch(BlockRegistry.SAP_TORCH.get(), BlockRegistry.SAP_WALL_TORCH.get());
        blockModels.createLantern(BlockRegistry.SAP_LANTERN.get());
        createSoullikeFire(BlockRegistry.SAP_FIRE.get());

        blockModels.createFullAndCarpetBlocks(
                BlockRegistry.DEAD_MOSS_BLOCK.get(),
                BlockRegistry.DEAD_MOSS_CARPET.get()
        );

        basicItem(ItemRegistry.SAP_GLOB.get());
        basicItem(ItemRegistry.VERDANT_RESIN_BRICK.get());

        basicItem(ItemRegistry.MANGO.get());
        basicItem(ItemRegistry.GOLDEN_MANGO.get());

        basicItem(ItemRegistry.ALOE_PUP.get());

        basicItem(ItemRegistry.ROASTED_COFFEE.get());
        basicItem(ItemRegistry.THORN.get());
        basicItem(BlockRegistry.STRANGLER_VINE.get().asItem());
        basicItem(BlockRegistry.LEAFY_STRANGLER_VINE.get().asItem());
        basicItem(BlockRegistry.STRANGLER_TENDRIL.get().asItem());
        basicItem(BlockRegistry.POISON_IVY.get().asItem());
        basicItem(BlockRegistry.DROWNED_HEMLOCK.get().asItem());
        basicItem(ItemRegistry.ROPE.get());
        basicItem(ItemRegistry.TWISTED_ROPE.get());
        basicItem(BlockRegistry.WOODEN_SPIKES.get().asItem());
        basicItem(BlockRegistry.COPPER_SPIKES.get().asItem());
        basicItem(BlockRegistry.IRON_SPIKES.get().asItem());
        basicItem(BlockRegistry.GOLDEN_SPIKES.get().asItem());
        basicItem(BlockRegistry.SNAPLEAF.get().asItem());
        basicItem(BlockRegistry.WOODEN_TRAP.get().asItem());
        basicItem(BlockRegistry.COPPER_TRAP.get().asItem());
        basicItem(BlockRegistry.IRON_TRAP.get().asItem());
        basicItem(BlockRegistry.GOLDEN_TRAP.get().asItem());
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
                ItemModelGenerators.TRIM_PREFIX_HELMET,
                false
        );
        itemModels.generateTrimmableItem(
                ItemRegistry.HEARTWOOD_CHESTPLATE.get(),
                ArmorMaterialRegistry.HEARTWOOD_ASSET,
                ItemModelGenerators.TRIM_PREFIX_CHESTPLATE,
                false
        );
        itemModels.generateTrimmableItem(
                ItemRegistry.HEARTWOOD_LEGGINGS.get(),
                ArmorMaterialRegistry.HEARTWOOD_ASSET,
                ItemModelGenerators.TRIM_PREFIX_LEGGINGS,
                false
        );
        itemModels.generateTrimmableItem(
                ItemRegistry.HEARTWOOD_BOOTS.get(),
                ArmorMaterialRegistry.HEARTWOOD_ASSET,
                ItemModelGenerators.TRIM_PREFIX_BOOTS,
                false
        );
        handheldItem(ItemRegistry.HEARTWOOD_AXE.get());
        handheldItem(ItemRegistry.HEARTWOOD_HOE.get());
        handheldItem(ItemRegistry.HEARTWOOD_SHOVEL.get());
        handheldItem(ItemRegistry.HEARTWOOD_PICKAXE.get());
        handheldItem(ItemRegistry.HEARTWOOD_SWORD.get());
        itemModels.generateSpear(ItemRegistry.HEARTWOOD_SPEAR.get());


        basicItem(ItemRegistry.IMBUEMENT_UPGRADE_SMITHING_TEMPLATE.get());
        basicItem(ItemRegistry.IMBUED_HEARTWOOD_HORSE_ARMOR.get());

        itemModels.generateTrimmableItem(
                ItemRegistry.IMBUED_HEARTWOOD_HELMET.get(),
                ArmorMaterialRegistry.IMBUED_HEARTWOOD_ASSET,
                ItemModelGenerators.TRIM_PREFIX_HELMET,
                false
        );
        itemModels.generateTrimmableItem(
                ItemRegistry.IMBUED_HEARTWOOD_CHESTPLATE.get(),
                ArmorMaterialRegistry.IMBUED_HEARTWOOD_ASSET,
                ItemModelGenerators.TRIM_PREFIX_CHESTPLATE,
                false
        );
        itemModels.generateTrimmableItem(
                ItemRegistry.IMBUED_HEARTWOOD_LEGGINGS.get(),
                ArmorMaterialRegistry.IMBUED_HEARTWOOD_ASSET,
                ItemModelGenerators.TRIM_PREFIX_LEGGINGS,
                false
        );
        itemModels.generateTrimmableItem(
                ItemRegistry.IMBUED_HEARTWOOD_BOOTS.get(),
                ArmorMaterialRegistry.IMBUED_HEARTWOOD_ASSET,
                ItemModelGenerators.TRIM_PREFIX_BOOTS,
                false
        );
        handheldItem(ItemRegistry.IMBUED_HEARTWOOD_AXE.get());
        handheldItem(ItemRegistry.IMBUED_HEARTWOOD_HOE.get());
        handheldItem(ItemRegistry.IMBUED_HEARTWOOD_SHOVEL.get());
        handheldItem(ItemRegistry.IMBUED_HEARTWOOD_PICKAXE.get());
        handheldItem(ItemRegistry.IMBUED_HEARTWOOD_SWORD.get());
        itemModels.generateSpear(ItemRegistry.IMBUED_HEARTWOOD_SPEAR.get());

        basicItem(BlockRegistry.ROPE_LADDER.get().asItem());
        basicItem(ItemRegistry.TOXIC_ASH.get());
        basicItem(ItemRegistry.BUCKET_OF_TOXIC_ASH.get());
        basicItem(ItemRegistry.TOXIC_SOLUTION_BUCKET.get());
        basicItem(BlockRegistry.DEAD_GRASS.get().asItem());

        basicItem(ItemRegistry.RANCID_SLIME.get());

        basicItem(ItemRegistry.DART.get());
        tippedArrow(ItemRegistry.TIPPED_DART.get());
        // handheldItem(ItemRegistry.HUNTING_SPEAR.get());

        basicItem(ItemRegistry.ALOE_LEAF.get());
        basicItem(ItemRegistry.YOUNG_ALOE_LEAF.get());
        basicItem(ItemRegistry.OLD_ALOE_LEAF.get());

        basicItem(ItemRegistry.METAL_BOMB.get());
        basicItem(ItemRegistry.TERRACOTTA_BOMB.get());
        basicItem(ItemRegistry.STABLE_BLASTING_BLOOM.get());
        basicItem(ItemRegistry.BLASTING_BLOOM.get());
        basicItem(ItemRegistry.TERRACOTTA_GRENADE.get());
        basicItem(ItemRegistry.METAL_GRENADE.get());
        basicItem(ItemRegistry.BLASTING_BLOSSOM_SPROUT.get());

        basicItem(ItemRegistry.FRAGILE_FLASK.get());

        basicItem(ItemRegistry.THORNS_UPGRADE_SMITHING_TEMPLATE.get());

        basicItem(ItemRegistry.THORNY_HEARTWOOD_HORSE_ARMOR.get());

        itemModels.generateTrimmableItem(
                ItemRegistry.THORNY_HEARTWOOD_HELMET.get(),
                ArmorMaterialRegistry.THORNY_HEARTWOOD_ASSET,
                ItemModelGenerators.TRIM_PREFIX_HELMET,
                false
        );
        itemModels.generateTrimmableItem(
                ItemRegistry.THORNY_HEARTWOOD_CHESTPLATE.get(),
                ArmorMaterialRegistry.THORNY_HEARTWOOD_ASSET,
                ItemModelGenerators.TRIM_PREFIX_CHESTPLATE,
                false
        );
        itemModels.generateTrimmableItem(
                ItemRegistry.THORNY_HEARTWOOD_LEGGINGS.get(),
                ArmorMaterialRegistry.THORNY_HEARTWOOD_ASSET,
                ItemModelGenerators.TRIM_PREFIX_LEGGINGS,
                false
        );
        itemModels.generateTrimmableItem(
                ItemRegistry.THORNY_HEARTWOOD_BOOTS.get(),
                ArmorMaterialRegistry.THORNY_HEARTWOOD_ASSET,
                ItemModelGenerators.TRIM_PREFIX_BOOTS,
                false
        );
        handheldItem(ItemRegistry.THORNY_HEARTWOOD_AXE.get());
        handheldItem(ItemRegistry.THORNY_HEARTWOOD_HOE.get());
        handheldItem(ItemRegistry.THORNY_HEARTWOOD_SHOVEL.get());
        handheldItem(ItemRegistry.THORNY_HEARTWOOD_PICKAXE.get());
        handheldItem(ItemRegistry.THORNY_HEARTWOOD_SWORD.get());
        itemModels.generateSpear(ItemRegistry.THORNY_HEARTWOOD_SPEAR.get());

        basicItem(ItemRegistry.ROOTED_SPAWN_EGG.get());
        basicItem(ItemRegistry.TIMBERMITE_SPAWN_EGG.get());
        basicItem(ItemRegistry.SKULL_SPIDER_SPAWN_EGG.get());
        basicItem(ItemRegistry.POISONER_SPAWN_EGG.get());
        basicItem(ItemRegistry.OOZE_SPAWN_EGG.get());

        itemModels.generateBundleModels(ItemRegistry.SACK.get());
        basicItem(ItemRegistry.MULCH_PILE.get());
        basicItem(ItemRegistry.LARGE_MULCH_PILE.get());
        basicItem(ItemRegistry.MULCH_BUCKET.get());

        basicItem(ItemRegistry.JUICE_BOTTLE.get());
        basicItem(ItemRegistry.NECTAR_BOTTLE.get());

        basicItem(ItemRegistry.BALSAM.get());
        basicItem(ItemRegistry.BALM.get());

        basicItem(ItemRegistry.OOZE_BUCKET.get());
        basicItem(ItemRegistry.VERDANT_RESIN_CLUMP.get());

        handheldItem(ItemRegistry.EARTHMOVER.get());
        handheldItem(ItemRegistry.COPPER_MACHETE.get());
        handheldItem(ItemRegistry.IRON_MACHETE.get());
        handheldItem(ItemRegistry.DIAMOND_MACHETE.get());
        handheldItem(ItemRegistry.NETHERITE_MACHETE.get());

    }

    @Override
    protected @NotNull Stream<? extends Holder<Block>> getKnownBlocks() {
        List<Block> excluded = new ArrayList<>();

        excluded.add(BlockRegistry.STRANGLER_VINE.get());
        excluded.add(BlockRegistry.LEAFY_STRANGLER_VINE.get());
        excluded.add(BlockRegistry.ROPE.get());
        excluded.add(BlockRegistry.TWISTED_ROPE.get());
        excluded.add(BlockRegistry.ROPE_LADDER.get());
        excluded.add(BlockRegistry.ROPE_HOOK.get());
        excluded.add(BlockRegistry.TWISTED_ROPE_HOOK.get());
        excluded.add(BlockRegistry.STINKING_BLOSSOM.get());
        excluded.add(BlockRegistry.VERDANT_CONDUIT.get());
        excluded.add(BlockRegistry.OVERGROWN_SPAWNER.get());
        WoodSets.WOOD_SETS.forEach(woodSet -> woodSet.getBlockProvider()
                .getEntries()
                .forEach(registryObject -> excluded.add(registryObject.get())));

        return super.getKnownBlocks().filter(entry -> !excluded.contains(entry.value()));
    }

    @Override
    protected @NotNull Stream<? extends Holder<Item>> getKnownItems() {
        List<Item> excluded = new ArrayList<>();

        excluded.add(ItemRegistry.BLOWGUN.get());
        excluded.add(ItemRegistry.ROPE_COIL.get());
        excluded.add(ItemRegistry.TWISTED_ROPE_COIL.get());
        excluded.add(BlockRegistry.VERDANT_CONDUIT.get().asItem());

        WoodSets.WOOD_SETS.forEach(woodSet -> woodSet.getItemProvider()
                .getEntries()
                .forEach(registryObject -> excluded.add(registryObject.get())));


        return super.getKnownItems().filter(entry -> !excluded.contains(entry.value()));
    }

    public void createSoullikeFire(Block block) {
        MultiVariant floorFireModels = this.blockModels.createFloorFireModels(block);
        MultiVariant sideFireModels = this.blockModels.createSideFireModels(block);
        this.blockModels.blockStateOutput.accept(MultiPartGenerator.multiPart(block)
                .with(floorFireModels)
                .with(sideFireModels)
                .with(sideFireModels.with(BlockModelGenerators.Y_ROT_90))
                .with(sideFireModels.with(BlockModelGenerators.Y_ROT_180))
                .with(sideFireModels.with(BlockModelGenerators.Y_ROT_270)));
    }

    public void createFruitingTintedLeaves(Block block, Function<Integer, TexturedModel.Provider> provider, int tint, IntegerProperty ageProperty) {
        int maxAge = ageProperty.getPossibleValues().stream().mapToInt(i -> i).max().orElse(0);

        Mutable<Identifier> itemModelLocation = new MutableObject<>();

        Int2ObjectMap<Identifier> int2objectmap = new Int2ObjectOpenHashMap<>();
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                .with(PropertyDispatch.initial(ageProperty)
                        .generate((age) -> BlockModelGenerators.plainVariant(int2objectmap.computeIfAbsent(
                                age, (stage) -> {
                                    Identifier modelLocation = this.blockModels.createSuffixedVariant(
                                            block,
                                            "_stage" + stage,
                                            provider.apply(stage)
                                                    .get(block)
                                                    .getTemplate()
                                                    .extend()
                                                    .renderType(ChunkSectionLayer.CUTOUT.label())
                                                    .build(),
                                            VerdantTextureMapping::fruitingLeaves
                                    );
                                    if (stage == maxAge) {
                                        itemModelLocation.setValue(modelLocation);
                                    }
                                    return modelLocation;
                                }
                        )))));
        blockModels.registerSimpleTintedItemModel(block, itemModelLocation.get(), ItemModelUtils.constantTint(tint));
    }

    private void basicItem(Item item) {
        itemModels.generateFlatItem(item, item, ModelTemplates.FLAT_ITEM);
    }

    private void handheldItem(Item item) {
        itemModels.generateFlatItem(item, item, ModelTemplates.FLAT_HANDHELD_ITEM);
    }

    public void tippedArrow(Item arrowItem) {
        Identifier identifier = itemModels.generateLayeredItem(
                arrowItem,
                ModelLocationUtils.getModelLocation(arrowItem, "_head"),
                ModelLocationUtils.getModelLocation(arrowItem, "_base")
        );
        itemModels.addPotionTint(arrowItem, identifier);
    }

    protected void simpleBlockWithItem(Block block) {
        blockModels.createTrivialBlock(block, TexturedModel.CUBE);
    }

    @SuppressWarnings("SameParameterValue")
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

    protected void nonrotatablePillarBlock(Block block) {
        TexturedModel model = TexturedModel.COLUMN.get(block)
                .updateTextures(p_387400_ -> p_387400_.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block)));
        this.blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(
                block,
                BlockModelGenerators.variant(new Variant(model.create(block, this.blockModels.modelOutput)))
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

    @SuppressWarnings("SameParameterValue")
    protected void tumbledBlockWithItem(Block block, @Nullable String renderType) {
        TexturedModel.Provider model = TexturedModel.CUBE;

        if (renderType != null) {
            model = model.updateTemplate(template -> template.extend().renderType(renderType).build());
        }

        blockModels.blockStateOutput.accept(createTumbledBlock(block, model.create(block, blockModels.modelOutput)));
    }

    @SuppressWarnings("SameParameterValue")
    protected void tumbledOverlaidBlockWithItem(Block block, Block base, int tint, String... overlays) {
        BiFunction<String, Block, TexturedModel.Provider> baseModel = VerdantTexturedModel.OVERLAID_CUBE;

        Function<String, TexturedModel.Provider> model = (lambdaOverlay) -> baseModel.apply(lambdaOverlay, base)
                .updateTemplate(template -> template.extend().renderType("cutout").build());

        blockModels.blockStateOutput.accept(createTumbledOverlaidBlock(block, model, overlays));

        Identifier identifier = model.apply(overlays.length > 0 ? overlays[0] : "default_overlay")
                .createWithSuffix(block, "_item", this.blockModels.modelOutput);
        this.blockModels.registerSimpleTintedItemModel(block, identifier, new CustomModelDataSource(0, tint));
    }

    @SuppressWarnings("SameParameterValue")
    protected void overlaidBlockWithItem(Block block, Block base, int tint, String... overlays) {
        BiFunction<String, Block, TexturedModel.Provider> baseModel = VerdantTexturedModel.OVERLAID_CUBE;

        Function<String, TexturedModel.Provider> model = (lambdaOverlay) -> baseModel.apply(lambdaOverlay, base)
                .updateTemplate(template -> template.extend().renderType("cutout").build());

        this.blockModels.blockStateOutput.accept(createOverlaidBlock(block, model, overlays));


        Identifier identifier = model.apply(overlays.length > 0 ? overlays[0] : "default_overlay")
                .createWithSuffix(block, "_item", this.blockModels.modelOutput);
        this.blockModels.registerSimpleTintedItemModel(block, identifier, ItemModelUtils.constantTint(tint));
    }

    public void createTintedOverlaidLeaves(Block block, Block base, int tint, String... overlays) {
        BiFunction<String, Block, TexturedModel.Provider> baseModel = VerdantTexturedModel.OVERLAID_CUBE;

        Function<String, TexturedModel.Provider> model = (lambdaOverlay) -> baseModel.apply(lambdaOverlay, base)
                .updateTemplate(template -> template.extend().renderType("cutout").build());


        this.blockModels.blockStateOutput.accept(createOverlaidBlock(block, model, overlays));

        Identifier identifier = model.apply(overlays.length > 0 ? overlays[0] : "default_overlay")
                .createWithSuffix(block, "_item", this.blockModels.modelOutput);
        this.blockModels.registerSimpleTintedItemModel(block, identifier, ItemModelUtils.constantTint(tint));
    }

    public void createOverlaidDoublePlantWithItem(Block block, VerdantPlantType plantType) {
        Identifier identifier = createFlatItemModelWithBlockTextureAndOverlay(
                this.blockModels,
                block.asItem(),
                block,
                "_top",
                "_top_overlay"
        );
        this.blockModels.registerSimpleTintedItemModel(
                block,
                identifier,
                plantType == VerdantPlantType.OVERLAID_TINTED ? new GrassColorSource() : new Constant(-1)
        );
        createOverlaidDoublePlantWithoutItem(block, plantType);
    }


    public void createOverlaidDoublePlantWithoutItem(Block block, VerdantPlantType plantType) {
        MultiVariant plantTop = BlockModelGenerators.plainVariant(this.blockModels.createSuffixedVariant(
                block,
                "_top",
                plantType.getCross(),
                VerdantTextureMapping::overlaidCross
        ));
        MultiVariant plantBottom = BlockModelGenerators.plainVariant(this.blockModels.createSuffixedVariant(
                block,
                "_bottom",
                plantType.getCross(),
                VerdantTextureMapping::overlaidCross
        ));
        this.blockModels.createDoubleBlock(block, plantTop, plantBottom);
    }


    protected void wallSkullBlock(Block block, Block skull) {
        blockModels.blockStateOutput.accept(createWallSkullBlock(block, skull, VerdantTexturedModel.WALL_SKULL));
    }

    protected void skullBlock(Block block) {
        blockModels.blockStateOutput.accept(createSkullBlock(
                block, i -> switch (i) {
                    case 0 -> VerdantTexturedModel.SKULL_ROT0;
                    case 1 -> VerdantTexturedModel.SKULL_ROT1;
                    case 2 -> VerdantTexturedModel.SKULL_ROT2;
                    case 3 -> VerdantTexturedModel.SKULL_ROT3;
                    default -> null;
                }
        ));
    }


    protected void trapBlock(Block block) {
        BiFunction<Integer, Boolean, TexturedModel.Provider> baseModel = VerdantTexturedModel.TRAP;

        BiFunction<Integer, Boolean, TexturedModel.Provider> model = (stage, isHidden) -> baseModel.apply(
                stage,
                isHidden
        ).updateTemplate(template -> template.extend().renderType("cutout").build());

        blockModels.blockStateOutput.accept(createTrapBlock(block, model));
    }


    protected void tintedTrapBlock(Block block) {
        BiFunction<Integer, Boolean, TexturedModel.Provider> baseModel = VerdantTexturedModel.TINTED_TRAP;

        BiFunction<Integer, Boolean, TexturedModel.Provider> model = (stage, isHidden) -> baseModel.apply(
                stage,
                isHidden
        ).updateTemplate(template -> template.extend().renderType("cutout").build());

        blockModels.blockStateOutput.accept(createTrapBlock(block, model));
    }

    @SuppressWarnings("unused")
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

    @SuppressWarnings("SameParameterValue")
    protected void rotatedTopOverlaidBlockWithItem(Block block, Block base, String topOverlay, int tint, @NotNull String[] overlays) {
        TriFunction<String, String, Block, TexturedModel.Provider> baseModel = VerdantTexturedModel.TOP_OVERLAID_CUBE;

        Function<String, TexturedModel.Provider> model = (lambdaOverlay) -> baseModel.apply(
                        lambdaOverlay,
                        topOverlay,
                        base
                )
                .updateTemplate(template -> template.extend().renderType("cutout").build());

        MultiVariant variants = createRotatedTopOverlaidBlock(block, model, overlays, "");
        Function<String, TexturedModel.Provider> snowyModel = (lambdaOverlay) -> baseModel.apply(
                        lambdaOverlay,
                        "snowy_" + topOverlay,
                        base
                )
                .updateTemplate(template -> template.extend().renderType("cutout").build());
        MultiVariant snowyVariants = createRotatedTopOverlaidBlock(block, snowyModel, overlays, "_snowy");

        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                .with(PropertyDispatch.initial(BlockStateProperties.SNOWY)
                        .select(true, snowyVariants)
                        .select(false, variants)));

        Identifier identifier = model.apply(overlays.length > 0 ? overlays[0] : "default_overlay")
                .createWithSuffix(block, "_item", this.blockModels.modelOutput);
        this.blockModels.registerSimpleTintedItemModel(block, identifier, ItemModelUtils.constantTint(tint));
    }


    public void createPlantWithDefaultItem(Block block, Block pottedBlock, BlockModelGenerators.PlantType plantType, String renderType) {
        blockModels.registerSimpleTintedItemModel(
                block,
                plantType.createItemModel(blockModels, block),
                plantType == BlockModelGenerators.PlantType.TINTED ? new GrassColorSource() : new Constant(-1)
        );
        createPlant(block, pottedBlock, plantType, renderType);
    }

    public void createOverlaidPlantWithDefaultItem(Block block, Block pottedBlock, VerdantPlantType plantType, String renderType) {

        blockModels.registerSimpleTintedItemModel(
                block,
                plantType.createItemModel(blockModels, block),
                plantType == VerdantPlantType.OVERLAID_TINTED ? new GrassColorSource() : new Constant(-1)
        );
        createOverlaidPlant(block, pottedBlock, plantType, renderType);
    }

    public void createPlantWithDefaultItemWithCustomPottedTexture(Block block, Block pottedBlock, Identifier customTexture, BlockModelGenerators.PlantType plantType, String renderType) {
        blockModels.registerSimpleItemModel(block.asItem(), plantType.createItemModel(blockModels, block));
        createPlantWithCustomPottedTexture(block, pottedBlock, customTexture, plantType, renderType);
    }

    public void createPlant(Block block, Block pottedBlock, BlockModelGenerators.PlantType plantType, String renderType) {
        createCrossBlock(block, plantType, renderType);
        TextureMapping texturemapping = plantType.getPlantTextureMapping(block);
        Identifier identifier = plantType.getCrossPot()
                .extend()
                .renderType(renderType)
                .build()
                .create(pottedBlock, texturemapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(
                pottedBlock,
                BlockModelGenerators.plainVariant(identifier)
        ));
    }


    public void createOverlaidPlant(Block block, Block pottedBlock, VerdantPlantType plantType, String renderType) {
        createOverlaidCrossBlock(block, plantType, renderType);
        TextureMapping texturemapping = plantType.getPlantTextureMapping(block);
        Identifier identifier = plantType.getCrossPot()
                .extend()
                .renderType(renderType)
                .build()
                .create(pottedBlock, texturemapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(
                pottedBlock,
                BlockModelGenerators.plainVariant(identifier)
        ));
    }

    public void createPlantWithCustomPottedTexture(Block block, Block pottedBlock, Identifier customPottedTexture, BlockModelGenerators.PlantType plantType, String renderType) {
        createCrossBlock(block, plantType, renderType);
        TextureMapping texturemapping = TextureMapping.singleSlot(TextureSlot.PLANT, customPottedTexture);

        Identifier identifier = plantType.getCrossPot()
                .extend()
                .renderType(renderType)
                .build()
                .create(pottedBlock, texturemapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(
                pottedBlock,
                BlockModelGenerators.plainVariant(identifier)
        ));
    }

    public void createPottedOnly(Block block, Block pottedBlock, BlockModelGenerators.PlantType plantType, String renderType) {
        TextureMapping texturemapping = plantType.getPlantTextureMapping(block);
        Identifier identifier = plantType.getCrossPot()
                .extend()
                .renderType(renderType)
                .build()
                .create(pottedBlock, texturemapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(
                pottedBlock,
                BlockModelGenerators.plainVariant(identifier)
        ));
    }

    public void createCrossBlock(Block block, BlockModelGenerators.PlantType plantType, String renderType) {
        TextureMapping texturemapping = plantType.getTextureMapping(block);
        createCrossBlock(block, plantType, texturemapping, renderType);
    }

    public void createOverlaidCrossBlock(Block block, VerdantPlantType plantType, String renderType) {
        TextureMapping texturemapping = plantType.getTextureMapping(block);
        createOverlaidCrossBlock(block, plantType, texturemapping, renderType);
    }

    public void createCrossBlock(Block block, BlockModelGenerators.PlantType plantType, TextureMapping textureMapping, String renderType) {
        Identifier identifier = plantType.getCross()
                .extend()
                .renderType(renderType)
                .build()
                .create(block, textureMapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(
                block,
                BlockModelGenerators.plainVariant(identifier)
        ));
    }


    public void createOverlaidCrossBlock(Block block, VerdantPlantType plantType, TextureMapping textureMapping, String renderType) {
        Identifier identifier = plantType.getCross()
                .extend()
                .renderType(renderType)
                .build()
                .create(block, textureMapping, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(
                block,
                BlockModelGenerators.plainVariant(identifier)
        ));
    }

    public void createCrossBlockWithoutItem(Block block, BlockModelGenerators.PlantType plantType, String renderType, Property<@NotNull Integer> ageProperty, int... possibleValues) {
        if (ageProperty.getPossibleValues().size() != possibleValues.length) {
            throw new IllegalArgumentException("missing values for property: " + ageProperty);
        } else {
            PropertyDispatch<MultiVariant> propertydispatch = PropertyDispatch.initial(ageProperty)
                    .generate(p_388685_ -> {
                        String s = "_stage" + possibleValues[p_388685_];
                        TextureMapping texturemapping = TextureMapping.cross(TextureMapping.getBlockTexture(block, s));
                        Identifier identifier = plantType.getCross()
                                .extend()
                                .renderType(renderType)
                                .build()
                                .createWithSuffix(block, s, texturemapping, blockModels.modelOutput);
                        return BlockModelGenerators.plainVariant(identifier);
                    });
            blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(propertydispatch));
        }
    }

    public void createCropBlock(Block cropBlock, String renderType, Property<@NotNull Integer> ageProperty, int... ageToVisualStageMapping) {
        this.blockModels.registerSimpleFlatItemModel(cropBlock.asItem());
        createCropBlockWithoutItem(cropBlock, renderType, ageProperty, ageToVisualStageMapping);
    }

    public void createCropBlockWithoutItem(Block cropBlock, String renderType, Property<@NotNull Integer> ageProperty, int... ageToVisualStageMapping) {
        if (ageProperty.getPossibleValues().size() != ageToVisualStageMapping.length) {
            throw new IllegalArgumentException();
        } else {
            Int2ObjectMap<Identifier> int2objectmap = new Int2ObjectOpenHashMap<>();
            this.blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(cropBlock)
                    .with(PropertyDispatch.initial(ageProperty).generate((p_408977_) -> {
                        int i = ageToVisualStageMapping[p_408977_];
                        return BlockModelGenerators.plainVariant(int2objectmap.computeIfAbsent(
                                i, (stage) -> this.blockModels.createSuffixedVariant(
                                        cropBlock,
                                        "_stage" + stage,
                                        ModelTemplates.CROP.extend().renderType(renderType).build(),
                                        TextureMapping::crop
                                )
                        ));
                    })));
        }
    }

    public void createCrossBlock(Block block, BlockModelGenerators.PlantType plantType, String renderType, Property<@NotNull Integer> ageProperty, int... possibleValues) {
        createCrossBlockWithoutItem(block, plantType, renderType, ageProperty, possibleValues);
        blockModels.registerSimpleFlatItemModel(block.asItem());

    }

    @SuppressWarnings("unused")
    public void createAsteriskBlockWithoutItem(Block block, BlockModelGenerators.PlantType plantType, String renderType, Property<@NotNull Integer> ageProperty, int... possibleValues) {
        if (ageProperty.getPossibleValues().size() != possibleValues.length) {
            throw new IllegalArgumentException("missing values for property: " + ageProperty);
        } else {
            PropertyDispatch<MultiVariant> propertydispatch = PropertyDispatch.initial(ageProperty).generate(index -> {
                String s = "_stage" + possibleValues[index];
                TextureMapping texture = VerdantTextureMapping.asterisk(
                        TextureMapping.getBlockTexture(block),
                        TextureMapping.getBlockTexture(block, s)
                );

                Identifier identifier = VerdantModelTemplates.ASTERISK.extend()
                        .renderType(renderType)
                        .build()
                        .createWithSuffix(block, s, texture, blockModels.modelOutput);
                return BlockModelGenerators.plainVariant(identifier);
            });
            blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(propertydispatch));
        }
    }

    @SuppressWarnings("unused")
    public void createAsteriskBlock(Block block, BlockModelGenerators.PlantType plantType, String renderType, Property<@NotNull Integer> ageProperty, int... possibleValues) {
        createAsteriskBlockWithoutItem(block, plantType, renderType, ageProperty, possibleValues);
        blockModels.registerSimpleFlatItemModel(block.asItem());

    }

    @SuppressWarnings("unused")
    public void generateSimpleSpecialItemModel(Block block, SpecialModelRenderer.Unbaked specialModel) {
        Item item = block.asItem();
        Identifier identifier = ModelLocationUtils.getModelLocation(item);
        itemModels.itemModelOutput.accept(item, ItemModelUtils.specialModel(identifier, specialModel));
    }
}