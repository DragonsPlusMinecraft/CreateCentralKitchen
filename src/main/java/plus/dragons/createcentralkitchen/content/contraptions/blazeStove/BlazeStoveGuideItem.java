package plus.dragons.createcentralkitchen.content.contraptions.blazeStove;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.entry.block.FDBlockEntries;
import plus.dragons.createcentralkitchen.foundation.utility.Lang;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class BlazeStoveGuideItem<G extends BlazeStoveGuide> extends Item implements MenuProvider {
    
    public BlazeStoveGuideItem(Properties properties) {
        super(properties);
    }
    
    protected abstract Capability<G> getGuideCapability();
    
    protected abstract BlazeStoveGuideMenu<G> createGuideMenu(int syncId, Inventory inventory, ItemStack guide);
    
    protected abstract BlazeStoveGuideMenu<G> createGuideMenu(int syncId, Inventory inventory, BlazeStoveBlockEntity stove);
    
    public void appendGuideTooltip(ItemStack stack, List<Component> tooltip, boolean goggle) {
        ItemStack result = stack
            .getCapability(getGuideCapability())
            .map(BlazeStoveGuide::getResult)
            .orElse(ItemStack.EMPTY);
        if (result.isEmpty()) {
            var text = Lang.translate("gui.goggles.blaze_stove.no_result").style(ChatFormatting.RED);
            if (goggle)
                text.forGoggles(tooltip);
            else
                text.addTo(tooltip);
        } else {
            var text = Lang.translate("gui.goggles.blaze_stove.recipe_result");
            var itemName = Lang.itemName(result).style(ChatFormatting.GRAY);
            if (goggle) {
                text.forGoggles(tooltip);
                itemName.forGoggles(tooltip, 4);
            } else {
                text.addTo(tooltip);
                itemName.forGoggles(tooltip);
            }
        }
    }
    
    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        var player = context.getPlayer();
        if (player == null)
            return InteractionResult.PASS;
        if (player.isSecondaryUseActive()) {
            var itemStack = context.getItemInHand();
            var blockPos = context.getClickedPos();
            var blockState = level.getBlockState(blockPos);
            var blockEntity = level.getBlockEntity(blockPos);
            if (blockState.getBlock() instanceof BlazeBurnerBlock &&
                blockEntity instanceof BlazeBurnerBlockEntity)
            {
                if (!level.isClientSide()) {
                    level.setBlockAndUpdate(blockPos, FDBlockEntries.BLAZE_STOVE.getDefaultState()
                        .setValue(BlazeStoveBlock.FACING, level.getBlockState(blockPos).getValue(BlazeBurnerBlock.FACING)));
                    if (level.getBlockEntity(blockPos) instanceof BlazeStoveBlockEntity blazeStove)
                        blazeStove.setGuide(itemStack.copy());
                    AdvancementBehaviour.setPlacedBy(context.getLevel(), blockPos, player);
            
                    if (!player.getAbilities().instabuild)
                        player.setItemInHand(context.getHand(), ItemStack.EMPTY);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
    
    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (!player.isShiftKeyDown() && hand == InteractionHand.MAIN_HAND) {
            if (!world.isClientSide && player instanceof ServerPlayer)
                NetworkHooks.openGui((ServerPlayer) player, this, buf -> {
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
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        appendGuideTooltip(stack, tooltip, false);
    }
    
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inventory, Player player) {
        ItemStack cookingGuide = player.getMainHandItem();
        if (!cookingGuide.is(this))
            return null;
        return createGuideMenu(syncId, inventory, cookingGuide);
    }
    
}
