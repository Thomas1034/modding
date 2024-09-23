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

		ModBlocks.VERDANT_HEARTWOOD.addItemModels(this);
		ModBlocks.VERDANT.addItemModels(this);

		simpleLogModel(ModBlocks.IMBUED_VERDANT_HEARTWOOD_LOG);
		simpleItem(ModItems.HEART_FRAGMENT);
		simpleItem(ModItems.YAM);

		otherTextureItem(ModItems.WATER_HEMLOCK, "block/water_hemlock_top");
		simpleItem(ModItems.GOLDEN_CASSAVA);
		simpleItem(ModItems.COOKED_GOLDEN_CASSAVA);
		simpleItem(ModItems.SPARKLING_STARCH);
		simpleItem(ModItems.COOKED_CASSAVA);
		simpleItem(ModItems.BITTER_BREAD);
		simpleItem(ModItems.BITTER_STARCH);
		simpleItem(ModItems.STARCH);
		simpleItem(ModItems.BITTER_CASSAVA_CUTTINGS);
		simpleItem(ModItems.CASSAVA_CUTTINGS);
		simpleItem(ModItems.BITTER_CASSAVA);
		simpleItem(ModItems.CASSAVA);
		simpleBlockItemBlockTexture(ModBlocks.WILD_CASSAVA);
		simpleBlockItemBlockTexture(ModBlocks.FRAME_BLOCK);
		simpleBlockItemBlockTexture(ModBlocks.ROPE);
		simpleBlockItemBlockTexture(ModBlocks.THORN_SPIKES);
		simpleBlockItemBlockTexture(ModBlocks.IRON_SPIKES);
		simpleBlockItemBlockTexture(ModBlocks.THORN_BUSH);
		simpleBlockItemBlockTexture(ModBlocks.BLEEDING_HEART);
		simpleBlockItemBlockTexture(ModBlocks.WILD_COFFEE);
		simpleBlockItem(ModBlocks.VERDANT_VINE);
		simpleBlockItem(ModBlocks.LEAFY_VERDANT_VINE);
		simpleBlockItem(ModBlocks.VERDANT_TENDRIL);
		simpleBlockItemBlockTexture(ModBlocks.POISON_IVY);
		simpleItem(ModItems.SHORT_ROPE_COIL);
		simpleItem(ModItems.ROPE_COIL);
		simpleItem(ModItems.VERDANT_HEARTWOOD_HELMET);
		simpleItem(ModItems.VERDANT_HEARTWOOD_CHESTPLATE);
		simpleItem(ModItems.VERDANT_HEARTWOOD_LEGGINGS);
		simpleItem(ModItems.VERDANT_HEARTWOOD_BOOTS);
		simpleItem(ModItems.IMBUED_VERDANT_HEARTWOOD_HELMET);
		simpleItem(ModItems.IMBUED_VERDANT_HEARTWOOD_CHESTPLATE);
		simpleItem(ModItems.IMBUED_VERDANT_HEARTWOOD_LEGGINGS);
		simpleItem(ModItems.IMBUED_VERDANT_HEARTWOOD_BOOTS);

		simpleItem(ModItems.TOXIC_ASH);
		simpleItem(ModItems.TOXIC_ASH_BUCKET);
		simpleItem(ModItems.TOXIC_SOLUTION_BUCKET);
		simpleLogModel(ModBlocks.POISON_IVY_BLOCK);
		simpleLogModel(ModBlocks.TOXIC_ASH_BLOCK);
		simpleLogModel(ModBlocks.ROTTEN_WOOD);

		evenSimplerBlockItem(ModBlocks.WILTED_VERDANT_LEAVES);
		evenSimplerBlockItem(ModBlocks.VERDANT_LEAVES);
		evenSimplerBlockItem(ModBlocks.THORNY_VERDANT_LEAVES);
		evenSimplerBlockItem(ModBlocks.POISON_IVY_VERDANT_LEAVES);

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

		simpleBlockItem(ModBlocks.THORN_TRAP);
		simpleBlockItem(ModBlocks.IRON_TRAP);

	}

	public void otherModel(RegistryObject<Block> block, String otherLocation) {
		this.withExistingParent(Verdant.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
				modLoc(otherLocation));
	}

	private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
				new ResourceLocation(Verdant.MOD_ID, "item/" + item.getId().getPath()));
	}

	private ItemModelBuilder otherTextureItem(RegistryObject<Item> item, String texture) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
				new ResourceLocation(Verdant.MOD_ID, texture));
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

	@SuppressWarnings("unused")
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