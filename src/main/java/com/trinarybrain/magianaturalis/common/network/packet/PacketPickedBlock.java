package com.trinarybrain.magianaturalis.common.network.packet;

import com.trinarybrain.magianaturalis.common.item.focus.ItemFocusBuild;
import com.trinarybrain.magianaturalis.common.util.FocusBuildHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWandCasting;

public class PacketPickedBlock
  implements IMessageHandler<PickedBlockMessage, IMessage>
{
  public IMessage onMessage(PickedBlockMessage message, MessageContext ctx)
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
          Block block = Block.getBlockById(message.blockID);
          int meta = message.blockMeta;
          if ((block != null) && (meta >= 0) && (meta <= 15)) {
            FocusBuildHelper.setpickedBlock(wand.getFocusItem(stack), block, meta);
          }
        }
      }
    }
    return null;
  }
  
  public static class PickedBlockMessage
    implements IMessage
  {
    private int blockID;
    private byte blockMeta;
    
    public PickedBlockMessage() {}
    
    public PickedBlockMessage(int blockId, byte meta)
    {
      this.blockID = blockId;
      this.blockMeta = meta;
    }
    
    public void fromBytes(ByteBuf buf)
    {
      this.blockID = buf.readInt();
      this.blockMeta = buf.readByte();
    }
    
    public void toBytes(ByteBuf buf)
    {
      buf.writeInt(this.blockID);
      buf.writeByte(this.blockMeta);
    }
  }
}
