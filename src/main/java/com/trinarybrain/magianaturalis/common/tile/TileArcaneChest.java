package com.trinarybrain.magianaturalis.common.tile;

import com.mojang.authlib.GameProfile;
import com.trinarybrain.magianaturalis.common.block.BlockArcaneChest;
import com.trinarybrain.magianaturalis.common.block.BlocksMN;
import com.trinarybrain.magianaturalis.common.util.NBTUtil;
import com.trinarybrain.magianaturalis.common.util.Platform;
import com.trinarybrain.magianaturalis.common.util.access.UserAccess;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.wands.IWandable;

public class TileArcaneChest
  extends TileThaumcraft
  implements ISidedInventory, IWandable
{
  private ItemStack[] inventory = new ItemStack[54];
  public float lidAngle;
  public float prevLidAngle;
  public int numUsingPlayers;
  public UUID owner;
  private String ownerName;
  public ArrayList<UserAccess> accessList = new ArrayList();
  private byte chestType = 0;
  public final String[] name = { "unknown", "gw", "sw" };
  private String customName;
  private static final int[] sides = { 0, 1, 2, 3, 4, 5 };
  
  public String getOwnerName()
  {
    if ((this.ownerName != null) && (!this.ownerName.equals(""))) {
      return this.ownerName;
    }
    if ((Platform.isServer()) && (this.owner != null))
    {
      GameProfile profile = Platform.findGameProfileByUUID(this.owner);
      if (profile != null) {
        return this.ownerName = profile.getName();
      }
      return this.ownerName = "Only UUID Available";
    }
    return "Unknown";
  }
  
  public int getSizeInventory()
  {
    return getChestType() == 2 ? 77 : 54;
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
  
  public void setInvetory(ItemStack[] inventory)
  {
    this.inventory = inventory;
  }
  
  public String getInventoryName()
  {
    return hasCustomInventoryName() ? this.customName : Platform.translate("tile.magianaturalis:arcaneChest." + this.name[getChestType()] + ".name");
  }
  
  public boolean hasCustomInventoryName()
  {
    return (this.customName != null) && (this.customName.length() > 0);
  }
  
  public void setGuiName(String name)
  {
    this.customName = name;
  }
  
  public int getChestType()
  {
    return this.chestType;
  }
  
  public void setChestType(byte type)
  {
    this.chestType = type;
  }
  
  public void readFromNBT(NBTTagCompound data)
  {
    super.readFromNBT(data);
    this.inventory = NBTUtil.loadInventoryFromNBT(data, getSizeInventory());
    if (data.hasKey("CustomName")) {
      this.customName = data.getString("CustomName");
    }
  }
  
  public void readCustomNBT(NBTTagCompound data)
  {
    this.owner = UUID.fromString(data.getString("owner"));
    this.chestType = data.getByte("Type");
    this.accessList = NBTUtil.loadUserAccesFromNBT(data);
  }
  
  public void writeToNBT(NBTTagCompound data)
  {
    super.writeToNBT(data);
    NBTUtil.saveInventoryToNBT(data, this.inventory);
    if (hasCustomInventoryName()) {
      data.setString("CustomName", this.customName);
    }
  }
  
  public void writeCustomNBT(NBTTagCompound data)
  {
    data.setString("owner", this.owner.toString());
    data.setByte("Type", this.chestType);
    NBTUtil.saveUserAccesToNBT(data, this.accessList);
  }
  
  public int getInventoryStackLimit()
  {
    return 64;
  }
  
  public boolean isUseableByPlayer(EntityPlayer player)
  {
    return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this;
  }
  
  public void openInventory()
  {
    if (this.numUsingPlayers < 0) {
      this.numUsingPlayers = 0;
    }
    this.numUsingPlayers += 1;
    this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, BlocksMN.arcaneChest, 1, this.numUsingPlayers);
  }
  
  public void closeInventory()
  {
    this.numUsingPlayers -= 1;
    this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, BlocksMN.arcaneChest, 1, this.numUsingPlayers);
  }
  
  public boolean isItemValidForSlot(int i, ItemStack stack)
  {
    return true;
  }
  
  public int[] getAccessibleSlotsFromSide(int side)
  {
    return sides;
  }
  
  public boolean canInsertItem(int index, ItemStack stack, int side)
  {
    return false;
  }
  
  public boolean canExtractItem(int index, ItemStack stack, int side)
  {
    return false;
  }
  
  public void updateEntity()
  {
    super.updateEntity();
    this.prevLidAngle = this.lidAngle;
    float angle = 0.1F;
    if ((this.numUsingPlayers > 0) && (this.lidAngle == 0.0F)) {
      this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
    }
    if (((this.numUsingPlayers == 0) && (this.lidAngle > 0.0F)) || ((this.numUsingPlayers > 0) && (this.lidAngle < 1.0F)))
    {
      float currAngle = this.lidAngle;
      if (this.numUsingPlayers > 0) {
        this.lidAngle += angle;
      } else {
        this.lidAngle -= angle;
      }
      if (this.lidAngle > 1.0F) {
        this.lidAngle = 1.0F;
      }
      if ((this.lidAngle < 0.5F) && (currAngle >= 0.5F)) {
        this.worldObj.playSoundEffect(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
      }
      if (this.lidAngle < 0.0F) {
        this.lidAngle = 0.0F;
      }
    }
  }
  
  public boolean receiveClientEvent(int eventID, int arg)
  {
    if (eventID == 1)
    {
      this.numUsingPlayers = arg;
      return true;
    }
    if (eventID == 2)
    {
      if (this.lidAngle < arg / 10.0F) {
        this.lidAngle = (arg / 10.0F);
      }
      return true;
    }
    return this.tileEntityInvalid;
  }
  
  public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md)
  {
    return 0;
  }
  
  public ItemStack onWandRightClick(World world, ItemStack stack, EntityPlayer player)
  {
    if ((Platform.isServer()) && (!world.restoringBlockSnapshots))
    {
      boolean hasAccess = false;
      if ((player.capabilities.isCreativeMode) || (this.owner.equals(player.getGameProfile().getId()))) {
        hasAccess = true;
      } else {
        hasAccess = this.accessList.contains(new UserAccess(player.getGameProfile().getId(), (byte)2));
      }
      if (!hasAccess)
      {
        player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + Platform.translate("chat.magianaturalis:chest.resist")));
        return stack;
      }
      Block block = world.getBlock(this.xCoord, this.yCoord, this.zCoord);
      if (block == null) {
        return stack;
      }
      ItemStack stack1 = new ItemStack(BlocksMN.arcaneChest, 1, this.chestType);
      BlockArcaneChest.setChestType(stack1, this.chestType);
      NBTUtil.saveInventoryToNBT(stack1, this.inventory);
      if (!this.accessList.isEmpty()) {
        NBTUtil.saveUserAccesToNBT(stack1, this.accessList);
      }
      world.removeTileEntity(this.xCoord, this.yCoord, this.zCoord);
      world.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
      
      float f = 0.7F;
      double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
      double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
      double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
      EntityItem entityitem = new EntityItem(world, this.xCoord + d0, this.yCoord + d1, this.zCoord + d2, stack1);
      entityitem.delayBeforeCanPickup = 10;
      world.spawnEntityInWorld(entityitem);
    }
    return stack;
  }
  
  public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {}
  
  public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {}
}
