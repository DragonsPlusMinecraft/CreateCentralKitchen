package plus.dragons.createcentralkitchen.content.contraptions.blazeStove;

import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.block.render.SpriteShiftEntry;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.tileEntity.renderer.SafeTileEntityRenderer;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.items.ItemStackHandler;
import plus.dragons.createcentralkitchen.entry.CentralKitchenBlockPartials;
import vectorwing.farmersdelight.common.block.StoveBlock;

public class BlazeStoveRenderer extends SafeTileEntityRenderer<BlazeStoveBlockEntity> {
    
    public BlazeStoveRenderer(BlockEntityRendererProvider.Context context) {}
    
    @Override
    protected void renderSafe(BlazeStoveBlockEntity stove, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        renderItem(stove, poseStack, buffer);
        BlazeBurnerBlock.HeatLevel heatLevel = stove.getHeatLevelFromBlock();
        if (heatLevel == BlazeBurnerBlock.HeatLevel.NONE) return;
        renderBlaze(stove, partialTicks, poseStack, buffer);
    }
    
    protected static void renderItem(BlazeStoveBlockEntity stove, PoseStack poseStack, MultiBufferSource buffer) {
        ItemStackHandler inventory = stove.getInventory();
        Direction direction = stove.getBlockState().getValue(StoveBlock.FACING).getOpposite();
        int directionIndex = direction.get2DDataValue();
        Vec2[] offsets = directionIndex % 2 == 0
            ? BlazeStoveBlockEntity.ITEM_OFFSET_NS
            : BlazeStoveBlockEntity.ITEM_OFFSET_WE;
        int seed = (int)stove.getBlockPos().asLong();
        poseStack.pushPose();
        poseStack.translate(0.5D, 1.0125D, 0.5D);
        for (int slot = 0; slot < inventory.getSlots(); ++slot) {
            ItemStack stack = inventory.getStackInSlot(slot);
            if (!stack.isEmpty()) {
                poseStack.pushPose();
                float directionRot = -direction.toYRot();
                poseStack.mulPose(Vector3f.YP.rotationDegrees(directionRot));
                poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                poseStack.translate(offsets[slot].x, offsets[slot].y, 0.0D);
                poseStack.scale(0.3125F, 0.3125F, 0.3125F);
                Minecraft.getInstance().getItemRenderer().renderStatic(
                    stack, ItemTransforms.TransformType.FIXED,
                    LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,
                    poseStack, buffer, seed + slot
                );
                poseStack.popPose();
            }
        }
        poseStack.popPose();
    }
    
    protected static void renderBlaze(BlazeStoveBlockEntity stove, float partialTicks, PoseStack poseStack, MultiBufferSource buffer) {
        Level level = stove.getLevel();
        BlockState blockState = stove.getBlockState();
        BlazeBurnerBlock.HeatLevel heatLevel = BlazeBurnerBlock.getHeatLevelOf(blockState);
        int hashCode = stove.hashCode();
        float horizontalAngle = AngleHelper.rad(stove.getHeadAngle().getValue(partialTicks));
        float animation = stove.getHeadAnimation().getValue(partialTicks) * .175f;
        float time = AnimationTickHolder.getRenderTime(level);
        float renderTick = time + (hashCode % 13) * 16f;
        float offsetMult = heatLevel.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING) ? 64 : 16;
        float offset = Mth.sin((float) ((renderTick / 16f) % (2 * Math.PI))) / offsetMult;
        float offset1 = Mth.sin((float) ((renderTick / 16f + Math.PI) % (2 * Math.PI))) / offsetMult;
        float offset2 = Mth.sin((float) ((renderTick / 16f + Math.PI / 2) % (2 * Math.PI))) / offsetMult;
        boolean blockAbove = animation > 0.125f;
    
        VertexConsumer solid = buffer.getBuffer(RenderType.solid());
        VertexConsumer cutout = buffer.getBuffer(RenderType.cutoutMipped());
    
        poseStack.pushPose();
    
