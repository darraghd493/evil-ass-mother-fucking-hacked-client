package me.darragh.eamfhc.screen.clickgui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.darragh.eamfhc.GameInstance;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a container for elements in the ClickGui.
 *
 * @author darraghd493
 */
@Getter
@AllArgsConstructor
public abstract class Container implements Interactive, GameInstance {
    protected int x, y;
    protected final int width, height;

    /**
     * Draws the container on the screen.
     *
     * @param guiGraphics The GuiGraphics instance used for rendering.
     * @param mouseX The x-coordinate of the mouse cursor.
     * @param mouseY The y-coordinate of the mouse cursor.
     * @param partialTick The partial tick time for smooth rendering.
     */
    public abstract void draw(@NotNull Screen screen, @NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);
}
