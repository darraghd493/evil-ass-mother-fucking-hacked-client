package me.darragh.eamfhc.feature.property.type.enumerable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.Getter;
import me.darragh.eamfhc.feature.Configurable;
import me.darragh.eamfhc.feature.property.*;
import me.darragh.eamfhc.feature.property.constraints.EmptyPropertyConstraints;
import me.darragh.eamfhc.util.JsonUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A property that represents multiple values from an enum type.
 *
 * @param <T> The enum type that extends {@link PropertyEnum}.
 *
 * @author darraghd493
 */
@Getter
public class MultiEnumProperty<T extends Enum<T> & PropertyEnum> extends Property<List<T>, EmptyPropertyConstraints, PropertyChangeObserver<List<T>>> {
    private final Class<T> enumClass;
    private final List<T> values;

    public MultiEnumProperty(PropertyMetadata metadata, EmptyPropertyConstraints constraints, PropertyChangeObserver<List<T>> observer, Configurable target, Property<?, ?, ?> parent, BooleanSupplier visibility, List<T> defaultValue, Class<T> enumClass) {
        super(metadata, constraints, observer, target, parent, visibility, defaultValue);
        this.enumClass = enumClass;
        this.values = Arrays.stream(enumClass.getEnumConstants()).collect(Collectors.toList());
    }

    public void enable(T value) {
        if (!this.getValue().contains(value)) {
            this.setValue(
                    Stream.concat(this.getValue().stream(), Stream.of(value))
                            .collect(Collectors.toList())
            );
        }
    }

    public void disable(T value) {
        if (this.getValue().contains(value)) {
            this.setValue(this.getValue().stream()
                            .filter(v -> !v.equals(value))
                            .collect(Collectors.toList()));
        }
    }

    public void toggle(T value) {
        if (this.getValue().contains(value)) {
            this.disable(value);
        } else {
            this.enable(value);
        }
    }

    public boolean isEnabled(T value) {
        return this.getValue().contains(value);
    }

    @Override
    public void setValue(List<T> value) {
        super.setValue(Collections.unmodifiableList(value));
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        JsonArray values = new JsonArray();
        this.getValue().stream()
                .map(PropertyEnum::getIdentifier)
                .forEach(values::add);
        object.add("value", values);
        return object;
    }

    @Override
    public void fromJson(JsonObject json) {
        if (json.has("value")) {
            this.setValue(
                    JsonUtil.getJsonArray(json.get("value").getAsJsonArray()).stream()
                            .filter(JsonPrimitive.class::isInstance)
                            .map(v -> ((JsonPrimitive) v).getAsString())
                            .map(identifier ->
                                    Arrays.stream(this.enumClass.getEnumConstants())
                                            .filter(e -> e.getIdentifier().equals(identifier))
                                            .findFirst()
                                            .orElse(null)
                            )
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList())
            );
        }
    }
}
