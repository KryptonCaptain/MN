package com.trinarybrain.magianaturalis.common.tile;

import com.trinarybrain.magianaturalis.common.block.BlocksMN;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemResearchLog;
import com.trinarybrain.magianaturalis.common.util.Platform;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.tiles.TileDeconstructionTable;

public class TileTranscribingTable
  extends TileThaumcraft
  implements ISidedInventory
{
  public int timer = 40;
  private ItemStack[] inventory = new ItemStack[2];
  private String customName;
  private static final int[] sides = { 0 };
  
  public int getSizeInventory()
  {
    return 2;
  }
  
  public ItemStack getStackInSlot(int index)
  {
    return this.inventory[index];
  }
  
  public ItemStack decrStackSize(int index, int amount)
  {
    if (this.inventory[index] == null) {
      return null;
    }
    if (this.inventory[index].stackSize <= amount)
    {
      ItemStack stack = this.inventory[index];
      this.inventory[index] = null;
      return stack;
    }
    ItemStack stack = this.inventory[index].splitStack(amount);
    if (this.inventory[index].stackSize == 0) {
      this.inventory[index] = null;
    }
    return stack;
  }
  
  public ItemStack getStackInSlotOnClosing(int index)
  {
    if (this.inventory[index] == null) {
      return null;
    }
    ItemStack stack = this.inventory[index];
    this.inventory[index] = null;
    return stack;
  }
  
  public void setInventorySlotContents(int index, ItemStack stack)
  {
    this.inventory[index] = stack;
    if ((stack != null) && (stack.stackSize > getInventoryStackLimit())) {
      stack.stackSize = getInventoryStackLimit();
    }
  }
  
  public String getInventoryName()
  {
    return hasCustomInventoryName() ? this.customName : "container.transtable";
  }
  
  public boolean hasCustomInventoryName()
  {
    return (this.customName != null) && (this.customName.length() > 0);
  }
  
  public void setCustomInventoryNamee(String name)
  {
    this.customName = name;
  }
  
  public void readFromNBT(NBTTagCompound data)
  {
    super.readFromNBT(data);
    if (data.hasKey("CustomName", 8)) {
      this.customName = data.getString("CustomName");
    }
  }
  
  public void writeToNBT(NBTTagCompound data)
  {
    super.writeToNBT(data);
    if (hasCustomInventoryName()) {
      data.setString("CustomName", this.customName);
    }
  }
  
  public void readCustomNBT(NBTTagCompound data)
  {
    NBTTagList nbttaglist = data.getTagList("Items", 10);
    this.inventory = new ItemStack[getSizeInventory()];
    for (int i = 0; i < nbttaglist.tagCount(); i++)
    {
      NBTTagCompound tempData = nbttaglist.getCompoundTagAt(i);
      byte b = tempData.getByte("Slot");
      if ((b >= 0) && (b < this.inventory.length)) {
        this.inventory[b] = ItemStack.loadItemStackFromNBT(tempData);
      }
    }
  }
  
  public void writeCustomNBT(NBTTagCompound data)
  {
    NBTTagList nbttaglist = new NBTTagList();
    for (int i = 0; i < this.inventory.length; i++) {
      if (this.inventory[i] != null)
      {
        NBTTagCompound tempData = new NBTTagCompound();
        tempData.setByte("Slot", (byte)i);
        this.inventory[i].writeToNBT(tempData);
        nbttaglist.appendTag(tempData);
      }
    }
    data.setTag("Items", nbttaglist);
  }
  
  public int getInventoryStackLimit()
  {
    return 64;
  }
  
  public boolean isUseableByPlayer(EntityPlayer player)
  {
    return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this;
  }
  
  public void openInventory() {}
  
  public void closeInventory() {}
  
  public boolean isItemValidForSlot(int index, ItemStack stack)
  {
    return index != 1;
  }
  
  public int[] getAccessibleSlotsFromSide(int side)
  {
    return side != 1 ? sides : new int[0];
  }
  
  public boolean canInsertItem(int index, ItemStack stack, int side)
  {
    return side == 1 ? false : isItemValidForSlot(index, stack);
  }
  
  public boolean canExtractItem(int index, ItemStack stack, int side)
  {
    return index == 1;
  }
  
  public void updateEntity()
  {
    super.updateEntity();
    boolean update = false;
    if (Platform.isServer())
    {
      if ((getStackInSlot(0) != null) && ((getStackInSlot(0).getItem() instanceof ItemResearchLog)) && 
        (--this.timer <= 0))
      {
        update = handleKnowledgeHarvest(update);this.timer = 40;
      }
      if (update) {
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
      }
    }
  }
  
  private boolean handleKnowledgeHarvest(boolean update)
  {
    if (this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord) != BlocksMN.transcribingTable) {
      return update;
    }
    ItemStack stack = getStackInSlot(0);
    
    int x = this.worldObj.rand.nextInt(2) - this.worldObj.rand.nextInt(2);
    int z = this.worldObj.rand.nextInt(2) - this.worldObj.rand.nextInt(2);
    x += x;z += z;
    if ((x != 0) || (z != 0))
    {
      TileEntity tile = this.worldObj.getTileEntity(this.xCoord + x, this.yCoord, this.zCoord + z);
      if ((tile != null) && ((tile instanceof TileDeconstructionTable)) && (this.worldObj.getBlock(this.xCoord + x, this.yCoord, this.zCoord + z) == ConfigBlocks.blockTable))
      {
        TileDeconstructionTable tileDT = (TileDeconstructionTable)tile;
        Aspect aspect = tileDT.aspect;
        if (aspect != null)
        {
          ItemResearchLog log = (ItemResearchLog)stack.getItem();
          if (log.addResearchPoint(stack, aspect, (short)1))
          {
            tileDT.aspect = null;
            this.worldObj.markBlockForUpdate(this.xCoord + x, this.yCoord, this.zCoord + z);
            update = true;
          }
          else if ((log.getResearchPoint(stack, Aspect.AIR) == 64) && (log.getResearchPoint(stack, Aspect.EARTH) == 64) && (log.getResearchPoint(stack, Aspect.WATER) == 64) && (log.getResearchPoint(stack, Aspect.FIRE) == 64) && (log.getResearchPoint(stack, Aspect.ORDER) == 64) && (log.getResearchPoint(stack, Aspect.ENTROPY) == 64))
          {
            if (getStackInSlot(1) == null)
            {
              ItemStack stack2 = stack.copy();
              setInventorySlotContents(0, null);
              setInventorySlotContents(1, stack2);
            }
          }
        }
      }
    }
    return update;
  }
}
