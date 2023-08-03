package plus.dragons.createcentralkitchen.foundation.data;

import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.loading.DatagenModLoader;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import plus.dragons.createcentralkitchen.foundation.data.lang.LangMerger;
import plus.dragons.createcentralkitchen.foundation.data.loot.BlockLootTables;
import plus.dragons.createcentralkitchen.foundation.data.recipe.provider.*;
import plus.dragons.createcentralkitchen.foundation.data.tag.ForgeBlockTags;
import plus.dragons.createcentralkitchen.foundation.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.foundation.data.tag.IntegrationBlockTags;
import plus.dragons.createcentralkitchen.foundation.data.tag.IntegrationItemTags;
import plus.dragons.createcentralkitchen.foundation.ponder.CentralKitchenPonders;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

public class CentralKitchenData {
    
    public static void register(IEventBus modBus) {
        if (!DatagenModLoader.isRunningDataGen())
            return;
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
        generator.addProvider(new StandardRecipes(generator));
        generator.addProvider(new AtmosphericRecipes(generator));
        generator.addProvider(new AutumnityRecipes(generator));
        generator.addProvider(new FDRecipes(generator));
        generator.addProvider(new FRRecipes(generator));
        generator.addProvider(new NeapolitanRecipes(generator));
        generator.addProvider(new PeculiarsRecipes(generator));
        generator.addProvider(new RespitefulRecipes(generator));
        generator.addProvider(new SeasonalsRecipes(generator));
        generator.addProvider(new EDRecipes(generator));
        generator.addProvider(new CornDelightRecipes(generator));
        DatapackRecipes.buildAll(REGISTRATE, generator);
        generator.addProvider(new BlockLootTables(generator));
        generator.addProvider(new LangMerger(generator));
    }
    
}
