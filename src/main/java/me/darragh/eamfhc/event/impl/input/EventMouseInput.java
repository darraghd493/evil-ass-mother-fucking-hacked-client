package me.darragh.eamfhc.event.impl.input;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.darragh.eamfhc.event.ClientEvent;

/**
 * Called upon keypress.
 *
 * @see me.darragh.eamfhc.processor.impl.forge.EventWrapperProcessor
 * @see net.minecraftforge.client.event.InputEvent.MouseButton
 * @see net.minecraft.client.MouseHandler
 *
 * @author darraghd493
 */
@Getter
@RequiredArgsConstructor
public class EventMouseInput extends ClientEvent {
    private final int button, action, modifiers;
}
