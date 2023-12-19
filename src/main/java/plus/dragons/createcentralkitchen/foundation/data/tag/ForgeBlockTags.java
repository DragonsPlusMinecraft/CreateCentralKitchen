package plus.dragons.createcentralkitchen.foundation.data.tag;

import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;

public enum ForgeBlockTags {
    STORAGE_BLOCKS__CHOCOLATE,
    SLABS__CHOCOLATE,
    STAIRS__CHOCOLATE,
    WALLS__CHOCOLATE;
    
    public final TagKey<Block> tag;
    
    ForgeBlockTags() {
        String path = name().toLowerCase(Locale.ROOT).replace("__", "/");
        this.tag = create(path);
    }
    
    public static TagKey<Block> create(String name) {
        return TagKey.create(ForgeRegistries.Keys.BLOCKS, new ResourceLocation("forge", name));
    }
    
    public static void datagen(RegistrateTagsProvider<Block> prov) {
        prov.addTag(Tags.Blocks.STORAGE_BLOCKS).addTag(STORAGE_BLOCKS__CHOCOLATE.tag);
        prov.addTag(BlockTags.SLABS).addTag(SLABS__CHOCOLATE.tag);
        prov.addTag(BlockTags.STAIRS).addTag(STAIRS__CHOCOLATE.tag);
        prov.addTag(BlockTags.WALLS).addTag(WALLS__CHOCOLATE.tag);
    }
    
}
