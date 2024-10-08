package com.thomas.turbulent.datagen;

import com.thomas.turbulent.Turbulent;

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
		super(output, Turbulent.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {

	}

	private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
				new ResourceLocation(Turbulent.MOD_ID, "item/" + item.getId().getPath()));
	}

	private ItemModelBuilder otherTextureItem(RegistryObject<Item> item, String texture) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
				new ResourceLocation(Turbulent.MOD_ID, texture));
	}

	public void evenSimplerBlockItem(RegistryObject<Block> block) {
		this.withExistingParent(Turbulent.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
				modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
	}

	public void trapdoorItem(RegistryObject<Block> block) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
				modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
	}

	public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
				.texture("texture", new ResourceLocation(Turbulent.MOD_ID,
						"block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
	}

	public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
				.texture("texture", new ResourceLocation(Turbulent.MOD_ID,
						"block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
	}

	public void buttonItem(RegistryObject<Block> block, Block baseBlock) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
				.texture("texture",
						new ResourceLocation("block/" + ForgeRegistries.BLOCKS.getKey(baseBlock).getPath()));
	}

	public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
				.texture("wall", new ResourceLocation(Turbulent.MOD_ID,
						"block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
	}

	private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/handheld")).texture("layer0",
				new ResourceLocation(Turbulent.MOD_ID, "item/" + item.getId().getPath()));
	}

	private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
				new ResourceLocation(Turbulent.MOD_ID, "item/" + item.getId().getPath()));
	}

	private ItemModelBuilder simpleBlockItemBlockTexture(RegistryObject<Block> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
				new ResourceLocation(Turbulent.MOD_ID, "block/" + item.getId().getPath()));
	}

	@SuppressWarnings("unused")
	private ItemModelBuilder simpleBlockModel(RegistryObject<Block> block) {
		return withExistingParent(block.getId().getPath(), new ResourceLocation("block/cube_all")).texture("all",
				new ResourceLocation(Turbulent.MOD_ID, "block/" + block.getId().getPath()));
	}

	private ItemModelBuilder simpleLogModel(RegistryObject<Block> block) {
		return withExistingParent(block.getId().getPath(), new ResourceLocation("block/cube_column"))
				.texture("side", new ResourceLocation(Turbulent.MOD_ID, "block/" + block.getId().getPath()))
				.texture("end", new ResourceLocation(Turbulent.MOD_ID, "block/" + block.getId().getPath() + "_top"));
	}

}