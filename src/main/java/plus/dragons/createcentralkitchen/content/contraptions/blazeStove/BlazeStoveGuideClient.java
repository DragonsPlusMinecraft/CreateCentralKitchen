package plus.dragons.createcentralkitchen.content.contraptions.blazeStove;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.Nullable;

final class BlazeStoveGuideClient {
    private BlazeStoveGuideClient() {}

    @Nullable
    static BlazeStoveBlockEntity findBlazeStove(BlockPos pos) {
        return BlazeStoveGuideMenu.findBlazeStove(Minecraft.getInstance().level, pos);
    }
}
