package plus.dragons.createcentralkitchen.entry;

import com.jozufozu.flywheel.core.PartialModel;
import plus.dragons.createcentralkitchen.FarmersAutomation;

public class CckBlockPartials {
    
    public static final PartialModel BLAZE_STOVE_HAT = block("blaze_stove/hat");
    
    private static PartialModel block(String path) {
        return new PartialModel(FarmersAutomation.genRL("block/" + path));
    }
    
    private static PartialModel entity(String path) {
        return new PartialModel(FarmersAutomation.genRL("entity/" + path));
    }
    
    public static void register() {}
    
}
