package com.startraveler.mansioneer;


import com.startraveler.mansioneer.platform.Services;
import com.startraveler.mansioneer.registry.StructureProcessorTypeRegistry;
import com.startraveler.mansioneer.util.blocktransformer.BuiltInTransformers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
/*
Special thanks to The...Why...Even...How...? for solving the thorny problem of getting the biome of a potentially non-yet-generated chunk. Your patience, dedication, and passion for problem-solving are much appreciated.

https://discord.com/channels/507304429255393322/511324119728521285/1327884671656132648

 */


public class CommonClass {

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {

        Constants.LOG.info(
                "Hello from Common init on {}! we are currently in a {} environment!",
                Services.PLATFORM.getPlatformName(),
                Services.PLATFORM.getEnvironmentName()
        );
        Constants.LOG.info("The ID for diamonds is {}", BuiltInRegistries.ITEM.getKey(Items.DIAMOND));

        // It is common for all supported loaders to provide a similar feature that can not be used directly in the
        // common code. A popular way to get around this is using Java's built-in service loader feature to create
        // your own abstraction layer. You can learn more about this in our provided services class. In this example
        // we have an interface in the common code and use a loader specific implementation to delegate our call to
        // the platform specific approach.
        if (Services.PLATFORM.isModLoaded("mansioneer")) {
            Constants.LOG.info("Hello to Mansioneer");
        }

        StructureProcessorTypeRegistry.init();
        BuiltInTransformers.init();
    }
}