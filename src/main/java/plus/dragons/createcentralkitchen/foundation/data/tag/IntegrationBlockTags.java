package plus.dragons.createcentralkitchen.foundation.data.tag;

import com.sammy.minersdelight.setup.MDBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.Locale;

public enum IntegrationBlockTags {
    VERTICAL_SLABS("quark"),
    VERTICAL_SLABS__CHOCOLATE("quark");
    
    public final TagKey<Block> tag;
    
    IntegrationBlockTags(String modid, String path) {
        this.tag = TagKey.create(ForgeRegistries.Keys.BLOCKS, new ResourceLocation(modid, path));
    }
    
    IntegrationBlockTags(String modid) {
        String path = name().toLowerCase(Locale.ROOT).replace("__", "/");
        this.tag = TagKey.create(ForgeRegistries.Keys.BLOCKS, new ResourceLocation(modid, path));
    }
    
    public static void datagen(RegistrateTagsProvider<Block> prov) {
        prov.addTag(VERTICAL_SLABS.tag).addTag(VERTICAL_SLABS__CHOCOLATE.tag);
        prov.addTag(ModTags.TRAY_HEAT_SOURCES)
            .add(AllBlocks.LIT_BLAZE_BURNER.get().builtInRegistryHolder().key())
            .add(AllBlocks.BLAZE_BURNER.get().builtInRegistryHolder().key());
        prov.addTag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .addOptional(ModBlocks.COOKING_POT.getId());
    }
    
}
