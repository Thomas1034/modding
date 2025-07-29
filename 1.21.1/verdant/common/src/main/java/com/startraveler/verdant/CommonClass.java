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

TODO set up entity motion tracker, possibly make into library with block-transformer later.
Search for TODO TODO TODO in Discord.
Mixin to public void teleportTo(double x, double y, double z) and public Entity teleport(TeleportTransition teleportTransition), both in Entity.

Add "tangled mats" which grow in the top layer of water (underwater) and slow down entities caught in them.
Multiple growth stages - start as a thin layer of algae along the surface, and grow to dense plants with roots filling the whole blocks.
Find use for them?

Add manatees.

Boss ideas:
Stationary
Spawns in large open areas
Retreats underground if there are no players nearby
AOE attack, like dragon's breath. Maybe summons thorn spike entities in an area?

Advancements:
"Born and Bred", "Walk through a thorn bush without taking damage."
"That is just wrong on so many levels", "Use Toxic Ash as a shortcut while fishing."
"In Aloe Veritas", "Use aloe's soothing gel to remove a harmful effect."
"Kabloom", "Step on a volatile flower."
"Minesweeper", "Collect a Blasting Bloom using shears."
"Someone Else's Problem", "Craft Wooden Spikes."
"Warning: Sharp Floor", "Craft Iron Spikes."
"", "Place a Wooden Trap."
"", "Place an Iron Trap."
"Gilded Cage", "Place a Golden Trap." (add golden traps first)

Compatibility

Ideas:
Caltrops - eight thorns, one plank, for a consumable that deals damage and inflicts slowness when entities step on it.
Lingers on the ground and will not hurt the person who threw it.

 */
// Credits: (other direct contributors only)
/*


// TODO fix ube cake model! fix advancement menu background!

Changes:
- Rebalanced grass/flower growth rates, so flowers are more rare.
- Rebalanced some fish trap bait rates, and added a few missing items such as potatoes.
- Halved the speed of the fish trap.
- Timbermite has a brighter eye.
- Frames cost less thorns.
- Stinking Blossoms now require silk touch or shears to collect.
- Tall bushes no longer drop themselves - they drop sticks/thorns instead. They can still be crafted from small bushes collected via silk touch.
- Blueweed flowers have higher contrast
- Blasting Blossoms grow a quarter as fast as they used to grow
- Buffed Heartwood tool and weapon durability
- Thorns Upgrade Templates now require copper instead of gold
- Added a tooltip telling how to upgrade rope coils, since it doesn't appear as a recipe
- Blowguns now wear out, and require copper instead of iron
- Parrots are no longer harmed by the Verdant Growth
- Tweaked a few sounds

Features Added:
- Chiseled Earth Bricks
- Mulch Sack, to help reduce tall grass

Bugs Fixed:
- Blasting Blossoms were not in the creative inventory
- Blasting Blossoms did not wear down the shears used to collect them
- All effect icons were the wrong size
- Wild Ube, Wild Cassava, and Blueweed were not flammable.
- Tipped Dart crafting recipes now properly add effect times.
- Toxic ash now spreads correctly.
- Cassava and Ube crops now use the correct crop model.
- Mineability block tags are actually correct for blocks other than wood sets. Not sure how I missed that.
- Leafy Strangler Vines no longer provide x-ray.

Verdant resin blocks will stick only to each other, and not to other blocks!
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
