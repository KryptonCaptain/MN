package com.trinarybrain.magianaturalis.common.item;

import com.trinarybrain.magianaturalis.common.item.alchemy.ItemAlchemicalStone;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemBiomeSampler;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemElementalSickle;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemEvilTrunkSpawner;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemGogglesDark;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemKey;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemResearchLog;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemSpectacles;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemThaumiumSickle;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemVoidSickle;
import com.trinarybrain.magianaturalis.common.item.baubles.ItemFocusPouchEnder;
import com.trinarybrain.magianaturalis.common.item.focus.ItemFocusBuild;
import com.trinarybrain.magianaturalis.common.item.focus.ItemFocusRevenant;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ItemsMN
{
  public static Item researchLog;
  public static Item biomeReport;
  public static Item alchemicalStone;
  public static Item key;
  public static Item gogglesDark;
  public static Item spectacles;
  public static Item focusBuild;
  public static Item focusRevenant;
  public static Item evilTrunkSpawner;
  public static Item focusPouchEnder;
  public static Item sickleThaumium;
  public static Item sickleElemental;
  public static Item voidSickle;
  
  public static void initItems()
  {
    researchLog = new ItemResearchLog();registerItem(researchLog, "researchLog");
    alchemicalStone = new ItemAlchemicalStone();registerItem(alchemicalStone, "alchemicalStone");
    key = new ItemKey();registerItem(key, "key");
    gogglesDark = new ItemGogglesDark();registerItem(gogglesDark, "gogglesDarkCrystal");
    spectacles = new ItemSpectacles();registerItem(spectacles, "spectacles");
    focusBuild = new ItemFocusBuild();registerItem(focusBuild, "focusBuild");
    evilTrunkSpawner = new ItemEvilTrunkSpawner();registerItem(evilTrunkSpawner, "evilTrunk");
    focusPouchEnder = new ItemFocusPouchEnder();registerItem(focusPouchEnder, "focusPouchEnder");
    sickleThaumium = new ItemThaumiumSickle();registerItem(sickleThaumium, "sickleThaumium");
    sickleElemental = new ItemElementalSickle();registerItem(sickleElemental, "sickleElemental");
    biomeReport = new ItemBiomeSampler();registerItem(biomeReport, "biomeSampler");
    focusRevenant = new ItemFocusRevenant();registerItem(focusRevenant, "focusRevenant");
    voidSickle = new ItemVoidSickle();registerItem(voidSickle, "voidSickle");
  }
  
  private static void registerItem(Item item, String str)
  {
    item.setUnlocalizedName("magianaturalis:" + str);
    GameRegistry.registerItem(item, "item." + str);
  }
}
