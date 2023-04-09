package plus.dragons.createcentralkitchen.foundation.mixin.common.farmersrespite;

import com.farmersrespite.common.block.entity.KettleBlockEntity;
import com.farmersrespite.common.crafting.KettleRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveBlock;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;

@Mixin(value = KettleBlockEntity.class, remap = false)
public abstract class KettleBlockEntityMixin extends SyncedBlockEntity {
    
    private KettleBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }
    
    @Inject(method = "processBrewing", at = @At(value = "INVOKE", target = "Lcom/farmersrespite/common/block/entity/KettleBlockEntity;trackRecipeExperience(Lnet/minecraft/world/item/crafting/Recipe;)V"))
    private void cck$notifyBlazeStove(KettleRecipe recipe, CallbackInfoReturnable<Boolean> cir) {
        assert this.level != null;
        BlockPos posBelow = this.worldPosition.below();
        if (this.level.getBlockState(posBelow).getBlock() instanceof BlazeStoveBlock stove) {
            stove.startSignal(this.level, posBelow);
        }
    }
    
}
