package com.thomas.cloudscape.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.Level;

public class WraithEntity extends Phantom {

	public WraithEntity(EntityType<? extends Phantom> template, Level level) {
		super(template, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.ATTACK_DAMAGE, 2.0D);
	}

	// Fireproof
	@Override
	public boolean fireImmune() {
		return true;
	}
	
	// Max spawn light level
	public static int getMaxLightLevel() {
		return 3;
	}
}
