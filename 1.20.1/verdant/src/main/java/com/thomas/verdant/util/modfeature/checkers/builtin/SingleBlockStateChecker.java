package com.thomas.verdant.util.modfeature.checkers.builtin;

import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.thomas.verdant.util.modfeature.checkers.AbstractCheckerType;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class SingleBlockStateChecker extends SingleBlockChecker {

	private Map<Property<?>, Object> properties;

	public SingleBlockStateChecker() {

	}

	@Override
	public AbstractCheckerType parse(Gson gson, JsonElement parameters) {
		super.parse(gson, parameters);
		this.properties = AbstractCheckerType.getBlockState(parameters, this.getBlock());
		return this;
	}

	@Override
	public boolean check(Level level, BlockPos pos) {

		// Check if the block matches.
		if (!super.check(level, pos)) {
			return false;
		}

		// Move to the offset.
		pos = pos.offset(offset[0], offset[1], offset[2]);
		// Get the state to check.
		BlockState stateToCheck = level.getBlockState(pos);
		// Now, check if all the properties are valid.
		for (Entry<Property<?>, Object> entry : this.properties.entrySet()) {
			// For each property, get its value and check if it equals the required one.
			if (!stateToCheck.getValue(entry.getKey()).equals(entry.getValue())) {
				return false;
			}
		}
		return true;
	}
}
