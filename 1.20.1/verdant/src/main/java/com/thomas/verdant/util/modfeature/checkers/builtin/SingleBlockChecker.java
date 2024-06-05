package com.thomas.verdant.util.modfeature.checkers.builtin;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.thomas.verdant.util.modfeature.checkers.AbstractCheckerType;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class SingleBlockChecker extends AbstractCheckerType {

	private Block block;
	protected int[] offset;
	

	public SingleBlockChecker() {

	}

	@Override
	public AbstractCheckerType parse(Gson gson, JsonElement parameters) {
		this.block = AbstractCheckerType.getBlock(parameters);
		this.offset = AbstractCheckerType.getOffset(parameters);
		return this;
	}

	public Block getBlock() {
		return this.block;
	}
	

	@Override
	public boolean check(Level level, BlockPos pos) {
		pos = pos.offset(offset[0], offset[1], offset[2]);
		return level.getBlockState(pos).is(this.block);
	}

}
