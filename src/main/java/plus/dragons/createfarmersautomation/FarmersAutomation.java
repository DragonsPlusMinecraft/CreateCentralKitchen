package plus.dragons.createfarmersautomation;

import com.mojang.logging.LogUtils;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import plus.dragons.createfarmersautomation.content.logistics.block.mechanicalArm.CfaArmInteractionPointTypes;
import plus.dragons.createfarmersautomation.entry.CfaBlockEntities;
import plus.dragons.createfarmersautomation.entry.CfaBlocks;
import plus.dragons.createfarmersautomation.entry.CfaFluids;
import plus.dragons.createfarmersautomation.entry.CfaItems;
import plus.dragons.createfarmersautomation.foundation.data.lang.LangMerger;
import plus.dragons.createfarmersautomation.foundation.utility.SafeRegistrate;

@Mod(FarmersAutomation.ID)
public class FarmersAutomation {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "create_farmers_automation";
    public static final String NAME = "Create: Farmer's Automation";
    
    public static final SafeRegistrate REGISTRATE = new SafeRegistrate(ID);
    public static final DeferredRegister<RecipeType<?>> TYPE_REGISTER =
        DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, ID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER =
        DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ID);
    
    public FarmersAutomation() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(EventPriority.LOW, FarmersAutomation::datagen);
        REGISTRATE.registerEventListeners(modEventBus);
        CfaBlocks.register();
        CfaBlockEntities.register();
        CfaItems.register();
        CfaFluids.register(modEventBus);
        SERIALIZER_REGISTER.register(modEventBus);
        TYPE_REGISTER.register(modEventBus);
        CfaArmInteractionPointTypes.register();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> FarmersAutomationClient::new);
    }
    
    public static ResourceLocation genRL(String path) {
        return new ResourceLocation(ID, path);
    }
    
    public static void datagen(final GatherDataEvent event) {
        DataGenerator datagen = event.getGenerator();
        datagen.addProvider(new LangMerger(datagen));
    }

}