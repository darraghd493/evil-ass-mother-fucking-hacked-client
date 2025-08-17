package me.darragh.eamfhc.service;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * A repository for managing services in the mod.
 *
 * @author darraghd493
 */
@Slf4j
public class ServiceRepository {
    private static final Map<String, Service> SERVICES = new Object2ObjectOpenHashMap<>();
    private static final Map<Class<? extends Service>, Service> SERVICES_2_CLASS = new Object2ObjectOpenHashMap<>();

    /**
     * Registers a service in the repository.
     *
     * @param service The service to register.
     */
    public static void register(String identifier, Service service) {
        log.debug("Registering service '{}'", identifier);
        if (SERVICES.containsKey(identifier)) {
            log.error("Service '{}' already exists!", identifier);
            throw new IllegalArgumentException("Service '%s' is already registered.".formatted(identifier));
        }
        SERVICES.put(identifier, service);
        service.init();
        log.info("Service '{}' registered successfully.", identifier);
    }

    /**
     * Unregisters a service from the repository.
     *
     * @param identifier The identifier of the service to unregister.
     */
    public static void unregister(String identifier) {
        Service service = SERVICES.remove(identifier);
        if (service != null) {
            log.info("Service '{}' unregistered successfully.", identifier);
            service.destroy();
        } else {
            log.error("Service '{}' was not registered, cannot unregister!", identifier);
            throw new RuntimeException("Service '%s' was not registered, cannot unregister.".formatted(identifier));
        }
    }

    /**
     * Gets a service by its class type.
     *
     * @param clazz The class type of the service.
     * @return The registered service, or null if not found.
     */
    public static <T extends Service> T getService(Class<T> clazz) {
        if (SERVICES_2_CLASS.containsKey(clazz)) {
            return clazz.cast(SERVICES_2_CLASS.get(clazz)); // Safe cast - potentially more inefficient than (T) <obj>?
        }
        for (Service service : SERVICES.values()) {
            if (clazz.isInstance(service)) {
                SERVICES_2_CLASS.put(
                        clazz,
                        clazz.cast(service)
                );
            }
        }
        log.warn("No service found for class '{}'", clazz.getName());
        return null;
    }
}
