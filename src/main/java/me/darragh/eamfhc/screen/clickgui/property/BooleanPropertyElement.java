package me.darragh.eamfhc.screen.clickgui.property;

import me.darragh.eamfhc.feature.property.type.BooleanProperty;
import me.darragh.eamfhc.screen.clickgui.Container;
import me.darragh.eamfhc.util.RenderUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

import java.util.function.BooleanSupplier;

/**
 * A property element for a {@link BooleanProperty} in the ClickGui.
 *
 * @author darraghd493
 */
public class BooleanPropertyElement extends PropertyElement<BooleanProperty, Boolean> {
    protected BooleanPropertyElement(BooleanProperty property, BooleanSupplier visibility, int offset) {
        super(property, visibility, offset);
    }

    @Override
    public int draw(@NotNull Screen screen, @NotNull GuiGraphics guiGraphics, Container container, int mouseX, int mouseY, float partialTick) {
        super.draw(screen, guiGraphics, container, mouseX, mouseY, partialTick);
        int checkboxSize = HEIGHT - 4;
        int checkboxX = this.x + this.width - 4 - checkboxSize,
                checkboxY = this.y + 2;
        if (this.property.getValue()) {
            guiGraphics.fill(
                    checkboxX, checkboxY,
                    checkboxX + checkboxSize, checkboxY + checkboxSize,
                    0xFFFFFFFF
            );
        } else {
            guiGraphics.renderOutline(
                    checkboxX, checkboxY,
                    checkboxSize, checkboxSize,
                    0xFFFFFFFF
            );
        }
        return HEIGHT;
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        if (RenderUtil.isMouseInArea(mouseX, mouseY, this.x, this.y, this.width, this.getHeight())) {
            this.property.setValue(!this.property.getValue());
            return true;
        }
        return false;
    }
}
