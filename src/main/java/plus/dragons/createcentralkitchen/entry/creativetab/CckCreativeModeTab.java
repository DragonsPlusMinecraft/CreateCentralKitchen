package plus.dragons.createcentralkitchen.entry.creativetab;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

import com.simibubi.create.AllCreativeModeTabs;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.item.FDItemEntries;
import plus.dragons.createcentralkitchen.entry.item.MDItemEntries;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

public class CckCreativeModeTab {
    public static final ItemEntry<Item> ICON = REGISTRATE.item("creative_tab_icon", Item::new)
            .lang("CreativeTabIcon")
            .properties(prop -> prop.stacksTo(1))
            .register();
    private static final DeferredRegister<CreativeModeTab> REGISTER;
    public static final RegistryObject<CreativeModeTab> CREATIVE_TAB;

    public CckCreativeModeTab() {}

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }

    static {
        REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CentralKitchen.ID);
        CREATIVE_TAB = REGISTER.register("base", () -> {
            return CreativeModeTab.builder().title(Component.literal("CCK"))
                    .withTabsBefore(AllCreativeModeTabs.BASE_CREATIVE_TAB.getKey(), AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getKey())
                    .icon(ICON::asStack)
                    .displayItems(new DisplayItemsGenerator())
                    .build();
        });
    }

    private static class DisplayItemsGenerator implements CreativeModeTab.DisplayItemsGenerator {
        @Override
        public void accept(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
            List<Item> items = new LinkedList<>();
            items.addAll(this.collectItems());
            items.addAll(this.collectBlocks());
            items.addAll(this.collectFluid());
            filterAndOutput(output, items);
        }

        private List<Item> collectBlocks() {
            List<Item> items = new ReferenceArrayList<>();
            for (RegistryEntry<Block> entry : REGISTRATE.getAll(Registries.BLOCK)) {
                Item item = entry.get().asItem();
                if (item != Items.AIR) {
                    items.add(item);
                }
            }

            items = new ReferenceArrayList<>(new ReferenceLinkedOpenHashSet<>(items));
            return items;
        }

        private List<Item> collectItems() {
            List<Item> items = new ReferenceArrayList<>();
            for (RegistryEntry<Item> entry : REGISTRATE.getAll(Registries.ITEM)) {
                Item item = entry.get();
                if (!(item instanceof BlockItem) && !(item instanceof BucketItem)) {
                    items.add(item);
                }
            }

            return items;
        }

        private List<Item> collectFluid() {
            List<Item> items = new ReferenceArrayList<>();
            for (RegistryEntry<Fluid> entry : REGISTRATE.getAll(Registries.FLUID)) {
                Fluid fluid = entry.get();
                if (fluid.getBucket() != Items.AIR && !items.contains(fluid.getBucket())) {
                    items.add(fluid.getBucket());
                }
            }

            return items;
        }

        private static void filterAndOutput(CreativeModeTab.Output output, List<Item> items) {
            for (Item item : items) {
                if (item.toString().contains("incomplete")) continue;
                if (item.toString().contains("guide")) continue;
                if (item.toString().contains("blaze_burner")) continue;
                if (item.toString().contains("creative_tab_icon")) continue;
                output.accept(item);
            }
            if (Mods.isLoaded(Mods.FD))
                output.accept(FDItemEntries.COOKING_GUIDE.asStack());
            if (Mods.isLoaded(Mods.MD))
                output.accept(MDItemEntries.MINERS_COOKING_GUIDE.asStack());
        }
    }
}
