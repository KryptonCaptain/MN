package com.trinarybrain.magianaturalis.client.core;

import com.trinarybrain.api.ISpectacles;
import com.trinarybrain.magianaturalis.client.util.RenderUtil;
import com.trinarybrain.magianaturalis.common.item.artifact.ItemGogglesDark;
import com.trinarybrain.magianaturalis.common.item.focus.ItemFocusBuild;
import com.trinarybrain.magianaturalis.common.tile.TileArcaneChest;
import com.trinarybrain.magianaturalis.common.util.FocusBuildHelper;
import com.trinarybrain.magianaturalis.common.util.FocusBuildHelper.Meta;
import com.trinarybrain.magianaturalis.common.util.Platform;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderPlayerEvent.Specials.Pre;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.nodes.INode;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.tiles.TileNodeEnergized;
import thaumcraft.common.tiles.TileOwned;

@SideOnly(Side.CLIENT)
public class EventHandlerRender
{
  public Minecraft mc = Minecraft.getMinecraft();
  FontRenderer fontRenderer = this.mc.fontRenderer;
  RenderItem itemRender = new RenderItem();
  ItemStack lastItem = null;
  int lastCount = 0;
  private static final ResourceLocation rlSilk = new ResourceLocation("thaumcraft", "textures/foci/silktouch.png");
  private static final ResourceLocation rlHudFrame = new ResourceLocation("magianaturalis", "textures/misc/frame-gold.png");
  
  public static void register()
  {
    MinecraftForge.EVENT_BUS.register(new EventHandlerRender());
  }
  
  @SubscribeEvent
  public void renderOverlay(RenderGameOverlayEvent event)
  {
    if (event.type == RenderGameOverlayEvent.ElementType.HELMET) {
      if ((Minecraft.isGuiEnabled()) && (!this.mc.isGamePaused()) && (this.mc.currentScreen == null) && (!this.mc.gameSettings.showDebugInfo)) {
        if ((this.mc.renderViewEntity != null) && ((this.mc.renderViewEntity instanceof EntityPlayer)))
        {
          EntityPlayer player = (EntityPlayer)this.mc.renderViewEntity;
          
          ItemStack stack = player.inventory.armorItemInSlot(3);
          if ((stack != null) && ((stack.getItem() instanceof ISpectacles)) && (((ISpectacles)stack.getItem()).drawSpectacleHUD(stack, player))) {
            renderSpectaclesHUD(this.mc, player);
          }
          stack = player.inventory.getCurrentItem();
          if ((stack != null) && ((stack.getItem() instanceof ItemWandCasting)))
          {
            ItemWandCasting wand = (ItemWandCasting)stack.getItem();
            ItemFocusBasic focus = wand.getFocus(stack);
            if ((focus != null) && ((focus instanceof ItemFocusBuild)))
            {
              ItemStack focusStack = wand.getFocusItem(stack);
              renderBuildFocusHUD(focusStack, player);
            }
          }
        }
      }
    }
  }
  
  private static final ResourceLocation rlGlowingEyes = new ResourceLocation("magianaturalis", "textures/models/glowingEyes.png");
  private static ModelBiped modelOverlay = new ModelBiped();
  
  @SubscribeEvent
  public void renderPlayerSpecial(RenderPlayerEvent.Specials.Pre event)
  {
    ModelBiped model = event.renderer.modelBipedMain;
    ItemStack itemstack = event.entityPlayer.inventory.armorItemInSlot(3);
    if ((event.renderHelmet) && (itemstack != null) && ((itemstack.getItem() instanceof ItemGogglesDark)))
    {
      GL11.glPushMatrix();
      float f6 = 0.0625F;
      
      model.bipedHead.postRender(f6);
      this.mc.renderEngine.bindTexture(rlGlowingEyes);
      
      GL11.glTranslatef(0.0F, f6, -0.01F);
      GL11.glScalef(1.25F, 1.25F, 1.25F);
      GL11.glEnable(3042);
      GL11.glBlendFunc(1, 771);
      if (event.entityPlayer.isInvisible()) {
        GL11.glDepthMask(false);
      } else {
        GL11.glDepthMask(true);
      }
      char c0 = 61680;
      int j = c0 % 65536;
      int k = c0 / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      modelOverlay.bipedHead.render(f6);
      GL11.glDisable(3042);
      
      GL11.glPopMatrix();
    }
  }
  
