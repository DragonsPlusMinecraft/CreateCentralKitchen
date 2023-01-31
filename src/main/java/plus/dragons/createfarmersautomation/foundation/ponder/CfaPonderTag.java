package plus.dragons.createfarmersautomation.foundation.ponder;

import com.simibubi.create.foundation.ponder.PonderTag;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createfarmersautomation.FarmersAutomation;
import plus.dragons.createfarmersautomation.entry.CfaBlocks;

public class CfaPonderTag extends PonderTag {

    public static final PonderTag FARMERS_AUTONMATION = create("farmers_automation").item(CfaBlocks.BLAZE_STOVE.get(), true, false).addToIndex();

    public CfaPonderTag(ResourceLocation id) {
        super(id);
    }

    private static PonderTag create(String id) {
        return new PonderTag(FarmersAutomation.genRL(id));
    }
}
