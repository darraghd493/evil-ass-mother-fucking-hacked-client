package me.darragh.eamfhc.gson.adapter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import me.darragh.eamfhc.Identifiable;

/**
 * A factory for creating Gson TypeAdapters (of {@link IdentifiableEnumAdapter}) for enums that implement the {@link Identifiable} interface.
 *
 * @author darraghd493
 */
public class IdentifiableEnumAdapterFactory implements TypeAdapterFactory {
    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (rawType.isEnum() // intercept Identifiable enums
                && Identifiable.class.isAssignableFrom(rawType)
                && rawType.getPackageName().startsWith("me.darragh.eamfhc")) {
            return (TypeAdapter<T>) createAdapter(rawType).nullSafe();
        }
        return null; // let Gson handle everything else
    }

    @SuppressWarnings("unchecked")
    private <E extends Enum<E> & Identifiable> TypeAdapter<E> createAdapter(Class<?> rawType) {
        return new IdentifiableEnumAdapter<>((Class<E>) rawType);
    }
}
