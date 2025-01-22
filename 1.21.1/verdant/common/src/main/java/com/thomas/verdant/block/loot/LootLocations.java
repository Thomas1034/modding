package com.thomas.verdant.block.loot;

import com.thomas.verdant.Constants;
import net.minecraft.resources.ResourceLocation;

public class LootLocations {

    public static final ResourceLocation CASSAVA_ROOTED_DIRT_POP = block("cassava_rooted_dirt_pop");
    public static final ResourceLocation BITTER_CASSAVA_ROOTED_DIRT_POP = block("bitter_cassava_rooted_dirt_pop");

    public static ResourceLocation block(String location) {
        return table("blocks/" + location);
    }

    public static ResourceLocation table(String location) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, location);
    }

}
