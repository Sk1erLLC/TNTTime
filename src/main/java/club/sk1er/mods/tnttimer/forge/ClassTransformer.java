package club.sk1er.mods.tnttimer.forge;


import club.sk1er.mods.tnttimer.forge.transformers.EntityTNTPrimedTransformer;
import club.sk1er.mods.tnttimer.forge.transformers.Transformer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.util.Collection;

public class ClassTransformer implements IClassTransformer {

    private final Multimap<String, Transformer> transformerMap = ArrayListMultimap.create();
    public static final Logger LOGGER = LogManager.getLogger("TNTTimer Transformers");

    public ClassTransformer() {
        registerTransformer(new EntityTNTPrimedTransformer());
    }

    private void registerTransformer(Transformer transformer) {
        for (String cls : transformer.getClassName()) {
            transformerMap.put(cls, transformer);
        }
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null) return null;

        Collection<Transformer> transformers = transformerMap.get(transformedName);
        if (transformers.isEmpty()) return bytes;

        LOGGER.info("Found {} transformers for {}", transformers.size(), transformedName);

        ClassReader reader = new ClassReader(bytes);
        ClassNode node = new ClassNode();
        reader.accept(node, ClassReader.EXPAND_FRAMES);

        for (Transformer transformer : transformers) {
            LOGGER.info("Applying transformer {} on {}...", transformer.getClass().getName(), transformedName);
            transformer.transform(node, transformedName);
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        try {
            node.accept(writer);
        } catch (Throwable t) {
            LOGGER.error("Exception when transforming " + transformedName + " : " + t.getClass().getSimpleName());
            t.printStackTrace();
        }

        return writer.toByteArray();
    }
}