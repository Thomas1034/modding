package com.thomas.verdant.client.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

import java.util.function.Function;

public class RopeModel extends Model {

    private static final String PLATE = "plate";
    private static final String HANDLE = "handle";

    private static final Function<Integer, String> LAYER = (i) -> ("layer" + i);
    private static final int SHIELD_WIDTH = 10;
    private static final int SHIELD_HEIGHT = 20;
    private static final int NUM_LAYERS = 5;
    private final ModelPart[] layers;
    private final ModelPart plate;
    private final ModelPart handle;


    public RopeModel(ModelPart root) {
        super(root, RenderType::entityCutout);
        this.plate = root.getChild(PLATE);
        this.handle = root.getChild(HANDLE);
        this.layers = new ModelPart[NUM_LAYERS];
        for (int i = 0; i < NUM_LAYERS; i++) {
            this.layers[i] = root.getChild(LAYER.apply(i));
        }
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild(
                PLATE,
                CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -11.0F, -2.0F, 12.0F, 22.0F, 1.0F),
                PartPose.ZERO
        );
        partdefinition.addOrReplaceChild(
                HANDLE,
                CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 6.0F),
                PartPose.ZERO
        );
        float cumulativeOffset = 0;

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    public ModelPart plate() {
        return this.plate;
    }

    public ModelPart handle() {
        return this.handle;
    }
}
