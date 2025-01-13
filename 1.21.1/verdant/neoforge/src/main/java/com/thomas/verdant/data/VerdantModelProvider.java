package com.thomas.verdant.data;

import com.thomas.verdant.Constants;
import com.thomas.verdant.block.custom.CoffeeCropBlock;
import com.thomas.verdant.block.custom.FishTrapBlock;
import com.thomas.verdant.block.custom.SpikesBlock;
import com.thomas.verdant.block.custom.TrapBlock;
import com.thomas.verdant.data.definitions.VerdantTexturedModel;
import com.thomas.verdant.registry.BlockRegistry;
import com.thomas.verdant.registry.ItemRegistry;
import com.thomas.verdant.registry.WoodSets;
import com.thomas.verdant.woodset.WoodSet;
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
import net.minecraft.world.level.block.state.properties.Property;
import org.apache.commons.lang3.function.TriFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        this.blockModels = blockModels;
        this.itemModels = itemModels;

        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            generateFor(woodSet);
        }

        fishTrapWithItem(BlockRegistry.FISH_TRAP_BLOCK.get());
        tumbledBlockWithItem(BlockRegistry.ANTIGORITE.get());
        tumbledBlockWithItem(BlockRegistry.ROTTEN_WOOD.get());
        simpleBlockWithItem(BlockRegistry.TEST_BLOCK.get());
        blockModels.createAxisAlignedPillarBlock(BlockRegistry.TEST_LOG.get(), TexturedModel.COLUMN);
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
        tumbledBlockWithItem(BlockRegistry.PACKED_GRAVEL.get());
        tumbledOverlaidBlockWithItem(
                BlockRegistry.WILTED_STRANGLER_LEAVES.get(),
                BlockRegistry.STRANGLER_LEAVES.get(),
                "overlay_wilted_thin"
        );
        tumbledBlockWithItem(BlockRegistry.STRANGLER_LEAVES.get(), "cutout");
        tumbledOverlaidBlockWithItem(
                BlockRegistry.POISON_STRANGLER_LEAVES.get(),
                BlockRegistry.STRANGLER_LEAVES.get(),
                "overlay_poison_ivy"
        );
        tumbledOverlaidBlockWithItem(
                BlockRegistry.THORNY_STRANGLER_LEAVES.get(),
                BlockRegistry.STRANGLER_LEAVES.get(),
                "overlay_thorns"
        );
        overlaidBlockWithItem(BlockRegistry.DIRT_COAL_ORE.get(), Blocks.DIRT, "coal_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_COPPER_ORE.get(), Blocks.DIRT, "copper_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_IRON_ORE.get(), Blocks.DIRT, "iron_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_GOLD_ORE.get(), Blocks.DIRT, "gold_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_LAPIS_ORE.get(), Blocks.DIRT, "lapis_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_REDSTONE_ORE.get(), Blocks.DIRT, "redstone_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_EMERALD_ORE.get(), Blocks.DIRT, "emerald_ore_overlay");
        overlaidBlockWithItem(BlockRegistry.DIRT_DIAMOND_ORE.get(), Blocks.DIRT, "diamond_ore_overlay");

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
                BlockRegistry.TIGER_LILY.get(),
                BlockRegistry.POTTED_TIGER_LILY.get(),
                BlockModelGenerators.PlantType.NOT_TINTED,
                "cutout"
        );
        trapBlockWithItem(BlockRegistry.WOODEN_TRAP.get());
        trapBlockWithItem(BlockRegistry.IRON_TRAP.get());
        trapBlockWithItem(BlockRegistry.SNAPLEAF.get());
        spikesBlockWithItem(BlockRegistry.WOODEN_SPIKES.get());
        spikesBlockWithItem(BlockRegistry.IRON_SPIKES.get());
        doubleSidedLogBlockWithItem(BlockRegistry.CHARRED_FRAME_BLOCK.get());
        doubleSidedLogBlockWithItem(BlockRegistry.FRAME_BLOCK.get());

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
        // TODO ropeCoilItem(ItemRegistry.ROPE_COIL);
        basicItem(ItemRegistry.ROTTEN_COMPOST.get());
        basicItem(ItemRegistry.HEART_OF_THE_FOREST.get());
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        List<Block> excluded = new ArrayList<>();

        excluded.add(BlockRegistry.STRANGLER_VINE.get());
        excluded.add(BlockRegistry.LEAFY_STRANGLER_VINE.get());
        excluded.add(BlockRegistry.ROPE.get());
        excluded.add(BlockRegistry.ROPE_HOOK.get());
        excluded.add(BlockRegistry.STINKING_BLOSSOM.get());
        excluded.add(BlockRegistry.VERDANT_CONDUIT.get());

        return super.getKnownBlocks().filter(entry -> !excluded.contains(entry.value()));
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        List<Item> excluded = new ArrayList<>();

        excluded.add(ItemRegistry.ROPE_COIL.get());
        excluded.add(BlockRegistry.VERDANT_CONDUIT.get().asItem());

        return super.getKnownItems().filter(entry -> !excluded.contains(entry.value()));
    }

    private String name(Block block) {
        return this.key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    protected void generateFor(WoodSet woodSet) {
        Block planks = woodSet.getPlanks().get();

        blockModels.family(woodSet.getPlanks().get()).generateFor(woodSet.getFamily());
        blockModels.createHangingSign(planks, woodSet.getHangingSign().get(), woodSet.getWallHangingSign().get());
        blockModels.createAxisAlignedPillarBlock(woodSet.getLog().get(), TexturedModel.COLUMN);
        blockModels.createAxisAlignedPillarBlock(woodSet.getStrippedLog().get(), TexturedModel.COLUMN);
        blockModels.createAxisAlignedPillarBlock(woodSet.getWood().get(), TexturedModel.COLUMN);
        blockModels.createAxisAlignedPillarBlock(woodSet.getStrippedWood().get(), TexturedModel.COLUMN);

        basicItem(woodSet.getBoatItem().get());
        basicItem(woodSet.getChestBoatItem().get());
    }

    private void basicItem(Item item) {
        itemModels.generateFlatItem(item, item, ModelTemplates.FLAT_ITEM);
    }

    private void handheldItem(Item item) {
        itemModels.generateFlatItem(item, item, ModelTemplates.FLAT_HANDHELD_ITEM);
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

    protected void trapBlockWithItem(Block block) {
        BiFunction<Integer, Boolean, TexturedModel.Provider> baseModel = VerdantTexturedModel.TRAP;

        BiFunction<Integer, Boolean, TexturedModel.Provider> model = (stage, isHidden) -> baseModel.apply(
                stage,
                isHidden
        ).updateTemplate(template -> template.extend().renderType("cutout").build());

        blockModels.blockStateOutput.accept(createTrapBlock(block, model));
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

    public void createCrossBlock(Block block, BlockModelGenerators.PlantType plantType, String renderType, Property<Integer> ageProperty, int... possibleValues) {
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
            blockModels.registerSimpleFlatItemModel(block.asItem());
            blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(propertydispatch));
        }
    }

    public void generateSimpleSpecialItemModel(Block block, SpecialModelRenderer.Unbaked specialModel) {
        Item item = block.asItem();
        ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(item);
        itemModels.itemModelOutput.accept(item, ItemModelUtils.specialModel(resourcelocation, specialModel));
    }
}