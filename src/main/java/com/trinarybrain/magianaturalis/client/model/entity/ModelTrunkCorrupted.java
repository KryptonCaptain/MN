package com.trinarybrain.magianaturalis.client.model.entity;

import com.trinarybrain.magianaturalis.common.entity.EntityEvilTrunk;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelTrunkCorrupted
  extends ModelBase
{
  public ModelRenderer chestSkull;
  public ModelRenderer chestJaw;
  public ModelRenderer chestTooth1;
  public ModelRenderer chestToothTop1;
  public ModelRenderer chestTooth2;
  public ModelRenderer chestToothTop2;
  public ModelRenderer chestTooth3;
  public ModelRenderer chestTooth4;
  public ModelRenderer chestHorn1;
  public ModelRenderer chestHorn2;
  public ModelRenderer chestHorn3;
  public ModelRenderer chestHorn4;
  public ModelRenderer chestHornTop1;
  public ModelRenderer chestHornTop2;
  
  public ModelTrunkCorrupted()
  {
    this.chestSkull = new ModelRenderer(this, 0, 19).setTextureSize(64, 64);
    this.chestSkull.addBox(0.0F, -10.0F, -14.0F, 14, 10, 14);
    this.chestSkull.setRotationPoint(1.0F, 11.0F, 15.0F);
    
    this.chestJaw = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
    this.chestJaw.addBox(0.0F, 0.0F, -14.0F, 14, 5, 14);
    this.chestJaw.setRotationPoint(1.0F, 11.0F, 15.0F);
    
    this.chestTooth1 = new ModelRenderer(this, 7, 0).setTextureSize(64, 64);
    this.chestTooth1.addBox(-1.0F, -1.0F, -14.5F, 2, 3, 1);
    this.chestTooth1.setRotationPoint(13.0F, 11.0F, 15.0F);
    
    this.chestToothTop1 = new ModelRenderer(this, 9, 0).setTextureSize(64, 64);
    this.chestToothTop1.addBox(0.0F, -2.0F, -14.5F, 1, 1, 1);
    this.chestToothTop1.setRotationPoint(13.0F, 11.0F, 15.0F);
    
    this.chestTooth2 = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
    this.chestTooth2.addBox(-1.0F, -1.0F, -14.5F, 2, 3, 1);
    this.chestTooth2.setRotationPoint(3.0F, 11.0F, 15.0F);
    
    this.chestToothTop2 = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
    this.chestToothTop2.addBox(-1.0F, -2.0F, -14.5F, 1, 1, 1);
    this.chestToothTop2.setRotationPoint(3.0F, 11.0F, 15.0F);
    
    this.chestTooth3 = new ModelRenderer(this, 0, 0).setTextureSize(64, 64);
    this.chestTooth3.addBox(-0.7F, -1.0F, -14.5F, 2, 3, 1);
    this.chestTooth3.setRotationPoint(6.0F, 11.0F, 15.0F);
    
    this.chestTooth4 = new ModelRenderer(this, 7, 0).setTextureSize(64, 64);
    this.chestTooth4.addBox(-1.25F, -1.0F, -14.5F, 2, 3, 1);
    this.chestTooth4.setRotationPoint(10.0F, 11.0F, 15.0F);
    
    this.chestHorn1 = new ModelRenderer(this, 0, 5).setTextureSize(64, 64);
    this.chestHorn1.addBox(1.0F, -12.0F, -13.0F, 2, 2, 2);
    this.chestHorn1.setRotationPoint(1.0F, 11.0F, 15.0F);
    
    this.chestHorn2 = new ModelRenderer(this, 0, 5).setTextureSize(64, 64);
    this.chestHorn2.addBox(2.0F, -14.0F, -13.0F, 2, 2, 2);
    this.chestHorn2.setRotationPoint(1.0F, 11.0F, 15.0F);
    
    this.chestHorn3 = new ModelRenderer(this, 0, 5).setTextureSize(64, 64);
    this.chestHorn3.addBox(11.0F, -12.0F, -13.0F, 2, 2, 2);
    this.chestHorn3.setRotationPoint(1.0F, 11.0F, 15.0F);
    
    this.chestHorn4 = new ModelRenderer(this, 0, 5).setTextureSize(64, 64);
    this.chestHorn4.addBox(10.0F, -14.0F, -13.0F, 2, 2, 2);
    this.chestHorn4.setRotationPoint(1.0F, 11.0F, 15.0F);
    
    this.chestHornTop1 = new ModelRenderer(this, 3, 6).setTextureSize(64, 64);
    this.chestHornTop1.addBox(10.0F, -15.0F, -12.0F, 1, 1, 1);
    this.chestHornTop1.setRotationPoint(1.0F, 11.0F, 15.0F);
    
    this.chestHornTop2 = new ModelRenderer(this, 3, 6).setTextureSize(64, 64);
    this.chestHornTop2.addBox(3.0F, -15.0F, -12.0F, 1, 1, 1);
    this.chestHornTop2.setRotationPoint(1.0F, 11.0F, 15.0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    GL11.glPushMatrix();
    GL11.glTranslatef(-0.5F, 0.5F, -0.5F);
    if (entity != null)
    {
      float f7 = 1.0F - ((EntityEvilTrunk)entity).skullrot;
      f7 = 1.0F - f7 * f7 * f7;
      f7 = -(f7 * 3.141593F / 2.0F);
      
      this.chestSkull.rotateAngleX = f7;
      this.chestHorn1.rotateAngleX = f7;
      this.chestHorn2.rotateAngleX = f7;
      this.chestHorn3.rotateAngleX = f7;
      this.chestHorn4.rotateAngleX = f7;
      this.chestHornTop1.rotateAngleX = f7;
      this.chestHornTop2.rotateAngleX = f7;
    }
    float f6 = 0.0625F;
    this.chestSkull.render(f6);
    this.chestJaw.render(f6);
    this.chestTooth1.render(f6);
    this.chestToothTop1.render(f6);
    this.chestTooth2.render(f6);
    this.chestToothTop2.render(f6);
    this.chestTooth3.render(f6);
    this.chestTooth4.render(f6);
    this.chestHorn1.render(f6);
    this.chestHorn2.render(f6);
    this.chestHorn3.render(f6);
    this.chestHorn4.render(f6);
    this.chestHornTop1.render(f6);
    this.chestHornTop2.render(f6);
    
    GL11.glPopMatrix();
  }
}
