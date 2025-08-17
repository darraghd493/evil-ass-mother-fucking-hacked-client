package me.darragh.eamfhc.event.impl.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.darragh.eamfhc.event.ClientEvent;

/**
 * Called upon world unload.
 *
 * @see me.darragh.eamfhc.processor.impl.forge.EventWrapperProcessor
 * @see net.minecraftforge.event.level.LevelEvent.Unload
 *
 * @author darraghd493
 */
@Getter
@RequiredArgsConstructor
public class EventLevelUnload extends ClientEvent {
}
