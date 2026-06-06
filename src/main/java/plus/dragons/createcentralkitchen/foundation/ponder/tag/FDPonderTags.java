package plus.dragons.createcentralkitchen.foundation.ponder.tag;

import com.simibubi.create.AllBlocks;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.item.FDItemEntries;
import vectorwing.farmersdelight.common.registry.ModBlocks;

public class FDPonderTags {
    public static final ResourceLocation COOKING = CentralKitchen.genRL("cooking_automation");

    public static void register(PonderTagRegistrationHelper<ResourceLocation> helper) {
        helper.registerTag(COOKING)
                .item(FDItemEntries.COOKING_GUIDE.get(), true, false)
                .title("Cooking Automation")
                .description("Components which automate the cooking process")
                .addToIndex()
                .register();
//        COOKING.item(FDItemEntries.COOKING_GUIDE.get(), true, false)
//            .defaultLang("Cooking Automation", "Components which automate the cooking process")
//            .addToIndex();
        PonderTagRegistrationHelper<RegistryEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.addToTag(COOKING)
                .add(AllBlocks.BLAZE_BURNER)
                .add(FDItemEntries.COOKING_GUIDE);
        PonderTagRegistrationHelper<RegistryObject<?>> HELPER2 = helper.withKeyFunction(RegistryObject::getId);
        HELPER2.addToTag(COOKING).add(ModBlocks.COOKING_POT);
    }
}
