package plus.dragons.createcentralkitchen.mixin.common.brewinandchewin;

import com.brewinandchewin.common.block.entity.KegBlockEntity;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;

@Mixin(value = KegBlockEntity.class, remap = false)
public class KegBlockEntityMixin extends SyncedBlockEntity {
    
    @Shadow public int heat;
    
    private KegBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }
    
    @ModifyExpressionValue(method = "updateTemperature", at = @At(value = "INVOKE", remap = true, target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
    private BlockState removeEmptyBlazeBurner(BlockState state) {
        if (state.hasProperty(BlazeBurnerBlock.HEAT_LEVEL) &&
            state.getValue(BlazeBurnerBlock.HEAT_LEVEL) == BlazeBurnerBlock.HeatLevel.NONE)
            return Blocks.AIR.defaultBlockState();
        return state;
    }
    
}
