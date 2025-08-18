package me.darragh.eamfhc.screen.clickgui;

/**
 * An interface for interactive object in the ClickGui, outlining a standard set of methods for handling user interactions.
 *
 * @author darraghd493
 */
public interface Interactive {
    /**
     * Handles mouse click events on the element.
     *
     * @param mouseX The x-coordinate of the mouse cursor.
     * @param mouseY The y-coordinate of the mouse cursor.
     * @param button The mouse button that was clicked.
     * @return True if the click was handled, false otherwise.
     */
    default boolean mouseClicked(int mouseX, int mouseY, int button) {
        return false;
    }

    /**
     * Handles mouse release events on the element.
     *
     * @param mouseX The x-coordinate of the mouse cursor.
     * @param mouseY The y-coordinate of the mouse cursor.
     * @param button The mouse button that was released.
     * @return True if the release was handled, false otherwise.
     */
    default boolean mouseReleased(int mouseX, int mouseY, int button) {
        return false;
    }

    /**
     * Handles key press events on the element.
     *
     * @param keyCode The key code of the pressed key.
     * @param scanCode The scan code of the pressed key.
     * @param modifiers The modifiers (e.g., Shift, Ctrl) that were held during the key press.
     * @return True if the key press was handled, false otherwise.
     */
    default boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    /**
     * Handles key release events on the element.
     *
     * @param keyCode The key code of the released key.
     * @param scanCode The scan code of the released key.
     * @param modifiers The modifiers (e.g., Shift, Ctrl) that were held during the key release.
     * @return True if the key release was handled, false otherwise.
     */
    default boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }
}
