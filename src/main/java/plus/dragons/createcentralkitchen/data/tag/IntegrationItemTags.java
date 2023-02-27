package plus.dragons.createcentralkitchen.data.tag;

import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;

public enum IntegrationItemTags {
    UPRIGHT_ON_DEPLOYER("create"),
    VERTICAL_SLABS("quark"),
    VERTICAL_SLABS__CHOCOLATE("quark");
    
    public final TagKey<Item> tag;
    
    IntegrationItemTags(String modid, String path) {
        this.tag = TagKey.create(ForgeRegistries.Keys.ITEMS, new ResourceLocation(modid, path));
    }
    
    IntegrationItemTags(String modid) {
        String path = name().toLowerCase(Locale.ROOT).replace("__", "/");
        this.tag = TagKey.create(ForgeRegistries.Keys.ITEMS, new ResourceLocation(modid, path));
    }
    
    public static void datagen(RegistrateItemTagsProvider prov) {
        prov.tag(UPRIGHT_ON_DEPLOYER.tag).addTag(ForgeItemTags.TOOLS.tag);
        prov.copy(IntegrationBlockTags.VERTICAL_SLABS.tag, VERTICAL_SLABS.tag);
        prov.copy(IntegrationBlockTags.VERTICAL_SLABS__CHOCOLATE.tag, VERTICAL_SLABS__CHOCOLATE.tag);
    }
    
}
