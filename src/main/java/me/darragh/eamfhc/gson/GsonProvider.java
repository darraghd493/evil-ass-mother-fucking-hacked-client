package me.darragh.eamfhc.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.darragh.eamfhc.gson.adapter.IdentifiableEnumAdapterFactory;

/**
 * A provider class for a singleton instance of Gson configured with custom settings.
 * <p>
 * This class provides a pre-configured Gson instance that can be used throughout the launcher
 * for serialising and deserialising JSON data.
 *
 * @author darraghd493
 */
public class GsonProvider {
    private static final Gson GSON;

    static {
        GSON = new GsonBuilder()
                .setPrettyPrinting() // for better readability
                .disableHtmlEscaping()
                .serializeNulls() // some fields may be null for certain objects, we want to retain this
                .registerTypeAdapterFactory(new IdentifiableEnumAdapterFactory())
                .create();
    }

    /**
     * Returns the singleton instance of Gson.
     *
     * @return The Gson instance.
     */
    public static Gson getGson() {
        return GSON;
    }
}
