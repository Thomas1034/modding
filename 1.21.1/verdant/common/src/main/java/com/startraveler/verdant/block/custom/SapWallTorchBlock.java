package com.startraveler.verdant.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SapWallTorchBlock extends WallTorchBlock {
    public SapWallTorchBlock(SimpleParticleType particleType, Properties properties) {
        super(particleType, properties);
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        Direction facingDirection = state.getValue(FACING);
        double centerX = (double) pos.getX() + (double) 0.5F;
        double centerY = (double) pos.getY() + 0.7;
        double centerZ = (double) pos.getZ() + (double) 0.5F;
        double verticalOffset = 0.22;
        double horizontalOffset = 0.27;
        Direction antifacingDirection = facingDirection.getOpposite();
        level.addParticle(
                ParticleTypes.SMOKE,
                centerX + horizontalOffset * (double) antifacingDirection.getStepX(),
                centerY + verticalOffset,
                centerZ + horizontalOffset * (double) antifacingDirection.getStepZ(),
                0.0F,
                0.0F,
                0.0F
        );

        if (random.nextInt(16) == 0) {
            level.addParticle(
                    this.flameParticle,
                    centerX + horizontalOffset * (double) antifacingDirection.getStepX(),
                    centerY + verticalOffset,
                    centerZ + horizontalOffset * (double) antifacingDirection.getStepZ(),
                    0.0F,
                    0.0F,
                    0.0F
            );
        }
    }
}
