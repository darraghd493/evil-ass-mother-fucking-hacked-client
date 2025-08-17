package me.darragh.eamfhc.feature;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import me.darragh.eamfhc.service.ServiceRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A simple implementation of the FeatureService interface.
 * This class can be extended to provide specific functionality for features.
 *
 * @implNote Poor type safety is used here to allow for flexibility in feature types.
 *
 * @param <F> The type of feature this service will handle, extending Feature<M>.
 * @param <M> The type of metadata associated with the feature, extending FeatureMetadata.
 */
// TODO: Cleaner type safety
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleFeatureRepositoryService<F extends Feature<M>, M extends FeatureMetadata> implements FeatureRepositoryService<F, M> {
    private final Map<String, F> features = new Object2ObjectOpenHashMap<>();
    private final Map<Class<? extends F>, F> featureClasses = new Object2ObjectOpenHashMap<>();
    private final Initialiser<F, M> initialiser;

    @Override
    public void init() {
        this.initialiser.init(this);
    }

    @Override
    public void destroy() {
        this.featureClasses.clear();
        this.features.clear();
    }

    @Override
    public void registerFeature(F feature) {
        if (!isValidFeature(feature)) {
            throw new IllegalArgumentException("Invalid feature: " + feature);
        }
        String identifier = feature.getMetadata().getIdentifier();
        if (this.features.containsKey(identifier)) { // Feature already registered
            throw new IllegalArgumentException("Feature with identifier '%s' is already registered.".formatted(identifier));
        }
        this.features.put(identifier, feature);
        //noinspection unchecked
        this.featureClasses.put((Class<? extends F>) feature.getClass(), feature);
    }

    @Override
    public void unregisterFeature(F feature) {
        if (!isValidFeature(feature)) {
            throw new IllegalArgumentException("Invalid feature: " + feature);
        }
        String identifier = feature.getMetadata().getIdentifier();
        if (this.features.containsKey(identifier)) {
            this.features.remove(identifier);
            this.featureClasses.remove(feature.getClass());
            return; // Successfully unregistered
        }
        throw new IllegalArgumentException("Feature with identifier '%s' is not registered.".formatted(identifier));
    }

    @Override
    public <T extends F> T getFeature(String identifier) {
        if (identifier == null || identifier.isEmpty()) {
            throw new IllegalArgumentException("Identifier cannot be null or empty");
        }
        //noinspection unchecked
        return (T) this.features.get(identifier);
    }

    @Override
    public boolean isFeatureRegistered(String identifier) {
        if (identifier == null || identifier.isEmpty()) {
            throw new IllegalArgumentException("Identifier cannot be null or empty");
        }
        return this.features.containsKey(identifier);
    }

    @Override
    public M getFeatureMetadata(String identifier) {
        if (identifier == null || identifier.isEmpty()) {
            throw new IllegalArgumentException("Identifier cannot be null or empty");
        }
        F feature = this.features.get(identifier);
        if (feature != null) {
            return feature.getMetadata();
        }
        return null; // Feature not found
    }

    @Override
    public <T extends F> T getFeature(Class<T> featureClass) {
        if (featureClass == null) {
            throw new IllegalArgumentException("Feature class cannot be null");
        }
        //noinspection unchecked
        return (T) this.featureClasses.get(featureClass);
    }

    @Override
    public <T extends F> boolean isFeatureRegistered(Class<T> featureClass) {
        if (featureClass == null) {
            throw new IllegalArgumentException("Feature class cannot be null");
        }
        return this.featureClasses.containsKey(featureClass);
    }

    @Override
    public Collection<F> getFeatures() {
        return List.copyOf(this.features.values());
    }

    /**
     * Creates a new instance of SimpleFeatureRepositoryService.
     *
     * @param identifier The identifier for the service.
     * @param initialiser The initialiser callback to set up the service.
     * @param <F>        The type of feature this service will handle.
     * @param <M>        The type of metadata associated with the feature.
     * @return A new instance of SimpleFeatureRepositoryService.
     */
    public static <F extends Feature<M>, M extends FeatureMetadata> SimpleFeatureRepositoryService<F, M> create(String identifier, Initialiser<F, M> initialiser) {
        SimpleFeatureRepositoryService<F, M> service = new SimpleFeatureRepositoryService<>(initialiser);
        ServiceRepository.register(identifier, service);
        return service;
    }

    /**
     * Determines if a feature is valid for registration.
     *
     * @param feature The feature to validate.
     * @return True if the feature is valid, false otherwise.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean isValidFeature(Feature<?> feature) {
        return feature != null && feature.getMetadata() != null && feature.getMetadata().getIdentifier() != null && !feature.getMetadata().getIdentifier().isEmpty();
    }

    /**
     * Callback interface for initialising the service.
     */
    @FunctionalInterface
    public interface Initialiser<F extends Feature<M>, M extends FeatureMetadata> {
        void init(SimpleFeatureRepositoryService<F, M> service);
    }
}
