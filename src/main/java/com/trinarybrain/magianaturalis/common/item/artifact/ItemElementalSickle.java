package com.trinarybrain.magianaturalis.common.item.artifact;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import thaumcraft.api.IRepairable;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.common.config.ConfigItems;

public class ItemElementalSickle
  extends ItemSickle
  implements IRepairable
{
  public ItemElementalSickle()
  {
    super(ThaumcraftApi.toolMatElemental);
    this.areaSize = 9;
    this.collectLoot = true;
    this.colorLoot = 0;
    this.abundanceLevel = 2;
  }
  
  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister icon)
  {
    this.itemIcon = icon.registerIcon("magianaturalis:sickle_abundance");
  }
  
  public EnumRarity getRarity(ItemStack stack)
  {
    return EnumRarity.rare;
  }
  
  public boolean getIsRepairable(ItemStack stack, ItemStack stackMaterial)
  {
    return stackMaterial.isItemEqual(new ItemStack(ConfigItems.itemResource, 1, 2)) ? true : super.getIsRepairable(stack, stackMaterial);
  }
}
