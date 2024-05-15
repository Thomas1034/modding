package com.thomas.verdant.effect;

import com.thomas.verdant.Verdant;

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
	
	
	
	public static void register(IEventBus eventBus) {
		MOB_EFFECTS.register(eventBus);
	}
}
