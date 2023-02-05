package plus.dragons.createcentralkitchen.content.contraptions.components.stove;

import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerTileEntity;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.CckBlocks;
import plus.dragons.createcentralkitchen.entry.CckContainerTypes;
import plus.dragons.createcentralkitchen.entry.CckItems;

import java.util.List;

public class CookingGuideItem extends Item implements MenuProvider {
    public CookingGuideItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        var level = useOnContext.getLevel();
        var player = useOnContext.getPlayer();
        if (player == null)
            return InteractionResult.PASS;
        if (player.isSecondaryUseActive()) {
            var itemStack = useOnContext.getItemInHand();
            if (itemStack.is(CckItems.COOKING_GUIDE.get())) {
                var blockPos = useOnContext.getClickedPos();
                var blockState = level.getBlockState(blockPos);
                var blockEntity = level.getBlockEntity(blockPos);
                if (blockState.getBlock() instanceof BlazeBurnerBlock &&
                    blockEntity instanceof BlazeBurnerTileEntity)
                {
                    if (!level.isClientSide()) {
                        level.setBlockAndUpdate(blockPos, CckBlocks.BLAZE_STOVE.getDefaultState()
                                .setValue(BlazeStoveBlock.FACING, level.getBlockState(blockPos).getValue(BlazeBurnerBlock.FACING)));
                        if(level.getBlockEntity(blockPos) instanceof BlazeStoveBlockEntity blazeStove){
                            var guide = itemStack.copy();
                            blazeStove.setCookingGuide(guide);
                        }

                        AdvancementBehaviour.setPlacedBy(useOnContext.getLevel(), blockPos, player);

                        if (!player.getAbilities().instabuild)
                            itemStack.shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(@NotNull Level world, Player player, @NotNull InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (!player.isShiftKeyDown() && hand == InteractionHand.MAIN_HAND) {
            if (!world.isClientSide && player instanceof ServerPlayer)
                NetworkHooks.openScreen((ServerPlayer) player, this, buf -> {
                    buf.writeItem(heldItem);
                    buf.writeBoolean(true);
                });
            return InteractionResultHolder.success(heldItem);
        }
        return InteractionResultHolder.pass(heldItem);
    }

    @Override
    @NotNull
    public Component getDisplayName() {
        return getDescription();
    }
    
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        ItemStack result = getResult(stack);
        if (result.isEmpty()) {
            CentralKitchen.LANG.translate("gui.goggles.blaze_stove.no_result")
                .style(ChatFormatting.RED)
                .addTo(tooltip);
        } else {
            CentralKitchen.LANG.translate("gui.goggles.blaze_stove.recipe_result")
                .addTo(tooltip);
            CentralKitchen.LANG.itemName(result)
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip);
        }
    }
    
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, @NotNull Inventory inventory, @NotNull Player pPlayer) {
        var item = pPlayer.getMainHandItem();
        return new CookingGuideMenu(CckContainerTypes.COOKING_GUIDE_FOR_BLAZE.get(), syncId, inventory, item, null);
    }
    
    public static ItemStack getResult(ItemStack guide) {
        CompoundTag tag = guide.getTag();
        if (tag != null && tag.contains("Result", 10))
            return ItemStack.of(tag.getCompound("Result"));
        return ItemStack.EMPTY;
    }

    public static void saveContent(List<ItemStack> content, ItemStack result, ItemStack guide) {
        var tag = guide.getOrCreateTag();
        ListTag listTag = new ListTag();
    
        for(int i = 0; i < content.size(); ++i) {
            ItemStack itemstack = content.get(i);
            if (!itemstack.isEmpty()) {
                CompoundTag compoundtag = new CompoundTag();
                compoundtag.putByte("Slot", (byte)i);
                itemstack.save(compoundtag);
                listTag.add(compoundtag);
            }
        }
    
        if (!listTag.isEmpty()) {
            tag.put("Ingredients", listTag);
            tag.put("Result", result.serializeNBT());
        }
    }
    
    public static void loadContent(NonNullList<ItemStack> content, ItemStack guide) {
        CompoundTag tag = guide.getTag();
        if (tag == null)
            return;
        ListTag listTag = guide.getTag().getList("Ingredients", 10);
        
        for(int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundtag = listTag.getCompound(i);
            int slot = compoundtag.getByte("Slot") & 255;
            if (slot < 6) {
                content.set(slot, ItemStack.of(compoundtag));
            }
        }
    }
    
}
