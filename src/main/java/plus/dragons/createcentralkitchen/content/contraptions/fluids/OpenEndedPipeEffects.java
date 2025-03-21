package plus.dragons.createcentralkitchen.content.contraptions.fluids;

import com.simibubi.create.api.effect.OpenPipeEffectHandler;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import plus.dragons.createcentralkitchen.entry.fluid.CckFluidEntries;

public class OpenEndedPipeEffects {
    public static void register() {
        OpenPipeEffectHandler.REGISTRY.register(
                CckFluidEntries.DRAGONS_BREATH.get(),
                (level, area, fluid) -> level.getEntitiesOfClass(Player.class, area, LivingEntity::isAlive)
                        .forEach(player -> player.addEffect(new MobEffectInstance(MobEffects.HARM, 1)))

        );
    }
}
