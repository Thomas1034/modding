package com.startraveler.verdant.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SapTorchBlock extends TorchBlock {
    public SapTorchBlock(SimpleParticleType particleType, Properties properties) {
        super(particleType, properties);
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        double x = (double) pos.getX() + (double) 0.5F;
        double y = (double) pos.getY() + 0.7;
        double z = (double) pos.getZ() + (double) 0.5F;
        level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0F, 0.0F, 0.0F);
        if (random.nextInt(16) == 0) {
            level.addParticle(this.flameParticle, x, y + 0.425, z, 0.0F, 0.0F, 0.0F);
        }
    }
}
