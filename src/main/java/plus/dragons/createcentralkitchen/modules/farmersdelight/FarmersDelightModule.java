package plus.dragons.createcentralkitchen.modules.farmersdelight;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import plus.dragons.createcentralkitchen.core.modules.ModModule;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.blazeStove.BlazeStoveBlockEntity;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.contraptions.deployer.CuttingBoardDeployingRecipe;
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.logistics.block.mechanicalArm.FarmersDelightModuleArmInteractionPointTypes;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.*;
import plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.FarmersDelightModulePonderTags;
import plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.FarmersDelightModulePonders;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;

@ModModule(id = "farmersdelight", dependencies = "farmersdelight")
public class FarmersDelightModule {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "farmersdelight";
    
    public FarmersDelightModule() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        
        registerEntries();
        registerModEvents(modBus);
        registerForgeEvents(forgeBus);
    
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> Client::new);
    }
    
    private void registerEntries() {
        FarmersDelightModuleBlocks.register();
        FarmersDelightModuleBlockEntities.register();
        FarmersDelightModuleItems.register();
        FarmersDelightModuleMenuTypes.register();
        FarmersDelightModuleFluids.register();
        FarmersDelightModuleRecipeTypes.register();
        FarmersDelightModuleArmInteractionPointTypes.register();
    }
    
    private void registerModEvents(IEventBus modBus) {
        modBus.addListener(FarmersDelightModuleCapabilities::register);
        modBus.addListener(this::setup);
    }
    
    private void registerForgeEvents(IEventBus forgeBus) {
        forgeBus.addListener(CuttingBoardDeployingRecipe::onDeployerRecipeSearch);
        forgeBus.addListener(FarmersDelightModuleItems::fillCreateItemGroup);
        forgeBus.addGenericListener(Fluid.class, FarmersDelightModuleFluids::remap);
    }
    
    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FarmersDelightModulePackets.register();
            BlazeStoveBlockEntity.registerBoostingCooker(ModBlockEntityTypes.COOKING_POT.get());
            BlazeStoveBlockEntity.registerBoostingCooker(ModBlockEntityTypes.SKILLET.get());
        });
    }
    
    public static ResourceLocation genRL(String path) {
        return new ResourceLocation(ID, path);
    }
    
    public static class Client {
    
        public Client() {
            IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
            IEventBus forgeBus = MinecraftForge.EVENT_BUS;
    
            registerEntries();
            registerModEvents(modBus);
            registerForgeEvents(forgeBus);
        }
    
        private void registerEntries() {
            FarmersDelightModuleBlockPartials.register();
        }
    
        private void registerModEvents(IEventBus modBus) {
            modBus.addListener(this::setup);
        }
    
        private void registerForgeEvents(IEventBus forgeBus) {
        
        }
        
        public void setup(final FMLClientSetupEvent event) {
            event.enqueueWork(FarmersDelightModulePonderTags::register);
            event.enqueueWork(FarmersDelightModulePonders::register);
        }
    
    }
    
}
