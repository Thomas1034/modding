package com.startraveler.verdant;

import com.startraveler.verdant.client.item.RopeGlowProperty;
import com.startraveler.verdant.client.item.RopeHangingBlockProperty;
import com.startraveler.verdant.client.item.RopeHookProperty;
import com.startraveler.verdant.client.item.RopeLengthProperty;
import com.startraveler.verdant.client.renderer.*;
import com.startraveler.verdant.client.screen.FishTrapScreen;
import com.startraveler.verdant.data.*;
import com.startraveler.verdant.registration.RegistryObject;
import com.startraveler.verdant.registry.BlockEntityTypeRegistry;
import com.startraveler.verdant.registry.EntityTypeRegistry;
import com.startraveler.verdant.registry.MenuRegistry;
import com.startraveler.verdant.registry.WoodSets;
import com.startraveler.verdant.util.baitdata.BaitData;
import com.startraveler.verdant.util.blocktransformer.BlockTransformer;
import com.startraveler.verdant.util.featureset.FeatureSet;
import com.startraveler.verdant.woodset.WoodSet;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
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

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Mod(value = "verdant", dist = Dist.CLIENT)
public class VerdantClient {

    protected static final Map<RegistryObject<EntityType<?>, EntityType<? extends Boat>>, ModelLayerLocation> LOCATION_FOR_BOAT = new HashMap<>();
    protected static final Map<RegistryObject<EntityType<?>, EntityType<? extends ChestBoat>>, ModelLayerLocation> LOCATION_FOR_CHEST_BOAT = new HashMap<>();

