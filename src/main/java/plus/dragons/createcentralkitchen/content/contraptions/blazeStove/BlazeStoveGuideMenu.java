package plus.dragons.createcentralkitchen.content.contraptions.blazeStove;

import com.simibubi.create.foundation.gui.container.GhostItemContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BlazeStoveGuideMenu<G extends BlazeStoveGuide> extends GhostItemContainer<ItemStack> {
    protected G guide;
    @Nullable
    protected BlazeStoveBlockEntity blazeStove;
    protected int inputSize;
    
    public BlazeStoveGuideMenu(MenuType<?> type, int id, Inventory inv, FriendlyByteBuf extraData) {
        super(type, id, inv, extraData);
    }
    
    public BlazeStoveGuideMenu(MenuType<?> type, int id, Inventory inv, ItemStack cookingGuide) {
        super(type, id, inv, cookingGuide);
    }
    
    public BlazeStoveGuideMenu(MenuType<?> type, int id, Inventory inv, BlazeStoveBlockEntity blazeStove) {
        super(type, id, inv, blazeStove.getGuide());
        this.blazeStove = blazeStove;
    }
    
    public abstract G createGuide(ItemStack contentHolder);
    
    public void updateRecipe() {
        guide.updateRecipe(this.player.level);
        this.getSlot(guide.getIngredientSize()).setChanged();
    }
    
    public int getInputSize() {
        return inputSize;
    }
    
    public int getBlazeStatus() {
        return blazeStove == null ? 0 : blazeStove.getBlazeStatusCode();
    }
    
    public ItemStack getContainerItem() {
        return guide.container;
    }
    
    public CompoundTag writeGuideToTag() {
        return guide.serializeNBT();
    }
    
    public void updateGuideFromTag(CompoundTag tag) {
        guide.deserializeNBT(tag);
    }
    
    @Override
    protected void init(Inventory inv, ItemStack contentHolderIn) {
        super.init(inv, contentHolderIn);
        updateRecipe();
    }
    
    @Override
    protected ItemStackHandler createGhostInventory() {
        return guide.inventory;
    }
    
    @Override
    protected void initAndReadInventory(ItemStack contentHolder) {
        this.guide = createGuide(contentHolder);
        this.inputSize = guide.inventory.getSlots() - 1;
        super.initAndReadInventory(contentHolder);
    }
    
    @Override
    protected ItemStack createOnClient(FriendlyByteBuf extraData) {
        ItemStack item = extraData.readItem();
        boolean fromItemStack = extraData.readBoolean();
        if (!fromItemStack) {
            BlockPos pos = extraData.readBlockPos();
            ClientLevel level = Minecraft.getInstance().level;
            assert level != null;
            if (level.getBlockEntity(pos) instanceof BlazeStoveBlockEntity blazeStove)
                this.blazeStove = blazeStove;
            else throw new RuntimeException("Expected Blaze Stove at " + pos + " but found none");
        }
        return item;
    }
    
    @Override
    protected void saveData(ItemStack contentHolder) {
    
    }
    
    @Override
    public boolean stillValid(Player player) {
        return super.stillValid(player) && (blazeStove == null || blazeStove.stillValid(player));
    }
    
    @Override
    public void removed(Player playerIn) {
        super.removed(playerIn);
        if (blazeStove != null) {
            blazeStove.notifyUpdate();
        } else {
            playerIn.getCooldowns().addCooldown(guide.getOwner().getItem(), 5);
        }
    }
    
    @Override
    protected boolean allowRepeats() {
        return true;
    }
    
    @Override
    public void clicked(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (slotId == 36 + inputSize)
            return;
        if (slotId < 36) {
            super.clicked(slotId, dragType, clickTypeIn, player);
            return;
        }
        if (clickTypeIn == ClickType.THROW)
            return;
        
        ItemStack held = getCarried().copy();
        held.setCount(1);
        if (clickTypeIn == ClickType.CLONE) {
            if (player.isCreative() && held.isEmpty()) {
                ItemStack cloned = ghostInventory.getStackInSlot(slotId - 36).copy();
                cloned.setCount(cloned.getMaxStackSize());
                setCarried(cloned);
            }
        } else if (getSlot(slotId).mayPlace(held) || held.isEmpty()) {
            ghostInventory.setStackInSlot(slotId - 36, held.copy());
            getSlot(slotId).setChanged();
        }
    }
    
    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        if (index < 36) {
            ItemStack stackToInsert = playerInventory.getItem(index).copy();
            stackToInsert.setCount(1);
            for (int i = 36; i < 36 + inputSize; i++) {
                if (getSlot(i).mayPlace(stackToInsert)) {
                    ghostInventory.insertItem(i - 36, stackToInsert, false);
                    getSlot(i).setChanged();
                    break;
                }
            }
        } else if (index < 36 + inputSize) {
            ghostInventory.extractItem(0, 1, false);
            getSlot(index).setChanged();
        }
        return ItemStack.EMPTY;
    }
    
    protected class CookingIngredientSlot extends SlotItemHandler {
        public CookingIngredientSlot(int index, int xPosition, int yPosition) {
            super(ghostInventory, index, xPosition, yPosition);
        }
        
        @Override
        public int getMaxStackSize() {
            return super.getMaxStackSize();
        }
        
        @Override
        public void setChanged() {
            super.setChanged();
            updateRecipe();
        }
    }
    
    protected class DisplaySlot extends SlotItemHandler {
        public DisplaySlot(int index, int xPosition, int yPosition) {
            super(ghostInventory, index, xPosition, yPosition);
        }
    
//        @Override
//        public boolean isActive() {
//            return false;
//        }
    
        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            return false;
        }
        
        @Override
        public boolean mayPickup(Player playerIn) {
            return false;
        }
    }
    
}
