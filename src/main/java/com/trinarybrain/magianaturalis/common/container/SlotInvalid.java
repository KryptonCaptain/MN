package com.trinarybrain.magianaturalis.common.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotInvalid
  extends Slot
{
  public SlotInvalid(IInventory inv, int index, int xc, int yc)
  {
    super(inv, index, xc, yc);
  }
  
  public boolean isItemValid(ItemStack stack)
  {
    return false;
  }
}
