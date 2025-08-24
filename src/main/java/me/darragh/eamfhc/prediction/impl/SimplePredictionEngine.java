package me.darragh.eamfhc.prediction.impl;

import me.darragh.eamfhc.prediction.PredictionEngine;
import me.darragh.eamfhc.type.MovementInput;
import me.darragh.eamfhc.type.Rotation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * A very basic prediction engine, performing the bare essentials for prediction.
 * <p>
 * Modeled after vanilla 1.8.
 *
 * @author darraghd493
 */
public class SimplePredictionEngine implements PredictionEngine {
    @Override
    public @NotNull Vec3 predictPosition(@NotNull ClientLevel level, @NotNull Vec3 position, @NotNull Vec3 motion, @NotNull Rotation rotation, @NotNull MovementInput movementInput, int ticks) {
        double motionX = motion.x,
                motionZ = motion.z;

        return position.add(
                motionX * ticks,
                motion.y,
                motionZ * ticks
        );
    }
}
