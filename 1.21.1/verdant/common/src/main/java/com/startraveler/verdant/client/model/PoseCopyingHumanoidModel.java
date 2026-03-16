package com.startraveler.verdant.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PoseCopyingHumanoidModel<T extends HumanoidRenderState> extends HumanoidModel<T> {
    protected final Model<T> toCopy;

    public PoseCopyingHumanoidModel(ModelPart root, HumanoidModel<T> toCopy) {
        super(root, toCopy::renderType);
        this.toCopy = toCopy;
    }


    public void setupAnim(@NotNull T renderState) {
        super.setupAnim(renderState);
        this.toCopy.setupAnim(renderState);
        Map<String, ModelPart> partsMap = new HashMap<>();
        partsMap.put("root", this.root);
        this.root.addAllChildren(partsMap::putIfAbsent);

        Function<String, @NotNull ModelPart> partsPoseLookup = this.toCopy.root().createPartLookup();
        partsMap.forEach((name, part) -> this.copyPose(part, partsPoseLookup.apply(name)));

    }

    public void copyPose(ModelPart to, ModelPart from) {
        if (from != null && to != null) {
            to.setInitialPose(from.getInitialPose());
            to.loadPose(from.storePose());
            to.xScale = from.xScale;
            to.yScale = from.yScale;
            to.zScale = from.zScale;
            to.skipDraw = from.skipDraw;
            to.visible = from.visible;
        }
    }
}
