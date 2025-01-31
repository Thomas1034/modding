package com.startraveler.verdant.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.startraveler.verdant.Constants;
import com.startraveler.verdant.menu.FishTrapMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FishTrapScreen extends AbstractContainerScreen<FishTrapMenu> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/gui/fish_trap_gui.png"
    );
    public static final int ARROW_WIDTH = 8;
    public static final int ARROW_HEIGHT = 26;
    public static final int ARROW_TEXTURE_OFFSET_X = 176;
    public static final int ARROW_TEXTURE_OFFSET_Y = 0;
    public static final int ARROW_BLIT_OFFSET_X = 85;
    public static final int ARROW_BLIT_OFFSET_Y = 30;
    public static final int SLOT_WIDTH = 18;
    public static final int SLOT_HEIGHT = 18;
    public static final int SLOT_TEXTURE_OFFSET_X = 184;
    public static final int SLOT_TEXTURE_OFFSET_Y = 0;
    public static final int BAIT_SLOT_BLIT_OFFSET_X = 79;
    public static final int BAIT_SLOT_BLIT_OFFSET_Y = 10;
    public static final int OUTPUT_SLOT_BLIT_OFFSET_X = 79;
    public static final int OUTPUT_SLOT_BLIT_OFFSET_Y = 58;
    public static final int SPACING = 5;

    public FishTrapScreen(FishTrapMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(CoreShaders.POSITION_TEX_COLOR);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        // Parameters:
        // texture location
        // x of top left corner
        // y of top left corner
        // offset x on texture location
        // offset y on texture location
        // pixel width of texture to draw
        // pixel height of texture to draw
        guiGraphics.blit(
                RenderType::guiTextured,
                TEXTURE,
                x,
                y,
                0,
                0,
                this.imageWidth,
                this.imageHeight,
                BACKGROUND_TEXTURE_WIDTH,
                BACKGROUND_TEXTURE_HEIGHT
        );

        this.renderProgressArrow(guiGraphics, x, y);

        int catchPercent = this.menu.getCatchPercent();
        Component catchPercentComponent = Component.translatable(
                "block.verdant.fish_trap.gui.bait",
                catchPercent
        ); // Component.literal(catchPercent + "%");
        boolean isActive = true; //TODO this.menu.blockEntity.getBlockState().getValue(FishTrapBlock.ENABLED);
        if (!isActive) {
            catchPercentComponent = Component.translatable("block.verdant.fish_trap.gui.no_water");
        } else if (catchPercent == 0) {
            catchPercentComponent = Component.translatable("block.verdant.fish_trap.gui.no_bait");
        }

        this.renderText(
                guiGraphics,
                x + ARROW_BLIT_OFFSET_X + ARROW_WIDTH + SPACING,
                y + ARROW_BLIT_OFFSET_Y + ARROW_HEIGHT / 2 - this.font.lineHeight / 2,
                catchPercentComponent
        );

        int numOutputSlots = this.menu.getNumOutputSlots();
        int numBaitSlots = this.menu.getNumBaitSlots();
        int inputOffsetIfEven = SLOT_WIDTH * ((numBaitSlots + 1) % 2) / 2;
        int outputOffsetIfEven = SLOT_WIDTH * ((numOutputSlots + 1) % 2) / 2;
        // Get locations of the slots to draw.
        for (int i = 0; i < numBaitSlots; i++) {
            this.renderSlot(
                    guiGraphics,
                    x,
                    y,
                    BAIT_SLOT_BLIT_OFFSET_X + SLOT_WIDTH * (i - numBaitSlots / 2) + inputOffsetIfEven,
                    BAIT_SLOT_BLIT_OFFSET_Y
            );
        }
        for (int i = 0; i < numOutputSlots; i++) {
            this.renderSlot(
                    guiGraphics,
                    x,
                    y,
                    OUTPUT_SLOT_BLIT_OFFSET_X + SLOT_WIDTH * (i - numOutputSlots / 2) + outputOffsetIfEven,
                    OUTPUT_SLOT_BLIT_OFFSET_Y
            );
        }
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (this.menu.isCrafting()) {
            // Draw the arrow
            guiGraphics.blit(
                    RenderType::guiTextured,
                    TEXTURE,
                    x + ARROW_BLIT_OFFSET_X,
                    y + ARROW_BLIT_OFFSET_Y,
                    ARROW_TEXTURE_OFFSET_X,
                    ARROW_TEXTURE_OFFSET_Y,
                    ARROW_WIDTH,
                    (int) (this.menu.getProgress() * ARROW_HEIGHT),
                    BACKGROUND_TEXTURE_WIDTH,
                    BACKGROUND_TEXTURE_HEIGHT
            );
        }
    }

    private void renderSlot(GuiGraphics guiGraphics, int x, int y, int blitOffsetX, int blitOffsetY) {
        // Draw the slot
        guiGraphics.blit(
                RenderType::guiTextured,
                TEXTURE,
                x + blitOffsetX,
                y + blitOffsetY,
                SLOT_TEXTURE_OFFSET_X,
                SLOT_TEXTURE_OFFSET_Y,
                SLOT_WIDTH,
                SLOT_HEIGHT,
                BACKGROUND_TEXTURE_WIDTH,
                BACKGROUND_TEXTURE_HEIGHT
        );
    }

    @SuppressWarnings("unused")
    private void renderText(GuiGraphics guiGraphics, int x, int y, String text) {
        // Draw the slot
        guiGraphics.drawString(this.font, text, x, y, 0x404040, false);
    }

    @SuppressWarnings("unused")
    private void renderText(GuiGraphics guiGraphics, int x, int y, Component text) {
        // Draw the slot
        guiGraphics.drawString(this.font, text, x, y, 0x404040, false);
    }
}
