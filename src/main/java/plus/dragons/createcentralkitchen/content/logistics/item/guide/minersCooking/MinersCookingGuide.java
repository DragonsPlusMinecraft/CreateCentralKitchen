//package plus.dragons.createcentralkitchen.content.logistics.item.guide.minersCooking;
//
//import com.sammy.minersdelight.setup.CupConversionHandler;
//import com.sammy.minersdelight.setup.MDItems;
//import net.minecraft.core.Direction;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.Level;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.util.LazyOptional;
//import net.minecraftforge.items.wrapper.RecipeWrapper;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//import plus.dragons.createcentralkitchen.content.contraptions.blazeStove.BlazeStoveGuide;
//import plus.dragons.createcentralkitchen.entry.capability.MDCapabilityEntries;
//import plus.dragons.createcentralkitchen.foundation.item.ItemHandlerModifiableView;
//import vectorwing.farmersdelight.common.registry.ModRecipeTypes;
//
//public class MinersCookingGuide extends BlazeStoveGuide {
//    private final LazyOptional<MinersCookingGuide> capability = LazyOptional.of(() -> this);
//
//    public MinersCookingGuide(ItemStack owner) {
//        super(owner, 4);
//        this.recipeWrapper = new RecipeWrapper(new ItemHandlerModifiableView(this.inventory, 0, 3, 6));
//    }
//
//    public static MinersCookingGuide of(ItemStack stack) {
//        return stack.getCapability(MDCapabilityEntries.MINERS_COOKING_GUIDE).orElseThrow(() ->
//            new UnsupportedOperationException("Requested Item " + stack.getItem() + " is not a Miner's Cooking Guide"));
//    }
//
//    @NotNull
//    @Override
//    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
//        if (cap == MDCapabilityEntries.MINERS_COOKING_GUIDE) {
//            return capability.cast();
//        }
//        return LazyOptional.empty();
//    }
//
//    public void updateRecipe(Level level) {
//        level.getRecipeManager()
//            .getRecipeFor(ModRecipeTypes.COOKING.get(), recipeWrapper, level)
//            .ifPresentOrElse(
//                recipe -> {
//                    ItemStack result = recipe.getResultItem();
//                    boolean cupServed = CupConversionHandler.BOWL_TO_CUP.containsKey(result.getItem());
//                    ItemStack container = cupServed ? MDItems.COPPER_CUP.asStack() : recipe.getOutputContainer();
//                    if (cupServed) {
//                        ItemStack cupResult = new ItemStack(CupConversionHandler.BOWL_TO_CUP.get(result.getItem()), result.getCount());
//                        cupResult.setTag(result.getTag());
//                        result = cupResult;
//                    }
//                    inventory.setStackInSlot(ingredientSize, result);
//                    this.container = container;
//                },
//                () -> {
//                    inventory.setStackInSlot(ingredientSize, ItemStack.EMPTY);
//                    container = ItemStack.EMPTY;
//                });
//    }
//
//}
