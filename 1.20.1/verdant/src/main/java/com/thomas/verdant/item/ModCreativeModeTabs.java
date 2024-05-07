package com.thomas.verdant.item;

import com.thomas.verdant.Verdant;

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
						// pOutput.accept(ModItems.ZIRCON.get());

					}).build());
	public static final RegistryObject<CreativeModeTab> VERDANT_BLOCKS = CREATIVE_MODE_TABS.register("verdant_blocks",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(Blocks.DIRT))
					.title(Component.translatable("creativetab.verdant_blocks"))
					.displayItems((pParameters, pOutput) -> {
						// pOutput.accept(ModBlocks.ZIRCON_BLOCK.get());
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