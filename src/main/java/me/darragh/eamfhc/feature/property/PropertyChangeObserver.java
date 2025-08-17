package me.darragh.eamfhc.feature.property;

/**
 * An observer interface for property changes.
 *
 * @param <T> The type of the property value.
 */
@FunctionalInterface
public interface PropertyChangeObserver<T> {
    void onPropertyChange(Property<T, ?, ?> property, T oldValue, T newValue);
}
