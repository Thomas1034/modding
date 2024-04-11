package com.thomas.cloudscape.entity.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thomas.cloudscape.entity.animations.ModAnimationDefinitions;
import com.thomas.cloudscape.entity.custom.WispEntity;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class WispModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart wisp;
	private final ModelPart head;

	public WispModel(ModelPart root) {
		super(RenderType::entityTranslucent);
		this.wisp = root.getChild("wisp");
		this.head = wisp.getChild("head");
	}

	@SuppressWarnings("unused")
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition wisp = partdefinition.addOrReplaceChild("wisp", CubeListBuilder.create(),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = wisp.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F,
				-2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition body = wisp.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(0, 10)
						.addBox(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 16)
						.addBox(-1.5F, 0.0F, -1.0F, 3.0F, 5.0F, 2.0F, new CubeDeformation(-0.2F)),
				PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition right_arm = wisp.addOrReplaceChild("right_arm",
				CubeListBuilder.create().texOffs(23, 0).addBox(-0.75F, -0.5F, -1.0F, 1.0F, 4.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-1.75F, -5.5F, 0.0F, 0.0F, 0.0F, 0.1309F));

		PartDefinition left_arm = wisp.addOrReplaceChild("left_arm",
				CubeListBuilder.create().texOffs(23, 6).addBox(-0.25F, -0.5F, -1.0F, 1.0F, 4.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(1.75F, -5.5F, 0.0F, 0.0F, 0.0F, -0.1309F));

		PartDefinition left_wing = wisp
				.addOrReplaceChild("left_wing",
						CubeListBuilder.create().texOffs(16, 14).addBox(0.0F, 1.0F, 0.0F, 0.0F, 5.0F, 8.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.5F, -6.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition right_wing = wisp.addOrReplaceChild("right_wing",
				CubeListBuilder.create().texOffs(16, 14).addBox(0.0F, 1.0F, 0.0F, 0.0F, 5.0F, 8.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-0.5F, -6.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		// deliberateError

		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		this.animateWalk(ModAnimationDefinitions.WISP_FLAP, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(((WispEntity) entity).idleAnimationState, ModAnimationDefinitions.WISP_IDLE, ageInTicks, 1f);
	}

	private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
		pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
		pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

		this.head.yRot = pNetHeadYaw * ((float) Math.PI / 180F);
		this.head.xRot = pHeadPitch * ((float) Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		wisp.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return wisp;
	}
}
