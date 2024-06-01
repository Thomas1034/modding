package com.thomas.verdant.client;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientOvergrowthData {
	private static final Map<UUID, Integer> OVERGROWTH_DATA = new HashMap<>();
	
	public static void set(UUID id, int level) {
		OVERGROWTH_DATA.put(id, level);
	}

	public static int getPlayerOvergrowth(UUID id) {
		//System.out.println("Infection levels:");
		//for (Entry<UUID, Integer> entry : INFECTION_LEVELS.entrySet()) {
		//	System.out.println(entry.getKey() + " : " + entry.getValue());
		//}
		//System.out.println("For reference, you are: " + Minecraft.getInstance().player.getUUID() + ", with a level of " + INFECTION_LEVELS.get(id));
		Integer res = OVERGROWTH_DATA.get(id);
		int result = res == null ? 0 : res;
		OVERGROWTH_DATA.put(id, result);
		return result;
	}
}
