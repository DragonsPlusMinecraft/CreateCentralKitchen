package plus.dragons.createcentralkitchen.foundation.data.tag;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import plus.dragons.createcentralkitchen.CentralKitchen;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.Locale;
//public enum IntegrationBlockTags {
public class IntegrationBlockTags {
    /*VERTICAL_SLABS("quark"),
    VERTICAL_SLABS__CHOCOLATE("quark");
    
    public final TagKey<Block> tag;
    
    IntegrationBlockTags(String modid, String path) {
        this.tag = BlockTags.create(new ResourceLocation(modid, path));
    }
    
    IntegrationBlockTags(String modid) {
        String path = name().toLowerCase(Locale.ROOT).replace("__", "/");
        this.tag = BlockTags.create(new ResourceLocation(modid, path));
    }*/
    
    public static void datagen(RegistrateTagsProvider<Block> provIn) {
        var prov = new CckTagsProvider<>(provIn, Block::builtInRegistryHolder);
        prov.tag(ModTags.TRAY_HEAT_SOURCES)
            .add(AllBlocks.LIT_BLAZE_BURNER.get())
            .add(AllBlocks.BLAZE_BURNER.get());
        prov.tag(AllTags.AllBlockTags.WRENCH_PICKUP.tag)
            .addOptional(ModBlocks.COOKING_POT.getId());
    }
    
}
