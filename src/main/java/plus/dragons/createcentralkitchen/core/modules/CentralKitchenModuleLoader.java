package plus.dragons.createcentralkitchen.core.modules;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.slf4j.Logger;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;

public class CentralKitchenModuleLoader {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Type MODULE_ANNOTATION = Type.getType(CentralKitchenModule.class);
    
    @SuppressWarnings("unchecked")
    public static void loadModules(ModContainer container) {
        var modid = container.getModId();
        container.getModInfo()
            .getOwningFile()
            .getFile()
            .getScanResult()
            .getAnnotations()
            .stream()
            .filter(data -> data.targetType() == ElementType.TYPE && data.annotationType().equals(MODULE_ANNOTATION))
            .map(data -> new ModuleData(modid,
                (String) data.annotationData().get("id"),
                (List<String>) data.annotationData().getOrDefault("dependencies", new ArrayList<>()),
                (Integer) data.annotationData().getOrDefault("priority", 0),
                data.clazz().getClassName()))
            .filter(ModuleData::enabled)
            .sorted()
            .forEach(ModuleData::load);
    }
    
    private record ModuleData(String modId, String moduleId, List<String> dependencies, int priority, String mainClass)
        implements Comparable<ModuleData> {
        
        public boolean enabled() {
            ModList modList = ModList.get();
            for (String modid : dependencies) {
                if (!modList.isLoaded(modid))
                    return false;
            }
            return true;
        }
        
        public void load() {
            try {
                Class.forName(mainClass).getConstructor().newInstance();
                LOGGER.info("Successfully loaded module [%s] for mod [%s]".formatted(moduleId, modId));
            } catch (Throwable t) {
                LOGGER.error("Failed to load module [%s] for mod [%s]".formatted(moduleId, modId), t);
            }
        }
        
        @Override
        public int compareTo(@NotNull CentralKitchenModuleLoader.ModuleData other) {
            if (priority != other.priority) {
                return Integer.compare(priority, other.priority);
            }
            return moduleId.compareTo(other.moduleId);
        }
        
    }
    
}
