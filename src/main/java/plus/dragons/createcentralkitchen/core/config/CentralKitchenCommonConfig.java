package plus.dragons.createcentralkitchen.core.config;

import com.simibubi.create.foundation.config.ui.ConfigAnnotations;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class CentralKitchenCommonConfig extends CentralKitchenConfigBase {
    public final AutomationConfig automation = nested(0, AutomationConfig::new, "Config for Automation related components");
    public final IntegrationConfig integration = nested(0, IntegrationConfig::new, "Config for Integration with other mods");
    
    public static class AutomationConfig extends CentralKitchenConfigBase {
        
        public final ConfigBool enableCuttingBoardDeploying = b(true,
            "enableCuttingBoardDeploying",
            "Whether allowing Deployers to perform Cutting Board Recipes");
        
        public final ConfigList<String> armInteractionPointBlackList = stringList(List.of(),
            "armInteractionPointBlackList",
            "Arm Interaction Point types in this list will not be registered",
            "Allowed values: [\"stove\", \"blaze_stove\", \"cooking_pot\", \"skillet\", \"cutting_board\", \"basket\", \"kettle\", \"copper_pot\"]",
            ConfigAnnotations.RequiresRestart.BOTH.asComment());
        
        public final ConfigRegistryObjectList<BlockEntityType<?>> boostingCookerList = new ConfigRegistryObjectList<>(
            "boostingCookerList",
            ForgeRegistries.BLOCK_ENTITIES,
            createRegistryObjects(ForgeRegistries.BLOCK_ENTITIES, List.of(
                new ResourceLocation("farmersdelight", "cooking_pot"),
                new ResourceLocation("farmersdelight", "skillet"),
                new ResourceLocation("farmersrespite", "kettle"),
                new ResourceLocation("miners_delight", "copper_pot"))),
            "List of Block Entities that can be boosted when placed on Blaze Stove");
        
        @Override
        public String getName() {
            return "automation";
        }
        
    }
    
    public static class IntegrationConfig extends CentralKitchenConfigBase {
        public final ConfigBool enablePieOverhaul = b(true,
            "enablePieOverhaul",
            "Whether replacing vanilla and modded pies into Farmer's Delight style",
            ConfigAnnotations.RequiresRestart.BOTH.asComment());
        
        public final ConfigIdList pieOverhaulBlackList = idList(List.of(),
            "pieOverhaulBlackList",
            "Pie items in this list will not be included in pie overhaul",
            ConfigAnnotations.RequiresRestart.BOTH.asComment());
    
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
