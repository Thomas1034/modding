package com.thomas.zirconmod.datagen;

import com.google.common.base.Function;
import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.block.custom.BlueberryCropBlock;
import com.thomas.zirconmod.block.custom.NimbulaPolypBlock;
import com.thomas.zirconmod.block.custom.ZirconLampBlock;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, ZirconMod.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		// Register simple blocks with the same texture on all sides
		blockWithItem(ModBlocks.CLOUD);
		blockWithItem(ModBlocks.THUNDER_CLOUD);
		blockWithItem(ModBlocks.ZIRCON_BLOCK);
		blockWithItem(ModBlocks.DEEPSLATE_ZIRCON_ORE);
		blockWithItem(ModBlocks.ECHO_BLOCK);
		blockWithItem(ModBlocks.QUICKSAND);
		blockWithItem(ModBlocks.RAW_ZIRCONIUM_BLOCK);
		blockWithItem(ModBlocks.THIRSTY_MUD_BRICKS);
		blockWithItem(ModBlocks.THIRSTY_PACKED_MUD);
		blockWithItem(ModBlocks.ZIRCON_ORE);
		blockWithItem(ModBlocks.ZIRCONIUM_BLOCK);
		blockWithItem(ModBlocks.PALM_PLANKS);
		blockWithItem(ModBlocks.UNOBTAINIUM_GEM);
		blockWithItem(ModBlocks.CLOUD_BRICKS);
		blockWithItem(ModBlocks.THUNDER_CLOUD_BRICKS);
		// Register logs
		logBlock((RotatedPillarBlock) ModBlocks.PALM_LOG.get());
		logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_PALM_LOG.get());
		logBlock((RotatedPillarBlock) ModBlocks.PALM_WOOD.get());
		logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_PALM_WOOD.get());

		// Register special blocks
		makeZirconLamp(ModBlocks.ZIRCON_LAMP.get(), "zircon_lamp", "zircon_lamp");

		stairsBlock(((StairBlock) ModBlocks.PALM_STAIRS.get()), blockTexture(ModBlocks.PALM_PLANKS.get()));
		slabBlock(((SlabBlock) ModBlocks.PALM_SLAB.get()), blockTexture(ModBlocks.PALM_PLANKS.get()),
				blockTexture(ModBlocks.PALM_PLANKS.get()));

		buttonBlock(((ButtonBlock) ModBlocks.PALM_BUTTON.get()), blockTexture(ModBlocks.PALM_PLANKS.get()));
		pressurePlateBlock(((PressurePlateBlock) ModBlocks.PALM_PRESSURE_PLATE.get()),
				blockTexture(ModBlocks.PALM_PLANKS.get()));

		fenceBlock(((FenceBlock) ModBlocks.PALM_FENCE.get()), blockTexture(ModBlocks.PALM_PLANKS.get()));
		fenceGateBlock(((FenceGateBlock) ModBlocks.PALM_FENCE_GATE.get()), blockTexture(ModBlocks.PALM_PLANKS.get()));

		doorBlockWithRenderType(((DoorBlock) ModBlocks.PALM_DOOR.get()), modLoc("block/palm_door_bottom"),
				modLoc("block/palm_door_top"), "cutout");
		trapdoorBlockWithRenderType(((TrapDoorBlock) ModBlocks.PALM_TRAPDOOR.get()), modLoc("block/palm_trapdoor"),
				true, "cutout");

		// Amethyst furniture
		lanternBlock(ModBlocks.CITRINE_LANTERN.get(), "citrine_lantern");
		torchBlock((TorchBlock) ModBlocks.CITRINE_BRACKET.get(), "citrine_bracket", "citrine_bracket");
		wallTorchBlock((WallTorchBlock) ModBlocks.CITRINE_WALL_BRACKET.get(), "citrine_wall_bracket", "citrine_bracket");

		// Blueberry crop
		makeBlueberryCrop((CropBlock) ModBlocks.BLUEBERRY_CROP.get(), "blueberry_stage", "blueberry_stage");

		// Torchflower
		simpleBlockWithItem(ModBlocks.ILLUMINATED_TORCHFLOWER.get(),
				models().cross(blockTexture(ModBlocks.ILLUMINATED_TORCHFLOWER.get()).getPath(),
						blockTexture(ModBlocks.ILLUMINATED_TORCHFLOWER.get())).renderType("cutout"));
		simpleBlockWithItem(ModBlocks.POTTED_ILLUMINATED_TORCHFLOWER.get(),
				models().singleTexture("potted_catmint", new ResourceLocation("flower_pot_cross"), "plant",
						blockTexture(ModBlocks.ILLUMINATED_TORCHFLOWER.get())).renderType("cutout"));

		// Carpentry table
		cubeBlockWithItem(ModBlocks.CARPENTRY_TABLE, "carpentry_table");
		// Nimbula Polyp
		makeNimbulaPolyp(ModBlocks.NIMBULA_POLYP.get(), "nimbula_polyp", "nimbula_polyp_stage");
	}

	protected void blockWithItem(RegistryObject<Block> blockRegistryObject) {
		simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
	}

	private ConfiguredModel[] blueberryStates(BlockState state, CropBlock block, String modelName, String textureName) {
		ConfiguredModel[] models = new ConfiguredModel[1];
		models[0] = new ConfiguredModel(models()
				.crop(modelName + state.getValue(((BlueberryCropBlock) block).getAgeProperty()),
						new ResourceLocation(ZirconMod.MOD_ID,
								"block/" + textureName + state.getValue(((BlueberryCropBlock) block).getAgeProperty())))
				.renderType("cutout"));

		return models;
	}

	private ConfiguredModel[] zirconLampStates(BlockState state, ZirconLampBlock block, String modelName,
			String textureName) {
		ConfiguredModel[] models = new ConfiguredModel[1];
		models[0] = new ConfiguredModel(models().cubeAll(modelName + state.getValue((block).getLitProperty()),
				new ResourceLocation(ZirconMod.MOD_ID,
						"block/" + textureName + "_" + (state.getValue((block).getLitProperty()) ? "lit" : "unlit"))));
		return models;
	}

	private ConfiguredModel[] nimbulaPolypStates(BlockState state, NimbulaPolypBlock block, String modelName,
			String parentName) {
		ConfiguredModel[] models = new ConfiguredModel[1];
		models[0] = new ConfiguredModel(
				models().withExistingParent(modelName + state.getValue(block.getAgeProperty()), new ResourceLocation(
						ZirconMod.MOD_ID, "block/" + parentName + "_" + (state.getValue((block).getAgeProperty())))));
		return models;
	}

	protected ConfiguredModel[] lanternStates(BlockState state, LanternBlock block, String modelName) {
		ConfiguredModel[] models = new ConfiguredModel[1];
		ConfiguredModel lanternModel;

		if (state.getValue(LanternBlock.HANGING)) {
			lanternModel = new ConfiguredModel(
					models().withExistingParent(modelName + "_hanging", mcLoc("template_hanging_lantern"))
							.texture("lantern", modLoc("block/" + modelName)));
		} else {
			lanternModel = new ConfiguredModel(models().withExistingParent(modelName, mcLoc("template_lantern"))
					.texture("lantern", modLoc("block/" + modelName)));
		}

		models[0] = lanternModel;

		return models;
	}

	protected void makeBlueberryCrop(CropBlock block, String modelName, String textureName) {
		Function<BlockState, ConfiguredModel[]> function = state -> blueberryStates(state, block, modelName,
				textureName);

		getVariantBuilder(block).forAllStates(function);
	}

	protected void makeZirconLamp(Block block, String modelName, String textureName) {
		Function<BlockState, ConfiguredModel[]> function = state -> zirconLampStates(state, (ZirconLampBlock) block,
				modelName, textureName);

		getVariantBuilder((ZirconLampBlock) block).forAllStates(function);
	}

	protected void makeNimbulaPolyp(Block block, String modelName, String parentName) {
		Function<BlockState, ConfiguredModel[]> function = state -> nimbulaPolypStates(state, (NimbulaPolypBlock) block,
				modelName, parentName);

		getVariantBuilder((NimbulaPolypBlock) block).forAllStates(function);
	}

	protected void cubeBlockWithItem(RegistryObject<Block> blockRegistryObject, String name) {
		ResourceLocation down = new ResourceLocation(ZirconMod.MOD_ID, "block/" + name + "_down");
		ResourceLocation up = new ResourceLocation(ZirconMod.MOD_ID, "block/" + name + "_up");
		ResourceLocation north = new ResourceLocation(ZirconMod.MOD_ID, "block/" + name + "_north");
		;
		ResourceLocation south = new ResourceLocation(ZirconMod.MOD_ID, "block/" + name + "_south");
		;
		ResourceLocation east = new ResourceLocation(ZirconMod.MOD_ID, "block/" + name + "_east");
		;
		ResourceLocation west = new ResourceLocation(ZirconMod.MOD_ID, "block/" + name + "_west");
		;
		ModelFile cubeModel = models().cube(name, down, up, north, south, east, west).texture("particle", up);

		simpleBlockWithItem(blockRegistryObject.get(), cubeModel);
	}

	protected void lanternBlock(Block lanternBlock, String name) {
		Function<BlockState, ConfiguredModel[]> function = state -> lanternStates(state, (LanternBlock) lanternBlock,
				name);

		getVariantBuilder((LanternBlock) lanternBlock).forAllStates(function);
	}

	public void wallTorchBlock(WallTorchBlock block, String modelName, String texture) {
		getVariantBuilder(block).forAllStates(state -> {
			Direction facing = state.getValue(WallTorchBlock.FACING);
			int yRot = ((int) facing.toYRot()) + 90;
			ModelFile model = models().withExistingParent(modelName, mcLoc("block/template_torch_wall"))
					.renderType("cutout").texture("torch", modLoc("block/" + texture));
			
			return ConfiguredModel.builder().modelFile(model).rotationY(yRot).build();
		});
	}

	public void torchBlock(TorchBlock block, String modelName, String texture) {
		getVariantBuilder(block).forAllStates(state -> {
			ModelFile model = models().withExistingParent(modelName, mcLoc("block/template_torch")).renderType("cutout")
					.texture("torch", modLoc("block/" + texture));

			return ConfiguredModel.builder().modelFile(model).build();
		});

	}
}