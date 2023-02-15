package plus.dragons.createcentralkitchen.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.data.recipe.FarmersDelightRecipes;
import plus.dragons.createcentralkitchen.data.recipe.FarmersRespiteRecipes;
import plus.dragons.createcentralkitchen.data.tag.CentralKitchenTags;
import plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.FdPonderIndex;
import plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.FdPonderTag;
import plus.dragons.createdragonlib.lang.LangFactory;

public enum CentralKitchenData {
    INSTANCE;
    
    public static void register(IEventBus modBus) {
        if (!DatagenModLoader.isRunningDataGen())
            return;
        LangFactory langFactory = LangFactory.create(CentralKitchen.NAME, CentralKitchen.ID)
                .ponders(() -> {
                    FdPonderIndex.register();
                    FdPonderTag.register();
                })
                .ui();
        modBus.addListener(EventPriority.LOWEST, langFactory::datagen);
        modBus.register(INSTANCE);
        CentralKitchenTags.register();
    }
    
    @SubscribeEvent(priority = EventPriority.LOW)
    public void afterRegistrate(final GatherDataEvent event) {
        DataGenerator datagen = event.getGenerator();
        datagen.addProvider(true,new FarmersDelightRecipes(datagen));
        datagen.addProvider(true,new FarmersRespiteRecipes(datagen));
    }
    
}
