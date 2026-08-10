package plus.dragons.createcentralkitchen.content.contraptions.blazeStove;

import com.simibubi.create.foundation.networking.SimplePacketBase;
import io.netty.handler.codec.DecoderException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

public class BlazeStoveGuideSyncPacket extends SimplePacketBase {
    static final int MAX_INPUT_SLOTS = 16;
    private final int containerId;
    private final List<ResourceLocation> inputIds;

    public BlazeStoveGuideSyncPacket(BlazeStoveGuideMenu<?> menu) {
        this(menu.containerId, menu.getGuideInputIds());
    }

    BlazeStoveGuideSyncPacket(int containerId, List<ResourceLocation> inputIds) {
        if (inputIds.size() > MAX_INPUT_SLOTS)
            throw new IllegalArgumentException("Too many blaze stove guide inputs: " + inputIds.size());
        this.containerId = containerId;
        this.inputIds = List.copyOf(inputIds);
    }

    public BlazeStoveGuideSyncPacket(FriendlyByteBuf buffer) {
        this.containerId = buffer.readVarInt();
        int inputSize = buffer.readVarInt();
        if (inputSize < 0 || inputSize > MAX_INPUT_SLOTS)
            throw new DecoderException("Invalid blaze stove guide input count: " + inputSize);
        List<ResourceLocation> inputs = new ArrayList<>(inputSize);
        for (int slot = 0; slot < inputSize; slot++)
            inputs.add(buffer.readResourceLocation());
        this.inputIds = List.copyOf(inputs);
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeVarInt(containerId);
        buffer.writeVarInt(inputIds.size());
        for (ResourceLocation inputId : inputIds)
            buffer.writeResourceLocation(inputId);
    }

    @Override
    public boolean handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            var player = context.getSender();
            if (player != null && player.containerMenu instanceof BlazeStoveGuideMenu<?> menu &&
                    menu.containerId == containerId)
                menu.updateGuideInputs(inputIds);
        });
        return true;
    }

    int containerId() {
        return containerId;
    }

    List<ResourceLocation> inputIds() {
        return inputIds;
    }
}
