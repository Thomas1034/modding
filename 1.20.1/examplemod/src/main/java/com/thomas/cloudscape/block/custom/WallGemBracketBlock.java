package com.thomas.cloudscape.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WallGemBracketBlock extends WallTorchBlock {

	public WallGemBracketBlock(Properties properties, ParticleOptions particle) {
		super(properties, particle);
	}

	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand) {
		// No particles are created.
	}
}
