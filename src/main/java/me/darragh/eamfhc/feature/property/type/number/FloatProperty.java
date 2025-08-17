package me.darragh.eamfhc.feature.property.type.number;

import com.google.gson.JsonObject;
import me.darragh.eamfhc.feature.Configurable;
import me.darragh.eamfhc.feature.property.Property;
import me.darragh.eamfhc.feature.property.PropertyChangeObserver;
import me.darragh.eamfhc.feature.property.PropertyMetadata;
import me.darragh.eamfhc.feature.property.constraints.NumberPropertyConstraints;
import me.darragh.math.util.MathUtil;

import java.util.function.BooleanSupplier;

/**
 * A property that represents a float number.
 *
 * @author darraghd493
 */
public class FloatProperty extends NumberProperty<Float> {
    public FloatProperty(PropertyMetadata metadata, NumberPropertyConstraints<Float> constraints, PropertyChangeObserver<Float> observer, Configurable target, Property<?, ?, ?> parent, BooleanSupplier visibility, Float defaultValue) {
        super(metadata, constraints, observer, target, parent, visibility, defaultValue);
    }

    @Override
    public void setValue(Float value) {
        super.setValue(
                MathUtil.clampFloat(
                        MathUtil.stepFloat(value, this.getConstraints().getStep()),
                        this.getConstraints().getMin(),
                        this.getConstraints().getMax()
                )
        );
    }

    @Override
    public void fromJson(JsonObject json) {
        if (json.has("value")) {
            this.setValue(json.get("value").getAsFloat());
        }
    }
}
