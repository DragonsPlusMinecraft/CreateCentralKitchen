package plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove;

import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerTileEntity;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FdBlocks;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FdCapabilities;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FdItems;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.FdMenuTypes;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CookingGuideItem extends Item implements MenuProvider {
    public CookingGuideItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        var player = context.getPlayer();
        if (player == null)
            return InteractionResult.PASS;
        if (player.isSecondaryUseActive()) {
            var itemStack = context.getItemInHand();
            if (itemStack.is(FdItems.COOKING_GUIDE.get())) {
                var blockPos = context.getClickedPos();
                var blockState = level.getBlockState(blockPos);
                var blockEntity = level.getBlockEntity(blockPos);
                if (blockState.getBlock() instanceof BlazeBurnerBlock &&
                    blockEntity instanceof BlazeBurnerTileEntity)
                {
                    if (!level.isClientSide()) {
                        level.setBlockAndUpdate(blockPos, FdBlocks.BLAZE_STOVE.getDefaultState()
                                .setValue(BlazeStoveBlock.FACING, level.getBlockState(blockPos).getValue(BlazeBurnerBlock.FACING)));
                        if (level.getBlockEntity(blockPos) instanceof BlazeStoveBlockEntity blazeStove)
                            blazeStove.setCookingGuide(itemStack.copy());
                        AdvancementBehaviour.setPlacedBy(context.getLevel(), blockPos, player);

                        if (!player.getAbilities().instabuild)
                            player.setItemInHand(context.getHand(), ItemStack.EMPTY);
                    }
                    return InteractionResult.SUCCESS;
                }
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
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        ItemStack result = stack
            .getCapability(FdCapabilities.COOKING_GUIDE)
            .map(CookingGuide::getResult)
            .orElse(ItemStack.EMPTY);
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
    public AbstractContainerMenu createMenu(int syncId, Inventory inventory, Player player) {
        ItemStack cookingGuide = player.getMainHandItem();
        if (!cookingGuide.is(this))
            return null;
        return new CookingGuideMenu(FdMenuTypes.COOKING_GUIDE_FOR_BLAZE.get(), syncId, inventory, cookingGuide);
    }
    
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CookingGuide(stack);
    }
    
}
