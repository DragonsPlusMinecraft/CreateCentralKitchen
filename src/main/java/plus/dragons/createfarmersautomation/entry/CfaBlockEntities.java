package plus.dragons.createfarmersautomation.entry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import plus.dragons.createfarmersautomation.content.contraptions.components.stove.BlazeStoveBlockEntity;
import plus.dragons.createfarmersautomation.content.contraptions.components.stove.BlazeStoveRenderer;

import static plus.dragons.createfarmersautomation.FarmersAutomation.REGISTRATE;

public class CfaBlockEntities {
    
    public static final BlockEntityEntry<BlazeStoveBlockEntity> BLAZE_STOVE = REGISTRATE
        .tileEntity("blaze_stove", BlazeStoveBlockEntity::new)
        .validBlocks(CfaBlocks.BLAZE_STOVE)
        .renderer(() -> BlazeStoveRenderer::new)
        .register();
    
    public static void register() {}

}
