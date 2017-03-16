package com.trinarybrain.magianaturalis.common.item;

import com.mojang.authlib.GameProfile;
import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.core.Log;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemResearchLog;
import com.trinarybrain.magianaturalis.common.tile.TileArcaneChest;
import com.trinarybrain.magianaturalis.common.tile.TileTranscribingTable;
import com.trinarybrain.magianaturalis.common.util.Platform;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import org.apache.logging.log4j.Logger;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.nodes.NodeModifier;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.common.tiles.TileBanner;
import thaumcraft.common.tiles.TileOwned;

public class DevTool
  extends Item
{
  private byte modes;
  
  public DevTool()
  {
    this.modes = 2;
    this.maxStackSize = 1;
    setCreativeTab(MagiaNaturalis.creativeTab);
  }
  
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
  {
    super.addInformation(stack, player, list, par4);
    list.add(EnumChatFormatting.DARK_PURPLE + "Modus Operandi: " + stack.getItemDamage());
    if (stack.getItemDamage() == 0) {
      list.add(EnumChatFormatting.DARK_GRAY + "Do Nothing");
    } else if (stack.getItemDamage() == 1) {
      list.add(EnumChatFormatting.DARK_GRAY + "Debug Certain Tiles");
    } else if (stack.getItemDamage() == 2) {
      list.add(EnumChatFormatting.DARK_GRAY + "Remove Temp Warp");
    }
  }
  
  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister icon)
  {
    this.itemIcon = icon.registerIcon("magianaturalis:book_magia_natura");
  }
  
  public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
  {
    if (Platform.isClient()) {
      return stack;
    }
    if (player.isSneaking())
    {
      stack.setItemDamage(stack.getItemDamage() + 1);
      if (stack.getItemDamage() > this.modes) {
        stack.setItemDamage(0);
      }
      return stack;
    }
    if (stack.getItemDamage() == 2)
    {
      ThaumcraftApiHelper.addStickyWarpToPlayer(player, -100);
      ThaumcraftApiHelper.addWarpToPlayer(player, -100, true);
    }
    if (stack.getItemDamage() == 0)
    {
      Log.logger.info("Item: " + player.inventory.getStackInSlot(0));
      Log.logger.info("NBT: " + player.inventory.getStackInSlot(0).stackTagCompound);
    }
    return stack;
  }
  
  public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
  {
    if (Platform.isClient()) {
      return false;
    }
    if (stack.getItemDamage() == 1)
    {
      TileEntity tile = world.getTileEntity(x, y, z);
      if (tile == null) {
        return false;
      }
      if ((tile instanceof TileOwned))
      {
        TileOwned owned = (TileOwned)tile;
        Log.logger.info(String.format("%nX= %d, Y= %d, Z= %d%nOwner: %s%nAccessList: %s", new Object[] { Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z), owned.owner, owned.accessList.toString() }).toString());
        return true;
      }
      if ((tile instanceof TileArcaneChest))
      {
        TileArcaneChest chest = (TileArcaneChest)tile;
        Log.logger.info(String.format("%nX= %d, Y= %d, Z= %d%nOwner UUID: %s%nPlayer UUID: %s%nAccessList: %s", new Object[] { Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z), chest.owner.toString(), player.getGameProfile().getId(), chest.accessList.toString() }).toString());
        return true;
      }
      if ((tile instanceof TileTranscribingTable))
      {
        TileTranscribingTable table = (TileTranscribingTable)tile;
        ItemStack stack2 = table.getStackInSlot(0);
        if ((stack2.getItem() instanceof ItemResearchLog))
        {
          ItemResearchLog log = (ItemResearchLog)stack2.getItem();
          int i = 6;
          if (log.getResearchPoint(stack2, Aspect.AIR) < 64) {
            i--;
          }
          if (log.getResearchPoint(stack2, Aspect.EARTH) < 64) {
            i--;
          }
          if (log.getResearchPoint(stack2, Aspect.WATER) < 64) {
            i--;
          }
          if (log.getResearchPoint(stack2, Aspect.FIRE) < 64) {
            i--;
          }
          if (log.getResearchPoint(stack2, Aspect.ORDER) < 64) {
            i--;
          }
          if (log.getResearchPoint(stack2, Aspect.ENTROPY) < 64) {
            i--;
          }
          Log.logger.info("ResearchLog " + i + " of 6 RP Maxed");
          return true;
        }
      }
      else
      {
        if ((tile instanceof INode))
        {
          INode node = (INode)tile;
          if (player.isSneaking())
          {
            node.setNodeType(NodeType.PURE);
            node.setNodeModifier(NodeModifier.BRIGHT);
            
            node.setNodeVisBase(Aspect.FIRE, (short)1000);
            node.setNodeVisBase(Aspect.AIR, (short)1000);
            node.setNodeVisBase(Aspect.WATER, (short)1000);
            node.setNodeVisBase(Aspect.EARTH, (short)1000);
            node.setNodeVisBase(Aspect.ENTROPY, (short)1000);
            node.setNodeVisBase(Aspect.ORDER, (short)1000);
            
            AspectList aspects = node.getAspects();
            aspects.merge(Aspect.FIRE, 1000);
            aspects.merge(Aspect.AIR, 1000);
            aspects.merge(Aspect.WATER, 1000);
            aspects.merge(Aspect.EARTH, 1000);
            aspects.merge(Aspect.ENTROPY, 1000);
            aspects.merge(Aspect.ORDER, 1000);
            node.setAspects(aspects);
            
            world.markBlockForUpdate(x, y, z);
          }
          Log.logger.info("AspectBase: " + node.getAspectsBase().visSize());
          return true;
        }
        if ((tile instanceof TileBanner)) {
          Log.logger.info("Block: " + world.getBlock(x, y, z));
        }
      }
    }
    return false;
  }
}
