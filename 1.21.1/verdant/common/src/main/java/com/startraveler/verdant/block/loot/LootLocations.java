package com.startraveler.verdant.block.loot;

import com.startraveler.verdant.Constants;
import net.minecraft.resources.ResourceLocation;

public class LootLocations {

    public static final ResourceLocation CASSAVA_ROOTED_DIRT_POP = block("cassava_rooted_dirt_pop");
    public static final ResourceLocation BITTER_CASSAVA_ROOTED_DIRT_POP = block("bitter_cassava_rooted_dirt_pop");
    public static final ResourceLocation DIRT_COAL_ORE_POP = block("dirt_coal_ore_pop");
    public static final ResourceLocation DIRT_COPPER_ORE_POP = block("dirt_copper_ore_pop");
    public static final ResourceLocation DIRT_IRON_ORE_POP = block("dirt_iron_ore_pop");
    public static final ResourceLocation DIRT_GOLD_ORE_POP = block("dirt_gold_ore_pop");
    public static final ResourceLocation DIRT_LAPIS_ORE_POP = block("dirt_lapis_ore_pop");
    public static final ResourceLocation DIRT_REDSTONE_ORE_POP = block("dirt_redstone_ore_pop");
    public static final ResourceLocation DIRT_EMERALD_ORE_POP = block("dirt_emerald_ore_pop");
    public static final ResourceLocation DIRT_DIAMOND_ORE_POP = block("dirt_diamond_ore_pop");

    public static ResourceLocation block(String location) {
        return table("blocks/" + location);
    }

    public static ResourceLocation table(String location) {
        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, location);
    }

}
