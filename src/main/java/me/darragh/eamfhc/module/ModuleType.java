package me.darragh.eamfhc.module;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.darragh.eamfhc.util.ColourUtil;

/**
 * An enum representing the possible types of modules.
 *
 * @author darraghd493
 */
@Getter
@RequiredArgsConstructor
public enum ModuleType {
    COMBAT("Combat", ColourUtil.getInt(255, 81, 84)),
    MOVEMENT("Movement", ColourUtil.getInt(243, 255, 155)),
    RENDER("Render", ColourUtil.getInt(76, 255, 249)),
    PLAYER("Player", ColourUtil.getInt(202, 150, 255)),
    WORLD("World", ColourUtil.getInt(210, 255, 170)),
    MISC("Misc", ColourUtil.getInt(255, 0, 246)),
    EXPLOIT("Exploit", ColourUtil.getInt(122, 122, 122));

    private final String displayName;
    private final int colour;
}
