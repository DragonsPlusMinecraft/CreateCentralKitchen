package plus.dragons.createcentralkitchen.foundation.data;

import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.foundation.data.loot.BlockLootTables;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.*;
import plus.dragons.createcentralkitchen.foundation.data.tag.ForgeBlockTags;
import plus.dragons.createcentralkitchen.foundation.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.foundation.data.tag.IntegrationBlockTags;
import plus.dragons.createcentralkitchen.foundation.data.tag.IntegrationItemTags;
import plus.dragons.createcentralkitchen.foundation.ponder.CentralKitchenPonders;
import plus.dragons.createdragonlib.lang.LangFactory;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

public class CentralKitchenData {
    
    //TODO: Add advancements so it fixes the LangMerger, for now, manually clean up the en_us.json before every run
    public static void register(IEventBus modBus) {
        LangFactory langFactory = LangFactory.create(CentralKitchen.NAME, CentralKitchen.ID)
            .ponders(CentralKitchenPonders::register)
            .ui();
        modBus.addListener(EventPriority.LOWEST, langFactory::datagen);
        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, ForgeBlockTags::datagen);
        REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, ForgeItemTags::datagen);
        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, IntegrationBlockTags::datagen);
        REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, IntegrationItemTags::datagen);
        modBus.addListener(EventPriority.HIGH, CentralKitchenData::beforeRegistrate);
        modBus.addListener(EventPriority.LOW, CentralKitchenData::afterRegistrate);
    }
    
    public static void beforeRegistrate(final GatherDataEvent event) {
        CentralKitchenPonders.register();
    }
    
    public static void afterRegistrate(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        boolean server = event.includeServer();
        boolean client = event.includeClient();
        generator.addProvider(server, new StandardRecipes(generator));
        generator.addProvider(server, new FDRecipes(generator));
        generator.addProvider(server, new EDRecipes(generator));
        generator.addProvider(server, new CornDelightRecipes(generator));
        DatapackRecipes.buildAll(REGISTRATE, generator);
        generator.addProvider(server, new BlockLootTables(generator));
    }
    
}
