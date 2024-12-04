package com.thomas.verdant;

import com.thomas.verdant.registry.properties.WoodSets;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod(value = "verdant", dist = Dist.CLIENT)
public class VerdantClient {

    // Our ModelLayerLocation.
    public static final ModelLayerLocation STRANGLER_SIGN = signLayer("strangler");


    public VerdantClient(IEventBus modBus) {
        modBus.addListener(VerdantClient::registerEntityRenderers);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        Constants.LOG.warn("Registering renderers");
        event.registerBlockEntityRenderer(
                // The block entity type to register the renderer for.
                WoodSets.STRANGLER.getSignBlockEntity().get(),
                // A function of BlockEntityRendererProvider.Context to BlockEntityRenderer.
                SignRenderer::new);
        event.registerBlockEntityRenderer(
                // The block entity type to register the renderer for.
                WoodSets.STRANGLER.getHangingSignBlockEntity().get(),
                // A function of BlockEntityRendererProvider.Context to BlockEntityRenderer.
                HangingSignRenderer::new);
    }

    private static ModelLayerLocation signLayer(String name) {
        return new ModelLayerLocation(
                // Should be the name of the entity this layer belongs to.
                // May be more generic if this layer can be used on multiple entities.
                ResourceLocation.withDefaultNamespace("sign/standing/" + name),
                // The name of the layer itself.
                "main"
        );
    }
}