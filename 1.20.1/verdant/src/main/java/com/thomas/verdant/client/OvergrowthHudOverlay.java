package com.thomas.verdant.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.thomas.verdant.Verdant;
import com.thomas.verdant.overgrowth.EntityOvergrowth;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class OvergrowthHudOverlay {

	private static final ResourceLocation INITIAL_0 = new ResourceLocation(Verdant.MOD_ID,
			"textures/overgrowth/segment/initial_0.png");
	private static final ResourceLocation INITIAL_1 = new ResourceLocation(Verdant.MOD_ID,
			"textures/overgrowth/segment/initial_1.png");
	private static final ResourceLocation INITIAL_2 = new ResourceLocation(Verdant.MOD_ID,
			"textures/overgrowth/segment/initial_2.png");
	private static final ResourceLocation INITIAL_3 = new ResourceLocation(Verdant.MOD_ID,
			"textures/overgrowth/segment/initial_3.png");
	private static final ResourceLocation MIDDLE_0 = new ResourceLocation(Verdant.MOD_ID,
			"textures/overgrowth/segment/middle_0.png");
	private static final ResourceLocation MIDDLE_1 = new ResourceLocation(Verdant.MOD_ID,
			"textures/overgrowth/segment/middle_1.png");
	private static final ResourceLocation MIDDLE_2 = new ResourceLocation(Verdant.MOD_ID,
			"textures/overgrowth/segment/middle_2.png");
	private static final ResourceLocation MIDDLE_3 = new ResourceLocation(Verdant.MOD_ID,
			"textures/overgrowth/segment/middle_3.png");
	private static final ResourceLocation FINAL_0 = new ResourceLocation(Verdant.MOD_ID,
			"textures/overgrowth/segment/final_0.png");
	private static final ResourceLocation FINAL_1 = new ResourceLocation(Verdant.MOD_ID,
			"textures/overgrowth/segment/final_1.png");
	private static final ResourceLocation FINAL_2 = new ResourceLocation(Verdant.MOD_ID,
			"textures/overgrowth/segment/final_2.png");
	private static final ResourceLocation FINAL_3 = new ResourceLocation(Verdant.MOD_ID,
			"textures/overgrowth/segment/final_3.png");

	@SuppressWarnings("resource")
	public static final IGuiOverlay HUD_INFECTION = ((gui, poseStack, partialTick, width, height) -> {
		int horizontalCenter = width / 2;
		int verticalCenter = height;

		// If the user has no overgrowth level, don't show this.
		// Also don't show if in creative or spectator.
		if (ClientOvergrowthData
				.getPlayerOvergrowth(Minecraft.getInstance().player.getUUID()) == EntityOvergrowth.MIN_LEVEL
				|| Minecraft.getInstance().player.getAbilities().invulnerable) {
			return;
		}

		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		int cumulativeSlotOffset = 0;
		for (int i = 0; i < EntityOvergrowth.STAGES; i++) {
			ResourceLocation toBlit = getTextureForSlotAndLevel(i,
					ClientOvergrowthData.getPlayerOvergrowth(Minecraft.getInstance().player.getUUID()));
			int slotWidth = i == EntityOvergrowth.STAGES ? 11 : 10;
			int slotHeight = 12;
			poseStack.blit(toBlit, horizontalCenter + 10 + cumulativeSlotOffset, verticalCenter - 50 - slotHeight, 0, 0,
					slotWidth, slotHeight, slotWidth, slotHeight);
			cumulativeSlotOffset += slotWidth;
		}
		return;
	});

	private static ResourceLocation getTextureForSlotAndLevel(int slot, int level) {

		// Break the overgrowth level into 32 parts; find how many fractions have been
		// filled.
		int parts = (int) (32.0 * (level - EntityOvergrowth.MIN_LEVEL)
				/ (EntityOvergrowth.MAX_LEVEL - EntityOvergrowth.MIN_LEVEL));
		int fullnessLevel = Math.max(parts - 4 * slot, 0);

		// Special case first slot.
		if (slot == 0) {
			switch (fullnessLevel) {
			case 0:
				return INITIAL_0;
			case 1:
				return INITIAL_1;
			case 2:
				return INITIAL_2;
			case 3:
				return INITIAL_3;
			default:
				return INITIAL_3;
			}
		}
		// Special case last slot
		else if (slot == EntityOvergrowth.STAGES - 1) {
			switch (fullnessLevel) {
			case 0:
				return FINAL_0;
			case 1:
				return FINAL_1;
			case 2:
				return FINAL_2;
			case 3:
				return FINAL_3;
			default:
				return FINAL_3;
			}
		}
		// Middle slots
		else {
			switch (fullnessLevel) {
			case 0:
				return MIDDLE_0;
			case 1:
				return MIDDLE_1;
			case 2:
				return MIDDLE_2;
			case 3:
				return MIDDLE_3;
			default:
				return MIDDLE_3;
			}
		}

	}
}
