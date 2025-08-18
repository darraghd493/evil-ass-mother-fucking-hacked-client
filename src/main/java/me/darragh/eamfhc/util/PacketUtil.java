package me.darragh.eamfhc.util;

import me.darragh.eamfhc.GameInstance;
import net.minecraft.network.protocol.Packet;

/**
 * Simplifies handling the transmission of packets in the game.
 *
 * @author darraghd493
 */
public class PacketUtil implements GameInstance {
    /**
     * Sends a packet.
     *
     * @param packet the packet to send
     */
    public static void sendPacket(Packet<?> packet) {
        if (minecraft.getConnection() != null) {
            minecraft.getConnection().send(packet);
        }
    }
}
