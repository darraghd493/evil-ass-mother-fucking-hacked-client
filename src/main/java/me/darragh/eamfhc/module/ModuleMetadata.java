package me.darragh.eamfhc.module;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import me.darragh.eamfhc.feature.FeatureMetadata;

/**
 * A module-specific metadata class that holds required feature metadata and any additional
 * information about a processor.
 *
 * @author darraghd493
 */
@SuppressWarnings("ClassCanBeRecord")
@Value
@RequiredArgsConstructor(staticName = "of")
public class ModuleMetadata implements FeatureMetadata {
    String identifier, displayName, description;
    ModuleType type;
}
