package com.thomas.verdant.util.baitdata;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BaitDataAccess {

    protected static final Map<Item, BaitData.InnerData> ITEM_MAP = new HashMap<>();
    protected static final Map<TagKey<Item>, BaitData.InnerData> TAG_MAP = new HashMap<>();
    protected static final Map<Item, BaitData.InnerData> MAP = new HashMap<>();


    public static BaitData.InnerData lookupOrCache(RegistryAccess access, Item item) {

        if (MAP.containsKey(item)) {
            return MAP.get(item);
        } else {
            BaitData.InnerData result = lookupItemOrCache(access, item);
            if (result == null) {
                List<BaitData.InnerData> values = new java.util.ArrayList<>(BuiltInRegistries.ITEM.wrapAsHolder(item)
                        .tags()
                        .map(tag -> lookupTagOrCache(access, tag))
                        .filter(Objects::nonNull)
                        .toList());

                if (!values.isEmpty()) {
                    values.sort(BaitData.InnerData.COMPARATOR);
                    result = values.getFirst();
                }
            }
            MAP.put(item, result);

            return result;
        }
    }

    protected static BaitData.InnerData lookupItemOrCache(RegistryAccess access, Item item) {
        if (ITEM_MAP.containsKey(item)) {
            return ITEM_MAP.get(item);
        } else {
            BaitData.InnerData result = null;
            Registry<BaitData> registry = access.lookupOrThrow(BaitData.KEY);
            // Iterate over all values.
            List<BaitData> values = new java.util.ArrayList<>(registry.stream()
                    .filter(data -> data.matches(item))
                    .toList());
            if (!values.isEmpty()) {
                values.sort(BaitData::compare);
                result = values.getFirst().data();
                ITEM_MAP.put(item, result);
            }
            return result;
        }
    }

    protected static BaitData.InnerData lookupTagOrCache(RegistryAccess access, TagKey<Item> tag) {
        if (TAG_MAP.containsKey(tag)) {
            return TAG_MAP.get(tag);
        } else {
            BaitData.InnerData result = null;
            Registry<BaitData> registry = access.lookupOrThrow(BaitData.KEY);

            // Iterate over all values.
            List<BaitData> values = new java.util.ArrayList<>(registry.stream()
                    .filter(data -> data.matches(tag))
                    .toList());

            if (!values.isEmpty()) {
                values.sort(BaitData::compare);
                result = values.getFirst().data();
                TAG_MAP.put(tag, result);
            }

            return result;
        }
    }

}
