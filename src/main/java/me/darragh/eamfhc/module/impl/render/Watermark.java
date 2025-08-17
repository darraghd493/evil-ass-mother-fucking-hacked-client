package me.darragh.eamfhc.module.impl.render;

import me.darragh.eamfhc.feature.property.PropertyFactory;
import me.darragh.eamfhc.feature.property.PropertyMetadata;
import me.darragh.eamfhc.feature.property.type.BooleanProperty;
import me.darragh.eamfhc.module.Module;
import me.darragh.eamfhc.module.ModuleIdentifier;
import me.darragh.eamfhc.module.ModuleType;

@ModuleIdentifier(
        identifier = "watermark",
        displayName = "Watermark",
        description = "Displays a devilish watermark on the screen.",
        type = ModuleType.RENDER
)
public class Watermark extends Module {
    private final BooleanProperty goodWatermark = PropertyFactory.booleanPropertyBuilder(this)
            .metadata(new PropertyMetadata("good-watermark", "Good Watermark", "Displays a watermark with a heartwarmingly design."))
            .build();

    // TODO: Render
}
