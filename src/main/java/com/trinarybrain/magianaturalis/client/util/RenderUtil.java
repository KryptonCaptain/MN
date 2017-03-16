package com.trinarybrain.magianaturalis.client.util;

import com.trinarybrain.magianaturalis.common.core.Log;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

public final class RenderUtil
{
  public static int RenderID = ;
  public static int RenderID2 = RenderingRegistry.getNextAvailableRenderId();
  
  public static void bindTexture(ResourceLocation resource)
  {
    Minecraft.getMinecraft().renderEngine.bindTexture(resource);
  }
  
  public static float getTicks()
  {
    return Minecraft.getMinecraft().renderViewEntity.ticksExisted;
  }
  
  public static EntityPlayer getRenderViewPlayer()
  {
    return (EntityPlayer)Minecraft.getMinecraft().renderViewEntity;
  }
  
  public static void drawTextureQuad(ResourceLocation rl, float w, float h)
  {
    Tessellator tessellator = Tessellator.instance;
    Minecraft.getMinecraft().renderEngine.bindTexture(rl);
    tessellator.startDrawingQuads();
    tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    tessellator.addVertexWithUV(0.0D, h, 0.0D, 0.0D, 1.0D);
    tessellator.addVertexWithUV(w, h, 0.0D, 1.0D, 1.0D);
    tessellator.addVertexWithUV(w, 0.0D, 0.0D, 1.0D, 0.0D);
    tessellator.draw();
    GL11.glEnable(3553);
  }
  
  public static void drawItemStack(RenderItem itemRender, FontRenderer fontRenderer, ItemStack stack, int x, int y)
  {
    itemRender.zLevel = 200.0F;
    RenderHelper.enableGUIStandardItemLighting();
    GL11.glDisable(2896);
    GL11.glEnable(32826);
    GL11.glEnable(2903);
    GL11.glEnable(2896);
    try
    {
      itemRender.renderItemAndEffectIntoGUI(fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, x, y);
    }
    catch (Exception e)
    {
      Log.logger.catching(e);
    }
    GL11.glDisable(2896);
  }
}
