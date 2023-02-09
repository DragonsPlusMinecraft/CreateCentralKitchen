package plus.dragons.createcentralkitchen.modules.farmersdelight.entry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.material.MaterialColor;
import plus.dragons.createcentralkitchen.data.tag.OptionalTags;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveBlock;
import vectorwing.farmersdelight.common.tag.ModTags;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

public class FdBlocks {
    
    public static final BlockEntry<BlazeStoveBlock> BLAZE_STOVE = REGISTRATE
        .block("blaze_stove", BlazeStoveBlock::new)
        .initialProperties(SharedProperties::softMetal)
        .properties(p -> p.color(MaterialColor.COLOR_GRAY).lightLevel(BlazeBurnerBlock::getLight))
        .transform(OptionalTags.block(
            BlockTags.MINEABLE_WITH_PICKAXE,
            FdTags.FAN_TRANSPARENT,
            FdTags.FAN_HEATERS,
            ModTags.HEAT_SOURCES))
        .loot((prov, block) -> prov.dropOther(block, AllBlocks.BLAZE_BURNER.get()))
        .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
        .register();
    
    public static void register() {}
    
}
