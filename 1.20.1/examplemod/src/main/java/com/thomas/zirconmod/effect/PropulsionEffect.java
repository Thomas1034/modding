package com.thomas.zirconmod.effect;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class PropulsionEffect extends MobEffect {

	// Applies a freezing effect to the entity.
	public PropulsionEffect(MobEffectCategory mobEffectCategory, int color) {
		super(mobEffectCategory, color);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		super.applyEffectTick(entity, amplifier);
		if (entity instanceof Player player ) {
			
			if (player instanceof AbstractClientPlayer acp) {
				System.out.println("on client");
				Vec3 curr = acp.getDeltaMovement();
				Vec3 facing = acp.getLookAngle();
				double dot = curr.dot(facing);
				if (dot < (amplifier * amplifier)) {
					acp.addDeltaMovement(facing.scale(amplifier * 0.05));
				}
			}
			else if (player instanceof ServerPlayer sp) {
				System.out.println("on server");
			}
			
			
		}
		else {
			Vec3 curr = entity.getDeltaMovement();
			Vec3 facing = entity.getLookAngle();
			double dot = curr.dot(facing);
			if (dot < (amplifier * amplifier)) {
				entity.addDeltaMovement(facing.scale(amplifier * 0.05));
			}
		}
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}
