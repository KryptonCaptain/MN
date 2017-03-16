package com.trinarybrain.magianaturalis.common.item.artifact;

import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.tile.TileGeoMorpher;
import com.trinarybrain.magianaturalis.common.util.NBTUtil;
import com.trinarybrain.magianaturalis.common.util.Platform;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import org.apache.commons.lang3.text.WordUtils;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.lib.world.biomes.BiomeHandler;

public class ItemBiomeSampler
  extends Item
{
  IIcon icon_overlay;
  
  public ItemBiomeSampler()
  {
    setMaxStackSize(1);
    setMaxDamage(0);
    setCreativeTab(MagiaNaturalis.creativeTab);
  }
  
  public String getItemStackDisplayName(ItemStack stack)
  {
    String name = NBTUtil.openNbtData(stack).getString("biomeName");
    return (name + StatCollector.translateToLocal(new StringBuilder().append(getUnlocalizedNameInefficiently(stack)).append(".name").toString())).trim();
  }
  
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
  {
    super.addInformation(stack, player, list, par4);
    if (GuiScreen.isCtrlKeyDown())
    {
      String[] aspects = getAspects(stack);
      if (aspects == null) {
        return;
      }
      for (int i = 0; i < aspects.length; i++) {
        if (aspects[i] != null) {
          list.add(aspects[i]);
        }
      }
    }
    else
    {
      list.add(EnumChatFormatting.DARK_GRAY + Platform.translate("hint.magianaturalis:ctrl"));
    }
  }
  
  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister icon)
  {
    this.itemIcon = icon.registerIcon("magianaturalis:report_base");
    this.icon_overlay = icon.registerIcon("magianaturalis:report_overlay");
  }
  
  @SideOnly(Side.CLIENT)
  public IIcon getIconFromDamageForRenderPass(int damage, int renderPass)
  {
    return renderPass == 0 ? this.itemIcon : this.icon_overlay;
  }
  
  @SideOnly(Side.CLIENT)
  public int getColorFromItemStack(ItemStack stack, int i)
  {
    int color = NBTUtil.openNbtData(stack).getInteger("color");
    color = i == 0 ? 16777215 : color;
    return color;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean requiresMultipleRenderPasses()
  {
    return true;
  }
  
  public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
  {
    if (Platform.isClient()) {
      return false;
    }
    if (player.isSneaking())
    {
      BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
      if (biome != null)
      {
        NBTTagCompound data = NBTUtil.openNbtData(stack);
        data.setString("biomeName", biome.biomeName + " ");
        data.setInteger("biomeID", biome.biomeID);
        data.setInteger("color", biome.color);
        
        BiomeDictionary.Type[] newTypes = BiomeDictionary.getTypesForBiome(biome);
        NBTTagList nbttaglist = new NBTTagList();
        NBTTagCompound tempData = new NBTTagCompound();
        for (int i = 0; i < newTypes.length; i++) {
          if (newTypes[i] != null) {
            if (newTypes[i] != BiomeDictionary.Type.MAGICAL)
            {
              Aspect aspect = (Aspect)((List)BiomeHandler.biomeInfo.get(newTypes[i])).get(1);
              if (aspect != null)
              {
                int aura = ((Integer)((List)BiomeHandler.biomeInfo.get(newTypes[i])).get(0)).intValue();
                tempData.setShort(aspect.getTag(), (short)Math.round(aura * 2.0F / 100.0F));
                nbttaglist.appendTag(tempData);
              }
            }
            else
            {
              tempData.setShort(Aspect.MAGIC.getTag(), (short)2);
              nbttaglist.appendTag(tempData);
            }
          }
        }
        data.setTag("aspects", nbttaglist);
        return true;
      }
    }
    else
    {
      TileEntity tile = world.getTileEntity(x, y, z);
      if ((tile instanceof TileGeoMorpher))
      {
        TileGeoMorpher geo = (TileGeoMorpher)tile;
        geo.cachedBiome = BiomeGenBase.getBiome(NBTUtil.openNbtData(stack).getInteger("biomeID"));
        world.markBlockForUpdate(x, y, z);
        return true;
      }
    }
    return false;
  }
  
  public String[] getAspects(ItemStack stack)
  {
    if (stack != null)
    {
      NBTTagCompound data = NBTUtil.openNbtData(stack);
      if (!data.hasKey("aspects")) {
        return null;
      }
      NBTTagList nbttaglist = data.getTagList("aspects", 10);
      
      String[] aspects = new String[nbttaglist.tagCount()];
      NBTTagCompound tempData;
      int j;
      for (int i = 0; i < nbttaglist.tagCount(); i++)
      {
        tempData = nbttaglist.getCompoundTagAt(i);
        Set keys = tempData.func_150296_c();
        j = 0;
        for (Object ob : keys) {
          if ((ob != null) && ((ob instanceof String)))
          {
            if (j >= nbttaglist.tagCount()) {
              break;
            }
            String aspectTag = (String)ob;
            
            String color = Aspect.getAspect(aspectTag).getChatcolor();
            if (color == null) {
              color = "5";
            }
            aspects[(j++)] = String.format("%dx %s%s", new Object[] { Short.valueOf(tempData.getShort(aspectTag)), String.valueOf('§') + color, WordUtils.capitalizeFully(aspectTag) });
          }
        }
      }
      return aspects;
    }
    return null;
  }
}
