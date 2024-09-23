package com.thomas.turbulent.datagen;

import java.util.function.Supplier;

import com.google.common.base.Function;
import com.thomas.turbulent.Turbulent;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
	public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, Turbulent.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {

	}

	public ResourceLocation extend(ResourceLocation rl, String suffix) {
		return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
	}

	public void logBlock(RotatedPillarBlock block, String renderType) {
		axisBlock(block, blockTexture(block), extend(blockTexture(block), "_top"), renderType);
	}

	public void doubleSidedLogBlock(RotatedPillarBlock block, String renderType) {
		doubleSidedAxisBlock(block, blockTexture(block), extend(blockTexture(block), "_top"), renderType);
	}

	public void axisBlock(RotatedPillarBlock block, ResourceLocation side, ResourceLocation end, String renderType) {
		axisBlock(block, models().cubeColumn(name(block), side, end).renderType(renderType),
				models().cubeColumnHorizontal(name(block) + "_horizontal", side, end).renderType(renderType));
	}

	public void doubleSidedAxisBlock(RotatedPillarBlock block, ResourceLocation side, ResourceLocation end,
			String renderType) {
		axisBlock(block,
				models().withExistingParent(name(block),
						new ResourceLocation(Turbulent.MOD_ID, "block/double_sided_cube_column"))
						.texture("side", blockTexture(block)).texture("end", extend(blockTexture(block), "_top"))
						.renderType(renderType),
				models().withExistingParent(name(block) + "_horizontal",
						new ResourceLocation(Turbulent.MOD_ID, "block/double_sided_cube_column_horizontal"))
						.texture("side", blockTexture(block)).texture("end", extend(blockTexture(block), "_top"))
						.renderType(renderType));
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
						new ResourceLocation(Turbulent.MOD_ID, "block/tinted_block"))
				.texture("all", new ResourceLocation(Turbulent.MOD_ID, "block/" + block.getId().getPath()));
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

	protected void doubleSidedBlockWithItem(RegistryObject<Block> blockRegistryObject, String renderType) {
		Block block = blockRegistryObject.get();
		ModelFile model = models()
				.withExistingParent(blockTexture(block).toString(),
						new ResourceLocation(Turbulent.MOD_ID, "block/double_sided_cube_all"))
				.texture("all", blockTexture(blockRegistryObject.get())).renderType(renderType);
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
		ResourceLocation down = new ResourceLocation(Turbulent.MOD_ID, "block/" + name + "_down");
		ResourceLocation up = new ResourceLocation(Turbulent.MOD_ID, "block/" + name + "_up");
		ResourceLocation north = new ResourceLocation(Turbulent.MOD_ID, "block/" + name + "_north");
		ResourceLocation south = new ResourceLocation(Turbulent.MOD_ID, "block/" + name + "_south");
		ResourceLocation east = new ResourceLocation(Turbulent.MOD_ID, "block/" + name + "_east");
		ResourceLocation west = new ResourceLocation(Turbulent.MOD_ID, "block/" + name + "_west");

		ModelFile cubeModel = models().cube(name, down, up, north, south, east, west).texture("particle", up);

		simpleBlockWithItem(blockRegistryObject.get(), cubeModel);
	}

	protected void sidedBlockWithItem(RegistryObject<Block> blockRegistryObject, String name) {
		ResourceLocation down = new ResourceLocation(Turbulent.MOD_ID, "block/" + name + "_bottom");
		ResourceLocation up = new ResourceLocation(Turbulent.MOD_ID, "block/" + name + "_top");
		ResourceLocation side = new ResourceLocation(Turbulent.MOD_ID, "block/" + name);

		ModelFile cubeModel = models().cube(name, down, up, side, side, side, side).texture("particle", side);

		simpleBlockWithItem(blockRegistryObject.get(), cubeModel);
	}

	protected void lanternBlock(Block lanternBlock, String name) {
		Function<BlockState, ConfiguredModel[]> function = state -> lanternStates(state, (LanternBlock) lanternBlock,
				name);

		getVariantBuilder((LanternBlock) lanternBlock).forAllStates(function);
	}

	protected void doublePlantBlock(DoublePlantBlock plant, String name) {
		Function<BlockState, ConfiguredModel[]> function = state -> {
			ConfiguredModel[] models = new ConfiguredModel[1];
			ConfiguredModel model;

			if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER) {
				model = new ConfiguredModel(models().withExistingParent(name + "_bottom", mcLoc("block/cross"))
						.texture("cross", modLoc("block/" + name + "_bottom")).renderType("cutout"));
			} else {
				model = new ConfiguredModel(models().withExistingParent(name + "_top", mcLoc("block/cross"))
						.texture("cross", modLoc("block/" + name + "_top")).renderType("cutout"));
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

	protected void simpleFlowerWithPot(Block flower, Block pottedFlower) {
		simpleBlock(flower, models().cross(blockTexture(flower).getPath(), blockTexture(flower)).renderType("cutout"));
		simpleBlock(pottedFlower, models().singleTexture(blockTexture(pottedFlower).getPath(),
				new ResourceLocation("flower_pot_cross"), "plant", blockTexture(flower)).renderType("cutout"));

	}

	protected void tumbledBlockWithItem(Supplier<Block> blockSupplier) {
		ModelFile simple = cubeAll(blockSupplier.get());
		tumbledBlockWithItem(blockSupplier, simple);
	}

	protected void tumbledBlockWithItem(Supplier<Block> blockSupplier, ModelFile model) {
		// Every possible unique rotation of the model.
		// With most simple blocks, many of these states are superfluous. However, it
		// doesn't hurt to have them.
		getVariantBuilder(blockSupplier.get()).forAllStates((state) -> ConfiguredModel.builder().modelFile(model)
				.rotationX(0).rotationY(0).nextModel().modelFile(model).rotationX(0).rotationY(90).nextModel()
				.modelFile(model).rotationX(0).rotationY(180).nextModel().modelFile(model).rotationX(0).rotationY(270)
				.nextModel().modelFile(model).rotationX(90).rotationY(0).nextModel().modelFile(model).rotationX(90)
				.rotationY(90).nextModel().modelFile(model).rotationX(90).rotationY(180).nextModel().modelFile(model)
				.rotationX(90).rotationY(270).nextModel().modelFile(model).rotationX(180).rotationY(0).nextModel()
				.modelFile(model).rotationX(180).rotationY(90).nextModel().modelFile(model).rotationX(180)
				.rotationY(180).nextModel().modelFile(model).rotationX(180).rotationY(270).nextModel().modelFile(model)
				.rotationX(270).rotationY(0).nextModel().modelFile(model).rotationX(270).rotationY(90).nextModel()
				.modelFile(model).rotationX(270).rotationY(180).nextModel().modelFile(model).rotationX(270)
				.rotationY(270).build());
		simpleBlockItem(blockSupplier.get(), model);
	}

	protected void spunBlockWithItem(Supplier<Block> blockSupplier) {
		ModelFile simple = cubeAll(blockSupplier.get());
		spunBlockWithItem(blockSupplier, simple);
	}

	protected void spunBlockWithItem(Supplier<Block> blockSupplier, ModelFile model) {
		// Every possible y-rotation of the model.
		getVariantBuilder(blockSupplier.get())
				.forAllStates((state) -> ConfiguredModel.builder().modelFile(model).rotationX(0).rotationY(0)
						.nextModel().modelFile(model).rotationX(0).rotationY(90).nextModel().modelFile(model)
						.rotationX(0).rotationY(180).nextModel().modelFile(model).rotationX(0).rotationY(270).build());
		simpleBlockItem(blockSupplier.get(), model);
	}
}