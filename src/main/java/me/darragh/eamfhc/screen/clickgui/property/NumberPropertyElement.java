package me.darragh.eamfhc.screen.clickgui.property;

import me.darragh.eamfhc.feature.property.type.BooleanProperty;
import me.darragh.eamfhc.feature.property.type.number.NumberProperty;
import me.darragh.eamfhc.screen.clickgui.Container;
import me.darragh.eamfhc.util.RenderUtil;
import me.darragh.math.util.MathUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

import java.util.function.BooleanSupplier;

/**
 * A property element for a {@link NumberProperty} in the ClickGui.
 *
 * @author darraghd493
 */
public class NumberPropertyElement<T extends Number> extends PropertyElement<NumberProperty<T>, T> {
    private boolean dragging;

    protected NumberPropertyElement(NumberProperty<T> property, BooleanSupplier visibility, int offset) {
        super(property, visibility, offset);
    }

    @Override
    public int draw(@NotNull Screen screen, @NotNull GuiGraphics guiGraphics, Container container, int mouseX, int mouseY, float partialTick) {
        super.draw(screen, guiGraphics, container, mouseX, mouseY, partialTick);

        float min = this.property.getConstraints().getMin().floatValue(),
                max = this.property.getConstraints().getMax().floatValue(),
                value = this.property.getValue().floatValue();

        if (this.dragging) {
            float progress = (float) (mouseX - this.x) / this.width;
            value = min + (max - min) * progress;
            this.setValue(value);
            value = this.property.getValue().floatValue();
        }

        float progress = (value - min) / (max - min);
        String text = String.format("%.2f", value);
        guiGraphics.fill(
                this.x, this.y + HEIGHT - 1,
                (int) (this.x + this.width * progress), this.y + HEIGHT,
                0xFF000000 // Background colour
        );
        guiGraphics.drawString(
                minecraft.font,
                text,
                this.x + this.width - 4 - minecraft.font.width(text), this.y + (HEIGHT - 1 - minecraft.font.lineHeight) / 2,
                0xFFFFFF
        );
        return HEIGHT;
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        if (RenderUtil.isMouseInArea(mouseX, mouseY, this.x, this.y, this.width, this.getHeight())) {
            this.dragging = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY, int button) {
        if (this.dragging) {
            this.dragging = false;
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void setValue(float value) {
        Class<?> type = this.property.getValue().getClass();
        float stepped = (float) MathUtil.stepDouble(value, this.property.getConstraints().getStep().doubleValue());

        if (type.equals(Integer.class)) {
            this.property.setValue((T) Integer.valueOf((int) stepped));
        } else if (type.equals(Float.class)) {
            this.property.setValue((T) Float.valueOf(stepped));
        }
    }
}
