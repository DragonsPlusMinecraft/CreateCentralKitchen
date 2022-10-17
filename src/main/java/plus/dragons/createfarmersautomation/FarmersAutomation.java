package plus.dragons.createfarmersautomation;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import plus.dragons.createfarmersautomation.content.logistics.block.mechanicalArm.CfaArmInteractionPointTypes;
import plus.dragons.createfarmersautomation.foundation.utility.SafeRegistrate;

import java.util.Objects;
import java.util.Set;

@Mod(FarmersAutomation.ID)
public class FarmersAutomation {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "create_farmers_automation";
    public static final String NAME = "Create: Farmer's Automation";
    public static final SafeRegistrate REGISTRATE = new SafeRegistrate(ID);
    
    public static final TagKey<Item> UPRIGHT_ON_DEPLOYER = Objects.requireNonNull(ForgeRegistries.ITEMS.tags())
        .createOptionalTagKey(genRL("upright_on_deployer"), Set.of());
    
    public FarmersAutomation() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        REGISTRATE.registerEventListeners(modEventBus);
        CfaArmInteractionPointTypes.register();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> FarmersAutomationClient::new);
    }
    
    public static ResourceLocation genRL(String path) {
        return new ResourceLocation(ID, path);
    }

}
