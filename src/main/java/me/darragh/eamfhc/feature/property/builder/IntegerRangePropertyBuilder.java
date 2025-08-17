package me.darragh.eamfhc.feature.property.builder;

import me.darragh.eamfhc.feature.Configurable;
import me.darragh.eamfhc.feature.property.PropertyChangeObserver;
import me.darragh.eamfhc.feature.property.constraints.NumberPropertyConstraints;
import me.darragh.eamfhc.feature.property.type.range.IntegerRangeProperty;
import me.darragh.eamfhc.type.Range;

import java.util.Objects;

/**
 * A builder for creating instances of {@link IntegerRangeProperty}.
 * <p>
 * This class extends {@link SimplePropertyBuilder} and provides a specific implementation for boolean properties.
 *
 * @author darraghd493
 */
public class IntegerRangePropertyBuilder extends SimplePropertyBuilder<IntegerRangeProperty, Range<Integer>, NumberPropertyConstraints<Integer>, PropertyChangeObserver<Range<Integer>>> {
    public IntegerRangePropertyBuilder(Configurable target) {
        super(target);
    }

    @Override
    public IntegerRangeProperty build() {
        Objects.requireNonNull(this.metadata, "Metadata must be set");
        Objects.requireNonNull(this.constraints, "Constraints must be set");
        Objects.requireNonNull(this.visibility, "Visibility must be set");
        Objects.requireNonNull(this.defaultValue, "Default value must be set");

        IntegerRangeProperty property = new IntegerRangeProperty(this.metadata, this.constraints, this.observer, this.target, this.parent, this.visibility, this.defaultValue);
        this.target.getPropertyManager().add(property);
        return property;
    }
}