package club.sk1er.mods.tnttimer.tweaker.transformers;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public interface Transformer {
    String[] getClassName();
    void transform(ClassNode classNode, String name);

    default String mapMethodName(ClassNode classNode, MethodNode methodNode) {
        return FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(classNode.name, methodNode.name, methodNode.desc);
    }

    default String mapMethodDesc(MethodNode methodNode) {
        return FMLDeobfuscatingRemapper.INSTANCE.mapMethodDesc(methodNode.desc);
    }
}