package plus.dragons.createcentralkitchen.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.common.tag.ModTags;

@Mixin(BlockHelper.class)
public class BlockHelperMixin {
    @ModifyReturnValue(method = "isNotUnheated", at = @At("RETURN"))
    private static boolean checkForFDHeatSources(boolean original, BlockState state) {
        if (state.is(ModTags.HEAT_SOURCES) && state.hasProperty(BlockStateProperties.LIT)) {
            return state.getValue(BlockStateProperties.LIT);
        }
        return original;
    }
}
