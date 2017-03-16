package com.trinarybrain.magianaturalis.common;

import com.trinarybrain.magianaturalis.common.core.CreativeTab;
import com.trinarybrain.magianaturalis.common.core.Log;
import com.trinarybrain.magianaturalis.common.network.PacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import thaumcraft.common.Thaumcraft;

@Mod(name="Magia Naturalis", modid="magianaturalis", version="0.1.5b", acceptedMinecraftVersions="[1.7.10]", dependencies="required-after:Thaumcraft")
public class MagiaNaturalis
{
  @Mod.Instance("magianaturalis")
  public static MagiaNaturalis instance;
  @SidedProxy(clientSide="com.trinarybrain.magianaturalis.client.core.ClientProxy", serverSide="com.trinarybrain.magianaturalis.common.core.CommonProxy")
  public static com.trinarybrain.magianaturalis.common.core.CommonProxy proxy;
  public static thaumcraft.common.CommonProxy proxyTC4;
  public static CreativeTabs creativeTab = new CreativeTab(CreativeTabs.getNextID(), "magianaturalis");
  
  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event)
  {
    Log.initLog();
    proxyTC4 = Thaumcraft.proxy;
    proxy.preInit(event);
  }
  
  @Mod.EventHandler
  public void init(FMLInitializationEvent event)
  {
    PacketHandler.initPackets();
    proxy.init(event);
  }
  
  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent event)
  {
    proxy.postInit(event);
  }
}
