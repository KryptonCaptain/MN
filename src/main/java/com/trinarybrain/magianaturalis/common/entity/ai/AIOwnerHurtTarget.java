package com.trinarybrain.magianaturalis.common.entity.ai;

import com.trinarybrain.magianaturalis.common.entity.EntityOwnableCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class AIOwnerHurtTarget
  extends EntityAITarget
{
  EntityOwnableCreature taskOwner;
  EntityLivingBase ownerTarget;
  private int lastAttackTimer;
  
  public AIOwnerHurtTarget(EntityOwnableCreature entity)
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
    this.ownerTarget = entityOwner.getLastAttacker();
    int i = entityOwner.getLastAttackerTime();
    return (i != this.lastAttackTimer) && (isSuitableTarget(this.ownerTarget, false)) && (this.taskOwner.canAttack(this.ownerTarget, entityOwner));
  }
  
  public void startExecuting()
  {
    this.taskOwner.setAttackTarget(this.ownerTarget);
    EntityLivingBase owner = this.taskOwner.getOwner();
    if (owner != null) {
      this.lastAttackTimer = owner.getLastAttackerTime();
    }
    super.startExecuting();
  }
}
