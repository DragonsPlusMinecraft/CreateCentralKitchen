package plus.dragons.createcentralkitchen.foundation.mixin.common.brewinandchewin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import umpaz.brewinandchewin.common.block.entity.KegBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;

@Mixin(value = KegBlockEntity.class, priority = 900, remap = false)
public class KegBlockEntityMixin extends SyncedBlockEntity {
    
    private KegBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @ModifyExpressionValue(method = "updateTemperature", at = @At(value = "INVOKE", remap = true, target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", ordinal = 0))
    private BlockState cck$checkHeatLevelAround(BlockState state) {
        if (state.hasProperty(BlazeBurnerBlock.HEAT_LEVEL) &&
            state.getValue(BlazeBurnerBlock.HEAT_LEVEL) == BlazeBurnerBlock.HeatLevel.NONE)
            return Blocks.AIR.defaultBlockState();
        return state;
    }
    
    @ModifyExpressionValue(method = "updateTemperature", at = @At(value = "INVOKE", remap = true, target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 1))
    private boolean cck$checkHeatLevelFurtherBelow(boolean original, @Local(ordinal = 1) BlockState stateFurtherBelow) {
        return original &&
            !(stateFurtherBelow.hasProperty(BlazeBurnerBlock.HEAT_LEVEL) &&
              stateFurtherBelow.getValue(BlazeBurnerBlock.HEAT_LEVEL) == BlazeBurnerBlock.HeatLevel.NONE);
    }

}
