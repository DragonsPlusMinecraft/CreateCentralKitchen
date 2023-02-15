package plus.dragons.createcentralkitchen.core.modules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModModule {
    
    /**
     * The id of the module, should be unique for each mod.
     * @return a string, preferably in modid format.
     */
    String id();
    
    /**
     * Optional, the mod dependencies required the module. <br>
     * The module will only load if all dependencies are satisfied.
     * @return a list of modid.
     */
    String[] dependencies() default {};
    
    /**
     * Optional, the priority of the module, smaller values stands for earlier load stage. <br>
     * If a module depends on other modules, it's priority should be lower than every one of them.
     * @return an integer representing the priority.
     */
    int priority() default 0;
    
}
