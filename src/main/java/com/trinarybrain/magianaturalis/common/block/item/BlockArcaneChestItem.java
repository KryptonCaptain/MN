package com.trinarybrain.magianaturalis.common.block.item;

import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.util.NBTUtil;
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

public class BlockArcaneChestItem
  extends ItemBlock
{
  final String[] unlocal = { "unknown", "gw", "sw" };
  
  public BlockArcaneChestItem(Block block)
  {
    super(block);
    setMaxDamage(0);
    setHasSubtypes(true);
    setCreativeTab(MagiaNaturalis.creativeTab);
  }
  
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
  {
    NBTTagCompound data = NBTUtil.openNbtData(stack);
    if (data != null)
    {
      if (data.hasKey("Items"))
      {
        list.add("");
        list.add(EnumChatFormatting.DARK_GRAY + Platform.translate("hint.magianaturalis:chest.items"));
      }
      if (data.hasKey("AccessList"))
      {
        if (!data.hasKey("Items")) {
          list.add("");
        }
        list.add(EnumChatFormatting.DARK_GRAY + Platform.translate("hint.magianaturalis:chest.access"));
      }
    }
  }
  
  public int getMetadata(int meta)
  {
    return meta;
  }
  
  public String getUnlocalizedName(ItemStack stack)
  {
    return super.getUnlocalizedName() + "." + this.unlocal[stack.getItemDamage()];
  }
}
