package com.trinarybrain.magianaturalis.common.entity;

import com.trinarybrain.magianaturalis.common.entity.ai.AIBreakDoor;
import com.trinarybrain.magianaturalis.common.entity.ai.EntityAIOwnerTarget;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityZombieExtended
  extends EntityZombie
  implements IEntityOwnable
{
  public EntityZombieExtended(World world)
  {
    super(world);
    this.tasks.taskEntries.clear();
    getNavigator().setAvoidSun(false);
    
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(1, new AIBreakDoor(this));
    this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
    this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityLiving.class, 1.0D, true));
    this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
    this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, 1.0D, false));
    this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
    this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    this.tasks.addTask(7, new EntityAILookIdle(this));
    this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    this.targetTasks.addTask(2, new EntityAIOwnerTarget(this, false));
  }
  
  protected void entityInit()
  {
    super.entityInit();
    this.dataWatcher.addObject(17, "");
  }
  
  protected boolean isAIEnabled()
  {
    return true;
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
    getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(new AttributeModifier("Random Knockback Resistance", this.rand.nextDouble() * 0.05000000074505806D, 0));
  }
  
  public void onUpdate()
  {
    super.onUpdate();
    if ((!this.worldObj.isRemote) && ((getAttackTarget() == null) || (getAttackTarget().isDead) || (this.ticksExisted > 300))) {
      attackEntityFrom(DamageSource.outOfWorld, 10.0F);
    }
  }
  
  public void onKillEntity(EntityLivingBase entity)
  {
    super.onKillEntity(entity);
    if ((entity instanceof EntityVillager))
    {
      if (this.rand.nextBoolean()) {
        return;
      }
      EntityZombieExtended entityZombie = new EntityZombieExtended(this.worldObj);
      entityZombie.copyLocationAndAnglesFrom(entity);
      this.worldObj.removeEntity(entity);
      entityZombie.setVillager(true);
      this.worldObj.spawnEntityInWorld(entityZombie);
      this.worldObj.playAuxSFXAtEntity(null, 1016, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
    }
  }
  
  public void writeEntityToNBT(NBTTagCompound data)
  {
    super.writeEntityToNBT(data);
    if (func_152113_b() == null) {
      data.setString("Owner", "");
    } else {
      data.setString("Owner", func_152113_b());
    }
  }
  
  public void readEntityFromNBT(NBTTagCompound data)
  {
    super.readEntityFromNBT(data);
    String name = data.getString("Owner");
    if (name.length() > 0) {
      setOwner(name);
    }
  }
  
  protected Item getDropItem()
  {
    return null;
  }
  
  protected void dropRareDrop(int n) {}
  
  public boolean interact(EntityPlayer player)
  {
    return false;
  }
  
  public void setOwner(String name)
  {
    this.dataWatcher.updateObject(17, name);
  }
  
  public EntityLivingBase getOwnerEntity()
  {
    return this.worldObj.getPlayerEntityByName(func_152113_b());
  }
  
  public Entity getOwner()
  {
    return getOwnerEntity();
  }
  
  public void setExperienceValue(int n)
  {
    this.experienceValue = n;
  }
  
  public String func_152113_b()
  {
    return this.dataWatcher.getWatchableObjectString(17);
  }
}
