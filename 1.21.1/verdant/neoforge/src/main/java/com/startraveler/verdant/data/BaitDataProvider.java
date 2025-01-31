package com.startraveler.verdant.data;

import com.startraveler.verdant.registry.ItemRegistry;
import com.startraveler.verdant.util.baitdata.BaitData;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class BaitDataProvider {

    public static void register(BootstrapContext<BaitData> bootstrap) {
        /*
         * Idea from womp6 on the Fabric Discord
         * who makes https://www.curseforge.com/minecraft/mc-mods/womps-shellfish-mod
         * Make chorus fruit have a chance to remove an already-caught item.
         */
        add(bootstrap, Items.CHORUS_FRUIT, 0.025, 0.005);
        add(bootstrap, Items.MELON_SEEDS, 0.05, 0.5);
        add(bootstrap, Items.SUGAR_CANE, 0.05, 0.1);
        add(bootstrap, Items.WHEAT_SEEDS, 0.1, 0.5);
        add(bootstrap, Items.BEETROOT_SEEDS, 0.15, 0.5);
        add(bootstrap, Items.ROTTEN_FLESH, 0.2, 0.75);
        add(bootstrap, Items.BROWN_MUSHROOM, 0.2, 0.5);
        add(bootstrap, Items.SWEET_BERRIES, 0.2, 0.5);
        add(bootstrap, Items.RED_MUSHROOM, 0.2, 0.5);
        add(bootstrap, Items.GLOW_BERRIES, 0.25, 0.5);
        // add(bootstrap, ItemRegistry.COFFEE_BERRIES.get(), 0.25, 0.5);
        add(bootstrap, Items.SUGAR, 0.4, 0.8);
        // add(bootstrap, ItemRegistry.STARCH.get(), 0.4, 0.8);
        add(bootstrap, Items.WHEAT, 0.5, 0.5);
        add(bootstrap, ItemRegistry.COFFEE_BERRIES.get(), 0.5, 0.5);
        add(bootstrap, Items.COD, 0.5, 0.5);
        add(bootstrap, Items.RABBIT, 0.5, 0.3);
        add(bootstrap, Items.SALMON, 0.5, 0.45);
        // add(bootstrap, ItemRegistry.BAKED_UBE.get(), 0.5, 0.25);
        add(bootstrap, Items.CHICKEN, 0.55, 0.3);
        add(bootstrap, Items.MUTTON, 0.55, 0.3);
        add(bootstrap, Items.BEEF, 0.55, 0.3);
        add(bootstrap, Items.PORKCHOP, 0.6, 0.3);
        add(bootstrap, Items.COOKIE, 0.6, 0.8);
        // add(bootstrap, ItemRegistry.UBE_COOKIE.get(), 0.6, 0.8);
        add(bootstrap, Items.BREAD, 0.6, 0.3);
        add(bootstrap, Items.ENDER_PEARL, 0.6, 0.1);
        add(bootstrap, ItemRegistry.ROASTED_COFFEE.get(), 0.8, 0.4);
        add(bootstrap, Items.PUMPKIN_PIE, 0.8, 0.2);
        add(bootstrap, Items.SPIDER_EYE, 0.8, 0.8);
        add(bootstrap, Items.COOKED_RABBIT, 0.8, 0.2);
        add(bootstrap, Items.COOKED_CHICKEN, 0.85, 0.2);
        add(bootstrap, Items.COOKED_MUTTON, 0.85, 0.2);
        add(bootstrap, Items.COOKED_PORKCHOP, 0.9, 0.2);
        add(bootstrap, Items.COOKED_BEEF, 0.85, 0.2);
        // add(bootstrap, ItemRegistry.UBE_CAKE.get(), 0.9, 0.2);
        add(bootstrap, Items.CAKE, 0.9, 0.2);
        add(bootstrap, Items.ENDER_EYE, 0.9, 0.05);
        // add(bootstrap, ItemRegistry.SPARKLING_STARCH.get(), 1.2, 0.3);
        // add(bootstrap, ItemRegistry.COOKED_GOLDEN_CASSAVA.get(), 1.2, 0.05);
        add(bootstrap, Items.GOLDEN_CARROT, 1.5, 0.2);
        add(bootstrap, Items.FERMENTED_SPIDER_EYE, 2.5, 0.5);

    }

    public static void add(BootstrapContext<BaitData> bootstrap, Item item, double catchChance, double consumeChance) {
        bootstrap.register(key(item), bait(item, (float) catchChance, (float) consumeChance));
    }

    public static BaitData bait(Item item, float catchChance, float consumeChance) {
        return new BaitData(
                BuiltInRegistries.ITEM.getKey(item),
                new BaitData.InnerData(catchChance, consumeChance),
                false
        );
    }

    public static ResourceKey<BaitData> key(ResourceLocation location) {
        return ResourceKey.create(BaitData.KEY, location);
    }

    public static ResourceKey<BaitData> key(Item item) {
        return ResourceKey.create(BaitData.KEY, BuiltInRegistries.ITEM.getKey(item).withSuffix("_data"));
    }


}
