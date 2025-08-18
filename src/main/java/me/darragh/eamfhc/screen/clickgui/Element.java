package me.darragh.eamfhc.screen.clickgui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.darragh.eamfhc.GameInstance;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

/**
 * A base (abstract) class for elements in the ClickGui.
 *
 * @author darraghd493
 */
@Getter
@Setter
@AllArgsConstructor
public abstract class Element implements Interactive, GameInstance {
    protected int x, y, width;

    public Element() {
        this(0, 0, 0);
    }

    /**
     * Draws the element on the screen.
     *
     * @param container The container that holds this element.
     * @param mouseX The x-coordinate of the mouse cursor.
     * @param mouseY The y-coordinate of the mouse cursor.
     * @param partialTick The partial tick time for smooth rendering.
     * @return The height of the element after drawing.
     */
    public abstract int draw(@NotNull Screen screen, @NotNull GuiGraphics guiGraphics, Container container, int mouseX, int mouseY, float partialTick);
}
