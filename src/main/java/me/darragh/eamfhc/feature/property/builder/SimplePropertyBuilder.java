package me.darragh.eamfhc.feature.property.builder;

import me.darragh.eamfhc.builder.Builder;
import me.darragh.eamfhc.feature.Configurable;
import me.darragh.eamfhc.feature.property.Property;
import me.darragh.eamfhc.feature.property.PropertyChangeObserver;
import me.darragh.eamfhc.feature.property.PropertyConstraints;
import me.darragh.eamfhc.feature.property.PropertyMetadata;

import java.util.function.BooleanSupplier;

/**
 * Provides a base implementation for building properties with common attributes.
 *
 * @param <P> The type of the property being built.
 * @param <T> The type of the property value.
 * @param <C> The type of the property constraints.
 * @param <O> The type of the property change observer.
 */
public abstract class SimplePropertyBuilder<P extends Property<T, C, O>, T, C extends PropertyConstraints, O extends PropertyChangeObserver<T>> implements Builder<P> {
    protected final Configurable target;
    protected PropertyMetadata metadata;
    protected C constraints;
    protected O observer;
    protected Property<?, ?, ?> parent = null;
    protected BooleanSupplier visibility = () -> true;
    protected T defaultValue;

    public SimplePropertyBuilder(Configurable target) {
        this.target = target;
    }

    /**
     * Sets the metadata for the property being built.
     *
     * @param metadata The metadata to associate with the property.
     * @return this
     */
    public SimplePropertyBuilder<P, T, C, O> metadata(PropertyMetadata metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * Sets the constraints for the property being built.
     *
     * @param constraints The constraints to apply to the property.
     * @return this
     */
    public SimplePropertyBuilder<P, T, C, O> constraints(C constraints) {
        this.constraints = constraints;
        return this;
    }

    /**
     * Sets the observer for the property being built.
     *
     * @param observer The observer to notify on property changes.
     * @return this
     */
    public SimplePropertyBuilder<P, T, C, O> observer(O observer) {
        this.observer = observer;
        return this;
    }

    /**
     * Sets the parent property for the property being built.
     *
     * @param parent The parent property, if any.
     * @return this
     */
    public SimplePropertyBuilder<P, T, C, O> parent(Property<?, ?, ?> parent) {
        this.parent = parent;
        return this;
    }

    /**
     * Sets the visibility condition for the property being built.
     *
     * @param visibility A supplier that returns true if the property should be visible.
     * @return this
     */
    public SimplePropertyBuilder<P, T, C, O> visibility(BooleanSupplier visibility) {
        this.visibility = visibility;
        return this;
    }

    /**
     * Sets the default value for the property being built.
     *
     * @param defaultValue The default value to set for the property.
     * @return this
     */
    public SimplePropertyBuilder<P, T, C, O> defaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }
}
