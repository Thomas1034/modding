package com.thomas.verdant;

import com.thomas.verdant.registry.BlockRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;
import java.util.function.Supplier;

public class VerdantClient implements ClientModInitializer {

    // Handles client-only code.
    @Override
    public void onInitializeClient() {

        // Mark some blocks as cutout.
        markCutout(BlockRegistry.DIRT_COAL_ORE, BlockRegistry.DIRT_COPPER_ORE, BlockRegistry.DIRT_DIAMOND_ORE,
                BlockRegistry.DIRT_EMERALD_ORE, BlockRegistry.DIRT_GOLD_ORE, BlockRegistry.DIRT_IRON_ORE,
                BlockRegistry.DIRT_LAPIS_ORE, BlockRegistry.DIRT_REDSTONE_ORE, BlockRegistry.VERDANT_ROOTED_DIRT,
                BlockRegistry.VERDANT_GRASS_DIRT, BlockRegistry.VERDANT_ROOTED_MUD, BlockRegistry.VERDANT_GRASS_MUD,
                BlockRegistry.VERDANT_ROOTED_CLAY, BlockRegistry.VERDANT_GRASS_CLAY, BlockRegistry.STRANGLER_VINE);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void markCutout(Supplier... blocks) {
        Arrays.stream(blocks).forEach(block -> BlockRenderLayerMapImpl.INSTANCE.putBlock(((Supplier<Block>) block).get(), RenderType.cutout()));
    }
}
