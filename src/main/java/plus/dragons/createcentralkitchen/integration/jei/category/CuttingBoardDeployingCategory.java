package plus.dragons.createcentralkitchen.integration.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedDeployer;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.ponder.ui.LayoutHelper;
import com.simibubi.create.foundation.utility.Lang;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.ChatFormatting;
import plus.dragons.createcentralkitchen.content.contraptions.deployer.CuttingBoardDeployingRecipe;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class CuttingBoardDeployingCategory extends CreateRecipeCategory<CuttingBoardDeployingRecipe> {
    
    private final AnimatedDeployer deployer = new AnimatedDeployer();
    
    public CuttingBoardDeployingCategory(Info<CuttingBoardDeployingRecipe> info) {
        super(info);
    }
    
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CuttingBoardDeployingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 51)
            .setBackground(getRenderedSlot(), -1, -1)
            .addIngredients(recipe.getIngredients().get(0));
        
        builder.addSlot(RecipeIngredientRole.INPUT, 51, 5)
            .setBackground(getRenderedSlot(), -1, -1)
            .addIngredients(recipe.getIngredients().get(1))
            .addTooltipCallback((view, tooltip) ->
                tooltip.add(1, Lang
                    .translate("recipe.deploying.not_consumed")
                    .component()
                    .withStyle(ChatFormatting.GOLD)
                )
            );
    
        layoutOutput(recipe).forEach(layoutEntry -> builder
            .addSlot(RecipeIngredientRole.OUTPUT, 139 + layoutEntry.posX() + 1, 54 + layoutEntry.posY() + 1)
            .setBackground(getRenderedSlot(layoutEntry.output()), -1, -1)
            .addItemStack(layoutEntry.output().getStack())
            .addTooltipCallback(addStochasticTooltip(layoutEntry.output()))
        );
    }
    
    @Override
    public void draw(CuttingBoardDeployingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
        AllGuiTextures.JEI_SHADOW.render(matrixStack, 62, 57);
        AllGuiTextures.JEI_DOWN_ARROW.render(matrixStack, 126, 29);
        deployer.draw(matrixStack, getBackground().getWidth() / 2 - 13, 22);
    }
    
    private List<LayoutEntry> layoutOutput(ProcessingRecipe<?> recipe) {
        int size = recipe.getRollableResults().size();
        List<LayoutEntry> positions = new ArrayList<>(size);
        
        LayoutHelper layout = LayoutHelper.centeredHorizontal(size, 1, 18, 18, 1);
        for (ProcessingOutput result : recipe.getRollableResults()) {
            positions.add(new LayoutEntry(result, layout.getX(), layout.getY()));
            layout.next();
        }
        
        return positions;
    }
    
    record LayoutEntry(ProcessingOutput output, int posX, int posY) {}
    
}