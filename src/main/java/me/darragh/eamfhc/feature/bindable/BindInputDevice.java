package me.darragh.eamfhc.feature.bindable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.darragh.eamfhc.Identifiable;

/**
 * Represents an input device that can be used for binding features.
 *
 * @author darraghd493
 */
@Getter
@RequiredArgsConstructor
public enum BindInputDevice implements Identifiable { // TODO: Mouse support
    MOUSE("mouse"),
    KEYBOARD("keyboard");

    private final String identifier;
}
