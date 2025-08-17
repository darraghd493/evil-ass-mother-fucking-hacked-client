package me.darragh.eamfhc.feature;

import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * A simple implementation of {@link FeatureMetadata} that does not store any additional data.
 * This class can be used as a placeholder for features that do not require metadata/require minimal metadata.
 *
 * @author darraghd493
 */
@SuppressWarnings("ClassCanBeRecord")
@Value
@RequiredArgsConstructor(staticName = "of")
public class SimpleFeatureMetadata implements FeatureMetadata {
    String identifier, displayName, description;
}
