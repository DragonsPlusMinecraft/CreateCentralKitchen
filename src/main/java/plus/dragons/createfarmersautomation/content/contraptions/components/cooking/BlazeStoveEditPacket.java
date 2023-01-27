package plus.dragons.createfarmersautomation.content.contraptions.components.cooking;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BlazeStoveEditPacket extends SimplePacketBase {
    private ItemStack itemStack;
    private BlockPos blockPos;

    public BlazeStoveEditPacket(ItemStack enchantedBook, BlockPos blockPos) {
        itemStack = enchantedBook;
        this.blockPos = blockPos;
    }

    public BlazeStoveEditPacket(FriendlyByteBuf buffer) {
        itemStack = buffer.readItem();
        blockPos = buffer.readBlockPos();
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeItem(itemStack);
        buffer.writeBlockPos(blockPos);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get()
                .enqueueWork(() -> {
                    ServerPlayer sender = context.get()
                            .getSender();
                    if(!(sender.level.getBlockEntity(blockPos) instanceof BlazeStoveBlockEntity blazeStove))
                        return;
                    blazeStove.setCookingGuide(itemStack);
                    blazeStove.notifyUpdate();
                });
        context.get()
                .setPacketHandled(true);
    }
}
