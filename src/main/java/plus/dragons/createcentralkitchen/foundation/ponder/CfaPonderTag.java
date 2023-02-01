package plus.dragons.createcentralkitchen.foundation.ponder;

import com.simibubi.create.foundation.ponder.PonderTag;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.CckBlocks;

public class CfaPonderTag extends PonderTag {

    public static final PonderTag KITCHEN = create("kitchen").item(CckBlocks.BLAZE_STOVE.get(), true, false).addToIndex();

    public CfaPonderTag(ResourceLocation id) {
        super(id);
    }

    private static PonderTag create(String id) {
        return new PonderTag(CentralKitchen.genRL(id));
    }
}
