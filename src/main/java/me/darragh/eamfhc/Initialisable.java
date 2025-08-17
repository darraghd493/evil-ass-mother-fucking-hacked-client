package me.darragh.eamfhc;

/**
 * Represents a component that must be initialised before usage.
 *
 * @author darraghd493
 */
public interface Initialisable {
    /**
     * Preforms initialisation logic.
     * This method should be called during the startup of the client.
     */
    void init();
}
