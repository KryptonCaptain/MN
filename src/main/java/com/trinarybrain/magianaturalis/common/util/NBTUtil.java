package com.trinarybrain.magianaturalis.common.util;

import com.trinarybrain.magianaturalis.common.util.access.UserAccess;
import java.util.ArrayList;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

public final class NBTUtil
{
  public static NBTTagCompound openNbtData(ItemStack stack)
  {
    NBTTagCompound data = stack.getTagCompound();
    if (data == null) {
      stack.setTagCompound(data = new NBTTagCompound());
    }
    return data;
  }
  
  public static NBTTagList newDoubleNBTList(double[] doubleArray)
  {
    NBTTagList nbttaglist = new NBTTagList();
    double[] arrayOfDouble = doubleArray;int i = arrayOfDouble.length;
    for (int j = 0; j < i; j++)
    {
      Double d1 = Double.valueOf(arrayOfDouble[j]);
      
      nbttaglist.appendTag(new NBTTagDouble(d1.doubleValue()));
    }
    return nbttaglist;
  }
  
  public static void saveInventoryToNBT(ItemStack stack, ItemStack[] inventory)
  {
    saveInventoryToNBT(openNbtData(stack), inventory);
  }
  
  public static void saveInventoryToNBT(NBTTagCompound data, ItemStack[] inventory)
  {
    if (inventory != null)
    {
      NBTTagList nbttaglist = new NBTTagList();
      for (int i = 0; i < inventory.length; i++)
      {
        NBTTagCompound tempData = new NBTTagCompound();
        tempData.setByte("Slot", (byte)i);
        if (inventory[i] != null) {
          inventory[i].writeToNBT(tempData);
        }
        nbttaglist.appendTag(tempData);
      }
      data.setTag("Items", nbttaglist);
    }
  }
  
  public static ArrayList<ItemStack> loadInventoryFromNBT(ItemStack stack)
  {
    NBTTagCompound data = openNbtData(stack);
    if (!data.hasKey("Items")) {
      return null;
    }
    NBTTagList nbttaglist = data.getTagList("Items", 10);
    ArrayList<ItemStack> inventory = new ArrayList();
    for (int i = 0; i < nbttaglist.tagCount(); i++)
    {
      NBTTagCompound tempData = nbttaglist.getCompoundTagAt(i);
      byte j = tempData.getByte("Slot");
      if (j >= 0) {
        inventory.add(ItemStack.loadItemStackFromNBT(tempData));
      }
    }
    return inventory;
  }
  
  public static ItemStack[] loadInventoryFromNBT(ItemStack stack, int invSize)
  {
    return loadInventoryFromNBT(openNbtData(stack), invSize);
  }
  
  public static ItemStack[] loadInventoryFromNBT(NBTTagCompound data, int invSize)
  {
    if (!data.hasKey("Items")) {
      return null;
    }
    NBTTagList nbttaglist = data.getTagList("Items", 10);
    ItemStack[] inventory = new ItemStack[invSize];
    for (int i = 0; i < nbttaglist.tagCount(); i++)
    {
      NBTTagCompound tempData = nbttaglist.getCompoundTagAt(i);
      byte j = tempData.getByte("Slot");
      if (j >= 0) {
        inventory[i] = ItemStack.loadItemStackFromNBT(tempData);
      }
    }
    return inventory;
  }
  
  public static void saveUserAccesToNBT(ItemStack stack, ArrayList<UserAccess> list)
  {
    saveUserAccesToNBT(openNbtData(stack), list);
  }
  
  public static ArrayList<UserAccess> loadUserAccesFromNBT(ItemStack stack)
  {
    return loadUserAccesFromNBT(openNbtData(stack));
  }
  
  public static void saveUserAccesToNBT(NBTTagCompound data, ArrayList<UserAccess> list)
  {
    if (list != null)
    {
      NBTTagList accessList = new NBTTagList();
      for (UserAccess user : list)
      {
        NBTTagCompound tempData = new NBTTagCompound();
        tempData.setString("UUID", user.getUUID().toString());
        tempData.setByte("Type", user.getAccessLevel());
        accessList.appendTag(tempData);
      }
      data.setTag("AccessList", accessList);
    }
  }
  
  public static ArrayList<UserAccess> loadUserAccesFromNBT(NBTTagCompound data)
  {
    if (!data.hasKey("AccessList")) {
      return new ArrayList();
    }
    NBTTagList tagList = data.getTagList("AccessList", 10);
    
    ArrayList<UserAccess> accessList = new ArrayList();
    for (int i = 0; i < tagList.tagCount(); i++)
    {
      NBTTagCompound tempData = tagList.getCompoundTagAt(i);
      UserAccess user = new UserAccess();
      user.setUUID(UUID.fromString(tempData.getString("UUID")));
      user.setAccesLevel(tempData.getByte("Type"));
      accessList.add(user);
    }
    return accessList;
  }
  
  public static boolean spawnEntityFromNBT(NBTTagCompound data, World world, double x, double y, double z)
  {
    if ((data != null) && (data.hasKey("id")) && (world != null))
    {
      data.setTag("Pos", newDoubleNBTList(new double[] { x, y, z }));
      data.setTag("Motion", newDoubleNBTList(new double[] { 0.0D, 0.0D, 0.0D }));
      data.setFloat("FallDistance", 0.0F);
      data.setInteger("Dimension", world.provider.dimensionId);
      Entity entity = EntityList.createEntityFromNBT(data, world);
      if (entity == null) {
        return false;
      }
      return world.spawnEntityInWorld(entity);
    }
    return false;
  }
}
