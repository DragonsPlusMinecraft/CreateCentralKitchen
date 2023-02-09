package plus.dragons.createcentralkitchen.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.data.lang.LangMerger;

@Mod.EventBusSubscriber(modid = CentralKitchen.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CentralKitchenData {
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void afterRegistrate(final GatherDataEvent event) {
        DataGenerator datagen = event.getGenerator();
        datagen.addProvider(new LangMerger(datagen));
    }
    
}
