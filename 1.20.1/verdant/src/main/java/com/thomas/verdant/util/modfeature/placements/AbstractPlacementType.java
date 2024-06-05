package com.thomas.verdant.util.modfeature.placements;

import com.thomas.verdant.util.data.DataParseable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public abstract class AbstractPlacementType implements DataParseable<AbstractPlacementType> {

	public abstract BlockPos get(Level level, BlockPos pos);

}
