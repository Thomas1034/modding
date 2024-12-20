package com.thomas.verdant.effect;

import java.util.List;

import com.thomas.verdant.Verdant;
import com.thomas.verdant.effect.custom.BoneMealEffect;
import com.thomas.verdant.effect.custom.CaffeinatedEffect;
import com.thomas.verdant.effect.custom.CassavaPoisoningEffect;
import com.thomas.verdant.effect.custom.ChokingEffect;
import com.thomas.verdant.effect.custom.ColloidEffect;
import com.thomas.verdant.effect.custom.FoodPoisoningEffect;
import com.thomas.verdant.effect.custom.GenericEffect;
import com.thomas.verdant.effect.custom.HallucinatingEffect;
import com.thomas.verdant.effect.custom.ImmunityEffect;
import com.thomas.verdant.effect.custom.StenchEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
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
	public static final RegistryObject<MobEffect> CASSAVA_POISONING = MOB_EFFECTS.register("cassava_poisoning",
			() -> new CassavaPoisoningEffect(MobEffectCategory.HARMFUL, 0x4B5320));
	public static final RegistryObject<MobEffect> COLLOID = MOB_EFFECTS.register("colloid",
			() -> new ColloidEffect(MobEffectCategory.HARMFUL, 0xf7ebc8));
	public static final RegistryObject<MobEffect> TRAPPED = MOB_EFFECTS.register("trapped",
			() -> new GenericEffect(MobEffectCategory.HARMFUL, 0x000000));
	public static final RegistryObject<MobEffect> ANTIDOTE = MOB_EFFECTS.register("antidote",
			() -> new ImmunityEffect(MobEffectCategory.BENEFICIAL, 0x3dffb5,
					List.of(MobEffects.CONFUSION, MobEffects.POISON, MobEffects.WITHER)));
	public static final RegistryObject<MobEffect> ASPHYXIATING = MOB_EFFECTS.register("asphyxiating",
			() -> new ChokingEffect(MobEffectCategory.HARMFUL, 0x0094ff));
	public static final RegistryObject<MobEffect> HALLUCINATING = MOB_EFFECTS.register("hallucinating",
			() -> new HallucinatingEffect(MobEffectCategory.HARMFUL, 0xFFFFFF));
	public static final RegistryObject<MobEffect> STENCH = MOB_EFFECTS.register("stench",
			() -> new StenchEffect(MobEffectCategory.BENEFICIAL, 0xAAE2B5));

	public static void register(IEventBus eventBus) {
		MOB_EFFECTS.register(eventBus);
	}
}
