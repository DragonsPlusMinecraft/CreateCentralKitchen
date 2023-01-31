package plus.dragons.createcentralkitchen.entry;

import com.simibubi.create.Create;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import plus.dragons.createcentralkitchen.FarmersAutomation;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import static plus.dragons.createcentralkitchen.FarmersAutomation.REGISTRATE;

public class CckTags {
    public static final TagKey<Item> UPRIGHT_ON_BELT = tag(Create.asResource("upright_on_belt"));
    public static final TagKey<Item> UPRIGHT_ON_DEPLOYER = tag(FarmersAutomation.genRL("upright_on_deployer"));
    
    public static TagKey<Item> tag(ResourceLocation id) {
        return TagKey.create(Registry.ITEM_REGISTRY, id);
    }
    
    public static void genTags(RegistrateItemTagsProvider prov) {
        prov.tag(UPRIGHT_ON_BELT).add(
            ModItems.APPLE_CIDER.get(),
            ModItems.MELON_JUICE.get(),
            ModItems.TOMATO_SAUCE.get());
        prov.tag(UPRIGHT_ON_DEPLOYER).addTag(ForgeTags.TOOLS);
    }
    
    public static void register() {
        REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, CckTags::genTags);
    }
    
}
