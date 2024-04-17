package com.thomas.cloudscape.item;

import com.thomas.cloudscape.ZirconMod;
import com.thomas.cloudscape.block.ModBlocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
			.create(Registries.CREATIVE_MODE_TAB, ZirconMod.MOD_ID);

	public static final RegistryObject<CreativeModeTab> ZIRCONMOD_ITEMS = CREATIVE_MODE_TABS.register("cloudscape_items",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ZIRCON.get()))
					.title(Component.translatable("creativetab.cloudscape_items"))
					.displayItems((pParameters, pOutput) -> {
						pOutput.accept(ModItems.ZIRCON.get());
						pOutput.accept(ModItems.ZIRCON_SHARD.get());
						pOutput.accept(ModItems.CITRINE_SHARD.get());
						pOutput.accept(ModItems.CUT_CITRINE.get());
						pOutput.accept(ModItems.HAILSTONE.get());
						pOutput.accept(ModItems.HEART_OF_THE_SKY.get());
						pOutput.accept(ModItems.ZIRCONIUM_INGOT.get());
						pOutput.accept(ModItems.ZIRCONIUM_UPGRADE_SMITHING_TEMPLATE.get());
						pOutput.accept(ModItems.RAW_ZIRCONIUM.get());
						pOutput.accept(ModItems.ECHO_POWDER.get());
						pOutput.accept(ModItems.VIGIL_EYE.get());
						pOutput.accept(ModItems.PINE_CONE.get());
						pOutput.accept(ModItems.FLAMING_PINE_CONE.get());
						pOutput.accept(ModItems.FLAMING_ARROW.get());
						pOutput.accept(ModItems.TOTEM_OF_RETURNING.get());
						// pOutput.accept(ModItems.CLEAR_WEATHER_TOTEM.get());
						pOutput.accept(ModItems.BERRY_PIE.get());
						pOutput.accept(ModItems.BLUEBERRY.get());
						pOutput.accept(ModItems.BLUEBERRY_SEEDS.get());
						pOutput.accept(ModItems.BUBBLEFRUIT.get());
						pOutput.accept(ModItems.PALM_SEEDS.get());
						pOutput.accept(ModItems.NIMBULA_GEL.get());
						pOutput.accept(ModBlocks.WHITE_ORCHID.get());
						pOutput.accept(ModBlocks.ILLUMINATED_TORCHFLOWER.get());
						pOutput.accept(ModItems.MOLE_SPAWN_EGG.get());
						pOutput.accept(ModItems.NIMBULA_SPAWN_EGG.get());
						pOutput.accept(ModItems.TEMPEST_SPAWN_EGG.get());
						pOutput.accept(ModItems.WISP_SPAWN_EGG.get());
						pOutput.accept(ModItems.PALM_BOAT.get());
						pOutput.accept(ModItems.PALM_CHEST_BOAT.get());
					}).build());
	public static final RegistryObject<CreativeModeTab> ZIRCONMOD_BLOCKS = CREATIVE_MODE_TABS.register(
			"cloudscape_blocks",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.ZIRCON_BLOCK.get()))
					.title(Component.translatable("creativetab.cloudscape_blocks"))
					.displayItems((pParameters, pOutput) -> {
						pOutput.accept(ModBlocks.ZIRCON_BLOCK.get());
						pOutput.accept(ModBlocks.ZIRCON_ORE.get());
						pOutput.accept(ModBlocks.DEEPSLATE_ZIRCON_ORE.get());
						pOutput.accept(ModBlocks.ZIRCON_LAMP.get());
						pOutput.accept(ModBlocks.ZIRCONIUM_BLOCK.get());
						pOutput.accept(ModBlocks.RAW_ZIRCONIUM_BLOCK.get());
						pOutput.accept(ModBlocks.CHARCOAL_BLOCK.get());
						pOutput.accept(ModBlocks.SCULK_JAW.get());
						pOutput.accept(ModBlocks.SCULK_ROOTS.get());
						pOutput.accept(ModBlocks.ECHO_BLOCK.get());
						pOutput.accept(ModBlocks.RESONATOR_BLOCK.get());
						pOutput.accept(ModBlocks.CARPENTRY_TABLE.get());
						pOutput.accept(ModBlocks.PETRIFIED_LOG.get());
						pOutput.accept(ModBlocks.PALM_TRUNK.get());
						pOutput.accept(ModBlocks.PALM_FROND.get());
						pOutput.accept(ModBlocks.PALM_FRUIT.get());
						pOutput.accept(ModBlocks.PALM_SAPLING.get());
						pOutput.accept(ModBlocks.PALM_LOG.get());
						pOutput.accept(ModBlocks.PALM_WOOD.get());
						pOutput.accept(ModBlocks.STRIPPED_PALM_LOG.get());
						pOutput.accept(ModBlocks.STRIPPED_PALM_WOOD.get());
						pOutput.accept(ModBlocks.PALM_PLANKS.get());
						pOutput.accept(ModBlocks.PALM_BUTTON.get());
						pOutput.accept(ModBlocks.PALM_TRAPDOOR.get());
						pOutput.accept(ModBlocks.PALM_DOOR.get());
						pOutput.accept(ModBlocks.PALM_SLAB.get());
						pOutput.accept(ModBlocks.PALM_STAIRS.get());
						pOutput.accept(ModBlocks.PALM_FENCE.get());
						pOutput.accept(ModBlocks.PALM_FENCE_GATE.get());
						pOutput.accept(ModBlocks.PALM_PRESSURE_PLATE.get());
						pOutput.accept(ModItems.PALM_SIGN.get());
						pOutput.accept(ModItems.PALM_HANGING_SIGN.get());
						pOutput.accept(ModBlocks.CITRINE_BLOCK.get());
						pOutput.accept(ModBlocks.BUDDING_CITRINE.get());
						pOutput.accept(ModBlocks.CITRINE_CLUSTER.get());
						pOutput.accept(ModBlocks.LARGE_CITRINE_BUD.get());
						pOutput.accept(ModBlocks.MEDIUM_CITRINE_BUD.get());
						pOutput.accept(ModBlocks.SMALL_CITRINE_BUD.get());
						pOutput.accept(ModItems.CITRINE_BRACKET.get());
						pOutput.accept(ModBlocks.CITRINE_LANTERN.get());
						pOutput.accept(ModBlocks.LIGHTNING_BLOCK.get());
						pOutput.accept(ModBlocks.UNSTABLE_LIGHTNING_BLOCK.get());
						pOutput.accept(ModBlocks.MIST.get());
						pOutput.accept(ModBlocks.CLOUD.get());
						pOutput.accept(ModBlocks.DENSE_MIST.get());
						pOutput.accept(ModBlocks.CLOUD_BRICKS.get());
						pOutput.accept(ModBlocks.CLOUD_BRICK_SLAB.get());
						pOutput.accept(ModBlocks.CLOUD_BRICK_STAIRS.get());
						pOutput.accept(ModBlocks.CLOUD_BRICK_WALL.get());
						pOutput.accept(ModBlocks.CLOUD_BRICK_PILLAR.get());
						pOutput.accept(ModBlocks.CHISELED_CLOUD_BRICKS.get());
						pOutput.accept(ModBlocks.THUNDER_CLOUD.get());
						pOutput.accept(ModBlocks.THUNDER_CLOUD_BRICKS.get());
						pOutput.accept(ModBlocks.THUNDER_CLOUD_BRICK_SLAB.get());
						pOutput.accept(ModBlocks.THUNDER_CLOUD_BRICK_STAIRS.get());
						pOutput.accept(ModBlocks.THUNDER_CLOUD_BRICK_WALL.get());
						pOutput.accept(ModBlocks.THUNDER_CLOUD_BRICK_PILLAR.get());
						pOutput.accept(ModBlocks.CHISELED_THUNDER_CLOUD_BRICKS.get());
						pOutput.accept(ModBlocks.CLOUD_CONVERTER.get());
						pOutput.accept(ModBlocks.CLOUD_INVERTER.get());
						pOutput.accept(ModBlocks.CLOUD_DETECTOR.get());
						pOutput.accept(ModBlocks.COPPER_BUTTON.get());
						pOutput.accept(ModBlocks.EXPOSED_COPPER_BUTTON.get());
						pOutput.accept(ModBlocks.WEATHERED_COPPER_BUTTON.get());
						pOutput.accept(ModBlocks.OXIDIZED_COPPER_BUTTON.get());
						pOutput.accept(ModBlocks.WAXED_COPPER_BUTTON.get());
						pOutput.accept(ModBlocks.WAXED_EXPOSED_COPPER_BUTTON.get());
						pOutput.accept(ModBlocks.WAXED_WEATHERED_COPPER_BUTTON.get());
						pOutput.accept(ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get());
						pOutput.accept(ModBlocks.NETHERITE_ANVIL.get());
						pOutput.accept(ModBlocks.COAL_STAIRS.get());
						pOutput.accept(ModBlocks.COAL_SLAB.get());
						pOutput.accept(ModBlocks.CHARCOAL_STAIRS.get());
						pOutput.accept(ModBlocks.CHARCOAL_SLAB.get());
						pOutput.accept(ModBlocks.RAW_COPPER_STAIRS.get());
						pOutput.accept(ModBlocks.RAW_COPPER_SLAB.get());
						pOutput.accept(ModBlocks.RAW_IRON_STAIRS.get());
						pOutput.accept(ModBlocks.RAW_IRON_SLAB.get());
						pOutput.accept(ModBlocks.IRON_STAIRS.get());
						pOutput.accept(ModBlocks.IRON_SLAB.get());
						pOutput.accept(ModBlocks.RAW_GOLD_STAIRS.get());
						pOutput.accept(ModBlocks.RAW_GOLD_SLAB.get());
						pOutput.accept(ModBlocks.GOLD_STAIRS.get());
						pOutput.accept(ModBlocks.GOLD_SLAB.get());
						pOutput.accept(ModBlocks.REDSTONE_STAIRS.get());
						pOutput.accept(ModBlocks.REDSTONE_SLAB.get());
						pOutput.accept(ModBlocks.LAPIS_STAIRS.get());
						pOutput.accept(ModBlocks.LAPIS_SLAB.get());
						pOutput.accept(ModBlocks.EMERALD_STAIRS.get());
						pOutput.accept(ModBlocks.EMERALD_SLAB.get());
						pOutput.accept(ModBlocks.DIAMOND_STAIRS.get());
						pOutput.accept(ModBlocks.DIAMOND_SLAB.get());
						pOutput.accept(ModBlocks.NETHERITE_STAIRS.get());
						pOutput.accept(ModBlocks.NETHERITE_SLAB.get());
						pOutput.accept(ModBlocks.AMETHYST_STAIRS.get());
						pOutput.accept(ModBlocks.AMETHYST_SLAB.get());
						pOutput.accept(ModBlocks.OBSIDIAN_STAIRS.get());
						pOutput.accept(ModBlocks.OBSIDIAN_SLAB.get());
						pOutput.accept(ModBlocks.CRYING_OBSIDIAN_STAIRS.get());
						pOutput.accept(ModBlocks.CRYING_OBSIDIAN_SLAB.get());
						pOutput.accept(ModBlocks.ZIRCON_STAIRS.get());
						pOutput.accept(ModBlocks.ZIRCON_SLAB.get());
						pOutput.accept(ModBlocks.RAW_ZIRCONIUM_STAIRS.get());
						pOutput.accept(ModBlocks.RAW_ZIRCONIUM_SLAB.get());
						pOutput.accept(ModBlocks.ZIRCONIUM_STAIRS.get());
						pOutput.accept(ModBlocks.ZIRCONIUM_SLAB.get());
						pOutput.accept(ModBlocks.CITRINE_STAIRS.get());
						pOutput.accept(ModBlocks.CITRINE_SLAB.get());
					}).build());
	public static final RegistryObject<CreativeModeTab> ZIRCONMOD_TOOLS = CREATIVE_MODE_TABS.register("cloudscape_tools",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.COPPER_PICKAXE.get()))
					.title(Component.translatable("creativetab.cloudscape_tools"))
					.displayItems((pParameters, pOutput) -> {
						pOutput.accept(ModItems.COPPER_PICKAXE.get());
						pOutput.accept(ModItems.COPPER_AXE.get());
						pOutput.accept(ModItems.COPPER_SHOVEL.get());
						pOutput.accept(ModItems.COPPER_HOE.get());
						pOutput.accept(ModItems.ZIRCONIUM_PICKAXE.get());
						pOutput.accept(ModItems.ZIRCONIUM_AXE.get());
						pOutput.accept(ModItems.ZIRCONIUM_SHOVEL.get());
						pOutput.accept(ModItems.ZIRCONIUM_HOE.get());
						pOutput.accept(ModItems.CITRINE_PICKAXE.get());
						pOutput.accept(ModItems.CITRINE_AXE.get());
						pOutput.accept(ModItems.CITRINE_SHOVEL.get());
						pOutput.accept(ModItems.CITRINE_HOE.get());
						pOutput.accept(ModItems.FEATHER_WINGS.get());
						pOutput.accept(ModItems.ANCIENT_FEATHER_WINGS.get());
						pOutput.accept(ModItems.GUST_BOTTLE.get());
						pOutput.accept(ModItems.TEMPEST_BOTTLE.get());
						pOutput.accept(ModItems.WIND_BAG.get());
						pOutput.accept(ModItems.EMPTY_WIND_BAG.get());
					}).build());
	public static final RegistryObject<CreativeModeTab> ZIRCONMOD_COMBAT = CREATIVE_MODE_TABS.register(
			"cloudscape_combat",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.COPPER_SWORD.get()))
					.title(Component.translatable("creativetab.cloudscape_combat"))
					.displayItems((pParameters, pOutput) -> {
						pOutput.accept(ModItems.COPPER_SWORD.get());
						pOutput.accept(ModItems.COPPER_AXE.get());
						pOutput.accept(ModItems.COPPER_HELMET.get());
						pOutput.accept(ModItems.COPPER_CHESTPLATE.get());
						pOutput.accept(ModItems.COPPER_WINGS.get());
						pOutput.accept(ModItems.COPPER_LEGGINGS.get());
						pOutput.accept(ModItems.COPPER_BOOTS.get());
						pOutput.accept(ModItems.ZIRCONIUM_SWORD.get());
						pOutput.accept(ModItems.ZIRCONIUM_AXE.get());
						pOutput.accept(ModItems.ZIRCONIUM_HELMET.get());
						pOutput.accept(ModItems.ZIRCONIUM_CHESTPLATE.get());
						pOutput.accept(ModItems.ZIRCONIUM_WINGS.get());
						pOutput.accept(ModItems.ZIRCONIUM_LEGGINGS.get());
						pOutput.accept(ModItems.ZIRCONIUM_BOOTS.get());
						pOutput.accept(ModItems.CITRINE_SWORD.get());
						pOutput.accept(ModItems.CITRINE_AXE.get());
						pOutput.accept(ModItems.CITRINE_HELMET.get());
						pOutput.accept(ModItems.CITRINE_CHESTPLATE.get());
						pOutput.accept(ModItems.CITRINE_WINGS.get());
						pOutput.accept(ModItems.CITRINE_LEGGINGS.get());
						pOutput.accept(ModItems.CITRINE_BOOTS.get());
						pOutput.accept(ModItems.FEATHER_WINGS.get());
						pOutput.accept(ModItems.LEATHER_WINGS.get());
						pOutput.accept(ModItems.GOLDEN_WINGS.get());
						pOutput.accept(ModItems.CHAINMAIL_WINGS.get());
						pOutput.accept(ModItems.IRON_WINGS.get());
						pOutput.accept(ModItems.DIAMOND_WINGS.get());
						pOutput.accept(ModItems.NETHERITE_WINGS.get());
						pOutput.accept(ModItems.HAILSTONE.get());
						pOutput.accept(ModItems.WOODEN_SPEAR.get());
						pOutput.accept(ModItems.STONE_SPEAR.get());
						pOutput.accept(ModItems.COPPER_SPEAR.get());
						pOutput.accept(ModItems.GOLDEN_SPEAR.get());
						pOutput.accept(ModItems.IRON_SPEAR.get());
						pOutput.accept(ModItems.ZIRCONIUM_SPEAR.get());
						pOutput.accept(ModItems.DIAMOND_SPEAR.get());
						pOutput.accept(ModItems.CITRINE_SPEAR.get());
						pOutput.accept(ModItems.NETHERITE_SPEAR.get());
					}).build());

	public static void register(IEventBus eventBus) {
		CREATIVE_MODE_TABS.register(eventBus);
	}
}