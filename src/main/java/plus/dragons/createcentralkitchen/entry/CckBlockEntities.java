package plus.dragons.createcentralkitchen.entry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import plus.dragons.createcentralkitchen.content.contraptions.components.stove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.content.contraptions.components.stove.BlazeStoveRenderer;

import static plus.dragons.createcentralkitchen.FarmersAutomation.REGISTRATE;

public class CckBlockEntities {
    
    public static final BlockEntityEntry<BlazeStoveBlockEntity> BLAZE_STOVE = REGISTRATE
        .tileEntity("blaze_stove", BlazeStoveBlockEntity::new)
        .validBlocks(CckBlocks.BLAZE_STOVE)
        .renderer(() -> BlazeStoveRenderer::new)
        .register();
    
    public static void register() {}

}
