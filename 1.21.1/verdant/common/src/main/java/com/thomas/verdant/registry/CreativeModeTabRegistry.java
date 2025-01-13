package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.registration.RegistrationProvider;
import com.thomas.verdant.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class CreativeModeTabRegistry {

    public static final RegistrationProvider<CreativeModeTab> CREATIVE_MODE_TABS = RegistrationProvider.get(
            Registries.CREATIVE_MODE_TAB,
            Constants.MOD_ID
    );
    public static final RegistryObject<CreativeModeTab, CreativeModeTab> ITEMS_TAB = CREATIVE_MODE_TABS.register(
            Constants.MOD_ID + "_items_tab",
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .icon(() -> new ItemStack(ItemRegistry.THORN.get()))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ItemRegistry.HEART_OF_THE_FOREST.get());
                        output.accept(ItemRegistry.THORN.get());
                        output.accept(ItemRegistry.COFFEE_BERRIES.get());
                        output.accept(ItemRegistry.ROASTED_COFFEE.get());
                        output.accept(ItemRegistry.ROPE_COIL.get());
                        output.accept(ItemRegistry.ROTTEN_COMPOST.get());
                    })
                    .title(Component.translatable("creativetab." + Constants.MOD_ID + ".items"))
                    .build()
    );
    public static final RegistryObject<CreativeModeTab, CreativeModeTab> BLOCKS_TAB = CREATIVE_MODE_TABS.register(
            Constants.MOD_ID + "_blocks_tab",
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .icon(() -> new ItemStack(BlockRegistry.VERDANT_ROOTED_DIRT.get()))
                    .displayItems((itemDisplayParameters, output) -> {
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
                        output.accept(BlockRegistry.WILTED_STRANGLER_LEAVES.get());
                        output.accept(BlockRegistry.STRANGLER_LEAVES.get());
                        output.accept(BlockRegistry.POISON_STRANGLER_LEAVES.get());
                        output.accept(BlockRegistry.THORNY_STRANGLER_LEAVES.get());
                        output.accept(BlockRegistry.LEAFY_STRANGLER_VINE.get());
                        output.accept(BlockRegistry.STRANGLER_VINE.get());
                        output.accept(BlockRegistry.ROTTEN_WOOD.get());
                        output.accept(BlockRegistry.POISON_IVY.get());
                        output.accept(BlockRegistry.STRANGLER_TENDRIL.get());
                        output.accept(BlockRegistry.FRAME_BLOCK.get());
                        output.accept(BlockRegistry.CHARRED_FRAME_BLOCK.get());
                        output.accept(BlockRegistry.ROPE.get());
                        output.accept(BlockRegistry.FISH_TRAP_BLOCK.get());
                        output.accept(BlockRegistry.WOODEN_SPIKES.get());
                        output.accept(BlockRegistry.IRON_SPIKES.get());
                        output.accept(BlockRegistry.WOODEN_TRAP.get());
                        output.accept(BlockRegistry.IRON_TRAP.get());
                        output.accept(BlockRegistry.STINKING_BLOSSOM.get());
                        output.accept(BlockRegistry.BUSH.get());
                        output.accept(BlockRegistry.THORN_BUSH.get());
                        output.accept(BlockRegistry.SNAPLEAF.get());
                        output.accept(BlockRegistry.WILD_COFFEE.get());
                        output.accept(BlockRegistry.BLEEDING_HEART.get());
                        output.accept(BlockRegistry.TIGER_LILY.get());
                        output.accept(BlockRegistry.DROWNED_HEMLOCK.get());
                        output.accept(BlockRegistry.VERDANT_CONDUIT.get());
                    })
                    .title(Component.translatable("creativetab." + Constants.MOD_ID + ".blocks"))
                    .build()
    );

    public static void init() {
    }

}
