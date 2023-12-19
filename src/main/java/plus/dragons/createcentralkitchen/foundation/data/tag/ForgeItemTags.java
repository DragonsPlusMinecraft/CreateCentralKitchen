package plus.dragons.createcentralkitchen.foundation.data.tag;

import com.simibubi.create.AllItems;
import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;

public enum ForgeItemTags {
    TOOLS,
    TOOLS__AXES,
    TOOLS__KNIVES,
    TOOLS__PICKAXES,
    TOOLS__SHOVELS,
    FLOUR__WHEAT,
    BARS__CHOCOLATE,
    STORAGE_BLOCKS__CHOCOLATE,
    SLABS__CHOCOLATE,
    STAIRS__CHOCOLATE,
    WALLS__CHOCOLATE,
    CROPS__CORN;
    
    public final TagKey<Item> tag;
    
    ForgeItemTags() {
        String path = name().toLowerCase(Locale.ROOT).replace("__", "/");
        this.tag = create(path);
    }
    
    public static TagKey<Item> create(String name) {
        return TagKey.create(ForgeRegistries.Keys.ITEMS, new ResourceLocation("forge", name));
    }
    
    @SuppressWarnings("unchecked")
    public static void datagen(RegistrateItemTagsProvider prov) {
        prov.addTag(TOOLS.tag).addTags(TOOLS__AXES.tag, TOOLS__PICKAXES.tag, TOOLS__SHOVELS.tag);
        prov.addTag(TOOLS__AXES.tag).add(Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.DIAMOND_AXE, Items.GOLDEN_AXE, Items.NETHERITE_AXE);
        prov.addTag(TOOLS__PICKAXES.tag).add(Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE, Items.DIAMOND_PICKAXE, Items.GOLDEN_PICKAXE, Items.NETHERITE_PICKAXE);
        prov.addTag(TOOLS__SHOVELS.tag).add(Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.IRON_SHOVEL, Items.DIAMOND_SHOVEL, Items.GOLDEN_SHOVEL, Items.NETHERITE_SHOVEL);
        prov.addTag(BARS__CHOCOLATE.tag).add(AllItems.BAR_OF_CHOCOLATE.get());
        prov.addTag(Tags.Items.STORAGE_BLOCKS).addTag(STORAGE_BLOCKS__CHOCOLATE.tag);
        prov.addTag(ItemTags.SLABS).addTag(SLABS__CHOCOLATE.tag);
        prov.addTag(ItemTags.STAIRS).addTag(STAIRS__CHOCOLATE.tag);
        prov.addTag(ItemTags.WALLS).addTag(WALLS__CHOCOLATE.tag);
        prov.copy(ForgeBlockTags.STORAGE_BLOCKS__CHOCOLATE.tag, STORAGE_BLOCKS__CHOCOLATE.tag);
        prov.copy(ForgeBlockTags.SLABS__CHOCOLATE.tag, SLABS__CHOCOLATE.tag);
        prov.copy(ForgeBlockTags.STAIRS__CHOCOLATE.tag, STAIRS__CHOCOLATE.tag);
        prov.copy(ForgeBlockTags.WALLS__CHOCOLATE.tag, WALLS__CHOCOLATE.tag);
    }
    
}
