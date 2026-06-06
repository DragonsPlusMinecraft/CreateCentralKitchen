package plus.dragons.createcentralkitchen.entry;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import plus.dragons.createcentralkitchen.CentralKitchen;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CentralKitchen.ID, bus = Bus.MOD)
public class CentralKitchenPartialModels {
    public static final PartialModel BLAZE_STOVE_HAT = block("blaze_stove/hat");

    private static PartialModel block(String path) {
        return PartialModel.of(CentralKitchen.genRL("block/" + path));
    }

    private static PartialModel entity(String path) {
        return PartialModel.of(CentralKitchen.genRL("entity/" + path));
    }
}
