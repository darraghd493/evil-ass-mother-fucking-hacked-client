package me.darragh.eamfhc.type;

import com.google.gson.JsonObject;
import lombok.Getter;
import me.darragh.eamfhc.Serialisable;

/**
 * A Range class that represents a range of numbers.
 * It is generic and can handle any subclass of Number.
 *
 * @param <T> The type of the numbers in the range, must extend {@link Number}.
 *
 * @author darraghd493
 */
@SuppressWarnings("ClassCanBeRecord")
@Getter
public class Range<T extends Number> implements Serialisable {
    private final T from, to;

    public Range(T from, T to) {
        this.from = from.doubleValue() < to.doubleValue() ? to : from;
        this.to = to.doubleValue() < from.doubleValue() ? from : to;
    }

    @SuppressWarnings("unchecked")
    public Class<T> type() {
        return (Class<T>) this.from.getClass();
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("from", this.getFrom());
        json.addProperty("to", this.getTo());
        return json;
    }

    @Override
    public void fromJson(JsonObject json) {
        throw new UnsupportedOperationException("fromJson is not supported for Range");
    }
}
