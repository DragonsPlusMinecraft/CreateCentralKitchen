package plus.dragons.createcentralkitchen.mixin.farmersdelight;

import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.JsonOps;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.data.RuntimeDataGenerator;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.createmod.catnip.codecs.CatnipCodecUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.conditions.WithConditions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import plus.dragons.createcentralkitchen.config.CCKConfig;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.Optional;

@Mixin(RuntimeDataGenerator.class)
public class RuntimeDataGeneratorMixin {
    @Shadow @Final private static Object2ObjectOpenHashMap<ResourceLocation, JsonElement> JSON_FILES;

    @WrapOperation(method = "cuttingRecipes", at = @At(value = "INVOKE", ordinal = 0, target = "Lcom/simibubi/create/foundation/data/RuntimeDataGenerator;simpleWoodRecipe(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;)V"))
    private static void addTreeBarkToStripCuttingRecipes(ResourceLocation unstripped, ResourceLocation stripped, Operation<Void> original, @Local(ordinal = 3) String type) {
        if (CCKConfig.recipes().addTreeBarkToStripSawingRecipes.get()) {
            if (BuiltInRegistries.ITEM.containsKey(stripped)) {
                var id = Create.asResource("cutting/runtime_generated/compat/" + unstripped.getNamespace() +
                                           "/" + unstripped.getPath() + "_to_" + stripped.getPath());
                var recipe = new ProcessingRecipeBuilder<>(CuttingRecipe::new, id)
                        .require(BuiltInRegistries.ITEM.get(unstripped))
                        .output(BuiltInRegistries.ITEM.get(stripped))
                        .output(type.contains("block") ? ModItems.STRAW.get() : ModItems.TREE_BARK.get())
                        .duration(50)
                        .build();
                var serialized = CatnipCodecUtils.encode(Recipe.CONDITIONAL_CODEC, JsonOps.INSTANCE, Optional.of(new WithConditions<>(recipe)));
                serialized.ifPresent(r -> JSON_FILES.put(id.withPrefix("recipe/"), r));
            }
        } else original.call(unstripped, stripped);
    }
}
