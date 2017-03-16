package com.trinarybrain.magianaturalis.client.render.item;

import com.trinarybrain.magianaturalis.client.model.entity.ModelTrunkCorrupted;
import com.trinarybrain.magianaturalis.client.model.entity.ModelTrunkDemonic;
import com.trinarybrain.magianaturalis.client.model.entity.ModelTrunkSinister;
import com.trinarybrain.magianaturalis.client.model.entity.ModelTrunkTainted;
import com.trinarybrain.magianaturalis.client.render.entity.RenderEvilTrunk;
import com.trinarybrain.magianaturalis.client.util.RenderUtil;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemEvilTrunkSpawner;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class RenderItemEvilTrunkSpawner
  implements IItemRenderer
{
  public ModelTrunkCorrupted modelTC = new ModelTrunkCorrupted();
  public ModelTrunkSinister modelTS = new ModelTrunkSinister();
  public ModelTrunkDemonic modelTD = new ModelTrunkDemonic();
  public ModelTrunkTainted modelTT = new ModelTrunkTainted();
  
  public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type)
  {
    return true;
  }
  
  public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper)
  {
    return true;
  }
  
  public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data)
  {
    GL11.glPushMatrix();
    GL11.glScalef(1.0F, -1.0F, -1.0F);
    if ((type == IItemRenderer.ItemRenderType.EQUIPPED) || (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON))
    {
      GL11.glTranslatef(-0.25F, -0.5F, -0.25F);
      if ((type == IItemRenderer.ItemRenderType.EQUIPPED) && (type != IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON))
      {
        GL11.glTranslatef(1.0F, 0.0F, 0.0F);
      }
      else
      {
        float angle = 90.0F;
        GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.25F, -0.25F, 0.25F);
      }
    }
    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
    GL11.glTranslatef(0.5F, -0.5F, 0.5F);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    if ((item.getItem() instanceof ItemEvilTrunkSpawner)) {
      switch (item.getItemDamage())
      {
      case 0: 
        RenderUtil.bindTexture(RenderEvilTrunk.rlTC);
        this.modelTC.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        break;
      case 1: 
        RenderUtil.bindTexture(RenderEvilTrunk.rlTS);
        this.modelTS.render(null, 0.0F, 0.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        break;
      case 2: 
        RenderUtil.bindTexture(RenderEvilTrunk.rlTD);
        this.modelTD.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        break;
      case 3: 
        RenderUtil.bindTexture(RenderEvilTrunk.rlTT);
        this.modelTT.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      }
    }
    GL11.glPopMatrix();
  }
}
