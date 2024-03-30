package com.thomas.zirconmod.entity.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.world.entity.Entity;

public abstract class ModColorableHierarchicalModel<E extends Entity> extends HierarchicalModel<E> {

	private float r;
	private float g;
	private float b;
	private float a;

	public void setColor(float r, float g, float b) {
		this.setColor(r, g, b, this.a);
	}

	public void multColor(float r, float g, float b) {
		this.r *= r;
		this.g *= g;
		this.b *= b;
	}

	public void multColor(float r, float g, float b, float a) {
		this.r *= r;
		this.g *= g;
		this.b *= b;
		this.a *= a;
	}

	public void setColor(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public void resetColor() {
		this.r = 1.0f;
		this.g = 1.0f;
		this.b = 1.0f;
		this.a = 1.0f;
	}

	public float[] getColor() {
		return new float[] { this.r, this.g, this.b, this.a };
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		this.root().render(poseStack, vertexConsumer, packedLight, packedOverlay, red * this.r, green * this.g,
				blue * this.b, alpha * this.a);
	}

}
