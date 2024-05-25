package com.thomas.verdant.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.thomas.verdant.Verdant;
import com.thomas.verdant.infection.EntityInfection;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class InfectionHudOverlay {

	private static final ResourceLocation INITIAL_0 = new ResourceLocation(Verdant.MOD_ID,
			"textures/infection/segment/initial_0.png");
	private static final ResourceLocation INITIAL_1 = new ResourceLocation(Verdant.MOD_ID,
			"textures/infection/segment/initial_1.png");
	private static final ResourceLocation INITIAL_2 = new ResourceLocation(Verdant.MOD_ID,
			"textures/infection/segment/initial_2.png");
	private static final ResourceLocation INITIAL_3 = new ResourceLocation(Verdant.MOD_ID,
			"textures/infection/segment/initial_3.png");
	private static final ResourceLocation MIDDLE_0 = new ResourceLocation(Verdant.MOD_ID,
			"textures/infection/segment/middle_0.png");
	private static final ResourceLocation MIDDLE_1 = new ResourceLocation(Verdant.MOD_ID,
			"textures/infection/segment/middle_1.png");
	private static final ResourceLocation MIDDLE_2 = new ResourceLocation(Verdant.MOD_ID,
			"textures/infection/segment/middle_2.png");
	private static final ResourceLocation MIDDLE_3 = new ResourceLocation(Verdant.MOD_ID,
			"textures/infection/segment/middle_3.png");
	private static final ResourceLocation FINAL_0 = new ResourceLocation(Verdant.MOD_ID,
			"textures/infection/segment/final_0.png");
	private static final ResourceLocation FINAL_1 = new ResourceLocation(Verdant.MOD_ID,
			"textures/infection/segment/final_1.png");
	private static final ResourceLocation FINAL_2 = new ResourceLocation(Verdant.MOD_ID,
			"textures/infection/segment/final_2.png");
	private static final ResourceLocation FINAL_3 = new ResourceLocation(Verdant.MOD_ID,
			"textures/infection/segment/final_3.png");

	@SuppressWarnings("resource")
	public static final IGuiOverlay HUD_THIRST = ((gui, poseStack, partialTick, width, height) -> {
		int horizontalCenter = width / 2;
		int verticalCenter = height;

		// If the user has no infection level, don't show this.
		if (ClientInfectionData
				.getPlayerInfection(Minecraft.getInstance().player.getUUID()) == EntityInfection.MIN_LEVEL) {
			return;
		}

		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		int cumulativeSlotOffset = 0;
		for (int i = 0; i < 8; i++) {
			ResourceLocation toBlit = getTextureForSlotAndLevel(i,
					ClientInfectionData.getPlayerInfection(Minecraft.getInstance().player.getUUID()));
			int slotWidth = i == 7 ? 11 : 10;
			int slotHeight = 12;
			poseStack.blit(toBlit, horizontalCenter + 10 + cumulativeSlotOffset, verticalCenter - 50 - slotHeight, 0, 0, slotWidth,
					slotHeight, slotWidth, slotHeight);
			cumulativeSlotOffset += slotWidth;
		}
		return;
	});

	private static ResourceLocation getTextureForSlotAndLevel(int slot, int level) {

		// Break the infection level into 32 parts; find how many fractions have been
		// filled.
		int parts = (int) (32.0 * (level - EntityInfection.MIN_LEVEL)
				/ (EntityInfection.MAX_LEVEL - EntityInfection.MIN_LEVEL));
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
		else if (slot == 7) {
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
