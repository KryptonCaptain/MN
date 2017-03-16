package com.trinarybrain.magianaturalis.common.item.artifact;

import com.trinarybrain.api.IRevealInvisible;
import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
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
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import thaumcraft.api.IGoggles;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;

public class ItemGogglesDark
  extends ItemArmor
  implements IRepairable, IVisDiscountGear, IRevealer, IGoggles, IRevealInvisible
{
  public ItemGogglesDark()
  {
    super(ThaumcraftApi.armorMatSpecial, 4, 0);
    setMaxDamage(350);
    setCreativeTab(MagiaNaturalis.creativeTab);
  }
  
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
  {
    super.addInformation(stack, player, list, par4);
    list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.visdiscount") + ": " + getVisDiscount(stack, player, null) + "%");
    list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("tc.visdiscount") + " (Perditio): " + getVisDiscount(stack, player, Aspect.ENTROPY) + "%");
  }
  
  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister ir)
  {
    this.itemIcon = ir.registerIcon("magianaturalis:gogglesDarkCrystal");
  }
  
  public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
  {
    return "magianaturalis:textures/models/armorGogglesDark.png";
  }
  
  public EnumRarity getRarity(ItemStack itemstack)
  {
    return EnumRarity.rare;
  }
  
  public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
  {
    if (player.isPotionActive(Potion.blindness)) {
      player.removePotionEffect(Potion.blindness.id);
    }
  }
  
  public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player)
  {
    return true;
  }
  
  public boolean showNodes(ItemStack itemstack, EntityLivingBase player)
  {
    return true;
  }
  
  public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect)
  {
    int i = 5;
    if (aspect == Aspect.ENTROPY) {
      i = player.worldObj.isDaytime() ? 9 : 7;
    }
    return i;
  }
  
  public boolean getIsRepairable(ItemStack stack, ItemStack stack2)
  {
    return stack2.isItemEqual(new ItemStack(Items.gold_ingot)) ? true : super.getIsRepairable(stack, stack2);
  }
  
  public boolean showInvisibleEntity(ItemStack itemstack, EntityLivingBase player, EntityLivingBase entityInvisible)
  {
    return true;
  }
}
