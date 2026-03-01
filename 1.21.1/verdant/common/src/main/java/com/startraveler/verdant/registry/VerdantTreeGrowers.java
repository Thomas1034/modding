package com.startraveler.verdant.registry;

import com.startraveler.verdant.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class VerdantTreeGrowers {

    public static final TreeGrower MANGO = new TreeGrower(
            "mango", Optional.empty(), Optional.of(ResourceKey.create(
            Registries.CONFIGURED_FEATURE, Identifier.fromNamespaceAndPath(
                    Constants.MOD_ID, "mango_tree")
    )), Optional.empty()
    );
}
