package plus.dragons.createcentralkitchen.content.contraptions.components.actor;

import net.brdle.collectorsreap.common.block.CRBlocks;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import static plus.dragons.createcentralkitchen.content.contraptions.components.actor.HarvesterMovementBehaviourExtension.REGISTRY;

@ModLoadSubscriber(modid = Mods.CR)
public class CRHarvesterMovementBehaviorExtensions {
    
    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            REGISTRY.put(CRBlocks.PORTOBELLO_COLONY.get(),
                FDHarvesterMovementBehaviorExtensions::harvestMushroomColony);
        });
    }
    
}
