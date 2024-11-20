package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.registration.RegistrationProvider;
import com.thomas.verdant.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeModeTabRegistry {

        public static void init() {}

        public static final RegistrationProvider<CreativeModeTab> CREATIVE_MODE_TABS = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

        public static final RegistryObject<CreativeModeTab, CreativeModeTab> ITEMS_TAB = CREATIVE_MODE_TABS.register(Constants.MOD_ID + "_items_tab", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                .icon(() -> new ItemStack(ItemRegistry.ROASTED_COFFEE.get()))
                .displayItems(
                        (itemDisplayParameters, output) -> {
                            output.accept(ItemRegistry.ROASTED_COFFEE.get());
                        }).title(Component.translatable("creativetab." + Constants.MOD_ID + ".items"))
                .build());

        public static final RegistryObject<CreativeModeTab, CreativeModeTab> BLOCKS_TAB = CREATIVE_MODE_TABS.register(Constants.MOD_ID + "_blocks_tab", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                .icon(() -> new ItemStack(BlockRegistry.VERDANT_ROOTED_DIRT.get()))
                .displayItems(
                        (itemDisplayParameters, output) -> {
                            output.accept(BlockRegistry.VERDANT_ROOTED_DIRT.get());
                            output.accept(BlockRegistry.VERDANT_GRASS_DIRT.get());
                            output.accept(BlockRegistry.VERDANT_ROOTED_MUD.get());
                            output.accept(BlockRegistry.VERDANT_GRASS_MUD.get());
                            output.accept(BlockRegistry.VERDANT_ROOTED_CLAY.get());
                            output.accept(BlockRegistry.VERDANT_GRASS_CLAY.get());
                            output.accept(BlockRegistry.PACKED_GRAVEL.get());
                            output.accept(BlockRegistry.DIRT_COAL_ORE.get());
                            output.accept(BlockRegistry.DIRT_COPPER_ORE.get());
                            output.accept(BlockRegistry.DIRT_IRON_ORE.get());
                            output.accept(BlockRegistry.DIRT_GOLD_ORE.get());
                            output.accept(BlockRegistry.DIRT_LAPIS_ORE.get());
                            output.accept(BlockRegistry.DIRT_REDSTONE_ORE.get());
                            output.accept(BlockRegistry.DIRT_EMERALD_ORE.get());
                            output.accept(BlockRegistry.DIRT_DIAMOND_ORE.get());
                        }).title(Component.translatable("creativetab." + Constants.MOD_ID + ".blocks"))
                .build());

}
