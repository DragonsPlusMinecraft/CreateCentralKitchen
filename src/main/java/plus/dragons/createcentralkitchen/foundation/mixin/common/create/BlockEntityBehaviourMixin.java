package plus.dragons.createcentralkitchen.foundation.mixin.common.create;

import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.dragons.createcentralkitchen.api.block.entity.SmartBlockEntityLike;

@Mixin(value = BlockEntityBehaviour.class, remap = false)
public class BlockEntityBehaviourMixin {
    
    @Inject(
        method = "get(Lnet/minecraft/world/level/block/entity/BlockEntity;Lcom/simibubi/create/foundation/blockEntity/behaviour/BehaviourType;)Lcom/simibubi/create/foundation/blockEntity/behaviour/BlockEntityBehaviour;",
        at = @At(value = "RETURN", ordinal = 1),
        cancellable = true)
    private static <T extends BlockEntityBehaviour> void checkSmartBlockEntityLike(BlockEntity be, BehaviourType<T> type, CallbackInfoReturnable<T> cir) {
        if (be instanceof SmartBlockEntityLike like)
            cir.setReturnValue(like.asSmartBlockEntity().getBehaviour(type));
    }
    
}
