package com.thomas.zirconmod.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.thomas.zirconmod.util.ModTags;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SpearItem extends TieredItem implements Vanishable {
	private final float attackDamage;
	private final Multimap<Attribute, AttributeModifier> defaultModifiers;

	public SpearItem(Tier tier, int damage, float useSpeed, Item.Properties properties) {
		super(tier, properties);
		this.attackDamage = (float) damage + tier.getAttackDamageBonus();
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier",
				(double) this.attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier",
				(double) useSpeed, AttributeModifier.Operation.ADDITION));
		this.defaultModifiers = builder.build();
	}

	public float getDamage() {
		return this.attackDamage;
	}

	public boolean canAttackBlock(BlockState p_43291_, Level p_43292_, BlockPos p_43293_, Player p_43294_) {
		return !p_43294_.isCreative();
	}

	@Override
	public float getDestroySpeed(ItemStack item, BlockState state) {
		return state.is(ModTags.Blocks.SPEAR_EFFICIENT) ? 1.5F : 1.0F;
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity holder) {
		// Deal extra damage based on relative speed.
		
		// Get the relative speed.
		// Vertical speed doesn't matter as much.
		double dv = target.getDeltaMovement().subtract(holder.getDeltaMovement()).multiply(1f, 0.5f, 1f).length();
		
		// Now multiply by 8 to get the integer quantity.
		int chargeDamage = (int) dv * 8;

		// If the holder is crouching and holding relatively still, add damage.
		// Defensive ground.
		// This means that charging someone with a spear is a very, very bad idea.
		// Just like when a boar fights hunters!
		if (holder.getDeltaMovement().length() < 0.1 && holder.isShiftKeyDown()) {
			chargeDamage += 4;
		}
		
		// Add the damage.
		
		System.out.println("Invulnerable time: " + target.invulnerableTime);
		System.out.println("Damage: " + chargeDamage);
		target.invulnerableTime = 0;
		target.hurt(holder.damageSources().mobAttack(holder), chargeDamage);
		
		stack.hurtAndBreak(1 + chargeDamage, holder, (wielder) -> {
			wielder.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		});
		return true;
	}

	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos,
			LivingEntity player) {
		if (state.getDestroySpeed(level, pos) != 0.0F) {
			stack.hurtAndBreak(2, player, (p_43276_) -> {
				p_43276_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
			});
		}

		return true;
	}

	@Override
	public boolean isCorrectToolForDrops(BlockState p_43298_) {
		return p_43298_.is(ModTags.Blocks.SPEAR_EFFICIENT);
	}

	@Override
	@SuppressWarnings("deprecation")
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot p_43274_) {
		return p_43274_ == EquipmentSlot.MAINHAND ? this.defaultModifiers
				: super.getDefaultAttributeModifiers(p_43274_);
	}

}
