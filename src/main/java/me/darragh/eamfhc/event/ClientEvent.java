package me.darragh.eamfhc.event;

import me.darragh.eamfhc.Client;
import me.darragh.event.Event;
import me.darragh.event.SimpleEvent;

/**
 * Base class for all client events.
 * <p>
 * This class extends {@link SimpleEvent} and provides a method to post the event
 * to the client's event dispatcher.
 *
 * @author darraghd493
 */
public class ClientEvent extends SimpleEvent {
    @Override
    public <T extends Event> T post() {
        Client.INSTANCE.getEventDispatcher().invoke(this);
        //noinspection unchecked
        return (T) this;
    }
}
