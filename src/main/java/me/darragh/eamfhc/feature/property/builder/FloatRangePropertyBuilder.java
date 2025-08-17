package me.darragh.eamfhc.feature.property.builder;

import me.darragh.eamfhc.feature.Configurable;
import me.darragh.eamfhc.feature.property.PropertyChangeObserver;
import me.darragh.eamfhc.feature.property.constraints.NumberPropertyConstraints;
import me.darragh.eamfhc.feature.property.type.range.FloatRangeProperty;
import me.darragh.eamfhc.type.Range;

import java.util.Objects;

/**
 * A builder for creating instances of {@link FloatRangeProperty}.
 * <p>
 * This class extends {@link SimplePropertyBuilder} and provides a specific implementation for boolean properties.
 *
 * @author darraghd493
 */
public class FloatRangePropertyBuilder extends SimplePropertyBuilder<FloatRangeProperty, Range<Float>, NumberPropertyConstraints<Float>, PropertyChangeObserver<Range<Float>>> {
    public FloatRangePropertyBuilder(Configurable target) {
        super(target);
    }

    @Override
    public FloatRangeProperty build() {
        Objects.requireNonNull(this.metadata, "Metadata must be set");
        Objects.requireNonNull(this.constraints, "Constraints must be set");
        Objects.requireNonNull(this.visibility, "Visibility must be set");
        Objects.requireNonNull(this.defaultValue, "Default value must be set");

        FloatRangeProperty property = new FloatRangeProperty(this.metadata, this.constraints, this.observer, this.target, this.parent, this.visibility, this.defaultValue);
        this.target.getPropertyManager().add(property);
        return property;
    }
}