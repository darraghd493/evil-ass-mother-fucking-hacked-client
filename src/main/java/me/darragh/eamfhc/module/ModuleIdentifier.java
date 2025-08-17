package me.darragh.eamfhc.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to identify a class as a module, used for real-time scanning.
 *
 * @see me.darragh.eamfhc.Identifiable
 * @see me.darragh.eamfhc.feature.FeatureMetadata
 * @see ModuleMetadata
 *
 * @author darraghd493
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleIdentifier {
    /**
     * Unique identifier for the module.
     *
     * @return The identifier string.
     */
    String identifier();

    /**
     * Display name for the module.
     *
     * @return The display name string.
     */
    String displayName();

    /**
     * Description of the module.
     *
     * @return The description string.
     */
    String description();

    /**
     * Type of the module.
     *
     * @return The module type.
     */
    ModuleType type();

    /**
     * The default keyboard keybind for the module.
     *
     * @return The default keyboard keybind keycode.
     */
    int defaultKeybind() default -1;
}
