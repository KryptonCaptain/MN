package com.trinarybrain.magianaturalis.common.research;

import com.trinarybrain.magianaturalis.common.block.BlockArcaneChest;
import com.trinarybrain.magianaturalis.common.block.BlocksMN;
import com.trinarybrain.magianaturalis.common.item.ItemsMN;
import com.trinarybrain.magianaturalis.common.recipe.Recipes;
import java.util.HashMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.common.config.ConfigItems;

public class Research
{
  public static void init()
  {
    initCategory();
    initEntry();
    initObjectTag();
  }
  
  private static void initObjectTag()
  {
    ThaumcraftApi.registerObjectTag(new ItemStack(ItemsMN.sickleElemental), new AspectList().add(Aspect.HARVEST, 6).add(Aspect.TOOL, 2).add(Aspect.GREED, 6).add(Aspect.CRYSTAL, 6).add(Aspect.MAGIC, 3).add(Aspect.TREE, 1));
    ThaumcraftApi.registerObjectTag(new ItemStack(ItemsMN.sickleThaumium), new AspectList().add(Aspect.HARVEST, 4).add(Aspect.TOOL, 2).add(Aspect.METAL, 9).add(Aspect.MAGIC, 3).add(Aspect.TREE, 1));
    ThaumcraftApi.registerObjectTag(new ItemStack(ItemsMN.gogglesDark), new AspectList().add(Aspect.ARMOR, 4).add(Aspect.SENSES, 7).add(Aspect.DARKNESS, 5).add(Aspect.ENTROPY, 4).add(Aspect.BEAST, 3));
    ThaumcraftApi.registerObjectTag(new ItemStack(ItemsMN.researchLog), new AspectList().add(Aspect.MIND, 8).add(Aspect.VOID, 6).add(Aspect.MAGIC, 3).add(Aspect.CLOTH, 3));
    ThaumcraftApi.registerObjectTag(new ItemStack(ItemsMN.spectacles), new AspectList().add(Aspect.ARMOR, 4).add(Aspect.SENSES, 5).add(Aspect.CLOTH, 3).add(Aspect.GREED, 3));
  }
  
  private static void initCategory()
  {
    ResearchCategories.registerCategory("magianaturalis", new ResourceLocation("magianaturalis", "textures/items/book_magia_natura.png"), new ResourceLocation("magianaturalis", "textures/gui/background.png"));
  }
  
