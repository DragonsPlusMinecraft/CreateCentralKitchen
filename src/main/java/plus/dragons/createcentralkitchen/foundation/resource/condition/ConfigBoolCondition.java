package plus.dragons.createcentralkitchen.foundation.resource.condition;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.simibubi.create.foundation.config.ConfigBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.foundation.config.CentralKitchenConfigs;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum ConfigBoolCondition implements ICondition {
    PIE_OVERHAUL(CentralKitchenConfigs.COMMON.integration.enablePieOverhaul);
    private final String name;
    private final ConfigBase.ConfigBool config;
    
    ConfigBoolCondition(ConfigBase.ConfigBool config) {
        this.name = name().toLowerCase(Locale.ROOT);
        this.config = config;
    }
    
    @Override
    public ResourceLocation getID() {
        return Serializer.ID;
    }
    
    @Override
    public boolean test(IContext context) {
        return config.get();
    }
    
    @SuppressWarnings("removal")
    @Override
    public boolean test() {
        return test(IContext.EMPTY);
    }
    
    public static class Serializer implements IConditionSerializer<ConfigBoolCondition> {
        private static final ResourceLocation ID = CentralKitchen.genRL("config");
        private static final Map<String, ConfigBoolCondition> CONDITIONS = new HashMap<>();
        static {
            for (var condition : ConfigBoolCondition.values())
                CONDITIONS.put(condition.name, condition);
        }
    
        @Override
        public void write(JsonObject json, ConfigBoolCondition value) {
            json.addProperty("config", value.name);
        }
    
        @Override
        public ConfigBoolCondition read(JsonObject json) {
            String id = GsonHelper.getAsString(json, "config");
            if (CONDITIONS.containsKey(id))
                return CONDITIONS.get(id);
            else throw new JsonParseException("Unknown config: " + id);
        }
    
        @Override
        public ResourceLocation getID() {
            return ID;
        }
        
    }
    
}
