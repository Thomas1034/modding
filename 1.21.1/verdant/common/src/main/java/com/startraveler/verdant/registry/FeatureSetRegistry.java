package com.startraveler.verdant.registry;

import com.startraveler.verdant.Constants;
import net.minecraft.resources.ResourceLocation;

public class FeatureSetRegistry {

    public static final ResourceLocation ABOVE_GROUND = set("above_ground");
    public static final ResourceLocation HANGING = set("hanging");
    public static final ResourceLocation WATER = set("water");
    public static final ResourceLocation ALWAYS = set("always");
    public static final ResourceLocation BELOW_LOG = set("below_log");

    private static ResourceLocation set(String name) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name);
    }

}
