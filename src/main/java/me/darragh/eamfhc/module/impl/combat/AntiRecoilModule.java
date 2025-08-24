package me.darragh.eamfhc.module.impl.combat;

import me.darragh.eamfhc.module.Module;
import me.darragh.eamfhc.module.ModuleIdentifier;
import me.darragh.eamfhc.module.ModuleType;

@ModuleIdentifier(
        identifier = "anti-recoil",
        displayName = "Anti Recoil",
        description = "Antic-recoil for Scorched Guns 2.",
        type = ModuleType.COMBAT
)
public class AntiRecoilModule extends Module {
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
    }
}