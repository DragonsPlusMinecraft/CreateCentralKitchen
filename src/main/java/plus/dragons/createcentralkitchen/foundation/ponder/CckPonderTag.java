package plus.dragons.createcentralkitchen.foundation.ponder;

import com.simibubi.create.foundation.ponder.PonderTag;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.CckItems;

public class CckPonderTag extends PonderTag {

    public static final PonderTag FARMERS_DELIGHT = create("farmers_delight")
            .defaultLang("Farmer's Delight Automation","Items and Components related to farmer's delight automation.")
            .item(CckItems.COOKING_GUIDE.get(), true, false).addToIndex();

    public CckPonderTag(ResourceLocation id) {
        super(id);
    }

    private static PonderTag create(String id) {
        return new PonderTag(CentralKitchen.genRL(id));
    }
}
