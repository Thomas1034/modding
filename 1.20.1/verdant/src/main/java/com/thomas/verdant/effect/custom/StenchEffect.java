package com.thomas.verdant.effect.custom;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingVisibilityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class StenchEffect extends MobEffect {

	private static final List<MobEffect> INSTANCES = new ArrayList<>();

	public StenchEffect(MobEffectCategory category, int color) {
		super(category, color);
		// Register for events, to control invisibility.
		MinecraftForge.EVENT_BUS.register(this);
		INSTANCES.add(this);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		// Doesn't do anything here, actually.
	}

	@SubscribeEvent
	public void reduceVisibility(LivingVisibilityEvent event) {
		LivingEntity entity = event.getEntity();
		int stenchLevel = 0;
		for (MobEffect stenchEffect : INSTANCES) {
			MobEffectInstance instance = entity.getEffect(stenchEffect);
			if (instance != null) {
				stenchLevel += instance.getAmplifier() + 1;
			}
		}
		double stenchMultiplier = 1.0/(1.0+stenchLevel);
		
		event.modifyVisibility(stenchMultiplier);
	}

	@Override
	public boolean isDurationEffectTick(int i, int j) {
		return true;
	}
}
