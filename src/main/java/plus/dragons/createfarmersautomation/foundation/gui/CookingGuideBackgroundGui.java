package plus.dragons.createfarmersautomation.foundation.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import plus.dragons.createfarmersautomation.FarmersAutomation;

public class CookingGuideBackgroundGui {
    public static final ResourceLocation TEXTURE = new ResourceLocation(FarmersAutomation.ID, "textures/gui/cooking_guide.png");
    public static final int WIDTH = 180, HEIGHT = 75;
    private final int status;

    public CookingGuideBackgroundGui(int status) {
        this.status = status;
    }

    
    @OnlyIn(Dist.CLIENT)
    public void bind() {
        RenderSystem.setShaderTexture(0, TEXTURE);
    }

    
    @OnlyIn(Dist.CLIENT)
    public void render(PoseStack ms, int x, int y, GuiComponent component) {
        bind();
        component.blit(ms, x, y, 0, 0, WIDTH, HEIGHT);
        var level = Minecraft.getInstance().level;
        if(level==null || status<1){
            component.blit(ms, x + 96, y + 25, 2, 85, 23, 21);
        } else {
            int time = (int) (level.getGameTime() / 5 % 3);
            component.blit(ms, x + 96, y + 25, 26 + 24 * time, 87, 21, 19);
        }
        component.blit(ms, x + 85, y + 59, 98, 105 - status * 6, 74, 6);

    }
    
}
