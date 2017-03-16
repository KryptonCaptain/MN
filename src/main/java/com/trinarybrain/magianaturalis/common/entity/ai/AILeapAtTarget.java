package com.trinarybrain.magianaturalis.common.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class AILeapAtTarget
  extends EntityAIBase
{
  private EntityLiving taskOwner;
  private EntityLivingBase leapTarget;
  private int jumpDelay;
  private boolean move;
  private World world;
  
  public AILeapAtTarget(EntityLiving entity)
  {
    this.taskOwner = entity;
    this.world = entity.worldObj;
    if (this.world != null) {
      this.jumpDelay = (this.world.rand.nextInt(20) + 10);
    }
    setMutexBits(5);
  }
  
  public boolean shouldExecute()
  {
    this.leapTarget = this.taskOwner.getAttackTarget();
    if (this.leapTarget == null) {
      return false;
    }
    if (this.taskOwner.getDistanceSqToEntity(this.leapTarget) >= 3.0D) {
      return this.taskOwner.onGround;
    }
    return false;
  }
  
  public boolean continueExecuting()
  {
    if ((this.leapTarget != null) && (this.taskOwner.getDistanceSqToEntity(this.leapTarget) >= 3.0D)) {
      return true;
    }
    return false;
  }
  
  public void updateTask()
  {
    if (this.leapTarget != null)
    {
      if (this.taskOwner.getDistanceToEntity(this.leapTarget) > 1.5F)
      {
        this.taskOwner.faceEntity(this.leapTarget, 10.0F, 20.0F);
        this.move = true;
      }
      else
      {
        this.move = false;
      }
      boolean high = true;
      if (((this.taskOwner.onGround) || (this.taskOwner.isInWater())) && (this.jumpDelay-- <= 0) && (this.move))
      {
        this.jumpDelay = (getJumpDelay() / 3);
        
        double d0 = this.leapTarget.posX - this.taskOwner.posX;
        double d1 = this.leapTarget.posZ - this.taskOwner.posZ;
        float f = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
        double d2 = 0.3000000298023224D;
        this.taskOwner.motionX += d0 / f * 0.5D * 1.25D + this.taskOwner.motionX * d2;
        this.taskOwner.motionZ += d1 / f * 0.5D * 1.25D + this.taskOwner.motionZ * d2;
        this.taskOwner.motionY = (high ? 0.45D : 0.35D);
        
        this.world.playSoundAtEntity(this.taskOwner, "random.chestclosed", 0.15F, this.world.rand.nextFloat() * 0.1F + 0.9F);
      }
    }
  }
  
  protected int getJumpDelay()
  {
    return this.world.rand.nextInt(20) + 10;
  }
}
