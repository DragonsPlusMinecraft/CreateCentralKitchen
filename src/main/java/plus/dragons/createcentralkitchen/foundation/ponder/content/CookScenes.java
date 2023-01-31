package plus.dragons.createcentralkitchen.foundation.ponder.content;

import com.simibubi.create.foundation.ponder.*;

public class CookScenes {

    public static void transformBlazeBurner(SceneBuilder scene, SceneBuildingUtil util){
        scene.title("transform", ""); // We do not use PonderLocalization. For title only
        /*scene.configureBasePlate(0, 0, 3);
        scene.showBasePlate();
        scene.idle(5);
        scene.world.showSection(util.select.fromTo(0, 1, 0, 2, 1, 2), Direction.DOWN);

        scene.overlay.showText(40)
                .text("") // We do not use PonderLocalization. For registerText only
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector.topOf(1, 1, 1));
        scene.idle(40);
        scene.world.setBlock(util.grid.at(1,1,1), CeiBlocks.BLAZE_ENCHANTER.getDefaultState(),false);
        scene.world.modifyTileEntity(util.grid.at(1,1,1), BlazeEnchanterBlockEntity.class, be-> be.setTargetItem(enchantingGuide(Enchantments.MENDING,1)));
        scene.overlay.showControls(new InputWindowElement(util.vector.centerOf(1, 1, 1), Pointing.DOWN).whileSneaking().rightClick()
                .withItem(CeiItems.ENCHANTING_GUIDE.asStack()), 40);
        scene.idle(50);
        scene.overlay.showText(40)
                .text("") // We do not use PonderLocalization. For registerText only
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector.topOf(1, 1, 1));
        scene.idle(40);
        scene.world.setBlock(util.grid.at(1,1,1), AllBlocks.BLAZE_BURNER.getDefaultState().setValue(BlazeBurnerBlock.HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.SMOULDERING),false);
        scene.overlay.showControls(new InputWindowElement(util.vector.centerOf(1, 1, 1), Pointing.DOWN).rightClick().withWrench().whileSneaking(), 40);
        scene.idle(40);*/
    }

    public static void stoveAndPot(SceneBuilder scene, SceneBuildingUtil util){
        scene.title("stove", ""); // We do not use PonderLocalization. For title only
        /*scene.configureBasePlate(0, 0, 8);
        scene.scaleSceneView(.60f);
        scene.world.setKineticSpeed(util.select.everywhere(), 0);
        scene.showBasePlate();
        scene.idle(5);
        scene.world.setKineticSpeed(util.select.everywhere(), 80F);
        scene.world.setKineticSpeed(util.select.fromTo(0,2,7,5,2,7), -80F);
        scene.world.setKineticSpeed(util.select.fromTo(7,2,2,7,2,7), -80F);
        scene.world.setBlock(util.grid.at(1,2,0),CeiBlocks.BLAZE_ENCHANTER.getDefaultState().setValue(BlazeEnchanterBlock.HEAT_LEVEL,
                BlazeEnchanterBlock.HeatLevel.KINDLED),false);
        scene.world.setBlock(util.grid.at(0,2,6),CeiBlocks.BLAZE_ENCHANTER.getDefaultState().setValue(BlazeEnchanterBlock.HEAT_LEVEL,
                BlazeEnchanterBlock.HeatLevel.KINDLED),false);
        scene.world.setBlock(util.grid.at(6,2,7),CeiBlocks.BLAZE_ENCHANTER.getDefaultState().setValue(BlazeEnchanterBlock.HEAT_LEVEL,
                BlazeEnchanterBlock.HeatLevel.KINDLED),false);
        scene.world.setBlock(util.grid.at(7,2,1),CeiBlocks.BLAZE_ENCHANTER.getDefaultState().setValue(BlazeEnchanterBlock.HEAT_LEVEL,
                BlazeEnchanterBlock.HeatLevel.KINDLED),false);
        scene.world.modifyTileEntity(util.grid.at(1,2,0), BlazeEnchanterBlockEntity.class, be-> {
            be.setTargetItem(enchantingGuide(Enchantments.MENDING,1));
            be.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(tank->
                    tank.fill(new FluidStack(CeiFluids.EXPERIENCE.get().getSource(), 1000), IFluidHandler.FluidAction.EXECUTE));
        });
        scene.world.modifyTileEntity(util.grid.at(0,2,6), BlazeEnchanterBlockEntity.class, be-> {
            be.setTargetItem(enchantingGuide(Enchantments.UNBREAKING,3));
            be.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(tank->
                    tank.fill(new FluidStack(CeiFluids.EXPERIENCE.get().getSource(), 1000), IFluidHandler.FluidAction.EXECUTE));
        });
        scene.world.modifyTileEntity(util.grid.at(6,2,7), BlazeEnchanterBlockEntity.class, be-> {
            be.setTargetItem(enchantingGuide(Enchantments.THORNS,1));
            be.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(tank->
                    tank.fill(new FluidStack(CeiFluids.EXPERIENCE.get().getSource(), 1000), IFluidHandler.FluidAction.EXECUTE));
        });
        scene.world.modifyTileEntity(util.grid.at(7,2,1), BlazeEnchanterBlockEntity.class, be-> {
            be.setTargetItem(enchantingGuide(Enchantments.ALL_DAMAGE_PROTECTION,3));
            be.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(tank->
                    tank.fill(new FluidStack(CeiFluids.EXPERIENCE.get().getSource(), 1000), IFluidHandler.FluidAction.EXECUTE));
        });
        scene.world.modifyTileEntity(util.grid.at(3,1,3), CreativeFluidTankTileEntity.class, be -> ((CreativeFluidTankTileEntity.CreativeSmartFluidTank) be.getTankInventory())
                .setContainedFluid(new FluidStack(CeiFluids.EXPERIENCE.get().getSource(), 1000)));
        // Must propagatePipeChange first or it won't work correctly
        scene.world.propagatePipeChange(util.grid.at(2,1, 2));
        scene.world.propagatePipeChange(util.grid.at(5,1, 2));
        scene.world.propagatePipeChange(util.grid.at(5,1, 5));
        scene.world.propagatePipeChange(util.grid.at(5,1, 2));
        scene.world.showSection(util.select.fromTo(0, 1, 0, 7, 4, 7), Direction.DOWN);

        scene.idle(5);
        List<ItemStack> items = Stream.of(Items.NETHERITE_CHESTPLATE,Items.DIAMOND_CHESTPLATE, Items.IRON_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.LEATHER_CHESTPLATE).map(Item::getDefaultInstance).toList();
        for(var item:items){
            BlockPos beltStart = util.grid.at(7, 2, 0);
            ElementLink<EntityElement> itemEntity = scene.world.createItemEntity(util.vector.centerOf(7, 5, 0), util.vector.of(0, 0, 0), item);
            scene.idle(13);
            scene.world.modifyEntity(itemEntity, Entity::discard);
            scene.world.createItemOnBelt(beltStart, Direction.DOWN, item);
            scene.idle(10);
        }

        scene.idle(400);*/
    }
}
