package com.thomas.verdant;


import com.thomas.verdant.client.renderer.WoodSetHangingSignRenderer;
import com.thomas.verdant.client.renderer.WoodSetSignRenderer;
import com.thomas.verdant.registry.properties.WoodSets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod(value = "examplemod", dist = Dist.CLIENT)
public class VerdantClient {
    public VerdantClient(IEventBus modBus) {

    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                // The block entity type to register the renderer for.
                WoodSets.STRANGLER.getSignBlockEntity().get(),
                // A function of BlockEntityRendererProvider.Context to BlockEntityRenderer.
                (ctx) -> new WoodSetSignRenderer(ctx, WoodSets.STRANGLER));
        event.registerBlockEntityRenderer(
                // The block entity type to register the renderer for.
                WoodSets.STRANGLER.getHangingSignBlockEntity().get(),
                // A function of BlockEntityRendererProvider.Context to BlockEntityRenderer.
                (ctx) -> new WoodSetHangingSignRenderer(ctx, WoodSets.STRANGLER));
    }
}