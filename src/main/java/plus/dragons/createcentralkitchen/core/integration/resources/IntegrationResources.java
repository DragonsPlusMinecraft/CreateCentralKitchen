package plus.dragons.createcentralkitchen.core.integration.resources;

import com.google.common.collect.ImmutableMap;
import com.simibubi.create.foundation.ModFilePackResources;
import com.simibubi.create.foundation.utility.Components;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.locating.IModFile;
import plus.dragons.createcentralkitchen.CentralKitchen;

import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class IntegrationResources {
    private static final Map<String, ICondition[]> CLIENT_RESOURCES = ImmutableMap.<String, ICondition[]>builder()
        .put("create_style_chocolate", new ICondition[]{new ModLoadedCondition("neapolitan")})
        .build();
    private static final Map<String, ICondition[]> SERVER_DATA = ImmutableMap.<String, ICondition[]>builder()
        .put("unified_chocolate", new ICondition[]{new ModLoadedCondition("neapolitan")})
        .build();
    
    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        String type = switch (event.getPackType()) {
            case CLIENT_RESOURCES -> "resourcepack";
            case SERVER_DATA -> "datapack";
        };
        String dir = type + "s/";
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
            String path = pack.getKey();
            ResourceLocation packId = CentralKitchen.genRL(path);
            String titleKey = Util.makeDescriptionId(type, packId);
            String descriptionKey = Util.makeDescriptionId(type, packId) + ".desc";
            event.addRepositorySource((consumer, constructor) -> consumer.accept(
                new Pack(packId.toString(), false,
                    () -> new ModFilePackResources(packId.toString(), modFile, dir + path),
                    Components.translatable(titleKey),
                    Components.translatable(descriptionKey),
                    PackCompatibility.COMPATIBLE,
                    Pack.Position.TOP, false,
                    PackSource.DEFAULT, false)));
        }
    }
    
}
