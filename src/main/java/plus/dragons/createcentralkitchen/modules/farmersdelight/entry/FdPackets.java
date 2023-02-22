package plus.dragons.createcentralkitchen.modules.farmersdelight.entry;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.core.network.LoadedPacket;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveGuideSyncPacket;

import java.util.function.Function;

public enum FdPackets {
    //Client to Server
    BLAZE_STOVE_GUIDE_SYNC(BlazeStoveGuideSyncPacket.class, BlazeStoveGuideSyncPacket::new, NetworkDirection.PLAY_TO_SERVER);
    
    public static final ResourceLocation CHANNEL_NAME = CentralKitchen.genRL("farmersdelight");
    public static final String NETWORK_VERSION = "1.3.0";
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(CHANNEL_NAME)
        .serverAcceptedVersions(NETWORK_VERSION::equals)
        .clientAcceptedVersions(NETWORK_VERSION::equals)
        .networkProtocolVersion(() -> NETWORK_VERSION)
        .simpleChannel();
    
    private final LoadedPacket<?> packet;

    <T extends SimplePacketBase> FdPackets(Class<T> type,
                                           Function<FriendlyByteBuf, T> factory,
                                           NetworkDirection direction) {
        packet = new LoadedPacket<>(type, factory, direction);
    }
    
    public static void register() {
        FdPackets[] packets = FdPackets.values();
        for (int i = 0; i < packets.length; ++i) {
            packets[i].packet.register(CHANNEL, i);
        }
    }
    
}
