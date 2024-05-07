package com.thomas.verdant.datagen;

import com.google.common.base.Function;
import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.ModBlocks;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, Verdant.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {

		blockWithItem(ModBlocks.VERDANT_PLANKS);
		blockWithItem(ModBlocks.VERDANT_ROOTED_DIRT);
		sidedBlockWithItem(ModBlocks.VERDANT_GRASS_BLOCK, "verdant_grass_block");
		logBlock((RotatedPillarBlock) ModBlocks.VERDANT_LOG.get());
		logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_VERDANT_LOG.get());
		logBlock((RotatedPillarBlock) ModBlocks.VERDANT_WOOD.get());
		logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_VERDANT_WOOD.get());
		stairsBlock(((StairBlock) ModBlocks.VERDANT_STAIRS.get()), blockTexture(ModBlocks.VERDANT_PLANKS.get()));
		slabBlock(((SlabBlock) ModBlocks.VERDANT_SLAB.get()), blockTexture(ModBlocks.VERDANT_PLANKS.get()),
				blockTexture(ModBlocks.VERDANT_PLANKS.get()));

		buttonBlock(((ButtonBlock) ModBlocks.VERDANT_BUTTON.get()), blockTexture(ModBlocks.VERDANT_PLANKS.get()));
		pressurePlateBlock(((PressurePlateBlock) ModBlocks.VERDANT_PRESSURE_PLATE.get()),
				blockTexture(ModBlocks.VERDANT_PLANKS.get()));

		fenceBlock(((FenceBlock) ModBlocks.VERDANT_FENCE.get()), blockTexture(ModBlocks.VERDANT_PLANKS.get()));
		fenceGateBlock(((FenceGateBlock) ModBlocks.VERDANT_FENCE_GATE.get()),
				blockTexture(ModBlocks.VERDANT_PLANKS.get()));

		doorBlockWithRenderType(((DoorBlock) ModBlocks.VERDANT_DOOR.get()), modLoc("block/verdant_door_bottom"),
				modLoc("block/verdant_door_top"), "cutout");
		trapdoorBlockWithRenderType(((TrapDoorBlock) ModBlocks.VERDANT_TRAPDOOR.get()),
				modLoc("block/verdant_trapdoor"), true, "cutout");

		signBlock(((StandingSignBlock) ModBlocks.VERDANT_SIGN.get()),
				((WallSignBlock) ModBlocks.VERDANT_WALL_SIGN.get()), blockTexture(ModBlocks.VERDANT_PLANKS.get()));

		hangingSignBlock(ModBlocks.VERDANT_HANGING_SIGN.get(), ModBlocks.VERDANT_WALL_HANGING_SIGN.get(),
				blockTexture(ModBlocks.VERDANT_PLANKS.get()));

	}

	public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
		ModelFile sign = models().sign(name(signBlock), texture);
		hangingSignBlock(signBlock, wallSignBlock, sign);
	}

	public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
		simpleBlock(signBlock, sign);
		simpleBlock(wallSignBlock, sign);
	}

	private String name(Block block) {
		return key(block).getPath();
	}

	private ResourceLocation key(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block);
	}

	protected void tintedBlockWithItem(RegistryObject<Block> block) {
		// System.out.println("Registering tinted block with " + block.getId());
		ModelFile model = models()
				.withExistingParent(block.getId().toString(),
						new ResourceLocation(Verdant.MOD_ID, "block/tinted_block"))
				.texture("all", new ResourceLocation(Verdant.MOD_ID, "block/" + block.getId().getPath()));
		simpleBlockWithItem(block.get(), model);
	}

	protected void blockWithItem(RegistryObject<Block> blockRegistryObject) {
		simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
	}

	protected void blockWithItem(RegistryObject<Block> blockRegistryObject, String renderType) {
		Block block = blockRegistryObject.get();
		ModelFile model = models().cubeAll(blockTexture(block).toString(), blockTexture(block)).renderType(renderType);
		simpleBlock(block, model);
		simpleBlockItem(block, model);
	}

	protected void anvilBlock(AnvilBlock block) {
		getVariantBuilder(block).forAllStates(state -> {
			Direction facing = state.getValue(AnvilBlock.FACING);

			int yRot = (((int) facing.toYRot()) + 180) % 360;

			ModelFile model;

			model = models()
					.withExistingParent(blockTexture(block).toString(), new ResourceLocation("block/template_anvil"))
					.texture("particle", blockTexture(block).toString()).texture("body", blockTexture(block).toString())
					.texture("top", blockTexture(block).toString() + "_top");

			return ConfiguredModel.builder().modelFile(model).rotationY(yRot).build();
		});
	}

	protected ConfiguredModel[] lanternStates(BlockState state, LanternBlock block, String modelName) {
		ConfiguredModel[] models = new ConfiguredModel[1];
		ConfiguredModel lanternModel;

		if (state.getValue(LanternBlock.HANGING)) {
			lanternModel = new ConfiguredModel(
					models().withExistingParent(modelName + "_hanging", mcLoc("template_hanging_lantern"))
							.texture("lantern", modLoc("block/" + modelName)).renderType("cutout"));
		} else {
			lanternModel = new ConfiguredModel(models().withExistingParent(modelName, mcLoc("template_lantern"))
					.texture("lantern", modLoc("block/" + modelName)).renderType("cutout"));
		}

		models[0] = lanternModel;

		return models;
	}

	protected void cubeBlockWithItem(RegistryObject<Block> blockRegistryObject, String name) {
		ResourceLocation down = new ResourceLocation(Verdant.MOD_ID, "block/" + name + "_down");
		ResourceLocation up = new ResourceLocation(Verdant.MOD_ID, "block/" + name + "_up");
		ResourceLocation north = new ResourceLocation(Verdant.MOD_ID, "block/" + name + "_north");
		ResourceLocation south = new ResourceLocation(Verdant.MOD_ID, "block/" + name + "_south");
		ResourceLocation east = new ResourceLocation(Verdant.MOD_ID, "block/" + name + "_east");
		ResourceLocation west = new ResourceLocation(Verdant.MOD_ID, "block/" + name + "_west");

		ModelFile cubeModel = models().cube(name, down, up, north, south, east, west).texture("particle", up);

		simpleBlockWithItem(blockRegistryObject.get(), cubeModel);
	}

	protected void sidedBlockWithItem(RegistryObject<Block> blockRegistryObject, String name) {
		ResourceLocation down = new ResourceLocation(Verdant.MOD_ID, "block/" + name + "_bottom");
		ResourceLocation up = new ResourceLocation(Verdant.MOD_ID, "block/" + name + "_top");
		ResourceLocation side = new ResourceLocation(Verdant.MOD_ID, "block/" + name);

		ModelFile cubeModel = models().cube(name, down, up, side, side, side, side).texture("particle", side);

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

	protected void clusterBlock(AmethystClusterBlock block) {
		getVariantBuilder(block).forAllStates(state -> {
			Direction facing = state.getValue(AmethystClusterBlock.FACING);
			int yRot = (((int) facing.toYRot()) + 180) % 360;
			int xRot = 90;
			ModelFile model = models().withExistingParent(blockTexture(block).toString(), mcLoc("block/cross"))
					.renderType("cutout").texture("cross", blockTexture(block));
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
				model = models()
						.withExistingParent(blockTexture(block).toString() + "_up", mcLoc("block/orientable_vertical"))
						.renderType(renderType).texture("front", blockTexture(block) + "_front")
						.texture("side", blockTexture(block) + "_side");
			} else if (facing == Direction.DOWN) {
				xRot = 180;
				model = models()
						.withExistingParent(blockTexture(block).toString() + "_down",
								mcLoc("block/orientable_vertical"))
						.renderType(renderType).texture("front", blockTexture(block) + "_front")
						.texture("side", blockTexture(block) + "_side");
			} else {
				model = models()
						.withExistingParent(blockTexture(block).toString() + "_horizontal", mcLoc("block/orientable"))
						.renderType(renderType).texture("front", blockTexture(block) + "_front")
						.texture("side", blockTexture(block) + "_side").texture("top", blockTexture(block) + "_top");
			}

			return ConfiguredModel.builder().modelFile(model).rotationY(yRot).rotationX(xRot).build();
		});
	}

	protected void crossBlock(Block block, String modelName) {
		simpleBlock(block, models().cross(modelName, blockTexture(block)));
	}
}