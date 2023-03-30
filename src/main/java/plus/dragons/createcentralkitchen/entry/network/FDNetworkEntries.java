package plus.dragons.createcentralkitchen.entry.network;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveGuideSyncPacket;
import plus.dragons.createcentralkitchen.foundation.network.LoadedPacket;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import java.util.function.Function;

@ModLoadSubscriber(modid = Mods.FD)
public enum FDNetworkEntries {
    BLAZE_STOVE_GUIDE_SYNC(BlazeStoveGuideSyncPacket.class, BlazeStoveGuideSyncPacket::new, NetworkDirection.PLAY_TO_SERVER);
    
    public static final ResourceLocation CHANNEL_NAME = CentralKitchen.genRL(Mods.FD);
    public static final String NETWORK_VERSION = "1.3.0";
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(CHANNEL_NAME)
        .serverAcceptedVersions(NETWORK_VERSION::equals)
        .clientAcceptedVersions(NETWORK_VERSION::equals)
        .networkProtocolVersion(() -> NETWORK_VERSION)
        .simpleChannel();
    
    private final LoadedPacket<?> packet;

    <T extends SimplePacketBase> FDNetworkEntries(Class<T> type,
                                                  Function<FriendlyByteBuf, T> factory,
                                                  NetworkDirection direction) {
        packet = new LoadedPacket<>(type, factory, direction);
    }
    
    public static void register(FMLCommonSetupEvent event) {
        FDNetworkEntries[] entries = FDNetworkEntries.values();
        for (int i = 0; i < entries.length; ++i) {
            entries[i].packet.register(CHANNEL, i);
        }
    }
    
}
