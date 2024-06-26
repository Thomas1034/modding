package com.thomas.cloudscape.item.custom;

import com.thomas.cloudscape.effect.ModEffects;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class CitrineArmorItem extends ArmorItem {

	private static final ItemStack MILK = new ItemStack(Items.MILK_BUCKET);
	
	public CitrineArmorItem(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
		super(p_40386_, p_266831_, p_40388_);
	}

	@SuppressWarnings("removal")
	@Override
	public void onArmorTick(ItemStack stack, Level level, Player player) {
		super.onArmorTick(stack, level, player);
		// Cures all effects from the player, and gives amethyst glow.
		player.curePotionEffects(MILK);
		player.addEffect(new MobEffectInstance(ModEffects.CITRINE_GLOW.get(), 10, 10, false, false));
	}

	@Override
	public boolean canBeHurtBy(DamageSource damageSource) {
		return super.canBeHurtBy(damageSource) && !damageSource.is(DamageTypeTags.IS_LIGHTNING)
				&& !damageSource.is(DamageTypeTags.WITCH_RESISTANT_TO);
	}

}
