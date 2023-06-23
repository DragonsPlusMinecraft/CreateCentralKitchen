package plus.dragons.createcentralkitchen.content.contraptions.components.actor;

import com.simibubi.create.content.contraptions.actors.harvester.HarvesterMovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.orcinus.overweightfarming.blocks.CropFullBlock;
import net.orcinus.overweightfarming.blocks.OverweightCarrotBlock;
import net.orcinus.overweightfarming.blocks.OverweightCocoaBlock;
import net.orcinus.overweightfarming.init.OFBlocks;
import plus.dragons.createcentralkitchen.foundation.config.CentralKitchenConfigs;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static plus.dragons.createcentralkitchen.content.contraptions.components.actor.HarvesterMovementBehaviourExtension.REGISTRY;

@ModLoadSubscriber(modid = Mods.OF)
public class OFHarvesterMovementBehaviorExtensions {

    private static final Map<Block,BlockState> FARMLAND_REVERT_MAP = new HashMap<>();
    private static final String SNOWY_SPIRIT_MODID = "snowyspirit";
    private static final ResourceLocation GINGER = new ResourceLocation(SNOWY_SPIRIT_MODID,"ginger");
    @SubscribeEvent
    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FARMLAND_REVERT_MAP.put(Blocks.DIRT,Blocks.FARMLAND.defaultBlockState().setValue(BlockStateProperties.MOISTURE,7));
            if(ModList.get().isLoaded(Mods.FD)){
                FARMLAND_REVERT_MAP.put(ForgeRegistries.BLOCKS.getValue(Mods.fd("rich_soil")),
                        ForgeRegistries.BLOCKS.getValue(Mods.fd("rich_soil_farmland")).defaultBlockState());
            }
            if(CentralKitchenConfigs.COMMON.integration.enableHarvesterSupportForOverweightFarming.get()){
                REGISTRY.put(OFBlocks.OVERWEIGHT_BEETROOT.get(), (behaviour,context,pos,state,replant,partial)->
                        OFHarvesterMovementBehaviorExtensions.harvest(behaviour,context,pos,state,replant, Blocks.BEETROOTS::defaultBlockState));
                REGISTRY.put(OFBlocks.OVERWEIGHT_CARROT.get(), (behaviour,context,pos,state,replant,partial)->
                        OFHarvesterMovementBehaviorExtensions.harvest(behaviour,context,pos,state,replant, Blocks.CARROTS::defaultBlockState));
                REGISTRY.put(OFBlocks.OVERWEIGHT_POTATO.get(), (behaviour,context,pos,state,replant,partial)->
                        OFHarvesterMovementBehaviorExtensions.harvest(behaviour,context,pos,state,replant, Blocks.POTATOES::defaultBlockState));
                REGISTRY.put(OFBlocks.OVERWEIGHT_NETHER_WART.get(), (behaviour,context,pos,state,replant,partial)->
                        OFHarvesterMovementBehaviorExtensions.harvest(behaviour,context,pos,state,replant, Blocks.NETHER_WART::defaultBlockState));
                REGISTRY.put(OFBlocks.OVERWEIGHT_COCOA.get(), (behaviour,context,pos,state,replant,partial)->
                        OFHarvesterMovementBehaviorExtensions.harvestCocoa(behaviour,context,pos,state,replant));
                if(ModList.get().isLoaded(Mods.FD)){
                    REGISTRY.put(OFBlocks.OVERWEIGHT_CABBAGE.get(), (behaviour,context,pos,state,replant,partial)->
                            OFHarvesterMovementBehaviorExtensions.harvest(behaviour,context,pos,state,replant, ofPlant(Mods.fd("cabbages"))));
                    REGISTRY.put(OFBlocks.OVERWEIGHT_ONION.get(), (behaviour,context,pos,state,replant,partial)->
                            OFHarvesterMovementBehaviorExtensions.harvest(behaviour,context,pos,state,replant,ofPlant(Mods.fd("onions"))));
                }
                if(ModList.get().isLoaded("bewitchment")){
                    REGISTRY.put(OFBlocks.OVERWEIGHT_GINGER.get(), (behaviour,context,pos,state,replant,partial)->
                            OFHarvesterMovementBehaviorExtensions.harvest(behaviour,context,pos,state,replant,ofPlant(GINGER)));
                }
            }
        });
    }

    public static void harvest(HarvesterMovementBehaviour behaviour,
                                             MovementContext context,
                                             BlockPos pos, BlockState state,
                                             boolean replant, Supplier<BlockState> plantSupplier)
    {
        if (!(state.getBlock() instanceof CropFullBlock))
            return;

        var plant = plantSupplier.get();

        Level level = context.world;
        behaviour.dropItem(context, new ItemStack(state.getBlock(), 1));
        if (replant) {
            if(FARMLAND_REVERT_MAP.containsKey(level.getBlockState(pos.below()).getBlock())){
                level.setBlock(pos.below(), FARMLAND_REVERT_MAP.get(level.getBlockState(pos.below()).getBlock()),2);
                level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlock(pos, plant,2);
            }
            if ((plant.getBlock() instanceof IPlantable iPlantable))
                if(level.getBlockState(pos.below()).canSustainPlant(level,pos, Direction.UP,iPlantable)){
                    level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.setBlock(pos, plant,2);
                }
        }
    }

    public static void harvestCocoa(HarvesterMovementBehaviour behaviour,
                               MovementContext context,
                               BlockPos pos, BlockState state,
                               boolean replant)
    {
        if (!(state.getBlock() instanceof OverweightCocoaBlock))
            return;

        Level level = context.world;
        BlockHelper.destroyBlock(level, pos, 1, stack -> behaviour.dropItem(context, stack));
        if (replant) {
            for(var direction: Direction.Plane.HORIZONTAL){
                if(level.getBlockState(pos.relative(direction)).is(BlockTags.JUNGLE_LOGS)){
                    level.playSound(null, pos, SoundEvents.CROP_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.setBlock(pos, Blocks.COCOA.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING,direction), 2);
                    return;
                }
            }
        }
    }

    private static Supplier<BlockState> ofPlant(ResourceLocation id){
        return new Supplier<>() {

            private Block block;

            @Override
            public BlockState get() {
                if (block == null) {
                    block = ForgeRegistries.BLOCKS.getValue(id);
                }
                return block.defaultBlockState();
            }
        };
    }
}
