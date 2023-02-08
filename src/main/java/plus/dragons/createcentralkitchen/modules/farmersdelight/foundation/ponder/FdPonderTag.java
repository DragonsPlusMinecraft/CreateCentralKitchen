package plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder;

import com.simibubi.create.foundation.ponder.PonderTag;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FdItems;

public class FdPonderTag extends PonderTag {

    public static final PonderTag FARMERS_DELIGHT = create("farmers_delight").item(FdItems.COOKING_GUIDE.get(), true, false).addToIndex();

    public FdPonderTag(ResourceLocation id) {
        super(id);
    }

    private static PonderTag create(String id) {
        return new PonderTag(CentralKitchen.genRL(id));
    }
}
