package plus.dragons.createcentralkitchen.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.simibubi.create.api.boiler.BoilerHeater;
import net.minecraft.util.Mth;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
import vectorwing.farmersdelight.common.tag.ModTags;

@Mixin(CookingPotBlockEntity.class)
public class CookingPotBlockEntityMixin {
    @ModifyExpressionValue(method = "processCooking", at = @At(value = "FIELD", target = "Lvectorwing/farmersdelight/common/block/entity/CookingPotBlockEntity;cookTime:I", ordinal = 0))
    private int addBoilerHeaterBonus(int cookTime, RecipeHolder<CookingPotRecipe> recipe, CookingPotBlockEntity cookingPot) {
        var level = cookingPot.getLevel();
        assert level != null;
        var pos = cookingPot.getBlockPos();
        var heaterPos = pos.below();
        var heaterState = level.getBlockState(heaterPos);
        BoilerHeater heater = BoilerHeater.REGISTRY.get(heaterState);
        if (heater == null && !cookingPot.requiresDirectHeat() && heaterState.is(ModTags.HEAT_CONDUCTORS)) {
            heaterPos = pos.below(2);
            heaterState = level.getBlockState(pos.below(2));
            heater = BoilerHeater.REGISTRY.get(heaterState);
        }
        if (heater != null) {
            float heat = heater.getHeat(level, heaterPos, heaterState);
            if (heat > 0)
                cookTime = cookTime + Mth.floor(Math.pow(2, heat)) - 1;
        }
        return cookTime;
    }
}
