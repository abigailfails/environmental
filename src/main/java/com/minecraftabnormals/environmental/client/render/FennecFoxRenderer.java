package com.minecraftabnormals.environmental.client.render;

import com.minecraftabnormals.environmental.client.model.FennecFoxModel;
import com.minecraftabnormals.environmental.client.render.layer.FennecFoxHeldItemLayer;
import com.minecraftabnormals.environmental.core.Environmental;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FennecFoxRenderer extends MobRenderer<FoxEntity, FennecFoxModel<FoxEntity>> {
	private static final ResourceLocation FENNEC_FOX = new ResourceLocation(Environmental.MODID, "textures/entity/fennec_fox.png");
	private static final ResourceLocation SLEEPING_FENNEC_FOX = new ResourceLocation(Environmental.MODID, "textures/entity/fennec_fox.png");

	public FennecFoxRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new FennecFoxModel<>(), 0.4F);
		this.addLayer(new FennecFoxHeldItemLayer(this));
	}
	
	@Override
	protected void applyRotations(FoxEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
		if (entityLiving.func_213480_dY() || entityLiving.isStuck()) {
			float f = -MathHelper.lerp(partialTicks, entityLiving.prevRotationPitch, entityLiving.rotationPitch);
			matrixStackIn.rotate(Vector3f.XP.rotationDegrees(f));
		}
	}

	@Override
	public ResourceLocation getEntityTexture(FoxEntity entity) {
		return entity.isSleeping() ? SLEEPING_FENNEC_FOX : FENNEC_FOX;
	}
}