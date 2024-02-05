package plus.dragons.createcentralkitchen.content.logistics.block.basket;

import com.sammy.minersdelight.content.block.sticky_basket.StickyBasketBlockEntity;
import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import plus.dragons.createcentralkitchen.api.block.entity.DelegatingSmartTileEntity;
import plus.dragons.createcentralkitchen.foundation.mixin.common.minersdelight.StickyBasketBlockEntityAccessor;
import vectorwing.farmersdelight.common.block.BasketBlock;

import java.util.List;

public class SmartStickyBasketBlockEntity extends DelegatingSmartTileEntity<StickyBasketBlockEntity> {

    public SmartStickyBasketBlockEntity(StickyBasketBlockEntity basketBlockEntity) {
        super(basketBlockEntity);
    }
    
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(new DirectBeltInputBehaviour(this)
            .onlyInsertWhen(this::canInsert)
            .setInsertionHandler(this::tryInsertingFromSide)
        );
    }
    
    private boolean canInsert(Direction direction) {
        Direction facing = this.getBlockState().getValue(BasketBlock.FACING);
        boolean match = switch (facing) {
            case UP -> direction != Direction.UP;
            case DOWN -> direction == Direction.UP;
            default -> direction == facing.getOpposite();
        };
        return match && !((StickyBasketBlockEntityAccessor)this.blockEntity).invokeIsOnTransferCooldown();
    }
    
    private ItemStack tryInsertingFromSide(TransportedItemStack inserted, Direction side, boolean simulate) {
        LazyOptional<IItemHandler> lazy = this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, side);
        if (!lazy.isPresent())
            return inserted.stack;
        ItemStack ret = ItemHandlerHelper.insertItemStacked(lazy.orElse(null), inserted.stack.copy(), simulate);
        if (!simulate && !ret.equals(inserted.stack, false)) {
            this.blockEntity.setTransferCooldown(8);
            this.blockEntity.setChanged();
        }
        return ret;
    }
    
}
