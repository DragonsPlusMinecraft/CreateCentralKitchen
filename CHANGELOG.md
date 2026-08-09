## Create: Central Kitchen 2.6.0

This release requires Create 6.0.10, Create: Dragons Plus 1.11.4, and Farmer's Delight 1.3.2 or newer.

### Fixed

* Extra Delight recipe conversions no longer modify shared cached recipe results during fallback searches.
* Sawing recipes on Farmer's Delight Cutting Boards now use the intended processing duration.
* Cutting Board recipes no longer consume reusable tools that cannot take durability damage.
* Create boilers now respect removals from the `farmersdelight:heat_sources` tag.
* Create Packagers can now unpack ingredients into Extra Delight Ovens.
* Blaze Burner chef rendering now falls back safely when optional baked model assets are unavailable.

### Updated

* Updated NeoForge, Create: Dragons Plus, JEI, Curios, and the supported Delight integrations.
* Added automated coverage for Packager interactions with Farmer's Delight Cooking Pots and Extra Delight appliances.

### Localization

* Added Ukrainian localization by @Ch1sho.
