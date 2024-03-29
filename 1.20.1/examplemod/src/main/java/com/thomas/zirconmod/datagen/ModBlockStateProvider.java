package com.thomas.zirconmod.datagen;

import com.google.common.base.Function;
import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.block.custom.BlueberryCropBlock;
import com.thomas.zirconmod.block.custom.BubblefruitCropBlock;
import com.thomas.zirconmod.block.custom.DirectionalPassageBlock;
import com.thomas.zirconmod.block.custom.FloorFrondBlock;
import com.thomas.zirconmod.block.custom.FrondBlock;
import com.thomas.zirconmod.block.custom.NimbulaPolypBlock;
import com.thomas.zirconmod.block.custom.PalmFruitBlock;
import com.thomas.zirconmod.block.custom.ResonatorBlock;
import com.thomas.zirconmod.block.custom.SculkRootBlock;
import com.thomas.zirconmod.block.custom.ZirconLampBlock;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
import net.minecraft.world.level.block.WallBlock;
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
		super(output, ZirconMod.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {

		// Register simple blocks with the same texture on all sides
		blockWithItem(ModBlocks.LIGHTNING_BLOCK, "cutout");
		blockWithItem(ModBlocks.UNSTABLE_LIGHTNING_BLOCK, "cutout");
		blockWithItem(ModBlocks.WISP_BED);
		blockWithItem(ModBlocks.CLOUD);
		blockWithItem(ModBlocks.MIST, "translucent");
		blockWithItem(ModBlocks.SEALED_MIST, "translucent");
		blockWithItem(ModBlocks.THUNDER_CLOUD);
		blockWithItem(ModBlocks.SEALED_CLOUD);
		blockWithItem(ModBlocks.CLOUD_CONVERTER);
		blockWithItem(ModBlocks.CLOUD_INVERTER);
		blockWithItem(ModBlocks.CLOUD_DETECTOR);
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
		blockWithItem(ModBlocks.ARCHITECT_WORKSITE);
		blockWithItem(ModBlocks.BOTANIST_WORKSITE);
		blockWithItem(ModBlocks.CHIEF_WORKSITE);
		blockWithItem(ModBlocks.GEMSMITH_WORKSITE);
		blockWithItem(ModBlocks.SCHOLAR_WORKSITE);
		blockWithItem(ModBlocks.TINKERER_WORKSITE);
		blockWithItem(ModBlocks.SEALED_CLOUD_BRICKS);
		blockWithItem(ModBlocks.SEALED_THUNDER_CLOUD_BRICKS);

		// Register logs
		logBlock((RotatedPillarBlock) ModBlocks.PETRIFIED_LOG.get());

		// Register special blocks
		makeZirconLamp(ModBlocks.ZIRCON_LAMP.get(), "zircon_lamp", "zircon_lamp");
		anvilBlock((AnvilBlock) ModBlocks.NETHERITE_ANVIL.get());
		
		
		// Palm furniture
		palmFruitBlock((PalmFruitBlock) ModBlocks.PALM_FRUIT.get());
		frondBlock((FrondBlock) ModBlocks.PALM_FROND.get());
		floorFrondBlock((FloorFrondBlock) ModBlocks.PALM_FLOOR_FROND.get());
		simpleBlockWithItem(ModBlocks.PALM_TRUNK.get(), models().singleTexture("palm_trunk",
				modLoc("template_palm_trunk"), "trunk", blockTexture(ModBlocks.PALM_TRUNK.get())));
		logBlock((RotatedPillarBlock) ModBlocks.PALM_LOG.get());
		logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_PALM_LOG.get());
		logBlock((RotatedPillarBlock) ModBlocks.PALM_WOOD.get());
		logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_PALM_WOOD.get());
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

		// Cloud brick blocks.
		blockWithItem(ModBlocks.CLOUD_BRICKS);
		blockWithItem(ModBlocks.THUNDER_CLOUD_BRICKS);
		blockWithItem(ModBlocks.CHISELED_CLOUD_BRICKS);
		blockWithItem(ModBlocks.CHISELED_THUNDER_CLOUD_BRICKS);
		stairsBlock(((StairBlock) ModBlocks.CLOUD_BRICK_STAIRS.get()), blockTexture(ModBlocks.CLOUD_BRICKS.get()));
		slabBlock(((SlabBlock) ModBlocks.CLOUD_BRICK_SLAB.get()), blockTexture(ModBlocks.CLOUD_BRICKS.get()),
				blockTexture(ModBlocks.CLOUD_BRICKS.get()));
		wallBlock(((WallBlock) ModBlocks.CLOUD_BRICK_WALL.get()), blockTexture(ModBlocks.CLOUD_BRICKS.get()));
		stairsBlock(((StairBlock) ModBlocks.THUNDER_CLOUD_BRICK_STAIRS.get()),
				blockTexture(ModBlocks.THUNDER_CLOUD_BRICKS.get()));
		slabBlock(((SlabBlock) ModBlocks.THUNDER_CLOUD_BRICK_SLAB.get()),
				blockTexture(ModBlocks.THUNDER_CLOUD_BRICKS.get()), blockTexture(ModBlocks.THUNDER_CLOUD_BRICKS.get()));
		wallBlock(((WallBlock) ModBlocks.THUNDER_CLOUD_BRICK_WALL.get()),
				blockTexture(ModBlocks.THUNDER_CLOUD_BRICKS.get()));
		logBlock((RotatedPillarBlock) ModBlocks.CLOUD_BRICK_PILLAR.get());
		logBlock((RotatedPillarBlock) ModBlocks.THUNDER_CLOUD_BRICK_PILLAR.get());

		directionalPassageBlock((DirectionalPassageBlock) ModBlocks.WEATHER_PASSAGE_BLOCK.get(), "translucent");

		// Copper buttons
		buttonBlock(((ButtonBlock) ModBlocks.COPPER_BUTTON.get()), blockTexture(Blocks.CUT_COPPER));
		buttonBlock(((ButtonBlock) ModBlocks.EXPOSED_COPPER_BUTTON.get()), blockTexture(Blocks.EXPOSED_CUT_COPPER));
		buttonBlock(((ButtonBlock) ModBlocks.WEATHERED_COPPER_BUTTON.get()), blockTexture(Blocks.WEATHERED_CUT_COPPER));
		buttonBlock(((ButtonBlock) ModBlocks.OXIDIZED_COPPER_BUTTON.get()), blockTexture(Blocks.OXIDIZED_CUT_COPPER));
		buttonBlock(((ButtonBlock) ModBlocks.WAXED_COPPER_BUTTON.get()), blockTexture(Blocks.CUT_COPPER));
		buttonBlock(((ButtonBlock) ModBlocks.WAXED_EXPOSED_COPPER_BUTTON.get()), blockTexture(Blocks.EXPOSED_CUT_COPPER));
		buttonBlock(((ButtonBlock) ModBlocks.WAXED_WEATHERED_COPPER_BUTTON.get()), blockTexture(Blocks.WEATHERED_CUT_COPPER));
		buttonBlock(((ButtonBlock) ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get()), blockTexture(Blocks.OXIDIZED_CUT_COPPER));
		
		// Citrine blocks
		blockWithItem(ModBlocks.CITRINE_BLOCK);
		blockWithItem(ModBlocks.BUDDING_CITRINE);
		lanternBlock(ModBlocks.CITRINE_LANTERN.get(), "citrine_lantern");
		torchBlock((TorchBlock) ModBlocks.CITRINE_BRACKET.get(), "citrine_bracket", "citrine_bracket");
		wallTorchBlock((WallTorchBlock) ModBlocks.CITRINE_WALL_BRACKET.get(), "citrine_wall_bracket",
				"citrine_bracket");
		clusterBlock((AmethystClusterBlock) ModBlocks.CITRINE_CLUSTER.get());
		clusterBlock((AmethystClusterBlock) ModBlocks.LARGE_CITRINE_BUD.get());
		clusterBlock((AmethystClusterBlock) ModBlocks.MEDIUM_CITRINE_BUD.get());
		clusterBlock((AmethystClusterBlock) ModBlocks.SMALL_CITRINE_BUD.get());

		// Blueberry crop
		makeBlueberryCrop((CropBlock) ModBlocks.BLUEBERRY_CROP.get(), "blueberry_stage", "blueberry_stage");

		// Bubblefruit crop
		makeBubblefruitCrop((CropBlock) ModBlocks.BUBBLEFRUIT_CROP.get(), "bubblefruit_stage", "bubblefruit_stage");

		// Torchflower
		simpleBlockWithItem(ModBlocks.ILLUMINATED_TORCHFLOWER.get(),
				models().cross(blockTexture(ModBlocks.ILLUMINATED_TORCHFLOWER.get()).getPath(),
						blockTexture(ModBlocks.ILLUMINATED_TORCHFLOWER.get())).renderType("cutout"));
		simpleBlockWithItem(ModBlocks.POTTED_ILLUMINATED_TORCHFLOWER.get(),
				models().singleTexture("potted_illuminated_torchflower", new ResourceLocation("flower_pot_cross"),
						"plant", blockTexture(ModBlocks.ILLUMINATED_TORCHFLOWER.get())).renderType("cutout"));

		// Carpentry table
		cubeBlockWithItem(ModBlocks.CARPENTRY_TABLE, "carpentry_table");
		// Sculk jaw
		cubeBlockWithItem(ModBlocks.SCULK_JAW, "sculk_jaw");

		// Nimbula Polyp
		makeNimbulaPolyp(ModBlocks.NIMBULA_POLYP.get(), "nimbula_polyp", "nimbula_polyp_stage");

		signBlock(((StandingSignBlock) ModBlocks.PALM_SIGN.get()), ((WallSignBlock) ModBlocks.PALM_WALL_SIGN.get()),
				blockTexture(ModBlocks.PALM_PLANKS.get()));

		hangingSignBlock(ModBlocks.PALM_HANGING_SIGN.get(), ModBlocks.PALM_WALL_HANGING_SIGN.get(),
				blockTexture(ModBlocks.PALM_PLANKS.get()));

		// Palm Sapling
		simpleBlockWithItem(ModBlocks.PALM_SAPLING.get(), models()
				.cross(blockTexture(ModBlocks.PALM_SAPLING.get()).getPath(), blockTexture(ModBlocks.PALM_SAPLING.get()))
				.renderType("cutout"));

		// Sculk root
		sculkRootBlock((SculkRootBlock) ModBlocks.SCULK_ROOTS.get(), "cutout");

		// Resonator block
		resonatorBlock((ResonatorBlock) ModBlocks.RESONATOR_BLOCK.get(), "resonator", "resonator");
		
		// Charcoal block
		blockWithItem(ModBlocks.CHARCOAL_BLOCK);
		
		// Ore and material stairs
		stairsBlock(((StairBlock) ModBlocks.COAL_STAIRS.get()), blockTexture(Blocks.COAL_BLOCK));
		stairsBlock(((StairBlock) ModBlocks.CHARCOAL_STAIRS.get()), blockTexture(ModBlocks.CHARCOAL_BLOCK.get()));
		stairsBlock(((StairBlock) ModBlocks.RAW_COPPER_STAIRS.get()), blockTexture(Blocks.RAW_COPPER_BLOCK));
		stairsBlock(((StairBlock) ModBlocks.RAW_IRON_STAIRS.get()), blockTexture(Blocks.RAW_IRON_BLOCK));
		stairsBlock(((StairBlock) ModBlocks.IRON_STAIRS.get()), blockTexture(Blocks.IRON_BLOCK));
		stairsBlock(((StairBlock) ModBlocks.RAW_GOLD_STAIRS.get()), blockTexture(Blocks.RAW_GOLD_BLOCK));
		stairsBlock(((StairBlock) ModBlocks.GOLD_STAIRS.get()), blockTexture(Blocks.GOLD_BLOCK));
		stairsBlock(((StairBlock) ModBlocks.REDSTONE_STAIRS.get()), blockTexture(Blocks.REDSTONE_BLOCK));
		stairsBlock(((StairBlock) ModBlocks.LAPIS_STAIRS.get()), blockTexture(Blocks.LAPIS_BLOCK));
		stairsBlock(((StairBlock) ModBlocks.EMERALD_STAIRS.get()), blockTexture(Blocks.EMERALD_BLOCK));
		stairsBlock(((StairBlock) ModBlocks.DIAMOND_STAIRS.get()), blockTexture(Blocks.DIAMOND_BLOCK));
		stairsBlock(((StairBlock) ModBlocks.NETHERITE_STAIRS.get()), blockTexture(Blocks.NETHERITE_BLOCK));
		stairsBlock(((StairBlock) ModBlocks.OBSIDIAN_STAIRS.get()), blockTexture(Blocks.OBSIDIAN));
		stairsBlock(((StairBlock) ModBlocks.CRYING_OBSIDIAN_STAIRS.get()), blockTexture(Blocks.CRYING_OBSIDIAN));
		stairsBlock(((StairBlock) ModBlocks.AMETHYST_STAIRS.get()), blockTexture(Blocks.AMETHYST_BLOCK));
		stairsBlock(((StairBlock) ModBlocks.ZIRCON_STAIRS.get()), blockTexture(ModBlocks.ZIRCON_BLOCK.get()));
		stairsBlock(((StairBlock) ModBlocks.RAW_ZIRCONIUM_STAIRS.get()), blockTexture(ModBlocks.RAW_ZIRCONIUM_BLOCK.get()));
		stairsBlock(((StairBlock) ModBlocks.ZIRCONIUM_STAIRS.get()), blockTexture(ModBlocks.ZIRCONIUM_BLOCK.get()));
		stairsBlock(((StairBlock) ModBlocks.CITRINE_STAIRS.get()), blockTexture(ModBlocks.CITRINE_BLOCK.get()));
		
		// Ore and material slabs
		slabBlock(((SlabBlock) ModBlocks.COAL_SLAB.get()), blockTexture(Blocks.COAL_BLOCK), blockTexture(Blocks.COAL_BLOCK));
		slabBlock(((SlabBlock) ModBlocks.CHARCOAL_SLAB.get()), blockTexture(ModBlocks.CHARCOAL_BLOCK.get()), blockTexture(ModBlocks.CHARCOAL_BLOCK.get()));
		slabBlock(((SlabBlock) ModBlocks.AMETHYST_SLAB.get()), blockTexture(Blocks.AMETHYST_BLOCK), blockTexture(Blocks.AMETHYST_BLOCK));
		slabBlock(((SlabBlock) ModBlocks.RAW_COPPER_SLAB.get()), blockTexture(Blocks.RAW_COPPER_BLOCK), blockTexture(Blocks.RAW_COPPER_BLOCK));
		slabBlock(((SlabBlock) ModBlocks.RAW_IRON_SLAB.get()), blockTexture(Blocks.RAW_IRON_BLOCK), blockTexture(Blocks.RAW_IRON_BLOCK));
		slabBlock(((SlabBlock) ModBlocks.IRON_SLAB.get()), blockTexture(Blocks.IRON_BLOCK), blockTexture(Blocks.IRON_BLOCK));
		slabBlock(((SlabBlock) ModBlocks.RAW_GOLD_SLAB.get()), blockTexture(Blocks.RAW_GOLD_BLOCK), blockTexture(Blocks.RAW_GOLD_BLOCK));
		slabBlock(((SlabBlock) ModBlocks.GOLD_SLAB.get()), blockTexture(Blocks.GOLD_BLOCK), blockTexture(Blocks.GOLD_BLOCK));
		slabBlock(((SlabBlock) ModBlocks.REDSTONE_SLAB.get()), blockTexture(Blocks.REDSTONE_BLOCK), blockTexture(Blocks.REDSTONE_BLOCK));
		slabBlock(((SlabBlock) ModBlocks.LAPIS_SLAB.get()), blockTexture(Blocks.LAPIS_BLOCK), blockTexture(Blocks.LAPIS_BLOCK));
		slabBlock(((SlabBlock) ModBlocks.EMERALD_SLAB.get()), blockTexture(Blocks.EMERALD_BLOCK), blockTexture(Blocks.EMERALD_BLOCK));
		slabBlock(((SlabBlock) ModBlocks.DIAMOND_SLAB.get()), blockTexture(Blocks.DIAMOND_BLOCK), blockTexture(Blocks.DIAMOND_BLOCK));
		slabBlock(((SlabBlock) ModBlocks.NETHERITE_SLAB.get()), blockTexture(Blocks.NETHERITE_BLOCK), blockTexture(Blocks.NETHERITE_BLOCK));
		slabBlock(((SlabBlock) ModBlocks.OBSIDIAN_SLAB.get()), blockTexture(Blocks.OBSIDIAN), blockTexture(Blocks.OBSIDIAN));
		slabBlock(((SlabBlock) ModBlocks.CRYING_OBSIDIAN_SLAB.get()), blockTexture(Blocks.CRYING_OBSIDIAN), blockTexture(Blocks.CRYING_OBSIDIAN));
		slabBlock(((SlabBlock) ModBlocks.ZIRCON_SLAB.get()), blockTexture(ModBlocks.ZIRCON_BLOCK.get()), blockTexture(ModBlocks.ZIRCON_BLOCK.get()));
		slabBlock(((SlabBlock) ModBlocks.RAW_ZIRCONIUM_SLAB.get()), blockTexture(ModBlocks.RAW_ZIRCONIUM_BLOCK.get()), blockTexture(ModBlocks.RAW_ZIRCONIUM_BLOCK.get()));
		slabBlock(((SlabBlock) ModBlocks.ZIRCONIUM_SLAB.get()), blockTexture(ModBlocks.ZIRCONIUM_BLOCK.get()), blockTexture(ModBlocks.ZIRCONIUM_BLOCK.get()));
		slabBlock(((SlabBlock) ModBlocks.CITRINE_SLAB.get()), blockTexture(ModBlocks.CITRINE_BLOCK.get()), blockTexture(ModBlocks.CITRINE_BLOCK.get()));
		
		
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

	protected void blockWithItem(RegistryObject<Block> blockRegistryObject) {
		simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
	}

	protected void blockWithItem(RegistryObject<Block> blockRegistryObject, String renderType) {
		Block block = blockRegistryObject.get();
		ModelFile model = models().cubeAll(blockTexture(block).toString(), blockTexture(block)).renderType(renderType);
		simpleBlock(block, model);
		simpleBlockItem(block, model);
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

	private ConfiguredModel[] bubblefruitStates(BlockState state, CropBlock block, String modelName,
			String textureName) {
		ConfiguredModel[] models = new ConfiguredModel[1];
		models[0] = new ConfiguredModel(models()
				.cross(modelName + state.getValue(((BubblefruitCropBlock) block).getAgeProperty()),
						new ResourceLocation(ZirconMod.MOD_ID,
								"block/" + textureName
										+ state.getValue(((BubblefruitCropBlock) block).getAgeProperty())))
				.renderType("cutout"));

		return models;
	}

	private ConfiguredModel[] zirconLampStates(BlockState state, ZirconLampBlock block, String modelName,
			String textureName) {
		ConfiguredModel[] models = new ConfiguredModel[1];
		models[0] = new ConfiguredModel(models().cubeAll(
				modelName + "_" + (state.getValue((block).getLitProperty()) ? "lit" : "unlit"),
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

	private void frondBlock(FrondBlock block) {
		getVariantBuilder(block).forAllStates(state -> {
			Direction facing = state.getValue(FrondBlock.getFacingProperty());
			int count = state.getValue(FrondBlock.getCountProperty());

			int yRot = (((int) facing.toYRot()) + 180) % 360;

			ModelFile model;

			model = models()
					.withExistingParent(blockTexture(block).toString() + "_count" + count,
							new ResourceLocation(ZirconMod.MOD_ID,
									"block/" + "template_frond_count"
											+ (state.getValue(FrondBlock.getCountProperty()))))
					.renderType("cutout").texture("frond", blockTexture(block).toString());

			return ConfiguredModel.builder().modelFile(model).rotationY(yRot).build();
		});
	}

	private void anvilBlock(AnvilBlock block) {
		getVariantBuilder(block).forAllStates(state -> {
			Direction facing = state.getValue(AnvilBlock.FACING);

			int yRot = (((int) facing.toYRot()) + 180) % 360;

			ModelFile model;

			model = models()
					.withExistingParent(blockTexture(block).toString(), new ResourceLocation("block/template_anvil"))
					.texture("particle", blockTexture(block).toString())
					.texture("body", blockTexture(block).toString())
					.texture("top", blockTexture(block).toString() + "_top");

			return ConfiguredModel.builder().modelFile(model).rotationY(yRot).build();
		});
	}

	private void floorFrondBlock(FloorFrondBlock block) {
		getVariantBuilder(block).forAllStates(state -> {
			ModelFile model;

			model = models()
					.withExistingParent(blockTexture(block).toString(),
							new ResourceLocation(ZirconMod.MOD_ID, "block/" + "template_floor_frond"))
					.renderType("cutout").texture("frond", blockTexture(block).toString());

			return ConfiguredModel.builder().modelFile(model).build();
		});
	}

	private void palmFruitBlock(PalmFruitBlock block) {
		getVariantBuilder(block).forAllStates(state -> {
			Direction facing = state.getValue(PalmFruitBlock.FACING);
			int age = state.getValue(PalmFruitBlock.AGE);

			int yRot = ((int) facing.toYRot()) % 360;

			ModelFile model;

			model = models()
					.withExistingParent(blockTexture(block).toString() + "_age" + age,
							new ResourceLocation(ZirconMod.MOD_ID, "block/" + "template_palm_fruit"))
					.renderType("cutout").texture("fruit", blockTexture(block).toString() + "_age" + age);

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

	protected void makeBlueberryCrop(CropBlock block, String modelName, String textureName) {
		Function<BlockState, ConfiguredModel[]> function = state -> blueberryStates(state, block, modelName,
				textureName);

		getVariantBuilder(block).forAllStates(function);
	}

	protected void makeBubblefruitCrop(CropBlock block, String modelName, String textureName) {
		Function<BlockState, ConfiguredModel[]> function = state -> bubblefruitStates(state, block, modelName,
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

	public void resonatorBlock(ResonatorBlock block, String modelName, String texture) {
		getVariantBuilder(block).forAllStates(state -> {
			Direction facing = state.getValue(ResonatorBlock.FACING);
			int charge = state.getValue(ResonatorBlock.CHARGE);
			int yRot = ((int) facing.toYRot()) + 180;
			ModelFile model = models().cube(modelName + "_" + charge, modLoc("block/" + texture + "_down"),
					modLoc("block/" + texture + "_up"), modLoc("block/" + texture + "_front"),
					modLoc("block/" + texture + "_side_" + charge), modLoc("block/" + texture + "_side_" + charge),
					modLoc("block/" + texture + "_side_" + charge))
					.texture("particle", modLoc("block/" + texture + "_front"));

			return ConfiguredModel.builder().modelFile(model).rotationY(yRot).build();
		});
		simpleBlockItem(block,
				models().cube(modelName, modLoc("block/" + texture + "_down"), modLoc("block/" + texture + "_up"),
						modLoc("block/" + texture + "_front"), modLoc("block/" + texture + "_side_" + 0),
						modLoc("block/" + texture + "_side_" + 0), modLoc("block/" + texture + "_side_" + 0)));
	}

	public void torchBlock(TorchBlock block, String modelName, String texture) {
		getVariantBuilder(block).forAllStates(state -> {
			ModelFile model = models().withExistingParent(modelName, mcLoc("block/template_torch")).renderType("cutout")
					.texture("torch", modLoc("block/" + texture));

			return ConfiguredModel.builder().modelFile(model).build();
		});

	}

	private void clusterBlock(AmethystClusterBlock block) {
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

	@SuppressWarnings("unused")
	private void directionalBlock(DirectionalBlock block, String renderType) {
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

	private void directionalPassageBlock(DirectionalPassageBlock block, String renderType) {
		getVariantBuilder(block).forAllStates(state -> {
			Direction facing = state.getValue(DirectionalBlock.FACING);
			int yRot = (((int) facing.toYRot()) + 180) % 360;
			int xRot = 0;
			String isSolid = state.getValue(DirectionalPassageBlock.SOLID) ? "_solid" : "_walkable";

			ModelFile model;
			if (facing == Direction.UP) {
				xRot = 0;
				model = models()
						.withExistingParent(blockTexture(block).toString() + isSolid + "_up",
								mcLoc("block/orientable_vertical"))
						.renderType(renderType).texture("front", blockTexture(block) + isSolid + "_front")
						.texture("side", blockTexture(block) + isSolid + "_side");
			} else if (facing == Direction.DOWN) {
				xRot = 180;
				model = models()
						.withExistingParent(blockTexture(block).toString() + isSolid + "_down",
								mcLoc("block/orientable_vertical"))
						.renderType(renderType).texture("front", blockTexture(block) + isSolid + "_front")
						.texture("side", blockTexture(block) + isSolid + "_side");
			} else {
				model = models()
						.withExistingParent(blockTexture(block).toString() + isSolid + "_horizontal",
								mcLoc("block/orientable"))
						.renderType(renderType).texture("front", blockTexture(block) + isSolid + "_front")
						.texture("side", blockTexture(block) + isSolid + "_side")
						.texture("top", blockTexture(block) + isSolid + "_top");
			}

			return ConfiguredModel.builder().modelFile(model).rotationY(yRot).rotationX(xRot).build();
		});
		simpleBlockItem(block,
				models().withExistingParent(blockTexture(block).toString() + "_horizontal", mcLoc("block/orientable"))
						.renderType(renderType).texture("front", blockTexture(block) + "_solid_front")
						.texture("side", blockTexture(block) + "_solid_side")
						.texture("top", blockTexture(block) + "_solid_top"));
	}

	private void sculkRootBlock(SculkRootBlock block, String renderType) {
		getVariantBuilder(block).forAllStates(state -> {
			int stage = state.getValue(SculkRootBlock.STAGE);
			ModelFile model;
			model = models()
					.cross(blockTexture(block).toString() + "_stage" + stage,
							new ResourceLocation(blockTexture(block).toString() + "_stage" + stage))
					.renderType(renderType);
			return ConfiguredModel.builder().modelFile(model).build();
		});
	}

	protected void crossBlock(Block block, String modelName) {
		simpleBlock(block, models().cross(modelName, blockTexture(block)));
	}
}