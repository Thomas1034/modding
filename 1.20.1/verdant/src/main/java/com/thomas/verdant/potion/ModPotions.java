package com.thomas.verdant.potion;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.effect.ModMobEffects;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS,
			Verdant.MOD_ID);

	public static final RegistryObject<Potion> ASPHYXIATING = POTIONS.register("asphyxiating",
			() -> new Potion(new MobEffectInstance(ModMobEffects.ASPHYXIATING.get(), 3600, 0)));
	public static final RegistryObject<Potion> LONG_ASPHYXIATING = POTIONS.register("long_asphyxiating",
			() -> new Potion(new MobEffectInstance(ModMobEffects.ASPHYXIATING.get(), 9600, 0)));
	public static final RegistryObject<Potion> STRONG_ASPHYXIATING = POTIONS.register("strong_asphyxiating",
			() -> new Potion(new MobEffectInstance(ModMobEffects.ASPHYXIATING.get(), 1800, 1)));
	public static final RegistryObject<Potion> CAFFEINE = POTIONS.register("caffeine",
			() -> new Potion(new MobEffectInstance(ModMobEffects.CAFFEINATED.get(), 3600, 0)));
	public static final RegistryObject<Potion> LONG_CAFFEINE = POTIONS.register("long_caffeine",
			() -> new Potion(new MobEffectInstance(ModMobEffects.CAFFEINATED.get(), 9600, 0)));
	public static final RegistryObject<Potion> STRONG_CAFFEINE = POTIONS.register("strong_caffeine",
			() -> new Potion(new MobEffectInstance(ModMobEffects.CAFFEINATED.get(), 1800, 1)));
	public static final RegistryObject<Potion> COLLOID = POTIONS.register("colloid",
			() -> new Potion(new MobEffectInstance(ModMobEffects.COLLOID.get(), 3600, 0)));
	public static final RegistryObject<Potion> LONG_COLLOID = POTIONS.register("long_colloid",
			() -> new Potion(new MobEffectInstance(ModMobEffects.COLLOID.get(), 9600, 0)));
	public static final RegistryObject<Potion> STRONG_COLLOID = POTIONS.register("strong_colloid",
			() -> new Potion(new MobEffectInstance(ModMobEffects.COLLOID.get(), 1800, 1)));
	public static final RegistryObject<Potion> ANTIDOTE = POTIONS.register("antidote",
			() -> new Potion(new MobEffectInstance(ModMobEffects.ANTIDOTE.get(), 1800, 0)));
	public static final RegistryObject<Potion> LONG_ANTIDOTE = POTIONS.register("long_antidote",
			() -> new Potion(new MobEffectInstance(ModMobEffects.ANTIDOTE.get(), 4800, 0)));

	public static void register(IEventBus eventBus) {
		POTIONS.register(eventBus);
	}
}