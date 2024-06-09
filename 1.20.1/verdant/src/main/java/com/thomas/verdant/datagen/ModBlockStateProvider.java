package com.thomas.verdant.datagen;

import com.google.common.base.Function;
import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.block.custom.CoffeeCropBlock;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CropBlock;
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

		blockWithItem(ModBlocks.DIRT_COAL_ORE);
		blockWithItem(ModBlocks.DIRT_COPPER_ORE);
		blockWithItem(ModBlocks.DIRT_IRON_ORE);
		blockWithItem(ModBlocks.DIRT_GOLD_ORE);
		blockWithItem(ModBlocks.DIRT_REDSTONE_ORE);
		blockWithItem(ModBlocks.DIRT_LAPIS_ORE);
		blockWithItem(ModBlocks.DIRT_EMERALD_ORE);
		blockWithItem(ModBlocks.DIRT_DIAMOND_ORE);

		blockWithItem(ModBlocks.VERDANT_ROOTED_DIRT);
		sidedBlockWithItem(ModBlocks.VERDANT_GRASS_BLOCK, "verdant_grass_block");
		blockWithItem(ModBlocks.VERDANT_ROOTED_MUD);
		sidedBlockWithItem(ModBlocks.VERDANT_MUD_GRASS_BLOCK, "verdant_mud_grass_block");
		blockWithItem(ModBlocks.VERDANT_ROOTED_CLAY);
		sidedBlockWithItem(ModBlocks.VERDANT_CLAY_GRASS_BLOCK, "verdant_clay_grass_block");
		doubleSidedLogBlock((RotatedPillarBlock) ModBlocks.ROTTEN_WOOD.get(), "cutout");
		// Frame block
		doubleSidedLogBlock((RotatedPillarBlock) ModBlocks.FRAME_BLOCK.get(), "cutout");
		
		// Poison ivy block
		logBlock((RotatedPillarBlock) ModBlocks.POISON_IVY_BLOCK.get());
		logBlock((RotatedPillarBlock) ModBlocks.TOXIC_ASH_BLOCK.get());


		// Standard verdant wood
		logBlock((RotatedPillarBlock) ModBlocks.VERDANT_LOG.get());
		logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_VERDANT_LOG.get());
		logBlock((RotatedPillarBlock) ModBlocks.VERDANT_WOOD.get());
		logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_VERDANT_WOOD.get());
		blockWithItem(ModBlocks.VERDANT_PLANKS);
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

		// Verdant heartwood.
		logBlock((RotatedPillarBlock) ModBlocks.VERDANT_HEARTWOOD_LOG.get());
		logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_VERDANT_HEARTWOOD_LOG.get());
		logBlock((RotatedPillarBlock) ModBlocks.VERDANT_HEARTWOOD_WOOD.get());
		logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_VERDANT_HEARTWOOD_WOOD.get());
		blockWithItem(ModBlocks.VERDANT_HEARTWOOD_PLANKS);
		stairsBlock(((StairBlock) ModBlocks.VERDANT_HEARTWOOD_STAIRS.get()),
				blockTexture(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()));
		slabBlock(((SlabBlock) ModBlocks.VERDANT_HEARTWOOD_SLAB.get()),
				blockTexture(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()),
				blockTexture(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()));

		buttonBlock(((ButtonBlock) ModBlocks.VERDANT_HEARTWOOD_BUTTON.get()),
				blockTexture(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()));
		pressurePlateBlock(((PressurePlateBlock) ModBlocks.VERDANT_HEARTWOOD_PRESSURE_PLATE.get()),
				blockTexture(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()));

		fenceBlock(((FenceBlock) ModBlocks.VERDANT_HEARTWOOD_FENCE.get()),
				blockTexture(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()));
		fenceGateBlock(((FenceGateBlock) ModBlocks.VERDANT_HEARTWOOD_FENCE_GATE.get()),
				blockTexture(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()));

		doorBlockWithRenderType(((DoorBlock) ModBlocks.VERDANT_HEARTWOOD_DOOR.get()),
				modLoc("block/verdant_heartwood_door_bottom"), modLoc("block/verdant_heartwood_door_top"), "cutout");
		trapdoorBlockWithRenderType(((TrapDoorBlock) ModBlocks.VERDANT_HEARTWOOD_TRAPDOOR.get()),
				modLoc("block/verdant_heartwood_trapdoor"), true, "cutout");

		signBlock(((StandingSignBlock) ModBlocks.VERDANT_HEARTWOOD_SIGN.get()),
				((WallSignBlock) ModBlocks.VERDANT_HEARTWOOD_WALL_SIGN.get()),
				blockTexture(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()));

		hangingSignBlock(ModBlocks.VERDANT_HEARTWOOD_HANGING_SIGN.get(),
				ModBlocks.VERDANT_HEARTWOOD_WALL_HANGING_SIGN.get(),
				blockTexture(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get()));

		// Tendrils
		simpleBlockWithItem(ModBlocks.VERDANT_TENDRIL.get(),
				models().cross(blockTexture(ModBlocks.VERDANT_TENDRIL.get()).getPath(),
						blockTexture(ModBlocks.VERDANT_TENDRIL.get())).renderType("cutout"));
		simpleBlockWithItem(ModBlocks.VERDANT_TENDRIL_PLANT.get(),
				models().cross(blockTexture(ModBlocks.VERDANT_TENDRIL_PLANT.get()).getPath(),
						blockTexture(ModBlocks.VERDANT_TENDRIL_PLANT.get())).renderType("cutout"));

		// Poison ivy
		simpleBlockWithItem(ModBlocks.POISON_IVY.get(), models()
				.cross(blockTexture(ModBlocks.POISON_IVY.get()).getPath(), blockTexture(ModBlocks.POISON_IVY.get()))
				.renderType("cutout"));
		simpleBlockWithItem(ModBlocks.POISON_IVY_PLANT.get(),
				models().cross(blockTexture(ModBlocks.POISON_IVY_PLANT.get()).getPath(),
						blockTexture(ModBlocks.POISON_IVY_PLANT.get())).renderType("cutout"));

		// Thorn spikes model
		clusterBlock((AmethystClusterBlock) ModBlocks.THORN_SPIKES.get());

		// Rope
		simpleBlockWithItem(ModBlocks.ROPE.get(),
				models().cross(blockTexture(ModBlocks.ROPE.get()).getPath(), blockTexture(ModBlocks.ROPE.get()))
						.renderType("cutout"));

		// Flowers
		simpleFlowerWithPot(ModBlocks.BLEEDING_HEART.get(), ModBlocks.POTTED_BLEEDING_HEART.get());
		simpleFlowerWithPot(ModBlocks.WILD_COFFEE.get(), ModBlocks.POTTED_WILD_COFFEE.get());

		// Thorn bush
		simpleFlowerWithPot(ModBlocks.THORN_BUSH.get(), ModBlocks.POTTED_THORN_BUSH.get());

		// Coffee
		makeCoffeeCrop((CoffeeCropBlock) ModBlocks.COFFEE_CROP.get(), "coffee_crop_", "coffee_crop_");
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
		axisBlock(block, models()
				.withExistingParent(name(block), new ResourceLocation(Verdant.MOD_ID, "block/double_sided_cube_column"))
				.texture("side", blockTexture(block)).texture("end", extend(blockTexture(block), "_top"))
				.renderType(renderType),
				models().withExistingParent(name(block) + "_horizontal",
						new ResourceLocation(Verdant.MOD_ID, "block/double_sided_cube_column_horizontal"))
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

	protected void doubleSidedBlockWithItem(RegistryObject<Block> blockRegistryObject, String renderType) {
		Block block = blockRegistryObject.get();
		ModelFile model = models()
				.withExistingParent(blockTexture(block).toString(),
						new ResourceLocation(Verdant.MOD_ID, "block/double_sided_cube_all"))
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

	protected void simpleFlowerWithPot(Block flower, Block pottedFlower) {
		simpleBlock(flower, models().cross(blockTexture(flower).getPath(), blockTexture(flower)).renderType("cutout"));
		simpleBlock(pottedFlower, models().singleTexture(blockTexture(pottedFlower).getPath(),
				new ResourceLocation("flower_pot_cross"), "plant", blockTexture(flower)).renderType("cutout"));

	}

	private ConfiguredModel[] coffeeStates(BlockState state, CropBlock block, String modelName, String textureName) {
		ConfiguredModel[] models = new ConfiguredModel[1];
		models[0] = new ConfiguredModel(models()
				.crop(modelName + state.getValue(((CoffeeCropBlock) block).getAgeProperty()),
						new ResourceLocation(Verdant.MOD_ID,
								"block/" + textureName + state.getValue(((CoffeeCropBlock) block).getAgeProperty())))
				.renderType("cutout"));

		return models;
	}

	protected void makeCoffeeCrop(CropBlock block, String modelName, String textureName) {
		Function<BlockState, ConfiguredModel[]> function = state -> coffeeStates(state, block, modelName, textureName);

		getVariantBuilder(block).forAllStates(function);
	}
}