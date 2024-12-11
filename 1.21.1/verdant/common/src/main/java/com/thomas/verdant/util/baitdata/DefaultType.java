package com.thomas.verdant.util.baitdata;

import com.thomas.verdant.Constants;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum DefaultType implements Type {

    ITEM("item"), ITEM_TAG("item_tag");

    static {
        Type.MAP.putAll(Arrays.stream(DefaultType.values()).collect(Collectors.toMap(DefaultType::location, Function.identity())));
    }

    final ResourceLocation location;

    DefaultType(String path) {
        this(Constants.MOD_ID, path);
    }

    DefaultType(String modid, String path) {
        this.location = ResourceLocation.fromNamespaceAndPath(modid, path);
    }

    @Override
    public ResourceLocation location() {
        return this.location;
    }
}
