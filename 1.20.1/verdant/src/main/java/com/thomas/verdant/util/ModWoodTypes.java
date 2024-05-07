package com.thomas.verdant.util;

import com.thomas.verdant.Verdant;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodTypes {
    public static final WoodType DEMO = WoodType.register(new WoodType(Verdant.MOD_ID + ":demo", BlockSetType.OAK));
}
