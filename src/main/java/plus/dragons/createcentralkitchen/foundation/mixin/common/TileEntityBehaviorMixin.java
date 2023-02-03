package plus.dragons.createcentralkitchen.foundation.mixin.common;

import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.BehaviourType;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.dragons.createcentralkitchen.foundation.tileEntity.SmartTileEntityLike;

@Mixin(TileEntityBehaviour.class)
public class TileEntityBehaviorMixin {
    
    @Inject(
        method = "get(Lnet/minecraft/world/level/block/entity/BlockEntity;Lcom/simibubi/create/foundation/tileEntity/behaviour/BehaviourType;)Lcom/simibubi/create/foundation/tileEntity/TileEntityBehaviour;",
        at = @At(value = "RETURN", ordinal = 1),
        cancellable = true,
        remap = false)
    private static <T extends TileEntityBehaviour> void get$checkSmartTileEntityLike(BlockEntity be, BehaviourType<T> type, CallbackInfoReturnable<T> cir) {
        if (be instanceof SmartTileEntityLike like)
            cir.setReturnValue(like.cck$asSmartTE().getBehaviour(type));
    }
    
}
