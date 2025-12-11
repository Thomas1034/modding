package com.startraveler.verdant.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.startraveler.verdant.block.custom.entity.OvergrownSpawnerBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SpawnerRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class OvergrownSpawnerRenderer implements BlockEntityRenderer<OvergrownSpawnerBlockEntity> {
    private final EntityRenderDispatcher entityRenderer;

    public OvergrownSpawnerRenderer(BlockEntityRendererProvider.Context context) {
        this.entityRenderer = context.getEntityRenderer();
    }

    public void render(OvergrownSpawnerBlockEntity spawnerBlockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int possiblyTheColor, @NotNull Vec3 position) {
        Level level = spawnerBlockEntity.getLevel();
        if (level != null) {
            BaseSpawner spawner = spawnerBlockEntity.getSpawner();
            Entity entity = spawner.getOrCreateDisplayEntity(level, spawnerBlockEntity.getBlockPos());
            if (entity != null) {
                SpawnerRenderer.renderEntityInSpawner(
                        partialTick,
                        poseStack,
                        bufferSource,
                        packedLight,
                        entity,
                        this.entityRenderer,
                        spawner.getoSpin(),
                        spawner.getSpin()
                );
            }
        }
    }
}
