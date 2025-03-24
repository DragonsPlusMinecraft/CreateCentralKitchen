package plus.dragons.createcentralkitchen.foundation.ponder.tag;

import com.sammy.minersdelight.setup.MDBlocks;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;
import plus.dragons.createcentralkitchen.entry.item.MDItemEntries;

public class MDPonderTags {

    public static void register(PonderTagRegistrationHelper<ResourceLocation> helper) {
        helper.addToTag(FDPonderTags.COOKING)
                .add(MDBlocks.COPPER_POT.get().getLootTable());
        PonderTagRegistrationHelper<RegistryEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
        HELPER.addToTag(FDPonderTags.COOKING)
            .add(MDItemEntries.MINERS_COOKING_GUIDE);
    }

}
