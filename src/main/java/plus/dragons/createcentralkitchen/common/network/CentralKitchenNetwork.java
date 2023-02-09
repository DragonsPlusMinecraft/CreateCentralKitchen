package plus.dragons.createcentralkitchen.common.network;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import plus.dragons.createcentralkitchen.CentralKitchen;

public class CentralKitchenNetwork {
    public static final ResourceLocation CHANNEL_NAME = CentralKitchen.genRL("main");
    public static final String NETWORK_VERSION = "1.2.0";
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(CHANNEL_NAME)
        .serverAcceptedVersions(NETWORK_VERSION::equals)
        .clientAcceptedVersions(NETWORK_VERSION::equals)
        .networkProtocolVersion(() -> NETWORK_VERSION)
        .simpleChannel();
    
    public static void sendToNear(Level world, BlockPos pos, int range, Object message) {
        CHANNEL.send(PacketDistributor.NEAR.with(
                PacketDistributor.TargetPoint.p(
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    range,
                    world.dimension())),
            message);
    }
    
}
