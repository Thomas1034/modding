package com.startraveler.verdant.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ParticleEmittingLanternBlock extends LanternBlock {
    protected final SimpleParticleType flameParticle;


    public ParticleEmittingLanternBlock(SimpleParticleType flameParticle, Properties properties) {
        super(properties);
        this.flameParticle = flameParticle;
    }

    @Override
    public void animateTick(@NotNull BlockState state, Level level, BlockPos pos, @NotNull RandomSource random) {
        double x = (double) pos.getX() + random.nextFloat();
        double y = (double) pos.getY() + (state.getOptionalValue(HANGING).orElse(false) ? -0.125 : 1.125);
        double z = (double) pos.getZ() + random.nextFloat();
        level.addParticle(this.flameParticle, x, y, z, 0.0F, 0.0F, 0.0F);
    }
}
