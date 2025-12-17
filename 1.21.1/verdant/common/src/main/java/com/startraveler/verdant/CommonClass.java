/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant;

import com.startraveler.verdant.block.custom.extensible.ExtensibleCakeBlock;
import com.startraveler.verdant.platform.Services;
import com.startraveler.verdant.registry.*;
import com.startraveler.verdant.util.ReloadableRegistryCache;
import net.minecraft.world.level.block.Blocks;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.

//
// Ideas for new effects:
//
// TODO Add firefly attractor! "You would not believe your eyes..."
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
Multiple growth stages - start as a thin layer of algae along the surface, and grow to dense plants with roots filling the whole blocks.
Find use for them?

Add manatees?

Boss ideas:
Stationary
Spawns in large open areas
Retreats underground if there are no players nearby
AOE attack, like dragon's breath. Maybe summons thorn spike entities in an area?

Advancements:
"Minesweeper", "Collect a Blasting Bloom using shears."

Compatibility

Ideas:
Caltrops - eight thorns, one plank, for a consumable that deals damage and inflicts slowness when entities step on it.
Lingers on the ground and will not hurt the person who threw it.

 */
// Credits: (other direct contributors only)
/*

Changes:
- Reduced the damage that spikes do once again.
- Tweaks to spike hitboxes (again).
- Sap slows entities in it less, but gives a slowing potion effect.
- Sap's model and texture changed.
- Sap now obscures vision of entities inside it.
- Tweaked heartwood log/wood textures.
- Changed spawn probabilities of verdant growth features, as always.
- Slightly changed the recipe for blasting blossom sprouts.
- Massively nerfed stable blasting blooms; they now no longer destroy blocks. See "Features Added" for the new alternatives.
- All bomb piles are gravity-affected and explode on landing. Have fun.
- Throwable bombs (blasting blooms, terracotta grenades, metal grenades) now have a cooldown.
- Made wild ube and cassava replaceable.
- Heart of the Forest is now crafted using heart fragments and balsam.
- Rearranged the creative mode tab contents.
- Increased the yield of gunpowder crafting with blasting blossoms.
- Decreased the suspicious soup time of Bleeding Hearts and Rue.

Features Added:
- Copper and gold spikes and traps.
- Juice and Nectar, alternatives to healing potions.
- A few more advancements.
- Oozes, darker-green slimes that can spawn holding flowers. They inflict potion effects corresponding to the flower they hold. Oozes will pick up flower items; use this to your advantage! Oozes drop sap globs when killed.
- Small oozes can be picked up with water buckets and carried around, for convenience. This makes them friendlier and deal less damage.
- Ooze fissures, uncommon features that rapidly spawn oozes around them. They are less productive after being harvested and re-placed.
- Balsam, an item obtained from harvesting natural ooze fissures.
- Balm, a healing item obtained from brewing balsam into an empty bottle.
- Terracotta bombs, crafted from stable blasting blooms and bricks. These destroy terrain in a large radius.
- Metal bombs, crafted from stable blasting blooms, copper ingots, and iron ingots. These destroy terrain in a very large radius with a fiery explosion.
- Terracotta grenades, crafted from a terracotta bomb and a string, are a more powerful equivalent to the blasting bloom.
- Metal grenades, crafted from a metal bomb and a string, are more powerful than terracotta grenades. Handle carefully!
- Machetes, a new type of tool craftable in iron, diamond, and netherite, that mines plants in a 3x3x3 cube! Excellent for hacking through the underbrush or clearing land.

Bugs Fixed:
- Fragile blocks like rotten wood now break correctly when fallen upon.
- Rotten flesh now correctly applies hunger to darts instead of poison.
- Critical bugfix: diamonds no longer turn into emeralds when eroded.
- Rope texture was misaligned.
- Rope is now correctly ignited by lava.
- Corrected the random sequence in the Timbermite loot table.
- The group is now correct in the mod's properties... I forgot to change it from back when it was my name.
- Somehow, I accidentally renamed zombie heads and altered their loot tables. I have no idea how I did this.
- Sacks can no longer be crafted into bundles.
- The achievement for golden traps had the same description, title, and image as the one for iron traps.

 */
