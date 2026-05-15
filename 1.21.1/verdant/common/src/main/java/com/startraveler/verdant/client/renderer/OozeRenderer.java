package com.startraveler.verdant.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.startraveler.verdant.Constants;
import com.startraveler.verdant.client.layer.OozeOuterLayer;
import com.startraveler.verdant.entity.custom.OozeEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.slime.SlimeModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.MovingBlockRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class OozeRenderer extends MobRenderer<@NotNull OozeEntity, @NotNull OozeRenderState, @NotNull SlimeModel> {

    public static final int MIN_BLOCK_LIGHT = 4;
    public static final Identifier OOZE_LOCATION = Identifier.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/entity/slime/ooze.png"
    );
    private final ItemModelResolver itemModelResolver;

    public OozeRenderer(EntityRendererProvider.Context context) {
        super(context, new SlimeModel(context.bakeLayer(ModelLayers.SLIME)), 0.25F);
        this.addLayer(new OozeOuterLayer(this, context.getModelSet()));
        this.itemModelResolver = context.getItemModelResolver();
    }

    @Override
    public void submit(OozeRenderState renderState, @NotNull PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector, @NotNull CameraRenderState state) {
        poseStack.pushPose();
        this.setupRotations(renderState, poseStack, renderState.bodyRot, renderState.scale);

        float oozeSize = renderState.size * ((7f + (1f / 8f)) / 16f);

        float flowerScaleFactor = renderState.size == 1 ? 1 : (8f / 16f + 3f / 16f * renderState.size);
        poseStack.scale(flowerScaleFactor, flowerScaleFactor, flowerScaleFactor);

        float positionInOoze = 7f / 16f;

        float fixedFlowerOffset = 2f / 16f;
        float scaledFlowerOffset = renderState.size == 1 ? (oozeSize - fixedFlowerOffset) : Math.max(
                (oozeSize - flowerScaleFactor) * positionInOoze,
                0
        );

        poseStack.translate(-0.5f, scaledFlowerOffset + fixedFlowerOffset, -0.5f);

        if (renderState.block != null) {
            // Offset to make up for the world offset.
            Vec3 blockOffset = renderState.block.blockState.getOffset(renderState.block.blockPos);
            poseStack.translate(blockOffset.scale(-1));

            submitNodeCollector.submitMovingBlock(poseStack, renderState.block);
        } else if (renderState.item != null) {
            renderState.item.submit(
                    poseStack,
                    submitNodeCollector,
                    renderState.lightCoords,
                    OverlayTexture.NO_OVERLAY,
                    renderState.outlineColor
            );
        }

        poseStack.popPose();

        super.submit(renderState, poseStack, submitNodeCollector, state);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull OozeRenderState state) {
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
            MovingBlockRenderState blockRenderState = new MovingBlockRenderState();
            BlockPos oozePos = ooze.blockPosition();
            BlockPos blockPos = BlockPos.containing(oozePos.getX(), ooze.getBoundingBox().maxY, oozePos.getZ());
            blockRenderState.randomSeedPos = blockPos;
            blockRenderState.blockPos = blockPos;
            blockRenderState.blockState = blockItem.getBlock().defaultBlockState();
            blockRenderState.lightEngine = ooze.level().getLightEngine();
            blockRenderState.biome = ooze.level().getBiome(blockPos);

            renderState.item = null;
            renderState.block = blockRenderState;
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
