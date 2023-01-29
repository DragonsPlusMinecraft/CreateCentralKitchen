package plus.dragons.createfarmersautomation.entry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.AllSections;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import plus.dragons.createfarmersautomation.content.contraptions.components.stove.BlazeStoveBlock;
import vectorwing.farmersdelight.common.tag.ModTags;

import static plus.dragons.createfarmersautomation.FarmersAutomation.REGISTRATE;

public class CfaBlocks {
    public static final TagKey<Block> FAN_TRANSPARENT = tag(Create.asResource("fan_transparent"));
    public static final TagKey<Block> FAN_HEATERS = tag(Create.asResource("fan_heaters"));
    
    static {
        REGISTRATE.creativeModeTab(() -> Create.BASE_CREATIVE_TAB).startSection(AllSections.KINETICS);
    }
    
    public static final BlockEntry<BlazeStoveBlock> BLAZE_STOVE = REGISTRATE
        .block("blaze_stove", BlazeStoveBlock::new)
        .initialProperties(SharedProperties::softMetal)
        .properties(p -> p.color(MaterialColor.COLOR_GRAY).lightLevel(BlazeBurnerBlock::getLight))
        .tag(BlockTags.MINEABLE_WITH_PICKAXE, FAN_TRANSPARENT, FAN_HEATERS, ModTags.HEAT_SOURCES)
        .loot((prov, block) -> prov.dropOther(block, AllBlocks.BLAZE_BURNER.get()))
        .addLayer(() -> RenderType::cutoutMipped)
        .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
        .register();
    
    public static TagKey<Block> tag(ResourceLocation id) {
        return TagKey.create(Registry.BLOCK_REGISTRY, id);
    }
    
    public static void register() {}
    
}
