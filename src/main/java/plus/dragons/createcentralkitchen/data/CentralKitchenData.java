package plus.dragons.createcentralkitchen.data;

import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import plus.dragons.createcentralkitchen.data.lang.LangMerger;
import plus.dragons.createcentralkitchen.data.recipe.FarmersDelightRecipes;
import plus.dragons.createcentralkitchen.data.recipe.FarmersRespiteRecipes;
import plus.dragons.createcentralkitchen.data.recipe.NeapolitanRecipes;
import plus.dragons.createcentralkitchen.data.recipe.VanillaRecipes;
import plus.dragons.createcentralkitchen.data.tag.ForgeBlockTags;
import plus.dragons.createcentralkitchen.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.data.tag.IntegrationBlockTags;
import plus.dragons.createcentralkitchen.data.tag.IntegrationItemTags;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

public class CentralKitchenData {
    
    public static void register(IEventBus modBus) {
        if (!DatagenModLoader.isRunningDataGen())
            return;
        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, ForgeBlockTags::datagen);
        REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, ForgeItemTags::datagen);
        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, IntegrationBlockTags::datagen);
        REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, IntegrationItemTags::datagen);
        modBus.addListener(EventPriority.LOW, CentralKitchenData::afterRegistrate);
    }
    
    public static void afterRegistrate(final GatherDataEvent event) {
        DataGenerator datagen = event.getGenerator();
        datagen.addProvider(new VanillaRecipes(datagen));
        datagen.addProvider(new FarmersDelightRecipes(datagen));
        datagen.addProvider(new FarmersRespiteRecipes(datagen));
        datagen.addProvider(new NeapolitanRecipes(datagen));
        datagen.addProvider(new LangMerger(datagen));
    }
    
}
