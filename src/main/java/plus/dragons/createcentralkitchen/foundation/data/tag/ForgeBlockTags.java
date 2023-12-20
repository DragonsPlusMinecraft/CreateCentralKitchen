/*package plus.dragons.createcentralkitchen.foundation.data.tag;

import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

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
        return BlockTags.create(new ResourceLocation("forge", name));
    }
    
    public static void datagen(RegistrateTagsProvider<Block> provIn) {
        var prov = new CckTagsProvider<>(provIn, Block::builtInRegistryHolder);
        prov.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(STORAGE_BLOCKS__CHOCOLATE.tag);
        prov.tag(BlockTags.SLABS).addTag(SLABS__CHOCOLATE.tag);
        prov.tag(BlockTags.STAIRS).addTag(STAIRS__CHOCOLATE.tag);
        prov.tag(BlockTags.WALLS).addTag(WALLS__CHOCOLATE.tag);
    }

    
}*/

// FIXME
