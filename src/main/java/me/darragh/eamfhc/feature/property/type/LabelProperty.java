package me.darragh.eamfhc.feature.property.type;

import com.google.gson.JsonObject;
import me.darragh.eamfhc.feature.Configurable;
import me.darragh.eamfhc.feature.property.Property;
import me.darragh.eamfhc.feature.property.PropertyChangeObserver;
import me.darragh.eamfhc.feature.property.PropertyMetadata;
import me.darragh.eamfhc.feature.property.constraints.EmptyPropertyConstraints;

import java.util.function.BooleanSupplier;

/**
 * A property that serves only as a label, without any associated value.
 *
 * @author darraghd493
 */
public class LabelProperty extends Property<String, EmptyPropertyConstraints, PropertyChangeObserver<String>> {
    public LabelProperty(PropertyMetadata metadata, EmptyPropertyConstraints constraints, PropertyChangeObserver<String> observer, Configurable target, Property<?, ?, ?> parent, BooleanSupplier visibility, String defaultValue) {
        super(metadata, constraints, observer, target, parent, visibility, defaultValue);
    }

    @Override
    public JsonObject toJson() {
        return new JsonObject();
    }

    @Override
    public void fromJson(JsonObject json) {
        // no-op
    }
}
