package me.darragh.eamfhc.scguns.util;

import lombok.experimental.UtilityClass;
import me.darragh.eamfhc.type.Rotation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.ribs.scguns.common.Gun;
import top.ribs.scguns.item.GunItem;
import top.ribs.scguns.network.PacketHandler;
import top.ribs.scguns.network.message.C2SMessageAim;
import top.ribs.scguns.network.message.C2SMessageShoot;
import top.ribs.scguns.network.message.C2SMessageShooting;

/**
 * A utility class for handling gun-related operations in the game.
 *
 * @author darraghd493
 */
@UtilityClass
public class GunUtil {
    /**
     * Determines if a gun is empty (i.e., cannot shoot).
     *
     * @param player The player holding the gun.
     * @param heldItem The item stack representing the gun.
     * @return True if the gun is empty, false otherwise.
     */
    public static boolean isGunEmpty(Player player, ItemStack heldItem) {
        if (!(heldItem.getItem() instanceof GunItem)) {
            return false;
        }
        if (player.isSpectator()) {
            return false;
        }
        return (!Gun.hasAmmo(heldItem) || !Gun.canShoot(heldItem)) && !player.isCreative();
    }

    //region Networking
    /**
     * Sends the shooting state to the server.
     *
     * @see C2SMessageShooting
     *
     * @param state The shooting state (true if shooting, false otherwise).
     */
    public static void sendShootingState(boolean state) {
        PacketHandler.getPlayChannel().sendToServer(new C2SMessageShooting(state));
    }

    /**
     * Sends the aiming state to the server.
     *
     * @see C2SMessageAim
     *
     * @param state The aiming state (true if aiming, false otherwise).
     */
    public static void sendAimingState(boolean state) {
        PacketHandler.getPlayChannel().sendToServer(new C2SMessageAim(state));
    }

    /**
     * Sends a shooting request to the server with the specified rotation.
     *
     * @see C2SMessageShoot
     *
     * @param rotation The rotation to shoot at.
     */
    public static void sendShootingRequest(Rotation rotation) {
        PacketHandler.getPlayChannel().sendToServer(new C2SMessageShoot(rotation.yaw(), rotation.pitch()));
    }
    //endregion
}
