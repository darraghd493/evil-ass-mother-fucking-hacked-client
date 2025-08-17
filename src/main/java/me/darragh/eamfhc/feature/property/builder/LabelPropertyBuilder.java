package me.darragh.eamfhc.feature.property.builder;

import me.darragh.eamfhc.feature.Configurable;
import me.darragh.eamfhc.feature.property.PropertyChangeObserver;
import me.darragh.eamfhc.feature.property.constraints.EmptyPropertyConstraints;
import me.darragh.eamfhc.feature.property.type.LabelProperty;

import java.util.Objects;

/**
 * A builder for creating instances of {@link LabelProperty}.
 * <p>
 * This class extends {@link SimplePropertyBuilder} and provides a specific implementation for boolean properties.
 *
 * @author darraghd493
 */
public class LabelPropertyBuilder extends SimplePropertyBuilder<LabelProperty, String, EmptyPropertyConstraints, PropertyChangeObserver<String>> {
    public LabelPropertyBuilder(Configurable target) {
        super(target);
        this.constraints = new EmptyPropertyConstraints();
    }

    @Override
    public LabelProperty build() {
        Objects.requireNonNull(this.metadata, "Metadata must be set");
        Objects.requireNonNull(this.visibility, "Visibility must be set");
        if (this.defaultValue != null) {
            Objects.requireNonNull(this.defaultValue, "Default value must not be set");
        }

        LabelProperty property = new LabelProperty(this.metadata, this.constraints, this.observer, this.target, this.parent, this.visibility, this.defaultValue);
        this.target.getPropertyManager().add(property);
        return property;
    }
}