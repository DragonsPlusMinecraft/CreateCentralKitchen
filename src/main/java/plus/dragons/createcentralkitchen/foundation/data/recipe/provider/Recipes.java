package plus.dragons.createcentralkitchen.foundation.data.recipe.provider;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.foundation.data.recipe.builder.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Recipes extends RegistrateRecipeProvider {
    protected final List<GeneratedRecipe> recipes = new ArrayList<>();
    
    public Recipes(AbstractRegistrate<?> registrate, DataGenerator generator) {
        super(registrate, generator.getPackOutput());
    }

    @Override
    public void accept(@Nullable FinishedRecipe recipe) {
        recipes.add(consumer -> consumer.accept(recipe));
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        for (var recipe : recipes)
            recipe.register(consumer);
    }

    public GeneratedRecipe add(ConditionedRecipeBuilder<?> builder) {
        GeneratedRecipe generated = builder::save;
        recipes.add(generated);
        return generated;
    }
    
    public GeneratedRecipe add(ConditionedSequencedAssemblyRecipeBuilder builder) {
        GeneratedRecipe generated = builder::build;
        recipes.add(generated);
        return generated;
    }
    
    public GeneratedRecipe add(ProcessingRecipeBuilder<?> builder) {
        GeneratedRecipe generated = builder::build;
        recipes.add(generated);
        return generated;
    }
    
    public GeneratedRecipe add(SequencedAssemblyRecipeBuilder builder) {
        GeneratedRecipe generated = builder::build;
        recipes.add(generated);
        return generated;
    }
    
    public static ConditionedShapelessRecipeBuilder shapeless(ResourceLocation id) {
        return new ConditionedShapelessRecipeBuilder(id);
    }
    
    public static ConditionedShapelessRecipeBuilder shapeless(String path) {
        return new ConditionedShapelessRecipeBuilder(CentralKitchen.genRL(path));
    }
    
    public static ConditionedShapedRecipeBuilder shaped(ResourceLocation id) {
        return new ConditionedShapedRecipeBuilder(id);
    }
    
    public static ConditionedShapedRecipeBuilder shaped(String path) {
        return new ConditionedShapedRecipeBuilder(CentralKitchen.genRL(path));
    }
    
    public static ConditionedCookingRecipeBuilder campfire(DataIngredient ingredient, ItemLike result, float experience, int cookingTime) {
        return ConditionedCookingRecipeBuilder.campfireCooking(ingredient, result, experience, cookingTime);
    }
    
    public static ConditionedCookingRecipeBuilder campfire(DataIngredient ingredient, ItemLike result, float experience) {
        return ConditionedCookingRecipeBuilder.campfireCooking(ingredient, result, experience, 600);
    }
    
    public static ConditionedCookingRecipeBuilder blasting(DataIngredient ingredient, ItemLike result, float experience, int cookingTime) {
        return ConditionedCookingRecipeBuilder.blasting(ingredient, result, experience, cookingTime);
    }
    
    public static ConditionedCookingRecipeBuilder blasting(DataIngredient ingredient, ItemLike result, float experience) {
        return ConditionedCookingRecipeBuilder.blasting(ingredient, result, experience, 100);
    }
    
    public static ConditionedCookingRecipeBuilder smelting(DataIngredient ingredient, ItemLike result, float experience, int cookingTime) {
        return ConditionedCookingRecipeBuilder.smelting(ingredient, result, experience, cookingTime);
    }
    
    public static ConditionedCookingRecipeBuilder smelting(DataIngredient ingredient, ItemLike result, float experience) {
        return ConditionedCookingRecipeBuilder.smelting(ingredient, result, experience, 200);
    }
    
    public static ConditionedCookingRecipeBuilder smoking(DataIngredient ingredient, ItemLike result, float experience, int cookingTime) {
        return ConditionedCookingRecipeBuilder.smoking(ingredient, result, experience, cookingTime);
    }
    
    public static ConditionedCookingRecipeBuilder smoking(DataIngredient ingredient, ItemLike result, float experience) {
        return ConditionedCookingRecipeBuilder.smoking(ingredient, result, experience, 100);
    }
    
    public static ConditionedCuttingBoardRecipeBuilder cuttingBoard(ResourceLocation id) {
        return new ConditionedCuttingBoardRecipeBuilder(id);
    }
    
    public static ConditionedCuttingBoardRecipeBuilder cuttingBoard(String path) {
        return new ConditionedCuttingBoardRecipeBuilder(CentralKitchen.genRL(path));
    }
    
/*    public static ConditionedKettleRecipeBuilder kettle(ResourceLocation id) {
        return new ConditionedKettleRecipeBuilder(id);
    }
    
    public static ConditionedKettleRecipeBuilder kettle(String path) {
        return new ConditionedKettleRecipeBuilder(CentralKitchen.genRL(path));
    }*/
    
    public static ProcessingRecipeBuilder<?> processing(IRecipeTypeInfo type, ResourceLocation id) {
        ProcessingRecipeSerializer<?> serializer = type.getSerializer();
        return new ProcessingRecipeBuilder<>(serializer.getFactory(), id);
    }
    
    public static ProcessingRecipeBuilder<?> crushing(ResourceLocation id) {
        return processing(AllRecipeTypes.CRUSHING, id);
    }
    
    public static ProcessingRecipeBuilder<?> crushing(String path) {
        return processing(AllRecipeTypes.CRUSHING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> milling(ResourceLocation id) {
        return processing(AllRecipeTypes.MILLING, id);
    }
    
    public static ProcessingRecipeBuilder<?> milling(String path) {
        return processing(AllRecipeTypes.MILLING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> mixing(ResourceLocation id) {
        return processing(AllRecipeTypes.MIXING, id);
    }
    
    public static ProcessingRecipeBuilder<?> mixing(String path) {
        return processing(AllRecipeTypes.MIXING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> compacting(ResourceLocation id) {
        return processing(AllRecipeTypes.COMPACTING, id);
    }
    
    public static ProcessingRecipeBuilder<?> compacting(String path) {
        return processing(AllRecipeTypes.COMPACTING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> pressing(ResourceLocation id) {
        return processing(AllRecipeTypes.PRESSING, id);
    }
    
    public static ProcessingRecipeBuilder<?> pressing(String path) {
        return processing(AllRecipeTypes.PRESSING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> polishing(ResourceLocation id) {
        return processing(AllRecipeTypes.SANDPAPER_POLISHING, id);
    }
    
    public static ProcessingRecipeBuilder<?> polishing(String path) {
        return processing(AllRecipeTypes.SANDPAPER_POLISHING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> splashing(ResourceLocation id) {
        return processing(AllRecipeTypes.SPLASHING, id);
    }
    
    public static ProcessingRecipeBuilder<?> splashing(String path) {
        return processing(AllRecipeTypes.SPLASHING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> haunting(ResourceLocation id) {
        return processing(AllRecipeTypes.HAUNTING, id);
    }
    
    public static ProcessingRecipeBuilder<?> haunting(String path) {
        return processing(AllRecipeTypes.HAUNTING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> deploying(ResourceLocation id) {
        return processing(AllRecipeTypes.DEPLOYING, id);
    }
    
    public static ProcessingRecipeBuilder<?> deploying(String path) {
        return processing(AllRecipeTypes.DEPLOYING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> filling(ResourceLocation id) {
        return processing(AllRecipeTypes.FILLING, id);
    }
    
    public static ProcessingRecipeBuilder<?> filling(String path) {
        return processing(AllRecipeTypes.FILLING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> emptying(ResourceLocation id) {
        return processing(AllRecipeTypes.EMPTYING, id);
    }
    
    public static ProcessingRecipeBuilder<?> emptying(String path) {
        return processing(AllRecipeTypes.EMPTYING, CentralKitchen.genRL(path));
    }
    
    public static ProcessingRecipeBuilder<?> itemApplication(ResourceLocation id) {
        return processing(AllRecipeTypes.ITEM_APPLICATION, id);
    }
    
    public static ProcessingRecipeBuilder<?> itemApplication(String path) {
        return processing(AllRecipeTypes.ITEM_APPLICATION, CentralKitchen.genRL(path));
    }
    
    public static ConditionedSequencedAssemblyRecipeBuilder sequencedAssembly(ResourceLocation id) {
        return new ConditionedSequencedAssemblyRecipeBuilder(id);
    }
    
    public static ConditionedSequencedAssemblyRecipeBuilder sequencedAssembly(String path) {
        return new ConditionedSequencedAssemblyRecipeBuilder(CentralKitchen.genRL(path));
    }
    
    @SuppressWarnings("deprecation")
    private static <T extends ForgeFlowingFluid, P extends AbstractRegistrate<P>> NonNullUnaryOperator<FluidBuilder<T, P>> fluidHandling(Supplier<? extends ItemLike> item, ResourceLocation id, int amount) {
        String mod = id.getNamespace();
        String name = id.getPath();
        return builder -> builder.transform(DatapackRecipes.addRecipe(mod, (ctx, prov) -> {
                Fluid source = ctx.get().getSource();
                Item container = item.get().asItem().getCraftingRemainingItem();
                prov.add(emptying(name)
                    .require(item.get())
                    .output(source, amount)
                    .output(container)
                    .whenModLoaded(mod));
                prov.add(filling(name)
                    .require(container)
                    .require(source, amount)
                    .output(item.get())
                    .whenModLoaded(mod));
            }));
    }
    
    public static <T extends ForgeFlowingFluid, P extends AbstractRegistrate<P>> NonNullUnaryOperator<FluidBuilder<T, P>> fluidHandling(RegistryObject<? extends ItemLike> item, int amount) {
        return fluidHandling(item, item.getId(), amount);
    }
    
    public static <T extends ForgeFlowingFluid, P extends AbstractRegistrate<P>> NonNullUnaryOperator<FluidBuilder<T, P>> fluidHandling(ItemProviderEntry<?> item, int amount) {
        return fluidHandling(item, item.getId(), amount);
    }
    
}
