package club.sk1er.mods.tnttimer.forge.transformers;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public interface Transformer {
    /**
     * The class name that's being transformed
     *
     * @return the class name
     */
    String[] getClassName();

    /**
     * Perform any asm in order to transform code
     *
     * @param classNode the transformed class node
     * @param name the transformed class name
     */
    void transform(ClassNode classNode, String name);

    /**
     * Map the method name from notch names
     *
     * @param classNode the transformed class node
     * @param methodNode the transformed classes method node
     * @return a mapped method name
     */
    default String mapMethodName(ClassNode classNode, MethodNode methodNode) {
        return FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(
            classNode.name, methodNode.name, methodNode.desc);
    }

    /**
     * Map the method desc from notch names
     *
     * @param methodNode the transformed method node
     * @return a mapped method desc
     */
    default String mapMethodDesc(MethodNode methodNode) {
        return FMLDeobfuscatingRemapper.INSTANCE.mapMethodDesc(methodNode.desc);
    }

    /**
     * Map the method name from notch names
     *
     * @param methodInsnNode the transformed method insn node
     * @return a mapped insn method
     */
    default String mapMethodNameFromNode(MethodInsnNode methodInsnNode) {
        return FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(
            methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc);
    }

    /**
     * Map the field name from notch names
     *
     * @param fieldInsnNode the transformed field insn node
     * @return a mapped insn field
     */
    default String mapFieldNameFromNode(FieldInsnNode fieldInsnNode) {
        return FMLDeobfuscatingRemapper.INSTANCE.mapFieldName(
            fieldInsnNode.owner, fieldInsnNode.name, fieldInsnNode.desc);
    }
}