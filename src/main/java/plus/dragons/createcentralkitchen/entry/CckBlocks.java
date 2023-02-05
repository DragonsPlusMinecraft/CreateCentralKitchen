package plus.dragons.createcentralkitchen.entry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.AllSections;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.material.MaterialColor;
import plus.dragons.createcentralkitchen.content.contraptions.components.stove.BlazeStoveBlock;
import vectorwing.farmersdelight.common.tag.ModTags;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

public class CckBlocks {
    
    static {
        REGISTRATE.creativeModeTab(() -> Create.BASE_CREATIVE_TAB).startSection(AllSections.KINETICS);
    }
    
    public static final BlockEntry<BlazeStoveBlock> BLAZE_STOVE = REGISTRATE
        .block("blaze_stove", BlazeStoveBlock::new)
        .initialProperties(SharedProperties::softMetal)
        .properties(p -> p.color(MaterialColor.COLOR_GRAY).lightLevel(BlazeBurnerBlock::getLight))
        .tag(BlockTags.MINEABLE_WITH_PICKAXE, CckTags.FAN_TRANSPARENT, CckTags.FAN_HEATERS, ModTags.HEAT_SOURCES)
        .loot((prov, block) -> prov.dropOther(block, AllBlocks.BLAZE_BURNER.get()))
        .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
        .register();
    
    public static void register() {}
    
}
