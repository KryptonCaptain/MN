package com.trinarybrain.magianaturalis.client.render.entity;

import com.trinarybrain.magianaturalis.client.model.entity.ModelTrunkCorrupted;
import com.trinarybrain.magianaturalis.client.model.entity.ModelTrunkDemonic;
import com.trinarybrain.magianaturalis.client.model.entity.ModelTrunkSinister;
import com.trinarybrain.magianaturalis.client.model.entity.ModelTrunkTainted;
import com.trinarybrain.magianaturalis.common.entity.EntityEvilTrunk;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderEvilTrunk
  extends RenderLiving
{
  public ModelTrunkCorrupted modelTC = new ModelTrunkCorrupted();
  public ModelTrunkSinister modelTS = new ModelTrunkSinister();
  public ModelTrunkDemonic modelTD = new ModelTrunkDemonic();
  public ModelTrunkTainted modelTT = new ModelTrunkTainted();
  public static final ResourceLocation rlTC = new ResourceLocation("magianaturalis", "textures/models/trunk_corrupted.png");
  public static final ResourceLocation rlTD = new ResourceLocation("magianaturalis", "textures/models/trunk_demonic_wings.png");
  public static final ResourceLocation rlTS = new ResourceLocation("magianaturalis", "textures/models/trunk_sinister.png");
  public static final ResourceLocation rlTT = new ResourceLocation("magianaturalis", "textures/models/trunk_tainted.png");
  
  public RenderEvilTrunk()
  {
    super(new ModelTrunkCorrupted(), 0.5F);
  }
  
  protected void renderModel(EntityLivingBase entity, float x, float y, float z, float f1, float f2, float f3)
  {
    if (entity != null) {
      switch (((EntityEvilTrunk)entity).getTrunkType())
      {
      case 0: 
        this.mainModel = this.modelTC;
        break;
      case 1: 
        this.mainModel = this.modelTS;
        break;
      case 2: 
        this.mainModel = this.modelTD;
        break;
      case 3: 
        this.mainModel = this.modelTT;
      }
    }
    super.renderModel(entity, x, y, z, f1, f2, f3);
  }
  
  protected ResourceLocation getEntityTexture(EntityEvilTrunk entity)
  {
    switch (entity.getTrunkType())
    {
    case 0: 
      return rlTC;
    case 1: 
      return rlTS;
    case 2: 
      return rlTD;
    case 3: 
      return rlTT;
    }
    return rlTC;
  }
  
  protected ResourceLocation getEntityTexture(Entity entity)
  {
    return getEntityTexture((EntityEvilTrunk)entity);
  }
}
