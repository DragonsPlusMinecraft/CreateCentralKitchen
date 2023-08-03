package plus.dragons.createcentralkitchen.content.contraptions.fluids;

import com.simibubi.create.content.fluids.OpenEndedPipe;
import com.simibubi.create.foundation.ponder.PonderWorld;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fluids.FluidStack;
import plus.dragons.createcentralkitchen.entry.fluid.CckFluidEntries;

public class OpenEndedPipeEffects {
    public static void register() {
        OpenEndedPipe.registerEffectHandler(new OpenEndedPipe.IEffectHandler() {
            @Override
            public boolean canApplyEffects(OpenEndedPipe pipe, FluidStack fluid) {
                return fluid.getFluid().isSame(CckFluidEntries.DRAGONS_BREATH.get());
            }

            @Override
            public void applyEffects(OpenEndedPipe pipe, FluidStack fluid) {
                if (pipe.getWorld() instanceof PonderWorld){
                    return;
                }
                if (!(pipe.getWorld() instanceof ServerLevel level))
                    return;
                level.getEntitiesOfClass(Player.class, pipe.getAOE(), LivingEntity::isAlive).forEach(player->player.addEffect(new MobEffectInstance(MobEffects.HARM,1)));
            }
        });
    }

}
