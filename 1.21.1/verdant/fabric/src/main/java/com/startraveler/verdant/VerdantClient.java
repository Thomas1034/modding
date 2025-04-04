package com.startraveler.verdant;

import com.startraveler.rootbound.RootboundClient;
import com.startraveler.verdant.client.item.RopeGlowProperty;
import com.startraveler.verdant.client.item.RopeHangingBlockProperty;
import com.startraveler.verdant.client.item.RopeHookProperty;
import com.startraveler.verdant.client.item.RopeLengthProperty;
import com.startraveler.verdant.client.renderer.*;
import com.startraveler.verdant.client.screen.FishTrapScreen;
import com.startraveler.verdant.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.SpecialBlockRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.TntRenderer;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperties;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;
import java.util.function.Supplier;

public class VerdantClient implements ClientModInitializer {

    // protected static final ModelLayerLocation STRANGLER_BOAT_LAYER = ModelLayers.register(WoodSets.STRANGLER.getName() + "_boat");

    // Handles client-only code.
    @Override
    public void onInitializeClient() {
        markCutoutMipped();
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
                BlockRegistry.LEAFY_STRANGLER_VINE,
                BlockRegistry.ROTTEN_WOOD,
                BlockRegistry.POISON_IVY,
                BlockRegistry.POISON_IVY_PLANT,
                BlockRegistry.STRANGLER_TENDRIL,
                BlockRegistry.STRANGLER_TENDRIL_PLANT,
                BlockRegistry.FISH_TRAP,
                BlockRegistry.ROPE,
                BlockRegistry.ROPE_HOOK,
                BlockRegistry.THORN_BUSH,
                BlockRegistry.BUSH,
                BlockRegistry.POTTED_THORN_BUSH,
                BlockRegistry.POTTED_BUSH,
                BlockRegistry.TALL_THORN_BUSH,
                BlockRegistry.TALL_BUSH,
                BlockRegistry.STINKING_BLOSSOM,
                BlockRegistry.WILD_COFFEE,
                BlockRegistry.POTTED_WILD_COFFEE,
                BlockRegistry.COFFEE_CROP,
                BlockRegistry.POTTED_COFFEE_CROP,
                BlockRegistry.BLEEDING_HEART,
                BlockRegistry.POTTED_BLEEDING_HEART,
                BlockRegistry.TIGER_LILY,
                BlockRegistry.POTTED_TIGER_LILY,
                BlockRegistry.DROWNED_HEMLOCK,
                BlockRegistry.DROWNED_HEMLOCK_PLANT,
                BlockRegistry.CHARRED_FRAME_BLOCK,
                BlockRegistry.FRAME_BLOCK,
                BlockRegistry.WOODEN_SPIKES,
                BlockRegistry.IRON_SPIKES,
                BlockRegistry.WOODEN_TRAP,
                BlockRegistry.IRON_TRAP,
                BlockRegistry.SNAPLEAF,
                BlockRegistry.CASSAVA_CROP,
                BlockRegistry.BITTER_CASSAVA_CROP,
                BlockRegistry.WILD_CASSAVA,
                BlockRegistry.POTTED_WILD_CASSAVA,
                BlockRegistry.CASSAVA_ROOTED_DIRT,
                BlockRegistry.BITTER_CASSAVA_ROOTED_DIRT,
                BlockRegistry.WILD_UBE,
                BlockRegistry.POTTED_WILD_UBE,
                BlockRegistry.UBE_CROP,
                BlockRegistry.DEAD_GRASS,
                BlockRegistry.RUE,
                BlockRegistry.POTTED_RUE,
                BlockRegistry.SMALL_ALOE,
                BlockRegistry.LARGE_ALOE,
                BlockRegistry.HUGE_ALOE,
                BlockRegistry.BLASTING_BLOSSOM,
                BlockRegistry.BLASTING_BUNCH,
                BlockRegistry.BLUEWEED,
                BlockRegistry.POTTED_BLUEWEED,
                BlockRegistry.VERDANT_CONDUIT
        );


        MenuScreens.register(MenuRegistry.FISH_TRAP_MENU.get(), FishTrapScreen::new);

        EntityRendererRegistry.register(EntityTypeRegistry.THROWN_ROPE.get(), ThrownItemRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.TIMBERMITE.get(), TimbermiteRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.POISON_ARROW.get(), PoisonArrowRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.ROOTED.get(), RootedRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.THROWN_SPEAR.get(), ThrownSpearRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.DART.get(), TippableDartRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.BLOCK_IGNORING_PRIMED_TNT.get(), TntRenderer::new);
        EntityRendererRegistry.register(EntityTypeRegistry.POISONER.get(), PoisonerRenderer::new);

        BlockEntityRenderers.register(
                BlockEntityTypeRegistry.VERDANT_CONDUIT_BLOCK_ENTITY.get(),
                VerdantConduitRenderer::new
        );
        SpecialModelRenderers.ID_MAPPER.put(
                VerdantConduitSpecialRenderer.Unbaked.LOCATION,
                VerdantConduitSpecialRenderer.Unbaked.MAP_CODEC
        );
        SpecialBlockRendererRegistry.register(
                BlockRegistry.VERDANT_CONDUIT.get(),
                new VerdantConduitSpecialRenderer.Unbaked()
        );

        RangeSelectItemModelProperties.ID_MAPPER.put(
                // The name to reference as the type
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "rope/rope_length"),
                // The map codec
                RopeLengthProperty.MAP_CODEC
        );
        RangeSelectItemModelProperties.ID_MAPPER.put(
                // The name to reference as the type
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "rope/glow_level"),
                // The map codec
                RopeGlowProperty.MAP_CODEC
        );
        ConditionalItemModelProperties.ID_MAPPER.put(
                // The name to reference as the type
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "rope/has_hook"),
                // The map codec
                RopeHookProperty.MAP_CODEC
        );
        SelectItemModelProperties.ID_MAPPER.put(
                // The name to reference as the type
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "rope/hanging_block"),
                // The property type
                RopeHangingBlockProperty.TYPE
        );


        registerItemProperties();


        RootboundClient.initializeWoodSets(WoodSets.WOOD_SETS);
    }

    protected void registerItemProperties() {
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    public void markCutout(Supplier... blocks) {
        Arrays.stream(blocks)
                .forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(
                        ((Supplier<Block>) block).get(),
                        RenderType.cutout()
                ));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void markCutoutMipped(Supplier... blocks) {
        Arrays.stream(blocks)
                .forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(
                        ((Supplier<Block>) block).get(),
                        RenderType.cutoutMipped()
                ));
    }

}
