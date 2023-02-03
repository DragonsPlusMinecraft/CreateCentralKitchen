package plus.dragons.createcentralkitchen.content.contraptions.components.stove;

import com.mojang.authlib.minecraft.client.MinecraftClient;
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
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

import javax.annotation.Nullable;

public class CookingGuideMenu extends GhostItemContainer<ItemStack> {
    boolean fromItemStack = true;
    @Nullable
    BlazeStoveBlockEntity blazeStove;

    public CookingGuideMenu(MenuType<?> type, int id, Inventory inv, FriendlyByteBuf extraData) {
        super(type, id, inv, extraData);
    }

    public CookingGuideMenu(MenuType<?> type, int id, Inventory inv, ItemStack contentHolder, @Nullable BlazeStoveBlockEntity stove) {
        super(type, id, inv, contentHolder);
        this.fromItemStack = blazeStove == null;
    }
    
    public void updateResult() {
        var wrapper = new RecipeWrapper(ghostInventory);
        this.player.level.getRecipeManager()
            .getRecipeFor(ModRecipeTypes.COOKING.get(), wrapper, this.player.level)
            .ifPresentOrElse(
                recipe -> ghostInventory.setStackInSlot(6, recipe.getResultItem()),
                () -> ghostInventory.setStackInSlot(6, ItemStack.EMPTY));
    }

    @Override
    protected ItemStackHandler createGhostInventory() {
        return new ItemStackHandler(7) {
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }

    @Override
    protected boolean allowRepeats() {
        return true;
    }

    @Override
    protected void initAndReadInventory(ItemStack contentHolder) {
        super.initAndReadInventory(contentHolder);
        var content = NonNullList.withSize(6, ItemStack.EMPTY);
        CookingGuideItem.loadContent(content, contentHolder);
        for (int i = 0; i < 6; i++) {
            ghostInventory.setStackInSlot(i, content.get(i));
        }
        updateResult();
    }

    @Override
    protected ItemStack createOnClient(FriendlyByteBuf extraData) {
        var containerItem = extraData.readItem();
        fromItemStack = extraData.readBoolean();
        if (!fromItemStack) {
            BlockPos blockPos = extraData.readBlockPos();
            if (Minecraft.getInstance().level.getBlockEntity(blockPos) instanceof BlazeStoveBlockEntity blazeStove) {
                this.blazeStove = blazeStove;
            } else {
                throw new RuntimeException("Can't open CookingGuideMenu on client: Missing BlazeStoveEntity at " + blockPos);
            }
        }
        return containerItem;
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
        if (blazeStove != null) {
            return super.stillValid(player) && blazeStove.stillValid(player);
        }
        return super.stillValid(player);
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
            updateResult();
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
                ItemStack stackInSlot = ghostInventory.getStackInSlot(slotId - 36).copy();
                setCarried(stackInSlot);
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
