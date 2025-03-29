package plus.dragons.createcentralkitchen.foundation.ponder;

import net.createmod.ponder.api.registration.*;
import net.minecraft.resources.ResourceLocation;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.entry.CentralKitchenArmInterationTypes;

public class CCKPonderPlugin implements PonderPlugin {

	@Override
	public String getModId() {
		return CentralKitchen.ID;
	}


	@Override
	public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
		CentralKitchenPonders.registerScenes(helper);


	}


	@Override
	public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
		CentralKitchenPonders.registerTag(helper);
		CentralKitchenArmInterationTypes.registerPonderTags(helper);
	}

}