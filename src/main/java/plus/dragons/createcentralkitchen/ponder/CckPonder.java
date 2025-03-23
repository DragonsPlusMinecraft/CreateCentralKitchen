package plus.dragons.createcentralkitchen.ponder;

import com.sammy.minersdelight.setup.MDBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.catnip.platform.CatnipServices;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.block.FDBlockEntries;
import plus.dragons.createcentralkitchen.entry.item.FDItemEntries;
import plus.dragons.createcentralkitchen.entry.item.MDItemEntries;
import plus.dragons.createcentralkitchen.foundation.ponder.scene.BasketScenes;
import plus.dragons.createcentralkitchen.foundation.ponder.scene.BlazeStoveScenes;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.registry.ModItems;

public class CckPonder {
    public static final ResourceLocation
            COOKING_AUTOMATION = loc("cooking_automation");

    private static ResourceLocation loc(String path) {
        return CentralKitchen.genRL(path);
    }

    public static void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
        PonderSceneRegistrationHelper<ItemLike> itemHelper = helper.withKeyFunction(CatnipServices.REGISTRIES::getKeyOrThrow);

        if (Mods.isLoaded(Mods.FD)) {
            itemHelper.forComponents(ModItems.BASKET.get())
                    .addStoryBoard("basket/intro", BasketScenes::intro, AllCreatePonderTags.LOGISTICS)
                    .addStoryBoard("basket/belt_interaction", BasketScenes::belt_interaction);

            HELPER.forComponents(AllBlocks.BLAZE_BURNER, FDItemEntries.COOKING_GUIDE)
                    .addStoryBoard("blaze_stove/intro", BlazeStoveScenes::intro, COOKING_AUTOMATION)
                    .addStoryBoard("blaze_stove/automation", BlazeStoveScenes::automation)
                    .addStoryBoard("blaze_stove/heat_source", BlazeStoveScenes::heat_source);
        }

        if (Mods.isLoaded(Mods.MD)) {
            HELPER.forComponents(MDItemEntries.MINERS_COOKING_GUIDE)
                    .addStoryBoard("blaze_stove/intro", BlazeStoveScenes::intro, COOKING_AUTOMATION)
                    .addStoryBoard("blaze_stove/automation", BlazeStoveScenes::automation)
                    .addStoryBoard("blaze_stove/heat_source", BlazeStoveScenes::heat_source);
            helper.forComponents(MDBlocks.COPPER_POT.getId())
                    .addStoryBoard("blaze_stove/automation", BlazeStoveScenes::automation, COOKING_AUTOMATION)
                    .addStoryBoard("blaze_stove/heat_source", BlazeStoveScenes::heat_source);
        }
    }

    public static void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        PonderTagRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        if (Mods.isLoaded(Mods.FD)) {
            HELPER.registerTag(COOKING_AUTOMATION)
                    .item(FDItemEntries.COOKING_GUIDE.get(), true, false)
                    .title("Cooking Automation")
                    .description("Components which automate the cooking process")
                    .addToIndex()
                    .register();
        }

    }
}
