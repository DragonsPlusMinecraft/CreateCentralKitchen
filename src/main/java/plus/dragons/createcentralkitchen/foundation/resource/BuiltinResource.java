package plus.dragons.createcentralkitchen.foundation.resource;

import com.simibubi.create.foundation.pack.ModFilePackResources;
import java.util.Locale;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forgespi.locating.IModFile;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@Mod.EventBusSubscriber(modid = CentralKitchen.ID, bus = Bus.MOD)
public enum BuiltinResource {
    //Resource Packs
    CREATE_STYLED(PackType.CLIENT_RESOURCES, CentralKitchen.ID),
    //Data Packs
    FARMERSDELIGHT(PackType.SERVER_DATA, true, false),
    FARMERSRESPITE(PackType.SERVER_DATA, true, false),
    RESPITEFUL(PackType.SERVER_DATA, true, false),
    MINERS_DELIGHT(PackType.SERVER_DATA, true, false),
    OVERWEIGHT_FARMING(PackType.SERVER_DATA, true, false),
    ENDS_DELIGHT(PackType.SERVER_DATA, true, false),
    UPGRADE_AQUATIC(PackType.SERVER_DATA, true, false),
    NEAPOLITAN(PackType.SERVER_DATA, true, false),
    BUZZIER_BEES(PackType.SERVER_DATA, true, false),
    ATMOSPHERIC(PackType.SERVER_DATA, true, false),
    CORN_DELIGHT(PackType.SERVER_DATA, true, false),
    AUTUMNITY(PackType.SERVER_DATA, true, false),
    COLLECTORSREAP(PackType.SERVER_DATA, true, false),
    PECULIARS(PackType.SERVER_DATA, true, false),
    SEASONALS(PackType.SERVER_DATA, true, false),
    ABNORMALS_DELIGHT(PackType.SERVER_DATA, true, false);

    private final String path;
    private final String mod;
    private final PackType type;
    private final boolean required;
    private final boolean hidden;

    BuiltinResource(PackType type, String mod, boolean required, boolean hidden) {
        this.path = name().toLowerCase(Locale.ROOT);
        this.mod = mod;
        this.type = type;
        this.required = required;
        this.hidden = hidden;
    }

    BuiltinResource(PackType type, String mod) {
        this(type, mod, false, false);
    }

    BuiltinResource(PackType type, boolean required, boolean hidden) {
        this.path = name().toLowerCase(Locale.ROOT);
        this.mod = this.path;
        this.type = type;
        this.required = required;
        this.hidden = hidden;
    }

    BuiltinResource(PackType type) {
        this(type, false, false);
    }

    public boolean enabled() {
        return Mods.isLoaded(mod);
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
            event.addRepositorySource(consumer -> consumer.accept(
                    Pack.readMetaAndCreate(packId.toString(), Component.translatable(titleKey), pack.required,
                            id -> new ModFilePackResources(packId.toString(), modFile, dir + "/" + path), type,
                            Pack.Position.TOP, PackSource.BUILT_IN)));
        }
    }
}
