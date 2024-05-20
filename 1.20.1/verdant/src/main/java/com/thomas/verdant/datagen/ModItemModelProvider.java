package com.thomas.verdant.datagen;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.item.ModItems;

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
		super(output, Verdant.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		simpleBlockItemBlockTexture(ModBlocks.ROPE);
		simpleBlockItemBlockTexture(ModBlocks.THORN_BUSH);
		simpleBlockItemBlockTexture(ModBlocks.BLEEDING_HEART);
		simpleBlockItemBlockTexture(ModBlocks.WILD_COFFEE);
		simpleBlockItem(ModBlocks.VERDANT_VINE);
		simpleBlockItem(ModBlocks.LEAFY_VERDANT_VINE);
		simpleBlockItem(ModBlocks.VERDANT_TENDRIL);
		simpleBlockItemBlockTexture(ModBlocks.POISON_IVY);
		simpleItem(ModItems.VERDANT_HEARTWOOD_HELMET);
		simpleItem(ModItems.VERDANT_HEARTWOOD_CHESTPLATE);
		simpleItem(ModItems.VERDANT_HEARTWOOD_LEGGINGS);
		simpleItem(ModItems.VERDANT_HEARTWOOD_BOOTS);
		simpleItem(ModItems.VERDANT_BOAT);
		simpleItem(ModItems.VERDANT_CHEST_BOAT);
		simpleItem(ModItems.VERDANT_SIGN);
		simpleItem(ModItems.VERDANT_HANGING_SIGN);
		simpleBlockItem(ModBlocks.VERDANT_DOOR);
		simpleLogModel(ModBlocks.ROTTEN_WOOD);
		simpleLogModel(ModBlocks.VERDANT_LOG);
		simpleLogModel(ModBlocks.VERDANT_WOOD);
		simpleLogModel(ModBlocks.STRIPPED_VERDANT_LOG);
		simpleLogModel(ModBlocks.STRIPPED_VERDANT_WOOD);

		fenceItem(ModBlocks.VERDANT_FENCE, ModBlocks.VERDANT_PLANKS);
		buttonItem(ModBlocks.VERDANT_BUTTON, ModBlocks.VERDANT_PLANKS);

		evenSimplerBlockItem(ModBlocks.VERDANT_LEAVES);
		evenSimplerBlockItem(ModBlocks.THORNY_VERDANT_LEAVES);
		evenSimplerBlockItem(ModBlocks.POISON_IVY_VERDANT_LEAVES);
		evenSimplerBlockItem(ModBlocks.VERDANT_STAIRS);
		evenSimplerBlockItem(ModBlocks.VERDANT_SLAB);
		evenSimplerBlockItem(ModBlocks.VERDANT_PRESSURE_PLATE);
		evenSimplerBlockItem(ModBlocks.VERDANT_FENCE_GATE);

		trapdoorItem(ModBlocks.VERDANT_TRAPDOOR);
		
		simpleItem(ModItems.VERDANT_HEARTWOOD_BOAT);
		simpleItem(ModItems.VERDANT_HEARTWOOD_CHEST_BOAT);
		simpleItem(ModItems.VERDANT_HEARTWOOD_SIGN);
		simpleItem(ModItems.VERDANT_HEARTWOOD_HANGING_SIGN);
		simpleBlockItem(ModBlocks.VERDANT_HEARTWOOD_DOOR);
		simpleLogModel(ModBlocks.VERDANT_HEARTWOOD_LOG);
		simpleLogModel(ModBlocks.VERDANT_HEARTWOOD_WOOD);
		simpleLogModel(ModBlocks.STRIPPED_VERDANT_HEARTWOOD_LOG);
		simpleLogModel(ModBlocks.STRIPPED_VERDANT_HEARTWOOD_WOOD);

		fenceItem(ModBlocks.VERDANT_HEARTWOOD_FENCE, ModBlocks.VERDANT_HEARTWOOD_PLANKS);
		buttonItem(ModBlocks.VERDANT_HEARTWOOD_BUTTON, ModBlocks.VERDANT_HEARTWOOD_PLANKS);

		evenSimplerBlockItem(ModBlocks.VERDANT_HEARTWOOD_STAIRS);
		evenSimplerBlockItem(ModBlocks.VERDANT_HEARTWOOD_SLAB);
		evenSimplerBlockItem(ModBlocks.VERDANT_HEARTWOOD_PRESSURE_PLATE);
		evenSimplerBlockItem(ModBlocks.VERDANT_HEARTWOOD_FENCE_GATE);

		trapdoorItem(ModBlocks.VERDANT_HEARTWOOD_TRAPDOOR);
		
		evenSimplerBlockItem(ModBlocks.STINKING_BLOSSOM);
		simpleItem(ModItems.COFFEE_BERRIES);
		simpleItem(ModItems.ROASTED_COFFEE);
		
		evenSimplerBlockItem(ModBlocks.VERDANT_CONDUIT);

		simpleItem(ModItems.THORN);
		simpleItem(ModItems.POISON_ARROW);
		
		handheldItem(ModItems.VERDANT_HEARTWOOD_SWORD);
		handheldItem(ModItems.VERDANT_HEARTWOOD_AXE);
		handheldItem(ModItems.VERDANT_HEARTWOOD_PICKAXE);
		handheldItem(ModItems.VERDANT_HEARTWOOD_SHOVEL);
		handheldItem(ModItems.VERDANT_HEARTWOOD_HOE);
		handheldItem(ModItems.VERDANT_HEARTWOOD_SHOVEL);
		
		simpleItem(ModItems.HEART_OF_THE_FOREST);
		
	}

	private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
				new ResourceLocation(Verdant.MOD_ID, "item/" + item.getId().getPath()));
	}

	public void evenSimplerBlockItem(RegistryObject<Block> block) {
		this.withExistingParent(Verdant.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
				modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
	}

	public void trapdoorItem(RegistryObject<Block> block) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
				modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
	}

	public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
				.texture("texture", new ResourceLocation(Verdant.MOD_ID,
						"block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
	}

	public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
				.texture("texture", new ResourceLocation(Verdant.MOD_ID,
						"block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
	}

	public void buttonItem(RegistryObject<Block> block, Block baseBlock) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
				.texture("texture",
						new ResourceLocation("block/" + ForgeRegistries.BLOCKS.getKey(baseBlock).getPath()));
	}

	public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
				.texture("wall", new ResourceLocation(Verdant.MOD_ID,
						"block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
	}

	private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/handheld")).texture("layer0",
				new ResourceLocation(Verdant.MOD_ID, "item/" + item.getId().getPath()));
	}

	private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
				new ResourceLocation(Verdant.MOD_ID, "item/" + item.getId().getPath()));
	}

	private ItemModelBuilder simpleBlockItemBlockTexture(RegistryObject<Block> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
				new ResourceLocation(Verdant.MOD_ID, "block/" + item.getId().getPath()));
	}

	private ItemModelBuilder simpleBlockModel(RegistryObject<Block> block) {
		return withExistingParent(block.getId().getPath(), new ResourceLocation("block/cube_all")).texture("all",
				new ResourceLocation(Verdant.MOD_ID, "block/" + block.getId().getPath()));
	}

	private ItemModelBuilder simpleLogModel(RegistryObject<Block> block) {
		return withExistingParent(block.getId().getPath(), new ResourceLocation("block/cube_column"))
				.texture("side", new ResourceLocation(Verdant.MOD_ID, "block/" + block.getId().getPath()))
				.texture("end", new ResourceLocation(Verdant.MOD_ID, "block/" + block.getId().getPath() + "_top"));
	}

}