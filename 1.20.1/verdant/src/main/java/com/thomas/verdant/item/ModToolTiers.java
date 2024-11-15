package com.thomas.verdant.item;

import java.util.List;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.block.ModBlocks;
import com.thomas.verdant.util.ModTags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

public class ModToolTiers {

	public static final Tier VERDANT_HEARTWOOD = TierSortingRegistry.registerTier(
			new ForgeTier(2, 59, 6.0F, 2.0F, 4, ModTags.Blocks.NEEDS_VERDANT_HEARTWOOD_TOOL,
					() -> Ingredient.of(ModBlocks.VERDANT_HEARTWOOD_LOG.get())),
			new ResourceLocation(Verdant.MOD_ID, "verdant_heartwood"),
			List.of(Tiers.WOOD, Tiers.STONE, Tiers.GOLD, Tiers.IRON), List.of(Tiers.DIAMOND, Tiers.NETHERITE));
	public static final Tier IMBUED_VERDANT_HEARTWOOD = TierSortingRegistry.registerTier(
			new ForgeTier(2, 131, 7.0F, 2.0F, 4, ModTags.Blocks.NEEDS_VERDANT_HEARTWOOD_TOOL,
					() -> Ingredient.of(ModBlocks.VERDANT_HEARTWOOD_LOG.get())),
			new ResourceLocation(Verdant.MOD_ID, "imbued_verdant_heartwood"),
			List.of(Tiers.WOOD, Tiers.STONE, Tiers.GOLD, Tiers.IRON), List.of(Tiers.DIAMOND, Tiers.NETHERITE));

}
