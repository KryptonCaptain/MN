package com.trinarybrain.magianaturalis.common.item.artifact;

import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.util.NBTUtil;
import com.trinarybrain.magianaturalis.common.util.Platform;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.CommonProxy;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.playerdata.PacketAspectPool;
import thaumcraft.common.lib.research.PlayerKnowledge;
import thaumcraft.common.lib.research.ResearchManager;
import thaumcraft.common.tiles.TileDeconstructionTable;

public class ItemResearchLog
  extends Item
{
  public ItemResearchLog()
  {
    this.maxStackSize = 1;
    setCreativeTab(MagiaNaturalis.creativeTab);
  }
  
  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister icon)
  {
    this.itemIcon = icon.registerIcon("magianaturalis:book_magia_natura");
  }
  
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
  {
    super.addInformation(stack, player, list, par4);
    if (GuiScreen.isCtrlKeyDown())
    {
      list.add(String.format("%dx %s%s", new Object[] { Short.valueOf(getResearchPoint(stack, Aspect.AIR)), EnumChatFormatting.YELLOW, Aspect.AIR.getName() }));
      list.add(String.format("%dx %s%s", new Object[] { Short.valueOf(getResearchPoint(stack, Aspect.EARTH)), EnumChatFormatting.GREEN, Aspect.EARTH.getName() }));
      list.add(String.format("%dx %s%s", new Object[] { Short.valueOf(getResearchPoint(stack, Aspect.WATER)), EnumChatFormatting.AQUA, Aspect.WATER.getName() }));
      list.add(String.format("%dx %s%s", new Object[] { Short.valueOf(getResearchPoint(stack, Aspect.FIRE)), EnumChatFormatting.RED, Aspect.FIRE.getName() }));
      list.add(String.format("%dx %s%s", new Object[] { Short.valueOf(getResearchPoint(stack, Aspect.ORDER)), EnumChatFormatting.WHITE, Aspect.ORDER.getName() }));
      list.add(String.format("%dx %s%s", new Object[] { Short.valueOf(getResearchPoint(stack, Aspect.ENTROPY)), EnumChatFormatting.DARK_GRAY, Aspect.ENTROPY.getName() }));
    }
    else
    {
      list.add(EnumChatFormatting.DARK_GRAY + Platform.translate("hint.magianaturalis:ctrl"));
    }
  }
  
  public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
  {
    if (Platform.isClient()) {
      return stack;
    }
    if (stack == null) {
      return stack;
    }
    if (player.isSneaking()) {
      return stack;
    }
    Aspect[] array = { Aspect.AIR, Aspect.EARTH, Aspect.WATER, Aspect.FIRE, Aspect.ORDER, Aspect.ENTROPY };
    for (Aspect aspect : array)
    {
      short amount = NBTUtil.openNbtData(stack).getShort("rp:" + aspect.getTag());
      if ((amount > 0) && (MagiaNaturalis.proxyTC4.playerKnowledge.addAspectPool(player.getCommandSenderName(), aspect, amount)))
      {
        ResearchManager.scheduleSave(player);
        PacketHandler.INSTANCE.sendTo(new PacketAspectPool(aspect.getTag(), Short.valueOf(amount), Short.valueOf(Thaumcraft.proxy.playerKnowledge.getAspectPoolFor(player.getCommandSenderName(), aspect))), (EntityPlayerMP)player);
        setResearchPoint(stack, aspect, (short)0);
      }
    }
    return stack;
  }
  
  public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
  {
    if (Platform.isClient()) {
      return false;
    }
    if (!player.isSneaking()) {
      return false;
    }
    TileEntity tile = world.getTileEntity(x, y, z);
    if ((tile instanceof TileDeconstructionTable))
    {
      TileDeconstructionTable table = (TileDeconstructionTable)tile;
      boolean bool = addResearchPoint(stack, table.aspect, (short)1);
      if (bool)
      {
        table.aspect = null;
        world.markBlockForUpdate(x, y, z);
      }
      return bool;
    }
    return false;
  }
  
  public short getResearchPoint(ItemStack stack, Aspect aspect)
  {
    if ((stack != null) && (aspect != null)) {
      return NBTUtil.openNbtData(stack).getShort("rp:" + aspect.getTag());
    }
    return 0;
  }
  
  public boolean setResearchPoint(ItemStack stack, Aspect aspect, short size)
  {
    if ((stack != null) && (aspect != null))
    {
      if (size > 64) {
        return false;
      }
      if (size < 0) {
        size = 0;
      }
      NBTUtil.openNbtData(stack).setShort("rp:" + aspect.getTag(), size);
      return true;
    }
    return false;
  }
  
  public boolean addResearchPoint(ItemStack stack, Aspect aspect, short size)
  {
    if ((stack != null) && (aspect != null) && (size > 0))
    {
      size = (short)(size + NBTUtil.openNbtData(stack).getShort("rp:" + aspect.getTag()));
      if (size > 64) {
        return false;
      }
      NBTUtil.openNbtData(stack).setShort("rp:" + aspect.getTag(), size);
      return true;
    }
    return false;
  }
}
