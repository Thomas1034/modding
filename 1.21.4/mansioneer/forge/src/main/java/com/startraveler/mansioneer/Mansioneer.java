package com.startraveler.mansioneer;

import com.startraveler.mansioneer.util.biomemapping.BiomeMapping;
import com.startraveler.mansioneer.util.blocktransformer.BlockTransformer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DataPackRegistryEvent;

@Mod(Constants.MOD_ID)
public class Mansioneer {

    public Mansioneer(FMLJavaModLoadingContext context) {

        IEventBus eventBus = context.getModEventBus();

        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.

        // Use Forge to bootstrap the Common mod.
        CommonClass.init();

        eventBus.addListener(Mansioneer::registerDatapackRegistries);
    }


    public static void registerDatapackRegistries(final DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(BlockTransformer.KEY, BlockTransformer.CODEC, BlockTransformer.CODEC);
        event.dataPackRegistry(BiomeMapping.KEY, BiomeMapping.CODEC, BiomeMapping.CODEC);
    }
}