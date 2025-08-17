package me.darragh.eamfhc.feature.property;

import lombok.Data;
import me.darragh.eamfhc.Serialisable;
import me.darragh.eamfhc.feature.Configurable;

import java.util.function.BooleanSupplier;

/**
 * The base class for all properties.
 *
 * @param <T> The type of the property value.
 * @param <C> The type of the property constraints.
 * @param <O> The type of the property change observer.
 */
@Data
public abstract class Property<T, C extends PropertyConstraints, O extends PropertyChangeObserver<T>> implements Serialisable {
    private final PropertyMetadata metadata;
    private final C constraints;
    private final O observer;
    private final Configurable target;
    private final Property<?, ?, ?> parent;
    private final BooleanSupplier visibility;
    private final T defaultValue;
    private T value;

    public Property(PropertyMetadata metadata, C constraints, O observer, Configurable target, Property<?, ?, ?> parent, BooleanSupplier visibility, T defaultValue) {
        this.metadata = metadata;
        this.constraints = constraints;
        this.observer = observer;
        this.target = target;
        this.parent = parent;
        this.visibility = visibility;
        this.defaultValue = this.value = defaultValue;
    }

    /**
     * Sets the value of the property and notifies the observer of the change.
     *
     * @param value The new value to set.
     */
    public void setValue(T value) {
        if (this.getObserver() != null) {
            this.getObserver().onPropertyChange(this, this.getValue(), value);
        }
        this.value = value;
    }

    /**
     * Determines whether the property is visible based on its parent's visibility and its own visibility condition.
     *
     * @return True if the property is visible, false otherwise.
     */
    public boolean isVisible() {
        return (this.parent == null || this.parent.isVisible()) && this.visibility.getAsBoolean();
    }
}
