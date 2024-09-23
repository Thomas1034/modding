package com.thomas.turbulent.effect;

import com.thomas.turbulent.Turbulent;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModMobEffects {
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS,
			Turbulent.MOD_ID);

	public static void register(IEventBus eventBus) {
		MOB_EFFECTS.register(eventBus);
	}
}
