package com.thomas.verdant.item;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.ModBlocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
			.create(Registries.CREATIVE_MODE_TAB, Verdant.MOD_ID);

	public static final RegistryObject<CreativeModeTab> VERDANT_ITEMS = CREATIVE_MODE_TABS.register("verdant_items",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.HEART_OF_THE_FOREST.get()))
					.title(Component.translatable("creativetab.verdant_items")).displayItems((pParameters, pOutput) -> {
						pOutput.accept(ModItems.VERDANT_BOAT.get());
						pOutput.accept(ModItems.VERDANT_CHEST_BOAT.get());
						pOutput.accept(ModItems.VERDANT_HEARTWOOD_BOAT.get());
						pOutput.accept(ModItems.VERDANT_HEARTWOOD_CHEST_BOAT.get());
						pOutput.accept(ModItems.ROASTED_COFFEE.get());
						pOutput.accept(ModItems.STARCH.get());
						pOutput.accept(ModItems.BITTER_STARCH.get());
						pOutput.accept(ModItems.SPARKLING_STARCH.get());
						pOutput.accept(ModItems.CASSAVA.get());
						pOutput.accept(ModItems.BITTER_CASSAVA.get());
						pOutput.accept(ModItems.GOLDEN_CASSAVA.get());
						pOutput.accept(ModItems.CASSAVA_CUTTINGS.get());
						pOutput.accept(ModItems.BITTER_CASSAVA_CUTTINGS.get());
						pOutput.accept(ModItems.THORN.get());
						pOutput.accept(ModItems.HEART_FRAGMENT.get());
						pOutput.accept(ModItems.HEART_OF_THE_FOREST.get());
						pOutput.accept(ModItems.TOXIC_ASH.get());
						pOutput.accept(ModItems.TOXIC_ASH_BUCKET.get());
					}).build());
	public static final RegistryObject<CreativeModeTab> VERDANT_FOODS = CREATIVE_MODE_TABS.register("verdant_foods",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.COFFEE_BERRIES.get()))
					.title(Component.translatable("creativetab.verdant_food")).displayItems((pParameters, pOutput) -> {
						pOutput.accept(ModItems.COFFEE_BERRIES.get());
						pOutput.accept(ModItems.BITTER_BREAD.get());
						pOutput.accept(ModItems.COOKED_CASSAVA.get());
						pOutput.accept(ModItems.COOKED_GOLDEN_CASSAVA.get());

					}).build());
	public static final RegistryObject<CreativeModeTab> VERDANT_BLOCKS = CREATIVE_MODE_TABS.register("verdant_blocks",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.VERDANT_GRASS_BLOCK.get()))
					.title(Component.translatable("creativetab.verdant_blocks"))
					.displayItems((pParameters, pOutput) -> {
						pOutput.accept(ModBlocks.VERDANT_GRASS_BLOCK.get());
						pOutput.accept(ModBlocks.VERDANT_ROOTED_DIRT.get());
						pOutput.accept(ModBlocks.VERDANT_MUD_GRASS_BLOCK.get());
						pOutput.accept(ModBlocks.VERDANT_ROOTED_MUD.get());
						pOutput.accept(ModBlocks.VERDANT_CLAY_GRASS_BLOCK.get());
						pOutput.accept(ModBlocks.VERDANT_ROOTED_CLAY.get());
						pOutput.accept(ModBlocks.ROTTEN_WOOD.get());
						pOutput.accept(ModBlocks.VERDANT_PLANKS.get());
						pOutput.accept(ModBlocks.VERDANT_STAIRS.get());
						pOutput.accept(ModBlocks.VERDANT_SLAB.get());
						pOutput.accept(ModBlocks.VERDANT_FENCE.get());
						pOutput.accept(ModBlocks.VERDANT_FENCE_GATE.get());
						pOutput.accept(ModBlocks.VERDANT_LOG.get());
						pOutput.accept(ModBlocks.VERDANT_WOOD.get());
						pOutput.accept(ModBlocks.STRIPPED_VERDANT_LOG.get());
						pOutput.accept(ModBlocks.STRIPPED_VERDANT_WOOD.get());
						pOutput.accept(ModItems.VERDANT_SIGN.get());
						pOutput.accept(ModItems.VERDANT_HANGING_SIGN.get());
						pOutput.accept(ModBlocks.VERDANT_PRESSURE_PLATE.get());
						pOutput.accept(ModBlocks.VERDANT_BUTTON.get());
						pOutput.accept(ModBlocks.VERDANT_DOOR.get());
						pOutput.accept(ModBlocks.VERDANT_TRAPDOOR.get());
						pOutput.accept(ModBlocks.IMBUED_VERDANT_HEARTWOOD_LOG.get());
						pOutput.accept(ModBlocks.VERDANT_HEARTWOOD_PLANKS.get());
						pOutput.accept(ModBlocks.VERDANT_HEARTWOOD_STAIRS.get());
						pOutput.accept(ModBlocks.VERDANT_HEARTWOOD_SLAB.get());
						pOutput.accept(ModBlocks.VERDANT_HEARTWOOD_FENCE.get());
						pOutput.accept(ModBlocks.VERDANT_HEARTWOOD_FENCE_GATE.get());
						pOutput.accept(ModBlocks.VERDANT_HEARTWOOD_LOG.get());
						pOutput.accept(ModBlocks.VERDANT_HEARTWOOD_WOOD.get());
						pOutput.accept(ModBlocks.STRIPPED_VERDANT_HEARTWOOD_LOG.get());
						pOutput.accept(ModBlocks.STRIPPED_VERDANT_HEARTWOOD_WOOD.get());
						pOutput.accept(ModItems.VERDANT_HEARTWOOD_SIGN.get());
						pOutput.accept(ModItems.VERDANT_HEARTWOOD_HANGING_SIGN.get());
						pOutput.accept(ModBlocks.VERDANT_HEARTWOOD_PRESSURE_PLATE.get());
						pOutput.accept(ModBlocks.VERDANT_HEARTWOOD_BUTTON.get());
						pOutput.accept(ModBlocks.VERDANT_HEARTWOOD_DOOR.get());
						pOutput.accept(ModBlocks.VERDANT_HEARTWOOD_TRAPDOOR.get());
						pOutput.accept(ModBlocks.VERDANT_LEAVES.get());
						pOutput.accept(ModBlocks.THORNY_VERDANT_LEAVES.get());
						pOutput.accept(ModBlocks.POISON_IVY_VERDANT_LEAVES.get());
						pOutput.accept(ModBlocks.VERDANT_VINE.get());
						pOutput.accept(ModBlocks.VERDANT_TENDRIL.get());
						pOutput.accept(ModBlocks.DIRT_COAL_ORE.get());
						pOutput.accept(ModBlocks.DIRT_COPPER_ORE.get());
						pOutput.accept(ModBlocks.DIRT_IRON_ORE.get());
						pOutput.accept(ModBlocks.DIRT_GOLD_ORE.get());
						pOutput.accept(ModBlocks.DIRT_LAPIS_ORE.get());
						pOutput.accept(ModBlocks.DIRT_REDSTONE_ORE.get());
						pOutput.accept(ModBlocks.DIRT_EMERALD_ORE.get());
						pOutput.accept(ModBlocks.DIRT_DIAMOND_ORE.get());
						pOutput.accept(ModBlocks.DENSE_GRAVEL.get());
						pOutput.accept(ModBlocks.VERDANT_CONDUIT.get());
						pOutput.accept(ModBlocks.FRAME_BLOCK.get());
						pOutput.accept(ModBlocks.CHARRED_FRAME_BLOCK.get());
						pOutput.accept(ModBlocks.POISON_IVY_BLOCK.get());
						pOutput.accept(ModBlocks.TOXIC_ASH_BLOCK.get());
						pOutput.accept(ModBlocks.ROPE.get());
						pOutput.accept(ModBlocks.THORN_SPIKES.get());
						pOutput.accept(ModBlocks.IRON_SPIKES.get());
						pOutput.accept(ModBlocks.THORN_TRAP.get());
						pOutput.accept(ModBlocks.IRON_TRAP.get());
						pOutput.accept(ModBlocks.ROPE_LADDER.get());
						pOutput.accept(ModBlocks.POISON_IVY.get());
						pOutput.accept(ModBlocks.WILD_CASSAVA.get());
						pOutput.accept(ModBlocks.BITTER_CASSAVA_ROOTED_DIRT.get());
						pOutput.accept(ModBlocks.CASSAVA_ROOTED_DIRT.get());
						pOutput.accept(ModBlocks.THORN_BUSH.get());
						pOutput.accept(ModBlocks.BUSH.get());
						pOutput.accept(ModBlocks.STINKING_BLOSSOM.get());
						pOutput.accept(ModBlocks.WILD_COFFEE.get());
						pOutput.accept(ModBlocks.BLEEDING_HEART.get());
						pOutput.accept(ModBlocks.WATER_HEMLOCK.get());
					}).build());
	public static final RegistryObject<CreativeModeTab> VERDANT_TOOLS = CREATIVE_MODE_TABS.register("verdant_tools",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.VERDANT_HEARTWOOD_PICKAXE.get()))
					.title(Component.translatable("creativetab.verdant_tools")).displayItems((pParameters, pOutput) -> {
						pOutput.accept(ModItems.VERDANT_HEARTWOOD_PICKAXE.get());
						pOutput.accept(ModItems.VERDANT_HEARTWOOD_AXE.get());
						pOutput.accept(ModItems.VERDANT_HEARTWOOD_SHOVEL.get());
						pOutput.accept(ModItems.VERDANT_HEARTWOOD_HOE.get());
						pOutput.accept(ModItems.SHORT_ROPE_COIL.get());
						pOutput.accept(ModItems.ROPE_COIL.get());
						pOutput.accept(ModItems.TOXIC_SOLUTION_BUCKET.get());
					}).build());
	public static final RegistryObject<CreativeModeTab> VERDANT_COMBAT = CREATIVE_MODE_TABS.register("verdant_combat",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.VERDANT_HEARTWOOD_SWORD.get()))
					.title(Component.translatable("creativetab.verdant_combat"))
					.displayItems((pParameters, pOutput) -> {
						pOutput.accept(ModItems.POISON_ARROW.get());
						pOutput.accept(ModItems.VERDANT_HEARTWOOD_SWORD.get());
						pOutput.accept(ModItems.VERDANT_HEARTWOOD_AXE.get());
						pOutput.accept(ModItems.VERDANT_HEARTWOOD_HELMET.get());
						pOutput.accept(ModItems.VERDANT_HEARTWOOD_CHESTPLATE.get());
						pOutput.accept(ModItems.VERDANT_HEARTWOOD_LEGGINGS.get());
						pOutput.accept(ModItems.VERDANT_HEARTWOOD_BOOTS.get());
						pOutput.accept(ModItems.IMBUED_VERDANT_HEARTWOOD_HELMET.get());
						pOutput.accept(ModItems.IMBUED_VERDANT_HEARTWOOD_CHESTPLATE.get());
						pOutput.accept(ModItems.IMBUED_VERDANT_HEARTWOOD_LEGGINGS.get());
						pOutput.accept(ModItems.IMBUED_VERDANT_HEARTWOOD_BOOTS.get());
					}).build());

	public static void register(IEventBus eventBus) {
		CREATIVE_MODE_TABS.register(eventBus);
	}
}