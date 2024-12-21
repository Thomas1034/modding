package com.thomas.verdant;

import com.thomas.verdant.client.item.VerdantItemProperties;
import com.thomas.verdant.client.item.properties.RopeHasHookPropertyFunction;
import com.thomas.verdant.client.item.properties.RopeLengthPropertyFunction;
import com.thomas.verdant.client.screen.FishTrapScreen;
import com.thomas.verdant.registry.*;
import com.thomas.verdant.woodset.WoodSet;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;
import java.util.function.Supplier;

public class VerdantClient implements ClientModInitializer {

    // protected static final ModelLayerLocation STRANGLER_BOAT_LAYER = ModelLayers.register(WoodSets.STRANGLER.getName() + "_boat");

    // Handles client-only code.
    @Override
    public void onInitializeClient() {

        // Mark some blocks as cutout.
        markCutout(
                BlockRegistry.DIRT_COAL_ORE,
                BlockRegistry.DIRT_COPPER_ORE,
                BlockRegistry.DIRT_DIAMOND_ORE,
                BlockRegistry.DIRT_EMERALD_ORE,
                BlockRegistry.DIRT_GOLD_ORE,
                BlockRegistry.DIRT_IRON_ORE,
                BlockRegistry.DIRT_LAPIS_ORE,
                BlockRegistry.DIRT_REDSTONE_ORE,
                BlockRegistry.VERDANT_ROOTED_DIRT,
                BlockRegistry.VERDANT_GRASS_DIRT,
                BlockRegistry.VERDANT_ROOTED_MUD,
                BlockRegistry.VERDANT_GRASS_MUD,
                BlockRegistry.VERDANT_ROOTED_CLAY,
                BlockRegistry.VERDANT_GRASS_CLAY,
                BlockRegistry.STRANGLER_VINE,
                BlockRegistry.STRANGLER_LEAVES,
                BlockRegistry.WILTED_STRANGLER_LEAVES,
                BlockRegistry.THORNY_STRANGLER_LEAVES,
                BlockRegistry.POISON_STRANGLER_LEAVES,
                BlockRegistry.LEAFY_STRANGLER_VINE,
                BlockRegistry.ROTTEN_WOOD,
                BlockRegistry.POISON_IVY,
                BlockRegistry.POISON_IVY_PLANT,
                BlockRegistry.STRANGLER_TENDRIL,
                BlockRegistry.STRANGLER_TENDRIL_PLANT,
                BlockRegistry.FISH_TRAP_BLOCK,
                BlockRegistry.ROPE,
                BlockRegistry.ROPE_HOOK
        );

        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            markCutout(woodSet.getDoor(), woodSet.getTrapdoor());
            registerBoatRenderers(woodSet);
        }

        MenuScreens.register(MenuRegistry.FISH_TRAP_MENU.get(), FishTrapScreen::new);

        EntityRendererRegistry.register(EntityTypeRegistry.THROWN_ROPE.get(), ThrownItemRenderer::new);

        registerItemProperties();
    }

    protected void registerItemProperties() {
        ItemProperties.register(
                ItemRegistry.ROPE_COIL.get(),
                VerdantItemProperties.ROPE_LENGTH,
                new RopeLengthPropertyFunction()
        );
        ItemProperties.register(
                ItemRegistry.ROPE_COIL.get(),
                VerdantItemProperties.HAS_HOOK,
                new RopeHasHookPropertyFunction()
        );
    }

    protected void registerBoatRenderers(WoodSet woodSet) {
        ModelLayerLocation boat = ModelLayers.register("boat/" + woodSet.getName());
        ModelLayerLocation chestBoat = ModelLayers.register("chest_boat/" + woodSet.getName());
        EntityModelLayerRegistry.registerModelLayer(boat, BoatModel::createBoatModel);
        EntityModelLayerRegistry.registerModelLayer(chestBoat, BoatModel::createChestBoatModel);
        EntityRendererRegistry.register(woodSet.getBoat().get(), (context) -> new BoatRenderer(context, boat));
        EntityRendererRegistry.register(
                woodSet.getChestBoat().get(),
                (context) -> new BoatRenderer(context, chestBoat)
        );
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void markCutout(Supplier... blocks) {
        Arrays.stream(blocks)
                .forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(
                        ((Supplier<Block>) block).get(),
                        RenderType.cutout()
                ));
    }

}
