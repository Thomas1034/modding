package com.startraveler.verdant;

import com.startraveler.rootbound.RootboundClient;
import com.startraveler.rootbound.blocktransformer.BlockTransformer;
import com.startraveler.rootbound.featureset.FeatureSet;
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
import com.startraveler.verdant.data.*;
import com.startraveler.verdant.registry.*;
import com.startraveler.verdant.util.baitdata.BaitData;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.Mannequin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class VerdantClient {

    public VerdantClient(IEventBus modBus) {
        modBus.addListener(VerdantClient::gatherData);
        modBus.addListener(
                GatherDataEvent.Client.class,
                (event) -> RootboundClient.gatherData(event, WoodSets.WOOD_SETS)
        );
        modBus.addListener(VerdantClient::onClientSetup);
        modBus.addListener(VerdantClient::registerScreens);
        modBus.addListener(VerdantClient::registerBlockEntityRenderers);
        modBus.addListener(VerdantClient::registerLayerDefinitions);
        modBus.addListener(VerdantClient::registerSpecialModels);
        modBus.addListener(VerdantClient::registerRangeProperties);
        modBus.addListener(VerdantClient::registerSelectProperties);
        modBus.addListener(VerdantClient::registerConditionalProperties);
        modBus.addListener(VerdantClient::registerTints);
        modBus.addListener(VerdantClient::addRenderLayers);
        RootboundClient.initializeWoodSets(modBus, WoodSets.WOOD_SETS);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void addRenderLayers(final EntityRenderersEvent.AddLayers event) {
        EntityModelSet modelSet = event.getEntityModels();
        Set<EntityRenderer<? extends LivingEntity, ? extends LivingEntityRenderState>> renderers = Stream.concat(
                event.getEntityTypes()
                        .stream()
                        .map(entityType -> (EntityRenderer<? extends LivingEntity, ? extends LivingEntityRenderState>) event.getRenderer(
                                entityType)), Stream.of(PlayerModelType.values()).flatMap(type -> Stream.of(
                        (EntityRenderer<? extends Player, ? extends AvatarRenderState>) event.getPlayerRenderer(type),
                        (EntityRenderer<? extends Mannequin, ? extends AvatarRenderState>) event.getMannequinRenderer(
                                type)
                ))
        ).collect(Collectors.toSet());
        for (EntityRenderer<?, ?> renderer : renderers) {
            if (renderer instanceof LivingEntityRenderer<?, ?, ?> livingEntityRenderer) {
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
                                event.getContext().getEquipmentRenderer()
                        ));
                    }
                }
            }
        }
    }

    public static void registerTints(final RegisterColorHandlersEvent.Block event) {
        event.register(
                (blockState, blockAndTintGetter, blockPos, i) -> blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageFoliageColor(
                        blockAndTintGetter,
                        blockPos
                ) : FoliageColor.FOLIAGE_DEFAULT, BlockRegistry.MANGO_LEAVES.get()
        );
        event.register(
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

        event.register(
                (blockState, blockAndTintGetter, blockPos, i) -> i == 0 && !blockState.getValueOrElse(
                        BlockStateProperties.SNOWY,
                        false
                ) ? (blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageGrassColor(
                        blockAndTintGetter,
                        blockPos
                ) : GrassColor.getDefaultColor()) : -1,
                BlockRegistry.VERDANT_GRASS_MUD.get(),
                BlockRegistry.VERDANT_GRASS_CLAY.get(),
                BlockRegistry.VERDANT_GRASS_DIRT.get(),
                BlockRegistry.VERDANT_GRASS_GRUS.get()
        );
        event.register(
                (blockState, blockAndTintGetter, blockPos, i) -> i == 0 ? (blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageGrassColor(
                        blockAndTintGetter,
                        blockState.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER ? blockPos.below() : blockPos
                ) : GrassColor.getDefaultColor()) : -1,
                BlockRegistry.TALL_BUSH.get(),
                BlockRegistry.TALL_THORN_BUSH.get()
        );

        event.register(
                (blockState, blockAndTintGetter, blockPos, i) -> i == 0 ? ((blockAndTintGetter != null && blockPos != null ? BiomeColors.getAverageGrassColor(
                        blockAndTintGetter,
                        blockPos
                ) : GrassColor.getDefaultColor())) : -1,
                BlockRegistry.BUSH.get(),
                BlockRegistry.POTTED_BUSH.get(),
                BlockRegistry.THORN_BUSH.get(),
                BlockRegistry.POTTED_THORN_BUSH.get(),
                BlockRegistry.SNAPLEAF.get()
        );
    }

    public static void registerLayerDefinitions(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(
                VerdantModelLayers.SKULL_SPIDER,
                () -> SkullSpiderModel.createBodyLayer().apply(MeshTransformer.scaling(0.7F))
        );
        ArmorModelSet<LayerDefinition> armorSpikesModelSet = HumanoidSpikesModel.createArmorMeshSet(
                        LayerDefinitions.INNER_ARMOR_DEFORMATION,
                        LayerDefinitions.OUTER_ARMOR_DEFORMATION
                )
                .map(meshDefinition -> LayerDefinition.create(meshDefinition, 64, 32));
        ArmorModelSet<LayerDefinition> babyArmorSpikesModelSet = armorSpikesModelSet.map(meshDefinition -> meshDefinition.apply(
                HumanoidModel.BABY_TRANSFORMER));
        VerdantModelLayers.putArmorLayersFrom(
                VerdantModelLayers.ARMOR_SPIKES,
                armorSpikesModelSet,
                event::registerLayerDefinition
        );
        VerdantModelLayers.putArmorLayersFrom(
                VerdantModelLayers.BABY_ARMOR_SPIKES,
                babyArmorSpikesModelSet,
                event::registerLayerDefinition
        );
    }

    public static void gatherData(final GatherDataEvent.Client event) {
        try {
            // Store some frequently-used fields for later use.
            DataGenerator generator = event.getGenerator();
            PackOutput packOutput = generator.getPackOutput();
            CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

            // Loot tables.
            generator.addProvider(
                    true, new LootTableProvider(
                            packOutput,
                            Collections.emptySet(),
                            List.of(new LootTableProvider.SubProviderEntry(
                                    VerdantBlockLootTableProvider::new,
                                    LootContextParamSets.BLOCK
                            )),
                            lookupProvider
                    ) {
                        @Override
                        protected void validate(@NotNull WritableRegistry<LootTable> writableregistry, @NotNull ValidationContext context, ProblemReporter.@NotNull Collector collector) {
                            // Do not validate at all, per what people online said.
                        }
                    }
            );

            // Generate data for the recipes
            generator.addProvider(true, new VerdantRecipeProvider.Runner(packOutput, lookupProvider));

            // Generate data for the tags
            BlockTagsProvider blockTagsProvider = new VerdantBlockTagProvider(
                    packOutput,
                    lookupProvider,
                    WoodSets.WOOD_SETS
            );
            generator.addProvider(true, blockTagsProvider);
            MobEffectTagProvider mobEffectTagsProvider = new VerdantMobEffectTagProvider(packOutput, lookupProvider);
            generator.addProvider(true, mobEffectTagsProvider);
            EntityTypeTagsProvider entityTypeTagsProvider = new VerdantEntityTypeTagProvider(
                    packOutput,
                    lookupProvider
            );
            DamageTypeTagsProvider damageTypeTagsProvider = new VerdantDamageSourceTagProvider(
                    packOutput,
                    lookupProvider
            );
            generator.addProvider(true, damageTypeTagsProvider);
            generator.addProvider(true, entityTypeTagsProvider);
            generator.addProvider(true, new VerdantItemTagProvider(packOutput, lookupProvider, WoodSets.WOOD_SETS));

            // Generate block and item models.
            generator.addProvider(true, new VerdantModelProvider(packOutput));

            // Generate dynamic registries
            generator.addProvider(
                    true, new DatapackBuiltinEntriesProvider(
                            packOutput,
                            lookupProvider,
                            new RegistrySetBuilder().add(Registries.DAMAGE_TYPE, VerdantDamageSourceProvider::register)
                                    .add(BaitData.KEY, BaitDataProvider::register)
                                    .add(BlockTransformer.KEY, VerdantBlockTransformerProvider::register)
                                    .add(FeatureSet.KEY, VerdantFeatureSetProvider::register),
                            Set.of(Constants.MOD_ID, "minecraft", com.startraveler.rootbound.Constants.MOD_ID)
                    )
            );

            // Generate lang file
            generator.addProvider(true, new VerdantEnglishUSLanguageProvider(packOutput));

            // Generate advancements
            generator.addProvider(
                    true, new AdvancementProvider(
                            packOutput, lookupProvider,
                            // Add generators here
                            List.of(VerdantAdvancementProvider::generate)
                    )
            );

            // Generate data maps for furnace fuel, composters, and such; only used on the NeoForge side.
            generator.addProvider(true, new VerdantDataMapProvider(packOutput, lookupProvider));

        } catch (RuntimeException e) {
            Constants.LOG.error("Failed to generate data.", e);
        }
    }


    @SuppressWarnings("deprecation")
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {

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

            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.MANGO_SAPLING.get(), ChunkSectionLayer.CUTOUT);
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.POTTED_MANGO_SAPLING.get(), ChunkSectionLayer.CUTOUT);
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.TALL_BUSH.get(), ChunkSectionLayer.CUTOUT);
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.TALL_THORN_BUSH.get(), ChunkSectionLayer.CUTOUT);
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.SAP_FIRE.get(), ChunkSectionLayer.TRANSLUCENT);
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.SAP_LANTERN.get(), ChunkSectionLayer.CUTOUT);

        });
    }


    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(MenuRegistry.FISH_TRAP_MENU.get(), FishTrapScreen::new);
    }


    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                // The block entity type to register the renderer for.
                BlockEntityTypeRegistry.VERDANT_CONDUIT_BLOCK_ENTITY.get(),
                // A function of BlockEntityRendererProvider.Context to BlockEntityRenderer.
                VerdantConduitRenderer::new
        );
        event.registerBlockEntityRenderer(
                // The block entity type to register the renderer for.
                BlockEntityTypeRegistry.OVERGROWN_SPAWNER.get(),
                // A function of BlockEntityRendererProvider.Context to BlockEntityRenderer.
                OvergrownSpawnerRenderer::new
        );
    }

    public static void registerSpecialModels(RegisterSpecialModelRendererEvent event) {
        event.register(VerdantConduitSpecialRenderer.Unbaked.LOCATION, VerdantConduitSpecialRenderer.Unbaked.MAP_CODEC);
    }

    public static void registerSelectProperties(RegisterSelectItemModelPropertyEvent event) {
        event.register(
                // The name to reference as the type
                Constants.id("rope/hanging_block"),
                // The property type
                RopeHangingBlockProperty.TYPE
        );
    }

    public static void registerRangeProperties(RegisterRangeSelectItemModelPropertyEvent event) {
        event.register(
                // The name to reference as the type
                Constants.id("rope/rope_length"),
                // The map codec
                RopeLengthProperty.MAP_CODEC
        );
        event.register(
                // The name to reference as the type
                Constants.id("rope/glow_level"),
                // The map codec
                RopeGlowProperty.MAP_CODEC
        );
    }

    public static void registerConditionalProperties(RegisterConditionalItemModelPropertyEvent event) {
        event.register(
                // The name to reference as the type
                Constants.id("rope/has_hook"),
                // The map codec
                RopeHookProperty.MAP_CODEC
        );
    }
}