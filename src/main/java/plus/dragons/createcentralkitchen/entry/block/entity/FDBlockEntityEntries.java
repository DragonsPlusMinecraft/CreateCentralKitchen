package plus.dragons.createcentralkitchen.entry.block.entity;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveRenderer;
import plus.dragons.createcentralkitchen.entry.block.FDBlockEntries;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

@ModLoadSubscriber(modid = Mods.FD)
public class FDBlockEntityEntries {
    
    public static final BlockEntityEntry<BlazeStoveBlockEntity> BLAZE_STOVE = REGISTRATE
        .tileEntity("blaze_stove", BlazeStoveBlockEntity::new)
        .validBlocks(FDBlockEntries.BLAZE_STOVE)
        .renderer(() -> BlazeStoveRenderer::new)
        .register();

}
