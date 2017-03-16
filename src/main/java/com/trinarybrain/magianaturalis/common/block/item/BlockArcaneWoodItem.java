package com.trinarybrain.magianaturalis.common.block.item;

import com.trinarybrain.magianaturalis.common.MagiaNaturalis;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockArcaneWoodItem
  extends ItemBlock
{
  final String[] unlocal = { "gwPlanks", "gwOrn", "swPlanks.0", "swPlanks.1", "gwGoldOrn.0", "gwGoldOrn.1", "gwGoldTrim" };
  
  public BlockArcaneWoodItem(Block block)
  {
    super(block);
    setMaxDamage(0);
    setHasSubtypes(true);
    setCreativeTab(MagiaNaturalis.creativeTab);
  }
  
  public int getMetadata(int meta)
  {
    return meta;
  }
  
  public String getUnlocalizedName(ItemStack stack)
  {
    return super.getUnlocalizedName() + "." + this.unlocal[stack.getItemDamage()];
  }
}