  protected void renderBuildFocusHUD(ItemStack focusStack, EntityPlayer player)
  {
    GL11.glClear(256);
    
    FocusBuildHelper.Meta meta = FocusBuildHelper.getMeta(focusStack);
    Block pblock = null;
    int pbdata = 0;
    Item item = null;
    ItemStack pickedBlock = null;
    if (meta == FocusBuildHelper.Meta.UNIFORM)
    {
      if ((this.mc.objectMouseOver != null) && (this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK))
      {
        pblock = player.worldObj.getBlock(this.mc.objectMouseOver.blockX, this.mc.objectMouseOver.blockY, this.mc.objectMouseOver.blockZ);
        pbdata = player.worldObj.getBlockMetadata(this.mc.objectMouseOver.blockX, this.mc.objectMouseOver.blockY, this.mc.objectMouseOver.blockZ);
        item = Item.getItemFromBlock(pblock);
      }
      if (item != null)
      {
        if (pblock == Blocks.double_plant) {
          pbdata = pblock.getDamageValue(player.worldObj, this.mc.objectMouseOver.blockX, this.mc.objectMouseOver.blockY, this.mc.objectMouseOver.blockZ);
        }
        pickedBlock = new ItemStack(item, 1, pbdata);
      }
      if ((pickedBlock == null) && (pblock != null)) {
        if (pblock == Blocks.lit_redstone_ore) {
          pickedBlock = new ItemStack(Blocks.redstone_ore);
        } else {
          pickedBlock = pblock.getPickBlock(this.mc.objectMouseOver, player.worldObj, this.mc.objectMouseOver.blockX, this.mc.objectMouseOver.blockY, this.mc.objectMouseOver.blockZ);
        }
      }
    }
    else
    {
      int[] i = FocusBuildHelper.getPickedBlock(focusStack);
      pblock = Block.getBlockById(i[0]);
      pbdata = i[1];
      if ((pblock != Blocks.air) && (pblock != null))
      {
        item = Item.getItemFromBlock(pblock);
        pickedBlock = new ItemStack(item, 1, pbdata);
      }
    }
    GL11.glPushMatrix();
    GL11.glEnable(3042);
    GL11.glTranslatef(45.0F, 40.0F, 0.0F);
    RenderUtil.drawTextureQuad(rlHudFrame, 32.0F, 32.0F);
    if ((pickedBlock == null) && (meta == FocusBuildHelper.Meta.UNIFORM))
    {
      GL11.glTranslatef(1.5F, 1.0F, 0.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.3F);
      RenderUtil.drawTextureQuad(rlSilk, 30.0F, 30.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
    GL11.glPopMatrix();
    if (pickedBlock != null)
    {
      int amount = this.lastCount;
      if ((player.inventory.inventoryChanged) || (!pickedBlock.isItemEqual(this.lastItem)))
      {
        amount = 0;
        for (ItemStack is : player.inventory.mainInventory) {
          if ((is != null) && (is.isItemEqual(pickedBlock))) {
            amount += is.stackSize;
          }
        }
        this.lastItem = pickedBlock;
        player.inventory.inventoryChanged = false;
      }
      this.lastCount = amount;
      
      GL11.glPushMatrix();
      GL11.glTranslatef(49.0F, 44.0F, 0.0F);
      GL11.glScalef(1.5F, 1.5F, 1.5F);
      if ((this.itemRender != null) && (this.fontRenderer != null)) {
        RenderUtil.drawItemStack(this.itemRender, this.fontRenderer, pickedBlock, 0, 0);
      }
      GL11.glEnable(3042);
      
      GL11.glPushMatrix();
      String am = "" + amount;
      int sw = this.fontRenderer.getStringWidth(am);
      GL11.glTranslatef(0.0F, -this.fontRenderer.FONT_HEIGHT, 500.0F);
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      for (int a = -1; a <= 1; a++) {
        for (int b = -1; b <= 1; b++) {
          if (((a == 0) || (b == 0)) && ((a != 0) || (b != 0))) {
            this.mc.fontRenderer.drawString(am, a + 16 - sw, b + 24, 0);
          }
        }
      }
      this.mc.fontRenderer.drawString(am, 16 - sw, 24, 16777215);
      if (meta == FocusBuildHelper.Meta.UNIFORM)
      {
        GL11.glPushMatrix();
        GL11.glScalef(1.5F, 1.5F, 1.5F);
        GL11.glTranslatef(15.0F, 15.0F, 0.0F);
        RenderUtil.drawTextureQuad(rlSilk, 16.0F, 16.0F);
        GL11.glPopMatrix();
      }
      GL11.glPopMatrix();
      GL11.glPopMatrix();
    }
  }
  
  protected void renderSpectaclesHUD(Minecraft mc, EntityPlayer player)
  {
    Boolean meterEquiped = Boolean.valueOf(false);
    if (player.inventory.getCurrentItem() != null) {
      if (player.inventory.getCurrentItem().getItem() == ConfigItems.itemThaumometer) {
        meterEquiped = Boolean.valueOf(true);
      }
    }
    if ((mc.gameSettings.thirdPersonView == 0) && (mc.currentScreen == null) && (!meterEquiped.booleanValue()))
    {
      TileEntity tile = null;
      MovingObjectPosition mop = mc.objectMouseOver;
      if (mop != null) {
        if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
          tile = player.worldObj.getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
        }
      }
      if (tile != null)
      {
        ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        int w = scaledresolution.getScaledWidth();
        int h = scaledresolution.getScaledHeight();
        if ((tile instanceof INode))
        {
          INode node = (INode)tile;
          String meta = Platform.translate("nodetype." + node.getNodeType() + ".name");
          if (node.getNodeModifier() != null) {
            meta = meta + ", " + Platform.translate(new StringBuilder().append("nodemod.").append(node.getNodeModifier()).append(".name").toString());
          }
          String name = Platform.translate("tile.blockAiry.0.name");
          FontRenderer fontRenderer = mc.fontRenderer;
          GL11.glPushMatrix();
          GL11.glTranslatef(w / 2, h / 2, 0.0F);
          fontRenderer.drawStringWithShadow(name, -fontRenderer.getStringWidth(name) / 2, 25, 15751100);
          fontRenderer.drawStringWithShadow(meta, -fontRenderer.getStringWidth(meta) / 2, 35, 16777215);
          GL11.glPopMatrix();
        }
        else if ((tile instanceof TileNodeEnergized))
        {
          TileNodeEnergized nodeEnergized = (TileNodeEnergized)tile;
          
          String meta = Platform.translate("nodetype." + nodeEnergized.getNodeType() + ".name");
          if (nodeEnergized.getNodeModifier() != null) {
            meta = meta + ", " + Platform.translate(new StringBuilder().append("nodemod.").append(nodeEnergized.getNodeModifier()).append(".name").toString());
          }
          String name = Platform.translate("tile.blockAiry.5.name");
          FontRenderer fontRenderer = mc.fontRenderer;
          GL11.glPushMatrix();
          GL11.glTranslatef(w / 2, h / 2, 0.0F);
          fontRenderer.drawStringWithShadow(name, -fontRenderer.getStringWidth(name) / 2, 25, 15751100);
          fontRenderer.drawStringWithShadow(meta, -fontRenderer.getStringWidth(meta) / 2, 35, 16777215);
          GL11.glPopMatrix();
        }
        else if ((tile instanceof TileOwned))
        {
          TileOwned owned = (TileOwned)tile;
          String owner = EnumChatFormatting.DARK_PURPLE + "Owner" + new StringBuilder().append(EnumChatFormatting.RESET).append(" ").toString() + owned.owner;
          FontRenderer fontRenderer = mc.fontRenderer;
          GL11.glPushMatrix();
          GL11.glTranslatef(w / 2, h / 2, 0.0F);
          fontRenderer.drawStringWithShadow(owner, -(fontRenderer.getStringWidth(owner) - 4) / 2, 25, 16777215);
          GL11.glPopMatrix();
        }
        else if ((tile instanceof TileArcaneChest))
        {
          TileArcaneChest chest = (TileArcaneChest)tile;
          String name = EnumChatFormatting.DARK_PURPLE + "Owner" + new StringBuilder().append(EnumChatFormatting.RESET).append(" ").toString() + chest.getOwnerName();
          GL11.glPushMatrix();
          GL11.glTranslatef(w / 2, h / 2, 0.0F);
          this.fontRenderer.drawStringWithShadow(name, -(this.fontRenderer.getStringWidth(name) - 4) / 2, 25, 16777215);
          GL11.glPopMatrix();
        }
      }
    }
  }
}
