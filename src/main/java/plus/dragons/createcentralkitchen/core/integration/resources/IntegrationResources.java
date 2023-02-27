package plus.dragons.createcentralkitchen.core.integration.resources;

import com.simibubi.create.foundation.ModFilePackResources;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.locating.IModFile;
import plus.dragons.createcentralkitchen.CentralKitchen;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IntegrationResources {
    private static final Map<String, ICondition[]> CLIENT_RESOURCES = new HashMap<>();
    private static final Map<String, ICondition[]> SERVER_DATA = new HashMap<>();
    static {
        SERVER_DATA.put("chocolate_integration", new ICondition[]{new ModLoadedCondition("neapolitan")});
    }
    
    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        String packType = switch (event.getPackType()) {
            case CLIENT_RESOURCES -> "resource_packs";
            case SERVER_DATA -> "data_packs";
        };
        Map<String, ICondition[]> packs = switch (event.getPackType()) {
            case CLIENT_RESOURCES -> CLIENT_RESOURCES;
            case SERVER_DATA -> SERVER_DATA;
        };
        IModFile modFile = ModList.get().getModFileById(CentralKitchen.ID).getFile();
        NextPack:
        for (var pack : packs.entrySet()) {
            for (var condition : pack.getValue()) {
                if (!condition.test(ICondition.IContext.EMPTY)) {
                    continue NextPack;
                }
            }
            String packId = pack.getKey();
            String packName = RegistrateLangProvider.toEnglishName(packId);
            event.addRepositorySource((consumer, constructor) -> consumer.accept(
                Pack.create(CentralKitchen.genRL(packId).toString(), false,
                () -> new ModFilePackResources(packName, modFile, packType + "/" + packId),
                constructor, Pack.Position.TOP, PackSource.DEFAULT)));
        }
    }
    
}
