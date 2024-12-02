package com.thomas.verdant;

import com.thomas.verdant.client.renderer.WoodSetHangingSignRenderer;
import com.thomas.verdant.client.renderer.WoodSetSignRenderer;
import com.thomas.verdant.registry.BlockRegistry;
import com.thomas.verdant.registry.properties.WoodSets;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
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

        BlockEntityRenderers.register(WoodSets.STRANGLER.getSignBlockEntity().get(), (ctx) -> new WoodSetSignRenderer(ctx, WoodSets.STRANGLER));
        BlockEntityRenderers.register(WoodSets.STRANGLER.getHangingSignBlockEntity().get(), (ctx) -> new WoodSetHangingSignRenderer(ctx, WoodSets.STRANGLER));

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void markCutout(Supplier... blocks) {
        Arrays.stream(blocks).forEach(block ->  BlockRenderLayerMapImpl.INSTANCE.putBlock(((Supplier<Block>) block).get(), RenderType.cutout()));
    }
}
