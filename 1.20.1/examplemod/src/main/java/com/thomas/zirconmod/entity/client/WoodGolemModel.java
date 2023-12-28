package com.thomas.zirconmod.entity.client;

// Made with Blockbench 4.9.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thomas.zirconmod.entity.animations.ModAnimationDefinitions;
import com.thomas.zirconmod.entity.custom.WoodGolemEntity;

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

public class WoodGolemModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart wood_golem;
	private final ModelPart head;

	public WoodGolemModel(ModelPart root) {
		this.wood_golem = root.getChild("wood_golem");
		this.head = wood_golem.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition wood_golem = partdefinition.addOrReplaceChild("wood_golem", CubeListBuilder.create(),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition torso = wood_golem.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 20).addBox(
				-3.0F, -7.5F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.5F, 0.0F));

		PartDefinition legs = wood_golem.addOrReplaceChild("legs", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition front = legs.addOrReplaceChild("front", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, -1.0F));

		PartDefinition front_right = front.addOrReplaceChild("front_right",
				CubeListBuilder.create().texOffs(40, 0).mirror()
						.addBox(-0.5F, -1.0F, -2.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(-3.5F, -11.0F, -2.0F));

		PartDefinition front_left = front.addOrReplaceChild("front_left", CubeListBuilder.create().texOffs(40, 0)
				.addBox(-1.5F, -1.0F, -2.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(2.5F, -11.0F, -2.0F));

		PartDefinition back = legs.addOrReplaceChild("back", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 1.0F));

		PartDefinition back_right = back.addOrReplaceChild("back_right",
				CubeListBuilder.create().texOffs(40, 0).mirror()
						.addBox(-0.5F, -1.0F, -1.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offset(-3.5F, -11.0F, 2.0F));

		PartDefinition back_left = back.addOrReplaceChild("back_left", CubeListBuilder.create().texOffs(40, 0)
				.addBox(-2.5F, -1.0F, -1.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)),
				PartPose.offset(3.5F, -11.0F, 2.0F));

		PartDefinition arms = wood_golem.addOrReplaceChild("arms", CubeListBuilder.create().texOffs(0, 40).addBox(-4.0F,
				-3.0F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -19.0F, 0.0F));

		PartDefinition sides = arms.addOrReplaceChild("sides", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition folder5 = sides.addOrReplaceChild("folder5",
				CubeListBuilder.create().texOffs(32, 32).addBox(-1.5F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-3.5F, -1.0F, 2.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition folder6 = sides.addOrReplaceChild("folder6",
				CubeListBuilder.create().texOffs(32, 32).addBox(-1.5F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-3.5F, -1.0F, -2.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition folder7 = sides.addOrReplaceChild("folder7",
				CubeListBuilder.create().texOffs(32, 32).addBox(-0.5F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(3.5F, -1.0F, 2.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition folder8 = sides.addOrReplaceChild("folder8",
				CubeListBuilder.create().texOffs(32, 32).addBox(-0.5F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(3.5F, -1.0F, -2.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition front_and_back = arms.addOrReplaceChild("front_and_back", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition folder1 = front_and_back.addOrReplaceChild("folder1",
				CubeListBuilder.create().texOffs(32, 32).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 12.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.0F, -1.0F, -3.5F, -0.2618F, 0.0F, 0.0F));

		PartDefinition folder2 = front_and_back.addOrReplaceChild("folder2",
				CubeListBuilder.create().texOffs(32, 32).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 12.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(2.0F, -1.0F, -3.5F, -0.2618F, 0.0F, 0.0F));

		PartDefinition folder3 = front_and_back.addOrReplaceChild("folder3",
				CubeListBuilder.create().texOffs(32, 32).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 12.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-2.0F, -1.0F, 3.5F, 0.2618F, 0.0F, 0.0F));

		PartDefinition folder4 = front_and_back
				.addOrReplaceChild("folder4",
						CubeListBuilder.create().texOffs(32, 32).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 12.0F, 2.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(2.0F, -1.0F, 3.5F, 0.2618F, 0.0F, 0.0F));

		PartDefinition head = wood_golem.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F,
				-9.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -23.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);
		
		this.animateWalk(ModAnimationDefinitions.WOOD_GOLEM_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		// this.animate(((WoodGolemEntity) entity).idleAnimationState,
		// ModAnimationDefinitions.WOOD_GOLEM_IDLE, ageInTicks, 1f);
		this.animate(((WoodGolemEntity) entity).attackAnimationState, ModAnimationDefinitions.WOOD_GOLEM_ATTACK,
				ageInTicks, 1f);
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
		wood_golem.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return wood_golem;
	}
}