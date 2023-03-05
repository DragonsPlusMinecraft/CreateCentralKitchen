package plus.dragons.createcentralkitchen.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("SuspiciousMethodCalls")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CentralKitchenConfigs {
    private static final Map<ForgeConfigSpec, CentralKitchenConfigBase> CONFIGS = new HashMap<>();
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
        context.registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
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

}
