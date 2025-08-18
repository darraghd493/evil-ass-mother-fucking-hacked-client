package me.darragh.eamfhc.module.impl.render;

import com.mojang.blaze3d.platform.InputConstants;
import me.darragh.eamfhc.module.Module;
import me.darragh.eamfhc.module.ModuleIdentifier;
import me.darragh.eamfhc.module.ModuleType;
import me.darragh.eamfhc.screen.clickgui.ClickGuiScreen;
import net.minecraft.network.chat.Component;

/**
 * A module that triggers opening the ClickGui screen.
 *
 * @author darraghd493
 */
@ModuleIdentifier(
        identifier = "click-gui",
        displayName = "Click Gui",
        description = "Opens the Click Gui.",
        type = ModuleType.RENDER,
        defaultKeybind = InputConstants.KEY_RSHIFT
)
public class ClickGuiModule extends Module {
    private ClickGuiScreen screen;

    @Override
    protected void onEnable() {
        super.onEnable();
        if (this.screen == null) {
            this.screen = new ClickGuiScreen(
                    Component.literal("ClickGui")
            );
        }
        minecraft.setScreen(this.screen);
        this.disable();
    }
}
