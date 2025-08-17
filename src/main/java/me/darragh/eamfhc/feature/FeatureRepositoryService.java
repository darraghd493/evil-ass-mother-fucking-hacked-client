package me.darragh.eamfhc.feature;

import me.darragh.eamfhc.service.Service;

import java.util.Collection;

/**
 * A base interface for storing features in the mod.
 *
 * @param <F> The type of feature this service will handle, extending Feature<M>.
 * @param <M> The type of metadata associated with the feature, extending FeatureMetadata.
 *
 * @author darraghd493
 */
public interface FeatureRepositoryService<F extends Feature<M>, M extends FeatureMetadata> extends Service {
    /**
     * Registers a feature with the service.
     *
     * @param feature The feature to register.
     */
    void registerFeature(F feature);

    /**
     * Unregisters a feature from the service.
     *
     * @param feature The feature to unregister.
     */
    void unregisterFeature(F feature);

    /**
     * Retrieves a feature by its identifier.
     *
     * @param identifier The identifier of the feature to retrieve.
     * @return The feature associated with the identifier, or null if not found.
     */
    <T extends F> T getFeature(String identifier);

    /**
     * Checks if a feature is registered with the service.
     *
     * @param identifier The identifier of the feature to check.
     * @return True if the feature is registered, false otherwise.
     */
    boolean isFeatureRegistered(String identifier);

    /**
     * Gets the metadata of a feature by its identifier.
     *
     * @param identifier The identifier of the feature.
     * @return The metadata of the feature, or null if not found.
     */
    M getFeatureMetadata(String identifier);

    /**
     * Gets a feature by its class type.
     *
     * @param featureClass The class of the feature to retrieve.
     * @return The feature instance if found, or null if not registered.
     */
    <T extends F> T getFeature(Class<T> featureClass);

    /**
     * Checks if a feature is registered by its class type.
     *
     * @param featureClass The class of the feature to check.
     * @return True if the feature is registered, false otherwise.
     */
    <T extends F> boolean isFeatureRegistered(Class<T> featureClass);

    /**
     * Returns all registered features.
     *
     * @return A collection of all registered features.
     */
    Collection<F> getFeatures();
}
