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
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperties;
import net.minecraft.client.renderer.special.SpecialModelRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.Optional;
import java.util.function.Function;

public class VerdantClient implements ClientModInitializer {

    // Handles client-only code.
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void onInitializeClient() {

        VerdantBlockColorRegistry.init(BlockColorRegistry::register);

        MenuScreens.register(MenuRegistry.FISH_TRAP_MENU.get(), FishTrapScreen::new);

        ModelLayerRegistry.registerModelLayer(
                VerdantModelLayers.SKULL_SPIDER,
                () -> SkullSpiderModel.createBodyLayer().apply(MeshTransformer.scaling(0.7F))
        );

        ArmorModelSet<LayerDefinition> armorSpikesModelSet = HumanoidSpikesModel.createArmorMeshSet(
                        LayerDefinitions.INNER_ARMOR_DEFORMATION,
                        LayerDefinitions.OUTER_ARMOR_DEFORMATION
                )
                .map(meshDefinition -> LayerDefinition.create(meshDefinition, 64, 32));
        ArmorModelSet<LayerDefinition> babyArmorSpikesModelSet = armorSpikesModelSet.map(layerDefinition -> layerDefinition.apply(
                HumanoidModel.BABY_TRANSFORMER));

        ModelLayerRegistry.registerArmorModelLayers(VerdantModelLayers.ARMOR_SPIKES, () -> armorSpikesModelSet);
        ModelLayerRegistry.registerArmorModelLayers(
                VerdantModelLayers.BABY_ARMOR_SPIKES,
                () -> babyArmorSpikesModelSet
        );

        LivingEntityRenderLayerRegistrationCallback.EVENT.register((EntityType<? extends LivingEntity> _, LivingEntityRenderer<?, ?, ?> livingEntityRenderer, LivingEntityRenderLayerRegistrationCallback.RegistrationHelper _, EntityRendererProvider.Context context) -> {
            EntityModelSet modelSet = context.getModelSet();
            if (livingEntityRenderer.getModel() instanceof HumanoidModel<?>) {
                Optional<? extends HumanoidArmorLayer<?, ?, ?>> optionalHumanoidArmorLayer = livingEntityRenderer.layers.stream()
                        .filter(renderLayer -> renderLayer instanceof HumanoidArmorLayer<?, ?, ?>)
                        .map(renderLayer -> (HumanoidArmorLayer<?, ?, ?>) renderLayer)
                        .findFirst();

                if (optionalHumanoidArmorLayer.isPresent()) {
                    ArmorModelSet<? extends HumanoidModel<?>> baseBabyModelSet = optionalHumanoidArmorLayer.get().babyModelSet;
                    ArmorModelSet<? extends HumanoidModel<?>> baseModelSet = optionalHumanoidArmorLayer.get().modelSet;
                    ArmorModelSet<Function<ModelPart, PoseCopyingHumanoidModel>> babyModelSetMapper = baseBabyModelSet.map(
                            humanoidModel -> (Function<ModelPart, PoseCopyingHumanoidModel>) ((ModelPart root) -> new PoseCopyingHumanoidModel(
                                    root,
                                    humanoidModel
                            )));
                    ArmorModelSet<Function<ModelPart, PoseCopyingHumanoidModel>> modelSetMapper = baseModelSet.map(
                            humanoidModel -> (Function<ModelPart, PoseCopyingHumanoidModel>) ((ModelPart root) -> new PoseCopyingHumanoidModel(
                                    root,
                                    humanoidModel
                            )));

                    livingEntityRenderer.addLayer(new HumanoidSpikesLayer(
                            livingEntityRenderer,
                            VerdantModelLayers.bakeIndividual(
                                    VerdantModelLayers.ARMOR_SPIKES,
                                    modelSet,
                                    modelSetMapper
                            ),
                            VerdantModelLayers.bakeIndividual(
                                    VerdantModelLayers.BABY_ARMOR_SPIKES,
                                    modelSet,
                                    babyModelSetMapper
                            ),
                            context.getEquipmentRenderer()
                    ));
                }
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
                Constants.id("verdant_conduit"),
                VerdantConduitSpecialRenderer.Unbaked.MAP_CODEC
        );

        RangeSelectItemModelProperties.ID_MAPPER.put(
                // The name to reference as the type
                Constants.id("rope/rope_length"),
                // The map codec
                RopeLengthProperty.MAP_CODEC
        );
        RangeSelectItemModelProperties.ID_MAPPER.put(
                // The name to reference as the type
                Constants.id("rope/glow_level"),
                // The map codec
                RopeGlowProperty.MAP_CODEC
        );
        ConditionalItemModelProperties.ID_MAPPER.put(
                // The name to reference as the type
                Constants.id("rope/has_hook"),
                // The map codec
                RopeHookProperty.MAP_CODEC
        );
        SelectItemModelProperties.ID_MAPPER.put(
                // The name to reference as the type
                Constants.id("rope/hanging_block"),
                // The property type
                RopeHangingBlockProperty.TYPE
        );


        registerItemProperties();


        RootboundClient.initializeWoodSets(WoodSets.WOOD_SETS);
    }

    protected void registerItemProperties() {
    }

}
