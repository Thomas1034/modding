package com.thomas.verdant.effect;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.effect.custom.CaffeinatedEffect;
import com.thomas.verdant.effect.custom.BoneMealEffect;
import com.thomas.verdant.effect.custom.FoodPoisoningEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMobEffects {
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS,
			Verdant.MOD_ID);


	public static final RegistryObject<MobEffect> FOOD_POISONING = MOB_EFFECTS.register("food_poisoning",
			() -> new FoodPoisoningEffect(MobEffectCategory.HARMFUL, 0x94ac02));
	public static final RegistryObject<MobEffect> CAFFEINATED = MOB_EFFECTS.register("caffeinated",
			() -> new CaffeinatedEffect(MobEffectCategory.NEUTRAL, 0x5c4033));
	public static final RegistryObject<MobEffect> VERDANT_ENERGY = MOB_EFFECTS.register("verdant_energy",
			() -> new BoneMealEffect(MobEffectCategory.BENEFICIAL, 0x2a7a1c));
	
	
	
	public static void register(IEventBus eventBus) {
		MOB_EFFECTS.register(eventBus);
	}
}
