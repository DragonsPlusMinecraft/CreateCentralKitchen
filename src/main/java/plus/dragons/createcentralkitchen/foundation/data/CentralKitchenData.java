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

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

public class CentralKitchenData {
    
    // Reminder: Add advancements, so it fixes the LangMerger, for now, manually clean up the en_us.json before every run
    public static void register(IEventBus modBus) {
        // We just don't use this for now. 1.20.1-6.0 temporary solution
        /*LangFactory langFactory = LangFactory.create(CentralKitchen.NAME, CentralKitchen.ID)
            //.ponders(CentralKitchenPonders::register)
            .ui();
        modBus.addListener(EventPriority.LOWEST, langFactory::datagen);*/
        modBus.addListener(EventPriority.HIGH, CentralKitchenData::beforeRegistrate);
        modBus.addListener(EventPriority.LOW, CentralKitchenData::afterRegistrate);
    }
    
    public static void beforeRegistrate(final GatherDataEvent event) {
        //CentralKitchenPonders.register();
    }
    
    public static void afterRegistrate(final GatherDataEvent event) {
        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, ForgeBlockTags::datagen);
        REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, ForgeItemTags::datagen);
        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, IntegrationBlockTags::datagen);
        REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, IntegrationItemTags::datagen);
        DataGenerator generator = event.getGenerator();
        boolean server = event.includeServer();
        boolean client = event.includeClient();
        generator.addProvider(server, new StandardRecipes(generator));
        generator.addProvider(server, new FDRecipes(generator));
        generator.addProvider(server, new EDRecipes(generator));
        generator.addProvider(server, new CornDelightRecipes(generator));
        generator.addProvider(server, new AutumnityRecipes(generator));
        generator.addProvider(server, new AtmosphericRecipes(generator));
        generator.addProvider(server, new CRRecipes(generator));
        generator.addProvider(server, new FRRecipes(generator));
        generator.addProvider(server, new NeapolitanRecipes(generator));
        generator.addProvider(server, new PeculiarsRecipes(generator));
        //generator.addProvider(server, new RespitefulRecipes(generator));
        generator.addProvider(server, new SeasonalsRecipes(generator));
        DatapackRecipes.buildAll(REGISTRATE, generator);
        generator.addProvider(server, new BlockLootTables(generator));
    }
    
}
