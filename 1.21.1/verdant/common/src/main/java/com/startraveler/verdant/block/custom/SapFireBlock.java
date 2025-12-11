package com.startraveler.verdant.block.custom;

import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SoulFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SapFireBlock extends SoulFireBlock {

    protected static final ParticleOptions PARTICLE_OPTIONS = ParticleTypes.LARGE_SMOKE;
    protected static final int FIREFLY_OFFSET = 1;

    public SapFireBlock(Properties properties) {
        super(properties);
    }

    public static boolean canSurviveOnBlock(BlockState state) {
        return state.is(VerdantTags.Blocks.SAP_FIRE_BASE_BLOCKS);
    }

    protected boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        return canSurviveOnBlock(level.getBlockState(pos.below()));
    }


    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, RandomSource random) {
        if (random.nextInt(24) == 0) {
            level.playLocalSound(
                    (double) pos.getX() + (double) 0.5F,
                    (double) pos.getY() + (double) 0.5F,
                    (double) pos.getZ() + (double) 0.5F,
                    SoundEvents.FIRE_AMBIENT,
                    SoundSource.BLOCKS,
                    1.0F + random.nextFloat(),
                    random.nextFloat() * 0.7F + 0.3F,
                    false
            );
        }

        BlockPos blockpos = pos.below();
        BlockState blockstate = level.getBlockState(blockpos);
        if (!this.canBurn(blockstate) && !blockstate.isFaceSturdy(level, blockpos, Direction.UP)) {
            if (this.canBurn(level.getBlockState(pos.west()))) {
                for (int j = 0; j < 2; ++j) {
                    double d3 = (double) pos.getX() + random.nextDouble() * (double) 0.1F;
                    double d8 = (double) pos.getY() + random.nextDouble();
                    double d13 = (double) pos.getZ() + random.nextDouble();
                    level.addParticle(PARTICLE_OPTIONS, d3, d8, d13, 0.0F, 0.0F, 0.0F);
                }
            }

            if (this.canBurn(level.getBlockState(pos.east()))) {
                for (int k = 0; k < 2; ++k) {
                    double d4 = (double) (pos.getX() + 1) - random.nextDouble() * (double) 0.1F;
                    double d9 = (double) pos.getY() + random.nextDouble();
                    double d14 = (double) pos.getZ() + random.nextDouble();
                    level.addParticle(PARTICLE_OPTIONS, d4, d9, d14, 0.0F, 0.0F, 0.0F);
                }
            }

            if (this.canBurn(level.getBlockState(pos.north()))) {
                for (int l = 0; l < 2; ++l) {
                    double d5 = (double) pos.getX() + random.nextDouble();
                    double d10 = (double) pos.getY() + random.nextDouble();
                    double d15 = (double) pos.getZ() + random.nextDouble() * (double) 0.1F;
                    level.addParticle(PARTICLE_OPTIONS, d5, d10, d15, 0.0F, 0.0F, 0.0F);
                }
            }

            if (this.canBurn(level.getBlockState(pos.south()))) {
                for (int i1 = 0; i1 < 2; ++i1) {
                    double d6 = (double) pos.getX() + random.nextDouble();
                    double d11 = (double) pos.getY() + random.nextDouble();
                    double d16 = (double) (pos.getZ() + 1) - random.nextDouble() * (double) 0.1F;
                    level.addParticle(PARTICLE_OPTIONS, d6, d11, d16, 0.0F, 0.0F, 0.0F);
                }
            }

            if (this.canBurn(level.getBlockState(pos.above()))) {
                for (int j1 = 0; j1 < 2; ++j1) {
                    double d7 = (double) pos.getX() + random.nextDouble();
                    double d12 = (double) (pos.getY() + 1) - random.nextDouble() * (double) 0.1F;
                    double d17 = (double) pos.getZ() + random.nextDouble();
                    level.addParticle(PARTICLE_OPTIONS, d7, d12, d17, 0.0F, 0.0F, 0.0F);
                }
            }
        } else {
            for (int i = 0; i < 3; ++i) {
                double d0 = (double) pos.getX() + random.nextDouble();
                double d1 = (double) pos.getY() + random.nextDouble() * (double) 0.5F + (double) 0.5F;
                double d2 = (double) pos.getZ() + random.nextDouble();
                level.addParticle(PARTICLE_OPTIONS, d0, d1, d2, 0.0F, 0.0F, 0.0F);
            }
        }

    }

}
