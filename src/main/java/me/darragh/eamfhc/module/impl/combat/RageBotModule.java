package me.darragh.eamfhc.module.impl.combat;

import lombok.extern.slf4j.Slf4j;
import me.darragh.eamfhc.event.impl.player.EventPlayerPreTick;
import me.darragh.eamfhc.feature.property.PropertyFactory;
import me.darragh.eamfhc.feature.property.PropertyMetadata;
import me.darragh.eamfhc.feature.property.constraints.NumberPropertyConstraints;
import me.darragh.eamfhc.feature.property.type.BooleanProperty;
import me.darragh.eamfhc.feature.property.type.LabelProperty;
import me.darragh.eamfhc.feature.property.type.number.IntegerProperty;
import me.darragh.eamfhc.module.Module;
import me.darragh.eamfhc.module.ModuleIdentifier;
import me.darragh.eamfhc.module.ModuleType;
import me.darragh.eamfhc.prediction.PredictionEngine;
import me.darragh.eamfhc.prediction.PredictionFactory;
import me.darragh.eamfhc.scguns.projectile.CoreProjectileMotion;
import me.darragh.eamfhc.scguns.util.GunUtil;
import me.darragh.eamfhc.type.MovementInput;
import me.darragh.eamfhc.type.Rotation;
import me.darragh.eamfhc.util.*;
import me.darragh.event.bus.Listener;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.ribs.scguns.attributes.SCAttributes;
import top.ribs.scguns.common.Gun;
import top.ribs.scguns.item.GunItem;
import top.ribs.scguns.util.GunEnchantmentHelper;
import top.ribs.scguns.util.GunModifierHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static me.darragh.eamfhc.scguns.util.GunUtil.*;
import static top.ribs.scguns.compat.PlayerReviveHelper.*;

@Slf4j
@ModuleIdentifier(
        identifier = "scguns-ragebot",
        displayName = "SCGuns Ragebot-0.0.0",
        description = "Rage bot for Scorched Guns 2.",
        type = ModuleType.COMBAT
)
public class RageBotModule extends Module {
    public static final List<String> EXEMPT = new ArrayList<>(); // TODO: .scgunsexempt command

    private final IntegerProperty range = PropertyFactory.integerPropertyBuilder(this)
            .metadata(new PropertyMetadata("range", "Range", "The maximum range to target players."))
            .constraints(new NumberPropertyConstraints<>(1, 256, 1))
            .defaultValue(10)
            .build();

    private final IntegerProperty fov = PropertyFactory.integerPropertyBuilder(this)
            .metadata(new PropertyMetadata("fov", "FOV", "The field of view to target players."))
            .constraints(new NumberPropertyConstraints<>(1, 180, 1))
            .defaultValue(180)
            .build();

    //region Targeting Properties
    private final LabelProperty targetingLabel = PropertyFactory.labelPropertyBuilder(this)
            .metadata(new PropertyMetadata("targeting-label", "Targeting"))
            .build();

    private final BooleanProperty retainLastTarget = PropertyFactory.booleanPropertyBuilder(this)
            .metadata(new PropertyMetadata("retain-last-target", "Retain Last Target"))
            .parent(this.targetingLabel)
            .defaultValue(true)
            .build();

    private final BooleanProperty excludeBleeding = PropertyFactory.booleanPropertyBuilder(this)
            .metadata(new PropertyMetadata("exclude-bleeding", "Exclude Bleeding"))
            .parent(this.targetingLabel)
            .defaultValue(true)
            .build();
    //endregion

    //region Exploit Properties
    private final LabelProperty exploitLabel = PropertyFactory.labelPropertyBuilder(this)
            .metadata(new PropertyMetadata("exploit-label", "Exploit"))
            .build();

    private final BooleanProperty exploitEnsureAccuratePosition = PropertyFactory.booleanPropertyBuilder(this) // see https://github.com/ribs498/Scorched-Guns-1.20.1/blob/master/src/main/java/top/ribs/scguns/entity/projectile/ProjectileEntity.java#L144
            .metadata(new PropertyMetadata("exploit-ensure-accurate-position", "Ensure Accurate Position"))
            .parent(this.exploitLabel)
            .defaultValue(true)
            .build();
    //endregion

