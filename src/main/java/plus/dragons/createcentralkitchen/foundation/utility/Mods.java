package plus.dragons.createcentralkitchen.foundation.utility;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

public class Mods {
    //Create and add-ons
    public static final String CREATE = "create";
    public static final String CA = "createaddition";
    //Farmer's Delight and add-ons
    public static final String FD = "farmersdelight";
    public static final String BNC = "brewinandchewin";
    public static final String FR = "farmersrespite";
    public static final String MD = "miners_delight";
    public static final String ND = "nethersdelight";
    public static final String ED = "ends_delight";
    public static final String CORN_DELIGHT = "corn_delight";
    //Abnormals' mods
    public static final String AD = "abnormals_delight";
    public static final String ATMOSPHERIC = "atmospheric";
    public static final String AUTUMNITY = "autumnity";
    public static final String BB = "buzzier_bees";
    public static final String ENVIRONMENTAL = "environmental";
    public static final String NEAPOLITAN = "neapolitan";
    public static final String UA = "upgrade_aquatic";
    //Abnormals' mods' add-ons
    public static final String RESPITEFUL = "respiteful";
    public static final String PECULIARS = "peculiars";
    public static final String SEASONALS = "seasonals";
    public static final String COOKIELICIOUS = "cookielicious";
    // Standole mods
    public static final String OF = "overweight_farming";
    //Utility mods
    public static final String JEI = "jei";
    public static final String CURIOS = "curios";
    public static final String APPLESKIN = "appleskin";
    
    public static boolean isLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }
    
    public static boolean isLoaded(String... modIds) {
        ModList modList = ModList.get();
        for (String modId : modIds)
            if (!ModList.get().isLoaded(modId))
                return false;
        return true;
    }
    
    public static ResourceLocation create(String path) {
        return new ResourceLocation(CREATE, path);
    }
    
    public static ResourceLocation ca(String path) {
        return new ResourceLocation(CA, path);
    }
    
    public static ResourceLocation fd(String path) {
        return new ResourceLocation(FD, path);
    }
    
    public static ResourceLocation bnc(String path) {
        return new ResourceLocation(BNC, path);
    }
    
    public static ResourceLocation fr(String path) {
        return new ResourceLocation(FR, path);
    }
    
    public static ResourceLocation md(String path) {
        return new ResourceLocation(MD, path);
    }
    
    public static ResourceLocation nd(String path) {
        return new ResourceLocation(ND, path);
    }
    
    public static ResourceLocation ad(String path) {
        return new ResourceLocation(AD, path);
    }
    
    public static ResourceLocation atmospheric(String path) {
        return new ResourceLocation(ATMOSPHERIC, path);
    }
    
    public static ResourceLocation autumnity(String path) {
        return new ResourceLocation(AUTUMNITY, path);
    }
    
    public static ResourceLocation bb(String path) {
        return new ResourceLocation(BB, path);
    }
    
    public static ResourceLocation environmental(String path) {
        return new ResourceLocation(ENVIRONMENTAL, path);
    }
    
    public static ResourceLocation neapolitan(String path) {
        return new ResourceLocation(NEAPOLITAN, path);
    }
    
    public static ResourceLocation ua(String path) {
        return new ResourceLocation(UA, path);
    }
    
    public static ResourceLocation respiteful(String path) {
        return new ResourceLocation(RESPITEFUL, path);
    }
    
    public static ResourceLocation peculiars(String path) {
        return new ResourceLocation(PECULIARS, path);
    }
    
    public static ResourceLocation seasonals(String path) {
        return new ResourceLocation(SEASONALS, path);
    }
    
    public static ResourceLocation cookielicious(String path) {
        return new ResourceLocation(COOKIELICIOUS, path);
    }
    
    public static ResourceLocation jei(String path) {
        return new ResourceLocation(JEI, path);
    }
    
    public static ResourceLocation curios(String path) {
        return new ResourceLocation(CURIOS, path);
    }
    
    public static ResourceLocation appleskin(String path) {
        return new ResourceLocation(APPLESKIN, path);
    }

}
