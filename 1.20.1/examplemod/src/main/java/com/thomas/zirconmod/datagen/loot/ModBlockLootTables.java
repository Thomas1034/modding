package com.thomas.zirconmod.datagen.loot;

import java.util.List;
import java.util.Set;

import com.thomas.zirconmod.block.ModBlocks;
import com.thomas.zirconmod.block.custom.BlueberryCropBlock;
import com.thomas.zirconmod.block.custom.PalmFruitBlock;
import com.thomas.zirconmod.item.ModItems;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockLootTables extends BlockLootSubProvider {
	public ModBlockLootTables() {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags());
	}

	@Override
	protected void generate() {

		this.dropOther(ModBlocks.WEATHER_PASSAGE_BLOCK.get(), Items.AIR);
		this.dropOther(ModBlocks.LIGHTNING_BLOCK.get(), Items.AIR);
		this.dropOther(ModBlocks.UNSTABLE_LIGHTNING_BLOCK.get(), Items.AIR);
		this.dropOther(ModBlocks.SCULK_JAW.get(), Blocks.BONE_BLOCK);
		this.dropOther(ModBlocks.SEALED_CLOUD_BRICKS.get(), Items.AIR);
		this.dropOther(ModBlocks.SEALED_THUNDER_CLOUD_BRICKS.get(), Items.AIR);
		this.dropOther(ModBlocks.SEALED_CLOUD.get(), Items.AIR);
		this.dropSelf(ModBlocks.PETRIFIED_LOG.get());
		this.dropSelf(ModBlocks.WISP_BED.get());
		this.dropSelf(ModBlocks.ZIRCON_BLOCK.get());
		this.dropSelf(ModBlocks.RAW_ZIRCONIUM_BLOCK.get());
		this.dropSelf(ModBlocks.ZIRCONIUM_BLOCK.get());
		this.dropSelf(ModBlocks.ECHO_BLOCK.get());
		this.dropSelf(ModBlocks.ZIRCON_LAMP.get());
		this.dropSelf(ModBlocks.RAW_ZIRCONIUM_BLOCK.get());
		this.dropSelf(ModBlocks.ZIRCONIUM_BLOCK.get());
		this.dropSelf(ModBlocks.ECHO_BLOCK.get());
		this.dropOther(ModBlocks.THIRSTY_PACKED_MUD.get(), Blocks.PACKED_MUD);
		this.dropOther(ModBlocks.THIRSTY_MUD_BRICKS.get(), Blocks.MUD_BRICKS);
		this.dropOther(ModBlocks.QUICKSAND.get(), Blocks.AIR);
		this.dropSelf(ModBlocks.PALM_BUTTON.get());
		this.dropSelf(ModBlocks.PALM_FENCE.get());
		this.dropSelf(ModBlocks.PALM_FENCE_GATE.get());
		this.dropSelf(ModBlocks.PALM_PLANKS.get());
		this.dropSelf(ModBlocks.PALM_LOG.get());
		this.dropSelf(ModBlocks.PALM_WOOD.get());
		this.dropSelf(ModBlocks.STRIPPED_PALM_LOG.get());
		this.dropSelf(ModBlocks.STRIPPED_PALM_WOOD.get());
		this.dropSelf(ModBlocks.PALM_PRESSURE_PLATE.get());
		this.dropSelf(ModBlocks.PALM_STAIRS.get());
		this.dropSelf(ModBlocks.PALM_TRAPDOOR.get());
		this.dropSelf(ModBlocks.CLOUD.get());
		this.dropSelf(ModBlocks.THUNDER_CLOUD.get());
		this.dropSelf(ModBlocks.CLOUD_BRICKS.get());
		this.dropSelf(ModBlocks.THUNDER_CLOUD_BRICKS.get());
		this.dropSelf(ModBlocks.CLOUD_BRICK_STAIRS.get());
		this.dropSelf(ModBlocks.THUNDER_CLOUD_BRICK_STAIRS.get());
		this.dropSelf(ModBlocks.CLOUD_BRICK_WALL.get());
		this.dropSelf(ModBlocks.THUNDER_CLOUD_BRICK_WALL.get());
		this.dropSelf(ModBlocks.CLOUD_BRICK_PILLAR.get());
		this.dropSelf(ModBlocks.THUNDER_CLOUD_BRICK_PILLAR.get());
		this.dropSelf(ModBlocks.CHISELED_CLOUD_BRICKS.get());
		this.dropSelf(ModBlocks.CHISELED_THUNDER_CLOUD_BRICKS.get());
		this.dropSelf(ModBlocks.CLOUD_CONVERTER.get());
		this.dropSelf(ModBlocks.CLOUD_INVERTER.get());
		this.dropSelf(ModBlocks.CLOUD_DETECTOR.get());
		this.dropSelf(ModBlocks.CITRINE_BLOCK.get());
		this.dropSelf(ModBlocks.CITRINE_LANTERN.get());
		this.dropSelf(ModBlocks.PALM_TRUNK.get());
		this.dropSelf(ModBlocks.PALM_FROND.get());
		this.dropSelf(ModBlocks.PALM_SAPLING.get());
		this.dropOther(ModBlocks.PALM_FLOOR_FROND.get(), ModBlocks.PALM_FROND.get());
		this.dropSelf(ModBlocks.RESONATOR_BLOCK.get());
		this.dropOther(ModBlocks.BUBBLEFRUIT_CROP.get(), ModItems.BUBBLEFRUIT.get());
		this.dropOther(ModBlocks.BUBBLEFRUIT_CROP_PLACER.get(), ModItems.BUBBLEFRUIT.get());
		
		
		LootItemCondition.Builder palmFruitLootBuilder = LootItemBlockStatePropertyCondition
				.hasBlockStateProperties(ModBlocks.PALM_FRUIT.get()).setProperties(StatePropertiesPredicate.Builder
						.properties().hasProperty(PalmFruitBlock.AGE, PalmFruitBlock.MAX_AGE));
		this.add(ModBlocks.PALM_FRUIT.get(), createCropDrops(ModBlocks.PALM_FRUIT.get(),
				Items.AIR, ModItems.PALM_SEEDS.get(), palmFruitLootBuilder));

		
		this.dropOther(ModBlocks.CITRINE_BRACKET.get(), ModItems.CITRINE_BRACKET.get());
		this.dropOther(ModBlocks.CITRINE_WALL_BRACKET.get(), ModItems.CITRINE_BRACKET.get());
		this.add(ModBlocks.PALM_SLAB.get(), block -> createSlabItemTable(ModBlocks.PALM_SLAB.get()));
		this.add(ModBlocks.PALM_DOOR.get(), block -> createDoorTable(ModBlocks.PALM_DOOR.get()));
		this.add(ModBlocks.CLOUD_BRICK_SLAB.get(), block -> createSlabItemTable(ModBlocks.CLOUD_BRICK_SLAB.get()));
		this.add(ModBlocks.THUNDER_CLOUD_BRICK_SLAB.get(),
				block -> createSlabItemTable(ModBlocks.THUNDER_CLOUD_BRICK_SLAB.get()));

		this.add(ModBlocks.PALM_SIGN.get(), block -> createSingleItemTable(ModItems.PALM_SIGN.get()));
		this.add(ModBlocks.PALM_WALL_SIGN.get(), block -> createSingleItemTable(ModItems.PALM_SIGN.get()));
		this.add(ModBlocks.PALM_HANGING_SIGN.get(), block -> createSingleItemTable(ModItems.PALM_HANGING_SIGN.get()));
		this.add(ModBlocks.PALM_WALL_HANGING_SIGN.get(),
				block -> createSingleItemTable(ModItems.PALM_HANGING_SIGN.get()));

		this.dropOther(ModBlocks.BUDDING_CITRINE.get(), Items.AIR);
		this.requireSilkTouch(ModBlocks.CITRINE_CLUSTER.get(), ModItems.CITRINE_SHARD.get(), List.of(3, 5));
		this.requireSilkTouch(ModBlocks.LARGE_CITRINE_BUD.get(), ModItems.CITRINE_SHARD.get(), List.of(2, 3));
		this.requireSilkTouch(ModBlocks.MEDIUM_CITRINE_BUD.get(), ModItems.CITRINE_SHARD.get(), List.of(1, 1));
		this.requireSilkTouch(ModBlocks.SMALL_CITRINE_BUD.get(), ModItems.CITRINE_SHARD.get());
		
		
		this.requireSilkTouch(ModBlocks.ZIRCON_ORE.get(), ModItems.ZIRCON_SHARD.get(), List.of(3, 5));
		this.requireSilkTouch(ModBlocks.DEEPSLATE_ZIRCON_ORE.get(), ModItems.ZIRCON_SHARD.get(), List.of(3, 5));

		// Blueberry crop block
		LootItemCondition.Builder blueberryLootBuilder = LootItemBlockStatePropertyCondition
				.hasBlockStateProperties(ModBlocks.BLUEBERRY_CROP.get()).setProperties(StatePropertiesPredicate.Builder
						.properties().hasProperty(BlueberryCropBlock.AGE, BlueberryCropBlock.MAX_AGE));
		this.add(ModBlocks.BLUEBERRY_CROP.get(), createCropDrops(ModBlocks.BLUEBERRY_CROP.get(),
				ModItems.BLUEBERRY.get(), ModItems.BLUEBERRY_SEEDS.get(), blueberryLootBuilder));
		
		// Torchflower stuff
		this.dropSelf(ModBlocks.ILLUMINATED_TORCHFLOWER.get());
		this.add(ModBlocks.POTTED_ILLUMINATED_TORCHFLOWER.get(),
				createPotFlowerItemTable(ModBlocks.ILLUMINATED_TORCHFLOWER.get()));

		// Villager workstations
		this.dropSelf(ModBlocks.CARPENTRY_TABLE.get());
		this.dropSelf(ModBlocks.ARCHITECT_WORKSITE.get());
		this.dropSelf(ModBlocks.BOTANIST_WORKSITE.get());
		this.dropSelf(ModBlocks.CHIEF_WORKSITE.get());
		this.dropSelf(ModBlocks.GEMSMITH_WORKSITE.get());
		this.dropSelf(ModBlocks.SCHOLAR_WORKSITE.get());
		this.dropSelf(ModBlocks.TINKERER_WORKSITE.get());

		// NimbulaPolyp
		this.add(ModBlocks.NIMBULA_POLYP.get(),
				block -> createSilkTouchDrop(ModBlocks.NIMBULA_POLYP.get(), ModBlocks.CLOUD.get().asItem()));
		
		// Sculk root
		this.requireSilkTouch(ModBlocks.SCULK_ROOTS.get(), Items.SCULK_VEIN);
		
		
	}

	protected LootTable.Builder createOreDrops(Block block, Item item, List<Integer> range) {
		return createSilkTouchDispatchTable(block,
				this.applyExplosionDecay(block, LootItem.lootTableItem(item)
						.apply(SetItemCountFunction.setCount(UniformGenerator.between(range.get(0), range.get(1))))
						.apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
	}

	protected LootTable.Builder createSilkTouchDrop(Block pBlock, Item item) {
		return createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)));
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
	}
	
	protected void requireSilkTouch(Block base, ItemLike withoutSilk) {
		this.add(base,
				block -> createSilkTouchDrop(base, withoutSilk.asItem()));
	}
	
	protected void requireSilkTouch(Block base, ItemLike withoutSilk, List<Integer> range) {
		this.add(base,
				block -> createOreDrops(base, withoutSilk.asItem(), range));
	}
	
}
