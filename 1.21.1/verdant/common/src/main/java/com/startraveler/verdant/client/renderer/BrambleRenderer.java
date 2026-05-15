package com.startraveler.verdant.client.renderer;

import com.startraveler.verdant.entity.custom.BrambleEntity;
import com.startraveler.verdant.registry.BlockRegistry;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.jetbrains.annotations.NotNull;

// TODO actually make it render.
public class BrambleRenderer extends EntityRenderer<@NotNull BrambleEntity, @NotNull BrambleEntityRenderState> {


    public BrambleRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.0F;
    }

    public BrambleEntityRenderState createRenderState() {
        return new BrambleEntityRenderState();
    }

    public void extractRenderState(BrambleEntity bramble, BrambleEntityRenderState renderState, float partialTick) {
        super.extractRenderState(bramble, renderState, partialTick);
        renderState.fuseRemainingInTicks = 1.0f;
        renderState.shellBlockState = bramble.getShellModelForCurrentStage();
        renderState.headBlockState = BlockRegistry.BRAMBLE_HEAD.get().defaultBlockState();
        renderState.isInvulnerable = bramble.getInvulnerableTicks() > 0;
        renderState.shellBlockScale = 1.25f;
        renderState.age = bramble.tickCount + partialTick;
        renderState.yRot = bramble.getYRot(partialTick);
        renderState.xRot = bramble.getXRot(partialTick);
        renderState.partialTick = partialTick;
    }
}
