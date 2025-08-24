package me.darragh.eamfhc.module.impl.combat;

import me.darragh.eamfhc.event.impl.player.EventPlayerPreTick;
import me.darragh.eamfhc.module.Module;
import me.darragh.eamfhc.module.ModuleIdentifier;
import me.darragh.eamfhc.module.ModuleType;
import me.darragh.eamfhc.scguns.util.GunUtil;
import me.darragh.event.bus.Listener;

@ModuleIdentifier(
        identifier = "reduce-spread",
        displayName = "Reduce Spread",
        description = "Reduces spread in Scorched Guns 2.",
        type = ModuleType.COMBAT
)
public class ReduceSpreadModule extends Module {
    public static boolean active;

    @Override
    protected void onEnable() {
        super.onEnable();
        active = true;
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        active = false;
        // TODO: Disable
    }

    @Listener
    public void onPlayerPreTick(EventPlayerPreTick event) {
        GunUtil.sendAimingState(true);
    }
}