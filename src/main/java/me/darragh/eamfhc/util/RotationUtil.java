package me.darragh.eamfhc.util;

import me.darragh.eamfhc.type.Rotation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

/**
 * A utility class for performing rotation calculations.
 *
 * @author darraghd493
 */
public class RotationUtil {
    /**
     * Calculates the yaw and pitch required to look from one position to another.
     *
     * @param from The starting position.
     * @param to The target position.
     * @return The calculated rotation.
     */
    public static Rotation calculateRotations(Vec3 from, Vec3 to) {
        Vec3 diff = to.subtract(from);

        double distance = Math.hypot(diff.x, diff.z);
        float yaw = (float) (Math.toDegrees(Mth.atan2(diff.z, diff.x))) - 90.0F,
                pitch = (float) -(Math.toDegrees(Mth.atan2(diff.y, distance)));

        return new Rotation(getWrappedAngle(yaw), pitch);
    }

    /**
     * Wraps an angle to the range [-180, 180).
     *
     * @param angle The angle to wrap.
     * @return The wrapped angle.
     */
    public static float getWrappedAngle(float angle) {
        angle %= 360.0F;
        if (angle >= 180.0F) angle -= 360.0F;
        if (angle < -180.0F) angle += 360.0F;
        return angle;
    }

    /**
     * Calculates the yaw and pitch required to look at a specific position from the player's eye position.
     *
     * @param from The starting position.
     * @param to The target position.
     * @return The calculated rotation.
     */
    public static float calculateFov(Vec3 from, Vec3 to, float yaw) {
        return Math.abs(
                getWrappedAngle(calculateRotations(from, to).yaw() - yaw)
        );
    }
}
