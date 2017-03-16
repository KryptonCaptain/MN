package com.trinarybrain.magianaturalis.common.util;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import java.util.UUID;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.common.CommonProxy;

public final class Platform
{
  public static boolean isClient()
  {
    return FMLCommonHandler.instance().getEffectiveSide().isClient();
  }
  
  public static boolean isServer()
  {
    return FMLCommonHandler.instance().getEffectiveSide().isServer();
  }
  
  public static World getClientWorld()
  {
    return MagiaNaturalis.proxyTC4.getClientWorld();
  }
  
  public static String translate(String str)
  {
    return StatCollector.translateToLocal(str);
  }
  
  public static GameProfile findGameProfileByName(String playerName)
  {
    return MinecraftServer.getServer().func_152358_ax().func_152655_a(playerName);
  }
  
  public static GameProfile findGameProfileByUUID(UUID uuid)
  {
    return MinecraftServer.getServer().func_152358_ax().func_152652_a(uuid);
  }
  
  public static UUID generateOfflineUUIDforName(String playerName)
  {
    return UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(Charsets.UTF_8));
  }
}
