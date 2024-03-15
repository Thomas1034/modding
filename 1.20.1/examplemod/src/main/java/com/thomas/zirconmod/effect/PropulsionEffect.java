package com.thomas.zirconmod.effect;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class PropulsionEffect extends MobEffect {

	// Applies a freezing effect to the entity.
	public PropulsionEffect(MobEffectCategory mobEffectCategory, int color) {
		super(mobEffectCategory, color);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		super.applyEffectTick(entity, amplifier);
		if (entity instanceof Player player) {

			if (player instanceof AbstractClientPlayer acp) {
				Vec3 curr = acp.getDeltaMovement();
				Vec3 facing = acp.getLookAngle();
				double dot = curr.dot(facing);
				if (dot < (amplifier * amplifier) / 25.0) {
					acp.addDeltaMovement(facing.scale(amplifier * 0.05));
				}
			} else if (player instanceof ServerPlayer sp) {
			}

		} else {
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

	// This effect cannot be cured.
	@Override
	public List<ItemStack> getCurativeItems() {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		return ret;
	}
}
