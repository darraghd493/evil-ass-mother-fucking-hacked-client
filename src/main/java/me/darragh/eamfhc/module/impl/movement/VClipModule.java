package me.darragh.eamfhc.module.impl.movement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.darragh.eamfhc.event.impl.player.EventPlayerTick;
import me.darragh.eamfhc.feature.property.PropertyEnum;
import me.darragh.eamfhc.feature.property.PropertyFactory;
import me.darragh.eamfhc.feature.property.PropertyMetadata;
import me.darragh.eamfhc.feature.property.constraints.NumberPropertyConstraints;
import me.darragh.eamfhc.feature.property.type.enumerable.EnumProperty;
import me.darragh.eamfhc.feature.property.type.number.IntegerProperty;
import me.darragh.eamfhc.module.Module;
import me.darragh.eamfhc.module.ModuleIdentifier;
import me.darragh.eamfhc.module.ModuleType;
import me.darragh.eamfhc.util.ChatUtil;
import me.darragh.eamfhc.util.PacketUtil;
import me.darragh.event.bus.Listener;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;

@ModuleIdentifier(
        identifier = "vclip",
        displayName = "VClip",
        description = "Vertically clips you.",
        type = ModuleType.MISC
)
public class VClipModule extends Module {
    private final EnumProperty<ModeEnum> mode = PropertyFactory.enumPropertyBuilder(this, ModeEnum.class)
            .metadata(new PropertyMetadata("mode", "Mode"))
            .defaultValue(ModeEnum.UP)
            .build();

    private final IntegerProperty distance = PropertyFactory.integerPropertyBuilder(this)
            .metadata(new PropertyMetadata("distance", "Distance"))
            .constraints(new NumberPropertyConstraints<>(1, 384, 1))
            .parent(this.mode)
            .visibility(() -> this.mode.getValue() == ModeEnum.UP || this.mode.getValue() == ModeEnum.DOWN)
            .defaultValue(50)
            .build();

    private int targetY;
    private boolean instant;

    @Override
    protected void onEnable() {
        super.onEnable();
        this.targetY = this.calculateY();
        if (this.targetY == -1) {
            ChatUtil.printMessage("V-clip will not perform - you will clip into the void!");
            this.disable();
            return;
        }
        assert minecraft.player != null : "Minecraft player is null";
        int distance = Math.abs(this.targetY - (int) minecraft.player.getY()) + 1; // + 1 for safety
        this.instant = distance / 6 <= 50;
        if (this.instant) {
            int currentY = (int) minecraft.player.getY();
            boolean upwards = this.targetY > currentY;
            while (upwards ? currentY < this.targetY : currentY > this.targetY) {
                PacketUtil.sendPacket(new ServerboundMovePlayerPacket.Pos(
                        minecraft.player.getX(),
                        upwards ? Math.min(currentY += 6, this.targetY) : Math.max(currentY -= 6, this.targetY),
                        minecraft.player.getZ(),
                        true
                ));
            }
            minecraft.player.setPos(
                    minecraft.player.getX(),
                    this.targetY,
                    minecraft.player.getZ()
            );
            ChatUtil.printMessage("V-clip completed successfully!");
            this.disable();
        } else {
            ChatUtil.printMessage("V-clip distance is too large to perform at once - it will be split into multiple clips. Please wait.");
        }
    }

    @Listener
    public void onPlayerTick(EventPlayerTick event) {
        if (this.instant) {
            return;
        }
        assert minecraft.player != null : "Minecraft player is null";
        int currentY = (int) minecraft.player.getY();
        boolean upwards = this.targetY > currentY;
        minecraft.player.setPos(
                minecraft.player.getX(),
                upwards ? Math.min(currentY += 6, this.targetY) : Math.max(currentY -= 6, this.targetY),
                minecraft.player.getZ()
        );
        if (currentY == this.targetY) {
            ChatUtil.printMessage("V-clip completed successfully!");
            this.disable();
        }
    }

    private int calculateY() {
        assert minecraft.player != null : "Minecraft player is null";
        return switch (this.mode.getValue()) {
            case UP -> (int) minecraft.player.getY() + this.distance.getValue();
            case DOWN -> (int) minecraft.player.getY() - this.distance.getValue();
        };
    }

    @Getter
    @RequiredArgsConstructor
    private enum ModeEnum implements PropertyEnum {
        UP("up", "Up"),
        DOWN("down", "Down");

        private final String identifier, name;
    }
}
