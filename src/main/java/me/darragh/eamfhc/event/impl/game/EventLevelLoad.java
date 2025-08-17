package me.darragh.eamfhc.event.impl.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.darragh.eamfhc.event.ClientEvent;

/**
 * Called upon world load.
 *
 * @see me.darragh.eamfhc.processor.impl.forge.EventWrapperProcessor
 * @see net.minecraftforge.event.level.LevelEvent.Load
 *
 * @author darraghd493
 */
@Getter
@RequiredArgsConstructor
public class EventLevelLoad extends ClientEvent {
}
