package plus.dragons.createcentralkitchen.content.contraptions.components.stove;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import plus.dragons.createcentralkitchen.entry.CckItems;

import java.util.function.Supplier;

public class CookingGuideEditPacket extends SimplePacketBase {
    private ItemStack itemStack;

    public CookingGuideEditPacket(ItemStack enchantedBook) {
        itemStack = enchantedBook;
    }

    public CookingGuideEditPacket(FriendlyByteBuf buffer) {
        itemStack = buffer.readItem();
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeItem(itemStack);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get()
                .enqueueWork(() -> {
                    ServerPlayer sender = context.get()
                            .getSender();
                    ItemStack mainHandItem = sender.getMainHandItem();
                    if (!CckItems.COOKING_GUIDE.isIn(mainHandItem))
                        return;

                    sender.setItemInHand(sender.getUsedItemHand(),itemStack);
                    sender.getCooldowns().addCooldown(mainHandItem.getItem(), 5);
                });
        context.get()
                .setPacketHandled(true);
    }
}
