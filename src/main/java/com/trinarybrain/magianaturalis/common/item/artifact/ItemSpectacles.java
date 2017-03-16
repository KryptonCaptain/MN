package com.trinarybrain.magianaturalis.common.item.artifact;

import com.trinarybrain.api.ISpectacles;
import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.util.Platform;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import thaumcraft.api.IGoggles;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;

public class ItemSpectacles
  extends ItemArmor
  implements IRepairable, IVisDiscountGear, IRevealer, IGoggles, ISpectacles
{
  public ItemSpectacles()
  {
    super(ThaumcraftApi.armorMatSpecial, 4, 0);
    setMaxDamage(350);
    setCreativeTab(MagiaNaturalis.creativeTab);
  }
  
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
  {
    list.add(EnumChatFormatting.DARK_PURPLE + Platform.translate("tc.visdiscount") + ": " + getVisDiscount(stack, player, null) + "%");
  }
  
  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister ir)
  {
    this.itemIcon = ir.registerIcon("magianaturalis:spectacles");
  }
  
  public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
  {
    return "magianaturalis:textures/models/armorSpectacles.png";
  }
  
  public EnumRarity getRarity(ItemStack stack)
  {
    return EnumRarity.epic;
  }
  
  public boolean getIsRepairable(ItemStack stack, ItemStack stack2)
  {
    return stack2.isItemEqual(new ItemStack(Items.gold_ingot)) ? true : super.getIsRepairable(stack, stack2);
  }
  
  public boolean showIngamePopups(ItemStack stack, EntityLivingBase player)
  {
    return true;
  }
  
  public boolean showNodes(ItemStack stack, EntityLivingBase player)
  {
    return true;
  }
  
  public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect)
  {
    return 6;
  }
  
  public boolean drawSpectacleHUD(ItemStack itemstack, EntityLivingBase player)
  {
    return true;
  }
}
