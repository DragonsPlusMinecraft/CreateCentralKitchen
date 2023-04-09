package plus.dragons.createcentralkitchen.foundation.network;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class LoadedPacket<T extends SimplePacketBase> {
    private final BiConsumer<T, FriendlyByteBuf> encoder;
    private final Function<FriendlyByteBuf, T> decoder;
    private final BiConsumer<T, Supplier<NetworkEvent.Context>> handler;
    private final Class<T> type;
    private final NetworkDirection direction;
    
    public LoadedPacket(Class<T> type, Function<FriendlyByteBuf, T> factory, NetworkDirection direction) {
        encoder = T::write;
        decoder = factory;
        handler = T::handle;
        this.type = type;
        this.direction = direction;
    }
    
    public void register(SimpleChannel channel, int index) {
        channel.messageBuilder(type, index, direction)
            .encoder(encoder)
            .decoder(decoder)
            .consumerMainThread(handler)
            .add();
    }
    
}
