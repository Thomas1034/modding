package com.thomas.zirconmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thomas.zirconmod.entity.animations.ModAnimationDefinitions;
import com.thomas.zirconmod.entity.custom.NimbulaEntity;

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

public class NimbulaModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart nimbula;
	private final ModelPart head;

	public NimbulaModel(ModelPart root) {
		this.nimbula = root.getChild("nimbula");
		this.head = nimbula.getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition nimbula = partdefinition.addOrReplaceChild("nimbula", CubeListBuilder.create(),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = nimbula.addOrReplaceChild("head", CubeListBuilder.create().texOffs(56, 2).addBox(-1.0F,
				-13.0F, 10.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, -11.0F));

		PartDefinition body = nimbula.addOrReplaceChild("body", CubeListBuilder.create(),
				PartPose.offset(0.0F, 0.0F, 4.0F));

		PartDefinition bell = body.addOrReplaceChild("bell", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F,
				-5.5833F, -8.0F, 16.0F, 9.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -14.4167F, -4.0F));

		PartDefinition pillar = body.addOrReplaceChild("pillar",
				CubeListBuilder.create().texOffs(0, 25)
						.addBox(-5.0F, -10.0F, -9.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(0, 36)
						.addBox(-3.0F, -9.0F, -7.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition tendrils = pillar.addOrReplaceChild("tendrils", CubeListBuilder.create().texOffs(33, 29).addBox(
				-3.5F, -9.0F, -7.5F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right = tendrils.addOrReplaceChild("right", CubeListBuilder.create(),
				PartPose.offset(-4.75F, 0.0F, 0.0F));

		PartDefinition ar = right.addOrReplaceChild("ar", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.75F, -8.0F, -6.5F));

		PartDefinition ar2 = ar.addOrReplaceChild("ar2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition ar3 = ar2.addOrReplaceChild("ar3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition ar4 = ar3.addOrReplaceChild("ar4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition br = right.addOrReplaceChild("br", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.25F, -8.0F, -5.0F));

		PartDefinition br2 = br.addOrReplaceChild("br2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition br3 = br2.addOrReplaceChild("br3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition br4 = br3.addOrReplaceChild("br4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition cr = right.addOrReplaceChild("cr", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.25F, -8.0F, -3.0F));

		PartDefinition cr2 = cr.addOrReplaceChild("cr2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition cr3 = cr2.addOrReplaceChild("cr3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition cr4 = cr3.addOrReplaceChild("cr4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition dr = right.addOrReplaceChild("dr", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.75F, -8.0F, -1.5F));

		PartDefinition dr2 = dr.addOrReplaceChild("dr2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition dr3 = dr2.addOrReplaceChild("dr3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition dr4 = dr3.addOrReplaceChild("dr4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition left = tendrils.addOrReplaceChild("left", CubeListBuilder.create(),
				PartPose.offset(-1.25F, 0.0F, 0.0F));

		PartDefinition al = left.addOrReplaceChild("al", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.25F, -8.0F, -6.5F));

		PartDefinition al2 = al.addOrReplaceChild("al2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition al3 = al2.addOrReplaceChild("al3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition al4 = al3.addOrReplaceChild("al4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition bl = left.addOrReplaceChild("bl", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.75F, -8.0F, -5.0F));

		PartDefinition bl2 = bl.addOrReplaceChild("bl2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 1.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition bl3 = bl2.addOrReplaceChild("bl3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 0.0F));

		PartDefinition bl4 = bl3.addOrReplaceChild("bl4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition cl = left.addOrReplaceChild("cl", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.75F, -8.0F, -3.0F));

		PartDefinition cl2 = cl.addOrReplaceChild("cl2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition cl3 = cl2.addOrReplaceChild("cl3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition cl4 = cl3.addOrReplaceChild("cl4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition dl = left.addOrReplaceChild("dl", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.25F, -8.0F, -1.5F));

		PartDefinition dl2 = dl.addOrReplaceChild("dl2", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition dl3 = dl2.addOrReplaceChild("dl3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition dl4 = dl3.addOrReplaceChild("dl4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, 0.0F,
				-0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		// deliberateError

		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);

		this.animateWalk(ModAnimationDefinitions.NIMBULA_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(((NimbulaEntity) entity).idleAnimationState, ModAnimationDefinitions.NIMBULA_IDLE, ageInTicks, 1f);
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
		nimbula.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return nimbula;
	}

}