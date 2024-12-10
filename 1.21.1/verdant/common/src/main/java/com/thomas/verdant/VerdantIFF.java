package com.thomas.verdant;

import com.thomas.verdant.util.VerdantTags;
import net.minecraft.world.entity.Entity;

public class VerdantIFF {

    public static boolean isFriend(Entity entity) {
        if (entity.getType().is(VerdantTags.EntityTypes.VERDANT_FRIENDLY_ENTITIES)) {
            return true;
        } else {
            // Dummy for the compiler.
            boolean b;
        }

        return false;
    }

}
