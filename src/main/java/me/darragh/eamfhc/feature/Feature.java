package me.darragh.eamfhc.feature;

import me.darragh.eamfhc.Destroyable;
import me.darragh.eamfhc.Initialisable;

/**
 * A marker interface for features in the mod.
 * Features can be anything that extends this interface,
 * such as commands, modules, or other functionalities
 * which are part of the mod's feature set.
 *
 * @author darraghd493
 */
public interface Feature<M extends FeatureMetadata> extends Initialisable, Destroyable {
    /**
     * Returns the metadata associated with this feature.
     *
     * @return The metadata of the feature.
     */
    M getMetadata();
}
