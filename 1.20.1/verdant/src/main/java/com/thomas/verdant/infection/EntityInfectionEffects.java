package com.thomas.verdant.infection;

import com.thomas.verdant.util.ModTags;

import net.minecraft.world.entity.LivingEntity;

// Handles updating and applying the infection.
public class EntityInfectionEffects {

	// Make it static
	private EntityInfectionEffects() {
	}

	// Check if the entity is a verdant friend.
	public static boolean isFriend(LivingEntity entity) {
		return entity.getType().is(ModTags.EntityTypes.VERDANT_ENTITIES)
				|| EntityInfection.getLevel(entity) >= EntityInfection.INCREASES;
	}

	public static void doEffects(LivingEntity entity) {

	}

	public static void updateLevel(LivingEntity entity) {
		int level = EntityInfection.getLevel(entity);

		// If that number is greater than the increase level, add one.
		// If that number is less than the decrease level, subtract one.
		if (level >= EntityInfection.INCREASES) {
			EntityInfection.addLevel(entity, 1);
		} else if (level < EntityInfection.DECREASES) {
			EntityInfection.addLevel(entity, -1);
		}
	}

}
