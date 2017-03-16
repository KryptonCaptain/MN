package com.trinarybrain.magianaturalis.common.item.artifact;

import com.mojang.authlib.GameProfile;
import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import com.trinarybrain.magianaturalis.common.entity.EntityEvilTrunk;
import com.trinarybrain.magianaturalis.common.inventory.InventoryEvilTrunk;
import com.trinarybrain.magianaturalis.common.util.Platform;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemEvilTrunkSpawner
  extends Item
{
  public String[] name = { "corrupted", "sinister", "demonic", "tainted" };
  
  public ItemEvilTrunkSpawner()
  {
    setMaxStackSize(1);
    setHasSubtypes(true);
    setCreativeTab(MagiaNaturalis.creativeTab);
  }
  
  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister ir)
  {
    this.itemIcon = ir.registerIcon("magianaturalis:emptyTexture");
  }
  
  @SideOnly(Side.CLIENT)
  public void getSubItems(Item item, CreativeTabs tab, List list)
  {
    list.add(new ItemStack(item, 1, 0));
    list.add(new ItemStack(item, 1, 1));
    list.add(new ItemStack(item, 1, 2));
    list.add(new ItemStack(item, 1, 3));
  }
  
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
  {
    if ((stack.hasTagCompound()) && (stack.stackTagCompound.hasKey("inventory"))) {
      list.add(Platform.translate("item.TrunkSpawner.text.1"));
    }
  }
  
  public String getUnlocalizedName(ItemStack stack)
  {
    return super.getUnlocalizedName() + "." + this.name[stack.getItemDamage()];
  }
  
  public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
  {
    if (Platform.isClient()) {
      return false;
    }
    Block block = world.getBlock(x, y, z);
    x += net.minecraft.util.Facing.offsetsXForSide[side];
    y += net.minecraft.util.Facing.offsetsYForSide[side];
    z += net.minecraft.util.Facing.offsetsZForSide[side];
    
    double d0 = 0.0D;
    if ((side == 1) && (!block.isAir(world, x, y, z)) && (block.getRenderType() == 11)) {
      d0 = 0.5D;
    }
    EntityEvilTrunk entity = new EntityEvilTrunk(world, stack.getItemDamage());
    if (entity != null)
    {
      if ((stack.hasTagCompound()) && (stack.stackTagCompound.hasKey("inventory")))
      {
        NBTTagList dataList = stack.stackTagCompound.getTagList("inventory", 10);
        entity.inventory.readFromNBT(dataList);
      }
      entity.setOwnerUUID(player.getGameProfile().getId().toString());
      entity.setLocationAndAngles(x, y + d0, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
      entity.rotationYawHead = entity.rotationYaw;
      entity.renderYawOffset = entity.rotationYaw;
      if (stack.hasDisplayName()) {
        entity.setCustomNameTag(stack.getDisplayName());
      }
      world.spawnEntityInWorld(entity);
      entity.playLivingSound();
      if (!player.capabilities.isCreativeMode) {
        stack.stackSize -= 1;
      }
    }
    return true;
  }
}
