package plus.dragons.createfarmersautomation.content.contraptions.components.cooking;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.gui.container.AbstractSimiContainerScreen;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import plus.dragons.createfarmersautomation.entry.CfaPackets;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.simibubi.create.foundation.gui.AllGuiTextures.PLAYER_INVENTORY;
import static plus.dragons.createfarmersautomation.foundation.gui.CfaGuiTextures.COOKING_GUIDE;

public class CookingGuideScreen extends AbstractSimiContainerScreen<CookingGuideMenu> {
    private static final int ENCHANTING_GUIDE_WIDTH = 178;
    private List<Rect2i> extraAreas = Collections.emptyList();
    private boolean directItemStackEdit;
    @Nullable
    private BlockPos blockPos;

    public CookingGuideScreen(CookingGuideMenu container, Inventory inv, Component title) {
        super(container, inv, title);
        directItemStackEdit = container.directItemStackEdit;
        blockPos = container.blockPos;
    }


    @Override
    protected void init() {
        setWindowSize(
                COOKING_GUIDE.width,
                COOKING_GUIDE.height + 4 + PLAYER_INVENTORY.height
        );
        setWindowOffset(-32, 0);
        super.init();
        int guideX = getLeftOfCentered(ENCHANTING_GUIDE_WIDTH);
        int guideY = topPos;
        extraAreas = ImmutableList.of(
            new Rect2i(guideX + COOKING_GUIDE.width, guideY + COOKING_GUIDE.height - 48, 48, 48),
            new Rect2i(guideX, guideY, imageWidth, imageHeight)
        );
    }

    @Override
    protected void renderBg(PoseStack ms, float partialTicks, int mouseX, int mouseY) {
        int invX = getLeftOfCentered(PLAYER_INVENTORY.width);
        int invY = topPos + COOKING_GUIDE.height + 4;
        renderPlayerInventory(ms, invX, invY);

        int guideX = getLeftOfCentered(ENCHANTING_GUIDE_WIDTH);
        int guideY = topPos;

        COOKING_GUIDE.render(ms, guideX, guideY, this);
        drawCenteredString(ms, font, title, guideX + ENCHANTING_GUIDE_WIDTH / 2, guideY + 3, 0xFFFFFF);

        GuiGameElement.of(menu.contentHolder)
            .<GuiGameElement.GuiRenderBuilder>at(
                guideX + COOKING_GUIDE.width,
                guideY + COOKING_GUIDE.height - 48,
                -200
            )
            .scale(3)
            .render(ms);
    }

    @Override
    public void removed() {
        super.removed();
        var put = new ArrayList<ItemStack>();
        for(int i=0;i<6;i++){
            put.add(getMenu().ghostInventory.getStackInSlot(i));
        }
        CookingGuideItem.saveContent(put,getMenu().contentHolder);
        if(directItemStackEdit)
            CfaPackets.channel.sendToServer(new CookingGuideEditPacket(getMenu().contentHolder));
        else
            CfaPackets.channel.sendToServer(new BlazeStoveEditPacket(getMenu().contentHolder, blockPos));
    }

    @Override
    public List<Rect2i> getExtraAreas() {
        return extraAreas;
    }

}
