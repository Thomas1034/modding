package com.thomas.verdant.registry;

import com.thomas.verdant.woodset.WoodSet;
import net.minecraft.core.dispenser.BoatDispenseItemBehavior;
import net.minecraft.core.dispenser.ProjectileDispenseBehavior;
import net.minecraft.world.level.block.DispenserBlock;

public class DispenserBehaviors {

    public static void init() {

        DispenserBlock.registerBehavior(
                ItemRegistry.ROPE_COIL.get(),
                new ProjectileDispenseBehavior(ItemRegistry.ROPE_COIL.get())
        );
    }

    public static void woodSet(WoodSet woodSet) {
        DispenserBlock.registerBehavior(
                woodSet.getBoatItem().get(),
                new BoatDispenseItemBehavior(woodSet.getBoat().get())
        );
        DispenserBlock.registerBehavior(
                woodSet.getChestBoatItem().get(),
                new BoatDispenseItemBehavior(woodSet.getChestBoat().get())
        );
    }

}
