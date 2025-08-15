package com.startraveler.verdant.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.startraveler.verdant.Constants;
import com.startraveler.verdant.client.layer.OozeOuterLayer;
import com.startraveler.verdant.entity.custom.OozeEntity;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OozeRenderer extends MobRenderer<OozeEntity, OozeRenderState, SlimeModel> {
    public static final ResourceLocation OOZE_LOCATION = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/entity/slime/ooze.png"
    );
    private final ItemModelResolver itemModelResolver;
    private final BlockRenderDispatcher blockRenderDispatcher;

    public OozeRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel(context.bakeLayer(ModelLayers.SLIME)), 0.25F);
        this.addLayer(new OozeOuterLayer(this, context.getModelSet()));
        this.itemModelResolver = context.getItemModelResolver();
        this.blockRenderDispatcher = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(OozeRenderState renderState, PoseStack poseStack, MultiBufferSource source, int tint) {
        super.render(renderState, poseStack, source, tint);
        poseStack.pushPose();
        this.setupRotations(renderState, poseStack, renderState.bodyRot, renderState.scale);
        poseStack.scale(1.125F, 1.125F, 1.125F);
        poseStack.translate(-0.5f, 0.35f, -0.5f);
        if (renderState.item != null) {
            renderState.item.render(poseStack, source, 15728880, OverlayTexture.NO_OVERLAY);
        } else {
            this.blockRenderDispatcher.renderSingleBlock(
                    renderState.block,
                    poseStack,
                    source,
                    15728880,
                    OverlayTexture.NO_OVERLAY
            );
        }

        poseStack.popPose();

    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull OozeRenderState state) {
        return OOZE_LOCATION;
    }

    @Override
    protected void scale(@NotNull OozeRenderState renderState, @NotNull PoseStack poseStack) {
        this.scale(renderState, poseStack, 1);
    }

    public void extractRenderState(@NotNull OozeEntity ooze, @NotNull OozeRenderState renderState, float p_361099_) {
        super.extractRenderState(ooze, renderState, p_361099_);
        renderState.squish = Mth.lerp(p_361099_, ooze.oSquish, ooze.squish);
        renderState.size = ooze.getSize();
        ItemStack mainHandItem = ooze.getMainHandItem();
        if (mainHandItem.getItem() instanceof BlockItem blockItem) {
            renderState.block = blockItem.getBlock().defaultBlockState();
        } else {
            renderState.item = new ItemStackRenderState();
            this.itemModelResolver.updateForNonLiving(
                    renderState.item,
                    ooze.getMainHandItem(),
                    ItemDisplayContext.FIXED,
                    ooze
            );
        }
    }

    protected void scale(OozeRenderState renderState, PoseStack poseStack, float amount) {
        float miniScale = 0.999F;
        poseStack.scale(miniScale, miniScale, miniScale);
        poseStack.translate(0.0F, 1 - miniScale, 0.0F);
        float size = (float) renderState.size;
        float squishFactor = renderState.squish / (size * 0.5F + 1.0F);
        float normalized = 1.0F / (squishFactor + 1.0F);
        poseStack.scale(amount * normalized * size, amount / normalized * size, amount * normalized * size);
    }

    public @NotNull OozeRenderState createRenderState() {
        return new OozeRenderState();
    }

}
