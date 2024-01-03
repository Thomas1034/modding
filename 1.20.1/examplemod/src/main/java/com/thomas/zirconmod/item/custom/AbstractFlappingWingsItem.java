package com.thomas.zirconmod.item.custom;

import java.util.Collection;

import com.thomas.zirconmod.effect.FlightExhaustionEffect;
import com.thomas.zirconmod.effect.ModEffects;
import com.thomas.zirconmod.util.Utilities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractFlappingWingsItem extends AbstractWingsItem {
	
	private float lift;
	private float push;
	private float exhaustion;
	
	// Lift is the amount added to the vertical velocity.
	// Push is the amount added to the lateral velocity.
	// Exhaustion is the number of seconds between flaps.
	public AbstractFlappingWingsItem(Properties properties, float lift, float push, float exhaustion) {
		super(properties);
		this.lift = lift;
		this.push = push;
		this.exhaustion = exhaustion;
	}
	
	@Override
	public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
		super.elytraFlightTick(stack, entity, flightTicks);

		// Check if the entity is trying to flap and can.
		if (entity.level() instanceof ServerLevel sl && entity.isShiftKeyDown() && canFlap(entity)) {
			// Boosts the entity's vertical and lateral velocity.
			Vec3 velocity = entity.getDeltaMovement();
			velocity = velocity.add(entity.getLookAngle().normalize().multiply(this.push, 0, this.push).add(0,  this.lift,  0));
			entity.setDeltaMovement(velocity);
			// Applies flight exhaustion.
			entity.addEffect(new MobEffectInstance(ModEffects.FLIGHT_EXHAUSTION.get(), (int)(this.exhaustion * 20), 0, false, false));
			// Adds flapping particles.
			Utilities.addParticlesAroundEntity(sl, entity, ParticleTypes.CLOUD, 3.0);
			Utilities.addParticlesAroundEntity(sl, entity, ParticleTypes.CLOUD, 2.0);
			Utilities.addParticlesAroundEntity(sl, entity, ParticleTypes.CLOUD, 3.0);
			Utilities.addParticlesAroundEntity(sl, entity, ParticleTypes.CLOUD, 2.0);
			
			// Wears down the wings.
			decreaseDurability(stack, entity);
			// Plays a sound.
			sl.playSound(null, entity.blockPosition(), SoundEvents.ENDER_DRAGON_FLAP, SoundSource.PLAYERS, 1.0f, 1.0f);

		}
		return true;
	}

	private boolean canFlap(LivingEntity entity) {
		Collection<MobEffectInstance> effects = entity.getActiveEffects();
		for (MobEffectInstance instance : effects) {
			if (instance.getEffect() instanceof FlightExhaustionEffect effect) {
				return false;
			}
		}
		return true;
	}
}
