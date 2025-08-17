package me.darragh.eamfhc.feature.property.type.number;

import com.google.gson.JsonObject;
import me.darragh.eamfhc.feature.Configurable;
import me.darragh.eamfhc.feature.property.Property;
import me.darragh.eamfhc.feature.property.PropertyChangeObserver;
import me.darragh.eamfhc.feature.property.PropertyMetadata;
import me.darragh.eamfhc.feature.property.constraints.NumberPropertyConstraints;

import java.util.function.BooleanSupplier;

/**
 * A property that represents a (genetic) number.
 *
 * @param <T> The type of number, must extend {@link Number}.
 *
 * @author darraghd493
 */
public abstract class NumberProperty<T extends Number> extends Property<T, NumberPropertyConstraints<T>, PropertyChangeObserver<T>> {
    public NumberProperty(PropertyMetadata metadata, NumberPropertyConstraints<T> constraints, PropertyChangeObserver<T> observer, Configurable target, Property<?, ?, ?> parent, BooleanSupplier visibility, T defaultValue) {
        super(metadata, constraints, observer, target, parent, visibility, defaultValue);
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("value", this.getValue());
        return object;
    }
}
