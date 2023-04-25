package plus.dragons.createcentralkitchen.foundation.mixin.common.farmersdelight;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.dragons.createcentralkitchen.entry.block.FDBlockEntries;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;

@Mixin(value = PieBlock.class, remap = false)
public class PieBlockMixin extends Block {
    private static final RegistryObject<Item> ENVIRONMENTAL_APPLE_PIE = RegistryObject
        .create(Mods.environmental("apple_pie"), ForgeRegistries.ITEMS);
    
    private PieBlockMixin(Properties properties) {
        super(properties);
    }
    
    @Inject(method = "consumeBite", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z", remap = true))
    private void cck$triggerConsumeItem(Level level, BlockPos pos, BlockState state, Player playerIn, CallbackInfoReturnable<InteractionResult> cir) {
        if (playerIn instanceof ServerPlayer serverPlayer) {
            if (this.equals(ModBlocks.APPLE_PIE.get()) &&
                ENVIRONMENTAL_APPLE_PIE.isPresent() &&
                FDBlockEntries.isPieOverhaulEnabled(ENVIRONMENTAL_APPLE_PIE.getId()))
            {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, ENVIRONMENTAL_APPLE_PIE.get().getDefaultInstance());
            }
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, this.asItem().getDefaultInstance());
        }
    }
    
}
