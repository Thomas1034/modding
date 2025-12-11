package com.startraveler.verdant.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.startraveler.verdant.Constants;
import com.startraveler.verdant.client.layer.OozeOuterLayer;
import com.startraveler.verdant.entity.custom.OozeEntity;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class OozeRenderer extends MobRenderer<OozeEntity, OozeRenderState, SlimeModel> {
    public static final int MIN_BLOCK_LIGHT = 4;
    public static final ResourceLocation OOZE_LOCATION = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/entity/slime/ooze.png"
    );
    public static final int FULL_BRIGHT = 15728880;

    private final ItemModelResolver itemModelResolver;
    private final BlockRenderDispatcher blockRenderDispatcher;

    public OozeRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel(context.bakeLayer(ModelLayers.SLIME)), 0.25F);
        this.addLayer(new OozeOuterLayer(this, context.getModelSet()));
        this.itemModelResolver = context.getItemModelResolver();
        this.blockRenderDispatcher = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(@NotNull OozeRenderState renderState, @NotNull PoseStack poseStack, @NotNull MultiBufferSource source, int tint) {
        poseStack.pushPose();
        this.setupRotations(renderState, poseStack, renderState.bodyRot, renderState.scale);

        float oozeSize = renderState.size * ((7f + (1f / 8f)) / 16f);

        if (renderState.item != null) {
            poseStack.translate(0f, 0.5, 0f);
            renderState.item.render(poseStack, source, FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        } else {

            float flowerScaleFactor = renderState.size == 1 ? 1 : (8f / 16f + 3f / 16f * renderState.size);
            poseStack.scale(flowerScaleFactor, flowerScaleFactor, flowerScaleFactor);

            float positionInOoze = 7f / 16f;

            float fixedFlowerOffset = 2f / 16f;
            float scaledFlowerOffset = renderState.size == 1 ? (oozeSize - fixedFlowerOffset) : Math.max(
                    (oozeSize - flowerScaleFactor) * positionInOoze,
                    0
            );

            poseStack.translate(-0.5f, scaledFlowerOffset + fixedFlowerOffset, -0.5f);

            this.renderSingleBlock(renderState.block, poseStack, source, FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        }

        poseStack.popPose();
        super.render(renderState, poseStack, source, tint);

    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull OozeRenderState state) {
        return OOZE_LOCATION;
    }

    @Override
    protected void scale(@NotNull OozeRenderState renderState, @NotNull PoseStack poseStack) {
        this.scale(renderState, poseStack, 1);
    }

    public void extractRenderState(@NotNull OozeEntity ooze, @NotNull OozeRenderState renderState, float partialTick) {
        super.extractRenderState(ooze, renderState, partialTick);
        renderState.squish = Mth.lerp(partialTick, ooze.oSquish, ooze.squish);
        renderState.size = ooze.getSize();
        ItemStack mainHandItem = ooze.getMainHandItem();

        if (mainHandItem.getItem() instanceof BlockItem blockItem) {
            renderState.block = blockItem.getBlock().defaultBlockState();
            renderState.item = null;
        } else {
            renderState.item = new ItemStackRenderState();
            this.itemModelResolver.updateForNonLiving(
                    renderState.item,
                    ooze.getMainHandItem(),
                    ItemDisplayContext.FIXED,
                    ooze
            );
            renderState.block = null;
        }
    }

    @Override
    protected int getBlockLightLevel(@NotNull OozeEntity entity, @NotNull BlockPos pos) {
        return Math.max(MIN_BLOCK_LIGHT, super.getBlockLightLevel(entity, pos));
    }

    public @NotNull OozeRenderState createRenderState() {
        return new OozeRenderState();
    }

    public void renderSingleBlock(BlockState state, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        RenderShape renderShape = state.getRenderShape();
        if (renderShape != RenderShape.INVISIBLE) {
            BlockStateModel blockStateModel = this.blockRenderDispatcher.getBlockModel(state);
            int color = this.blockRenderDispatcher.blockColors.getColor(state, null, null, 0);
            float red = (float) (color >> 16 & 255) / 255.0F;
            float green = (float) (color >> 8 & 255) / 255.0F;
            float blue = (float) (color & 255) / 255.0F;
            ModelBlockRenderer.renderModel(
                    poseStack.last(),
                    bufferSource.getBuffer(RenderType.cutout()),
                    blockStateModel,
                    red,
                    green,
                    blue,
                    packedLight,
                    packedOverlay
            );
            this.blockRenderDispatcher.specialBlockModelRenderer.get()
                    .renderByBlock(
                            state.getBlock(),
                            ItemDisplayContext.NONE,
                            poseStack,
                            bufferSource,
                            packedLight,
                            packedOverlay
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

}
