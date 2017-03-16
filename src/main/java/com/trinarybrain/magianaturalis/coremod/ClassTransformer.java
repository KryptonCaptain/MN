package com.trinarybrain.magianaturalis.coremod;

import com.trinarybrain.magianaturalis.common.core.Log;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ClassTransformer
  implements IClassTransformer, Opcodes
{
  private static final String classToBeTransformed = "net.minecraft.client.renderer.entity.RendererLivingEntity";
  
  public byte[] transform(String className, String transformedName, byte[] classfileBuffer)
  {
    int isObfuscated = !className.equals(transformedName) ? 1 : 0;
    if (!transformedName.equals("net.minecraft.client.renderer.entity.RendererLivingEntity")) {
      return classfileBuffer;
    }
    Log.logger.info("Transforming: net.minecraft.client.renderer.entity.RendererLivingEntity");
    try
    {
      ClassNode classNode = new ClassNode();
      ClassReader classReader = new ClassReader(classfileBuffer);
      classReader.accept(classNode, 0);
      
      transformRendererLivingEntity(classNode, isObfuscated);
      
      ClassWriter classWriter = new ClassWriter(3);
      classNode.accept(classWriter);
      classfileBuffer = classWriter.toByteArray();
      
      Log.logger.info("Successfully transformed class: net.minecraft.client.renderer.entity.RendererLivingEntity");
    }
    catch (Exception e)
    {
      Log.logger.catching(e);
    }
    return classfileBuffer;
  }
  
  private static void transformRendererLivingEntity(ClassNode classNode, int isObfuscated)
  {
    String RENDER_MODEL = isObfuscated == 1 ? "a" : "renderModel";
    String RENDER_MODEL_DESC = isObfuscated == 1 ? "(Lsv;FFFFFF)V" : "(Lnet/minecraft/entity/EntityLivingBase;FFFFFF)V";
    for (MethodNode method : classNode.methods) {
      if ((method.name.equals(RENDER_MODEL)) && (method.desc.equals(RENDER_MODEL_DESC)))
      {
        AbstractInsnNode targetNode = null;
        for (AbstractInsnNode instruction : method.instructions.toArray()) {
          if ((instruction.getOpcode() == 25) && (((VarInsnNode)instruction).var == 1) && 
            (instruction.getNext().getOpcode() == 184))
          {
            targetNode = instruction;
            break;
          }
        }
        if (targetNode != null)
        {
          targetNode = targetNode.getNext().getNext();
          
          String DESC = isObfuscated == 1 ? "(Lsv;)Z" : "(Lnet/minecraft/entity/EntityLivingBase;)Z";
          MethodInsnNode newNode = new MethodInsnNode(184, "com/trinarybrain/magianaturalis/coremod/MethodStub", "showInvisibleEntityToPlayer", DESC, false);
          method.instructions.set(targetNode.getPrevious(), newNode);
          
          targetNode = targetNode.getNext();
          method.instructions.remove(targetNode.getPrevious());
          
          targetNode = targetNode.getNext();
          method.instructions.remove(targetNode.getPrevious());
          
          targetNode = targetNode.getNext();
          JumpInsnNode jumpNode = new JumpInsnNode(153, ((JumpInsnNode)targetNode.getPrevious()).label);
          method.instructions.set(targetNode.getPrevious(), jumpNode);
          break;
        }
        Log.logger.info("can't find place for byte injection");
        
        break;
      }
    }
  }
}
