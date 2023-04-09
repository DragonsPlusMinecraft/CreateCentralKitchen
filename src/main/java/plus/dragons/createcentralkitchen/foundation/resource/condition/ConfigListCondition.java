package plus.dragons.createcentralkitchen.foundation.resource.condition;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableBiMap;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.foundation.config.CentralKitchenConfigBase;
import plus.dragons.createcentralkitchen.foundation.config.CentralKitchenConfigs;

public class ConfigListCondition implements ICondition {
    private static final ImmutableBiMap<String, CentralKitchenConfigBase.ConfigList<String>> CONFIGS = ImmutableBiMap
        .<String, CentralKitchenConfigBase.ConfigList<String>>builder()
        .put("pie_overhaul_black_list", CentralKitchenConfigs.COMMON.integration.pieOverhaulBlackList)
        .build();
    private final String name;
    private final CentralKitchenConfigBase.ConfigList<String> config;
    private final String value;
    
    public ConfigListCondition(String name, String value) {
        Preconditions.checkArgument(CONFIGS.containsKey(name), "Id " + name + " has no config registered");
        this.name = name;
        this.config = CONFIGS.get(name);
        this.value = value;
    }
    
    public ConfigListCondition(CentralKitchenConfigBase.ConfigList<String> config, String value) {
        Preconditions.checkArgument(CONFIGS.containsValue(config), "Config " + config.getName() + " is not registered");
        this.name = CONFIGS.inverse().get(config);
        this.config = config;
        this.value = value;
    }
    
    public NotCondition blackList() {
        return new NotCondition(this);
    }
    
    @Override
    public ResourceLocation getID() {
        return Serializer.ID;
    }
    
    @Override
    public boolean test(IContext context) {
        return config.get().contains(value);
    }
    
    public static class Serializer implements IConditionSerializer<ConfigListCondition> {
        private static final ResourceLocation ID = CentralKitchen.genRL("config_list");
    
        @Override
        public void write(JsonObject json, ConfigListCondition value) {
            json.addProperty("config", value.name);
            json.addProperty("value", value.value);
        }
    
        @Override
        public ConfigListCondition read(JsonObject json) {
            return new ConfigListCondition(
                GsonHelper.getAsString(json, "config"),
                GsonHelper.getAsString(json, "value"));
        }
    
        @Override
        public ResourceLocation getID() {
            return ID;
        }
        
    }
    
}
