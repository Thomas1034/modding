package com.thomas.verdant.effect.custom;

import java.util.List;
import java.util.stream.Collectors;

import com.thomas.verdant.entity.custom.HallucinatedCreeperEntity;
import com.thomas.verdant.entity.custom.Hallucination;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class HallucinatingEffect extends MobEffect {

	public HallucinatingEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {

		// Summon up to n**2 hallucinated entities.
		if (!(entity instanceof Player player)) {
			return;
		}

		if (entity.tickCount % 4 == 0 && entity.level() instanceof ServerLevel level) {
			HallucinatedCreeperEntity hal = new HallucinatedCreeperEntity(level, player);
			// Get a spawn position.
			Vec3 spawnPos = entity.position().offsetRandom(level.random, hal.getDespawnDistance());
			hal.setPos(spawnPos);

			List<Entity> entities = level.getEntities(entity,
					entity.getBoundingBox().inflate(hal.getDespawnDistance()));
			entities = entities.stream().filter(e -> e instanceof Hallucination).collect(Collectors.toList());

			if (level.noCollision(hal.getBoundingBox()) && entities != null
					&& entities.size() < (1 + amplifier) * (1 + amplifier)) {
				level.addFreshEntity(hal);
			}
		}
	}

	@Override
	public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
		return true;
	}
}
