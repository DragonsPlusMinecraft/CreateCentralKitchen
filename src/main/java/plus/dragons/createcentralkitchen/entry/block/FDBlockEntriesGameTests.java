package plus.dragons.createcentralkitchen.entry.block;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.registries.ForgeRegistries;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

@ModLoadSubscriber(modid = Mods.FD)
@PrefixGameTestTemplate(false)
public class FDBlockEntriesGameTests {
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        GameTestRegistry.register(FDBlockEntriesGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void foodTabUsesRegisteredPieItems(GameTestHelper helper) {
        var level = helper.getLevel();
        CreativeModeTab foodTab = level.registryAccess()
                .registryOrThrow(Registries.CREATIVE_MODE_TAB)
                .getOrThrow(CreativeModeTabs.FOOD_AND_DRINKS);
        var parameters = new CreativeModeTab.ItemDisplayParameters(level.enabledFeatures(), false,
                level.registryAccess());
        var entries = new MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility>();
        var event = new BuildCreativeModeTabContentsEvent(foodTab, CreativeModeTabs.FOOD_AND_DRINKS,
                parameters, entries);

        FDBlockEntries.buildContents(event);
        Map<ResourceLocation, ItemStack> entriesById = new HashMap<>();
        entries.forEach(entry -> {
            ResourceLocation id = ForgeRegistries.ITEMS.getKey(entry.getKey().getItem());
            if (id != null)
                entriesById.put(id, entry.getKey());
        });

        assertRegisteredEntry(helper, entriesById, ResourceLocation.withDefaultNamespace("pumpkin_pie"));
        if (Mods.isLoaded(Mods.ENVIRONMENTAL))
            assertRegisteredEntry(helper, entriesById, Mods.environmental("apple_pie"));
        if (Mods.isLoaded(Mods.UA))
            assertRegisteredEntry(helper, entriesById, Mods.ua("mulberry_pie"));
        helper.succeed();
    }

    private static void assertRegisteredEntry(GameTestHelper helper,
            Map<ResourceLocation, ItemStack> entries, ResourceLocation id) {
        ItemStack entry = entries.get(id);
        helper.assertTrue(entry != null, "The food tab is missing " + id);
        helper.assertTrue(entry.getItem() == ForgeRegistries.ITEMS.getValue(id),
                "The food tab used an unregistered replacement for " + id);
    }
}
