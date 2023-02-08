package plus.dragons.createcentralkitchen.mixin.client.create;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.contraptions.components.deployer.DeployerRenderer;
import com.simibubi.create.content.contraptions.components.deployer.DeployerTileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import plus.dragons.createcentralkitchen.mixin.common.create.DeployerTileEntityAccessor;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FdTags;

@Mixin(value = DeployerRenderer.class, remap = false)
public class DeployerRendererMixin {
    
    @ModifyVariable(
        method = "renderItem",
        at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/AngleHelper;horizontalAngle(Lnet/minecraft/core/Direction;)F"),
        ordinal = 0,
        index = 10,
        name = "punching",
        remap = false
    )
    private boolean createsdelight$uprightOnDeployer(boolean original, DeployerTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        return original || ((DeployerTileEntityAccessor) te).getHeldItem().is(FdTags.UPRIGHT_ON_DEPLOYER);
    }

}