package me.darragh.eamfhc.util;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import lombok.experimental.UtilityClass;

import java.awt.*;
import java.util.Map;

/**
 * A utility class for colour-related operations.
 *
 * @author darraghd493
 */
@UtilityClass
public class ColourUtil {
    private static final Map<Color, Integer> COLOUR_CACHE_MAP = new Object2IntOpenHashMap<>();

    /**
     * Converts RGB values to a single integer representation.
     *
     * @param r Red component (0-255)
     * @param g Green component (0-255)
     * @param b Blue component (0-255)
     * @return An integer representing the RGB colour
     */
    public static int getInt(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }

    /**
     * Converts RGBA values to a single integer representation.
     *
     * @param r Red component (0-255)
     * @param g Green component (0-255)
     * @param b Blue component (0-255)
     * @param a Alpha component (0-255)
     * @return An integer representing the RGBA colour
     */
    public static int getInt(int r, int g, int b, int a) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    /**
     * Converts a {@link Color} to an integer representation with caching.
     *
     * @param color The {@link Color} to convert
     * @return An integer representing the colour
     */
    public static int getInt(Color color) {
        return COLOUR_CACHE_MAP.computeIfAbsent(color, c -> getInt(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()));
    }
}
