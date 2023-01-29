package plus.dragons.createfarmersautomation.content.contraptions.components.stove;

import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerTileEntity;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
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
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plus.dragons.createfarmersautomation.entry.CfaBlocks;
import plus.dragons.createfarmersautomation.entry.CfaContainerTypes;
import plus.dragons.createfarmersautomation.entry.CfaItems;

import java.util.ArrayList;
import java.util.List;

public class CookingGuideItem extends Item implements MenuProvider {
    // TODO Texture & Lang
    public CookingGuideItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useOnContext) {
        var level = useOnContext.getLevel();
        var player = useOnContext.getPlayer();
        if (player == null)
            return InteractionResult.PASS;
        if (player.isShiftKeyDown()) {
            var itemStack = useOnContext.getItemInHand();
            if (itemStack.is(CfaItems.COOKING_GUIDE.get())) {
                var blockPos = useOnContext.getClickedPos();
                var blockState = level.getBlockState(blockPos);
                var blockEntity = level.getBlockEntity(blockPos);
                if (blockState.getBlock() instanceof BlazeBurnerBlock &&
                        blockEntity instanceof BlazeBurnerTileEntity)
                {
                    if (!level.isClientSide()) {
                        level.setBlockAndUpdate(blockPos, CfaBlocks.BLAZE_STOVE.getDefaultState()
                                .setValue(BlazeStoveBlock.FACING, level.getBlockState(blockPos).getValue(BlazeBurnerBlock.FACING)));
                        if(level.getBlockEntity(blockPos) instanceof BlazeStoveBlockEntity blazeStove){
                            var in = itemStack.copy();
                            in.setCount(1);
                            blazeStove.setCookingGuide(in);
                        }

                        // TODO ADVANCEMENT
                        AdvancementBehaviour.setPlacedBy(useOnContext.getLevel(), blockPos, player);
                        // CeiAdvancements.BLAZES_NEW_JOB.getTrigger().trigger((ServerPlayer) player);

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

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, @NotNull Player pPlayer) {
        var item = pPlayer.getMainHandItem();
        return new CookingGuideMenu(CfaContainerTypes.COOKING_GUIDE_FOR_BLAZE.get(),pContainerId,pPlayerInventory,item,null);
    }

    public static List<ItemStack> getContent(ItemStack itemStack){
        var ret = new ArrayList<ItemStack>();
        var tag = itemStack.getOrCreateTag();
        for(int i=0;i<6;i++){
            if(tag.contains("Ingredient" + i)){
                ret.add(ItemStack.of(tag.getCompound("Ingredient" + i)));
            } else ret.add(ItemStack.EMPTY);
        }
        return ret;
    }

    public static void saveContent(List<ItemStack> content,ItemStack itemStack){
        var tag = itemStack.getOrCreateTag();
        for(int i=0;i<6;i++){
            tag.put("Ingredient"+i,content.get(i).serializeNBT());
        }
    }
}
