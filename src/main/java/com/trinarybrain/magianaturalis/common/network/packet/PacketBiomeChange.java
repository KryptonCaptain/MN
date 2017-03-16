package com.trinarybrain.magianaturalis.common.network.packet;

import com.trinarybrain.magianaturalis.common.util.WorldUtil;

import com.trinarybrain.magianaturalis.common.network.packet.PacketBiomeChange.BiomeChangeMessage;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.biome.BiomeGenBase;

public class PacketBiomeChange
  implements IMessageHandler<BiomeChangeMessage, IMessage>
{
  @SideOnly(Side.CLIENT)
  public IMessage onMessage(BiomeChangeMessage message, MessageContext ctx)
  {
    if (ctx.side.isClient())
    {
      WorldUtil.setBiomeAt(Minecraft.getMinecraft().theWorld, message.posX, message.posZ, BiomeGenBase.getBiome(message.biomeID));
      
      int y = Minecraft.getMinecraft().theWorld.getTopSolidOrLiquidBlock(message.posX, message.posZ);
      Minecraft.getMinecraft().theWorld.markBlockForUpdate(message.posX, y, message.posZ);
    }
    return null;
  }
  
  public static class BiomeChangeMessage implements IMessage
  {
    private int posX;
    private int posZ;
    private short biomeID;
    
    public BiomeChangeMessage() {}
    
    public BiomeChangeMessage(int x, int z, short biomeId)
    {
      this.posX = x;
      this.posZ = z;
      this.biomeID = biomeId;
    }
    
    public void fromBytes(ByteBuf buf)
    {
      this.posX = buf.readInt();
      this.posZ = buf.readInt();
      this.biomeID = buf.readShort();
    }
    
    public void toBytes(ByteBuf buf)
    {
      buf.writeInt(this.posX);
      buf.writeInt(this.posZ);
      buf.writeShort(this.biomeID);
    }
  }
}
