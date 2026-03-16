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

        PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);


        head.addOrReplaceChild(
                "mohawk",
                CubeListBuilder.create()
                        .texOffs(0, -8)
                        .addBox(
                                0.0F,
                                -16.0F - cubeDeformation.growY,
                                -4.0F,
                                0.0F,
                                16.0F,
                                8.0F,
                                cubeDeformation.extend(-cubeDeformation.growX, cubeDeformation.growY, 0)
                        ),
                PartPose.ZERO
        );

        root.addOrReplaceChild(
                "body",
                CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, cubeDeformation),
                PartPose.offset(0.0F, 0.0F + yOffset, 0.0F)
        );
        root.addOrReplaceChild(
                "right_arm",
                CubeListBuilder.create()
                        .texOffs(40, 16)
                        .addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
                PartPose.offset(-5.0F, 2.0F + yOffset, 0.0F)
        );
        root.addOrReplaceChild(
                "left_arm",
                CubeListBuilder.create()
                        .texOffs(40, 16)
                        .mirror()
                        .addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
                PartPose.offset(5.0F, 2.0F + yOffset, 0.0F)
        );
        root.addOrReplaceChild(
                "right_leg",
                CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
                PartPose.offset(-1.9F, 12.0F + yOffset, 0.0F)
        );
        root.addOrReplaceChild(
                "left_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .mirror()
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation),
                PartPose.offset(1.9F, 12.0F + yOffset, 0.0F)
        );
        return mesh;
    }

    public static ArmorModelSet<MeshDefinition> createArmorMeshSet(@NotNull CubeDeformation innerArmorCubeDeformation, @NotNull CubeDeformation outerArmorCubeDeformation) {
        // TODO scaled by five for demonstration.
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
        body.getRoot().retainExactParts(Set.of("body", "left_arm", "right_arm"));
        MeshDefinition legs = meshCreator.apply(innerCubeDeformation);
        legs.getRoot().retainExactParts(Set.of("left_leg", "right_leg", "body"));
        MeshDefinition feet = meshCreator.apply(outerCubeDeformation);
        feet.getRoot().retainExactParts(Set.of("left_leg", "right_leg"));
        return new ArmorModelSet<>(head, body, legs, feet);
    }

    private static MeshDefinition createBaseArmorMesh(CubeDeformation cubeDeformation) {
        MeshDefinition mesh = createMesh(cubeDeformation, 0.0F);
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild(
                "right_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation.extend(-0.1F)),
                PartPose.offset(-1.9F, 12.0F, 0.0F)
        );
        root.addOrReplaceChild(
                "left_leg",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .mirror()
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, cubeDeformation.extend(-0.1F)),
                PartPose.offset(1.9F, 12.0F, 0.0F)
        );
        return mesh;
    }
}