    //region Debug Properties
    private final LabelProperty debugLabel = PropertyFactory.labelPropertyBuilder(this)
            .metadata(new PropertyMetadata("debug-label", "Debug"))
            .build();

    private final BooleanProperty debugRepresentRotations = PropertyFactory.booleanPropertyBuilder(this)
            .metadata(new PropertyMetadata("debug-represent-rotations", "Represent Rotations"))
            .parent(this.debugLogs)
            .defaultValue(true) // todo: false
            .build();

    private final BooleanProperty debugLogs = PropertyFactory.booleanPropertyBuilder(this)
            .metadata(new PropertyMetadata("debug-logs", "Logs"))
            .parent(this.debugLogs)
            .defaultValue(true) // todo: false
            .build();
    //endregion

    // todo: non-normal guns?

    private static final PredictionEngine PREDICTION_ENGINE = PredictionFactory.createSimplePredictionEngine();
    private final List<Player> activeTargets = new ArrayList<>();
    private Player currentTarget = null;
    private AABB debugAABB = null;

    @SuppressWarnings("DataFlowIssue")
    @Listener
    public void onPlayerPreTick(EventPlayerPreTick event) {
        if (minecraft.level == null) return;
        assert minecraft.player != null; // cannot be null

        LocalPlayer localPlayer = minecraft.player;
        ItemStack stack = localPlayer.getMainHandItem();

        // Validate if we can shoot
        boolean valid = !GunUtil.isGunEmpty(localPlayer, stack);
        if (!valid) return;

        // Populate targets
        this.activeTargets.clear();
        for (Player player : minecraft.level.players()) {
            if (
                    player == localPlayer ||
                            EXEMPT.contains(player.getName().getString()) ||
                            (isBleeding(player) && this.excludeBleeding.getValue()) ||
                            player.distanceTo(localPlayer) > this.range.getValue() ||
                            !player.isAlive() ||
                            player.getHealth() <= 0.0F ||
                            player.isSpectator() ||
                            player.isCreative() ||
                            RotationUtil.calculateFov(
                                    localPlayer.getEyePosition(),
                                    player.getEyePosition(),
                                    localPlayer.getYRot()
                            ) > this.fov.getValue()// ||
//                            !RayCastUtil.isType(
//                                    RayCastUtil.raycast(
//                                            localPlayer,
//                                            minecraft.level,
//                                            localPlayer.getEyePosition(1.0F),
//                                            player.getEyePosition(1.0F)
//                                    ),
//                                    HitResult.Type.ENTITY
//                            )
                /* todo fov */
            ) {
                continue;
            }
            this.activeTargets.add(player);
        }

        // Select target
        this.activeTargets.sort(Comparator.comparingDouble(player -> player.distanceTo(localPlayer) + player.hurtTime));
        if (!this.retainLastTarget.getValue() || this.currentTarget == null || !this.activeTargets.contains(this.currentTarget) || this.currentTarget.getHealth() <= 0.0F) {
            this.currentTarget = this.activeTargets.isEmpty() ? null : this.activeTargets.get(0);
        }

        if (this.currentTarget == null) { // We don't want bad targets - handle final filtering here
            return;
        }

        // Handle init modifiers
        if (this.exploitEnsureAccuratePosition.getValue()) {
            PacketUtil.sendPacket(new ServerboundMovePlayerPacket.Pos(
                    localPlayer.getX(),
                    localPlayer.getY(),
                    localPlayer.getZ(),
                    true
            ));
        }

        // Calculate eye position
        // see https://github.com/ribs498/Scorched-Guns-1.20.1/blob/master/src/main/java/top/ribs/scguns/entity/projectile/ProjectileEntity.java#L144
        Vec3 eyePosition = this.exploitEnsureAccuratePosition.getValue() ?
                localPlayer.getEyePosition(1.0F) :
                new Vec3(
                        localPlayer.xOld + (localPlayer.getX() - localPlayer.xOld) / 2.0D,
                        localPlayer.yOld + (localPlayer.getY() - localPlayer.yOld) / 2.0D + localPlayer.getEyeHeight(),
                        localPlayer.zOld + (localPlayer.getZ() - localPlayer.zOld) / 2.0D
                );

        // Calculate the rotation
        Rotation rotation = this.consultEngine(
                localPlayer,
                this.currentTarget,
                eyePosition,
                stack
        );

        if (this.debugRepresentRotations.getValue()) {
            localPlayer.setYRot(rotation.yaw());
            localPlayer.setXRot(rotation.pitch());
        }

        // Shoot the target
        this.shoot(
                localPlayer,
                rotation
        );
    }

