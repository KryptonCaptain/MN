package com.trinarybrain.magianaturalis.common.util;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public final class FocusBuildHelper
{
  public static Meta getMeta(ItemStack stack)
  {
    if (stack == null) {
      return Meta.NONE;
    }
    NBTTagList nbttaglist = NBTUtil.openNbtData(stack).getTagList("magia_naturalis", 10);
    if (nbttaglist == null) {
      return Meta.NONE;
    }
    int i = nbttaglist.getCompoundTagAt(0).getByte("meta");
    switch (i)
    {
    case 0: 
      return Meta.NONE;
    case 1: 
      return Meta.UNIFORM;
    }
    return Meta.NONE;
  }
  
  public static boolean setMeta(ItemStack stack, Meta meta)
  {
    if (stack == null) {
      return false;
    }
    NBTTagCompound data = NBTUtil.openNbtData(stack);
    NBTTagList nbttaglist = data.getTagList("magia_naturalis", 10);
    NBTTagCompound tempData;
    NBTTagCompound tempData;
    if (nbttaglist == null)
    {
      nbttaglist = new NBTTagList();
      tempData = new NBTTagCompound();
    }
    else
    {
      tempData = nbttaglist.getCompoundTagAt(0);
    }
    tempData.setByte("meta", (byte)meta.ordinal());
    nbttaglist.appendTag(tempData);
    if (nbttaglist.tagCount() > 0)
    {
      data.setTag("magia_naturalis", nbttaglist);
      return true;
    }
    return false;
  }
  
  public static Shape getShape(ItemStack stack)
  {
    if (stack == null) {
      return Shape.NONE;
    }
    NBTTagList nbttaglist = NBTUtil.openNbtData(stack).getTagList("magia_naturalis", 10);
    if (nbttaglist == null) {
      return Shape.NONE;
    }
    return getShapeByID(nbttaglist.getCompoundTagAt(0).getByte("shape"));
  }
  
  public static boolean setShape(ItemStack stack, Shape shape)
  {
    if (stack == null) {
      return false;
    }
    NBTTagCompound data = NBTUtil.openNbtData(stack);
    NBTTagList nbttaglist = data.getTagList("magia_naturalis", 10);
    NBTTagCompound tempData;
    NBTTagCompound tempData;
    if (nbttaglist == null)
    {
      nbttaglist = new NBTTagList();
      tempData = new NBTTagCompound();
    }
    else
    {
      tempData = nbttaglist.getCompoundTagAt(0);
    }
    tempData.setByte("shape", (byte)shape.ordinal());
    nbttaglist.appendTag(tempData);
    if (nbttaglist.tagCount() > 0)
    {
      stack.setTagInfo("magia_naturalis", nbttaglist);
      return true;
    }
    return false;
  }
  
  public static int getSize(ItemStack stack)
  {
    if (stack == null) {
      return 1;
    }
    NBTTagList nbttaglist = NBTUtil.openNbtData(stack).getTagList("magia_naturalis", 10);
    if (nbttaglist == null) {
      return 1;
    }
    return nbttaglist.getCompoundTagAt(0).getByte("size");
  }
  
  public static boolean setSize(ItemStack stack, int size)
  {
    if (stack == null) {
      return false;
    }
    NBTTagCompound data = NBTUtil.openNbtData(stack);
    NBTTagList nbttaglist = data.getTagList("magia_naturalis", 10);
    NBTTagCompound tempData;
    NBTTagCompound tempData;
    if (nbttaglist == null)
    {
      nbttaglist = new NBTTagList();
      tempData = new NBTTagCompound();
    }
    else
    {
      tempData = nbttaglist.getCompoundTagAt(0);
    }
    tempData.setByte("size", (byte)size);
    nbttaglist.appendTag(tempData);
    if (nbttaglist.tagCount() > 0)
    {
      stack.setTagInfo("magia_naturalis", nbttaglist);
      return true;
    }
    return false;
  }
  
  public static boolean setpickedBlock(ItemStack stack, Block block, int metadata)
  {
    if (stack == null) {
      return false;
    }
    int bid = Block.getIdFromBlock(block);
    if ((metadata < 0) || (metadata > 15)) {
      metadata = 0;
    }
    NBTTagCompound data = NBTUtil.openNbtData(stack);
    NBTTagList nbttaglist = data.getTagList("magia_naturalis", 10);
    NBTTagCompound nbttagcompound;
    NBTTagCompound nbttagcompound;
    if (nbttaglist == null)
    {
      nbttaglist = new NBTTagList();
      nbttagcompound = new NBTTagCompound();
    }
    else
    {
      nbttagcompound = nbttaglist.getCompoundTagAt(0);
    }
    nbttagcompound.setInteger("bid", bid);
    nbttagcompound.setByte("bdata", (byte)metadata);
    
    nbttaglist.appendTag(nbttagcompound);
    if (nbttaglist.tagCount() > 0)
    {
      stack.setTagInfo("magia_naturalis", nbttaglist);
      return true;
    }
    return false;
  }
  
  public static int[] getPickedBlock(ItemStack stack)
  {
    int[] i = { 0, 0 };
    if (stack == null) {
      return i;
    }
    NBTTagList nbttaglist = NBTUtil.openNbtData(stack).getTagList("magia_naturalis", 10);
    if (nbttaglist == null) {
      return i;
    }
    i[0] = nbttaglist.getCompoundTagAt(0).getInteger("bid");
    i[1] = nbttaglist.getCompoundTagAt(0).getByte("bdata");
    return i;
  }
  
  public static enum Meta
  {
    NONE,  UNIFORM;
    
    private Meta() {}
    
    public String toString()
    {
      switch (FocusBuildHelper.1.$SwitchMap$com$trinarybrain$magianaturalis$common$util$FocusBuildHelper$Meta[ordinal()])
      {
      case 1: 
        return Platform.translate("enum.magianaturalis:none");
      case 2: 
        return Platform.translate("enum.magianaturalis:uniform");
      }
      return Platform.translate("enum.magianaturalis:unknown");
    }
  }
  
  public static enum Shape
  {
    NONE,  PLANE,  CUBE,  PLANE_EXTEND,  SPHERE;
    
    private Shape() {}
    
    public String toString()
    {
      switch (FocusBuildHelper.1.$SwitchMap$com$trinarybrain$magianaturalis$common$util$FocusBuildHelper$Shape[ordinal()])
      {
      case 1: 
        return Platform.translate("enum.magianaturalis:cube");
      case 2: 
        return Platform.translate("enum.magianaturalis:none");
      case 3: 
        return Platform.translate("enum.magianaturalis:plane");
      case 4: 
        return Platform.translate("enum.magianaturalis:plane.extend");
      case 5: 
        return Platform.translate("enum.magianaturalis:sphere");
      }
      return Platform.translate("enum.magianaturalis:unknown");
    }
  }
  
  public static Shape getShapeByID(int i)
  {
    switch (i)
    {
    case 0: 
      return Shape.NONE;
    case 1: 
      return Shape.PLANE;
    case 2: 
      return Shape.CUBE;
    case 3: 
      return Shape.PLANE_EXTEND;
    case 4: 
      return Shape.SPHERE;
    }
    return Shape.NONE;
  }
}
