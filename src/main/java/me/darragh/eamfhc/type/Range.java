package me.darragh.eamfhc.type;

/**
 * A Range class that represents a range of numbers.
 * It is generic and can handle any subclass of Number.
 *
 * @param <T> The type of the numbers in the range, must extend Number.
 *
 * @author darraghd493
 */
public class Range<T extends Number> {
    private final T from, to;

    public Range(T from, T to) {
        this.from = from;
        this.to = to;
    }

    public Class<T> type() {
        return (Class<T>) this.from.getClass();
    }

    public T getFrom() {
        return this.from.doubleValue() < this.to.doubleValue() ? this.from : this.to;
    }

    public T getTo() {
        return this.from.doubleValue() < this.to.doubleValue() ? this.to : this.from;
    }
}
