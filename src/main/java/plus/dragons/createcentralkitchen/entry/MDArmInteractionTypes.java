package plus.dragons.createcentralkitchen.entry;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm.CopperPotPoint;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.MD)
public class MDArmInteractionTypes {
    public static final CopperPotPoint.Type COPPER_POT = new CopperPotPoint.Type(plus.dragons.createcentralkitchen.CentralKitchen.genRL("copper_pot"));

    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        if (Mods.isLoaded(Mods.FD))
            CentralKitchenArmInterationTypes.register(COPPER_POT);
    }
}
