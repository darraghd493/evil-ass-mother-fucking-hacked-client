package me.darragh.eamfhc.type;

import me.darragh.eamfhc.util.RotationUtil;
import me.darragh.math.util.MathUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Stores an abstract representation of a player's movement input.
 *
 * @see me.darragh.eamfhc.prediction.PredictionEngine
 *
 * @author darraghd493
 */
public record MovementInput(
        byte forwards,
        byte strafing,
        boolean sneaking,
        boolean sprinting,
        boolean usingItem // not really movement related, but affects speed so included :p
        // jumping is not included as it we cannot for certain tell if done is
) {
    /**
     * Identifies the movement input from the change in position.
     *
     * @param player The player whose movement input is being identified.
     * @param previousPosition The previous position of the player.
     * @param currentPosition The current position of the player.
     * @param sneaking Whether the player is sneaking.
     * @param sprinting Whether the player is sprinting.
     * @param usingItem Whether the player is using an item.
     * @return The identified movement input.
     */
    public static MovementInput identify(Player player, Vec3 previousPosition, Vec3 currentPosition, boolean sneaking, boolean sprinting, boolean usingItem) {
        Rotation rotation = RotationUtil.calculateRotations(previousPosition, currentPosition);
        int direction = (int) MathUtil.stepDouble(
                RotationUtil.getWrappedAngle(rotation.yaw() - player.getXRot()),
                45.0D
        );
        
        byte forwards = 0, strafing = 0;

        if (previousPosition.distanceTo(currentPosition) > 0.1D) {
            switch (direction) {
                case 0 -> forwards = 1;
                case 45 -> {
                    forwards = 1;
                    strafing = -1;
                }
                case 90 -> strafing = -1;
                case 135 -> {
                    forwards = -1;
                    strafing = -1;
                }
                case -180 -> forwards = -1;
                case -135 -> {
                    forwards = -1;
                    strafing = 1;
                }
                case -90 -> strafing = 1;
                case -45 -> {
                    forwards = 1;
                    strafing = 1;
                }
            }
        }

        return new MovementInput(forwards, strafing, sneaking, sprinting, usingItem);
    }

    /**
     * Identifies the movement input from the change in player position.
     *
     * @param player The player whose movement input is being identified.
     * @return The identified movement input.
     */
    public static MovementInput identify(Player player) {
        return identify(
                player,
                new Vec3(
                        player.xOld,
                        player.yOld,
                        player.zOld
                ),
                new Vec3(
                        player.getX(),
                        player.getY(),
                        player.getZ()
                ),
                player.isCrouching(),
                player.isSprinting(),
                player.isUsingItem()
        );
    }
}
