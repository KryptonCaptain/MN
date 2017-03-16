package com.trinarybrain.magianaturalis.common.item;

import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.core.Log;
import com.trinarybrain.magianaturalis.common.util.Platform;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import org.apache.logging.log4j.Logger;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.lib.world.biomes.BiomeHandler;

public class BiomeDevTool
  extends Item
{
  public BiomeDevTool()
  {
    this.maxStackSize = 1;
    setCreativeTab(MagiaNaturalis.creativeTab);
  }
  
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
  {
    super.addInformation(stack, player, list, par4);
    list.add(EnumChatFormatting.DARK_PURPLE + "Last Biome: " + stack.getItemDamage());
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
    return stack;
  }
  
  public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
  {
    if (Platform.isClient()) {
      return false;
    }
    boolean ignore = false;
    if (ignore)
    {
      Log.logger.info(world.getBlock(x, y, z));
      Log.logger.info(Integer.valueOf(world.getBlockMetadata(x, y, z)));
    }
    else
    {
      String divider = "----------------------------------------------------";
      
      File mcDir = (File)cpw.mods.fml.relauncher.FMLInjectionData.data()[6];
      File modsDir = new File(mcDir, "magia_naturalis");
      if (!modsDir.exists()) {
        modsDir.mkdirs();
      }
      File mnDir = new File(mcDir, "magia_naturalis/Biome_Types-Aspects.txt");
      Throwable localThrowable6;
      String types;
      int aura;
      Aspect aspect;
      try
      {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mnDir)));localThrowable6 = null;
        try
        {
          String aspectName = "";
          types = "";
          aura = 0;
          for (BiomeDictionary.Type type : BiomeDictionary.Type.values())
          {
            types = "[" + type.toString() + "]";
            aspect = (Aspect)((List)BiomeHandler.biomeInfo.get(type)).get(1);
            aura = ((Integer)((List)BiomeHandler.biomeInfo.get(type)).get(0)).intValue();
            aura = Math.round(aura * 2.0F / 100.0F);
            if (aspect != null) {
              aspectName = "[" + aspect.getName() + "]";
            } else {
              aspectName = type == BiomeDictionary.Type.MAGICAL ? "[" + Aspect.MAGIC.getName() + "]" : "[NULL]";
            }
            String info = String.format("%-16s :\t%dx\t%s%n", new Object[] { types, Integer.valueOf(aura), aspectName });
            writer.write(info);
          }
        }
        catch (Throwable localThrowable1)
        {
          localThrowable6 = localThrowable1;throw localThrowable1;
        }
        finally
        {
          if (writer != null) {
            if (localThrowable6 != null) {
              try
              {
                writer.close();
              }
              catch (Throwable localThrowable2)
              {
                localThrowable6.addSuppressed(localThrowable2);
              }
            } else {
              writer.close();
            }
          }
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
      mnDir = new File(mcDir, "magia_naturalis/Biome_Composition.txt");
      try
      {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mnDir)));localThrowable6 = null;
        try
        {
          BiomeGenBase[] biomes = BiomeGenBase.getBiomeGenArray();
          types = biomes;aura = types.length;
          for (aspect = 0; aspect < aura; aspect++)
          {
            BiomeGenBase biome = types[aspect];
            if (biome != null)
            {
              String aspectName = "";
              String types = "";
              int aura = 0;
              String aspectCost = "";
              for (BiomeDictionary.Type type : BiomeDictionary.getTypesForBiome(biome))
              {
                types = "[" + type.toString() + "]";
                Aspect aspect = (Aspect)((List)BiomeHandler.biomeInfo.get(type)).get(1);
                aura = ((Integer)((List)BiomeHandler.biomeInfo.get(type)).get(0)).intValue();
                aura = Math.round(aura * 2.0F / 100.0F);
                if (aspect != null) {
                  aspectName = "[" + aspect.getName() + "]";
                } else {
                  aspectName = type == BiomeDictionary.Type.MAGICAL ? "[" + Aspect.MAGIC.getName() + "]" : "[NULL]";
                }
                aspectCost = aspectCost + String.format("%-16s :\t%dx\t%s%n", new Object[] { types, Integer.valueOf(aura), aspectName });
              }
              String info = String.format(divider + "%nBiome: %s%n%s", new Object[] { biome.biomeName, aspectCost });
              writer.write(info);
            }
          }
        }
        catch (Throwable localThrowable4)
        {
          localThrowable6 = localThrowable4;throw localThrowable4;
        }
        finally
        {
          if (writer != null) {
            if (localThrowable6 != null) {
              try
              {
                writer.close();
              }
              catch (Throwable localThrowable5)
              {
                localThrowable6.addSuppressed(localThrowable5);
              }
            } else {
              writer.close();
            }
          }
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    return false;
  }
}
