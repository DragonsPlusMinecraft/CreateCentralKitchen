package plus.dragons.createcentralkitchen.content.logistics.block.mechanicalArm;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import net.createmod.catnip.platform.ForgeRegisteredObjectsHelper;
import net.createmod.ponder.api.registration.MultiTagBuilder;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.foundation.ponder.PonderArmInteractionPointType;
import vectorwing.farmersdelight.common.block.BasketBlock;
import vectorwing.farmersdelight.common.block.entity.BasketBlockEntity;

public class BasketPoint extends ArmInteractionPoint {
    public BasketPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Override
    protected Vec3 getInteractionPositionVector() {
        return Vec3.atCenterOf(pos);
    }

    public static class Type extends PonderArmInteractionPointType {

        public Type(ResourceLocation id) {
            super(id);
        }

        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof BasketBlockEntity;
        }

        @Nullable
        @Override
        public BasketPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new BasketPoint(this, level, pos, state);
        }
    
        @Override
        public void addToPonderTag(PonderTagRegistrationHelper<ResourceLocation> helper) {
            ForgeRegisteredObjectsHelper forgeRegisteredObjectsHelper = new ForgeRegisteredObjectsHelper();
            MultiTagBuilder.Tag<Item> builder = helper.withKeyFunction((Item s) -> forgeRegisteredObjectsHelper.getKeyOrThrow(s)).addToTag(AllCreatePonderTags.ARM_TARGETS);
            //var builder = PonderRegistry.TAGS.forTag(AllCreatePonderTags.ARM_TARGETS);

            ForgeRegistries.ITEMS
                .getValues()
                .stream()
                .filter(item -> item instanceof BlockItem blockItem && blockItem.getBlock() instanceof BasketBlock)
                .forEach(builder::add);
        }
        
    }
}
