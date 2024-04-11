package com.thomas.cloudscape.block.custom;

import javax.annotation.Nullable;

import com.thomas.cloudscape.util.Reflection;
import com.thomas.cloudscape.util.Utilities;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class DynamicColorBlock extends Block {

	public DynamicColorBlock(Properties properties) {
		super(properties);
	}

	public static int skyColors(BlockState state, @Nullable BlockAndTintGetter getter, @Nullable BlockPos pos, int i1) {

		Level level = null;

		if (getter instanceof ClientLevel) {
			level = (ClientLevel) getter;
		} else if (getter instanceof RenderChunkRegion region) {
			try {
				Object obj = Reflection.getFromFinal(region, "level");
				if (obj instanceof Level levelRaw) {
					level = levelRaw;
				} else {
					throw new IllegalArgumentException("The level field did not contain the expected type Level");
				}
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		if (level != null) {

			// Manually calculating darkness, from Level class
			double d0 = 1.0D - (double) (level.getRainLevel(1.0F) * 5.0F) / 16.0D;
			double d1 = 1.0D - (double) (level.getThunderLevel(1.0F) * 5.0F) / 16.0D;
			double d2 = 0.5D + 2.0D
					* Mth.clamp((double) Mth.cos(level.getTimeOfDay(1.0F) * ((float) Math.PI * 2F)), -0.25D, 0.25D);
			int manualDarkness = (int) ((1.0D - d2 * d0 * d1) * 11.0D);

			float darkness = (0.5f - (manualDarkness / 22.0f)) * 2.0f;
			//System.out.println("Sky darken is " + (level.getSkyDarken()) + " manually is " + manualDarkness);
			//System.out.println("Darkness is " + darkness);
			int skyColor = level.getBiome(pos).get().getSkyColor();

			int[] channels = Utilities.splitChannels(skyColor);
			channels[0] *= darkness;
			channels[1] *= darkness;
			channels[2] *= darkness;

			int adjustedSkyColor = Utilities.mergeChannels(channels);

			return adjustedSkyColor;

		} else {
			//System.out.println("Level is null!");
			// return default plains sky.
			return 0x78A7FF;
		}

	}

}
