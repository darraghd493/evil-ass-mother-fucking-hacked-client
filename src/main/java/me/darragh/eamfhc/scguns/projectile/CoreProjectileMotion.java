package me.darragh.eamfhc.scguns.projectile;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.darragh.eamfhc.Copyable;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Handles the fundamentals of projectile motion for prediction.
 *
 * @author darraghd493
 */
@Data
@AllArgsConstructor
public class CoreProjectileMotion implements Copyable<CoreProjectileMotion> {
    private Vec3 position,  velocity;
    private double gravity;
    private int life, tickCount;

    /**
     * Update position based on velocity and gravity
     */
    public void tick() {
        this.velocity = this.velocity.add(0.0D, this.gravity, 0.0D);
        this.position = this.position.add(this.velocity);
        this.tickCount++;
    }

    /**
     * Checks if projectile lifetime is over
     *
     * @return True if projectile is expired, false otherwise.
     */
    public boolean isExpired() {
        return this.tickCount >= this.life;
    }

    /**
     * Returns whether the projectile is within an axis-aligned bounding box.
     *
     * @param aabb The axis-aligned bounding box to check against.
     * @return True if the projectile is within the AABB, false otherwise.
     */
    public boolean isWithinAABB(AABB aabb) {
        Vec3 pos = this.position;
        return pos.x >= aabb.minX && pos.x <= aabb.maxX &&
                pos.y >= aabb.minY && pos.y <= aabb.maxY &&
                pos.z >= aabb.minZ && pos.z <= aabb.maxZ;
    }

    @Override
    public CoreProjectileMotion copy() {
        return new CoreProjectileMotion(this.position, this.velocity, this.gravity, this.life, this.tickCount);
    }
}