package com.thomas.quantumcircuit.item;

import java.util.function.Supplier;

import com.thomas.quantumcircuit.QuantumCircuit;
import com.thomas.quantumcircuit.block.ModBlocks;

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
			.create(Registries.CREATIVE_MODE_TAB, QuantumCircuit.MOD_ID);

	public static final RegistryObject<CreativeModeTab> QUANTUMCIRCUIT_ITEMS = CREATIVE_MODE_TABS.register(
			"quantumcircuit_items",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.REDSTONE))
					.title(Component.translatable("creativetab.quantumcircuit_items"))
					.displayItems((pParameters, pOutput) -> {

					}).build());
	public static final RegistryObject<CreativeModeTab> QUANTUMCIRCUIT_BLOCKS = CREATIVE_MODE_TABS.register(
			"quantumcircuit_blocks",
			() -> CreativeModeTab.builder().icon(() -> new ItemStack(Blocks.REDSTONE_BLOCK))
					.title(Component.translatable("creativetab.quantumcircuit_blocks"))
					.displayItems((pParameters, pOutput) -> {
						pOutput.accept(ModBlocks.ZIRCON_BLOCK.get());

					}).build());

	public static RegistryObject<CreativeModeTab> register(String name, Supplier<? extends CreativeModeTab> tab) {
		return CREATIVE_MODE_TABS.register(name, tab);
	}

	public static void register(IEventBus eventBus) {
		CREATIVE_MODE_TABS.register(eventBus);
	}
}