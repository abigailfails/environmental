package com.minecraftabnormals.environmental.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * ModelFennecFox - Undefined Created using Tabula 8.0.0
 */
@OnlyIn(Dist.CLIENT)
public class FennecFoxModel<T extends FoxEntity> extends AgeableModel<T> {
	public ModelRenderer head;
	public ModelRenderer legFrontRight;
	public ModelRenderer legFrontLeft;
	public ModelRenderer legBackRight;
	public ModelRenderer legBackLeft;
	public ModelRenderer body;
	public ModelRenderer rightEar;
	public ModelRenderer leftEar;
	public ModelRenderer tail;
	private float field_217125_n;

	public FennecFoxModel() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 17.0F, -5.0F);
		head.setTextureOffset(0, 0).addBox(-3.5F, -2.0F, -4.0F, 7.0F, 5.0F, 5.0F, 0.0F, false);
		head.setTextureOffset(0, 10).addBox(-1.5F, 1.0F, -7.0F, 3.0F, 2.0F, 3.0F, 0.0F, false);

		rightEar = new ModelRenderer(this);
		rightEar.setRotationPoint(0.0F, 0.0F, 5.0F);
		head.addChild(rightEar);
		setRotationAngle(rightEar, 0.0F, -0.4363F, -0.4363F);
		rightEar.setTextureOffset(24, 0).addBox(-5.1131F, -8.0F, -5.5315F, 3.0F, 6.0F, 1.0F, 0.0F, false);

		leftEar = new ModelRenderer(this);
		leftEar.setRotationPoint(0.0F, 0.0F, 5.0F);
		head.addChild(leftEar);
		setRotationAngle(leftEar, 0.0F, 0.4363F, 0.4363F);
		leftEar.setTextureOffset(32, 0).addBox(2.1131F, -8.0F, -5.5315F, 3.0F, 6.0F, 1.0F, 0.0F, false);

		legFrontRight = new ModelRenderer(this);
		legFrontRight.setRotationPoint(-3.6F, 19.0F, -2.9F);
		legFrontRight.setTextureOffset(12, 20).addBox(4.0F, 1.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		legFrontLeft = new ModelRenderer(this);
		legFrontLeft.setRotationPoint(3.6F, 19.0F, -2.9F);
		legFrontLeft.setTextureOffset(20, 20).addBox(-6.0F, 1.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		legBackRight = new ModelRenderer(this);
		legBackRight.setRotationPoint(-0.6F, 20.0F, 2.0F);
		legBackRight.setTextureOffset(28, 20).addBox(1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		legBackLeft = new ModelRenderer(this);
		legBackLeft.setRotationPoint(0.6F, 20.0F, 2.0F);
		legBackLeft.setTextureOffset(36, 20).addBox(-3.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 19.0F, -1.0F);
		setRotationAngle(body, 1.5708F, 0.0F, 0.0F);
		body.setTextureOffset(12, 10).addBox(-2.5F, -3.0F, -2.0F, 5.0F, 6.0F, 4.0F, 0.0F, false);

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 6.0F, 0.0F);
		body.addChild(tail);
		setRotationAngle(tail, -0.1047F, 0.0F, 0.0F);
		tail.setTextureOffset(0, 15).addBox(-1.5F, -2.9836F, -1.3136F, 3.0F, 8.0F, 3.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		ImmutableList.of(this.legBackLeft, this.legFrontLeft, this.head, this.body, this.legBackRight, this.legFrontRight).forEach((modelRenderer) -> {
			modelRenderer.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		});
	}

	@Override
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entityIn.isSleeping() && !entityIn.isStuck() && !entityIn.isCrouching()) {
			this.head.rotateAngleX = headPitch * ((float) Math.PI / 180F);
			this.head.rotateAngleY = netHeadYaw * ((float) Math.PI / 180F);
		}

		if (entityIn.isSleeping()) {
			this.head.rotateAngleX = 0.0F;
			this.head.rotateAngleY = -2.0943952F;
			this.head.rotateAngleZ = MathHelper.cos(ageInTicks * 0.027F) / 22.0F;
		}

		if (entityIn.isCrouching()) {
			float f = MathHelper.cos(ageInTicks) * 0.01F;
			this.body.rotateAngleY = f;
			this.legBackRight.rotateAngleZ = f;
			this.legBackLeft.rotateAngleZ = f;
			this.legFrontRight.rotateAngleZ = f / 2.0F;
			this.legFrontLeft.rotateAngleZ = f / 2.0F;
		}

		if (entityIn.isStuck()) {
			this.field_217125_n += 0.67F;
			this.legBackRight.rotateAngleX = MathHelper.cos(this.field_217125_n * 0.4662F) * 0.1F;
			this.legBackLeft.rotateAngleX = MathHelper.cos(this.field_217125_n * 0.4662F + (float) Math.PI) * 0.1F;
			this.legFrontRight.rotateAngleX = MathHelper.cos(this.field_217125_n * 0.4662F + (float) Math.PI) * 0.1F;
			this.legFrontLeft.rotateAngleX = MathHelper.cos(this.field_217125_n * 0.4662F) * 0.1F;
		}
	}

