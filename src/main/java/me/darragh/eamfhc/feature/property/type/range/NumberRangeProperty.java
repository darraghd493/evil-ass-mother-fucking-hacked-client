package me.darragh.eamfhc.feature.property.type.range;

import com.google.gson.JsonObject;
import me.darragh.eamfhc.feature.Configurable;
import me.darragh.eamfhc.feature.property.Property;
import me.darragh.eamfhc.feature.property.PropertyChangeObserver;
import me.darragh.eamfhc.feature.property.PropertyMetadata;
import me.darragh.eamfhc.feature.property.constraints.NumberPropertyConstraints;
import me.darragh.eamfhc.type.Range;

import java.util.function.BooleanSupplier;

/**
 * A property that represents a (genetic) number range.
 *
 * @param <T> The type of number, must extend {@link Number}.
 *
 * @author darraghd493
 */
public abstract class NumberRangeProperty<T extends Number> extends Property<Range<T>, NumberPropertyConstraints<T>, PropertyChangeObserver<Range<T>>> {
    public NumberRangeProperty(PropertyMetadata metadata, NumberPropertyConstraints<T> constraints, PropertyChangeObserver<Range<T>> observer, Configurable target, Property<?, ?, ?> parent, BooleanSupplier visibility, Range<T> defaultValue) {
        super(metadata, constraints, observer, target, parent, visibility, defaultValue);
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.add("value", this.getValue().toJson());
        return object;
    }
}
