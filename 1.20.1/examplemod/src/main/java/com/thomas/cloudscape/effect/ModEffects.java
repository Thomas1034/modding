package com.thomas.cloudscape.effect;

import com.thomas.cloudscape.Cloudscape;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// Citrine: Amethyst +100 warmth, +100 warmth -100 tint, 

public class ModEffects {
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS,
			Cloudscape.MOD_ID);

	public static final RegistryObject<MobEffect> CITRINE_GLOW = MOB_EFFECTS.register("citrine_glow",
			() -> new CitrineGlowEffect(MobEffectCategory.NEUTRAL, 0xc48ef3));
	public static final RegistryObject<MobEffect> FLIGHT_EXHAUSTION = MOB_EFFECTS.register("flight_exhaustion",
			() -> new FlightExhaustionEffect(MobEffectCategory.HARMFUL, 0xcccccc));
	public static final RegistryObject<MobEffect> FREEZING = MOB_EFFECTS.register("freezing",
			() -> new FreezeEffect(MobEffectCategory.HARMFUL, 0xffffff));
	public static final RegistryObject<MobEffect> PROPELLED = MOB_EFFECTS.register("propelled",
			() -> new PropulsionEffect(MobEffectCategory.BENEFICIAL, 0x4f4f4f));


	public static void register(IEventBus eventBus) {
		MOB_EFFECTS.register(eventBus);
	}
}
