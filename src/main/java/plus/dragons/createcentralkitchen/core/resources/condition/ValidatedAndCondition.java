package plus.dragons.createcentralkitchen.core.resources.condition;

import com.google.common.base.Joiner;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import plus.dragons.createcentralkitchen.CentralKitchen;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>An {@link IConditionSerializer} which stops further parsing
 * when previous parsed {@link ICondition} failed {@link ICondition#test(ICondition.IContext)}.</p>
 * <p>Useful for using {@link ICondition} from other mods that may not be available at runtime.</p>
 * @author LimonBlaze
 */
public class ValidatedAndCondition implements ICondition {
    private static final ResourceLocation ID = CentralKitchen.genRL("and");
    private final List<ICondition> children;
    
    public ValidatedAndCondition(List<ICondition> children) {
        this.children = children;
    }
    
    @Override
    public ResourceLocation getID()
    {
        return ID;
    }
    
    @SuppressWarnings("removal")
    @Override
    public boolean test() {
        return test(IContext.EMPTY);
    }
    
    @Override
    public boolean test(IContext context) {
        return !children.isEmpty();
    }
    
    @Override
    public String toString()
    {
        return Joiner.on(" && ").join(children);
    }
    
    public static class Serializer implements IConditionSerializer<ValidatedAndCondition> {
        
        @Override
        public void write(JsonObject json, ValidatedAndCondition value) {
            JsonArray values = new JsonArray();
            for (ICondition child : value.children) {
                values.add(CraftingHelper.serialize(child));
            }
            json.add("values", values);
        }
    
        @Override
        public ValidatedAndCondition read(JsonObject json) {
            List<ICondition> children = new ArrayList<>();
            for (JsonElement elements : GsonHelper.getAsJsonArray(json, "values")) {
                if (!elements.isJsonObject()) {
                    throw new JsonSyntaxException("And condition values must be an array of JsonObjects");
                }
                ICondition condition = CraftingHelper.getCondition(elements.getAsJsonObject());
                if (!condition.test(IContext.EMPTY)) {
                    children.clear();
                    break;
                } else {
                    children.add(condition);
                }
            }
            return new ValidatedAndCondition(children);
        }
    
        @Override
        public ResourceLocation getID() {
            return ID;
        }
        
    }
    
}
