package com.thomas.verdant.entity.custom;

import com.thomas.verdant.entity.ModEntityType;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

public class VerdantZombieEntity extends Zombie {

	public VerdantZombieEntity(EntityType<? extends Zombie> type, Level level) {
		super(type, level);
	}

	public VerdantZombieEntity(Level level) {
		super(ModEntityType.VERDANT_ZOMBIE.get(), level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 35.0D)
				.add(Attributes.MOVEMENT_SPEED, (double) 0.23F).add(Attributes.ATTACK_DAMAGE, 3.0D)
				.add(Attributes.ARMOR, 2.0D).add(Attributes.SPAWN_REINFORCEMENTS_CHANCE);
	}

	protected boolean isSunSensitive() {
		return false;
	}
}
