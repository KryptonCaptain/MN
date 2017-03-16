package com.trinarybrain.magianaturalis.common.entity.ai;

import com.trinarybrain.magianaturalis.common.entity.EntityPechCustom;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathNavigate;

public class AIInteractPlayer
  extends EntityAIBase
{
  private EntityPechCustom pech;
  
  public AIInteractPlayer(EntityPechCustom entityPech)
  {
    this.pech = entityPech;
    setMutexBits(5);
  }
  
  public boolean shouldExecute()
  {
    if (!this.pech.isEntityAlive()) {
      return false;
    }
    if (this.pech.isInWater()) {
      return false;
    }
    if (!this.pech.onGround) {
      return false;
    }
    if (this.pech.velocityChanged) {
      return false;
    }
    return this.pech.isInteracting;
  }
  
  public void startExecuting()
  {
    this.pech.getNavigator().clearPathEntity();
  }
  
  public void resetTask()
  {
    this.pech.isInteracting = false;
  }
}
