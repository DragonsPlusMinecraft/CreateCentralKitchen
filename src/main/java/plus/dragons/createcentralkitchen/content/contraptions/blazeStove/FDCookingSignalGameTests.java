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
import plus.dragons.createcentralkitchen.foundation.utility.ModLoadSubscriber;
import plus.dragons.createcentralkitchen.foundation.utility.Mods;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
import vectorwing.farmersdelight.common.registry.ModBlocks;

@ModLoadSubscriber(modid = Mods.FD)
@PrefixGameTestTemplate(false)
public class FDCookingSignalGameTests {
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        GameTestRegistry.register(FDCookingSignalGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void cookingPotCompletionSignalsBlazeStove(GameTestHelper helper) {
        var level = helper.getLevel();
        CookingSignalGameTestHelper.placeBlazeStove(helper);
        var potPos = helper.absolutePos(CookingSignalGameTestHelper.APPLIANCE_POS);
        var potState = ModBlocks.COOKING_POT.get().defaultBlockState();
        level.setBlock(potPos, potState, 3);
        BlockEntity blockEntity = level.getBlockEntity(potPos);
        helper.assertTrue(blockEntity instanceof CookingPotBlockEntity,
                "Expected a Farmer's Delight cooking pot block entity");
        CookingPotBlockEntity pot = (CookingPotBlockEntity) blockEntity;

        Recipe<?> loadedRecipe = level.getRecipeManager()
                .byKey(Mods.fd("cooking/beef_stew"))
                .orElseThrow();
        helper.assertTrue(loadedRecipe instanceof CookingPotRecipe,
                "Expected Farmer's Delight beef stew to be a cooking recipe");
        CookingPotRecipe recipe = (CookingPotRecipe) loadedRecipe;
        CookingSignalGameTestHelper.fillInputs(helper, pot.getInventory(), recipe.getIngredients());

        BlazeStoveBlock.notifyCookingComplete(null, potPos);
        helper.assertTrue(!CookingSignalGameTestHelper.isSignalScheduled(helper),
                "A completion notification without a level scheduled a signal");
        CookingPotBlockEntity.cookingTick(level, potPos, potState, pot);
        CompoundTag progress = new CompoundTag();
        pot.saveAdditional(progress);
        progress.putInt("CookTime", recipe.getCookTime() - 1);
        progress.putInt("CookTimeTotal", recipe.getCookTime());
        pot.load(progress);
        CookingPotBlockEntity.cookingTick(level, potPos, potState, pot);

        helper.assertTrue(CookingSignalGameTestHelper.containsItem(
                pot.getInventory(), recipe.getResultItem(level.registryAccess())),
                "The cooking pot did not produce the expected meal");
        CookingSignalGameTestHelper.assertSignalScheduled(helper);
        helper.succeed();
    }
}
