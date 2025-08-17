package me.darragh.eamfhc.module.impl.render;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.darragh.eamfhc.feature.property.PropertyEnum;
import me.darragh.eamfhc.feature.property.PropertyFactory;
import me.darragh.eamfhc.feature.property.PropertyMetadata;
import me.darragh.eamfhc.feature.property.type.enumerable.EnumProperty;
import me.darragh.eamfhc.module.Module;
import me.darragh.eamfhc.module.ModuleIdentifier;
import me.darragh.eamfhc.module.ModuleType;

/**
 * A module that displays a watermark on the screen.
 *
 * @author darraghd493
 */
@ModuleIdentifier(
        identifier = "watermark",
        displayName = "Watermark",
        description = "Displays a devilish watermark on the screen.",
        type = ModuleType.RENDER
)
public class Watermark extends Module {
    private final EnumProperty<ModeEnum> mode = PropertyFactory.enumPropertyBuilder(this, ModeEnum.class)
            .metadata(new PropertyMetadata("mode", "Mode"))
            .defaultValue(ModeEnum.GOOD_WATERMARK)
            .build();

    // TODO: Render

    /**
     * An enum representing the different modes of the watermark.
     */
    @Getter
    @RequiredArgsConstructor
    private enum ModeEnum implements PropertyEnum {
        GOOD_WATERMARK("good", "Good"),
        EVIL_WATERMARK("evil", "Evil"),
        TEXT("text", "Text");

        private final String identifier, name;
    }
}
