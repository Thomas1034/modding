package com.thomas.verdant.item;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.ModBlocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
			.create(Registries.CREATIVE_MODE_TAB, Verdant.MOD_ID);

	public static final RegistryObject<CreativeModeTab> VERDANT_ITEMS = CREATIVE_MODE_TABS.register("verdant_items",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.WHEAT_SEEDS))
					.title(Component.translatable("creativetab.verdant_items")).displayItems((pParameters, pOutput) -> {

						pOutput.accept(ModItems.VERDANT_BOAT.get());
						pOutput.accept(ModItems.VERDANT_CHEST_BOAT.get());

					}).build());
	public static final RegistryObject<CreativeModeTab> VERDANT_BLOCKS = CREATIVE_MODE_TABS.register("verdant_blocks",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(Blocks.DIRT))
					.title(Component.translatable("creativetab.verdant_blocks"))
					.displayItems((pParameters, pOutput) -> {
						pOutput.accept(ModBlocks.VERDANT_GRASS_BLOCK.get());
						pOutput.accept(ModBlocks.VERDANT_ROOTED_DIRT.get());
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
					}).build());
	public static final RegistryObject<CreativeModeTab> VERDANT_TOOLS = CREATIVE_MODE_TABS.register("verdant_tools",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.WOODEN_PICKAXE))
					.title(Component.translatable("creativetab.verdant_tools")).displayItems((pParameters, pOutput) -> {
						// pOutput.accept(ModItems.COPPER_PICKAXE.get());
					}).build());
	public static final RegistryObject<CreativeModeTab> VERDANT_COMBAT = CREATIVE_MODE_TABS.register("verdant_combat",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.WOODEN_SWORD))
					.title(Component.translatable("creativetab.verdant_combat"))
					.displayItems((pParameters, pOutput) -> {
						// pOutput.accept(ModItems.COPPER_SWORD.get());
					}).build());

	public static void register(IEventBus eventBus) {
		CREATIVE_MODE_TABS.register(eventBus);
	}
}