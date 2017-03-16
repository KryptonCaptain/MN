package com.trinarybrain.magianaturalis.common.entity.ai;

import com.trinarybrain.magianaturalis.common.entity.EntityOwnableCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class AIOwnerHurtByTarget
  extends EntityAITarget
{
  EntityOwnableCreature taskOwner;
  EntityLivingBase ownerTarget;
  private int revengeTimer;
  
  public AIOwnerHurtByTarget(EntityOwnableCreature entity)
  {
    super(entity, false);
    this.taskOwner = entity;
    setMutexBits(1);
  }
  
  public boolean shouldExecute()
  {
    EntityLivingBase entityOwner = this.taskOwner.getOwner();
    if (entityOwner == null) {
      return false;
    }
    this.ownerTarget = entityOwner.getAITarget();
    int i = entityOwner.func_142015_aE();
    return (i != this.revengeTimer) && (isSuitableTarget(this.ownerTarget, false)) && (this.taskOwner.canAttack(this.ownerTarget, entityOwner));
  }
  
  public void startExecuting()
  {
    this.taskOwner.setAttackTarget(this.ownerTarget);
    EntityLivingBase owner = this.taskOwner.getOwner();
    if (owner != null) {
      this.revengeTimer = owner.func_142015_aE();
    }
    super.startExecuting();
  }
}
