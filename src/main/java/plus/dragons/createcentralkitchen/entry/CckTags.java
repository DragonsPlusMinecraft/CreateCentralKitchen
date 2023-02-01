package plus.dragons.createcentralkitchen.entry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import plus.dragons.createcentralkitchen.CentralKitchen;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.common.tag.ModTags;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

public class CckTags {
    public static final TagKey<Item> UPRIGHT_ON_BELT = item(Create.asResource("upright_on_belt"));
    public static final TagKey<Item> UPRIGHT_ON_DEPLOYER = item(CentralKitchen.genRL("upright_on_deployer"));
    public static final TagKey<Block> FAN_TRANSPARENT = block(Create.asResource("fan_transparent"));
    public static final TagKey<Block> FAN_HEATERS = block(Create.asResource("fan_heaters"));
    
    public static TagKey<Item> item(ResourceLocation id) {
        return TagKey.create(Registry.ITEM_REGISTRY, id);
    }

    public static TagKey<Block> block(ResourceLocation id) {
        return TagKey.create(Registry.BLOCK_REGISTRY, id);
    }
    
    public static void genItemTags(RegistrateItemTagsProvider prov) {
        prov.tag(UPRIGHT_ON_BELT).add(
            ModItems.APPLE_CIDER.get(),
            ModItems.MELON_JUICE.get(),
            ModItems.TOMATO_SAUCE.get());
        prov.tag(UPRIGHT_ON_DEPLOYER).addTag(ForgeTags.TOOLS);
    }

    public static void genBlockTags(RegistrateTagsProvider<Block> prov) {
        prov.tag(ModTags.HEAT_SOURCES).add(
                AllBlocks.LIT_BLAZE_BURNER.get());
    }
    
    public static void register() {
        REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, CckTags::genItemTags);
        REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, CckTags::genBlockTags);
    }
    
}
