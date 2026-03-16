package com.startraveler.verdant;

import com.startraveler.rootbound.RootboundClient;
import com.startraveler.verdant.client.VerdantModelLayers;
import com.startraveler.verdant.client.item.RopeGlowProperty;
import com.startraveler.verdant.client.item.RopeHangingBlockProperty;
import com.startraveler.verdant.client.item.RopeHookProperty;
import com.startraveler.verdant.client.item.RopeLengthProperty;
import com.startraveler.verdant.client.layer.HumanoidSpikesLayer;
import com.startraveler.verdant.client.model.HumanoidSpikesModel;
import com.startraveler.verdant.client.model.PoseCopyingHumanoidModel;
import com.startraveler.verdant.client.model.SkullSpiderModel;
import com.startraveler.verdant.client.renderer.*;
import com.startraveler.verdant.client.screen.FishTrapScreen;
import com.startraveler.verdant.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperties;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import java.util.Arrays;
import java.util.function.Supplier;

public class VerdantClient implements ClientModInitializer {

    // Handles client-only code.
    @Override
    public void onInitializeClient() {


        markCutoutMipped();
        // Mark some blocks as cutout.
        markCutout(
                BlockRegistry.GRUS_COAL_ORE,
                BlockRegistry.GRUS_COPPER_ORE,
                BlockRegistry.GRUS_DIAMOND_ORE,
                BlockRegistry.GRUS_EMERALD_ORE,
                BlockRegistry.GRUS_GOLD_ORE,
                BlockRegistry.GRUS_IRON_ORE,
                BlockRegistry.GRUS_LAPIS_ORE,
                BlockRegistry.GRUS_REDSTONE_ORE,
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
                BlockRegistry.TWISTED_ROPE,
                BlockRegistry.TWISTED_ROPE_HOOK,
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
                BlockRegistry.COPPER_SPIKES,
                BlockRegistry.IRON_SPIKES,
                BlockRegistry.GOLDEN_SPIKES,
                BlockRegistry.WOODEN_TRAP,
                BlockRegistry.COPPER_TRAP,
                BlockRegistry.IRON_TRAP,
                BlockRegistry.GOLDEN_TRAP,
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
                BlockRegistry.BLASTING_BLOSSOM,
                BlockRegistry.BLASTING_BUNCH,
                BlockRegistry.BLUEWEED,
                BlockRegistry.POTTED_BLUEWEED,
                BlockRegistry.VERDANT_CONDUIT,
                BlockRegistry.MANGO_SAPLING,
                BlockRegistry.POTTED_MANGO_SAPLING,
                BlockRegistry.OVERGROWN_SPAWNER,
                BlockRegistry.SAP_LANTERN
        );
        markTranslucent(BlockRegistry.SAP_BLOCK, BlockRegistry.SAP_FIRE);


        ColorProviderRegistry.BLOCK.register(
                (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageFoliageColor(
                        blockAndTintGetter,
                        blockPos
                ) : FoliageColor.FOLIAGE_DEFAULT, BlockRegistry.MANGO_LEAVES.get()
        );
        ColorProviderRegistry.BLOCK.register(
                (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageFoliageColor(
                        blockAndTintGetter,
                        blockPos
                ) : FoliageColor.FOLIAGE_DEFAULT,
                BlockRegistry.STRANGLER_LEAVES.get(),
                BlockRegistry.WILTED_STRANGLER_LEAVES.get(),
                BlockRegistry.THORNY_STRANGLER_LEAVES.get(),
                BlockRegistry.POISON_STRANGLER_LEAVES.get(),
                BlockRegistry.LEAFY_STRANGLER_VINE.get()
        );

        ColorProviderRegistry.BLOCK.register(
                (blockState, blockAndTintGetter, blockPos, i) -> i == 0 ? (blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageGrassColor(
                        blockAndTintGetter,
                        blockState.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER ? blockPos.below() : blockPos
                ) : GrassColor.getDefaultColor()) : -1,
                BlockRegistry.TALL_BUSH.get(),
                BlockRegistry.TALL_THORN_BUSH.get()
        );

        ColorProviderRegistry.BLOCK.register(
                (blockState, blockAndTintGetter, blockPos, i) -> i == 0 ? (blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageGrassColor(
                        blockAndTintGetter,
                        blockPos
                ) : GrassColor.getDefaultColor()) : -1,
                BlockRegistry.VERDANT_GRASS_MUD.get(),
                BlockRegistry.VERDANT_GRASS_CLAY.get(),
                BlockRegistry.VERDANT_GRASS_DIRT.get(),
                BlockRegistry.VERDANT_GRASS_GRUS.get()
        );

        ColorProviderRegistry.BLOCK.register(
                (blockState, blockAndTintGetter, blockPos, i) -> i == 0 ? (blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageGrassColor(
                        blockAndTintGetter,
                        blockPos
                ) : GrassColor.getDefaultColor()) : 0,
                BlockRegistry.BUSH.get(),
                BlockRegistry.POTTED_BUSH.get(),
                BlockRegistry.THORN_BUSH.get(),
                BlockRegistry.POTTED_THORN_BUSH.get()
        );

        MenuScreens.register(MenuRegistry.FISH_TRAP_MENU.get(), FishTrapScreen::new);

        EntityModelLayerRegistry.registerModelLayer(
                VerdantModelLayers.SKULL_SPIDER,
                () -> SkullSpiderModel.createBodyLayer().apply(MeshTransformer.scaling(0.7F))
        );

        ArmorModelSet<LayerDefinition> armorSpikesModelSet = HumanoidSpikesModel.createArmorMeshSet(
                        LayerDefinitions.INNER_ARMOR_DEFORMATION,
                        LayerDefinitions.OUTER_ARMOR_DEFORMATION
                )
                .map(meshDefinition -> LayerDefinition.create(meshDefinition, 64, 32));

        EntityModelLayerRegistry.registerEquipmentModelLayers(
                VerdantModelLayers.ARMOR_SPIKES,
                () -> armorSpikesModelSet
        );

        // TODO add spikes for armor stands.
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((EntityType<? extends LivingEntity> entityType, LivingEntityRenderer<?, ?, ?> livingEntityRenderer, LivingEntityFeatureRendererRegistrationCallback.RegistrationHelper registrationHelper, EntityRendererProvider.Context context) -> {
            if (livingEntityRenderer.getModel() instanceof HumanoidModel<?> model) {
                livingEntityRenderer.addLayer(new HumanoidSpikesLayer(
                        livingEntityRenderer, ArmorModelSet.bake(
                        VerdantModelLayers.ARMOR_SPIKES,
                        context.getModelSet(),
                        (root) -> new PoseCopyingHumanoidModel<>(root, model)
                ), context.getEquipmentRenderer()
                ));
            }

        });

        EntityRenderers.register(EntityTypeRegistry.THROWN_ROPE.get(), ThrownItemRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.TIMBERMITE.get(), TimbermiteRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.POISON_ARROW.get(), PoisonArrowRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.ROOTED.get(), RootedRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.DART.get(), TippableDartRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.BLOCK_IGNORING_PRIMED_TNT.get(), TntRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.POISONER.get(), PoisonerRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.BRAMBLE.get(), BrambleRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.OOZE.get(), OozeRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.BLOCK_PLACING_PROJECTILE.get(), ThrownItemRenderer::new);
        EntityRenderers.register(EntityTypeRegistry.SKULL_SPIDER.get(), SkullSpiderRenderer::new);

        BlockEntityRenderers.register(
                BlockEntityTypeRegistry.VERDANT_CONDUIT_BLOCK_ENTITY.get(),
                VerdantConduitRenderer::new
        );
        BlockEntityRenderers.register(BlockEntityTypeRegistry.OVERGROWN_SPAWNER.get(), OvergrownSpawnerRenderer::new);

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
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "rope/rope_length"),
                // The map codec
                RopeLengthProperty.MAP_CODEC
        );
        RangeSelectItemModelProperties.ID_MAPPER.put(
                // The name to reference as the type
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "rope/glow_level"),
                // The map codec
                RopeGlowProperty.MAP_CODEC
        );
        ConditionalItemModelProperties.ID_MAPPER.put(
                // The name to reference as the type
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "rope/has_hook"),
                // The map codec
                RopeHookProperty.MAP_CODEC
        );
        SelectItemModelProperties.ID_MAPPER.put(
                // The name to reference as the type
                Identifier.fromNamespaceAndPath(Constants.MOD_ID, "rope/hanging_block"),
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
                .forEach(block -> BlockRenderLayerMap.putBlock(
                        ((Supplier<Block>) block).get(),
                        ChunkSectionLayer.CUTOUT
                ));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void markTranslucent(Supplier... blocks) {
        Arrays.stream(blocks)
                .forEach(block -> BlockRenderLayerMap.putBlock(
                        ((Supplier<Block>) block).get(),
                        ChunkSectionLayer.TRANSLUCENT
                ));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void markCutoutMipped(Supplier... blocks) {
        Arrays.stream(blocks)
                .forEach(block -> BlockRenderLayerMap.putBlock(
                        ((Supplier<Block>) block).get(),
                        ChunkSectionLayer.CUTOUT
                ));
    }

}
