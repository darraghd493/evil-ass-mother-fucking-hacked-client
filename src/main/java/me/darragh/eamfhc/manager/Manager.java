package me.darragh.eamfhc.manager;

import java.util.List;

/**
 * A generic manager interface for managing a collection of objects of type T.
 * Provides methods to add, remove, check existence, retrieve, and clear objects.
 *
 * @param <T> the type of objects managed by this manager
 */
public interface Manager<T> {
    /**
     * Adds an object of type T to the manager.
     *
     * @param t The object to be added.
     */
    void add(T t);

    /**
     * Adds all objects from the provided list to the manager.
     *
     * @param list The list of objects to be added.
     */
    default void addAll(List<T> list) {
        list.forEach(this::add);
    }

    /**
     * Removes an object of type T from the manager.
     *
     * @param t The object to be removed.
     */
    void remove(T t);

    /**
     * Removes an object from the manager by its identifier.
     *
     * @param identifier The identifier of the object to be removed.
     */
    void remove(String identifier);

    /**
     * Removes all objects from the provided list from the manager.
     *
     * @param list The list of objects to be removed.
     */
    default void removeAll(List<T> list) {
        list.forEach(this::remove);
    }

    /**
     * Checks if the manager contains a specific object.
     *
     * @param t The object to check for existence.
     * @return True if the object is contained, false otherwise.
     */
    boolean contains(T t);

    /**
     * Checks if the manager contains an object with the specified identifier.
     *
     * @param identifier The identifier of the object to check for existence.
     * @return True if an object with the identifier is contained, false otherwise.
     */
    boolean contains(String identifier);

    /**
     * Retrieves an object from the manager by its identifier.
     *
     * @param identifier The identifier of the object to retrieve.
     * @return The object if found, or null if not found.
     */
    <U extends T> U get(String identifier);

    /**
     * Retrieves all objects managed by this manager.
     *
     * @return A list of all objects.
     */
    List<T> getAll();

    /**
     * Clears all objects from the manager.
     */
    void clear();
}
