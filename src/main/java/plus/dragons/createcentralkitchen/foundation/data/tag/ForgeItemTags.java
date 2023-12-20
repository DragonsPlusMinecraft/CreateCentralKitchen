package plus.dragons.createcentralkitchen.foundation.data.tag;

import com.simibubi.create.AllItems;
import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Locale;

public enum ForgeItemTags {
    TOOLS,
    TOOLS__AXES,
    TOOLS__KNIVES,
    TOOLS__PICKAXES,
    TOOLS__SHOVELS,
    FLOUR__WHEAT,
    BARS__CHOCOLATE,
    CROPS__CORN;
    
    public final TagKey<Item> tag;
    
    ForgeItemTags() {
        String path = name().toLowerCase(Locale.ROOT).replace("__", "/");
        this.tag = create(path);
    }
    
    public static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("forge", name));
    }
    
    @SuppressWarnings("unchecked")
    public static void datagen(RegistrateItemTagsProvider provIn) {
        var prov = new CckTagsProvider<>(provIn, Item::builtInRegistryHolder);
        prov.tag(TOOLS.tag).addTags(TOOLS__AXES.tag, TOOLS__PICKAXES.tag, TOOLS__SHOVELS.tag);
        prov.tag(TOOLS__AXES.tag).add(Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.DIAMOND_AXE, Items.GOLDEN_AXE, Items.NETHERITE_AXE);
        prov.tag(TOOLS__PICKAXES.tag).add(Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE, Items.DIAMOND_PICKAXE, Items.GOLDEN_PICKAXE, Items.NETHERITE_PICKAXE);
        prov.tag(TOOLS__SHOVELS.tag).add(Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.IRON_SHOVEL, Items.DIAMOND_SHOVEL, Items.GOLDEN_SHOVEL, Items.NETHERITE_SHOVEL);
        prov.tag(BARS__CHOCOLATE.tag).add(AllItems.BAR_OF_CHOCOLATE.get());
    }
    
}
