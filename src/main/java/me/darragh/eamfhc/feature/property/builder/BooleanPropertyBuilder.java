package me.darragh.eamfhc.feature.property.builder;

import me.darragh.eamfhc.feature.Configurable;
import me.darragh.eamfhc.feature.property.PropertyChangeObserver;
import me.darragh.eamfhc.feature.property.constraints.EmptyPropertyConstraints;
import me.darragh.eamfhc.feature.property.type.BooleanProperty;

/**
 * A builder for creating instances of {@link BooleanProperty}.
 * <p>
 * This class extends {@link SimplePropertyBuilder} and provides a specific implementation for boolean properties.
 *
 * @author darraghd493
 */
public class BooleanPropertyBuilder extends SimplePropertyBuilder<BooleanProperty, Boolean, EmptyPropertyConstraints, PropertyChangeObserver<Boolean>> {
    public BooleanPropertyBuilder(Configurable target) {
        super(target);
        this.constraints = new EmptyPropertyConstraints();
    }

    @Override
    public BooleanProperty build() {
        BooleanProperty property = new BooleanProperty(this.metadata, this.constraints, this.observer, this.target, this.parent, this.visibility, this.defaultValue);
        this.target.getPropertyManager().add(property);
        return property;
    }
}
