package com.thomas.cloudscape.item.custom;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.thomas.cloudscape.util.ModTags;
import com.thomas.cloudscape.util.MotionHelper;
import com.thomas.cloudscape.util.Utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SpearItem extends TieredItem implements Vanishable {
	private final float attackDamage;
	private final float baseChargeMultiplier;
	private final Multimap<Attribute, AttributeModifier> defaultModifiers;

	public SpearItem(Tier tier, int damage, float useSpeed, Item.Properties properties) {
		super(tier, properties);

		// Override the attack damage.
		this.attackDamage = (float) damage + tier.getAttackDamageBonus() / 2;
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier",
				(double) this.attackDamage, AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier",
				(double) useSpeed, AttributeModifier.Operation.ADDITION));
		this.defaultModifiers = builder.build();
		// Set the base bonus charge multiplier.
		// Based on the damage bonus from the tier, ranked based on netherite, plus a
		// flat bonus.
		this.baseChargeMultiplier = 0.2f + tier.getAttackDamageBonus() / Tiers.NETHERITE.getAttackDamageBonus();
	}

	public float getDamage() {
		return this.attackDamage;
	}

	public float getBaseBonusChargeMultiplier() {
		return this.baseChargeMultiplier;
	}

	public boolean canAttackBlock(BlockState p_43291_, Level p_43292_, BlockPos p_43293_, Player p_43294_) {
		return !p_43294_.isCreative();
	}

	@Override
	public float getDestroySpeed(ItemStack item, BlockState state) {
		return state.is(ModTags.Blocks.SPEAR_EFFICIENT) ? 1.5F : 1.0F;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {

		boolean debug = false;

		// Get the attack damage.
		float attributeDamage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
		float baseBonus = 0;
		float chargeBonus = this.getBaseBonusChargeMultiplier();
		float baseDamage = attributeDamage + this.attackDamage;
		int chargeDamage;

		// Deal extra damage based on relative speed.
		// Get the relative speed.
		// Vertical speed doesn't matter as much.
		Vec3 holderSpeed = player instanceof ServerPlayer ? MotionHelper.getVelocity((ServerPlayer) player)
				: player.getDeltaMovement();
		Vec3 targetSpeed = entity instanceof ServerPlayer ? MotionHelper.getVelocity((ServerPlayer) entity)
				: entity.getDeltaMovement();
		float dv = (float) targetSpeed.subtract(holderSpeed).multiply(1f, 0.5f, 1f).length();

		// Now multiply by 10 to get the integer quantity of the charge damage.
		chargeDamage = (int) (dv * 10);

		// If the holder is crouching and holding relatively still, add damage.
		// Defensive ground.
		// This means that charging someone with a spear is a very, very bad idea.
		// Just like when a boar charges hunters!
		if (player.getDeltaMovement().length() < 0.1 && player.isShiftKeyDown()) {
			chargeBonus += 1;
		}

		// Calculate damage added by enchantments.
		if (entity instanceof LivingEntity le) {
			baseBonus = getEnchantmentAttackBonuses(stack, le.getMobType());
			chargeBonus += getEnchantmentImpaleBonuses(stack, le.getMobType());
		} else {
			baseBonus = getEnchantmentAttackBonuses(stack, null);
			chargeBonus += getEnchantmentImpaleBonuses(stack, null);
		}

		float totalDamage = baseDamage + baseBonus + chargeDamage * chargeBonus;

		float appliedDamage = totalDamage;

		// Scale the total damage by the swing time squared.
		// This makes spam clicking much less effective.
		appliedDamage *= (1 - player.attackAnim) * (1 - player.attackAnim);

		// Decrease attack damage by 75% if the enemy has a shield.
		if (entity instanceof LivingEntity le && le.isBlocking()) {
			appliedDamage *= 0.25;
		}

		// Make sure the applied damage isn't negative.
		appliedDamage = appliedDamage < 0 ? 0 : appliedDamage;

		// Add the damage.
		if (!entity.level().isClientSide()) {
			if (debug) {
				System.out.println("============================================");
				System.out.println("Speed: " + dv);
				System.out.println("Base charge multiplier: " + this.baseChargeMultiplier);
				System.out.println("Charge bonus: " + chargeBonus);
				System.out.println("Base charge damage: " + chargeDamage);
				System.out.println("Total charge damage: " + chargeDamage * chargeBonus);
				System.out.println(player.isShiftKeyDown() ? "Is crouching." : "Is not crouching.");
				System.out.println("Base damage: " + baseDamage);
				System.out.println("Base bonus: " + baseBonus);
				System.out.println("Total damage: " + totalDamage);
				System.out.println("Applied Damage: " + appliedDamage);
				System.out.println("Attack animation time: " + player.attackAnim);
				System.out.println("============================================");
			}
			entity.hurt(player.damageSources().mobAttack(player), appliedDamage);

			// Add particles if enough charge damage was done.
			if (chargeDamage > totalDamage) {
				Utilities.addParticlesAroundEntity((ServerLevel) player.level(), entity, ParticleTypes.CRIT, 1);
			}
		}
		// Damage the spear.
		stack.hurtAndBreak(1 + (chargeDamage / 2), player, (wielder) -> {
			wielder.broadcastBreakEvent(EquipmentSlot.MAINHAND);
		});

		// No further processing is required.
		return true;
	}

	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity player) {
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

	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
		return this.getDefaultAttributeModifiers(slot);
	}

	public float getEnchantmentAttackBonuses(ItemStack stack, @Nullable MobType type) {

		Map<Enchantment, Integer> enchantments = stack.getAllEnchantments();
		float total = 0;
		for (Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
			total += getEnchantmentAttackBonus(stack, entry.getKey(), entry.getValue(), type);
		}
		return total;
	}

	public float getEnchantmentImpaleBonuses(ItemStack stack, @Nullable MobType type) {

		Map<Enchantment, Integer> enchantments = stack.getAllEnchantments();
		float total = 0;
		for (Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
			total += getEnchantmentImpaleBonus(stack, entry.getKey(), entry.getValue(), type);
		}
		return total;
	}

	public float getEnchantmentAttackBonus(ItemStack stack, Enchantment enchantment, int strength, MobType type) {
		float result = enchantment.getDamageBonus(strength, type, stack);

		return result;
	}

	// Multiplies the impaling damage.
	public float getEnchantmentImpaleBonus(ItemStack stack, Enchantment enchantment, int strength, MobType type) {
		float result = 0;

		// On a spear, impaling does more damage to all entities.
		if (enchantment == Enchantments.IMPALING) {
			result += 0.2f * strength;
		}

		return result;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		boolean isValid = super.canApplyAtEnchantingTable(stack, enchantment);

		boolean hasImpaling = stack.getEnchantmentLevel(Enchantments.IMPALING) > 0;
		boolean hasSmite = stack.getEnchantmentLevel(Enchantments.SMITE) > 0;
		boolean hasArthro = stack.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS) > 0;

		if (enchantment == Enchantments.IMPALING && !hasSmite && !hasArthro) {
			isValid = true;
		} else if (enchantment == Enchantments.SMITE && !hasImpaling && !hasArthro) {
			isValid = true;
		} else if (enchantment == Enchantments.BANE_OF_ARTHROPODS && !hasImpaling && !hasSmite) {
			isValid = true;
		} else if (enchantment == Enchantments.MOB_LOOTING) {
			isValid = true;
		}

		return isValid;
	}

}
