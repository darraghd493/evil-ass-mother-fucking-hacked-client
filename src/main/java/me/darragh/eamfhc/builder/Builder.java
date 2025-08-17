package me.darragh.eamfhc.builder;

/**
 * An interface for builders that create instances of type T.
 * This interface defines a single method, `build()`, which returns an instance of T.
 *
 * @param <T> The type of object to be built.
 */
public interface Builder<T> {
    T build();
}
