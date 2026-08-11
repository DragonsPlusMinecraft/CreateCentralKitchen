package plus.dragons.createcentralkitchen.content.contraptions.blazeStove;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.RegisterGameTestsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import umpaz.farmersrespite.common.block.entity.KettleBlockEntity;
import umpaz.farmersrespite.common.crafting.KettleRecipe;
import umpaz.farmersrespite.common.registry.FRBlocks;

@ModLoadSubscriber(modid = Mods.FR)
@PrefixGameTestTemplate(false)
public class FRCookingSignalGameTests {
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        if (Mods.isLoaded(Mods.FD))
            GameTestRegistry.register(FRCookingSignalGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void kettleCompletionSignalsBlazeStove(GameTestHelper helper) {
        var level = helper.getLevel();
        CookingSignalGameTestHelper.placeBlazeStove(helper);
        var kettlePos = helper.absolutePos(CookingSignalGameTestHelper.APPLIANCE_POS);
        var kettleState = FRBlocks.KETTLE.get().defaultBlockState();
        level.setBlock(kettlePos, kettleState, 3);
        BlockEntity blockEntity = level.getBlockEntity(kettlePos);
        helper.assertTrue(blockEntity instanceof KettleBlockEntity,
                "Expected a Farmer's Respite kettle block entity");
        KettleBlockEntity kettle = (KettleBlockEntity) blockEntity;

        Recipe<?> loadedRecipe = level.getRecipeManager()
                .byKey(CentralKitchen.genRL("brewing/builders_tea"))
                .orElseThrow();
        helper.assertTrue(loadedRecipe instanceof KettleRecipe,
                "Expected builder's tea to be a kettle recipe");
        KettleRecipe recipe = (KettleRecipe) loadedRecipe;
        CookingSignalGameTestHelper.fillInputs(helper, kettle.getInventory(), recipe.getIngredients());
        kettle.getFluidTank().setFluid(recipe.getFluidIn().copy());

        KettleBlockEntity.brewingTick(level, kettlePos, kettleState, kettle);
        CompoundTag progress = new CompoundTag();
        kettle.saveAdditional(progress);
        progress.putInt("BrewTime", recipe.getBrewTime() - 1);
        progress.putInt("BrewTimeTotal", recipe.getBrewTime());
        kettle.load(progress);
        KettleBlockEntity.brewingTick(level, kettlePos, kettleState, kettle);

        var brewedFluid = kettle.getFluidTank().getFluid();
        helper.assertTrue(brewedFluid.getFluid().isSame(recipe.getFluidOut().getFluid())
                && brewedFluid.getAmount() == recipe.getFluidOut().getAmount(),
                "The kettle did not produce the expected fluid");
        CookingSignalGameTestHelper.assertSignalScheduled(helper);
        helper.succeed();
    }
}
