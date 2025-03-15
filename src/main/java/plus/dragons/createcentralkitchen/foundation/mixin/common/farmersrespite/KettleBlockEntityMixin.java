package plus.dragons.createcentralkitchen.foundation.mixin.common.farmersrespite;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveBlock;
import umpaz.farmersrespite.common.block.entity.KettleBlockEntity;
import umpaz.farmersrespite.common.crafting.KettleRecipe;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;

@Mixin(value = KettleBlockEntity.class, remap = false)
public abstract class KettleBlockEntityMixin extends SyncedBlockEntity {
    
    private KettleBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }
    
    @Inject(method = "processBrewing", at = @At(value = "INVOKE", target = "Lumpaz/farmersrespite/common/block/entity/KettleBlockEntity;setRecipeUsed(Lnet/minecraft/world/item/crafting/Recipe;)V", remap = true))
    private void cck$notifyBlazeStove(KettleRecipe recipe, KettleBlockEntity kettle, CallbackInfoReturnable<Boolean> cir) {
        assert this.level != null;
        BlockPos posBelow = this.worldPosition.below();
        if (this.level.getBlockState(posBelow).getBlock() instanceof BlazeStoveBlock stove) {
            stove.startSignal(this.level, posBelow);
        }
    }
    
}
