package me.darragh.eamfhc.feature.property;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents metadata for a property, including its ID, name, and an optional description.
 *
 * @author darraghd493
 */
@Value
@RequiredArgsConstructor
public class PropertyMetadata {
    @NotNull String identifier, name;
    @Nullable String description;

    public PropertyMetadata(String identifier, String name) {
        this(identifier, name, null);
    }
}
