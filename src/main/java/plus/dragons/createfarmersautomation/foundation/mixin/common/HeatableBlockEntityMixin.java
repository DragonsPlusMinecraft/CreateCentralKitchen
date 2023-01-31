package plus.dragons.createfarmersautomation.foundation.mixin.common;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.dragons.createfarmersautomation.content.contraptions.components.stove.BlazeStoveBlock;
import plus.dragons.createfarmersautomation.entry.CfaBlocks;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;

@Mixin(HeatableBlockEntity.class)
interface HeatableBlockEntityMixin {
    @Inject(method = "isHeated", at = @At("HEAD"))
    private void inject(Level level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        BlockState stateBelow = level.getBlockState(pos.below());
        if (stateBelow.is(AllBlocks.BLAZE_BURNER.get())) {
            cir.setReturnValue(stateBelow.getValue(BlazeBurnerBlock.HEAT_LEVEL) != BlazeBurnerBlock.HeatLevel.NONE);
        } else if (stateBelow.is(AllBlocks.LIT_BLAZE_BURNER.get())){
            cir.setReturnValue(true);
        } else if(stateBelow.is(CfaBlocks.BLAZE_STOVE.get())){
            cir.setReturnValue(stateBelow.getValue(BlazeStoveBlock.HEAT_LEVEL) != BlazeBurnerBlock.HeatLevel.NONE);
        }
    }
}
