package me.darragh.eamfhc.util;

import lombok.experimental.UtilityClass;
import me.darragh.eamfhc.GameInstance;

/**
 * A utility class for rendering-related operations in the game.
 *
 * @author darraghd493
 */
@UtilityClass
public class RenderUtil implements GameInstance {
    /**
     * Determines if the mouse is within a specified area.
     *
     * @param mouseX The x-coordinate of the mouse cursor.
     * @param mouseY The y-coordinate of the mouse cursor.
     * @param x The x-coordinate of the top-left corner of the area.
     * @param y The y-coordinate of the top-left corner of the area.
     * @param width The width of the area.
     * @param height The height of the area.
     * @return True if the mouse is within the area, false otherwise.
     */
    public static boolean isMouseInArea(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
