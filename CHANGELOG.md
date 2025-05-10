### Create: Central Kitchen 2.1.0

#### Features
- Added Ponder Scenes for Farmer's Delight and Brewin' and Chewin
- Added Tankard to `create:upright_on_belt`.
- Removed the behaviour Block Item without 3D model render upright on belt due to affecting too many items,
now adds `#c:foods/edible_when_placed` to `create:upright_on_belt` as a safe replacement.

#### Fixes
- Fixed Skillet not a valid Mechanical Arm target