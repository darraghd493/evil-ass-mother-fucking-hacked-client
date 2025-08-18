package me.darragh.eamfhc.screen.clickgui.property;

import me.darragh.eamfhc.feature.property.type.LabelProperty;

import java.util.function.BooleanSupplier;

/**
 * A property element for a {@link LabelProperty} in the ClickGui.
 *
 * @author darraghd493
 */
public class LabelPropertyElement extends PropertyElement<LabelProperty, String> {
    protected LabelPropertyElement(LabelProperty property, BooleanSupplier visibility, int offset) {
        super(property, visibility, offset);
    }
}
