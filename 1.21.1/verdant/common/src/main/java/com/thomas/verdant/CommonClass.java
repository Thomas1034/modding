package com.thomas.verdant;

import com.thomas.verdant.platform.Services;
import com.thomas.verdant.registry.*;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.
// TODO: when toxic ash is added, implement it as a feature type.
// Configurable radius and number of attempts - like flower patch, maybe.
// Then, the toxic ash item and bucket can just place different features, easy enough!
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

Add "tangled mats" which grow in the top layer of water (underwater) and slow down entities caught in them.
Multiple growth stages - start as a thin layer of algae along the surface, and grow to dense plants with roots
filling the whole blocks.
Find use for them?

Add manatees.

Boss ideas:
Stationary
Spawns in large open areas
Retreats underground if there are no players nearby
AOE attack, like dragon's breath. Maybe summons thorn spike entities in an area?

Advancements:
born and bred, walk through a thorn bush without taking damage.
in soviet russia..., get eaten by a snap plant
thou wast a spirit to delicate, cut open a tree, step inside, and have log block grow on you

Snap plant: a natural trap block. Much fun.

// TODO fix rendering problem with strangler vine age 1 on top and bottom faces of blocks
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

}