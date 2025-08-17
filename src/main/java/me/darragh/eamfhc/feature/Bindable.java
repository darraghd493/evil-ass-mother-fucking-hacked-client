package me.darragh.eamfhc.feature;

import me.darragh.eamfhc.feature.bindable.Bind;

/**
 * Represents a toggleable feature that can be bound to an input.
 *
 * @author darraghd493
 */
public interface Bindable {
    /**
     * Returns the bind associated with this feature.
     *
     * @return The bind for this feature.
     */
    Bind getBind();
}
