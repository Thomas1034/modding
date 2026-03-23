package com.startraveler.verdant.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.Function;

public class HumanoidSpikesModel<T extends HumanoidRenderState> extends HumanoidModel<T> {


    @SuppressWarnings("unused")
    public HumanoidSpikesModel(ModelPart root) {
        super(root);
    }

    @SuppressWarnings("unused")
    public HumanoidSpikesModel(ModelPart root, Function<Identifier, RenderType> renderType) {
        super(root, renderType);
    }


    public static MeshDefinition createMesh(CubeDeformation cubeDeformation, float yOffset) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild(
                "head",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F + yOffset, 0.0F)
        );

        head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

        head.addOrReplaceChild(
                "crest", CubeListBuilder.create().texOffs(0, -8).addBox(
                        0.0F,
                        -16.0F - 2 * cubeDeformation.growY,
                        -4.0F,
                        0.0F,
                        8.0F,
                        8.0F,
                        cubeDeformation.extend(-cubeDeformation.growX, 0, 0)
                ), PartPose.ZERO
        );

        PartDefinition halo = head.addOrReplaceChild(
                "halo",
                CubeListBuilder.create(),
                PartPose.offset(0, -(7F / 8F) * (8 + 2 * cubeDeformation.growY) + cubeDeformation.growY, 0)
        );

        PartDefinition haloZPlus = halo.addOrReplaceChild(
                "halo_z_plus",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0, 0, -4 - cubeDeformation.growZ, 0, (float) Math.PI, 0)
        );

        haloZPlus.addOrReplaceChild(
                "halo_z_plus_up", CubeListBuilder.create().texOffs(12, 0).addBox(
                        -4,
                        0,
                        cubeDeformation.growZ / 2,
                        8,
                        0,
                        4,
                        cubeDeformation.extend(0, -cubeDeformation.growY, -cubeDeformation.growZ / 2)
                ), PartPose.offsetAndRotation(0, 0, 0, (float) Math.PI / 8, 0, 0)
        );

        haloZPlus.addOrReplaceChild(
                "halo_z_plus_down", CubeListBuilder.create().texOffs(12, 4).addBox(
                        -4,
                        0,
                        cubeDeformation.growZ / 2,
                        8,
                        0,
                        4,
                        cubeDeformation.extend(0, -cubeDeformation.growY, -cubeDeformation.growZ / 2)
                ), PartPose.offsetAndRotation(0, 0, 0, -(float) Math.PI / 8, 0, 0)
        );

        PartDefinition haloZMinus = halo.addOrReplaceChild(
                "halo_z_minus",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(0, 0, 4 + cubeDeformation.growZ, 0, 0, 0)
        );

        haloZMinus.addOrReplaceChild(
                "halo_z_minus_up", CubeListBuilder.create().texOffs(12, 0).addBox(
                        -4,
                        0,
                        cubeDeformation.growZ / 2,
                        8,
                        0,
                        4,
                        cubeDeformation.extend(0, -cubeDeformation.growY, -cubeDeformation.growZ / 2)
                ), PartPose.offsetAndRotation(0, 0, 0, (float) (Math.PI / 8), 0, 0)
        );

        haloZMinus.addOrReplaceChild(
                "halo_z_minus_down", CubeListBuilder.create().texOffs(12, 4).addBox(
                        -4,
                        0,
                        cubeDeformation.growZ / 2,
                        8,
                        0,
                        4,
                        cubeDeformation.extend(0, -cubeDeformation.growY, -cubeDeformation.growZ / 2)
                ), PartPose.offsetAndRotation(0, 0, 0, -(float) (Math.PI / 8), 0, 0)
        );


        PartDefinition haloXPlus = halo.addOrReplaceChild(
                "halo_x_plus",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(-4 - cubeDeformation.growZ, 0, 0, 0, (float) Math.PI, 0)
        );

        haloXPlus.addOrReplaceChild(
                "halo_x_plus_up", CubeListBuilder.create().texOffs(8, 16).addBox(
                        cubeDeformation.growZ / 2,
                        0,
                        -4,
                        4,
                        0,
                        8,
                        cubeDeformation.extend(-cubeDeformation.growX / 2, -cubeDeformation.growY, 0)
                ), PartPose.offsetAndRotation(0, 0, 0, 0, 0, -(float) Math.PI / 8)
        );

        haloXPlus.addOrReplaceChild(
                "halo_x_plus_down", CubeListBuilder.create().texOffs(8, 24).addBox(
                        cubeDeformation.growZ / 2,
                        0,
                        -4,
                        4,
                        0,
                        8,
                        cubeDeformation.extend(-cubeDeformation.growX / 2, -cubeDeformation.growY, 0)
                ), PartPose.offsetAndRotation(0, 0, 0, 0, 0, (float) Math.PI / 8)
        );

        PartDefinition haloXMinus = halo.addOrReplaceChild(
                "halo_x_minus",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(4 + cubeDeformation.growX, 0, 0, 0, 0, 0)
        );

        haloXMinus.addOrReplaceChild(
                "halo_x_minus_up", CubeListBuilder.create().texOffs(8, 16).addBox(
                        cubeDeformation.growX / 2,
                        0,
                        -4,
                        4,
                        0,
                        8,
                        cubeDeformation.extend(-cubeDeformation.growX / 2, -cubeDeformation.growY, 0)
                ), PartPose.offsetAndRotation(0, 0, 0, 0, 0, -(float) (Math.PI / 8))
        );

        haloXMinus.addOrReplaceChild(
                "halo_x_minus_down", CubeListBuilder.create().texOffs(8, 24).addBox(
                        cubeDeformation.growX / 2,
                        0,
                        -4,
                        4,
                        0,
                        8,
                        cubeDeformation.extend(-cubeDeformation.growX / 2, -cubeDeformation.growY, 0)
                ), PartPose.offsetAndRotation(0, 0, 0, 0, 0, (float) (Math.PI / 8))
        );

        root.addOrReplaceChild(
                "body",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F + yOffset, 0.0F)
        );

        PartDefinition rightArm = root.addOrReplaceChild(
                "right_arm",
                CubeListBuilder.create(),
                PartPose.offset(-5.0F, 2.0F + yOffset, 0.0F)
        );

        rightArm.addOrReplaceChild(
                "right_arm_spikes",
                CubeListBuilder.create().mirror().texOffs(0, 8).addBox(
                        -7 - (2F) * cubeDeformation.growX,
                        -2,
                        0,
                        4,
                        12,
                        0,
                        cubeDeformation.extend(0, 0, -cubeDeformation.growZ)
                ),
                PartPose.ZERO
        );

        PartDefinition leftArm = root.addOrReplaceChild(
                "left_arm",
                CubeListBuilder.create(),
                PartPose.offset(5.0F, 2.0F + yOffset, 0.0F)
        );


        leftArm.addOrReplaceChild(
                "left_arm_spikes", CubeListBuilder.create().texOffs(0, 8).addBox(
                        3 + (2F) * cubeDeformation.growX,
                        -2,
                        0,
                        4,
                        12,
                        0,
                        cubeDeformation.extend(0, 0, -cubeDeformation.growZ)
                ), PartPose.ZERO
        );

        final float weirdLegOffset = 0.090F;

        PartDefinition rightLeg = root.addOrReplaceChild(
                "right_leg",
                CubeListBuilder.create(),
                PartPose.offset(-1.9F, 12.0F + yOffset, 0.0F)
        );

        rightLeg.addOrReplaceChild(
                "right_leg_spikes",
                CubeListBuilder.create().mirror().texOffs(8, 8).addBox(
                        weirdLegOffset - 6 - (2F) * cubeDeformation.growX,
                        0,
                        0,
                        4,
                        12,
                        0,
                        cubeDeformation.extend(0, 0, -cubeDeformation.growZ)
                ),
                PartPose.ZERO
        );

        PartDefinition leftLeg = root.addOrReplaceChild(
                "left_leg",
                CubeListBuilder.create(),
                PartPose.offset(1.9F, 12.0F + yOffset, 0.0F)
        );

        leftLeg.addOrReplaceChild(
                "left_leg_spikes", CubeListBuilder.create().texOffs(8, 8).addBox(
                        -weirdLegOffset + 2F + (2F) * cubeDeformation.growX,
                        0,
                        0,
                        4,
                        12,
                        0,
                        cubeDeformation.extend(0, 0, -cubeDeformation.growZ)
                ), PartPose.ZERO
        );
        return mesh;
    }

    public static ArmorModelSet<MeshDefinition> createArmorMeshSet(@NotNull CubeDeformation innerArmorCubeDeformation, @NotNull CubeDeformation outerArmorCubeDeformation) {
        return createArmorMeshSet(
                HumanoidSpikesModel::createBaseArmorMesh,
                innerArmorCubeDeformation,
                outerArmorCubeDeformation
        );
    }

    protected static ArmorModelSet<MeshDefinition> createArmorMeshSet(Function<CubeDeformation, MeshDefinition> meshCreator, @NotNull CubeDeformation innerCubeDeformation, @NotNull CubeDeformation outerCubeDeformation) {
        MeshDefinition head = meshCreator.apply(outerCubeDeformation);
        head.getRoot().retainPartsAndChildren(Set.of("head"));
        MeshDefinition body = meshCreator.apply(outerCubeDeformation);
        body.getRoot().retainPartsAndChildren(Set.of("body", "left_arm", "right_arm"));
        MeshDefinition legs = meshCreator.apply(innerCubeDeformation);
        legs.getRoot().retainPartsAndChildren(Set.of("left_leg", "right_leg", "body"));
        MeshDefinition feet = meshCreator.apply(outerCubeDeformation);
        feet.getRoot().retainPartsAndChildren(Set.of("left_leg", "right_leg"));
        return new ArmorModelSet<>(head, body, legs, feet);
    }

    private static MeshDefinition createBaseArmorMesh(CubeDeformation cubeDeformation) {
        MeshDefinition mesh = createMesh(cubeDeformation, 0.0F);
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.0F));
        return mesh;
    }
}
