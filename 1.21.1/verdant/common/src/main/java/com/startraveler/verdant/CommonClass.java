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
"That is just wrong on so many levels", "Use Toxic Ash as a shortcut while fishing." for using toxic ash for fishing
"Not Generally Considered Edible", "Why would you eat that?", for eating rotten fertilizer
"In Aloe Veritas", "Use aloe's soothing gel to remove a harmful effect."
"Kabloom", "Step on a volatile flower"
"Minesweeper", "Collect a Blasting Bloom using shears."

Compatibility


 */
// Credits: (other direct contributors only)
/*
Features Added:
- Blasting Blossoms, a plant inspired by Legend of Zelda's Bomb Flowers.
- Blasting Blooms, usable as grenade-like weapons
- Stable Blasting Blooms, which can be placed and stacked for powerful mining explosions.
- Broken Armor potions, reducing the protection given by armor, brewed from Blasting Blooms in Thick potions.
- The Unbreakable potion effect prevents durability loss, brewed from Fermented Spider's Eye in Broken Armor potions.
- Colorblindness and Fading potions disrupt player vision.
- Clumsiness potions, brewed from a Fermented Spider's Eye in a Blurred potion, cause entities to drop what they are holding.
- Inner Fire potions, brewed with Magma Cream in a Colloid potion, ignite entities they are inflicted upon.
- The Flickering potion effect causes entities to teleport uncontrollably when hit, brewed with a Chorus Flower in a Colloid potion.
- The Recall potion will teleport the player back to their spawn point, brewed with a Fermented Spider's Eye in a Flickering potion.
- Blasting Blossom Sprouts, used to plant Blasting Blooms.
- Rebalanced some spread rates.
- Rope Ladders, which can hang from ladders above them.
- Blowgun, firing darts.
- Tipped darts, crafted with darts. Add flowers or various other items to add effects, and lengthen the effects with slime balls or honey bottles.
- Aloe, a two-stage crop used to soothe harmful effects.
- Snapleaves can be smelted into green dye.
- Adrenaline potions grant a burst of speed when you take damage, brewed from Fermented Spider's Eye in Colloid potions.
- Added Fused Gravel, a decorative block smelted from Packed Gravel.
- Added Scree, a dark-colored gravel equivalent formed by erosion from Deepslate.
- Added Packed Scree and Fused Scree.
- Added Grus (yes, that's a real word!), a gravely and nutrient-poor soil eroded from Scree.
- Added Rocky Grus, the equivalent of Coarse Dirt for Grus and Scree.
- Poisoners, witches that have learned to use the plants of the Verdant Growth for more potent potions.
- Fragile Flasks, exclusive drops from Poisoners that amplify potion effects when consumed.
- Earth Bricks, made of Dirt and Grus, with a dash of Toxic Ash to keep plants away.
- Blueweed, a flower that causes photosensitivity when touched.

Bugs Fixed:
- Fixed a few mistagged blocks/items.
- Spiders and Cave Spiders are now friendly to the Verdant Growth (and vice versa).
- Dead Bushes can now survive on verdant ground.
- The poison effect from Toxic Dirt flickers when viewed in the inventory.
- Bucket of Toxic Solution does not actually use up when it is used.
- Rooted Dirt and Muddy Mangrove Roots are no longer inexplicably immune to the Verdant Growth
- Reduced lag from verdant ground that isn't actively spreading.
- Packed Gravel is now properly mineable with shovels.
- And More! ( that I forgot to write down :) )
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
