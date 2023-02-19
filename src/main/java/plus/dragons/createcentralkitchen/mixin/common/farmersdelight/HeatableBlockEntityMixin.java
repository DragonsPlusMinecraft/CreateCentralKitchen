package plus.dragons.createcentralkitchen.mixin.common.farmersdelight;

import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.*;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.tag.ModTags;

@Pseudo
@Mixin(
    value = {
        CookingPotBlockEntity.class,
        SkilletBlockEntity.class,
    },
    targets = {
        "umpaz.farmersrespite.common.block.entity.KettleBlockEntity",
        "com.sammy.minersdelight.content.block.copper_pot.CopperPotBlockEntity"
    }
)
@Implements(@Interface(iface = HeatableBlockEntity.class, prefix = "override$", remap = Interface.Remap.NONE))
public abstract class HeatableBlockEntityMixin extends SyncedBlockEntity implements HeatableBlockEntity {
    
    private HeatableBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    
    @Intrinsic
    public boolean override$isHeated(Level level, BlockPos pos) {
        BlockState stateBelow = level.getBlockState(pos.below());
        if (stateBelow.is(ModTags.HEAT_SOURCES)) {
            if (stateBelow.hasProperty(BlockStateProperties.LIT))
                return stateBelow.getValue(BlockStateProperties.LIT);
            if (stateBelow.hasProperty(BlazeBurnerBlock.HEAT_LEVEL))
                return stateBelow.getValue(BlazeBurnerBlock.HEAT_LEVEL).isAtLeast(BlazeBurnerBlock.HeatLevel.SMOULDERING);
            return true;
        } else {
            if (!this.requiresDirectHeat() && stateBelow.is(ModTags.HEAT_CONDUCTORS)) {
                BlockState stateFurtherBelow = level.getBlockState(pos.below(2));
                if (stateFurtherBelow.is(ModTags.HEAT_SOURCES)) {
                    if (stateBelow.hasProperty(BlockStateProperties.LIT))
                        return stateBelow.getValue(BlockStateProperties.LIT);
                    if (stateBelow.hasProperty(BlazeBurnerBlock.HEAT_LEVEL))
                        return stateBelow.getValue(BlazeBurnerBlock.HEAT_LEVEL).isAtLeast(BlazeBurnerBlock.HeatLevel.SMOULDERING);
                    return true;
                }
            }
            return false;
        }
    }
    
}
