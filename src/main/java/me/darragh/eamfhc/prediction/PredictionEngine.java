package me.darragh.eamfhc.prediction;

import me.darragh.eamfhc.type.MovementInput;
import me.darragh.eamfhc.type.Rotation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * A base-line interface for interacting with prediction engines.
 *
 * @author darraghd493
 */
public interface PredictionEngine {
    @NotNull Vec3 predictPosition(@NotNull ClientLevel level, @NotNull Vec3 position, @NotNull Vec3 motion, @NotNull Rotation rotation, @NotNull MovementInput movementInput, int ticks);
}
