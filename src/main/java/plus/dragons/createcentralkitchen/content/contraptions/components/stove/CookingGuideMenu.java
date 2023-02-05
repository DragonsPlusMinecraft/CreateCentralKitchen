package plus.dragons.createcentralkitchen.content.contraptions.components.stove;

import com.simibubi.create.foundation.gui.container.GhostItemContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.entry.CckPackets;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

public class CookingGuideMenu extends GhostItemContainer<ItemStack> {
    private CookingGuide cookingGuide;
    @Nullable
    private BlazeStoveBlockEntity blazeStove;

    public CookingGuideMenu(MenuType<?> type, int id, Inventory inv, FriendlyByteBuf extraData) {
        super(type, id, inv, extraData);
    }

    public CookingGuideMenu(MenuType<?> type, int id, Inventory inv, ItemStack cookingGuide) {
        super(type, id, inv, cookingGuide);
    }
    
    public CookingGuideMenu(MenuType<?> type, int id, Inventory inv, BlazeStoveBlockEntity blazeStove) {
        super(type, id, inv, blazeStove.getCookingGuide());
        this.blazeStove = blazeStove;
    }
    
    public void updateRecipe() {
        cookingGuide.updateRecipe(this.player.level);
        this.getSlot(6).setChanged();
    }
    
    public int getBlazeStatus() {
        return blazeStove == null ? 0 : blazeStove.getBlazeStatusCode();
    }
    
    @Override
    protected void init(Inventory inv, ItemStack contentHolderIn) {
        super.init(inv, contentHolderIn);
        updateRecipe();
    }
    
    @Override
    protected ItemStackHandler createGhostInventory() {
        return cookingGuide.inventory;
    }

    @Override
    protected void initAndReadInventory(ItemStack contentHolder) {
        this.cookingGuide = CookingGuide.of(contentHolder);
        super.initAndReadInventory(contentHolder);
    }

    @Override
    protected ItemStack createOnClient(FriendlyByteBuf extraData) {
        ItemStack item = extraData.readItem();
        boolean fromItemStack = extraData.readBoolean();
        if (!fromItemStack) {
            BlockPos pos = extraData.readBlockPos();
            if (player.level.getBlockEntity(pos) instanceof BlazeStoveBlockEntity blazeStove)
                this.blazeStove = blazeStove;
            else throw new RuntimeException("Expected Blaze Stove at " + pos + " but found none");
        }
        return item;
    }

    @Override
    protected void addSlots() {
        addPlayerSlots(42, 97);
        this.addSlot(new CookingIngredientSlot(0, 54, 27));
        this.addSlot(new CookingIngredientSlot(1, 73, 27));
        this.addSlot(new CookingIngredientSlot(2, 92, 27));
        this.addSlot(new CookingIngredientSlot(3, 54, 45));
        this.addSlot(new CookingIngredientSlot(4, 73, 45));
        this.addSlot(new CookingIngredientSlot(5, 92, 45));
        this.addSlot(new DisplaySlot(6, 177, 34));
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
        playerIn.getCooldowns().addCooldown(cookingGuide.getOwner().getItem(), 5);
    }
    
    @Override
    protected boolean allowRepeats() {
        return true;
    }
    
    class CookingIngredientSlot extends SlotItemHandler {
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

    class DisplaySlot extends SlotItemHandler {
        public DisplaySlot(int index, int xPosition, int yPosition) {
            super(ghostInventory, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            return false;
        }

        @Override
        public boolean mayPickup(Player playerIn) {
            return false;
        }
    }

    @Override
    public void clicked(int slotId, int dragType, ClickType clickTypeIn, Player player) {
        if (slotId == 42)
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
            for(int i = 36; i < 42; i++)
            if (getSlot(i).mayPlace(stackToInsert)) {
                ghostInventory.insertItem(i - 36, stackToInsert, false);
                getSlot(i).setChanged();
            }
        } else {
            ghostInventory.extractItem(0, 1, false);
            getSlot(index).setChanged();
        }
        return ItemStack.EMPTY;
    }
    
}