    //region Engine
    /**
     * Consults the "rage-bot engine" for a target position (handles calculating the position).
     *
     * @apiNote Documentation.
     * @implNote This was written at 3/4 AM, so it might be a bit jank. I need to sleep... zzZZZ
     *
     * @param localPlayer The local player.
     * @param targetPlayer The player to consult the engine for.
     * @param eyePosition The eye position of the local player.
     * @param weapon The weapon being used.
     * @return The rotation to shoot from.
     */
    // TODO: Consider initial spread/inaccuracy? see https://github.com/ribs498/Scorched-Guns-1.20.1/blob/master/src/main/java/top/ribs/scguns/entity/projectile/ProjectileEntity.java#L211
    // TODO: Spread? see https://github.com/ribs498/Scorched-Guns-1.20.1/blob/master/src/main/java/top/ribs/scguns/common/SpreadTracker.java
    private Rotation consultEngine(@NotNull LocalPlayer localPlayer, @NotNull Player targetPlayer, @NotNull Vec3 eyePosition, @NotNull ItemStack weapon) {
        final Vec3 currentTargetPosition = targetPlayer.getPosition(1.0F);

        final GunItem gunItem = (GunItem) weapon.getItem(); // we can be confident that the held item is a gun here due to prior checks
        final Gun modifiedGun = gunItem.getModifiedGun(weapon);
        final Gun.Projectile projectile = modifiedGun.getProjectile();

        // Step 1. Estimate how long (in ticks) it will take for the bullet to reach the target
        final AABB initialAABB = targetPlayer.getBoundingBox();
        final Rotation initialRotation = RotationUtil.calculateRotations(eyePosition, currentTargetPosition.add( // target the top of the playerm, .98 for safety
                0.0D, targetPlayer.getBbHeight() * 0.98D, 0.0D
        ));
        final CoreProjectileMotion initialProjectileMotion = this.createProjectileMotion(localPlayer, eyePosition, initialRotation, weapon, modifiedGun, projectile, null);

        Rotation resultRotation = initialRotation;

        int travelTicks = 0; // reused
        boolean hit = false; // reused

        while (!initialProjectileMotion.isExpired()) {
            Vec3 originalPosition = initialProjectileMotion.getPosition();
            initialProjectileMotion.tick();
            if (VectorUtil.doesVectorIntersectAABB(originalPosition, initialProjectileMotion.getPosition(), initialAABB, 5)) {
                hit = true;
                break;
            }
            travelTicks++;
        }

        if (!hit) { // if not hit - we cannot be confident in our prediction, so we can try and bruteforce it
            List<RotationResult> results = IntStream.range(0, 360 * 180)
                    .parallel()
                    .mapToObj(i -> {
                        int yaw = i / 180;
                        int pitch = (i % 180) - 90;
                        Rotation testRotation = new Rotation(pitch, yaw);

                        CoreProjectileMotion motion = this.createProjectileMotion(localPlayer, eyePosition, testRotation, weapon, modifiedGun, projectile, null);
                        int bruteforceTravelTicks = 0;
                        boolean bruteforceHit = false;

                        while (!motion.isExpired()) {
                            Vec3 originalPosition = motion.getPosition();
                            motion.tick();
                            if (VectorUtil.doesVectorIntersectAABB(originalPosition, motion.getPosition(), initialAABB, 5)) {
                                bruteforceHit = true;
                                break;
                            }
                            bruteforceTravelTicks++;
                        }

                        return new RotationResult(testRotation, bruteforceHit, bruteforceTravelTicks);
                    })
                    .filter(RotationResult::hit) // only keep hits
                    .sorted(Comparator.comparingDouble(r -> r.rotation().distanceTo(initialRotation)))
                    .toList();

            if (!results.isEmpty()) {
                RotationResult best = results.get(0);
                travelTicks = best.travelTicks();
                resultRotation = best.rotation();
                this.log("engine", "bruteforce found init rotation(%s): %s".formatted(travelTicks, best.rotation()), ChatFormatting.BLUE);
            } else {
                // TODO: uh oh! we couldn't hit the target at all - fallback to initial rotation
                this.log("engine", "failed to init hit target - fallback to base rotation", ChatFormatting.BLUE);
                return resultRotation;
            }
        }

        this.log("engine", "found init rotation(%s): %s".formatted(travelTicks, resultRotation), ChatFormatting.BLUE);

        // Step 2. Predict the target's position after the travel time
        final MovementInput predictedMovementInput = MovementInput.identify(targetPlayer);
        //noinspection DataFlowIssue
        final Vec3 predictedTargetPosition = PREDICTION_ENGINE.predictPosition(
                minecraft.level,
                currentTargetPosition,
                new Vec3(
                        targetPlayer.getX() - targetPlayer.xOld,
                        targetPlayer.getY() - targetPlayer.yOld,
                        targetPlayer.getZ() - targetPlayer.zOld
                ),
                new Rotation(targetPlayer.getXRot(), targetPlayer.getYRot()),
                predictedMovementInput,
                travelTicks + 1
        );
        final AABB predictedAABB = new AABB(
                targetPlayer.getBoundingBox().minX + (predictedTargetPosition.x - currentTargetPosition.x),
                targetPlayer.getBoundingBox().minY + (predictedTargetPosition.y - currentTargetPosition.y),
                targetPlayer.getBoundingBox().minZ + (predictedTargetPosition.z - currentTargetPosition.z),
                targetPlayer.getBoundingBox().maxX + (predictedTargetPosition.x - currentTargetPosition.x),
                targetPlayer.getBoundingBox().maxY + (predictedTargetPosition.y - currentTargetPosition.y),
                targetPlayer.getBoundingBox().maxZ + (predictedTargetPosition.z - currentTargetPosition.z)
        );
        final Rotation predictedRotation = RotationUtil.calculateRotations(eyePosition, predictedTargetPosition.add( // target the middle of the player
                0.0D, targetPlayer.getBbHeight() * 0.5D, 0.0D
        ));

        double distance = localPlayer.getPosition(1.0F).distanceTo(predictedTargetPosition);

        final List<Rotation> possiblePredictedRotations = IntStream.rangeClosed(-90, 90)
                .parallel()
                .mapToObj(pitch -> new Rotation(predictedRotation.yaw(), pitch))
                .filter(r -> RayCastUtil.raycast(localPlayer, minecraft.level, eyePosition, r, distance * 2.0D, e -> true, predictedAABB)) // TODO: FINISH THIS filter
                .sorted(Comparator.comparingDouble(r -> r.distanceTo(predictedRotation)))
                .toList();

        if (!possiblePredictedRotations.isEmpty()) {
            this.log("engine", "found %s possible pitch rotations".formatted(possiblePredictedRotations.size()), ChatFormatting.BLUE);
            this.log("engine", "possible rotations: %s".formatted(possiblePredictedRotations), ChatFormatting.DARK_BLUE);
            // pick the closest to the predicted rotation
            return possiblePredictedRotations.get(0);
        } else {
            this.log("engine", "failed to find any possible pitch rotations - falling back to predicted rotation", ChatFormatting.BLUE);
        }

        return predictedRotation;
    }

