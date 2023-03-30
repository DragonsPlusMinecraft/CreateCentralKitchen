package plus.dragons.createcentralkitchen.foundation.mixin.client.create;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.contraptions.components.deployer.DeployerRenderer;
import com.simibubi.create.content.contraptions.components.deployer.DeployerTileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import plus.dragons.createcentralkitchen.foundation.data.tag.IntegrationItemTags;
import plus.dragons.createcentralkitchen.foundation.mixin.common.create.DeployerTileEntityAccessor;

@Mixin(value = DeployerRenderer.class, remap = false)
public class DeployerRendererMixin {
    
    @ModifyVariable(
        method = "renderItem",
        at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/AngleHelper;horizontalAngle(Lnet/minecraft/core/Direction;)F"),
        ordinal = 0,
        index = 10,
        name = "punching"
    )
    private boolean cck$isUprightOnDeployer(boolean original, DeployerTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        return original || ((DeployerTileEntityAccessor) te).getHeldItem().is(IntegrationItemTags.UPRIGHT_ON_DEPLOYER.tag);
    }

}