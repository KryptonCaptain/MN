package com.trinarybrain.magianaturalis.client.render.tile;

import com.trinarybrain.magianaturalis.client.util.RenderUtil;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemResearchLog;
import com.trinarybrain.magianaturalis.common.tile.TileTranscribingTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.renderers.models.ModelArcaneWorkbench;
import thaumcraft.common.blocks.BlockTable;
import thaumcraft.common.config.ConfigBlocks;

public class TileTranscribingTableRenderer
  extends TileEntitySpecialRenderer
{
  private static final ResourceLocation rl = new ResourceLocation("magianaturalis", "textures/models/transcribing_table.png");
  private ModelArcaneWorkbench tableModel = new ModelArcaneWorkbench();
  
  public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f)
  {
    renderTileEntityAt((TileTranscribingTable)tile, x, y, z, f);
  }
  
  public void renderTileEntityAt(TileTranscribingTable table, double x, double y, double z, float f)
  {
    GL11.glPushMatrix();
    RenderUtil.bindTexture(rl);
    GL11.glTranslatef((float)x + 0.5F, (float)y + 1.0F, (float)z + 0.5F);
    GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.tableModel.renderAll();
    GL11.glPopMatrix();
    if (table.getWorldObj() != null) {
      if ((table.getStackInSlot(0) != null) && ((table.getStackInSlot(0).getItem() instanceof ItemResearchLog)))
      {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x + 0.59F, (float)y + 1.02F, (float)z + 0.29F);
        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
        
        ItemStack stack = table.getStackInSlot(0).copy();
        stack.stackSize = 1;
        EntityItem entityitem = new EntityItem(table.getWorldObj(), 0.0D, 0.0D, 0.0D, stack);
        entityitem.hoverStart = 0.0F;
        RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
        GL11.glPopMatrix();
        
        float ticks = Minecraft.getMinecraft().renderViewEntity.ticksExisted + f;
        
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y + 1.0F, (float)z);
        GL11.glTranslatef(0.5F, 0.0F, 0.5F);
        GL11.glRotatef(ticks % 360.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.25F, 0.0F, -0.25F);
        
        IIcon icon = ((BlockTable)ConfigBlocks.blockTable).iconQuill;
        float f1 = icon.getMaxU();
        float f2 = icon.getMinV();
        float f3 = icon.getMinU();
        float f4 = icon.getMaxV();
        Tessellator tessellator = Tessellator.instance;
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        this.field_147501_a.field_147553_e.bindTexture(TextureMap.locationBlocksTexture);
        ItemRenderer.renderItemIn2D(tessellator, f1, f2, f3, f4, icon.getIconWidth(), icon.getIconHeight(), 0.025F);
        GL11.glPopMatrix();
      }
    }
  }
}
