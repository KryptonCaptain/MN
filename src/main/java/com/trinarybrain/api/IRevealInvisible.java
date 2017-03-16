package com.trinarybrain.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public abstract interface IRevealInvisible
{
  public abstract boolean showInvisibleEntity(ItemStack paramItemStack, EntityLivingBase paramEntityLivingBase1, EntityLivingBase paramEntityLivingBase2);
}
