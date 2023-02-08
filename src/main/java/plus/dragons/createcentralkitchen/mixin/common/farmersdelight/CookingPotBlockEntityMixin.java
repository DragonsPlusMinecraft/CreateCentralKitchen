package plus.dragons.createcentralkitchen.mixin.common.farmersdelight;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;

@Mixin(CookingPotBlockEntity.class)
public abstract class CookingPotBlockEntityMixin extends SyncedBlockEntity implements HeatableBlockEntity {
    public CookingPotBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Inject(method = "isHeated", at = @At("HEAD"), remap = false, cancellable = true)
    public void inject(CallbackInfoReturnable<Boolean> cir) {
        if(level!=null){
            BlockState stateBelow = level.getBlockState(worldPosition.below());
            if (stateBelow.is(AllBlocks.BLAZE_BURNER.get())) {
                cir.setReturnValue(stateBelow.getValue(BlazeBurnerBlock.HEAT_LEVEL) != BlazeBurnerBlock.HeatLevel.NONE);
            }
        }
    }
}
