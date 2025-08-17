package me.darragh.eamfhc.feature;

import me.darragh.eamfhc.feature.property.PropertyManager;

/**
 * Represents a user configurable feature.
 *
 * @author darraghd493
 */
public interface Configurable {
    /**
     * Returns the property manager associated with this Configurable feature.
     *
     * @return The property manager for this feature
     */
    PropertyManager getPropertyManager();
}
