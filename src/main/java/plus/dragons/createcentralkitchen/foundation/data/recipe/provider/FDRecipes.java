package plus.dragons.createcentralkitchen.foundation.data.recipe.provider;

import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.components.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.contraptions.fluids.actors.FillingRecipe;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider.GeneratedRecipe;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.upgrade_aquatic.core.registry.UAItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.fluid.FDFluidEntries;
import plus.dragons.createcentralkitchen.entry.item.FDItemEntries;
import plus.dragons.createcentralkitchen.foundation.data.tag.ForgeItemTags;
import plus.dragons.createcentralkitchen.foundation.resource.condition.ConfigBoolCondition;
import plus.dragons.createcentralkitchen.foundation.resource.condition.ConfigListCondition;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;

@MethodsReturnNonnullByDefault
public class FDRecipes extends DatapackRecipes {
    private final GeneratedRecipe
        MIXING_APPLE_CIDER = add(mixing("apple_cider")
            .output(FDFluidEntries.APPLE_CIDER.get(), 250)
            .require(Items.APPLE)
            .require(Items.APPLE)
            .require(Items.SUGAR)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_HOT_COCOA = add(mixing("hot_cocoa")
            .output(FDFluidEntries.HOT_COCOA.get(), 250)
            .require(AllTags.forgeFluidTag("milk"), 125)
            .require(AllTags.forgeFluidTag("chocolate"), 125)
            .requiresHeat(HeatCondition.HEATED)),
        MIXING_MELON_JUICE = add(mixing("melon_juice")
            .output(FDFluidEntries.MELON_JUICE.get(), 250)
            .require(Items.MELON_SLICE)
            .require(Items.MELON_SLICE)
            .require(Items.MELON_SLICE)
            .require(Items.MELON_SLICE)
            .require(Items.SUGAR)),
        MIXING_TOMATO_SAUCE = add(mixing("tomato_sauce")
            .output(FDFluidEntries.TOMATO_SAUCE.get(), 250)
            .require(ForgeTags.VEGETABLES_TOMATO)
            .require(ForgeTags.VEGETABLES_TOMATO)),
        COMPACTING_SWEET_BERRY_COOKIE_FROM_FLOUR = add(compacting("sweet_berry_cookie_from_flour")
            .output(ModItems.SWEET_BERRY_COOKIE.get(), 8)
            .require(Items.SWEET_BERRIES)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)),
        COMPACTING_SWEET_BERRY_COOKIE_FROM_DOUGH = add(compacting("sweet_berry_cookie_from_dough")
            .output(ModItems.SWEET_BERRY_COOKIE.get(), 8)
            .require(Items.SWEET_BERRIES)
            .require(ForgeTags.DOUGH_WHEAT)),
        COMPACTING_HONEY_COOKIE_FROM_FLOUR = add(compacting("honey_cookie_from_flour")
            .output(ModItems.HONEY_COOKIE.get(), 8)
            .require(AllTags.AllFluidTags.HONEY.tag, 250)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)
            .require(ForgeItemTags.FLOUR__WHEAT.tag)),
        COMPACTING_HONEY_COOKIE_FROM_DOUGH = add(compacting("honey_cookie_from_dough")
            .output(ModItems.HONEY_COOKIE.get(), 8)
            .require(AllTags.AllFluidTags.HONEY.tag, 250)
            .require(ForgeTags.DOUGH_WHEAT)),
        SEQUENCED_ASSEMBLY_EGG_SANDWICH = add(sequencedAssembly("egg_sandwich")
            .require(ForgeTags.BREAD)
            .transitionTo(FDItemEntries.INCOMPLETE_EGG_SANDWICH.get())
            .addOutput(ModItems.EGG_SANDWICH.get(), 1)
            .loops(2)
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ForgeTags.COOKED_EGGS))),
        SEQUENCED_ASSEMBLY_CHICKEN_SANDWICH = add(sequencedAssembly("chicken_sandwich")
            .require(ForgeTags.BREAD)
            .transitionTo(FDItemEntries.INCOMPLETE_CHICKEN_SANDWICH.get())
            .addOutput(ModItems.CHICKEN_SANDWICH.get(), 1)
            .loops(1)
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ForgeTags.COOKED_CHICKEN))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ForgeTags.SALAD_INGREDIENTS))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.CARROT))),
        SEQUENCED_ASSEMBLY_HAMBURGER = add(sequencedAssembly("hamburger")
            .require(ForgeTags.BREAD)
            .transitionTo(FDItemEntries.INCOMPLETE_HAMBURGER.get())
            .addOutput(ModItems.HAMBURGER.get(), 1)
            .loops(1)
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ModItems.BEEF_PATTY.get()))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ForgeTags.SALAD_INGREDIENTS))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ForgeTags.CROPS_TOMATO))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ForgeTags.CROPS_ONION))),
        SEQUENCED_ASSEMBLY_BACON_SANDWICH = add(sequencedAssembly("bacon_sandwich")
            .require(ForgeTags.BREAD)
            .transitionTo(FDItemEntries.INCOMPLETE_BACON_SANDWICH.get())
            .addOutput(ModItems.BACON_SANDWICH.get(), 1)
            .loops(1)
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ForgeTags.COOKED_BACON))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ForgeTags.SALAD_INGREDIENTS))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ForgeTags.CROPS_TOMATO))),
        SEQUENCED_ASSEMBLY_MUTTON_WRAP = add(sequencedAssembly("mutton_wrap")
            .require(ForgeTags.BREAD)
            .transitionTo(FDItemEntries.INCOMPLETE_MUTTON_WRAP.get())
            .addOutput(ModItems.MUTTON_WRAP.get(), 1)
            .loops(1)
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ForgeTags.COOKED_MUTTON))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ForgeTags.SALAD_INGREDIENTS))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ForgeTags.CROPS_ONION))),
        SEQUENCED_ASSEMBLY_APPLE_PIE = add(sequencedAssembly("apple_pie")
            .require(ModItems.PIE_CRUST.get())
            .transitionTo(FDItemEntries.INCOMPLETE_APPLE_PIE.get())
            .addOutput(ModItems.APPLE_PIE.get(), 1)
            .loops(2)
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.APPLE))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ForgeItemTags.FLOUR__WHEAT.tag))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.SUGAR))),
        SEQUENCED_ASSEMBLY_SWEET_BERRY_CHEESE_CAKE = add(sequencedAssembly("sweet_berry_cheesecake")
            .require(ModItems.PIE_CRUST.get())
            .transitionTo(FDItemEntries.INCOMPLETE_SWEET_BERRY_CHEESECAKE.get())
            .addOutput(ModItems.SWEET_BERRY_CHEESECAKE.get(), 1)
            .loops(2)
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.SWEET_BERRIES))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.SWEET_BERRIES))
            .addStep(FillingRecipe::new, builder -> builder.require(Tags.Fluids.MILK, 250))),
        SEQUENCED_ASSEMBLY_PUMPKIN_PIE = add(sequencedAssembly("pumpkin_pie")
            .require(ModItems.PIE_CRUST.get())
            .transitionTo(FDItemEntries.INCOMPLETE_PUMPKIN_PIE.get())
            .addOutput(Items.PUMPKIN_PIE, 1)
            .withCondition(ConfigBoolCondition.PIE_OVERHAUL)
            .withCondition(new ConfigListCondition("pie_overhaul_black_list", "minecraft:pumpkin_pie").blackList())
            .loops(2)
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ModItems.PUMPKIN_SLICE.get()))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ModItems.PUMPKIN_SLICE.get()))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.SUGAR))),
        SEQUENCED_ASSEMBLY_CHERRY_PIE = add(sequencedAssembly("cherry_pie")
            .require(ModItems.PIE_CRUST.get())
            .transitionTo(FDItemEntries.INCOMPLETE_CHERRY_PIE.get())
            .addOutput(EnvironmentalItems.CHERRY_PIE.get(), 1)
            .whenModLoaded(Mods.ENVIRONMENTAL)
            .withCondition(ConfigBoolCondition.PIE_OVERHAUL)
            .withCondition(new ConfigListCondition("pie_overhaul_black_list", "environmental:cherry_pie").blackList())
            .loops(2)
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(EnvironmentalItems.CHERRIES.get()))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(EnvironmentalItems.CHERRIES.get()))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.SUGAR))),
        SEQUENCED_ASSEMBLY_TRUFFLE_PIE = add(sequencedAssembly("truffle_pie")
            .require(ModItems.PIE_CRUST.get())
            .transitionTo(FDItemEntries.INCOMPLETE_TRUFFLE_PIE.get())
            .addOutput(EnvironmentalItems.TRUFFLE_PIE.get(), 1)
            .whenModLoaded(Mods.ENVIRONMENTAL)
            .withCondition(ConfigBoolCondition.PIE_OVERHAUL)
            .withCondition(new ConfigListCondition("pie_overhaul_black_list", "environmental:truffle_pie").blackList())
            .loops(1)
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(EnvironmentalItems.TRUFFLE.get()))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.BROWN_MUSHROOM))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.BROWN_MUSHROOM))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.SUGAR))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.SUGAR))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ForgeTags.EGGS))),
        SEQUENCED_ASSEMBLY_MULBERRY_PIE = add(sequencedAssembly("mulberry_pie")
            .require(ModItems.PIE_CRUST.get())
            .transitionTo(FDItemEntries.INCOMPLETE_MULBERRY_PIE.get())
            .addOutput(UAItems.MULBERRY_PIE.get(), 1)
            .whenModLoaded(Mods.UA)
            .withCondition(ConfigBoolCondition.PIE_OVERHAUL)
            .withCondition(new ConfigListCondition("pie_overhaul_black_list", "upgrade_aquatic:mulberry_pie").blackList())
            .loops(2)
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(UAItems.MULBERRY.get()))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(ForgeItemTags.FLOUR__WHEAT.tag))
            .addStep(DeployerApplicationRecipe::new, builder -> builder.require(Items.SUGAR)));
    
    public FDRecipes(DataGenerator datagen) {
        super(Mods.FD, CentralKitchen.REGISTRATE, datagen);
    }
    
}
