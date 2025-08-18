package me.darragh.eamfhc.util;

import lombok.experimental.UtilityClass;
import me.darragh.eamfhc.type.Range;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A utility class for generating random numbers and selecting random elements from collections.
 *
 * @apiNote Methods are not documented as they are self-explanatory.
 *
 * @author darraghd493
 */
@UtilityClass
public class RandomUtil {
    private static final Random RANDOM = ThreadLocalRandom.current();

    public static int nextInt(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static int nextInt(int bound) {
        if (bound <= 0) return bound;
        return RANDOM.nextInt(bound);
    }

    public static int nextInt() {
        return RANDOM.nextInt();
    }

    public static int nextInt(Range<Integer> range) {
        if (range.getFrom().equals(range.getTo())) return range.getTo();
        return nextInt(range.getFrom(), range.getTo());
    }

    public static long nextLong(long min, long max) {
        return RANDOM.nextLong() % (max - min) + min;
    }

    public static long nextLong(long bound) {
        return RANDOM.nextLong() % bound;
    }

    public static long nextLong() {
        return RANDOM.nextLong();
    }

    public static double nextDouble(double min, double max) {
        return RANDOM.nextDouble() * (max - min) + min;
    }

    public static double nextDouble(double bound) {
        return RANDOM.nextDouble() * bound;
    }

    public static double nextDouble() {
        return nextDouble(0.0D, 1.0D);
    }

    public static float nextFloat(float min, float max) {
        return RANDOM.nextFloat() * (max - min) + min;
    }

    public static float nextFloat(float bound) {
        return RANDOM.nextFloat() * bound;
    }

    public static float nextFloat() {
        return nextFloat(0.0F, 1.0F);
    }

    public static float nextFloat(Range<Float> range) {
        return nextFloat(range.getFrom(), range.getTo());
    }

    public static boolean nextBoolean() {
        return RANDOM.nextBoolean();
    }

    public static boolean nextBoolean(double chance) {
        return RANDOM.nextDouble() < chance;
    }

    public static boolean nextBoolean(int chance) {
        return RANDOM.nextInt(100) < chance;
    }

    public static double nextGaussian(double mean, double standardDeviation) {
        return RANDOM.nextGaussian(mean, standardDeviation);
    }

    public static double nextGaussian() {
        return RANDOM.nextGaussian();
    }

    public static int nextNegative() {
        return nextBoolean() ? -1 : 1;
    }

    public static <T> T nextElement(T[] list) {
        return list[RANDOM.nextInt(list.length)];
    }

    public static <T> T nextElement(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }
}
