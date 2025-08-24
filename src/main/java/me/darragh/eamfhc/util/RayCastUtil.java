package me.darragh.eamfhc.util;

import lombok.experimental.UtilityClass;
import me.darragh.eamfhc.type.Rotation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

/**
 * A utility class for performing raycasting operations in the game.
 *
 * @author darraghd493
 */
@UtilityClass
public class RayCastUtil {
    /**
     * Performs a raycast from the player's eye position in the direction they are looking, up to a specified reach distance.
     *
     * @param player The player performing the raycast.
     * @param level The game level in which to perform the raycast.
     * @param from The starting position of the raycast.
     * @param to The ending position of the raycast.
     * @return The result of the raycast, containing information about any block hit.
     */
    public static @NotNull BlockHitResult raycastBlock(@NotNull Player player, @NotNull Level level, @NotNull Vec3 from, @NotNull Vec3 to) {
        return level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
    }

    /**
     * Performs a raycast from the player's eye position in the direction they are looking, up to a specified reach distance.
     *
     * @param player The player performing the raycast.
     * @param level The game level in which to perform the raycast.
     * @param point The starting position of the raycast.
     * @param rotation The rotation (pitch and yaw) representing the direction of the raycast.
     * @param reach The maximum distance the raycast should check for collisions.
     * @return The result of the raycast, containing information about any block hit.
     */
    public static BlockHitResult raycastBlock(@NotNull Player player, @NotNull Level level, @NotNull Vec3 point, @NotNull Rotation rotation, double reach) {
        Vec3 direction = VectorUtil.getVectorFromRotation(rotation),
                to = point.add(direction.x * reach, direction.y * reach, direction.z * reach);
        return raycastBlock(player, level, point, to);
    }

    /**
     * Performs an entity raycast in the look direction up to a reach distance.
     *
     * @param player The player performing the raycast.
     * @param level The game level in which to perform the raycast.
     * @param reach The maximum distance the raycast should check for entities.
     * @param filter A predicate to filter which entities can be hit by the raycast.
     * @return The result of the entity raycast, or null if no entity was hit
     */
    public static EntityHitResult raycastEntity(@NotNull Player player, @NotNull Level level, @NotNull Vec3 point, @NotNull Rotation rotation, double reach, @NotNull Predicate<Entity> filter) {
        Vec3 direction = VectorUtil.getVectorFromRotation(rotation),
                to = point.add(direction.x * reach, direction.y * reach, direction.z * reach);
        AABB searchBox = player.getBoundingBox().expandTowards(direction.scale(reach)).inflate(1.0D);
        return ProjectileUtil.getEntityHitResult(level, player, point, to, searchBox, filter);
    }

    /**
     * Performs a raycast against a target aligned bounding box in the look direction up to a reach distance.
     *
     * @param point The starting point of the raycast.
     * @param rotation The rotation (yaw, pitch) for the ray direction.
     * @param reach The maximum distance the raycast should check.
     * @param aabb The target bounding box to test the ray against.
     * @return The location where the ray intersects the AABB, or null if no intersection occurs.
     */
    public static @Nullable Vec3 raycastAABB(@NotNull Vec3 point, @NotNull Rotation rotation, double reach, @NotNull AABB aabb) {
        Vec3 direction = VectorUtil.getVectorFromRotation(rotation),
                to = point.add(direction.x * reach, direction.y * reach, direction.z * reach);
        return aabb.clip(point, to).orElse(null);
    }

    /**
     * Performs a combined raycast for blocks and entities, returning whichever is closer.
     *
     * @param player The player performing the raycast.
     * @param level The game level in which to perform the raycast.
     * @param reach The maximum distance the raycast should check for collisions.
     * @param filter A predicate to filter which entities can be hit by the raycast.
     * @return The result of the raycast, either a block hit or an entity hit, whichever is closer.
     */
    public static HitResult raycast(@NotNull Player player, @NotNull Level level, @NotNull Vec3 point, @NotNull Rotation rotation, double reach, @NotNull Predicate<Entity> filter) {
        Vec3 direction = VectorUtil.getVectorFromRotation(rotation),
                to = point.add(direction.x * reach, direction.y * reach, direction.z * reach);

        BlockHitResult blockResult = raycastBlock(player, level, point, to);
        EntityHitResult entityResult = raycastEntity(player, level, point, rotation, reach, filter);

        if (entityResult != null) {
            double blockDist = point.distanceTo(blockResult.getLocation()),
                    entityDist = point.distanceTo(entityResult.getLocation());
            if (entityDist < blockDist) {
                return entityResult;
            }
        }

        return blockResult;
    }

    /**
     * Performs a combined raycast for blocks, entities and a custom axis aligned bounding box, returning whichever is closer.
     *
     * @param player The player performing the raycast.
     * @param level The game level in which to perform the raycast.
     * @param reach The maximum distance the raycast should check for collisions.
     * @param filter A predicate to filter which entities can be hit by the raycast
     * @param aabb The target bounding box to test the ray against.
     * @return True if the AABB is hit first, false if a block or entity is hit first.
     */
    public static boolean raycast(@NotNull Player player, @NotNull Level level, @NotNull Vec3 point, @NotNull Rotation rotation, double reach, @NotNull Predicate<Entity> filter, @NotNull AABB aabb) {
        Vec3 direction = VectorUtil.getVectorFromRotation(rotation),
                to = point.add(direction.x * reach, direction.y * reach, direction.z * reach);

        BlockHitResult blockResult = raycastBlock(player, level, point, to);
        EntityHitResult entityResult = raycastEntity(player, level, point, rotation, reach, filter);
        Vec3 aabbResult = raycastAABB(point, rotation, reach, aabb);

        if (aabbResult == null) {
            return false;
        }

        double blockDist = point.distanceTo(blockResult.getLocation()),
                entityDist = entityResult != null ? point.distanceTo(entityResult.getLocation()) : Double.MAX_VALUE,
                aabbDist = point.distanceTo(aabbResult);

        if (entityDist < blockDist && entityDist < aabbDist) {
            return false;
        } else {
            return aabbDist < blockDist;
        }
    }
}
