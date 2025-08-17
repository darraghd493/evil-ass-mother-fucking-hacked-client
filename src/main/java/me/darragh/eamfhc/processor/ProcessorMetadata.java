package me.darragh.eamfhc.processor;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import me.darragh.eamfhc.feature.FeatureMetadata;

/**
 * A processor-specific metadata class that holds required feature metadata and any additional
 * information about a processor.
 *
 * @author darraghd493
 */
@SuppressWarnings("ClassCanBeRecord")
@Value
@RequiredArgsConstructor(staticName = "of")
public class ProcessorMetadata implements FeatureMetadata {
    String identifier, displayName, description;
    ProcessorType type;
}
