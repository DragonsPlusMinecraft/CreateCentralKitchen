package plus.dragons.createcentralkitchen.core.config;

public class CentralKitchenServerConfig extends CentralKitchenConfigBase {
    public final RecipeConfig recipe = nested(0, RecipeConfig::new, "Config for Recipe loading conditions");
    
    public static class RecipeConfig extends CentralKitchenConfigBase {
        
    
        @Override
        public String getName() {
            return "recipe";
        }
    }
    
    @Override
    public String getName() {
        return "server";
    }
    
}