        //flame
        if (heatLevel.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING) && blockAbove) {
            SpriteShiftEntry spriteShift = heatLevel == BlazeBurnerBlock.HeatLevel.SEETHING
                ? AllSpriteShifts.SUPER_BURNER_FLAME
                : AllSpriteShifts.BURNER_FLAME;
        
            float spriteWidth = spriteShift.getTarget().getU1() - spriteShift.getTarget().getU0();
            float spriteHeight = spriteShift.getTarget().getV1() - spriteShift.getTarget().getV0();
            float speed = 1 / 32f + 1 / 64f * heatLevel.ordinal();
        
            double vScroll = speed * time;
            vScroll = vScroll - Math.floor(vScroll);
            vScroll = vScroll * spriteHeight / 2;
        
            double uScroll = speed * time / 2;
            uScroll = uScroll - Math.floor(uScroll);
            uScroll = uScroll * spriteWidth / 2;
        
            draw(CachedBufferer
                    .partial(AllBlockPartials.BLAZE_BURNER_FLAME, blockState)
                    .shiftUVScrolling(spriteShift, (float) uScroll, (float) vScroll), horizontalAngle, poseStack, cutout);
        }
    
        //blaze
        PartialModel blazeModel = AllBlockPartials.BLAZE_INERT;
        if (heatLevel.isAtLeast(BlazeBurnerBlock.HeatLevel.SEETHING)) {
            blazeModel = blockAbove ?
                AllBlockPartials.BLAZE_SUPER_ACTIVE :
                AllBlockPartials.BLAZE_SUPER;
        } else if (heatLevel.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING)) {
            blazeModel = blockAbove && heatLevel.isAtLeast(BlazeBurnerBlock.HeatLevel.KINDLED)
                ? AllBlockPartials.BLAZE_ACTIVE
                : AllBlockPartials.BLAZE_IDLE;
        }
        
        float headY = offset - (animation * .75f);
    
        draw(CachedBufferer.partial(blazeModel, blockState)
            .translate(0, headY, 0), horizontalAngle, poseStack, solid);
    
        //hat
        SuperByteBuffer partial = CachedBufferer
            .partial(CentralKitchenBlockPartials.BLAZE_STOVE_HAT, blockState)
            .translate(0, headY, 0);
        if (blazeModel == AllBlockPartials.BLAZE_INERT) {
            partial.translateY(0.5f)
                .centre()
                .scale(0.75f)
                .unCentre();
        } else {
            //chef hat is slightly bigger than the train hat, so we have to move down half a pixel
            partial.translateY(0.71875f);
        }
        partial
            .rotateCentered(Direction.UP, horizontalAngle + Mth.PI)
            .translate(0.5f, 0, 0.5f)
            .light(LightTexture.FULL_BRIGHT)
            .renderInto(poseStack, solid);
        
        //rods
        if (heatLevel.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING)) {
            PartialModel rods = heatLevel == BlazeBurnerBlock.HeatLevel.SEETHING ? AllBlockPartials.BLAZE_BURNER_SUPER_RODS
                : AllBlockPartials.BLAZE_BURNER_RODS;
            PartialModel rods2 = heatLevel == BlazeBurnerBlock.HeatLevel.SEETHING ? AllBlockPartials.BLAZE_BURNER_SUPER_RODS_2
                : AllBlockPartials.BLAZE_BURNER_RODS_2;
            draw(CachedBufferer.partial(rods, blockState)
                .translate(0, offset1 + animation + .125f, 0), 0, poseStack, solid);
            draw(CachedBufferer.partial(rods2, blockState)
                .translate(0, offset2 + animation - 3 / 16f, 0), 0, poseStack, solid);
        }
    
        poseStack.popPose();
    }
    
    private static void draw(SuperByteBuffer blazeBuffer, float horizontalAngle,
                             PoseStack ms, VertexConsumer vb) {
        blazeBuffer
            .rotateCentered(Direction.UP, horizontalAngle)
            .light(LightTexture.FULL_BRIGHT)
            .renderInto(ms, vb);
    }
    
}
