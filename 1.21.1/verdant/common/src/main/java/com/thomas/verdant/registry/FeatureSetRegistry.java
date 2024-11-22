package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import net.minecraft.resources.ResourceLocation;

public class FeatureSetRegistry {

    public static final ResourceLocation ABOVE_GROUND = set("above_ground");
    public static final ResourceLocation HANGING = set("hanging");
    public static final ResourceLocation WATER = set("water");

    private static ResourceLocation set(String name) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name);
    }


}
