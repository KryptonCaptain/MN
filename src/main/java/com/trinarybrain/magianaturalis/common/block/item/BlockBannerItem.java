package com.trinarybrain.magianaturalis.common.block.item;

import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.tile.TileBannerCustom;
import com.trinarybrain.magianaturalis.common.util.Platform;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockBannerItem
  extends ItemBlock
{
  public BlockBannerItem(Block block)
  {
    super(block);
    setMaxDamage(0);
    setCreativeTab(MagiaNaturalis.creativeTab);
  }
  
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
  {
    list.add(EnumChatFormatting.DARK_PURPLE + Platform.translate("flavor.magianaturalis:banner"));
  }
  
  public int getMetadata(int meta)
  {
    return meta;
  }
  
  public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
  {
    boolean ret = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
    if (ret)
    {
      TileBannerCustom tile = (TileBannerCustom)world.getTileEntity(x, y, z);
      if (tile != null)
      {
        if (side <= 1)
        {
          int i = MathHelper.floor_double((player.rotationYaw + 180.0F) * 16.0F / 360.0F + 0.5D) & 0xF;
          tile.setFacing((byte)i);
        }
        else
        {
          tile.setWall(true);
          
          int i = 0;
          if (side == 2) {
            i = 8;
          }
          if (side == 4) {
            i = 4;
          }
          if (side == 5) {
            i = 12;
          }
          tile.setFacing((byte)i);
        }
        if (stack.hasTagCompound()) {
          if (stack.stackTagCompound.hasKey("color")) {
            tile.setColor(stack.stackTagCompound.getByte("color"));
          }
        }
        tile.markDirty();
        world.markBlockForUpdate(x, y, z);
      }
    }
    return ret;
  }
}
