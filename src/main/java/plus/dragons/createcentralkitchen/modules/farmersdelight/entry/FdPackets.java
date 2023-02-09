package plus.dragons.createcentralkitchen.modules.farmersdelight.entry;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import plus.dragons.createcentralkitchen.common.network.LoadedPacket;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.CookingGuideSyncPacket;

import java.util.function.Function;

public enum FdPackets {
    //Client to Server
    COOKING_GUIDE_SYNC(CookingGuideSyncPacket.class, CookingGuideSyncPacket::new, NetworkDirection.PLAY_TO_SERVER);
    
    private final LoadedPacket<?> packet;

    <T extends SimplePacketBase> FdPackets(Class<T> type,
                                           Function<FriendlyByteBuf, T> factory,
                                           NetworkDirection direction) {
        packet = new LoadedPacket<>(type, factory, direction);
    }
    
    public static void register() {
        for (FdPackets entry : values())
            entry.packet.register();
    }
    
}
