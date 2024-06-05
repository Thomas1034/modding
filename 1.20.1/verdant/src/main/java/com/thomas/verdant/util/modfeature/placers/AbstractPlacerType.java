package com.thomas.verdant.util.modfeature.placers;

import com.thomas.verdant.util.data.DataParseable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public abstract class AbstractPlacerType implements DataParseable<AbstractPlacerType> {

	public abstract void place(Level level, BlockPos pos);

}