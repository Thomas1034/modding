package com.thomas.cloudscape.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum WoodGolemVariant {

	OAK(0), SPRUCE(1), BIRCH(2), JUNGLE(3), ACACIA(4), DARK_OAK(5), MANGROVE(6), PALM(7), CHERRY(8);

	private static final WoodGolemVariant[] BY_ID = Arrays.stream(values())
			.sorted(Comparator.comparingInt(WoodGolemVariant::getId)).toArray(WoodGolemVariant[]::new);
	public final int id;

	WoodGolemVariant(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static WoodGolemVariant byId(int id) {
		return BY_ID[id % BY_ID.length];
	}

}
