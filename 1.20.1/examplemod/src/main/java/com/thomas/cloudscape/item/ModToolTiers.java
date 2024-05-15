package com.thomas.cloudscape.item;

import java.util.List;

import com.thomas.cloudscape.Cloudscape;
import com.thomas.cloudscape.util.ModTags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

public class ModToolTiers {

	public static final Tier VERDANT_HEARTWOOD = TierSortingRegistry.registerTier(
			new ForgeTier(2, 131, 6.0F, 1.5F, 8, ModTags.Blocks.NEEDS_ZIRCONIUM_TOOL,
					() -> Ingredient.of(ModItems.ZIRCONIUM_INGOT.get())),
			new ResourceLocation(Cloudscape.MOD_ID, "zirconium"),
			List.of(Tiers.WOOD, Tiers.STONE, Tiers.GOLD, Tiers.IRON), List.of(Tiers.DIAMOND, Tiers.NETHERITE));

}
