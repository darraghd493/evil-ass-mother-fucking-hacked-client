package me.darragh.eamfhc.feature;

/**
 * Represents a feature that can be toggled on or off.
 * <p>
 * This interface provides methods to enable, disable, and toggle the state of the feature.
 *
 * @author darraghd493
 */
public interface Toggleable {
    /**
     * Enables the feature, does nothing if it is already enabled.
     */
    default void enable() {
        this.setEnabled(true);
    }

    /**
     * Disables the feature, does nothing if it is already disabled.
     */
    default void disable() {
        this.setEnabled(false);
    }

    /**
     * Toggles the feature's state. If it is enabled, it will be disabled, and vice versa.
     */
    default void toggle() {
        this.setEnabled(!this.isEnabled());
    }

    /**
     * Checks if the feature is currently enabled.
     *
     * @return True if the feature is enabled, false otherwise.
     */
    boolean isEnabled();

    /**
     * Sets the enabled state of the feature.
     *
     * @param enabled True to enable the feature, false to disable it.
     */
    void setEnabled(boolean enabled);
}
