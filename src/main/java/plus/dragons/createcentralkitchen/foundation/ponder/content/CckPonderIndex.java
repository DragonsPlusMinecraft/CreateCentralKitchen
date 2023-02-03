package plus.dragons.createcentralkitchen.foundation.ponder.content;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.CckItems;
import plus.dragons.createcentralkitchen.foundation.ponder.CckPonderTag;
import plus.dragons.createcentralkitchen.foundation.utility.SafeRegistrate;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModItems;

public class CckPonderIndex {
    
    private static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(CentralKitchen.ID);
    
    /**
     * For wrapping {@link net.minecraftforge.registries.RegistryObject} into {@link com.tterrag.registrate.util.entry.ItemProviderEntry}.
     */
    private static final SafeRegistrate REGISTRATE = new SafeRegistrate("");

    private static <T extends IForgeRegistryEntry<? super T> & ItemLike>
    ItemProviderEntry<T> component(RegistryObject<T> object) {
        return new ItemProviderEntry<>(REGISTRATE, object);
    }
    
    public static void register() {
        HELPER.forComponents(CckItems.COOKING_GUIDE)
                .addStoryBoard("blaze_stove/intro", FarmersDelightScenes::blazeStoveIntro, CckPonderTag.FARMERS_DELIGHT)
                .addStoryBoard("blaze_stove/processing", FarmersDelightScenes::blazeStoveProcessing, CckPonderTag.FARMERS_DELIGHT);
        HELPER.forComponents(component(ModItems.BASKET))
                .addStoryBoard("basket", FarmersDelightScenes::basket, CckPonderTag.FARMERS_DELIGHT);
    }

    public static void registerTags() {
        PonderRegistry.TAGS.forTag(CckPonderTag.FARMERS_DELIGHT)
                .add(CckItems.COOKING_GUIDE)
                .add(AllBlocks.MECHANICAL_ARM.get())
                .add(ModBlocks.COOKING_POT.get())
                .add(ModBlocks.SKILLET.get())
                .add(ModBlocks.STOVE.get())
                .add(ModBlocks.BASKET.get())
                .add(ModBlocks.CUTTING_BOARD.get());
    }

}
