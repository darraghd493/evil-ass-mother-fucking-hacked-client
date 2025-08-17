package me.darragh.eamfhc.processor.impl.client;

import com.mojang.blaze3d.platform.InputConstants;
import it.unimi.dsi.fastutil.objects.Object2BooleanArrayMap;
import me.darragh.eamfhc.Client;
import me.darragh.eamfhc.event.impl.input.EventKeyInput;
import me.darragh.eamfhc.event.impl.input.EventMouseInput;
import me.darragh.eamfhc.feature.bindable.BindInputDevice;
import me.darragh.eamfhc.processor.Processor;
import me.darragh.eamfhc.processor.ProcessorIdentifier;
import me.darragh.eamfhc.processor.ProcessorType;
import me.darragh.event.bus.Listener;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;

import java.util.Map;

/**
 * A processor that handles key bindings for modules.
 *
 * @author darraghd493
 */
@ProcessorIdentifier(
        identifier = "binding-handler",
        displayName = "Binding Handler",
        description = "Handles key bindings for modules.",
        type = ProcessorType.CLIENT
)
public class BindingHandlerProcessor extends Processor {
    private final Map<InputIdentifier, Boolean> inputStates = new Object2BooleanArrayMap<>();

    @Listener
    public void onMouseInput(EventMouseInput event) {
        boolean state = event.getAction() == InputConstants.PRESS;
        this.handleBindable(BindInputDevice.MOUSE, event.getButton(), state);
    }

    @Listener
    public void onKeyInput(EventKeyInput event) {
        if (event.getAction() == InputConstants.REPEAT) return;
        boolean state = event.getAction() == InputConstants.PRESS;
        this.handleBindable(BindInputDevice.KEYBOARD, event.getKey(), state);
    }

    private void handleBindable(@SuppressWarnings("SameParameterValue") BindInputDevice inputDevice, int code, boolean state) {
        InputIdentifier identifier = new InputIdentifier(inputDevice, code);
        boolean oldState = this.inputStates.getOrDefault(identifier, false);

        if (minecraft.screen == null || minecraft.screen instanceof ContainerScreen) {
            Client.INSTANCE.getModuleRepositoryService().getFeatures().forEach(module -> {
                if (module.getBind().getDevice() == inputDevice && module.getBind().getKeyCode() == code &&
                        module.getBind().getTrigger().evaluate(oldState, state))
                    module.toggle();
            });
        }

        this.inputStates.put(identifier, state);
    }

    private record InputIdentifier(BindInputDevice inputDevice, int code) {
    }
}
