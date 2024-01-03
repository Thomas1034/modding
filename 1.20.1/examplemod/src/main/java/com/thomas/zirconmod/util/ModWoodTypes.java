package com.thomas.zirconmod.util;

import com.thomas.zirconmod.ZirconMod;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodTypes {
    public static final WoodType PALM = WoodType.register(new WoodType(ZirconMod.MOD_ID + ":palm", BlockSetType.OAK));
}
