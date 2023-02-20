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
import plus.dragons.createcentralkitchen.modules.farmersdelight.content.logistics.block.mechanicalArm.FdArmInteractionPointTypes;
import plus.dragons.createcentralkitchen.modules.farmersdelight.entry.*;
import plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.FdPonderIndex;
import plus.dragons.createcentralkitchen.modules.farmersdelight.foundation.ponder.FdPonderTag;
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
        FdBlocks.register();
        FdBlockEntities.register();
        FdItems.register();
        FdMenuTypes.register();
        FdFluids.register();
        FdRecipeTypes.register();
        FdArmInteractionPointTypes.register();
    }
    
    private void registerModEvents(IEventBus modBus) {
        modBus.addListener(FdCapabilities::register);
        modBus.addListener(this::setup);
    }
    
    private void registerForgeEvents(IEventBus forgeBus) {
        forgeBus.addListener(CuttingBoardDeployingRecipe::onDeployerRecipeSearch);
        forgeBus.addListener(FdItems::fillCreateItemGroup);
        forgeBus.addGenericListener(Fluid.class, FdFluids::remap);
    }
    
    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FdPackets.register();
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
            FdBlockPartials.register();
        }
    
        private void registerModEvents(IEventBus modBus) {
            modBus.addListener(this::setup);
        }
    
        private void registerForgeEvents(IEventBus forgeBus) {
        
        }
        
        public void setup(final FMLClientSetupEvent event) {
            event.enqueueWork(FdPonderTag::register);
            event.enqueueWork(FdPonderIndex::register);
        }
    
    }
    
}
