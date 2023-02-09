package plus.dragons.createcentralkitchen.common.modules;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.ModLoadingStage;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;
import org.slf4j.Logger;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;

public class ModModuleLoader {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Type MOD_MODULE = Type.getType(ModModule.class);
    
    @SuppressWarnings("unchecked")
    public static void loadModules() {
        var modContainer = ModLoadingContext.get().getActiveContainer();
        if (modContainer.getCurrentState() != ModLoadingStage.CONSTRUCT) {
            LOGGER.error("ModModuleLoader.loadModules() should be called in mod's constructor");
            return;
        }
        var modid = modContainer.getModId();
        modContainer.getModInfo()
            .getOwningFile()
            .getFile()
            .getScanResult()
            .getAnnotations()
            .stream()
            .filter(data -> data.targetType() == ElementType.TYPE && data.annotationType().equals(MOD_MODULE))
            .map(data -> new ModModuleData(modid,
                (String) data.annotationData().get("id"),
                (List<String>) data.annotationData().getOrDefault("dependencies", new ArrayList<>()),
                (Integer) data.annotationData().getOrDefault("priority", 0),
                data.clazz().getClassName()))
            .filter(ModModuleData::enabled)
            .sorted()
            .forEach(ModModuleData::load);
    }
    
    private record ModModuleData(String modId, String moduleId, List<String> dependencies, int priority, String mainClass)
        implements Comparable<ModModuleData> {
        
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
        public int compareTo(@NotNull ModModuleLoader.ModModuleData other) {
            if (priority != other.priority) {
                return Integer.compare(priority, other.priority);
            }
            return moduleId.compareTo(other.moduleId);
        }
        
    }
    
}
