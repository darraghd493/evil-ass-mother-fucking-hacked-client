package me.darragh.eamfhc.util;

import me.darragh.eamfhc.type.Rotation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * A utility class for vector-related operations.
 *
 * @author darraghd493
 */
public class VectorUtil {
    /**
     * Rotates a vector around a given axis by a specified angle using Rodrigues' rotation formula.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Rodrigues%27_rotation_formula">Rodrigues' rotation formula</a>
     * @see <a href="https://github.com/ribs498/Scorched-Guns-1.20.1/blob/master/src/main/java/top/ribs/scguns/entity/projectile/ProjectileEntity.java#L259">Source</a>
     *
     * @param vector The vector to rotate.
     * @param axis The axis to rotate around (should be a normalised vector).
     * @param angle The angle in radians to rotate by.
     * @return The rotated vector.
     */
    public static Vec3 rotateVector(Vec3 vector, Vec3 axis, float angle) {
        float sin = Mth.sin(angle),
                cos = Mth.cos(angle),
                dot = (float) vector.dot(axis);

        return new Vec3(
                vector.x * cos + (axis.y * vector.z - axis.z * vector.y) * sin + axis.x * dot * (1.0F - cos),
                vector.y * cos + (axis.z * vector.x - axis.x * vector.z) * sin + axis.y * dot * (1.0F - cos),
                vector.z * cos + (axis.x * vector.y - axis.y * vector.x) * sin + axis.z * dot * (1.0F - cos)
        );
    }

    /**
     * Returns a vector pointing in the direction of a given rotation.
     *
     * @param rotation The rotation to get the vector from.
     * @return The vector pointing in the direction of the rotation.
     */
    public static Vec3 getVectorFromRotation(Rotation rotation) {
        float yawRad = (float) Math.toRadians(rotation.yaw()),
                pitchRad = (float) Math.toRadians(rotation.pitch());

        float x = -Mth.sin(yawRad) * Mth.cos(pitchRad),
                y = -Mth.sin(pitchRad),
                z = Mth.cos(yawRad) * Mth.cos(pitchRad);

        return new Vec3(x, y, z);
    }

    /**
     * Identifies if a vector (between two points) intersect with an axis-aligned bounding box.
     *
     * @param start The starting point of the vector.
     * @param end The ending point of the vector.
     * @param aabb The axis-aligned bounding box to check against.
     * @param steps The number of steps to divide the vector into for checking.
     * @return True if the vector intersects with the AABB, false otherwise.
     */
    public static boolean doesVectorIntersectAABB(Vec3 start, Vec3 end, AABB aabb, int steps) {
        double tMin = 0.0;
        double tMax = 1.0;

        Vec3 direction = end.subtract(start);

        for (int i = 0; i < 3; i++) {
            double startCoord = i == 0.0D ? start.x : (i == 1.0D ? start.y : start.z),
                    dirCoord = i == 0.0D ? direction.x : (i == 1.0D ? direction.y : direction.z);
            double minCoord = i == 0.0D ? aabb.minX : (i == 1.0D ? aabb.minY : aabb.minZ),
                    maxCoord = i == 0.0D ? aabb.maxX : (i == 1.0D ? aabb.maxY : aabb.maxZ);

            if (Math.abs(dirCoord) < 1e-8D) {
                if (startCoord < minCoord || startCoord > maxCoord) {
                    return false;
                }
            } else {
                double t1 = (minCoord - startCoord) / dirCoord,
                        t2 = (maxCoord - startCoord) / dirCoord;

                if (t1 > t2) {
                    double temp = t1;
                    t1 = t2;
                    t2 = temp;
                }

                tMin = Math.max(tMin, t1);
                tMax = Math.min(tMax, t2);

                if (tMin > tMax) {
                    return false;
                }
            }
        }

        return true;
    }
}
