package plus.dragons.createcentralkitchen.foundation.mixin.common.minersdelight;

import com.sammy.minersdelight.content.block.copper_pot.CopperPotBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveBlock;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;

@Mixin(value = CopperPotBlockEntity.class, remap = false)
public abstract class CopperPotBlockEntityMixin extends SyncedBlockEntity {

    private CopperPotBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    @Inject(method = "processCooking", at = @At(value = "INVOKE", target = "Lcom/sammy/minersdelight/content/block/copper_pot/CopperPotBlockEntity;setRecipeUsed(Lnet/minecraft/world/item/crafting/Recipe;)V", remap = true))
    private void cck$notifyBlazeStove(CookingPotRecipe recipe, CopperPotBlockEntity cookingPot, CallbackInfoReturnable<Boolean> cir) {
        assert this.level != null;
        BlockPos posBelow = this.worldPosition.below();
        if (this.level.getBlockState(posBelow).getBlock() instanceof BlazeStoveBlock stove) {
            stove.startSignal(this.level, posBelow);
        }
    }

}
