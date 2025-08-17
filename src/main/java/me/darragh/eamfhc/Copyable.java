package me.darragh.eamfhc;

/**
 * An interface for objects that can be copied.
 *
 * @param <T> The type of the object that can be copied.
 */
public interface Copyable<T> {
    /**
     * Creates a copy of the current object.
     *
     * @return A new instance of the object that is a copy of the current one.
     */
    T copy();
}
