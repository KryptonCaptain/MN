package com.trinarybrain.magianaturalis.common.inventory;

import com.trinarybrain.magianaturalis.common.entity.EntityEvilTrunk;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryEvilTrunk
  implements IInventory
{
  public ItemStack[] inventory;
  public EntityEvilTrunk entityTrunk;
  public boolean inventoryChanged;
  public int slotCount;
  public int stacklimit = 64;
  
  public InventoryEvilTrunk(EntityEvilTrunk entity, int slots)
  {
    this.slotCount = slots;
    this.inventory = new ItemStack[36];
    this.inventoryChanged = false;
    this.entityTrunk = entity;
  }
  
  public int getSizeInventory()
  {
    return this.slotCount;
  }
  
  public ItemStack getStackInSlot(int index)
  {
    return this.inventory[index];
  }
  
  public ItemStack decrStackSize(int index, int amount)
  {
    ItemStack[] inv = this.inventory;
    if (inv[index] != null)
    {
      if (inv[index].stackSize <= amount)
      {
        ItemStack stack = inv[index];
        inv[index] = null;
        return stack;
      }
      ItemStack stack = inv[index].splitStack(amount);
      if (inv[index].stackSize == 0) {
        inv[index] = null;
      }
      return stack;
    }
    return null;
  }
  
  public ItemStack getStackInSlotOnClosing(int index)
  {
    return null;
  }
  
  public void setInventorySlotContents(int index, ItemStack stack)
  {
    this.inventory[index] = stack;
  }
  
  public String getInventoryName()
  {
    return "Inventory";
  }
  
  public boolean hasCustomInventoryName()
  {
    return false;
  }
  
  public int getInventoryStackLimit()
  {
    return this.stacklimit;
  }
  
  public void markDirty()
  {
    this.inventoryChanged = true;
  }
  
  public boolean isUseableByPlayer(EntityPlayer player)
  {
    return false;
  }
  
  public void openInventory()
  {
    this.entityTrunk.setOpen(true);
  }
  
  public void closeInventory()
  {
    this.entityTrunk.setOpen(false);
  }
  
  public boolean isItemValidForSlot(int index, ItemStack stack)
  {
    return true;
  }
  
  public void dropAllItems()
  {
    for (int index = 0; index < this.inventory.length; index++) {
      if (this.inventory[index] != null)
      {
        this.entityTrunk.entityDropItem(this.inventory[index], 0.0F);
        this.inventory[index] = null;
      }
    }
  }
  
  public NBTBase writeToNBT(NBTTagList dataList)
  {
    for (int index = 0; index < this.inventory.length; index++) {
      if (this.inventory[index] != null)
      {
        NBTTagCompound data = new NBTTagCompound();
        data.setByte("Slot", (byte)index);
        this.inventory[index].writeToNBT(data);
        dataList.appendTag(data);
      }
    }
    return dataList;
  }
  
  public void readFromNBT(NBTTagList dataList)
  {
    this.inventory = new ItemStack[this.inventory.length];
    for (int i = 0; i < dataList.tagCount(); i++)
    {
      NBTTagCompound data = dataList.getCompoundTagAt(i);
      byte index = data.getByte("Slot");
      ItemStack stack = ItemStack.loadItemStackFromNBT(data);
      if (stack.getItem() != null) {
        if ((index >= 0) && (index < this.inventory.length)) {
          this.inventory[index] = stack;
        }
      }
    }
  }
}
