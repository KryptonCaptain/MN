package com.trinarybrain.magianaturalis.coremod;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.Name;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import java.util.Map;

@IFMLLoadingPlugin.Name("MN-COREMOD")
@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.TransformerExclusions({"com.trinarybrain.magianaturalis.coremod"})
public class Plugin
  implements IFMLLoadingPlugin
{
  public String[] getASMTransformerClass()
  {
    return new String[] { "com.trinarybrain.magianaturalis.coremod.ClassTransformer" };
  }
  
  public String getModContainerClass()
  {
    return null;
  }
  
  public String getSetupClass()
  {
    return null;
  }
  
  public void injectData(Map<String, Object> data) {}
  
  public String getAccessTransformerClass()
  {
    return null;
  }
}
