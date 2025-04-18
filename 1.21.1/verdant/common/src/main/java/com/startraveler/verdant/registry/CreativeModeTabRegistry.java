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
package com.startraveler.verdant.registry;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.registration.RegistrationProvider;
import com.startraveler.verdant.registration.RegistryObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;

public class CreativeModeTabRegistry {

    public static final RegistrationProvider<CreativeModeTab> CREATIVE_MODE_TABS = RegistrationProvider.get(
            Registries.CREATIVE_MODE_TAB,
            Constants.MOD_ID
    );

    public static final RegistryObject<CreativeModeTab, CreativeModeTab> BLOCKS_TAB = CREATIVE_MODE_TABS.register(
            Constants.MOD_ID + "_blocks_tab",
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .icon(() -> new ItemStack(BlockRegistry.VERDANT_ROOTED_DIRT.get()))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(BlockRegistry.VERDANT_ROOTED_DIRT.get());
                        output.accept(BlockRegistry.VERDANT_GRASS_DIRT.get());
                        output.accept(BlockRegistry.VERDANT_ROOTED_MUD.get());
                        output.accept(BlockRegistry.VERDANT_GRASS_MUD.get());
                        output.accept(BlockRegistry.VERDANT_ROOTED_CLAY.get());
                        output.accept(BlockRegistry.VERDANT_GRASS_CLAY.get());
                        output.accept(BlockRegistry.VERDANT_ROOTED_GRUS.get());
                        output.accept(BlockRegistry.VERDANT_GRASS_GRUS.get());
                        output.accept(BlockRegistry.PACKED_GRAVEL.get());
                        output.accept(BlockRegistry.GRUS.get());
                        output.accept(BlockRegistry.STONY_GRUS.get());
                        output.accept(BlockRegistry.SCREE.get());
                        output.accept(BlockRegistry.TOXIC_GRUS.get());
                        output.accept(BlockRegistry.FUSED_GRAVEL.get());
                        output.accept(BlockRegistry.PACKED_SCREE.get());
                        output.accept(BlockRegistry.FUSED_SCREE.get());
                        output.accept(BlockRegistry.TOXIC_DIRT.get());
                        output.accept(BlockRegistry.DIRT_COAL_ORE.get());
                        output.accept(BlockRegistry.DIRT_COPPER_ORE.get());
                        output.accept(BlockRegistry.DIRT_IRON_ORE.get());
                        output.accept(BlockRegistry.DIRT_GOLD_ORE.get());
                        output.accept(BlockRegistry.DIRT_LAPIS_ORE.get());
                        output.accept(BlockRegistry.DIRT_REDSTONE_ORE.get());
                        output.accept(BlockRegistry.DIRT_EMERALD_ORE.get());
                        output.accept(BlockRegistry.DIRT_DIAMOND_ORE.get());
                        output.accept(BlockRegistry.GRUS_COAL_ORE.get());
                        output.accept(BlockRegistry.GRUS_COPPER_ORE.get());
                        output.accept(BlockRegistry.GRUS_IRON_ORE.get());
                        output.accept(BlockRegistry.GRUS_GOLD_ORE.get());
                        output.accept(BlockRegistry.GRUS_LAPIS_ORE.get());
                        output.accept(BlockRegistry.GRUS_REDSTONE_ORE.get());
                        output.accept(BlockRegistry.GRUS_EMERALD_ORE.get());
                        output.accept(BlockRegistry.GRUS_DIAMOND_ORE.get());
                        output.accept(BlockRegistry.EARTH_BRICKS.get());
                        output.accept(BlockRegistry.EARTH_BRICK_SLAB.get());
                        output.accept(BlockRegistry.EARTH_BRICK_STAIRS.get());
                        output.accept(BlockRegistry.EARTH_BRICK_WALL.get());
                        output.accept(BlockRegistry.IMBUED_HEARTWOOD_LOG.get());
                        output.accept(BlockRegistry.WILTED_STRANGLER_LEAVES.get());
                        output.accept(BlockRegistry.STRANGLER_LEAVES.get());
                        output.accept(BlockRegistry.POISON_STRANGLER_LEAVES.get());
                        output.accept(BlockRegistry.THORNY_STRANGLER_LEAVES.get());
                        output.accept(BlockRegistry.LEAFY_STRANGLER_VINE.get());
                        output.accept(BlockRegistry.STRANGLER_VINE.get());
                        output.accept(BlockRegistry.ROTTEN_WOOD.get());
                        output.accept(BlockRegistry.POISON_IVY.get());
                        output.accept(BlockRegistry.STRANGLER_TENDRIL.get());
                        output.accept(BlockRegistry.FRAME_BLOCK.get());
                        output.accept(BlockRegistry.CHARRED_FRAME_BLOCK.get());
                        output.accept(BlockRegistry.PAPER_FRAME.get());
                        output.accept(BlockRegistry.ROPE.get());
                        output.accept(BlockRegistry.FISH_TRAP.get());
                        output.accept(BlockRegistry.POISON_IVY_BLOCK.get());
                        output.accept(BlockRegistry.TOXIC_ASH_BLOCK.get());
                        output.accept(BlockRegistry.PUTRID_FERTILIZER.get());
                        output.accept(BlockRegistry.WOODEN_SPIKES.get());
                        output.accept(BlockRegistry.IRON_SPIKES.get());
                        output.accept(BlockRegistry.WOODEN_TRAP.get());
                        output.accept(BlockRegistry.IRON_TRAP.get());
                        output.accept(BlockRegistry.STINKING_BLOSSOM.get());
                        output.accept(BlockRegistry.BUSH.get());
                        output.accept(BlockRegistry.THORN_BUSH.get());
                        output.accept(BlockRegistry.TALL_BUSH.get());
                        output.accept(BlockRegistry.TALL_THORN_BUSH.get());
                        output.accept(BlockRegistry.SNAPLEAF.get());
                        output.accept(BlockRegistry.WILD_CASSAVA.get());
                        output.accept(BlockRegistry.WILD_UBE.get());
                        output.accept(BlockRegistry.WILD_COFFEE.get());
                        output.accept(BlockRegistry.BLEEDING_HEART.get());
                        output.accept(BlockRegistry.TIGER_LILY.get());
                        output.accept(BlockRegistry.RUE.get());
                        output.accept(BlockRegistry.BLUEWEED.get());
                        output.accept(BlockRegistry.DROWNED_HEMLOCK.get());
                        output.accept(BlockRegistry.VERDANT_CONDUIT.get());
                    })
                    .title(Component.translatable("creativetab." + Constants.MOD_ID + ".blocks"))
                    .build()
    );


    public static final RegistryObject<CreativeModeTab, CreativeModeTab> ITEMS_TAB = CREATIVE_MODE_TABS.register(
            Constants.MOD_ID + "_items_tab",
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .icon(() -> new ItemStack(ItemRegistry.HEART_OF_THE_FOREST.get()))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ItemRegistry.HEART_OF_THE_FOREST.get());
                        output.accept(ItemRegistry.HEART_FRAGMENT.get());
                        output.accept(ItemRegistry.THORNS_UPGRADE_SMITHING_TEMPLATE.get());
                        output.accept(ItemRegistry.IMBUEMENT_UPGRADE_SMITHING_TEMPLATE.get());
                        output.accept(ItemRegistry.THORN.get());
                        output.accept(ItemRegistry.ROPE_COIL.get());
                        output.accept(ItemRegistry.CASSAVA_CUTTINGS.get());
                        output.accept(ItemRegistry.BITTER_CASSAVA_CUTTINGS.get());
                        output.accept(ItemRegistry.CASSAVA.get());
                        output.accept(ItemRegistry.ALOE_PUP.get());
                        output.accept(ItemRegistry.YOUNG_ALOE_LEAF.get());
                        output.accept(ItemRegistry.ALOE_LEAF.get());
                        output.accept(ItemRegistry.OLD_ALOE_LEAF.get());
                    })
                    .title(Component.translatable("creativetab." + Constants.MOD_ID + ".items"))
                    .build()
    );

    public static final RegistryObject<CreativeModeTab, CreativeModeTab> FOODS_TAB = CREATIVE_MODE_TABS.register(
            Constants.MOD_ID + "_foods_tab",
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .icon(() -> new ItemStack(ItemRegistry.GOLDEN_BREAD.get()))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ItemRegistry.FRAGILE_FLASK.get());
                        output.accept(ItemRegistry.COFFEE_BERRIES.get());
                        output.accept(ItemRegistry.ROASTED_COFFEE.get());
                        output.accept(ItemRegistry.ROTTEN_COMPOST.get());
                        output.accept(ItemRegistry.RANCID_SLIME.get());
                        output.accept(ItemRegistry.BITTER_CASSAVA.get());
                        output.accept(ItemRegistry.GOLDEN_CASSAVA.get());
                        output.accept(ItemRegistry.COOKED_CASSAVA.get());
                        output.accept(ItemRegistry.COOKED_GOLDEN_CASSAVA.get());
                        output.accept(ItemRegistry.STARCH.get());
                        output.accept(ItemRegistry.BITTER_STARCH.get());
                        output.accept(ItemRegistry.SPARKLING_STARCH.get());
                        output.accept(ItemRegistry.BITTER_BREAD.get());
                        output.accept(ItemRegistry.GOLDEN_BREAD.get());
                        output.accept(ItemRegistry.UBE.get());
                        output.accept(ItemRegistry.BAKED_UBE.get());
                        output.accept(ItemRegistry.UBE_COOKIE.get());
                        output.accept(ItemRegistry.UBE_CAKE.get());
                    })
                    .title(Component.translatable("creativetab." + Constants.MOD_ID + ".foods"))
                    .build()
    );


    public static final RegistryObject<CreativeModeTab, CreativeModeTab> COMBAT_TAB = CREATIVE_MODE_TABS.register(
            Constants.MOD_ID + "_combat_tab",
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .icon(() -> new ItemStack(ItemRegistry.IMBUED_HEARTWOOD_SWORD.get()))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(BlockRegistry.WOODEN_SPIKES.get());
                        output.accept(BlockRegistry.IRON_SPIKES.get());
                        output.accept(BlockRegistry.WOODEN_TRAP.get());
                        output.accept(BlockRegistry.IRON_TRAP.get());
                        output.accept(ItemRegistry.POISON_ARROW.get());
                        output.accept(ItemRegistry.HEARTWOOD_SWORD.get());
                        output.accept(ItemRegistry.HEARTWOOD_AXE.get());
                        output.accept(ItemRegistry.HEARTWOOD_HELMET.get());
                        output.accept(ItemRegistry.HEARTWOOD_CHESTPLATE.get());
                        output.accept(ItemRegistry.HEARTWOOD_LEGGINGS.get());
                        output.accept(ItemRegistry.HEARTWOOD_BOOTS.get());
                        output.accept(ItemRegistry.HEARTWOOD_HORSE_ARMOR.get());
                        output.accept(ItemRegistry.THORNY_HEARTWOOD_SWORD.get());
                        output.accept(ItemRegistry.THORNY_HEARTWOOD_AXE.get());
                        output.accept(ItemRegistry.THORNY_HEARTWOOD_HELMET.get());
                        output.accept(ItemRegistry.THORNY_HEARTWOOD_CHESTPLATE.get());
                        output.accept(ItemRegistry.THORNY_HEARTWOOD_LEGGINGS.get());
                        output.accept(ItemRegistry.THORNY_HEARTWOOD_BOOTS.get());
                        output.accept(ItemRegistry.THORNY_HEARTWOOD_HORSE_ARMOR.get());
                        output.accept(ItemRegistry.IMBUED_HEARTWOOD_SWORD.get());
                        output.accept(ItemRegistry.IMBUED_HEARTWOOD_AXE.get());
                        output.accept(ItemRegistry.IMBUED_HEARTWOOD_HELMET.get());
                        output.accept(ItemRegistry.IMBUED_HEARTWOOD_CHESTPLATE.get());
                        output.accept(ItemRegistry.IMBUED_HEARTWOOD_LEGGINGS.get());
                        output.accept(ItemRegistry.IMBUED_HEARTWOOD_BOOTS.get());
                        output.accept(ItemRegistry.IMBUED_HEARTWOOD_HORSE_ARMOR.get());
                        output.accept(ItemRegistry.BLOWGUN.get());
                        output.accept(ItemRegistry.DART.get());
                        output.accept(ItemRegistry.TIPPED_DART.get());
                        itemDisplayParameters.holders()
                                .lookup(Registries.POTION)
                                .ifPresent((registryLookup) -> generatePotionEffectTypes(
                                        output,
                                        registryLookup,
                                        ItemRegistry.TIPPED_DART.get(),
                                        itemDisplayParameters.enabledFeatures()
                                ));
                    })
                    .title(Component.translatable("creativetab." + Constants.MOD_ID + ".combat"))
                    .build()
    );

    public static final RegistryObject<CreativeModeTab, CreativeModeTab> TOOLS_TAB = CREATIVE_MODE_TABS.register(
            Constants.MOD_ID + "_tools_tab",
            () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .icon(() -> new ItemStack(ItemRegistry.IMBUED_HEARTWOOD_PICKAXE.get()))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ItemRegistry.FRAGILE_FLASK.get());
                        output.accept(ItemRegistry.ALOE_LEAF.get());
                        output.accept(ItemRegistry.ROPE.get());
                        output.accept(ItemRegistry.ROPE_COIL.get());
                        output.accept(ItemRegistry.TOXIC_ASH.get());
                        output.accept(ItemRegistry.BUCKET_OF_TOXIC_ASH.get());
                        output.accept(ItemRegistry.BUCKET_OF_TOXIC_SOLUTION.get());
                        output.accept(ItemRegistry.ROTTEN_COMPOST.get());
                        output.accept(ItemRegistry.RANCID_SLIME.get());
                        output.accept(ItemRegistry.HEARTWOOD_AXE.get());
                        output.accept(ItemRegistry.HEARTWOOD_HOE.get());
                        output.accept(ItemRegistry.HEARTWOOD_PICKAXE.get());
                        output.accept(ItemRegistry.HEARTWOOD_SHOVEL.get());
                        output.accept(ItemRegistry.THORNY_HEARTWOOD_AXE.get());
                        output.accept(ItemRegistry.THORNY_HEARTWOOD_HOE.get());
                        output.accept(ItemRegistry.THORNY_HEARTWOOD_PICKAXE.get());
                        output.accept(ItemRegistry.THORNY_HEARTWOOD_SHOVEL.get());
                        output.accept(ItemRegistry.IMBUED_HEARTWOOD_AXE.get());
                        output.accept(ItemRegistry.IMBUED_HEARTWOOD_HOE.get());
                        output.accept(ItemRegistry.IMBUED_HEARTWOOD_PICKAXE.get());
                        output.accept(ItemRegistry.IMBUED_HEARTWOOD_SHOVEL.get());
                        output.accept(ItemRegistry.ROOTED_SPAWN_EGG.get());
                        output.accept(ItemRegistry.TIMBERMITE_SPAWN_EGG.get());
                        output.accept(ItemRegistry.POISONER_SPAWN_EGG.get());
                    })
                    .title(Component.translatable("creativetab." + Constants.MOD_ID + ".tools"))
                    .build()
    );

    private static void generatePotionEffectTypes(CreativeModeTab.Output output, HolderLookup<Potion> potions, Item item, FeatureFlagSet requiredFeatures) {
        potions.listElements()
                .filter((holder) -> (holder.value().isEnabled(requiredFeatures)))
                .map((holder) -> PotionContents.createItemStack(item, holder))
                .forEach(output::accept);
    }

    public static void init() {
    }

}

