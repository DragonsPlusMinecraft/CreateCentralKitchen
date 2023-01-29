package plus.dragons.createfarmersautomation.content.contraptions.components.cooking;

import com.simibubi.create.foundation.gui.container.GhostItemContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

import javax.annotation.Nullable;
import java.util.Optional;

public class CookingGuideMenu extends GhostItemContainer<ItemStack> {
    boolean directItemStackEdit;
    @Nullable
    BlockPos blockPos = null;

    public CookingGuideMenu(MenuType<?> type, int id, Inventory inv, FriendlyByteBuf extraData) {
        super(type, id, inv, extraData);
        directItemStackEdit = extraData.readBoolean();
        if(!directItemStackEdit){
            blockPos = extraData.readBlockPos();
        }
    }

    public CookingGuideMenu(MenuType<?> type, int id, Inventory inv, ItemStack contentHolder, @Nullable BlockPos blockPos) {
        super(type, id, inv, contentHolder);
        if(blockPos!=null){
            directItemStackEdit = false;
            this.blockPos = blockPos;
        } else directItemStackEdit = true;
    }

    private void updateResult() {
        var wrapper = new RecipeWrapper(ghostInventory);
        Optional<CookingPotRecipe> recipe = Minecraft.getInstance().level.getRecipeManager().getRecipeFor(ModRecipeTypes.COOKING.get(), wrapper, Minecraft.getInstance().level);
        recipe.ifPresent(r -> ghostInventory.setStackInSlot(6, r.getResultItem()));
    }

    @Override
    protected ItemStackHandler createGhostInventory() {
        return new ItemStackHandler(7){
            @Override
            public int getSlotLimit(int slot)
            {
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
        var content = CookingGuideItem.getContent(contentHolder);
        for(int i=0;i<6;i++){
            ghostInventory.setStackInSlot(i, content.get(i));
        }
        updateResult();
    }

    @Override
    protected ItemStack createOnClient(FriendlyByteBuf extraData) {
        return extraData.readItem();
    }

    @Override
    protected void addSlots() {
        addPlayerSlots(44, 70);
        this.addSlot(new CookingIngredientSlot(0, 51, 22));
        this.addSlot(new CookingIngredientSlot(1, 71, 22));
        this.addSlot(new CookingIngredientSlot(2, 91, 22));
        this.addSlot(new CookingIngredientSlot(3, 51, 42));
        this.addSlot(new CookingIngredientSlot(4, 71, 42));
        this.addSlot(new CookingIngredientSlot(5, 91, 42));
        this.addSlot(new DisplaySlot(6, 51, 72));
    }

    @Override
    protected void saveData(ItemStack contentHolder) {
    }

    @Override
    public boolean stillValid(Player player) {
        if(!directItemStackEdit){
            return super.stillValid(player) && player.level.getBlockEntity(blockPos) instanceof BlazeStoveBlockEntity;
        }
        return super.stillValid(player);
    }


    class CookingIngredientSlot extends SlotItemHandler {
        public CookingIngredientSlot(int index, int xPosition, int yPosition) {
            super(ghostInventory, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack pStack) {
            return pStack.is(Items.ENCHANTED_BOOK) && !EnchantmentHelper.getEnchantments(pStack).isEmpty();
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
        // TODO
        if (slotId < 36) {
            super.clicked(slotId, dragType, clickTypeIn, player);
            return;
        }
        if (clickTypeIn == ClickType.THROW)
            return;

        ItemStack held = getCarried();
        if (clickTypeIn == ClickType.CLONE) {
            if (player.isCreative() && held.isEmpty()) {
                ItemStack stackInSlot = ghostInventory.getStackInSlot(0)
                        .copy();
                setCarried(stackInSlot);
            }
        } else if (getSlot(36).mayPlace(held) || held.isEmpty()) {
            ghostInventory.setStackInSlot(0, held.copy());
            getSlot(slotId).setChanged();
        }
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        // TODO
        if (index < 36) {
            ItemStack stackToInsert = playerInventory.getItem(index);
            if (getSlot(36).mayPlace(stackToInsert)) {
                ItemStack copy = stackToInsert.copy();
                ghostInventory.insertItem(0, copy, false);
                getSlot(36).setChanged();
            }
        } else {
            ghostInventory.extractItem(0, 1, false);
            getSlot(index).setChanged();
        }
        return ItemStack.EMPTY;
    }
}
