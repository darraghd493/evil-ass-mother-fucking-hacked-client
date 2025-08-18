package me.darragh.eamfhc.module.impl.render;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.darragh.eamfhc.Client;
import me.darragh.eamfhc.event.impl.render.EventRenderOverlay;
import me.darragh.eamfhc.feature.property.PropertyEnum;
import me.darragh.eamfhc.feature.property.PropertyFactory;
import me.darragh.eamfhc.feature.property.PropertyMetadata;
import me.darragh.eamfhc.feature.property.type.enumerable.EnumProperty;
import me.darragh.eamfhc.module.Module;
import me.darragh.eamfhc.module.ModuleIdentifier;
import me.darragh.eamfhc.module.ModuleType;
import me.darragh.eamfhc.util.FontHandler;
import me.darragh.event.bus.Listener;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

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
public class WatermarkModule extends Module {
    private final EnumProperty<ModeEnum> mode = PropertyFactory.enumPropertyBuilder(this, ModeEnum.class)
            .metadata(new PropertyMetadata("mode", "Mode"))
            .defaultValue(ModeEnum.TEXT)
            .build();

    @Listener
    public void onRenderOverlay(EventRenderOverlay event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        //noinspection SwitchStatementWithTooFewBranches
        switch (this.mode.getValue()) {
            case TEXT -> {
                FontHandler.draw("evil ass mother fucking hacked client", 2.0F, 2.0F, Color.PINK.getRGB(), true, guiGraphics, guiGraphics.pose(), guiGraphics.bufferSource());
                FontHandler.draw("1.20.1 Forge", 2.0F, 2.0F + FontHandler.getHeight(), Color.GRAY.getRGB(), true, guiGraphics, guiGraphics.pose(), guiGraphics.bufferSource());

                int y = 2 + FontHandler.getHeight() * 2;

                for (Module module : Client.INSTANCE.getModuleRepositoryService().getFeatures()) {
                    if (!module.isEnabled()) continue;
                    FontHandler.draw(module.getMetadata().getDisplayName(), 2.0F, y, Color.WHITE.getRGB(), true, guiGraphics, guiGraphics.pose(), guiGraphics.bufferSource());
                    y += FontHandler.getHeight();
                }
            }
        }
    }

    /**
     * An enum representing the different modes of the watermark.
     */
    @Getter
    @RequiredArgsConstructor
    private enum ModeEnum implements PropertyEnum {
        TEXT("text", "Text");

        private final String identifier, name;
    }
}
