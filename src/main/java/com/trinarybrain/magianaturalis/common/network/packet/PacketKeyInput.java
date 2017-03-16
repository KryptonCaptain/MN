package com.trinarybrain.magianaturalis.common.network.packet;

import com.trinarybrain.magianaturalis.common.core.Log;
import com.trinarybrain.magianaturalis.common.item.focus.ItemFocusBuild;
import com.trinarybrain.magianaturalis.common.util.FocusBuildHelper;
import com.trinarybrain.magianaturalis.common.util.FocusBuildHelper.Meta;
import com.trinarybrain.magianaturalis.common.util.FocusBuildHelper.Shape;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import org.apache.logging.log4j.Logger;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWandCasting;

public class PacketKeyInput
  implements IMessageHandler<KeyInputMessage, IMessage>
{
  public IMessage onMessage(KeyInputMessage message, MessageContext ctx)
  {
    if (ctx.side.isServer())
    {
      ItemStack stack = ctx.getServerHandler().playerEntity.inventory.getCurrentItem();
      if ((stack != null) && ((stack.getItem() instanceof ItemWandCasting)))
      {
        ItemWandCasting wand = (ItemWandCasting)stack.getItem();
        ItemFocusBasic focus = wand.getFocus(stack);
        if ((focus != null) && ((focus instanceof ItemFocusBuild)))
        {
          ItemStack focusStack = wand.getFocusItem(stack);
          switch (message.keyID)
          {
          case 2: 
            int i = FocusBuildHelper.getSize(focusStack) - 1;
            FocusBuildHelper.setSize(focusStack, i < 1 ? focus.getMaxAreaSize(focusStack) : i);
            break;
          case 3: 
            int j = FocusBuildHelper.getSize(focusStack) + 1;
            FocusBuildHelper.setSize(focusStack, j > focus.getMaxAreaSize(focusStack) ? 1 : j);
            break;
          case 4: 
            int k = FocusBuildHelper.getMeta(focusStack).ordinal();
            FocusBuildHelper.setMeta(focusStack, k == 0 ? FocusBuildHelper.Meta.UNIFORM : FocusBuildHelper.Meta.NONE);
            break;
          case 5: 
            int l = FocusBuildHelper.getShape(focusStack).ordinal() + 1;
            FocusBuildHelper.setShape(focusStack, l > 3 ? FocusBuildHelper.Shape.PLANE : FocusBuildHelper.getShapeByID(l));
            break;
          case 51: 
            int q = FocusBuildHelper.getShape(focusStack).ordinal() - 1;
            FocusBuildHelper.setShape(focusStack, q < 1 ? FocusBuildHelper.Shape.PLANE_EXTEND : FocusBuildHelper.getShapeByID(q));
            break;
          default: 
            Log.logger.error("Invalid KEY ID recieved.");
          }
        }
      }
    }
    return null;
  }
  
  public static class KeyInputMessage
    implements IMessage
  {
    private byte keyID;
    
    public KeyInputMessage() {}
    
    public KeyInputMessage(byte customKeyID)
    {
      this.keyID = customKeyID;
    }
    
    public void fromBytes(ByteBuf buf)
    {
      this.keyID = buf.readByte();
    }
    
    public void toBytes(ByteBuf buf)
    {
      buf.writeByte(this.keyID);
    }
  }
}
