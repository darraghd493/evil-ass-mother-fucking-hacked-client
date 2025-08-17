package me.darragh.eamfhc.feature.property;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import lombok.extern.slf4j.Slf4j;
import me.darragh.eamfhc.Serialisable;
import me.darragh.eamfhc.manager.Manager;

import java.util.List;
import java.util.Map;

/**
 * Stores and manages properties, allowing for addition, removal, and retrieval of properties by their identifiers.
 * <p>
 * This class implements the Manager interface for Property objects and provides methods to manage them.
 *
 * @author darraghd493
 */
@Slf4j
public class PropertyManager implements Manager<Property<?, ?, ?>>, Serialisable {
    private final Map<String, Property<?, ?, ?>> properties = new Object2ObjectArrayMap<>();

    @Override
    public void add(Property<?, ?, ?> property) {
        if (this.properties.containsKey(property.getMetadata().getIdentifier())) {
            throw new IllegalArgumentException("Property with identifier " + property.getMetadata().getIdentifier() + " already exists");
        }
        this.properties.put(property.getMetadata().getIdentifier(), property);
    }

    @Override
    public void remove(Property<?, ?, ?> property) {
        this.properties.remove(property.getMetadata().getIdentifier());
    }

    @Override
    public void remove(String id) {
        this.properties.remove(id);
    }

    @Override
    public boolean contains(Property<?, ?, ?> property) {
        return this.properties.containsValue(property);
    }

    @Override
    public boolean contains(String id) {
        return this.properties.containsKey(id);
    }

    @Override
    public <U extends Property<?, ?, ?>> U get(String id) {
        //noinspection unchecked
        return (U) this.properties.get(id);
    }

    @Override
    public List<Property<?, ?, ?>> getAll() {
        return List.copyOf(this.properties.values());
    }

    @Override
    public void clear() {
        this.properties.clear();
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        this.properties.forEach((identifier, property) -> {
            try {
                object.add(identifier, property.toJson());
            } catch (Exception e) {
                log.error("Failed to serialize property {} to json", identifier, e);
            }
        });
        return object;
    }

    @Override
    public void fromJson(JsonObject json) {
        for (Map.Entry<String, Property<?, ?, ?>> entry : this.properties.entrySet()) {
            try {
                String property = entry.getKey();
                if (!json.has(property)) {
                    continue;
                }
                JsonObject object = json.getAsJsonObject(entry.getKey());
                if (object != null) {
                    entry.getValue().fromJson(object);
                }
            } catch (Exception e) {
                log.error("Failed to load property {} from json", entry.getKey(), e);
            }
        }
    }
}