    /**
     * Creates a projectile motion instance based on the weapon and player stats.
     *
     * @see RageBotModule#createProjectileMotion(LocalPlayer, Vec3, Rotation, ItemStack, Gun, Gun.Projectile, Integer)
     *
     * @param localPlayer The local player.
     * @param eyePosition The eye position of the local player.
     * @param rotation The rotation to shoot at.
     * @param weapon The weapon being used.
     * @param modifiedGun The modified gun instance.
     * @param projectile The projectile being fired.
     * @param projectileLife Optional override for the projectile life (in ticks), if null, the modified projectile life will be used.
     * @return The projectile motion instance.
     */
    private CoreProjectileMotion createProjectileMotion(@NotNull LocalPlayer localPlayer, @NotNull Vec3 eyePosition, @NotNull Rotation rotation, @NotNull ItemStack weapon, @NotNull Gun modifiedGun, @NotNull Gun.Projectile projectile, @Nullable Integer projectileLife) {
        Vec3 motion = this.calculateInitialMotion(localPlayer, rotation, weapon, projectile);
        double gravity = modifiedGun.getProjectile().isGravity() ? GunModifierHelper.getModifiedProjectileGravity(weapon, -0.04) : 0.0; // https://github.com/ribs498/Scorched-Guns-1.20.1/blob/master/src/main/java/top/ribs/scguns/entity/projectile/ProjectileEntity.java#L131
        int life = projectileLife == null ? Math.min(
                GunModifierHelper.getModifiedProjectileLife(weapon, projectile.getLife()),
                100 // hard clip to 100, processing anymore is overkill and unnecessary
        ) : projectileLife;

        return new CoreProjectileMotion(
                eyePosition,
                motion,
                gravity,
                life,
                0
        );
    }

