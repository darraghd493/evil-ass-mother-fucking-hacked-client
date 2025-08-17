package me.darragh.eamfhc.feature.property.type.enumerable;

import com.google.gson.JsonObject;
import lombok.Getter;
import me.darragh.eamfhc.feature.Configurable;
import me.darragh.eamfhc.feature.property.Property;
import me.darragh.eamfhc.feature.property.PropertyChangeObserver;
import me.darragh.eamfhc.feature.property.PropertyEnum;
import me.darragh.eamfhc.feature.property.PropertyMetadata;
import me.darragh.eamfhc.feature.property.constraints.EmptyPropertyConstraints;
import me.darragh.eamfhc.util.EnumUtil;

import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

/**
 * A property that represents a value from an enum type.
 *
 * @param <T> The enum type that extends {@link PropertyEnum}.
 *
 * @author darraghd493
 */
@Getter
public class EnumProperty<T extends Enum<T> & PropertyEnum> extends Property<T, EmptyPropertyConstraints, PropertyChangeObserver<T>> {
    private final Class<T> enumClass;
    private final List<T> values;

    public EnumProperty(PropertyMetadata metadata, EmptyPropertyConstraints constraints, PropertyChangeObserver<T> observer, Configurable target, Property<?, ?, ?> parent, BooleanSupplier visibility, T defaultValue, Class<T> enumClass) {
        super(metadata, constraints, observer, target, parent, visibility, defaultValue);
        this.enumClass = enumClass;
        this.values = Arrays.stream(enumClass.getEnumConstants()).collect(Collectors.toList());
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("value", this.getValue().name());
        return object;
    }

    @Override
    public void fromJson(JsonObject json) {
        if (json.has("value")) {
            this.setValue(
                    EnumUtil.getEnum(this.enumClass, json.get("value").getAsString())
            );
        }
    }
}
