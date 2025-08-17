package me.darragh.eamfhc.feature.property.constraints;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import me.darragh.eamfhc.feature.property.PropertyConstraints;

/**
 * Represents a number bound set of property constraints.
 * This is specifically used for properties that hold numeric values.
 *
 * @see me.darragh.eamfhc.feature.property.type.number.NumberProperty
 * @see me.darragh.eamfhc.feature.property.type.range.NumberRangeProperty
 *
 * @author darraghd493
 */
@Value
@RequiredArgsConstructor
public class NumberPropertyConstraints<T extends Number> implements PropertyConstraints {
    T min, max, step;
}
