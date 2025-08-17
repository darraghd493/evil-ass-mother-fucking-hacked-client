package me.darragh.eamfhc.feature.property;

import lombok.experimental.UtilityClass;
import me.darragh.eamfhc.feature.Configurable;
import me.darragh.eamfhc.feature.property.builder.BooleanPropertyBuilder;

/**
 * A factory class for creating various types of properties.
 * Provides static methods to create builders for different property types.
 *
 * @author darraghd493
 */
@UtilityClass
public class PropertyFactory {
    /**
     * Creates a builder for a boolean property.
     *
     * @param target The target configurable feature for which the property is being created.
     * @return A new instance of {@link BooleanPropertyBuilder} for building boolean properties.
     */
    public static BooleanPropertyBuilder booleanPropertyBuilder(Configurable target) {
        return new BooleanPropertyBuilder(target);
    }
}
