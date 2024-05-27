package com.thomas.verdant.client;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientInfectionData {
	private static final Map<UUID, Integer> INFECTION_LEVELS = new HashMap<>();
	
	public static void set(UUID id, int level) {
		INFECTION_LEVELS.put(id, level);
	}

	public static int getPlayerInfection(UUID id) {
		//System.out.println("Infection levels:");
		//for (Entry<UUID, Integer> entry : INFECTION_LEVELS.entrySet()) {
		//	System.out.println(entry.getKey() + " : " + entry.getValue());
		//}
		//System.out.println("For reference, you are: " + Minecraft.getInstance().player.getUUID() + ", with a level of " + INFECTION_LEVELS.get(id));
		return INFECTION_LEVELS.get(id);
	}
}
