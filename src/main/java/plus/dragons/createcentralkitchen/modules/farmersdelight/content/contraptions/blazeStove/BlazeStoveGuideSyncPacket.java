package plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BlazeStoveGuideSyncPacket extends SimplePacketBase {
    private final CompoundTag nbt;
    
    public BlazeStoveGuideSyncPacket(BlazeStoveGuideMenu<?> menu) {
        this.nbt = menu.writeGuideToTag();
    }
    
    public BlazeStoveGuideSyncPacket(FriendlyByteBuf buffer) {
        this.nbt = buffer.readNbt();
    }
    
    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeNbt(this.nbt);
    }
    
    @Override
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        var context = supplier.get();
        var player = context.getSender();
        if (player != null && player.containerMenu instanceof BlazeStoveGuideMenu menu) {
            menu.updateGuideFromTag(nbt);
            context.setPacketHandled(true);
        }
    }
    
}
