package com.startraveler.verdant.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.startraveler.verdant.client.model.HumanoidSpikesModel;
import com.startraveler.verdant.registry.DataComponentRegistry;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import org.jetbrains.annotations.NotNull;

public class HumanoidSpikesLayer<S extends HumanoidRenderState, M extends HumanoidSpikesModel<S>, A extends HumanoidModel<S>> extends
        HumanoidArmorLayer<S, M, A> {
    public HumanoidSpikesLayer(RenderLayerParent<S, M> renderer, ArmorModelSet<A> modelSet, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer, modelSet, equipmentRenderer);
    }

    public HumanoidSpikesLayer(RenderLayerParent<S, M> renderer, ArmorModelSet<A> modelSet, ArmorModelSet<A> babyModelSet, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer, modelSet, babyModelSet, equipmentRenderer);
    }

    public static boolean shouldRender(ItemStack stack, EquipmentSlot slot) {
        Equippable equippable = stack.get(DataComponentRegistry.EQUIPPABLE_SPIKES.get());
        return equippable != null && shouldRender(equippable, slot);
    }

    private static boolean shouldRender(Equippable equippable, EquipmentSlot slot) {
        return equippable.assetId().isPresent() && equippable.slot() == slot;
    }

    @Override
    public void submit(@NotNull PoseStack stack, @NotNull SubmitNodeCollector submitNodeCollector, int packedLight, S renderState, float p_435802_, float p_434554_) {
        this.renderSpikes(
                stack,
                submitNodeCollector,
                renderState.chestEquipment,
                EquipmentSlot.CHEST,
                packedLight,
                renderState
        );
        this.renderSpikes(
                stack,
                submitNodeCollector,
                renderState.legsEquipment,
                EquipmentSlot.LEGS,
                packedLight,
                renderState
        );
        this.renderSpikes(
                stack,
                submitNodeCollector,
                renderState.feetEquipment,
                EquipmentSlot.FEET,
                packedLight,
                renderState
        );
        this.renderSpikes(
                stack,
                submitNodeCollector,
                renderState.headEquipment,
                EquipmentSlot.HEAD,
                packedLight,
                renderState
        );
    }

    protected void renderSpikes(PoseStack poseStack, SubmitNodeCollector nodeCollector, ItemStack item, EquipmentSlot slot, int packedLight, S renderState) {
        Equippable equippable = item.get(DataComponentRegistry.EQUIPPABLE_SPIKES.get());
        if (equippable != null && shouldRender(equippable, slot)) {
            A a = this.getArmorModel(renderState, slot);
            EquipmentClientInfo.LayerType layerType = this.usesInnerModel(slot) ? EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS : EquipmentClientInfo.LayerType.HUMANOID;
            this.equipmentRenderer.renderLayers(
                    layerType,
                    equippable.assetId().orElseThrow(),
                    a,
                    renderState,
                    item,
                    poseStack,
                    nodeCollector,
                    packedLight,
                    renderState.outlineColor
            );
        }
    }
}
