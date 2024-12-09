package com.thomas.verdant.data;

import com.thomas.verdant.Constants;
import com.thomas.verdant.registry.BlockRegistry;
import com.thomas.verdant.registry.WoodSets;
import com.thomas.verdant.woodset.WoodSet;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

public class VerdantBlockStateProvider extends BlockStateProvider {
    public VerdantBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            generateFor(woodSet);
        }

        tumbledBlockWithItem(BlockRegistry.ROTTEN_WOOD, "cutout");
        simpleBlockWithItem(BlockRegistry.TEST_BLOCK.get());
        logBlockWithItem((RotatedPillarBlock) BlockRegistry.TEST_LOG.get());
        verdantGroundBlock(BlockRegistry.VERDANT_ROOTED_DIRT, () -> Blocks.DIRT);
        verdantGrassBlock(BlockRegistry.VERDANT_GRASS_DIRT, () -> Blocks.DIRT);
        verdantGroundBlock(BlockRegistry.VERDANT_ROOTED_MUD, () -> Blocks.MUD);
        verdantGrassBlock(BlockRegistry.VERDANT_GRASS_MUD, () -> Blocks.MUD);
        verdantGroundBlock(BlockRegistry.VERDANT_ROOTED_CLAY, () -> Blocks.CLAY);
        verdantGrassBlock(BlockRegistry.VERDANT_GRASS_CLAY, () -> Blocks.CLAY);
        tumbledBlockWithItem(BlockRegistry.PACKED_GRAVEL);
        tumbledBlockWithItem(BlockRegistry.STRANGLER_LEAVES, "cutout");
        overlayBlockWithItem(BlockRegistry.DIRT_COAL_ORE, () -> Blocks.DIRT, new String[]{"coal_ore_overlay"});
        overlayBlockWithItem(BlockRegistry.DIRT_COPPER_ORE, () -> Blocks.DIRT, new String[]{"copper_ore_overlay"});
        overlayBlockWithItem(BlockRegistry.DIRT_IRON_ORE, () -> Blocks.DIRT, new String[]{"iron_ore_overlay"});
        overlayBlockWithItem(BlockRegistry.DIRT_GOLD_ORE, () -> Blocks.DIRT, new String[]{"gold_ore_overlay"});
        overlayBlockWithItem(BlockRegistry.DIRT_LAPIS_ORE, () -> Blocks.DIRT, new String[]{"lapis_ore_overlay"});
        overlayBlockWithItem(BlockRegistry.DIRT_REDSTONE_ORE, () -> Blocks.DIRT, new String[]{"redstone_ore_overlay"});
        overlayBlockWithItem(BlockRegistry.DIRT_EMERALD_ORE, () -> Blocks.DIRT, new String[]{"emerald_ore_overlay"});
        overlayBlockWithItem(BlockRegistry.DIRT_DIAMOND_ORE, () -> Blocks.DIRT, new String[]{"diamond_ore_overlay"});
    }

    private String name(Block block) {
        return this.key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    protected void generateFor(WoodSet woodSet) {

        logBlockWithItem((RotatedPillarBlock) woodSet.getLog().get());
        logBlockWithItem((RotatedPillarBlock) woodSet.getStrippedLog().get());
        logBlockWithItem((RotatedPillarBlock) woodSet.getWood().get());
        logBlockWithItem((RotatedPillarBlock) woodSet.getStrippedWood().get());
        Block planks = woodSet.getPlanks().get();
        simpleBlockWithItem(planks);
        SlabBlock slab = (SlabBlock) woodSet.getSlab().get();
        slabBlock(slab, blockTexture(planks), blockTexture(planks));
        StairBlock stairs = (StairBlock) woodSet.getStairs().get();
        stairsBlock(stairs, woodSet.getName(), blockTexture(planks));
        fenceBlock((FenceBlock) woodSet.getFence().get(), blockTexture(planks));
        fenceGateBlock((FenceGateBlock) woodSet.getFenceGate().get(), woodSet.getName(), blockTexture(planks));
        buttonBlock((ButtonBlock) woodSet.getButton().get(), blockTexture(planks));
        pressurePlateBlock((PressurePlateBlock) woodSet.getPressurePlate().get(), blockTexture(planks));
        signBlock((StandingSignBlock) woodSet.getSign().get(), (WallSignBlock) woodSet.getWallSign().get(), blockTexture(planks));
        hangingSignBlock(woodSet.getHangingSign().get(), woodSet.getWallHangingSign().get(), blockTexture(planks));
        trapdoorBlockWithRenderType((TrapDoorBlock) woodSet.getTrapdoor().get(), blockTexture(woodSet.getTrapdoor().get()), true, "cutout");
        DoorBlock door = (DoorBlock) woodSet.getDoor().get();
        doorBlockWithRenderType(door, blockTexture(door).withSuffix("_bottom"), blockTexture(door).withSuffix("_top"), "cutout");

    }

    protected void simpleBlockWithItem(Block block) {
        simpleBlockWithItem(block, cubeAll(block));
    }

    protected void overlayBlockWithItem(@NotNull Supplier<Block> blockSource, Supplier<Block> baseSource, String[] overlays) {

        Block block = blockSource.get();
        Block base = baseSource.get();
        @SuppressWarnings("deprecation") ResourceLocation baseLocation = base.builtInRegistryHolder().key().location();

        ModelFile[] files = new ModelFile[overlays.length];

        for (int i = 0; i < overlays.length; i++) {
            files[i] = models().withExistingParent(name(block) + (overlays.length == 1 ? "" : "_" + overlays[i]), ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/bilayer_block")).texture("base", baseLocation.getNamespace() + ":block/" + baseLocation.getPath()).texture("overlay", ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + overlays[i])).renderType("cutout");
        }

        blockWithItem(blockSource, files);
    }

    protected void verdantGroundBlock(@NotNull Supplier<Block> blockSource, Supplier<Block> baseSource) {
        String[] extensions = new String[]{"", "_thick", "_thin", "_thin2", "_wilted", "_very_thin", "_very_thin_mixed"};

        Block block = blockSource.get();
        Block base = baseSource.get();
        @SuppressWarnings("deprecation") ResourceLocation baseLocation = base.builtInRegistryHolder().key().location();

        ModelFile[] files = new ModelFile[extensions.length];

        for (int i = 0; i < extensions.length; i++) {
            files[i] = models().withExistingParent(name(block) + extensions[i], ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/bilayer_block")).texture("base", baseLocation.getNamespace() + ":block/" + baseLocation.getPath()).texture("overlay", ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/verdant_overlay" + extensions[i])).renderType("cutout");
        }

        blockWithItem(blockSource, files);
    }

    private void verdantGrassBlock(Supplier<Block> blockSource, Supplier<Block> baseSource) {
        String[] extensions = new String[]{"", "_thick", "_thin", "_thin2", "_wilted", "_very_thin", "_very_thin_mixed"

        };

        Block block = blockSource.get();
        Block base = baseSource.get();
        @SuppressWarnings("deprecation") ResourceLocation baseLocation = base.builtInRegistryHolder().key().location();

        ModelFile[] files = new ModelFile[extensions.length];

        for (int i = 0; i < extensions.length; i++) {
            files[i] = models().withExistingParent(name(block) + extensions[i], ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/verdant_base_grass_block")).texture("base", baseLocation.getNamespace() + ":block/" + baseLocation.getPath()).texture("overlay", ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/verdant_overlay" + extensions[i])).texture("top", ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/verdant_grass_block_top")).texture("side", ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/verdant_grass_block_side")).renderType("cutout");
        }

        spunBlockWithItem(blockSource, files);
    }

    public ResourceLocation extend(ResourceLocation rl, String suffix) {
        return ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), rl.getPath() + suffix);
    }

    public void logBlock(RotatedPillarBlock block, String renderType) {
        axisBlock(block, blockTexture(block), extend(blockTexture(block), "_top"), renderType);
    }

    public void doubleSidedLogBlock(RotatedPillarBlock block, String renderType) {
        doubleSidedAxisBlock(block, renderType);
    }

    public void axisBlock(RotatedPillarBlock block, ResourceLocation side, ResourceLocation end, String renderType) {
        axisBlock(block, models().cubeColumn(name(block), side, end).renderType(renderType), models().cubeColumnHorizontal(name(block) + "_horizontal", side, end).renderType(renderType));
    }

    public void doubleSidedAxisBlock(RotatedPillarBlock block, String renderType) {
        axisBlock(block, models().withExistingParent(name(block), ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/double_sided_cube_column")).texture("side", blockTexture(block)).texture("end", extend(blockTexture(block), "_top")).renderType(renderType), models().withExistingParent(name(block) + "_horizontal", ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/double_sided_cube_column_horizontal")).texture("side", blockTexture(block)).texture("end", extend(blockTexture(block), "_top")).renderType(renderType));
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    protected void anvilBlock(AnvilBlock block) {
        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(AnvilBlock.FACING);

            int yRot = (((int) facing.toYRot()) + 180) % 360;

            ModelFile model;

            model = models().withExistingParent(blockTexture(block).toString(), ResourceLocation.withDefaultNamespace("block/template_anvil")).texture("particle", blockTexture(block).toString()).texture("body", blockTexture(block).toString()).texture("top", blockTexture(block) + "_top");

            return ConfiguredModel.builder().modelFile(model).rotationY(yRot).build();
        });
    }

    protected ConfiguredModel[] lanternStates(BlockState state, String modelName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        ConfiguredModel lanternModel;

        if (state.getValue(LanternBlock.HANGING)) {
            lanternModel = new ConfiguredModel(models().withExistingParent(modelName + "_hanging", mcLoc("template_hanging_lantern")).texture("lantern", modLoc("block/" + modelName)).renderType("cutout"));
        } else {
            lanternModel = new ConfiguredModel(models().withExistingParent(modelName, mcLoc("template_lantern")).texture("lantern", modLoc("block/" + modelName)).renderType("cutout"));
        }

        models[0] = lanternModel;

        return models;
    }

    protected void cubeBlockWithItem(Supplier<Block> blockRegistryObject, String name) {
        ResourceLocation down = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + name + "_down");
        ResourceLocation up = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + name + "_up");
        ResourceLocation north = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + name + "_north");
        ResourceLocation south = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + name + "_south");
        ResourceLocation east = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + name + "_east");
        ResourceLocation west = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + name + "_west");

        ModelFile cubeModel = models().cube(name, down, up, north, south, east, west).texture("particle", up);

        simpleBlockWithItem(blockRegistryObject.get(), cubeModel);
    }

    protected void sidedBlockWithItem(Supplier<Block> blockRegistryObject, String name) {
        ResourceLocation down = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + name + "_bottom");
        ResourceLocation up = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + name + "_top");
        ResourceLocation side = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + name);

        ModelFile cubeModel = models().cube(name, down, up, side, side, side, side).texture("particle", side);

        simpleBlockWithItem(blockRegistryObject.get(), cubeModel);
    }

    protected void lanternBlock(Block lanternBlock, String name) {
        Function<BlockState, ConfiguredModel[]> function = state -> lanternStates(state, name);

        getVariantBuilder(lanternBlock).forAllStates(function);
    }

    protected void doublePlantBlock(DoublePlantBlock plant, String name) {
        Function<BlockState, ConfiguredModel[]> function = state -> {
            ConfiguredModel[] models = new ConfiguredModel[1];
            ConfiguredModel model;

            if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
                model = new ConfiguredModel(models().withExistingParent(name + "_bottom", mcLoc("block/cross")).texture("cross", modLoc("block/" + name + "_bottom")).renderType("cutout"));
            } else {
                model = new ConfiguredModel(models().withExistingParent(name + "_top", mcLoc("block/cross")).texture("cross", modLoc("block/" + name + "_top")).renderType("cutout"));
            }

            models[0] = model;

            return models;
        };

        getVariantBuilder(plant).forAllStates(function);
    }

    public void wallTorchBlock(WallTorchBlock block, String modelName, String texture) {
        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(WallTorchBlock.FACING);
            int yRot = ((int) facing.toYRot()) + 90;
            ModelFile model = models().withExistingParent(modelName, mcLoc("block/template_torch_wall")).renderType("cutout").texture("torch", modLoc("block/" + texture));

            return ConfiguredModel.builder().modelFile(model).rotationY(yRot).build();
        });
    }

    public void horizontalRotatedCustomModelBlock(Block block, String name, String customModel) {
        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            int yRot = ((int) facing.toYRot()) + 180;
            ModelFile model = models().withExistingParent(name, modLoc(customModel)).renderType("cutout");

            return ConfiguredModel.builder().modelFile(model).rotationY(yRot).build();
        });
    }

    public void ladderBlock(LadderBlock block, String texture) {
        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(LadderBlock.FACING);
            int yRot = ((int) facing.toYRot()) + 180;
            ModelFile model = models().withExistingParent(texture, mcLoc("block/ladder")).renderType("cutout").texture("texture", modLoc("block/" + texture)).texture("particle", modLoc("block/" + texture));

            return ConfiguredModel.builder().modelFile(model).rotationY(yRot).build();
        });
    }

    public void torchBlock(TorchBlock block, String modelName, String texture) {
        getVariantBuilder(block).forAllStates(state -> {
            ModelFile model = models().withExistingParent(modelName, mcLoc("block/template_torch")).renderType("cutout").texture("torch", modLoc("block/" + texture));

            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    protected void clusterBlock(AmethystClusterBlock block) {
        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(AmethystClusterBlock.FACING);
            int yRot = (((int) facing.toYRot()) + 180) % 360;
            int xRot = 90;
            ModelFile model = models().withExistingParent(blockTexture(block).toString(), mcLoc("block/cross")).renderType("cutout").texture("cross", blockTexture(block));
            if (facing == Direction.UP) {
                xRot = 0;
            } else if (facing == Direction.DOWN) {
                xRot = 180;
            }

            return ConfiguredModel.builder().modelFile(model).rotationY(yRot).rotationX(xRot).build();
        });
    }

    protected void directionalBlock(DirectionalBlock block, String renderType) {
        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(DirectionalBlock.FACING);
            int yRot = (((int) facing.toYRot()) + 180) % 360;
            int xRot = 90;
            ModelFile model;
            if (facing == Direction.UP) {
                xRot = 0;
                model = models().withExistingParent(blockTexture(block) + "_up", mcLoc("block/orientable_vertical")).renderType(renderType).texture("front", blockTexture(block) + "_front").texture("side", blockTexture(block) + "_side");
            } else if (facing == Direction.DOWN) {
                xRot = 180;
                model = models().withExistingParent(blockTexture(block) + "_down", mcLoc("block/orientable_vertical")).renderType(renderType).texture("front", blockTexture(block) + "_front").texture("side", blockTexture(block) + "_side");
            } else {
                model = models().withExistingParent(blockTexture(block) + "_horizontal", mcLoc("block/orientable")).renderType(renderType).texture("front", blockTexture(block) + "_front").texture("side", blockTexture(block) + "_side").texture("top", blockTexture(block) + "_top");
            }

            return ConfiguredModel.builder().modelFile(model).rotationY(yRot).rotationX(xRot).build();
        });
    }

    protected void crossBlock(Block block, String modelName) {
        simpleBlock(block, models().cross(modelName, blockTexture(block)));
    }

    protected void simpleFlowerWithPot(Block flower, Block pottedFlower) {
        simpleBlock(flower, models().cross(blockTexture(flower).getPath(), blockTexture(flower)).renderType("cutout"));
        simpleBlock(pottedFlower, models().singleTexture(blockTexture(pottedFlower).getPath(), ResourceLocation.withDefaultNamespace("flower_pot_cross"), "plant", blockTexture(flower)).renderType("cutout"));
    }

    protected void simpleFlowerPot(Block pottedFlower, ResourceLocation pottedFlowerTexture) {
        simpleBlock(pottedFlower, models().singleTexture(blockTexture(pottedFlower).getPath(), ResourceLocation.withDefaultNamespace("flower_pot_cross"), "plant", pottedFlowerTexture).renderType("cutout"));
    }

    protected void cropBlock(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = (state) -> {
            ConfiguredModel[] models = new ConfiguredModel[1];
            String age = "_stage" + state.getValue(CropBlock.AGE);
            models[0] = new ConfiguredModel(models().crop(modelName + age, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + textureName + age)).renderType("cutout"));
            return models;
        };
        // Add all the states
        getVariantBuilder(block).forAllStates(function);
    }

    protected void tumbledBlockWithItem(Supplier<Block> blockSupplier) {
        tumbledBlockWithItem(blockSupplier, (String) null);
    }

    protected void tumbledBlockWithItem(Supplier<Block> blockSupplier, String renderType) {
        BlockModelBuilder simple = models().cubeAll(name(blockSupplier.get()), blockTexture(blockSupplier.get()));
        if (renderType != null) {
            simple = simple.renderType(renderType);
        }
        tumbledBlockWithItem(blockSupplier, simple);
    }

    protected void tumbledBlockWithItemMultitexture(Supplier<Block> blockSupplier, String[] textureSuffixes) {
        Block block = blockSupplier.get();
        ModelFile simple = cubeAll(block);
        ModelFile[] models = new ModelFile[textureSuffixes.length + 1];

        models[0] = simple;
        for (int i = 1; i <= textureSuffixes.length; i++) {
            models[i] = models().cubeAll(name(block), blockTexture(block).withSuffix(textureSuffixes[i - 1]));
        }

        tumbledBlockWithItem(blockSupplier, models);
    }

    protected void tumbledBlockWithItem(Supplier<Block> blockSupplier, ModelFile model) {
        // Every possible unique rotation of the model.
        // With most simple blocks, many of these states are superfluous. However, it
        // doesn't hurt (much) to have them.
        getVariantBuilder(blockSupplier.get()).forAllStates((state) -> ConfiguredModel.builder().modelFile(model).rotationX(0).rotationY(0).nextModel().modelFile(model).rotationX(0).rotationY(90).nextModel().modelFile(model).rotationX(0).rotationY(180).nextModel().modelFile(model).rotationX(0).rotationY(270).nextModel().modelFile(model).rotationX(90).rotationY(0).nextModel().modelFile(model).rotationX(90).rotationY(90).nextModel().modelFile(model).rotationX(90).rotationY(180).nextModel().modelFile(model).rotationX(90).rotationY(270).nextModel().modelFile(model).rotationX(180).rotationY(0).nextModel().modelFile(model).rotationX(180).rotationY(90).nextModel().modelFile(model).rotationX(180).rotationY(180).nextModel().modelFile(model).rotationX(180).rotationY(270).nextModel().modelFile(model).rotationX(270).rotationY(0).nextModel().modelFile(model).rotationX(270).rotationY(90).nextModel().modelFile(model).rotationX(270).rotationY(180).nextModel().modelFile(model).rotationX(270).rotationY(270).build());
        simpleBlockItem(blockSupplier.get(), model);
    }

    protected void tumbledBlockWithItem(Supplier<Block> blockSupplier, ModelFile[] models) {
        // Every possible unique rotation of the model.
        // With most simple blocks, many of these states are superfluous. However, it
        // doesn't hurt to have them.
        final int[] rotations = new int[]{0, 90, 180, 270};

        blockWithItem(blockSupplier, models, rotations);
    }

    protected void blockWithItem(Supplier<Block> blockSupplier, ModelFile[] models) {
        blockWithItem(blockSupplier, models, new int[]{0});
    }

    protected void blockWithItem(Supplier<Block> blockSupplier, ModelFile[] models, int[] rotations) {
        // Every possible unique rotation of the model.
        // With most simple blocks, many of these states are superfluous. However, it
        // doesn't hurt to have them.

        getVariantBuilder(blockSupplier.get()).forAllStates((state) -> {
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(models[0]);

            for (int xrot : rotations) {
                for (int yrot : rotations) {
                    for (ModelFile model : models) {
                        if (0 == xrot && 0 == yrot && model == models[0]) {
                            continue;
                        }

                        builder = builder.nextModel().modelFile(model).rotationX(xrot).rotationY(yrot);
                    }
                }
            }

            return builder.build();

        });

        simpleBlockItem(blockSupplier.get(), models[0]);
    }

    protected void spunBlockWithItem(Supplier<Block> blockSupplier) {
        ModelFile simple = cubeAll(blockSupplier.get());
        spunBlockWithItem(blockSupplier, simple);
    }

    protected void spunBlockWithItem(Supplier<Block> blockSupplier, ModelFile model) {
        // Every possible y-rotation of the model.
        getVariantBuilder(blockSupplier.get()).forAllStates((state) -> ConfiguredModel.builder().modelFile(model).rotationX(0).rotationY(0).nextModel().modelFile(model).rotationX(0).rotationY(90).nextModel().modelFile(model).rotationX(0).rotationY(180).nextModel().modelFile(model).rotationX(0).rotationY(270).build());
        simpleBlockItem(blockSupplier.get(), model);
    }

    protected void spunBlockWithItem(Supplier<Block> blockSupplier, ModelFile[] models) {
        // Every possible unique rotation of the model.
        // With most simple blocks, many of these states are superfluous. However, it
        // doesn't hurt to have them.
        final int[] rotations = new int[]{0, 90, 180, 270};

        getVariantBuilder(blockSupplier.get()).forAllStates((state) -> {
            ConfiguredModel.Builder<?> builder = ConfiguredModel.builder().modelFile(models[0]);
            for (int yrot : rotations) {
                for (ModelFile model : models) {
                    if (0 == yrot && model == models[0]) {
                        continue;
                    }

                    builder = builder.nextModel().modelFile(model).rotationY(yrot);
                }
            }

            return builder.build();

        });

        simpleBlockItem(blockSupplier.get(), models[0]);
    }

    // From @random832 on the NeoForge server
    // https://discord.com/channels/313125603924639766/1249305774987939900/1311358871604035695
    private void logBlockWithItem(RotatedPillarBlock block) {
        logBlock(block);
        simpleBlockItem(block, models().getBuilder(BuiltInRegistries.BLOCK.getKey(block).getPath()));
    }
}