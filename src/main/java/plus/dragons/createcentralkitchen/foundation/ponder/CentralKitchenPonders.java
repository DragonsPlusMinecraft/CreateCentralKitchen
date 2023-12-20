package plus.dragons.createcentralkitchen.foundation.ponder;

import com.simibubi.create.foundation.ponder.PonderStoryBoardEntry;
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
    
    public static PonderEntry create(String schematic, PonderStoryBoardEntry.PonderStoryBoard storyBoard) {
        var entry = new PonderEntry(CentralKitchen.genRL(schematic), storyBoard);
        ENTRIES.add(entry);
        return entry;
    }
    
    public static void register() {
        if (Mods.isLoaded(Mods.FD)) {
            FDPonderEntries.register();
            FDPonderTags.register();
        }
/*        if (Mods.isLoaded(Mods.FR)) {
            FRPonderEntries.register();
            FRPonderTags.register();
        }*/
        if (Mods.isLoaded(Mods.MD)) {
            MDPonderEntries.register();
            MDPonderTags.register();
        }
        ENTRIES.forEach(PonderEntry::register);
    }
    
}
