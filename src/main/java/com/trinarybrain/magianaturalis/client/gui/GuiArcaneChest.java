package com.trinarybrain.magianaturalis.client.gui;

import com.trinarybrain.magianaturalis.client.util.RenderUtil;
import com.trinarybrain.magianaturalis.common.container.ContainerArcaneChest;
import com.trinarybrain.magianaturalis.common.tile.TileArcaneChest;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiArcaneChest
  extends GuiContainer
{
  private static final ResourceLocation rl_gw = new ResourceLocation("magianaturalis", "textures/gui/container/chest_greatwood.png");
  private static final ResourceLocation rl_sw = new ResourceLocation("magianaturalis", "textures/gui/container/chest_silverwood.png");
  private TileArcaneChest chest;
  private int type;
  
  public GuiArcaneChest(InventoryPlayer invPlayer, TileArcaneChest tile)
  {
    super(new ContainerArcaneChest(invPlayer, tile));
    this.chest = tile;
    this.type = tile.getChestType();
    this.ySize = (this.type == 1 ? 222 : 240);
    if (this.type == 2) {
      this.xSize = 212;
    }
  }
  
  protected void drawGuiContainerForegroundLayer(int x, int y)
  {
    this.fontRendererObj.drawString(this.chest.getInventoryName(), 8, 6, 4210752);
    this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8 + (this.type - 1) * 18, this.ySize - 96 + 2, 4210752);
  }
  
  protected void drawGuiContainerBackgroundLayer(float f1, int x, int y)
  {
    RenderUtil.bindTexture(this.type == 1 ? rl_gw : rl_sw);
    int k = (this.width - this.xSize) / 2;
    int l = (this.height - this.ySize) / 2;
    drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
  }
}
