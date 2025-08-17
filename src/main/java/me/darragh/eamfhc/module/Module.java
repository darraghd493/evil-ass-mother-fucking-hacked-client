package me.darragh.eamfhc.module;

import lombok.Getter;
import lombok.Setter;
import me.darragh.eamfhc.Client;
import me.darragh.eamfhc.feature.Bindable;
import me.darragh.eamfhc.feature.Feature;
import me.darragh.eamfhc.feature.Toggleable;
import me.darragh.eamfhc.feature.bindable.Bind;

/**
 * A toggleable module designed to be interacted with by the user.
 *
 * @author darragh
 */
public class Module implements Feature<ModuleMetadata>, Bindable, Toggleable {
    @Getter // Undocumented method
    private final ModuleIdentifier identifier;
    private final ModuleMetadata metadata;
    @SuppressWarnings("FieldMayBeFinal")
    @Getter
    private Bind bind = Bind.EMPTY.copy();
    @Getter
    @Setter
    private boolean enabled;

    public Module() {
        this.identifier = this.getClass().getAnnotation(ModuleIdentifier.class);
        if (this.identifier == null) {
            throw new IllegalStateException("ModuleIdentifier annotation is missing on " + this.getClass().getName());
        }
        this.metadata = ModuleMetadata.of(
                identifier.identifier(),
                identifier.displayName(),
                identifier.description(),
                identifier.type()
        );
    }

    @Override
    public ModuleMetadata getMetadata() {
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
