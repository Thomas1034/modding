package com.thomas.verdant;

import com.thomas.verdant.registry.BlockRegistry;
import com.thomas.verdant.registry.properties.WoodSets;
import com.thomas.verdant.woodset.WoodSet;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;
import java.util.function.Supplier;

public class VerdantClient implements ClientModInitializer {

    // protected static final ModelLayerLocation STRANGLER_BOAT_LAYER = ModelLayers.register(WoodSets.STRANGLER.getName() + "_boat");

    // Handles client-only code.
    @Override
    public void onInitializeClient() {

        // Mark some blocks as cutout.
        markCutout(BlockRegistry.DIRT_COAL_ORE, BlockRegistry.DIRT_COPPER_ORE, BlockRegistry.DIRT_DIAMOND_ORE,
                BlockRegistry.DIRT_EMERALD_ORE, BlockRegistry.DIRT_GOLD_ORE, BlockRegistry.DIRT_IRON_ORE,
                BlockRegistry.DIRT_LAPIS_ORE, BlockRegistry.DIRT_REDSTONE_ORE, BlockRegistry.VERDANT_ROOTED_DIRT,
                BlockRegistry.VERDANT_GRASS_DIRT, BlockRegistry.VERDANT_ROOTED_MUD, BlockRegistry.VERDANT_GRASS_MUD,
                BlockRegistry.VERDANT_ROOTED_CLAY, BlockRegistry.VERDANT_GRASS_CLAY, BlockRegistry.STRANGLER_VINE);

        markCutout(WoodSets.STRANGLER.getDoor(), WoodSets.STRANGLER.getTrapdoor());

        registerBoatRenderers(WoodSets.STRANGLER);
    }

    protected void registerBoatRenderers(WoodSet woodSet) {
        ModelLayerLocation boat = ModelLayers.register("boat/" + woodSet.getName());
        ModelLayerLocation chestBoat = ModelLayers.register("chest_boat/" + woodSet.getName());
        EntityModelLayerRegistry.registerModelLayer(boat, BoatModel::createBoatModel);
        EntityModelLayerRegistry.registerModelLayer(chestBoat, BoatModel::createChestBoatModel);
        EntityRendererRegistry.register(woodSet.getBoat().get(), (context) -> new BoatRenderer(context, boat));
        EntityRendererRegistry.register(woodSet.getChestBoat().get(), (context) -> new BoatRenderer(context, chestBoat));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void markCutout(Supplier... blocks) {
        Arrays.stream(blocks).forEach(block -> BlockRenderLayerMapImpl.INSTANCE.putBlock(((Supplier<Block>) block).get(), RenderType.cutout()));
    }

}
