package com.trinarybrain.magianaturalis.client.render.entity;

import com.trinarybrain.magianaturalis.client.model.entity.ModelTaintman;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderTaintman
  extends RenderLiving
{
  private static final ResourceLocation rl = new ResourceLocation("magianaturalis", "textures/models/taintman.png");
  
  public RenderTaintman()
  {
    super(new ModelTaintman(), 0.5F);
  }
  
  protected ResourceLocation getEntityTexture(Entity entity)
  {
    return rl;
  }
}
