package plus.dragons.createcentralkitchen.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.tag.ModTags;

@Mixin(BlazeBurnerBlockEntity.class)
public abstract class BlazeBurnerBlockEntityMixin extends SmartBlockEntity {
    private BlazeBurnerBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @ModifyReturnValue(method = "isValidBlockAbove", at = @At("TAIL"))
    private boolean isHeatableBlockEntityAbove(boolean original, @Local BlockState aboveState) {
        if (original)
            return true;
        assert level != null;
        if (level.getBlockEntity(worldPosition.above()) instanceof HeatableBlockEntity)
            return true;
        if (aboveState.is(ModTags.HEAT_CONDUCTORS))
            return level.getBlockEntity(worldPosition.above(2)) instanceof HeatableBlockEntity;
        return false;
    }
}
