package plus.dragons.createcentralkitchen.foundation.mixin.common.create;

import com.simibubi.create.foundation.gui.menu.GhostItemSubmitPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveGuideMenu;

@Mixin(value = GhostItemSubmitPacket.class, remap = false)
public abstract class GhostItemSubmitPacketMixin {
    @Shadow
    @Final
    private ItemStack item;

    @Shadow
    @Final
    private int slot;

    @Inject(method = "lambda$handle$0", at = @At("HEAD"), cancellable = true, remap = false)
    private void createCentralKitchen$handleGuideSubmission(NetworkEvent.Context context, CallbackInfo ci) {
        ServerPlayer player = context.getSender();
        if (player == null || !(player.containerMenu instanceof BlazeStoveGuideMenu<?> menu))
            return;

        menu.submitGhostItem(slot, item);
        ci.cancel();
    }
}
