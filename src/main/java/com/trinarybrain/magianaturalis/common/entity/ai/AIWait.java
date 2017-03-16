package com.trinarybrain.magianaturalis.common.entity.ai;

import com.trinarybrain.magianaturalis.common.entity.EntityOwnableCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;

public class AIWait
  extends EntityAIBase
{
  private EntityOwnableCreature taskOwner;
  private boolean isWaiting;
  
  public AIWait(EntityOwnableCreature entity)
  {
    this.taskOwner = entity;
    setMutexBits(5);
  }
  
  public boolean shouldExecute()
  {
    if (this.taskOwner.isInWater()) {
      return false;
    }
    if (!this.taskOwner.onGround) {
      return false;
    }
    EntityLivingBase entitylivingbase = this.taskOwner.getOwner();
    return (this.taskOwner.getDistanceSqToEntity(entitylivingbase) < 144.0D) && (entitylivingbase.getAITarget() != null) ? false : entitylivingbase == null ? true : this.isWaiting;
  }
  
  public void startExecuting()
  {
    this.taskOwner.getNavigator().clearPathEntity();
    this.taskOwner.setWaiting(true);
  }
  
  public void resetTask()
  {
    this.taskOwner.setWaiting(false);
  }
  
  public void setWaiting(boolean par1)
  {
    this.isWaiting = par1;
  }
}
