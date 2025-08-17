package me.darragh.eamfhc.feature.property;

import lombok.experimental.UtilityClass;
import me.darragh.eamfhc.feature.Configurable;
import me.darragh.eamfhc.feature.property.builder.*;

/**
 * A factory class for creating various types of properties.
 * Provides static methods to create builders for different property types.
 *
 * @author darraghd493
 */
@UtilityClass
public class PropertyFactory {
    /**
     * Creates a builder for a {@link me.darragh.eamfhc.feature.property.type.BooleanProperty}.
     *
     * @param target The target configurable feature for which the property is being created.
     * @return A new instance of {@link BooleanPropertyBuilder} for building boolean properties.
     */
    public static BooleanPropertyBuilder booleanPropertyBuilder(Configurable target) {
        return new BooleanPropertyBuilder(target);
    }

    /**
     * Creates a builder for a {@link me.darragh.eamfhc.feature.property.type.enumerable.EnumProperty}.
     *
     * @param target The target configurable feature for which the property is being created.
     * @param enumClass The class of the enum.
     * @return A new instance of {@link EnumPropertyBuilder} for building enum properties.
     */
    public static <T extends Enum<T> & PropertyEnum> EnumPropertyBuilder<T> enumPropertyBuilder(Configurable target, Class<T> enumClass) {
        return new EnumPropertyBuilder<>(target, enumClass);
    }

    /**
     * Creates a builder for a {@link me.darragh.eamfhc.feature.property.type.number.FloatProperty}.
     *
     * @param target The target configurable feature for which the property is being created.
     * @return A new instance of {@link FloatPropertyBuilder} for building float properties.
     */
    public static FloatPropertyBuilder floatPropertyBuilder(Configurable target) {
        return new FloatPropertyBuilder(target);
    }

    /**
     * Creates a builder for a {@link me.darragh.eamfhc.feature.property.type.range.FloatRangeProperty}.
     *
     * @param target The target configurable feature for which the property is being created.
     * @return A new instance of {@link FloatRangePropertyBuilder} for building float range properties.
     */
    public static FloatRangePropertyBuilder floatRangePropertyBuilder(Configurable target) {
        return new FloatRangePropertyBuilder(target);
    }

    /**
     * Creates a builder for a {@link me.darragh.eamfhc.feature.property.type.number.IntegerProperty}.
     *
     * @param target The target configurable feature for which the property is being created.
     * @return A new instance of {@link IntegerPropertyBuilder} for building integer properties.
     */
    public static IntegerPropertyBuilder integerPropertyBuilder(Configurable target) {
        return new IntegerPropertyBuilder(target);
    }

    /**
     * Creates a builder for a {@link me.darragh.eamfhc.feature.property.type.range.IntegerRangeProperty}.
     *
     * @param target The target configurable feature for which the property is being created.
     * @return A new instance of {@link IntegerRangePropertyBuilder} for building integer range properties.
     */
    public static IntegerRangePropertyBuilder integerRangePropertyBuilder(Configurable target) {
        return new IntegerRangePropertyBuilder(target);
    }

    /**
     * Creates a builder for a {@link me.darragh.eamfhc.feature.property.type.LabelProperty}.
     *
     * @param target The target configurable feature for which the property is being created.
     * @return A new instance of {@link LabelPropertyBuilder} for building label properties.
     */
    public static LabelPropertyBuilder labelPropertyBuilder(Configurable target) {
        return new LabelPropertyBuilder(target);
    }

    /**
     * Creates a builder for a {@link me.darragh.eamfhc.feature.property.type.enumerable.MultiEnumProperty}.
     *
     * @param target The target configurable feature for which the property is being created.
     * @param enumClass The class of the enum.
     * @return A new instance of {@link MultiEnumPropertyBuilder} for building multi-enum properties.
     */
    public static <T extends Enum<T> & PropertyEnum> MultiEnumPropertyBuilder<T> multiEnumPropertyBuilder(Configurable target, Class<T> enumClass) {
        return new MultiEnumPropertyBuilder<>(target, enumClass);
    }
}
