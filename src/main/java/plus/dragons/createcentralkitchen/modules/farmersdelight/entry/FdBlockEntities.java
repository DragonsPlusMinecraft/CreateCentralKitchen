package plus.dragons.createcentralkitchen.modules.farmersdelight.entry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveRenderer;

import static plus.dragons.createcentralkitchen.CentralKitchen.REGISTRATE;

public class FdBlockEntities {
    
    public static final BlockEntityEntry<BlazeStoveBlockEntity> BLAZE_STOVE = REGISTRATE
        .tileEntity("blaze_stove", BlazeStoveBlockEntity::new)
        .validBlocks(FdBlocks.BLAZE_STOVE)
        .renderer(() -> BlazeStoveRenderer::new)
        .register();
    
    public static void register() {}

}
