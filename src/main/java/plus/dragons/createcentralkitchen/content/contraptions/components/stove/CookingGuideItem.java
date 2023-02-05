package plus.dragons.createcentralkitchen.content.contraptions.components.stove;

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
import plus.dragons.createcentralkitchen.entry.CckBlocks;
import plus.dragons.createcentralkitchen.entry.CckContainerTypes;
import plus.dragons.createcentralkitchen.entry.CckItems;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
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
                        if (level.getBlockEntity(blockPos) instanceof BlazeStoveBlockEntity blazeStove)
                            blazeStove.setCookingGuide(itemStack);
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
            .getCapability(CookingGuide.CAPABILITY)
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
        return new CookingGuideMenu(CckContainerTypes.COOKING_GUIDE_FOR_BLAZE.get(), syncId, inventory, cookingGuide);
    }
    
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CookingGuide(stack);
    }
    
}
