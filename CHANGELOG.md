## Create: Central Kitchen 2.6.0

This release requires Create 6.0.10, Create: Dragons Plus 1.11.4, and Farmer's Delight 1.3.2 or newer.

### Added
* Added optional Cultural Delights integration. Mechanical Arms can serve Eggplant Parmesan Feasts, preserve their block state, and atomically refresh the final serving with a matching whole feast.
* Added optional Rustic Delight integration. Mechanical Arms support every pancake flavor, including its non-linear serving states, matching-flavor refills, and clean removal after the final serving.
* Added optional Festive Delight integration. Mechanical Arms serve Festive Chicken through all four registered stages and leave the final leftover block in place.
* Added optional Hearth and Harvest integration. Mechanical Arms and Packagers can load Casks recipe-by-recipe, split repeated ingredients across input slots, wait for normal fermentation, and extract only finished outputs.
* Added dedicated Ponder scenes, Arm Targets and High Logistics entries.

### Fixed
* Native Create Filling and Emptying recipes now take priority over Brewin' and Chewin' Keg conversion fallbacks, including in JEI and after recipe reloads.
* Extra Delight recipe conversions no longer modify shared cached recipe results during fallback searches.
* Sawing recipes on Farmer's Delight Cutting Boards now use the intended processing duration.
* Cutting Board recipes no longer consume reusable tools that cannot take durability damage.
* Create boilers now respect removals from the `farmersdelight:heat_sources` tag.
* Create Packagers can now unpack ingredients into Extra Delight Ovens.
* Blaze Burner chef rendering now falls back safely when optional baked model assets are unavailable.

### Updated

* Updated NeoForge, Create: Dragons Plus, JEI, Curios, and the supported Delight integrations to their current 1.21.1 compatibility baselines.

### Localization

* Added Ukrainian localization by @Ch1sho.
