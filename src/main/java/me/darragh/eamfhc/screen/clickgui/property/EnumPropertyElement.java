package me.darragh.eamfhc.screen.clickgui.property;

import me.darragh.eamfhc.feature.property.PropertyEnum;
import me.darragh.eamfhc.feature.property.type.BooleanProperty;
import me.darragh.eamfhc.feature.property.type.enumerable.EnumProperty;
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
public class EnumPropertyElement<T extends Enum<T> & PropertyEnum> extends PropertyElement<EnumProperty<T>, T> {
    private boolean expanded;

    protected EnumPropertyElement(EnumProperty<T> property, BooleanSupplier visibility, int offset) {
        super(property, visibility, offset);
    }

    @Override
    public int draw(@NotNull Screen screen, @NotNull GuiGraphics guiGraphics, Container container, int mouseX, int mouseY, float partialTick) {
        super.draw(screen, guiGraphics, container, mouseX, mouseY, partialTick);
        if (!this.expanded) {
            return this.getHeight();
        }
        int yOffset = this.y + HEIGHT;
        for (T value : this.property.getValues()) {
            guiGraphics.drawString(
                    minecraft.font,
                    value.getName(),
                    this.x + 4, yOffset + (HEIGHT - 1 - minecraft.font.lineHeight) / 2,
                    this.property.getValue() == value ? 0xFFFFFF : 0x888888
            );
            guiGraphics.fill(
                    this.x, yOffset + HEIGHT - 1,
                    this.x + this.width, yOffset + HEIGHT,
                    BORDER_COLOUR
            );
            yOffset += HEIGHT;
        }
        return this.getHeight();
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        if (RenderUtil.isMouseInArea(mouseX, mouseY, this.x, this.y, this.width, HEIGHT)) {
            this.expanded = !this.expanded;
            return true;
        }
        if (this.expanded && RenderUtil.isMouseInArea(mouseX, mouseY, this.x, this.y + HEIGHT, this.width, HEIGHT * (this.property.getValues().size() + 1))) {
            int index = (mouseY - this.y - HEIGHT) / HEIGHT;
            if (index >= 0 && index < this.property.getValues().size()) {
                this.property.setValue(this.property.getValues().get(index));
                return true;
            }
        }
        return false;
    }

    @Override
    protected int getHeight() {
        return this.expanded ? HEIGHT * (this.property.getValues().size() + 1) : HEIGHT;
    }
}
