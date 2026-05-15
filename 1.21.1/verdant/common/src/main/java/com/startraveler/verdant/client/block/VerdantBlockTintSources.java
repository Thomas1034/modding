package com.startraveler.verdant.client.block;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

public class VerdantBlockTintSources {

    public static BlockTintSource snowyGrassBlock() {
        return new BlockTintSource() {
            public int color(final @NotNull BlockState state) {
                return isSnowy(state) ? -1 : GrassColor.getDefaultColor();
            }

            public int colorInWorld(final @NotNull BlockState state, final @NotNull BlockAndTintGetter level, final @NotNull BlockPos pos) {
                return isSnowy(state) ? -1 : BiomeColors.getAverageGrassColor(level, pos);
            }

            public int colorAsTerrainParticle(final @NotNull BlockState state, final @NotNull BlockAndTintGetter level, final @NotNull BlockPos pos) {
                return -1;
            }
        };
    }

    public static boolean isSnowy(@NotNull BlockState state) {
        return state.getValueOrElse(
                BlockStateProperties.SNOWY,
                false
        );
    }
}
