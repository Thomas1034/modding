package com.thomas.cloudscape.item;

import java.util.function.Supplier;

import com.thomas.cloudscape.Cloudscape;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public enum ModArmorMaterials implements ArmorMaterial {

	ZIRCONIUM("zirconium", 22, new int[] { 3, 5, 6, 2 }, 12, SoundEvents.ARMOR_EQUIP_GOLD, 1.0F, 0.05F,
			() -> Ingredient.of(ModItems.ZIRCONIUM_INGOT.get())),
	COPPER("copper", 15, new int[] { 2, 4, 5, 2 }, 15, SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 0f,
			() -> Ingredient.of(Items.COPPER_INGOT)),
	CITRINE("citrine", 33, new int[] { 3, 6, 8, 3 }, 0, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0f, 0f,
			() -> Ingredient.of(ModItems.CUT_CITRINE.get()));

	private final String name;
	private final int durabilityMultiplier;
	private final int[] protectionAmounts;
	private final int enchantmentValue;
	private final SoundEvent equipSound;
	private final float toughness;
	private final float knockbackResistance;
	private final Supplier<Ingredient> repairIngredient;

	private static final int[] BASE_DURABILITY = { 11, 16, 16, 13 };

	ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue,
			SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
		this.name = name;
		this.durabilityMultiplier = durabilityMultiplier;
		this.protectionAmounts = protectionAmounts;
		this.enchantmentValue = enchantmentValue;
		this.equipSound = equipSound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairIngredient = repairIngredient;
	}

	@Override
	public int getDurabilityForType(ArmorItem.Type type) {
		return BASE_DURABILITY[type.ordinal()] * this.durabilityMultiplier;
	}

	@Override
	public int getDefenseForType(ArmorItem.Type type) {
		return this.protectionAmounts[type.ordinal()];
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	@Override
	public SoundEvent getEquipSound() {
		return this.equipSound;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngredient.get();
	}

	@Override
	public String getName() {
		return Cloudscape.MOD_ID + ":" + this.name;
	}

	@Override
	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}
}
