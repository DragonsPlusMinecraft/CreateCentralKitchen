package plus.dragons.createcentralkitchen.foundation.utility;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.moddiscovery.ModAnnotation;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Type;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import static net.minecraftforge.fml.Logging.LOADING;

public class AutomaticModLoadSubscriber {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Type MOD_LOAD_SUBSCRIBER = Type.getType(ModLoadSubscriber.class);
    private static final List<ModAnnotation.EnumHolder> DEFAULT_DIST = List.of(
        new ModAnnotation.EnumHolder(null, "CLIENT"),
        new ModAnnotation.EnumHolder(null, "DEDICATED_SERVER"));
    private static final ModAnnotation.EnumHolder DEFAULT_BUS = new ModAnnotation.EnumHolder(null, "MOD");
    
    public static void load(FMLModContainer mod, Class<?> modClass) {
        LOGGER.debug(LOADING, "Attempting to load class annotated with @ModLoadSubscriber {}", mod.getModId());
        String modId = mod.getModId();
        ClassLoader classLoader = modClass.getClassLoader();
        ModFileScanData scanData = mod.getModInfo().getOwningFile().getFile().getScanResult();
        ModList mods = ModList.get();
        List<ModFileScanData.AnnotationData> targets = scanData.getAnnotations().stream()
            .filter(annotationData -> MOD_LOAD_SUBSCRIBER.equals(annotationData.annotationType()))
            //Sort the targets, so we get a fine loading order
            //Mod id should be the best key, but class name is also fine as we have them named after mod names
            .sorted(Comparator.comparing(data -> data.clazz().getClassName()))
            .toList();
        for (var target : targets) {
            @SuppressWarnings("unchecked")
            final List<ModAnnotation.EnumHolder> distHolder =
                (List<ModAnnotation.EnumHolder>)target.annotationData().getOrDefault("value", DEFAULT_DIST);
            final EnumSet<Dist> dist = distHolder.stream()
                .map(holder -> Dist.valueOf(holder.getValue()))
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Dist.class)));
            if (!dist.contains(FMLEnvironment.dist))
                continue;
            final String requiredModId = (String)target.annotationData().getOrDefault("modid", modId);
            if (!Mods.isLoaded(requiredModId))
                continue;
            final ModAnnotation.EnumHolder busHolder =
                (ModAnnotation.EnumHolder)target.annotationData().getOrDefault("bus", DEFAULT_BUS);
            final Bus bus = Bus.valueOf(busHolder.getValue());
            final String targetClassName = target.clazz().getClassName();
            try {
                LOGGER.debug(LOADING, "Auto-loading class {}", targetClassName);
                Class<?> targetClass = Class.forName(targetClassName, true, classLoader);
                LOGGER.debug(LOADING, "Auto-subscribing {} to {}", targetClassName, bus);
                bus.bus().get().register(targetClass);
            } catch (ClassNotFoundException exception) {
                LOGGER.fatal(LOADING, "Failed to load class {} for @ModLoadSubscriber annotation", targetClassName, exception);
                throw new RuntimeException(exception);
            }
        }
    }
    
}