public class CommonClass {

    public static final ReloadableRegistryCache.Transformers TRANSFORMERS = new ReloadableRegistryCache.Transformers();

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    public static void init() {

        //        Constants.LOG.info(
        //                "Hello from Common init on {}! we are currently in a {} environment!",
        //                Services.PLATFORM.getPlatformName(),
        //                Services.PLATFORM.getEnvironmentName()
        //        );

        // It is common for all supported loaders to provide a similar feature that can not be used directly in the
        // common code. A popular way to get around this is using Java's built-in service loader feature to create
        // your own abstraction layer. You can learn more about this in our provided services class. In this example
        // we have an interface in the common code and use a loader specific implementation to delegate our call to
        // the platform specific approach.
        if (Services.PLATFORM.isModLoaded(Constants.MOD_ID) && Services.PLATFORM.isDevelopmentEnvironment()) {
            // Constants.LOG.debug("Verdant is loaded successfully.");
        }

        DataComponentRegistry.init();
        ItemRegistry.init();
        EntityTypeRegistry.init();

        BlockRegistry.init();
        MobEffectRegistry.init();
        PotionRegistry.init();
        WoodSets.init();
        BlockEntityTypeRegistry.init();
        MenuRegistry.init();
        FeatureRegistry.init();
        CreativeModeTabRegistry.init();
        RecipeSerializerRegistry.init();
        TriggerRegistry.init();
    }


    public static void addCakeCandles() {
        ExtensibleCakeBlock cake = (ExtensibleCakeBlock) BlockRegistry.UBE_CAKE.get();
        cake.addCandleCake(Blocks.CANDLE, BlockRegistry.CANDLE_UBE_CAKE.get());
        cake.addCandleCake(Blocks.WHITE_CANDLE, BlockRegistry.WHITE_CANDLE_UBE_CAKE.get());
        cake.addCandleCake(Blocks.ORANGE_CANDLE, BlockRegistry.ORANGE_CANDLE_UBE_CAKE.get());
        cake.addCandleCake(Blocks.MAGENTA_CANDLE, BlockRegistry.MAGENTA_CANDLE_UBE_CAKE.get());
        cake.addCandleCake(Blocks.LIGHT_BLUE_CANDLE, BlockRegistry.LIGHT_BLUE_CANDLE_UBE_CAKE.get());
        cake.addCandleCake(Blocks.YELLOW_CANDLE, BlockRegistry.YELLOW_CANDLE_UBE_CAKE.get());
        cake.addCandleCake(Blocks.LIME_CANDLE, BlockRegistry.LIME_CANDLE_UBE_CAKE.get());
        cake.addCandleCake(Blocks.PINK_CANDLE, BlockRegistry.PINK_CANDLE_UBE_CAKE.get());
        cake.addCandleCake(Blocks.GRAY_CANDLE, BlockRegistry.GRAY_CANDLE_UBE_CAKE.get());
        cake.addCandleCake(Blocks.LIGHT_GRAY_CANDLE, BlockRegistry.LIGHT_GRAY_CANDLE_UBE_CAKE.get());
        cake.addCandleCake(Blocks.CYAN_CANDLE, BlockRegistry.CYAN_CANDLE_UBE_CAKE.get());
        cake.addCandleCake(Blocks.PURPLE_CANDLE, BlockRegistry.PURPLE_CANDLE_UBE_CAKE.get());
        cake.addCandleCake(Blocks.BLUE_CANDLE, BlockRegistry.BLUE_CANDLE_UBE_CAKE.get());
        cake.addCandleCake(Blocks.BROWN_CANDLE, BlockRegistry.BROWN_CANDLE_UBE_CAKE.get());
        cake.addCandleCake(Blocks.GREEN_CANDLE, BlockRegistry.GREEN_CANDLE_UBE_CAKE.get());
        cake.addCandleCake(Blocks.RED_CANDLE, BlockRegistry.RED_CANDLE_UBE_CAKE.get());
        cake.addCandleCake(Blocks.BLACK_CANDLE, BlockRegistry.BLACK_CANDLE_UBE_CAKE.get());
    }

}
