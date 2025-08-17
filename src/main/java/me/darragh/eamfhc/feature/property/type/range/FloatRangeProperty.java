package me.darragh.eamfhc.feature.property.type.range;

import com.google.gson.JsonObject;
import me.darragh.eamfhc.feature.Configurable;
import me.darragh.eamfhc.feature.property.Property;
import me.darragh.eamfhc.feature.property.PropertyChangeObserver;
import me.darragh.eamfhc.feature.property.PropertyMetadata;
import me.darragh.eamfhc.feature.property.constraints.NumberPropertyConstraints;
import me.darragh.eamfhc.type.Range;
import me.darragh.math.util.MathUtil;

import java.util.function.BooleanSupplier;

/**
 * A property that represents an float number range.
 *
 * @author darraghd493
 */
public class FloatRangeProperty extends NumberRangeProperty<Float> {
    public FloatRangeProperty(PropertyMetadata metadata, NumberPropertyConstraints<Float> constraints, PropertyChangeObserver<Range<Float>> observer, Configurable target, Property<?, ?, ?> parent, BooleanSupplier visibility, Range<Float> defaultValue) {
        super(metadata, constraints, observer, target, parent, visibility, defaultValue);
    }

    @Override
    public void setValue(Range<Float> value) {
        super.setValue(new Range<>(
                this.clamp(value.getFrom()),
                this.clamp(value.getTo())
        ));
    }

    private float clamp(float value) {
        return MathUtil.clampFloat(
                MathUtil.stepFloat(value, this.getConstraints().getStep()),
                this.getConstraints().getMin(),
                this.getConstraints().getMax()
        );
    }

    @Override
    public void fromJson(JsonObject json) {
        if (json.has("value")) {
            JsonObject valueJson = json.getAsJsonObject("value");
            if (valueJson.has("from") && valueJson.has("to")) {
                this.setValue(
                        new Range<>(
                                valueJson.get("from").getAsFloat(),
                                valueJson.get("to").getAsFloat()
                        )
                );
            }
        }
    }
}