    /**
     * Calculates the initial motion of a projectile based on the weapon and player stats, although not accounting for spread/inaccuracy
     * due to its inherent randomness.
     *
     * @see <a href="https://github.com/ribs498/Scorched-Guns-1.20.1/blob/master/src/main/java/top/ribs/scguns/entity/projectile/ProjectileEntity.java#L107>Source</a>
     *
     * @param localPlayer The local player.
     * @param rotation The rotation to shoot at.
     * @param weapon The weapon being used.
     * @param projectile The projectile being fired.
     * @return The initial motion vector of the projectile.
     */
    private Vec3 calculateInitialMotion(@NotNull LocalPlayer localPlayer, @NotNull Rotation rotation, @NotNull ItemStack weapon, @NotNull Gun.Projectile projectile) {
        double speedModifier = GunEnchantmentHelper.getProjectileSpeedModifier(weapon),
                speed = GunModifierHelper.getModifiedProjectileSpeed(weapon, projectile.getSpeed() * speedModifier);

        AttributeInstance speedAttr = localPlayer.getAttribute(SCAttributes.PROJECTILE_SPEED.get());
        speed *= speedAttr != null ? speedAttr.getValue() : 1.0D;

        Vec3 baseVelocityVector = VectorUtil.getVectorFromRotation(rotation);
        return baseVelocityVector.scale(speed);
    }
    //endregion

    //region Gun Handling
    /**
     * Shoots the gun with the given rotation.
     *
     * @param player The player shooting.
     * @param rotation The rotation to shoot at.
     */
    private void shoot(LocalPlayer player, Rotation rotation) {
        ItemStack stack = player.getMainHandItem();
        if (isGunEmpty(player, stack)) {
            return;
        }
        GunUtil.sendShootingState(true);
        GunUtil.sendShootingRequest(rotation);
        GunUtil.sendShootingState(false);
    }
    //endregion

    //region Debug
    /**
     * Prints a debug message to the chat if debug logs are enabled.
     *
     * @param component The component or module name.
     * @param message The debug message.
     * @param colour The colour to use for the component name.
     */
    private void log(String component, String message, ChatFormatting colour) {
        if (!this.debugLogs.getValue()) return;
        ChatUtil.printMessage("%s[%s]%s %s".formatted(
                colour.toString(),
                component,
                ChatFormatting.RESET.toString(),
                message
        ));
    }
    //endregion

    private record RotationResult(Rotation rotation, boolean hit, int travelTicks) {
    }
}
