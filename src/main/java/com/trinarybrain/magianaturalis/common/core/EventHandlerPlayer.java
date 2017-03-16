package com.trinarybrain.magianaturalis.common.core;

import com.mojang.authlib.GameProfile;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemKey;
import com.trinarybrain.magianaturalis.common.util.NBTUtil;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.io.PrintStream;
import java.util.UUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;

public class EventHandlerPlayer
{
  @SubscribeEvent
  public void onAnvilRepair(AnvilRepairEvent event)
  {
    EntityPlayer player = event.entityPlayer;
    ItemStack stack = event.output;
    if ((player != null) && (stack != null)) {
      if ((stack.getItem() instanceof ItemKey))
      {
        NBTTagCompound data = NBTUtil.openNbtData(stack);
        boolean hasAccess = false;
        if ((data.hasKey("forger")) && (UUID.fromString(data.getString("forger")).equals(player.getGameProfile().getId()))) {
          hasAccess = true;
        } else if ((data.hasKey("owner")) && (UUID.fromString(data.getString("owner")).equals(player.getGameProfile().getId())) && (data.hasKey("accessLevel")) && (data.getByte("accessLevel") > 0)) {
          hasAccess = true;
        }
        if (hasAccess)
        {
          System.out.print("\nHAS ACCESS");
          if (data.hasKey("display", 10))
          {
            NBTTagCompound tempData = data.getCompoundTag("display");
            if (tempData.hasKey("Name", 8))
            {
              String str = tempData.getString("Name");
              System.out.print("\n" + str);
              str = str.substring(2, str.length());
              System.out.print("\n" + str);
            }
          }
        }
      }
    }
  }
  
  @SubscribeEvent
  public void onAnvilUpdate(AnvilUpdateEvent event)
  {
    if ((event.left.getItem() != null) && (event.right == null)) {
      if (!(event.left.getItem() instanceof ItemKey)) {}
    }
  }
}