//	@Override
//	public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
//		this.body.rotateAngleX = ((float) Math.PI / 2F);
//		this.tail.rotateAngleX = -0.05235988F;
//		this.legBackRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
//		this.legBackLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
//		this.legFrontRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
//		this.legFrontLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
//		this.head.setRotationPoint(-1.0F, 16.5F, -3.0F);
//		this.head.rotateAngleY = 0.0F;
//		this.head.rotateAngleZ = entityIn.func_213475_v(partialTick);
//		this.legBackRight.showModel = true;
//		this.legBackLeft.showModel = true;
//		this.legFrontRight.showModel = true;
//		this.legFrontLeft.showModel = true;
//		this.body.setRotationPoint(0.0F, 16.0F, -6.0F);
//		this.body.rotateAngleZ = 0.0F;
//		this.legBackRight.setRotationPoint(-5.0F, 17.5F, 7.0F);
//		this.legBackLeft.setRotationPoint(-1.0F, 17.5F, 7.0F);
//		if (entityIn.isCrouching()) {
//			this.body.rotateAngleX = 1.6755161F;
//			float f = entityIn.func_213503_w(partialTick);
//			this.body.setRotationPoint(0.0F, 16.0F + entityIn.func_213503_w(partialTick), -6.0F);
//			this.head.setRotationPoint(-1.0F, 16.5F + f, -3.0F);
//			this.head.rotateAngleY = 0.0F;
//		} else if (entityIn.isSleeping()) {
//			this.body.rotateAngleZ = (-(float) Math.PI / 2F);
//			this.body.setRotationPoint(0.0F, 21.0F, -6.0F);
//			this.tail.rotateAngleX = -2.6179938F;
//			if (this.isChild) {
//				this.tail.rotateAngleX = -2.1816616F;
//				this.body.setRotationPoint(0.0F, 21.0F, -2.0F);
//			}
//
//			this.head.setRotationPoint(1.0F, 19.49F, -3.0F);
//			this.head.rotateAngleX = 0.0F;
//			this.head.rotateAngleY = -2.0943952F;
//			this.head.rotateAngleZ = 0.0F;
//			this.legBackRight.showModel = false;
//			this.legBackLeft.showModel = false;
//			this.legFrontRight.showModel = false;
//			this.legFrontLeft.showModel = false;
//		} else if (entityIn.isSitting()) {
//			this.body.rotateAngleX = ((float) Math.PI / 6F);
//			this.body.setRotationPoint(0.0F, 9.0F, -3.0F);
//			this.tail.rotateAngleX = ((float) Math.PI / 4F);
//			this.tail.setRotationPoint(-4.0F, 15.0F, -2.0F);
//			this.head.setRotationPoint(-1.0F, 10.0F, -0.25F);
//			this.head.rotateAngleX = 0.0F;
//			this.head.rotateAngleY = 0.0F;
//			if (this.isChild) {
//				this.head.setRotationPoint(-1.0F, 13.0F, -3.75F);
//			}
//
//			this.legBackRight.rotateAngleX = -1.3089969F;
//			this.legBackRight.setRotationPoint(-5.0F, 21.5F, 6.75F);
//			this.legBackLeft.rotateAngleX = -1.3089969F;
//			this.legBackLeft.setRotationPoint(-1.0F, 21.5F, 6.75F);
//			this.legFrontRight.rotateAngleX = -0.2617994F;
//			this.legFrontLeft.rotateAngleX = -0.2617994F;
//		}
//
//	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	protected Iterable<ModelRenderer> getHeadParts() {
		return ImmutableList.of(this.body, this.legBackRight, this.legFrontRight, this.legBackLeft, this.legFrontLeft);
	}

	@Override
	protected Iterable<ModelRenderer> getBodyParts() {
		return ImmutableList.of(this.head);
	}
}
