package plus.dragons.createcentralkitchen.modules.farmersdelight.content.curiosities.pie;

import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import plus.dragons.createcentralkitchen.CentralKitchen;
import plus.dragons.createcentralkitchen.modules.farmersdelight.FarmersDelightModule;
import vectorwing.farmersdelight.common.block.PieBlock;

public class PieBlockStateGen extends SpecialBlockStateGen {
    public static final PieBlockStateGen DEFAULT = new PieBlockStateGen(true);
    public static final PieBlockStateGen CUSTOM = new PieBlockStateGen(false);
    
    private static final ResourceLocation DEFAULT_PIE_BOTTOM = FarmersDelightModule.genRL("block/pie_bottom");
    private static final ResourceLocation DEFAULT_PIE_SIDE = FarmersDelightModule.genRL("block/pie_side");
    
    private final boolean useDefaultTexture;
    
    private PieBlockStateGen(boolean useDefaultTexture) {
        this.useDefaultTexture = useDefaultTexture;
    }
    
    @Override
    protected int getXRotation(BlockState state) {
        return 0;
    }
    
    @Override
    protected int getYRotation(BlockState state) {
        return horizontalAngle(state.getValue(PieBlock.FACING).getOpposite());
    }
    
    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov, BlockState state) {
        int bites = state.getValue(PieBlock.BITES);
        String modid = ctx.getId().getNamespace();
        String name = ctx.getName();
        String path = "block/" + name;
        String suffix = bites > 0 ? "_slice" + bites : "";
        BlockModelBuilder builder = prov.models()
            .withExistingParent(name + suffix, CentralKitchen.genRL("block/pie" + suffix))
            .texture("particle", new ResourceLocation(modid, path + "_top"))
            .texture("top", new ResourceLocation(modid, path + "_top"))
            .texture("bottom", useDefaultTexture
                ? DEFAULT_PIE_BOTTOM
                : new ResourceLocation(modid, path + "_bottom"))
            .texture("side", useDefaultTexture
                ? DEFAULT_PIE_SIDE
                : new ResourceLocation(modid, path + "_side"));
        if(bites > 0)
            builder.texture("inner", new ResourceLocation(modid, path + "_inner"));
        return builder;
    }
    
}
