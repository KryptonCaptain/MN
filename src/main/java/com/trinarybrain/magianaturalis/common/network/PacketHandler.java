package com.trinarybrain.magianaturalis.common.network;

import com.trinarybrain.magianaturalis.common.network.packet.PacketBiomeChange;
import com.trinarybrain.magianaturalis.common.network.packet.PacketBiomeChange.BiomeChangeMessage;
import com.trinarybrain.magianaturalis.common.network.packet.PacketKeyInput;
import com.trinarybrain.magianaturalis.common.network.packet.PacketKeyInput.KeyInputMessage;
import com.trinarybrain.magianaturalis.common.network.packet.PacketPickedBlock;
import com.trinarybrain.magianaturalis.common.network.packet.PacketPickedBlock.PickedBlockMessage;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler
{
  public static SimpleNetworkWrapper network;
  private static int nextPacketID = 0;
  
  public static void initPackets()
  {
    network = NetworkRegistry.INSTANCE.newSimpleChannel("magianaturalis".toUpperCase());
    network.registerMessage(PacketKeyInput.class, PacketKeyInput.KeyInputMessage.class, nextPacketID++, Side.SERVER);
    network.registerMessage(PacketPickedBlock.class, PacketPickedBlock.PickedBlockMessage.class, nextPacketID++, Side.SERVER);
    network.registerMessage(PacketBiomeChange.class, PacketBiomeChange.BiomeChangeMessage.class, nextPacketID++, Side.CLIENT);
  }
  
  private static void registerMessage(Class packet, Class message)
  {
    network.registerMessage(packet, message, nextPacketID, Side.CLIENT);
    network.registerMessage(packet, message, nextPacketID, Side.SERVER);
    nextPacketID += 1;
  }
}
