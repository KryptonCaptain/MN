package com.trinarybrain.magianaturalis.client.render.block;

import com.trinarybrain.magianaturalis.client.util.RenderUtil;
import com.trinarybrain.magianaturalis.common.block.BlockArcaneChest;
import com.trinarybrain.magianaturalis.common.block.BlockBanner;
import com.trinarybrain.magianaturalis.common.block.BlockGeoMorpher;
import com.trinarybrain.magianaturalis.common.block.BlockTranscribingTable;
import com.trinarybrain.magianaturalis.common.tile.TileArcaneChest;
import com.trinarybrain.magianaturalis.common.tile.TileBannerCustom;
import com.trinarybrain.magianaturalis.common.tile.TileGeoMorpher;
import com.trinarybrain.magianaturalis.common.tile.TileTranscribingTable;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class BlockRenderer
  implements ISimpleBlockRenderingHandler
{
  public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
  {
    GL11.glPushMatrix();
    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
    if ((block instanceof BlockTranscribingTable))
    {
      TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileTranscribingTable(), 0.0D, 0.0D, 0.0D, 0.0F);
    }
    else if ((block instanceof BlockArcaneChest))
    {
      TileArcaneChest tile = new TileArcaneChest();
      tile.setChestType((byte)metadata);
      TileEntityRendererDispatcher.instance.renderTileEntityAt(tile, 0.0D, 0.0D, 0.0D, 0.0F);
    }
    else if ((block instanceof BlockBanner))
    {
      GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
      GL11.glTranslatef(-1.0F, -0.5F, 0.0F);
      TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileBannerCustom(), 0.0D, 0.0D, 0.0D, 0.0F);
    }
    else if ((block instanceof BlockGeoMorpher))
    {
      TileGeoMorpher tile = new TileGeoMorpher();
      tile.idle = true;
      TileEntityRendererDispatcher.instance.renderTileEntityAt(tile, 0.0D, 0.0D, 0.0D, 0.0F);
    }
    GL11.glEnable(32826);
    GL11.glPopMatrix();
  }
  
  public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
  {
    return false;
  }
  
  public boolean shouldRender3DInInventory(int modelId)
  {
    return true;
  }
  
  public int getRenderId()
  {
    return RenderUtil.RenderID;
  }
}