    public VerdantClient(IEventBus modBus) {
        modBus.addListener(VerdantClient::gatherData);
        modBus.addListener(VerdantClient::onClientSetup);
        modBus.addListener(VerdantClient::onRegisterLayerDefinitions);
        modBus.addListener(VerdantClient::registerScreens);
        modBus.addListener(VerdantClient::registerBlockEntityRenderers);
        modBus.addListener(VerdantClient::registerSpecialModels);
        modBus.addListener(VerdantClient::registerRangeProperties);
        modBus.addListener(VerdantClient::registerSelectProperties);
        modBus.addListener(VerdantClient::registerConditionalProperties);

        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            initialSetupBeforeRenderEvents(woodSet);
        }
    }


    public static void gatherData(final GatherDataEvent.Client event) {
        try {
            // Store some frequently-used fields for later use.
            DataGenerator generator = event.getGenerator();
            PackOutput packOutput = generator.getPackOutput();
            CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

            /*
            Debugging purposes only.
            BuiltInRegistries.REGISTRY.stream()
                    .forEach(registry -> Constants.LOG.warn("Found registry {} ", registry.key().location()));
            */

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
                        protected void validate(@NotNull WritableRegistry<LootTable> writableregistry, @NotNull ValidationContext context, ProblemReporter.Collector collector) {
                            // Do not validate at all, per what people online said.
                        }
                    }
            );

            // Generate data for the recipes
            generator.addProvider(true, new VerdantRecipeProvider.Runner(packOutput, lookupProvider));

            // Generate data for the tags
            BlockTagsProvider blockTagsProvider = new VerdantBlockTagProvider(packOutput, lookupProvider);
            generator.addProvider(true, blockTagsProvider);
            MobEffectTagProvider mobEffectTagsProvider = new VerdantMobEffectTagProvider(packOutput, lookupProvider);
            generator.addProvider(true, mobEffectTagsProvider);
            EntityTypeTagsProvider entityTypeTagsProvider = new VerdantEntityTypeTagProvider(
                    packOutput,
                    lookupProvider
            );
            generator.addProvider(true, entityTypeTagsProvider);
            generator.addProvider(
                    true,
                    new VerdantItemTagProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter())
            );

            // Generate block and item models.
            generator.addProvider(true, new VerdantModelProvider(packOutput));

            // Generate dynamic registries
            generator.addProvider(
                    true, new DatapackBuiltinEntriesProvider(
                            packOutput,
                            lookupProvider,
                            new RegistrySetBuilder().add(Registries.DAMAGE_TYPE, VerdantDamageSourceProvider::register)
                                    .add(BlockTransformer.KEY, VerdantBlockTransformerProvider::register)
                                    .add(BaitData.KEY, BaitDataProvider::register)
                                    .add(FeatureSet.KEY, VerdantFeatureSetProvider::register),
                            Set.of(Constants.MOD_ID, "minecraft")
                    )
            );

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


    public static void initialSetupBeforeRenderEvents(WoodSet woodSet) {

        ModelLayerLocation boat = new ModelLayerLocation(
                ResourceLocation.withDefaultNamespace("boat/" + woodSet.getName()),
                "main"
        );
        // ModelLayers.register("boat/" + woodSet.getName());
        ModelLayerLocation chestBoat = new ModelLayerLocation(
                ResourceLocation.withDefaultNamespace("chest_boat/" + woodSet.getName()),
                "main"
        );
        // ModelLayers.register("chest_boat/" + woodSet.getName());

        LOCATION_FOR_BOAT.put(woodSet.getBoat(), boat);
        LOCATION_FOR_CHEST_BOAT.put(woodSet.getChestBoat(), chestBoat);
    }

    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            event.registerLayerDefinition(LOCATION_FOR_BOAT.get(woodSet.getBoat()), BoatModel::createBoatModel);
            event.registerLayerDefinition(
                    LOCATION_FOR_CHEST_BOAT.get(woodSet.getChestBoat()),
                    BoatModel::createChestBoatModel
            );
        }
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            setupWoodSets();

            EntityRenderers.register(EntityTypeRegistry.THROWN_ROPE.get(), ThrownItemRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.TIMBERMITE.get(), TimbermiteRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.POISON_ARROW.get(), PoisonArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.ROOTED.get(), RootedRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.THROWN_SPEAR.get(), ThrownSpearRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.DART.get(), TippableDartRenderer::new);
        });
    }

    public static void setupWoodSets() {
        for (WoodSet woodSet : WoodSets.WOOD_SETS) {
            setupWoodSet(woodSet);
        }
    }

    public static void setupWoodSet(WoodSet woodSet) {
        EntityRenderers.register(
                woodSet.getBoat().get(),
                (context) -> new BoatRenderer(context, LOCATION_FOR_BOAT.get(woodSet.getBoat()))
        );
        EntityRenderers.register(
                woodSet.getChestBoat().get(),
                (context) -> new BoatRenderer(context, LOCATION_FOR_CHEST_BOAT.get(woodSet.getChestBoat()))
        );

        registerRenderTypes(woodSet);
    }

    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(MenuRegistry.FISH_TRAP_MENU.get(), FishTrapScreen::new);
    }

    @SuppressWarnings("deprecation")
    public static void registerRenderTypes(WoodSet woodSet) {
        ItemBlockRenderTypes.setRenderLayer(woodSet.getTrapdoor().get(), RenderType.CUTOUT);
        ItemBlockRenderTypes.setRenderLayer(woodSet.getDoor().get(), RenderType.CUTOUT);
    }

    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                // The block entity type to register the renderer for.
                BlockEntityTypeRegistry.VERDANT_CONDUIT_BLOCK_ENTITY.get(),
                // A function of BlockEntityRendererProvider.Context to BlockEntityRenderer.
                VerdantConduitRenderer::new
        );
    }

    public static void registerSpecialModels(RegisterSpecialModelRendererEvent event) {
        event.register(VerdantConduitSpecialRenderer.Unbaked.LOCATION, VerdantConduitSpecialRenderer.Unbaked.MAP_CODEC);
    }

    public static void registerSelectProperties(RegisterSelectItemModelPropertyEvent event) {
        event.register(
                // The name to reference as the type
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "rope/hanging_block"),
                // The property type
                RopeHangingBlockProperty.TYPE
        );
    }

    public static void registerRangeProperties(RegisterRangeSelectItemModelPropertyEvent event) {
        event.register(
                // The name to reference as the type
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "rope/rope_length"),
                // The map codec
                RopeLengthProperty.MAP_CODEC
        );
        event.register(
                // The name to reference as the type
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "rope/glow_level"),
                // The map codec
                RopeGlowProperty.MAP_CODEC
        );
    }


    public static void registerConditionalProperties(RegisterConditionalItemModelPropertyEvent event) {
        event.register(
                // The name to reference as the type
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "rope/has_hook"),
                // The map codec
                RopeHookProperty.MAP_CODEC
        );
    }
}