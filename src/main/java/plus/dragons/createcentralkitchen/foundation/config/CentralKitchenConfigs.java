package plus.dragons.createcentralkitchen.foundation.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import plus.dragons.createcentralkitchen.CentralKitchen;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = CentralKitchen.ID, bus = Bus.MOD)
public class CentralKitchenConfigs {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Marker MARKER = MarkerManager.getMarker("CONFIG");
    private static final Map<IConfigSpec<?>, CentralKitchenConfigBase> CONFIGS = new HashMap<>();
    public static CentralKitchenCommonConfig COMMON;
    public static ForgeConfigSpec COMMON_SPEC;
    static {
        Pair<CentralKitchenCommonConfig, ForgeConfigSpec> commonConfig = new ForgeConfigSpec.Builder()
            .configure(builder -> {
                CentralKitchenCommonConfig config = new CentralKitchenCommonConfig();
                config.registerAll(builder);
                return config;
            });
        COMMON = commonConfig.getKey();
        COMMON_SPEC = commonConfig.getValue();
        CONFIGS.put(COMMON_SPEC, COMMON);
    }
    
    public static void register(ModLoadingContext context) {
        var container = context.getActiveContainer();
        var common = new ModConfig(ModConfig.Type.COMMON, COMMON_SPEC, container);
        container.addConfig(common);
        earlyLoadConfig(COMMON, COMMON_SPEC, container, common);
    }
    
    private static void earlyLoadConfig(CentralKitchenConfigBase object,
                                        ForgeConfigSpec spec,
                                        ModContainer container,
                                        ModConfig config)
    {
        LOGGER.trace(MARKER, "Early Loading config file type {} at {} for {}", config.getType(), config.getFileName(), config.getModId());
        var configData = config.getHandler().reader(FMLPaths.CONFIGDIR.get()).apply(config);
        spec.acceptConfig(configData);
        object.onLoad();
    }
    
    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        var spec = event.getConfig().getSpec();
        if (CONFIGS.containsKey(spec)) {
            CONFIGS.get(spec).onLoad();
        }
    }
    
    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        var spec = event.getConfig().getSpec();
        if (CONFIGS.containsKey(spec)) {
            CONFIGS.get(spec).onReload();
        }
    }
    
    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        CONFIGS.values().forEach(config -> event.enqueueWork(config::onCommonSetup));
    }

}
