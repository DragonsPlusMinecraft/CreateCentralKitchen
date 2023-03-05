package plus.dragons.createcentralkitchen.core.config;

import com.google.common.collect.Lists;
import com.simibubi.create.foundation.config.ui.ConfigAnnotations;

public class CentralKitchenCommonConfig extends CentralKitchenConfigBase {
    public final AutomationConfig automationConfig = nested(0, AutomationConfig::new, "Config for Automation related components");
    public final IntegrationConfig integrationConfig = nested(0, IntegrationConfig::new, "Config for Integration with other mods");
    
    public static class AutomationConfig extends CentralKitchenConfigBase {
        
        public final ConfigBool enableCuttingBoardDeploying = b(true,
            "enableCuttingBoardDeploying",
            "Whether allowing Deployers to perform Cutting Board Recipes");
        
        public final ConfigList<String> armInteractionPointBlackList = ls(Lists.newArrayList(
                "stove",
                "blaze_stove",
                "cooking_pot",
                "skillet",
                "cutting_board",
                "basket",
                "kettle",
                "copper_pot"),
            "enabledArmInteractionPointType",
            "List of supported Arm Interaction Point types",
            ConfigAnnotations.RequiresRestart.BOTH.asComment());
        
        @Override
        public String getName() {
            return "automation";
        }
        
    }
    
    public static class IntegrationConfig extends CentralKitchenConfigBase {
        public final ConfigBool enablePieOverhaul = b(true,
            "enablePieOverhaul",
            "Whether replacing vanilla and modded pies into Farmer's Delight style");
    
        @Override
        public String getName() {
            return "integration";
        }
    }
    
    @Override
    public String getName() {
        return "common";
    }
    
}
