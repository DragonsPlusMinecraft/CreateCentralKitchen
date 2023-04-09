package plus.dragons.createcentralkitchen.foundation.utility;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>An annotation for automatically load classes in different {@link Dist}s and when certain mod loaded.</p>
 * <p>Also provides the function of {@link net.minecraftforge.fml.common.Mod.EventBusSubscriber}.</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModLoadSubscriber {
    
    /**
     * Specify targets to load this event subscriber on. Can be used to avoid loading Client specific events
     * on a dedicated server, for example.
     *
     * @return an array of Dist to load this event subscriber on
     */
    Dist[] value() default { Dist.CLIENT, Dist.DEDICATED_SERVER };
    
    /**
     * Optional value, only necessary if this annotation is on the class for initialize mod integration contents.
     * Needed to prevent classloading of other mod's classes that might not be available at runtime.
     * @return a modid
     */
    String modid() default "";
    
    /**
     * Specify an alternative bus to listen to.
     * Unlike {@link net.minecraftforge.fml.common.Mod.EventBusSubscriber},
     * the default value is {@link Bus#MOD}, as this annotation is mostly used in mod setup.
     *
     * @return the bus you wish to listen to
     */
    Bus bus() default Bus.MOD;
    
}
