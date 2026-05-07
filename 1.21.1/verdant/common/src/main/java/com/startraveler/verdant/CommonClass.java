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
import com.startraveler.verdant.entity.custom.SkullSpiderEntity;
import com.startraveler.verdant.platform.Services;
import com.startraveler.verdant.registry.*;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as Forge events
// however it will be compatible with all supported mod loaders.

//
// Todo
// Retexture skull spiders.
// Make skull spider web projectiles not drop as items.
// Make skull spiders start spawning again
// Give traps their own damage source.

/*

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
// Make spawner that creates green growth particles.

Changes:
- Updated to 1.21.11
- Added uncommon moss patches as grown features in the Verdant.
- Slight rework to how traps work, to improve the experience.
- Tweaked thorny heartwood armor protection.
- Sneaking now prevents damage from thorn bushes.
- Reduced damage from thorn bushes.
- Rebalanced the broken armor effect to decrease armor by 25% per level instead of 10% per level.
- Changed almost all heartwood equipment textures.
- Various general texture tweaks and improvements.
- Changed the stranger vine overlay texture.
- Verdant grass and bushes are now biome-tinted.
- Snapleaves are now biome-tinted.
- Snapleaves now can only generate on dirt-like blocks and logs.
- Poisoners now use better potions when healing allies.
- Stinking blossoms are now killed by toxic ash.
- Stinking blossoms now produce particles over the correct volume.
- Stinking blossoms now produce a temporary stench cloud when broken. This has its benefits!
- Thorny strangler leaves now only deal damage when you are moving.
- Reduced the damage that spikes do once again.
- Tweaks to spike hitboxes (again).
- Sap slows entities in it less, but gives a slowing potion effect.
- Sap's model and texture changed.
- Sap now obscures vision of entities inside it.
- Tweaked heartwood log/wood textures.
- Tweaked strangler door/trapdoor textures.
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
- All heartwood swords can now attack through grass, bushes, and other non-colliding blocks.
- Toxic ash now turns moss into dead moss.
- Shelves for heartwood, strangler, mango, and dead wood sets.
- Copper machete.
- Copper and gold spikes and traps.
- Spears for the heartwood tool sets.
- Juice and Nectar, alternatives to healing potions.
- A few more advancements.
- Oozes, darker-green slimes that can spawn holding flowers. They inflict potion effects corresponding to the flower they hold. Some Oozes can pick up flower items; use this to your advantage! Oozes drop sap globs when killed.
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
- Cooked golden cassava and golden mangoes are now always edible.
- Pistons pushing Verdant Resin now works correctly (i.e., resin blocks stick to each other) on Fabric, if it didn't before.
- Leafy Strangler Vines now correctly place Leafy Strangler vines. Previously, they placed Strangler Vines (without leaves) due to hardcoding.
- Ube cakes now restore the correct amount of food and saturation.
- Resin brick walls were misnamed. This will cause all existing ones to disappear. Sorry.
- Resin blocks can now be crafted directly from resin clumps.
- Fragile blocks like rotten wood now break correctly when fallen upon.
- Rotten flesh now correctly applies hunger to darts instead of poison.
- Critical bugfix: diamonds no longer turn into emeralds when eroded.
- Rope texture was misaligned.
- Rope is now correctly ignited by lava.
- Corrected the random sequence in the Timbermite loot table.
- The group is now correct in the mod's properties... I forgot to change it from back when it was my name. Forget you saw that.
- Somehow, I accidentally renamed zombie heads and altered their loot tables. I have no idea how I did this.
- Sacks can no longer be crafted into bundles.
- The achievement for golden traps had the same description, title, and image as the one for iron traps.

 */
public class CommonClass {

    // The loader specific projects are able to import and use any code from the common project. This allows you to
    // write the majority of your code here and load it from your loader specific projects. This example has some
    // code that gets invoked by the entry point of the loader specific projects.
    @SuppressWarnings("StatementWithEmptyBody")
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

    public static void spawnSpiderlingsOnBlockBreak(LevelAccessor level, @SuppressWarnings("unused") Player player, BlockPos blockPos, BlockState blockState) {

        double bugSpawningChance = Constants.SPIDER_CHANCE;

        if (blockState.is(VerdantTags.Blocks.CAN_SPAWN_BUGS_WHEN_BROKEN)) {

            if (level instanceof ServerLevel serverLevel) {

                BlockPos belowPos = blockPos.below();
                BlockState belowState = serverLevel.getBlockState(belowPos);

                if (belowState.is(VerdantTags.Blocks.BUGS_CAN_SPAWN_ABOVE)) {

                    if (level.getRandom().nextDouble() < bugSpawningChance) {
                        SkullSpiderEntity spider = new SkullSpiderEntity(
                                EntityTypeRegistry.SKULL_SPIDER.get(),
                                serverLevel
                        );
                        spider.setPos(blockPos.getBottomCenter());
                        level.addFreshEntity(spider);
                    }
                }
            }
        }
    }

}
