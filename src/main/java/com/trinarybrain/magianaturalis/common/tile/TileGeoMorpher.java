package com.trinarybrain.magianaturalis.common.tile;

import com.trinarybrain.magianaturalis.common.util.Platform;
import com.trinarybrain.magianaturalis.common.util.WorldUtil;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import java.util.HashMap;
import java.util.List;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.TileThaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.wands.IWandable;
import thaumcraft.common.blocks.BlockCosmeticSolid;
import thaumcraft.common.lib.events.EssentiaHandler;
import thaumcraft.common.lib.network.PacketHandler;
import thaumcraft.common.lib.network.fx.PacketFXBlockSparkle;
import thaumcraft.common.lib.world.biomes.BiomeHandler;

public class TileGeoMorpher
  extends TileThaumcraft
  implements IAspectContainer, IWandable
{
  public int ticks = 0;
  int morphX = 0;
  int morphZ = 0;
  public BiomeGenBase cachedBiome = null;
  BiomeGenBase lastBiome = null;
  AspectList morphCost = new AspectList();
  AspectList realCost = new AspectList();
  public boolean idle = true;
  
  public void updateEntity()
  {
    boolean update = false;
    if (Platform.isServer())
    {
      if ((++this.ticks % 5 == 0) && 
        (!this.idle)) {
        if (validateStructure())
        {
          update = handleBiomeMorphing(8, this.cachedBiome);
        }
        else
        {
          this.idle = true;
          this.realCost = new AspectList();
          update = true;
          if (this.morphX == 0)
          {
            this.morphZ -= 1;
            this.morphX = 1024;
          }
          else
          {
            this.morphX -= 1;
          }
        }
      }
      if (update)
      {
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        markDirty();
      }
    }
  }
  
  public boolean validateStructure()
  {
    boolean valid = (this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) instanceof BlockAir);
    for (int i = 1; i <= 3; i++)
    {
      if (!valid) {
        return false;
      }
      valid = ((this.worldObj.getBlock(this.xCoord, this.yCoord - 1 - i, this.zCoord) instanceof BlockCosmeticSolid)) && (this.worldObj.getBlockMetadata(this.xCoord, this.yCoord - 1 - i, this.zCoord) == 0);
    }
    return valid;
  }
  
  public boolean handleBiomeMorphing(int radius, BiomeGenBase newBiome)
  {
    boolean update = false;
    boolean isComplete = false;
    if (this.realCost.visSize() > 0)
    {
      for (Aspect aspect : this.realCost.getAspects()) {
        if ((this.realCost.getAmount(aspect) > 0) && 
          (EssentiaHandler.drainEssentia(this, aspect, ForgeDirection.UNKNOWN, 12)))
        {
          this.realCost.reduce(aspect, 1);
          this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
          markDirty();
          break;
        }
      }
      if (this.realCost.visSize() <= 0)
      {
        isComplete = true;
        update = true;
        this.realCost = new AspectList();
        int posX = this.xCoord - radius + this.morphX;
        int posZ = this.zCoord - radius + this.morphZ;
        int posY = this.worldObj.getTopSolidOrLiquidBlock(posX, posZ);
        WorldUtil.setBiomeAt(this.worldObj, posX, posZ, newBiome);
        
        this.worldObj.getChunkFromBlockCoords(posX, posZ).setChunkModified();
        this.worldObj.markBlockForUpdate(posX, posY, posZ);
        
        PacketHandler.INSTANCE.sendToAllAround(new PacketFXBlockSparkle(this.xCoord, this.yCoord, this.zCoord, newBiome.color), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 32.0D));
      }
    }
    else
    {
      isComplete = true;
    }
    if ((isComplete) && (this.ticks % 20 == 0)) {
      if (this.morphZ < radius * 2)
      {
        if (this.morphX < radius * 2)
        {
          this.morphX += 1;
        }
        else
        {
          this.morphX = 0;
          this.morphZ += 1;
        }
        int posX = this.xCoord - radius + this.morphX;
        int posZ = this.zCoord - radius + this.morphZ;
        BiomeGenBase oldBiome = this.worldObj.getBiomeGenForCoords(posX, posZ);
        if ((newBiome != oldBiome) && (Math.sqrt((this.morphX - radius) * (this.morphX - radius) + (this.morphZ - radius) * (this.morphZ - radius)) <= radius))
        {
          if (this.lastBiome != newBiome)
          {
            this.lastBiome = newBiome;
            this.morphCost = calculateMorphCost(newBiome);
          }
          this.realCost = this.morphCost.copy();
          return true;
        }
      }
      else
      {
        this.morphZ = 0;
        this.idle = true;
        update = true;
      }
    }
    return update;
  }
  
  public AspectList calculateMorphCost(BiomeGenBase newBiome)
  {
    BiomeDictionary.Type[] newTypes = BiomeDictionary.getTypesForBiome(newBiome);
    AspectList aspectCost = new AspectList();
    for (int i = 0; i < newTypes.length; i++) {
      if (newTypes[i] != null) {
        if (newTypes[i] != BiomeDictionary.Type.MAGICAL)
        {
          Aspect aspect = (Aspect)((List)BiomeHandler.biomeInfo.get(newTypes[i])).get(1);
          if (aspect != null)
          {
            int aura = ((Integer)((List)BiomeHandler.biomeInfo.get(newTypes[i])).get(0)).intValue();
            aspectCost.add(aspect, Math.round(aura * 2.0F / 100.0F));
          }
        }
        else
        {
          aspectCost.add(Aspect.MAGIC, 2);
        }
      }
    }
    return aspectCost;
  }
  
  public void readFromNBT(NBTTagCompound data)
  {
    super.readFromNBT(data);
    this.morphX = data.getByte("morphX");
    this.morphZ = data.getByte("morphZ");
  }
  
  public void writeToNBT(NBTTagCompound data)
  {
    super.writeToNBT(data);
    data.setByte("morphX", (byte)this.morphX);
    data.setByte("morphZ", (byte)this.morphZ);
  }
  
  public void readCustomNBT(NBTTagCompound data)
  {
    this.realCost.readFromNBT(data);
    this.idle = data.getBoolean("idle");
    
    int id = data.getInteger("biomeID");
    this.cachedBiome = (id >= 0 ? BiomeGenBase.getBiome(id) : null);
  }
  
  public void writeCustomNBT(NBTTagCompound data)
  {
    this.realCost.writeToNBT(data);
    data.setBoolean("idle", this.idle);
    data.setInteger("biomeID", this.cachedBiome != null ? this.cachedBiome.biomeID : -1);
  }
  
  public AspectList getAspects()
  {
    return this.realCost;
  }
  
  public void setAspects(AspectList aspects) {}
  
  public boolean doesContainerAccept(Aspect tag)
  {
    return false;
  }
  
  public int addToContainer(Aspect tag, int amount)
  {
    return 0;
  }
  
  public boolean takeFromContainer(Aspect tag, int amount)
  {
    return false;
  }
  
  public boolean takeFromContainer(AspectList ot)
  {
    return false;
  }
  
  public boolean doesContainerContainAmount(Aspect tag, int amount)
  {
    return false;
  }
  
  public boolean doesContainerContain(AspectList ot)
  {
    return false;
  }
  
  public int containerContains(Aspect tag)
  {
    return 0;
  }
  
  public int onWandRightClick(World world, ItemStack wandstack, EntityPlayer player, int x, int y, int z, int side, int md)
  {
    if (player.isSneaking()) {
      this.realCost = calculateMorphCost(this.cachedBiome);
    } else {
      this.idle = (!this.idle);
    }
    this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    markDirty();
    return -1;
  }
  
  public ItemStack onWandRightClick(World world, ItemStack wandstack, EntityPlayer player)
  {
    return null;
  }
  
  public void onUsingWandTick(ItemStack wandstack, EntityPlayer player, int count) {}
  
  public void onWandStoppedUsing(ItemStack wandstack, World world, EntityPlayer player, int count) {}
}
