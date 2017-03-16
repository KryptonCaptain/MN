package com.trinarybrain.magianaturalis.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class ModelTaintBreeder
  extends ModelBase
{
  public ModelRenderer head;
  public ModelRenderer body;
  public ModelRenderer rearEnd0;
  public ModelRenderer rearEnd1;
  public ModelRenderer leg8;
  public ModelRenderer leg6;
  public ModelRenderer leg4;
  public ModelRenderer leg2;
  public ModelRenderer leg7;
  public ModelRenderer leg5;
  public ModelRenderer leg3;
  public ModelRenderer leg1;
  public ModelRenderer headClaw;
  public ModelRenderer headClaw2;
  public ModelRenderer eye0;
  public ModelRenderer eye1;
  
  public ModelTaintBreeder()
  {
    this.textureWidth = 64;
    this.textureHeight = 64;
    
    this.head = new ModelRenderer(this, 32, 4);
    this.head.addBox(-2.5F, -4.0F, -5.0F, 5, 6, 6);
    this.head.setRotationPoint(0.0F, 20.0F, -3.0F);
    this.head.setTextureSize(64, 64);
    setRotation(this.head, 0.0698132F, 0.0F, 0.0F);
    
    this.body = new ModelRenderer(this, 0, 0);
    this.body.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 8);
    this.body.setRotationPoint(0.0F, 20.0F, 0.0F);
    this.body.setTextureSize(64, 64);
    setRotation(this.body, 0.1396263F, 0.0F, 0.0F);
    
    this.rearEnd0 = new ModelRenderer(this, 0, 14);
    this.rearEnd0.addBox(-5.0F, -4.0F, 0.0F, 10, 9, 12);
    this.rearEnd0.setRotationPoint(0.0F, 19.0F, 3.0F);
    this.rearEnd0.setTextureSize(64, 64);
    setRotation(this.rearEnd0, 0.3490659F, 0.0F, 0.0F);
    
    this.leg8 = new ModelRenderer(this, 21, 0);
    this.leg8.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2);
    this.leg8.setRotationPoint(4.0F, 20.0F, -1.0F);
    this.leg8.setTextureSize(64, 64);
    
    this.leg6 = new ModelRenderer(this, 21, 0);
    this.leg6.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2);
    this.leg6.setRotationPoint(4.0F, 20.0F, 0.0F);
    this.leg6.setTextureSize(64, 64);
    
    this.leg4 = new ModelRenderer(this, 21, 0);
    this.leg4.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2);
    this.leg4.setRotationPoint(4.0F, 20.0F, 1.0F);
    this.leg4.setTextureSize(64, 64);
    
    this.leg2 = new ModelRenderer(this, 21, 0);
    this.leg2.addBox(-1.0F, -1.0F, -1.0F, 16, 2, 2);
    this.leg2.setRotationPoint(4.0F, 20.0F, 2.0F);
    this.leg2.setTextureSize(64, 64);
    
    this.leg7 = new ModelRenderer(this, 21, 0);
    this.leg7.mirror = true;
    this.leg7.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2);
    this.leg7.setRotationPoint(-4.0F, 20.0F, -1.0F);
    this.leg7.setTextureSize(64, 64);
    
    this.leg5 = new ModelRenderer(this, 21, 0);
    this.leg5.mirror = true;
    this.leg5.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2);
    this.leg5.setRotationPoint(-4.0F, 20.0F, 0.0F);
    this.leg5.setTextureSize(64, 64);
    
    this.leg3 = new ModelRenderer(this, 21, 0);
    this.leg3.mirror = true;
    this.leg3.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2);
    this.leg3.setRotationPoint(-4.0F, 20.0F, 1.0F);
    this.leg3.setTextureSize(64, 64);
    
    this.leg1 = new ModelRenderer(this, 21, 0);
    this.leg1.mirror = true;
    this.leg1.addBox(-15.0F, -1.0F, -1.0F, 16, 2, 2);
    this.leg1.setRotationPoint(-4.0F, 20.0F, 2.0F);
    this.leg1.setTextureSize(64, 64);
    
    this.headClaw = new ModelRenderer(this, 32, 16);
    this.headClaw.addBox(-1.5F, -2.5F, -11.0F, 3, 2, 7);
    this.headClaw.setRotationPoint(0.0F, 20.0F, -3.0F);
    this.headClaw.setTextureSize(64, 64);
    setRotation(this.headClaw, -0.0194155F, 0.0F, 0.0F);
    
    this.headClaw2 = new ModelRenderer(this, 44, 26);
    this.headClaw2.mirror = true;
    this.headClaw2.addBox(-1.5F, -0.5F, -11.0F, 3, 2, 7);
    this.headClaw2.setRotationPoint(0.0F, 20.0F, -3.0F);
    this.headClaw2.setTextureSize(64, 64);
    setRotation(this.headClaw2, 0.0400703F, 0.0F, 0.0F);
    
    this.eye0 = new ModelRenderer(this, 0, 35);
    this.eye0.mirror = true;
    this.eye0.addBox(-3.5F, -5.0F, -3.0F, 2, 4, 2);
    this.eye0.setRotationPoint(0.0F, 20.0F, -3.0F);
    this.eye0.setTextureSize(64, 64);
    setRotation(this.eye0, 0.0698132F, 0.0F, 0.0F);
    
    this.eye1 = new ModelRenderer(this, 0, 35);
    this.eye1.addBox(1.5F, -5.0F, -3.0F, 2, 4, 2);
    this.eye1.setRotationPoint(0.0F, 20.0F, -3.0F);
    this.eye1.setTextureSize(64, 64);
    setRotation(this.eye1, 0.0698132F, 0.0F, 0.0F);
    
    this.rearEnd1 = new ModelRenderer(this, 8, 35);
    this.rearEnd1.addBox(-3.5F, -3.0F, 12.0F, 7, 7, 2);
    this.rearEnd1.setRotationPoint(0.0F, 19.0F, 3.0F);
    this.rearEnd1.setTextureSize(64, 64);
    setRotation(this.rearEnd1, 0.3490659F, 0.0F, 0.0F);
  }
  
  public void render(Entity entity, float limbSwing, float prevLimbSwing, float rotationTicks, float rotationYaw, float rotationPitch, float scale)
  {
    GL11.glPushMatrix();
    setRotationAngles(limbSwing, prevLimbSwing, rotationTicks, rotationYaw, rotationPitch, scale, entity);
    GL11.glTranslatef(0.0F, -0.25F, 0.0F);
    this.head.render(scale);
    this.body.render(scale);
    this.leg8.render(scale);
    this.leg6.render(scale);
    this.leg4.render(scale);
    this.leg2.render(scale);
    this.leg7.render(scale);
    this.leg5.render(scale);
    this.leg3.render(scale);
    this.leg1.render(scale);
    this.headClaw.render(scale);
    this.headClaw2.render(scale);
    
    GL11.glEnable(3042);
    GL11.glBlendFunc(1, 771);
    this.eye0.render(scale);
    this.eye1.render(scale);
    
    EntityLiving entityLiving = (EntityLiving)entity;
    float health = Math.max(entityLiving.getMaxHealth() - entityLiving.getHealth(), 1.0F) * 0.5F;
    float bob = MathHelper.sin(rotationTicks) * 0.0133F * health;
    this.rearEnd0.rotateAngleY = bob;
    this.rearEnd1.rotateAngleY = bob;
    this.rearEnd0.rotateAngleX = (0.3490659F + bob);
    this.rearEnd1.rotateAngleX = (0.3490659F + bob);
    
    this.headClaw.rotateAngleX = (-0.0194155F + bob * 0.5F);
    this.headClaw2.rotateAngleX = (0.0194155F - bob * 0.25F);
    
    this.rearEnd0.render(scale);
    this.rearEnd1.render(scale);
    
    GL11.glDisable(3042);
    GL11.glPopMatrix();
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float limbSwing, float prevLimbSwing, float rotationTicks, float rotationYaw, float rotationPitch, float scale, Entity entity)
  {
    this.headClaw.rotateAngleY = (this.headClaw2.rotateAngleY = this.eye0.rotateAngleY = this.eye1.rotateAngleY = this.head.rotateAngleY = rotationYaw / 57.295776F);
    float f7 = 0.7853982F;
    this.leg1.rotateAngleZ = (-f7);
    this.leg2.rotateAngleZ = f7;
    this.leg3.rotateAngleZ = (-f7 * 0.74F);
    this.leg4.rotateAngleZ = (f7 * 0.74F);
    this.leg5.rotateAngleZ = (-f7 * 0.74F);
    this.leg6.rotateAngleZ = (f7 * 0.74F);
    this.leg7.rotateAngleZ = (-f7);
    this.leg8.rotateAngleZ = f7;
    float f8 = -0.0F;
    float f9 = 0.3926991F;
    this.leg1.rotateAngleY = (f9 * 2.0F + f8);
    this.leg2.rotateAngleY = (-f9 * 2.0F - f8);
    this.leg3.rotateAngleY = (f9 * 1.0F + f8);
    this.leg4.rotateAngleY = (-f9 * 1.0F - f8);
    this.leg5.rotateAngleY = (-f9 * 1.0F + f8);
    this.leg6.rotateAngleY = (f9 * 1.0F - f8);
    this.leg7.rotateAngleY = (-f9 * 2.0F + f8);
    this.leg8.rotateAngleY = (f9 * 2.0F - f8);
    float f10 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * prevLimbSwing;
    float f11 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * prevLimbSwing;
    float f12 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * prevLimbSwing;
    float f13 = -(MathHelper.cos(limbSwing * 0.6662F * 2.0F + 4.712389F) * 0.4F) * prevLimbSwing;
    float f14 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 0.0F) * 0.4F) * prevLimbSwing;
    float f15 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 3.1415927F) * 0.4F) * prevLimbSwing;
    float f16 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 1.5707964F) * 0.4F) * prevLimbSwing;
    float f17 = Math.abs(MathHelper.sin(limbSwing * 0.6662F + 4.712389F) * 0.4F) * prevLimbSwing;
    this.leg1.rotateAngleY += f10;
    this.leg2.rotateAngleY += -f10;
    this.leg3.rotateAngleY += f11;
    this.leg4.rotateAngleY += -f11;
    this.leg5.rotateAngleY += f12;
    this.leg6.rotateAngleY += -f12;
    this.leg7.rotateAngleY += f13;
    this.leg8.rotateAngleY += -f13;
    this.leg1.rotateAngleZ += f14;
    this.leg2.rotateAngleZ += -f14;
    this.leg3.rotateAngleZ += f15;
    this.leg4.rotateAngleZ += -f15;
    this.leg5.rotateAngleZ += f16;
    this.leg6.rotateAngleZ += -f16;
    this.leg7.rotateAngleZ += f17;
    this.leg8.rotateAngleZ += -f17;
  }
}
