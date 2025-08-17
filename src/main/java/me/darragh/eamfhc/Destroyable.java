package me.darragh.eamfhc;

/**
 * Represents a component that can be destroyed after usage.
 *
 * @author darraghd493
 */
public interface Destroyable {
    /**
     * Preforms pre-destruction clean-up logic.
     */
    void destroy();
}