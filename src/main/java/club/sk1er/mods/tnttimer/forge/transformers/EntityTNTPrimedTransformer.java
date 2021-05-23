package club.sk1er.mods.tnttimer.forge.transformers;

import org.objectweb.asm.tree.*;

import java.util.Iterator;

import static org.objectweb.asm.Opcodes.*;

public class EntityTNTPrimedTransformer implements Transformer {
    @Override
    public String[] getClassName() {
        return new String[]{"net.minecraft.client.renderer.entity.RenderTNTPrimed"};
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            String methodName = mapMethodName(classNode, methodNode);
            String methodDesc = mapMethodDesc(methodNode);
            if ((methodName.equals("doRender") || methodName.equals("func_76986_a")) && methodDesc.equals("(Lnet/minecraft/entity/item/EntityTNTPrimed;DDDFF)V")) {
                Iterator<AbstractInsnNode> iterator = methodNode.instructions.iterator();
                while (iterator.hasNext()) {
                    AbstractInsnNode node = iterator.next();
                    if (node.getOpcode() == INVOKESTATIC) {
                        MethodInsnNode methodInsnNode = (MethodInsnNode) node;
                        String methodInsnName = mapMethodNameFromNode(methodInsnNode);
                        if (methodInsnName.equals("popMatrix") || methodInsnName.equals("func_179121_F")) {
                            InsnList list = new InsnList();
                            list.add(new FieldInsnNode(GETSTATIC, "club/sk1er/mods/tnttimer/TNTTime", "instance", "Lclub/sk1er/mods/tnttimer/TNTTime;"));
                            list.add(new VarInsnNode(ALOAD, 0));
                            list.add(new VarInsnNode(ALOAD, 1));
                            list.add(new VarInsnNode(DLOAD, 2));
                            list.add(new VarInsnNode(DLOAD, 4));
                            list.add(new VarInsnNode(DLOAD, 6));
                            list.add(new VarInsnNode(FLOAD, 9));
                            list.add(new MethodInsnNode(INVOKEVIRTUAL, "club/sk1er/mods/tnttimer/TNTTime", "renderTag", "(Lnet/minecraft/client/renderer/entity/RenderTNTPrimed;Lnet/minecraft/entity/item/EntityTNTPrimed;DDDF)V", false));
                            methodNode.instructions.insert(methodInsnNode, list);
                            return;
                        }
                    }
                }
            }
        }
    }
}
