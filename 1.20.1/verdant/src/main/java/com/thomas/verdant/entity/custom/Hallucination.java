package com.thomas.verdant.entity.custom;

public interface Hallucination {

	default public int getDespawnDistance() {
		return 32;
	}
	
	default public int getImmunityTicks() {
		return 5*20;
	}
}
