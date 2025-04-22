package plus.dragons.createcentralkitchen.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.api.boiler.BoilerHeater;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;

@Mixin(HeatableBlockEntity.class)
public interface HeatableBlockEntityMixin {
    @Inject(method = "isHeated", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 0), cancellable = true)
    private void isHeatedByBoilerHeaterBelow(Level level, BlockPos pos, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) BlockState stateBelow) {
        BoilerHeater heater = BoilerHeater.REGISTRY.get(stateBelow);
        if (heater != null)
            cir.setReturnValue(heater.getHeat(level, pos.below(), stateBelow) >= 0);
    }

    @Inject(method = "isHeated", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 2), cancellable = true)
    private void isHeatedByBoilerHeaterFurtherBelow(Level level, BlockPos pos, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 1) BlockState stateFurtherBelow) {
        BoilerHeater heater = BoilerHeater.REGISTRY.get(stateFurtherBelow);
        if (heater != null)
            cir.setReturnValue(heater.getHeat(level, pos.below(2), stateFurtherBelow) >= 0);
    }
}
