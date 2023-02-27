package plus.dragons.createcentralkitchen.data.tag;

import com.teamabnormals.neapolitan.core.registry.NeapolitanBlocks;
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
        prov.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(STORAGE_BLOCKS__CHOCOLATE.tag);
        prov.tag(BlockTags.SLABS).addTag(SLABS__CHOCOLATE.tag);
        prov.tag(BlockTags.STAIRS).addTag(STAIRS__CHOCOLATE.tag);
        prov.tag(BlockTags.WALLS).addTag(WALLS__CHOCOLATE.tag);
        prov.tag(STORAGE_BLOCKS__CHOCOLATE.tag)
            .addOptional(NeapolitanBlocks.CHOCOLATE_BLOCK.getId())
            .addOptional(NeapolitanBlocks.CHOCOLATE_BRICKS.getId())
            .addOptional(NeapolitanBlocks.CHOCOLATE_TILES.getId())
            .addOptional(new ResourceLocation("create_confectionary", "chocolate_bricks"));
        prov.tag(SLABS__CHOCOLATE.tag)
            .addOptional(NeapolitanBlocks.CHOCOLATE_BRICK_SLAB.getId())
            .addOptional(NeapolitanBlocks.CHOCOLATE_TILE_SLAB.getId())
            .addOptional(new ResourceLocation("create_confectionary", "chocolate_bricks_slab"));
        prov.tag(STAIRS__CHOCOLATE.tag)
            .addOptional(NeapolitanBlocks.CHOCOLATE_BRICK_STAIRS.getId())
            .addOptional(NeapolitanBlocks.CHOCOLATE_TILE_STAIRS.getId())
            .addOptional(new ResourceLocation("create_confectionary", "chocolate_bricks_stairs"));
        prov.tag(WALLS__CHOCOLATE.tag)
            .addOptional(NeapolitanBlocks.CHOCOLATE_BRICK_WALL.getId())
            .addOptional(NeapolitanBlocks.CHOCOLATE_TILE_WALL.getId())
            .addOptional(new ResourceLocation("create_confectionary", "chocolate_bricks_wall"));
    }
    
}
