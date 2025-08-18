package me.darragh.eamfhc.screen.clickgui.property;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import me.darragh.eamfhc.feature.Configurable;
import me.darragh.eamfhc.feature.property.*;
import me.darragh.eamfhc.feature.property.type.BooleanProperty;
import me.darragh.eamfhc.feature.property.type.LabelProperty;
import me.darragh.eamfhc.feature.property.type.enumerable.EnumProperty;
import me.darragh.eamfhc.feature.property.type.enumerable.MultiEnumProperty;
import me.darragh.eamfhc.feature.property.type.number.NumberProperty;
import me.darragh.eamfhc.feature.property.type.range.NumberRangeProperty;
import me.darragh.eamfhc.screen.clickgui.Container;
import me.darragh.eamfhc.screen.clickgui.Element;
import me.darragh.eamfhc.util.ColourUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BooleanSupplier;

/**
 * A base class for representing a properties as elements within the ClickGui.
 *
 * @author darraghd493
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PropertyElement<P extends Property<T, ?, ?>, T> extends Element {
    private static final Configurable DUMMY_CONFIGURABLE = PropertyManager::new;

    protected static final int HEIGHT = 12;

    protected static final int BACKGROUND_COLOUR = ColourUtil.getInt(24, 24, 24);
    protected static final int BORDER_COLOUR = ColourUtil.getInt(48, 48, 48);

    protected final P property;
    protected final BooleanSupplier visibility;
    protected final int offset;

    @Override
    public int draw(@NotNull Screen screen, @NotNull GuiGraphics guiGraphics, Container container, int mouseX, int mouseY, float partialTick) {
        int height = this.getHeight();
        guiGraphics.fill(
                this.x, this.y,
                this.x + this.width, this.y + height,
                BACKGROUND_COLOUR
        );
        guiGraphics.drawString(
                minecraft.font,
                this.property.getMetadata().getName(),
                this.x + 4, this.y + (HEIGHT - 1 - minecraft.font.lineHeight) / 2,
                0xFFFFFF
        );
        guiGraphics.fill(
                this.x, this.y + height - 1,
                this.x + this.width, this.y + height,
                BORDER_COLOUR
        );
        return height;
    }

    /**
     * Returns the current height of the property element.
     *
     * @return The height of the property element.
     */
    protected int getHeight() {
        return HEIGHT;
    }

    /**
     * Simplifies building a property elements for the ClickGui.
     *
     * @param propertyManager The property manager to create elements for.
     * @return A list of property elements.
     */
    public static List<PropertyElement<?, ?>> create(PropertyManager propertyManager) {
        return create(propertyManager, () -> true, 0);
    }

    /**
     * Simplifies building a property elements for the ClickGui with a visibility condition.
     *
     * @param propertyManager The property manager to create elements for.
     * @param visibility A visibility condition that determines if the properties should be visible.
     * @param offset The offset for the property elements, used for indentation in the ClickGui.
     * @return A list of property elements with the specified visibility condition.
     */
    private static List<PropertyElement<?, ?>> create(PropertyManager propertyManager, BooleanSupplier visibility, int offset) {
        List<PropertyElement<?, ?>> elements = new ObjectArrayList<>();

        for (Property<?, ?, ?> property : propertyManager.getAll()) {
            List<BooleanSupplier> visibilitySuppliers = new ObjectArrayList<>();
            visibilitySuppliers.add(visibility == null ? property::isVisible : () -> visibility.getAsBoolean() && property.isVisible());

            // Handle property parents
            int currentOffset = offset;
            Property<?, ?, ?> parent = property.getParent();
            while (parent != null) {
                Property<?, ?, ?> finalParent = parent;
                visibilitySuppliers.add(parent instanceof BooleanProperty ? () -> ((BooleanProperty) finalParent).getValue() && finalParent.isVisible() : parent::isVisible);
                currentOffset++;
                parent = parent.getParent();
            }

            // Create the property element
            BooleanSupplier propertyVisibility = () -> visibilitySuppliers.stream().allMatch(BooleanSupplier::getAsBoolean);
            PropertyElement<?, ?> element = createPropertyElement(property, propertyVisibility, currentOffset);
            if (element == null) {
                continue;
            }

            elements.add(element);
        }

        return elements;
    }

    /**
     * Creates a property element based on the type of the property.
     *
     * @param property The property to create an element for.
     * @param visibility A visibility condition that determines if the property should be visible.
     * @param offset The offset for the property element, used for indentation in the ClickGui.
     * @return A property element for the specified property, or null if the property type is not supported.
     */
    private static PropertyElement<?, ?> createPropertyElement(Property<?, ?, ?> property, BooleanSupplier visibility, int offset) {
        if (property instanceof BooleanProperty) {
            return new BooleanPropertyElement((BooleanProperty) property, visibility, offset);
        }

        if (property instanceof EnumProperty) {
            return new EnumPropertyElement<>((EnumProperty<?>) property, visibility, offset);
        }

        if (property instanceof LabelProperty) {
            return new LabelPropertyElement((LabelProperty) property, visibility, offset);
        }

        if (property instanceof MultiEnumProperty) {
            return new MultiEnumPropertyElement<>((MultiEnumProperty<?>) property, visibility, offset);
        }

        if (property instanceof NumberProperty) {
            return new NumberPropertyElement<>((NumberProperty<?>) property, visibility, offset);
        }

        if (property instanceof NumberRangeProperty<?>) {
            return new NumberRangePropertyElement<>((NumberRangeProperty<?>) property, visibility, offset);
        }

        return null;
    }
}
