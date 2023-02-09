package plus.dragons.createcentralkitchen.modules.farmersdelight.entry;

import com.jozufozu.flywheel.core.PartialModel;
import plus.dragons.createcentralkitchen.CentralKitchen;

public class FdBlockPartials {
    
    public static final PartialModel BLAZE_STOVE_HAT = block("blaze_stove/hat");
    
    private static PartialModel block(String path) {
        return new PartialModel(CentralKitchen.genRL("block/" + path));
    }
    
    private static PartialModel entity(String path) {
        return new PartialModel(CentralKitchen.genRL("entity/" + path));
    }
    
    public static void register() {}
    
}
