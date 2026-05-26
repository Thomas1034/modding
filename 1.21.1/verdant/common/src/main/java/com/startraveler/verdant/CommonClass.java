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
Caltrops - four thorns, four hanging roots, for a consumable that deals damage and inflicts slowness when entities step on it (crafts two).
Lingers on the ground and will not hurt the person who threw it.
Tilapia nests that act as tilapia spawners
Arapaima nests that act as arapaima spawners
Root ball weapons that create a tangled mess of roots when thrown
Incense; crafted out of balsam and blaze rod, right click to light/extinguish, slowly burns while lit in inventory and grants plant immunity

*/
// Credits: (other direct contributors only)
/*
// Make spawner that creates green growth particles.

Changes:
- Updated to 26.1
- Imbued Verdant Heartwood now cycles its texture three times as fast.
- Verdant Conduits have an updated texture.
- Max age strangler vine sides can now stand unsupported, acting as a sort of vertical slab.
- Leafy strangler vines now drop ordinary strangler vine loot.

Features Added:

Bugs Fixed:
- Fixed a long-standing glitch where leafy strangler vines wouldn't grow leaves around them when they mature.

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
        ExtensibleCakeBlock cake = BlockRegistry.UBE_CAKE.get();
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
