package com.trinarybrain.magianaturalis.common.entity.ai;

import com.trinarybrain.magianaturalis.common.entity.EntityPechCustom;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class AIFollowCreature
  extends EntityAIBase
{
  private EntityPechCustom taskOwner;
  private Class targetEntityClass;
  private EntityLiving closestLivingEntity;
  private int followTime;
  
  public AIFollowCreature(EntityPechCustom entityPech, Class par2Class)
  {
    this.taskOwner = entityPech;
    this.targetEntityClass = par2Class;
    
    setMutexBits(1);
  }
  
  public boolean shouldExecute()
  {
    if (this.taskOwner.getRNG().nextInt(400) != 0) {
      return false;
    }
    List list = this.taskOwner.worldObj.getEntitiesWithinAABB(this.targetEntityClass, this.taskOwner.boundingBox.expand(6.0D, 3.0D, 6.0D));
    if (list.isEmpty()) {
      return false;
    }
    this.closestLivingEntity = ((EntityLiving)list.get(0));
    return true;
  }
  
  public boolean continueExecuting()
  {
    return this.followTime > 0;
  }
  
  public void startExecuting()
  {
    this.followTime = 1000;
  }
  
  public void resetTask()
  {
    this.closestLivingEntity = null;
    this.taskOwner.getNavigator().clearPathEntity();
  }
  
  public void updateTask()
  {
    this.followTime -= 1;
    if (this.closestLivingEntity != null)
    {
      this.taskOwner.getLookHelper().setLookPositionWithEntity(this.closestLivingEntity, 30.0F, 30.0F);
      if (this.taskOwner.getDistanceSqToEntity(this.closestLivingEntity) > 4.0D) {
        this.taskOwner.getNavigator().tryMoveToEntityLiving(this.closestLivingEntity, 0.5D);
      } else {
        this.taskOwner.getNavigator().clearPathEntity();
      }
    }
  }
}
