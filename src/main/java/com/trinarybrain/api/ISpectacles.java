package com.trinarybrain.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public abstract interface ISpectacles
{
  public abstract boolean drawSpectacleHUD(ItemStack paramItemStack, EntityLivingBase paramEntityLivingBase);
}
