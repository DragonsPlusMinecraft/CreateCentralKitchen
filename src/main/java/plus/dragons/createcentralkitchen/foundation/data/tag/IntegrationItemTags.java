package plus.dragons.createcentralkitchen.foundation.data.tag;

import com.simibubi.create.AllTags;
import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Locale;

public enum IntegrationItemTags {
    UPRIGHT_ON_DEPLOYER("create"),
    IGNORED_IN_AUTOMATIC_SHAPELESS("create"),
    VERTICAL_SLABS("quark"),
    VERTICAL_SLABS__CHOCOLATE("quark");
    
    public final TagKey<Item> tag;
    
    IntegrationItemTags(String modid, String path) {
        this.tag = TagKey.create(Registries.ITEM, new ResourceLocation(modid, path));
    }
    
    IntegrationItemTags(String modid) {
        String path = name().toLowerCase(Locale.ROOT).replace("__", "/");
        this.tag = TagKey.create(Registries.ITEM, new ResourceLocation(modid, path));
    }
    
    public static void datagen(RegistrateItemTagsProvider provIn) {
        var prov = new CckTagsProvider<>(provIn, Item::builtInRegistryHolder);
        prov.tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag).add(Items.BOWL, Items.BUCKET, Items.POWDER_SNOW_BUCKET);
        prov.tag(UPRIGHT_ON_DEPLOYER.tag).addTag(ForgeItemTags.TOOLS.tag);
        prov.tag(IGNORED_IN_AUTOMATIC_SHAPELESS.tag).add(Items.BUCKET, Items.BOWL, Items.GLASS_BOTTLE, Items.POTION, Items.MILK_BUCKET);
        provIn.copy(IntegrationBlockTags.VERTICAL_SLABS.tag, VERTICAL_SLABS.tag);
        provIn.copy(IntegrationBlockTags.VERTICAL_SLABS__CHOCOLATE.tag, VERTICAL_SLABS__CHOCOLATE.tag);
    }
    
}
