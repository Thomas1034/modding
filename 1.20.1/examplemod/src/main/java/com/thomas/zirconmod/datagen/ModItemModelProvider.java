package com.thomas.zirconmod.datagen;

import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.item.ModItems;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {

	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, ZirconMod.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		simpleItem(ModItems.ZIRCON);
		simpleItem(ModItems.ZIRCON_SHARD);
		simpleItem(ModItems.RAW_ZIRCONIUM);
		simpleItem(ModItems.ZIRCONIUM_INGOT);
		simpleItem(ModItems.PINE_CONE);
		simpleItem(ModItems.FLAMING_PINE_CONE);
		simpleItem(ModItems.FLAMING_ARROW);
		simpleItem(ModItems.ECHO_POWDER);
		simpleItem(ModItems.QUICKSAND_BUCKET);
		simpleItem(ModItems.TOTEM_OF_RETURNING);
		simpleItem(ModItems.VIGIL_EYE);
		simpleItem(ModItems.ZIRCONIUM_UPGRADE_SMITHING_TEMPLATE);
		simpleItem(ModItems.BLUEBERRY);
		simpleItem(ModItems.BLUEBERRY_SEEDS);
		simpleItem(ModItems.CUT_CITRINE);
		simpleItem(ModItems.FEATHER_WINGS);
		simpleItem(ModItems.CITRINE_BRACKET);
		simpleItem(ModItems.CITRINE_SHARD);
		simpleBlockItem(ModBlocks.CITRINE_LANTERN);
		simpleBlockItemBlockTexture(ModBlocks.ILLUMINATED_TORCHFLOWER);

		simpleBlockItem(ModBlocks.PALM_DOOR);
		simpleBlockModel(ModBlocks.ZIRCON_LAMP);
		simpleLogModel(ModBlocks.PALM_LOG);
		simpleLogModel(ModBlocks.PALM_WOOD);
		simpleLogModel(ModBlocks.STRIPPED_PALM_LOG);
		simpleLogModel(ModBlocks.STRIPPED_PALM_WOOD);

		fenceItem(ModBlocks.PALM_FENCE, ModBlocks.PALM_PLANKS);
		buttonItem(ModBlocks.PALM_BUTTON, ModBlocks.PALM_PLANKS);

		evenSimplerBlockItem(ModBlocks.PALM_STAIRS);
		evenSimplerBlockItem(ModBlocks.PALM_SLAB);
		evenSimplerBlockItem(ModBlocks.PALM_PRESSURE_PLATE);
		evenSimplerBlockItem(ModBlocks.PALM_FENCE_GATE);

		trapdoorItem(ModBlocks.PALM_TRAPDOOR);

		handheldItem(ModItems.COPPER_SWORD);
		handheldItem(ModItems.COPPER_PICKAXE);
		handheldItem(ModItems.COPPER_AXE);
		handheldItem(ModItems.COPPER_SHOVEL);
		handheldItem(ModItems.COPPER_HOE);

		simpleItem(ModItems.COPPER_HELMET);
		simpleItem(ModItems.COPPER_CHESTPLATE);
		simpleItem(ModItems.COPPER_LEGGINGS);
		simpleItem(ModItems.COPPER_BOOTS);

		handheldItem(ModItems.ZIRCONIUM_SWORD);
		handheldItem(ModItems.ZIRCONIUM_PICKAXE);
		handheldItem(ModItems.ZIRCONIUM_AXE);
		handheldItem(ModItems.ZIRCONIUM_SHOVEL);
		handheldItem(ModItems.ZIRCONIUM_HOE);

		simpleItem(ModItems.ZIRCONIUM_HELMET);
		simpleItem(ModItems.ZIRCONIUM_CHESTPLATE);
		simpleItem(ModItems.ZIRCONIUM_LEGGINGS);
		simpleItem(ModItems.ZIRCONIUM_BOOTS);

		simpleItem(ModItems.CITRINE_HELMET);
		simpleItem(ModItems.CITRINE_CHESTPLATE);
		simpleItem(ModItems.CITRINE_LEGGINGS);
		simpleItem(ModItems.CITRINE_BOOTS);

		withExistingParent(ModItems.MOLE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
		withExistingParent(ModItems.NIMBULA_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

		withExistingParent(ModBlocks.NIMBULA_POLYP.getId().getPath(), modLoc("block/nimbula_polyp0"));

		simpleItem(ModItems.NIMBULA_GEL);
	}

	private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
				new ResourceLocation(ZirconMod.MOD_ID, "item/" + item.getId().getPath()));
	}

	public void evenSimplerBlockItem(RegistryObject<Block> block) {
		this.withExistingParent(ZirconMod.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
				modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
	}

	public void trapdoorItem(RegistryObject<Block> block) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
				modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
	}

	public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
				.texture("texture", new ResourceLocation(ZirconMod.MOD_ID,
						"block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
	}

	public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
				.texture("texture", new ResourceLocation(ZirconMod.MOD_ID,
						"block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
	}

	public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
				.texture("wall", new ResourceLocation(ZirconMod.MOD_ID,
						"block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
	}

	private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/handheld")).texture("layer0",
				new ResourceLocation(ZirconMod.MOD_ID, "item/" + item.getId().getPath()));
	}

	private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
				new ResourceLocation(ZirconMod.MOD_ID, "item/" + item.getId().getPath()));
	}

	private ItemModelBuilder simpleBlockItemBlockTexture(RegistryObject<Block> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
				new ResourceLocation(ZirconMod.MOD_ID, "block/" + item.getId().getPath()));
	}

	private ItemModelBuilder simpleBlockModel(RegistryObject<Block> block) {
		return withExistingParent(block.getId().getPath(), new ResourceLocation("block/cube_all")).texture("all",
				new ResourceLocation(ZirconMod.MOD_ID, "block/" + block.getId().getPath()));
	}

	private ItemModelBuilder simpleLogModel(RegistryObject<Block> block) {
		return withExistingParent(block.getId().getPath(), new ResourceLocation("block/cube_column"))
				.texture("side", new ResourceLocation(ZirconMod.MOD_ID, "block/" + block.getId().getPath()))
				.texture("end", new ResourceLocation(ZirconMod.MOD_ID, "block/" + block.getId().getPath() + "_top"));
	}
}