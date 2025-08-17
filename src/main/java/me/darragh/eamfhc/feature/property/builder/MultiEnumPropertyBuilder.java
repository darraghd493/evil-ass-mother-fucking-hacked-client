package me.darragh.eamfhc.feature.property.builder;

import me.darragh.eamfhc.feature.Configurable;
import me.darragh.eamfhc.feature.property.PropertyChangeObserver;
import me.darragh.eamfhc.feature.property.PropertyEnum;
import me.darragh.eamfhc.feature.property.constraints.EmptyPropertyConstraints;
import me.darragh.eamfhc.feature.property.type.enumerable.MultiEnumProperty;

import java.util.List;
import java.util.Objects;

/**
 * A builder for creating instances of {@link MultiEnumProperty}.
 * <p>
 * This class extends {@link SimplePropertyBuilder} and provides a specific implementation for boolean properties.
 *
 * @param <T> The type of the enum, must implement {@link PropertyEnum}.
 *
 * @author darraghd493
 */
public class MultiEnumPropertyBuilder<T extends Enum<T> & PropertyEnum> extends SimplePropertyBuilder<MultiEnumProperty<T>, List<T>, EmptyPropertyConstraints, PropertyChangeObserver<List<T>>> {
    private final Class<T> enumClass;

    public MultiEnumPropertyBuilder(Configurable target, Class<T> enumClass) {
        super(target);
        this.enumClass = enumClass;
        this.constraints = new EmptyPropertyConstraints();
    }

    @Override
    public MultiEnumProperty<T> build() {
        Objects.requireNonNull(this.metadata, "Metadata must be set");
        Objects.requireNonNull(this.visibility, "Visibility must be set");
        Objects.requireNonNull(this.defaultValue, "Default value must be set");
        Objects.requireNonNull(this.enumClass, "Enum class must be set");

        MultiEnumProperty<T> property = new MultiEnumProperty<>(this.metadata, this.constraints, this.observer, this.target, this.parent, this.visibility, this.defaultValue, this.enumClass);
        this.target.getPropertyManager().add(property);
        return property;
    }
}