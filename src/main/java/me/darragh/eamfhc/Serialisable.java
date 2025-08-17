package me.darragh.eamfhc;

import com.google.gson.JsonObject;

/**
 * An interface for serialisable objects that can be converted to and from JSON.
 *
 * @author darraghd493
 */
public interface Serialisable {
    /**
     * Converts the object to a JSON representation.
     *
     * @return A {@link JsonObject} representing the object.
     */
    JsonObject toJson();

    /**
     * Populates the object from a JSON representation.
     *
     * @param json A {@link JsonObject} containing the object's data.
     */
    void fromJson(JsonObject json);
}
