package me.darragh.eamfhc.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to identify a class as a processor, used for real-time scanning.
 *
 * @see me.darragh.eamfhc.Identifiable
 * @see me.darragh.eamfhc.feature.FeatureMetadata
 * @see me.darragh.eamfhc.processor.ProcessorMetadata
 *
 * @author darraghd493
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ProcessorIdentifier {
    /**
     * Unique identifier for the processor.
     *
     * @return The identifier string.
     */
    String identifier();

    /**
     * Display name for the processor.
     *
     * @return The display name string.
     */
    String displayName();

    /**
     * Description of the processor.
     *
     * @return The description string.
     */
    String description();

    /**
     * Type of the processor.
     *
     * @return The processor type.
     */
    ProcessorType type();
}
