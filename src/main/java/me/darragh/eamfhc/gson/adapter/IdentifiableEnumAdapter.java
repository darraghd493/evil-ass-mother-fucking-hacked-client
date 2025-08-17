package me.darragh.eamfhc.gson.adapter;

import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import me.darragh.eamfhc.Identifiable;

import java.io.IOException;

/**
 * A Gson TypeAdapter for serialising and deserialising enums that implement the {@link Identifiable} interface.
 * This adapter serialises the enum by its identifier and deserializes it back to the enum constant.
 *
 * @param <T> The type of the enum that implements {@link Identifiable}.
 */
public class IdentifiableEnumAdapter<T extends Enum<T> & Identifiable> extends TypeAdapter<T> {
    private final Class<T> clazz;

    public IdentifiableEnumAdapter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void write(JsonWriter out, T value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.getIdentifier());
        }
    }

    @Override
    public T read(JsonReader in) throws IOException {
        String id = in.nextString();
        for (T constant : clazz.getEnumConstants()) {
            if (constant.getIdentifier().equalsIgnoreCase(id)) {
                return constant;
            }
        }
        throw new JsonParseException("Unknown identifier '%s' for enum %s".formatted(id, clazz.getSimpleName()));
    }
}