  private static void initEntry()
  {
    ResearchItem research = null;
    
    research = new CustomResearchItem("INTRO", 0, 0, 0, new ItemStack(ConfigItems.itemResource, 1, 15));
    research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.INTRO.1") }).setSpecial().setRound().setAutoUnlock().registerResearchItem();
    
    research = new CustomResearchItem("CARPENTRY", -2, 2, 0, new ItemStack(BlocksMN.arcaneWood, 1, 4));
    research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.CARPENTRY.1"), new ResearchPage((IRecipe)Recipes.recipes.get("GreatwoodOrn")), new ResearchPage((IRecipe)Recipes.recipes.get("PlankSilverwood")), new ResearchPage((IRecipe)Recipes.recipes.get("GreatwoodGoldOrn1")), new ResearchPage((IRecipe)Recipes.recipes.get("GreatwoodGoldOrn2")), new ResearchPage((IRecipe)Recipes.recipes.get("GreatwoodGoldTrim")) }).setRound().setAutoUnlock().registerResearchItem();
    
    research = new CustomResearchItem("RESEARCH_LOG", new AspectList().add(Aspect.MIND, 3).add(Aspect.VOID, 3).add(Aspect.ORDER, 3), -1, -2, 0, new ItemStack(ItemsMN.researchLog, 1, 0));
    research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.RESEARCH_LOG.1"), new ResearchPage((IArcaneRecipe)Recipes.recipes.get("ResearchLog")) }).setRound().setParentsHidden(new String[] { "DECONSTRUCTOR" }).registerResearchItem();
    
    research = new CustomResearchItem("TRANSCRIBINGTABLE", new AspectList().add(Aspect.MIND, 3).add(Aspect.VOID, 3).add(Aspect.ORDER, 3), -2, -4, 0, new ItemStack(BlocksMN.transcribingTable, 1, 0));
    research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.TRANSCRIBINGTABLE.1"), new ResearchPage((IArcaneRecipe)Recipes.recipes.get("TranscribingTable")) }).setParents(new String[] { "RESEARCH_LOG" }).registerResearchItem();
    
    research = new CustomResearchItem("GOGGLES_DARK", new AspectList().add(Aspect.SENSES, 6).add(Aspect.AURA, 3).add(Aspect.MAGIC, 3).add(Aspect.DARKNESS, 4), -7, 2, 2, new ItemStack(ItemsMN.gogglesDark, 1, 0));
    research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.GOGGLES_DARK.1"), new ResearchPage((InfusionRecipe)Recipes.recipes.get("GogglesDark")) }).setParents(new String[] { "MN_GOGGLES" }).setParentsHidden(new String[] { "SPECTACLES" }).registerResearchItem();
    ThaumcraftApi.addWarpToResearch("GOGGLES_DARK", 1);
    
    research = new CustomResearchItem("SPECTACLES", new AspectList().add(Aspect.SENSES, 3).add(Aspect.AURA, 3).add(Aspect.MAGIC, 3), -6, 0, 1, new ItemStack(ItemsMN.spectacles, 1, 0));
    research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.SPECTACLES.1"), new ResearchPage((IArcaneRecipe)Recipes.recipes.get("Spectacles")) }).setSecondary().setParents(new String[] { "MN_GOGGLES" }).registerResearchItem();
    
    research = new FakeResearchItem("MN_GOGGLES", "magianaturalis", "GOGGLES", "ARTIFICE", -4, 1, ResearchCategories.getResearch("GOGGLES").icon_item).registerResearchItem();
    
    research = new CustomResearchItem("KEY_SPECIAL", new AspectList().add(Aspect.TOOL, 4).add(Aspect.MIND, 3).add(Aspect.MECHANISM, 3), -4, -3, 3, new ItemStack(ItemsMN.key, 1, 0));
    research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.KEY_SPECIAL.1"), new ResearchPage((IArcaneRecipe)Recipes.recipes.get("ThaumiumKey1")), new ResearchPage("mn.research_page.KEY_SPECIAL.2"), new ResearchPage((IArcaneRecipe)Recipes.recipes.get("ThaumiumKey2")) });
    research.setParents(new String[] { "MN_WARDEDARCANA" }).registerResearchItem();
    
    ItemStack chest = new ItemStack(BlocksMN.arcaneChest, 1, 1);
    BlockArcaneChest.setChestType(chest, (byte)1);
    research = new CustomResearchItem("ARCANE_CHEST", new AspectList().add(Aspect.VOID, 4).add(Aspect.MIND, 3).add(Aspect.MECHANISM, 3).add(Aspect.ARMOR, 3), -7, -3, 3, chest);
    research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.ARCANE_CHEST.1"), new ResearchPage((IArcaneRecipe)Recipes.recipes.get("ArcaneChest1")), new ResearchPage((IArcaneRecipe)Recipes.recipes.get("ArcaneChest2")) }).setParents(new String[] { "MN_WARDEDARCANA" }).registerResearchItem();
    
    research = new FakeResearchItem("MN_WARDEDARCANA", "magianaturalis", "WARDEDARCANA", "ARTIFICE", -5, -2, ResearchCategories.getResearch("WARDEDARCANA").icon_item).registerResearchItem();
    
    research = new FakeResearchItem("MN_FOCUSTRADE", "magianaturalis", "FOCUSTRADE", "THAUMATURGY", 2, -4, ResearchCategories.getResearch("FOCUSTRADE").icon_item).registerResearchItem();
    research = new CustomResearchItem("FOCUS_BUILD", new AspectList().add(Aspect.MAGIC, 3).add(Aspect.CRAFT, 6).add(Aspect.ORDER, 2).add(Aspect.EARTH, 2), 4, -5, 2, new ItemStack(ItemsMN.focusBuild, 1, 0));
    research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.FOCUS_BUILD.1"), new ResearchPage((InfusionRecipe)Recipes.recipes.get("FocusBuild")) }).setParents(new String[] { "MN_FOCUSTRADE" }).registerResearchItem();
    
    research = new CustomResearchItem("STONE_PHENO", new AspectList().add(Aspect.MAGIC, 3).add(Aspect.EXCHANGE, 4).add(Aspect.EARTH, 2), 4, -3, 2, new ItemStack(ItemsMN.alchemicalStone, 1, 0));
    research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.STONE_PHENO.1"), new ResearchPage((InfusionRecipe)Recipes.recipes.get("StonePheno")), new ResearchPage((IRecipe[])(IRecipe[])Recipes.recipes.get("WoodConversion")), new ResearchPage((IRecipe[])(IRecipe[])Recipes.recipes.get("ColorConversion")) });
    research.setParents(new String[] { "MN_FOCUSTRADE", "MN_CRUCIBLE" }).setSecondary().registerResearchItem();
    
    research = new FakeResearchItem("MN_CRUCIBLE", "magianaturalis", "CRUCIBLE", "ALCHEMY", 3, -1, ResearchCategories.getResearch("CRUCIBLE").icon_item).registerResearchItem();
    
    research = new CustomResearchItem("STONE_QUICKSILVER", new AspectList().add(Aspect.SENSES, 3).add(Aspect.EXCHANGE, 4).add(Aspect.AURA, 2), 6, -1, 2, new ItemStack(ItemsMN.alchemicalStone, 1, 1));
    research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.STONE_QUICKSILVER.1"), new ResearchPage((InfusionRecipe)Recipes.recipes.get("StoneQuick")) }).setParents(new String[] { "MN_CRUCIBLE" }).registerResearchItem();
    
    research = new FakeResearchItem("MN_FOCUSPOUCH", "magianaturalis", "FOCUSPOUCH", "THAUMATURGY", 4, 2, ResearchCategories.getResearch("FOCUSPOUCH").icon_item).registerResearchItem();
    research = new CustomResearchItem("ENDER_POUCH", new AspectList().add(Aspect.ELDRITCH, 3).add(Aspect.VOID, 3), 6, 3, 2, new ItemStack(ItemsMN.focusPouchEnder, 1, 0));
    research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.ENDER_POUCH.1"), new ResearchPage((InfusionRecipe)Recipes.recipes.get("EnderPouch")) }).setRound().setParents(new String[] { "MN_FOCUSPOUCH" }).registerResearchItem();
    
    research = new FakeResearchItem("MN_TRAVELTRUNK", "magianaturalis", "TRAVELTRUNK", "GOLEMANCY", 1, 3, ResearchCategories.getResearch("TRAVELTRUNK").icon_item).registerResearchItem();
    research = new CustomResearchItem("EVIL_TRUNK", new AspectList().add(Aspect.SOUL, 3).add(Aspect.BEAST, 3).add(Aspect.TAINT, 3), 2, 5, 2, new ItemStack(ItemsMN.evilTrunkSpawner, 1, 0));
    research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.EVIL_TRUNK.1"), new ResearchPage((InfusionRecipe)Recipes.recipes.get("CorruptedTrunk")), new ResearchPage((InfusionRecipe)Recipes.recipes.get("SinisterTrunk")), new ResearchPage((InfusionRecipe)Recipes.recipes.get("DemonicTrunk")), new ResearchPage((InfusionRecipe)Recipes.recipes.get("TaintedTrunk")) }).setParents(new String[] { "MN_TRAVELTRUNK" }).registerResearchItem();
    ThaumcraftApi.addWarpToResearch("EVIL_TRUNK", 1);
    
    research = new CustomResearchItem("JAR_PRISON", new AspectList().add(Aspect.TRAP, 6).add(Aspect.GREED, 3).add(Aspect.EXCHANGE, 3).add(Aspect.MOTION, 3), -1, 4, 3, new ItemStack(BlocksMN.jarPrison, 1, 0));
    research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.JAR_PRISON.1"), new ResearchPage((InfusionRecipe)Recipes.recipes.get("JarPrison")) }).setParentsHidden(new String[] { "JARLABEL" }).registerResearchItem();
    
    research = new CustomResearchItem("SICKLE_THAUM", new AspectList().add(Aspect.TOOL, 3).add(Aspect.CROP, 3).add(Aspect.HARVEST, 3), -4, 3, 1, new ItemStack(ItemsMN.sickleThaumium, 1, 0));
    research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.SICKLE_THAUM.1"), new ResearchPage((IRecipe)Recipes.recipes.get("SickleThaumium")) }).setParentsHidden(new String[] { "THAUMIUM" }).setSecondary().registerResearchItem();
    
    research = new CustomResearchItem("SICKLE_ABUNDANCE", new AspectList().add(Aspect.TOOL, 3).add(Aspect.CROP, 3).add(Aspect.HARVEST, 3).add(Aspect.GREED, 6), -5, 5, 2, new ItemStack(ItemsMN.sickleElemental, 1, 0));
    research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.SICKLE_ABUNDANCE.1"), new ResearchPage((InfusionRecipe)Recipes.recipes.get("SickleElemental")) }).setParents(new String[] { "SICKLE_THAUM" }).setParentsHidden(new String[] { "INFUSION" }).registerResearchItem();
    
    research = new CustomResearchItem("GEO_OCCULTISM", new AspectList().add(Aspect.AURA, 4).add(Aspect.EXCHANGE, 3).add(Aspect.WEATHER, 3).add(Aspect.MAGIC, 6).add(Aspect.EARTH, 2).add(Aspect.AIR, 2), 0, -5, 0, new ItemStack(BlocksMN.geoMorpher));
    research.setPages(new ResearchPage[] { new ResearchPage("mn.research_page.GEO_OCCULTISM.1"), new ResearchPage((InfusionRecipe)Recipes.recipes.get("GeoPylon")), new ResearchPage("mn.research_page.GEO_OCCULTISM.2"), new ResearchPage((IArcaneRecipe)Recipes.recipes.get("BiomeReport")) }).setParentsHidden(new String[] { "INFUSION", "STONE_PHENO" }).registerResearchItem();
    
    research = null;
  }
}
