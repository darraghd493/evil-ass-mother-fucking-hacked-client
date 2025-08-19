package me.darragh.eamfhc.event.impl.player;

import me.darragh.eamfhc.event.ClientEvent;
import net.minecraft.client.player.LocalPlayer;

/**
 * Triggered on player pre-tick.
 *
 * @see me.darragh.eamfhc.mixins.MixinLocalPlayer
 * @see LocalPlayer#tick()
 *
 * @author darraghd493
 */
public class EventPlayerPreTick extends ClientEvent {
}
