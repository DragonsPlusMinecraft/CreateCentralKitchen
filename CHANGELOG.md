## Create: Central Kitchen 2.6.2

Requires Create 6.0.10 and Create: Dragons Plus 1.11.9 or newer. Farmer's Delight integration requires Farmer's Delight 1.3.2 or newer.

### Added

* Packagers can now refill machines processing the same recipe with complete single-serving or multi-serving packages.
* Added batch unpacking for packages containing a whole number of servings of one recipe. Supported machines are Farmer's Delight Cooking Pots, Brewin' and Chewin' Kegs, Miner's Delight Copper Pots, Dungeon's Delight Monster Pots, Extra Delight Ovens and Chillers, and Hearth and Harvest Casks.

### Changed

* Unpacking keeps repeated ingredients in separate recipe slots and preserves existing slots and item components when refilling. Mixed recipes and incomplete or unbalanced input inventories are not accepted.
* Capacity is checked for every input slot against both the slot limit and the item's actual stack limit, including component overrides. If any slot would overflow, the entire package is rejected without inserting items or using extra slots to bypass the limit.
* Recipe candidates are cached and refreshed after recipe or tag reloads. Machines retain their existing fuel, fluid, container, mold, and output handling; auxiliary supplies can be provided as processing continues.
