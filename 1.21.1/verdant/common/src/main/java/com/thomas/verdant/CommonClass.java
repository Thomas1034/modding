package com.thomas.verdant;

import com.thomas.verdant.platform.Services;
import com.thomas.verdant.registry.*;
import net.minecraft.world.level.block.ComposterBlock;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
// TODO: add dispenser behavior to wood set boats.
//
// TODO When achievements are added: Why would you do that? for eating rotten compost.
//
// Look into connected textures for Antigorite, and make polished, brick, and chiseled variants.
// https://www.curseforge.com/minecraft/mc-mods/continuity
//
// Ideas for new effects:
// Photosensitivity - sky light causes damage during the daytime
// Numbness - health bar displays as full if above max - level hearts (needs mixins)
// Nettled - walking hurts
// Cyclopegia - apply blur filter over entire screen, with varying severity based on the strength
//
// TODO Ideas for future improvements:
/*
 Make the rope coil recipe more customizable.
 Don't hard-code the items; instead, have them be defined in the JSON
 along with a list of operations they can perform on the input stack.
 For example, setting length, adding/subtracting length, adding a hook,
 removing a hook, etc., etc., all defined as JSON objects.
 Then build the result based on these as if they're tiny lambdas.
 Order of operations would be important, especially with operations
 that involve setting the length.

Add manatees.
 */
// Credits: (other direct contributors only)
/*

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

        // It is common for all supported loaders to provide a similar feature that can not be used directly in the
        // common code. A popular way to get around this is using Java's built-in service loader feature to create
        // your own abstraction layer. You can learn more about this in our provided services class. In this example
        // we have an interface in the common code and use a loader specific implementation to delegate our call to
        // the platform specific approach.
        if (Services.PLATFORM.isModLoaded("verdant") && Services.PLATFORM.isDevelopmentEnvironment()) {

            Constants.LOG.debug("Verdant is loaded successfully.");
        }

        DataComponentRegistry.init();
        ItemRegistry.init();
        EntityTypeRegistry.init();
        BlockRegistry.init();
        MobEffectRegistry.init();
        WoodSets.init();
        BlockEntityTypeRegistry.init();
        MenuRegistry.init();
        FeatureRegistry.init();
        CreativeModeTabRegistry.init();
        RecipeSerializerRegistry.init();
    }

    public static void initCompostables() {
        ComposterBlock.COMPOSTABLES.put(BlockRegistry.POISON_STRANGLER_LEAVES.get().asItem(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(BlockRegistry.THORNY_STRANGLER_LEAVES.get().asItem(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(BlockRegistry.STRANGLER_LEAVES.get().asItem(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(BlockRegistry.WILTED_STRANGLER_LEAVES.get().asItem(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(BlockRegistry.STRANGLER_TENDRIL.get().asItem(), 0.2f);
    }


}