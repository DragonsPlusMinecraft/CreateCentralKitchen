package plus.dragons.createcentralkitchen.content.logistics.item.guide.brewing;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import plus.dragons.createcentralkitchen.CentralKitchen;
import umpaz.farmersrespite.common.utility.FRTextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.simibubi.create.foundation.gui.AllGuiTextures.PLAYER_INVENTORY;

public class BrewingGuideScreen extends AbstractSimiContainerScreen<BrewingGuideMenu> {
    private static final ResourceLocation TEXTURE = CentralKitchen.genRL("textures/gui/brewing_guide.png");
    private static final int WINDOW_WIDTH = 144;
    private static final int BACKGROUND_WIDTH = 152;
    private static final int BACKGROUND_HEIGHT = 80;
    private static final int ICON_SIZE = 48;
    private List<Rect2i> extraAreas = Collections.emptyList();
    
    public BrewingGuideScreen(BrewingGuideMenu container, Inventory inv, Component title) {
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
    protected void renderBg(@NotNull PoseStack pose, float partialTicks, int mouseX, int mouseY) {
        //Player Inventory
        int invX = getLeftOfCentered(PLAYER_INVENTORY.width);
        int invY = topPos + BACKGROUND_HEIGHT + 4;
        renderPlayerInventory(pose, invX, invY);
        //Guide
        int guideX = getLeftOfCentered(WINDOW_WIDTH);
        int guideY = topPos;
        renderGuide(pose, guideX, guideY);
        //Title
        drawCenteredString(pose, font, title, guideX + WINDOW_WIDTH / 2, guideY + 5, 0xFFFFFF);
        //Guide Icon
        GuiGameElement.of(menu.contentHolder)
            .<GuiGameElement.GuiRenderBuilder>at(guideX + WINDOW_WIDTH + 16, guideY + 16, -200)
            .scale(3)
            .render(pose);
    }
    
    private void renderGuide(@NotNull PoseStack pose, int x, int y) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.blit(pose, x, y, 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
        var level = Minecraft.getInstance().level;
        boolean needWater = this.getMenu().needWater();
        int status = this.getMenu().getBlazeStatus();
        if (level == null || status <= 0) {
            this.blit(pose, x + 56, y + 24, 0, 80, 24, 24);
        } else {
            int time = ((int) level.getGameTime() / 5 % 3) + 1;
            this.blit(pose, x + 56, y + 24, 24 * time, 80, 24, 24);
        }
        if (needWater)
            this.blit(pose, x + 11, y + 32, 176, 80, 5, 32);
        this.blit(pose, x + 48, y + 66, 96, 80 + status * 6, 80, 6);
    }
    
    @Override
    protected void renderTooltip(@NotNull PoseStack pose, int x, int y) {
        if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
            if (this.hoveredSlot.index == 2) {
                List<Component> tooltip = new ArrayList<>();
                ItemStack mealStack = this.hoveredSlot.getItem();
                tooltip.add(((MutableComponent)mealStack.getItem().getDescription())
                    .withStyle(mealStack.getRarity().getStyleModifier()));
                ItemStack containerStack = this.menu.getContainerItem();
                String container = !containerStack.isEmpty() ? containerStack.getItem().getDescription().getString() : "";
                tooltip.add(FRTextUtils.getTranslation("container.kettle.served_on", container)
                    .withStyle(ChatFormatting.GRAY));
                this.renderComponentTooltip(pose, tooltip, x, y);
            } else {
                this.renderTooltip(pose, this.hoveredSlot.getItem(), x, y);
            }
        }
    }
    
    @Override
    public List<Rect2i> getExtraAreas() {
        return extraAreas;
    }
    
}
