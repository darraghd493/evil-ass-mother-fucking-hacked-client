package me.darragh.math.util;

import me.darragh.eamfhc.type.Range;

public class MathUtil { // TODO: Optimise
    //region clamp(s)
    public static int clampInt(int value, int min, int max) {
        return value > max ? max : Math.max(value, min);
    }

    public static double clampDouble(double value, double min, double max) {
        return value > max ? max : Math.max(value, min);
    }

    public static float clampFloat(float value, float min, float max) {
        return value > max ? max : Math.max(value, min);
    }

    public static long clampLong(long value, long min, long max) {
        return value > max ? max : Math.max(value, min);
    }

    //region Specific
    public static float clampFloat01(float value) {
        return value < 0.0F ? 0.0F : Math.min(value, 1.0F);
    }
    //endregion
    //endregion

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException("Places must be greater than or equal to 0");
        }
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    //region step(s)
    public static double stepDouble(double value, double step) {
        return Math.round(value / step) * step;
    }

    public static float stepFloat(float value, float step) {
        return Math.round(value / step) * step;
    }

    public static float floorStepFloat(float value, float step) {
        return (float) Math.floor(value / step) * step;
    }

    public static float ceilStepFloat(float value, float step) {
        return (float) Math.ceil(value / step) * step;
    }
    //endregion

    //region lerp(s)
    public static double lerpDouble(double a, double b, double t) {
        return a + (b - a) * t;
    }

    public static float lerpFloat(float a, float b, float t) {
        return a + (b - a) * t;
    }

    public static float lerpFloat(Range<Float> range, float t) {
        return lerpFloat(range.getFrom(), range.getTo(), t);
    }
    //endregion

    //region min/max
    public static float minFloat(float... values) {
        float max = Float.POSITIVE_INFINITY;
        for (float value : values) {
            if (value < max) {
                max = value;
            }
        }
        return max;
    }

    public static float maxFloat(float... values) {
        float max = Float.NEGATIVE_INFINITY;
        for (float value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
    //endregion

    public static boolean isDoubleSameSign(double a, double b) {
        return (a < 0.0D && b < 0.0D) || (a >= 0.0D && b >= 0.0D);
    }

    //region isEqual(s)
    public static boolean isFloatEqual(float a, float b, float epsilon) {
        return Math.abs(a - b) <= epsilon;
    }

    public static boolean isDoubleEqual(double a, double b, double epsilon) {
        return Math.abs(a - b) <= epsilon;
    }
    //endregion
}
