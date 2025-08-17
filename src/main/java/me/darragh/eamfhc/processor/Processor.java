package me.darragh.eamfhc.processor;

import lombok.Getter;
import me.darragh.eamfhc.Client;
import me.darragh.eamfhc.GameInstance;
import me.darragh.eamfhc.feature.Feature;

/**
 * Handles a background process within the client.
 *
 * @author darragh
 */
public class Processor implements Feature<ProcessorMetadata>, GameInstance {
    @Getter // Undocumented method
    private final ProcessorIdentifier identifier;
    private final ProcessorMetadata metadata;

    public Processor() {
        this.identifier = this.getClass().getAnnotation(ProcessorIdentifier.class);
        if (this.identifier == null) {
            throw new IllegalStateException("ProcessorIdentifier annotation is missing on " + this.getClass().getName());
        }
        this.metadata = ProcessorMetadata.of(
                identifier.identifier(),
                identifier.displayName(),
                identifier.description(),
                identifier.type()
        );
    }

    @Override
    public ProcessorMetadata getMetadata() {
        return this.metadata;
    }

    @Override
    public void init() {
        Client.INSTANCE.getEventDispatcher().register(this);
    }

    @Override
    public void destroy() {
        Client.INSTANCE.getEventDispatcher().unregister(this);
    }
}
