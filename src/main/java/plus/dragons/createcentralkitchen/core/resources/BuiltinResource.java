package plus.dragons.createcentralkitchen.core.resources;

import com.simibubi.create.foundation.ModFilePackResources;
import com.simibubi.create.foundation.utility.Components;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.locating.IModFile;
import plus.dragons.createcentralkitchen.CentralKitchen;

import java.util.Locale;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public enum BuiltinResource {
    CREATE_STYLE_CHOCOLATE(PackType.CLIENT_RESOURCES) {
        @Override
        public boolean enabled() {
            return ModList.get().isLoaded("neapolitan");
        }
    },
    UNIFIED_CHOCOLATE(PackType.SERVER_DATA) {
        @Override
        public boolean enabled() {
            return ModList.get().isLoaded("neapolitan");
        }
    };
    
    private final String path;
    private final PackType type;
    private final boolean required;
    private final boolean hidden;
    
    BuiltinResource(PackType type, boolean required, boolean hidden) {
        this.path = name().toLowerCase(Locale.ROOT);
        this.type = type;
        this.required = required;
        this.hidden = hidden;
    }
    
    BuiltinResource(PackType type) {
        this(type, false, false);
    }
    
    public boolean enabled() {
        return true;
    }
    
    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        PackType type = event.getPackType();
        String typeId = switch (type) {
            case CLIENT_RESOURCES -> "resourcepack";
            case SERVER_DATA -> "datapack";
        };
        String dir = typeId + "s";
        IModFile modFile = ModList.get().getModFileById(CentralKitchen.ID).getFile();
        for (var pack : values()) {
            if (pack.type != type || !pack.enabled())
                continue;
            String path = pack.path;
            ResourceLocation packId = CentralKitchen.genRL(path);
            String titleKey = Util.makeDescriptionId(typeId, packId);
            String descriptionKey = Util.makeDescriptionId(typeId, packId) + ".desc";
            event.addRepositorySource((consumer, constructor) -> consumer.accept(
                new Pack(packId.toString(), pack.required,
                    () -> new ModFilePackResources(packId.toString(), modFile, dir + "/" + path),
                    Components.translatable(titleKey),
                    Components.translatable(descriptionKey),
                    PackCompatibility.COMPATIBLE,
                    Pack.Position.TOP, false,
                    PackSource.DEFAULT, pack.hidden)));
        }
    }
    
}
