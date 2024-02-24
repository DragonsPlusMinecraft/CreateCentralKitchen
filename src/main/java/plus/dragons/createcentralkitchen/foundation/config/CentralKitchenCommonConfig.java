package plus.dragons.createcentralkitchen.foundation.config;

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
        
        public final ConfigRegistryObjectList<BlockEntityType<?>> boostingCookerList = new ConfigRegistryObjectList<>(
            "boostingCookerList",
            ForgeRegistries.BLOCK_ENTITY_TYPES,
            createRegistryObjects(ForgeRegistries.BLOCK_ENTITY_TYPES, List.of(
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

        public final ConfigBool enableHarvesterSupportForOverweightFarming = b(true,
                "enableHarvesterSupportForOverweightFarming",
                "For harvester to function properly, turning on this support will cause the collision shape of the overweight crop block to disappear.",
                ConfigAnnotations.RequiresRestart.SERVER.asComment());

        public final ConfigBool enableHarvesterSupportForFarmersRespite = b(true,
                "enableHarvesterSupportForFarmersRespite",
                "For harvester to function properly, turning on this support will cause the collision shape of Coffee Bush and Tea Bush block to disappear.",
                ConfigAnnotations.RequiresRestart.SERVER.asComment());

        public final ConfigBool disableTransferCooldownForFarmersDelightBasket = b(true,
                "disableTransferCooldownForFarmersDelightBasket",
                "Basket of Farmers Delight has a 8 ticks transfer cooldown. Disable the cooldown for better automation.",
                ConfigAnnotations.RequiresRestart.SERVER.asComment());
    
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
