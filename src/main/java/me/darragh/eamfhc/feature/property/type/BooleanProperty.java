package me.darragh.eamfhc.feature.property.type;

import com.google.gson.JsonObject;
import me.darragh.eamfhc.feature.Configurable;
import me.darragh.eamfhc.feature.property.Property;
import me.darragh.eamfhc.feature.property.PropertyChangeObserver;
import me.darragh.eamfhc.feature.property.PropertyMetadata;
import me.darragh.eamfhc.feature.property.constraints.EmptyPropertyConstraints;

import java.util.function.BooleanSupplier;

/**
 * A property that holds a singular boolean value.
 *
 * @author darraghd493
 */
public class BooleanProperty extends Property<Boolean, EmptyPropertyConstraints, PropertyChangeObserver<Boolean>> {
    public BooleanProperty(PropertyMetadata metadata, EmptyPropertyConstraints constraints, PropertyChangeObserver<Boolean> observer, Configurable target, Property<?, ?, ?> parent, BooleanSupplier visibility, Boolean defaultValue) {
        super(metadata, constraints, observer, target, parent, visibility, defaultValue);
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("value", this.getValue());
        return json;
    }

    @Override
    public void fromJson(JsonObject json) {
        if (json.has("value")) {
            this.setValue(json.get("value").getAsBoolean());
        }
    }
}
