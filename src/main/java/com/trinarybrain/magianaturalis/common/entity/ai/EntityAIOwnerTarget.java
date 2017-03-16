package com.trinarybrain.magianaturalis.common.entity.ai;

import com.trinarybrain.magianaturalis.common.entity.EntityZombieExtended;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAIOwnerTarget
  extends EntityAITarget
{
  private EntityLivingBase targetEntity;
  private EntityZombieExtended entityZE;
  
  public EntityAIOwnerTarget(EntityCreature creature, boolean bool)
  {
    super(creature, bool);
    this.targetEntity = this.taskOwner.getAttackTarget();
    this.entityZE = ((EntityZombieExtended)this.taskOwner);
  }
  
  public boolean shouldExecute()
  {
    if (this.targetEntity == null) {
      return false;
    }
    if (!this.targetEntity.isDead) {
      return true;
    }
    return false;
  }
  
  public void startExecuting()
  {
    if ((this.taskOwner.getAttackTarget() != null) && (this.taskOwner.getAttackTarget().isDead)) {
      this.taskOwner.setDead();
    }
    if ((this.taskOwner.getAttackTarget() == null) && (this.entityZE.getAITarget() != null) && (!this.entityZE.getOwnerEntity().getAITarget().isDead) && 
      ((this.entityZE.getOwnerEntity().getAITarget() instanceof EntityLivingBase)) && (this.entityZE.canEntityBeSeen(this.entityZE.getOwnerEntity().getAITarget()))) {
      this.taskOwner.setAttackTarget(this.entityZE.getOwnerEntity().getAITarget());
    }
    this.taskOwner.setAttackTarget(this.targetEntity);
    super.startExecuting();
  }
  
  public void updateTask()
  {
    if ((this.taskOwner.getAttackTarget() != null) && (this.taskOwner.getAttackTarget().isDead)) {
      this.taskOwner.setDead();
    }
    if ((this.taskOwner.getAttackTarget() == null) && (this.entityZE.getAITarget() != null) && (!this.entityZE.getOwnerEntity().getAITarget().isDead) && 
      ((this.entityZE.getOwnerEntity().getAITarget() instanceof EntityLivingBase)) && (this.entityZE.canEntityBeSeen(this.entityZE.getOwnerEntity().getAITarget()))) {
      this.taskOwner.setAttackTarget(this.entityZE.getOwnerEntity().getAITarget());
    }
  }
}
