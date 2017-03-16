package com.trinarybrain.magianaturalis.common.util.access;

import com.mojang.authlib.GameProfile;
import com.trinarybrain.magianaturalis.common.block.BlocksMN;
import com.trinarybrain.magianaturalis.common.tile.TileArcaneChest;
import com.trinarybrain.magianaturalis.common.util.Platform;
import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import thaumcraft.common.blocks.BlockArcaneDoor;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.tiles.TileOwned;

public final class TileAccess
{
  public static byte addPlayerToAccesList(GameProfile gameProfile, byte accesLevel, World world, int x, int y, int z)
  {
    Block block = world.getBlock(x, y, z);
    if (block == null) {
      return 0;
    }
    int metadata = world.getBlockMetadata(x, y, z);
    if (block == BlocksMN.arcaneChest)
    {
      TileEntity tile = world.getTileEntity(x, y, z);
      if (tile == null) {
        return 0;
      }
      if ((tile instanceof TileArcaneChest))
      {
        TileArcaneChest chest = (TileArcaneChest)tile;
        if (!chest.owner.equals(gameProfile.getId())) {
          if ((!chest.accessList.contains(new UserAccess(gameProfile.getId(), (byte)1))) && (!chest.accessList.contains(new UserAccess(gameProfile.getId(), (byte)2))) && (!chest.accessList.contains(new UserAccess(gameProfile.getId(), (byte)0))))
          {
            chest.accessList.add(new UserAccess(gameProfile.getId(), accesLevel));
            world.markBlockForUpdate(x, y, z);
            return 1;
          }
        }
      }
    }
    else
    {
      if (block == ConfigBlocks.blockArcaneDoor)
      {
        TileEntity[] tiles = new TileEntity[2];
        tiles[0] = world.getTileEntity(x, y, z);
        int offset = 1;
        int magic = ((BlockArcaneDoor)block).getFullMetadata(world, x, y, z);
        if ((magic & 0x8) != 0) {
          offset = -1;
        }
        tiles[1] = world.getTileEntity(x, y + offset, z);
        for (byte b = 0; b < 2; b = (byte)(b + 1)) {
          if ((tiles[b] != null) && ((tiles[b] instanceof TileOwned)))
          {
            TileOwned owned = (TileOwned)tiles[b];
            if ((!owned.owner.equals(gameProfile.getName())) && (!owned.accessList.contains("0" + gameProfile.getName())) && (!owned.accessList.contains("1" + gameProfile.getName())))
            {
              switch (accesLevel)
              {
              case 2: 
                accesLevel = 1; break;
              case 1: 
                accesLevel = 0;
              }
              owned.accessList.add(accesLevel + gameProfile.getName());
            }
          }
        }
        world.markBlockForUpdate(x, y, z);
        world.markBlockForUpdate(x, y + offset, z);
        return 2;
      }
      if (((block == ConfigBlocks.blockWoodenDevice) && ((metadata == 2) || (metadata == 3))) || (block != ConfigBlocks.blockWoodenDevice))
      {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile == null) {
          return -1;
        }
        if ((tile instanceof TileOwned))
        {
          TileOwned owned = (TileOwned)tile;
          if ((!owned.owner.equals(gameProfile.getName())) && (!owned.accessList.contains("0" + gameProfile.getName())) && (!owned.accessList.contains("1" + gameProfile.getName())))
          {
            switch (accesLevel)
            {
            case 2: 
              accesLevel = 1; break;
            case 1: 
              accesLevel = 0;
            }
            owned.accessList.add(accesLevel + gameProfile.getName());
            world.markBlockForUpdate(x, y, z);
            return 3;
          }
        }
      }
    }
    return 0;
  }
  
  public static boolean addPlayerToAccesList(EntityPlayer player, byte accesLevel, World world, int x, int y, int z)
  {
    byte result = addPlayerToAccesList(player.getGameProfile(), accesLevel, world, x, y, z);
    result = (byte)(result - 1);
    if (result > 0)
    {
      world.playSoundEffect(x, y, z, "thaumcraft:key", 1.0F, 0.9F);
      String[] word = { "chest", "door", "misc" };
      player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + Platform.translate(new StringBuilder().append("chat.magianaturalis:key.access.").append(word[result]).toString())));
      return true;
    }
    return false;
  }
}
