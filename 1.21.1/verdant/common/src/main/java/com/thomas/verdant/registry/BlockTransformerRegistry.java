package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import net.minecraft.resources.ResourceLocation;

public class BlockTransformerRegistry {

    public static final ResourceLocation EROSION = transformer("erosion");
    public static final ResourceLocation EROSION_WET = transformer("erosion_wet");
    public static final ResourceLocation HOEING = transformer("hoeing");
    public static final ResourceLocation VERDANT_ROOTS = transformer("verdant_roots");

    private static ResourceLocation transformer(String name) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name);
    }


}
