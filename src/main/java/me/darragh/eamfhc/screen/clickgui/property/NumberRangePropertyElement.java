package me.darragh.eamfhc.screen.clickgui.property;

import me.darragh.eamfhc.feature.property.type.range.NumberRangeProperty;
import me.darragh.eamfhc.screen.clickgui.Container;
import me.darragh.eamfhc.type.Range;
import me.darragh.eamfhc.util.RenderUtil;
import me.darragh.math.util.MathUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

import java.util.function.BooleanSupplier;

/**
 * A property element for a {@link NumberRangeProperty} in the ClickGui.
 *
 * @author darraghd493
 */
public class NumberRangePropertyElement<T extends Number> extends PropertyElement<NumberRangeProperty<T>, Range<T>> {
    private boolean draggingMin, draggingMax;

    protected NumberRangePropertyElement(NumberRangeProperty<T> property, BooleanSupplier visibility, int offset) {
        super(property, visibility, offset);
    }

    @Override
    public int draw(@NotNull Screen screen, @NotNull GuiGraphics guiGraphics, Container container, int mouseX, int mouseY, float partialTick) {
        super.draw(screen, guiGraphics, container, mouseX, mouseY, partialTick);

        float min = this.property.getConstraints().getMin().floatValue(),
                max = this.property.getConstraints().getMax().floatValue();

        Range<T> value = this.property.getValue();

        if (this.draggingMin || this.draggingMax) {
            float progress = (float) (mouseX - this.x) / this.width;
            this.setValue(min + (max - min) * progress, this.draggingMin);
        }

        float progressFrom = (value.getFrom().floatValue() - min) / (max - min),
                progressTo = (value.getTo().floatValue() - min) / (max - min);

        String text = String.format("%.2f - %.2f", value.getFrom().floatValue(), value.getTo().floatValue());
        guiGraphics.fill(
                (int) (this.x + this.width * progressFrom), this.y + HEIGHT - 1,
                (int) (this.x + this.width * progressTo), this.y + HEIGHT,
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
            float from = this.property.getValue().getFrom().floatValue(),
                    to = this.property.getValue().getTo().floatValue(),
                    min = this.property.getConstraints().getMin().floatValue(),
                    max = this.property.getConstraints().getMax().floatValue();

            float fromValue = from - min / (max - min),
                    toValue = to - min / (max - min);
            float currentValue = (float) (mouseX - this.x) / this.width;

            boolean side = Math.abs(fromValue - currentValue) < Math.abs(toValue - currentValue);
            this.draggingMin = side;
            this.draggingMax = !side;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY, int button) {
        if (this.draggingMin || this.draggingMax) {
            this.draggingMin = this.draggingMax = false;
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void setValue(float value, boolean min) {
        Class<?> type = this.property.getValue().type();
        float stepped = (float) MathUtil.stepDouble(value, this.property.getConstraints().getStep().doubleValue());

        Range<T> range = this.property.getValue();
        T fromValue = range.getFrom(),
                toValue = range.getTo();
        T newValue = type.equals(Integer.class) ? (T) Integer.valueOf((int) stepped) : (T) Float.valueOf(stepped);

        this.property.setValue(min ? new Range<>(newValue, toValue) : new Range<>(fromValue, newValue));
    }
}
