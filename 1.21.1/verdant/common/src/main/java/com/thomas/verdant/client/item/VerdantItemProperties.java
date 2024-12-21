package com.thomas.verdant.client.item;

import com.thomas.verdant.Constants;
import net.minecraft.resources.ResourceLocation;

public class VerdantItemProperties {

    public static final ResourceLocation ROPE_LENGTH = location("rope_length");
    public static final ResourceLocation HAS_HOOK = location("has_hook");
    public static final ResourceLocation ROPE_GLOW = location("rope_glow");
    public static final ResourceLocation LANTERN_OPTION = location("lantern_option");

    public static void init() {
    }

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, path);
    }
}
