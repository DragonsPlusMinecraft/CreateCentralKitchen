package plus.dragons.createcentralkitchen.foundation.data.recipe.provider;

import com.cosmicgelatin.seasonals.core.SeasonalsConfig;
import com.cosmicgelatin.seasonals.core.registry.SeasonalsItems;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import com.teamabnormals.abnormals_delight.core.registry.ADItems;
import com.teamabnormals.autumnity.core.registry.AutumnityBlocks;
import com.teamabnormals.autumnity.core.registry.AutumnityItems;
import com.teamabnormals.blueprint.core.api.conditions.BlueprintAndCondition;
import com.teamabnormals.blueprint.core.api.conditions.ConfigValueCondition;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.fluid.AutumnityFluidEntries;
import plus.dragons.createcentralkitchen.foundation.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import java.util.List;
import java.util.Map;

public class AutumnityRecipes extends DatapackRecipes {
    private final GeneratedRecipe
        FILLING_SAPPY_MAPLE_LOG = add(filling("sappy_maple_log")
            .output(AutumnityBlocks.SAPPY_MAPLE_LOG.get())
            .require(AutumnityBlocks.MAPLE_LOG.get())
            .require(AutumnityFluidEntries.SAP.get(), 250)),
        FILLING_SAPPY_MAPLE_WOOD = add(filling("sappy_maple_wood")
            .output(AutumnityBlocks.SAPPY_MAPLE_WOOD.get())
            .require(AutumnityBlocks.MAPLE_WOOD.get())
            .require(AutumnityFluidEntries.SAP.get(), 250)),
        COMPACTING_MAPLE_COOKIE = add(compacting("maple_cookie")
            .output(ADItems.MAPLE_COOKIE.get())
            .require(AutumnityFluidEntries.SYRUP.get(), 250)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)),
        MIXING_SYRUP = add(mixing("syrup")
            .output(AutumnityFluidEntries.SYRUP.get(), 250)
            .require(AutumnityFluidEntries.SAP.get(), 250)
            .requiresHeat(HeatCondition.HEATED)),
        FILLING_PANCAKE = add(filling("pancake")
            .output(AutumnityBlocks.PANCAKE.get())
            .require(ModItems.PIE_CRUST.get())
            .require(AutumnityFluidEntries.SYRUP.get(), 250)
            .whenModLoaded(Mods.FD)),
        COMPACTING_PANCAKE = add(compacting("pancake")
            .output(AutumnityBlocks.PANCAKE.get())
            .require(Tags.Items.EGGS)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(Tags.Fluids.MILK, 250)
            .require(AutumnityFluidEntries.SYRUP.get(), 250)),
        COMPACTING_PANCAKE_FROM_DOUGH = add(compacting("pancake_from_dough")
            .output(AutumnityBlocks.PANCAKE.get())
            .require(Tags.Items.EGGS)
            .require(ForgeTags.DOUGH_WHEAT)
            .require(Tags.Fluids.MILK, 250)
            .require(AutumnityFluidEntries.SYRUP.get(), 250)),
        CRAFTING_PANCAKE_FROM_DOUGH = add(shapeless("pancake_from_dough")
            .output(AutumnityBlocks.PANCAKE.get())
            .require(ForgeTags.MILK)
            .require(AutumnityItems.SYRUP_BOTTLE.get())
            .require(Tags.Items.EGGS)
            .require(ForgeTags.DOUGH_WHEAT)),
        MIXING_PUMPKIN_BREAD = add(mixing("pumpkin_bread")
            .output(AutumnityItems.PUMPKIN_BREAD.get(), 2)
            .require(Items.PUMPKIN)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(AutumnityFluidEntries.SYRUP.get(), 250)
            .whenModMissing(Mods.AD)),
        MIXING_PUMPKIN_BREAD_FROM_ROASTED_PUMPKIN = add(mixing("pumpkin_bread_from_roasted_pumpkin")
            .output(AutumnityItems.PUMPKIN_BREAD.get(), 2)
            .require(SeasonalsItems.ROASTED_PUMPKIN.get())
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(AutumnityFluidEntries.SYRUP.get(), 250)
            .withCondition(new BlueprintAndCondition(new ResourceLocation("blueprint", "and"), List.of(
                new ModLoadedCondition(Mods.SEASONALS),
                new ConfigValueCondition(Mods.seasonals("config"),
                    SeasonalsConfig.COMMON.outsideRecipes, "outside_recipes",
                    Map.of(), false))))
            .whenModMissing(Mods.AD)),
        MIXING_PUMPKIN_BREAD_FROM_PUMPKIN_SLICE = add(mixing("pumpkin_bread_from_pumpkin_slice")
            .output(AutumnityItems.PUMPKIN_BREAD.get(), 2)
            .require(ModItems.PUMPKIN_SLICE.get())
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(AutumnityFluidEntries.SYRUP.get(), 250)
            .whenModLoaded(Mods.AD)),
        CRAFTING_PUMPKIN_BREAD_FROM_DOUGH = add(shapeless("pumpkin_bread_from_dough")
            .output(AutumnityItems.PUMPKIN_BREAD.get(), 2)
            .require(AutumnityItems.SYRUP_BOTTLE.get())
            .require(Items.PUMPKIN)
            .require(ForgeTags.DOUGH_WHEAT)
            .whenModMissing(Mods.AD)),
        CRAFTING_PUMPKIN_BREAD_FROM_DOUGH_AND_ROASTED_PUMPKIN = add(shapeless("pumpkin_bread_from_dough_and_roasted_pumpkin")
            .output(AutumnityItems.PUMPKIN_BREAD.get(), 2)
            .require(AutumnityItems.SYRUP_BOTTLE.get())
            .require(ModItems.PUMPKIN_SLICE.get())
            .require(ForgeTags.DOUGH_WHEAT)
            .withCondition(new BlueprintAndCondition(new ResourceLocation("blueprint", "and"), List.of(
                new ModLoadedCondition(Mods.SEASONALS),
                new ConfigValueCondition(Mods.seasonals("config"),
                    SeasonalsConfig.COMMON.outsideRecipes, "outside_recipes",
                    Map.of(), false))))
            .whenModLoaded(Mods.AD)),
        CRAFTING_PUMPKIN_BREAD_FROM_DOUGH_AND_PUMPKIN_SLICE = add(shapeless("pumpkin_bread_from_dough_and_pumpkin_slice")
            .output(AutumnityItems.PUMPKIN_BREAD.get(), 2)
            .require(AutumnityItems.SYRUP_BOTTLE.get())
            .require(ModItems.PUMPKIN_SLICE.get())
            .require(ForgeTags.DOUGH_WHEAT)
            .whenModLoaded(Mods.AD));
    
    public AutumnityRecipes(DataGenerator datagen) {
        super(Mods.AUTUMNITY, CentralKitchen.REGISTRATE, datagen);
    }
    
}
