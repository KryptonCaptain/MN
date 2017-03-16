package com.trinarybrain.magianaturalis.common.tile;

import com.trinarybrain.magianaturalis.common.util.NBTUtil;
import com.trinarybrain.magianaturalis.common.util.Platform;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import thaumcraft.api.wands.IWandable;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.entities.monster.EntityTaintacle;
import thaumcraft.common.tiles.TileJar;

public class TileJarPrison
  extends TileJar
  implements IWandable
{
  private NBTTagCompound entityData = new NBTTagCompound();
  private Entity cachedEntity;
  
  public Entity getCachedEntity()
  {
    return this.cachedEntity;
  }
  
  private void setEntityForCache(NBTTagCompound data)
  {
    this.cachedEntity = null;
    if (data == null) {
      return;
    }
    if (data.hasKey("entity"))
    {
      this.cachedEntity = EntityList.createEntityFromNBT(data.getCompoundTag("entity"), getWorldObj());
      if ((this.cachedEntity != null) && ((this.cachedEntity instanceof EntityTaintacle))) {
        this.cachedEntity.ticksExisted = 30;
      }
    }
  }
  
  public void writeCustomNBT(NBTTagCompound data)
  {
    data.setTag("entity", this.entityData);
  }
  
  public void readCustomNBT(NBTTagCompound data)
  {
    this.entityData = data.getCompoundTag("entity");
    setEntityForCache(this.entityData);
  }
  
  public void setEntityData(NBTTagCompound data)
  {
    this.entityData = data;
    setEntityForCache(this.entityData);
  }
  
  public boolean saveEntityToNBT(EntityLivingBase entity)
  {
    if (Platform.isClient()) {
      return false;
    }
    if ((entity == null) || ((entity instanceof IBossDisplayData)) || ((entity instanceof EntityPlayer))) {
      return false;
    }
    if (!(entity instanceof EntityCreature)) {
      return false;
    }
    if (!entity.writeMountToNBT(this.entityData)) {
      return false;
    }
    entity.setDead();
    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    return true;
  }
  
  public boolean hasEntityInside()
  {
    if ((this.entityData != null) && (this.entityData.hasKey("entity"))) {
      return true;
    }
    return false;
  }
  
  public NBTTagCompound getEntityData()
  {
    return hasEntityInside() ? null : this.entityData.getCompoundTag("entity");
  }
  
  public NBTTagCompound getEntityDataPrimitive()
  {
    return this.entityData;
  }
  
  public void releaseFromContainer()
  {
    if (Platform.isServer()) {
      if (NBTUtil.spawnEntityFromNBT(this.entityData.getCompoundTag("entity"), this.worldObj, this.xCoord + 0.5D, this.yCoord, this.zCoord + 0.5D))
      {
        this.entityData = null;
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
      }
    }
  }
  
  public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md)
  {
    if (hasEntityInside())
    {
      if (Platform.isServer())
      {
        world.setBlockToAir(x, y, z);
        releaseFromContainer();
      }
      this.worldObj.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(ConfigBlocks.blockJar) + 61440);
      player.worldObj.playSound(x + 0.5D, y + 0.5D, z + 0.5D, "random.glass", 1.0F, 0.9F + player.worldObj.rand.nextFloat() * 0.2F, false);
      player.swingItem();
      return 0;
    }
    return -1;
  }
  
  public ItemStack onWandRightClick(World world, ItemStack wandstack, EntityPlayer player)
  {
    return null;
  }
  
  public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {}
  
  public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {}
}
