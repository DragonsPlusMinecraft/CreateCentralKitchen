## Create Central Kitchen 1.5.0

Final maintenance release for the Minecraft 1.20.1 line, updated for Create 6.0.8.

### Update

* Updated Forge, Create, Farmer's Delight, JEI, and the complete 1.20.1 integration dependency stack.
* Added the sequenced assembly recipe for Miner's Delight's Vegan Hamburger.
* Migrated deprecated Forge APIs and replaced the heat-source coremod transformer with native block-state handling.
* Reduced `dragonLibLegacy` to the single fluid type helper still required by registered fluids.
* Removed obsolete data generation code, stale mod identifiers, retired pie content, and retired Brewing Guide assets.
* Added validation for manually maintained JSON resources, translations, built-in packs, and conditional recipes.
* Isolated optional integrations and client-only JEI hooks so reduced mod sets can start safely.

### Recipes and Data Packs

* Restored missing processing recipes for Atmospheric, Autumnity, Corn Delight, End's Delight, Farmer's Delight, Farmer's Respite, Collector's Reap, Peculiars, Respiteful, Seasonals, and Upgrade Aquatic.
* Restored missing recipe unlock advancements for the manually maintained built-in packs.
* Restored the Blaze Stove loot table so breaking it returns the Blaze Burner.
* Restored missing Farmer's Delight sequenced assemblies, fixed the Hamburger assembly, and corrected the Mutton Wrap recipe identifier.
* Corrected the stale Farmer's Delight wheat dough override and conditional cross-mod recipe loading.
* Corrected registered pie items in the creative tab and removed recipes and assets for content no longer registered on 1.20.1.

### Fix

* Fixed Create contraptions not harvesting Atmospheric oranges (#127).
* Fixed mechanical harvesters not resetting Collector's Reap bushes, both Farmer's Respite coffee branches, Snowy Spirit ginger, Corn Delight corn, Overweight Farming crops, Nether's Delight propelpearls, and Propelplant crops.
* Fixed harvesting behavior not honoring Corn Delight and Propelplant replant configuration.
* Fixed guided cooking item duplication and invalid guide or shapeless slot checks.
* Fixed Blaze Stove guide sync accepting invalid, stale, legacy, or out-of-range client data.
* Fixed JEI ghost submissions bypassing Blaze Stove validation and enabled Miner's Cooking Guide ghost inputs.
* Fixed simulated guide deposits mutating inventory and fixed item-handler views exposing slots outside their declared range.
* Fixed guide items losing metadata and Blaze Stoves retaining stale guide data.
* Fixed delayed client sync causing Blaze Stove client crashes.
* Fixed cooking inputs being lost when recipes disappear and fixed only one burned cooking slot being reset.
* Fixed Blaze Stoves signaling block updates unsafely after cooking.
* Fixed Blaze Burner state being lost while converting to a Blaze Stove.
* Fixed custom configuration list validators and registry-backed lists not applying during initial config load.
* Corrected the French translation for the incomplete Mutton Wrap.
