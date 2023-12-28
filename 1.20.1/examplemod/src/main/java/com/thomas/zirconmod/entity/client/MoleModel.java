package com.thomas.zirconmod.entity.client;

// Made with Blockbench 4.9.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thomas.zirconmod.entity.animations.ModAnimationDefinitions;
import com.thomas.zirconmod.entity.custom.MoleEntity;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class MoleModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart mole;
	private final ModelPart head;

	public MoleModel(ModelPart root) {
		this.mole = root.getChild("mole");
		this.head = mole.getChild("head");
	}

	@SuppressWarnings("unused")
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition mole = partdefinition.addOrReplaceChild("mole", CubeListBuilder.create(),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition legs = mole.addOrReplaceChild("legs", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition middle = legs.addOrReplaceChild("middle", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition middle_left = middle.addOrReplaceChild("middle_left",
				CubeListBuilder.create().texOffs(30, 6).mirror()
						.addBox(0.0F, -0.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(-4.0F, -4.5F, -2.5F));

		PartDefinition middle_right = middle.addOrReplaceChild("middle_right", CubeListBuilder.create().texOffs(10, 25)
				.addBox(-2.0F, -0.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(4.0F, -4.5F, -2.5F));

		PartDefinition back = legs.addOrReplaceChild("back", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 8.0F));

		PartDefinition back_left = back.addOrReplaceChild("back_left",
				CubeListBuilder.create().texOffs(0, 25).mirror()
						.addBox(0.0F, -0.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(-4.0F, -4.5F, -2.5F));

		PartDefinition back_right = back.addOrReplaceChild("back_right", CubeListBuilder.create().texOffs(23, 22)
				.addBox(-2.0F, -0.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(4.0F, -4.5F, -2.5F));

		PartDefinition front = legs.addOrReplaceChild("front", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition front_left = front.addOrReplaceChild("front_left",
				CubeListBuilder.create().texOffs(27, 30).mirror()
						.addBox(0.0F, -0.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(-4.0F, -4.5F, -2.5F));

		PartDefinition front_right = front.addOrReplaceChild("front_right", CubeListBuilder.create().texOffs(17, 30)
				.addBox(-2.0F, -0.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(4.0F, -4.5F, -2.5F));

		PartDefinition body = mole.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(-3.0F, 0.0685F, -5.0747F, 6.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(31, 5)
						.addBox(-3.0F, -1.9315F, -5.0747F, 6.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -6.0685F, 2.0747F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1",
				CubeListBuilder.create().texOffs(0, 33)
						.addBox(-2.0F, -10.0F, -3.0F, 4.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(0, 12)
						.addBox(-2.0F, -7.0F, -3.0F, 4.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 6.0685F, -2.0747F, -0.1745F, 0.0F, 0.0F));

		PartDefinition head = mole.addOrReplaceChild("head", CubeListBuilder.create(),
				PartPose.offsetAndRotation(0.0F, -5.0F, -3.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition upper = head.addOrReplaceChild("upper", CubeListBuilder.create().texOffs(17, 12).addBox(-2.0F,
				-2.5F, -4.75F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, -0.25F));

		PartDefinition horn = upper.addOrReplaceChild("horn",
				CubeListBuilder.create().texOffs(0, 46)
						.addBox(-3.0F, -2.0F, -6.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(-7, 45)
						.addBox(-4.0F, 0.0F, -6.0F, 8.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(0, 48)
						.addBox(3.0F, -2.0F, -6.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, -2.5F, -0.75F, 0.3491F, 0.0F, 0.0F));

		PartDefinition lower = head.addOrReplaceChild("lower", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.5F, -0.25F));

		PartDefinition lower_r1 = lower
				.addOrReplaceChild("lower_r1",
						CubeListBuilder.create().texOffs(21, 0).addBox(-2.0F, -0.6F, -4.5F, 4.0F, 1.0F, 5.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0436F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		// deliberateError

		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		this.animateWalk(ModAnimationDefinitions.MOLE_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(((MoleEntity) entity).idleAnimationState, ModAnimationDefinitions.MOLE_IDLE, ageInTicks, 1f);
		this.animate(((MoleEntity) entity).attackAnimationState, ModAnimationDefinitions.MOLE_ATTACK, ageInTicks, 1f);
		this.animate(((MoleEntity) entity).sniffAnimationState, ModAnimationDefinitions.MOLE_SNIFF, ageInTicks, 1f);
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
		mole.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return mole;
	}
}