package me.darragh.eamfhc.util;

import com.google.gson.*;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * A utility class for handling JSON operations.
 *
 * @author darraghd493
 */
@UtilityClass
public class JsonUtil {
    public static List<Object> getJsonArray(JsonArray array) {
        List<Object> list = new ObjectArrayList<>();
        for (JsonElement element : array) {
            if (element.isJsonArray()) {
                list.add(getJsonArray(element.getAsJsonArray()));
            } else {
                list.add(element);
            }
        }
        return list;
    }
}
