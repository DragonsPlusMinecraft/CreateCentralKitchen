package plus.dragons.createcentralkitchen.content.logistics.item.guide.cooking;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import plus.dragons.createcentralkitchen.CentralKitchen;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.simibubi.create.foundation.gui.AllGuiTextures.PLAYER_INVENTORY;

public class CookingGuideScreen extends AbstractSimiContainerScreen<CookingGuideMenu> {
    private static final ResourceLocation TEXTURE = CentralKitchen.genRL("textures/gui/cooking_guide.png");
    private static final int WINDOW_WIDTH = 176;
    private static final int BACKGROUND_WIDTH = 184;
    private static final int BACKGROUND_HEIGHT = 80;
    private static final int ICON_SIZE = 48;
    private List<Rect2i> extraAreas = Collections.emptyList();

    public CookingGuideScreen(CookingGuideMenu container, Inventory inv, Component title) {
        super(container, inv, title);
    }
    
    @Override
    protected void init() {
        setWindowSize(BACKGROUND_WIDTH, BACKGROUND_HEIGHT + 4 + PLAYER_INVENTORY.height);
        setWindowOffset((WINDOW_WIDTH - 256) / 2, 0);
        super.init();
        int guideX = getLeftOfCentered(WINDOW_WIDTH);
        int guideY = topPos;
        extraAreas = ImmutableList.of(
            new Rect2i(guideX + WINDOW_WIDTH + 16, guideY + 16, ICON_SIZE, ICON_SIZE),
            new Rect2i(guideX, guideY, imageWidth, imageHeight)
        );
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        var pose = pGuiGraphics.pose();
        //Player Inventory
        int invX = getLeftOfCentered(PLAYER_INVENTORY.width);
        int invY = topPos + BACKGROUND_HEIGHT + 4;
        renderPlayerInventory(pGuiGraphics, invX, invY);
        //Guide
        int guideX = getLeftOfCentered(WINDOW_WIDTH);
        int guideY = topPos;
        renderGuide(pGuiGraphics, guideX, guideY);
        //Title
        pGuiGraphics.drawCenteredString(font, title, guideX + WINDOW_WIDTH / 2, guideY + 5, 0xFFFFFF);
        //Guide Icon
        GuiGameElement.of(menu.contentHolder)
                .<GuiGameElement.GuiRenderBuilder>at(guideX + WINDOW_WIDTH + 16, guideY + 16, -200)
                .scale(3)
                .render(pGuiGraphics);
    }
    
    private void renderGuide(@NotNull GuiGraphics pGuiGraphics, int x, int y) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
        var level = Minecraft.getInstance().level;
        int status = this.getMenu().getBlazeStatus();
        if (level == null || status <= 0) {
            pGuiGraphics.blit(TEXTURE,x + 88, y + 24, 0, 80, 24, 24);
        } else {
            int time = ((int) level.getGameTime() / 5 % 3) + 1;
            pGuiGraphics.blit(TEXTURE,x + 88, y + 24, 24 * time, 80, 24, 24);
        }
        pGuiGraphics.blit(TEXTURE,x + 80, y + 66, 96, 80 + status * 6, 80, 6);
    }
    
    @Override
    protected void renderTooltip(@NotNull GuiGraphics pGuiGraphics, int x, int y) {
        if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
            var pose = pGuiGraphics.pose();
            if (this.hoveredSlot.index == 6) {
                List<Component> tooltip = new ArrayList<>();
                ItemStack mealStack = this.hoveredSlot.getItem();
                tooltip.add(((MutableComponent)mealStack.getItem().getDescription())
                    .withStyle(mealStack.getRarity().getStyleModifier()));
                ItemStack containerStack = this.getMenu().getContainerItem();
                String container = !containerStack.isEmpty() ? containerStack.getItem().getDescription().getString() : "";
                tooltip.add(TextUtils.getTranslation("container.cooking_pot.served_on", container)
                    .withStyle(ChatFormatting.GRAY));
                pGuiGraphics.renderComponentTooltip(font, tooltip, x, y);
            } else {
                pGuiGraphics.renderTooltip(font, this.hoveredSlot.getItem(), x, y);
            }
        }
    }
    
    @Override
    public List<Rect2i> getExtraAreas() {
        return extraAreas;
    }
    
}
