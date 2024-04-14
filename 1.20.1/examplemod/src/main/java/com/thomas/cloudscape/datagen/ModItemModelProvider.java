package com.thomas.cloudscape.datagen;

import com.thomas.cloudscape.ZirconMod;
import com.thomas.cloudscape.block.ModBlocks;
import com.thomas.cloudscape.item.ModItems;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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

		simpleItem(ModItems.SPEEDOMETER);
		simpleItem(ModItems.HEART_OF_THE_SKY);
		simpleItem(ModItems.HAILSTONE);
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
		simpleItem(ModItems.BUBBLEFRUIT);
		simpleItem(ModItems.BERRY_PIE);
		simpleItem(ModItems.PALM_SEEDS);
		simpleItem(ModItems.CUT_CITRINE);
		simpleItem(ModItems.ANCIENT_FEATHER_WINGS);
		simpleItem(ModItems.FEATHER_WINGS);
		simpleItem(ModItems.LEATHER_WINGS);
		simpleItem(ModItems.CHAINMAIL_WINGS);
		simpleItem(ModItems.COPPER_WINGS);
		simpleItem(ModItems.GOLDEN_WINGS);
		simpleItem(ModItems.IRON_WINGS);
		simpleItem(ModItems.DIAMOND_WINGS);
		simpleItem(ModItems.CITRINE_WINGS);
		simpleItem(ModItems.ZIRCONIUM_WINGS);
		simpleItem(ModItems.NETHERITE_WINGS);
		simpleItem(ModItems.CITRINE_BRACKET);
		simpleItem(ModItems.CITRINE_SHARD);
		simpleItem(ModItems.PALM_BOAT);
		simpleItem(ModItems.PALM_CHEST_BOAT);
		simpleBlockItem(ModBlocks.PALM_SAPLING);
		simpleBlockItem(ModBlocks.CITRINE_LANTERN);
		simpleBlockItemBlockTexture(ModBlocks.ILLUMINATED_TORCHFLOWER);
		simpleBlockItemBlockTexture(ModBlocks.WHITE_ORCHID);
		simpleBlockItem(ModBlocks.CITRINE_CLUSTER);
		simpleBlockItem(ModBlocks.LARGE_CITRINE_BUD);
		simpleBlockItem(ModBlocks.MEDIUM_CITRINE_BUD);
		simpleBlockItem(ModBlocks.SMALL_CITRINE_BUD);
		simpleBlockItem(ModBlocks.SCULK_ROOTS);
		simpleItem(ModItems.GUST_BOTTLE);
		simpleItem(ModItems.TEMPEST_BOTTLE);
		simpleItem(ModItems.WIND_BAG);
		simpleItem(ModItems.EMPTY_WIND_BAG);

		simpleItem(ModItems.PALM_SIGN);
		simpleItem(ModItems.PALM_HANGING_SIGN);
		simpleBlockItem(ModBlocks.PALM_DOOR);
		simpleBlockModel(ModBlocks.ZIRCON_LAMP);
		simpleLogModel(ModBlocks.PALM_LOG);
		simpleLogModel(ModBlocks.PALM_WOOD);
		simpleLogModel(ModBlocks.STRIPPED_PALM_LOG);
		simpleLogModel(ModBlocks.STRIPPED_PALM_WOOD);
		simpleLogModel(ModBlocks.PETRIFIED_LOG);
		simpleBlockItem(ModBlocks.PALM_TRUNK);
		simpleBlockItem(ModBlocks.PALM_FRUIT);
		simpleBlockItem(ModBlocks.PALM_FROND);

		fenceItem(ModBlocks.PALM_FENCE, ModBlocks.PALM_PLANKS);
		buttonItem(ModBlocks.PALM_BUTTON, ModBlocks.PALM_PLANKS);

		evenSimplerBlockItem(ModBlocks.PALM_STAIRS);
		evenSimplerBlockItem(ModBlocks.PALM_SLAB);
		evenSimplerBlockItem(ModBlocks.PALM_PRESSURE_PLATE);
		evenSimplerBlockItem(ModBlocks.PALM_FENCE_GATE);

		trapdoorItem(ModBlocks.PALM_TRAPDOOR);

		buttonItem(ModBlocks.COPPER_BUTTON, Blocks.CUT_COPPER);
		buttonItem(ModBlocks.EXPOSED_COPPER_BUTTON, Blocks.EXPOSED_CUT_COPPER);
		buttonItem(ModBlocks.WEATHERED_COPPER_BUTTON, Blocks.WEATHERED_CUT_COPPER);
		buttonItem(ModBlocks.OXIDIZED_COPPER_BUTTON, Blocks.OXIDIZED_CUT_COPPER);
		buttonItem(ModBlocks.WAXED_COPPER_BUTTON, Blocks.CUT_COPPER);
		buttonItem(ModBlocks.WAXED_EXPOSED_COPPER_BUTTON, Blocks.EXPOSED_CUT_COPPER);
		buttonItem(ModBlocks.WAXED_WEATHERED_COPPER_BUTTON, Blocks.WEATHERED_CUT_COPPER);
		buttonItem(ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON, Blocks.OXIDIZED_CUT_COPPER);

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

		handheldItem(ModItems.CITRINE_SWORD);
		handheldItem(ModItems.CITRINE_PICKAXE);
		handheldItem(ModItems.CITRINE_AXE);
		handheldItem(ModItems.CITRINE_SHOVEL);
		handheldItem(ModItems.CITRINE_HOE);

		simpleItem(ModItems.CITRINE_HELMET);
		simpleItem(ModItems.CITRINE_CHESTPLATE);
		simpleItem(ModItems.CITRINE_LEGGINGS);
		simpleItem(ModItems.CITRINE_BOOTS);

		withExistingParent(ModItems.MOLE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
		withExistingParent(ModItems.NIMBULA_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
		withExistingParent(ModItems.TEMPEST_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
		withExistingParent(ModItems.WISP_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
		withExistingParent(ModItems.GUST_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
		withExistingParent(ModItems.WRAITH_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

		withExistingParent(ModBlocks.NIMBULA_POLYP.getId().getPath(), modLoc("block/nimbula_polyp0"));
		withExistingParent(ModBlocks.NETHERITE_ANVIL.getId().getPath(), modLoc("block/netherite_anvil"));

		simpleItem(ModItems.NIMBULA_GEL);

		// Cloud blocks
		wallItem(ModBlocks.CLOUD_BRICK_WALL, ModBlocks.CLOUD_BRICKS);
		wallItem(ModBlocks.THUNDER_CLOUD_BRICK_WALL, ModBlocks.THUNDER_CLOUD_BRICKS);
		evenSimplerBlockItem(ModBlocks.CLOUD_BRICK_STAIRS);
		evenSimplerBlockItem(ModBlocks.CLOUD_BRICK_SLAB);
		evenSimplerBlockItem(ModBlocks.THUNDER_CLOUD_BRICK_STAIRS);
		evenSimplerBlockItem(ModBlocks.THUNDER_CLOUD_BRICK_SLAB);
		evenSimplerBlockItem(ModBlocks.CLOUD_BRICK_PILLAR);
		evenSimplerBlockItem(ModBlocks.THUNDER_CLOUD_BRICK_PILLAR);

		// Ore and material stairs
		evenSimplerBlockItem(ModBlocks.COAL_STAIRS);
		evenSimplerBlockItem(ModBlocks.CHARCOAL_STAIRS);
		evenSimplerBlockItem(ModBlocks.RAW_COPPER_STAIRS);
		evenSimplerBlockItem(ModBlocks.RAW_IRON_STAIRS);
		evenSimplerBlockItem(ModBlocks.IRON_STAIRS);
		evenSimplerBlockItem(ModBlocks.RAW_GOLD_STAIRS);
		evenSimplerBlockItem(ModBlocks.GOLD_STAIRS);
		evenSimplerBlockItem(ModBlocks.REDSTONE_STAIRS);
		evenSimplerBlockItem(ModBlocks.LAPIS_STAIRS);
		evenSimplerBlockItem(ModBlocks.EMERALD_STAIRS);
		evenSimplerBlockItem(ModBlocks.DIAMOND_STAIRS);
		evenSimplerBlockItem(ModBlocks.NETHERITE_STAIRS);
		evenSimplerBlockItem(ModBlocks.AMETHYST_STAIRS);
		evenSimplerBlockItem(ModBlocks.OBSIDIAN_STAIRS);
		evenSimplerBlockItem(ModBlocks.CRYING_OBSIDIAN_STAIRS);
		evenSimplerBlockItem(ModBlocks.ZIRCON_STAIRS);
		evenSimplerBlockItem(ModBlocks.RAW_ZIRCONIUM_STAIRS);
		evenSimplerBlockItem(ModBlocks.ZIRCONIUM_STAIRS);
		evenSimplerBlockItem(ModBlocks.CITRINE_STAIRS);

		// Ore and material slabs
		evenSimplerBlockItem(ModBlocks.COAL_SLAB);
		evenSimplerBlockItem(ModBlocks.CHARCOAL_SLAB);
		evenSimplerBlockItem(ModBlocks.RAW_COPPER_SLAB);
		evenSimplerBlockItem(ModBlocks.RAW_IRON_SLAB);
		evenSimplerBlockItem(ModBlocks.IRON_SLAB);
		evenSimplerBlockItem(ModBlocks.RAW_GOLD_SLAB);
		evenSimplerBlockItem(ModBlocks.GOLD_SLAB);
		evenSimplerBlockItem(ModBlocks.REDSTONE_SLAB);
		evenSimplerBlockItem(ModBlocks.LAPIS_SLAB);
		evenSimplerBlockItem(ModBlocks.EMERALD_SLAB);
		evenSimplerBlockItem(ModBlocks.DIAMOND_SLAB);
		evenSimplerBlockItem(ModBlocks.NETHERITE_SLAB);
		evenSimplerBlockItem(ModBlocks.AMETHYST_SLAB);
		evenSimplerBlockItem(ModBlocks.OBSIDIAN_SLAB);
		evenSimplerBlockItem(ModBlocks.CRYING_OBSIDIAN_SLAB);
		evenSimplerBlockItem(ModBlocks.ZIRCON_SLAB);
		evenSimplerBlockItem(ModBlocks.RAW_ZIRCONIUM_SLAB);
		evenSimplerBlockItem(ModBlocks.ZIRCONIUM_SLAB);
		evenSimplerBlockItem(ModBlocks.CITRINE_SLAB);

		// Spears
		//spearItem(ModItems.ZIRCONIUM_SPEAR);
		
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

	public void buttonItem(RegistryObject<Block> block, Block baseBlock) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
				.texture("texture",
						new ResourceLocation("block/" + ForgeRegistries.BLOCKS.getKey(baseBlock).getPath()));
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
	
	// TODO
	// Make a template, and figure out how to link the textures?
	/*private ItemModelBuilder spearItem(RegistryObject<Item> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation(ZirconMod.MOD_ID, "item/spear")).texture("layer0",
				new ResourceLocation(ZirconMod.MOD_ID, "item/" + item.getId().getPath()));
	}*/
}