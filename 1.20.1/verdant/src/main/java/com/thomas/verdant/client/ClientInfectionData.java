package com.thomas.verdant.client;

public class ClientInfectionData {
    private static int playerInfection;

    public static void set(int thirst) {
        ClientInfectionData.playerInfection = thirst;
    }

    public static int getPlayerInfection() {
        return playerInfection;
    }
}
