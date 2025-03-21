package com.startraveler.verdant;

import com.startraveler.rootbound.RootboundClient;
import com.startraveler.rootbound.blocktransformer.BlockTransformer;
import com.startraveler.rootbound.featureset.FeatureSet;
import com.startraveler.verdant.client.item.RopeGlowProperty;
import com.startraveler.verdant.client.item.RopeHangingBlockProperty;
import com.startraveler.verdant.client.item.RopeHookProperty;
import com.startraveler.verdant.client.item.RopeLengthProperty;
import com.startraveler.verdant.client.renderer.*;
import com.startraveler.verdant.client.screen.FishTrapScreen;
import com.startraveler.verdant.data.*;
import com.startraveler.verdant.registry.*;
import com.startraveler.verdant.util.baitdata.BaitData;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.TntRenderer;
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
import java.util.Set;
import java.util.concurrent.CompletableFuture;

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
        modBus.addListener(VerdantClient::registerSpecialModels);
        modBus.addListener(VerdantClient::registerRangeProperties);
        modBus.addListener(VerdantClient::registerSelectProperties);
        modBus.addListener(VerdantClient::registerConditionalProperties);

        RootboundClient.initializeWoodSets(modBus, WoodSets.WOOD_SETS);
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
                                    // .add(TileSet.KEY, VerdantTileSetProvider::register)
                                    // .add(StructureTile.KEY, VerdantStructureTileProvider::register)
                                    // .add(TileConnection.KEY, VerdantTileConnectionProvider::register)
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


    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            EntityRenderers.register(EntityTypeRegistry.THROWN_ROPE.get(), ThrownItemRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.TIMBERMITE.get(), TimbermiteRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.POISON_ARROW.get(), PoisonArrowRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.ROOTED.get(), RootedRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.THROWN_SPEAR.get(), ThrownSpearRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.DART.get(), TippableDartRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.BLOCK_IGNORING_PRIMED_TNT.get(), TntRenderer::new);
            EntityRenderers.register(EntityTypeRegistry.POISONER.get(), PoisonerRenderer::new);

            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.TALL_BUSH.get(), RenderType.CUTOUT);
            ItemBlockRenderTypes.setRenderLayer(BlockRegistry.TALL_THORN_BUSH.get(), RenderType.CUTOUT);

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