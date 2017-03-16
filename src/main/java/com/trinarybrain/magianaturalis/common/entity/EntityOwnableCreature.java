package com.trinarybrain.magianaturalis.common.entity;

import com.trinarybrain.magianaturalis.common.entity.ai.AIWait;
import java.util.UUID;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.world.World;

public class EntityOwnableCreature
  extends EntityCreature
  implements IEntityOwnable
{
  protected AIWait aiWait = new AIWait(this);
  
  public EntityOwnableCreature(World world)
  {
    super(world);
  }
  
  protected void entityInit()
  {
    super.entityInit();
    this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(17, "");
  }
  
  public void writeEntityToNBT(NBTTagCompound data)
  {
    super.writeEntityToNBT(data);
    if (getOwnerUUID() == null) {
      data.setString("OwnerUUID", "");
    } else {
      data.setString("OwnerUUID", getOwnerUUID());
    }
    data.setBoolean("Waiting", isWaiting());
  }
  
  public void readEntityFromNBT(NBTTagCompound data)
  {
    super.readEntityFromNBT(data);
    
    String s = "";
    if (data.hasKey("OwnerUUID", 8))
    {
      s = data.getString("OwnerUUID");
    }
    else
    {
      String s1 = data.getString("Owner");
      s = PreYggdrasilConverter.func_152719_a(s1);
    }
    if (s.length() > 0) {
      setOwnerUUID(s);
    }
    this.aiWait.setWaiting(data.getBoolean("Waiting"));
    setWaiting(data.getBoolean("Waiting"));
  }
  
  public boolean isWaiting()
  {
    return this.dataWatcher.getWatchableObjectByte(16) != 0;
  }
  
  public void setWaiting(boolean bool)
  {
    this.dataWatcher.updateObject(16, Byte.valueOf((byte)(bool ? 1 : 0)));
  }
  
  public String func_152113_b()
  {
    return this.dataWatcher.getWatchableObjectString(17);
  }
  
  public String getOwnerUUID()
  {
    return func_152113_b();
  }
  
  public void setOwnerUUID(String uuid)
  {
    this.dataWatcher.updateObject(17, uuid);
  }
  
  public EntityLivingBase getOwner()
  {
    try
    {
      UUID uuid = UUID.fromString(getOwnerUUID());
      return uuid == null ? null : this.worldObj.func_152378_a(uuid);
    }
    catch (IllegalArgumentException illegalargumentexception) {}
    return null;
  }
  
  public boolean isOwner(EntityLivingBase entity)
  {
    return entity == getOwner();
  }
  
  public AIWait getAIWaitObj()
  {
    return this.aiWait;
  }
  
  public Team getTeam()
  {
    EntityLivingBase owner = getOwner();
    if (owner != null) {
      return owner.getTeam();
    }
    return super.getTeam();
  }
  
  public boolean isOnSameTeam(EntityLivingBase entity)
  {
    EntityLivingBase owner = getOwner();
    if (entity == owner) {
      return true;
    }
    if (owner != null) {
      return owner.isOnSameTeam(entity);
    }
    return super.isOnSameTeam(entity);
  }
  
  public boolean canAttack(EntityLivingBase attacker, EntityLivingBase owner)
  {
    return true;
  }
}
