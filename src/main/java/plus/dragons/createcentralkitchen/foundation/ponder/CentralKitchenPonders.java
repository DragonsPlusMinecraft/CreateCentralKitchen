package plus.dragons.createcentralkitchen.foundation.ponder;

import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.createmod.ponder.api.scene.PonderStoryBoard;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.foundation.ponder.entry.FDPonderEntries;
import plus.dragons.createcentralkitchen.foundation.ponder.entry.MDPonderEntries;
import plus.dragons.createcentralkitchen.foundation.ponder.tag.FDPonderTags;
import plus.dragons.createcentralkitchen.foundation.ponder.tag.MDPonderTags;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import java.util.ArrayList;
import java.util.List;

public class CentralKitchenPonders {
    private static final List<PonderEntry> ENTRIES = new ArrayList<>();
    
    public static PonderEntry create(String schematic, PonderStoryBoard storyBoard) {
        var entry = new PonderEntry(CentralKitchen.genRL(schematic), storyBoard);
        ENTRIES.add(entry);
        return entry;
    }
    
    public static void registerTag(PonderTagRegistrationHelper<ResourceLocation> helper) {
        if (Mods.isLoaded(Mods.FD)) {
            FDPonderTags.register(helper);
        }
/*        if (Mods.isLoaded(Mods.FR)) {
            FRPonderEntries.register();
            FRPonderTags.register();
        }*/
        if (Mods.isLoaded(Mods.MD)) {
            MDPonderTags.register(helper);
        }

	}
    public static void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        if (Mods.isLoaded(Mods.FD)) {
            FDPonderEntries.register();
        }
/*        if (Mods.isLoaded(Mods.FR)) {
            FRPonderEntries.register();
            FRPonderTags.register();
        }*/
        if (Mods.isLoaded(Mods.MD)) {
            MDPonderEntries.register();
        }
		for (PonderEntry ENTRY : ENTRIES) {
			ENTRY.register(helper);
		}

	}

}
