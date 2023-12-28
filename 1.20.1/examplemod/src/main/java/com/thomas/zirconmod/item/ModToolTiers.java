package com.thomas.zirconmod.item;

import java.util.List;

import com.thomas.zirconmod.ZirconMod;
import com.thomas.zirconmod.util.ModTags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

public class ModToolTiers {

	public static final Tier ZIRCONIUM = TierSortingRegistry.registerTier(
			new ForgeTier(4, 500, 7.0F, 2.5F, 18, ModTags.Blocks.NEEDS_ZIRCONIUM_TOOL,
					() -> Ingredient.of(ModItems.ZIRCONIUM_INGOT.get())),
			new ResourceLocation(ZirconMod.MOD_ID, "zirconium"), List.of(Tiers.WOOD, Tiers.STONE, Tiers.GOLD, Tiers.IRON),
			List.of(Tiers.DIAMOND, Tiers.NETHERITE));

	public static final Tier COPPER = TierSortingRegistry.registerTier(
			new ForgeTier(1, 160, 5.5F, 1.5F, 17, ModTags.Blocks.NEEDS_COPPER_TOOL,
					() -> Ingredient.of(Items.COPPER_INGOT)),
			new ResourceLocation(ZirconMod.MOD_ID, "copper"), List.of(Tiers.WOOD, Tiers.STONE),
			List.of(Tiers.IRON, Tiers.DIAMOND, Tiers.NETHERITE));
}
