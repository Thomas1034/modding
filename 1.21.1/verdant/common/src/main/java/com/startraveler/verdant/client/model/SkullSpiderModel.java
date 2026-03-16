package com.startraveler.verdant.client.model;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class SkullSpiderModel {

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition head = partDefinition.addOrReplaceChild(
                "head",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 15.0F, -3.0F)
        );

        head.addOrReplaceChild(
                "head_r1",
                CubeListBuilder.create()
                        .texOffs(28, 8)
                        .addBox(-4.0F, -5.0F, -8.0F, 8.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        partDefinition.addOrReplaceChild(
                "neck",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 15.0F, 0.0F)
        );

        PartDefinition body = partDefinition.addOrReplaceChild(
                "body",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 15.0F, 9.0F)
        );

        body.addOrReplaceChild(
                "body_r1",
                CubeListBuilder.create()
                        .texOffs(2, 41)
                        .addBox(-4.0F, -4.0F, -6.0F, 8.0F, 7.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F)
        );

        partDefinition.addOrReplaceChild(
                "right_hind_leg",
                CubeListBuilder.create()
                        .texOffs(18, 0)
                        .addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, 15.0F, 4.0F, 0.0F, (float) (Math.PI / 4), (float) (-Math.PI / 4))
        );

        partDefinition.addOrReplaceChild(
                "left_hind_leg",
                CubeListBuilder.create()
                        .texOffs(18, 0)
                        .addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(4.0F, 15.0F, 4.0F, 0.0F, (float) (-Math.PI / 4), (float) (Math.PI / 4))
        );

        partDefinition.addOrReplaceChild(
                "right_middle_hind_leg",
                CubeListBuilder.create()
                        .texOffs(18, 0)
                        .addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, 15.0F, 1.0F, 0.0F, (float) (Math.PI / 8), -0.58119464F)
        );

        partDefinition.addOrReplaceChild(
                "left_middle_hind_leg",
                CubeListBuilder.create()
                        .texOffs(18, 0)
                        .addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(4.0F, 15.0F, 1.0F, 0.0F, (float) (-Math.PI / 8), 0.58119464F)
        );

        partDefinition.addOrReplaceChild(
                "right_middle_front_leg",
                CubeListBuilder.create()
                        .texOffs(18, 0)
                        .addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, 15.0F, -2.0F, 0.0F, (float) (-Math.PI / 8), -0.58119464F)
        );

        partDefinition.addOrReplaceChild(
                "left_middle_front_leg",
                CubeListBuilder.create()
                        .texOffs(18, 0)
                        .addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(4.0F, 15.0F, -2.0F, 0.0F, (float) (Math.PI / 8), 0.58119464F)
        );

        partDefinition.addOrReplaceChild(
                "right_front_leg",
                CubeListBuilder.create()
                        .texOffs(18, 0)
                        .addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, 15.0F, -5.0F, 0.0F, (float) (-Math.PI / 4), (float) (-Math.PI / 4))
        );

        partDefinition.addOrReplaceChild(
                "left_front_leg",
                CubeListBuilder.create()
                        .texOffs(18, 0)
                        .addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(4.0F, 15.0F, -5.0F, 0.0F, (float) (Math.PI / 4), (float) (Math.PI / 4))
        );

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

}
