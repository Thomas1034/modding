package com.thomas.verdant.overgrowth;

import com.thomas.verdant.util.ModTags;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;

// Handles updating and applying the overgrowth.
public class EntityOvergrowthEffects {

	// Make it static
	private EntityOvergrowthEffects() {
	}

	// Check if the entity is a verdant friend.
	public static boolean isFriend(LivingEntity entity) {

		return entity.getType().is(ModTags.EntityTypes.VERDANT_ENTITIES)
				|| EntityOvergrowth.getLevel(entity) >= EntityOvergrowth.FRIEND;
	}

	// Check if the entity is not a verdant friend.
	public static boolean isEnemy(LivingEntity entity) {

		return !isFriend(entity);
	}

	public static void doEffects(LivingEntity entity) {

	}

	public static void updateLevel(LivingEntity entity) {

		// Don't increase in creative
		if (entity instanceof ServerPlayer serverPlayer && serverPlayer.getAbilities().instabuild) {
			return;
		}

		int level = EntityOvergrowth.getLevel(entity);

		// Find the progression amount of the overgrowth
		int stage = ((level - EntityOvergrowth.MIN_LEVEL) * EntityOvergrowth.STAGES)
				/ (EntityOvergrowth.MAX_LEVEL - EntityOvergrowth.MIN_LEVEL);

		// Get the growth amount for the overgrowth.
		int toAdd = OvergrowthProgression.getStage(entity.level(), stage);

		// Update
		EntityOvergrowth.addLevel(entity, toAdd);

	}

}
