package com.trinarybrain.magianaturalis.common.entity.ai;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIDoorInteract;
import net.minecraft.world.World;

public class AIBreakDoor
  extends EntityAIDoorInteract
{
  private int destroyTicks;
  private int destroyed = -1;
  
  public AIBreakDoor(EntityLiving entity)
  {
    super(entity);
  }
  
  public boolean shouldExecute()
  {
    return super.shouldExecute();
  }
  
  public void startExecuting()
  {
    super.startExecuting();
    this.destroyTicks = 0;
  }
  
  public boolean continueExecuting()
  {
    double d0 = this.theEntity.getDistanceSq(this.entityPosX, this.entityPosY, this.entityPosZ);
    return (this.destroyTicks <= 200) && (!this.field_151504_e.func_150015_f(this.theEntity.worldObj, this.entityPosX, this.entityPosY, this.entityPosZ)) && (d0 < 4.0D);
  }
  
  public void resetTask()
  {
    super.resetTask();
    this.theEntity.worldObj.destroyBlockInWorldPartially(this.theEntity.getEntityId(), this.entityPosX, this.entityPosY, this.entityPosZ, -1);
  }
  
  public void updateTask()
  {
    super.updateTask();
    if (this.theEntity.getRNG().nextInt(20) == 0) {
      this.theEntity.worldObj.playAuxSFX(1010, this.entityPosX, this.entityPosY, this.entityPosZ, 0);
    }
    this.destroyTicks += 1;
    int n = (int)(this.destroyTicks / 200.0F * 10.0F);
    if (n != this.destroyed)
    {
      this.theEntity.worldObj.destroyBlockInWorldPartially(this.theEntity.getEntityId(), this.entityPosX, this.entityPosY, this.entityPosZ, n);
      this.destroyed = n;
    }
    if (this.destroyTicks == 200)
    {
      this.theEntity.worldObj.setBlockToAir(this.entityPosX, this.entityPosY, this.entityPosZ);
      this.theEntity.worldObj.playAuxSFX(1012, this.entityPosX, this.entityPosY, this.entityPosZ, 0);
      this.theEntity.worldObj.playAuxSFX(2001, this.entityPosX, this.entityPosY, this.entityPosZ, Block.getIdFromBlock(this.field_151504_e));
    }
  }
}
