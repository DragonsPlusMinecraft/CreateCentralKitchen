package plus.dragons.createcentralkitchen.content.contraptions.components.stove;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.gui.container.AbstractSimiContainerScreen;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import plus.dragons.createcentralkitchen.entry.CckPackets;
import plus.dragons.createcentralkitchen.foundation.gui.CookingGuideBackgroundGui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.simibubi.create.foundation.gui.AllGuiTextures.PLAYER_INVENTORY;

public class CookingGuideScreen extends AbstractSimiContainerScreen<CookingGuideMenu> {
    private static final int COOKING_GUIDE_WIDTH = 172;
    private List<Rect2i> extraAreas = Collections.emptyList();
    private final CookingGuideBackgroundGui backgroundGui;

    public CookingGuideScreen(CookingGuideMenu container, Inventory inv, Component title) {
        super(container, inv, title);
        backgroundGui = new CookingGuideBackgroundGui(container.getBlazeStatus());
    }

    @Override
    protected void init() {
        setWindowSize(
                backgroundGui.WIDTH,
                backgroundGui.HEIGHT + 4 + PLAYER_INVENTORY.height
        );
        setWindowOffset(-32, 0);
        super.init();
        int guideX = getLeftOfCentered(COOKING_GUIDE_WIDTH);
        int guideY = topPos;
        extraAreas = ImmutableList.of(
            new Rect2i(guideX + backgroundGui.WIDTH, guideY + backgroundGui.HEIGHT - 60, 48, 48),
            new Rect2i(guideX, guideY, imageWidth, imageHeight)
        );
    }

    @Override
    protected void renderBg(PoseStack ms, float partialTicks, int mouseX, int mouseY) {
        int invX = getLeftOfCentered(PLAYER_INVENTORY.width);
        int invY = topPos + backgroundGui.HEIGHT + 4;
        renderPlayerInventory(ms, invX, invY);

        int guideX = getLeftOfCentered(COOKING_GUIDE_WIDTH);
        int guideY = topPos;

        backgroundGui.render(ms, guideX, guideY,this);
        drawCenteredString(ms, font, title, guideX + COOKING_GUIDE_WIDTH / 2, guideY + 5, 0xFFFFFF);

        GuiGameElement.of(menu.contentHolder)
            .<GuiGameElement.GuiRenderBuilder>at(
                guideX + backgroundGui.WIDTH,
                guideY + backgroundGui.HEIGHT - 60,
                -200
            )
            .scale(3)
            .render(ms);
    }

    @Override
    public List<Rect2i> getExtraAreas() {
        return extraAreas;
    }

}
