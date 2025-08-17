package me.darragh.eamfhc.screen.clickgui;

import me.darragh.eamfhc.util.ColourUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

/**
 * Handles the rendering and interaction of the ClickGui screen.
 *
 * @author darraghd493
 */
public class ClickGuiScreen extends Screen {
    public ClickGuiScreen(Component title) {
        super(title);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        // Draw the background
        guiGraphics.fill(0, 0, this.width, this.height, ColourUtil.getInt(0, 0, 0, 101));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
