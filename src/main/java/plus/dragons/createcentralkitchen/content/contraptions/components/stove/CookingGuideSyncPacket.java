package plus.dragons.createcentralkitchen.content.contraptions.components.stove;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CookingGuideSyncPacket extends SimplePacketBase {
    private final CompoundTag nbt;
    
    public CookingGuideSyncPacket(CookingGuideMenu menu) {
        this.nbt = menu.cookingGuide.serializeNBT();
    }
    
    public CookingGuideSyncPacket(FriendlyByteBuf buffer) {
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
        if (player != null && player.containerMenu instanceof CookingGuideMenu menu) {
            menu.cookingGuide.deserializeNBT(nbt);
        }
    }
    
}
