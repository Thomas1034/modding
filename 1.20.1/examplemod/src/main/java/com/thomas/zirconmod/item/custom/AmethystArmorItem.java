package com.thomas.zirconmod.item.custom;

import com.thomas.zirconmod.effect.ModEffects;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class AmethystArmorItem extends ArmorItem {

	public AmethystArmorItem(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
		super(p_40386_, p_266831_, p_40388_);
	}

	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player) {
		super.onArmorTick(stack, level, player);
		// Removes all effects from the player that can be removed by drinking milk, and gives amethyst glow.
		player.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
		player.addEffect(new MobEffectInstance(ModEffects.CITRINE_GLOW.get(), 10, 0, false, false));
	}

	@Override
	public boolean canBeHurtBy(DamageSource damageSource) {
		return super.canBeHurtBy(damageSource) && !damageSource.is(DamageTypeTags.IS_LIGHTNING)
				&& !damageSource.is(DamageTypeTags.WITCH_RESISTANT_TO);
	}

	// Amethysts can't be enchanted.
	@Override
	public boolean isEnchantable(ItemStack p_41456_) {
		return false;
	}

}
