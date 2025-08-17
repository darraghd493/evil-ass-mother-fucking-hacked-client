package me.darragh.eamfhc.feature;

import me.darragh.eamfhc.Identifiable;

/**
 * A base interface for storing metadata about features in the mod.
 *
 * @author darraghd493
 */
public interface FeatureMetadata extends Identifiable {
    /**
     * Returns the display name of the feature.
     *
     * @return The display name of the feature.
     */
    String getDisplayName();

    /**
     * Returns the description of the feature.
     *
     * @return The description of the feature.
     */
    String getDescription();
}
