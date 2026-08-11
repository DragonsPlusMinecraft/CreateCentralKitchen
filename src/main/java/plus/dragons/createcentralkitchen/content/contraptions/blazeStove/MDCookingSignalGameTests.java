package plus.dragons.createcentralkitchen.content.contraptions.blazeStove;

import com.sammy.minersdelight.content.block.copper_pot.CopperPotBlockEntity;
import com.sammy.minersdelight.setup.MDBlocks;
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
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;

@ModLoadSubscriber(modid = Mods.MD)
@PrefixGameTestTemplate(false)
public class MDCookingSignalGameTests {
    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void register(RegisterGameTestsEvent ignored) {
        if (Mods.isLoaded(Mods.FD))
            GameTestRegistry.register(MDCookingSignalGameTests.class);
    }

    @GameTest(templateNamespace = "create", template = "gametest/processing/iron_compacting")
    public static void copperPotCompletionSignalsBlazeStove(GameTestHelper helper) {
        var level = helper.getLevel();
        CookingSignalGameTestHelper.placeBlazeStove(helper);
        var potPos = helper.absolutePos(CookingSignalGameTestHelper.APPLIANCE_POS);
        var potState = MDBlocks.COPPER_POT.get().defaultBlockState();
        level.setBlock(potPos, potState, 3);
        BlockEntity blockEntity = level.getBlockEntity(potPos);
        helper.assertTrue(blockEntity instanceof CopperPotBlockEntity,
                "Expected a Miner's Delight copper pot block entity");
        CopperPotBlockEntity pot = (CopperPotBlockEntity) blockEntity;

        Recipe<?> loadedRecipe = level.getRecipeManager()
                .byKey(Mods.fd("cooking/beef_stew"))
                .orElseThrow();
        helper.assertTrue(loadedRecipe instanceof CookingPotRecipe,
                "Expected Farmer's Delight beef stew to be a cooking recipe");
        CookingPotRecipe recipe = (CookingPotRecipe) loadedRecipe;
        CookingSignalGameTestHelper.fillInputs(helper, pot.getInventory(), recipe.getIngredients());

        CopperPotBlockEntity.cookingTick(level, potPos, potState, pot);
        CompoundTag progress = new CompoundTag();
        pot.saveAdditional(progress);
        progress.putInt("CookTime", recipe.getCookTime() - 1);
        progress.putInt("CookTimeTotal", recipe.getCookTime());
        pot.load(progress);
        CopperPotBlockEntity.cookingTick(level, potPos, potState, pot);

        for (int slot = 0; slot < CopperPotBlockEntity.MEAL_DISPLAY_SLOT; slot++) {
            helper.assertTrue(pot.getInventory().getStackInSlot(slot).isEmpty(),
                    "The copper pot did not consume input slot " + slot);
        }
        boolean hasOutput = false;
        for (int slot = CopperPotBlockEntity.MEAL_DISPLAY_SLOT; slot < pot.getInventory().getSlots(); slot++) {
            hasOutput |= !pot.getInventory().getStackInSlot(slot).isEmpty();
        }
        helper.assertTrue(hasOutput,
                "The copper pot did not produce a meal or converted copper-cup serving");
        CookingSignalGameTestHelper.assertSignalScheduled(helper);
        helper.succeed();
    }
}
