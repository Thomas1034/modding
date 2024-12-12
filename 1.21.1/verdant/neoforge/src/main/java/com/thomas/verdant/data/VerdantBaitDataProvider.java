package com.thomas.verdant.data;

import com.thomas.verdant.Constants;
import com.thomas.verdant.util.baitdata.BaitData;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class VerdantBaitDataProvider {

    public static void register(BootstrapContext<BaitData> bootstrap) {
        Constants.LOG.warn("Adding items");
        bootstrap.register(
                key(Items.WHEAT_SEEDS),
                new BaitData(loc(Items.WHEAT_SEEDS), new BaitData.InnerData(0.5f, 0.25f), false));
        bootstrap.register(
                key(Items.BREAD),
                new BaitData(loc(Items.BREAD), new BaitData.InnerData(1.0f, 0.20f), false));
        // add(bootstrap, Items.WHEAT_SEEDS, 0.5f, 0.25f);
    }

    private static void add(BootstrapContext<BaitData> bootstrap, Item item, float catchChance, float consumeChance) {
        bootstrap.register(
                key(item),
                new BaitData(loc(item), new BaitData.InnerData(catchChance, consumeChance), false));
    }

    private static void add(BootstrapContext<BaitData> bootstrap, TagKey<Item> tag, float catchChance, float consumeChance) {
        bootstrap.register(
                key(tag),
                new BaitData(tag.location(), new BaitData.InnerData(catchChance, consumeChance), true));
    }

    private static ResourceKey<BaitData> key(ResourceLocation location) {
        return ResourceKey.create(BaitData.KEY, location);
    }

    private static ResourceKey<BaitData> key(TagKey<Item> tag) {
        return key(tag.location());
    }

    private static ResourceKey<BaitData> key(Item item) {
        return key(loc(item));
    }

    private static ResourceLocation loc(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
}
