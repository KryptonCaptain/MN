package com.trinarybrain.magianaturalis.common.core;

import com.trinarybrain.magianaturalis.common.item.focus.ItemFocusBuild;
import com.trinarybrain.magianaturalis.common.network.PacketHandler;
import com.trinarybrain.magianaturalis.common.network.packet.PacketKeyInput.KeyInputMessage;
import com.trinarybrain.magianaturalis.common.network.packet.PacketPickedBlock.PickedBlockMessage;
import com.trinarybrain.magianaturalis.common.util.WorldUtil;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWandCasting;

public class KeyHandler
{
  public KeyBinding keyMultSize = new KeyBinding("key.magianaturalis.size.add", 78, "key.categories.magianaturalis");
  public KeyBinding keySubSize = new KeyBinding("key.magianaturalis.size.sub", 74, "key.categories.magianaturalis");
  public KeyBinding keyMisc = new KeyBinding("key.magianaturalis.misc", 47, "key.categories.magianaturalis");
  public KeyBinding keyPickBlock = new KeyBinding("key.magianaturalis.pickBlock", -98, "key.categories.magianaturalis");
  
  public KeyHandler()
  {
    ClientRegistry.registerKeyBinding(this.keyMultSize);
    ClientRegistry.registerKeyBinding(this.keySubSize);
    ClientRegistry.registerKeyBinding(this.keyMisc);
    ClientRegistry.registerKeyBinding(this.keyPickBlock);
  }
  
  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void playerTick(TickEvent.PlayerTickEvent event)
  {
    if (event.side == Side.SERVER) {
      return;
    }
    if (event.phase == TickEvent.Phase.START)
    {
      byte id = 0;
      if (this.keySubSize.isPressed())
      {
        id = 2;
      }
      else if (this.keyMultSize.isPressed())
      {
        id = 3;
      }
      else if ((GuiScreen.isCtrlKeyDown()) && (this.keyMisc.isPressed()))
      {
        id = 4;
      }
      else if (this.keyMisc.isPressed())
      {
        id = 5;
      }
      else if (this.keyPickBlock.isPressed())
      {
        EntityPlayer player = event.player;
        if ((player != null) && (player.getCurrentEquippedItem() != null) && ((player.getCurrentEquippedItem().getItem() instanceof ItemWandCasting)))
        {
          ItemWandCasting wand = (ItemWandCasting)player.getCurrentEquippedItem().getItem();
          ItemFocusBasic focus = wand.getFocus(player.getCurrentEquippedItem());
          if ((focus != null) && ((focus instanceof ItemFocusBuild)))
          {
            MovingObjectPosition target = WorldUtil.getMovingObjectPositionFromPlayer(event.player.worldObj, event.player, ItemFocusBuild.reachDistance, true);
            if (target != null) {
              if (target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
              {
                int x = target.blockX;
                int y = target.blockY;
                int z = target.blockZ;
                
                Block block = event.player.worldObj.getBlock(x, y, z);
                byte meta = (byte)event.player.worldObj.getBlockMetadata(x, y, z);
                if (block == Blocks.double_plant) {
                  meta = (byte)block.getDamageValue(event.player.worldObj, x, y, z);
                }
                if ((meta < 0) || (meta > 15)) {
                  meta = 0;
                }
                PacketHandler.network.sendToServer(new PacketPickedBlock.PickedBlockMessage(Block.getIdFromBlock(block), meta));
              }
            }
          }
        }
      }
      if ((FMLClientHandler.instance().getClient().inGameHasFocus) && (id > 0))
      {
        EntityPlayer player = event.player;
        if ((player != null) && (player.getCurrentEquippedItem() != null) && ((player.getCurrentEquippedItem().getItem() instanceof ItemWandCasting)))
        {
          ItemWandCasting wand = (ItemWandCasting)player.getCurrentEquippedItem().getItem();
          ItemFocusBasic focus = wand.getFocus(player.getCurrentEquippedItem());
          if ((focus != null) && ((focus instanceof ItemFocusBuild))) {
            PacketHandler.network.sendToServer(new PacketKeyInput.KeyInputMessage(id));
          }
        }
      }
    }
  }
}
