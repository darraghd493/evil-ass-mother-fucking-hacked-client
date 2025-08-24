package me.darragh.eamfhc.type;

import me.darragh.eamfhc.util.RotationUtil;
import me.darragh.math.util.MathUtil;

/**
 * Represents a rotation in 3D space with yaw and pitch components.
 *
 * @param yaw The yaw component of the rotation.
 * @param pitch The pitch component of the rotation.
 */
public record Rotation(float yaw, float pitch) {
    //region Mathematical Operations

    /**
     * Performs addition of this rotation with another rotation.
     *
     * @param rotation The rotation to add.
     * @return A new Rotation instance representing the sum of the two rotations.
     */
    public Rotation add(Rotation rotation) {
        return new Rotation(
                this.yaw + RotationUtil.getWrappedAngle(rotation.yaw),
                this.pitch + rotation.pitch
        );
    }

    /**
     * Performs subtraction of another rotation from this rotation.
     *
     * @param rotation The rotation to subtract.
     * @return A new Rotation instance representing the difference between the two rotations.
     */
    public Rotation subtract(Rotation rotation) {
        return new Rotation(
                this.yaw - RotationUtil.getWrappedAngle(rotation.yaw),
                this.pitch - rotation.pitch
        );
    }

    /**
     * Multiplies this rotation by a scalar value.
     *
     * @param scalar The scalar value to multiply by.
     * @return A new Rotation instance representing the scaled rotation.
     */
    public Rotation scale(float scalar) {
        return new Rotation(
                this.yaw * scalar,
                this.pitch * scalar
        );
    }

    /**
     * Multiplies this rotation by another rotation component-wise.
     *
     * @param rotation The rotation to multiply with.
     * @return A new Rotation instance representing the component-wise product of the two rotations.
     */
    public Rotation multiply(Rotation rotation) {
        return new Rotation(
                this.yaw * rotation.yaw,
                this.pitch * rotation.pitch
        );
    }

    /**
     * Divides this rotation by another rotation component-wise.
     *
     * @param rotation The rotation to divide by.
     * @return A new Rotation instance representing the component-wise quotient of the two rotations.
     */
    public Rotation divide(Rotation rotation) {
        return new Rotation(this.yaw / rotation.yaw, this.pitch / rotation.pitch);
    }

    /**
     * Linearly interpolates between this rotation and a target rotation by a given factor.
     *
     * @param target The target rotation to interpolate towards.
     * @param factor The interpolation factor (0.0 to 1.0).
     * @return A new Rotation instance representing the interpolated rotation.
     */
    public Rotation lerp(Rotation target, float factor) {
        return new Rotation(
                this.yaw + RotationUtil.getWrappedAngle(target.yaw - this.yaw) * factor,
                this.pitch + (target.pitch - this.pitch) * factor
        );
    }

    /**
     * Clamps the pitch of this rotation to the range [-90, 90] degrees.
     *
     * @return A new Rotation instance with the clamped pitch.
     */
    public Rotation clamp() {
        return new Rotation(
                this.yaw,
                MathUtil.clampFloat(this.pitch, -90.0F, 90.0F)
        );
    }

    /**
     * Normalises this rotation to have a length of 1.
     *
     * @return A new Rotation instance representing the normalised rotation.
     */
    public Rotation normalise() {
        float length = (float) Math.hypot(this.yaw, this.pitch);
        if (length == 0.0F) {
            return new Rotation(0.0F, 0.0F);
        }
        return new Rotation(this.yaw / length, this.pitch / length);
    }

    /**
     * Calculates the length (magnitude) of this rotation vector.
     *
     * @return The length of the rotation vector.
     */
    public float length() {
        return (float) Math.hypot(this.yaw, this.pitch);
    }

    /**
     * Calculates the Euclidean distance between this rotation and another rotation.
     *
     * @param rotation The other rotation to calculate the distance to.
     * @return The Euclidean distance between the two rotations.
     */
    public double distanceTo(Rotation rotation) {
        float deltaYaw = RotationUtil.getWrappedAngle(this.yaw - rotation.yaw);
        float deltaPitch = this.pitch - rotation.pitch;
        return Math.hypot(deltaYaw, deltaPitch);
    }
    //endregion
}
